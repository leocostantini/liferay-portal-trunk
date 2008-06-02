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

package com.liferay.portlet.wiki.service;


/**
 * <a href="WikiPageResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.wiki.service.WikiPageResourceLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.service.WikiPageResourceLocalService
 * @see com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceFactory
 *
 */
public class WikiPageResourceLocalServiceUtil {
	public static com.liferay.portlet.wiki.model.WikiPageResource addWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.addWikiPageResource(wikiPageResource);
	}

	public static void deleteWikiPageResource(long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		wikiPageResourceLocalService.deleteWikiPageResource(resourcePrimKey);
	}

	public static void deleteWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		wikiPageResourceLocalService.deleteWikiPageResource(wikiPageResource);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPageResource> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource getWikiPageResource(
		long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.getWikiPageResource(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource updateWikiPageResource(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource)
		throws com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.updateWikiPageResource(wikiPageResource);
	}

	public static void deletePageResource(long nodeId, java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		wikiPageResourceLocalService.deletePageResource(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPageResource getPageResource(
		long pageResourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.getPageResource(pageResourcePrimKey);
	}

	public static long getPageResourcePrimKey(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WikiPageResourceLocalService wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getService();

		return wikiPageResourceLocalService.getPageResourcePrimKey(nodeId, title);
	}
}