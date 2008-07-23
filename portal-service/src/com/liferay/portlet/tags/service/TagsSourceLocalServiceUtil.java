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

package com.liferay.portlet.tags.service;


/**
 * <a href="TagsSourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tags.service.TagsSourceLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.tags.service.TagsSourceLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsSourceLocalService
 * @see com.liferay.portlet.tags.service.TagsSourceLocalServiceFactory
 *
 */
public class TagsSourceLocalServiceUtil {
	public static com.liferay.portlet.tags.model.TagsSource addTagsSource(
		com.liferay.portlet.tags.model.TagsSource tagsSource)
		throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.addTagsSource(tagsSource);
	}

	public static void deleteTagsSource(long sourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		tagsSourceLocalService.deleteTagsSource(sourceId);
	}

	public static void deleteTagsSource(
		com.liferay.portlet.tags.model.TagsSource tagsSource)
		throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		tagsSourceLocalService.deleteTagsSource(tagsSource);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.tags.model.TagsSource getTagsSource(
		long sourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.getTagsSource(sourceId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsSource> getTagsSources(
		int start, int end) throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.getTagsSources(start, end);
	}

	public static int getTagsSourcesCount()
		throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.getTagsSourcesCount();
	}

	public static com.liferay.portlet.tags.model.TagsSource updateTagsSource(
		com.liferay.portlet.tags.model.TagsSource tagsSource)
		throws com.liferay.portal.SystemException {
		TagsSourceLocalService tagsSourceLocalService = TagsSourceLocalServiceFactory.getService();

		return tagsSourceLocalService.updateTagsSource(tagsSource);
	}
}