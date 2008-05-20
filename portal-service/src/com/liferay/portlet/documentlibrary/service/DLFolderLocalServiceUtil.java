/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFolderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.documentlibrary.service.DLFolderLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.DLFolderLocalService
 * @see com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceFactory
 *
 */
public class DLFolderLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFolder addDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.addDLFolder(dlFolder);
	}

	public static void deleteDLFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.deleteDLFolder(folderId);
	}

	public static void deleteDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.deleteDLFolder(dlFolder);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateDLFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.updateDLFolder(dlFolder);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long plid, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.addFolder(userId, plid, parentFolderId,
			name, description, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		java.lang.String uuid, long userId, long plid, long parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.addFolder(uuid, userId, plid,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long userId, long plid, long parentFolderId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.addFolder(userId, plid, parentFolderId,
			name, description, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		java.lang.String uuid, long userId, long plid, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.addFolder(uuid, userId, plid,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolderToGroup(
		java.lang.String uuid, long userId, long groupId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.addFolderToGroup(uuid, userId, groupId,
			parentFolderId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.addFolderResources(folderId, communityPermissions,
			guestPermissions);
	}

	public static void addFolderResources(
		com.liferay.portlet.documentlibrary.model.DLFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.addFolderResources(folder, communityPermissions,
			guestPermissions);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.deleteFolder(folderId);
	}

	public static void deleteFolder(
		com.liferay.portlet.documentlibrary.model.DLFolder folder)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.deleteFolder(folder);
	}

	public static void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.deleteFolders(groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.getFolder(folderId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.getFolder(groupId, parentFolderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long companyId) throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.getFolders(companyId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.getFolders(groupId, parentFolderId, start,
			end);
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.getFoldersCount(groupId, parentFolderId);
	}

	public static void getSubfolderIds(java.util.List<Long> folderIds,
		long groupId, long folderId) throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		dlFolderLocalService.reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long[] folderIds, java.lang.String keywords, int start,
		int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.search(companyId, groupId, folderIds,
			keywords, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFolderLocalService dlFolderLocalService = DLFolderLocalServiceFactory.getService();

		return dlFolderLocalService.updateFolder(folderId, parentFolderId,
			name, description);
	}
}