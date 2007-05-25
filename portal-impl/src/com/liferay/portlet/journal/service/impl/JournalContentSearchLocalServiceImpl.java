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

package com.liferay.portlet.journal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.base.JournalContentSearchLocalServiceBaseImpl;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="JournalContentSearchLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalContentSearchLocalServiceImpl
	extends JournalContentSearchLocalServiceBaseImpl {

	public void checkContentSearches(long companyId)
		throws PortalException, SystemException {

		List layouts = new ArrayList();

		List groups = GroupLocalServiceUtil.search(
			companyId, null, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (int i = 0; i < groups.size(); i++) {
			Group group = (Group)groups.get(i);

			// Private layouts

			deleteOwnerContentSearches(group.getGroupId(), true);

			layouts.addAll(
				LayoutLocalServiceUtil.getLayouts(group.getGroupId(), true));

			// Public layouts

			deleteOwnerContentSearches(group.getGroupId(), false);

			layouts.addAll(
				LayoutLocalServiceUtil.getLayouts(group.getGroupId(), false));
		}

		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = (Layout)layouts.get(i);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List portletIds = layoutTypePortlet.getPortletIds();

			for (int j = 0; j < portletIds.size(); j++) {
				String portletId = (String)portletIds.get(j);

				String rootPortletId = PortletImpl.getRootPortletId(portletId);

				if (rootPortletId.equals(PortletKeys.JOURNAL_CONTENT)) {
					PortletPreferences prefs =
						PortletPreferencesLocalServiceUtil.getPreferences(
							layout.getCompanyId(),
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							layout.getPlid(), portletId);

					String articleId = prefs.getValue(
						"article-id", StringPool.BLANK);

					if (Validator.isNotNull(articleId)) {
						updateContentSearch(
							layout.getCompanyId(), layout.getGroupId(),
							layout.isPrivateLayout(), layout.getLayoutId(),
							portletId, articleId);
					}
				}
			}
		}
	}

	public void deleteArticleContentSearches(
			long companyId, long groupId, String articleId)
		throws SystemException {

		JournalContentSearchUtil.removeByC_G_A(companyId, groupId, articleId);
	}

	public void deleteLayoutContentSearches(
			long groupId, boolean privateLayout, long layoutId)
		throws SystemException {

		JournalContentSearchUtil.removeByG_P_L(groupId, privateLayout, layoutId);
	}

	public void deleteOwnerContentSearches(long groupId, boolean privateLayout)
		throws SystemException {

		JournalContentSearchUtil.removeByG_P(groupId, privateLayout);
	}

	public List getArticleContentSearches() throws SystemException {
		return JournalContentSearchUtil.findAll();
	}

	public List getArticleContentSearches(
			long companyId, long groupId, String articleId)
		throws SystemException {

		return JournalContentSearchUtil.findByC_G_A(
			companyId, groupId, articleId);
	}

	public List getLayoutIds(
			long groupId, boolean privateLayout, String articleId)
		throws SystemException {

		List layoutIds = new ArrayList();

		Iterator itr = JournalContentSearchUtil.findByG_P_A(
			groupId, privateLayout, articleId).iterator();

		while (itr.hasNext()) {
			JournalContentSearch contentSearch =
				(JournalContentSearch)itr.next();

			layoutIds.add(new Long(contentSearch.getLayoutId()));
		}

		return layoutIds;
	}

	public int getLayoutIdsCount(
			long groupId, boolean privateLayout, String articleId)
		throws SystemException {

		return JournalContentSearchUtil.countByG_P_A(
			groupId, privateLayout, articleId);
	}

	public JournalContentSearch updateContentSearch(
			long companyId, long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws PortalException, SystemException {
		
		JournalContentSearch contentSearch = null;
		
		try {
			contentSearch = JournalContentSearchUtil.findByG_P_L_P_A(
						groupId, privateLayout, layoutId, portletId, articleId);
		}
		catch (NoSuchContentSearchException e) {
		}

		if (contentSearch == null) {
			long contentSearchId = CounterLocalServiceUtil.increment();

			contentSearch = JournalContentSearchUtil.create(contentSearchId);

			contentSearch.setCompanyId(companyId);
			contentSearch.setGroupId(groupId);
			contentSearch.setPrivateLayout(privateLayout);
			contentSearch.setLayoutId(layoutId);
			contentSearch.setPortletId(portletId);
			contentSearch.setArticleId(articleId);
		}

		JournalContentSearchUtil.update(contentSearch);

		return contentSearch;
	}

	public List updateContentSearch(
			long companyId, long groupId, boolean privateLayout, long layoutId,
			String portletId, String[] articleIds)
		throws PortalException, SystemException {

		List contentSearches = new ArrayList();

		for (int i = 0; i < articleIds.length; i++) {
			JournalContentSearch contentSearch = updateContentSearch(
				companyId, groupId, privateLayout, layoutId, portletId,
				articleIds[i]);

			contentSearches.add(contentSearch);
		}

		return contentSearches;
	}

}