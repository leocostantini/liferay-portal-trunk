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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBCategory category = message.getCategory();

MBThread thread = MBThreadLocalServiceUtil.getThread(message.getThreadId());

long messageId = message.getMessageId();

long categoryId = message.getCategoryId();
long threadId = message.getThreadId();

MBMessage curParentMessage = null;
String parentAuthor = null;

String body = StringPool.BLANK;
boolean quote = false;
%>

<aui:script>
	function <portlet:namespace />splitThread() {
		document.<portlet:namespace />fm.<portlet:namespace />body.value = <portlet:namespace />getHTML();
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectCategory(categoryId, categoryName) {
		document.<portlet:namespace />fm.<portlet:namespace />mbCategoryId.value = categoryId;

		var nameEl = document.getElementById("<portlet:namespace />categoryName");

		nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/view" /></portlet:renderURL>&<portlet:namespace />mbCategoryId=" + categoryId;
		nameEl.innerHTML = categoryName + "&nbsp;";
	}

	function <portlet:namespace />toggleExplanationPost() {
		if (document.getElementById("<portlet:namespace />addExplanationPostCheckbox").checked) {
			document.getElementById("<portlet:namespace />explanationPost").style.display = "";
		}
		else {
			document.getElementById("<portlet:namespace />explanationPost").style.display = "none";
		}
	}
</aui:script>

<portlet:actionURL var="splitThreadURL">
	<portlet:param name="struts_action" value="/message_boards/split_thread" />
</portlet:actionURL>

<aui:form action="<%= splitThreadURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "splitThread(); return false;" %>'>
	<aui:input name="messageId" type="hidden" value="<%= messageId %>" />
	<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />

	<liferay-ui:tabs
		names="message"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<liferay-ui:error exception="<%= MessageBodyException.class %>" message="please-enter-a-valid-message" />
	<liferay-ui:error exception="<%= MessageSubjectException.class %>" message="please-enter-a-valid-subject" />
	<liferay-ui:error exception="<%= NoSuchCategoryException.class %>" message="please-enter-a-valid-category" />
	<liferay-ui:error exception="<%= SplitThreadException.class %>" message="a-thread-cannot-be-split-at-its-root-message" />

	<%
	long breadcrumbsMessageId = message.getMessageId();
	%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="click-ok-to-create-a-new-thread-with-the-following-messages" />
	</div>

	<%
	MBMessageDisplay messageDisplay = MBMessageLocalServiceUtil.getMessageDisplay(messageId, StatusConstants.APPROVED, MBThreadConstants.THREAD_VIEW_TREE);

	MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

	List messages = new ArrayList();

	messages.addAll(treeWalker.getMessages());

	messages = ListUtil.sort(messages, new MessageCreateDateComparator(true));
	%>

	<table class="toggle_id_message_boards_view_message_thread" id="toggle_id_message_boards_view_message_thread" style="display: <liferay-ui:toggle-value id="toggle_id_message_boards_view_message_thread" />;" width="100%">

	<%
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, message);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(false));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
	%>

	<liferay-util:include page="/html/portlet/message_boards/view_thread_shortcut.jsp" />

	</table>

	<br />

	<aui:fieldset>
		<aui:input inlineLabel="left" label="add-explanation-post-to-the-source-thread" name="addExplanationPost" onClick='<%= renderResponse.getNamespace() + "toggleExplanationPost();" %>' type="checkbox" />

		<div id="<portlet:namespace/>explanationPost" style="display: none;">

			<div class="portlet-msg-info">
				<liferay-ui:message key="the-following-post-will-be-added-in-place-of-the-moved-message" />
			</div>

			<aui:input model="<%= MBMessage.class %>" name="subject" value='<%= LanguageUtil.get(pageContext, "thread-splitted") %>' />

			<aui:field-wrapper label="body">
				<%@ include file="/html/portlet/message_boards/bbcode_editor.jspf" %>

				<aui:input name="body" type="hidden" />

				<aui:script use="liferay-bbcode-editor">
					<portlet:namespace />bbCode.setHTML('<%= LanguageUtil.format(pageContext, "the-new-thread-can-be-found-at-x", "[url=[$NEW_THREAD_URL$]][$NEW_THREAD_URL$][/url]") %>');
				</aui:script>
			</aui:field-wrapper>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<%
MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "split-thread"), currentURL);
%>