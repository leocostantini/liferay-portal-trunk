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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;

/**
 * The persistence interface for the d d m structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistenceImpl
 * @see DDMStructureLinkUtil
 * @generated
 */
public interface DDMStructureLinkPersistence extends BasePersistence<DDMStructureLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLinkUtil} to access the d d m structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d m structure link in the entity cache if it is enabled.
	*
	* @param ddmStructureLink the d d m structure link to cache
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink);

	/**
	* Caches the d d m structure links in the entity cache if it is enabled.
	*
	* @param ddmStructureLinks the d d m structure links to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> ddmStructureLinks);

	/**
	* Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	*
	* @param structureLinkId the primary key for the new d d m structure link
	* @return the new d d m structure link
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink create(
		long structureLinkId);

	/**
	* Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLinkId the primary key of the d d m structure link to remove
	* @return the d d m structure link that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink remove(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d d m structure link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link to find
	* @return the d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByPrimaryKey(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link to find
	* @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByPrimaryKey(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @return the matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d d m structure link in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the last d d m structure link in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] findByClassNameId_PrevAndNext(
		long structureLinkId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Filters by the user's permissions and finds all the d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @return the matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> filterFindByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> filterFindByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> filterFindByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] filterFindByClassNameId_PrevAndNext(
		long structureLinkId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the d d m structure link where classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param classPK the class p k to search with
	* @return the matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the d d m structure link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classPK the class p k to search with
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d d m structure link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classPK the class p k to search with
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByClassPK(
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Filters by the user's permissions and finds all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> filterFindByStructureId(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> filterFindByStructureId(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> filterFindByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] filterFindByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Finds all the d d m structure links.
	*
	* @return the d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @return the range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links to return
	* @param end the upper bound of the range of d d m structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m structure links where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m structure link where classPK = &#63; from the database.
	*
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassPK(long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;

	/**
	* Removes all the d d m structure links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m structure links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @return the number of matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure links where classPK = &#63;.
	*
	* @param classPK the class p k to search with
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassPK(long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public int countByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching d d m structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d d m structure links.
	*
	* @return the number of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DDMStructureLink remove(DDMStructureLink ddmStructureLink)
		throws SystemException;
}