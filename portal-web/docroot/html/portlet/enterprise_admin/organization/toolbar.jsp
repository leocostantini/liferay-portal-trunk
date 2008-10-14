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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-organizations");

Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewOrganizationsURL">
		<portlet:param name="struts_action" value="/enterprise_admin_organizations/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-organizations") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewOrganizationsURL %>"><liferay-ui:message key="view-organizations" /></a>
	</span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ORGANIZATION) %>">
		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add-organization") ? "current" : StringPool.BLANK %> ">
			<a href="javascript: <portlet:namespace />addOrganization();"><liferay-ui:message key="add-organization" /></a>
		</span>
	</c:if>
</div>

<script type="text/javascript">
	function <portlet:namespace />addOrganization() {
		var url = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /></portlet:renderURL>';

		if (toggle_id_enterprise_admin_organization_searchcurClickValue == 'basic') {
			url += '&<portlet:namespace />redirect=' + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />organizationsRedirect.value);

			<c:if test="<%= organization != null %>">
				url += '&<portlet:namespace />parentOrganizationId=<%= organization.getOrganizationId() %>';
			</c:if>

			url += '&<portlet:namespace /><%= OrganizationDisplayTerms.NAME %>=' + document.<portlet:namespace />fm.<portlet:namespace /><%= OrganizationDisplayTerms.KEYWORDS %>.value;

			submitForm(document.hrefFm, url);
		}
		else {
			<c:if test="<%= organization != null %>">
				url += '&<portlet:namespace />parentOrganizationId=<%= organization.getOrganizationId() %>';
			</c:if>

			document.<portlet:namespace />fm.method = 'post';
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />organizationsRedirect.value;
			submitForm(document.<portlet:namespace />fm, url);
		}
	}
</script>