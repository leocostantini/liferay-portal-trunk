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

import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ImageHeightUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageSizeUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageTextUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageTypeUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ImageWidthUpgradeColumnImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeImage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeImage extends UpgradeProcess {

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

		// Image

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(
			"imageId", new Integer(Types.VARCHAR), true);

		ImageTextUpgradeColumnImpl upgradeTextColumn =
			new ImageTextUpgradeColumnImpl();

		ImageTypeUpgradeColumnImpl upgradeTypeColumn =
			new ImageTypeUpgradeColumnImpl(upgradeTextColumn);

		ImageHeightUpgradeColumnImpl upgradeHeightColumn =
			new ImageHeightUpgradeColumnImpl(upgradeTextColumn);

		ImageWidthUpgradeColumnImpl upgradeWidthColumn =
			new ImageWidthUpgradeColumnImpl(upgradeTextColumn);

		ImageSizeUpgradeColumnImpl upgradeSizeColumn =
			new ImageSizeUpgradeColumnImpl(upgradeTextColumn);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ImageImpl.TABLE_NAME, ImageImpl.TABLE_COLUMNS,
			pkUpgradeColumn, upgradeTextColumn, upgradeTypeColumn,
			upgradeHeightColumn, upgradeWidthColumn, upgradeSizeColumn);

		upgradeTable.updateTable();

		ValueMapper imageIdMapper = pkUpgradeColumn.getValueMapper();

		AvailableMappersUtil.setImageIdMapper(imageIdMapper);

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter_column_type Image imageId LONG"
	};

	private static Log _log = LogFactory.getLog(UpgradeImage.class);

}