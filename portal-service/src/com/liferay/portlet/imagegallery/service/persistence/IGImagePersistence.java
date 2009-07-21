/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

public interface IGImagePersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.imagegallery.model.IGImage igImage);

	public void cacheResult(
		java.util.List<com.liferay.portlet.imagegallery.model.IGImage> igImages);

	public void clearCache();

	public com.liferay.portlet.imagegallery.model.IGImage create(long imageId);

	public com.liferay.portlet.imagegallery.model.IGImage remove(long imageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage remove(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage update(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage updateImpl(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByPrimaryKey(
		long imageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByPrimaryKey(
		long imageId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage[] findByUuid_PrevAndNext(
		long imageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage[] findByGroupId_PrevAndNext(
		long imageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByFolderId(
		long folderId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByFolderId(
		long folderId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByFolderId(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByFolderId_First(
		long folderId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findByFolderId_Last(
		long folderId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage[] findByFolderId_PrevAndNext(
		long imageId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByLargeImageId(
		long largeImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByLargeImageId(
		long largeImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByCustom1ImageId(
		long custom1ImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByCustom1ImageId(
		long custom1ImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByCustom2ImageId(
		long custom2ImageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage fetchByCustom2ImageId(
		long custom2ImageId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage[] findByG_U_PrevAndNext(
		long imageId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByF_N(
		long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByF_N(
		long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByF_N(
		long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.imagegallery.model.IGImage findByF_N_First(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage findByF_N_Last(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public com.liferay.portlet.imagegallery.model.IGImage[] findByF_N_PrevAndNext(
		long imageId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByFolderId(long folderId)
		throws com.liferay.portal.SystemException;

	public void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public void removeByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public void removeByCustom1ImageId(long custom1ImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public void removeByCustom2ImageId(long custom2ImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public void removeByF_N(long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByFolderId(long folderId)
		throws com.liferay.portal.SystemException;

	public int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException;

	public int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.SystemException;

	public int countByCustom1ImageId(long custom1ImageId)
		throws com.liferay.portal.SystemException;

	public int countByCustom2ImageId(long custom2ImageId)
		throws com.liferay.portal.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public int countByF_N(long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}