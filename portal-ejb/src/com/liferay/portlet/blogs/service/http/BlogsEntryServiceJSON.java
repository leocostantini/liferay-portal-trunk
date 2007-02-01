/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.http;

import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import org.json.JSONObject;

/**
 * <a href="BlogsEntryServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsEntryServiceJSON {
	public static JSONObject addEntry(java.lang.String plid,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.addEntry(plid,
				categoryId, title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				addCommunityPermissions, addGuestPermissions);

		return BlogsEntryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addEntry(java.lang.String plid,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.addEntry(plid,
				categoryId, title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				communityPermissions, guestPermissions);

		return BlogsEntryJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteEntry(java.lang.String entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		BlogsEntryServiceUtil.deleteEntry(entryId);
	}

	public static JSONObject getEntry(java.lang.String entryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(entryId);

		return BlogsEntryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateEntry(java.lang.String entryId,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.updateEntry(entryId,
				categoryId, title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute);

		return BlogsEntryJSONSerializer.toJSONObject(returnValue);
	}
}