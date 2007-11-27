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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NullSafeProperties;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.util.dao.DataAccess;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeLinkToLayoutPages.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class UpgradeLinkToLayoutPages extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(
				"SELECT plid,typeSettings FROM Layout" +
					" WHERE type_='link_to_layout'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings;

				try {
					newTypeSettings = upgradeTypeSettings(typeSettings);

					ps = con.prepareStatement(
						"UPDATE Layout SET typeSettings = '" + newTypeSettings +
							"' WHERE plid=" + plid);

					ps.executeUpdate();

				}
				catch (Exception e) {
					_log.error("Error upgrading layout with plid=" + plid, e);
				}

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	private String upgradeTypeSettings(String typeSettings)
		throws IOException, PortalException, SystemException {
		Properties props = new NullSafeProperties();

		PropertiesUtil.load(props,typeSettings);

		long linkToPlid = GetterUtil.getLong(props.getProperty("linkToPlid"));

		if (linkToPlid > 0) {
			Layout layout = LayoutLocalServiceUtil.getLayout(linkToPlid);

			props.remove("linkToPlid");
			props.put("groupId", Long.toString(layout.getGroupId()));
			props.put(
				"privateLayout", Boolean.toString(layout.isPrivateLayout()));
			props.put("linkToLayoutId", Long.toString(layout.getLayoutId()));
		}

		return PropertiesUtil.toString(props);
	}

	private static Log _log = LogFactory.getLog(UpgradeLinkToLayoutPages.class);

}