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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalArticleResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.journal.service.JournalArticleResourceLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalArticleResourceLocalService
 * @see com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceFactory
 *
 */
public class JournalArticleResourceLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalArticleResource addJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.addJournalArticleResource(journalArticleResource);
	}

	public static void deleteJournalArticleResource(long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		journalArticleResourceLocalService.deleteJournalArticleResource(resourcePrimKey);
	}

	public static void deleteJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		journalArticleResourceLocalService.deleteJournalArticleResource(journalArticleResource);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource getJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.getJournalArticleResource(resourcePrimKey);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.updateJournalArticleResource(journalArticleResource);
	}

	public static void deleteArticleResource(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		journalArticleResourceLocalService.deleteArticleResource(groupId,
			articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource getArticleResource(
		long articleResourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.getArticleResource(articleResourcePrimKey);
	}

	public static long getArticleResourcePrimKey(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.getArticleResourcePrimKey(groupId,
			articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getArticleResources(
		long groupId) throws com.liferay.portal.SystemException {
		JournalArticleResourceLocalService journalArticleResourceLocalService = JournalArticleResourceLocalServiceFactory.getService();

		return journalArticleResourceLocalService.getArticleResources(groupId);
	}
}