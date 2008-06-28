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

package com.liferay.portal.servlet.taglib.security;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.security.Key;

import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="EncryptTagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EncryptTagUtil {

	public static void doStartTag(
			String className, String style, String protocol,
			Set<String> unencryptedParamsSet, String url, String target,
			PageContext pageContext)
		throws JspException {

		try {
			StringBuilder sb = new StringBuilder();

			// Open anchor

			sb.append("<a ");

			// Class

			if (Validator.isNotNull(className)) {
				sb.append("class=\"");
				sb.append(className);
				sb.append("\" ");
			}

			// HREF

			sb.append("href=\"").append(protocol).append("://");

			int pos = url.indexOf("?");

			if (pos == -1) {
				sb.append(url);
			}
			else {
				sb.append(url.substring(0, pos)).append("?");

				Company company = PortalUtil.getCompany(
					(HttpServletRequest)pageContext.getRequest());

				Key key = company.getKeyObj();

				StringTokenizer st = new StringTokenizer(
					url.substring(pos + 1, url.length()), "&");

				while (st.hasMoreTokens()) {
					String paramAndValue = st.nextToken();

					int x = paramAndValue.indexOf("=");

					String param = paramAndValue.substring(0, x);
					String value = paramAndValue.substring(
						x + 1, paramAndValue.length());

					sb.append(param).append("=");

					if (unencryptedParamsSet.contains(param)) {
						sb.append(HttpUtil.encodeURL(value));
					}
					else {
						try {
							sb.append(HttpUtil.encodeURL(
								Encryptor.encrypt(key, value)));
						}
						catch (EncryptorException ee) {
							_log.error(ee.getMessage());
						}

						if (st.hasMoreTokens()) {
							sb.append("&");
						}
					}
				}

				sb.append("&shuo=1");
			}

			sb.append("\" ");

			// Style

			if (Validator.isNotNull(style)) {
				sb.append("style=\"");
				sb.append(style);
				sb.append("\" ");
			}

			// Target

			if (Validator.isNotNull(target)) {
				sb.append("target=\"" + target + "\"");
			}

			// Close anchor

			sb.append(">");

			pageContext.getOut().print(sb.toString());
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public static void doEndTag(PageContext pageContext) throws JspException {
		try {
			pageContext.getOut().print("</a>");
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	private static Log _log = LogFactory.getLog(EncryptTagUtil.class);

}