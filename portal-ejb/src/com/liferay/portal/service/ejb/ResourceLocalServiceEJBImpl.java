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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ResourceLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portal.service.ResourceLocalService
 * @see com.liferay.portal.service.ResourceLocalServiceUtil
 * @see com.liferay.portal.service.ejb.ResourceLocalServiceEJB
 * @see com.liferay.portal.service.ejb.ResourceLocalServiceHome
 * @see com.liferay.portal.service.impl.ResourceLocalServiceImpl
 *
 */
public class ResourceLocalServiceEJBImpl implements ResourceLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void addModelResources(java.lang.String companyId, long groupId,
		java.lang.String userId, java.lang.String name, long primKey,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().addModelResources(companyId,
			groupId, userId, name, primKey, communityPermissions,
			guestPermissions);
	}

	public void addModelResources(java.lang.String companyId, long groupId,
		java.lang.String userId, java.lang.String name,
		java.lang.String primKey, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().addModelResources(companyId,
			groupId, userId, name, primKey, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portal.model.Resource addResource(
		java.lang.String companyId, java.lang.String name,
		java.lang.String scope, java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().addResource(companyId,
			name, scope, primKey);
	}

	public void addResources(java.lang.String companyId, long groupId,
		java.lang.String name, boolean portletActions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().addResources(companyId,
			groupId, name, portletActions);
	}

	public void addResources(java.lang.String companyId, long groupId,
		java.lang.String userId, java.lang.String name, long primKey,
		boolean portletActions, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().addResources(companyId,
			groupId, userId, name, primKey, portletActions,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addResources(java.lang.String companyId, long groupId,
		java.lang.String userId, java.lang.String name,
		java.lang.String primKey, boolean portletActions,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().addResources(companyId,
			groupId, userId, name, primKey, portletActions,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteResource(long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().deleteResource(resourceId);
	}

	public void deleteResource(com.liferay.portal.model.Resource resource)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().deleteResource(resource);
	}

	public void deleteResource(java.lang.String companyId,
		java.lang.String name, java.lang.String scope, long primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().deleteResource(companyId, name,
			scope, primKey);
	}

	public void deleteResource(java.lang.String companyId,
		java.lang.String name, java.lang.String scope, java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().deleteResource(companyId, name,
			scope, primKey);
	}

	public void deleteResources(java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ResourceLocalServiceFactory.getTxImpl().deleteResources(name);
	}

	public long getLatestResourceId()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().getLatestResourceId();
	}

	public com.liferay.portal.model.Resource getResource(long resourceId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().getResource(resourceId);
	}

	public java.util.List getResources()
		throws com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().getResources();
	}

	public com.liferay.portal.model.Resource getResource(
		java.lang.String companyId, java.lang.String name,
		java.lang.String scope, java.lang.String primKey)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ResourceLocalServiceFactory.getTxImpl().getResource(companyId,
			name, scope, primKey);
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