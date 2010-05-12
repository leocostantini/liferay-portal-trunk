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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddAddress1MyAccountTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddAddress1MyAccountTest extends BaseTestCase {
	public void testAddAddress1MyAccount() throws Exception {
		selenium.open("/web/guest/home");

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

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_2_addressStreet10")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_2_addressStreet10",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.select("_2_addressTypeId0",
			RuntimeVariables.replace("label=Business"));
		selenium.type("_2_addressStreet20", RuntimeVariables.replace("Ste 12"));
		selenium.type("_2_addressZip0", RuntimeVariables.replace("91789"));
		selenium.type("_2_addressStreet30", RuntimeVariables.replace("Walnut"));
		selenium.type("_2_addressCity0", RuntimeVariables.replace("Los Angeles"));
		selenium.select("_2_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		selenium.click("_2_addressPrimary0");
		selenium.click("_2_addressMailing0Checkbox");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"AlabamaAlaskaArizonaArkansasCaliforniaColoradoConnecticutDelawareDistrict of ColumbiaFloridaGeorgiaHawaiiIdahoIllinoisIndianaIowaKansasKentuckyLouisianaMaineMarylandMassachusettsMichiganMinnesotaMississippiMissouriMontanaNebraskaNevadaNew HampshireNew JerseyNew MexicoNew YorkNorth CarolinaNorth DakotaOhioOklahomaOregonPennsylvaniaPuerto RicoRhode IslandSouth CarolinaSouth DakotaTennesseeTexasUtahVermontVirginiaWashingtonWest VirginiaWisconsinWyoming")
										.equals(selenium.getText(
								"_2_addressRegionId0"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("_2_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		Thread.sleep(5000);
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if ("United States".equals(selenium.getSelectedLabel(
								"_2_addressCountryId0"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if ("California".equals(selenium.getSelectedLabel(
								"_2_addressRegionId0"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("_2_addressStreet10"));
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[2]/div/div/div"));
		assertEquals("Business", selenium.getSelectedLabel("_2_addressTypeId0"));
		assertEquals("Ste 12", selenium.getValue("_2_addressStreet20"));
		assertEquals("91789", selenium.getValue("_2_addressZip0"));
		assertEquals("Walnut", selenium.getValue("_2_addressStreet30"));
		assertEquals("Los Angeles", selenium.getValue("_2_addressCity0"));
		assertEquals("United States",
			selenium.getSelectedLabel("_2_addressCountryId0"));
		assertTrue(selenium.isChecked("_2_addressPrimary0"));
		assertTrue(selenium.isChecked("_2_addressMailing0Checkbox"));
		assertEquals("California",
			selenium.getSelectedLabel("_2_addressRegionId0"));
	}
}