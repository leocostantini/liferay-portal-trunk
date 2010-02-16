/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PermissionFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PermissionFinderUtil {
	public static boolean containsPermissions_2(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .containsPermissions_2(permissions, userId, groups, groupId);
	}

	public static boolean containsPermissions_4(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .containsPermissions_4(permissions, userId, groups, roles);
	}

	public static int countByGroupsPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByGroupsPermissions(permissions, groups);
	}

	public static int countByGroupsRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByGroupsRoles(permissions, groups);
	}

	public static int countByRolesPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByRolesPermissions(permissions, roles);
	}

	public static int countByUserGroupRole(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUserGroupRole(permissions, userId, groupId);
	}

	public static int countByUsersPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUsersPermissions(permissions, userId);
	}

	public static int countByUsersRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUsersRoles(permissions, userId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByA_R(
		java.lang.String actionId, long[] resourceIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByA_R(actionId, resourceIds);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByG_R(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_R(groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByR_R(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByR_R(roleId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByU_R(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_R(userId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByO_G_R(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByO_G_R(organizationId, groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByU_A_R(
		long userId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_A_R(userId, actionIds, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByG_C_N_S_P(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByG_C_N_S_P(groupId, companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByU_C_N_S_P(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByU_C_N_S_P(userId, companyId, name, scope, primKey);
	}

	public static PermissionFinder getFinder() {
		if (_finder == null) {
			_finder = (PermissionFinder)PortalBeanLocatorUtil.locate(PermissionFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(PermissionFinder finder) {
		_finder = finder;
	}

	private static PermissionFinder _finder;
}