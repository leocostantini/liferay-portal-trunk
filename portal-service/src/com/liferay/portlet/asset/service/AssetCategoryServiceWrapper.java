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

package com.liferay.portlet.asset.service;


/**
 * <a href="AssetCategoryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetCategoryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryService
 * @generated
 */
public class AssetCategoryServiceWrapper implements AssetCategoryService {
	public AssetCategoryServiceWrapper(
		AssetCategoryService assetCategoryService) {
		_assetCategoryService = assetCategoryService;
	}

	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		long parentCategoryId, java.lang.String name, long vocabularyId,
		java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.addCategory(parentCategoryId, name,
			vocabularyId, categoryProperties, serviceContext);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryService.deleteCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.getCategories(className, classPK);
	}

	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.getCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.getChildCategories(parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.getVocabularyCategories(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.getVocabularyRootCategories(vocabularyId);
	}

	public com.liferay.portal.kernel.json.JSONArray search(long groupId,
		java.lang.String name, java.lang.String[] categoryProperties,
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetCategoryService.search(groupId, name, categoryProperties,
			start, end);
	}

	public com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryService.updateCategory(categoryId,
			parentCategoryId, name, vocabularyId, categoryProperties,
			serviceContext);
	}

	private AssetCategoryService _assetCategoryService;
}