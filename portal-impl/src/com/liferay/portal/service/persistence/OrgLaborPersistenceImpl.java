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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchOrgLaborException;
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
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.impl.OrgLaborImpl;
import com.liferay.portal.model.impl.OrgLaborModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="OrgLaborPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborPersistence
 * @see       OrgLaborUtil
 * @generated
 */
public class OrgLaborPersistenceImpl extends BasePersistenceImpl<OrgLabor>
	implements OrgLaborPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = OrgLaborImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ORGANIZATIONID = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByOrganizationId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_ORGANIZATIONID = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByOrganizationId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ORGANIZATIONID = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByOrganizationId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(OrgLabor orgLabor) {
		EntityCacheUtil.putResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborImpl.class, orgLabor.getPrimaryKey(), orgLabor);
	}

	public void cacheResult(List<OrgLabor> orgLabors) {
		for (OrgLabor orgLabor : orgLabors) {
			if (EntityCacheUtil.getResult(
						OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
						OrgLaborImpl.class, orgLabor.getPrimaryKey(), this) == null) {
				cacheResult(orgLabor);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(OrgLaborImpl.class.getName());
		EntityCacheUtil.clearCache(OrgLaborImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public OrgLabor create(long orgLaborId) {
		OrgLabor orgLabor = new OrgLaborImpl();

		orgLabor.setNew(true);
		orgLabor.setPrimaryKey(orgLaborId);

		return orgLabor;
	}

	public OrgLabor remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public OrgLabor remove(long orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgLabor orgLabor = (OrgLabor)session.get(OrgLaborImpl.class,
					new Long(orgLaborId));

			if (orgLabor == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No OrgLabor exists with the primary key " +
						orgLaborId);
				}

				throw new NoSuchOrgLaborException(
					"No OrgLabor exists with the primary key " + orgLaborId);
			}

			return remove(orgLabor);
		}
		catch (NoSuchOrgLaborException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public OrgLabor remove(OrgLabor orgLabor) throws SystemException {
		for (ModelListener<OrgLabor> listener : listeners) {
			listener.onBeforeRemove(orgLabor);
		}

		orgLabor = removeImpl(orgLabor);

		for (ModelListener<OrgLabor> listener : listeners) {
			listener.onAfterRemove(orgLabor);
		}

		return orgLabor;
	}

	protected OrgLabor removeImpl(OrgLabor orgLabor) throws SystemException {
		orgLabor = toUnwrappedModel(orgLabor);

		Session session = null;

		try {
			session = openSession();

			if (orgLabor.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(OrgLaborImpl.class,
						orgLabor.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(orgLabor);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborImpl.class, orgLabor.getPrimaryKey());

		return orgLabor;
	}

	public OrgLabor updateImpl(com.liferay.portal.model.OrgLabor orgLabor,
		boolean merge) throws SystemException {
		orgLabor = toUnwrappedModel(orgLabor);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, orgLabor, merge);

			orgLabor.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
			OrgLaborImpl.class, orgLabor.getPrimaryKey(), orgLabor);

		return orgLabor;
	}

	protected OrgLabor toUnwrappedModel(OrgLabor orgLabor) {
		if (orgLabor instanceof OrgLaborImpl) {
			return orgLabor;
		}

		OrgLaborImpl orgLaborImpl = new OrgLaborImpl();

		orgLaborImpl.setNew(orgLabor.isNew());
		orgLaborImpl.setPrimaryKey(orgLabor.getPrimaryKey());

		orgLaborImpl.setOrgLaborId(orgLabor.getOrgLaborId());
		orgLaborImpl.setOrganizationId(orgLabor.getOrganizationId());
		orgLaborImpl.setTypeId(orgLabor.getTypeId());
		orgLaborImpl.setSunOpen(orgLabor.getSunOpen());
		orgLaborImpl.setSunClose(orgLabor.getSunClose());
		orgLaborImpl.setMonOpen(orgLabor.getMonOpen());
		orgLaborImpl.setMonClose(orgLabor.getMonClose());
		orgLaborImpl.setTueOpen(orgLabor.getTueOpen());
		orgLaborImpl.setTueClose(orgLabor.getTueClose());
		orgLaborImpl.setWedOpen(orgLabor.getWedOpen());
		orgLaborImpl.setWedClose(orgLabor.getWedClose());
		orgLaborImpl.setThuOpen(orgLabor.getThuOpen());
		orgLaborImpl.setThuClose(orgLabor.getThuClose());
		orgLaborImpl.setFriOpen(orgLabor.getFriOpen());
		orgLaborImpl.setFriClose(orgLabor.getFriClose());
		orgLaborImpl.setSatOpen(orgLabor.getSatOpen());
		orgLaborImpl.setSatClose(orgLabor.getSatClose());

		return orgLaborImpl;
	}

	public OrgLabor findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public OrgLabor findByPrimaryKey(long orgLaborId)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = fetchByPrimaryKey(orgLaborId);

		if (orgLabor == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No OrgLabor exists with the primary key " +
					orgLaborId);
			}

			throw new NoSuchOrgLaborException(
				"No OrgLabor exists with the primary key " + orgLaborId);
		}

		return orgLabor;
	}

	public OrgLabor fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public OrgLabor fetchByPrimaryKey(long orgLaborId)
		throws SystemException {
		OrgLabor orgLabor = (OrgLabor)EntityCacheUtil.getResult(OrgLaborModelImpl.ENTITY_CACHE_ENABLED,
				OrgLaborImpl.class, orgLaborId, this);

		if (orgLabor == null) {
			Session session = null;

			try {
				session = openSession();

				orgLabor = (OrgLabor)session.get(OrgLaborImpl.class,
						new Long(orgLaborId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (orgLabor != null) {
					cacheResult(orgLabor);
				}

				closeSession(session);
			}
		}

		return orgLabor;
	}

	public List<OrgLabor> findByOrganizationId(long organizationId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(organizationId) };

		List<OrgLabor> list = (List<OrgLabor>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ORGANIZATIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT orgLabor FROM OrgLabor orgLabor WHERE ");

				query.append("orgLabor.organizationId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("orgLabor.organizationId ASC, ");
				query.append("orgLabor.typeId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<OrgLabor>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ORGANIZATIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<OrgLabor> findByOrganizationId(long organizationId, int start,
		int end) throws SystemException {
		return findByOrganizationId(organizationId, start, end, null);
	}

	public List<OrgLabor> findByOrganizationId(long organizationId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(organizationId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<OrgLabor> list = (List<OrgLabor>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_ORGANIZATIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT orgLabor FROM OrgLabor orgLabor WHERE ");

				query.append("orgLabor.organizationId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("orgLabor.");
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

					query.append("orgLabor.organizationId ASC, ");
					query.append("orgLabor.typeId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				list = (List<OrgLabor>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<OrgLabor>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_ORGANIZATIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public OrgLabor findByOrganizationId_First(long organizationId,
		OrderByComparator obc) throws NoSuchOrgLaborException, SystemException {
		List<OrgLabor> list = findByOrganizationId(organizationId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgLabor exists with the key {");

			msg.append("organizationId=" + organizationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgLaborException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgLabor findByOrganizationId_Last(long organizationId,
		OrderByComparator obc) throws NoSuchOrgLaborException, SystemException {
		int count = countByOrganizationId(organizationId);

		List<OrgLabor> list = findByOrganizationId(organizationId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No OrgLabor exists with the key {");

			msg.append("organizationId=" + organizationId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgLaborException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgLabor[] findByOrganizationId_PrevAndNext(long orgLaborId,
		long organizationId, OrderByComparator obc)
		throws NoSuchOrgLaborException, SystemException {
		OrgLabor orgLabor = findByPrimaryKey(orgLaborId);

		int count = countByOrganizationId(organizationId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT orgLabor FROM OrgLabor orgLabor WHERE ");

			query.append("orgLabor.organizationId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("orgLabor.");
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

				query.append("orgLabor.organizationId ASC, ");
				query.append("orgLabor.typeId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(organizationId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, orgLabor);

			OrgLabor[] array = new OrgLaborImpl[3];

			array[0] = (OrgLabor)objArray[0];
			array[1] = (OrgLabor)objArray[1];
			array[2] = (OrgLabor)objArray[2];

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

	public List<OrgLabor> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<OrgLabor> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<OrgLabor> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<OrgLabor> list = (List<OrgLabor>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT orgLabor FROM OrgLabor orgLabor ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("orgLabor.");
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

					query.append("orgLabor.organizationId ASC, ");
					query.append("orgLabor.typeId ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<OrgLabor>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<OrgLabor>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<OrgLabor>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByOrganizationId(long organizationId)
		throws SystemException {
		for (OrgLabor orgLabor : findByOrganizationId(organizationId)) {
			remove(orgLabor);
		}
	}

	public void removeAll() throws SystemException {
		for (OrgLabor orgLabor : findAll()) {
			remove(orgLabor);
		}
	}

	public int countByOrganizationId(long organizationId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(organizationId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ORGANIZATIONID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(orgLabor) ");
				query.append("FROM OrgLabor orgLabor WHERE ");

				query.append("orgLabor.organizationId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ORGANIZATIONID,
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
						"SELECT COUNT(orgLabor) FROM OrgLabor orgLabor");

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
						"value.object.listener.com.liferay.portal.model.OrgLabor")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<OrgLabor>> listenersList = new ArrayList<ModelListener<OrgLabor>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<OrgLabor>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(OrgLaborPersistenceImpl.class);
}