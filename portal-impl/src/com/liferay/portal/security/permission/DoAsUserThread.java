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

package com.liferay.portal.security.permission;

import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DoAsUserThread.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class DoAsUserThread extends Thread {

	public DoAsUserThread(long userId) {
		_userId = userId;
	}

	public boolean isSuccess() {
		return _success;
	}

	public void run() {
		PermissionChecker permissionChecker = null;

		try {
			PrincipalThreadLocal.setName(_userId);

			User user = UserLocalServiceUtil.getUserById(_userId);

			permissionChecker = PermissionCheckerFactory.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			doRun();

			_success = true;
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			if (permissionChecker != null) {
				try {
					PermissionCheckerFactory.recycle(permissionChecker);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
	}

	protected abstract void doRun() throws Exception;

	private static Log _log = LogFactory.getLog(DoAsUserThread.class);

	private long _userId;
	private boolean _success;

}