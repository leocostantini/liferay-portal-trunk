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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="DLDisplayPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 */
public class DLDisplayPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			preferences.setValue("rootFolderId", StringPool.BLANK);
			preferences.setValue("showBreadcrumbs", StringPool.BLANK);
			preferences.setValue("showFoldersSearch", StringPool.BLANK);
			preferences.setValue("showSubfolders", StringPool.BLANK);
			preferences.setValue("foldersPerPage", StringPool.BLANK);
			preferences.setValue("folderColumns", StringPool.BLANK);
			preferences.setValue("showFileEntriesSearch", StringPool.BLANK);
			preferences.setValue("fileEntriesPerPage", StringPool.BLANK);
			preferences.setValue("fileEntryColumns", StringPool.BLANK);
			preferences.setValue("enable-comment-ratings", StringPool.BLANK);

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			context.addPermissions(
				"com.liferay.portlet.documentlibrary", context.getGroupId());

			long rootFolderId = GetterUtil.getLong(
				preferences.getValue("rootFolderId", null));

			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("documentlibrary-display-data");

			Element foldersEl = root.addElement("folders");
			Element fileEntriesEl = root.addElement("file-entries");
			Element fileShortcutsEl = root.addElement("file-shortcuts");
			Element fileRanksEl = root.addElement("file-ranks");

			if (rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				List<DLFolder> folders = DLFolderUtil.findByGroupId(
					context.getGroupId());

				for (DLFolder folder : folders) {
					DLPortletDataHandlerImpl.exportFolder(
						context, foldersEl, fileEntriesEl, fileShortcutsEl,
						fileRanksEl, folder);
				}
			}
			else {
				DLFolder folder = DLFolderUtil.findByPrimaryKey(rootFolderId);

				root.addAttribute(
					"root-folder-id", String.valueOf(folder.getFolderId()));

				DLPortletDataHandlerImpl.exportFolder(
					context, foldersEl, fileEntriesEl, fileShortcutsEl,
					fileRanksEl, folder);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _comments, _ratings, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			context.importPermissions(
				"com.liferay.portlet.documentlibrary",
				context.getSourceGroupId(), context.getGroupId());

			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> folderEls = root.element("folders").elements(
				"folder");

			for (Element folderEl : folderEls) {
				DLPortletDataHandlerImpl.importFolder(context, folderEl);
			}

			List<Element> fileEntryEls = root.element("file-entries").elements(
				"file-entry");

			for (Element fileEntryEl : fileEntryEls) {
				DLPortletDataHandlerImpl.importFileEntry(context, fileEntryEl);
			}

			if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
				List<Element> fileShortcutEls = root.element(
					"file-shortcuts").elements("file-shortcut");

				for (Element fileShortcutEl : fileShortcutEls) {
					DLPortletDataHandlerImpl.importFileShortcut(
						context, fileShortcutEl);
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
				List<Element> fileRankEls = root.element("file-ranks").elements(
					"file-rank");

				for (Element fileRankEl : fileRankEls) {
					DLPortletDataHandlerImpl.importFileRank(
						context, fileRankEl);
				}
			}

			Map<Long, Long> folderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

			long rootFolderId = GetterUtil.getLong(
				root.attributeValue("root-folder-id"));

			if (Validator.isNotNull(rootFolderId)) {
				rootFolderId = MapUtil.getLong(
					folderPKs, rootFolderId, rootFolderId);

				preferences.setValue(
					"rootFolderId", String.valueOf(rootFolderId));
			}

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	private static final String _NAMESPACE = "document_library";

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}