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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsCategoryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.base.BlogsEntryServiceBaseImpl;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.util.Html;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsEntryServiceImpl extends BlogsEntryServiceBaseImpl {

	public BlogsEntry addEntry(
			long plid, long categoryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			ThemeDisplay themeDisplay, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.BLOGS,
			ActionKeys.ADD_ENTRY);

		return BlogsEntryLocalServiceUtil.addEntry(
			getUserId(), plid, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			themeDisplay, tagsEntries, addCommunityPermissions,
			addGuestPermissions);
	}

	public BlogsEntry addEntry(
			long plid, long categoryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			ThemeDisplay themeDisplay, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.BLOGS,
			ActionKeys.ADD_ENTRY);

		return BlogsEntryLocalServiceUtil.addEntry(
			getUserId(), plid, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			themeDisplay, tagsEntries, communityPermissions, guestPermissions);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		BlogsEntryLocalServiceUtil.deleteEntry(entryId);
	}

	public String getCategoryBlogsRSS(
			long categoryId, int max, String type, double version,
			String feedURL, String entryURL)
		throws PortalException, SystemException {

		BlogsCategory category = BlogsCategoryLocalServiceUtil.getCategory(
			categoryId);

		String name = category.getName();
		String description = category.getDescription();

		List blogsEntries = BlogsEntryLocalServiceUtil.getEntries(
			categoryId, 0, max);

		Iterator itr = blogsEntries.iterator();

		while (itr.hasNext()) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			if (!BlogsEntryPermission.contains(
					getPermissionChecker(), entry, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return exportToRSS(
			name, description, type, version, feedURL, entryURL, blogsEntries);
	}

	public List getOrganizationEntries(long organizationId, int max)
		throws PortalException, SystemException {

		List entries = new ArrayList();

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		Iterator itr = BlogsEntryLocalServiceUtil.getCompanyEntries(
			organization.getCompanyId(), 0, _MAX_END).iterator();

		while (itr.hasNext() && (entries.size() < max)) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			if (OrganizationLocalServiceUtil.hasUserOrganization(
					entry.getUserId(), organizationId) &&
			   (BlogsEntryPermission.contains(
					getPermissionChecker(), entry, ActionKeys.VIEW))) {

				entries.add(entry);
			}
		}

		return entries;
	}

	public String getOrganizationEntriesRSS(
			long organizationId, int max, String type, double version,
			String feedURL, String entryURL)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		Company company = CompanyUtil.findByPrimaryKey(
			organization.getCompanyId());

		String name = company.getName();

		List blogsEntries = getOrganizationEntries(organizationId, max);

		return exportToRSS(
			name, null, type, version, feedURL, entryURL, blogsEntries);
	}

	public List getCompanyEntries(long companyId, int max)
		throws PortalException, SystemException {

		List entries = new ArrayList();

		Iterator itr = BlogsEntryLocalServiceUtil.getCompanyEntries(
			companyId, 0, _MAX_END).iterator();

		while (itr.hasNext() && (entries.size() < max)) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			if (BlogsEntryPermission.contains(
					getPermissionChecker(), entry, ActionKeys.VIEW)) {

				entries.add(entry);
			}
		}

		return entries;
	}

	public String getCompanyEntriesRSS(
			long companyId, int max, String type, double version,
			String feedURL, String entryURL)
		throws PortalException, SystemException {

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		String name = company.getName();

		List blogsEntries = getCompanyEntries(companyId, max);

		return exportToRSS(
			name, null, type, version, feedURL, entryURL, blogsEntries);
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return BlogsEntryLocalServiceUtil.getEntry(entryId);
	}

	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(
			groupId, urlTitle);

		BlogsEntryPermission.check(
			getPermissionChecker(), entry.getEntryId(), ActionKeys.VIEW);

		return entry;
	}

	public List getGroupEntries(long groupId, int max)
		throws PortalException, SystemException {

		List entries = new ArrayList();

		Iterator itr = BlogsEntryLocalServiceUtil.getGroupEntries(
			groupId, 0, _MAX_END).iterator();

		while (itr.hasNext() && (entries.size() < max)) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			if (BlogsEntryPermission.contains(
					getPermissionChecker(), entry, ActionKeys.VIEW)) {

				entries.add(entry);
			}
		}

		return entries;
	}

	public String getGroupEntriesRSS(
			long groupId, int max, String type, double version,
			String feedURL, String entryURL)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		String name = group.getDescriptiveName();

		List blogsEntries = getGroupEntries(groupId, max);

		return exportToRSS(
			name, null, type, version, feedURL, entryURL, blogsEntries);
	}

	public BlogsEntry updateEntry(
			long entryId, long categoryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			ThemeDisplay themeDisplay, String[] tagsEntries)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return BlogsEntryLocalServiceUtil.updateEntry(
			getUserId(), entryId, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, themeDisplay, tagsEntries);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String feedURL, String entryURL, List blogsEntries)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(type + "_" + version);

		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(GetterUtil.getString(description, name));

		List entries = new ArrayList();

		syndFeed.setEntries(entries);

		Iterator itr = blogsEntries.iterator();

		while (itr.hasNext()) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			String link = entryURL;

			if (link.endsWith("/blogs/rss")) {
				link =
					link.substring(0, link.length() - 3) + entry.getUrlTitle();
			}
			else {
				if (!link.endsWith("?")) {
					link += "&";
				}

				link += "entryId=" + entry.getEntryId();
			}

			String firstLine = StringUtil.shorten(
				Html.stripHtml(entry.getContent()), 200, StringPool.BLANK);

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(entry.getUserName());
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setLink(link);
			syndEntry.setPublishedDate(entry.getCreateDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(ContentTypes.TEXT_PLAIN);
			syndContent.setValue(firstLine);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final int _MAX_END = 200;

}