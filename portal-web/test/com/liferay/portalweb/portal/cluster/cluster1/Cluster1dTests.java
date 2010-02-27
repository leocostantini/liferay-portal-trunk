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

package com.liferay.portalweb.portal.cluster.cluster1;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.StopSeleniumTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="Cluster1dTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Cluster1dTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(N2_AssertMessageBoardsContentPresentTest.class);
		testSuite.addTestSuite(N2_UpdateMessageBoardsContentTest.class);

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}