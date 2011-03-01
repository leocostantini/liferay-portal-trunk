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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link PasswordTrackerLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTrackerLocalService
 * @generated
 */
public class PasswordTrackerLocalServiceWrapper
	implements PasswordTrackerLocalService {
	public PasswordTrackerLocalServiceWrapper(
		PasswordTrackerLocalService passwordTrackerLocalService) {
		_passwordTrackerLocalService = passwordTrackerLocalService;
	}

	/**
	* Adds the password tracker to the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to add
	* @return the password tracker that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordTracker addPasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.addPasswordTracker(passwordTracker);
	}

	/**
	* Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	*
	* @param passwordTrackerId the primary key for the new password tracker
	* @return the new password tracker
	*/
	public com.liferay.portal.model.PasswordTracker createPasswordTracker(
		long passwordTrackerId) {
		return _passwordTrackerLocalService.createPasswordTracker(passwordTrackerId);
	}

	/**
	* Deletes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTrackerId the primary key of the password tracker to delete
	* @throws PortalException if a password tracker with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deletePasswordTracker(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTracker(passwordTrackerId);
	}

	/**
	* Deletes the password tracker from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deletePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTracker(passwordTracker);
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
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery);
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
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
		return _passwordTrackerLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _passwordTrackerLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the password tracker with the primary key.
	*
	* @param passwordTrackerId the primary key of the password tracker to get
	* @return the password tracker
	* @throws PortalException if a password tracker with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordTracker getPasswordTracker(
		long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTracker(passwordTrackerId);
	}

	/**
	* Gets a range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of password trackers to return
	* @param end the upper bound of the range of password trackers to return (not inclusive)
	* @return the range of password trackers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.PasswordTracker> getPasswordTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTrackers(start, end);
	}

	/**
	* Gets the number of password trackers.
	*
	* @return the number of password trackers
	* @throws SystemException if a system exception occurred
	*/
	public int getPasswordTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.getPasswordTrackersCount();
	}

	/**
	* Updates the password tracker in the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to update
	* @return the password tracker that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.updatePasswordTracker(passwordTracker);
	}

	/**
	* Updates the password tracker in the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to update
	* @param merge whether to merge the password tracker with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the password tracker that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.updatePasswordTracker(passwordTracker,
			merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _passwordTrackerLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_passwordTrackerLocalService.setIdentifier(identifier);
	}

	public void deletePasswordTrackers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.deletePasswordTrackers(userId);
	}

	public boolean isSameAsCurrentPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.isSameAsCurrentPassword(userId,
			newClearTextPwd);
	}

	public boolean isValidPassword(long userId, java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _passwordTrackerLocalService.isValidPassword(userId,
			newClearTextPwd);
	}

	public void trackPassword(long userId, java.lang.String encPassword)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_passwordTrackerLocalService.trackPassword(userId, encPassword);
	}

	public PasswordTrackerLocalService getWrappedPasswordTrackerLocalService() {
		return _passwordTrackerLocalService;
	}

	public void setWrappedPasswordTrackerLocalService(
		PasswordTrackerLocalService passwordTrackerLocalService) {
		_passwordTrackerLocalService = passwordTrackerLocalService;
	}

	private PasswordTrackerLocalService _passwordTrackerLocalService;
}