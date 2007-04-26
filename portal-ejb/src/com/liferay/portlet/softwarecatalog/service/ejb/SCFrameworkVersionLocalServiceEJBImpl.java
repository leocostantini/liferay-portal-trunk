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

package com.liferay.portlet.softwarecatalog.service.ejb;

import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="SCFrameworkVersionLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService
 * @see com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil
 * @see com.liferay.portlet.softwarecatalog.service.ejb.SCFrameworkVersionLocalServiceEJB
 * @see com.liferay.portlet.softwarecatalog.service.ejb.SCFrameworkVersionLocalServiceHome
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCFrameworkVersionLocalServiceImpl
 *
 */
public class SCFrameworkVersionLocalServiceEJBImpl
	implements SCFrameworkVersionLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String plid, java.lang.String name,
		java.lang.String url, boolean active, int priority,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.addFrameworkVersion(userId,
			plid, name, url, active, priority, addCommunityPermissions,
			addGuestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String plid, java.lang.String name,
		java.lang.String url, boolean active, int priority,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.addFrameworkVersion(userId,
			plid, name, url, active, priority, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String plid, java.lang.String name,
		java.lang.String url, boolean active, int priority,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.addFrameworkVersion(userId,
			plid, name, url, active, priority, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public void addFrameworkVersionResources(long frameworkVersionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCFrameworkVersionLocalServiceFactory.getTxImpl()
											 .addFrameworkVersionResources(frameworkVersionId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCFrameworkVersionLocalServiceFactory.getTxImpl()
											 .addFrameworkVersionResources(frameworkVersion,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(long frameworkVersionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCFrameworkVersionLocalServiceFactory.getTxImpl()
											 .addFrameworkVersionResources(frameworkVersionId,
			communityPermissions, guestPermissions);
	}

	public void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCFrameworkVersionLocalServiceFactory.getTxImpl()
											 .addFrameworkVersionResources(frameworkVersion,
			communityPermissions, guestPermissions);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCFrameworkVersionLocalServiceFactory.getTxImpl()
											 .deleteFrameworkVersion(frameworkVersionId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getFrameworkVersion(frameworkVersionId);
	}

	public java.util.List getFrameworkVersions(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getFrameworkVersions(groupId,
			begin, end);
	}

	public java.util.List getFrameworkVersions(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getFrameworkVersions(groupId,
			active);
	}

	public java.util.List getFrameworkVersions(long groupId, boolean active,
		int begin, int end) throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getFrameworkVersions(groupId,
			active, begin, end);
	}

	public int getFrameworkVersionsCount(long groupId)
		throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getFrameworkVersionsCount(groupId);
	}

	public int getFrameworkVersionsCount(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getFrameworkVersionsCount(groupId,
			active);
	}

	public java.util.List getProductVersionFrameworkVersions(
		long productVersionId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.getProductVersionFrameworkVersions(productVersionId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return SCFrameworkVersionLocalServiceFactory.getTxImpl()
													.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}