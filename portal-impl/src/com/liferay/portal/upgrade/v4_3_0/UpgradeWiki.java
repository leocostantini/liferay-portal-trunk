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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageResourcePrimKeyUpgradeColumnImpl;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeWiki.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeWiki extends UpgradeProcess {

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

		// WikiNode

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"nodeId", true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			WikiNodeImpl.TABLE_NAME, WikiNodeImpl.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.updateTable();

		ValueMapper nodeIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setWikiNodeIdMapper(nodeIdMapper);

		UpgradeColumn upgradeNodeIdColumn = new SwapUpgradeColumnImpl(
			"nodeId", nodeIdMapper);

		// WikiPage

		UpgradeColumn upgradeTitleColumn = new TempUpgradeColumnImpl("title");

		WikiPageIdUpgradeColumnImpl upgradePageIdColumn =
			new WikiPageIdUpgradeColumnImpl(
				upgradeNodeIdColumn, upgradeTitleColumn);

		UpgradeColumn upgradePageResourcePrimKeyColumn =
			new WikiPageResourcePrimKeyUpgradeColumnImpl(
				upgradePageIdColumn);

		upgradeTable = new DefaultUpgradeTableImpl(
			WikiPageImpl.TABLE_NAME, WikiPageImpl.TABLE_COLUMNS,
			upgradeNodeIdColumn, upgradeTitleColumn, upgradePageIdColumn,
			upgradePageResourcePrimKeyColumn, upgradeUserIdColumn);

		upgradeTable.updateTable();

		ValueMapper pageIdMapper = upgradePageIdColumn.getValueMapper();

		AvailableMappersUtil.setWikiPageIdMapper(pageIdMapper);

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter_column_type WikiNode userId LONG",

		"alter table WikiPage drop primary key",
		"alter table WikiPage add primary key (pageId)",
		"alter_column_type WikiPage userId LONG"
	};

	private static Log _log = LogFactory.getLog(UpgradeWiki.class);

}