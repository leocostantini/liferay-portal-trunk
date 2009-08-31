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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CA_ImageGalleryRolesTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CA_ImageGalleryRolesTest extends BaseTestCase {
	public void testCA_ImageGalleryRoles() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("add-permissions",
			RuntimeVariables.replace("label=Image Gallery"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderADD_IMAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderADD_SUBFOLDER']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImageDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImagePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImageUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImageVIEW']");
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}