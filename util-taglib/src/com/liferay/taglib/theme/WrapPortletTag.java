/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.theme;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;
import com.liferay.taglib.util.ThemeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * <a href="WrapPortletTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WrapPortletTag extends ParamAndPropertyAncestorTagImpl {

	public static String doTag(
			String wrapPage, String portletPage, ServletContext servletContext,
			HttpServletRequest request, StringServletResponse stringResponse,
			PageContext pageContext)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		// Portlet content

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(portletPage);

		requestDispatcher.include(request, stringResponse);

		portletDisplay.setContent(stringResponse.getString());

		// Page

		stringResponse.recycle();

		String content = ThemeUtil.includeVM(
			servletContext, request, pageContext, wrapPage, theme, false);

		return _CONTENT_WRAPPER_PRE.concat(content).concat(
			_CONTENT_WRAPPER_POST);
	}

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		_bodyContentString = getBodyContent().getString();

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse stringResponse = getServletResponse();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Theme theme = themeDisplay.getTheme();
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			// Portlet content

			portletDisplay.setContent(_bodyContentString);

			// Page

			ThemeUtil.include(
				servletContext, request, stringResponse, pageContext, getPage(),
				theme);

			pageContext.getOut().print(stringResponse.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			clearParams();
			clearProperties();
		}
	}

	protected String getPage() {
		return _page;
	}

	public void setPage(String page) {
		_page = page;
	}

	private static final String _CONTENT_WRAPPER_PRE =
		"<div id=\"content-wrapper\" class=\"column-1\">";

	private static final String _CONTENT_WRAPPER_POST = "</div>";

	private String _page;
	private String _bodyContentString = StringPool.BLANK;

}