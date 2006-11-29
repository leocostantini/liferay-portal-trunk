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

package com.liferay.portal.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;

/**
 * <a href="PortletPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPermission {

	public static void check(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, portletId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, String plid, String portletId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, portletId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException {

		return contains(permissionChecker, null, portletId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String plid, String portletId,
			String actionId)
		throws PortalException, SystemException {

		String groupId = null;
		String name = null;
		String primKey = null;

		if (plid != null) {
			String layoutId = LayoutImpl.getLayoutId(plid);
			String ownerId = LayoutImpl.getOwnerId(plid);

			groupId = LayoutImpl.getGroupId(ownerId);
			name = PortletImpl.getRootPortletId(portletId);
			primKey = getPrimaryKey(plid, portletId);

			if (LayoutPermission.contains(
					permissionChecker, layoutId, ownerId, ActionKeys.UPDATE)) {

				return true;
			}
		}
		else {
			name = portletId;
			primKey = portletId;
		}

		return permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	public static String getPrimaryKey(String plid, String portletId) {
		return plid + PortletImpl.LAYOUT_SEPARATOR + portletId;
	}

}