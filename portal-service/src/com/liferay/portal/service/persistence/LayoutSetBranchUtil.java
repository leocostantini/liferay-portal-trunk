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
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the layout set branch service. This utility wraps {@link LayoutSetBranchPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranchPersistence
 * @see LayoutSetBranchPersistenceImpl
 * @generated
 */
public class LayoutSetBranchUtil {
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
	public static void clearCache(LayoutSetBranch layoutSetBranch) {
		getPersistence().clearCache(layoutSetBranch);
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
	public static List<LayoutSetBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutSetBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutSetBranch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static LayoutSetBranch remove(LayoutSetBranch layoutSetBranch)
		throws SystemException {
		return getPersistence().remove(layoutSetBranch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LayoutSetBranch update(LayoutSetBranch layoutSetBranch,
		boolean merge) throws SystemException {
		return getPersistence().update(layoutSetBranch, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LayoutSetBranch update(LayoutSetBranch layoutSetBranch,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(layoutSetBranch, merge, serviceContext);
	}

	/**
	* Caches the layout set branch in the entity cache if it is enabled.
	*
	* @param layoutSetBranch the layout set branch to cache
	*/
	public static void cacheResult(
		com.liferay.portal.model.LayoutSetBranch layoutSetBranch) {
		getPersistence().cacheResult(layoutSetBranch);
	}

	/**
	* Caches the layout set branchs in the entity cache if it is enabled.
	*
	* @param layoutSetBranchs the layout set branchs to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSetBranch> layoutSetBranchs) {
		getPersistence().cacheResult(layoutSetBranchs);
	}

	/**
	* Creates a new layout set branch with the primary key. Does not add the layout set branch to the database.
	*
	* @param layoutSetBranchId the primary key for the new layout set branch
	* @return the new layout set branch
	*/
	public static com.liferay.portal.model.LayoutSetBranch create(
		long layoutSetBranchId) {
		return getPersistence().create(layoutSetBranchId);
	}

	/**
	* Removes the layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetBranchId the primary key of the layout set branch to remove
	* @return the layout set branch that was removed
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch remove(
		long layoutSetBranchId)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(layoutSetBranchId);
	}

	public static com.liferay.portal.model.LayoutSetBranch updateImpl(
		com.liferay.portal.model.LayoutSetBranch layoutSetBranch, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layoutSetBranch, merge);
	}

	/**
	* Finds the layout set branch with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutSetBranchException} if it could not be found.
	*
	* @param layoutSetBranchId the primary key of the layout set branch to find
	* @return the layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch findByPrimaryKey(
		long layoutSetBranchId)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(layoutSetBranchId);
	}

	/**
	* Finds the layout set branch with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutSetBranchId the primary key of the layout set branch to find
	* @return the layout set branch, or <code>null</code> if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch fetchByPrimaryKey(
		long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutSetBranchId);
	}

	/**
	* Finds all the layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @return the range of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first layout set branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last layout set branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch[] findByGroupId_PrevAndNext(
		long layoutSetBranchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutSetBranchId, groupId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @return the range of matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the layout set branchs where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Filters the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch[] filterFindByGroupId_PrevAndNext(
		long layoutSetBranchId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(layoutSetBranchId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	* Finds a range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @return the range of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Finds an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Finds the last layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch findByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Finds the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch[] findByG_P_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_PrevAndNext(layoutSetBranchId, groupId,
			privateLayout, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, privateLayout);
	}

	/**
	* Filters by the user's permissions and finds a range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @return the range of matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Filters the layout set branchs before and after the current layout set branch in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the primary key of the current layout set branch
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a layout set branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch[] filterFindByG_P_PrevAndNext(
		long layoutSetBranchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(layoutSetBranchId, groupId,
			privateLayout, orderByComparator);
	}

	/**
	* Finds the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutSetBranchException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param name the name to search with
	* @return the matching layout set branch
	* @throws com.liferay.portal.NoSuchLayoutSetBranchException if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch findByG_P_N(
		long groupId, boolean privateLayout, java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_N(groupId, privateLayout, name);
	}

	/**
	* Finds the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param name the name to search with
	* @return the matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch fetchByG_P_N(
		long groupId, boolean privateLayout, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_N(groupId, privateLayout, name);
	}

	/**
	* Finds the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param name the name to search with
	* @return the matching layout set branch, or <code>null</code> if a matching layout set branch could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutSetBranch fetchByG_P_N(
		long groupId, boolean privateLayout, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_N(groupId, privateLayout, name, retrieveFromCache);
	}

	/**
	* Finds all the layout set branchs.
	*
	* @return the layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @return the range of layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the layout set branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout set branchs to return
	* @param end the upper bound of the range of layout set branchs to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutSetBranch> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the layout set branchs where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the layout set branchs where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	* Removes the layout set branch where groupId = &#63; and privateLayout = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_N(long groupId, boolean privateLayout,
		java.lang.String name)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_N(groupId, privateLayout, name);
	}

	/**
	* Removes all the layout set branchs from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the layout set branchs where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the number of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	* Filters by the user's permissions and counts all the layout set branchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the number of matching layout set branchs that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, privateLayout);
	}

	/**
	* Counts all the layout set branchs where groupId = &#63; and privateLayout = &#63; and name = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param name the name to search with
	* @return the number of matching layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_N(long groupId, boolean privateLayout,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_N(groupId, privateLayout, name);
	}

	/**
	* Counts all the layout set branchs.
	*
	* @return the number of layout set branchs
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutSetBranchPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutSetBranchPersistence)PortalBeanLocatorUtil.locate(LayoutSetBranchPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutSetBranchUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(LayoutSetBranchPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(LayoutSetBranchUtil.class,
			"_persistence");
	}

	private static LayoutSetBranchPersistence _persistence;
}