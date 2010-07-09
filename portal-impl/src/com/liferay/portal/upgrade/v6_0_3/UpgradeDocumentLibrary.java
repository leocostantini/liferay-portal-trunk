/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateFileEntries();
		updateFileVersions();
	}

	protected long getLatestFileVersionId(long folderId, String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long fileVersionId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileVersionId from DLFileVersion where folderId = ? " +
					"and name = ? order by version desc");

			ps.setLong(1, folderId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				fileVersionId = rs.getLong("fileVersionId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return fileVersionId;
	}

	protected void updateFileEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select uuid_, fileEntryId, groupId, folderId, name, title " +
					"from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				String uuid_ = rs.getString("uuid_");
				long fileEntryId = rs.getLong("fileEntryId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				String title = rs.getString("title");

				String extension = FileUtil.getExtension(title);

				runSQL(
					"update DLFileEntry set extension = '" + extension +
						"' where uuid_ = '" + uuid_ + "' and groupId = " +
							groupId);

				long fileVersionId = getLatestFileVersionId(folderId, name);

				runSQL(
					"update ExpandoRow set classPK = " + fileVersionId +
						 " where classPK = " + fileEntryId);

				runSQL(
					"update ExpandoValue set classPK = " + fileVersionId +
						 " where classPK = " + fileEntryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileVersion(
			long fileVersionId, String extension, String title, String
			description, String extraSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update DLFileVersion set extension = ?, title = ?, " +
					"description = ?,  extraSettings = ? where fileVersionId " +
						"= ?");

			ps.setString(1, extension);
			ps.setString(2, title);
			ps.setString(3, description);
			ps.setString(4, extraSettings);
			ps.setLong(5, fileVersionId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select folderId, name, extension, title, description, " +
					"extraSettings from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				String extension = rs.getString("extension");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String extraSettings = rs.getString("extraSettings");

				long fileVersionId = getLatestFileVersionId(folderId, name);

				updateFileVersion(
					fileVersionId, extension, title, description,
					extraSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}