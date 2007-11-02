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

package com.liferay.portlet.tags.service;

/**
 * <a href="TagsAssetLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.tags.service.TagsAssetLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.tags.service.TagsAssetLocalServiceFactory</code> is
 * responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsAssetLocalService
 * @see com.liferay.portlet.tags.service.TagsAssetLocalServiceFactory
 *
 */
public class TagsAssetLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsAssetPersistence getTagsAssetPersistence() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsAssetPersistence();
	}

	public static void setTagsAssetPersistence(
		com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsAssetPersistence(tagsAssetPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsAssetFinder getTagsAssetFinder() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsAssetFinder();
	}

	public static void setTagsAssetFinder(
		com.liferay.portlet.tags.service.persistence.TagsAssetFinder tagsAssetFinder) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsAssetFinder(tagsAssetFinder);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsEntryPersistence getTagsEntryPersistence() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsEntryPersistence();
	}

	public static void setTagsEntryPersistence(
		com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsEntryPersistence(tagsEntryPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsEntryFinder getTagsEntryFinder() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsEntryFinder();
	}

	public static void setTagsEntryFinder(
		com.liferay.portlet.tags.service.persistence.TagsEntryFinder tagsEntryFinder) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsEntryFinder(tagsEntryFinder);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence getTagsPropertyPersistence() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsPropertyPersistence();
	}

	public static void setTagsPropertyPersistence(
		com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence tagsPropertyPersistence) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsPropertyPersistence(tagsPropertyPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsPropertyFinder getTagsPropertyFinder() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsPropertyFinder();
	}

	public static void setTagsPropertyFinder(
		com.liferay.portlet.tags.service.persistence.TagsPropertyFinder tagsPropertyFinder) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsPropertyFinder(tagsPropertyFinder);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsPropertyKeyFinder getTagsPropertyKeyFinder() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsPropertyKeyFinder();
	}

	public static void setTagsPropertyKeyFinder(
		com.liferay.portlet.tags.service.persistence.TagsPropertyKeyFinder tagsPropertyKeyFinder) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsPropertyKeyFinder(tagsPropertyKeyFinder);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsSourcePersistence getTagsSourcePersistence() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsSourcePersistence();
	}

	public static void setTagsSourcePersistence(
		com.liferay.portlet.tags.service.persistence.TagsSourcePersistence tagsSourcePersistence) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.setTagsSourcePersistence(tagsSourcePersistence);
	}

	public static void afterPropertiesSet() {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.afterPropertiesSet();
	}

	public static void deleteAsset(long assetId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.deleteAsset(assetId);
	}

	public static void deleteAsset(java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.deleteAsset(className, classPK);
	}

	public static void deleteAsset(
		com.liferay.portlet.tags.model.TagsAsset asset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.deleteAsset(asset);
	}

	public static com.liferay.portlet.tags.model.TagsAsset getAsset(
		long assetId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAsset(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset getAsset(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAsset(className, classPK);
	}

	public static com.liferay.portlet.tags.model.TagsAssetType[] getAssetTypes(
		java.lang.String languageId) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetTypes(languageId);
	}

	public static java.util.List getAssets(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount, int begin, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, begin, end);
	}

	public static java.util.List getAssets(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount,
		int begin, int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(groupId, entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, begin, end);
	}

	public static java.util.List getAssets(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int begin,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate,
			begin, end);
	}

	public static java.util.List getAssets(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int begin,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(groupId, entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate,
			begin, end);
	}

	public static java.util.List getAssets(long[] entryIds, long[] notEntryIds,
		boolean andOperator, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int begin,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(entryIds, notEntryIds,
			andOperator, orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, begin, end);
	}

	public static java.util.List getAssets(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int begin,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(groupId, entryIds, notEntryIds,
			andOperator, orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, begin, end);
	}

	public static int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount);
	}

	public static int getAssetsCount(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(groupId, entryIds,
			notEntryIds, andOperator, excludeZeroViewCount);
	}

	public static int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate);
	}

	public static int getAssetsCount(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(groupId, entryIds,
			notEntryIds, andOperator, excludeZeroViewCount, publishDate,
			expirationDate);
	}

	public static com.liferay.portlet.tags.model.TagsAssetDisplay[] getCompanyAssetDisplays(
		long companyId, int begin, int end, java.lang.String languageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getCompanyAssetDisplays(companyId, begin,
			end, languageId);
	}

	public static java.util.List getCompanyAssets(long companyId, int begin,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getCompanyAssets(companyId, begin, end);
	}

	public static int getCompanyAssetsCount(long companyId)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getCompanyAssetsCount(companyId);
	}

	public static java.util.List getTopViewedAssets(
		java.lang.String className, boolean asc, int begin, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTopViewedAssets(className, asc, begin,
			end);
	}

	public static java.util.List getTopViewedAssets(
		java.lang.String[] className, boolean asc, int begin, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTopViewedAssets(className, asc, begin,
			end);
	}

	public static com.liferay.portlet.tags.model.TagsAsset incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.incrementViewCounter(className, classPK);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.search(companyId, portletId, keywords);
	}

	public static com.liferay.portlet.tags.model.TagsAssetDisplay[] searchAssetDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.searchAssetDisplays(companyId, portletId,
			keywords, languageId, begin, end);
	}

	public static int searchAssetDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.searchAssetDisplaysCount(companyId,
			portletId, keywords, languageId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.updateAsset(userId, groupId, className,
			classPK, entryNames);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url, int height, int width)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.updateAsset(userId, groupId, className,
			classPK, entryNames, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url, height,
			width);
	}

	public static void validate(java.lang.String className,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();
		tagsAssetLocalService.validate(className, entryNames);
	}
}