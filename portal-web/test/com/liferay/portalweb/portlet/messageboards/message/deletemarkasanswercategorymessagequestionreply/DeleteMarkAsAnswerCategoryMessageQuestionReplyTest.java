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

package com.liferay.portalweb.portlet.messageboards.message.deletemarkasanswercategorymessagequestionreply;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="DeleteMarkAsAnswerCategoryMessageQuestionReplyTest.java.html"><b>
 * <i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DeleteMarkAsAnswerCategoryMessageQuestionReplyTest
	extends BaseTestCase {
	public void testDeleteMarkAsAnswerCategoryMessageQuestionReply()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Resolved"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		selenium.clickAt("link=T\u00e9st M\u00e9ssag\u00e9 Question",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//li[5]/span/a/span"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S]$"));
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals(RuntimeVariables.replace("Waiting for an Answer"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		assertNotEquals(RuntimeVariables.replace("Resolved"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
	}
}