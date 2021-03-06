<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute(WebKeys.LAYOUT_REVISION);

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());

Layout targetLayout = LayoutLocalServiceUtil.getLayout(layoutRevision.getPlid());

String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(targetLayout, themeDisplay);
%>

<strong><liferay-ui:message key="layout" />:</strong> <a href="<%= layoutFriendlyURL + "?layoutSetBranchId=" + layoutRevision.getLayoutSetBranchId() + "&layoutRevisionId=" + layoutRevision.getLayoutRevisionId() %>"><%= targetLayout.getHTMLTitle(locale) %></a><br />

<strong><liferay-ui:message key="branch" />:</strong> <%= layoutSetBranch.getName() %><br />

<strong><liferay-ui:message key="revision-id" />:</strong> <%= layoutRevision.getLayoutRevisionId() %>