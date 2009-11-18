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

<%@ include file="/html/portlet/language/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset>
		<aui:legend label="languages" />
		<aui:input name="languageIds" type="hidden" />

		<%
		Set availableLanguageIdsSet = SetUtil.fromArray(availableLanguageIds);

		// Left list

		List leftList = new ArrayList();

		for (int i = 0; i < languageIds.length; i++) {
			String languageId = languageIds[i];

			leftList.add(new KeyValuePair(languageId, LocaleUtil.fromLanguageId(languageId).getDisplayName()));
		}

		// Right list

		List rightList = new ArrayList();

		Arrays.sort(languageIds);

		Iterator itr = availableLanguageIdsSet.iterator();

		while (itr.hasNext()) {
			String languageId = (String)itr.next();

			if (Arrays.binarySearch(languageIds, languageId) < 0) {
				rightList.add(new KeyValuePair(languageId, LocaleUtil.fromLanguageId(languageId).getDisplayName()));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<liferay-ui:input-move-boxes
			formName="fm"
			leftTitle="current"
			rightTitle="available"
			leftBoxName="currentLanguageIds"
			rightBoxName="availableLanguageIds"
			leftReorder="true"
			leftList="<%= leftList %>"
			rightList="<%= rightList %>"
		/>
	</aui:fieldset>

	<aui:fieldset>
		<aui:select name="displayStyle">
			<aui:option label="icon" selected="<%= displayStyle == LanguageTag.LIST_ICON %>" value="<%= LanguageTag.LIST_ICON %>" />
			<aui:option label="long-text" selected="<%= displayStyle == LanguageTag.LIST_LONG_TEXT %>" value="<%= LanguageTag.LIST_LONG_TEXT %>" />
			<aui:option label="short-text" selected="<%= displayStyle == LanguageTag.LIST_SHORT_TEXT %>" value="<%= LanguageTag.LIST_SHORT_TEXT %>" />
			<aui:option label="select-box" selected="<%= displayStyle == LanguageTag.SELECT_BOX %>" value="<%= LanguageTag.SELECT_BOX %>" />
		</aui:select>
	</aui:fieldset>

	<aui:button-row>
		<aui:button name="saveButton" onClick='<%= "document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "languageIds.value = Liferay.Util.listSelect(document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "currentLanguageIds); submitForm(document." + renderResponse.getNamespace() + "fm);" %>' type="button" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>