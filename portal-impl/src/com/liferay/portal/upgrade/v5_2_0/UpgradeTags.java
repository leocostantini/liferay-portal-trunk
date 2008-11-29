/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.SmartResultSet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.NoSuchVocabularyException;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.service.TagsVocabularyLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeTags.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeTags extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			updateGroupId();
			updateCategory();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected long copyEntry(long groupId, long entryId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsEntry where entryId = ?");

			ps.setLong(1, entryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");

				long newEntryId = CounterLocalServiceUtil.increment();

				ps = con.prepareStatement(
					"insert into TagsEntry (entryId, groupId, companyId, " +
						"userId, userName, createDate, modifiedDate, name) " +
							"values (?, ?, ?, ?, ?, ?, ?, ?)");

				ps.setLong(1, newEntryId);
				ps.setLong(2, groupId);
				ps.setLong(3, companyId);
				ps.setLong(4, userId);
				ps.setString(5, userName);
				ps.setTimestamp(6, createDate);
				ps.setTimestamp(7, modifiedDate);
				ps.setString(8, name);

				ps.executeUpdate();

				ps.close();

				copyProperties(entryId, newEntryId);

				return newEntryId;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		throw new NoSuchEntryException(
			"No TagsEntry exists with the primary key " + entryId);
	}

	public void copyProperties(long entryId, long newEntryId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsProperty where entryId = ?");

			ps.setLong(1, entryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String key = rs.getString("key_");
				String value = rs.getString("value");

				long newPropertyId = CounterLocalServiceUtil.increment();

				ps = con.prepareStatement(
					"insert into TagsProperty (propertyId, companyId, " +
						"userId, userName, createDate, modifiedDate, " +
							"entryId, key_, value) values (?, ?, ?, ?, ?, ?, " +
								"?, ?, ?)");

				ps.setLong(1, newPropertyId);
				ps.setLong(2, companyId);
				ps.setLong(3, userId);
				ps.setString(4, userName);
				ps.setTimestamp(5, createDate);
				ps.setTimestamp(6, modifiedDate);
				ps.setLong(7, newEntryId);
				ps.setString(8, key);
				ps.setString(9, value);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select entryId from TagsEntry where groupId = 0");

			rs = ps.executeQuery();

			while (rs.next()) {
				long entryId = rs.getLong("entryId");

				ps = con.prepareStatement(
					"delete from TagsAssets_TagsEntries where entryId = ?");

				ps.setLong(1, entryId);

				ps.executeUpdate();

				ps.close();

				ps = con.prepareStatement(
					"delete from TagsProperty where entryId = ?");

				ps.setLong(1, entryId);

				ps.executeUpdate();

				ps.close();
			}

			ps = con.prepareStatement(
				"delete from TagsEntry where groupId = 0");

			ps.executeUpdate();

			ps.close();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getVocabularyId(
			long userId, long groupId, String vocabularyName)
		throws Exception {

		vocabularyName = vocabularyName.trim();

		if (Validator.isNull(vocabularyName) ||
			ArrayUtil.contains(
				_DEFAULT_CATEGORY_PROPERTY_VALUES, vocabularyName)) {

			vocabularyName = PropsValues.TAGS_VOCABULARY_DEFAULT;
		}

		String key = groupId + StringPool.UNDERLINE + vocabularyName;

		TagsVocabulary vocabulary = _vocabulariesMap.get(key);

		if (vocabulary == null) {
			try {
				vocabulary = TagsVocabularyLocalServiceUtil.getGroupVocabulary(
					groupId, vocabularyName);
			}
			catch (NoSuchVocabularyException nsve) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddCommunityPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(groupId);

				vocabulary = TagsVocabularyLocalServiceUtil.addVocabulary(
					userId, vocabularyName, true, serviceContext);
			}

			_vocabulariesMap.put(key, vocabulary);
		}

		return vocabulary.getVocabularyId();
	}

	protected void updateCategory() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select TE.entryId, TE.groupId, TE.userId, TP.propertyId, " +
					"TP.value from TagsEntry TE, TagsProperty TP where " +
						"TE.entryId = TP.entryId and TE.vocabularyId <= 0 " +
							"and TP.key_ = 'category'");

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long entryId = srs.getLong("TE.entryId");
				long groupId = srs.getLong("TE.groupId");
				long userId = srs.getLong("TE.userId");
				long propertyId = srs.getLong("TP.propertyId");
				String value = srs.getString("TP.value");

				long vocabularyId = getVocabularyId(userId, groupId, value);

				ps = con.prepareStatement(
					"update TagsEntry set vocabularyId = ? where entryId = ?");

				ps.setLong(1, vocabularyId);
				ps.setLong(2, entryId);

				ps.executeUpdate();

				ps.close();

				ps = con.prepareStatement(
					"delete from TagsProperty where propertyId = ?");

				ps.setLong(1, propertyId);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select TA.assetId, TA.groupId, TA_TE.entryId from " +
					"TagsAssets_TagsEntries TA_TE inner join TagsAsset TA on " +
						"TA.assetId = TA_TE.assetId");

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long assetId = srs.getLong("TA.assetId");
				long groupId = srs.getLong("TA.groupId");
				long entryId = srs.getLong("TA_TE.entryId");

				long newEntryId = copyEntry(groupId, entryId);

				ps = con.prepareStatement(
					"insert into TagsAssets_TagsEntries (assetId, entryId) " +
						"values (?, ?)");

				ps.setLong(1, assetId);
				ps.setLong(2, newEntryId);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		deleteEntries();
	}

	private String[] _DEFAULT_CATEGORY_PROPERTY_VALUES = new String[] {
		"undefined", "no category", "category"
	};

	private static Log _log = LogFactory.getLog(UpgradeTags.class);

	private Map<String, TagsVocabulary> _vocabulariesMap =
		new HashMap<String, TagsVocabulary>();

}