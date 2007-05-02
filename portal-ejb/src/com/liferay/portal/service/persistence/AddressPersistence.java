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

import com.liferay.portal.NoSuchAddressException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="AddressPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddressPersistence extends BasePersistence {
	public Address create(long addressId) {
		Address address = new AddressImpl();
		address.setNew(true);
		address.setPrimaryKey(addressId);

		return address;
	}

	public Address remove(long addressId)
		throws NoSuchAddressException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Address address = (Address)session.get(AddressImpl.class,
					new Long(addressId));

			if (address == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Address exists with the primary key " +
						addressId);
				}

				throw new NoSuchAddressException(
					"No Address exists with the primary key " + addressId);
			}

			return remove(address);
		}
		catch (NoSuchAddressException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address remove(Address address) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(address);
			session.flush();

			return address;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address) throws SystemException {
		return update(address, false);
	}

	public com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(address);
			}
			else {
				if (address.isNew()) {
					session.save(address);
				}
			}

			session.flush();
			address.setNew(false);

			return address;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByPrimaryKey(long addressId)
		throws NoSuchAddressException, SystemException {
		Address address = fetchByPrimaryKey(addressId);

		if (address == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Address exists with the primary key " +
					addressId);
			}

			throw new NoSuchAddressException(
				"No Address exists with the primary key " + addressId);
		}

		return address;
	}

	public Address fetchByPrimaryKey(long addressId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Address)session.get(AddressImpl.class, new Long(addressId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(long companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(long companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByCompanyId_PrevAndNext(long addressId,
		long companyId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new AddressImpl[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("userId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, userId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByUserId(userId);
		List list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByUserId_PrevAndNext(long addressId, long userId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByUserId(userId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new AddressImpl[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(long companyId, long classNameId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C(long companyId, long classNameId, int begin, int end)
		throws SystemException {
		return findByC_C(companyId, classNameId, begin, end, null);
	}

	public List findByC_C(long companyId, long classNameId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_First(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		List list = findByC_C(companyId, classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_Last(long companyId, long classNameId,
		OrderByComparator obc) throws NoSuchAddressException, SystemException {
		int count = countByC_C(companyId, classNameId);
		List list = findByC_C(companyId, classNameId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_PrevAndNext(long addressId, long companyId,
		long classNameId, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C(companyId, classNameId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new AddressImpl[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C(long companyId, long classNameId, long classPK,
		int begin, int end) throws SystemException {
		return findByC_C_C(companyId, classNameId, classPK, begin, end, null);
	}

	public List findByC_C_C(long companyId, long classNameId, long classPK,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_C_First(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByC_C_C(companyId, classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_C_Last(long companyId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C(companyId, classNameId, classPK);
		List list = findByC_C_C(companyId, classNameId, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_C_PrevAndNext(long addressId, long companyId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C(companyId, classNameId, classPK);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new AddressImpl[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("createDate ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, mailing);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing, int begin, int end) throws SystemException {
		return findByC_C_C_M(companyId, classNameId, classPK, mailing, begin,
			end, null);
	}

	public List findByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("mailing = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, mailing);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_C_M_First(long companyId, long classNameId,
		long classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByC_C_C_M(companyId, classNameId, classPK, mailing, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(", ");
			msg.append("mailing=");
			msg.append(mailing);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_C_M_Last(long companyId, long classNameId,
		long classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C_M(companyId, classNameId, classPK, mailing);
		List list = findByC_C_C_M(companyId, classNameId, classPK, mailing,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(", ");
			msg.append("mailing=");
			msg.append(mailing);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_C_M_PrevAndNext(long addressId, long companyId,
		long classNameId, long classPK, boolean mailing, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C_M(companyId, classNameId, classPK, mailing);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("mailing = ?");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, mailing);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new AddressImpl[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			return q.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary, int begin, int end) throws SystemException {
		return findByC_C_C_P(companyId, classNameId, classPK, primary, begin,
			end, null);
	}

	public List findByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary, int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Address findByC_C_C_P_First(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		List list = findByC_C_C_P(companyId, classNameId, classPK, primary, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(", ");
			msg.append("primary=");
			msg.append(primary);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address findByC_C_C_P_Last(long companyId, long classNameId,
		long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);
		List list = findByC_C_C_P(companyId, classNameId, classPK, primary,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No Address exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("classNameId=");
			msg.append(classNameId);
			msg.append(", ");
			msg.append("classPK=");
			msg.append(classPK);
			msg.append(", ");
			msg.append("primary=");
			msg.append(primary);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchAddressException(msg.toString());
		}
		else {
			return (Address)list.get(0);
		}
	}

	public Address[] findByC_C_C_P_PrevAndNext(long addressId, long companyId,
		long classNameId, long classPK, boolean primary, OrderByComparator obc)
		throws NoSuchAddressException, SystemException {
		Address address = findByPrimaryKey(addressId);
		int count = countByC_C_C_P(companyId, classNameId, classPK, primary);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.Address WHERE ");
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
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, address);
			Address[] array = new AddressImpl[3];
			array[0] = (Address)objArray[0];
			array[1] = (Address)objArray[1];
			array[2] = (Address)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
			query.append("FROM com.liferay.portal.model.Address ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		Iterator itr = findByUserId(userId).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		Iterator itr = findByC_C(companyId, classNameId).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Iterator itr = findByC_C_C(companyId, classNameId, classPK).iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws SystemException {
		Iterator itr = findByC_C_C_M(companyId, classNameId, classPK, mailing)
						   .iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Iterator itr = findByC_C_C_P(companyId, classNameId, classPK, primary)
						   .iterator();

		while (itr.hasNext()) {
			Address address = (Address)itr.next();
			remove(address);
		}
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Address)itr.next());
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("mailing = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, mailing);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Address WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("classNameId = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" AND ");
			query.append("primary_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, companyId);
			q.setLong(queryPos++, classNameId);
			q.setLong(queryPos++, classPK);
			q.setBoolean(queryPos++, primary);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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
			query.append("FROM com.liferay.portal.model.Address");

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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void initDao() {
	}

	private static Log _log = LogFactory.getLog(AddressPersistence.class);
}