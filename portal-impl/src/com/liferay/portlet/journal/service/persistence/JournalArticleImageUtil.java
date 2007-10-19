/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalArticleImageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleImageUtil {
	public static com.liferay.portlet.journal.model.JournalArticleImage create(
		long articleImageId) {
		return getPersistence().create(articleImageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage remove(
		long articleImageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(articleImageId));
		}

		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage =
			getPersistence().remove(articleImageId);

		if (listener != null) {
			listener.onAfterRemove(journalArticleImage);
		}

		return journalArticleImage;
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage remove(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(journalArticleImage);
		}

		journalArticleImage = getPersistence().remove(journalArticleImage);

		if (listener != null) {
			listener.onAfterRemove(journalArticleImage);
		}

		return journalArticleImage;
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage update(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalArticleImage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalArticleImage);
			}
			else {
				listener.onBeforeUpdate(journalArticleImage);
			}
		}

		journalArticleImage = getPersistence().update(journalArticleImage);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalArticleImage);
			}
			else {
				listener.onAfterUpdate(journalArticleImage);
			}
		}

		return journalArticleImage;
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage update(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage,
		boolean merge) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalArticleImage.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalArticleImage);
			}
			else {
				listener.onBeforeUpdate(journalArticleImage);
			}
		}

		journalArticleImage = getPersistence().update(journalArticleImage, merge);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalArticleImage);
			}
			else {
				listener.onAfterUpdate(journalArticleImage);
			}
		}

		return journalArticleImage;
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByPrimaryKey(
		long articleImageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByPrimaryKey(articleImageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage fetchByPrimaryKey(
		long articleImageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(articleImageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByUuid(uuid);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage fetchByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUuid(uuid);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage[] findByGroupId_PrevAndNext(
		long articleImageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByGroupId_PrevAndNext(articleImageId,
			groupId, obc);
	}

	public static java.util.List findByTempImage(boolean tempImage)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTempImage(tempImage);
	}

	public static java.util.List findByTempImage(boolean tempImage, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByTempImage(tempImage, begin, end);
	}

	public static java.util.List findByTempImage(boolean tempImage, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTempImage(tempImage, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByTempImage_First(
		boolean tempImage, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByTempImage_First(tempImage, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByTempImage_Last(
		boolean tempImage, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByTempImage_Last(tempImage, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage[] findByTempImage_PrevAndNext(
		long articleImageId, boolean tempImage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByTempImage_PrevAndNext(articleImageId,
			tempImage, obc);
	}

	public static java.util.List findByG_A_V(long groupId,
		java.lang.String articleId, double version)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A_V(groupId, articleId, version);
	}

	public static java.util.List findByG_A_V(long groupId,
		java.lang.String articleId, double version, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A_V(groupId, articleId, version, begin,
			end);
	}

	public static java.util.List findByG_A_V(long groupId,
		java.lang.String articleId, double version, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A_V(groupId, articleId, version, begin,
			end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByG_A_V_First(
		long groupId, java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByG_A_V_First(groupId, articleId, version,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByG_A_V_Last(
		long groupId, java.lang.String articleId, double version,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByG_A_V_Last(groupId, articleId, version,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage[] findByG_A_V_PrevAndNext(
		long articleImageId, long groupId, java.lang.String articleId,
		double version, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByG_A_V_PrevAndNext(articleImageId,
			groupId, articleId, version, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage findByG_A_V_E_L(
		long groupId, java.lang.String articleId, double version,
		java.lang.String elName, java.lang.String languageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		return getPersistence().findByG_A_V_E_L(groupId, articleId, version,
			elName, languageId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage fetchByG_A_V_E_L(
		long groupId, java.lang.String articleId, double version,
		java.lang.String elName, java.lang.String languageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_A_V_E_L(groupId, articleId, version,
			elName, languageId);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByTempImage(boolean tempImage)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTempImage(tempImage);
	}

	public static void removeByG_A_V(long groupId, java.lang.String articleId,
		double version) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_A_V(groupId, articleId, version);
	}

	public static void removeByG_A_V_E_L(long groupId,
		java.lang.String articleId, double version, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleImageException {
		getPersistence().removeByG_A_V_E_L(groupId, articleId, version, elName,
			languageId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByTempImage(boolean tempImage)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTempImage(tempImage);
	}

	public static int countByG_A_V(long groupId, java.lang.String articleId,
		double version) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_A_V(groupId, articleId, version);
	}

	public static int countByG_A_V_E_L(long groupId,
		java.lang.String articleId, double version, java.lang.String elName,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_A_V_E_L(groupId, articleId, version,
			elName, languageId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static JournalArticleImagePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(JournalArticleImagePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalArticleImageUtil _getUtil() {
		if (_util == null) {
			_util = (JournalArticleImageUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = JournalArticleImageUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalArticleImage"));
	private static Log _log = LogFactory.getLog(JournalArticleImageUtil.class);
	private static JournalArticleImageUtil _util;
	private JournalArticleImagePersistence _persistence;
}