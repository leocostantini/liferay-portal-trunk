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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoValueFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ExpandoValueFinderImpl
	extends BasePersistenceImpl implements ExpandoValueFinder {

	public static String COUNT_BY_TC_TC_TN_CN =
		ExpandoValueFinder.class.getName() + ".countByTC_TC_TN_CN";

	public static String COUNT_BY_TC_TC_TN_C =
		ExpandoValueFinder.class.getName() + ".countByTC_TC_TN_C";

	public static String COUNT_BY_TC_TC_TN_CN_D =
		ExpandoValueFinder.class.getName() + ".countByTC_TC_TN_CN_D";

	public static String FIND_BY_TC_TC_TN_CN =
		ExpandoValueFinder.class.getName() + ".findByTC_TC_TN_CN";

	public static String FIND_BY_TC_TC_TN_C =
		ExpandoValueFinder.class.getName() + ".findByTC_TC_TN_C";

	public static String FIND_BY_TC_TC_TN_CN_C =
		ExpandoValueFinder.class.getName() + ".findByTC_TC_TN_CN_C";

	public static String FIND_BY_TC_TC_TN_CN_D =
		ExpandoValueFinder.class.getName() + ".findByTC_TC_TN_CN_D";

	public int countByTC_TC_TN_CN(
			long companyId, long classNameId, String tableName,
			String columnName)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_TC_TC_TN_CN);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(columnName);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByTC_TC_TN_C(
			long companyId, long classNameId, String tableName, long classPK)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_TC_TC_TN_C);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(classPK);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByTC_TC_TN_CN_D(
			long companyId, long classNameId, String tableName,
			String columnName, String data)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_TC_TC_TN_CN_D);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(columnName);
			qPos.add(data);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByTC_TC_TN_CN(
			long companyId, long classNameId, String tableName,
			String columnName, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TC_TC_TN_CN);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ExpandoValue", ExpandoValueImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(columnName);

			return (List<ExpandoValue>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByTC_TC_TN_C(
			long companyId, long classNameId, String tableName, long classPK,
			int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TC_TC_TN_C);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ExpandoValue", ExpandoValueImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(classPK);

			return (List<ExpandoValue>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue fetchByTC_TC_TN_CN_C(
			long companyId, long classNameId, String tableName,
			String columnName, long classPK)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TC_TC_TN_CN_C);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ExpandoValue", ExpandoValueImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(columnName);
			qPos.add(classPK);

			return (ExpandoValue)q.uniqueResult();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByTC_TC_TN_CN_D(
			long companyId, long classNameId, String tableName,
			String columnName, String data, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_TC_TC_TN_CN_D);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ExpandoValue", ExpandoValueImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(classNameId);
			qPos.add(tableName);
			qPos.add(columnName);
			qPos.add(data);

			return (List<ExpandoValue>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}