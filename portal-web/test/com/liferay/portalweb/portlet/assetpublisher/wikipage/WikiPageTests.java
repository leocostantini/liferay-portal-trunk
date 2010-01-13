/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher.wikipage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewcountwikipage.ViewCountWikiPageTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewwikipageabstracts.ViewWikiPageAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewwikipagedynamicassettypewikipage.ViewWikiPageDynamicAssetTypeWikiPageTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewwikipagefullcontent.ViewWikiPageFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewwikipagetable.ViewWikiPageTableTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewwikipagetitlelist.ViewWikiPageTitleListTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="WikiPageTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WikiPageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewCountWikiPageTests.suite());
		testSuite.addTest(ViewWikiPageAbstractsTests.suite());
		testSuite.addTest(ViewWikiPageDynamicAssetTypeWikiPageTests.suite());
		testSuite.addTest(ViewWikiPageFullContentTests.suite());
		testSuite.addTest(ViewWikiPageTableTests.suite());
		testSuite.addTest(ViewWikiPageTitleListTests.suite());

		return testSuite;
	}

}