/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link BranchLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BranchLocalService
 * @generated
 */
public class BranchLocalServiceWrapper implements BranchLocalService {
	public BranchLocalServiceWrapper(BranchLocalService branchLocalService) {
		_branchLocalService = branchLocalService;
	}

	/**
	* Adds the branch to the database. Also notifies the appropriate model listeners.
	*
	* @param branch the branch to add
	* @return the branch that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch addBranch(
		com.liferay.portal.model.Branch branch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.addBranch(branch);
	}

	/**
	* Creates a new branch with the primary key. Does not add the branch to the database.
	*
	* @param branchId the primary key for the new branch
	* @return the new branch
	*/
	public com.liferay.portal.model.Branch createBranch(long branchId) {
		return _branchLocalService.createBranch(branchId);
	}

	/**
	* Deletes the branch with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param branchId the primary key of the branch to delete
	* @throws PortalException if a branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBranch(long branchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_branchLocalService.deleteBranch(branchId);
	}

	/**
	* Deletes the branch from the database. Also notifies the appropriate model listeners.
	*
	* @param branch the branch to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBranch(com.liferay.portal.model.Branch branch)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_branchLocalService.deleteBranch(branch);
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
		return _branchLocalService.dynamicQuery(dynamicQuery);
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
		return _branchLocalService.dynamicQuery(dynamicQuery, start, end);
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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _branchLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the branch with the primary key.
	*
	* @param branchId the primary key of the branch to get
	* @return the branch
	* @throws PortalException if a branch with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch getBranch(long branchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.getBranch(branchId);
	}

	/**
	* Gets a range of all the branchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of branchs to return
	* @param end the upper bound of the range of branchs to return (not inclusive)
	* @return the range of branchs
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Branch> getBranchs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.getBranchs(start, end);
	}

	/**
	* Gets the number of branchs.
	*
	* @return the number of branchs
	* @throws SystemException if a system exception occurred
	*/
	public int getBranchsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.getBranchsCount();
	}

	/**
	* Updates the branch in the database. Also notifies the appropriate model listeners.
	*
	* @param branch the branch to update
	* @return the branch that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch updateBranch(
		com.liferay.portal.model.Branch branch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.updateBranch(branch);
	}

	/**
	* Updates the branch in the database. Also notifies the appropriate model listeners.
	*
	* @param branch the branch to update
	* @param merge whether to merge the branch with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the branch that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Branch updateBranch(
		com.liferay.portal.model.Branch branch, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.updateBranch(branch, merge);
	}

	public com.liferay.portal.model.Branch addBranch(java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.addBranch(name, description, serviceContext);
	}

	public void deleteBranches(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_branchLocalService.deleteBranches(groupId);
	}

	public com.liferay.portal.model.Branch getMasterBranch(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.getMasterBranch(groupId);
	}

	public java.util.List<com.liferay.portal.model.Branch> getBranches(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.getBranches(groupId);
	}

	public com.liferay.portal.model.Branch updateBranch(long branchId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _branchLocalService.updateBranch(branchId, name, description,
			serviceContext);
	}

	public BranchLocalService getWrappedBranchLocalService() {
		return _branchLocalService;
	}

	private BranchLocalService _branchLocalService;
}