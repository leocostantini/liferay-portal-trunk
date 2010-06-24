<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
long ldapServerId = ParamUtil.getLong(request, "ldapServerId", 0);

String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

String baseProviderURL = ParamUtil.getString(request, "baseProviderURL");
String baseDN = ParamUtil.getString(request, "baseDN");
String principal = ParamUtil.getString(request, "principal");
String credentials = ParamUtil.getString(request, "credentials");

if (credentials.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
	credentials = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.LDAP_SECURITY_CREDENTIALS + postfix);
}

LdapContext ldapContext = PortalLDAPUtil.getContext(themeDisplay.getCompanyId(), baseProviderURL, principal, credentials);

if (ldapContext == null) {
%>

	<liferay-ui:message key="liferay-has-failed-to-connect-to-the-ldap-server" />

<%
	return;
}

if (Validator.isNull(ParamUtil.getString(request, "groupMappingGroupName")) ||
	Validator.isNull(ParamUtil.getString(request, "groupMappingUser"))) {
%>

	<liferay-ui:message key="please-map-each-of-the-group-properties-group-name-and-user-to-an-ldap-attribute" />

<%
	return;
}

String groupFilter = ParamUtil.getString(request, "importGroupSearchFilter");

List<SearchResult> results = PortalLDAPUtil.getGroups(themeDisplay.getCompanyId(), ldapContext, 20, baseDN, groupFilter);

String groupMappingsParam =
	"groupName=" + ParamUtil.getString(request, "groupMappingGroupName") +
	"\ndescription=" + ParamUtil.getString(request, "groupMappingDescription") +
	"\nuser=" + ParamUtil.getString(request, "groupMappingUser");

Properties groupMappings = PropertiesUtil.load(groupMappingsParam);
%>

<liferay-ui:message key="test-ldap-groups" />

<br /><br />

<liferay-ui:message key="a-subset-of-groups-has-been-displayed-for-you-to-review" />

<br /><br />

<table class="lfr-table">

<%
boolean showMissingAttributeMessage = false;

int counter = 0;

for (SearchResult result : results) {
	Attributes attrs = result.getAttributes();

	String name = LDAPUtil.getAttributeValue(attrs, groupMappings.getProperty("groupName")).toLowerCase();
	String description = LDAPUtil.getAttributeValue(attrs, groupMappings.getProperty("description"));
	Attribute attribute = attrs.get(groupMappings.getProperty("user"));

	if (Validator.isNull(name)) {
		showMissingAttributeMessage = true;
	}

	if (attribute != null) {
		StringBundler sb = new StringBundler(7);

		sb.append("(&");
		sb.append(groupFilter);
		sb.append("(");
		sb.append(groupMappings.getProperty("groupName"));
		sb.append("=");
		sb.append(name);
		sb.append("))");

		String filter = sb.toString();

		attribute = PortalLDAPUtil.getMultivaluedAttribute(themeDisplay.getCompanyId(), ldapContext, baseDN, filter, attribute);
	}

	if (counter == 0) {
%>

		<tr>
			<th>
				#
			</th>
			<th>
				<liferay-ui:message key="name" />
			</th>
			<th>
				<liferay-ui:message key="description" />
			</th>
			<th>
				<liferay-ui:message key="members" />
			</th>
		</tr>

<%
	}

	counter++;
%>

	<tr>
		<td>
			<%= counter %>
		</td>
		<td>
			<%= name %>
		</td>
		<td>
			<%= description %>
		</td>
		<td>
			<%= (attribute == null) ? "0" : String.valueOf(attribute.size()) %>
		</td>
	</tr>

<%
}

if (counter == 0) {
%>

	<tr>
		<td colspan="4">
			<liferay-ui:message key="no-groups-were-found" />
		</td>
	</tr>

<%
}
%>

</table>

<%
if (showMissingAttributeMessage) {
%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="the-above-results-include-groups-which-are-missing-the-required-attributes-(group-name-and-user).-these-groups-will-not-be-imported-until-these-attributes-are-filled-in" />
	</div>

<%
}

if (ldapContext != null) {
	ldapContext.close();
}
%>