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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "");
String tabs2 = ParamUtil.getString(request, "tabs2", "public");
String tabs3 = ParamUtil.getString(request, "tabs3", "page");

String redirect = ParamUtil.getString(request, "redirect");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group liveGroup = null;
Group stagingGroup = null;

int pagesCount = 0;

if (selGroup.isStagingGroup()) {
	liveGroup = selGroup.getLiveGroup();
	stagingGroup = selGroup;
}
else {
	liveGroup = selGroup;

	if (selGroup.hasStagingGroup()) {
		stagingGroup = selGroup.getStagingGroup();
	}
}

if (Validator.isNull(tabs1)) {
	if (selGroup.isStagingGroup()) {
		tabs1 = "staging";
	}
	else {
		tabs1 = "live";
	}
}

Group group = null;

if (tabs1.equals("staging")) {
	group = stagingGroup;
}
else {
	group = liveGroup;
}

long groupId = liveGroup.getGroupId();

if (group != null) {
	groupId = group.getGroupId();
}

long liveGroupId = liveGroup.getGroupId();

long stagingGroupId = 0;

if (stagingGroup != null) {
	stagingGroupId = stagingGroup.getGroupId();
}

String selPlid = ParamUtil.getString(request, "selPlid", LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
String layoutId = LayoutImpl.getLayoutId(selPlid);
String ownerId = LayoutImpl.getOwnerId(selPlid);

boolean privateLayout = tabs2.equals("private");

if (Validator.isNull(ownerId)) {
	if (privateLayout) {
		ownerId = LayoutImpl.PRIVATE + groupId;

		if (group != null) {
			pagesCount = group.getPrivateLayoutsPageCount();
		}
	}
	else {
		ownerId = LayoutImpl.PUBLIC + groupId;

		if (group != null) {
			pagesCount = group.getPublicLayoutsPageCount();
		}
	}
}

Layout selLayout = null;

try {
	selLayout = LayoutLocalServiceUtil.getLayout(layoutId, ownerId);
}
catch (NoSuchLayoutException nsle) {
}

if (selLayout != null) {
	if (!PortalUtil.isLayoutParentable(selLayout) && tabs3.equals("children")) {
		tabs3 = "page";
	}
	else if (tabs3.equals("logo") || tabs3.equals("import-export") || (tabs3.equals("virtual-host")) || (tabs3.equals("sitemap"))) {
		tabs3 = "page";
	}
}

if (selLayout == null) {
	if (tabs3.equals("page")) {
		tabs3 = "children";
	}
	else if (tabs3.equals("sitemap") && ownerId.startsWith(LayoutImpl.PRIVATE)) {
		tabs3 = "children";
	}
}

String parentLayoutId = BeanParamUtil.getString(selLayout, request,  "parentLayoutId", LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

LayoutLister layoutLister = new LayoutLister();

String rootNodeName = liveGroup.isUser() ? contact.getFullName() : liveGroup.getName();
LayoutView layoutView = layoutLister.getLayoutView(ownerId, rootNodeName, locale);

List layoutList = layoutView.getList();

request.setAttribute(WebKeys.LAYOUT_LISTER_LIST, layoutList);

PortletURL portletURL = renderResponse.createRenderURL();

if (themeDisplay.isStatePopUp()) {
	portletURL.setWindowState(LiferayWindowState.POP_UP);
}
else {
	portletURL.setWindowState(WindowState.MAXIMIZED);
}

portletURL.setParameter("struts_action", "/communities/edit_pages");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(liveGroupId));

PortletURL viewPagesURL = new PortletURLImpl(request, PortletKeys.MY_PLACES, plid, true);

viewPagesURL.setWindowState(WindowState.NORMAL);
viewPagesURL.setPortletMode(PortletMode.VIEW);

viewPagesURL.setParameter("struts_action", "/my_places/view");
viewPagesURL.setParameter("ownerId", ownerId);
%>

<script type="text/javascript">
	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-overwrite-the-existing-staging-configuration") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "copy_from_live";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />deletePage() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value = "<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= ownerId + StringPool.PERIOD + parentLayoutId %>";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />importPages() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/communities/import_pages" /><portlet:param name="ownerId" value="<%= ownerId %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />publishToLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-to-live-and-overwrite-the-existing-configuration") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "publish_to_live";
			submitForm(document.<portlet:namespace />fm);
		}
	}

	<c:if test="<%= themeDisplay.isStatePopUp() %>">
		function <portlet:namespace />resizeParent() {
			var box = document.getElementById("p_p_id_<%= portletDisplay.getId() %>_");
			parent.Alerts.resizeIframe({height: box.scrollHeight});
		}

		_$J(window).load(<portlet:namespace />resizeParent);
		_$J(document).click(<portlet:namespace />resizeParent);
	</c:if>

	function <portlet:namespace />savePage() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		<c:choose>
			<c:when test='<%= tabs3.equals("virtual-host") %>'>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "virtual_host";
			</c:when>
			<c:otherwise>
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= tabs3.equals("children") ? Constants.ADD : Constants.UPDATE %>';
			</c:otherwise>
		</c:choose>

		if (document.<portlet:namespace />fm.<portlet:namespace />languageId) {
			document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value += "&<portlet:namespace />languageId=" + document.<portlet:namespace />fm.<portlet:namespace />languageId.value;
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateDisplayOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "display_order";
		document.<portlet:namespace />fm.<portlet:namespace />layoutIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLogo() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "logo";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateLookAndFeel(themeId, colorSchemeId, sectionParam, sectionName) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "look_and_feel";

		var themeRadio = document.<portlet:namespace />fm.<portlet:namespace />themeId;

		if (themeRadio.length) {
			themeRadio[Liferay.Util.getSelectedIndex(themeRadio)].value = themeId;
		}
		else {
			themeRadio.value = themeId;
		}

		var colorSchemeRadio = document.<portlet:namespace />fm.<portlet:namespace />colorSchemeId;

		if (colorSchemeRadio) {
			if (colorSchemeRadio.length) {
				colorSchemeRadio[Liferay.Util.getSelectedIndex(colorSchemeRadio)].value = colorSchemeId;
			}
			else {
				colorSchemeRadio.value = colorSchemeId;
			}
		}

		if ((sectionParam != null) && (sectionName != null)) {
			document.<portlet:namespace />fm.<portlet:namespace />pagesRedirect.value += "&" + sectionParam + "=" + sectionName;
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateStagingState() {
		var checked = document.<portlet:namespace />fm.<portlet:namespace />activateStaging.checked;

		var ok = true;

		if (!checked) {
			ok = confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-staging-public-and-private-pages") %>');
		}

		if (ok) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "update_staging_state";
			submitForm(document.<portlet:namespace />fm);
		}

		document.<portlet:namespace />fm.<portlet:namespace />activateStaging.checked = !checked;
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_pages" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />savePage(); return false;">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= tabs3 %>">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />pagesRedirect" type="hidden" value="<%= portletURL.toString() %>&<portlet:namespace />selPlid=<%= selPlid %>">
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>">
<input name="<portlet:namespace />liveGroupId" type="hidden" value="<%= liveGroupId %>">
<input name="<portlet:namespace />stagingGroupId" type="hidden" value="<%= stagingGroupId %>">
<input name="<portlet:namespace />selPlid" type="hidden" value="<%= selPlid %>">
<input name="<portlet:namespace />layoutId" type="hidden" value="<%= layoutId %>">
<input name="<portlet:namespace />ownerId" type="hidden" value="<%= ownerId %>">
<input name="<portlet:namespace />privateLayout" type="hidden" value="<%= privateLayout %>">

<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) %>">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "edit-pages-for-community") %>: <%= liveGroup.getName() %>
			</td>
			<td align="right">
				&laquo; <a href="<%= redirect %>"><%= LanguageUtil.get(pageContext, "back") %></a>
			</td>
		</tr>
		</table>

		<br>
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.MY_ACCOUNT) %>">
		<liferay-util:include page="/html/portlet/my_account/tabs1.jsp">
			<liferay-util:param name="tabs1" value="pages" />
		</liferay-util:include>
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) %>">
		<liferay-ui:tabs
			names="live,staging"
			param="tabs1"
			value="<%= tabs1 %>"
			url="<%= currentURL %>"
		/>
	</c:if>
</c:if>

<c:if test='<%= tabs1.equals("staging") %>'>
	<table border="0" cellpadding="5" cellspacing="5">
	<tr>
		<td>
			<%= LanguageUtil.get(pageContext, "activate-staging") %>
		</td>
		<td style="padding-left: 10px;"></td>
		<td>
			<input <%= (stagingGroup != null) ? "checked" : "" %> name="<portlet:namespace />activateStaging" type="checkbox" onClick="<portlet:namespace />updateStagingState();">
		</td>
	</tr>
	</table>

	<br>
</c:if>

<c:if test="<%= (portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.MY_ACCOUNT)) && (group != null) %>">
	<liferay-ui:tabs
		names="public,private"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<c:if test="<%= (group != null) %>">
	<c:choose>
		<c:when test='<%= tabs1.equals("staging") %>'>
			<c:if test="<%= (portletName.equals(PortletKeys.COMMUNITIES) || !selGroup.isStagingGroup()) && (pagesCount > 0)  %>">
				<input type="button" value='<%= LanguageUtil.get(pageContext, "view-pages") %>'  onClick="var stagingGroupWindow = window.open('<%= viewPagesURL%>'); void(''); stagingGroupWindow.focus();">
			</c:if>

			<input type="button" value='<%= LanguageUtil.get(pageContext, "publish-to-live") %>'  onClick="<portlet:namespace />publishToLive();">

			<input type="button" value='<%= LanguageUtil.get(pageContext, "copy-from-live") %>'  onClick="<portlet:namespace />copyFromLive();">

			<br><br>
		</c:when>
		<c:otherwise>
			<c:if test="<%= (portletName.equals(PortletKeys.COMMUNITIES) || selGroup.isStagingGroup()) && (pagesCount > 0) %>">
				<input type="button" value='<%= LanguageUtil.get(pageContext, "view-pages") %>'  onClick="var liveGroupWindow = window.open('<%= viewPagesURL %>'); void(''); liveGroupWindow.focus();">

				<br><br>
			</c:if>
		</c:otherwise>
	</c:choose>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td valign="top">
			<div id="<%= renderResponse.getNamespace() %>tree-output"></div>

			<%@ include file="/html/portlet/communities/tree_js.jsp" %>

			<script type="text/javascript">
				var layoutsTree = new Tree("layoutsTree", layoutsArray, layoutsIcons, "gamma");

				layoutsTree.create("<%= SessionTreeJSClicks.getOpenNodes(request, "layoutsTree") %>");

				document.getElementById("<%= renderResponse.getNamespace() %>tree-output").innerHTML = layoutsTree.getHTML();
			</script>
		</td>
		<td style="padding-left: 10px;"></td>
		<td valign="top" width="75%">

			<%
			PortletURL breadcrumbURL = PortletURLUtil.clone(portletURL, false, renderResponse);
			%>

			<c:choose>
				<c:when test="<%= selLayout != null %>">
					<%= LanguageUtil.get(pageContext, "edit-" + (privateLayout ? "private" : "public") + "-page") %>: <a href="<%= breadcrumbURL.toString() %>"><%= rootNodeName %></a> &raquo; <liferay-ui:breadcrumb selLayout="<%= selLayout %>" selLayoutParam="selPlid" portletURL="<%= breadcrumbURL %>" />
				</c:when>
				<c:otherwise>
					<%= LanguageUtil.get(pageContext, "manage-top-" + (privateLayout ? "private" : "public") + "-pages-for") %>: <a href="<%= breadcrumbURL.toString() %>"><%= rootNodeName %></a>
				</c:otherwise>
			</c:choose>

			<br><br>

			<%
			String tabs3Names = "page,children,look-and-feel";

			if ((selLayout != null) && !PortalUtil.isLayoutParentable(selLayout)) {
				tabs3Names = StringUtil.replace(tabs3Names, "children,", StringPool.BLANK);
			}

			if (selLayout == null) {
				tabs3Names = StringUtil.replace(tabs3Names, "page,", StringPool.BLANK);

				if (GroupPermission.contains(permissionChecker, liveGroupId, ActionKeys.UPDATE)) {
					if (!tabs1.equals("staging") && company.isCommunityLogo()) {
						tabs3Names += ",logo";
					}

					tabs3Names += ",import-export";

					if (!tabs1.equals("staging")) {
						tabs3Names += ",virtual-host";

						if (ownerId.startsWith(LayoutImpl.PUBLIC)) {
							tabs3Names += ",sitemap";
						}
					}
				}
			}
			%>

			<liferay-ui:tabs
				names="<%= tabs3Names %>"
				param="tabs3"
				url='<%= portletURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>'
			/>

			<liferay-ui:error exception="<%= LayoutFriendlyURLException.class %>">

				<%
				LayoutFriendlyURLException lfurle = (LayoutFriendlyURLException)errorException;
				%>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-friendly-url-that-begins-with-a-slash") %>
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ENDS_WITH_SLASH %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-friendly-url-that-does-not-end-with-a-slash") %>
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.TOO_SHORT %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-friendly-url-that-is-at-least-two-characters-long") %>
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ADJACENT_SLASHES %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-friendly-url-that-does-not-have-adjacent-slashes") %>
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.INVALID_CHARACTERS %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-friendly-url-with-valid-characters") %>
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DUPLICATE %>">
					<%= LanguageUtil.get(pageContext, "please-enter-a-unique-friendly-url") %>
				</c:if>

				<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.KEYWORD_CONFLICT %>">
					<%= LanguageUtil.format(pageContext, "please-enter-a-friendly-url-that-does-not-conflict-with-the-keyword-x", lfurle.getKeywordConflict()) %>
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LayoutHiddenException.class %>" message="your-first-page-must-not-be-hidden" />
			<liferay-ui:error exception="<%= LayoutNameException.class %>" message="please-enter-a-valid-name" />

			<liferay-ui:error exception="<%= LayoutParentLayoutIdException.class %>">

				<%
				LayoutParentLayoutIdException lplide = (LayoutParentLayoutIdException)errorException;
				%>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.NOT_PARENTABLE %>">
					<%= LanguageUtil.get(pageContext, "a-page-cannot-become-a-child-of-a-page-that-is-not-parentable") %>
				</c:if>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.SELF_DESCENDANT %>">
					<%= LanguageUtil.get(pageContext, "a-page-cannot-become-a-child-of-itself") %>
				</c:if>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE %>">
					<%= LanguageUtil.get(pageContext, "the-resulting-first-page-must-be-a-portlet-page") %>
				</c:if>

				<c:if test="<%= lplide.getType() == LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN %>">
					<%= LanguageUtil.get(pageContext, "the-resulting-first-page-must-not-be-hidden") %>
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LayoutSetVirtualHostException.class %>" message="please-enter-a-unique-virtual-host" />

			<liferay-ui:error exception="<%= LayoutTypeException.class %>">

				<%
				LayoutTypeException lte = (LayoutTypeException)errorException;
				%>

				<c:if test="<%= lte.getType() == LayoutTypeException.NOT_PARENTABLE %>">
					<%= LanguageUtil.get(pageContext, "your-type-must-allow-children-pages") %>
				</c:if>

				<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
					<%= LanguageUtil.get(pageContext, "your-first-page-must-be-a-portlet-page") %>
				</c:if>
			</liferay-ui:error>

			<c:choose>
				<c:when test='<%= tabs3.equals("page") %>'>

					<%
					String languageId = LanguageUtil.getLanguageId(request);
					Locale languageLocale = LocaleUtil.fromLanguageId(languageId);

					String name = request.getParameter("name");

					if (Validator.isNull(name)) {
						name = selLayout.getName(languageLocale);
					}

					String title = request.getParameter("title");

					if (Validator.isNull(title)) {
						title = selLayout.getTitle(languageLocale);
					}

					String type = BeanParamUtil.getString(selLayout, request, "type");
					String friendlyURL = BeanParamUtil.getString(selLayout, request, "friendlyURL");
					%>

					<input name="<portlet:namespace />curLanguageId" type="hidden" value="<%= languageId %>">

					<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td>
							<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "parent") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<select name="<portlet:namespace />parentLayoutId">

										<%
										for (int i = 0; i < layoutList.size(); i++) {

											// id | parentId | ls | obj id | name | img | depth

											String layoutDesc = (String)layoutList.get(i);

											String[] nodeValues = StringUtil.split(layoutDesc, "|");

											String objId = LayoutImpl.getLayoutId(nodeValues[3]);
											String layoutName = nodeValues[4];

											int depth = 0;

											if (i != 0) {
												depth = GetterUtil.getInteger(nodeValues[6]);
											}

											for (int j = 0; j < depth; j++) {
												layoutName = "-&nbsp;" + layoutName;
											}
										%>

											<option <%= parentLayoutId.equals(objId) ? "selected" : "" %> value="<%= objId %>"><%= layoutName %></option>

										<%
										}
										%>

									</select>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<br>
								</td>
							</tr>
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "name") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input name="<portlet:namespace />name" size="30" type="text" value="<%= name %>">
										</td>
										<td style="padding-left: 10px;"></td>
										<td>
											<select name="<portlet:namespace />languageId" onChange="<portlet:namespace />savePage();">

												<%
												Locale[] locales = LanguageUtil.getAvailableLocales();

												for (int i = 0; i < locales.length; i++) {
												%>

													<option <%= (languageId.equals(LocaleUtil.toLanguageId(locales[i]))) ? "selected" : "" %> value="<%= LocaleUtil.toLanguageId(locales[i]) %>"><%= locales[i].getDisplayName(locales[i]) %></option>

												<%
												}
												%>

											</select>
										</td>
									</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "html-title") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<input name="<portlet:namespace />title" size="30" type="text" value="<%= title %>">
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<br>
								</td>
							</tr>
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "type") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<select name="<portlet:namespace />type">

										<%
										for (int i = 0; i < LayoutImpl.TYPES.length; i++) {
										%>

											<option <%= type.equals(LayoutImpl.TYPES[i]) ? "selected" : "" %> value="<%= LayoutImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, "layout.types." + LayoutImpl.TYPES[i]) %></option>

										<%
										}
										%>

									</select>
								</td>
							</tr>
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "hidden") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<liferay-ui:input-checkbox param="hidden" defaultValue="<%= selLayout.isHidden() %>" />
								</td>
							</tr>

							<c:if test="<%= PortalUtil.isLayoutFriendliable(selLayout) %>">
								<tr>
									<td>
										<%= LanguageUtil.get(pageContext, "friendly-url") %>
									</td>
									<td style="padding-left: 10px;"></td>
									<td nowrap>

										<%
										String parentFriendlyURL = group.getFriendlyURL();

										if (Validator.isNull(parentFriendlyURL)) {
											parentFriendlyURL = group.getDefaultFriendlyURL(privateLayout);
										}
										%>

										<%= Http.getProtocol(request) %>://<%= company.getPortalURL() %><%= group.getPathFriendlyURL(privateLayout, themeDisplay) %><%= parentFriendlyURL %>

										<input name="<portlet:namespace />friendlyURL" size="30" type="text" value="<%= friendlyURL %>"> <%= LanguageUtil.format(pageContext, "for-example-x", "<i>/news</i>") %>
									</td>
								</tr>
							</c:if>

							<tr>
								<td colspan="3">
									<br>
								</td>
							</tr>
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "icon") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<liferay-theme:layout-icon layout="<%= selLayout %>" />

									<input name="<portlet:namespace />iconFileName" size="30" type="file" onChange="document.<portlet:namespace />fm.<portlet:namespace />iconImage.value = true; document.<portlet:namespace />fm.<portlet:namespace />iconImageCheckbox.checked = true;">
								</td>
							</tr>
							<tr>
								<td>
									<%= LanguageUtil.get(pageContext, "use-icon") %>
								</td>
								<td style="padding-left: 10px;"></td>
								<td>
									<liferay-ui:input-checkbox param="iconImage" defaultValue="<%= selLayout.isIconImage() %>" />
								</td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<br><div class="separator"></div><br>
						</td>
					</tr>
					<tr>
						<td>

							<%
							request.setAttribute(WebKeys.SEL_LAYOUT, selLayout);
							%>

							<liferay-util:include page="<%= Constants.TEXT_HTML_DIR + PortalUtil.getLayoutEditPage(selLayout) %>" />
						</td>
					</tr>
					</table>

					<br>

					<input type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>

					<liferay-security:permissionsURL
						modelResource="<%= Layout.class.getName() %>"
						modelResourceDescription="<%= selLayout.getName() %>"
						resourcePrimKey="<%= selLayout.getPrimaryKey().toString() %>"
						var="permissionURL"
					/>

					<input type="button" value='<%= LanguageUtil.get(pageContext, "permissions") %>' onClick="self.location = '<%= permissionURL %>';">

					<input type="button" value='<%= LanguageUtil.get(pageContext, "delete") %>' onClick="<portlet:namespace />deletePage();">

					<script type="text/javascript">
						document.<portlet:namespace />fm.<portlet:namespace />name.focus();

						function <portlet:namespace />getChoice(value) {
							for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
								if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value == value) {
									return document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].index;
								}
							}

							return null;
						}

						<%
						try {
							SAXReader reader = new SAXReader();

							Document doc = reader.read(new StringReader(selLayout.getName()));

							Element root = doc.getRootElement();

							String [] availableLocales = StringUtil.split(root.attributeValue("available-locales"));

							if (availableLocales != null) {
								for (int i = 0; i < availableLocales.length; i++) {
						%>

									document.<portlet:namespace />fm.<portlet:namespace />languageId.options[<portlet:namespace />getChoice("<%= availableLocales[i] %>")].style.color = "<%= colorScheme.getPortletMsgError() %>";

						<%
								}
							}
						}
						catch (Exception e) {
						}
						%>

					</script>
				</c:when>
				<c:when test='<%= tabs3.equals("children") %>'>
					<input name="<portlet:namespace />parentLayoutId" type="hidden" value="<%= (selLayout != null) ? selLayout.getLayoutId() : LayoutImpl.DEFAULT_PARENT_LAYOUT_ID %>">
					<input name="<portlet:namespace />layoutIds" type="hidden" value="">

					<%
					String name = ParamUtil.getString(request, "name");
					String type = ParamUtil.getString(request, "type");
					boolean hidden = ParamUtil.getBoolean(request, "hidden");
					%>

					<%= LanguageUtil.get(pageContext, "add-child-pages") %>

					<br><br>

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "name") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							<input name="<portlet:namespace />name" size="30" type="text" value="<%= name %>">
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<br>
						</td>
					</tr>
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "type") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							<select name="<portlet:namespace />type">

								<%
								for (int i = 0; i < LayoutImpl.TYPES.length; i++) {
								%>

									<option <%= type.equals(LayoutImpl.TYPES[i]) ? "selected" : "" %> value="<%= LayoutImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, "layout.types." + LayoutImpl.TYPES[i]) %></option>

								<%
								}
								%>

							</select>
						</td>
					</tr>
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "hidden") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							<liferay-ui:input-checkbox param="hidden" defaultValue="<%= hidden %>" />
						</td>
					</tr>
					</table>

					<br>

					<input type="submit" value='<%= LanguageUtil.get(pageContext, "add-page") %>'><br>

					<script type="text/javascript">
						document.<portlet:namespace />fm.<portlet:namespace />name.focus();
					</script>

					<%
					List selLayoutChildren = null;

					if (selLayout != null) {
						selLayoutChildren = selLayout.getChildren();
					}
					else {
						selLayoutChildren = LayoutLocalServiceUtil.getLayouts(ownerId, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);
					}
					%>

					<c:if test="<%= (selLayoutChildren != null) && (selLayoutChildren.size() > 0) %>">
						<br><div class="separator"></div><br>

						<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

							<%
							RequiredLayoutException rle = (RequiredLayoutException)errorException;
							%>

							<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
								<%= LanguageUtil.get(pageContext, "you-must-have-at-least-one-page") %>
							</c:if>

							<c:if test="<%= rle.getType() == RequiredLayoutException.FIRST_LAYOUT_TYPE %>">
								<%= LanguageUtil.get(pageContext, "your-first-page-must-be-a-portlet-page") %>
							</c:if>

							<c:if test="<%= rle.getType() == RequiredLayoutException.FIRST_LAYOUT_HIDDEN %>">
								<%= LanguageUtil.get(pageContext, "your-first-page-must-not-be-hidden") %>
							</c:if>
						</liferay-ui:error>

						<%= LanguageUtil.get(pageContext, "set-the-display-order-of-child-pages") %>

						<br><br>

						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<select name="<portlet:namespace />layoutIdsBox" size="7">

								<%
								for (int i = 0; i < selLayoutChildren.size(); i++) {
									Layout selLayoutChild = (Layout)selLayoutChildren.get(i);
								%>

									<option value="<%= selLayoutChild.getLayoutId() %>"><%= selLayoutChild.getName(locale) %></option>

								<%
								}
								%>

								</select>
							</td>
							<td style="padding-left: 10px;"></td>
							<td valign="top">
								<a href="javascript: Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox, 0);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_up.png" vspace="2" width="16"></a><br>
								<a href="javascript: Liferay.Util.reorder(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox, 1);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_down.png" vspace="2" width="16"></a><br>
								<a href="javascript: Liferay.Util.removeItem(document.<portlet:namespace />fm.<portlet:namespace />layoutIdsBox);"><img border="0" height="16" hspace="0" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_x.png" vspace="2" width="16"></a><br>
							</td>
						</tr>
						</table>

						<br>

						<input type="button" value='<%= LanguageUtil.get(pageContext, "update-display-order") %>' onClick="<portlet:namespace />updateDisplayOrder();">
					</c:if>
				</c:when>
				<c:when test='<%= tabs3.equals("look-and-feel") %>'>

					<%
					Theme selTheme = theme;
					ColorScheme selColorScheme = colorScheme;

					if (selLayout != null) {
						selTheme = selLayout.getTheme();
						selColorScheme = selLayout.getColorScheme();
					}
					else {
						LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

						selTheme = layoutSet.getTheme();
						selColorScheme = layoutSet.getColorScheme();
					}
					%>

					<c:if test="<%= selLayout != null %>">
						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<%= LanguageUtil.get(pageContext, "inherit-look-and-feel-from-the-" + (privateLayout ? "public" : "private") + "-root-node") %>
							</td>
							<td style="padding-left: 10px;"></td>
							<td>
								<select name="<portlet:namespace />hidden" onChange="if (this.value == 1) { <portlet:namespace />updateLookAndFeel('', ''); } else { <portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= selColorScheme.getColorSchemeId() %>'); }">
									<option <%= (selLayout.isInheritLookAndFeel()) ? "selected" : "" %> value="1"><%= LanguageUtil.get(pageContext, "yes") %></option>
									<option <%= (!selLayout.isInheritLookAndFeel()) ? "selected" : "" %> value="0"><%= LanguageUtil.get(pageContext, "no") %></option>
								</select>
							</td>
						</table>

						<br>
					</c:if>

					<liferay-ui:tabs names="themes,color-schemes,css" refresh="<%= false %>">
						<liferay-ui:section>

							<%
							List themes = ThemeLocalUtil.getThemes(company.getCompanyId());

							themes = PluginUtil.restrictPlugins(themes, user);

							Iterator itr = themes.iterator();

							while (itr.hasNext()) {
								Theme curTheme = (Theme)itr.next();

								if (!curTheme.isGroupAvailable(liveGroupId)) {
									itr.remove();
								}
							}
							%>

							<liferay-ui:table-iterator
								list="<%= themes %>"
								listType="com.liferay.portal.model.Theme"
								rowLength="2"
								rowPadding="30"
								rowValign="top"
							>
								<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td align="center">
										<%= tableIteratorObj.getName() %> <input <%= selTheme.getThemeId().equals(tableIteratorObj.getThemeId()) ? "checked" : "" %> name="<portlet:namespace />themeId" type="radio" value="<%= tableIteratorObj.getThemeId() %>" onClick="<portlet:namespace />updateLookAndFeel('<%= tableIteratorObj.getThemeId() %>', '', '<%= sectionParam %>', '<%= sectionName %>');"><br>

										<img border="0" hspace="0" src="<%= tableIteratorObj.getContextPath() %><%= tableIteratorObj.getImagesPath() %>/thumbnail.png" vspace="0">
									</td>
								</tr>
								</table>
							</liferay-ui:table-iterator>
						</liferay-ui:section>
						<liferay-ui:section>

							<%
							List colorSchemes = selTheme.getColorSchemes();
							%>

							<c:choose>
								<c:when test="<%= colorSchemes.size() > 0 %>">
									<liferay-ui:table-iterator
										list="<%= colorSchemes %>"
										listType="com.liferay.portal.model.ColorScheme"
										rowLength="2"
										rowPadding="30"
										rowValign="top"
									>
										<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="center">
												<%= tableIteratorObj.getName() %> <input <%= selColorScheme.getColorSchemeId().equals(tableIteratorObj.getColorSchemeId()) ? "checked" : "" %> name="<portlet:namespace />colorSchemeId" type="radio" value="<%= tableIteratorObj.getColorSchemeId() %>" onClick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '<%= tableIteratorObj.getColorSchemeId() %>', '<%= sectionParam %>', '<%= sectionName %>')"><br>

												<img border="0" hspace="0" src="<%= selTheme.getContextPath() %><%= tableIteratorObj.getColorSchemeImagesPath() %>/thumbnail.png" vspace="0">
											</td>
										</tr>
										</table>
									</liferay-ui:table-iterator>
								</c:when>
								<c:otherwise>
									<%= LanguageUtil.get(pageContext, "this-theme-does-not-have-any-color-schemes") %>
								</c:otherwise>
							</c:choose>
						</liferay-ui:section>
						<liferay-ui:section>

							<%
							String cssText = null;

							if ((selLayout != null) && !selLayout.isInheritLookAndFeel()) {
								cssText = selLayout.getCssText();
							}
							else {
								LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

								cssText = layoutSet.getCss();
							}
							%>

							<%= LanguageUtil.get(pageContext, "insert-custom-css-that-will-loaded-after-the-theme") %>

							<br><br>

							<textarea name="<portlet:namespace />css" style="height: 200px; width: 400px"><%= cssText %></textarea>

							<br><br>

							<input type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />updateLookAndFeel('<%= selTheme.getThemeId() %>', '', '<%= sectionParam %>', '<%= sectionName %>');"/>
						</liferay-ui:section>
					</liferay-ui:tabs>
				</c:when>
				<c:when test='<%= tabs3.equals("logo") %>'>
					<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

					<%
					LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);
					%>

					<%= LanguageUtil.get(pageContext, "upload-a-logo-for-the-" + (privateLayout ? "private" : "public") + "-pages-that-will-be-used-instead-of-the-default-enterprise-logo") %>

					<br><br>

					<c:if test="<%= layoutSet.isLogo() %>">
						<img src="<%= themeDisplay.getPathImage() %>/layout_set_logo?img_id=<%= layoutSet.getOwnerId() %>">

						<br><br>
					</c:if>

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "logo") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							<input name="<portlet:namespace />logoFileName" size="30" type="file" onChange="document.<portlet:namespace />fm.<portlet:namespace />logo.value = true; document.<portlet:namespace />fm.<portlet:namespace />logoCheckbox.checked = true;">
						</td>
					</tr>
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "use-logo") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td>
							<liferay-ui:input-checkbox param="logo" defaultValue="<%= layoutSet.isLogo() %>" />
						</td>
					</tr>
					</table>

					<br>

					<input type="button" value='<%= LanguageUtil.get(pageContext, "save") %>' onClick="<portlet:namespace />updateLogo();">
				</c:when>
				<c:when test='<%= tabs3.equals("import-export") %>'>
					<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

					<c:if test="<%= !layout.getOwnerId().equals(ownerId) %>">
						<%= LanguageUtil.get(pageContext, "import-a-lar-file-to-overwrite-the-current-pages") %>

						<br><br>

						<input name="<portlet:namespace />importFileName" size="50" type="file">

						<input type="button" value='<%= LanguageUtil.get(pageContext, "import") %>' onClick="<portlet:namespace />importPages();">

						<br><br>
					</c:if>

					<%= LanguageUtil.get(pageContext, "export-the-current-pages-to-the-given-lar-file-name") %>

					<br><br>

					<input name="<portlet:namespace />exportFileName" size="50" type="text" value="<%= StringUtil.replace(rootNodeName, " ", "_") %>-<%= Time.getShortTimestamp() %>.lar">

					<br><br>

					<%= LanguageUtil.get(pageContext, "do-you-want-to-export-portlet-preferences") %>

					<liferay-ui:input-checkbox param="exportPortletPreferences" defaultValue="<%= false %>" />

					<br><br>

					<%= LanguageUtil.get(pageContext, "do-you-want-to-export-portlet-data") %>

					<liferay-ui:input-checkbox param="exportPortletData" defaultValue="<%= false %>" />

					<br><br>

					<%= LanguageUtil.get(pageContext, "do-you-want-to-export-permissions") %>

					<liferay-ui:input-checkbox param="exportPermissions" defaultValue="<%= false %>" />

					<br><br>

					<%= LanguageUtil.get(pageContext, "do-you-want-to-export-the-root-theme") %>

					<liferay-ui:input-checkbox param="exportTheme" defaultValue="<%= false %>" />

					<br><br>

					<input type="button" value='<%= LanguageUtil.get(pageContext, "export") %>' onClick="self.location = '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/communities/export_pages" /><portlet:param name="ownerId" value="<%= ownerId %>" /></portlet:actionURL>&<portlet:namespace />exportFileName=' + document.<portlet:namespace />fm.<portlet:namespace />exportFileName.value + '&<portlet:namespace />exportPortletPreferences=' + document.<portlet:namespace />fm.<portlet:namespace />exportPortletPreferences.value + '&<portlet:namespace />exportPortletData=' + document.<portlet:namespace />fm.<portlet:namespace />exportPortletData.value + '&<portlet:namespace />exportPermissions=' + document.<portlet:namespace />fm.<portlet:namespace />exportPermissions.value + '&<portlet:namespace />exportTheme=' + document.<portlet:namespace />fm.<portlet:namespace />exportTheme.value;">
				</c:when>
				<c:when test='<%= tabs3.equals("virtual-host") %>'>
					<%= LanguageUtil.get(pageContext, "enter-the-public-and-private-virtual-host-that-will-map-to-the-public-and-private-friendly-url") %>

					<%= LanguageUtil.format(pageContext, "for-example,-if-the-public-virtual-host-is-www.helloworld.com-and-the-friendly-url-is-/helloworld", new Object[] {Http.getProtocol(request), Http.getProtocol(request) + "://" + company.getPortalURL() + themeDisplay.getPathFriendlyURLPublic()}) %>

					<br><br>

					<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "public-virtual-host") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td nowrap>

							<%
							String publicOwnerId = LayoutImpl.PUBLIC + LayoutImpl.getGroupId(ownerId);

							LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(publicOwnerId);

							String publicVirtualHost = ParamUtil.getString(request, "publicVirtualHost", BeanParamUtil.getString(publicLayoutSet, request, "virtualHost"));
							%>

							<input name="<portlet:namespace />publicVirtualHost" size="50" type="text" value="<%= publicVirtualHost %>">
						</td>
					</tr>
					<tr>
						<td>
							<%= LanguageUtil.get(pageContext, "private-virtual-host") %>
						</td>
						<td style="padding-left: 10px;"></td>
						<td nowrap>

							<%
							String privateOwnerId = LayoutImpl.PRIVATE + LayoutImpl.getGroupId(ownerId);

							LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(privateOwnerId);

							String privateVirtualHost = ParamUtil.getString(request, "privateVirtualHost", BeanParamUtil.getString(privateLayoutSet, request, "virtualHost"));
							%>

							<input name="<portlet:namespace />privateVirtualHost" size="50" type="text" value="<%= privateVirtualHost %>">
						</td>
					</tr>
					</table>

					<c:if test="<%= group.isCommunity() %>">
						<br>

						<%= LanguageUtil.get(pageContext, "enter-the-friendly-url-that-will-be-used-by-both-public-and-private-pages") %>

						<%= LanguageUtil.format(pageContext, "the-friendly-url-is-appended-to-x-for-public-pages-and-x-for-private-pages", new Object[] {Http.getProtocol(request) + "://" + company.getPortalURL() + themeDisplay.getPathFriendlyURLPublic(), Http.getProtocol(request) + "://" + company.getPortalURL() + group.getPathFriendlyURL(false, themeDisplay)}) %>

						<br><br>

						<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<%= LanguageUtil.get(pageContext, "friendly-url") %>
							</td>
							<td style="padding-left: 10px;"></td>
							<td nowrap>

								<%
								String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");
								%>

								<input name="<portlet:namespace />friendlyURL" size="30" type="text" value="<%= friendlyURL %>">
							</td>
						</tr>
						</table>
					</c:if>

					<br>

					<input type="submit" value='<%= LanguageUtil.get(pageContext, "save") %>'>
				</c:when>
				<c:when test='<%= tabs3.equals("sitemap") %>'>

					<%
					String host = PortalUtil.getHost(request);

					String sitemapUrl = PortalUtil.getPortalURL(host, request.getServerPort(), request.isSecure()) + themeDisplay.getPathRoot() + "/sitemap.xml";

					LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

					String virtualHost = layoutSet.getVirtualHost();

					if (!host.equals(virtualHost)) {
						sitemapUrl += "?ownerId=" + ownerId;
					}
					%>

					<%= LanguageUtil.get(pageContext, "the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website") %> <%= LanguageUtil.format(pageContext, "see-x-for-more-information", "<a href=\"http://www.sitemaps.org\" target=\"_blank\">http://www.sitemaps.org</a>") %>

					<br><br>

					<%= LanguageUtil.format(pageContext, "send-sitemap-information-to-preview", new Object[] {"<a target=\"_blank\" href=\"" + sitemapUrl + "\">", "</a>"}) %>

					<ul>
						<li><a href="http://www.google.com/webmasters/sitemaps/ping?sitemap=<%= sitemapUrl %>" target="_blank">Google</a>
						<li><a href="https://siteexplorer.search.yahoo.com/submit/ping?sitemap=<%= sitemapUrl %>" target="_blank">Yahoo!</a> (<%= LanguageUtil.get(pageContext, "requires-login") %>)
					</ul>
				</c:when>
			</c:choose>
		</td>
	</tr>
	</table>
</c:if>

</form>