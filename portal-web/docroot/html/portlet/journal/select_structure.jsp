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

<%@ include file="/html/portlet/journal/init.jsp" %>

<aui:form method="post" name="fm">

	<%
	String tabs1 = ParamUtil.getString(request, "tabs1", "structures");

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/journal/select_structure");
	portletURL.setParameter("tabs1", tabs1);

	StructureSearch searchContainer = new StructureSearch(renderRequest, portletURL);

	searchContainer.setDelta(10);

	String tabs1Names = "shared-structures";

	if (scopeGroupId != themeDisplay.getCompanyGroupId()) {
		tabs1Names = "structures," + tabs1Names;
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		url="<%= portletURL.toString() %>"
	/>

	<liferay-ui:search-form
		page="/html/portlet/journal/structure_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
	StructureSearchTerms searchTerms = (StructureSearchTerms)searchContainer.getSearchTerms();

	if (tabs1.equals("shared-structures")) {
		long companyGroupId = themeDisplay.getCompanyGroupId();

		searchTerms.setGroupId(companyGroupId);
	}
	%>

	<%@ include file="/html/portlet/journal/structure_search_results.jspf" %>

	<div class="separator"><!-- --></div>

	<%

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalStructure structure = (JournalStructure)results.get(i);

		ResultRow row = new ResultRow(structure, structure.getId(), i);

		StringBundler sb = new StringBundler(7);

		sb.append("javascript:opener.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectStructure('");
		sb.append(structure.getStructureId());
		sb.append("', '");
		sb.append(structure.getName());
		sb.append("'); window.close();");

		String rowHREF = sb.toString();

		// Structure id

		row.addText(structure.getStructureId(), rowHREF);

		// Name and description

		if (Validator.isNotNull(structure.getDescription())) {
			row.addText(structure.getName().concat("<br />").concat(structure.getDescription()), rowHREF);
		}
		else {
			row.addText(structure.getName(), rowHREF);
		}

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />searchStructureId);
</aui:script>