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

package com.liferay.portlet.softwarecatalog.service;

/**
 * <a href="SCProductEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService
 * @see com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceFactory
 *
 */
public class SCProductEntryLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, long plid, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List thumbnails, java.util.List fullImages,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.addProductEntry(userId, plid, name,
			type, tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, long plid, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List thumbnails, java.util.List fullImages,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.addProductEntry(userId, plid, name,
			type, tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, long plid, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List thumbnails, java.util.List fullImages,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.addProductEntry(userId, plid, name,
			type, tags, shortDescription, longDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addProductEntryResources(long productEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.addProductEntryResources(productEntryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.addProductEntryResources(productEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addProductEntryResources(long productEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.addProductEntryResources(productEntryId,
			communityPermissions, guestPermissions);
	}

	public static void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.addProductEntryResources(productEntry,
			communityPermissions, guestPermissions);
	}

	public static void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.deleteProductEntry(productEntryId);
	}

	public static void deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.deleteProductEntry(productEntry);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntry(productEntryId);
	}

	public static java.util.List getProductEntries(long groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntries(groupId, begin, end);
	}

	public static java.util.List getProductEntries(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntries(groupId, begin,
			end, obc);
	}

	public static java.util.List getProductEntries(long groupId, long userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntries(groupId, userId,
			begin, end);
	}

	public static java.util.List getProductEntries(long groupId, long userId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntries(groupId, userId,
			begin, end, obc);
	}

	public static int getProductEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntriesCount(groupId);
	}

	public static int getProductEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getProductEntriesCount(groupId, userId);
	}

	public static java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.getRepositoryXML(groupId,
			baseImageURL, oldestDate, maxNumOfVersions, repoSettings);
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();
		scProductEntryLocalService.reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.lang.String keywords, java.lang.String type)
		throws com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.search(companyId, groupId, keywords,
			type);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List thumbnails, java.util.List fullImages)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		SCProductEntryLocalService scProductEntryLocalService = SCProductEntryLocalServiceFactory.getService();

		return scProductEntryLocalService.updateProductEntry(productEntryId,
			name, type, tags, shortDescription, longDescription, pageURL,
			author, repoGroupId, repoArtifactId, licenseIds, thumbnails,
			fullImages);
	}
}