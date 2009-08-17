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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCFrameworkVersionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SCFrameworkVersionLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersionLocalService
 * @generated
 */
public class SCFrameworkVersionLocalServiceUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.SystemException {
		return getService().addSCFrameworkVersion(scFrameworkVersion);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion createSCFrameworkVersion(
		long frameworkVersionId) {
		return getService().createSCFrameworkVersion(frameworkVersionId);
	}

	public static void deleteSCFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteSCFrameworkVersion(frameworkVersionId);
	}

	public static void deleteSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.SystemException {
		getService().deleteSCFrameworkVersion(scFrameworkVersion);
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

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getSCFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getSCFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getSCFrameworkVersions(start, end);
	}

	public static int getSCFrameworkVersionsCount()
		throws com.liferay.portal.SystemException {
		return getService().getSCFrameworkVersionsCount();
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.SystemException {
		return getService().updateSCFrameworkVersion(scFrameworkVersion);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateSCFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateSCFrameworkVersion(scFrameworkVersion, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		long userId, java.lang.String name, java.lang.String url,
		boolean active, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addFrameworkVersion(userId, name, url, active, priority,
			serviceContext);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersionId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersion,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFrameworkVersionResources(long frameworkVersionId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersionId,
			communityPermissions, guestPermissions);
	}

	public static void addFrameworkVersionResources(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addFrameworkVersionResources(frameworkVersion,
			communityPermissions, guestPermissions);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteFrameworkVersion(frameworkVersionId);
	}

	public static void deleteFrameworkVersion(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion frameworkVersion)
		throws com.liferay.portal.SystemException {
		getService().deleteFrameworkVersion(frameworkVersion);
	}

	public static void deleteFrameworkVersions(long groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteFrameworkVersions(groupId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getFrameworkVersion(frameworkVersionId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getFrameworkVersions(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) throws com.liferay.portal.SystemException {
		return getService().getFrameworkVersions(groupId, active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getFrameworkVersions(groupId, active, start, end);
	}

	public static int getFrameworkVersionsCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getFrameworkVersionsCount(groupId);
	}

	public static int getFrameworkVersionsCount(long groupId, boolean active)
		throws com.liferay.portal.SystemException {
		return getService().getFrameworkVersionsCount(groupId, active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId) throws com.liferay.portal.SystemException {
		return getService().getProductVersionFrameworkVersions(productVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateFrameworkVersion(frameworkVersionId, name, url,
			active, priority);
	}

	public static SCFrameworkVersionLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"SCFrameworkVersionLocalService is not set");
		}

		return _service;
	}

	public void setService(SCFrameworkVersionLocalService service) {
		_service = service;
	}

	private static SCFrameworkVersionLocalService _service;
}