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

<%@ include file="/html/taglib/ui/asset_tags_selector/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-ui:asset-tags-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-tags-selector:classPK"));
String hiddenInput = (String)request.getAttribute("liferay-ui:asset-tags-selector:hiddenInput");
String curTags = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-tags-selector:curTags"));
boolean focus = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:asset-tags-selector:focus"));
String contentCallback = GetterUtil.getString((String)request.getAttribute("liferay-ui:asset-tags-selector:contentCallback"));

boolean suggestible = Validator.isNotNull(contentCallback);

if (Validator.isNotNull(className) && (classPK > 0)) {
	List<AssetTag> tags = AssetTagServiceUtil.getTags(className, classPK);

	curTags = ListUtil.toString(tags, "name");
}

String curTagsParam = request.getParameter(hiddenInput);

if (curTagsParam != null) {
	curTags = curTagsParam;
}
%>

<div class="lfr-asset-tags-selector" id="<%= namespace + randomNamespace %>assetTagsSelector">
	<aui:input name="<%= hiddenInput %>" type="hidden" />

	<span class="ui-tags empty" id="<%= randomNamespace %>assetTagsSummary"></span>
		<input class="ui-tags-input" id="<%= randomNamespace %>assetTagNames" size="15" type="text" />

		<aui:button disabled="<%= true %>" name='<%= randomNamespace + "addTag" %>' value="add-tags" />
	</span>

	<liferay-ui:message key="or" />

	<aui:button name="<%= randomNamespace + "selectTag" %>" value="select-tags" />

	<c:if test="<%= suggestible %>">
		<aui:button name="<%= randomNamespace + "suggestions" %>" value="suggestions" />
	</c:if>
</div>

<script type="text/javascript">
	AUI().ready(
		function() {
			new Liferay.AssetTagsSelector(
				{
					instanceVar: "<%= namespace + randomNamespace %>",
					hiddenInput: "<%= namespace + hiddenInput %>",
					textInput: "<%= randomNamespace %>assetTagNames",
					summarySpan: "<%= randomNamespace %>assetTagsSummary",
					curTags: "<%= HtmlUtil.escapeJS(curTags) %>",
					focus: <%= focus %>,
					contentCallback: function() {
						return <%= contentCallback %>();
					}
				}
			);
		}
	);
</script>