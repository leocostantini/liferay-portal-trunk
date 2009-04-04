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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PermissionLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.OrgGroupPermissionPK;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.PermissionComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="PermissionLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class PermissionLocalServiceImpl extends PermissionLocalServiceBaseImpl {

	public Permission addPermission(
			long companyId, String actionId, long resourceId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		if (permission == null) {
			long permissionId = counterLocalService.increment(
				Permission.class.getName());

			permission = permissionPersistence.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resourceId);

			permissionPersistence.update(permission, false);
		}

		return permission;
	}

	public List<Permission> addPermissions(
			long companyId, String name, long resourceId,
			boolean portletActions)
		throws SystemException {

		List<String> actionIds = null;

		if (portletActions) {
			actionIds = ResourceActionsUtil.getPortletResourceActions(name);
		}
		else {
			actionIds = ResourceActionsUtil.getModelResourceActions(name);
		}

		return addPermissions(companyId, actionIds, resourceId);
	}

	public List<Permission> addPermissions(
			long companyId, List<String> actionIds, long resourceId)
		throws SystemException {

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resourceId);

		permissions = ListUtil.copy(permissions);

		Set<String> actionIdsSet = new HashSet<String>();

		for (Permission permission : permissions) {
			actionIdsSet.add(permission.getActionId());
		}

		for (String actionId : actionIds) {
			if (actionIdsSet.contains(actionId)) {
				continue;
			}

			long permissionId = counterLocalService.increment(
				Permission.class.getName());

			Permission permission = permissionPersistence.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resourceId);

			try {
				permissionPersistence.update(permission, false);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Add failed, fetch {actionId=" + actionId +
							", resourceId=" + resourceId + "}");
				}

				permission = permissionPersistence.fetchByA_R(
					actionId, resourceId, false);

				if (permission == null) {
					throw se;
				}
			}

			permissions.add(permission);
		}

		return permissions;
	}

	public void addUserPermissions(
			long userId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		List<Permission> permissions = permissionFinder.findByU_R(
			userId, resourceId);

		permissions = getPermissions(
			user.getCompanyId(), actionIds, resourceId);

		userPersistence.addPermissions(userId, permissions);

		PermissionCacheUtil.clearCache();
	}

	public List<String> getActions(List<Permission> permissions) {
		List<String> actionIds = new ArrayList<String>();

		Iterator<Permission> itr = permissions.iterator();

		while (itr.hasNext()) {
			Permission permission = itr.next();

			actionIds.add(permission.getActionId());
		}

		return actionIds;
	}

	public List<Permission> getGroupPermissions(long groupId, long resourceId)
		throws SystemException {

		return permissionFinder.findByG_R(groupId, resourceId);
	}

	public List<Permission> getGroupPermissions(
			long groupId, long companyId, String name, int scope,
			String primKey)
		throws SystemException {

		return permissionFinder.findByG_C_N_S_P(
			groupId, companyId, name, scope, primKey);
	}

	public List<Permission> getOrgGroupPermissions(
			long organizationId, long groupId, long resourceId)
		throws SystemException {

		return permissionFinder.findByO_G_R(
			organizationId, groupId, resourceId);
	}

	public long getLatestPermissionId() throws SystemException {
		List<Permission> permissions = permissionPersistence.findAll(
			0, 1, new PermissionComparator());

		if (permissions.size() == 0) {
			return 0;
		}
		else {
			Permission permission = permissions.get(0);

			return permission.getPermissionId();
		}
	}

	public List<Permission> getPermissions(
			long companyId, String[] actionIds, long resourceId)
		throws SystemException {

		List<Permission> permissions = new ArrayList<Permission>();

		for (int i = 0; i < actionIds.length; i++) {
			Permission permission = addPermission(
				companyId, actionIds[i], resourceId);

			permissions.add(permission);
		}

		return permissions;
	}

	public List<Permission> getRolePermissions(long roleId)
		throws SystemException {

		return rolePersistence.getPermissions(roleId);
	}

	public List<Permission> getRolePermissions(long roleId, long resourceId)
		throws SystemException {

		return permissionFinder.findByR_R(roleId, resourceId);
	}

	public List<Permission> getUserPermissions(long userId, long resourceId)
		throws SystemException {

		return permissionFinder.findByU_R(userId, resourceId);
	}

	public List<Permission> getUserPermissions(
			long userId, long companyId, String name, int scope, String primKey)
		throws SystemException {

		return permissionFinder.findByU_C_N_S_P(
			userId, companyId, name, scope, primKey);
	}

	public boolean hasGroupPermission(
			long groupId, String actionId, long resourceId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		// Return false if there is no permission based on the given action
		// id and resource id

		if (permission == null) {
			return false;
		}

		return groupPersistence.containsPermission(
			groupId, permission.getPermissionId());
	}

	public boolean hasRolePermission(
			long roleId, long companyId, String name, int scope,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		List<Resource> resources = resourcePersistence.findByCodeId(
			resourceCode.getCodeId());

		for (Resource resource : resources) {
			Permission permission = permissionPersistence.fetchByA_R(
				actionId, resource.getResourceId());

			if (permission != null) {
				if (rolePersistence.containsPermission(
						roleId, permission.getPermissionId())) {

					return true;
				}
			}
		}

		return false;
	}

	public boolean hasRolePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		Resource resource = resourcePersistence.fetchByC_P(
			resourceCode.getCodeId(), primKey);

		if (resource == null) {
			return false;
		}

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resource.getResourceId());

		if (permission == null) {
			return false;
		}

		return rolePersistence.containsPermission(
			roleId, permission.getPermissionId());
	}

	public boolean hasUserPermission(
			long userId, String actionId, long resourceId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		// Return false if there is no permission based on the given action
		// id and resource id

		if (permission == null) {
			return false;
		}

		return userPersistence.containsPermission(
			userId, permission.getPermissionId());
	}

	public boolean hasUserPermissions(
			long userId, long groupId, String actionId,
			String name, long[] resourceIds,
			PermissionCheckerBag permissionCheckerBag)
		throws SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		int block = 1;

		// Return false if there are no resources

		if ((Validator.isNull(actionId)) || (resourceIds == null) ||
			(resourceIds.length == 0)) {

			return false;
		}

		List<Permission> permissions = permissionFinder.findByA_R(
			actionId, resourceIds);

		// Return false if there are no permissions

		if (permissions.size() == 0) {
			return false;
		}

		// Record logs with the first resource id

		long resourceId = resourceIds[0];

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		//List<Group> userGroups = permissionCheckerBag.getUserGroups();
		//List<Organization> userOrgs = permissionCheckerBag.getUserOrgs();
		//List<Group> userOrgGroups = permissionCheckerBag.getUserOrgGroups();
		//List<Group> userUserGroupGroups =
		//	permissionCheckerBag.getUserUserGroupGroups();
		List<Group> groups = permissionCheckerBag.getGroups();
		List<Role> roles = permissionCheckerBag.getRoles();

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Check the organization and community intersection table. Break out of
		// this method if the user has one of the permissions set at the
		// intersection because that takes priority.

		//if (checkOrgGroupPermission(userOrgs, userGroups, permissions)) {
		//	return true;
		//}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 1) {
			return hasUserPermissions_1(
				userId, actionId, resourceId, permissions, groups, groupId,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 2) {
			return hasUserPermissions_2(
				userId, actionId, resourceId, permissions, groups, groupId,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 3) {
			return hasUserPermissions_3(
				userId, actionId, resourceId, permissions, groups, roles,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 4) {
			return hasUserPermissions_4(
				userId, actionId, resourceId, permissions, groups, roles,
				stopWatch, block);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			return hasUserPermissions_5(
				userId, actionId, resourceId, permissions, roles, stopWatch,
				block);
		}

		return false;
	}

	public void setGroupPermissions(
			long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		List<Permission> permissions = permissionFinder.findByG_R(
			groupId, resourceId);

		for (Permission permission : permissions) {
			groupPersistence.removePermission(groupId, permission);
		}

		permissions = getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		groupPersistence.addPermissions(groupId, permissions);

		PermissionCacheUtil.clearCache();
	}

	public void setGroupPermissions(
			String className, String classPK, long groupId,
			String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		long associatedGroupId = 0;

		if (className.equals(Organization.class.getName())) {
			long organizationId = GetterUtil.getLong(classPK);

			Organization organization =
				organizationPersistence.findByPrimaryKey(organizationId);

			orgGroupPermissionFinder.removeByO_G_R(
				organizationId, groupId, resourceId);

			associatedGroupId = organization.getGroup().getGroupId();
		}
		else if (className.equals(UserGroup.class.getName())) {
			long userGroupId = GetterUtil.getLong(classPK);

			UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
				userGroupId);

			associatedGroupId = userGroup.getGroup().getGroupId();
		}

		setGroupPermissions(associatedGroupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(
			long organizationId, long groupId, String[] actionIds,
			long resourceId)
		throws PortalException, SystemException {

		Organization organization =
			organizationPersistence.findByPrimaryKey(organizationId);

		long orgGroupId = organization.getGroup().getGroupId();

		List<Permission> permissions = permissionPersistence.findByResourceId(
			resourceId);

		for (Permission permission : permissions) {
			groupPersistence.removePermission(orgGroupId, permission);
		}

		permissions = getPermissions(
			organization.getCompanyId(), actionIds, resourceId);

		orgGroupPermissionFinder.removeByO_G_R(
			organizationId, groupId, resourceId);

		for (Permission permission : permissions) {
			OrgGroupPermissionPK pk = new OrgGroupPermissionPK(
				organizationId, groupId, permission.getPermissionId());

			OrgGroupPermission orgGroupPermission =
				orgGroupPermissionPersistence.create(pk);

			orgGroupPermissionPersistence.update(orgGroupPermission, false);
		}

		PermissionCacheUtil.clearCache();
	}

	public void setRolePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		if (scope == ResourceConstants.SCOPE_COMPANY) {

			// Remove group permission

			unsetRolePermissions(
				roleId, companyId, name, ResourceConstants.SCOPE_GROUP,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {

			// Remove company permission

			unsetRolePermissions(
				roleId, companyId, name, ResourceConstants.SCOPE_COMPANY,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			throw new NoSuchPermissionException();
		}

		Resource resource = resourceLocalService.addResource(
			companyId, name, scope, primKey);

		long resourceId = resource.getResourceId();

		Permission permission = permissionPersistence.fetchByA_R(
			actionId, resourceId);

		if (permission == null) {
			long permissionId = counterLocalService.increment(
				Permission.class.getName());

			permission = permissionPersistence.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resourceId);

			permissionPersistence.update(permission, false);
		}

		rolePersistence.addPermission(roleId, permission);

		PermissionCacheUtil.clearCache();

		SearchEngineUtil.updatePermissionFields(resourceId);
	}

	public void setRolePermissions(
			long roleId, long companyId, String name, int scope, String primKey,
			String[] actionIds)
		throws PortalException, SystemException {

		for (int i = 0; i < actionIds.length; i++) {
			String actionId = actionIds[i];

			setRolePermission(
				roleId, companyId, name, scope, primKey, actionId);
		}
	}

	public void setRolePermissions(
			long roleId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		Role role = rolePersistence.findByPrimaryKey(roleId);

		List<Permission> permissions = permissionFinder.findByR_R(
			roleId, resourceId);

		rolePersistence.removePermissions(roleId, permissions);

		permissions = getPermissions(
			role.getCompanyId(), actionIds, resourceId);

		rolePersistence.addPermissions(roleId, permissions);

		PermissionCacheUtil.clearCache();

		SearchEngineUtil.updatePermissionFields(resourceId);
	}

	public void setUserPermissions(
			long userId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		List<Permission> permissions = permissionFinder.findByU_R(
			userId, resourceId);

		userPersistence.removePermissions(userId, permissions);

		permissions = getPermissions(
			user.getCompanyId(), actionIds, resourceId);

		userPersistence.addPermissions(userId, permissions);

		PermissionCacheUtil.clearCache();
	}

	public void unsetRolePermission(long roleId, long permissionId)
		throws SystemException {

		Permission permission = permissionPersistence.fetchByPrimaryKey(
			permissionId);

		if (permission != null) {
			rolePersistence.removePermission(roleId, permission);
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetRolePermission(
			long roleId, long companyId, String name, int scope, String primKey,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode =
			resourceCodeLocalService.getResourceCode(
				companyId, name, scope);

		Resource resource = resourcePersistence.fetchByC_P(
			resourceCode.getCodeId(), primKey);

		if (resource != null) {
			Permission permission = permissionPersistence.fetchByA_R(
				actionId, resource.getResourceId());

			if (permission != null) {
				rolePersistence.removePermission(roleId, permission);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetRolePermissions(
			long roleId, long companyId, String name, int scope,
			String actionId)
		throws SystemException {

		ResourceCode resourceCode = resourceCodeLocalService.getResourceCode(
			companyId, name, scope);

		List<Resource> resources = resourcePersistence.findByCodeId(
			resourceCode.getCodeId());

		for (Resource resource : resources) {
			Permission permission = permissionPersistence.fetchByA_R(
				actionId, resource.getResourceId());

			if (permission != null) {
				rolePersistence.removePermission(roleId, permission);
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void unsetUserPermissions(
			long userId, String[] actionIds, long resourceId)
		throws SystemException {

		List<Permission> permissions = permissionFinder.findByU_A_R(
			userId, actionIds, resourceId);

		userPersistence.removePermissions(userId, permissions);

		PermissionCacheUtil.clearCache();
	}

	protected boolean checkOrgGroupPermission(
			List<Organization> organizations, List<Group> groups,
			List<Permission> permissions)
		throws PortalException, SystemException {

		for (Permission permission : permissions) {
			if (checkOrgGroupPermission(organizations, groups, permission)) {
				return true;
			}
		}

		return false;
	}

	protected boolean checkOrgGroupPermission(
			List<Organization> organizations, List<Group> groups,
			Permission permission)
		throws PortalException, SystemException {

		// Do not check for an OrgGroupPermission intersection unless there is
		// at least one organization and one group to check

		if ((organizations.size() == 0) || (groups.size() == 0)) {
			return false;
		}

		// Do not check unless the OrgGroupPermission intersection contains at
		// least one permission

		List<OrgGroupPermission> orgGroupPermissions =
			orgGroupPermissionPersistence.findByPermissionId(
				permission.getPermissionId());

		if (orgGroupPermissions.size() == 0) {
			return false;
		}

		for (OrgGroupPermission orgGroupPermission : orgGroupPermissions) {
			if (orgGroupPermission.containsOrganization(organizations) &&
				orgGroupPermission.containsGroup(groups)) {

				return true;
			}
		}

		// Throw an exception so that we do not continue checking permissions.
		// The user has a specific permission given in the OrgGroupPermission
		// intersection that prohibits him from going further.

		throw new NoSuchPermissionException(
			"User has a permission in OrgGroupPermission that does not match");
	}

	protected boolean hasUserPermissions_1(
			long userId, String actionId, long resourceId,
			List<Permission> permissions, List<Group> groups, long groupId,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Is the user connected to one of the permissions via group or
		// organization roles?

		if (groups.size() > 0) {
			if (permissionFinder.countByGroupsRoles(permissions, groups) > 0) {
				return true;
			}
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Is the user associated with groups or organizations that are directly
		// connected to one of the permissions?

		if (groups.size() > 0) {
			if (permissionFinder.countByGroupsPermissions(
					permissions, groups) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Is the user connected to one of the permissions via user roles?

		if (permissionFinder.countByUsersRoles(permissions, userId) > 0) {
			return true;
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Is the user connected to one of the permissions via user group roles?

		if (permissionFinder.countByUserGroupRole(
				permissions, userId, groupId) > 0) {

			return true;
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Is the user directly connected to one of the permissions?

		if (permissionFinder.countByUsersPermissions(permissions, userId) > 0) {
			return true;
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_2(
			long userId, String actionId, long resourceId,
			List<Permission> permissions, List<Group> groups, long groupId,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Call countByGroupsRoles, countByGroupsPermissions, countByUsersRoles,
		// countByUserGroupRole, and countByUsersPermissions in one method

		if (permissionFinder.containsPermissions_2(
				permissions, userId, groups, groupId)) {

			return true;
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_3(
			long userId, String actionId, long resourceId,
			List<Permission> permissions, List<Group> groups, List<Role> roles,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Is the user associated with groups or organizations that are directly
		// connected to one of the permissions?

		if (groups.size() > 0) {
			if (permissionFinder.countByGroupsPermissions(
					permissions, groups) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Is the user associated with a role that is directly connected to one
		// of the permissions?

		if (roles.size() > 0) {
			if (permissionFinder.countByRolesPermissions(
					permissions, roles) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		// Is the user directly connected to one of the permissions?

		if (permissionFinder.countByUsersPermissions(permissions, userId) > 0) {
			return true;
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_4(
			long userId, String actionId, long resourceId,
			List<Permission> permissions, List<Group> groups, List<Role> roles,
			StopWatch stopWatch, int block)
		throws SystemException {

		// Call countByGroupsPermissions, countByRolesPermissions, and
		// countByUsersPermissions in one method

		if (permissionFinder.containsPermissions_4(
				permissions, userId, groups, roles)) {

			return true;
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		return false;
	}

	protected boolean hasUserPermissions_5(
			long userId, String actionId, long resourceId,
			List<Permission> permissions, List<Role> roles, StopWatch stopWatch,
			int block)
		throws SystemException {

		if (roles.size() > 0) {
			if (permissionFinder.countByRolesPermissions(
					permissions, roles) > 0) {

				return true;
			}
		}

		logHasUserPermissions(userId, actionId, resourceId, stopWatch, block++);

		return false;
	}

	protected void logHasUserPermissions(
		long userId, String actionId, long resourceId, StopWatch stopWatch,
		int block) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		_log.debug(
			"Checking user permissions block " + block + " for " + userId +
				" " + actionId + " " + resourceId + " takes " +
					stopWatch.getTime() + " ms");
	}

	private static Log _log =
		LogFactoryUtil.getLog(PermissionLocalServiceImpl.class);

}