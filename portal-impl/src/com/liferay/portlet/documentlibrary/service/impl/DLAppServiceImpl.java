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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppServiceImpl extends DLAppServiceBaseImpl {

	public FileEntry addFileEntry(
			long repositoryId, long folderId, String title, String description,
			String changeLog, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (bytes == null) {
			throw new FileSizeException();
		}

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		return addFileEntry(
			repositoryId, folderId, title, description, changeLog, is,
			bytes.length, serviceContext);
	}

	public FileEntry addFileEntry(
			long repositoryId, long folderId, String title, String description,
			String changeLog, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			if (file == null) {
				throw new FileSizeException();
			}

			InputStream is = new UnsyncBufferedInputStream(
				new FileInputStream(file));

			return addFileEntry(
				repositoryId, folderId, title, description, changeLog,
				is, file.length(), serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

	public FileEntry addFileEntry(
			long repositoryId, long folderId, String title, String description,
			String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.addFileEntry(
			folderId, title, description, changeLog, is, size, serviceContext);
	}

	public DLFileShortcut addFileShortcut(
			long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.addFileShortcut(
			repositoryId, folderId, toFileEntryId, serviceContext);
	}

	public Folder addFolder(
			long repositoryId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	public Folder copyFolder(
			long repositoryId, long sourceFolderId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		Folder srcFolder = repository.getFolder(sourceFolderId);

		Folder destFolder = repository.addFolder(
			parentFolderId, name,description, serviceContext);

		copyFolder(repository, srcFolder, destFolder, serviceContext);

		return destFolder;
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.deleteFileEntry(fileEntryId);
	}

	public void deleteFileEntryByTitle(
			long repositoryId, long folderId, String title)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFileEntry(folderId, title);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		dlFileShortcutService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		repository.deleteFolder(folderId);
	}

	public void deleteFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.deleteFolder(parentFolderId, name);
	}

	public List<FileEntry> getFileEntries(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getFileEntries(
			repositoryId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end)
		throws PortalException, SystemException {

		return getFileEntries(repositoryId, folderId, start, end, null);
	}

	public List<FileEntry> getFileEntries(
			long repositoryId, long folderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntries(folderId, start, end, obc);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	public int getFileEntriesCount(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFileEntriesCount(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.getFileEntry(fileEntryId);
	}

	public FileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntry(folderId, title);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				Repository repository = getRepository(folderId, 0, 0);

				return repository.getFileEntry(folderId, title);
			}
			else {
				throw nsfee;
			}
		}
	}

	public FileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		try {
			Repository repository = getRepository(groupId);

			return repository.getFileEntryByUuid(uuid);
		}
		catch (NoSuchFileEntryException nsfee) {
			List<com.liferay.portal.model.Repository> repositories =
				repositoryPersistence.findByGroupId(groupId);

			for (int i = 0; i < repositories.size(); i++) {
				try {
					long repositoryId = repositories.get(i).getRepositoryId();

					Repository repository = getRepository(repositoryId);

					return repository.getFileEntryByUuid(uuid);
				}
				catch (NoSuchFileEntryException nsfee2) {
				}
			}
		}

		StringBundler msg = new StringBundler(6);

		msg.append("No DLFileEntry exists with the key {");
		msg.append("uuid=");
		msg.append(uuid);
		msg.append(", groupId=");
		msg.append(groupId);
		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		return dlFileShortcutService.getFileShortcut(fileShortcutId);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.getFolder(folderId);
	}

	public Folder getFolder(long repositoryId, long parentFolderId, String name)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolder(parentFolderId, name);
	}

	public List<Folder> getFolders(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		return getFolders(
			repositoryId, parentFolderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Folder> getFolders(
			long repositoryId, long parentFolderId, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFolders(parentFolderId, start, end);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long repositoryId, long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long repositoryId, long folderId, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	public int getFoldersCount(long repositoryId, long parentFolderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersCount(parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long repositoryId, List<Long> folderIds, int status)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getFoldersFileEntriesCount(folderIds, status);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, int start, int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			repositoryId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, new FileEntryModifiedDateComparator());
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			repositoryId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end, obc);
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, long rootFolderId, int start,
			int end)
		throws PortalException, SystemException {

		return getGroupFileEntries(
			repositoryId, userId, rootFolderId, start, end,
			new FileEntryModifiedDateComparator());
	}

	public List<FileEntry> getGroupFileEntries(
			long repositoryId, long userId, long rootFolderId, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	public int getGroupFileEntriesCount(long repositoryId, long userId)
		throws PortalException, SystemException {

		return getGroupFileEntriesCount(
			repositoryId, userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	public int getGroupFileEntriesCount(
			long repositoryId, long userId, long rootFolderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getRepositoryFileEntriesCount(userId, rootFolderId);
	}

	public List<Long> getSubfolderIds(long repositoryId, long folderId)
		throws PortalException, SystemException {

		return getSubfolderIds(repositoryId, folderId, true);
	}

	public List<Long> getSubfolderIds(
			long repositoryId, long folderId, boolean recurse)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.getSubfolderIds(folderId, recurse);
	}

	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.lockFileEntry(fileEntryId);
	}

	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.lockFileEntry(
			fileEntryId, owner, expirationTime);
	}

	public Lock lockFolder(long repositoryId, long folderId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(folderId);
	}

	public Lock lockFolder(
			long repositoryId, long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.moveFolder(folderId, parentFolderId, serviceContext);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long fileEntryId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.refreshFileEntryLock(lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		Lock lock = lockLocalService.getLockByUuid(lockUuid);

		long folderId = GetterUtil.getLong(lock.getKey());

		Repository repository = getRepository(0, folderId, 0);

		return repository.refreshFolderLock(lockUuid, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.revertFileEntry(fileEntryId, version, serviceContext);
	}

	public void unlockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.unlockFileEntry(fileEntryId);
	}

	public void unlockFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		repository.unlockFileEntry(fileEntryId, lockUuid);
	}

	public void unlockFolder(long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(folderId, lockUuid);
	}

	public void unlockFolder(
			long repositoryId, long parentFolderId, String name,
			String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		repository.unlockFolder(parentFolderId, name, lockUuid);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if (bytes != null) {
			is = new UnsyncByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, is, size, serviceContext);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file != null) && file.exists()) {
				is = new UnsyncBufferedInputStream(new FileInputStream(file));
				size = file.length();
			}

			return updateFileEntry(
				fileEntryId, sourceFileName, title, description, changeLog,
				majorVersion, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, fileEntryId, 0);

		return repository.updateFileEntry(
			fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, is, size, serviceContext);
	}

	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return dlFileShortcutService.updateFileShortcut(
			fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(folderId, 0, 0);

		return repository.updateFolder(
			folderId, name, description, serviceContext);
	}

	public boolean verifyFileEntryLock(
			long repositoryId, long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyFileEntryLock(
			fileEntryId, lockUuid);
	}

	public boolean verifyInheritableLock(
			long repositoryId, long folderId, String lockUuid)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		return repository.verifyInheritableLock(
			folderId, lockUuid);
	}

	protected void copyFolder(
			Repository repository, Folder srcFolder, Folder destFolder,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<FileEntry> srcFileEntries = repository.getFileEntries(
			srcFolder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		for (FileEntry srcFileEntry : srcFileEntries) {
			try {
				repository.copyFileEntry(
					destFolder.getGroupId(), srcFileEntry.getFileEntryId(),
					destFolder.getFolderId(), serviceContext);
			}
			catch (Exception e) {
				_log.error(e, e);

				continue;
			}
		}

		List<Folder> srcSubfolders = repository.getFolders(
			srcFolder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Folder srcSubfolder : srcSubfolders) {
			Folder destSubfolder = repository.addFolder(
				destFolder.getFolderId(), srcSubfolder.getName(),
				srcSubfolder.getDescription(), serviceContext);

			copyFolder(repository, srcSubfolder, destSubfolder, serviceContext);
		}
	}

	protected Repository getRepository(long repositoryId)
		throws PortalException, SystemException {

		return repositoryService.getRepositoryImpl(repositoryId);
	}

	protected Repository getRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		return repositoryService.getRepositoryImpl(
			folderId, fileEntryId, fileVersionId);
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceImpl.class);

}