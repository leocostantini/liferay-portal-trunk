/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service;


/**
 * <a href="IGImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link IGImageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImageLocalService
 * @generated
 */
public class IGImageLocalServiceWrapper implements IGImageLocalService {
	public IGImageLocalServiceWrapper(IGImageLocalService igImageLocalService) {
		_igImageLocalService = igImageLocalService;
	}

	public com.liferay.portlet.imagegallery.model.IGImage addIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addIGImage(igImage);
	}

	public com.liferay.portlet.imagegallery.model.IGImage createIGImage(
		long imageId) {
		return _igImageLocalService.createIGImage(imageId);
	}

	public void deleteIGImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteIGImage(imageId);
	}

	public void deleteIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteIGImage(igImage);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getIGImage(
		long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImage(imageId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getIGImages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImages(start, end);
	}

	public int getIGImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getIGImagesCount();
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateIGImage(igImage);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateIGImage(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateIGImage(igImage, merge);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addImage(uuid, userId, groupId, folderId,
			name, description, file, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.lang.String fileName, byte[] bytes, java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addImage(uuid, userId, groupId, folderId,
			name, description, fileName, bytes, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage addImage(
		java.lang.String uuid, long userId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.lang.String fileName, java.io.InputStream is,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.addImage(uuid, userId, groupId, folderId,
			name, description, fileName, is, contentType, serviceContext);
	}

	public void addImageResources(
		com.liferay.portlet.imagegallery.model.IGImage image,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(image, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addImageResources(
		com.liferay.portlet.imagegallery.model.IGImage image,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(image, communityPermissions,
			guestPermissions);
	}

	public void addImageResources(long imageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(imageId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addImageResources(long imageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.addImageResources(imageId, communityPermissions,
			guestPermissions);
	}

	public void deleteImage(
		com.liferay.portlet.imagegallery.model.IGImage image)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteImage(image);
	}

	public void deleteImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteImage(imageId);
	}

	public void deleteImages(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.deleteImages(groupId, folderId);
	}

	public int getFoldersImagesCount(long groupId,
		java.util.List<Long> folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getFoldersImagesCount(groupId, folderIds);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImages(groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getGroupImages(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImages(groupId, userId, start, end);
	}

	public int getGroupImagesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImagesCount(groupId);
	}

	public int getGroupImagesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getGroupImagesCount(groupId, userId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImage(imageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByCustom1ImageId(custom1ImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByCustom2ImageId(custom2ImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByFolderIdAndNameWithExtension(
		long groupId, long folderId, java.lang.String nameWithExtension)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByFolderIdAndNameWithExtension(groupId,
			folderId, nameWithExtension);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByLargeImageId(largeImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageBySmallImageId(smallImageId);
	}

	public com.liferay.portlet.imagegallery.model.IGImage getImageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImageByUuidAndGroupId(uuid, groupId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImages(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImages(groupId, folderId, start, end);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getImages(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImages(groupId, folderId, start, end, obc);
	}

	public int getImagesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getImagesCount(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> getNoAssetImages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.getNoAssetImages();
	}

	public void updateAsset(long userId,
		com.liferay.portlet.imagegallery.model.IGImage image,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.lang.String contentType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.updateAsset(userId, image, assetCategoryIds,
			assetTagNames, contentType);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, byte[] bytes,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateImage(userId, imageId, groupId,
			folderId, name, description, bytes, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description, java.io.File file,
		java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateImage(userId, imageId, groupId,
			folderId, name, description, file, contentType, serviceContext);
	}

	public com.liferay.portlet.imagegallery.model.IGImage updateImage(
		long userId, long imageId, long groupId, long folderId,
		java.lang.String name, java.lang.String description,
		java.io.InputStream is, java.lang.String contentType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igImageLocalService.updateImage(userId, imageId, groupId,
			folderId, name, description, is, contentType, serviceContext);
	}

	public void updateSmallImage(long smallImageId, long largeImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_igImageLocalService.updateSmallImage(smallImageId, largeImageId);
	}

	public IGImageLocalService getWrappedIGImageLocalService() {
		return _igImageLocalService;
	}

	private IGImageLocalService _igImageLocalService;
}