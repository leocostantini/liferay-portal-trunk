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

package com.liferay.portal.servlet.filters.i18n;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="I18nFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class I18nFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		I18nFilter.class.getName() + "SKIP_FILTER";

	public static Set<String> getLanguageIds() {
		return _languageIds;
	}

	public static void setLanguageIds(Set<String> languageIds) {
		for (String languageId : languageIds) {
			languageId = languageId.substring(1);

			_languageIds.add(languageId);
		}

		_languageIds = Collections.unmodifiableSet(_languageIds);
	}

	protected String[] getI18nData(HttpServletRequest request) {
		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
			return null;
		}

		String contextPath = PortalUtil.getPathContext();

		String requestURI = request.getRequestURI();

		if ((Validator.isNotNull(contextPath)) &&
			(requestURI.indexOf(contextPath) != -1)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String i18nLanguageId = requestURI.substring(1);

		int pos = requestURI.indexOf(StringPool.SLASH, 1);

		if (pos != -1) {
			i18nLanguageId = i18nLanguageId.substring(0, pos - 1);
		}

		if (_languageIds.contains(i18nLanguageId)) {
			return null;
		}

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		String guestLanguageId = CookieKeys.getCookie(
			request, CookieKeys.GUEST_LANGUAGE_ID);

		if (Validator.isNull(guestLanguageId)) {
			guestLanguageId = defaultLanguageId;
		}

		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) {
			if (!defaultLanguageId.equals(guestLanguageId)) {
				i18nLanguageId = guestLanguageId;
			}
			else {
				return null;
			}
		}
		else if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2) {
			i18nLanguageId = guestLanguageId;
		}

		if (i18nLanguageId == null) {
			return null;
		}

		String i18nPath = StringPool.SLASH + i18nLanguageId;

		Locale locale = LocaleUtil.fromLanguageId(i18nLanguageId);

		if (!LanguageUtil.isDuplicateLanguageCode(locale.getLanguage())) {
			i18nPath = StringPool.SLASH + locale.getLanguage();
		}
		else {
			Locale priorityLocale = LanguageUtil.getLocale(
				locale.getLanguage());

			if (locale.equals(priorityLocale)) {
				i18nPath = StringPool.SLASH + locale.getLanguage();
			}
		}

		String redirect = contextPath + i18nPath + requestURI;

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if ((layoutSet != null) &&
			(requestURI.startsWith(_PRIVATE_GROUP_SERVLET_MAPPING) ||
			 requestURI.startsWith(_PRIVATE_USER_SERVLET_MAPPING) ||
			 requestURI.startsWith(_PUBLIC_GROUP_SERVLET_MAPPING))) {

			int x = requestURI.indexOf(StringPool.SLASH, 1);

			if (x == -1) {

				// /web

				requestURI += StringPool.SLASH;

				x = requestURI.indexOf(StringPool.SLASH, 1);
			}

			int y = requestURI.indexOf(StringPool.SLASH, x + 1);

			if (y == -1) {

				// /web/alpha

				requestURI += StringPool.SLASH;

				y = requestURI.indexOf(StringPool.SLASH, x + 1);
			}

			String groupFriendlyURL = requestURI.substring(x, y);

			Group group = layoutSet.getGroup();

			if (group.getFriendlyURL().equals(groupFriendlyURL)) {
				redirect = contextPath + i18nPath + requestURI.substring(y);
			}
		}

		String queryString = request.getQueryString();

		if (Validator.isNotNull(queryString)) {
			redirect += StringPool.QUESTION + request.getQueryString();
		}

		return new String[] {i18nLanguageId, i18nPath, redirect};
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isForwardedByI18nServlet(HttpServletRequest request) {
		if ((request.getAttribute(WebKeys.I18N_LANGUAGE_ID) != null) ||
			(request.getAttribute(WebKeys.I18N_PATH) != null)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (isAlreadyFiltered(request) || isForwardedByI18nServlet(request)) {
			processFilter(I18nFilter.class, request, response, filterChain);

			return;
		}

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		String[] i18nData = getI18nData(request);

		if (i18nData == null) {
			processFilter(I18nFilter.class, request, response, filterChain);

			return;
		}

		String i18nLanguageId = i18nData[0];
		String i18nPath = i18nData[1];
		String redirect = i18nData[2];

		if (_log.isDebugEnabled()) {
			_log.debug("Language ID " + i18nLanguageId);
			_log.debug("Redirect " + redirect);
		}

		response.sendRedirect(redirect);
	}

	private static Log _log = LogFactoryUtil.getLog(I18nFilter.class);

	private static final String  _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static Set<String> _languageIds = new HashSet<String>();

	private ServletContext _servletContext;

}