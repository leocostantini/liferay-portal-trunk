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

package com.liferay.portalweb.portal.controlpanel.virtualhosting;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertPublicPageHostURLTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertPublicPageHostURLTest extends BaseTestCase {
	public void testAssertPublicPageHostURL() throws Exception {
		selenium.open("http://www.able.com:8080/");
		Thread.sleep(5000);
		assertEquals("http://www.able.com:8080/", selenium.getLocation());
		assertEquals(RuntimeVariables.replace("Virtual Hosting Community"),
			selenium.getText("//h1/span"));
		assertTrue(selenium.isElementPresent("link=Public Page"));
		selenium.clickAt("link=Public Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Public Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals("http://www.able.com:8080/public-page",
			selenium.getLocation());
	}
}