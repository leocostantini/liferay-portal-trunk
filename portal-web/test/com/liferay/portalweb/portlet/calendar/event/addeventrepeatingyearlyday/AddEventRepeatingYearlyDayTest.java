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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingyearlyday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddEventRepeatingYearlyDayTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingYearlyDayTest extends BaseTestCase {
	public void testAddEventRepeatingYearlyDay() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_8_startDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_startDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_startDateYear",
			RuntimeVariables.replace("label=2010"));
		selenium.type("_8_title",
			RuntimeVariables.replace("Yearly Day Event Test"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='6']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//input[@name='_8_yearlyType' and @value='1']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@name='_8_yearlyType' and @value='1']",
			RuntimeVariables.replace(""));
		selenium.select("_8_yearlyPos", RuntimeVariables.replace("label=Second"));
		selenium.select("_8_yearlyDay1",
			RuntimeVariables.replace("label=Monday"));
		selenium.select("_8_yearlyMonth1",
			RuntimeVariables.replace("label=February"));
		selenium.type("_8_yearlyInterval1", RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@name='_8_endDateType' and @value='2']",
			RuntimeVariables.replace(""));
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_endDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_endDateYear", RuntimeVariables.replace("label=2013"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2010"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[contains(@href, 'javascript:_8_updateCalendar(1, 8, 2010);')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 8, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Yearly Day Repeating Event"),
			selenium.getText("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2011"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[contains(@href, 'javascript:_8_updateCalendar(1, 8, 2011);')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 8, 2011);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Yearly Day Repeating Event"),
			selenium.getText("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2011"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//a[contains(@href, 'javascript:_8_updateCalendar(2, 8, 2011);')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 8, 2011);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Yearly Day Repeating Event"));
	}
}