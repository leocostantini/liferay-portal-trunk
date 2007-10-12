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

package com.liferay.portal.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.service.OrganizationLocalServiceUtil;

/**
 * <a href="OrganizationPermissionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 * @author Jorge Ferrer
 *
 */
public class OrganizationPermissionImpl implements OrganizationPermission {

	public void check(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, organizationId, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long organizationId,
			String actionId)
		throws PortalException, SystemException {

		PermissionCheckerImpl permissionCheckerImpl =
			(PermissionCheckerImpl)permissionChecker;

		if (permissionChecker.hasPermission(
				0, Organization.class.getName(), organizationId, actionId) ||
			(!actionId.equals(ActionKeys.PERMISSIONS) &&
			 hasGroupAdministratePermission(
				 permissionChecker, organizationId))) {

			return true;
		}
		else if (actionId.equals(ActionKeys.VIEW)) {
			User user = permissionCheckerImpl.getUser();

			long[] organizationIds = user.getOrganizationIds();

			for (int i = 0; i < organizationIds.length; i++) {
				if (organizationId == organizationIds[i]) {
					return true;
				}
			}

			return false;
		}
		else if (actionId.endsWith("_USER")){
			while (organizationId !=
						OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				organizationId = organization.getParentOrganizationId();

				if (permissionChecker.hasPermission(
						0, Organization.class.getName(), organizationId,
						actionId) ||
					(!actionId.equals(ActionKeys.PERMISSIONS) &&
					 hasGroupAdministratePermission(
						 permissionChecker, organization))) {

					return true;
				}
			}

			return false;
		}
		else {
			return false;
		}
	}

	protected boolean hasGroupAdministratePermission(
			PermissionChecker permissionChecker, long organizationId)
		throws SystemException, PortalException {

		if (organizationId <= 0) {
			return false;
		}

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		return hasGroupAdministratePermission(permissionChecker, organization);
	}

	protected boolean hasGroupAdministratePermission(
		PermissionChecker permissionChecker, Organization organization) {

		Group group = organization.getGroup();

		if (GroupPermissionUtil.contains(
				permissionChecker, group.getGroupId(),
				ActionKeys.ADMINISTRATE)) {

			return true;
		}
		else {
			return false;
		}
	}

}