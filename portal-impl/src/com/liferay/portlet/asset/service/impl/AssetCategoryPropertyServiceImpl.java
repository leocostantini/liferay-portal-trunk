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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.base.AssetCategoryPropertyServiceBaseImpl;

import java.util.List;

/**
 * <a href="AssetCategoryPropertyServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetCategoryPropertyServiceImpl
	extends AssetCategoryPropertyServiceBaseImpl {

	public AssetCategoryProperty addCategoryProperty(
			long entryId, String key, String value)
		throws PortalException, SystemException {

		return assetCategoryPropertyLocalService.addCategoryProperty(
			getUserId(), entryId, key, value);
	}

	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException, SystemException {

		assetCategoryPropertyLocalService.deleteCategoryProperty(
			categoryPropertyId);
	}

	public List<AssetCategoryProperty> getCategoryProperties(long entryId)
		throws SystemException {

		return assetCategoryPropertyLocalService.getCategoryProperties(entryId);
	}

	public List<AssetCategoryProperty> getCategoryPropertyValues(
			long companyId, String key)
		throws SystemException {

		return assetCategoryPropertyLocalService.getCategoryPropertyValues(
			companyId, key);
	}

	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException, SystemException {

		return assetCategoryPropertyLocalService.updateCategoryProperty(
			categoryPropertyId, key, value);
	}

}