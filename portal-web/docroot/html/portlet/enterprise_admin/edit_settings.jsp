<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String[] configurationSections = PropsValues.COMPANY_SETTINGS_FORM_CONFIGURATION;
String[] identificationSections = PropsValues.COMPANY_SETTINGS_FORM_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.COMPANY_SETTINGS_FORM_MISCELLANEOUS;

String[][] categorySections = {configurationSections, identificationSections, miscellaneousSections};

request.setAttribute("addresses.className", Account.class.getName());
request.setAttribute("emailAddresses.className", Account.class.getName());
request.setAttribute("phones.className", Account.class.getName());
request.setAttribute("websites.className", Account.class.getName());

request.setAttribute("addresses.classPK", company.getAccountId());
request.setAttribute("emailAddresses.classPK", company.getAccountId());
request.setAttribute("phones.classPK", company.getAccountId());
request.setAttribute("websites.classPK", company.getAccountId());
%>

<portlet:actionURL var="editCompanyURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_company" />
</portlet:actionURL>

<aui:form action="<%= editCompanyURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCompany();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" />

	<liferay-util:buffer var="htmlTop">
		<div class="company-info">
			<p class="float-container">
				<img alt="<liferay-ui:message key="logo" />" class="company-logo" src="<%= themeDisplay.getPathImage() %>/company_logo?img_id=<%= company.getLogoId() %>&t=<%= ImageServletTokenUtil.getToken(company.getLogoId()) %>" /><br />

				<span class="company-name"><%= company.getName() %></span>
			</p>
		</div>
	</liferay-util:buffer>

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		htmlTop="<%= htmlTop %>"
		jspPath="/html/portlet/enterprise_admin/settings/"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCompany() {
			document.<portlet:namespace />fm.method = "post";
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";

			var redirect = "<portlet:renderURL><portlet:param name="struts_action" value="/enterprise_admin/edit_company" /></portlet:renderURL>";

			redirect += Liferay.Util.getHistoryParam('<portlet:namespace />');

			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;

			<portlet:namespace />saveLdap();
			<portlet:namespace />saveEmails();

			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/enterprise_admin/edit_company" /></portlet:actionURL>");
		}

</aui:script>

<%!
private static String[] _CATEGORY_NAMES = {"configuration", "identification", "miscellaneous"};
%>