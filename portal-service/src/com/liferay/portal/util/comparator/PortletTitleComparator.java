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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * <a href="PortletTitleComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletTitleComparator
	implements Comparator<Portlet>, Serializable {

	public PortletTitleComparator(long companyId, Locale locale) {
		_companyId = companyId;
		_locale = locale;
	}

	public PortletTitleComparator(
		ServletContext servletContext, Locale locale) {

		_servletContext = servletContext;
		_locale = locale;
	}

	public int compare(Portlet portlet1, Portlet portlet2) {
		String portletTitle1 = StringPool.BLANK;
		String portletTitle2 = StringPool.BLANK;

		if (_servletContext != null) {
			portletTitle1 = PortalUtil.getPortletTitle(
				portlet1, _servletContext, _locale);
			portletTitle2 = PortalUtil.getPortletTitle(
				portlet2, _servletContext, _locale);
		}
		else {
			portletTitle1 = PortalUtil.getPortletTitle(
				portlet1, _companyId, _locale);
			portletTitle2 = PortalUtil.getPortletTitle(
				portlet2, _companyId, _locale);
		}

		return portletTitle1.compareTo(portletTitle2);
	}

	private long _companyId;
	private ServletContext _servletContext;
	private Locale _locale;

}