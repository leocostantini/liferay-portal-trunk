<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<c:if test="<%= ShutdownUtil.isInProcess() %>">
	<div class="popup-alert-notice">
		<span class="notice-label"><liferay-ui:message key="maintenance-alert" /></span> <span class="notice-date"><%= DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(Time.getDate(CalendarFactoryUtil.getCalendar(timeZone))) %> <%= timeZone.getDisplayName(false, TimeZone.SHORT, locale) %></span>
		<span class="notice-message"><%= LanguageUtil.format(pageContext, "the-portal-will-shutdown-for-maintenance-in-x-minutes", String.valueOf(ShutdownUtil.getInProcess() / Time.MINUTE), false) %></span>

		<c:if test="<%= Validator.isNotNull(ShutdownUtil.getMessage()) %>">
			<span class="custom-shutdown-message"><%= HtmlUtil.escape(ShutdownUtil.getMessage()) %></span>
		</c:if>
	</div>
</c:if>

<c:if test="<%= themeDisplay.isImpersonated() %>">
	<div class="popup-alert-notice">
		<span class="notice-message">
			<c:choose>
				<c:when test="<%= themeDisplay.isSignedIn() %>">
					<%= LanguageUtil.format(pageContext, "hi-x-you-are-impersonating-x", new Object[] {realUser.getFullName(), user.getFullName()}) %>
				</c:when>
				<c:otherwise>
					<%= LanguageUtil.format(pageContext, "hi-x-you-are-impersonating-the-guest-user", new Object[] {realUser.getFullName()}) %>
				</c:otherwise>
			</c:choose>
		</span>

		<%= LanguageUtil.format(pageContext, "click-here-to-be-yourself-again", new Object[] {"<a href=\"" + PortalUtil.getLayoutURL(layout, themeDisplay, false) + "\">", "</a>"}) %>

		<%
		Locale realUserLocale = realUser.getLocale();
		Locale userLocale = user.getLocale();
		%>

		<c:if test="<%= !realUserLocale.equals(userLocale) %>">

			<%
			boolean isRealUsersLanguage = (locale.getLanguage().equals(realUserLocale.getLanguage()) && locale.getCountry().equals(realUserLocale.getCountry()));

			String changeLanguage = null;
			String languageId = null;
			String language = null;

			if (!isRealUsersLanguage) {
				changeLanguage = "change-to-your-language";
				languageId = realUserLocale.getLanguage() + "_" + realUserLocale.getCountry();
				language = realUserLocale.getDisplayName(realUserLocale);
			}
			else {
				changeLanguage = "change-to-this-users-language";
				languageId = userLocale.getLanguage() + "_" + userLocale.getCountry();
				language = userLocale.getDisplayName(realUserLocale);
			}
			%>

			<div class="current-user-language" >
				<a href="<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsUserLanguageId", languageId) %>"><%= LanguageUtil.get(realUserLocale, changeLanguage) %>: <%= language %></a>
			</div>
		</c:if>
	</div>
</c:if>