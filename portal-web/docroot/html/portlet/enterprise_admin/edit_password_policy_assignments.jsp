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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute(WebKeys.PASSWORD_POLICY);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/edit_password_policy_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-members"), portletURL.toString());

portletURL.setParameter("tabs2", tabs2);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs2), portletURL.toString());

portletURL.setParameter("tabs3", tabs3);
%>

<script type="text/javascript">
	function <portlet:namespace />updatePasswordPolicyOrganizations(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "password_policy_organizations";
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
		document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeOrganizationIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updatePasswordPolicyUsers(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "password_policy_users";
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editAssignmentsURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_password_policy_assignments" />
</portlet:actionURL>

<aui:form action="<%= editAssignmentsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%=tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />

	<liferay-ui:message key="edit-assignments-for-password-policy" />: <%= HtmlUtil.escape(passwordPolicy.getName()) %>

	<br /><br />

	<liferay-ui:tabs
		names="users,organizations"
		param="tabs2"
		url="<%= portletURL.toString() %>"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<aui:input name="addUserIds" type="hidden" />
			<aui:input name="removeUserIds" type="hidden" />

			<liferay-ui:tabs
				names="current,available"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:search-container
				rowChecker="<%= new UserPasswordPolicyChecker(renderResponse, passwordPolicy) %>"
				searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-form
					page="/html/portlet/enterprise_admin/user_search.jsp"
				/>

				<%
				UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

				LinkedHashMap userParams = new LinkedHashMap();

				if (tabs3.equals("current")) {
					userParams.put("usersPasswordPolicies", new Long(passwordPolicy.getPasswordPolicyId()));
				}
				%>

				<liferay-ui:search-container-results>
					<%@ include file="/html/portlet/enterprise_admin/user_search_results.jspf" %>
				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.User"
					escapedModel="<%= true %>"
					keyProperty="userId"
					modelVar="user2"
				>
					<liferay-ui:search-container-column-text
						name="name"
						property="fullName"
					/>

					<liferay-ui:search-container-column-text
						name="screen-name"
						property="screenName"
					/>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "updatePasswordPolicyUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

				<br /><br />

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<aui:input name="addOrganizationIds" type="hidden" />
			<aui:input name="removeOrganizationIds" type="hidden" />

			<liferay-ui:tabs
				names="current,available"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:search-container
				rowChecker="<%= new OrganizationPasswordPolicyChecker(renderResponse, passwordPolicy) %>"
				searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
			>
				<liferay-ui:search-form
					page="/html/portlet/enterprise_admin/organization_search.jsp"
				/>

				<%
				OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

				long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

				LinkedHashMap organizationParams = new LinkedHashMap();

				if (tabs3.equals("current")) {
					organizationParams.put("organizationsPasswordPolicies", new Long(passwordPolicy.getPasswordPolicyId()));
				}
				%>

				<liferay-ui:search-container-results>
					<%@ include file="/html/portlet/enterprise_admin/organization_search_results.jspf" %>
				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Organization"
					escapedModel="<%= true %>"
					keyProperty="organizationId"
					modelVar="organization"
				>
					<liferay-ui:search-container-column-text
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						buffer="buffer"
						name="parent-organization"
					>

						<%
						if (organization.getParentOrganizationId() > 0) {
							try {
								Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

								buffer.append(HtmlUtil.escape(parentOrganization.getName()));
							}
							catch (Exception e) {
							}
						}
						%>

					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						orderable="<%= true %>"
						value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="city"
						value="<%= organization.getAddress().getCity() %>"
					/>

					<liferay-ui:search-container-column-text
						name="region"
					>
						<liferay-ui:write bean="<%= organization %>" property="region" />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="country"
					>
						<liferay-ui:write bean="<%= organization %>" property="country" />
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "updatePasswordPolicyOrganizations('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

				<br /><br />

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:when>
	</c:choose>
</aui:form>