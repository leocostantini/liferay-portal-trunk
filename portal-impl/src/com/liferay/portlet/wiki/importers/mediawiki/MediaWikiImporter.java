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

package com.liferay.portlet.wiki.importers.mediawiki;

import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.ProgressTrackerThreadLocal;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;
import com.liferay.portlet.tags.service.TagsPropertyLocalServiceUtil;
import com.liferay.portlet.tags.util.TagsUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.importers.WikiImporter;
import com.liferay.portlet.wiki.importers.WikiImporterKeys;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.MediaWikiToCreoleTranslator;
import com.liferay.util.MapUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="MediaWikiImporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 *
 */
public class MediaWikiImporter implements WikiImporter {

	public static final String SHARED_IMAGES_CONTENT = "See attachments";

	public static final String SHARED_IMAGES_TITLE = "SharedImages";

	public void importPages(
			long userId, WikiNode node, File[] files,
			Map<String, String[]> options)
		throws PortalException {

		if ((files.length < 1) || (files[0] == null) || (!files[0].exists())) {
			throw new PortalException("The pages file is mandatory");
		}

		File pagesFile = files[0];
		File usersFile = files[1];
		File imagesFile = files[2];

		try {
			SAXReader saxReader = new SAXReader();

			Document doc = saxReader.read(pagesFile);

			Map<String, String> usersMap = readUsersFile(usersFile);

			Element root = doc.getRootElement();

			List<String> specialNamespaces = readSpecialNamespaces(root);

			processSpecialPages(userId, node, root, specialNamespaces);
			processRegularPages(
				userId, node, root, specialNamespaces, usersMap, imagesFile,
				options);
			processImages(userId, node, imagesFile);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected long getUserId(
			long userId, WikiNode node, String author,
			Map<String, String> usersMap)
		throws PortalException, SystemException {

		User user = null;

		String emailAddress = usersMap.get(author);

		try {
			if (Validator.isNull(emailAddress)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					node.getCompanyId(), author.toLowerCase());
			}
			else {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					node.getCompanyId(), emailAddress);
			}
		}
		catch (NoSuchUserException nsue) {
			user = UserLocalServiceUtil.getUserById(userId);
		}

		return user.getUserId();
	}

	protected void importPage(
			long userId, String author, WikiNode node, String title,
			String content, String summary, Map<String, String> usersMap)
		throws PortalException {

		try {
			long authorUserId = getUserId(userId, node, author, usersMap);

			String[] tagsEntries = readTagsEntries(userId, node, content);

			String redirectTitle = readRedirectTitle(content);

			if (Validator.isNull(redirectTitle)) {
				content = _translator.translate(content);
			}
			else {
				content =
					StringPool.DOUBLE_OPEN_BRACKET + redirectTitle +
						StringPool.DOUBLE_CLOSE_BRACKET;
			}

			WikiPage page = null;

			try {
				page = WikiPageLocalServiceUtil.getPage(
					node.getNodeId(), title);
			}
			catch (NoSuchPageException nspe) {
				page = WikiPageLocalServiceUtil.addPage(
					authorUserId, node.getNodeId(), title, WikiPageImpl.NEW,
					null, true, null, null);
			}

			WikiPageLocalServiceUtil.updatePage(
				authorUserId, node.getNodeId(), title, page.getVersion(),
				content, summary, true, "creole", StringPool.BLANK,
				redirectTitle, tagsEntries, null, null);
		}
		catch (Exception e) {
			throw new PortalException("Error importing page " + title, e);
		}
	}

	protected boolean isSpecialMediaWikiPage(
		String title, List<String> specialNamespaces) {

		for (String namespace: specialNamespaces) {
			if (title.startsWith(namespace + StringPool.COLON)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidImage(String[] paths, byte[] bytes) {
		if (ArrayUtil.contains(_SPECIAL_MEDIA_WIKI_DIRS, paths[0])) {
			return false;
		}

		if ((paths.length > 1) &&
			(ArrayUtil.contains(_SPECIAL_MEDIA_WIKI_DIRS, paths[1]))) {

			return false;
		}

		String fileName = paths[paths.length - 1];

		try {
			DLLocalServiceUtil.validate(fileName, bytes);
		}
		catch (PortalException pe) {
			return false;
		}

		return true;
	}

	protected String normalize(String categoryName, int length) {
		categoryName = TagsUtil.toWord(categoryName.trim());

		return StringUtil.shorten(categoryName, length);
	}

	protected String normalizeDescription(String description) {
		description = description.replaceAll(
			_CATEGORIES_REGEXP, StringPool.BLANK);

		return normalize(description, 300);
	}

	protected String normalizeTitle(String title) {
		title = title.replaceAll(
			PropsValues.WIKI_PAGE_TITLES_REMOVE_REGEXP, StringPool.BLANK);

		return StringUtil.shorten(title, 75);
	}

	private void processImages(long userId, WikiNode node, File imagesFile)
		throws Exception {

		if ((imagesFile == null) || (!imagesFile.exists())) {
			return;
		}

		ProgressTracker progressTracker =
			ProgressTrackerThreadLocal.getProgressTracker();

		int count = 0;

		ZipReader zipReader = new ZipReader(imagesFile);

		Map<String, byte[]> entries = zipReader.getEntries();

		int total = entries.size();

		if (total > 0) {
			try {
				WikiPageLocalServiceUtil.getPage(
					node.getNodeId(), SHARED_IMAGES_TITLE);
			}
			catch (NoSuchPageException nspe) {
				WikiPageLocalServiceUtil.addPage(
					userId, node.getNodeId(), SHARED_IMAGES_TITLE,
					SHARED_IMAGES_CONTENT, null, true, null, null);
			}
		}

		List<ObjectValuePair<String, byte[]>> attachments =
			new ArrayList<ObjectValuePair<String, byte[]>>();

		Iterator<Map.Entry<String, byte[]>> itr = entries.entrySet().iterator();

		int percentage = 50;

		for (int i = 0; itr.hasNext(); i++) {
			Map.Entry<String, byte[]> entry = itr.next();

			String key = entry.getKey();
			byte[] value = entry.getValue();

			if (key.endsWith(StringPool.SLASH)) {
				if (_log.isInfoEnabled()) {
					_log.info("Ignoring " + key);
				}

				continue;
			}

			String[] paths = StringUtil.split(key, StringPool.SLASH);

			if (!isValidImage(paths, value)) {
				if (_log.isInfoEnabled()) {
					_log.info("Ignoring " + key);
				}

				continue;
			}

			String fileName = paths[paths.length - 1];

			attachments.add(
				new ObjectValuePair<String, byte[]>(fileName, value));

			count++;

			if ((i % 5) == 0) {
				WikiPageLocalServiceUtil.addPageAttachments(
					node.getNodeId(), SHARED_IMAGES_TITLE, attachments);

				attachments.clear();

				percentage = Math.min(50 + (i * 50) / total, 99);

				progressTracker.updateProgress(percentage);
			}
		}

		if (!attachments.isEmpty()) {
			WikiPageLocalServiceUtil.addPageAttachments(
				node.getNodeId(), SHARED_IMAGES_TITLE, attachments);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Imported " + count + " images into " + node.getName());
		}
	}

	protected void processRegularPages(
		long userId, WikiNode node, Element root,
		List<String> specialNamespaces, Map usersMap,
		File imagesFile, Map<String, String[]> options) {

		boolean importLatestVersion = MapUtil.getBoolean(
			options, WikiImporterKeys.OPTIONS_IMPORT_LATEST_VERSION);

		ProgressTracker progressTracker =
			ProgressTrackerThreadLocal.getProgressTracker();

		int count = 0;

		List<Element> pages = root.elements("page");

		int total = pages.size();

		Iterator<Element> itr = root.elements("page").iterator();

		int percentage = 10;
		int maxPercentage = 50;

		if ((imagesFile == null) || (!imagesFile.exists())) {
			maxPercentage = 99;
		}

		int percentageRange = maxPercentage - percentage;

		for (int i = 0; itr.hasNext(); i++) {
			Element pageEl = itr.next();

			String title = pageEl.elementText("title");

			title = normalizeTitle(title);

			percentage = Math.min(
				10 + (i * percentageRange) / total, maxPercentage);

			progressTracker.updateProgress(percentage);

			if (isSpecialMediaWikiPage(title, specialNamespaces)) {
				continue;
			}

			List<Element> revisionEls = pageEl.elements("revision");

			if (importLatestVersion) {
				Element lastRevisionEl = revisionEls.get(
					revisionEls.size() - 1);

				revisionEls = new ArrayList<Element>();

				revisionEls.add(lastRevisionEl);
			}

			for (Element curRevisionEl : revisionEls) {
				String author = curRevisionEl.element(
					"contributor").elementText("username");
				String content = curRevisionEl.elementText("text");
				String summary = curRevisionEl.elementText("comment");

				try {
					importPage(
						userId, author, node, title, content, summary,
						usersMap);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						StringMaker sm = new StringMaker();

						sm.append("Page with title ");
						sm.append(title);
						sm.append(" could not be imported");

						_log.warn(sm.toString(), e);
					}
				}
			}

			count++;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Imported " + count + " pages into " + node.getName());
		}
	}

	protected void processSpecialPages(
			long userId, WikiNode node, Element root,
			List<String> specialNamespaces)
		throws PortalException {

		ProgressTracker progressTracker =
			ProgressTrackerThreadLocal.getProgressTracker();

		List<Element> pages = root.elements("page");

		int total = pages.size();

		Iterator<Element> itr = pages.iterator();

		for (int i = 0; itr.hasNext(); i++) {
			Element page = itr.next();

			String title = page.elementText("title");

			if (!title.startsWith("Category:")) {
				if (isSpecialMediaWikiPage(title, specialNamespaces)) {
					root.remove(page);
				}

				continue;
			}

			String categoryName = title.substring("Category:".length());

			categoryName = normalize(categoryName, 75);

			String description = page.element("revision").elementText("text");

			description = normalizeDescription(description);

			try {
				TagsEntry tagsEntry = null;

				try {
					tagsEntry = TagsEntryLocalServiceUtil.getEntry(
						node.getCompanyId(), categoryName);
				}
				catch (NoSuchEntryException nsee) {
					tagsEntry = TagsEntryLocalServiceUtil.addEntry(
						userId, categoryName);
				}

				if (Validator.isNotNull(description)) {
					TagsPropertyLocalServiceUtil.addProperty(
						userId, tagsEntry.getEntryId(), "description",
						description);
				}
			}
			catch (SystemException se) {
				 _log.error(se, se);
			}

			if ((i % 5) == 0) {
				progressTracker.updateProgress((i * 10) / total);
			}
		}
	}

	protected String readRedirectTitle(String content) {
		Matcher matcher = _REDIRECT_REGEXP_PATTERN.matcher(content);

		String redirectTitle = StringPool.BLANK;

		if (matcher.find()) {
			redirectTitle = matcher.group(1);

			redirectTitle = normalizeTitle(redirectTitle);
		}

		return redirectTitle;
	}

	protected List<String> readSpecialNamespaces(Element root) {
		List<String> namespaces = new ArrayList<String>();

		Iterator<Element> itr = root.element("siteinfo").element(
			"namespaces").elements("namespace").iterator();

		while (itr.hasNext()) {
			Element namespace = itr.next();

			if (!namespace.attribute("key").equals("0")) {
				namespaces.add(namespace.getText());
			}
		}

		return namespaces;
	}

	protected String[] readTagsEntries(
			long userId, WikiNode node, String content)
		throws PortalException, SystemException {

		Matcher matcher = _CATEGORIES_REGEXP_PATTERN.matcher(content);

		List<String> tagsEntries = new ArrayList<String>();

		while (matcher.find()) {
			String categoryName = matcher.group(1);

			categoryName = normalize(categoryName, 75);

			TagsEntry tagsEntry = null;

			try {
				tagsEntry = TagsEntryLocalServiceUtil.getEntry(
					node.getCompanyId(), categoryName);
			}
			catch (NoSuchEntryException nsee) {
				tagsEntry = TagsEntryLocalServiceUtil.addEntry(
					userId, categoryName);
			}

			tagsEntries.add(tagsEntry.getName());
		}

		return tagsEntries.toArray(new String[tagsEntries.size()]);
	}

	protected Map<String, String> readUsersFile(File usersFile)
		throws IOException {

		if ((usersFile == null) || (!usersFile.exists())) {
			return Collections.EMPTY_MAP;
		}

		Map<String, String> usersMap = new HashMap<String, String>();

		BufferedReader reader = new BufferedReader(new FileReader(usersFile));

		String line = reader.readLine();

		while (line != null) {
			String[] array = StringUtil.split(line);

			if ((array.length == 2) && (Validator.isNotNull(array[0])) &&
				(Validator.isNotNull(array[1]))) {

				usersMap.put(array[0], array[1]);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Ignoring line " + line +
							" because it does not contain exactly 2 columns");
				}
			}

			line = reader.readLine();
		}

		return usersMap;
	}

	private static final String _CATEGORIES_REGEXP =
		"\\[\\[[Cc]ategory:([^\\]]*)\\]\\][\\n]*";

	private static final Pattern _CATEGORIES_REGEXP_PATTERN = Pattern.compile(
		_CATEGORIES_REGEXP);

	private static final String _REDIRECT_REGEXP =
		"#REDIRECT \\[\\[([^\\]]*)\\]\\]";

	private static final Pattern _REDIRECT_REGEXP_PATTERN = Pattern.compile(
		_REDIRECT_REGEXP);

	private static final String[] _SPECIAL_MEDIA_WIKI_DIRS = new String[]{
		"thumb", "temp", "archive"};

	private static Log _log = LogFactory.getLog(MediaWikiImporter.class);

	private MediaWikiToCreoleTranslator _translator =
		new MediaWikiToCreoleTranslator();

}