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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchWebsiteException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
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
import com.liferay.portal.model.Website;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.portal.model.impl.WebsiteModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="WebsitePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WebsitePersistenceImpl extends BasePersistenceImpl
	implements WebsitePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WebsiteImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_C = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_C = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_C_C_P = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_C_C_P = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C_P = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_C_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Website website) {
		EntityCacheUtil.putResult(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteImpl.class, website.getPrimaryKey(), website);
	}

	public void cacheResult(List<Website> websites) {
		for (Website website : websites) {
			if (EntityCacheUtil.getResult(
						WebsiteModelImpl.ENTITY_CACHE_ENABLED,
						WebsiteImpl.class, website.getPrimaryKey(), this) == null) {
				cacheResult(website);
			}
		}
	}

	public Website create(long websiteId) {
		Website website = new WebsiteImpl();

		website.setNew(true);
		website.setPrimaryKey(websiteId);

		return website;
	}

	public Website remove(long websiteId)
		throws NoSuchWebsiteException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Website website = (Website)session.get(WebsiteImpl.class,
					new Long(websiteId));

			if (website == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Website exists with the primary key " +
						websiteId);
				}

				throw new NoSuchWebsiteException(
					"No Website exists with the primary key " + websiteId);
			}

			return remove(website);
		}
		catch (NoSuchWebsiteException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Website remove(Website website) throws SystemException {
		for (ModelListener<Website> listener : listeners) {
			listener.onBeforeRemove(website);
		}

		website = removeImpl(website);

		for (ModelListener<Website> listener : listeners) {
			listener.onAfterRemove(website);
		}

		return website;
	}

	protected Website removeImpl(Website website) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (website.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WebsiteImpl.class,
						website.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(website);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteImpl.class, website.getPrimaryKey());

		return website;
	}

	/**
	 * @deprecated Use <code>update(Website website, boolean merge)</code>.
	 */
	public Website update(Website website) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(Website website) method. Use update(Website website, boolean merge) instead.");
		}

		return update(website, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        website the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when website is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public Website update(Website website, boolean merge)
		throws SystemException {
		boolean isNew = website.isNew();

		for (ModelListener<Website> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(website);
			}
			else {
				listener.onBeforeUpdate(website);
			}
		}

		website = updateImpl(website, merge);

		for (ModelListener<Website> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(website);
			}
			else {
				listener.onAfterUpdate(website);
			}
		}

		return website;
	}

	public Website updateImpl(com.liferay.portal.model.Website website,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, website, merge);

			website.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
			WebsiteImpl.class, website.getPrimaryKey(), website);

		return website;
	}

	public Website findByPrimaryKey(long websiteId)
		throws NoSuchWebsiteException, SystemException {
		Website website = fetchByPrimaryKey(websiteId);

		if (website == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Website exists with the primary key " +
					websiteId);
			}

			throw new NoSuchWebsiteException(
				"No Website exists with the primary key " + websiteId);
		}

		return website;
	}

	public Website fetchByPrimaryKey(long websiteId) throws SystemException {
		Website website = (Website)EntityCacheUtil.getResult(WebsiteModelImpl.ENTITY_CACHE_ENABLED,
				WebsiteImpl.class, websiteId, this);

		if (website == null) {
			Session session = null;

			try {
				session = openSession();

				website = (Website)session.get(WebsiteImpl.class,
						new Long(websiteId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (website != null) {
					cacheResult(website);
				}

				closeSession(session);
			}
		}

		return website;
	}

	public List<Website> findByCompanyId(long companyId)
		throws SystemException {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Website> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<Website> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

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

				qPos.add(companyId);

				list = (List<Website>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Website findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		List<Website> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByCompanyId(companyId);

		List<Website> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website[] findByCompanyId_PrevAndNext(long websiteId,
		long companyId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		Website website = findByPrimaryKey(websiteId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Website WHERE ");

			query.append("companyId = ?");

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

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, website);

			Website[] array = new WebsiteImpl[3];

			array[0] = (Website)objArray[0];
			array[1] = (Website)objArray[1];
			array[2] = (Website)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Website> findByUserId(long userId) throws SystemException {
		int count = countByUserId(userId);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] { new Long(userId) };

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Website> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<Website> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		int count = countByUserId(userId);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("userId = ?");

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

				qPos.add(userId);

				list = (List<Website>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Website findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		List<Website> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByUserId(userId);

		List<Website> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website[] findByUserId_PrevAndNext(long websiteId, long userId,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		Website website = findByPrimaryKey(websiteId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Website WHERE ");

			query.append("userId = ?");

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

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, website);

			Website[] array = new WebsiteImpl[3];

			array[0] = (Website)objArray[0];
			array[1] = (Website)objArray[1];
			array[2] = (Website)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Website> findByC_C(long companyId, long classNameId)
		throws SystemException {
		int count = countByC_C(companyId, classNameId);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Website> findByC_C(long companyId, long classNameId, int start,
		int end) throws SystemException {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	public List<Website> findByC_C(long companyId, long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		int count = countByC_C(companyId, classNameId);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

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

				qPos.add(companyId);

				qPos.add(classNameId);

				list = (List<Website>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Website findByC_C_First(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		List<Website> list = findByC_C(companyId, classNameId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website findByC_C_Last(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchWebsiteException, SystemException {
		int count = countByC_C(companyId, classNameId);

		List<Website> list = findByC_C(companyId, classNameId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website[] findByC_C_PrevAndNext(long websiteId, long companyId,
		long classNameId, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		Website website = findByPrimaryKey(websiteId);

		int count = countByC_C(companyId, classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Website WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

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

			qPos.add(companyId);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, website);

			Website[] array = new WebsiteImpl[3];

			array[0] = (Website)objArray[0];
			array[1] = (Website)objArray[1];
			array[2] = (Website)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Website> findByC_C_C(long companyId, long classNameId,
		long classPK) throws SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Website> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, start, end, null);
	}

	public List<Website> findByC_C_C(long companyId, long classNameId,
		long classPK, int start, int end, OrderByComparator obc)
		throws SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

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

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<Website>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Website findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		List<Website> list = findByC_C_C(companyId, classNameId, classPK, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);

		List<Website> list = findByC_C_C(companyId, classNameId, classPK,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website[] findByC_C_C_PrevAndNext(long websiteId, long companyId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		Website website = findByPrimaryKey(websiteId);

		int count = countByC_C_C(companyId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Website WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

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

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, website);

			Website[] array = new WebsiteImpl[3];

			array[0] = (Website)objArray[0];
			array[1] = (Website)objArray[1];
			array[2] = (Website)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Website> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary) throws SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_C_C_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("primary_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_C_C_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Website> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end)
		throws SystemException {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, start,
			end, null);
	}

	public List<Website> findByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary, int start, int end, OrderByComparator obc)
		throws SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		if (count == 0) {
			return Collections.EMPTY_LIST;
		}

		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_C_C_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("primary_ = ?");

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

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				list = (List<Website>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_C_C_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Website findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		List<Website> list = findByC_C_C_P(companyId, classNameId, classPK,
				primary, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("primary=" + primary);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		List<Website> list = findByC_C_C_P(companyId, classNameId, classPK,
				primary, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Website exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("primary=" + primary);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchWebsiteException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Website[] findByC_C_C_P_PrevAndNext(long websiteId, long companyId,
		long classNameId, long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchWebsiteException, SystemException {
		Website website = findByPrimaryKey(websiteId);

		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.Website WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" AND ");

			query.append("primary_ = ?");

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

			qPos.add(companyId);

			qPos.add(classNameId);

			qPos.add(classPK);

			qPos.add(primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, website);

			Website[] array = new WebsiteImpl[3];

			array[0] = (Website)objArray[0];
			array[1] = (Website)objArray[1];
			array[2] = (Website)objArray[2];

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

	public List<Website> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Website> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Website> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Website> list = (List<Website>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.Website ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Website>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Website>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Website>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (Website website : findByCompanyId(companyId)) {
			remove(website);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (Website website : findByUserId(userId)) {
			remove(website);
		}
	}

	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		for (Website website : findByC_C(companyId, classNameId)) {
			remove(website);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		for (Website website : findByC_C_C(companyId, classNameId, classPK)) {
			remove(website);
		}
	}

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		for (Website website : findByC_C_C_P(companyId, classNameId, classPK,
				primary)) {
			remove(website);
		}
	}

	public void removeAll() throws SystemException {
		for (Website website : findAll()) {
			remove(website);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(classNameId), new Long(classPK),
				Boolean.valueOf(primary)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C_C_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.Website WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("primary_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(primary);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C_P,
					finderArgs, count);

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
						"SELECT COUNT(*) FROM com.liferay.portal.model.Website");

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
						"value.object.listener.com.liferay.portal.model.Website")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Website>> listenersList = new ArrayList<ModelListener<Website>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Website>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	private static Log _log = LogFactoryUtil.getLog(WebsitePersistenceImpl.class);
}