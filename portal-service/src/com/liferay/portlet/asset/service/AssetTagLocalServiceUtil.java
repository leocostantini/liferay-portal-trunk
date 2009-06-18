/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;


/**
 * <a href="AssetTagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.asset.service.AssetTagLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.asset.service.AssetTagLocalService
 *
 */
public class AssetTagLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetTag addAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return getService().addAssetTag(assetTag);
	}

	public static com.liferay.portlet.asset.model.AssetTag createAssetTag(
		long tagId) {
		return getService().createAssetTag(tagId);
	}

	public static void deleteAssetTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAssetTag(tagId);
	}

	public static void deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		getService().deleteAssetTag(assetTag);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetTag getAssetTag(
		long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAssetTag(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAssetTags(start, end);
	}

	public static int getAssetTagsCount()
		throws com.liferay.portal.SystemException {
		return getService().getAssetTagsCount();
	}

	public static com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return getService().updateAssetTag(assetTag);
	}

	public static com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateAssetTag(assetTag, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addTag(userId, name, tagProperties, serviceContext);
	}

	public static void addTagResources(
		com.liferay.portlet.asset.model.AssetTag tag,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addTagResources(tag, addCommunityPermissions, addGuestPermissions);
	}

	public static void addTagResources(
		com.liferay.portlet.asset.model.AssetTag tag,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addTagResources(tag, communityPermissions, guestPermissions);
	}

	public static void checkTags(long userId, long groupId,
		java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkTags(userId, groupId, names);
	}

	public static com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long classNameId, long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().decrementAssetCount(classNameId, tagId);
	}

	public static void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTag(tag);
	}

	public static void deleteTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTag(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId) throws com.liferay.portal.SystemException {
		return getService().getEntryTags(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId) throws com.liferay.portal.SystemException {
		return getService().getGroupTags(groupId);
	}

	public static com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTag(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTag getTag(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTag(groupId, name);
	}

	public static long[] getTagIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTagIds(groupId, names);
	}

	public static java.lang.String[] getTagNames()
		throws com.liferay.portal.SystemException {
		return getService().getTagNames();
	}

	public static java.lang.String[] getTagNames(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getTagNames(classNameId, classPK);
	}

	public static java.lang.String[] getTagNames(java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		return getService().getTagNames(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.SystemException {
		return getService().getTags();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getTags(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getService().getTags(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().getTags(groupId, classNameId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getTags(className, classPK);
	}

	public static int getTagsSize(long groupId, long classNameId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getService().getTagsSize(groupId, classNameId, name);
	}

	public static boolean hasTag(long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().hasTag(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long classNameId, long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().incrementAssetCount(classNameId, tagId);
	}

	public static void mergeTags(long fromTagId, long toTagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().mergeTags(fromTagId, toTagId);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().search(groupId, name, tagProperties, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetTag updateTag(
		long userId, long tagId, java.lang.String name,
		java.lang.String[] tagProperties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateTag(userId, tagId, name, tagProperties);
	}

	public static AssetTagLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("AssetTagLocalService is not set");
		}

		return _service;
	}

	public void setService(AssetTagLocalService service) {
		_service = service;
	}

	private static AssetTagLocalService _service;
}