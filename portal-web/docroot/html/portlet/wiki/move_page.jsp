<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();
String newTitle = ParamUtil.get(request, "newTitle", StringPool.BLANK);

PortletURL pageURL = renderResponse.createRenderURL();

pageURL.setParameter("struts_action", "/wiki/view");
pageURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
pageURL.setParameter("title", title);
%>

<liferay-util:include page="/html/portlet/wiki/node_tabs.jsp" />

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-valid-title" />
<liferay-ui:error exception="<%= PageAlreadyExistsException.class %>" message="there-is-already-a-page-with-the-specified-title" />

<%@ include file="/html/portlet/wiki/page_name.jspf" %>

<div class="portlet-msg-info">
	<liferay-ui:message key="using-the-form-below-will-rename-a-page,-moving-all-of-its-history-to-the-new-name.-The-old-title-will-become-a-redirect-page-to-the-new-title.-Links-to-the-old-page-title-will-not-be-changed" />
</div>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/wiki/move_page" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input type='hidden' name="<portlet:namespace />nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
<input type='hidden' name="<portlet:namespace />title" value="<%= title %>" />
<input type='hidden' name="<portlet:namespace />redirect" value="<%= pageURL.toString() %>" />

<table class="lfr-table">
	<tr>
		<td><liferay-ui:message key="current-title" /></td>
		<td>
			<%= wikiPage.getTitle() %>
		</td>
	</tr>
	<tr>
		<td><label for="<portlet:namespace />newTitle"><liferay-ui:message key="new-title" /></label></td>

		<td>
			<input type='text' name="<portlet:namespace />newTitle" value="<%= newTitle %>" />
		</td>
	</tr>
</table>

<br />

<input type='submit' value="<liferay-ui:message key="move-page" />" />
<input type='button' value="<liferay-ui:message key="cancel" />" onClick="history.go(-1);"/>

</form>