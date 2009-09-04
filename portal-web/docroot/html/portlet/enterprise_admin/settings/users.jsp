<%
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
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
boolean termsOfUseRequired = ParamUtil.getBoolean(request, "settings(" + PropsKeys.TERMS_OF_USE_REQUIRED + ")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED, PropsValues.TERMS_OF_USE_REQUIRED));
boolean usersScreenNameAlwaysAutogenerate = ParamUtil.getBoolean(request, "settings(" + PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE + ")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE, PropsValues.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE));
boolean fieldEnableMale = ParamUtil.getBoolean(request, "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE + ")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE, PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE));
boolean fieldEnableBirthday = ParamUtil.getBoolean(request, "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY + ")", PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY, PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY));

String adminReservedScreenNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_RESERVED_SCREEN_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_SCREEN_NAMES));
String adminReservedEmailAddresses = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES));

String adminDefaultGroupNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_GROUP_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES));
String adminDefaultRoleNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_ROLE_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES));
String adminDefaultUserGroupNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES));
%>

<h3><liferay-ui:message key="users" /></h3>

<liferay-ui:tabs
	names="fields,reserved-credentials,default-user-associations"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input inlineLabel="<%= true %>" label="terms-of-use-required" name='<%= "settings(" + PropsKeys.TERMS_OF_USE_REQUIRED + ")" %>' type="checkbox" value="<%= termsOfUseRequired %>" />

			<aui:input inlineLabel="<%= true %>" label="autogenerate-user-screen-names" name='<%= "settings(" + PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE + ")" %>' type="checkbox" value="<%= usersScreenNameAlwaysAutogenerate %>" />

			<aui:input inlineLabel="<%= true %>" label="enable-birthday" name='<%= "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_BIRTHDAY + ")" %>' type="checkbox" value="<%= fieldEnableBirthday %>" />

			<aui:input inlineLabel="<%= true %>" label="enable-gender" name='<%= "settings(" + PropsKeys.FIELD_ENABLE_COM_LIFERAY_PORTAL_MODEL_CONTACT_MALE + ")" %>' type="checkbox" value="<%= fieldEnableMale %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input helpMessage="enter-one-screen-name-per-line-to-reserve-the-screen-name" label="screen-names" name='<%= "settings(" + PropsKeys.ADMIN_RESERVED_SCREEN_NAMES + ")" %>' type="textarea" value="<%= adminReservedScreenNames %>" />

			<aui:input helpMessage="enter-one-user-email-address-per-line-to-reserve-the-user-email-address" label="email-addresses" name='<%= "settings(" + PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES + ")" %>' type="textarea" value="<%= adminReservedEmailAddresses %>" />
		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<aui:fieldset>
			<aui:input helpMessage="enter-the-default-community-names-per-line-that-are-associated-with-newly-created-users" label="communities" name='<%= "settings(" + PropsKeys.ADMIN_DEFAULT_GROUP_NAMES + ")" %>' type="textarea" value="<%= adminDefaultGroupNames %>" />

			<aui:input helpMessage="enter-the-default-role-names-per-line-that-are-associated-with-newly-created-users" label="roles" name='<%= "settings(" + PropsKeys.ADMIN_DEFAULT_ROLE_NAMES + ")" %>' type="textarea" value="<%= adminDefaultRoleNames %>" />

			<aui:input helpMessage="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" label="user-groups" name='<%= "settings(" + PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES + ")" %>' type="textarea" value="<%= adminDefaultUserGroupNames %>" />
		</aui:fieldset>
	</liferay-ui:section>
</liferay-ui:tabs>