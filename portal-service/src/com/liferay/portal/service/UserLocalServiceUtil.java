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

package com.liferay.portal.service;

/**
 * <a href="UserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.UserLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.UserLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserLocalService
 * @see com.liferay.portal.service.UserLocalServiceFactory
 *
 */
public class UserLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addGroupUsers(groupId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(java.lang.String roleId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(java.lang.String userGroupId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User addUser(long creatorUserId,
		java.lang.String companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId,
		boolean sendEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.addUser(creatorUserId, companyId, autoPassword,
			password1, password2, passwordReset, autoScreenName, screenName,
			emailAddress, locale, firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			organizationId, locationId, sendEmail);
	}

	public static int authenticateByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByEmailAddress(companyId,
			emailAddress, password, headerMap, parameterMap);
	}

	public static int authenticateByScreenName(java.lang.String companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByScreenName(companyId, screenName,
			password, headerMap, parameterMap);
	}

	public static int authenticateByUserId(java.lang.String companyId,
		long userId, java.lang.String password, java.util.Map headerMap,
		java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByUserId(companyId, userId,
			password, headerMap, parameterMap);
	}

	public static boolean authenticateForJAAS(long userId,
		java.lang.String encPwd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateForJAAS(userId, encPwd);
	}

	public static com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		java.lang.String companyId, java.lang.String name,
		java.lang.String password)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.decryptUserId(companyId, name, password);
	}

	public static void deletePasswordPolicyUser(long passwordPolicyId,
		long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.deletePasswordPolicyUser(passwordPolicyId, userId);
	}

	public static void deleteRoleUser(java.lang.String roleId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.deleteRoleUser(roleId, userId);
	}

	public static void deleteUser(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.deleteUser(userId);
	}

	public static java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.encryptUserId(name);
	}

	public static com.liferay.portal.model.User getDefaultUser(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getDefaultUser(companyId);
	}

	public static long getDefaultUserId(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getDefaultUserId(companyId);
	}

	public static java.util.List getGroupUsers(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getGroupUsers(groupId);
	}

	public static java.util.List getPermissionUsers(
		java.lang.String companyId, long groupId, java.lang.String name,
		java.lang.String primKey, java.lang.String actionId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getPermissionUsers(companyId, groupId, name,
			primKey, actionId, firstName, middleName, lastName, emailAddress,
			andOperator, begin, end);
	}

	public static int getPermissionUsersCount(java.lang.String companyId,
		long groupId, java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getPermissionUsersCount(companyId, groupId,
			name, primKey, actionId, firstName, middleName, lastName,
			emailAddress, andOperator);
	}

	public static java.util.List getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getRoleUsers(roleId);
	}

	public static com.liferay.portal.model.User getUserByContactId(
		long contactId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByContactId(contactId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserById(
		java.lang.String companyId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserById(companyId, userId);
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		java.lang.String companyId, java.lang.String screenName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByScreenName(companyId, screenName);
	}

	public static long getUserIdByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserIdByEmailAddress(companyId, emailAddress);
	}

	public static long getUserIdByScreenName(java.lang.String companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserIdByScreenName(companyId, screenName);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasGroupUser(groupId, userId);
	}

	public static boolean hasPasswordPolicyUser(long passwordPolicyId,
		long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasPasswordPolicyUser(passwordPolicyId, userId);
	}

	public static boolean hasRoleUser(java.lang.String roleId, long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasRoleUser(roleId, userId);
	}

	public static boolean hasUserGroupUser(java.lang.String userGroupId,
		long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasUserGroupUser(userGroupId, userId);
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, boolean active,
		java.util.LinkedHashMap params, boolean andSearch, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.search(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch,
			begin, end, obc);
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, boolean active,
		java.util.LinkedHashMap params, boolean andSearch)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.searchCount(companyId, firstName, middleName,
			lastName, screenName, emailAddress, active, params, andSearch);
	}

	public static void sendPassword(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.sendPassword(companyId, emailAddress, remoteAddr,
			remoteHost, userAgent);
	}

	public static void setGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.setGroupUsers(groupId, userIds);
	}

	public static void setRoleUsers(java.lang.String roleId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(java.lang.String userGroupId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(java.lang.String roleId, long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(java.lang.String userGroupId,
		long[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateAgreedToTermsOfUse(userId,
			agreedToTermsOfUse);
	}

	public static com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLastLogin(userId, loginIP);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.updatePortrait(userId, bytes);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateUser(userId, password, screenName,
			emailAddress, languageId, timeZoneId, greeting, comments,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, icqSn,
			jabberSn, msnSn, skypeSn, ymSn, jobTitle, organizationId, locationId);
	}
}