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

package com.liferay.portal.service;


/**
 * <a href="LayoutLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portal.service.impl.LayoutLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.LayoutLocalServiceFactory
 * @see com.liferay.portal.service.LayoutLocalServiceUtil
 *
 */
public interface LayoutLocalService {
	public com.liferay.portal.model.Layout addLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException;

	public void deleteLayout(long plid)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteLayout(com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portal.model.Layout> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.service.persistence.AccountPersistence getAccountPersistence();

	public void setAccountPersistence(
		com.liferay.portal.service.persistence.AccountPersistence accountPersistence);

	public com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence();

	public void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence);

	public com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder();

	public void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder);

	public com.liferay.portal.service.persistence.AddressPersistence getAddressPersistence();

	public void setAddressPersistence(
		com.liferay.portal.service.persistence.AddressPersistence addressPersistence);

	public com.liferay.portal.service.persistence.ClassNamePersistence getClassNamePersistence();

	public void setClassNamePersistence(
		com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence);

	public com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence();

	public void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence);

	public com.liferay.portal.service.persistence.ContactPersistence getContactPersistence();

	public void setContactPersistence(
		com.liferay.portal.service.persistence.ContactPersistence contactPersistence);

	public com.liferay.portal.service.persistence.CountryPersistence getCountryPersistence();

	public void setCountryPersistence(
		com.liferay.portal.service.persistence.CountryPersistence countryPersistence);

	public com.liferay.portal.service.persistence.EmailAddressPersistence getEmailAddressPersistence();

	public void setEmailAddressPersistence(
		com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence);

	public com.liferay.portal.service.persistence.GroupPersistence getGroupPersistence();

	public void setGroupPersistence(
		com.liferay.portal.service.persistence.GroupPersistence groupPersistence);

	public com.liferay.portal.service.persistence.GroupFinder getGroupFinder();

	public void setGroupFinder(
		com.liferay.portal.service.persistence.GroupFinder groupFinder);

	public com.liferay.portal.service.persistence.ImagePersistence getImagePersistence();

	public void setImagePersistence(
		com.liferay.portal.service.persistence.ImagePersistence imagePersistence);

	public com.liferay.portal.service.persistence.LayoutPersistence getLayoutPersistence();

	public void setLayoutPersistence(
		com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence);

	public com.liferay.portal.service.persistence.LayoutFinder getLayoutFinder();

	public void setLayoutFinder(
		com.liferay.portal.service.persistence.LayoutFinder layoutFinder);

	public com.liferay.portal.service.persistence.LayoutSetPersistence getLayoutSetPersistence();

	public void setLayoutSetPersistence(
		com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence);

	public com.liferay.portal.service.persistence.ListTypePersistence getListTypePersistence();

	public void setListTypePersistence(
		com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence);

	public com.liferay.portal.service.persistence.MembershipRequestPersistence getMembershipRequestPersistence();

	public void setMembershipRequestPersistence(
		com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence);

	public com.liferay.portal.service.persistence.OrganizationPersistence getOrganizationPersistence();

	public void setOrganizationPersistence(
		com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence);

	public com.liferay.portal.service.persistence.OrganizationFinder getOrganizationFinder();

	public void setOrganizationFinder(
		com.liferay.portal.service.persistence.OrganizationFinder organizationFinder);

	public com.liferay.portal.service.persistence.OrgGroupPermissionPersistence getOrgGroupPermissionPersistence();

	public void setOrgGroupPermissionPersistence(
		com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence);

	public com.liferay.portal.service.persistence.OrgGroupPermissionFinder getOrgGroupPermissionFinder();

	public void setOrgGroupPermissionFinder(
		com.liferay.portal.service.persistence.OrgGroupPermissionFinder orgGroupPermissionFinder);

	public com.liferay.portal.service.persistence.OrgGroupRolePersistence getOrgGroupRolePersistence();

	public void setOrgGroupRolePersistence(
		com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence);

	public com.liferay.portal.service.persistence.OrgLaborPersistence getOrgLaborPersistence();

	public void setOrgLaborPersistence(
		com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence);

	public com.liferay.portal.service.persistence.PasswordPolicyPersistence getPasswordPolicyPersistence();

	public void setPasswordPolicyPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence);

	public com.liferay.portal.service.persistence.PasswordPolicyFinder getPasswordPolicyFinder();

	public void setPasswordPolicyFinder(
		com.liferay.portal.service.persistence.PasswordPolicyFinder passwordPolicyFinder);

	public com.liferay.portal.service.persistence.PasswordPolicyRelPersistence getPasswordPolicyRelPersistence();

	public void setPasswordPolicyRelPersistence(
		com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence);

	public com.liferay.portal.service.persistence.PasswordTrackerPersistence getPasswordTrackerPersistence();

	public void setPasswordTrackerPersistence(
		com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence);

	public com.liferay.portal.service.persistence.PermissionPersistence getPermissionPersistence();

	public void setPermissionPersistence(
		com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence);

	public com.liferay.portal.service.persistence.PermissionFinder getPermissionFinder();

	public void setPermissionFinder(
		com.liferay.portal.service.persistence.PermissionFinder permissionFinder);

	public com.liferay.portal.service.persistence.PermissionUserFinder getPermissionUserFinder();

	public void setPermissionUserFinder(
		com.liferay.portal.service.persistence.PermissionUserFinder permissionUserFinder);

	public com.liferay.portal.service.persistence.PhonePersistence getPhonePersistence();

	public void setPhonePersistence(
		com.liferay.portal.service.persistence.PhonePersistence phonePersistence);

	public com.liferay.portal.service.persistence.PluginSettingPersistence getPluginSettingPersistence();

	public void setPluginSettingPersistence(
		com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence);

	public com.liferay.portal.service.persistence.PortletPersistence getPortletPersistence();

	public void setPortletPersistence(
		com.liferay.portal.service.persistence.PortletPersistence portletPersistence);

	public com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence();

	public void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence);

	public com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder();

	public void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder);

	public com.liferay.portal.service.persistence.RegionPersistence getRegionPersistence();

	public void setRegionPersistence(
		com.liferay.portal.service.persistence.RegionPersistence regionPersistence);

	public com.liferay.portal.service.persistence.ReleasePersistence getReleasePersistence();

	public void setReleasePersistence(
		com.liferay.portal.service.persistence.ReleasePersistence releasePersistence);

	public com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence();

	public void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence);

	public com.liferay.portal.service.persistence.ResourceFinder getResourceFinder();

	public void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder);

	public com.liferay.portal.service.persistence.ResourceCodePersistence getResourceCodePersistence();

	public void setResourceCodePersistence(
		com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence);

	public com.liferay.portal.service.persistence.RolePersistence getRolePersistence();

	public void setRolePersistence(
		com.liferay.portal.service.persistence.RolePersistence rolePersistence);

	public com.liferay.portal.service.persistence.RoleFinder getRoleFinder();

	public void setRoleFinder(
		com.liferay.portal.service.persistence.RoleFinder roleFinder);

	public com.liferay.portal.service.persistence.ServiceComponentPersistence getServiceComponentPersistence();

	public void setServiceComponentPersistence(
		com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence);

	public com.liferay.portal.service.persistence.SubscriptionPersistence getSubscriptionPersistence();

	public void setSubscriptionPersistence(
		com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence);

	public com.liferay.portal.service.persistence.UserPersistence getUserPersistence();

	public void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence);

	public com.liferay.portal.service.persistence.UserFinder getUserFinder();

	public void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder);

	public com.liferay.portal.service.persistence.UserGroupPersistence getUserGroupPersistence();

	public void setUserGroupPersistence(
		com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence);

	public com.liferay.portal.service.persistence.UserGroupFinder getUserGroupFinder();

	public void setUserGroupFinder(
		com.liferay.portal.service.persistence.UserGroupFinder userGroupFinder);

	public com.liferay.portal.service.persistence.UserGroupRolePersistence getUserGroupRolePersistence();

	public void setUserGroupRolePersistence(
		com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence);

	public com.liferay.portal.service.persistence.UserIdMapperPersistence getUserIdMapperPersistence();

	public void setUserIdMapperPersistence(
		com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence);

	public com.liferay.portal.service.persistence.UserTrackerPersistence getUserTrackerPersistence();

	public void setUserTrackerPersistence(
		com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence);

	public com.liferay.portal.service.persistence.UserTrackerPathPersistence getUserTrackerPathPersistence();

	public void setUserTrackerPathPersistence(
		com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence);

	public com.liferay.portal.service.persistence.WebDAVPropsPersistence getWebDAVPropsPersistence();

	public void setWebDAVPropsPersistence(
		com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence);

	public com.liferay.portal.service.persistence.WebsitePersistence getWebsitePersistence();

	public void setWebsitePersistence(
		com.liferay.portal.service.persistence.WebsitePersistence websitePersistence);

	public com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence getDLFolderPersistence();

	public void setDLFolderPersistence(
		com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence);

	public com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence getJournalContentSearchPersistence();

	public void setJournalContentSearchPersistence(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence);

	public com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence getMBMessagePersistence();

	public void setMBMessagePersistence(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence);

	public com.liferay.portlet.messageboards.service.persistence.MBMessageFinder getMBMessageFinder();

	public void setMBMessageFinder(
		com.liferay.portlet.messageboards.service.persistence.MBMessageFinder mbMessageFinder);

	public com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence getRatingsStatsPersistence();

	public void setRatingsStatsPersistence(
		com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence ratingsStatsPersistence);

	public com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence getTasksProposalPersistence();

	public void setTasksProposalPersistence(
		com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence);

	public com.liferay.portlet.tasks.service.persistence.TasksProposalFinder getTasksProposalFinder();

	public void setTasksProposalFinder(
		com.liferay.portlet.tasks.service.persistence.TasksProposalFinder tasksProposalFinder);

	public void afterPropertiesSet();

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map localeNamesMap, java.util.Map localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		long dlFolderId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map localeNamesMap, java.util.Map localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, long dlFolderId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map parameterMap)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public byte[] exportPortletInfo(long plid, java.lang.String portletId,
		java.util.Map parameterMap)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public long getDefaultPlid(long groupId)
		throws com.liferay.portal.SystemException;

	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Layout getDLFolderLayout(long dlFolderId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout getFriendlyURLLayout(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout getLayoutByIconImageId(
		long iconImageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List getLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public java.util.List getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException;

	public java.util.List getLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List getLayouts(long groupId, boolean privateLayout,
		long[] layoutIds)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId, java.lang.String prefsKey,
		java.lang.String prefsValue) throws com.liferay.portal.SystemException;

	public java.util.List getNullFriendlyURLLayouts()
		throws com.liferay.portal.SystemException;

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map parameterMap, java.io.File file)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map parameterMap, java.io.InputStream is)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void importPortletInfo(long userId, long plid,
		java.lang.String portletId, java.util.Map parameterMap,
		java.io.File file)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void importPortletInfo(long userId, long plid,
		java.lang.String portletId, java.util.Map parameterMap,
		java.io.InputStream is)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateFriendlyURL(long plid,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map localeNamesMap, java.util.Map localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map localeNamesMap, java.util.Map localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateLookAndFeel(long groupId,
		boolean privateLayout, long layoutId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateName(
		com.liferay.portal.model.Layout layout, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateParentLayoutId(long plid,
		long parentPlid)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updateParentLayoutId(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Layout updatePriority(
		com.liferay.portal.model.Layout layout, int priority)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}