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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="UserGroupRoleModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>UserGroupRole</code> table in
 * the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.UserGroupRole
 * @see com.liferay.portal.service.model.UserGroupRoleModel
 * @see com.liferay.portal.service.model.impl.UserGroupRoleImpl
 *
 */
public class UserGroupRoleModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "UserGroupRole";
	public static Object[][] TABLE_COLUMNS = {
			{ "userId", new Integer(Types.VARCHAR) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "roleId", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserGroupRole"), XSS_ALLOW);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserGroupRole.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ROLEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.UserGroupRole.roleId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserGroupRoleModel"));

	public UserGroupRoleModelImpl() {
	}

	public UserGroupRolePK getPrimaryKey() {
		return new UserGroupRolePK(_userId, _groupId, _roleId);
	}

	public void setPrimaryKey(UserGroupRolePK pk) {
		setUserId(pk.userId);
		setGroupId(pk.groupId);
		setRoleId(pk.roleId);
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public String getRoleId() {
		return GetterUtil.getString(_roleId);
	}

	public void setRoleId(String roleId) {
		if (((roleId == null) && (_roleId != null)) ||
				((roleId != null) && (_roleId == null)) ||
				((roleId != null) && (_roleId != null) &&
				!roleId.equals(_roleId))) {
			if (!XSS_ALLOW_ROLEID) {
				roleId = XSSUtil.strip(roleId);
			}

			_roleId = roleId;
		}
	}

	public Object clone() {
		UserGroupRoleImpl clone = new UserGroupRoleImpl();
		clone.setUserId(getUserId());
		clone.setGroupId(getGroupId());
		clone.setRoleId(getRoleId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserGroupRoleImpl userGroupRole = (UserGroupRoleImpl)obj;
		UserGroupRolePK pk = userGroupRole.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserGroupRoleImpl userGroupRole = null;

		try {
			userGroupRole = (UserGroupRoleImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		UserGroupRolePK pk = userGroupRole.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _userId;
	private long _groupId;
	private String _roleId;
}