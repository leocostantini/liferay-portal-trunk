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

package com.liferay.portalweb.portlet.activities.portlet;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.activities.portlet.addportlet.AddPortletTests;
import com.liferay.portalweb.portlet.activities.portlet.addportletduplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.activities.portlet.removeportlet.RemovePortletTests;
import com.liferay.portalweb.portlet.activities.portlet.viewportletlookandfeel.ViewPortletLookAndFeelTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletTests.suite());
		testSuite.addTest(AddPortletDuplicateTests.suite());
		testSuite.addTest(RemovePortletTests.suite());
		testSuite.addTest(ViewPortletLookAndFeelTests.suite());

		return testSuite;
	}

}