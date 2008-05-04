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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.social.NoSuchRequestException;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.impl.SocialRequestImpl;
import com.liferay.portlet.social.model.impl.SocialRequestModelImpl;

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
 * <a href="SocialRequestPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialRequestPersistenceImpl extends BasePersistence
	implements SocialRequestPersistence {
	public SocialRequest create(long requestId) {
		SocialRequest socialRequest = new SocialRequestImpl();

		socialRequest.setNew(true);
		socialRequest.setPrimaryKey(requestId);

		String uuid = PortalUUIDUtil.generate();

		socialRequest.setUuid(uuid);

		return socialRequest;
	}

	public SocialRequest remove(long requestId)
		throws NoSuchRequestException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialRequest socialRequest = (SocialRequest)session.get(SocialRequestImpl.class,
					new Long(requestId));

			if (socialRequest == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SocialRequest exists with the primary key " +
						requestId);
				}

				throw new NoSuchRequestException(
					"No SocialRequest exists with the primary key " +
					requestId);
			}

			return remove(socialRequest);
		}
		catch (NoSuchRequestException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRequest remove(SocialRequest socialRequest)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(socialRequest);
			}
		}

		socialRequest = removeImpl(socialRequest);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(socialRequest);
			}
		}

		return socialRequest;
	}

	protected SocialRequest removeImpl(SocialRequest socialRequest)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(socialRequest);

			session.flush();

			return socialRequest;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(SocialRequest.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(SocialRequest socialRequest, boolean merge)</code>.
	 */
	public SocialRequest update(SocialRequest socialRequest)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SocialRequest socialRequest) method. Use update(SocialRequest socialRequest, boolean merge) instead.");
		}

		return update(socialRequest, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        socialRequest the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when socialRequest is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SocialRequest update(SocialRequest socialRequest, boolean merge)
		throws SystemException {
		boolean isNew = socialRequest.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(socialRequest);
				}
				else {
					listener.onBeforeUpdate(socialRequest);
				}
			}
		}

		socialRequest = updateImpl(socialRequest, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(socialRequest);
				}
				else {
					listener.onAfterUpdate(socialRequest);
				}
			}
		}

		return socialRequest;
	}

	public SocialRequest updateImpl(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge) throws SystemException {
		if (Validator.isNull(socialRequest.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			socialRequest.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(socialRequest);
			}
			else {
				if (socialRequest.isNew()) {
					session.save(socialRequest);
				}
			}

			session.flush();

			socialRequest.setNew(false);

			return socialRequest;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(SocialRequest.class.getName());
		}
	}

	public SocialRequest findByPrimaryKey(long requestId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByPrimaryKey(requestId);

		if (socialRequest == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SocialRequest exists with the primary key " +
					requestId);
			}

			throw new NoSuchRequestException(
				"No SocialRequest exists with the primary key " + requestId);
		}

		return socialRequest;
	}

	public SocialRequest fetchByPrimaryKey(long requestId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SocialRequest)session.get(SocialRequestImpl.class,
				new Long(requestId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByUuid(String uuid)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<SocialRequest> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public List<SocialRequest> findByUuid(String uuid, int begin, int end)
		throws SystemException {
		return findByUuid(uuid, begin, end, null);
	}

	public List<SocialRequest> findByUuid(String uuid, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public SocialRequest findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		int count = countByUuid(uuid);

		List<SocialRequest> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByUuid_PrevAndNext(long requestId, String uuid,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("requestId DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRequest findByUUID_G(String uuid, long groupId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByUUID_G(uuid, groupId);

		if (socialRequest == null) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	public SocialRequest fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "fetchByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<SocialRequest> list = q.list();

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SocialRequest> list = (List<SocialRequest>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<SocialRequest> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SocialRequest> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public List<SocialRequest> findByCompanyId(long companyId, int begin,
		int end) throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List<SocialRequest> findByCompanyId(long companyId, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public SocialRequest findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialRequest> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByCompanyId_PrevAndNext(long requestId,
		long companyId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("requestId DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<SocialRequest> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public List<SocialRequest> findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List<SocialRequest> findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public SocialRequest findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		int count = countByUserId(userId);

		List<SocialRequest> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByUserId_PrevAndNext(long requestId,
		long userId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("requestId DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByReceiverUserId(long receiverUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByReceiverUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("receiverUserId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				List<SocialRequest> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public List<SocialRequest> findByReceiverUserId(long receiverUserId,
		int begin, int end) throws SystemException {
		return findByReceiverUserId(receiverUserId, begin, end, null);
	}

	public List<SocialRequest> findByReceiverUserId(long receiverUserId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByReceiverUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("receiverUserId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public SocialRequest findByReceiverUserId_First(long receiverUserId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByReceiverUserId(receiverUserId, 0, 1,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("receiverUserId=" + receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByReceiverUserId_Last(long receiverUserId,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByReceiverUserId(receiverUserId);

		List<SocialRequest> list = findByReceiverUserId(receiverUserId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("receiverUserId=" + receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByReceiverUserId_PrevAndNext(long requestId,
		long receiverUserId, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByReceiverUserId(receiverUserId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

			query.append("receiverUserId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("requestId DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(receiverUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByU_S(long userId, int status)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByU_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), new Integer(status) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				List<SocialRequest> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public List<SocialRequest> findByU_S(long userId, int status, int begin,
		int end) throws SystemException {
		return findByU_S(userId, status, begin, end, null);
	}

	public List<SocialRequest> findByU_S(long userId, int status, int begin,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByU_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Integer(status),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public SocialRequest findByU_S_First(long userId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByU_S(userId, status, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByU_S_Last(long userId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByU_S(userId, status);

		List<SocialRequest> list = findByU_S(userId, status, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByU_S_PrevAndNext(long requestId, long userId,
		int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByU_S(userId, status);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

			query.append("userId = ?");

			query.append(" AND ");

			query.append("status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("requestId DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findByR_S(long receiverUserId, int status)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByR_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId), new Integer(status)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("receiverUserId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				List<SocialRequest> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public List<SocialRequest> findByR_S(long receiverUserId, int status,
		int begin, int end) throws SystemException {
		return findByR_S(receiverUserId, status, begin, end, null);
	}

	public List<SocialRequest> findByR_S(long receiverUserId, int status,
		int begin, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findByR_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId), new Integer(status),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("receiverUserId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public SocialRequest findByR_S_First(long receiverUserId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		List<SocialRequest> list = findByR_S(receiverUserId, status, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("receiverUserId=" + receiverUserId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest findByR_S_Last(long receiverUserId, int status,
		OrderByComparator obc) throws NoSuchRequestException, SystemException {
		int count = countByR_S(receiverUserId, status);

		List<SocialRequest> list = findByR_S(receiverUserId, status, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("receiverUserId=" + receiverUserId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchRequestException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialRequest[] findByR_S_PrevAndNext(long requestId,
		long receiverUserId, int status, OrderByComparator obc)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByPrimaryKey(requestId);

		int count = countByR_S(receiverUserId, status);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

			query.append("receiverUserId = ?");

			query.append(" AND ");

			query.append("status = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("requestId DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(receiverUserId);

			qPos.add(status);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialRequest);

			SocialRequest[] array = new SocialRequestImpl[3];

			array[0] = (SocialRequest)objArray[0];
			array[1] = (SocialRequest)objArray[1];
			array[2] = (SocialRequest)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialRequest findByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_C_C_T_R(userId, classNameId,
				classPK, type, receiverUserId);

		if (socialRequest == null) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(", ");
			msg.append("receiverUserId=" + receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	public SocialRequest fetchByU_C_C_T_R(long userId, long classNameId,
		long classPK, int type, long receiverUserId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "fetchByU_C_C_T_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" AND ");

				query.append("receiverUserId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				List<SocialRequest> list = q.list();

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SocialRequest> list = (List<SocialRequest>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public SocialRequest findByU_C_C_T_R_S(long userId, long classNameId,
		long classPK, int type, long receiverUserId, int status)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = fetchByU_C_C_T_R_S(userId, classNameId,
				classPK, type, receiverUserId, status);

		if (socialRequest == null) {
			StringMaker msg = new StringMaker();

			msg.append("No SocialRequest exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(", ");
			msg.append("receiverUserId=" + receiverUserId);

			msg.append(", ");
			msg.append("status=" + status);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRequestException(msg.toString());
		}

		return socialRequest;
	}

	public SocialRequest fetchByU_C_C_T_R_S(long userId, long classNameId,
		long classPK, int type, long receiverUserId, int status)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "fetchByU_C_C_T_R_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId), new Integer(status)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" AND ");

				query.append("receiverUserId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("requestId DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

				List<SocialRequest> list = q.list();

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<SocialRequest> list = (List<SocialRequest>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<SocialRequest> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialRequest> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialRequest> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<SocialRequest> findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("requestId DESC");
				}

				Query q = session.createQuery(query.toString());

				List<SocialRequest> list = (List<SocialRequest>)QueryUtil.list(q,
						getDialect(), begin, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<SocialRequest>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (SocialRequest socialRequest : findByUuid(uuid)) {
			remove(socialRequest);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByUUID_G(uuid, groupId);

		remove(socialRequest);
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialRequest socialRequest : findByCompanyId(companyId)) {
			remove(socialRequest);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (SocialRequest socialRequest : findByUserId(userId)) {
			remove(socialRequest);
		}
	}

	public void removeByReceiverUserId(long receiverUserId)
		throws SystemException {
		for (SocialRequest socialRequest : findByReceiverUserId(receiverUserId)) {
			remove(socialRequest);
		}
	}

	public void removeByU_S(long userId, int status) throws SystemException {
		for (SocialRequest socialRequest : findByU_S(userId, status)) {
			remove(socialRequest);
		}
	}

	public void removeByR_S(long receiverUserId, int status)
		throws SystemException {
		for (SocialRequest socialRequest : findByR_S(receiverUserId, status)) {
			remove(socialRequest);
		}
	}

	public void removeByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByU_C_C_T_R(userId, classNameId,
				classPK, type, receiverUserId);

		remove(socialRequest);
	}

	public void removeByU_C_C_T_R_S(long userId, long classNameId,
		long classPK, int type, long receiverUserId, int status)
		throws NoSuchRequestException, SystemException {
		SocialRequest socialRequest = findByU_C_C_T_R_S(userId, classNameId,
				classPK, type, receiverUserId, status);

		remove(socialRequest);
	}

	public void removeAll() throws SystemException {
		for (SocialRequest socialRequest : findAll()) {
			remove(socialRequest);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByUUID_G";
		String[] finderParams = new String[] {
				String.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" AND ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByReceiverUserId(long receiverUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByReceiverUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("receiverUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByU_S(long userId, int status) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByU_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), new Integer(status) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(status);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByR_S(long receiverUserId, int status)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByR_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId), new Integer(status)
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

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("receiverUserId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				qPos.add(status);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByU_C_C_T_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId)
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

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" AND ");

				query.append("receiverUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByU_C_C_T_R_S(long userId, long classNameId, long classPK,
		int type, long receiverUserId, int status) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
		String finderMethodName = "countByU_C_C_T_R_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId), new Integer(status)
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

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.social.model.SocialRequest WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" AND ");

				query.append("receiverUserId = ?");

				query.append(" AND ");

				query.append("status = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				qPos.add(status);

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
				throw HibernateUtil.processException(e);
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
		boolean finderClassNameCacheEnabled = SocialRequestModelImpl.CACHE_ENABLED;
		String finderClassName = SocialRequest.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.social.model.SocialRequest");

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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialRequest")));

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

	private static Log _log = LogFactory.getLog(SocialRequestPersistenceImpl.class);
	private ModelListener[] _listeners;
}