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

package com.liferay.portalweb.properties.mailintegration.messageboards.replymessageemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownServerTest extends BaseTestCase {
	public void testTearDownServer() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Server Administration",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Mail", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_137_pop3Host", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();
				selenium.type("_137_pop3Port", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();

				boolean popSecureChecked = selenium.isChecked(
						"_137_pop3SecureCheckbox");

				if (!popSecureChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("_137_pop3SecureCheckbox",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("_137_pop3User", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();
				selenium.type("_137_pop3Password", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();
				selenium.type("_137_smtpHost", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();
				selenium.type("_137_smtpPort", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();

				boolean smtpSecureChecked = selenium.isChecked(
						"_137_smtpSecureCheckbox");

				if (!smtpSecureChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("_137_smtpSecureCheckbox",
					RuntimeVariables.replace(""));

			case 3:
				selenium.type("_137_smtpUser", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();
				selenium.type("_137_smtpPassword", RuntimeVariables.replace(""));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Portal Instances",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[4]/a", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_135_virtualHostname",
					RuntimeVariables.replace("localhost"));
				selenium.saveScreenShotAndSource();
				selenium.type("_135_mx", RuntimeVariables.replace("liferay.com"));
				selenium.saveScreenShotAndSource();
				selenium.type("_135_maxUsers", RuntimeVariables.replace("0"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}