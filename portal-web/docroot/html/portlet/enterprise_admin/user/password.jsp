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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute("user.passwordPolicy");

boolean passwordReset = BeanParamUtil.getBoolean(selUser, request, "passwordReset");
%>

<liferay-ui:error-marker key="errorSection" value="password" />

<aui:model-context bean="<%= selUser %>" model="<%= User.class %>" />

<h3><liferay-ui:message key="password" /></h3>

<liferay-ui:error exception="<%= UserPasswordException.class %>">

	<%
	UserPasswordException upe = (UserPasswordException)errorException;
	%>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_ALREADY_USED %>">
		<liferay-ui:message key="that-password-has-already-been-used-please-enter-in-a-different-password" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS %>">
		<liferay-ui:message key="that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_INVALID %>">
		<liferay-ui:message key="that-password-is-invalid-please-enter-in-a-different-password" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_LENGTH %>">
		<%= LanguageUtil.format(pageContext, "that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters", String.valueOf(passwordPolicy.getMinLength()), false) %>
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_NOT_CHANGEABLE %>">
		<liferay-ui:message key="your-password-cannot-be-changed" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_SAME_AS_CURRENT %>">
		<liferay-ui:message key="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-in-a-different-password" />
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_TOO_YOUNG %>">
		<%= LanguageUtil.format(pageContext, "you-cannot-change-your-password-yet-please-wait-at-least-x-before-changing-your-password-again", LanguageUtil.getTimeDescription(pageContext, passwordPolicy.getMinAge() * 1000), false) %>
	</c:if>

	<c:if test="<%= upe.getType() == UserPasswordException.PASSWORDS_DO_NOT_MATCH %>">
		<liferay-ui:message key="the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password" />
	</c:if>
</liferay-ui:error>

<aui:fieldset>
	<aui:input autocomplete="off" label="password" name="password1" size="30" type="password" />

	<aui:input autocomplete="off" label="enter-again" name="password2" size="30" type="password" />

	<c:if test="<%= (selUser != null) && (user.getUserId() != selUser.getUserId()) %>">
		<aui:input inlineLabel="left" label="password-reset-required" name="passwordReset" type="checkbox" value="<%= passwordReset %>" />
	</c:if>
</aui:fieldset>

<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_ENABLED && portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<h3><liferay-ui:message key="reminder" /></h3>

	<%
	boolean hasCustomQuestion = true;
	%>

	<aui:fieldset>
		<aui:select label="question" name="reminderQueryQuestion">

			<%
			Set<String> questions = selUser.getReminderQueryQuestions();

			for (String question : questions) {
				if (selUser.getReminderQueryQuestion().equals(question)) {
					hasCustomQuestion = false;
			%>

					<aui:option label="<%= question %>" selected="<%= true %>" value="<%= question %>" />

			<%
				}
				else {
			%>

					<aui:option label="<%= question %>" />

			<%
				}
			}

			if (hasCustomQuestion && Validator.isNull(selUser.getReminderQueryQuestion())) {
				hasCustomQuestion = false;
			}
			%>

			<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
				<aui:option label="write-my-own-question" selected="<%= hasCustomQuestion %>" value="<%= EnterpriseAdminUtil.CUSTOM_QUESTION %>" />
			</c:if>
		</aui:select>

		<c:if test="<%= PropsValues.USERS_REMINDER_QUERIES_CUSTOM_QUESTION_ENABLED %>">
			<div id="<portlet:namespace />customQuestionDiv">
				<aui:input fieldParam="reminderQueryCustomQuestion" label="custom-question" name="reminderQueryQuestion" />
			</div>
		</c:if>

		<aui:input label="answer" name="reminderQueryAnswer" size="50" value="<%= selUser.getReminderQueryAnswer() %>" />
	</aui:fieldset>

	<aui:script use="event,node">
		var reminderQueryQuestion = A.one('#<portlet:namespace />reminderQueryQuestion');
		var customQuestionDiv = A.one('#<portlet:namespace />customQuestionDiv');

		if (<%= !hasCustomQuestion %> && customQuestionDiv) {
			customQuestionDiv.hide();
		}

		if (reminderQueryQuestion) {
			reminderQueryQuestion.on(
				'change',
				function(event) {
					if (event.target.val() == '<%= EnterpriseAdminUtil.CUSTOM_QUESTION %>') {
						var reminderQueryCustomQuestion = A.one('#<portlet:namespace />reminderQueryCustomQuestion');

						if (customQuestionDiv) {
							customQuestionDiv.show();
						}

						<%
						for (String question : PropsValues.USERS_REMINDER_QUERIES_QUESTIONS) {
						%>

							if (reminderQueryCustomQuestion && (reminderQueryCustomQuestion.val() == '<%= UnicodeFormatter.toString(question) %>')) {
								reminderQueryCustomQuestion.val('');
							}

						<%
						}
						%>

						Liferay.Util.focusFormField(reminderQueryCustomQuestion);
					}
					else{
						if (customQuestionDiv) {
							customQuestionDiv.hide();
						}

						Liferay.Util.focusFormField(A.one('#<portlet:namespace />reminderQueryAnswer'));
					}
				}
			);
		}
	</aui:script>
</c:if>