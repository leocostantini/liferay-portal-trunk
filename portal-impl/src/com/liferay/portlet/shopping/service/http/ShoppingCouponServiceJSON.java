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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil;

/**
 * <a href="ShoppingCouponServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the
 * <code>com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a
 * <code>com.liferay.portal.kernel.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.shopping.model.ShoppingCoupon</code>,
 * that is translated to a
 * <code>com.liferay.portal.kernel.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portlet.shopping.service.http.ShoppingCouponJSONSerializer</code>.
 * </p>
 *
 * <p>
 * This allows you to call the the backend services directly from JavaScript.
 * See <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a
 * reference of how that portlet uses the generated JavaScript in
 * <code>portal-web/docroot/html/js/service.js</code> to call the backend
 * services directly from JavaScript.
 * </p>
 *
 * <p>
 * The JSON utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.ShoppingCouponServiceUtil
 * @see com.liferay.portlet.shopping.service.http.ShoppingCouponJSONSerializer
 *
 */
public class ShoppingCouponServiceJSON {
	public static JSONObject addCoupon(java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.addCoupon(code,
				autoCode, name, description, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, endDateMonth,
				endDateDay, endDateYear, endDateHour, endDateMinute,
				neverExpire, active, limitCategories, limitSkus, minOrder,
				discount, discountType, serviceContext);

		return ShoppingCouponJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addCoupon(java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, java.lang.String minOrder,
		java.lang.String discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.addCoupon(code,
				autoCode, name, description, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, endDateMonth,
				endDateDay, endDateYear, endDateHour, endDateMinute,
				neverExpire, active, limitCategories, limitSkus, minOrder,
				discount, discountType, serviceContext);

		return ShoppingCouponJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteCoupon(long groupId, long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ShoppingCouponServiceUtil.deleteCoupon(groupId, couponId);
	}

	public static JSONObject getCoupon(long groupId, long couponId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.getCoupon(groupId,
				couponId);

		return ShoppingCouponJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONArray search(long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> returnValue =
			ShoppingCouponServiceUtil.search(groupId, companyId, code, active,
				discountType, andOperator, start, end);

		return ShoppingCouponJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject updateCoupon(long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.updateCoupon(couponId,
				name, description, startDateMonth, startDateDay, startDateYear,
				startDateHour, startDateMinute, endDateMonth, endDateDay,
				endDateYear, endDateHour, endDateMinute, neverExpire, active,
				limitCategories, limitSkus, minOrder, discount, discountType,
				serviceContext);

		return ShoppingCouponJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateCoupon(long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		java.lang.String minOrder, java.lang.String discount,
		java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		com.liferay.portlet.shopping.model.ShoppingCoupon returnValue = ShoppingCouponServiceUtil.updateCoupon(couponId,
				name, description, startDateMonth, startDateDay, startDateYear,
				startDateHour, startDateMinute, endDateMonth, endDateDay,
				endDateYear, endDateHour, endDateMinute, neverExpire, active,
				limitCategories, limitSkus, minOrder, discount, discountType,
				serviceContext);

		return ShoppingCouponJSONSerializer.toJSONObject(returnValue);
	}
}