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

package com.liferay.portalweb.portal.dbupgrade.sampledata510.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddTemplateImageAssociationTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddTemplateImageAssociationTest extends BaseTestCase {
	public void testAddTemplateImageAssociation() throws Exception {
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Communities I Own")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Communities I Own", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_29_name",
			RuntimeVariables.replace("Web Content Image Association Community"));
		selenium.clickAt("//input[@value='Search Communities']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[@class='portlet-section-body results-row']/td[1]/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Journal Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Templates", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Template']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_15_newTemplateId",
			RuntimeVariables.replace("test_image"));
		selenium.type("_15_name",
			RuntimeVariables.replace("Image Template Test"));
		selenium.type("_15_description",
			RuntimeVariables.replace("This is an image template test."));
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("structure", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=structure");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=TEST_IMAGE")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("link=TEST_IMAGE");
		selenium.selectWindow("null");
		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("_15_structureName"));
		selenium.clickAt("_15_editorButton", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_15_xslContent")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_15_xslContent",
			RuntimeVariables.replace(
				"<h1 id=\"web-content-title\">$text-test.getData()</h1>\n\n<h2 id=\"image-title\">Image Test:</h2>\n<a id=\"image\"><img src=\"$image-test.data\"></img></a>\n\n<h2 id=\"image-gallery-title\">Image Gallery Image Test:</h2>\n<a id=\"image-gallery\"><img src=\"$image-gallery-test.data\"></img></a>"));
		selenium.click("//input[@value='Update']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals(RuntimeVariables.replace("TEST_IMAGE"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"Image Template Test\n This is an image template test."),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[3]"));
	}
}