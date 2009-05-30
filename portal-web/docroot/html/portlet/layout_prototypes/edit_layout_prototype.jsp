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

<%@ include file="/html/portlet/layout_prototypes/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

LayoutPrototype layoutPrototype = (LayoutPrototype)request.getAttribute(WebKeys.LAYOUT_PROTOTYPE);

if (layoutPrototype == null) {
	layoutPrototype = new LayoutPrototypeImpl();

	layoutPrototype.setActive(true);
}

long layoutPrototypeId = BeanParamUtil.getLong(layoutPrototype, request, "layoutPrototypeId");

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();
%>

<script type="text/javascript">
	function <portlet:namespace />saveLayoutPrototype() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= layoutPrototype == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/layout_prototypes/edit_layout_prototype" /></portlet:actionURL>");
	}
</script>

<liferay-util:include page="/html/portlet/layout_prototypes/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= layoutPrototype.isNew() ? "add" : "view-all" %>' />
</liferay-util:include>

<form class="exp-form" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveLayoutPrototype(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />layoutPrototypeId" type="hidden" value="<%= layoutPrototypeId %>" />

<fieldset class="exp-block-labels">
	<c:if test="<%= !layoutPrototype.isNew() %>">
		<div class="exp-ctrl-holder">
			<label><%= LanguageUtil.get(pageContext, "old-name") %></label>

			<%= HtmlUtil.escape(layoutPrototype.getName()) %>
		</div>
	</c:if>

	<div class="exp-ctrl-holder">
		<label><%= LanguageUtil.get(pageContext, (!layoutPrototype.isNew() ? "new-name" : "name")) %></label>

		<liferay-ui:input-field model="<%= LayoutPrototype.class %>" bean="<%= layoutPrototype %>" field="name" />
	</div>

	<div class="exp-ctrl-holder">
		<label><%= LanguageUtil.get(pageContext, "active") %></label>

		<liferay-ui:input-field model="<%= LayoutPrototype.class %>" bean="<%= layoutPrototype %>" field="active" />
	</div>

	<div class="exp-ctrl-holder">
		<label><liferay-ui:message key="title" /></label>

		<input id="<portlet:namespace />title_<%= defaultLanguageId %>" name="<portlet:namespace />title_<%= defaultLanguageId %>" size="30" type="text" value="<%= HtmlUtil.escape(layoutPrototype.getTitle(defaultLocale)) %>" />

		<img class="default-language" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= defaultLanguageId %>.png" />

		<%
		List<String> languageIds = new ArrayList<String>();

		for (int i = 0; i < locales.length; i++) {
			if (locales[i].equals(defaultLocale)) {
				continue;
			}

			if (Validator.isNotNull(layoutPrototype.getTitle(locales[i], false))) {
				languageIds.add(LanguageUtil.getLanguageId(locales[i]));
			}
		}
		%>

		<a class="lfr-floating-trigger" href="javascript:;" id="<portlet:namespace />languageSelectorTrigger">
			<liferay-ui:message key="other-languages" /> (<%= languageIds.size() %>)
		</a>

		<%
		if (languageIds.isEmpty()) {
			languageIds.add(StringPool.BLANK);
		}
		%>

		<div class="lfr-floating-container" id="<portlet:namespace />languageSelector">
			<div class="lfr-panel">
				<div class="lfr-panel-titlebar">
					<h3 class="lfr-panel-title"><span><liferay-ui:message key="other-languages" /></span></h3>
				</div>

				<div class="lfr-panel-content">

					<%
					for (int i = 0; i < languageIds.size(); i++) {
						String languageId = languageIds.get(i);
					%>

						<div class="lfr-form-row">
							<div class="row-fields">
								<div class="exp-ctrl-holder exp-form-column">
									<img class="language-flag" src="<%= themeDisplay.getPathThemeImages() %>/language/<%= Validator.isNotNull(languageId) ? languageId : "../spacer" %>.png" />

									<select id="<portlet:namespace />languageId<%= i %>">
										<option value="" />

										<%
										for (int j = 0; j < locales.length; j++) {
											if (locales[j].equals(defaultLocale)) {
												continue;
											}

											String optionStyle = StringPool.BLANK;

											if (Validator.isNotNull(layoutPrototype.getTitle(locales[j], false))) {
												optionStyle = "style=\"font-weight: bold\"";
											}
										%>

											<option <%= (languageId.equals(LocaleUtil.toLanguageId(locales[j]))) ? "selected" : "" %> <%= optionStyle %> value="<%= LocaleUtil.toLanguageId(locales[j]) %>"><%= locales[j].getDisplayName(locale) %></option>

										<%
										}
										%>

									</select>
								</div>

								<div class="exp-ctrl-holder exp-form-column">
									<label><liferay-ui:message key="title" /></label>

									<input class="language-value" name="<portlet:namespace />title_<%= languageId %>" type="text" value="<%= HtmlUtil.escape(layoutPrototype.getTitle(languageId, false)) %>" />
								</div>
							</div>
						</div>

					<%
					}
					%>

				</div>
			</div>
		</div>

	</div>

	<div class="exp-ctrl-holder">
		<label><liferay-ui:message key="description" /></label>

		<liferay-ui:input-field model="<%= LayoutPrototype.class %>" bean="<%= layoutPrototype %>" field="description" />
	</div>

	<c:if test="<%= !layoutPrototype.isNew() %>">
		<div class="exp-ctrl-holder">
			<label><liferay-ui:message key="configuration" /></label>

			<liferay-portlet:actionURL var="viewURL"  portletName="<%= PortletKeys.MY_PLACES %>">
				<portlet:param name="struts_action" value="/my_places/view" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutPrototype.getGroup().getGroupId()) %>" />
				<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon image="view" message="open-page-template" url="<%= viewURL %>" method="get" target="_blank" label="<%= true %>" /> (<liferay-ui:message key="new-window" />)
		</div>
	</c:if>

	<div class="exp-button-holder">
		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />
	</div>
</fieldset>

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>

<script type="text/javascript">
	jQuery(
		function () {
			new Liferay.AutoFields(
				{
					container: '#<portlet:namespace />languageSelector .lfr-panel-content',
					baseRows: '#<portlet:namespace />languageSelector .lfr-form-row'
				}
			);

			var panel = new Liferay.PanelFloating(
				{
					container: '#<portlet:namespace />languageSelector',
					trigger: '#<portlet:namespace />languageSelectorTrigger',
					width: 500,
					isCollapsible: false
				}
			);

			panel.bind(
				'hide',
				function(event) {
					var instance = this;

					var container = instance.get('container');

					jQuery(document.<portlet:namespace />fm).append(container);
				}
			);

			jQuery('#<portlet:namespace />languageSelector select').change(
				function(event) {
					var selectedOption = this[this.selectedIndex];
					var selectedValue = selectedOption.value;

					var newName = '<portlet:namespace />title_';

					var currentRow = jQuery(this).parents('.lfr-form-row:first');

					var img = currentRow.find('img.language-flag');
					var imgSrc = 'spacer';

					if (selectedValue) {
						newName ='<portlet:namespace />title_' + selectedValue;

						imgSrc = 'language/' + selectedValue;
					}

					var inputField = currentRow.find('.language-value');

					inputField.attr('name', newName);
					inputField.attr('id', newName);

					img.attr('src', '<%= themeDisplay.getPathThemeImages() %>/' + imgSrc + '.png');
				}
			);
		}
	);
</script>