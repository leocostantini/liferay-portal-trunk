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

package com.liferay.portalweb.portlet.translator;

import com.liferay.portalweb.portal.BaseTests;

/**
 * <a href="TranslatorTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TranslatorTests extends BaseTests {

	public TranslatorTests() {
		addTestSuite(AddPageTest.class);
		addTestSuite(AddPortletTest.class);
		addTestSuite(TranslateEnglishChineseCTest.class);
		addTestSuite(TranslateEnglishChineseTTest.class);
		addTestSuite(TranslateEnglishDutchTest.class);
		addTestSuite(TranslateEnglishFrenchTest.class);
		addTestSuite(TranslateEnglishGermanTest.class);
		addTestSuite(TranslateEnglishItalianTest.class);
		addTestSuite(TranslateEnglishJapaneseTest.class);
		addTestSuite(TranslateEnglishPortugueseTest.class);
		addTestSuite(TranslateEnglishSpanishTest.class);
		addTestSuite(TranslateChineseCEnglishTest.class);
		addTestSuite(TranslateChineseTEnglishTest.class);
		addTestSuite(TranslateDutchEnglishTest.class);
		addTestSuite(TranslateFrenchEnglishTest.class);
		addTestSuite(TranslateFrenchGermanTest.class);
		addTestSuite(TranslateGermanEnglishTest.class);
		addTestSuite(TranslateGermanFrenchTest.class);
		addTestSuite(TranslateItalianEnglishTest.class);
		addTestSuite(TranslateJapaneseEnglishTest.class);
		addTestSuite(TranslatePortugueseEnglishTest.class);
		addTestSuite(TranslateSpanishEnglishTest.class);
		addTestSuite(TearDownTest.class);
	}

}