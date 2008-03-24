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

package com.liferay.portal.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * <a href="AccountPermissionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AccountPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException, SystemException {

		getAccountPermission().check(permissionChecker, accountId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException, SystemException {

		getAccountPermission().check(permissionChecker, account, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException, SystemException {

		return getAccountPermission().contains(
			permissionChecker, accountId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException, SystemException {

		return getAccountPermission().contains(
			permissionChecker, account, actionId);
	}

	public static AccountPermission getAccountPermission() {
		return _getUtil()._accountPermission;
	}

	public void setAccountPermission(AccountPermission accountPermission) {
		_accountPermission = accountPermission;
	}

	private static AccountPermissionUtil _getUtil() {
		if (_util == null) {
			_util = (AccountPermissionUtil)BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AccountPermissionUtil.class.getName();

	private static AccountPermissionUtil _util;

	private AccountPermission _accountPermission;

}