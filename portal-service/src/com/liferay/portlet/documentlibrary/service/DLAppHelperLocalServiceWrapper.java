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

package com.liferay.portlet.documentlibrary.service;

/**
 * <p>
 * This class is a wrapper for {@link DLAppHelperLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLAppHelperLocalService
 * @generated
 */
public class DLAppHelperLocalServiceWrapper implements DLAppHelperLocalService {
	public DLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {
		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	public void addFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		com.liferay.portlet.documentlibrary.model.DLFileVersion fileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.addFileEntry(fileEntry, fileVersion,
			serviceContext);
	}

	public void addFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.addFolder(folder, serviceContext);
	}

	public void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.deleteFileEntry(fileEntry);
	}

	public void deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.deleteFolder(folder);
	}

	public void getFileAsStream(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.getFileAsStream(userId, fileEntry);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcuts(groupId, folderIds,
			status);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcuts(groupId, folderId,
			status);
	}

	public int getFileShortcutsCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcutsCount(groupId,
			folderIds, status);
	}

	public int getFileShortcutsCount(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getFileShortcutsCount(groupId,
			folderId, status);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntry> getNoAssetFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlAppHelperLocalService.getNoAssetFileEntries();
	}

	public void moveFileEntry(long userId, long groupId, long folderId,
		long newFolderId, long oldFileEntryId, long newFileEntryId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.moveFileEntry(userId, groupId, folderId,
			newFolderId, oldFileEntryId, newFileEntryId, name);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		com.liferay.portlet.documentlibrary.model.DLFileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.lang.String mimeType, boolean addDraftAssetEntry, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateAsset(userId, fileEntry, fileVersion,
			assetCategoryIds, assetTagNames, mimeType, addDraftAssetEntry,
			visible);
	}

	public void updateStatus(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		com.liferay.portlet.documentlibrary.model.DLFileVersion latestFileVersion,
		int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlAppHelperLocalService.updateStatus(userId, fileEntry,
			latestFileVersion, status);
	}

	public DLAppHelperLocalService getWrappedDLAppHelperLocalService() {
		return _dlAppHelperLocalService;
	}

	public void setWrappedDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {
		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	private DLAppHelperLocalService _dlAppHelperLocalService;
}