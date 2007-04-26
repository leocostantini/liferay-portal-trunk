/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.service.ejb;

import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="DLFileEntryLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService
 * @see com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.ejb.DLFileEntryLocalServiceEJB
 * @see com.liferay.portlet.documentlibrary.service.ejb.DLFileEntryLocalServiceHome
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl
 *
 */
public class DLFileEntryLocalServiceEJBImpl implements DLFileEntryLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings,
			file, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		byte[] byteArray, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings,
			byteArray, addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings,
			file, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		byte[] byteArray, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings,
			byteArray, communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings,
			file, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		byte[] byteArray, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings,
			byteArray, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry addFileEntry(
		long userId, java.lang.String folderId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.InputStream is, long size,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().addFileEntry(userId,
			folderId, name, title, description, tagsEntries, extraSettings, is,
			size, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(java.lang.String folderId,
		java.lang.String name, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().addFileEntryResources(folderId,
			name, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().addFileEntryResources(folder,
			fileEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(java.lang.String folderId,
		java.lang.String name, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().addFileEntryResources(folderId,
			name, communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().addFileEntryResources(folder,
			fileEntry, communityPermissions, guestPermissions);
	}

	public void deleteFileEntries(java.lang.String folderId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().deleteFileEntries(folderId);
	}

	public void deleteFileEntry(java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().deleteFileEntry(folderId,
			name);
	}

	public void deleteFileEntry(java.lang.String folderId,
		java.lang.String name, double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().deleteFileEntry(folderId,
			name, version);
	}

	public void deleteFileEntry(
		com.liferay.portlet.documentlibrary.model.DLFileEntry fileEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		DLFileEntryLocalServiceFactory.getTxImpl().deleteFileEntry(fileEntry);
	}

	public java.io.InputStream getFileAsStream(java.lang.String companyId,
		long userId, java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getFileAsStream(companyId,
			userId, folderId, name);
	}

	public java.io.InputStream getFileAsStream(java.lang.String companyId,
		long userId, java.lang.String folderId, java.lang.String name,
		double version)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getFileAsStream(companyId,
			userId, folderId, name, version);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry getFileEntry(
		java.lang.String folderId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getFileEntry(folderId,
			name);
	}

	public java.util.List getFileEntries(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getFileEntries(folderId);
	}

	public java.util.List getFileEntries(java.lang.String folderId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getFileEntries(folderId,
			begin, end);
	}

	public java.util.List getFileEntriesAndShortcuts(
		java.lang.String folderId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getFileEntriesAndShortcuts(folderId,
			begin, end);
	}

	public java.util.List getFileEntriesAndShortcuts(java.util.List folderIds,
		int begin, int end) throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getFileEntriesAndShortcuts(folderIds,
			begin, end);
	}

	public int getFileEntriesAndShortcutsCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getFileEntriesAndShortcutsCount(folderId);
	}

	public int getFileEntriesAndShortcutsCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getFileEntriesAndShortcutsCount(folderIds);
	}

	public int getFileEntriesCount(java.lang.String folderId)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getFileEntriesCount(folderId);
	}

	public int getFoldersFileEntriesCount(java.util.List folderIds)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getFoldersFileEntriesCount(folderIds);
	}

	public java.util.List getGroupFileEntries(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getGroupFileEntries(groupId,
			begin, end);
	}

	public java.util.List getGroupFileEntries(long groupId, long userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().getGroupFileEntries(groupId,
			userId, begin, end);
	}

	public int getGroupFileEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getGroupFileEntriesCount(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl()
											 .getGroupFileEntriesCount(groupId,
			userId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, java.lang.String folderId, java.lang.String newFolderId,
		java.lang.String name, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.File file)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().updateFileEntry(userId,
			folderId, newFolderId, name, sourceFileName, title, description,
			tagsEntries, extraSettings, file);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, java.lang.String folderId, java.lang.String newFolderId,
		java.lang.String name, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		byte[] byteArray)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().updateFileEntry(userId,
			folderId, newFolderId, name, sourceFileName, title, description,
			tagsEntries, extraSettings, byteArray);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry updateFileEntry(
		long userId, java.lang.String folderId, java.lang.String newFolderId,
		java.lang.String name, java.lang.String sourceFileName,
		java.lang.String title, java.lang.String description,
		java.lang.String[] tagsEntries, java.lang.String extraSettings,
		java.io.InputStream is, long size)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return DLFileEntryLocalServiceFactory.getTxImpl().updateFileEntry(userId,
			folderId, newFolderId, name, sourceFileName, title, description,
			tagsEntries, extraSettings, is, size);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}