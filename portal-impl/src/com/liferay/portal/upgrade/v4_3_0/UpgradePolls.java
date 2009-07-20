/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.PollsChoiceIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PollsVoteChoiceIdUpgradeColumnImpl;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;

import java.sql.Types;

public class UpgradePolls extends UpgradeProcess {

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

		// PollsQuestion

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"questionId", true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			PollsQuestionImpl.TABLE_NAME, PollsQuestionImpl.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(PollsQuestionImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper questionIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setPollsQuestionIdMapper(questionIdMapper);

		UpgradeColumn upgradeQuestionIdColumn = new SwapUpgradeColumnImpl(
			"questionId", questionIdMapper);

		// PollsChoice

		PKUpgradeColumnImpl upgradeChoiceId =
			new PollsChoiceIdUpgradeColumnImpl(upgradeQuestionIdColumn);

		upgradeTable = new DefaultUpgradeTableImpl(
			PollsChoiceImpl.TABLE_NAME, PollsChoiceImpl.TABLE_COLUMNS,
			upgradeQuestionIdColumn, upgradeChoiceId);

		upgradeTable.setCreateSQL(PollsChoiceImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper choiceIdMapper = upgradeChoiceId.getValueMapper();

		// PollsVote

		UpgradeColumn upgradeVoteChoiceIdColumn =
			new PollsVoteChoiceIdUpgradeColumnImpl(
				upgradeQuestionIdColumn, choiceIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			PollsVoteImpl.TABLE_NAME, PollsVoteImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("voteId", false), upgradeUserIdColumn,
			upgradeQuestionIdColumn, upgradeVoteChoiceIdColumn);

		upgradeTable.setCreateSQL(PollsVoteImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradePolls.class);

}