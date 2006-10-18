<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<script type="text/javascript">

	<%
	int sessionTimeout = GetterUtil.getInteger(PropsUtil.get(PropsUtil.SESSION_TIMEOUT));
	sessionTimeout = 2;
	int sessionTimeoutWarning = GetterUtil.getInteger(PropsUtil.get(PropsUtil.SESSION_TIMEOUT_WARNING));
	int sessionTimeoutWarningMinute = sessionTimeoutWarning * (int)Time.MINUTE;
	int timeoutDiff = (sessionTimeout - sessionTimeoutWarning) * (int)Time.MINUTE;

	Calendar sessionTimeoutCal = new GregorianCalendar(timeZone);

	sessionTimeoutCal.add(Calendar.MILLISECOND, sessionTimeout * (int)Time.MINUTE);
	%>

	<c:if test="<%= (themeDisplay.isSignedIn()) && (sessionTimeoutWarning > 0) && (timeoutDiff > 0) %>">
		setTimeout("openSessionWarning()", <%= timeoutDiff %>);
	</c:if>

	function extendSession() {
		loadPage("<%= themeDisplay.getPathMain() %>/portal/extend_session");

		setTimeout("openSessionWarning()", <%= timeoutDiff %>);
	}

	function openSessionWarning() {
		var message = Alerts.fireMessageBox({
				modal: true,
				width: 300
			});
			
		var url = "<%= themeDisplay.getPathMain() %>/portal/extend_session_confirm?p_p_state=<%= LiferayWindowState.POP_UP %>";
		
		Ajax.update(url, message);
			
		message.style.background = "<%= colorScheme.getBodyBg() %>";
		message.style.border = "1px solid <%= colorScheme.getPortletMsgAlert() %>";
		
		setTimeout("sessionHasExpired()", <%= sessionTimeoutWarningMinute %>);
	}

	function sessionHasExpired() {
		document.getElementById("session_warning_text").innerHTML = "<%= UnicodeLanguageUtil.get(pageContext, "warning-due-to-inactivity-your-session-has-expired") %>";
		document.getElementById("ok_btn").onclick = Alerts.killAlert;
		document.getElementById("cancel_btn").style.display = "none";

		loadPage("<%= themeDisplay.getPathMain() %>/portal/expire_session");
	}
	
</script>