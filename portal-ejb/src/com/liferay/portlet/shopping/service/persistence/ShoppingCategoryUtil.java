/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="ShoppingCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryUtil {
	public static final String CLASS_NAME = ShoppingCategoryUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCategory"));

	public static com.liferay.portlet.shopping.model.ShoppingCategory create(
		java.lang.String categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory remove(
		java.lang.String categoryId)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(categoryId));
		}

		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory = getPersistence()
																				   .remove(categoryId);

		if (listener != null) {
			listener.onAfterRemove(shoppingCategory);
		}

		return shoppingCategory;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory update(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = shoppingCategory.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCategory);
			}
			else {
				listener.onBeforeUpdate(shoppingCategory);
			}
		}

		shoppingCategory = getPersistence().update(shoppingCategory);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(shoppingCategory);
			}
			else {
				listener.onAfterUpdate(shoppingCategory);
			}
		}

		return shoppingCategory;
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory findByPrimaryKey(
		java.lang.String categoryId)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory[] findByGroupId_PrevAndNext(
		java.lang.String categoryId, java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(categoryId, groupId,
			obc);
	}

	public static java.util.List findByG_P(java.lang.String groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId);
	}

	public static java.util.List findByG_P(java.lang.String groupId,
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, begin, end);
	}

	public static java.util.List findByG_P(java.lang.String groupId,
		java.lang.String parentCategoryId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, begin,
			end, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory findByG_P_First(
		java.lang.String groupId, java.lang.String parentCategoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P_First(groupId, parentCategoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory findByG_P_Last(
		java.lang.String groupId, java.lang.String parentCategoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P_Last(groupId, parentCategoryId, obc);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory[] findByG_P_PrevAndNext(
		java.lang.String categoryId, java.lang.String groupId,
		java.lang.String parentCategoryId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.shopping.NoSuchCategoryException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_P(java.lang.String groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P(groupId, parentCategoryId);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_P(java.lang.String groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P(groupId, parentCategoryId);
	}

	public static ShoppingCategoryPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		ShoppingCategoryUtil util = (ShoppingCategoryUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(ShoppingCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(ShoppingCategoryUtil.class);
	private ShoppingCategoryPersistence _persistence;
}