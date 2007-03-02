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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.impl.RegionImpl;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.util.StringMaker;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="RegionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RegionPersistence extends BasePersistence {
	public Region create(String regionId) {
		Region region = new RegionImpl();
		region.setNew(true);
		region.setPrimaryKey(regionId);

		return region;
	}

	public Region remove(String regionId)
		throws NoSuchRegionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Region region = (Region)session.get(RegionImpl.class, regionId);

			if (region == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Region exists with the primary key " +
						regionId);
				}

				throw new NoSuchRegionException(
					"No Region exists with the primary key " + regionId);
			}

			return remove(region);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Region remove(Region region) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(region);
			session.flush();

			return region;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region) throws SystemException {
		return update(region, false);
	}

	public com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(region);
			}
			else {
				if (region.isNew()) {
					session.save(region);
				}
			}

			session.flush();
			region.setNew(false);

			return region;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Region findByPrimaryKey(String regionId)
		throws NoSuchRegionException, SystemException {
		Region region = fetchByPrimaryKey(regionId);

		if (region == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Region exists with the primary key " + regionId);
			}

			throw new NoSuchRegionException(
				"No Region exists with the primary key " + regionId);
		}

		return region;
	}

	public Region fetchByPrimaryKey(String regionId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Region)session.get(RegionImpl.class, regionId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCountryId(String countryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCountryId(String countryId, int begin, int end)
		throws SystemException {
		return findByCountryId(countryId, begin, end, null);
	}

	public List findByCountryId(String countryId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Region findByCountryId_First(String countryId, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		List list = findByCountryId(countryId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Region exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("countryId=");
			msg.append(countryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchRegionException(msg.toString());
		}
		else {
			return (Region)list.get(0);
		}
	}

	public Region findByCountryId_Last(String countryId, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		int count = countByCountryId(countryId);
		List list = findByCountryId(countryId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Region exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("countryId=");
			msg.append(countryId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchRegionException(msg.toString());
		}
		else {
			return (Region)list.get(0);
		}
	}

	public Region[] findByCountryId_PrevAndNext(String regionId,
		String countryId, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		Region region = findByPrimaryKey(regionId);
		int count = countByCountryId(countryId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, region);
			Region[] array = new RegionImpl[3];
			array[0] = (Region)objArray[0];
			array[1] = (Region)objArray[1];
			array[2] = (Region)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByActive(boolean active, int begin, int end)
		throws SystemException {
		return findByActive(active, begin, end, null);
	}

	public List findByActive(boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Region findByActive_First(boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		List list = findByActive(active, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Region exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchRegionException(msg.toString());
		}
		else {
			return (Region)list.get(0);
		}
	}

	public Region findByActive_Last(boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		int count = countByActive(active);
		List list = findByActive(active, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Region exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchRegionException(msg.toString());
		}
		else {
			return (Region)list.get(0);
		}
	}

	public Region[] findByActive_PrevAndNext(String regionId, boolean active,
		OrderByComparator obc) throws NoSuchRegionException, SystemException {
		Region region = findByPrimaryKey(regionId);
		int count = countByActive(active);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, region);
			Region[] array = new RegionImpl[3];
			array[0] = (Region)objArray[0];
			array[1] = (Region)objArray[1];
			array[2] = (Region)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_A(String countryId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_A(String countryId, boolean active, int begin, int end)
		throws SystemException {
		return findByC_A(countryId, active, begin, end, null);
	}

	public List findByC_A(String countryId, boolean active, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Region findByC_A_First(String countryId, boolean active,
		OrderByComparator obc) throws NoSuchRegionException, SystemException {
		List list = findByC_A(countryId, active, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Region exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("countryId=");
			msg.append(countryId);
			msg.append(", ");
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchRegionException(msg.toString());
		}
		else {
			return (Region)list.get(0);
		}
	}

	public Region findByC_A_Last(String countryId, boolean active,
		OrderByComparator obc) throws NoSuchRegionException, SystemException {
		int count = countByC_A(countryId, active);
		List list = findByC_A(countryId, active, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Region exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("countryId=");
			msg.append(countryId);
			msg.append(", ");
			msg.append("active=");
			msg.append(active);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchRegionException(msg.toString());
		}
		else {
			return (Region)list.get(0);
		}
	}

	public Region[] findByC_A_PrevAndNext(String regionId, String countryId,
		boolean active, OrderByComparator obc)
		throws NoSuchRegionException, SystemException {
		Region region = findByPrimaryKey(regionId);
		int count = countByC_A(countryId, active);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, region);
			Region[] array = new RegionImpl[3];
			array[0] = (Region)objArray[0];
			array[1] = (Region)objArray[1];
			array[2] = (Region)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Region ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCountryId(String countryId) throws SystemException {
		Iterator itr = findByCountryId(countryId).iterator();

		while (itr.hasNext()) {
			Region region = (Region)itr.next();
			remove(region);
		}
	}

	public void removeByActive(boolean active) throws SystemException {
		Iterator itr = findByActive(active).iterator();

		while (itr.hasNext()) {
			Region region = (Region)itr.next();
			remove(region);
		}
	}

	public void removeByC_A(String countryId, boolean active)
		throws SystemException {
		Iterator itr = findByC_A(countryId, active).iterator();

		while (itr.hasNext()) {
			Region region = (Region)itr.next();
			remove(region);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Region)itr.next());
		}
	}

	public int countByCountryId(String countryId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByActive(boolean active) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Region WHERE ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_A(String countryId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Region WHERE ");

			if (countryId == null) {
				query.append("countryId IS NULL");
			}
			else {
				query.append("countryId = ?");
			}

			query.append(" AND ");
			query.append("active_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (countryId != null) {
				q.setString(queryPos++, countryId);
			}

			q.setBoolean(queryPos++, active);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Region");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(RegionPersistence.class);
}