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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Release;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the release service. This utility wraps {@link ReleasePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReleasePersistence
 * @see ReleasePersistenceImpl
 * @generated
 */
public class ReleaseUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Release release) {
		getPersistence().clearCache(release);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Release> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Release> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Release remove(Release release) throws SystemException {
		return getPersistence().remove(release);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Release update(Release release, boolean merge)
		throws SystemException {
		return getPersistence().update(release, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Release update(Release release, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(release, merge, serviceContext);
	}

	/**
	* Caches the release in the entity cache if it is enabled.
	*
	* @param release the release to cache
	*/
	public static void cacheResult(com.liferay.portal.model.Release release) {
		getPersistence().cacheResult(release);
	}

	/**
	* Caches the releases in the entity cache if it is enabled.
	*
	* @param releases the releases to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Release> releases) {
		getPersistence().cacheResult(releases);
	}

	/**
	* Creates a new release with the primary key. Does not add the release to the database.
	*
	* @param releaseId the primary key for the new release
	* @return the new release
	*/
	public static com.liferay.portal.model.Release create(long releaseId) {
		return getPersistence().create(releaseId);
	}

	/**
	* Removes the release with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param releaseId the primary key of the release to remove
	* @return the release that was removed
	* @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Release remove(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(releaseId);
	}

	public static com.liferay.portal.model.Release updateImpl(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(release, merge);
	}

	/**
	* Finds the release with the primary key or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	*
	* @param releaseId the primary key of the release to find
	* @return the release
	* @throws com.liferay.portal.NoSuchReleaseException if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Release findByPrimaryKey(
		long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(releaseId);
	}

	/**
	* Finds the release with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param releaseId the primary key of the release to find
	* @return the release, or <code>null</code> if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Release fetchByPrimaryKey(
		long releaseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(releaseId);
	}

	/**
	* Finds the release where servletContextName = &#63; or throws a {@link com.liferay.portal.NoSuchReleaseException} if it could not be found.
	*
	* @param servletContextName the servlet context name to search with
	* @return the matching release
	* @throws com.liferay.portal.NoSuchReleaseException if a matching release could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Release findByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByServletContextName(servletContextName);
	}

	/**
	* Finds the release where servletContextName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param servletContextName the servlet context name to search with
	* @return the matching release, or <code>null</code> if a matching release could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByServletContextName(servletContextName);
	}

	/**
	* Finds the release where servletContextName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param servletContextName the servlet context name to search with
	* @return the matching release, or <code>null</code> if a matching release could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByServletContextName(servletContextName,
			retrieveFromCache);
	}

	/**
	* Finds all the releases.
	*
	* @return the releases
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Release> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of releases to return
	* @param end the upper bound of the range of releases to return (not inclusive)
	* @return the range of releases
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Release> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of releases to return
	* @param end the upper bound of the range of releases to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of releases
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Release> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the release where servletContextName = &#63; from the database.
	*
	* @param servletContextName the servlet context name to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByServletContextName(servletContextName);
	}

	/**
	* Removes all the releases from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the releases where servletContextName = &#63;.
	*
	* @param servletContextName the servlet context name to search with
	* @return the number of matching releases
	* @throws SystemException if a system exception occurred
	*/
	public static int countByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByServletContextName(servletContextName);
	}

	/**
	* Counts all the releases.
	*
	* @return the number of releases
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ReleasePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName());

			ReferenceRegistry.registerReference(ReleaseUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(ReleasePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ReleaseUtil.class, "_persistence");
	}

	private static ReleasePersistence _persistence;
}