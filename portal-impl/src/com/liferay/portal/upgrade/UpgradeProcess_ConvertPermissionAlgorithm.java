/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade;

import com.liferay.portal.convert.ConvertPermissionAlgorithm;
import com.liferay.portal.convert.ConvertProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_ConvertPermissionAlgorithm extends UpgradeProcess {

	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_0_0_BUILD_NUMBER;
	}

	protected void doUpgrade() throws Exception {
		ConvertProcess convertProcess = new ConvertPermissionAlgorithm();

		convertProcess.setParameterValues(
			new String[] {Boolean.FALSE.toString()});

		convertProcess.convert();
	}

}