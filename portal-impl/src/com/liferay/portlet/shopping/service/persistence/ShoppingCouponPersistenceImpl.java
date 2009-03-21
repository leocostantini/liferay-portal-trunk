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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingCouponPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCouponPersistenceImpl extends BasePersistenceImpl
	implements ShoppingCouponPersistence {
	public ShoppingCoupon create(long couponId) {
		ShoppingCoupon shoppingCoupon = new ShoppingCouponImpl();

		shoppingCoupon.setNew(true);
		shoppingCoupon.setPrimaryKey(couponId);

		return shoppingCoupon;
	}

	public ShoppingCoupon remove(long couponId)
		throws NoSuchCouponException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCoupon shoppingCoupon = (ShoppingCoupon)session.get(ShoppingCouponImpl.class,
					new Long(couponId));

			if (shoppingCoupon == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ShoppingCoupon exists with the primary key " +
						couponId);
				}

				throw new NoSuchCouponException(
					"No ShoppingCoupon exists with the primary key " +
					couponId);
			}

			return remove(shoppingCoupon);
		}
		catch (NoSuchCouponException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon remove(ShoppingCoupon shoppingCoupon)
		throws SystemException {
		for (ModelListener<ShoppingCoupon> listener : listeners) {
			listener.onBeforeRemove(shoppingCoupon);
		}

		shoppingCoupon = removeImpl(shoppingCoupon);

		for (ModelListener<ShoppingCoupon> listener : listeners) {
			listener.onAfterRemove(shoppingCoupon);
		}

		return shoppingCoupon;
	}

	protected ShoppingCoupon removeImpl(ShoppingCoupon shoppingCoupon)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingCouponImpl.class,
						shoppingCoupon.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingCoupon);

			session.flush();

			return shoppingCoupon;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingCoupon.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ShoppingCoupon shoppingCoupon, boolean merge)</code>.
	 */
	public ShoppingCoupon update(ShoppingCoupon shoppingCoupon)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingCoupon shoppingCoupon) method. Use update(ShoppingCoupon shoppingCoupon, boolean merge) instead.");
		}

		return update(shoppingCoupon, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        shoppingCoupon the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when shoppingCoupon is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ShoppingCoupon update(ShoppingCoupon shoppingCoupon, boolean merge)
		throws SystemException {
		boolean isNew = shoppingCoupon.isNew();

		for (ModelListener<ShoppingCoupon> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCoupon);
			}
			else {
				listener.onBeforeUpdate(shoppingCoupon);
			}
		}

		shoppingCoupon = updateImpl(shoppingCoupon, merge);

		for (ModelListener<ShoppingCoupon> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(shoppingCoupon);
			}
			else {
				listener.onAfterUpdate(shoppingCoupon);
			}
		}

		return shoppingCoupon;
	}

	public ShoppingCoupon updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingCoupon, merge);

			shoppingCoupon.setNew(false);

			return shoppingCoupon;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ShoppingCoupon.class.getName());
		}
	}

	public ShoppingCoupon findByPrimaryKey(long couponId)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = fetchByPrimaryKey(couponId);

		if (shoppingCoupon == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingCoupon exists with the primary key " +
					couponId);
			}

			throw new NoSuchCouponException(
				"No ShoppingCoupon exists with the primary key " + couponId);
		}

		return shoppingCoupon;
	}

	public ShoppingCoupon fetchByPrimaryKey(long couponId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ShoppingCoupon)session.get(ShoppingCouponImpl.class,
				new Long(couponId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingCoupon> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<ShoppingCoupon> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ShoppingCoupon>)result;
		}
	}

	public List<ShoppingCoupon> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<ShoppingCoupon> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<ShoppingCoupon> list = (List<ShoppingCoupon>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ShoppingCoupon>)result;
		}
	}

	public ShoppingCoupon findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchCouponException, SystemException {
		List<ShoppingCoupon> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCoupon exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCouponException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCoupon findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingCoupon> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCoupon exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCouponException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCoupon[] findByGroupId_PrevAndNext(long couponId,
		long groupId, OrderByComparator obc)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = findByPrimaryKey(couponId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCoupon);

			ShoppingCoupon[] array = new ShoppingCouponImpl[3];

			array[0] = (ShoppingCoupon)objArray[0];
			array[1] = (ShoppingCoupon)objArray[1];
			array[2] = (ShoppingCoupon)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCoupon findByCode(String code)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = fetchByCode(code);

		if (shoppingCoupon == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCoupon exists with the key {");

			msg.append("code=" + code);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCouponException(msg.toString());
		}

		return shoppingCoupon;
	}

	public ShoppingCoupon fetchByCode(String code) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "fetchByCode";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { code };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

				if (code == null) {
					query.append("code_ IS NULL");
				}
				else {
					query.append("code_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (code != null) {
					qPos.add(code);
				}

				List<ShoppingCoupon> list = q.list();

				if (list.isEmpty()) {
					return null;
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list.get(0);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ShoppingCoupon> list = (List<ShoppingCoupon>)result;

			if (list.isEmpty()) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingCoupon> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingCoupon> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingCoupon> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<ShoppingCoupon> list = null;

				if (obc == null) {
					list = (List<ShoppingCoupon>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingCoupon>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ShoppingCoupon>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingCoupon shoppingCoupon : findByGroupId(groupId)) {
			remove(shoppingCoupon);
		}
	}

	public void removeByCode(String code)
		throws NoSuchCouponException, SystemException {
		ShoppingCoupon shoppingCoupon = findByCode(code);

		remove(shoppingCoupon);
	}

	public void removeAll() throws SystemException {
		for (ShoppingCoupon shoppingCoupon : findAll()) {
			remove(shoppingCoupon);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByCode(String code) throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "countByCode";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { code };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.shopping.model.ShoppingCoupon WHERE ");

				if (code == null) {
					query.append("code_ IS NULL");
				}
				else {
					query.append("code_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (code != null) {
					qPos.add(code);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ShoppingCouponModelImpl.CACHE_ENABLED;
		String finderClassName = ShoppingCoupon.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.shopping.model.ShoppingCoupon");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCoupon")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingCoupon>> listenersList = new ArrayList<ModelListener<ShoppingCoupon>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingCoupon>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence.impl")
	protected com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(ShoppingCouponPersistenceImpl.class);
}