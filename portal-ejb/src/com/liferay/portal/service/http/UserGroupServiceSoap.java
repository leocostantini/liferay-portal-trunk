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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.UserGroupServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="UserGroupServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserGroupServiceSoap {
	public static void addGroupUserGroups(java.lang.String groupId,
		java.lang.String[] userGroupIds) throws RemoteException {
		try {
			UserGroupServiceUtil.addGroupUserGroups(groupId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap addUserGroup(
		java.lang.String name, java.lang.String description)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.addUserGroup(name,
					description);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteUserGroup(java.lang.String userGroupId)
		throws RemoteException {
		try {
			UserGroupServiceUtil.deleteUserGroup(userGroupId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap getUserGroup(
		java.lang.String userGroupId) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(userGroupId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap[] getUserUserGroups(
		java.lang.String userId) throws RemoteException {
		try {
			java.util.List returnValue = UserGroupServiceUtil.getUserUserGroups(userId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetGroupUserGroups(java.lang.String groupId,
		java.lang.String[] userGroupIds) throws RemoteException {
		try {
			UserGroupServiceUtil.unsetGroupUserGroups(groupId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap updateUserGroup(
		java.lang.String userGroupId, java.lang.String name,
		java.lang.String description) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.updateUserGroup(userGroupId,
					name, description);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupServiceSoap.class);
}