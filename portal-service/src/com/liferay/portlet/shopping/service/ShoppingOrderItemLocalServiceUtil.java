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
 * <a href="ShoppingOrderItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.shopping.service.ShoppingOrderItemLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingOrderItemLocalServiceFactory
 *
 */
public class ShoppingOrderItemLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingOrderItem addShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		return shoppingOrderItemLocalService.addShoppingOrderItem(shoppingOrderItem);
	}

	public static void deleteShoppingOrderItem(long orderItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		shoppingOrderItemLocalService.deleteShoppingOrderItem(orderItemId);
	}

	public static void deleteShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		shoppingOrderItemLocalService.deleteShoppingOrderItem(shoppingOrderItem);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		return shoppingOrderItemLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		return shoppingOrderItemLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem getShoppingOrderItem(
		long orderItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		return shoppingOrderItemLocalService.getShoppingOrderItem(orderItemId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem updateShoppingOrderItem(
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem)
		throws com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		return shoppingOrderItemLocalService.updateShoppingOrderItem(shoppingOrderItem);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingOrderItem> getOrderItems(
		long orderId) throws com.liferay.portal.SystemException {
		ShoppingOrderItemLocalService shoppingOrderItemLocalService = ShoppingOrderItemLocalServiceFactory.getService();

		return shoppingOrderItemLocalService.getOrderItems(orderId);
	}
}