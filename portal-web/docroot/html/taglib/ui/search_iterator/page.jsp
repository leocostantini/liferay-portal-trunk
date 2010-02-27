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

<%@ include file="/html/taglib/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));

String id = searchContainer.getId();
String randomId = randomId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
int start = searchContainer.getStart();
int end = searchContainer.getEnd();
int total = searchContainer.getTotal();
List resultRows = searchContainer.getResultRows();
List<String> headerNames = searchContainer.getHeaderNames();
Map orderableHeaders = searchContainer.getOrderableHeaders();
String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
RowChecker rowChecker = searchContainer.getRowChecker();

if (end > total) {
	end = total;
}

if (rowChecker != null) {
	if (headerNames != null) {
		headerNames.add(0, rowChecker.getAllRowsCheckBox());
	}
}

String url = StringPool.BLANK;

PortletURL iteratorURL = searchContainer.getIteratorURL();

if (iteratorURL != null) {
	url = iteratorURL.toString();
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByColParam());
	url = HttpUtil.removeParameter(url, namespace + searchContainer.getOrderByTypeParam());
}

List<String> primaryKeys = new ArrayList<String>();
%>

<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
	<div class="portlet-msg-info">
		<%= LanguageUtil.get(pageContext, emptyResultsMessage) %>
	</div>
</c:if>

<c:if test="<%= !resultRows.isEmpty() %>">
	<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP && (resultRows.size() > 10) && paginate %>">
		<div class="taglib-search-iterator-page-iterator-top">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</div>
	</c:if>

	<div class="results-grid"
		<c:if test="<%= Validator.isNotNull(id) %>">
			id="<%= id %>SearchContainer"
		</c:if>
	>
		<table class="taglib-search-iterator">

		<c:if test="<%= headerNames != null %>">
			<tr class="portlet-section-header results-header">

			<%
			for (int i = 0; i < headerNames.size(); i++) {
				String headerName = headerNames.get(i);

				String orderKey = null;
				String orderByType = null;
				boolean orderCurrentHeader = false;

				if (orderableHeaders != null) {
					orderKey = (String)orderableHeaders.get(headerName);

					if (orderKey != null) {
						orderByType = searchContainer.getOrderByType();

						if (orderKey.equals(searchContainer.getOrderByCol())) {
							orderCurrentHeader = true;
						}
					}
				}

				String cssClass = StringPool.BLANK;

				if (orderCurrentHeader) {
					cssClass = "sort-column";

					if (orderByType.equals("asc")) {
						orderByType = "desc";
					}
					else {
						orderByType = "asc";
					}

					cssClass += " sort-" + orderByType;
				}
			%>

				<th class="col-<%= i + 1 %> <%= cssClass %>" id="<%=randomId%>_col-<%= i + 1 %>"

					<%--

					// Maximize the width of the second column if and only if the first
					// column is a row checker and there is only one second column.

					--%>

					<c:if test="<%= (rowChecker != null) && (headerNames.size() == 2) && (i == 1) %>">
						width="95%"
					</c:if>
				>

					<c:if test="<%= orderKey != null %>">
						<span class="result-column-name">
							<a href="<%= url %>&<%= namespace %><%= searchContainer.getOrderByColParam() %>=<%= orderKey %>&<%= namespace %><%= searchContainer.getOrderByTypeParam() %>=<%= orderByType %>">
					</c:if>

						<%
						String headerNameValue = LanguageUtil.get(pageContext, headerName);
						%>

						<c:choose>
							<c:when test="<%= Validator.isNull(headerNameValue) %>">
								<%= StringPool.NBSP %>
							</c:when>
							<c:otherwise>
								<%= headerNameValue %>
							</c:otherwise>
						</c:choose>

					<c:if test="<%= orderKey != null %>">
							</a>
						</span>
					</c:if>
				</th>

			<%
			}
			%>

			</tr>
			<tr class="lfr-template portlet-section-body results-row">

				<%
				for (int i = 0; i < headerNames.size(); i++) {
				%>

					<td></td>

				<%
				}
				%>

			</tr>
		</c:if>

		<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
			<tr class="portlet-section-body results-row last">
				<td align="center" class="only" colspan="<%= (headerNames == null) ? 1 : headerNames.size() %>">
					<%= LanguageUtil.get(pageContext, emptyResultsMessage) %>
				</td>
			</tr>
		</c:if>

		<%
		boolean allRowsIsChecked = true;

		for (int i = 0; i < resultRows.size(); i++) {
			ResultRow row = (ResultRow)resultRows.get(i);

			String rowClassName = "portlet-section-alternate results-row alt";
			String rowClassHoverName = "portlet-section-alternate-hover results-row alt hover";

			primaryKeys.add(row.getPrimaryKey());

			if (MathUtil.isEven(i)) {
				rowClassName = "portlet-section-body results-row";
				rowClassHoverName = "portlet-section-body-hover results-row hover";
			}

			if (Validator.isNotNull(row.getClassName())) {
				rowClassName += " " + row.getClassName();
			}

			if (Validator.isNotNull(row.getClassHoverName())) {
				rowClassHoverName += " " + row.getClassHoverName();
			}

			if (row.isRestricted()) {
				rowClassName += " restricted";
				rowClassHoverName += " restricted";
			}

			if ((i + 1) == resultRows.size()) {
				rowClassName += " last";
				rowClassHoverName += " last";
			}

			row.setClassName(rowClassName);
			row.setClassHoverName(rowClassHoverName);

			request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

			List entries = row.getEntries();

			if (rowChecker != null) {
				boolean rowIsChecked = rowChecker.isChecked(row.getObject());

				if (!rowIsChecked) {
					allRowsIsChecked = false;
				}

				row.addText(0, rowChecker.getAlign(), rowChecker.getValign(), rowChecker.getColspan(), rowChecker.getRowCheckBox(rowIsChecked, row.getPrimaryKey()));
			}
		%>

			<tr class="<%= rowClassName %>"
				<c:if test="<%= searchContainer.isHover() %>">
					onmouseover="this.className = '<%= rowClassHoverName %>';" onmouseout="this.className = '<%= rowClassName %>';"
				</c:if>
			>

			<%
			for (int j = 0; j < entries.size(); j++) {
				SearchEntry entry = (SearchEntry)entries.get(j);

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);

				String columnClassName = StringPool.BLANK;

				if (entries.size() == 1) {
					columnClassName = " only";
				}
				else if (j == 0) {
					columnClassName = " first";
				}
				else if ((j + 1) == entries.size()) {
					columnClassName = " last";
				}
			%>

				<td align="<%= entry.getAlign() %>" class="col-<%= j + 1 %><%= row.isBold() ? " taglib-search-iterator-highlighted" : "" %><%= columnClassName %>" colspan="<%= entry.getColspan() %>" headers="<%=randomId%>_col-<%= j + 1 %>" valign="<%= entry.getValign() %>">

					<%
					entry.print(pageContext);
					%>

				</td>

			<%
			}
			%>

			</tr>

		<%
		}
		%>

		</table>
	</div>

	<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_BOTTOM && paginate %>">
		<div class="taglib-search-iterator-page-iterator-bottom">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</div>
	</c:if>

	<c:if test="<%= (rowChecker != null) && !resultRows.isEmpty() && Validator.isNotNull(rowChecker.getAllRowsId()) && allRowsIsChecked %>">
		<aui:script>
			document.<%= rowChecker.getFormName() %>.<%= rowChecker.getAllRowsId() %>.checked = true;
		</aui:script>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(id) %>">
	<input id="<%= id %>PrimaryKeys" name="<%= id %>PrimaryKeys" type="hidden" value="<%= StringUtil.merge(primaryKeys) %>" />

	<aui:script use="liferay-search-container">
		new Liferay.SearchContainer(
			{
				id: '<%= id %>'
			}
		).render();
	</aui:script>
</c:if>