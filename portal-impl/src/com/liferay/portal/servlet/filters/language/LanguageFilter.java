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

package com.liferay.portal.servlet.filters.language;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletConfig;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="LanguageFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class LanguageFilter extends BasePortalFilter {

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		ServletContext servletContext = filterConfig.getServletContext();

		PortletApp portletApp = (PortletApp)servletContext.getAttribute(
			PortletServlet.PORTLET_APP);

		if ((portletApp == null) || !portletApp.isWARFile()) {
			return;
		}

		List<Portlet> portlets = portletApp.getPortlets();

		if (portlets.size() <= 0) {
			return;
		}

		_portletConfig = PortletConfigFactory.create(
			portlets.get(0), filterConfig.getServletContext());
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		processFilter(
			LanguageFilter.class, request, stringResponse, filterChain);

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);

			_log.debug("Translating response " + completeURL);
		}

		String content = translateResponse(request, stringResponse);

		ServletResponseUtil.write(response, content);
	}

	protected String translateResponse(
		HttpServletRequest request, StringServletResponse stringResponse) {

		String languageId = LanguageUtil.getLanguageId(request);
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String content = stringResponse.getString();

		StringBundler sb = new StringBundler();

		Matcher matcher = _pattern.matcher(content);

		int x = 0;

		while (matcher.find()) {
			int y = matcher.start(0);

			String key = matcher.group(1);

			sb.append(content.substring(x, y));
			sb.append(StringPool.APOSTROPHE);

			String value = null;

			if (_portletConfig != null) {
				ResourceBundle resourceBundle =
					_portletConfig.getResourceBundle(locale);

				try {
					value = resourceBundle.getString(key);
				}
				catch (MissingResourceException mre) {
				}
			}

			if (Validator.isNull(value)) {
				value = UnicodeLanguageUtil.get(locale, key);
			}

			sb.append(value);
			sb.append(StringPool.APOSTROPHE);

			x = matcher.end(0);
		}

		sb.append(content.substring(x));

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(LanguageFilter.class);

	private static Pattern _pattern = Pattern.compile(
		"Liferay\\.Language\\.get\\([\"']([^)]+)[\"']\\)");

	private PortletConfig _portletConfig;

}