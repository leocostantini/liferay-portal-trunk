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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

long categoryId = BeanParamUtil.getLong(entry, request, "categoryId", BlogsCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);

String categoryName = StringPool.BLANK;

if (categoryId > 0) {
	try {
		BlogsCategory category = BlogsCategoryLocalServiceUtil.getCategory(categoryId);

		categoryName = category.getName();
	}
	catch (NoSuchCategoryException nsce) {
	}
}

String content = BeanParamUtil.getString(entry, request, "content");

Calendar displayDate = new GregorianCalendar(timeZone, locale);

if (entry != null) {
	if (entry.getDisplayDate() != null) {
		displayDate.setTime(entry.getDisplayDate());
	}
}
%>

<script type="text/javascript">
	function initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= entry == null ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />content.value = parent.<portlet:namespace />editor.getHTML();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectCategory(categoryId, categoryName) {
		document.<portlet:namespace />fm.<portlet:namespace />categoryId.value = categoryId;

		var nameEl = document.getElementById("<portlet:namespace />categoryName");

		nameEl.href = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/blogs/view" /><portlet:param name="tabs1" value="categories" /></portlet:renderURL>&<portlet:namespace />categoryId=" + categoryId;
		nameEl.innerHTML = categoryName + "&nbsp;";
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/blogs/edit_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />entryId" type="hidden" value="<%= entryId %>" />

<liferay-ui:tabs names="entry" />

<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />

<table class="liferay-table">
<tr>
	<td>
		<liferay-ui:message key="title" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="title" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="category" />
	</td>
	<td>
		<input name="<portlet:namespace />categoryId" type="hidden" value="<%= categoryId %>" />

		<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/blogs/view" /><portlet:param name="tabs1" value="categories" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>" id="<portlet:namespace />categoryName">
		<%= categoryName %>
		</a>

		<input type="button" value="<liferay-ui:message key="select" />" onClick="var categoryWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/blogs/select_category" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>', 'category', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680'); void(''); categoryWindow.focus();" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-date" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= BlogsEntry.class %>" bean="<%= entry %>" field="displayDate" defaultValue="<%= displayDate %>" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="content" />
	</td>
	<td>
		<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

		<input name="<portlet:namespace />content" type="hidden" value="" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="tags" />
	</td>
	<td>

		<%
		long classPK = 0;

		if (entry != null) {
			classPK = entry.getEntryId();
		}
		%>

		<liferay-ui:tags-selector
			className="<%= BlogsEntry.class.getName() %>"
			classPK="<%= classPK %>"
			hiddenInput="tagsEntries"
		/>
	</td>
</tr>

<c:if test="<%= entry == null %>">
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="permissions" />
		</td>
		<td>
			<liferay-ui:input-permissions
				modelName="<%= BlogsEntry.class.getName() %>"
			/>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />title.focus();
</script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp";
%>