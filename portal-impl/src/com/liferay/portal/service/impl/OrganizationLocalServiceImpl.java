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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Location;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ListTypeImpl;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.base.OrganizationLocalServiceBaseImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.comparator.OrganizationNameComparator;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="OrganizationLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class OrganizationLocalServiceImpl
	extends OrganizationLocalServiceBaseImpl {

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		groupPersistence.addOrganizations(groupId, organizationIds);

		PermissionCacheUtil.clearCache();
	}

	public Organization addOrganization(
			long userId, long parentOrganizationId, String name,
			int type, boolean recursable, long regionId, long countryId,
			int statusId, String comments)
		throws PortalException, SystemException {

		// Organization

		User user = userPersistence.findByPrimaryKey(userId);
		parentOrganizationId = getParentOrganizationId(
			user.getCompanyId(), parentOrganizationId);

		validate(
			user.getCompanyId(), parentOrganizationId, name, type, countryId,
			statusId);

		long organizationId = counterLocalService.increment();

		Organization organization = organizationPersistence.create(
			organizationId);

		organization.setCompanyId(user.getCompanyId());
		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);

		if (type == OrganizationImpl.TYPE_LOCATION) {
			organization.setLocation(true);
		}
		else {
			organization.setLocation(false);
		}

		organization.setRecursable(recursable);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		organization.setComments(comments);

		organizationPersistence.update(organization);

		// Group

		groupLocalService.addGroup(
			userId, Organization.class.getName(),
			organization.getOrganizationId(), null, null, null, null, true);

		// Resources

		addOrganizationResources(userId, organization);

		return organization;
	}

	public void addOrganizationResources(long userId, Organization organization)
		throws PortalException, SystemException {

		String name = Organization.class.getName();

		if (organization.isLocation()) {
			name = Location.class.getName();
		}

		resourceLocalService.addResources(
			organization.getCompanyId(), 0, userId, name,
			organization.getOrganizationId(), false, false, false);
	}

	public void addPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		passwordPolicyRelLocalService.addPasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	public void deleteOrganization(long organizationId)
		throws PortalException, SystemException {

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		deleteOrganization(organization);
	}

	public void deleteOrganization(Organization organization)
		throws PortalException, SystemException {

		if ((organizationPersistence.containsUsers(
				organization.getOrganizationId())) ||
			(organizationPersistence.countByC_P(
				organization.getCompanyId(),
				organization.getOrganizationId()) > 0)) {

			throw new RequiredOrganizationException();
		}

		// Addresses

		addressLocalService.deleteAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Email addresses

		emailAddressLocalService.deleteEmailAddresses(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Password policy relation

		passwordPolicyRelLocalService.deletePasswordPolicyRel(
			Organization.class.getName(), organization.getOrganizationId());

		// Phone

		phoneLocalService.deletePhones(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Website

		websiteLocalService.deleteWebsites(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId());

		// Group

		Group group = organization.getGroup();

		groupLocalService.deleteGroup(group.getGroupId());

		// Resources

		String name = Organization.class.getName();

		if (organization.isLocation()) {
			name = Location.class.getName();
		}

		resourceLocalService.deleteResource(
			organization.getCompanyId(), name, ResourceImpl.SCOPE_INDIVIDUAL,
			organization.getOrganizationId());

		// Organization

		organizationPersistence.remove(organization.getOrganizationId());

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public List getAncestorOrganizations(long organizationId)
		throws PortalException, SystemException {

		List organizations = null;

		if (organizationId == OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID) {
			organizations = new ArrayList();
		}
		else {
			Organization organization =
				organizationPersistence.findByPrimaryKey(organizationId);

			organizations = getAncestorOrganizations(
				organization.getParentOrganizationId());

			organizations.add(organization);
		}

		return organizations;
	}

	public List getGroupOrganizations(long groupId)
		throws PortalException, SystemException {

		return groupPersistence.getOrganizations(groupId);
	}

	public Organization getOrganization(long organizationId)
		throws PortalException, SystemException {

		return organizationPersistence.findByPrimaryKey(organizationId);
	}

	public long getOrganizationId(long companyId, String name)
		throws PortalException, SystemException {

		try {
			Organization organization = organizationPersistence.findByC_N(
				companyId, name);

			return organization.getOrganizationId();
		}
		catch (NoSuchOrganizationException nsoge) {
			return 0;
		}
	}

	public List getOrganizations(long[] organizationIds)
		throws PortalException, SystemException {

		List organizations = new ArrayList();

		for (int i = 0; i < organizationIds.length; i++) {
			Organization organization = getOrganization(organizationIds[i]);

			organizations.add(organization);
		}

		return organizations;
	}

	public List getRecursableAncestorOrganizations(long organizationId)
		throws PortalException, SystemException {

		List organizations = new ArrayList();

		Iterator itr = getAncestorOrganizations(organizationId).iterator();

		while (itr.hasNext()){
			Organization organization = (Organization)itr.next();

			if (organization.isRecursable()){
				organizations.add(organization);
			}
		}

		return organizations;
	}

	public List getUserOrganizations(long userId)
		throws PortalException, SystemException {

		return userPersistence.getOrganizations(userId);
	}

	public boolean hasGroupOrganization(long groupId, long organizationId)
		throws PortalException, SystemException {

		return groupPersistence.containsOrganization(groupId, organizationId);
	}

	public boolean hasUserOrganization(long userId, long organizationId)
		throws PortalException, SystemException {

		return userPersistence.containsOrganization(userId, organizationId);
	}

	public boolean hasPasswordPolicyOrganization(
			long passwordPolicyId, long organizationId)
		throws PortalException, SystemException {

		return passwordPolicyRelLocalService.hasPasswordPolicyRel(
			passwordPolicyId, Organization.class.getName(), organizationId);
	}

	public boolean isAncestor(long locationId, long ancestorOrganizationId)
		throws PortalException, SystemException {

		Organization location = organizationPersistence.findByPrimaryKey(
			locationId);

		Iterator itr = getAncestorOrganizations(
			ancestorOrganizationId).iterator();

		while (itr.hasNext()){
			Organization ancestor = (Organization)itr.next();

			if (ancestor.getOrganizationId() ==
					location.getParentOrganizationId()){

				return true;
			}
		}

		return false;
	}

	public List search(
			long companyId, long parentOrganizationId, String keywords,
			int type, Long regionId, Long countryId, LinkedHashMap params,
			int begin, int end)
		throws PortalException, SystemException {

		return search(
			companyId, parentOrganizationId, keywords, type, regionId,
			countryId, params, begin, end,
			new OrganizationNameComparator(true));
	}

	public List search(
			long companyId, long parentOrganizationId, String keywords,
			int type, Long regionId, Long countryId, LinkedHashMap params,
			int begin, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		String parentOrganizationComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationImpl.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.findByKeywords(
			companyId, parentOrganizationId, parentOrganizationComparator,
			keywords, type, regionId, countryId, params, begin, end,
			obc);
	}

	public List search(
			long companyId, long parentOrganizationId, String name, int type,
			String street, String city, String zip,
			Long regionId, Long countryId, LinkedHashMap params,
			boolean andOperator, int begin, int end)
		throws PortalException, SystemException {

		return search(
			companyId, parentOrganizationId, name, type, street, city, zip,
			regionId, countryId, params, andOperator, begin, end,
			new OrganizationNameComparator(true));
	}

	public List search(
			long companyId, long parentOrganizationId, String name, int type,
			String street, String city, String zip,
			Long regionId, Long countryId, LinkedHashMap params,
			boolean andOperator, int begin, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		String parentOrganizationComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationImpl.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.findByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationComparator,
			name, type, street, city, zip, regionId, countryId, params,
			andOperator, begin, end, obc);
	}

	public int searchCount(
			long companyId, long parentOrganizationId, String keywords,
			int type, Long regionId, Long countryId,
			LinkedHashMap params)
		throws PortalException, SystemException {

		String parentOrganizationComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationImpl.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.countByKeywords(
			companyId, parentOrganizationId, parentOrganizationComparator,
			keywords, type, regionId, countryId, params);
	}

	public int searchCount(
			long companyId, long parentOrganizationId, String name, int type,
			String street, String city, String zip,
			Long regionId, Long countryId, LinkedHashMap params,
			boolean andOperator)
		throws PortalException, SystemException {

		String parentOrganizationComparator = StringPool.EQUAL;

		if (parentOrganizationId ==
				OrganizationImpl.ANY_PARENT_ORGANIZATION_ID) {

			parentOrganizationComparator = StringPool.NOT_EQUAL;
		}

		return organizationFinder.countByC_PO_N_T_S_C_Z_R_C(
			companyId, parentOrganizationId, parentOrganizationComparator,
			name, type, street, city, zip, regionId, countryId, params,
			andOperator);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		groupPersistence.setOrganizations(groupId, organizationIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws PortalException, SystemException {

		groupPersistence.removeOrganizations(groupId, organizationIds);

		PermissionCacheUtil.clearCache();
	}

	public void unsetPasswordPolicyOrganizations(
			long passwordPolicyId, long[] organizationIds)
		throws PortalException, SystemException {

		passwordPolicyRelLocalService.deletePasswordPolicyRels(
			passwordPolicyId, Organization.class.getName(), organizationIds);
	}

	public Organization updateOrganization(
			long companyId, long organizationId, long parentOrganizationId,
			String name, int type, boolean recursable, long regionId,
			long countryId, int statusId, String comments)
		throws PortalException, SystemException {

		parentOrganizationId = getParentOrganizationId(
			companyId, parentOrganizationId);

		validate(
			companyId, organizationId, parentOrganizationId, name, type,
			countryId, statusId);

		Organization organization = organizationPersistence.findByPrimaryKey(
			organizationId);

		organization.setParentOrganizationId(parentOrganizationId);
		organization.setName(name);

		if (type == OrganizationImpl.TYPE_LOCATION) {
			organization.setLocation(true);
		}
		else {
			organization.setLocation(false);
		}

		organization.setRecursable(recursable);
		organization.setRegionId(regionId);
		organization.setCountryId(countryId);
		organization.setStatusId(statusId);
		organization.setComments(comments);

		organizationPersistence.update(organization);

		return organization;
	}

	protected long getParentOrganizationId(
			long companyId, long parentOrganizationId)
		throws PortalException, SystemException {

		if (parentOrganizationId !=
				OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID) {

			// Ensure parent organization exists and belongs to the proper
			// company

			try {
				Organization parentOrganization =
					organizationPersistence.findByPrimaryKey(
						parentOrganizationId);

				if (companyId != parentOrganization.getCompanyId()) {
					parentOrganizationId =
						OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID;
				}
			}
			catch (NoSuchOrganizationException nsoe) {
				parentOrganizationId =
					OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID;
			}
		}

		return parentOrganizationId;
	}

	protected void validate(
			long companyId, long parentOrganizationId, String name, int type,
			long countryId, int statusId)
		throws PortalException, SystemException {

		validate(
			companyId, 0, parentOrganizationId, name, type, countryId,
			statusId);
	}

	protected void validate(
			long companyId, long organizationId, long parentOrganizationId,
			String name, int type, long countryId, int statusId)
		throws PortalException, SystemException {

		if ((type == OrganizationImpl.TYPE_LOCATION) ||
			(parentOrganizationId !=
				OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID)) {

			try {
				Organization parentOrganization =
					organizationPersistence.findByPrimaryKey(
						parentOrganizationId);

				if ((companyId != parentOrganization.getCompanyId()) ||
					(parentOrganizationId == organizationId) ||
					(parentOrganization.isLocation())) {

					throw new OrganizationParentException();
				}
			}
			catch (NoSuchOrganizationException nsoe) {
				throw new OrganizationParentException();
			}

		}

		if (Validator.isNull(name)) {
			throw new OrganizationNameException();
		}
		else {
			try {
				Organization organization = organizationPersistence.findByC_N(
					companyId, name);

				if (organization.getName().equalsIgnoreCase(name)) {
					if ((organizationId <= 0) ||
						(organization.getOrganizationId() != organizationId)) {

						throw new DuplicateOrganizationException();
					}
				}
			}
			catch (NoSuchOrganizationException nsoe) {
			}
		}

		try {
			boolean countryRequired = GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.ORGANIZATIONS_COUNTRY_REQUIRED));

			if ((countryId > 0) || countryRequired) {
				countryPersistence.findByPrimaryKey(countryId);
			}

			listTypeService.validate(
				statusId, ListTypeImpl.ORGANIZATION_STATUS);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

}