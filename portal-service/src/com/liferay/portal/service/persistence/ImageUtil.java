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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImagePersistence
 * @see       ImagePersistenceImpl
 * @generated
 */
public class ImageUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Image image) {
		getPersistence().clearCache(image);
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
	public static List<Image> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Image> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Image> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Image remove(Image image) throws SystemException {
		return getPersistence().remove(image);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Image update(Image image, boolean merge)
		throws SystemException {
		return getPersistence().update(image, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Image update(Image image, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(image, merge, serviceContext);
	}

	public static void cacheResult(com.liferay.portal.model.Image image) {
		getPersistence().cacheResult(image);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Image> images) {
		getPersistence().cacheResult(images);
	}

	public static com.liferay.portal.model.Image create(long imageId) {
		return getPersistence().create(imageId);
	}

	public static com.liferay.portal.model.Image remove(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(imageId);
	}

	public static com.liferay.portal.model.Image updateImpl(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(image, merge);
	}

	public static com.liferay.portal.model.Image findByPrimaryKey(long imageId)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(imageId);
	}

	public static com.liferay.portal.model.Image fetchByPrimaryKey(long imageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(imageId);
	}

	public static java.util.List<com.liferay.portal.model.Image> findByLtSize(
		int size) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLtSize(size);
	}

	public static java.util.List<com.liferay.portal.model.Image> findByLtSize(
		int size, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLtSize(size, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Image> findByLtSize(
		int size, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLtSize(size, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.Image findByLtSize_First(int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLtSize_First(size, orderByComparator);
	}

	public static com.liferay.portal.model.Image findByLtSize_Last(int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLtSize_Last(size, orderByComparator);
	}

	public static com.liferay.portal.model.Image[] findByLtSize_PrevAndNext(
		long imageId, int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchImageException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLtSize_PrevAndNext(imageId, size, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.Image> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Image> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Image> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByLtSize(int size)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByLtSize(size);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByLtSize(int size)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLtSize(size);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ImagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ImagePersistence)PortalBeanLocatorUtil.locate(ImagePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ImagePersistence persistence) {
		_persistence = persistence;
	}

	private static ImagePersistence _persistence;
}