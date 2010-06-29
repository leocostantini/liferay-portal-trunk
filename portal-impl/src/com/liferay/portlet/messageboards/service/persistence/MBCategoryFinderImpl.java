/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.NoSuchSubscriptionException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBCategoryFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class MBCategoryFinderImpl
	extends BasePersistenceImpl<MBCategory> implements MBCategoryFinder {

	public static String COUNT_BY_S_G_U =
		MBCategoryFinder.class.getName() + ".countByS_G_U";

	public static String FIND_BY_S_G_U =
		MBCategoryFinder.class.getName() + ".findByS_G_U";

	public int countByS_G_U(long groupId, long userId) throws SystemException {
		return doCountByS_G_U(groupId, userId, null, false);
	}

	public int filterCountByS_G_U(long groupId, long userId)
		throws SystemException {

		return doCountByS_G_U(groupId, userId, null, true);
	}

	public int filterCountByS_G_U(
			long groupId, long userId, long[] parentCategoryId)
		throws SystemException {

		return doCountByS_G_U(groupId, userId, parentCategoryId, true);
	}

	public List<MBCategory> filterFindByS_G_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return doFindByS_G_U(groupId, userId, null, start, end, true);
	}

	public List<MBCategory> filterFindByS_G_U(
			long groupId, long userId, long[] parentCategoryId, int start,
			int end)
		throws SystemException {

		return doFindByS_G_U(
			groupId, userId, parentCategoryId, start, end, true);
	}

	public List<MBCategory> findByS_G_U(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return doFindByS_G_U(groupId, userId, null, start, end, false);
	}

	protected int doCountByS_G_U(
			long groupId, long userId, long[] parentCategoryIds,
			boolean inlineSQLHelper)
		throws SystemException {

		if ((parentCategoryIds != null) && (parentCategoryIds.length == 0)) {
			return 0;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_S_G_U);

			if (parentCategoryIds == null) {
				sql = StringUtil.replace(
					sql, "(MBCategory.parentCategoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				StringBundler sb = new StringBundler(2);

				sb.append("MBCategory.parentCategoryId = ");
				sb.append(StringUtil.merge(
					parentCategoryIds, " OR MBCategory.parentCategoryId = "));

				sql = StringUtil.replace(
					sql, "MBCategory.parentCategoryId = ?", sb.toString());
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBCategory.class.getName(), "MBCategory.categoryId",
					"MBCategory.userId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBCategory.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			int count = 0;

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count = l.intValue();
				}
			}

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				SubscriptionLocalServiceUtil.getSubscription(
					group.getCompanyId(), userId, MBCategory.class.getName(),
					groupId);

				count++;
			}
			catch (NoSuchSubscriptionException nsse) {
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<MBCategory> doFindByS_G_U(
			long groupId, long userId, long[] parentCategoryIds, int start,
			int end, boolean inlineSQLHelper)
		throws SystemException {

		if ((parentCategoryIds != null) && (parentCategoryIds.length == 0)) {
			return new ArrayList<MBCategory>();
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_S_G_U);

			if (parentCategoryIds == null) {
				sql = StringUtil.replace(
					sql, "(MBCategory.parentCategoryId = ?) AND",
					StringPool.BLANK);
			}
			else {
				StringBundler sb = new StringBundler(2);

				sb.append("MBCategory.parentCategoryId = ");
				sb.append(StringUtil.merge(
					parentCategoryIds, " OR MBCategory.parentCategoryId = "));

				sql = StringUtil.replace(
					sql, "MBCategory.parentCategoryId = ?", sb.toString());
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, MBCategory.class.getName(), "MBCategory.categoryId",
					"MBCategory.userId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("MBCategory", MBCategoryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(MBCategory.class.getName()));
			qPos.add(groupId);
			qPos.add(userId);

			List<MBCategory> list = (List<MBCategory>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, false);

			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				SubscriptionLocalServiceUtil.getSubscription(
					group.getCompanyId(), userId, MBCategory.class.getName(),
					groupId);

				int threadCount =
					MBThreadLocalServiceUtil.getCategoryThreadsCount(
						groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
						WorkflowConstants.STATUS_APPROVED);
				int messageCount =
					MBMessageLocalServiceUtil.getCategoryMessagesCount(
						groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
						WorkflowConstants.STATUS_APPROVED);

				MBCategory category = new MBCategoryImpl();

				category.setCompanyId(group.getCompanyId());
				category.setName(group.getName());
				category.setDescription(group.getDescription());
				category.setThreadCount(threadCount);
				category.setMessageCount(messageCount);

				list.add(category);
			}
			catch (NoSuchSubscriptionException nsse) {
			}

			return new UnmodifiableList<MBCategory>(
				ListUtil.subList(list, start, end));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}