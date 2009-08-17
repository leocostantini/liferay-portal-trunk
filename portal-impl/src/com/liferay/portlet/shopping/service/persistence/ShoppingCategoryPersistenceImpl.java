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
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
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

import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ShoppingCategoryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategoryPersistence
 * @see       ShoppingCategoryUtil
 * @generated
 */
public class ShoppingCategoryPersistenceImpl extends BasePersistenceImpl
	implements ShoppingCategoryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_P = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ShoppingCategory shoppingCategory) {
		EntityCacheUtil.putResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey(),
			shoppingCategory);
	}

	public void cacheResult(List<ShoppingCategory> shoppingCategories) {
		for (ShoppingCategory shoppingCategory : shoppingCategories) {
			if (EntityCacheUtil.getResult(
						ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCategoryImpl.class,
						shoppingCategory.getPrimaryKey(), this) == null) {
				cacheResult(shoppingCategory);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ShoppingCategoryImpl.class.getName());
		EntityCacheUtil.clearCache(ShoppingCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public ShoppingCategory create(long categoryId) {
		ShoppingCategory shoppingCategory = new ShoppingCategoryImpl();

		shoppingCategory.setNew(true);
		shoppingCategory.setPrimaryKey(categoryId);

		return shoppingCategory;
	}

	public ShoppingCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCategory shoppingCategory = (ShoppingCategory)session.get(ShoppingCategoryImpl.class,
					new Long(categoryId));

			if (shoppingCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No ShoppingCategory exists with the primary key " +
						categoryId);
				}

				throw new NoSuchCategoryException(
					"No ShoppingCategory exists with the primary key " +
					categoryId);
			}

			return remove(shoppingCategory);
		}
		catch (NoSuchCategoryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ShoppingCategory remove(ShoppingCategory shoppingCategory)
		throws SystemException {
		for (ModelListener<ShoppingCategory> listener : listeners) {
			listener.onBeforeRemove(shoppingCategory);
		}

		shoppingCategory = removeImpl(shoppingCategory);

		for (ModelListener<ShoppingCategory> listener : listeners) {
			listener.onAfterRemove(shoppingCategory);
		}

		return shoppingCategory;
	}

	protected ShoppingCategory removeImpl(ShoppingCategory shoppingCategory)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (shoppingCategory.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ShoppingCategoryImpl.class,
						shoppingCategory.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(shoppingCategory);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey());

		return shoppingCategory;
	}

	/**
	 * @deprecated Use {@link #update(ShoppingCategory, boolean merge)}.
	 */
	public ShoppingCategory update(ShoppingCategory shoppingCategory)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ShoppingCategory shoppingCategory) method. Use update(ShoppingCategory shoppingCategory, boolean merge) instead.");
		}

		return update(shoppingCategory, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  shoppingCategory the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when shoppingCategory is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public ShoppingCategory update(ShoppingCategory shoppingCategory,
		boolean merge) throws SystemException {
		boolean isNew = shoppingCategory.isNew();

		for (ModelListener<ShoppingCategory> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(shoppingCategory);
			}
			else {
				listener.onBeforeUpdate(shoppingCategory);
			}
		}

		shoppingCategory = updateImpl(shoppingCategory, merge);

		for (ModelListener<ShoppingCategory> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(shoppingCategory);
			}
			else {
				listener.onAfterUpdate(shoppingCategory);
			}
		}

		return shoppingCategory;
	}

	public ShoppingCategory updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, shoppingCategory, merge);

			shoppingCategory.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCategoryImpl.class, shoppingCategory.getPrimaryKey(),
			shoppingCategory);

		return shoppingCategory;
	}

	public ShoppingCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		ShoppingCategory shoppingCategory = fetchByPrimaryKey(categoryId);

		if (shoppingCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ShoppingCategory exists with the primary key " +
					categoryId);
			}

			throw new NoSuchCategoryException(
				"No ShoppingCategory exists with the primary key " +
				categoryId);
		}

		return shoppingCategory;
	}

	public ShoppingCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		ShoppingCategory shoppingCategory = (ShoppingCategory)EntityCacheUtil.getResult(ShoppingCategoryModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCategoryImpl.class, categoryId, this);

		if (shoppingCategory == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingCategory = (ShoppingCategory)session.get(ShoppingCategoryImpl.class,
						new Long(categoryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (shoppingCategory != null) {
					cacheResult(shoppingCategory);
				}

				closeSession(session);
			}
		}

		return shoppingCategory;
	}

	public List<ShoppingCategory> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<ShoppingCategory> list = (List<ShoppingCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ");

				query.append("shoppingCategory.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("shoppingCategory.parentCategoryId ASC, ");
				query.append("shoppingCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingCategory> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<ShoppingCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingCategory> list = (List<ShoppingCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ");

				query.append("shoppingCategory.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingCategory.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("shoppingCategory.parentCategoryId ASC, ");
					query.append("shoppingCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<ShoppingCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingCategory findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		List<ShoppingCategory> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCategory exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCategory findByGroupId_Last(long groupId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingCategory> list = findByGroupId(groupId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCategory exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ");

			query.append("shoppingCategory.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingCategory.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("shoppingCategory.parentCategoryId ASC, ");
				query.append("shoppingCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCategory);

			ShoppingCategory[] array = new ShoppingCategoryImpl[3];

			array[0] = (ShoppingCategory)objArray[0];
			array[1] = (ShoppingCategory)objArray[1];
			array[2] = (ShoppingCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ShoppingCategory> findByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentCategoryId)
			};

		List<ShoppingCategory> list = (List<ShoppingCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ");

				query.append("shoppingCategory.groupId = ?");

				query.append(" AND ");

				query.append("shoppingCategory.parentCategoryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("shoppingCategory.parentCategoryId ASC, ");
				query.append("shoppingCategory.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end) throws SystemException {
		return findByG_P(groupId, parentCategoryId, start, end, null);
	}

	public List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentCategoryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingCategory> list = (List<ShoppingCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ");

				query.append("shoppingCategory.groupId = ?");

				query.append(" AND ");

				query.append("shoppingCategory.parentCategoryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingCategory.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("shoppingCategory.parentCategoryId ASC, ");
					query.append("shoppingCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				list = (List<ShoppingCategory>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public ShoppingCategory findByG_P_First(long groupId,
		long parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		List<ShoppingCategory> list = findByG_P(groupId, parentCategoryId, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCategory exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCategory findByG_P_Last(long groupId, long parentCategoryId,
		OrderByComparator obc) throws NoSuchCategoryException, SystemException {
		int count = countByG_P(groupId, parentCategoryId);

		List<ShoppingCategory> list = findByG_P(groupId, parentCategoryId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ShoppingCategory exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("parentCategoryId=" + parentCategoryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchCategoryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ShoppingCategory[] findByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, OrderByComparator obc)
		throws NoSuchCategoryException, SystemException {
		ShoppingCategory shoppingCategory = findByPrimaryKey(categoryId);

		int count = countByG_P(groupId, parentCategoryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory WHERE ");

			query.append("shoppingCategory.groupId = ?");

			query.append(" AND ");

			query.append("shoppingCategory.parentCategoryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("shoppingCategory.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("shoppingCategory.parentCategoryId ASC, ");
				query.append("shoppingCategory.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					shoppingCategory);

			ShoppingCategory[] array = new ShoppingCategoryImpl[3];

			array[0] = (ShoppingCategory)objArray[0];
			array[1] = (ShoppingCategory)objArray[1];
			array[2] = (ShoppingCategory)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<ShoppingCategory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ShoppingCategory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ShoppingCategory> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<ShoppingCategory> list = (List<ShoppingCategory>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT shoppingCategory FROM ShoppingCategory shoppingCategory ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("shoppingCategory.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("shoppingCategory.parentCategoryId ASC, ");
					query.append("shoppingCategory.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingCategory>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<ShoppingCategory>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingCategory shoppingCategory : findByGroupId(groupId)) {
			remove(shoppingCategory);
		}
	}

	public void removeByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		for (ShoppingCategory shoppingCategory : findByG_P(groupId,
				parentCategoryId)) {
			remove(shoppingCategory);
		}
	}

	public void removeAll() throws SystemException {
		for (ShoppingCategory shoppingCategory : findAll()) {
			remove(shoppingCategory);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(shoppingCategory) ");
				query.append("FROM ShoppingCategory shoppingCategory WHERE ");

				query.append("shoppingCategory.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P(long groupId, long parentCategoryId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(parentCategoryId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(shoppingCategory) ");
				query.append("FROM ShoppingCategory shoppingCategory WHERE ");

				query.append("shoppingCategory.groupId = ?");

				query.append(" AND ");

				query.append("shoppingCategory.parentCategoryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(shoppingCategory) FROM ShoppingCategory shoppingCategory");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCategory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingCategory>> listenersList = new ArrayList<ModelListener<ShoppingCategory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingCategory>)Class.forName(
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
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(ShoppingCategoryPersistenceImpl.class);
}