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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
BookmarksEntry entry = (BookmarksEntry)request.getAttribute(WebKeys.BOOKMARKS_ENTRY);
%>

<aui:a href='<%= themeDisplay.getPathMain() + "/bookmarks/open_entry?entryId=" + entry.getEntryId() %>' target="_blank"><%= entry.getName() %> (<%= entry.getUrl() %>)</aui:a>

<p class="asset-description"><%= entry.getComments() %></p>

<liferay-ui:custom-attributes-available className="<%= BookmarksEntry.class.getName() %>">
	<liferay-ui:custom-attribute-list
		className="<%= BookmarksEntry.class.getName() %>"
		classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
		editable="<%= false %>"
		label="<%= true %>"
	/>
</liferay-ui:custom-attributes-available>