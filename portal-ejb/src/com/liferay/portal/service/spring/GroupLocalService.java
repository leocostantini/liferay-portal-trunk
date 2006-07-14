/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.spring;

/**
 * <a href="GroupLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface GroupLocalService {
	public com.liferay.portal.model.Group addGroup(java.lang.String userId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void addRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void checkSystemGroups(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void deleteGroup(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getFriendlyURLGroup(
		java.lang.String companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getGroup(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getGroup(java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getOrganizationGroup(
		java.lang.String companyId, java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getOrganizationsGroups(java.util.List organizations)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getRoleGroups(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getUserGroup(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group getUserGroupGroup(
		java.lang.String companyId, java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getUserGroupsGroups(java.util.List userGroups)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasRoleGroup(java.lang.String roleId,
		java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasUserGroup(java.lang.String userId,
		java.lang.String groupId) throws com.liferay.portal.SystemException;

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String description,
		java.util.Map params, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int searchCount(java.lang.String companyId, java.lang.String name,
		java.lang.String description, java.util.Map params)
		throws com.liferay.portal.SystemException;

	public void setRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void unsetRoleGroups(java.lang.String roleId,
		java.lang.String[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group updateGroup(
		java.lang.String groupId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;
}