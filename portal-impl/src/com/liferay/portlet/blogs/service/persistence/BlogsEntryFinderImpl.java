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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="BlogsEntryFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsEntryFinderImpl implements BlogsEntryFinder {

	public static String COUNT_BY_ORGANIZATION_IDS =
		BlogsEntryFinder.class.getName() + ".countByOrganizationIds";

	public static String FIND_BY_ORGANIZATION_IDS =
		BlogsEntryFinder.class.getName() + ".findByOrganizationIds";

	public static String FIND_BY_NO_ASSETS =
		BlogsEntryFinder.class.getName() + ".findByNoAssets";

	public int countByOrganizationId(long organizationId, boolean draft)
		throws SystemException {

		List<Long> organizationIds = new ArrayList<Long>();

		organizationIds.add(organizationId);

		return countByOrganizationIds(organizationIds, draft);
	}

	public int countByOrganizationIds(
			List<Long> organizationIds, boolean draft)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_ORGANIZATION_IDS);

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < organizationIds.size(); i++) {
				Long organizationId = organizationIds.get(i);

				qPos.add(organizationId);
			}

			qPos.add(draft);

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
			HibernateUtil.closeSession(session);
		}
	}

	public List<BlogsEntry> findByOrganizationId(
			long organizationId, boolean draft, int start, int end)
		throws SystemException {

		List<Long> organizationIds = new ArrayList<Long>();

		organizationIds.add(organizationId);

		return findByOrganizationIds(organizationIds, draft, start, end);
	}

	public List<BlogsEntry> findByOrganizationIds(
			List<Long> organizationIds, boolean draft, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ORGANIZATION_IDS);

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			for (int i = 0; i < organizationIds.size(); i++) {
				Long organizationId = organizationIds.get(i);

				qPos.add(organizationId);
			}

			qPos.add(draft);

			return (List<BlogsEntry>)QueryUtil.list(
				q, HibernateUtil.getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public List<BlogsEntry> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("BlogsEntry", BlogsEntryImpl.class);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	protected String getOrganizationIds(List<Long> organizationIds) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < organizationIds.size(); i++) {
			sb.append("Users_Orgs.organizationId = ? ");

			if ((i + 1) != organizationIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}