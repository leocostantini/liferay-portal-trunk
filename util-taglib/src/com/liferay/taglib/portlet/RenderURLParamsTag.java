/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.portlet;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.StringTokenizer;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="RenderURLParamsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RenderURLParamsTag extends TagSupport {

	public static String doTag(String varImpl, PageContext pageContext)
		throws Exception {

		PortletURL portletURL = (PortletURL)pageContext.getAttribute(varImpl);

		String params = StringPool.BLANK;

		if (portletURL != null) {
			params = _toParamsString(portletURL, pageContext);

			pageContext.getOut().print(params);
		}

		return params;
	}

	public int doEndTag() throws JspException {
		try {
			doTag(_varImpl, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setVarImpl(String varImpl) {
		_varImpl = varImpl;
	}

	private static String _toParamsString(
			PortletURL portletURL, PageContext pageContext)
		throws Exception {

		StringBundler sb = new StringBundler();

		String url = portletURL.toString();

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (ParamUtil.getBoolean(request, "wsrp")) {
			int x = url.indexOf("/wsrp_rewrite");

			url = url.substring(0, x);
		}

		String queryString = HttpUtil.getQueryString(url);

		StringTokenizer st = new StringTokenizer(
			queryString, StringPool.AMPERSAND);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (Validator.isNotNull(token)) {
				String[] kvp = StringUtil.split(token, StringPool.EQUAL);

				if ((kvp != null) && (kvp.length > 0)) {
					String key = kvp[0];
					String value = StringPool.BLANK;

					if (kvp.length > 1) {
						value = kvp[1];
					}

					value = HttpUtil.decodeURL(value);

					sb.append("<input name=\"");
					sb.append(key);
					sb.append("\" type=\"hidden\" value=\"");
					sb.append(HtmlUtil.escapeAttribute(value));
					sb.append("\" />");
				}
			}
		}

		return sb.toString();
	}

	private String _varImpl;

}