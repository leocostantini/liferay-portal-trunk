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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

String path = (String)request.getAttribute(WebKeys.CONFIGURATION_ACTION_PATH);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="setup" />
</liferay-util:include>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, layout.getGroupId(), ActionKeys.MANAGE_ARCHIVED_SETUPS) %>">
	<portlet:renderURL var="archivedSetupsURL">
		<portlet:param name="struts_action" value="/portlet_configuration/edit_archived_setups" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
	</portlet:renderURL>

	<div class="archived-setups">
		<liferay-ui:icon
			message="archive-restore-setup"
			image="export"
			label="<%= true %>"
			url="<%= archivedSetupsURL %>"
		/>
	</div>
</c:if>

<c:if test="<%= Validator.isNotNull(portletResource) && Validator.isNotNull(path) %>">
	<liferay-util:include page="<%= path %>" portletId="<%= portletResource %>" />
</c:if>

<c:if test='<%= themeDisplay.isStatePopUp() && SessionMessages.contains(renderRequest, portletName + ".doConfigure") %>'>
	<aui:script use="aui-base">
		if (window.parent) {
			<c:choose>
				<c:when test="<%= !selPortlet.isAjaxable() %>">
					window.parent.location.reload();
				</c:when>
				<c:otherwise>
					var curPortletBoundaryId = '#p_p_id_<%= portletResource %>_';

					window.parent.Liferay.Portlet.refresh(curPortletBoundaryId);
				</c:otherwise>
			</c:choose>
		}
	</aui:script>
</c:if>