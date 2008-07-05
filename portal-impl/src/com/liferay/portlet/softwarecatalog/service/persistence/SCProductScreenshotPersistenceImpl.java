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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCProductScreenshotPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductScreenshotPersistenceImpl extends BasePersistence
	implements SCProductScreenshotPersistence {
	public SCProductScreenshot create(long productScreenshotId) {
		SCProductScreenshot scProductScreenshot = new SCProductScreenshotImpl();

		scProductScreenshot.setNew(true);
		scProductScreenshot.setPrimaryKey(productScreenshotId);

		return scProductScreenshot;
	}

	public SCProductScreenshot remove(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
					new Long(productScreenshotId));

			if (scProductScreenshot == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No SCProductScreenshot exists with the primary key " +
						productScreenshotId);
				}

				throw new NoSuchProductScreenshotException(
					"No SCProductScreenshot exists with the primary key " +
					productScreenshotId);
			}

			return remove(scProductScreenshot);
		}
		catch (NoSuchProductScreenshotException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductScreenshot remove(SCProductScreenshot scProductScreenshot)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(scProductScreenshot);
			}
		}

		scProductScreenshot = removeImpl(scProductScreenshot);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(scProductScreenshot);
			}
		}

		return scProductScreenshot;
	}

	protected SCProductScreenshot removeImpl(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(scProductScreenshot);

			session.flush();

			return scProductScreenshot;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(SCProductScreenshot.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(SCProductScreenshot scProductScreenshot, boolean merge)</code>.
	 */
	public SCProductScreenshot update(SCProductScreenshot scProductScreenshot)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SCProductScreenshot scProductScreenshot) method. Use update(SCProductScreenshot scProductScreenshot, boolean merge) instead.");
		}

		return update(scProductScreenshot, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        scProductScreenshot the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when scProductScreenshot is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SCProductScreenshot update(SCProductScreenshot scProductScreenshot,
		boolean merge) throws SystemException {
		boolean isNew = scProductScreenshot.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(scProductScreenshot);
				}
				else {
					listener.onBeforeUpdate(scProductScreenshot);
				}
			}
		}

		scProductScreenshot = updateImpl(scProductScreenshot, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(scProductScreenshot);
				}
				else {
					listener.onAfterUpdate(scProductScreenshot);
				}
			}
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(scProductScreenshot);
			}
			else {
				if (scProductScreenshot.isNew()) {
					session.save(scProductScreenshot);
				}
			}

			session.flush();

			scProductScreenshot.setNew(false);

			return scProductScreenshot;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(SCProductScreenshot.class.getName());
		}
	}

	public SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByPrimaryKey(productScreenshotId);

		if (scProductScreenshot == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SCProductScreenshot exists with the primary key " +
					productScreenshotId);
			}

			throw new NoSuchProductScreenshotException(
				"No SCProductScreenshot exists with the primary key " +
				productScreenshotId);
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByPrimaryKey(long productScreenshotId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
				new Long(productScreenshotId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "findByProductEntryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				List<SCProductScreenshot> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<SCProductScreenshot>)result;
		}
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "findByProductEntryId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(productEntryId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("productEntryId ASC, ");
					query.append("priority ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				List<SCProductScreenshot> list = (List<SCProductScreenshot>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<SCProductScreenshot>)result;
		}
	}

	public SCProductScreenshot findByProductEntryId_First(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductScreenshot findByProductEntryId_Last(long productEntryId,
		OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId, OrderByComparator obc)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByPrimaryKey(productScreenshotId);

		int count = countByProductEntryId(productEntryId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

			query.append("productEntryId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(productEntryId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					scProductScreenshot);

			SCProductScreenshot[] array = new SCProductScreenshotImpl[3];

			array[0] = (SCProductScreenshot)objArray[0];
			array[1] = (SCProductScreenshot)objArray[1];
			array[2] = (SCProductScreenshot)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCProductScreenshot findByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByThumbnailId(thumbnailId);

		if (scProductScreenshot == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("thumbnailId=" + thumbnailId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByThumbnailId(long thumbnailId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "fetchByThumbnailId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(thumbnailId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("thumbnailId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				List<SCProductScreenshot> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SCProductScreenshot> list = (List<SCProductScreenshot>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public SCProductScreenshot findByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByFullImageId(fullImageId);

		if (scProductScreenshot == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("fullImageId=" + fullImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByFullImageId(long fullImageId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "fetchByFullImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(fullImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("fullImageId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				List<SCProductScreenshot> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SCProductScreenshot> list = (List<SCProductScreenshot>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public SCProductScreenshot findByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByP_P(productEntryId,
				priority);

		if (scProductScreenshot == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SCProductScreenshot exists with the key {");

			msg.append("productEntryId=" + productEntryId);

			msg.append(", ");
			msg.append("priority=" + priority);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByP_P(long productEntryId, int priority)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "fetchByP_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), new Integer(priority)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" AND ");

				query.append("priority = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("productEntryId ASC, ");
				query.append("priority ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				List<SCProductScreenshot> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SCProductScreenshot> list = (List<SCProductScreenshot>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<SCProductScreenshot> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductScreenshot> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCProductScreenshot> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductScreenshot> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductScreenshot> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("productEntryId ASC, ");
					query.append("priority ASC");
				}

				Query q = session.createQuery(query.toString());

				List<SCProductScreenshot> list = (List<SCProductScreenshot>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
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
			return (List<SCProductScreenshot>)result;
		}
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findByProductEntryId(
				productEntryId)) {
			remove(scProductScreenshot);
		}
	}

	public void removeByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByThumbnailId(thumbnailId);

		remove(scProductScreenshot);
	}

	public void removeByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByFullImageId(fullImageId);

		remove(scProductScreenshot);
	}

	public void removeByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByP_P(productEntryId,
				priority);

		remove(scProductScreenshot);
	}

	public void removeAll() throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findAll()) {
			remove(scProductScreenshot);
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countByProductEntryId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(productEntryId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
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

	public int countByThumbnailId(long thumbnailId) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countByThumbnailId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(thumbnailId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("thumbnailId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
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

	public int countByFullImageId(long fullImageId) throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countByFullImageId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(fullImageId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("fullImageId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
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

	public int countByP_P(long productEntryId, int priority)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countByP_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(productEntryId), new Integer(priority)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot WHERE ");

				query.append("productEntryId = ?");

				query.append(" AND ");

				query.append("priority = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
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
		boolean finderClassNameCacheEnabled = SCProductScreenshotModelImpl.CACHE_ENABLED;
		String finderClassName = SCProductScreenshot.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.softwarecatalog.model.SCProductScreenshot");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
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

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(SCProductScreenshotPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}