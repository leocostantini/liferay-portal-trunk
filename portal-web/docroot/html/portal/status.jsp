<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);
%>

<c:choose>
	<c:when test="<%= SessionErrors.contains(request, NoSuchLayoutException.class.getName()) || SessionErrors.contains(request, NoSuchFileException.class.getName()) || SessionErrors.contains(request, NoSuchFileEntryException.class.getName()) || SessionErrors.contains(request, NoSuchLayoutSetException.class.getName()) || SessionErrors.contains(request, NoSuchImageException.class.getName()) || SessionErrors.contains(request, NoSuchGroupException.class.getName()) %>">
		<h3><liferay-ui:message key="not-found" /></h3>

		<br />
		<br />

		<liferay-ui:message key="the-requested-resource-was-not-found" />

		<br />

		<code><%= PortalUtil.getPortalURL(request) + currentURL %></code>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, PrincipalException.class.getName()) %>">
		<h3><liferay-ui:message key="forbidden" /></h3>

		<br />
		<br />

		<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />

		<br />

		<code><%= PortalUtil.getPortalURL(request) + currentURL %></code>
	</c:when>
	<c:when test="<%= SessionErrors.contains(request, SystemException.class.getName()) || SessionErrors.contains(request, PortalException.class.getName()) %>">
		<h3><liferay-ui:message key="internal-server-error" /></h3>

		<br />
		<br />

		<liferay-ui:message key="an-error-occurred-while-attempting-to-access-the-requested-resource" />

		<br />

		<code><%= PortalUtil.getPortalURL(request) + currentURL %></code>
	</c:when>
	<c:otherwise>
		<h3><liferay-ui:message key="internal-server-error" /></h3>

		<br />
		<br />

		<liferay-ui:message key="an-error-occurred-while-attempting-to-access-the-requested-resource" />

		<br />

		<code><%= PortalUtil.getPortalURL(request) + currentURL %></code>
	</c:otherwise>
</c:choose>

<br />
<br />

<div class="separator"><!----></div>

<a href="javascript: history.go(-1);"><liferay-ui:message key="back" /></a>