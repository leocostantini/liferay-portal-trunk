<%--
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
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.taglib.aui.ValidatorTag" %>

<%
String name = GetterUtil.getString((String)request.getAttribute("aui:form:name"));
String onSubmit = GetterUtil.getString((String)request.getAttribute("aui:form:onSubmit"));
Map<String, List<ValidatorTag>> validatorTagsMap = (Map<String, List<ValidatorTag>>)request.getAttribute("aui:form:validatorTagsMap");
%>

</form>

<aui:script use="liferay-form">
	Liferay.Form.register(
		{
			id: '<%= namespace + name %>'

			<c:if test="<%= Validator.isNotNull(onSubmit) %>">
				, onSubmit: function(event) {
					<%= onSubmit %>
				}
			</c:if>

			<c:if test="<%= validatorTagsMap != null %>">
				,fieldRules: [

				<%
				int i = 0;

				for (String fieldName : validatorTagsMap.keySet()) {
					List<ValidatorTag> validatorTags = validatorTagsMap.get(fieldName);

					for (ValidatorTag validatorTag : validatorTags) {
				%>

						<%= i != 0 ? StringPool.COMMA : StringPool.BLANK %>

						{
							fieldName: '<%= namespace + fieldName %>',
							validatorName: '<%= validatorTag.getName() %>',
							errorMessage: '<%= validatorTag.getErrorMessage() %>',
							body: <%= validatorTag.getBody() %>,
							isCustom: <%= validatorTag.isCustom() %>
						}

				<%
						i++;
					}
				}
				%>

				]
			</c:if>
		}
	);
</aui:script>