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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MBStatsUser statsUser = (MBStatsUser)row.getObject();

String rank = MBUtil.getUserRank(prefs, themeDisplay.getLanguageId(), statsUser);
%>

<liferay-ui:user-display userId="<%= statsUser.getUserId() %>">
	<c:if test="<%= Validator.isNotNull(rank) %>">
		<liferay-ui:message key="rank" />: <%= rank %><br />
	</c:if>

	<liferay-ui:message key="posts" />: <%= statsUser.getMessageCount() %><br />
	<liferay-ui:message key="join-date" />: <%= dateFormatDate.format(userDisplay.getCreateDate()) %><br />

	<c:if test="<%= statsUser.getLastPostDate() != null %>">
		<liferay-ui:message key="last-post-date" />: <%= dateFormatDate.format(statsUser.getLastPostDate()) %>
	</c:if>
</liferay-ui:user-display>