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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.MapUtil;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="JournalPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior
 * to import.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * @author Raymond Aug�
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 * @see com.liferay.portal.kernel.lar.PortletDataHandler
 * @see com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see com.liferay.portlet.journal.lar.JournalCreationStrategy
 *
 */
public class JournalPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					JournalPortletDataHandlerImpl.class, "deleteData")) {

				List<JournalArticle> articles =
					JournalArticleUtil.findByGroupId(
						context.getGroupId());

				for (JournalArticle article : articles) {

					// Templates

					JournalTemplateLocalServiceUtil.deleteTemplate(
						context.getGroupId(), article.getTemplateId());

					// Structures

					JournalStructureLocalServiceUtil.deleteStructure(
						context.getGroupId(), article.getStructureId());
				}

				// Articles

				JournalArticleLocalServiceUtil.deleteArticles(
					context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("journal-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Structures

			List<JournalStructure> structures =
				JournalStructureUtil.findByGroupId(
					context.getGroupId());

			Iterator<JournalStructure> structuresItr = structures.iterator();

			while (structuresItr.hasNext()) {
				JournalStructure structure = structuresItr.next();

				if (context.addPrimaryKey(
						JournalStructure.class, structure.getPrimaryKeyObj())) {

					structuresItr.remove();
				}
				else {
					exportStructure(structure);
				}
			}

			String xml = xStream.toXML(structures);

			Document tempDoc = PortalUtil.readDocumentFromXML(xml);

			Element el = root.addElement("journal-structures");

			el.content().add(tempDoc.getRootElement().createCopy());

			// Templates

			List<JournalTemplate> templates =
				JournalTemplateUtil.findByGroupId(context.getGroupId());

			Iterator<JournalTemplate> templatesItr = templates.iterator();

			while (templatesItr.hasNext()) {
				JournalTemplate template = templatesItr.next();

				if (context.addPrimaryKey(
						JournalTemplate.class, template.getPrimaryKeyObj())) {

					templatesItr.remove();
				}
				else {
					exportTemplate(context, template);
				}
			}

			xml = xStream.toXML(templates);

			el = root.addElement("journal-templates");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Articles

			List<JournalArticle> articles = JournalArticleUtil.findByGroupId(
				context.getGroupId());

			Iterator<JournalArticle> articlesItr = articles.iterator();

			while (articlesItr.hasNext()) {
				JournalArticle article = articlesItr.next();

				if (context.addPrimaryKey(
						JournalArticle.class, article.getPrimaryKeyObj())) {

					articlesItr.remove();
				}
				else {
					exportArticle(context, article);
				}
			}

			xml = xStream.toXML(articles);

			el = root.addElement("journal-articles");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			return doc.asXML();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_articlesStructuresAndTemplates, _images, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_articlesStructuresAndTemplates, _images, _comments, _ratings, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			XStream xStream = new XStream();

			Document doc = PortalUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Structures

			Element el = root.element("journal-structures").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map<String, String> structureIds = context.getNewPrimaryKeysMap(
				JournalStructure.class);

			List<JournalStructure> structures =
				(List<JournalStructure>)xStream.fromXML(tempDoc.asXML());

			for (JournalStructure structure : structures) {
				importStructure(context, structureIds, structure);
			}

			// Templates

			el = root.element("journal-templates").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map<String, String> templateIds = context.getNewPrimaryKeysMap(
				JournalTemplate.class);

			List<JournalTemplate> templates =
				(List<JournalTemplate>)xStream.fromXML(tempDoc.asXML());

			for (JournalTemplate template : templates) {
				importTemplate(context, structureIds, templateIds, template);
			}

			// Articles

			el = root.element("journal-articles").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map<String, String> articleIds = context.getNewPrimaryKeysMap(
				JournalArticle.class);

			List<JournalArticle> articles =
				(List<JournalArticle>)xStream.fromXML(tempDoc.asXML());

			for (JournalArticle article : articles) {
				importArticle(
					context, structureIds, templateIds, articleIds, article);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return true;
	}

	protected static void exportArticle(
			PortletDataContext context, JournalArticle article)
		throws IOException, PortalException, SystemException {

		article.setUserUuid(article.getUserUuid());
		article.setApprovedByUserUuid(article.getApprovedByUserUuid());

		if (article.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				article.getSmallImageId());

			article.setSmallImageType(smallImage.getType());

			context.getZipWriter().addEntry(
				getSmallImageDir(article), smallImage.getTextObj());
		}

		if (context.getBooleanParameter(_NAMESPACE, "images")) {
			List<JournalArticleImage> articleImages =
				JournalArticleImageUtil.findByG_A_V(
					context.getGroupId(), article.getArticleId(),
					article.getVersion());

			for (JournalArticleImage articleImage : articleImages) {
				try {
					Image image = ImageUtil.findByPrimaryKey(
						articleImage.getArticleImageId());

					String fileName =
						articleImage.getElName() +
							articleImage.getLanguageId() + "." +
								image.getType();

					context.getZipWriter().addEntry(
						getArticleImageDir(article) + fileName,
						image.getTextObj());
				}
				catch (NoSuchImageException nsie) {
				}
			}
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(
				JournalArticle.class, new Long(article.getResourcePrimKey()));
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(
				JournalArticle.class, new Long(article.getResourcePrimKey()));
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addTagsEntries(
				JournalArticle.class, new Long(article.getResourcePrimKey()));
		}
	}

	protected static void exportStructure(JournalStructure structure)
		throws SystemException {

		structure.setUserUuid(structure.getUserUuid());
	}

	protected static void exportTemplate(
			PortletDataContext context, JournalTemplate template)
		throws IOException, PortalException, SystemException {

		template.setUserUuid(template.getUserUuid());

		if (template.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			template.setSmallImageType(smallImage.getType());

			context.getZipWriter().addEntry(
				getSmallImageDir(template), smallImage.getTextObj());
		}
	}

	protected static String getArticleImageDir(JournalArticle article) {
		return _ARTICLE_IMAGES_FOLDER + article.getArticleId() + "/" +
			article.getVersion() + "/";
	}

	protected static String getSmallImageDir(JournalArticle article)
		throws PortalException, SystemException {

		return _ARTICLE_SMALL_IMAGES_FOLDER + article.getSmallImageId() + "." +
			article.getSmallImageType();
	}

	protected static String getSmallImageDir(JournalTemplate template)
		throws PortalException, SystemException {

		return _TEMPLATE_SMALL_IMAGES_FOLDER + template.getSmallImageId() +
			"." + template.getSmallImageType();
	}

	protected static void importArticle(
			PortletDataContext context, Map<String, String> structureIds,
			Map<String, String> templateIds, Map<String, String> articleIds,
			JournalArticle article)
		throws Exception {

		long userId = context.getUserId(article.getUserUuid());
		long plid = context.getPlid();

		String articleId = article.getArticleId();
		boolean autoArticleId = false;

		if ((Validator.isNumber(articleId)) ||
			(JournalArticleUtil.fetchByG_A_V(
				context.getGroupId(), articleId,
					JournalArticleImpl.DEFAULT_VERSION) != null)) {

			autoArticleId = true;
		}

		boolean incrementVersion = false;

		String parentStructureId = MapUtil.getString(
			structureIds, article.getStructureId(), article.getStructureId());
		String parentTemplateId = MapUtil.getString(
			templateIds, article.getTemplateId(), article.getTemplateId());

		Date displayDate = article.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar();

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);
		}

		Date expirationDate = article.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;
		}

		Date reviewDate = article.getReviewDate();

		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;

		if (reviewDate != null) {
			Calendar reviewCal = CalendarFactoryUtil.getCalendar();

			reviewCal.setTime(reviewDate);

			reviewDateMonth = reviewCal.get(Calendar.MONTH);
			reviewDateDay = reviewCal.get(Calendar.DATE);
			reviewDateYear = reviewCal.get(Calendar.YEAR);
			reviewDateHour = reviewCal.get(Calendar.HOUR);
			reviewDateMinute = reviewCal.get(Calendar.MINUTE);
			neverReview = false;
		}

		File smallFile = null;

		if (article.isSmallImage()) {
			byte[] byteArray = context.getZipReader().getEntryAsByteArray(
				getSmallImageDir(article));

			smallFile = File.createTempFile(
				String.valueOf(article.getSmallImageId()),
				StringPool.PERIOD + article.getSmallImageType());

			FileUtil.write(smallFile, byteArray);
		}

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		if (context.getBooleanParameter(_NAMESPACE, "images")) {
			List<ObjectValuePair<String, byte[]>> imageFiles =
				context.getZipReader().getFolderEntries().get(
					getArticleImageDir(article));

			if (imageFiles != null) {
				for (ObjectValuePair<String, byte[]> imageFile : imageFiles) {
					String fileName = imageFile.getKey();

					int pos = fileName.lastIndexOf(".");

					if (pos != -1) {
						fileName = fileName.substring(0, pos);
					}

					images.put(fileName, imageFile.getValue());
				}
			}
		}

		String articleURL = null;

		PortletPreferences prefs = null;

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				JournalArticle.class, new Long(article.getResourcePrimKey()));
		}

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		String newContent = creationStrategy.getTransformedContent(
			context, article);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			article.setContent(newContent);
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, article);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, article);

		JournalArticle existingArticle = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingArticle = JournalArticleUtil.fetchByUUID_G(
				article.getUuid(), context.getGroupId());

			if (existingArticle == null) {
				existingArticle =  JournalArticleLocalServiceUtil.addArticle(
					article.getUuid(), userId, articleId, autoArticleId, plid,
					article.getVersion(), article.getTitle(),
					article.getDescription(), article.getContent(),
					article.getType(), parentStructureId, parentTemplateId,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.getIndexable(),
					article.getSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, prefs, tagsEntries,
					addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingArticle =  JournalArticleLocalServiceUtil.updateArticle(
					userId, existingArticle.getGroupId(),
					existingArticle.getArticleId(),
					existingArticle.getVersion(), incrementVersion,
					article.getTitle(), article.getDescription(),
					article.getContent(), article.getType(),
					existingArticle.getStructureId(),
					existingArticle.getTemplateId(), displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.getIndexable(),
					article.getSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, prefs, tagsEntries);
			}
		}
		else {
			existingArticle = JournalArticleLocalServiceUtil.addArticle(
				userId, articleId, autoArticleId, plid, article.getVersion(),
				article.getTitle(), article.getDescription(),
				article.getContent(), article.getType(), parentStructureId,
				parentTemplateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, article.getIndexable(),
				article.getSmallImage(), article.getSmallImageURL(), smallFile,
				images, articleURL, prefs, tagsEntries, addCommunityPermissions,
				addGuestPermissions);
		}

		long strategyApprovalUserId = creationStrategy.getApprovalUserId(
			context, article);

		if ((strategyApprovalUserId !=
				JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) ||
			(article.isApproved() && !existingArticle.isApproved())) {

			long approvedByUserId = strategyApprovalUserId;

			if (approvedByUserId == 0) {
				approvedByUserId = context.getUserId(
					article.getApprovedByUserUuid());
			}

			JournalArticleLocalServiceUtil.approveArticle(
				approvedByUserId, context.getGroupId(),
				existingArticle.getArticleId(), existingArticle.getVersion(),
				articleURL, prefs);
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				JournalArticle.class, new Long(article.getResourcePrimKey()),
				new Long(existingArticle.getResourcePrimKey()),
				context.getGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				JournalArticle.class, new Long(article.getResourcePrimKey()),
				new Long(existingArticle.getResourcePrimKey()));
		}

		articleIds.put(
			article.getArticleId(), existingArticle.getArticleId());

		if (!articleId.equals(existingArticle.getArticleId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"An article with the ID " + articleId + " already " +
						"exists. The new generated ID is " +
							existingArticle.getArticleId());
			}
		}
	}

	protected static void importStructure(
			PortletDataContext context, Map<String, String> structureIds,
			JournalStructure structure)
		throws Exception {

		long userId = context.getUserId(structure.getUserUuid());
		long plid = context.getPlid();

		String structureId = structure.getStructureId();
		boolean autoStructureId = false;

		if ((Validator.isNumber(structureId)) ||
			(JournalStructureUtil.fetchByG_S(
				context.getGroupId(), structureId) != null)) {

			autoStructureId = true;
		}

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, structure);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, structure);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, structure);

		JournalStructure existingStructure = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingStructure = JournalStructureUtil.fetchByUUID_G(
				structure.getUuid(), context.getGroupId());

			if (existingStructure == null) {
				existingStructure =
					JournalStructureLocalServiceUtil.addStructure(
						structure.getUuid(), userId, structureId,
						autoStructureId, plid, structure.getName(),
						structure.getDescription(), structure.getXsd(),
						addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingStructure =
					JournalStructureLocalServiceUtil.updateStructure(
						existingStructure.getGroupId(),
						existingStructure.getStructureId(), structure.getName(),
						structure.getDescription(), structure.getXsd());
			}
		}
		else {
			existingStructure =
				JournalStructureLocalServiceUtil.addStructure(
					userId, structureId, autoStructureId, plid,
					structure.getName(), structure.getDescription(),
					structure.getXsd(), addCommunityPermissions,
					addGuestPermissions);
		}

		structureIds.put(
			structure.getStructureId(), existingStructure.getStructureId());

		if (!structureId.equals(existingStructure.getStructureId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A structure with the ID " + structureId + " already " +
						"exists. The new generated ID is " +
							existingStructure.getStructureId());
			}
		}
	}

	protected static void importTemplate(
			PortletDataContext context, Map<String, String> structureIds,
			Map<String, String> templateIds, JournalTemplate template)
		throws Exception {

		long userId = context.getUserId(template.getUserUuid());
		long plid = context.getPlid();

		String templateId = template.getTemplateId();
		boolean autoTemplateId = false;

		if ((Validator.isNumber(templateId)) ||
			(JournalTemplateUtil.fetchByG_T(
				context.getGroupId(), templateId) != null)) {

			autoTemplateId = true;
		}

		String parentStructureId = MapUtil.getString(
			structureIds, template.getStructureId(), template.getStructureId());

		boolean formatXsl = false;

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, template);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, template);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, template);

		File smallFile = null;

		if (template.isSmallImage()) {
			byte[] byteArray = context.getZipReader().getEntryAsByteArray(
				getSmallImageDir(template));

			smallFile = File.createTempFile(
				String.valueOf(template.getSmallImageId()),
				StringPool.PERIOD + template.getSmallImageType());

			FileUtil.write(smallFile, byteArray);
		}

		JournalTemplate existingTemplate = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingTemplate = JournalTemplateUtil.fetchByUUID_G(
				template.getUuid(), context.getGroupId());

			if (existingTemplate == null) {
				existingTemplate =
					JournalTemplateLocalServiceUtil.addTemplate(
						template.getUuid(), userId, templateId, autoTemplateId,
						plid, parentStructureId, template.getName(),
						template.getDescription(), template.getXsl(), formatXsl,
						template.getLangType(), template.getCacheable(),
						template.isSmallImage(), template.getSmallImageURL(),
						smallFile, addCommunityPermissions,
						addGuestPermissions);
			}
			else {
				existingTemplate =
					JournalTemplateLocalServiceUtil.updateTemplate(
						existingTemplate.getGroupId(),
						existingTemplate.getTemplateId(),
						existingTemplate.getStructureId(), template.getName(),
						template.getDescription(), template.getXsl(), formatXsl,
						template.getLangType(), template.getCacheable(),
						template.isSmallImage(), template.getSmallImageURL(),
						smallFile);
			}
		}
		else {
			existingTemplate =
				JournalTemplateLocalServiceUtil.addTemplate(
					userId, templateId, autoTemplateId, plid, parentStructureId,
					template.getName(), template.getDescription(),
					template.getXsl(), formatXsl, template.getLangType(),
					template.getCacheable(), template.isSmallImage(),
					template.getSmallImageURL(), smallFile,
					addCommunityPermissions, addGuestPermissions);
		}

		templateIds.put(
			template.getTemplateId(), existingTemplate.getTemplateId());

		if (!templateId.equals(existingTemplate.getTemplateId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A template with the ID " + templateId + " already " +
						"exists. The new generated ID is " +
							existingTemplate.getTemplateId());
			}
		}
	}

	private static final String _NAMESPACE = "journal";

	private static final String _ARTICLE_IMAGES_FOLDER =
		"article-images/";

	private static final String _ARTICLE_SMALL_IMAGES_FOLDER =
		"article-thumbnails/";

	private static final String _TEMPLATE_SMALL_IMAGES_FOLDER =
		"template-thumbnails/";

	private static final PortletDataHandlerBoolean
		_articlesStructuresAndTemplates = new PortletDataHandlerBoolean(
			_NAMESPACE, "articles-structures-and-templates", true, true);

	private static final PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log =
		LogFactory.getLog(JournalPortletDataHandlerImpl.class);

}