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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.impl.ExpandoColumnImpl;
import com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoColumnPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnPersistenceImpl extends BasePersistenceImpl
	implements ExpandoColumnPersistence {
	public ExpandoColumn create(long columnId) {
		ExpandoColumn expandoColumn = new ExpandoColumnImpl();

		expandoColumn.setNew(true);
		expandoColumn.setPrimaryKey(columnId);

		return expandoColumn;
	}

	public ExpandoColumn remove(long columnId)
		throws NoSuchColumnException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoColumn expandoColumn = (ExpandoColumn)session.get(ExpandoColumnImpl.class,
					new Long(columnId));

			if (expandoColumn == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoColumn exists with the primary key " +
						columnId);
				}

				throw new NoSuchColumnException(
					"No ExpandoColumn exists with the primary key " + columnId);
			}

			return remove(expandoColumn);
		}
		catch (NoSuchColumnException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(expandoColumn);
			}
		}

		expandoColumn = removeImpl(expandoColumn);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(expandoColumn);
			}
		}

		return expandoColumn;
	}

	protected ExpandoColumn removeImpl(ExpandoColumn expandoColumn)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoColumn);

			session.flush();

			return expandoColumn;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ExpandoColumn.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ExpandoColumn expandoColumn, boolean merge)</code>.
	 */
	public ExpandoColumn update(ExpandoColumn expandoColumn)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoColumn expandoColumn) method. Use update(ExpandoColumn expandoColumn, boolean merge) instead.");
		}

		return update(expandoColumn, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoColumn the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoColumn is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoColumn update(ExpandoColumn expandoColumn, boolean merge)
		throws SystemException {
		boolean isNew = expandoColumn.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(expandoColumn);
				}
				else {
					listener.onBeforeUpdate(expandoColumn);
				}
			}
		}

		expandoColumn = updateImpl(expandoColumn, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(expandoColumn);
				}
				else {
					listener.onAfterUpdate(expandoColumn);
				}
			}
		}

		return expandoColumn;
	}

	public ExpandoColumn updateImpl(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoColumn);
			}
			else {
				if (expandoColumn.isNew()) {
					session.save(expandoColumn);
				}
			}

			session.flush();

			expandoColumn.setNew(false);

			return expandoColumn;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ExpandoColumn.class.getName());
		}
	}

	public ExpandoColumn findByPrimaryKey(long columnId)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByPrimaryKey(columnId);

		if (expandoColumn == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoColumn exists with the primary key " +
					columnId);
			}

			throw new NoSuchColumnException(
				"No ExpandoColumn exists with the primary key " + columnId);
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByPrimaryKey(long columnId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoColumn)session.get(ExpandoColumnImpl.class,
				new Long(columnId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoColumn> findByTableId(long tableId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
		String finderMethodName = "findByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

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
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoColumn> list = q.list();

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
			return (List<ExpandoColumn>)result;
		}
	}

	public List<ExpandoColumn> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoColumn> findByTableId(long tableId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
		String finderMethodName = "findByTableId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
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
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoColumn> list = (List<ExpandoColumn>)QueryUtil.list(q,
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
			return (List<ExpandoColumn>)result;
		}
	}

	public ExpandoColumn findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchColumnException, SystemException {
		List<ExpandoColumn> list = findByTableId(tableId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn findByTableId_Last(long tableId, OrderByComparator obc)
		throws NoSuchColumnException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoColumn> list = findByTableId(tableId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn[] findByTableId_PrevAndNext(long columnId,
		long tableId, OrderByComparator obc)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = findByPrimaryKey(columnId);

		int count = countByTableId(tableId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

			query.append("tableId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoColumn);

			ExpandoColumn[] array = new ExpandoColumnImpl[3];

			array[0] = (ExpandoColumn)objArray[0];
			array[1] = (ExpandoColumn)objArray[1];
			array[2] = (ExpandoColumn)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn findByT_N(long tableId, String name)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByT_N(tableId, name);

		if (expandoColumn == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchColumnException(msg.toString());
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByT_N(long tableId, String name)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
		String finderMethodName = "fetchByT_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(tableId), name };

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
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (name != null) {
					qPos.add(name);
				}

				List<ExpandoColumn> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
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
			List<ExpandoColumn> list = (List<ExpandoColumn>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ExpandoColumn> findWithDynamicQuery(DynamicQuery dynamicQuery)
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

	public List<ExpandoColumn> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoColumn> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoColumn> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoColumn> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
					"FROM com.liferay.portlet.expando.model.ExpandoColumn ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoColumn> list = (List<ExpandoColumn>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
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
			return (List<ExpandoColumn>)result;
		}
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoColumn expandoColumn : findByTableId(tableId)) {
			remove(expandoColumn);
		}
	}

	public void removeByT_N(long tableId, String name)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = findByT_N(tableId, name);

		remove(expandoColumn);
	}

	public void removeAll() throws SystemException {
		for (ExpandoColumn expandoColumn : findAll()) {
			remove(expandoColumn);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
		String finderMethodName = "countByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

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
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

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

	public int countByT_N(long tableId, String name) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
		String finderMethodName = "countByT_N";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(tableId), name };

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
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (name != null) {
					qPos.add(name);
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
		boolean finderClassNameCacheEnabled = ExpandoColumnModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoColumn.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.expando.model.ExpandoColumn");

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
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoColumn")));

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

	private static Log _log = LogFactory.getLog(ExpandoColumnPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}