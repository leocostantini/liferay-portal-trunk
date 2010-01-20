<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
ArticleSearch searchContainer = (ArticleSearch)request.getAttribute("liferay-ui:search:searchContainer");

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	id="toggle_id_journal_article_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search"
>
	<aui:fieldset>
		<aui:column>
			<aui:input label="id" name="<%= displayTerms.ARTICLE_ID %>" size="20" value="<%= HtmlUtil.escape(displayTerms.getArticleId()) %>" />

			<aui:input name="<%= displayTerms.CONTENT %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getContent()) %>" />
		</aui:column>

		<aui:column>
			<aui:input name="<%= displayTerms.VERSION %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getVersionString()) %>" />

			<aui:select name="<%= displayTerms.TYPE %>">
				<aui:option value=""></aui:option>

				<%
				for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
				%>

					<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= displayTerms.getType().equals(JournalArticleConstants.TYPES[i]) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:column>

		<aui:column>
			<aui:input label="name" name="<%= displayTerms.TITLE %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getTitle()) %>" />

			<c:choose>
				<c:when test="<%= portletName.equals(PortletKeys.JOURNAL) %>">
					<aui:select name="<%= displayTerms.STATUS %>">
						<aui:option value=""></aui:option>
						<aui:option label="approved" selected='<%= displayTerms.getStatus().equals("approved") %>' />
						<aui:option label="not-approved" selected='<%= displayTerms.getStatus().equals("not-approved") %>' />
						<aui:option label="expired" selected='<%= displayTerms.getStatus().equals("expired") %>' />
						<aui:option label="review" selected='<%= displayTerms.getStatus().equals("review") %>' />
					</aui:select>
				</c:when>
				<c:otherwise>

					<%
					List<Group> myPlaces = user.getMyPlaces();
					%>

					<aui:select label="my-places" name="<%= displayTerms.GROUP_ID %>">

						<%
						for (Group myPlace : myPlaces) {
							if (myPlace.hasStagingGroup()) {
								myPlace = myPlace.getStagingGroup();
							}
						%>

							<aui:option label='<%= myPlace.isUser() ? "my-community" : HtmlUtil.escape(myPlace.getDescriptiveName()) %>' selected="<%= displayTerms.getGroupId() == myPlace.getGroupId() %>" value="<%= myPlace.getGroupId() %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>
		</aui:column>

		<aui:column>
			<aui:input label="description" name="<%= displayTerms.DESCRIPTION %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getDescription()) %>" />
		</aui:column>
	</aui:fieldset>
</liferay-ui:search-toggle>

<%
boolean showAddArticleButtonButton = false;
boolean showPermissionsButton = false;

if (portletName.equals(PortletKeys.JOURNAL)) {
	showAddArticleButtonButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE);
	showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}
%>

<c:if test="<%= showAddArticleButtonButton || showPermissionsButton %>">
	<aui:button-row>
		<c:if test="<%= showAddArticleButtonButton %>">
			<aui:button onClick='<%= renderResponse.getNamespace() + "addArticle();" %>' value="add-web-content" />
		</c:if>

		<c:if test="<%= showPermissionsButton %>">
			<liferay-security:permissionsURL
				modelResource="com.liferay.portlet.journal"
				modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
				resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
				var="permissionsURL"
			/>

			<aui:button onClick="<%= permissionsURL %>" value="permissions" />
		</c:if>
	</aui:button-row>
</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
	<aui:input name="<%= displayTerms.STRUCTURE_ID %>" type="hidden" value="<%= displayTerms.getStructureId() %>" />

	<div class="portlet-msg-info">
		<liferay-ui:message key="filter-by-structure" />: <%= displayTerms.getStructureId() %><br />
	</div>
</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getTemplateId()) %>">
	<aui:input name="<%= displayTerms.TEMPLATE_ID %>" type="hidden" value="<%= displayTerms.getTemplateId() %>" />

	<div class="portlet-msg-info">
		<liferay-ui:message key="filter-by-template" />: <%= displayTerms.getTemplateId() %><br />
	</div>
</c:if>

<aui:script>
	function <portlet:namespace />addArticle() {
		var url = '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= PortletKeys.JOURNAL %>"><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="structureId" value="<%= displayTerms.getStructureId() %>" /><portlet:param name="templateId" value="<%= displayTerms.getTemplateId() %>" /></liferay-portlet:renderURL>';

		if (toggle_id_journal_article_searchcurClickValue == 'basic') {
			url += '&<portlet:namespace /><%= displayTerms.TITLE %>=' + document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>.value;

			submitForm(document.hrefFm, url);
		}
		else {
			document.<portlet:namespace />fm.method = 'post';
			submitForm(document.<portlet:namespace />fm, url);
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.ARTICLE_ID %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</aui:script>