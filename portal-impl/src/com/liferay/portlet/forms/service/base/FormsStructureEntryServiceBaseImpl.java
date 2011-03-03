/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.forms.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.forms.service.FormsStructureEntryLinkLocalService;
import com.liferay.portlet.forms.service.FormsStructureEntryLinkService;
import com.liferay.portlet.forms.service.FormsStructureEntryLocalService;
import com.liferay.portlet.forms.service.FormsStructureEntryService;
import com.liferay.portlet.forms.service.persistence.FormsStructureEntryLinkPersistence;
import com.liferay.portlet.forms.service.persistence.FormsStructureEntryPersistence;

import javax.sql.DataSource;

/**
 * The base implementation of the forms structure entry remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryServiceImpl}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.forms.service.impl.FormsStructureEntryServiceImpl
 * @see com.liferay.portlet.forms.service.FormsStructureEntryServiceUtil
 * @generated
 */
public abstract class FormsStructureEntryServiceBaseImpl extends PrincipalBean
	implements FormsStructureEntryService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.forms.service.FormsStructureEntryServiceUtil} to access the forms structure entry remote service.
	 */

	/**
	 * Gets the forms structure entry local service.
	 *
	 * @return the forms structure entry local service
	 */
	public FormsStructureEntryLocalService getFormsStructureEntryLocalService() {
		return formsStructureEntryLocalService;
	}

	/**
	 * Sets the forms structure entry local service.
	 *
	 * @param formsStructureEntryLocalService the forms structure entry local service
	 */
	public void setFormsStructureEntryLocalService(
		FormsStructureEntryLocalService formsStructureEntryLocalService) {
		this.formsStructureEntryLocalService = formsStructureEntryLocalService;
	}

	/**
	 * Gets the forms structure entry remote service.
	 *
	 * @return the forms structure entry remote service
	 */
	public FormsStructureEntryService getFormsStructureEntryService() {
		return formsStructureEntryService;
	}

	/**
	 * Sets the forms structure entry remote service.
	 *
	 * @param formsStructureEntryService the forms structure entry remote service
	 */
	public void setFormsStructureEntryService(
		FormsStructureEntryService formsStructureEntryService) {
		this.formsStructureEntryService = formsStructureEntryService;
	}

	/**
	 * Gets the forms structure entry persistence.
	 *
	 * @return the forms structure entry persistence
	 */
	public FormsStructureEntryPersistence getFormsStructureEntryPersistence() {
		return formsStructureEntryPersistence;
	}

	/**
	 * Sets the forms structure entry persistence.
	 *
	 * @param formsStructureEntryPersistence the forms structure entry persistence
	 */
	public void setFormsStructureEntryPersistence(
		FormsStructureEntryPersistence formsStructureEntryPersistence) {
		this.formsStructureEntryPersistence = formsStructureEntryPersistence;
	}

	/**
	 * Gets the forms structure entry link local service.
	 *
	 * @return the forms structure entry link local service
	 */
	public FormsStructureEntryLinkLocalService getFormsStructureEntryLinkLocalService() {
		return formsStructureEntryLinkLocalService;
	}

	/**
	 * Sets the forms structure entry link local service.
	 *
	 * @param formsStructureEntryLinkLocalService the forms structure entry link local service
	 */
	public void setFormsStructureEntryLinkLocalService(
		FormsStructureEntryLinkLocalService formsStructureEntryLinkLocalService) {
		this.formsStructureEntryLinkLocalService = formsStructureEntryLinkLocalService;
	}

	/**
	 * Gets the forms structure entry link remote service.
	 *
	 * @return the forms structure entry link remote service
	 */
	public FormsStructureEntryLinkService getFormsStructureEntryLinkService() {
		return formsStructureEntryLinkService;
	}

	/**
	 * Sets the forms structure entry link remote service.
	 *
	 * @param formsStructureEntryLinkService the forms structure entry link remote service
	 */
	public void setFormsStructureEntryLinkService(
		FormsStructureEntryLinkService formsStructureEntryLinkService) {
		this.formsStructureEntryLinkService = formsStructureEntryLinkService;
	}

	/**
	 * Gets the forms structure entry link persistence.
	 *
	 * @return the forms structure entry link persistence
	 */
	public FormsStructureEntryLinkPersistence getFormsStructureEntryLinkPersistence() {
		return formsStructureEntryLinkPersistence;
	}

	/**
	 * Sets the forms structure entry link persistence.
	 *
	 * @param formsStructureEntryLinkPersistence the forms structure entry link persistence
	 */
	public void setFormsStructureEntryLinkPersistence(
		FormsStructureEntryLinkPersistence formsStructureEntryLinkPersistence) {
		this.formsStructureEntryLinkPersistence = formsStructureEntryLinkPersistence;
	}

	/**
	 * Gets the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Gets the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Gets the resource remote service.
	 *
	 * @return the resource remote service
	 */
	public ResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * Sets the resource remote service.
	 *
	 * @param resourceService the resource remote service
	 */
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * Gets the resource persistence.
	 *
	 * @return the resource persistence
	 */
	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	/**
	 * Sets the resource persistence.
	 *
	 * @param resourcePersistence the resource persistence
	 */
	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	/**
	 * Gets the resource finder.
	 *
	 * @return the resource finder
	 */
	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	/**
	 * Sets the resource finder.
	 *
	 * @param resourceFinder the resource finder
	 */
	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	/**
	 * Gets the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Gets the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Gets the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Gets the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Gets the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query to perform
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = formsStructureEntryPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = FormsStructureEntryLocalService.class)
	protected FormsStructureEntryLocalService formsStructureEntryLocalService;
	@BeanReference(type = FormsStructureEntryService.class)
	protected FormsStructureEntryService formsStructureEntryService;
	@BeanReference(type = FormsStructureEntryPersistence.class)
	protected FormsStructureEntryPersistence formsStructureEntryPersistence;
	@BeanReference(type = FormsStructureEntryLinkLocalService.class)
	protected FormsStructureEntryLinkLocalService formsStructureEntryLinkLocalService;
	@BeanReference(type = FormsStructureEntryLinkService.class)
	protected FormsStructureEntryLinkService formsStructureEntryLinkService;
	@BeanReference(type = FormsStructureEntryLinkPersistence.class)
	protected FormsStructureEntryLinkPersistence formsStructureEntryLinkPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceFinder.class)
	protected ResourceFinder resourceFinder;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	private String _beanIdentifier;
}