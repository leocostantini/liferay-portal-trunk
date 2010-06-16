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

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "web-content");

String orderByCol = ParamUtil.getString(request, "orderByCol");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view_article_history");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(article.getGroupId()));
portletURL.setParameter("articleId", article.getArticleId());
%>

<liferay-util:include page="/html/portlet/journal/article_tabs.jsp">
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />

	<aui:input name="groupId" type="hidden" />
	<aui:input name="deleteArticleIds" type="hidden" />
	<aui:input name="expireArticleIds" type="hidden" />

	<%
	ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(3, "status");
	headerNames.add(StringPool.BLANK);

	if (Validator.isNull(orderByCol)) {
		searchContainer.setOrderByCol("version");
	}

	searchContainer.setRowChecker(new RowChecker(renderResponse));

	ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

	searchTerms.setAdvancedSearch(true);
	searchTerms.setArticleId(article.getArticleId());
	%>

	<%@ include file="/html/portlet/journal/article_search_results.jspf" %>

	<c:if test="<%= !results.isEmpty() %>">
		<aui:button-row>
			<aui:button onClick='<%= renderResponse.getNamespace() + "expireArticles();" %>' value="expire" />

			<aui:button onClick='<%= renderResponse.getNamespace() + "deleteArticles();" %>' value="delete" />
		</aui:button-row>
	</c:if>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalArticle articleVersion = (JournalArticle)results.get(i);

		articleVersion = articleVersion.toEscapedModel();

		ResultRow row = new ResultRow(articleVersion, articleVersion.getArticleId() + EditArticleAction.VERSION_SEPARATOR + articleVersion.getVersion(), i);

		PortletURL rowURL = null;

		// Article id

		row.addText(articleVersion.getArticleId(), rowURL);

		// Title

		row.addText(articleVersion.getTitle(), rowURL);

		// Version

		row.addText(String.valueOf(articleVersion.getVersion()), rowURL);

		// Status

		String status = null;

		if (articleVersion.isApproved()) {
			status = "approved";
		}
		else if (articleVersion.isDraft()) {
			status = "draft";
		}
		else if (articleVersion.isExpired()) {
			status = "expired";
		}
		else if (articleVersion.isPending()) {
			status = "pending";
		}

		row.addText(LanguageUtil.get(pageContext, status), rowURL);

		// Modified date

		row.addText(dateFormatDateTime.format(articleVersion.getModifiedDate()), rowURL);

		// Display date

		row.addText(dateFormatDateTime.format(articleVersion.getDisplayDate()), rowURL);

		// Author

		row.addText(HtmlUtil.escape(PortalUtil.getUserName(articleVersion.getUserId(), articleVersion.getUserName())), rowURL);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/journal/article_version_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:script>
	function <portlet:namespace />deleteArticles() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-version") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.DELETE %>';
			document.<portlet:namespace />fm.<portlet:namespace />groupId.value = '<%= scopeGroupId %>';

			Liferay.Util.listCheckedExcept(
				document.<portlet:namespace />fm,
				'<portlet:namespace />allRowIds',
				function(deleteArticles) {
					document.<portlet:namespace />fm.<portlet:namespace />deleteArticleIds.value = deleteArticles;

					submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
				}
			);
		}
	}

	function <portlet:namespace />expireArticles() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-expire-the-selected-version") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= Constants.EXPIRE %>';
			document.<portlet:namespace />fm.<portlet:namespace />groupId.value = '<%= scopeGroupId %>';

			Liferay.Util.listCheckedExcept(
				document.<portlet:namespace />fm,
				'<portlet:namespace />allRowIds',
				function(expireArticles) {
					document.<portlet:namespace />fm.<portlet:namespace />expireArticleIds.value = expireArticles;

					submitForm(document.<portlet:namespace />fm, '<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
				}
			);
		}
	}
</aui:script>