/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.language;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.util.CollectionFactory;
import com.liferay.util.CookieUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

/**
 * <a href="LanguageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Andrius Vitkauskas
 *
 */
public class LanguageUtil {

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static String format(
			long companyId, Locale locale, String pattern, Object argument)
		throws LanguageException {

		return format(companyId, locale, pattern, new Object[] {argument});
	}

	public static String format(
			long companyId, Locale locale, String pattern, Object[] arguments)
		throws LanguageException {

		String value = null;

		try {
			pattern = get(companyId, locale, pattern);

			if (arguments != null) {
				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					formattedArguments[i] = get(
						companyId, locale, arguments[i].toString());
				}

				value = MessageFormat.format(pattern, formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			throw new LanguageException(e);
		}

		return value;
	}

	public static String format(
			PageContext pageContext, String pattern, Object argument)
		throws LanguageException {

		return format(pageContext, pattern, new Object[] {argument}, true);
	}

	public static String format(
			PageContext pageContext, String pattern, Object argument,
			boolean translateArguments)
		throws LanguageException {

		return format(
			pageContext, pattern, new Object[] {argument}, translateArguments);
	}

	public static String format(
			PageContext pageContext, String pattern, Object[] arguments)
		throws LanguageException {

		return format(pageContext, pattern, arguments, true);
	}

	public static String format(
			PageContext pageContext, String pattern, Object[] arguments,
			boolean translateArguments)
		throws LanguageException {

		String value = null;

		try {
			pattern = get(pageContext, pattern);

			if (arguments != null) {
				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] =
							get(pageContext, arguments[i].toString());
					}
					else {
						formattedArguments[i] = arguments[i];
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			throw new LanguageException(e);
		}

		return value;
	}

	public static String format(
			PageContext pageContext, String pattern, LanguageWrapper argument)
		throws LanguageException {

		return format(
			pageContext, pattern, new LanguageWrapper[] {argument}, true);
	}

	public static String format(
			PageContext pageContext, String pattern, LanguageWrapper argument,
			boolean translateArguments)
		throws LanguageException {

		return format(
			pageContext, pattern, new LanguageWrapper[] {argument},
			translateArguments);
	}

	public static String format(
			PageContext pageContext, String pattern,
			LanguageWrapper[] arguments)
		throws LanguageException {

		return format(pageContext, pattern, arguments, true);
	}

	public static String format(
			PageContext pageContext, String pattern,
			LanguageWrapper[] arguments, boolean translateArguments)
		throws LanguageException {

		String value = null;

		try {
			pattern = get(pageContext, pattern);

			if (arguments != null) {
				Object[] formattedArguments = new Object[arguments.length];

				for (int i = 0; i < arguments.length; i++) {
					if (translateArguments) {
						formattedArguments[i] =
							arguments[i].getBefore() +
							get(pageContext, arguments[i].getText()) +
							arguments[i].getAfter();
					}
					else {
						formattedArguments[i] =
							arguments[i].getBefore() +
							arguments[i].getText() +
							arguments[i].getAfter();
					}
				}

				value = MessageFormat.format(pattern, formattedArguments);
			}
			else {
				value = pattern;
			}
		}
		catch (Exception e) {
			throw new LanguageException(e);
		}

		return value;
	}

	public static String get(User user, String key) throws LanguageException {
		return get(user, key, key);
	}

	public static String get(User user, String key, String defaultValue)
		throws LanguageException {

		return get(user.getCompanyId(), user.getLocale(), key, defaultValue);
	}

	public static String get(long companyId, Locale locale, String key)
		throws LanguageException {

		return get(companyId, locale, key, key);
	}

	public static String get(
			long companyId, Locale locale, String key, String defaultValue)
		throws LanguageException {

		if (key == null) {
			return null;
		}

		String value = null;

		try {
			MessageResources resources = (MessageResources)WebAppPool.get(
				String.valueOf(companyId), Globals.MESSAGES_KEY);

			value = resources.getMessage(locale, key);
		}
		catch (Exception e) {
			throw new LanguageException(e);
		}

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public static String get(PageContext pageContext, String key)
		throws LanguageException {

		return get(pageContext, key, key);
	}

	public static String get(
			PageContext pageContext, String key, String defaultValue)
		throws LanguageException {

		if (key == null) {
			return null;
		}

		String value = null;

		try {
			value = TagUtils.getInstance().message(
				pageContext, null, null, key);
		}
		catch (Exception e) {
			_log.error(e);

			throw new LanguageException(key, e);
		}

		if (value == null) {

			// LEP-2849

			HttpServletRequest req =
				(HttpServletRequest)pageContext.getRequest();

			PortletConfig portletConfig = (PortletConfig)req.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

			if (portletConfig != null) {
				Locale locale = req.getLocale();

				ResourceBundle bundle = portletConfig.getResourceBundle(locale);

				try {
					value = bundle.getString(key);
				}
				catch (MissingResourceException mre) {
				}
			}
		}

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public static Locale[] getAvailableLocales() {
		return _getInstance()._locales;
	}

	public static String getCharset(Locale locale) {
		return _getInstance()._getCharset(locale);
	}

	public static String getLanguageId(ActionRequest req)
		throws PortalException, SystemException {

		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		return getLanguageId(httpReq);
	}

	public static String getLanguageId(RenderRequest req)
		throws PortalException, SystemException {

		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);

		return getLanguageId(httpReq);
	}

	public static String getLanguageId(HttpServletRequest req) {
		String languageId = ParamUtil.getString(req, "languageId");

		if (Validator.isNotNull(languageId)) {
			return languageId;
		}

		Locale locale =
			(Locale)req.getSession().getAttribute(Globals.LOCALE_KEY);

		if (locale == null) {
			languageId = CookieUtil.get(
				req.getCookies(), CookieKeys.GUEST_LANGUAGE_ID);

			if (Validator.isNotNull(languageId)) {
				locale = LocaleUtil.fromLanguageId(languageId);
			}
		}

		return getLanguageId(locale);
	}

	public static String getLanguageId(Locale locale) {
		return LocaleUtil.toLanguageId(locale);
	}

	public static Locale getLocale(String languageCode) {
		return _getInstance()._getLocale(languageCode);
	}

	public static String getTimeDescription(
			PageContext pageContext, Long milliseconds)
		throws LanguageException {

		return getTimeDescription(pageContext, milliseconds.longValue());
	}

	public static String getTimeDescription(
			PageContext pageContext, long milliseconds)
		throws LanguageException {

		String desc = Time.getDescription(milliseconds);

		String value = null;

		try {
			int pos = desc.indexOf(StringPool.SPACE);

			int x = GetterUtil.getInteger(desc.substring(0, pos));

			value =
				x + " " +
				get(
					pageContext,
					desc.substring(pos + 1, desc.length()).toLowerCase());
		}
		catch (Exception e) {
			throw new LanguageException(e);
		}

		return value;
	}

	public static void updateCookie(HttpServletResponse res, Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		Cookie languageIdCookie = new Cookie(
			CookieKeys.GUEST_LANGUAGE_ID, languageId);

		languageIdCookie.setPath(StringPool.SLASH);
		languageIdCookie.setMaxAge(CookieKeys.MAX_AGE);

		CookieKeys.addCookie(res, languageIdCookie);
	}

	private static LanguageUtil _getInstance() {
		Long companyIdObj = new Long(CompanyThreadLocal.getCompanyId());

		LanguageUtil instance = (LanguageUtil)_instances.get(companyIdObj);

		if (instance == null) {
			instance = new LanguageUtil();

			_instances.put(companyIdObj, instance);
		}

		return instance;
	}

	private LanguageUtil() {
		String[] array = StringUtil.split(
			PropsUtil.get(PropsUtil.LOCALES), StringPool.COMMA);

		_locales = new Locale[array.length];
		_localesByLanguageCode = CollectionFactory.getHashMap();
		_charEncodings = CollectionFactory.getHashMap();

		for (int i = 0; i < array.length; i++) {
			String languageId = array[i];

			int x = languageId.indexOf(StringPool.UNDERLINE);

			String language = array[i].substring(0, x);
			//String country = array[i].substring(x + 1, array[i].length());

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			_locales[i] = locale;
			_localesByLanguageCode.put(language, locale);
			_charEncodings.put(locale.toString(), DEFAULT_ENCODING);
		}
	}

	private String _getCharset(Locale locale) {
		return DEFAULT_ENCODING;
	}

	private Locale _getLocale(String languageCode) {
		return (Locale)_localesByLanguageCode.get(languageCode);
	}

	private static Log _log = LogFactory.getLog(LanguageUtil.class);

	private static Map _instances = CollectionFactory.getSyncHashMap();

	private Locale[] _locales;
	private Map _localesByLanguageCode;
	private Map _charEncodings;

}