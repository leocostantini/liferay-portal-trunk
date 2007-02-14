/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.test.service.portlet.bookmarks;

import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.test.service.BaseServiceTest;

/**
 * <a href="BookmarksFolderServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BookmarksFolderServiceTest extends BaseServiceTest {

	public void test() {
		try {
			String plid = "PRI.3.1";
			String parentFolderId = "-1";
			String name = "Test Folder";
			String description = "This is a test folder.";

			String[] communityPermissions = new String[0];
			String[] guestPermissions = new String[0];

			BookmarksFolderServiceUtil.addFolder(
				plid, parentFolderId, name, description, communityPermissions,
				guestPermissions);
		}
		catch (Exception e) {
			fail(e);
		}
	}

}