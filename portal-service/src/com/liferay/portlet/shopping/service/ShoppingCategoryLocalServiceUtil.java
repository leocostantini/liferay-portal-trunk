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

package com.liferay.portlet.shopping.service;


/**
 * <a href="ShoppingCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.shopping.service.ShoppingCategoryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingCategoryLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingCategoryLocalServiceFactory
 *
 */
public class ShoppingCategoryLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCategory addShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.addShoppingCategory(shoppingCategory);
	}

	public static void deleteShoppingCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.deleteShoppingCategory(categoryId);
	}

	public static void deleteShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.deleteShoppingCategory(shoppingCategory);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory getShoppingCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getShoppingCategory(categoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.updateShoppingCategory(shoppingCategory);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		long userId, long plid, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		long userId, long plid, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		long userId, long plid, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.deleteCategories(groupId);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.deleteCategory(category);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		long groupId) throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getCategories(groupId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getCategories(groupId,
			parentCategoryId, start, end);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getCategory(categoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory getParentCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getParentCategory(category);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getParentCategories(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getParentCategories(categoryId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getParentCategories(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.getParentCategories(category);
	}

	public static void getSubcategoryIds(java.util.List<Long> categoryIds,
		long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		shoppingCategoryLocalService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCategoryLocalService shoppingCategoryLocalService = ShoppingCategoryLocalServiceFactory.getService();

		return shoppingCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory);
	}
}