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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String type = ParamUtil.getString(request, "type");
long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

if (wikiPage != null) {
	portletURL.setParameter("title", wikiPage.getTitle());
}

if (type.equals("all_pages")) {
	portletURL.setParameter("struts_action", "/wiki/view_all_pages");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "all-pages"), portletURL.toString());
}
else if (type.equals("categorized_pages")) {
	portletURL.setParameter("struts_action", "/wiki/view_categorized_pages");
	portletURL.setParameter("categoryId", String.valueOf(categoryId));

	AssetUtil.addPortletBreadcrumbEntries(categoryId, request, PortletURLUtil.clone(portletURL, renderResponse));
}
else if (type.equals("history")) {
	if (wikiPage != null) {
		portletURL.setParameter("struts_action", "/wiki/view");

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());
	}

	portletURL.setParameter("struts_action", "/wiki/view_page_history");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "history"), portletURL.toString());
}
else if (type.equals("incoming_links")) {
	if (wikiPage != null) {
		portletURL.setParameter("struts_action", "/wiki/view");

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());
	}

	portletURL.setParameter("struts_action", "/wiki/view_page_incoming_links");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "incoming-links"), portletURL.toString());
}
else if (type.equals("orphan_pages")) {
	portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "orphan-pages"), portletURL.toString());
}
else if (type.equals("outgoing_links")) {
	if (wikiPage != null) {
		portletURL.setParameter("struts_action", "/wiki/view");

		PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());
	}

	portletURL.setParameter("struts_action", "/wiki/view_page_outgoing_links");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "outgoing-links"), portletURL.toString());
}
else if (type.equals("recent_changes")) {
	portletURL.setParameter("struts_action", "/wiki/view_recent_changes");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "recent-changes"), portletURL.toString());
}
else if (type.equals("tagged_pages")) {
	portletURL.setParameter("struts_action", "/wiki/view_tagged_pages");
	portletURL.setParameter("tag", tagName);

	PortalUtil.addPortletBreadcrumbEntry(request, tagName, portletURL.toString());
}

List<String> headerNames = new ArrayList<String>();

headerNames.add("page");
headerNames.add("status");
headerNames.add("revision");
headerNames.add("user");
headerNames.add("date");

if (type.equals("history") || type.equals("recent_changes")) {
	headerNames.add("summary");
}

if (type.equals("all_pages") || type.equals("categorized_pages") || type.equals("history") || type.equals("orphan_pages") || type.equals("recent_changes") || type.equals("tagged_pages")) {
	headerNames.add(StringPool.BLANK);
}

String emptyResultsMessage = null;

if (type.equals("categorized_pages")) {
	emptyResultsMessage = "there-are-no-pages-with-this-category";
}
else if (type.equals("incoming_links")) {
	emptyResultsMessage = "there-are-no-pages-that-link-to-this-page";
}
else if (type.equals("outgoing_links")) {
	emptyResultsMessage = "this-page-has-no-links";
}
else if (type.equals("recent_changes")) {
	emptyResultsMessage = "there-are-no-recent-changes";
}
else if (type.equals("tagged_pages")) {
	emptyResultsMessage = "there-are-no-pages-with-this-tag";
}

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, emptyResultsMessage);

if (type.equals("history")) {
	searchContainer.setRowChecker(new RowChecker(renderResponse, RowChecker.ALIGN, RowChecker.VALIGN, RowChecker.FORM_NAME, null, RowChecker.ROW_IDS));
}

int total = 0;
List<WikiPage> results = new ArrayList<WikiPage>();

if (type.equals("all_pages")) {
	total = WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true);
	results = WikiPageLocalServiceUtil.getPages(node.getNodeId(), true, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("categorized_pages") || type.equals("tagged_pages")) {
	AssetEntryQuery assetEntryQuery = new AssetEntryQuery(WikiPage.class.getName(), searchContainer);

	total = AssetEntryLocalServiceUtil.getEntriesCount(assetEntryQuery);
	List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

	for (AssetEntry assetEntry : assetEntries) {
		WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.getPageResource(assetEntry.getClassPK());

		WikiPage assetPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

		results.add(assetPage);
	}
}
else if (type.equals("orphan_pages")) {
	List<WikiPage> orphans = WikiPageLocalServiceUtil.getOrphans(node.getNodeId());

	total = orphans.size();
	results = ListUtil.subList(orphans, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("history")) {
	total = WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle());
	results = WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());
}
else if (type.equals("incoming_links")) {
	List<WikiPage> links = WikiPageLocalServiceUtil.getIncomingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

	total = links.size();
	results = ListUtil.subList(links, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("outgoing_links")) {
	List<WikiPage> links = WikiPageLocalServiceUtil.getOutgoingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

	total = links.size();
	results = ListUtil.subList(links, searchContainer.getStart(), searchContainer.getEnd());
}
else if (type.equals("recent_changes")) {
	total = WikiPageLocalServiceUtil.getRecentChangesCount(node.getNodeId());
	results = WikiPageLocalServiceUtil.getRecentChanges(node.getNodeId(), searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setTotal(total);
searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	WikiPage curWikiPage = results.get(i);

	curWikiPage = curWikiPage.toEscapedModel();

	ResultRow row = new ResultRow(curWikiPage, String.valueOf(curWikiPage.getVersion()), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	if (!curWikiPage.isNew()) {
		rowURL.setParameter("struts_action", "/wiki/view");
		rowURL.setParameter("nodeName", curWikiPage.getNode().getName());
	}
	else {
		rowURL.setParameter("struts_action", "/wiki/edit_page");
		rowURL.setParameter("nodeId", String.valueOf(curWikiPage.getNodeId()));
	}

	rowURL.setParameter("title", HtmlUtil.unescape(curWikiPage.getTitle()));

	if (type.equals("history")) {
		rowURL.setParameter("version", String.valueOf(curWikiPage.getVersion()));
	}

	// Title

	row.addText(curWikiPage.getTitle(), rowURL);

	// Status

	String status = null;

	if (curWikiPage.getStatus() == StatusConstants.APPROVED) {
		status = "approved";
	}
	else {
		status = "not-approved";
	}

	row.addText(LanguageUtil.get(pageContext, status), rowURL);

	// Revision

	if (!curWikiPage.isNew()) {
		String revision = String.valueOf(curWikiPage.getVersion());

		if (curWikiPage.isMinorEdit()) {
			revision += " (" + LanguageUtil.get(pageContext, "minor-edit") + ")";
		}

		row.addText(revision, rowURL);
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// User

	if (!curWikiPage.isNew()) {
		row.addText(HtmlUtil.escape(PortalUtil.getUserName(curWikiPage.getUserId(), curWikiPage.getUserName())), rowURL);
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// Date

	if (!curWikiPage.isNew()) {
		row.addText(dateFormatDateTime.format(curWikiPage.getCreateDate()), rowURL);
	}
	else {
		row.addText(StringPool.BLANK);
	}

	// Summary

	if (type.equals("history") || type.equals("recent_changes")) {
		if (Validator.isNotNull(curWikiPage.getSummary())) {
			row.addText(curWikiPage.getSummary());
		}
		else {
			row.addText(StringPool.BLANK);
		}
	}

	// Action

	if (type.equals("history")) {
		if (curWikiPage.isHead()) {
			row.addText(StringPool.BLANK);
		}
		else {
			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wiki/page_history_action.jsp");
		}
	}

	if (type.equals("all_pages") || type.equals("orphan_pages") || type.equals("recent_changes") || type.equals("tagged_pages")) {
		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wiki/page_action.jsp");
	}

	// Add result row

	resultRows.add(row);
}
%>

<c:if test='<%= type.equals("history") && (results.size() > 1) %>'>

	<%
	WikiPage latestWikiPage = (WikiPage)results.get(1);
	%>

	<portlet:renderURL var="compareVersionsURL">
		<portlet:param name="struts_action" value="/wiki/compare_versions" />
	</portlet:renderURL>

	<aui:form action="<%= compareVersionsURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "compare();" %>'>
		<aui:input name="backURL" type="hidden" value="<%= currentURL %>" />
		<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />
		<aui:input name="title" type="hidden" value="<%= wikiPage.getTitle() %>" />
		<aui:input name="sourceVersion" type="hidden" value="<%= latestWikiPage.getVersion() %>" />
		<aui:input name="targetVersion" type="hidden" value="<%= wikiPage.getVersion() %>" />
		<aui:input name="type" type="hidden" value="html" />

		<aui:button-row>
			<aui:button name="submitButton" type="submit" value="compare-versions" />
		</aui:button-row>
	</aui:form>
</c:if>

<c:if test='<%= type.equals("all_pages") && WikiNodePermission.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_PAGE) %>'>
	<aui:button-row>
		<portlet:actionURL var="addPageURL">
			<portlet:param name="struts_action" value="/wiki/edit_page" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
			<portlet:param name="title" value="<%= StringPool.BLANK %>" />
			<portlet:param name="editTitle" value="1" />
		</portlet:actionURL>

		<aui:button name="addPageButton" onClick="<%= addPageURL %>" type="button" value="add-page" />
	</aui:button-row>
</c:if>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate='<%= type.equals("history") ? false : true %>' />

<c:if test='<%= type.equals("history") %>'>
	<aui:script>
		function <portlet:namespace />compare() {
			AUI().use(
				'selector-css3',
				function(A) {
					var rowIds = A.all('input[name=<portlet:namespace />rowIds]:checked');
					var sourceVersion = A.one('input[name="<portlet:namespace />sourceVersion"]');
					var targetVersion = A.one('input[name="<portlet:namespace />targetVersion"]');

					var rowIdsSize = rowIds.size();

					if (rowIdsSize == 1) {
						if (sourceVersion) {
							sourceVersion.val(rowIds.item(0).val());
						}
					}
					else if (rowIdsSize == 2) {
						if (sourceVersion) {
							sourceVersion.val(rowIds.item(1).val());
						}

						if (targetVersion) {
							targetVersion.val(rowIds.item(0).val());
						}
					}

					submitForm(document.<portlet:namespace />fm);
				}
			);
		}

		function <portlet:namespace />initRowsChecked() {
			var rowIds = AUI().all('input[name=<portlet:namespace />rowIds]');

			rowIds.each(
				function(item, index, collection) {
					if (index >= 2) {
						item.set('checked', false);
					}
				}
			);
		}

		function <portlet:namespace />updateRowsChecked(element) {
			AUI().use(
				'selector-css3',
				function(A) {
					var rowsChecked = A.all('input[name=<portlet:namespace />rowIds]:checked');

					if (rowsChecked.size() > 2) {
						var index = 2;

						if (rowsChecked.item(2).compareTo(element)) {
							index = 1;
						}

						rowsChecked.item(index).set('checked', false);
					}
				}
			);
		}
	</aui:script>

	<aui:script use="event,node">
		<portlet:namespace />initRowsChecked();

		A.all('input[name=<portlet:namespace />rowIds]').on(
			'click',
			function(event) {
				<portlet:namespace />updateRowsChecked(event.currentTarget);
			}
		);
	</aui:script>
</c:if>