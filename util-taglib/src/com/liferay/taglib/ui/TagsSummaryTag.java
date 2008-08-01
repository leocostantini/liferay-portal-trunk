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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="TagsSummaryTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsSummaryTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:tags_summary:className", _className);
		request.setAttribute(
			"liferay-ui:tags_summary:classPK", String.valueOf(_classPK));
		request.setAttribute(
				"liferay-ui:tags_summary:folksonomy", 
				String.valueOf(_folksonomy));
		request.setAttribute("liferay-ui:tags_summary:message", _message);
		request.setAttribute("liferay-ui:tags_summary:portletURL", _portletURL);

		return EVAL_BODY_BUFFERED;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setFolksonomy(boolean folksonomy) {
		_folksonomy = folksonomy;
	}
	
	public void setMessage(String message) {
		_message = message;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/tags_summary/page.jsp";

	private String _className;
	private long _classPK;
	private boolean _folksonomy;
	private String _message;
	private PortletURL _portletURL;

}