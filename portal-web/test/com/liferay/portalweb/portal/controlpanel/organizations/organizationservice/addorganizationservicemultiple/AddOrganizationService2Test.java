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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationservice.addorganizationservicemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationService2Test extends BaseTestCase {
	public void testAddOrganizationService2() throws Exception {
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
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("_126_keywords",
					RuntimeVariables.replace("Selenium"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Selenium"),
					selenium.getText("//td[2]/a/strong"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("servicesLink")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("servicesLink", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[8]/fieldset/div[2]/div/span/span/button[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[1]",
					RuntimeVariables.replace("Add Row"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("_126_orgLaborTypeId2")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("_126_orgLaborTypeId2",
					RuntimeVariables.replace("label=Training"));
				selenium.select("_126_sunOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_sunClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_monOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_monClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_tueOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_tueClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_wedOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_wedClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_thuOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_thuClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_friOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_friClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.select("_126_satOpen2",
					RuntimeVariables.replace("label=09:00"));
				selenium.select("_126_satClose2",
					RuntimeVariables.replace("label=05:00"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//section/div/div/div/div[1]"));
				assertEquals("Training",
					selenium.getSelectedLabel("_126_orgLaborTypeId1"));

			case 100:
				label = -1;
			}
		}
	}
}