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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBMailingListLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBMailingListLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingListLocalService
 * @generated
 */
public class MBMailingListLocalServiceWrapper
	implements MBMailingListLocalService {
	public MBMailingListLocalServiceWrapper(
		MBMailingListLocalService mbMailingListLocalService) {
		_mbMailingListLocalService = mbMailingListLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBMailingList addMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.addMBMailingList(mbMailingList);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList createMBMailingList(
		long mailingListId) {
		return _mbMailingListLocalService.createMBMailingList(mailingListId);
	}

	public void deleteMBMailingList(long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMailingListLocalService.deleteMBMailingList(mailingListId);
	}

	public void deleteMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMailingListLocalService.deleteMBMailingList(mbMailingList);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList getMBMailingList(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.getMBMailingList(mailingListId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> getMBMailingLists(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.getMBMailingLists(start, end);
	}

	public int getMBMailingListsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.getMBMailingListsCount();
	}

	public com.liferay.portlet.messageboards.model.MBMailingList updateMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.updateMBMailingList(mbMailingList);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList updateMBMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.updateMBMailingList(mbMailingList,
			merge);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList addMailingList(
		java.lang.String uuid, long userId, long groupId, long categoryId,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.addMailingList(uuid, userId, groupId,
			categoryId, emailAddress, inProtocol, inServerName, inServerPort,
			inUseSSL, inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, active);
	}

	public void deleteCategoryMailingList(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMailingListLocalService.deleteCategoryMailingList(groupId, categoryId);
	}

	public void deleteMailingList(long mailingListId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMailingListLocalService.deleteMailingList(mailingListId);
	}

	public void deleteMailingList(
		com.liferay.portlet.messageboards.model.MBMailingList mailingList)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMailingListLocalService.deleteMailingList(mailingList);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList getCategoryMailingList(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.getCategoryMailingList(groupId,
			categoryId);
	}

	public com.liferay.portlet.messageboards.model.MBMailingList updateMailingList(
		long mailingListId, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingListLocalService.updateMailingList(mailingListId,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			active);
	}

	public MBMailingListLocalService getWrappedMBMailingListLocalService() {
		return _mbMailingListLocalService;
	}

	private MBMailingListLocalService _mbMailingListLocalService;
}