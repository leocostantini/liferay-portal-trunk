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

			<div id="bottom-container">
				<div id="bottom-content">
					<a href="<%= themeDisplay.getURLAbout() %>"><bean:message key="about" /></a> - <a href="<%= themeDisplay.getURLDisclaimer() %>"><bean:message key="disclaimer" /></a>

					<script language="JavaScript">
						if (is_ie_5_up) {
							document.write("- <a style=\"cursor: hand\" onClick=\"this.style.behavior='url(#default#homepage)'; this.setHomePage('<%= themeDisplay.getURLPortal() %>');\"><%= LanguageUtil.format(pageContext, "make-x-my-start-page", company.getShortName(), false) %></a>");
						}
					</script>
				</div>
			</div>
		</div><!-- layout-box -->
		</div><!-- layout-box-inner-decoration -->
		</div><!-- layout-box-outer-decoration -->

		<div id="layout-bottom-decoration">
			<div id="layout-corner-bl"></div>
			<div id="layout-corner-br"></div>
		</div>

		<div id="layout-bottom-decoration-2">
			<div id="layout-corner-2-bl"></div>
			<div id="layout-corner-2-br"></div>
		</div>
	</div>
</div>
