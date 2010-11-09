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

package com.liferay.portal.upgrade.v4_4_0.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TagsAssetGroupIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public TagsAssetGroupIdUpgradeColumnImpl(
		UpgradeColumn classNameIdColumn, UpgradeColumn classPKColumn) {

		super("groupId");

		_classNameIdColumn = classNameIdColumn;
		_classPKColumn = classPKColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Long oldGroupId = (Long)oldValue;

		Long newGroupId = oldGroupId;

		if (oldGroupId.longValue() == 0) {
			Long classNameId = (Long)_classNameIdColumn.getOldValue();
			Long classPK = (Long)_classPKColumn.getOldValue();

			String className = PortalUtil.getClassName(classNameId.longValue());

			try {
				newGroupId = new Long(
					getGroupId(className, classPK.longValue()));
			}
			catch (PortalException pe) {
				throw new StagnantRowException(pe.getMessage(), pe);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return newGroupId;
	}

	protected long getGroupId(String className, long classPK) throws Exception {
		if (className.equals(BlogsEntry.class.getName())) {
			BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

			return entry.getGroupId();
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(
				classPK);

			return entry.getFolder().getGroupId();
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			DLFileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				classPK);

			return fileEntry.getFolder().getGroupId();
		}
		else if (className.equals(IGImage.class.getName())) {
			IGImage image = IGImageLocalServiceUtil.getImage(classPK);

			return image.getFolder().getGroupId();
		}
		else if (className.equals(JournalArticle.class.getName())) {
			JournalArticleResource articleResource =
				JournalArticleResourceLocalServiceUtil.getArticleResource(
					classPK);

			return articleResource.getGroupId();
		}
		else if (className.equals(MBMessage.class.getName())) {
			MBMessage message = MBMessageLocalServiceUtil.getMessage(classPK);

			return message.getCategory().getGroupId();
		}
		else if (className.equals(WikiPage.class.getName())) {
			WikiPageResource pageResource =
				WikiPageResourceLocalServiceUtil.getPageResource(classPK);

			WikiNode node = WikiNodeLocalServiceUtil.getNode(
				pageResource.getNodeId());

			return node.getGroupId();
		}
		else {
			return 0;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		TagsAssetGroupIdUpgradeColumnImpl.class);

	private UpgradeColumn _classNameIdColumn;
	private UpgradeColumn _classPKColumn;

}