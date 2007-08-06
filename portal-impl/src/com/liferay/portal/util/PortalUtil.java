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

package com.liferay.portal.util;

import com.germinus.easyconf.Filter;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.service.ClassNameServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.UserPermission;
import com.liferay.portal.servlet.PortletContextPool;
import com.liferay.portal.servlet.PortletContextWrapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.CachePortlet;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.UserAttributes;
import com.liferay.portlet.wsrp.URLGeneratorImpl;
import com.liferay.util.ArrayUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.Encryptor;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.HttpUtil;
import com.liferay.util.JS;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.portlet.PortletRequestWrapper;
import com.liferay.util.portlet.PortletResponseWrapper;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.StringServletResponse;
import com.liferay.util.servlet.UploadPortletRequest;
import com.liferay.util.servlet.UploadServletRequest;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

import org.hibernate.util.FastHashMap;

/**
 * <a href="PortalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Jorge Ferrer
 *
 */
public class PortalUtil {

	public static final boolean DEFAULT_P_L_RESET = GetterUtil.getBoolean(
		PropsUtil.get(PropsUtil.LAYOUT_DEFAULT_P_L_RESET));

	public static final String PATH_IMAGE = "/image";

	public static final String PATH_MAIN = "/c";

	public static final String PATH_PORTAL_LAYOUT = "/portal/layout";

	public static final String PORTLET_XML_FILE_NAME_STANDARD = "portlet.xml";

	public static final String PORTLET_XML_FILE_NAME_CUSTOM =
		"portlet-custom.xml";

	public static final Date UP_TIME = new Date();

	public static void clearRequestParameters(RenderRequest req) {

		// Clear the render parameters if they were set during processAction

		boolean action = ParamUtil.getBoolean(req, "p_p_action");

		if (action) {
			((RenderRequestImpl)req).getRenderParameters().clear();
		}
	}

	public static void copyRequestParameters(
		ActionRequest req, ActionResponse res) {

		try {
			ActionResponseImpl resImpl = (ActionResponseImpl)res;

			Map renderParameters = resImpl.getRenderParameters();

			res.setRenderParameter("p_p_action", "1");

			Enumeration enu = req.getParameterNames();

			while (enu.hasMoreElements()) {
				String param = (String)enu.nextElement();
				String[] values = req.getParameterValues(param);

				if (renderParameters.get(
						resImpl.getNamespace() + param) == null) {

					res.setRenderParameter(param, values);
				}
			}
		}
		catch (IllegalStateException ise) {

			// This should only happen if the developer called
			// sendRedirect of javax.portlet.ActionResponse

		}
	}

	public static String createSecureProxyURL(String url, boolean secure) {

		// Use this method to fetch external content that may not be available
		// in secure mode. See how the Weather portlet fetches images.

		if (!secure) {
			return url;
		}
		else {
			Map params = CollectionFactory.getHashMap();

			params.put(org.apache.wsrp4j.util.Constants.URL, url);

			return URLGeneratorImpl.getResourceProxyURL(params);
		}
	}

	public static String getClassName(long classNameId) {
		try {
			ClassName className = ClassNameServiceUtil.getClassName(
				classNameId);

			return className.getValue();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from id " + classNameId);
		}
	}

	public static long getClassNameId(Class classObj) {
		return getClassNameId(classObj.getName());
	}

	public static long getClassNameId(String value) {
		try {
			ClassName className = ClassNameServiceUtil.getClassName(value);

			return className.getClassNameId();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from value " + value);
		}
	}

	public static Company getCompany(HttpServletRequest req)
		throws PortalException, SystemException {

		long companyId = getCompanyId(req);

		if (companyId <= 0) {
			return null;
		}

		Company company = (Company)req.getAttribute(WebKeys.COMPANY);

		if (company == null) {
			company = CompanyLocalServiceUtil.getCompanyById(companyId);

			req.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	public static Company getCompany(ActionRequest req)
		throws PortalException, SystemException {

		return getCompany(getHttpServletRequest(req));
	}

	public static Company getCompany(RenderRequest req)
		throws PortalException, SystemException {

		return getCompany(getHttpServletRequest(req));
	}

	public static long getCompanyId(HttpServletRequest req) {
		Long companyIdObj = (Long)req.getAttribute(WebKeys.COMPANY_ID);

		if (companyIdObj != null) {
			return companyIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	public static long getCompanyId(ActionRequest req) {
		return getCompanyId(getHttpServletRequest(req));
	}

	public static long getCompanyId(PortletRequest req) {
		long companyId = 0;

		if (req instanceof ActionRequest) {
			companyId = getCompanyId((ActionRequest)req);
		}
		else {
			companyId = getCompanyId((RenderRequest)req);
		}

		return companyId;
	}

	public static long getCompanyId(RenderRequest req) {
		return getCompanyId(getHttpServletRequest(req));
	}

	public static long getCompanyIdByWebId(ServletContext ctx) {
		String webId = GetterUtil.getString(
			ctx.getInitParameter("company_web_id"));

		return getCompanyIdByWebId(webId);
	}

	public static long getCompanyIdByWebId(String webId) {
		long companyId = 0;

		try {
			Company company = CompanyLocalServiceUtil.getCompanyByWebId(webId);

			companyId = company.getCompanyId();
		}
		catch (Exception e) {
			_log.error(e.getMessage());
		}

		return companyId;
	}

	public static String getCurrentURL(HttpServletRequest req) {
		String currentURL = (String)req.getAttribute(WebKeys.CURRENT_URL);

		if (currentURL == null) {
			currentURL = ParamUtil.getString(req, "currentURL");

			if (Validator.isNull(currentURL)) {
				currentURL = Http.getCompleteURL(req);

				if ((Validator.isNotNull(currentURL)) &&
					(currentURL.indexOf("j_security_check") == -1)) {

					currentURL = currentURL.substring(
						currentURL.indexOf("://") + 3, currentURL.length());

					currentURL = currentURL.substring(
						currentURL.indexOf("/"), currentURL.length());
				}
			}

			if (Validator.isNull(currentURL)) {
				currentURL = PortalUtil.getPathMain();
			}

			req.setAttribute(WebKeys.CURRENT_URL, currentURL);
		}

		return currentURL;
	}

	public static Date getDate(
			int month, int day, int year, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, null, pe);
	}

	public static Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, -1, -1, timeZone, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, hour, min, null, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		if (!Validator.isGregorianDate(month, day, year)) {
			throw pe;
		}
		else {
			Calendar cal = null;

			if (timeZone == null) {
				cal = CalendarFactoryUtil.getCalendar();
			}
			else {
				cal = CalendarFactoryUtil.getCalendar(timeZone);
			}

			if ((hour == -1) || (min == -1)) {
				cal.set(year, month, day);
			}
			else {
				cal.set(year, month, day, hour, min, 0);
			}

			Date date = cal.getTime();

			/*if (timeZone != null &&
				cal.before(CalendarFactoryUtil.getCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	public static String getHost(HttpServletRequest req) {
		String host = req.getHeader("Host");

		if (host != null) {
			host = host.trim().toLowerCase();

			int pos = host.indexOf(':');

			if (pos >= 0) {
				host = host.substring(0, pos);
			}
		}
		else {
			host = null;
		}

		return host;
	}

	public static String getHost(ActionRequest req) {
		return getHost(getHttpServletRequest(req));
	}

	public static String getHost(RenderRequest req) {
		return getHost(getHttpServletRequest(req));
	}

	public static HttpServletRequest getHttpServletRequest(PortletRequest req) {
		if (req instanceof ActionRequestImpl) {
			ActionRequestImpl reqImpl = (ActionRequestImpl)req;

			return reqImpl.getHttpServletRequest();
		}
		else if (req instanceof RenderRequestImpl) {
			RenderRequestImpl reqImpl = (RenderRequestImpl)req;

			return reqImpl.getHttpServletRequest();
		}
		else if (req instanceof PortletRequestWrapper) {
			PortletRequestWrapper reqWrapper = (PortletRequestWrapper)req;

			return getHttpServletRequest(reqWrapper.getPortletRequest());
		}
		else {
			throw new RuntimeException(
				"Unable to get the HTTP servlet request from " +
					req.getClass().getName());
		}
	}

	public static HttpServletResponse getHttpServletResponse(
		PortletResponse res) {

		if (res instanceof ActionResponseImpl) {
			ActionResponseImpl resImpl = (ActionResponseImpl)res;

			return resImpl.getHttpServletResponse();
		}
		else if (res instanceof RenderResponseImpl) {
			RenderResponseImpl resImpl = (RenderResponseImpl)res;

			return resImpl.getHttpServletResponse();
		}
		else if (res instanceof PortletResponseWrapper) {
			PortletResponseWrapper resWrapper = (PortletResponseWrapper)res;

			return getHttpServletResponse(resWrapper.getPortletResponse());
		}
		else {
			throw new RuntimeException(
				"Unable to get the HTTP servlet resuest from " +
					res.getClass().getName());
		}
	}

	public static String getLayoutEditPage(Layout layout) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_EDIT_PAGE, Filter.by(layout.getType()));
	}

	public static String getLayoutViewPage(Layout layout) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_VIEW_PAGE, Filter.by(layout.getType()));
	}

	public static String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutURL(layout, themeDisplay, true);
	}

	public static String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		if (layout == null) {
			return themeDisplay.getPathMain() + PATH_PORTAL_LAYOUT;
		}

		if (!layout.getType().equals(LayoutImpl.TYPE_URL)) {
			String layoutFriendlyURL = getLayoutFriendlyURL(
				layout, themeDisplay);

			if (Validator.isNotNull(layoutFriendlyURL)) {
				if (doAsUser &&
					Validator.isNotNull(themeDisplay.getDoAsUserId())) {

					layoutFriendlyURL = Http.addParameter(
						layoutFriendlyURL, "doAsUserId",
						themeDisplay.getDoAsUserId());
				}

				return layoutFriendlyURL;
			}
		}

		String layoutURL = getLayoutActualURL(layout);

		if (doAsUser && Validator.isNotNull(themeDisplay.getDoAsUserId())) {
			layoutURL = Http.addParameter(
				layoutURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		return layoutURL;
	}

	public static String getLayoutActualURL(Layout layout)
		throws PortalException, SystemException {

		return getLayoutActualURL(layout, getPathMain());
	}

	public static String getLayoutActualURL(Layout layout, String mainPath)
		throws PortalException, SystemException {

		Map vars = new FastHashMap();

		vars.put("liferay:mainPath", mainPath);
		vars.put("liferay:plid", String.valueOf(layout.getPlid()));
		vars.putAll(layout.getLayoutType().getTypeSettingsProperties());

		String href = PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_URL,
			Filter.by(layout.getType()).setVariables(vars));

		return href;
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, null);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map params)
		throws PortalException, SystemException {

		Layout layout = null;
		String queryString = StringPool.BLANK;

		if (Validator.isNull(friendlyURL)) {
			List layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() > 0) {
				layout = (Layout)layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					"{groupId=" + groupId + ",privateLayout=" + privateLayout +
						"} does not have any layouts");
			}
		}
		else {
			Object[] friendlyURLMapper = getPortletFriendlyURLMapper(
				groupId, privateLayout, friendlyURL, params);

			layout = (Layout)friendlyURLMapper[0];
			queryString = (String)friendlyURLMapper[1];
		}

		String layoutActualURL = getLayoutActualURL(layout, mainPath);

		if (Validator.isNotNull(queryString)) {
			layoutActualURL = layoutActualURL + queryString;
		}

		return layoutActualURL;
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (!isLayoutFriendliable(layout)) {
			return null;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		if (Validator.isNull(layoutFriendlyURL)) {
			layoutFriendlyURL = layout.getDefaultFriendlyURL();
		}

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(layoutSet.getVirtualHost())) {
			String portalURL = PortalUtil.getPortalURL(
				layoutSet.getVirtualHost(), themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			long curLayoutSetId =
				themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

			if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
					(portalURL.startsWith(themeDisplay.getURLPortal()))) {

				return portalURL + getPathContext() + layoutFriendlyURL;
			}
		}

		Group group = layout.getGroup();

		String parentFriendlyURL = group.getFriendlyURL();

		if (Validator.isNull(parentFriendlyURL)) {
			parentFriendlyURL = group.getDefaultFriendlyURL(
				layout.isPrivateLayout());
		}

		String friendlyURL = null;

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = getPathFriendlyURLPrivateUser();
			}
			else {
				friendlyURL = getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			friendlyURL = getPathFriendlyURLPublic();
		}

		return friendlyURL + parentFriendlyURL + layoutFriendlyURL;
	}

	public static String getLayoutTarget(Layout layout) {
		Properties typeSettingsProps = layout.getTypeSettingsProperties();

		String target = typeSettingsProps.getProperty("target");

		if (Validator.isNull(target)) {
			target = StringPool.BLANK;
		}
		else {
			target = "target=\"" + target + "\"";
		}

		return target;
	}

	public static String getJsSafePortletName(String portletName) {
		return JS.getSafeName(portletName);
	}

	public static Locale getLocale(HttpServletRequest req) {
		return (Locale)req.getSession().getAttribute(Globals.LOCALE_KEY);
	}

	public static Locale getLocale(RenderRequest req) {
		return getLocale(getHttpServletRequest(req));
	}

	public static HttpServletRequest getOriginalServletRequest(
		HttpServletRequest req) {

		HttpServletRequest originalReq = req;

		while (originalReq.getClass().getName().startsWith("com.liferay.")) {

			// Get original request so that portlets inside portlets render
			// properly

			originalReq = (HttpServletRequest)
				((HttpServletRequestWrapper)originalReq).getRequest();
		}

		return originalReq;
	}

	public static String getPathContext() {
		return _instance._getPathContext();
	}

	public static String getPathFriendlyURLPrivateGroup() {
		return _instance._getPathFriendlyURLPrivateGroup();
	}

	public static String getPathFriendlyURLPrivateUser() {
		return _instance._getPathFriendlyURLPrivateUser();
	}

	public static String getPathFriendlyURLPublic() {
		return _instance._getPathFriendlyURLPublic();
	}

	public static String getPathImage() {
		return _instance._getPathImage();
	}

	public static String getPathMain() {
		return _instance._getPathMain();
	}

	public static String getPortalURL(HttpServletRequest req) {
		return getPortalURL(req, req.isSecure());
	}

	public static String getPortalURL(HttpServletRequest req, boolean secure) {
		return getPortalURL(req.getServerName(), req.getServerPort(), secure);
	}

	public static String getPortalURL(PortletRequest req) {
		return getPortalURL(req, req.isSecure());
	}

	public static String getPortalURL(PortletRequest req, boolean secure) {
		return getPortalURL(req.getServerName(), req.getServerPort(), secure);
	}

	public static String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringMaker sm = new StringMaker();

		String serverProtocol = GetterUtil.getString(
			PropsUtil.get(PropsUtil.WEB_SERVER_PROTOCOL));

		if (secure || Http.HTTPS.equals(serverProtocol)) {
			sm.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sm.append(Http.HTTP_WITH_SLASH);
		}

		String serverHost = PropsUtil.get(PropsUtil.WEB_SERVER_HOST);

		if (Validator.isNull(serverHost)) {
			sm.append(serverName);
		}
		else {
			sm.append(serverHost);
		}

		int serverHttpPort = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.WEB_SERVER_HTTP_PORT), -1);

		if (serverHttpPort == -1) {
			if (!secure && (serverPort != Http.HTTP_PORT)) {
				sm.append(StringPool.COLON);
				sm.append(serverPort);
			}
		}
		else {
			if (!secure && (serverPort != serverHttpPort)) {
				if (serverHttpPort != Http.HTTP_PORT) {
					sm.append(StringPool.COLON);
					sm.append(serverHttpPort);
				}
			}
		}

		int serverHttpsPort = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.WEB_SERVER_HTTPS_PORT), -1);

		if (serverHttpsPort == -1) {
			if (secure && (serverPort != Http.HTTPS_PORT)) {
				sm.append(StringPool.COLON);
				sm.append(serverPort);
			}
		}
		else {
			if (secure && (serverPort != serverHttpsPort)) {
				if (serverHttpsPort != Http.HTTPS_PORT) {
					sm.append(StringPool.COLON);
					sm.append(serverHttpsPort);
				}
			}
		}

		return sm.toString();
	}

	public static Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException {

		return getPortletFriendlyURLMapper(groupId, privateLayout, url, null);
	}

	public static Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url, Map params)
		throws PortalException, SystemException {

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		Map friendlyURLMappers =
			PortletLocalServiceUtil.getFriendlyURLMappers();

		Iterator itr = friendlyURLMappers.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String className = (String)entry.getValue();

			FriendlyURLMapper friendlyURLMapper =
				(FriendlyURLMapper)InstancePool.get(className);

			if (url.endsWith(
					StringPool.SLASH + friendlyURLMapper.getMapping())) {

				url += StringPool.SLASH;
			}

			int pos = url.indexOf(
				StringPool.SLASH + friendlyURLMapper.getMapping() +
					StringPool.SLASH);

			if (pos != -1) {
				friendlyURL = url.substring(0, pos);

				Map actualParams = null;

				if (params != null) {
					actualParams = new HashMap(params);
				}
				else {
					actualParams = new HashMap();
				}

				Object action = actualParams.get("p_p_action");

				if ((action == null) || (((String[])action).length == 0)) {
					actualParams.put("p_p_action", "0");
				}

				Object state = actualParams.get("p_p_state");

				if ((state == null) || (((String[])state).length == 0)) {
					actualParams.put(
						"p_p_state", WindowState.MAXIMIZED.toString());
				}

				friendlyURLMapper.populateParams(
					url.substring(pos), actualParams);

				queryString =
					StringPool.AMPERSAND +
						HttpUtil.parameterMapToString(actualParams, false);

				break;
			}
		}

		friendlyURL = StringUtil.replace(friendlyURL, "//", StringPool.SLASH);

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, privateLayout, friendlyURL);
		}
		catch(NoSuchLayoutException nsle) {
			long layoutId = GetterUtil.getLong(friendlyURL.substring(1));

			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);
		}

		return new Object[] {layout, queryString};
	}

	public static long getPortletGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
		}

		return getPortletGroupId(layout);
	}

	public static long getPortletGroupId(Layout layout) {
		if (layout == null) {
			return 0;
		}
		else {
			return layout.getGroupId();
		}
	}

	public static long getPortletGroupId(HttpServletRequest req) {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		return getPortletGroupId(layout);
	}

	public static long getPortletGroupId(ActionRequest req) {
		return getPortletGroupId(getHttpServletRequest(req));
	}

	public static long getPortletGroupId(RenderRequest req) {
		return getPortletGroupId(getHttpServletRequest(req));
	}

	public static String getPortletNamespace(String portletName) {
		return StringPool.UNDERLINE + portletName + StringPool.UNDERLINE;
	}

	public static String getPortletXmlFileName()
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(
				PropsUtil.AUTO_DEPLOY_CUSTOM_PORTLET_XML)) {

			return PORTLET_XML_FILE_NAME_CUSTOM;
		}
		else {
			return PORTLET_XML_FILE_NAME_STANDARD;
		}
	}

	public static String getPortletTitle(String portletId, User user) {
		StringMaker sm = new StringMaker();

		sm.append(JavaConstants.JAVAX_PORTLET_TITLE);
		sm.append(StringPool.PERIOD);
		sm.append(portletId);

		return LanguageUtil.get(
			user.getCompanyId(), user.getLocale(), sm.toString());
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext ctx, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactory.create(portlet, ctx);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(JavaConstants.JAVAX_PORTLET_TITLE);
	}

	public static PortletPreferences getPreferences(HttpServletRequest req) {
		RenderRequest renderRequest = (RenderRequest)req.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletPreferences prefs = null;

		if (renderRequest != null) {
			PortletPreferencesWrapper prefsWrapper =
				(PortletPreferencesWrapper)renderRequest.getPreferences();

			prefs = prefsWrapper.getPreferencesImpl();
		}

		return prefs;
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		if (portlet.isWARFile()) {
			PortletContextWrapper pcw =
				PortletContextPool.get(portlet.getRootPortletId());

			return pcw.getPreferencesValidator();
		}
		else {
			PreferencesValidator prefsValidator = null;

			if (Validator.isNotNull(portlet.getPreferencesValidator())) {
				prefsValidator =
					(PreferencesValidator)InstancePool.get(
						portlet.getPreferencesValidator());
			}

			return prefsValidator;
		}
	}

	public static User getSelectedUser(HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(req, true);
	}

	public static User getSelectedUser(
			HttpServletRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		long userId = ParamUtil.getLong(req, "p_u_i_d");

		User user = null;

		try {
			if (checkPermission) {
				user = UserServiceUtil.getUserById(userId);
			}
			else {
				user = UserLocalServiceUtil.getUserById(userId);
			}
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	public static User getSelectedUser(ActionRequest req)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(req, true);
	}

	public static User getSelectedUser(
			ActionRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(getHttpServletRequest(req), checkPermission);
	}

	public static User getSelectedUser(RenderRequest req)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(req, true);
	}

	public static User getSelectedUser(
			RenderRequest req, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getSelectedUser(getHttpServletRequest(req), checkPermission);
	}

	public static String[] getSystemCommunityRoles() {
		return _instance._getSystemCommunityRoles();
	}

	public static String[] getSystemGroups() {
		return _instance._getSystemGroups();
	}

	public static String[] getSystemRoles() {
		return _instance._getSystemRoles();
	}

	public static UploadPortletRequest getUploadPortletRequest(
		ActionRequest req) {

		ActionRequestImpl actionReq = (ActionRequestImpl)req;

		DynamicServletRequest dynamicReq =
			(DynamicServletRequest)actionReq.getHttpServletRequest();

		HttpServletRequestWrapper reqWrapper =
			(HttpServletRequestWrapper)dynamicReq.getRequest();

		UploadServletRequest uploadReq = getUploadServletRequest(reqWrapper);

		return new UploadPortletRequest(
			uploadReq, getPortletNamespace(actionReq.getPortletName()));
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest httpReq) {

		HttpServletRequestWrapper httpReqWrapper = null;

		if (httpReq instanceof HttpServletRequestWrapper) {
			httpReqWrapper = (HttpServletRequestWrapper)httpReq;
		}

		UploadServletRequest uploadReq = null;

		while (uploadReq == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (httpReqWrapper instanceof UploadServletRequest) {
				uploadReq = (UploadServletRequest)httpReqWrapper;
			}
			else {
				ServletRequest req = httpReqWrapper.getRequest();

				if (!(req instanceof HttpServletRequestWrapper)) {
					break;
				}
				else {
					httpReqWrapper =
						(HttpServletRequestWrapper)httpReqWrapper.getRequest();
				}
			}
		}

		return uploadReq;
	}

	public static Date getUptime() {
		return UP_TIME;
	}

	public static User getUser(HttpServletRequest req)
		throws PortalException, SystemException {

		long userId = getUserId(req);

		if (userId <= 0) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session
			// and may not be accessible by the portlet WAR's session. This
			// behavior is inconsistent across different application servers.

			String remoteUser = req.getRemoteUser();

			if (remoteUser == null) {
				return null;
			}

			userId = GetterUtil.getLong(remoteUser);
		}

		User user = (User)req.getAttribute(WebKeys.USER);

		if (user == null) {
			user = UserLocalServiceUtil.getUserById(userId);

			req.setAttribute(WebKeys.USER, user);
		}

		return user;
	}

	public static User getUser(ActionRequest req)
		throws PortalException, SystemException {

		return getUser(getHttpServletRequest(req));
	}

	public static User getUser(RenderRequest req)
		throws PortalException, SystemException {

		return getUser(getHttpServletRequest(req));
	}

	public static long getUserId(HttpServletRequest req) {
		Long userIdObj = (Long)req.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		if (!GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE)) &&
			GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PORTAL_IMPERSONATION_ENABLE))) {

			String doAsUserIdString = ParamUtil.getString(req, "doAsUserId");

			try {
				long doAsUserId = _getDoAsUserId(req, doAsUserIdString);

				if (doAsUserId > 0) {
					if (_log.isDebugEnabled()) {
						_log.debug("Impersonating user " + doAsUserId);
					}

					return doAsUserId;
				}
			}
			catch (Exception e) {
				_log.error("Unable to impersonate user " + doAsUserIdString, e);
			}
		}

		HttpSession ses = req.getSession();

		userIdObj = (Long)ses.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			req.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	public static long getUserId(ActionRequest req) {
		return getUserId(getHttpServletRequest(req));
	}

	public static long getUserId(RenderRequest req) {
		return getUserId(getHttpServletRequest(req));
	}

	public static String getUserName(long userId, String defaultUserName) {
		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_NICKNAME);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getUserName(userId, defaultUserName, userAttribute, null);
	}

	public static String getUserName(
		long userId, String defaultUserName, HttpServletRequest req) {

		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_NICKNAME, req);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest req) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			if (userAttribute.equals(UserAttributes.USER_NAME_FULL)) {
				userName = user.getFullName();
			}
			else {
				userName = user.getScreenName();
			}

			if (req != null) {
				Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

				PortletURL portletURL = new PortletURLImpl(
					req, PortletKeys.DIRECTORY, layout.getPlid(), false);

				portletURL.setWindowState(WindowState.MAXIMIZED);
				portletURL.setPortletMode(PortletMode.VIEW);

				portletURL.setParameter("struts_action", "/directory/edit_user");
				portletURL.setParameter(
					"p_u_i_d", String.valueOf(user.getUserId()));

				userName =
					"<a href=\"" + portletURL.toString() + "\">" + userName +
						"</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	public static String getUserPassword(HttpSession ses) {
		return (String)ses.getAttribute(WebKeys.USER_PASSWORD);
	}

	public static String getUserPassword(HttpServletRequest req) {
		return getUserPassword(req.getSession());
	}

	public static String getUserPassword(ActionRequest req) {
		return getUserPassword(getHttpServletRequest(req));
	}

	public static String getUserPassword(RenderRequest req) {
		return getUserPassword(getHttpServletRequest(req));
	}

	public static boolean isLayoutFriendliable(Layout layout) {
		return PropsUtil.getComponentProperties().getBoolean(
			PropsUtil.LAYOUT_URL_FRIENDLIABLE,
			Filter.by(layout.getType()), true);
	}

	public static boolean isLayoutParentable(Layout layout) {
		return isLayoutParentable(layout.getType());
	}

	public static boolean isLayoutParentable(String type) {
		return PropsUtil.getComponentProperties().getBoolean(
			PropsUtil.LAYOUT_PARENTABLE, Filter.by(type), true);
	}

	public static boolean isLayoutSitemapable(Layout layout) {
		if (layout.isPrivateLayout()) {
			return false;
		}

		return PropsUtil.getComponentProperties().getBoolean(
			PropsUtil.LAYOUT_SITEMAPABLE, Filter.by(layout.getType()), true);
	}

	public static boolean isReservedParameter(String name) {
		return _instance._reservedParams.contains(name);
	}

	public static boolean isSystemGroup(String groupName) {
		return _instance._isSystemGroup(groupName);
	}

	public static boolean isSystemRole(String roleName) {
		return _instance._isSystemRole(roleName);
	}

	public static boolean isUpdateAvailable() {
		return PluginPackageUtil.isUpdateAvailable();
	}

	public static void renderPage(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, String path)
		throws IOException, ServletException {

		RequestDispatcher rd = ctx.getRequestDispatcher(path);

		StringServletResponse stringServletRes =
			new StringServletResponse(res);

		rd.include(req, stringServletRes);

		sm.append(stringServletRes.getString());
	}

	public static void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString)
		throws IOException, ServletException {

		renderPortlet(
			sm, ctx, req, res, portlet, queryString, null, null, null);
	}

	public static void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		renderPortlet(
			sm, ctx, req, res, portlet, queryString, columnId, columnPos,
			columnCount, null);
	}

	public static void renderPortlet(
			StringMaker sm, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path)
		throws IOException, ServletException {

		queryString = GetterUtil.getString(queryString);
		columnId = GetterUtil.getString(columnId);

		if (columnPos == null) {
			columnPos = new Integer(0);
		}

		if (columnCount == null) {
			columnCount = new Integer(0);
		}

		req.setAttribute(WebKeys.RENDER_PORTLET, portlet);
		req.setAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING, queryString);
		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);

		if (path == null) {
			path = "/html/portal/render_portlet.jsp";
		}

		RequestDispatcher rd = ctx.getRequestDispatcher(path);

		if (sm != null) {
			StringServletResponse stringServletRes =
				new StringServletResponse(res);

			rd.include(req, stringServletRes);

			sm.append(stringServletRes.getString());
		}
		else {

			// LEP-766

			res.setContentType(Constants.TEXT_HTML + "; charset=UTF-8");

			rd.include(req, res);
		}
	}

	/**
	 * Sets the subtitle for a page. This is just a hint and can be overridden
	 * by subsequent calls. The last call to this method wins.
	 *
	 * @param		subtitle the subtitle for a page
	 * @param		req the HTTP servlet request
	 */
	public static void setPageSubtitle(
		String subtitle, HttpServletRequest req) {

		req.setAttribute(WebKeys.PAGE_SUBTITLE, subtitle);
	}

	/**
	 * Sets the whole title for a page. This is just a hint and can be
	 * overridden by subsequent calls. The last call to this method wins.
	 *
	 * @param		title the whole title for a page
	 * @param		req the HTTP servlet request
	 */
	public static void setPageTitle(String title, HttpServletRequest req) {
		req.setAttribute(WebKeys.PAGE_TITLE, title);
	}

	public static void storePreferences(PortletPreferences prefs)
		throws IOException, ValidatorException {

		PortletPreferencesWrapper prefsWrapper =
			(PortletPreferencesWrapper)prefs;

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)prefsWrapper.getPreferencesImpl();

		prefsImpl.store();
	}

	public static PortletMode updatePortletMode(
			String portletId, User user, Layout layout, PortletMode portletMode,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if (portletMode == null || Validator.isNull(portletMode.toString())) {
			if (layoutType.hasModeAboutPortletId(portletId)) {
				return LiferayPortletMode.ABOUT;
			}
			else if (layoutType.hasModeConfigPortletId(portletId)) {
				return LiferayPortletMode.CONFIG;
			}
			else if (layoutType.hasModeEditPortletId(portletId)) {
				return PortletMode.EDIT;
			}
			else if (layoutType.hasModeEditDefaultsPortletId(portletId)) {
				return LiferayPortletMode.EDIT_DEFAULTS;
			}
			else if (layoutType.hasModeEditGuestPortletId(portletId)) {
				return LiferayPortletMode.EDIT_GUEST;
			}
			else if (layoutType.hasModeHelpPortletId(portletId)) {
				return PortletMode.HELP;
			}
			else if (layoutType.hasModePreviewPortletId(portletId)) {
				return LiferayPortletMode.PREVIEW;
			}
			else if (layoutType.hasModePrintPortletId(portletId)) {
				return LiferayPortletMode.PRINT;
			}
			else {
				return PortletMode.VIEW;
			}
		}
		else {
			boolean updateLayout = false;

			if (portletMode.equals(LiferayPortletMode.ABOUT) &&
				!layoutType.hasModeAboutPortletId(portletId)) {

				layoutType.addModeAboutPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.CONFIG) &&
					 !layoutType.hasModeConfigPortletId(portletId)) {

				layoutType.addModeConfigPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.EDIT) &&
					 !layoutType.hasModeEditPortletId(portletId)) {

				layoutType.addModeEditPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS) &&
					 !layoutType.hasModeEditDefaultsPortletId(portletId)) {

				layoutType.addModeEditDefaultsPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST) &&
					 !layoutType.hasModeEditGuestPortletId(portletId)) {

				layoutType.addModeEditGuestPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.HELP) &&
					 !layoutType.hasModeHelpPortletId(portletId)) {

				layoutType.addModeHelpPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PREVIEW) &&
					 !layoutType.hasModePreviewPortletId(portletId)) {

				layoutType.addModePreviewPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PRINT) &&
					 !layoutType.hasModePrintPortletId(portletId)) {

				layoutType.addModePrintPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.VIEW) &&
					 !layoutType.hasModeViewPortletId(portletId)) {

				layoutType.removeModesPortletId(portletId);

				updateLayout = true;
			}

			if (updateLayout) {
				if ((user != null) && !layout.isShared()) {
					LayoutServiceUtil.updateLayout(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getLayoutId(), layout.getTypeSettings());
				}
				else {
					LayoutClone layoutClone = LayoutCloneFactory.getInstance();

					if (layoutClone != null) {
						layoutClone.update(
							req, layout.getPlid(), layout.getTypeSettings());
					}
				}
			}

			return portletMode;
		}
	}

	public static WindowState updateWindowState(
			String portletId, User user, Layout layout, WindowState windowState,
			HttpServletRequest req)
		throws PortalException, RemoteException, SystemException {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((windowState == null) ||
			(Validator.isNull(windowState.toString()))) {

			if (layoutType.hasStateMaxPortletId(portletId)) {
				return WindowState.MAXIMIZED;
			}
			else if (layoutType.hasStateMinPortletId(portletId)) {
				return WindowState.MINIMIZED;
			}
			else {
				return WindowState.NORMAL;
			}
		}
		else {
			boolean updateLayout = false;

			if ((windowState.equals(WindowState.MAXIMIZED)) ||
				(windowState.equals(LiferayWindowState.EXCLUSIVE)) ||
				(windowState.equals(LiferayWindowState.POP_UP))) {

				if (layoutType.hasStateMax()) {
					String curMaxPortletId =
						StringUtil.split(layoutType.getStateMax())[0];

					// Clear cache and render parameters for the previous
					// portlet that had a maximum window state

					CachePortlet.clearResponse(
						req.getSession(), layout.getPlid(), curMaxPortletId);

					/*RenderParametersPool.clear(
						req, layout.getPlid(), curMaxPortletId);*/

					if ((windowState.equals(LiferayWindowState.EXCLUSIVE)) ||
						(windowState.equals(LiferayWindowState.POP_UP))) {

						String stateMaxPrevious =
							layoutType.getStateMaxPrevious();

						if (stateMaxPrevious == null) {
							layoutType.setStateMaxPrevious(curMaxPortletId);

							updateLayout = true;
						}
					}
				}
				else {
					if ((windowState.equals(LiferayWindowState.EXCLUSIVE)) ||
						(windowState.equals(LiferayWindowState.POP_UP))) {

						String stateMaxPrevious =
							layoutType.getStateMaxPrevious();

						if (stateMaxPrevious == null) {
							layoutType.setStateMaxPrevious(StringPool.BLANK);

							updateLayout = true;
						}
					}
				}

				if (!layoutType.hasStateMaxPortletId(portletId)) {
					layoutType.addStateMaxPortletId(portletId);

					updateLayout = true;
				}
			}
			else if (windowState.equals(WindowState.MINIMIZED) &&
					 !layoutType.hasStateMinPortletId(portletId)) {

				layoutType.addStateMinPortletId(portletId);

				updateLayout = true;
			}
			else if (windowState.equals(WindowState.NORMAL) &&
					 !layoutType.hasStateNormalPortletId(portletId)) {

				layoutType.removeStatesPortletId(portletId);

				updateLayout = true;
			}

			if (updateLayout) {
				if ((user != null) && !layout.isShared()) {
					LayoutServiceUtil.updateLayout(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getLayoutId(), layout.getTypeSettings());
				}
				else {
					LayoutClone layoutClone = LayoutCloneFactory.getInstance();

					if (layoutClone != null) {
						layoutClone.update(
							req, layout.getPlid(), layout.getTypeSettings());
					}
				}
			}

			return windowState;
		}
	}

	private static long _getDoAsUserId(
			HttpServletRequest req, String doAsUserIdString)
		throws Exception {

		if (Validator.isNull(doAsUserIdString)) {
			return 0;
		}

		HttpSession ses = req.getSession();

		Long realUserIdObj = (Long)ses.getAttribute(WebKeys.USER_ID);

		if (realUserIdObj == null) {
			return 0;
		}

		Company company = getCompany(req);

		long doAsUserId = GetterUtil.getLong(
			Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));

		User doAsUser = UserLocalServiceUtil.getUserById(doAsUserId);

		long organizationId = doAsUser.getOrganization().getOrganizationId();
		long locationId = doAsUser.getLocation().getOrganizationId();

		User realUser = UserLocalServiceUtil.getUserById(
			realUserIdObj.longValue());
		boolean checkGuest = true;

		PermissionCheckerImpl permissionChecker = null;

		try {
			permissionChecker = PermissionCheckerFactory.create(
				realUser, checkGuest);

			if (doAsUser.isDefaultUser() ||
				UserPermission.contains(
					permissionChecker, doAsUserId, organizationId, locationId,
					ActionKeys.IMPERSONATE)) {

				req.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

				return doAsUserId;
			}
			else {
				_log.error(
					"User " + realUserIdObj + " does not have the permission " +
						"to impersonate " + doAsUserId);

				return 0;
			}
		}
		finally {
			try {
				PermissionCheckerFactory.recycle(permissionChecker);
			}
			catch (Exception e) {
			}
		}
	}

	private PortalUtil() {

		// Paths

		_pathContext = PropsUtil.get(PropsUtil.PORTAL_CTX);

		if (_pathContext.equals(StringPool.SLASH)) {
			_pathContext = StringPool.BLANK;
		}

		_pathFriendlyURLPrivateGroup = _pathContext + PropsUtil.get(
			PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);
		_pathFriendlyURLPrivateUser = _pathContext + PropsUtil.get(
			PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);
		_pathFriendlyURLPublic = _pathContext + PropsUtil.get(
			PropsUtil.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
		_pathImage = _pathContext + PATH_IMAGE;
		_pathMain = _pathContext + PATH_MAIN;

		// Groups

		String customSystemGroups[] =
			PropsUtil.getArray(PropsUtil.SYSTEM_GROUPS);

		if ((customSystemGroups == null) || (customSystemGroups.length == 0)) {
			_allSystemGroups = GroupImpl.SYSTEM_GROUPS;
		}
		else {
			_allSystemGroups = ArrayUtil.append(
				GroupImpl.SYSTEM_GROUPS, customSystemGroups);
		}

		_sortedSystemGroups = new String[_allSystemGroups.length];

		System.arraycopy(
			_allSystemGroups, 0, _sortedSystemGroups, 0,
			_allSystemGroups.length);

		Arrays.sort(_sortedSystemGroups, new StringComparator());

		// Regular Roles

		String customSystemRoles[] = PropsUtil.getArray(PropsUtil.SYSTEM_ROLES);

		if ((customSystemRoles == null) || (customSystemRoles.length == 0)) {
			_allSystemRoles = RoleImpl.SYSTEM_ROLES;
		}
		else {
			_allSystemRoles = ArrayUtil.append(
				RoleImpl.SYSTEM_ROLES, customSystemRoles);
		}

		_sortedSystemRoles = new String[_allSystemRoles.length];

		System.arraycopy(
			_allSystemRoles, 0, _sortedSystemRoles, 0, _allSystemRoles.length);

		Arrays.sort(_sortedSystemRoles, new StringComparator());

		// Community Roles

		String customSystemCommunityRoles[] =
				PropsUtil.getArray(PropsUtil.SYSTEM_COMMUNITY_ROLES);

		if ((customSystemCommunityRoles == null) ||
				(customSystemCommunityRoles.length == 0)) {

			_allSystemCommunityRoles = RoleImpl.SYSTEM_COMMUNITY_ROLES;
		}
		else {
			_allSystemCommunityRoles = ArrayUtil.append(
				RoleImpl.SYSTEM_COMMUNITY_ROLES, customSystemCommunityRoles);
		}

		_sortedSystemCommunityRoles =
			new String[_allSystemCommunityRoles.length];

		System.arraycopy(
			_allSystemCommunityRoles, 0, _sortedSystemCommunityRoles, 0,
				_allSystemCommunityRoles.length);

		Arrays.sort(_sortedSystemCommunityRoles, new StringComparator());

		// Reserved parameter names

		_reservedParams = CollectionFactory.getHashSet();

		_reservedParams.add("p_l_id");
		_reservedParams.add("p_l_reset");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_action");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
		_reservedParams.add("p_p_static");
	}

	private String _getPathContext() {
		return _pathContext;
	}

	private String _getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	private String _getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	private String _getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	private String _getPathImage() {
		return _pathImage;
	}

	private String _getPathMain() {
		return _pathMain;
	}

	private String[] _getSystemCommunityRoles() {
		return _allSystemCommunityRoles;
	}

	private String[] _getSystemGroups() {
		return _allSystemGroups;
	}

	private String[] _getSystemRoles() {
		return _allSystemRoles;
	}

	private boolean _isSystemGroup(String groupName) {
		if (groupName == null) {
			return false;
		}

		groupName = groupName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemGroups, groupName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isSystemRole(String roleName) {
		if (roleName == null) {
			return false;
		}

		roleName = roleName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemRoles, roleName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			pos = Arrays.binarySearch(
				_sortedSystemCommunityRoles, roleName, new StringComparator());

			if (pos >= 0) {
				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactory.getLog(PortalUtil.class);

	private static PortalUtil _instance = new PortalUtil();

	private String _pathContext;
	private String _pathFriendlyURLPrivateGroup;
	private String _pathFriendlyURLPrivateUser;
	private String _pathFriendlyURLPublic;
	private String _pathImage;
	private String _pathMain;
	private String[] _allSystemCommunityRoles;
	private String[] _allSystemGroups;
	private String[] _allSystemRoles;
	private String[] _sortedSystemCommunityRoles;
	private String[] _sortedSystemGroups;
	private String[] _sortedSystemRoles;
	private Set _reservedParams;

}