/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.spring;

/**
 * <a href="BlogsCategoryLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface BlogsCategoryLocalService {
	public com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		java.lang.String userId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		java.lang.String userId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		java.lang.String userId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void addCategoryResources(java.lang.String categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void addCategoryResources(java.lang.String categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void deleteCategory(
		com.liferay.portlet.blogs.model.BlogsCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getCategories(java.lang.String parentCategoryId,
		int begin, int end) throws com.liferay.portal.SystemException;

	public int getCategoriesCount(java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void getSubcategoryIds(java.util.List categoryIds,
		java.lang.String categoryId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsCategory updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;
}