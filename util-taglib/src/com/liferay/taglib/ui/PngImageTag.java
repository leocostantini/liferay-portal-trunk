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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * <a href="PngImageTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PngImageTag extends IncludeTag {

	public static void doTag(
			String image, String height, String width,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		doTag(_PAGE, image, height, width, servletContext, request, response);
	}

	public static void doTag(
			String page, String image, String height, String width,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		request.setAttribute("liferay-ui:png_image:image", image);
		request.setAttribute("liferay-ui:png_image:height", height);
		request.setAttribute("liferay-ui:png_image:width", width);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	public int doEndTag() throws JspException {
		try {
			ServletContext servletContext = getServletContext();
			HttpServletRequest request = getServletRequest();
			StringServletResponse response = getServletResponse();

			doTag(
				getPage(), _image, _height, _width, servletContext, request,
				response);

			pageContext.getOut().print(response.getString());

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setHeight(String height) {
		_height = height;
	}

	public void setWidth(String width) {
		_width = width;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/png_image/page.jsp";

	private String _image;
	private String _height;
	private String _width;

}