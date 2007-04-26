/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingCartUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCartUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCart create(
		java.lang.String cartId) {
		return getPersistence().create(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart remove(
		java.lang.String cartId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(cartId));
		}

		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart = getPersistence()
																		   .remove(cartId);

		if (listener != null) {
			listener.onAfterRemove(shoppingCart);
		}

		return shoppingCart;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart remove(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(shoppingCart);
		}

		shoppingCart = getPersistence().remove(shoppingCart);

		if (listener != null) {
			listener.onAfterRemove(shoppingCart);
		}

		return shoppingCart;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart update(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = shoppingCart.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCart);
			}
			else {
				listener.onBeforeUpdate(shoppingCart);
			}
		}

		shoppingCart = getPersistence().update(shoppingCart);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingCart);
			}
			else {
				listener.onAfterUpdate(shoppingCart);
			}
		}

		return shoppingCart;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart update(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = shoppingCart.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCart);
			}
			else {
				listener.onBeforeUpdate(shoppingCart);
			}
		}

		shoppingCart = getPersistence().update(shoppingCart, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingCart);
			}
			else {
				listener.onAfterUpdate(shoppingCart);
			}
		}

		return shoppingCart;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByPrimaryKey(
		java.lang.String cartId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByPrimaryKey(cartId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart fetchByPrimaryKey(
		java.lang.String cartId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(cartId);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByGroupId_PrevAndNext(
		java.lang.String cartId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByGroupId_PrevAndNext(cartId, groupId, obc);
	}

	public static java.util.List findByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart[] findByUserId_PrevAndNext(
		java.lang.String cartId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.shopping.NoSuchCartException {
		return getPersistence().findByUserId_PrevAndNext(cartId, userId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static ShoppingCartPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ShoppingCartPersistence persistence) {
		_persistence = persistence;
	}

	private static ShoppingCartUtil _getUtil() {
		if (_util == null) {
			_util = (ShoppingCartUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = ShoppingCartUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCart"));
	private static Log _log = LogFactory.getLog(ShoppingCartUtil.class);
	private static ShoppingCartUtil _util;
	private ShoppingCartPersistence _persistence;
}