/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.enterpriseadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="Member_MessageBoardsRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Member_MessageBoardsRolesTest extends BaseTestCase {
	public void testMember_MessageBoardsRoles() throws Exception {
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Portlet Permissions']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Next"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Message Boards"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_79_scope19VIEW",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBCategoryADD_FILE",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBCategoryVIEW",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.select("_79_scopecom.liferay.portlet.messageboards.model.MBMessageVIEW",
			RuntimeVariables.replace("label=Enterprise"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
	}
}