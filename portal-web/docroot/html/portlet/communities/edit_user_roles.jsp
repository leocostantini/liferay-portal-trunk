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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

String groupName = group.getName();

Role role = (Role)request.getAttribute(WebKeys.ROLE);

long roleId = BeanParamUtil.getLong(role, request, "roleId");

int roleType = RoleConstants.TYPE_COMMUNITY;

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());

	groupName = organization.getName();

	roleType = RoleConstants.TYPE_ORGANIZATION;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/communities/edit_user_roles");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));

// Breadcrumbs

if (organization != null) {
	EnterpriseAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);
}
else if (group != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(), null);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-user-roles"), portletURL.toString());

if (role != null) {
	portletURL.setParameter("roleId", String.valueOf(roleId));

	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.escape(role.getTitle(locale)), currentURL);
}

request.setAttribute("edit_user_roles.jsp-tabs1", tabs1);

request.setAttribute("edit_user_roles.jsp-cur", cur);

request.setAttribute("edit_user_roles.jsp-redirect", redirect);

request.setAttribute("edit_user_roles.jsp-group", group);
request.setAttribute("edit_user_roles.jsp-groupName", groupName);
request.setAttribute("edit_user_roles.jsp-role", role);
request.setAttribute("edit_user_roles.jsp-roleId", roleId);
request.setAttribute("edit_user_roles.jsp-roleType", roleType);
request.setAttribute("edit_user_roles.jsp-organization", organization);

request.setAttribute("edit_user_roles.jsp-portletURL", portletURL);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
	<aui:input name="roleId" type="hidden" value="<%= roleId %>" />

	<%= LanguageUtil.get(pageContext, "assign-" + (group.isOrganization() ? "organization" : "community") + "-roles-to-users") %>

	<br /><br />

	<c:choose>
		<c:when test="<%= role == null %>">
			<liferay-util:include page="/html/portlet/communities/edit_user_roles_role.jsp" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portlet/communities/edit_user_roles_users.jsp" />
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />updateUserGroupRoleUsers(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'user_group_role_users';
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;

		Liferay.Util.listCheckedExcept(
			document.<portlet:namespace />fm,
			'<portlet:namespace />allRowIds',
			function(addUsers) {
				document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = addUsers;

				Liferay.Util.listUncheckedExcept(
					document.<portlet:namespace />fm,
					'<portlet:namespace />allRowIds',
					function(removeUsers) {
						document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = removeUsers;

						submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/communities/edit_user_roles" /></portlet:actionURL>');
					}
				);
			}
		);
	}
</aui:script>