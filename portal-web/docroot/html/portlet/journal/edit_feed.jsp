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
String redirect = ParamUtil.getString(request, "redirect");

JournalFeed feed = (JournalFeed)request.getAttribute(WebKeys.JOURNAL_FEED);

long groupId = BeanParamUtil.getLong(feed, request, "groupId", scopeGroupId);

String feedId = BeanParamUtil.getString(feed, request, "feedId");
String newFeedId = ParamUtil.getString(request, "newFeedId");
String type = BeanParamUtil.getString(feed, request, "type");

String structureId = BeanParamUtil.getString(feed, request, "structureId");

JournalStructure structure = null;

String structureName = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();
	}
	catch (NoSuchStructureException nsse) {
	}
}

List<JournalTemplate> templates = new ArrayList<JournalTemplate>();

if (structure != null) {
	templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
}

String templateId = BeanParamUtil.getString(feed, request, "templateId");

if ((structure == null) && Validator.isNotNull(templateId)) {
	JournalTemplate template = null;

	try {
		template = JournalTemplateLocalServiceUtil.getTemplate(groupId, templateId);

		structureId = template.getStructureId();

		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();

		templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
	}
	catch (NoSuchTemplateException nste) {
	}
}

String rendererTemplateId = BeanParamUtil.getString(feed, request, "rendererTemplateId");

String contentField = BeanParamUtil.getString(feed, request, "contentField");

if (Validator.isNull(contentField) || ((structure == null) && !contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION) && !contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT))) {
	contentField = JournalFeedConstants.WEB_CONTENT_DESCRIPTION;
}

String feedType = BeanParamUtil.getString(feed, request, "feedType", RSSUtil.DEFAULT_TYPE);
double feedVersion = BeanParamUtil.getDouble(feed, request, "feedVersion", RSSUtil.DEFAULT_VERSION);

int delta = BeanParamUtil.getInteger(feed, request, "delta", 10);
String orderByCol = BeanParamUtil.getString(feed, request, "orderByCol");
String orderByType = BeanParamUtil.getString(feed, request, "orderByType");

ResourceURL feedURL = null;

if (feed != null) {
	long targetLayoutPlid = PortalUtil.getPlidFromFriendlyURL(feed.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

	feedURL = new PortletURLImpl(request, PortletKeys.JOURNAL, targetLayoutPlid, PortletRequest.RESOURCE_PHASE);

	feedURL.setCacheability(ResourceURL.FULL);

	feedURL.setParameter("struts_action", "/journal/rss");
	feedURL.setParameter("groupId", String.valueOf(groupId));
	feedURL.setParameter("feedId", String.valueOf(feedId));
}
%>

<script type="text/javascript">
	function <portlet:namespace />saveFeed() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= feed == null ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= feed == null %>">
			document.<portlet:namespace />fm.<portlet:namespace />feedId.value = document.<portlet:namespace />fm.<portlet:namespace />newFeedId.value;
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />contentField.value = "<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectRendererTemplate(rendererTemplateId) {
		document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = rendererTemplateId;
	}

	function <portlet:namespace />selectStructure(structureId) {
		if (document.<portlet:namespace />fm.<portlet:namespace />structureId.value != structureId) {
			document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
			document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = "";
			document.<portlet:namespace />fm.<portlet:namespace />contentField.value = "<%= JournalFeedConstants.WEB_CONTENT_DESCRIPTION %>";
			<portlet:namespace />saveFeed();
		}
	}

	function <portlet:namespace />selectTemplate(structureId, templateId) {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = templateId;
		<portlet:namespace />saveFeed();
	}
</script>

<portlet:actionURL var="editFeedURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_feed" />
</portlet:actionURL>

<aui:form action="<%= editFeedURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveFeed(); return false;" %>' >
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= feed == null ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="feedId" type="hidden" value="<%= feedId %>" />
	<aui:input name="rendererTemplateId" type="hidden" value="<%= rendererTemplateId %>" />

	<liferay-ui:tabs
		names="feed"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= DuplicateFeedIdException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= FeedContentFieldException.class %>" message="please-select-a-valid-feed-item-content" />
	<liferay-ui:error exception="<%= FeedDescriptionException.class %>" message="please-enter-a-valid-description" />
	<liferay-ui:error exception="<%= FeedIdException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= FeedNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= FeedTargetLayoutFriendlyUrlException.class %>" message="please-enter-a-valid-target-layout-friendly-url" />
	<liferay-ui:error exception="<%= FeedTargetPortletIdException.class %>" message="please-enter-a-valid-portlet-id" />

	<aui:fieldset>
		<aui:field-wrapper label="id">
			<c:choose>
				<c:when test="<%= PropsValues.JOURNAL_FEED_FORCE_AUTOGENERATE_ID %>">
					<c:choose>
						<c:when test="<%= feed == null %>">
							<liferay-ui:message key="autogenerate-id" />

							<aui:input name="newFeedId" type="hidden" />
							<aui:input name="autoFeedId" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<%= feedId %>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<table class="lfr-table">
					<tr>
						<td>
							<c:choose>
								<c:when test="<%= feed == null %>">
									<liferay-ui:input-field model="<%= JournalFeed.class %>" bean="<%= feed %>" field="feedId" fieldParam="newFeedId" defaultValue="<%= newFeedId %>" />
								</c:when>
								<c:otherwise>
									<%= feedId %>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:if test="<%= feed == null %>">
								<aui:input inlineLabel="right" name="autogenerateId" type="checkbox" value="autoFeedId" />
							</c:if>
						</td>
					</tr>
					</table>
				</c:otherwise>
			</c:choose>
		</aui:field-wrapper>

		<aui:input bean="<%= feed %>" cssClass="lfr-input-text-container" model="<%= JournalFeed.class %>" name="name" />

		<aui:input bean="<%= feed %>" cssClass="lfr-textarea-container" model="<%= JournalFeed.class %>" name="description" />

		<aui:input bean="<%= feed %>" cssClass="lfr-input-text-container" helpMessage="journal-feed-target-layout-friendly-url-help" model="<%= JournalFeed.class %>" name="targetLayoutFriendlyUrl" />

		<aui:input bean="<%= feed %>" cssClass="lfr-input-text-container" helpMessage="journal-feed-target-portlet-id-help" model="<%= JournalFeed.class %>" name="targetPortletId" />

		<c:choose>
			<c:when test="<%= feed == null %>">
				<aui:field-wrapper label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= JournalFeed.class.getName() %>"
					/>
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:input name="url" value="<%= feedURL.toString() %>" />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:fieldset>
		<aui:legend label="web-content-contraints" />

		<aui:select name="webContentType">
			<aui:option value="" />

			<%
			for (String curType : JournalArticleConstants.TYPES) {
			%>

				<aui:option label="<%= curType %>" selected="<%= type.equals(curType) %>" />

			<%
			}
			%>

		</aui:select>

		<aui:field-wrapper label="structure">
			<aui:input name="structureId" type="hidden" value="<%= structureId %>" />

			<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="structureURL">
				<portlet:param name="struts_action" value="/journal/edit_structure" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="parentStructureId" value="<%= structureId %>" />
			</portlet:renderURL>

			<aui:a href="<%= structureURL %>" label="<%= structureName %>" id="structureName" />

			<input type="button" value="<liferay-ui:message key="select" />"
				onClick="
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-templates-and-available-feed-item-content") %>')) {
						var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');
						void('');
						structureWindow.focus();
					}"
			/>

			<input <%= Validator.isNull(structureId) ? "disabled" : "" %> id="<portlet:namespace />removeStructureButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeStructure();" />
		</aui:field-wrapper>

		<aui:field-wrapper label="template">
			<c:choose>
				<c:when test="<%= templates.isEmpty() %>">
					<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

					<input type="button" value="<liferay-ui:message key="select" />"
						onClick="
							if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-template-will-change-the-structure,-available-templates,-and-available-feed-item-content") %>')) {
								var templateWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_template" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'template', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');
								void('');
								templateWindow.focus();
							}"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:table-iterator
						list="<%= templates %>"
						listType="com.liferay.portlet.journal.model.JournalTemplate"
						rowLength="3"
						rowPadding="30"
					>

						<%
						boolean templateChecked = false;

						if (templateId.equals(tableIteratorObj.getTemplateId())) {
							templateChecked = true;
						}
						%>

						<aui:input checked="<%= templateChecked %>" name="templateId" type="radio" value="<%= tableIteratorObj.getTemplateId() %>" />

						<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="templateURL">
							<portlet:param name="struts_action" value="/journal/edit_template" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" />
							<portlet:param name="templateId" value="<%= tableIteratorObj.getTemplateId() %>" />
						</portlet:renderURL>

						<aui:a href="<%= templateURL %>"><%= tableIteratorObj.getName() %></aui:a>

						<c:if test="<%= tableIteratorObj.isSmallImage() %>">
							<br />

							<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getSmallImageId() + "&t=" + ImageServletTokenUtil.getToken(tableIteratorObj.getSmallImageId()) %>" vspace="0" />
						</c:if>
					</liferay-ui:table-iterator>
				</c:otherwise>
			</c:choose>
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:fieldset>
		<aui:legend label="feed-item-content" />

		<%
		String selectRendererTemplateOption = renderResponse.getNamespace() + "selectRendererTemplate('');";
		%>

		<aui:select label="feed-item-content" name="contentField">
			<aui:option label="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" onClick="<%= selectRendererTemplateOption %>" selected="<%= contentField.equals(JournalFeedConstants.WEB_CONTENT_DESCRIPTION) %>" />
			<optgroup label='<liferay-ui:message key="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" />'>
				<aui:option label="use-default-template" selected="<%= contentField.equals(JournalFeedConstants.RENDERED_WEB_CONTENT) %>" onClick="<%= selectRendererTemplateOption %>" value="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" />

				<c:if test="<%= (structure != null) && (templates.size() > 1) %>">

					<%
					for (JournalTemplate currTemplate : templates) {
					%>

						<%
						String selectRendererTemplateOptions = renderResponse.getNamespace() + "selectRendererTemplate('" + currTemplate.getTemplateId() + "');";
						%>

						<aui:option label='<%= LanguageUtil.format(pageContext, "use-template-x", currTemplate.getName()) %>' selected="<%= rendererTemplateId.equals(currTemplate.getTemplateId()) %>" onClick="<%= selectRendererTemplateOptions %>" value="<%= JournalFeedConstants.RENDERED_WEB_CONTENT %>" />

					<%
					}
					%>

				</c:if>
			</optgroup>

			<c:if test="<%= structure != null %>">
				<optgroup label="<liferay-ui:message key="structure-fields" />">

					<%
					Document doc = SAXReaderUtil.read(structure.getXsd());

					XPath xpathSelector = SAXReaderUtil.createXPath("//dynamic-element");

					List<Node> nodes = xpathSelector.selectNodes(doc);

					for (Node node : nodes) {
						Element el = (Element)node;

						String elName = el.attributeValue("name");
						String elType = StringUtil.replace(el.attributeValue("type"), StringPool.UNDERLINE, StringPool.DASH);

						if (!elType.equals("boolean") && !elType.equals("list") && !elType.equals("multi-list")) {
					%>

							<aui:option label='<%= TextFormatter.format(elName, TextFormatter.J) + "(" + LanguageUtil.get(pageContext, elType) + ")" %>' onClick="<%= selectRendererTemplateOption %>" selected="<%= contentField.equals(elName) %>" value="<%= elName %>" />

					<%
						}
					}
					%>

				</optgroup>
			</c:if>
		</aui:select>

		<aui:select label="feed-type" name="feedTypeAndVersion">

			<%
			StringBuilder sb = new StringBuilder();

			for (int i = 4; i < RSSUtil.RSS_VERSIONS.length; i++) {
				sb.append("<option ");

				if (feedType.equals(RSSUtil.RSS) && (feedVersion == RSSUtil.RSS_VERSIONS[i])) {
					sb.append("selected ");
				}

				sb.append("value=\"");
				sb.append(RSSUtil.RSS);
				sb.append(":");
				sb.append(RSSUtil.RSS_VERSIONS[i]);
				sb.append("\">");
				sb.append(LanguageUtil.get(pageContext, RSSUtil.RSS));
				sb.append(" ");
				sb.append(RSSUtil.RSS_VERSIONS[i]);
				sb.append("</option>");
			}

			for (int i = 1; i < RSSUtil.ATOM_VERSIONS.length; i++) {
				sb.append("<option ");

				if (feedType.equals(RSSUtil.ATOM) && (feedVersion == RSSUtil.ATOM_VERSIONS[i])) {
					sb.append("selected ");
				}

				sb.append("value=\"");
				sb.append(RSSUtil.ATOM);
				sb.append(":");
				sb.append(RSSUtil.ATOM_VERSIONS[i]);
				sb.append("\">");
				sb.append(LanguageUtil.get(pageContext, RSSUtil.ATOM));
				sb.append(" ");
				sb.append(RSSUtil.ATOM_VERSIONS[i]);
				sb.append("</option>");
			}
			%>

			<%= sb.toString() %>

		</aui:select>

		<aui:input label="maximum-items-to-display" name="delta" style="width: 50px;" value="<%= delta %>" />

		<aui:select label="order-by-column" name="orderByCol">
			<aui:option label="modified-date" selected='<%=orderByCol.equals("modified-date") %>' />
			<aui:option label="display-date" selected='<%= orderByCol.equals("display-date") %>' />
		</aui:select>

		<aui:select name="orderByType">
			<aui:option label="ascending" selected='<%= orderByType.equals("asc") %>' value="asc" />
			<aui:option label="descending" selected='<%= orderByType.equals("desc") %>' value="desc" />
		</aui:select>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

	<c:if test="<%= feed != null %>">
		<aui:button type="button" value="preview" />
	</c:if>

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_FEED_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= (feed == null) ? "newFeedId" : "name" %>);
			</c:otherwise>
		</c:choose>
	</script>
</c:if>