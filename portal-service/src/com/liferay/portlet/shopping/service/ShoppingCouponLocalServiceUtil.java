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
 * <a href="ShoppingCouponLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.shopping.service.ShoppingCouponLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingCouponLocalService
 * @see com.liferay.portlet.shopping.service.ShoppingCouponLocalServiceFactory
 *
 */
public class ShoppingCouponLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCoupon addShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.addShoppingCoupon(shoppingCoupon);
	}

	public static void deleteShoppingCoupon(long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		shoppingCouponLocalService.deleteShoppingCoupon(couponId);
	}

	public static void deleteShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		shoppingCouponLocalService.deleteShoppingCoupon(shoppingCoupon);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.updateShoppingCoupon(shoppingCoupon);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon addCoupon(
		long userId, long plid, java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.addCoupon(userId, plid, code,
			autoCode, name, description, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, endDateMonth,
			endDateDay, endDateYear, endDateHour, endDateMinute, neverExpire,
			active, limitCategories, limitSkus, minOrder, discount, discountType);
	}

	public static void deleteCoupon(long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		shoppingCouponLocalService.deleteCoupon(couponId);
	}

	public static void deleteCoupons(long groupId)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		shoppingCouponLocalService.deleteCoupons(groupId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.getCoupon(couponId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String code)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.getCoupon(code);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> search(
		long plid, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.search(plid, companyId, code, active,
			discountType, andOperator, begin, end);
	}

	public static int searchCount(long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator) throws com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.searchCount(groupId, companyId, code,
			active, discountType, andOperator);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCoupon updateCoupon(
		long userId, long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponLocalService shoppingCouponLocalService = ShoppingCouponLocalServiceFactory.getService();

		return shoppingCouponLocalService.updateCoupon(userId, couponId, name,
			description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType);
	}
}