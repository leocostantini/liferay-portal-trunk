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

package com.liferay.portlet.journal.service;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleResourceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourceLocalService
 * @generated
 */
public class JournalArticleResourceLocalServiceWrapper
	implements JournalArticleResourceLocalService {
	public JournalArticleResourceLocalServiceWrapper(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		_journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	/**
	* Adds the journal article resource to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource to add
	* @return the journal article resource that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource addJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.addJournalArticleResource(journalArticleResource);
	}

	/**
	* Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	*
	* @param resourcePrimKey the primary key for the new journal article resource
	* @return the new journal article resource
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource createJournalArticleResource(
		long resourcePrimKey) {
		return _journalArticleResourceLocalService.createJournalArticleResource(resourcePrimKey);
	}

	/**
	* Deletes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the journal article resource to delete
	* @throws PortalException if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteJournalArticleResource(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResourceLocalService.deleteJournalArticleResource(resourcePrimKey);
	}

	/**
	* Deletes the journal article resource from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResourceLocalService.deleteJournalArticleResource(journalArticleResource);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the journal article resource with the primary key.
	*
	* @param resourcePrimKey the primary key of the journal article resource to get
	* @return the journal article resource
	* @throws PortalException if a journal article resource with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource getJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResource(resourcePrimKey);
	}

	/**
	* Gets the journal article resource with the UUID and group id.
	*
	* @param uuid the UUID of journal article resource to get
	* @param groupId the group id of the journal article resource to get
	* @return the journal article resource
	* @throws PortalException if a journal article resource with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource getJournalArticleResourceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResourceByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources to return
	* @param end the upper bound of the range of journal article resources to return (not inclusive)
	* @return the range of journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getJournalArticleResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResources(start,
			end);
	}

	/**
	* Gets the number of journal article resources.
	*
	* @return the number of journal article resources
	* @throws SystemException if a system exception occurred
	*/
	public int getJournalArticleResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResourcesCount();
	}

	/**
	* Updates the journal article resource in the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource to update
	* @return the journal article resource that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.updateJournalArticleResource(journalArticleResource);
	}

	/**
	* Updates the journal article resource in the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource to update
	* @param merge whether to merge the journal article resource with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the journal article resource that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.updateJournalArticleResource(journalArticleResource,
			merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _journalArticleResourceLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_journalArticleResourceLocalService.setIdentifier(identifier);
	}

	public void deleteArticleResource(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResourceLocalService.deleteArticleResource(groupId,
			articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource getArticleResource(
		long articleResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResource(articleResourcePrimKey);
	}

	public long getArticleResourcePrimKey(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResourcePrimKey(groupId,
			articleId);
	}

	public long getArticleResourcePrimKey(java.lang.String uuid, long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResourcePrimKey(uuid,
			groupId, articleId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getArticleResources(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResources(groupId);
	}

	public JournalArticleResourceLocalService getWrappedJournalArticleResourceLocalService() {
		return _journalArticleResourceLocalService;
	}

	public void setWrappedJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		_journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	private JournalArticleResourceLocalService _journalArticleResourceLocalService;
}