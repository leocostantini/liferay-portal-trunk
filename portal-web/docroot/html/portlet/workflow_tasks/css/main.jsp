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

<%@ include file="/html/portlet/css_init.jsp" %>

<%@ page import="com.liferay.portal.kernel.util.StringUtil" %>

<liferay-util:buffer var="html">
	<liferay-util:include page="/html/portlet/document_library/css/main.jsp" />
</liferay-util:buffer>

<%
html = StringUtil.replace(html, "documentLibraryPanelContainer", "workfloTaskPanelContainer");
html = StringUtil.replace(html, "file-entries", "tasks");
html = StringUtil.replace(html, "file-entry", "task");
html = StringUtil.replace(html, "portlet-document-library", "portlet-workflow-tasks");
%>

<%= html %>

.portlet-workflow-tasks label {
	font-weight: bold;
}

.portlet-workflow-tasks .task-activity {
	padding: 5px 5px 5px 25px;
}

.portlet-workflow-tasks .task-activity-date {
	font-weight: bold;
}

.portlet-workflow-tasks .task-author {
	background: url(<%= themeImagesPath %>/common/assign.png) no-repeat 0 50%;
	border-width: 0;
	color: #000;
	float: none;
	margin-right: 10px;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-workflow-tasks h3.task-content-title {
	margin-top: 0;
	border-bottom: 1px solid #ddd;
}

.portlet-workflow-tasks .task-panel-container .lfr-panel-content {
	padding: 0.7em;
}

.portlet-workflow-tasks .task-content-actions {
	float: right;
}

.portlet-workflow-tasks .task-column-first .task-column-content {
	margin-right: 0;
}

.portlet-workflow-tasks .task-due-date {
	background: url(<%= themeImagesPath %>/common/time.png) no-repeat 0 50%;
	padding-left: 25px;
}

.portlet-workflow-tasks .task-type-0 {
	background: url(<%= themeImagesPath %>/common/recent_changes.png) no-repeat 0 50%;
}

.portlet-workflow-tasks .task-type-1 {
	background: url(<%= themeImagesPath %>/common/assign.png) no-repeat 0 50%;
}

.portlet-workflow-tasks .task-type-2 {
	background: url(<%= themeImagesPath %>/common/time.png) no-repeat 0 50%;
	padding-left: 30px;
}

.portlet-workflow-tasks .task-date {
	background: url(<%= themeImagesPath %>/common/date.png) no-repeat 0 50%;
	border-width: 0;
	color: #000;
	float: none;
	margin-right: 10px;
	overflow: auto;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-workflow-tasks .task-status {
	background: url(<%= themeImagesPath %>/common/recent_changes.png) no-repeat 0 50%;
	color: #000;
	padding-left: 25px;
	padding-right: 10px;
}

.portlet-workflow-tasks .lfr-portlet-toolbar .lfr-toolbar-button.completed-button a {
	background-image: url(<%= themeImagesPath %>/common/checked.png);
}

.portlet-workflow-tasks .lfr-portlet-toolbar .lfr-toolbar-button.assigned-to-me a {
	background-image: url(<%= themeImagesPath %>/common/assign.png);
}

.portlet-workflow-tasks .lfr-portlet-toolbar .lfr-toolbar-button.assigned-to-my-role a {
	background-image: url(<%= themeImagesPath %>/common/assign_user_roles.png);
}

.portlet-workflow-tasks .user-avatar {
	vertical-align: middle;
	width: 25px;
}