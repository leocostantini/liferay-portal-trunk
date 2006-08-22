<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/alfresco_content/init.jsp" %>

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>">

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "user-id") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />userId" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= userId %>">
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "password") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />password" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= password %>">
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "alfresco-web-client-url") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<input class="form-text" name="<portlet:namespace />alfrescoWebClientURL" style="width: <%= ModelHintsDefaults.TEXT_DISPLAY_WIDTH %>px;" type="text" value="<%= alfrescoWebClientURL %>">
	</td>
</tr>
<tr>
	<td colspan="3">
		<br>
	</td>
</tr>
<tr>
	<td>
		<%= LanguageUtil.get(pageContext, "maximize-links") %>
	</td>
	<td style="padding-left: 10px;"></td>
	<td>
		<liferay-ui:input-checkbox param="maximizeLinks" defaultValue="<%= maximizeLinks %>" />
	</td>
</tr>
</table>

<br>

<input class="portlet-form-button" type="button" value="<bean:message key="save" />" onClick="submitForm(document.<portlet:namespace />fm);">

<liferay-portlet:renderURL portletConfiguration="true" varImpl="portletURL" />

<input name="<portlet:namespace />apaceUuid" type="hidden" value="">
<input name="<portlet:namespace />contentUuid" type="hidden" value="">
<input name="<portlet:namespace />nodeUuid" type="hidden" value="">

<%
DynamicRenderRequest dynamicRenderReq = new DynamicRenderRequest(renderRequest);

AlfrescoContentSearch searchContainer = new AlfrescoContentSearch(dynamicRenderReq, portletURL);
%>

<c:if test="<%= null != null %>">
	<br>

	<%-- = LanguageUtil.get(pageContext, "displaying-article") %>: <%= article.getArticleId() --%><br>
</c:if>

<%
String spaceUuid = ParamUtil.getString(renderRequest, "spaceUuid");

ResultSetRow[] resultSetRows = {};
try {
	resultSetRows = AlfrescoContentLocalServiceUtil.getNodes(spaceUuid, alfrescoWebClientURL, userId, password);
}
catch (Exception e) {
e.printStackTrace();
}

int total = resultSetRows.length;

searchContainer.setTotal(total);

List results = Arrays.asList(resultSetRows);

searchContainer.setResults(results);
%>

<br><div class="beta-separator"></div><br>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	ResultSetRow resultSetRow = (ResultSetRow)results.get(i);

	ResultSetRowNode node = resultSetRow.getNode();
	
	ResultRow row = new ResultRow(node, node.getId(), i);

    NamedValue namedValues[] = resultSetRow.getColumns();

	StringBuffer sb = new StringBuffer();

	sb.append("javascript: ");

	String propContent = AlfrescoContentUtil.getNamedValue(namedValues, org.alfresco.webservice.util.Constants.PROP_CONTENT);
	
	if (propContent != null) {
		sb.append("document.");
		sb.append(renderResponse.getNamespace());
		sb.append("fm.");
		sb.append(renderResponse.getNamespace());
		sb.append(Constants.CMD);
		sb.append(".value = '");
		sb.append(Constants.UPDATE);
		sb.append("'; ");
		sb.append("document.");
		sb.append(renderResponse.getNamespace());
		sb.append("fm.");
		sb.append(renderResponse.getNamespace());
		sb.append("nodeUuid.value = '");
		sb.append(node.getId());
		sb.append("'; ");
	}
	else {
		sb.append("document.");
		sb.append(renderResponse.getNamespace());
		sb.append("fm.");
		sb.append(renderResponse.getNamespace());
		sb.append("spaceUuid.value = '");
		sb.append(node.getId());
		sb.append("'; ");
	}
	sb.append("submitForm(document.");
	sb.append(renderResponse.getNamespace());
	sb.append("fm);");

	String rowHREF = sb.toString();

	// Name
	    
	row.addText(AlfrescoContentUtil.getNamedValue(namedValues, org.alfresco.webservice.util.Constants.PROP_NAME), rowHREF);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</form>


<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />baseURL.focus();
</script>