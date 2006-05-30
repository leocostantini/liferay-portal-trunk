/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.CompanyLocalServiceUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.service.spring.UserServiceUtil;
import com.liferay.portal.servlet.PortletContextPool;
import com.liferay.portal.servlet.PortletContextWrapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.comparator.RecipientComparator;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.CachePortlet;
import com.liferay.portlet.LiferayPortletMode;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.wsrp.URLGeneratorImpl;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringComparator;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.StringServletResponse;
import com.liferay.util.servlet.UploadPortletRequest;
import com.liferay.util.servlet.UploadServletRequest;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
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
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 * @author  Jorge Ferrer
 *
 */
public class PortalUtil {

	public static void clearRequestParameters(RenderRequest req) {

		// Clear the render parameters if they were set during processAction

		boolean action = ParamUtil.getBoolean(req, "p_p_action");

		if (action) {
			((RenderRequestImpl)req).getRenderParameters().clear();
		}
	}

	public static void copyRequestParameters(
		ActionRequest req, ActionResponse res) {

		// Set the request parameters as the render parameters. Skip this step
		// if the window state is exclusive, because the exclusive window state
		// is used for streaming binary files and does not have a render phase.

		try {
			ActionRequestImpl reqImpl = (ActionRequestImpl)req;

			HttpServletRequest originalReq = getOriginalServletRequest(
				reqImpl.getHttpServletRequest());

			if (LiferayWindowState.isExclusive(originalReq)) {
				return;
			}

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

	public static List getAllRecipients()
		throws PortalException, SystemException {

		List recipients = new ArrayList();

		recipients.addAll(getRecipients());

		Collections.sort(recipients, new RecipientComparator());

		return recipients;
	}

	public static Company getCompany(HttpServletRequest req)
		throws PortalException, SystemException {

		String companyId = getCompanyId(req);

		if (companyId == null) {
			return null;
		}

		Company company = (Company)req.getAttribute(WebKeys.COMPANY);

		if (company == null) {
			company = CompanyLocalServiceUtil.getCompany(companyId);

			req.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	public static Company getCompany(ActionRequest req)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getCompany(reqImpl.getHttpServletRequest());
	}

	public static Company getCompany(RenderRequest req)
		throws PortalException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getCompany(reqImpl.getHttpServletRequest());
	}

	public static String getCompanyId(HttpServletRequest req) {
		String companyId =
			(String)req.getSession().getAttribute(WebKeys.COMPANY_ID);

		if (companyId == null) {
			companyId = (String)req.getAttribute(WebKeys.COMPANY_ID);
		}

		return companyId;
	}

	public static String getCompanyId(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getCompanyId(reqImpl.getHttpServletRequest());
	}

	public static String getCompanyId(PortletRequest req) {
		String companyId = null;

		if (req instanceof ActionRequest) {
			companyId = getCompanyId((ActionRequest)req);
		}
		else {
			companyId = getCompanyId((RenderRequest)req);
		}

		return companyId;
	}

	public static String getCompanyId(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getCompanyId(reqImpl.getHttpServletRequest());
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
				cal = new GregorianCalendar();
			}
			else {
				cal = new GregorianCalendar(timeZone);
			}

			if ((hour == -1) || (min == -1)) {
				cal.set(year, month, day);
			}
			else {
				cal.set(year, month, day, hour, min, 0);
			}

			Date date = cal.getTime();

			/*if (timeZone != null &&
				cal.before(new GregorianCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	public static String getLayoutIdWithPortletId(
		List allLayouts, String portletId) {

		return getLayoutIdWithPortletId(allLayouts, portletId, null);
	}

	public static String getLayoutIdWithPortletId(
		List allLayouts, String portletId, String defaultLayoutId) {

		/*if (defaultLayoutId == null) {
			defaultLayoutId = Layout.DEFAULT_LAYOUT_ID;
		}

		for (int i = 0; i < allLayouts.size(); i++) {
			Layout layout = (Layout)allLayouts.get(i);

			if (layout.getType().equals(Layout.TYPE_PORTLET)) {
				LayoutTypePortlet layoutType =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutType.hasPortletId(portletId)) {
					return layout.getLayoutId();
				}
			}
		}*/ // FIX ME

		return defaultLayoutId;
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

		String layoutFriendlyURL = getLayoutFriendlyURL(layout, themeDisplay);

		if (Validator.isNotNull(layoutFriendlyURL)) {
			return layoutFriendlyURL;
		}

		return getLayoutActualURL(layout, themeDisplay);
	}

	public static String getLayoutActualURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutActualURL(layout, themeDisplay.getPathMain());
	}

	public static String getLayoutActualURL(Layout layout, String mainPath)
		throws PortalException, SystemException {

		Map vars = new FastHashMap();

		vars.put("liferay:mainPath", mainPath);
		vars.put("liferay:plid", layout.getPlid());
		vars.putAll(layout.getLayoutType().getTypeSettingsProperties());

		String href = PropsUtil.getComponentProperties().getString(
			PropsUtil.LAYOUT_URL,
			Filter.by(layout.getType()).setVariables(vars));

		return href;
	}

	public static String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (!isLayoutFriendliable(layout)) {
			return null;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		if (Validator.isNotNull(layoutFriendlyURL)) {
			Group group = GroupLocalServiceUtil.getGroup(
				layout.getGroupId());

			String parentFriendlyURL = group.getFriendlyURL();

			if (Validator.isNotNull(parentFriendlyURL)) {
				String friendlyURL = null;

				if (layout.isPrivateLayout()) {
					friendlyURL = themeDisplay.getPathFriendlyURLPrivate();
				}
				else {
					friendlyURL = themeDisplay.getPathFriendlyURLPublic();
				}

				return friendlyURL + parentFriendlyURL + layoutFriendlyURL;
			}
		}

		return null;
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
			PropsUtil.LAYOUT_PARENTABLE,
			Filter.by(type), true);
	}

	public static Locale getLocale(HttpServletRequest req) {
		return (Locale)req.getSession().getAttribute(Globals.LOCALE_KEY);
	}

	public static Locale getLocale(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getLocale(reqImpl.getHttpServletRequest());
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

	public static String getPortalURL(HttpServletRequest req, boolean secure) {
		StringBuffer sb = new StringBuffer();

		String serverProtocol = GetterUtil.getString(
			PropsUtil.get(PropsUtil.WEB_SERVER_PROTOCOL));

		if (secure || Http.HTTPS.equals(serverProtocol)) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		String serverHost = PropsUtil.get(PropsUtil.WEB_SERVER_HOST);

		if (Validator.isNull(serverHost)) {
			sb.append(req.getServerName());
		}
		else {
			sb.append(serverHost);
		}

		int serverHttpPort = GetterUtil.get(
			PropsUtil.get(PropsUtil.WEB_SERVER_HTTP_PORT), -1);

		if (serverHttpPort == -1) {
			if (!secure && (req.getServerPort() != Http.HTTP_PORT)) {
				sb.append(StringPool.COLON).append(req.getServerPort());
			}
		}
		else {
			if (!secure && (req.getServerPort() != serverHttpPort)) {
				if (serverHttpPort != Http.HTTP_PORT) {
					sb.append(StringPool.COLON).append(serverHttpPort);
				}
			}
		}

		int serverHttpsPort = GetterUtil.get(
			PropsUtil.get(PropsUtil.WEB_SERVER_HTTPS_PORT), -1);

		if (serverHttpsPort == -1) {
			if (secure && (req.getServerPort() != Http.HTTPS_PORT)) {
				sb.append(StringPool.COLON).append(req.getServerPort());
			}
		}
		else {
			if (secure && (req.getServerPort() != serverHttpsPort)) {
				if (serverHttpsPort != Http.HTTPS_PORT) {
					sb.append(StringPool.COLON).append(serverHttpsPort);
				}
			}
		}

		return sb.toString();
	}

	public static String getPortletGroupId(String plid) {
		String ownerId = Layout.getOwnerId(plid);

		return GetterUtil.get(
			Layout.getGroupId(ownerId), Group.DEFAULT_PARENT_GROUP_ID);
	}

	public static String getPortletGroupId(HttpServletRequest req) {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		return getPortletGroupId(layout.getPlid());
	}

	public static String getPortletGroupId(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getPortletGroupId(reqImpl.getHttpServletRequest());
	}

	public static String getPortletGroupId(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getPortletGroupId(reqImpl.getHttpServletRequest());
	} // FIX ME these methods can be removed

	public static String getPortletNamespace(String portletName) {
		String jsSafePortletName =
			StringUtil.replace(portletName, StringPool.SPACE, StringPool.BLANK);

		return StringPool.UNDERLINE + jsSafePortletName + StringPool.UNDERLINE;
	}

	public static String getPortletTitle(String portletId, User user)
		throws LanguageException {

		return LanguageUtil.get(
			user, WebKeys.JAVAX_PORTLET_TITLE + StringPool.PERIOD + portletId);
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext ctx, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactory.create(portlet, ctx);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(WebKeys.JAVAX_PORTLET_TITLE);
	}

	public static PortletPreferences getPreferences(HttpServletRequest req) {
		RenderRequest renderRequest =
			(RenderRequest)req.getAttribute(WebKeys.JAVAX_PORTLET_REQUEST);

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

	public static List getRecipients() throws PortalException, SystemException {
		List recipients = new ArrayList();

		/*recipients.addAll(CompanyServiceUtil.getUsers());

		Iterator itr = CompanyServiceUtil.getGroups().iterator();

		while (itr.hasNext()) {
			Group group = (Group)itr.next();

			if (!isSystemGroup(group.getName())) {
				recipients.add(group);
			}
		}

		Collections.sort(recipients, new RecipientComparator()); FIX ME */

		return recipients;
	}

	public static User getSelectedUser(HttpServletRequest req)
		throws PortalException, SystemException {

		String emailAddress = ParamUtil.getString(req, "p_u_e_a");

		User user = null;

		try {
			user = UserServiceUtil.getUserByEmailAddress(
				getCompanyId(req), emailAddress);
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	public static User getSelectedUser(ActionRequest req)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getSelectedUser(reqImpl.getHttpServletRequest());
	}

	public static User getSelectedUser(RenderRequest req)
		throws PortalException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getSelectedUser(reqImpl.getHttpServletRequest());
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
		HttpServletRequestWrapper reqWrapper) {

		UploadServletRequest uploadReq = null;

		while (uploadReq == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (reqWrapper instanceof UploadServletRequest) {
				uploadReq = (UploadServletRequest)reqWrapper;
			}
			else {
				ServletRequest req = reqWrapper.getRequest();

				if (!(req instanceof HttpServletRequestWrapper)) {
					break;
				}

				reqWrapper = (HttpServletRequestWrapper)reqWrapper.getRequest();
			}
		}

		return uploadReq;
	}

	public static User getUser(HttpServletRequest req)
		throws PortalException, SystemException {

		String userId = getUserId(req);

		if (userId == null) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session
			// and may not be accessible by the portlet WAR's session. This
			// behavior is inconsistent across different application servers.

			userId = req.getRemoteUser();

			if (userId == null) {
				return null;
			}
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

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUser(reqImpl.getHttpServletRequest());
	}

	public static User getUser(RenderRequest req)
		throws PortalException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getUser(reqImpl.getHttpServletRequest());
	}

	public static String getUserId(HttpSession ses) {
		return (String)ses.getAttribute(WebKeys.USER_ID);
	}

	public static String getUserId(HttpServletRequest req) {
		return getUserId(req.getSession());
	}

	public static String getUserId(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUserId(reqImpl.getHttpServletRequest());
	}

	public static String getUserId(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getUserId(reqImpl.getHttpServletRequest());
	}

	public static String getUserName(String userId, String defaultUserName) {
		return getUserName(userId, defaultUserName, null);
	}

	public static String getUserName(
		String userId, String defaultUserName, HttpServletRequest req) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			userName = user.getFullName();

			if (req != null) {
				Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

				PortletURL portletURL = new PortletURLImpl(
					req, PortletKeys.DIRECTORY, layout.getPlid(), false);

				portletURL.setWindowState(WindowState.MAXIMIZED);
				portletURL.setPortletMode(PortletMode.VIEW);

				portletURL.setParameter("struts_action", "/directory/edit_user");
				portletURL.setParameter("p_u_e_a", user.getEmailAddress());

				userName =
					"<a href=\"" + portletURL.toString() + "\">" + userName +
						"</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	public static String getUserPassword(HttpServletRequest req) {
		return (String)req.getSession().getAttribute(WebKeys.USER_PASSWORD);
	}

	public static String getUserPassword(ActionRequest req) {
		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getUserPassword(reqImpl.getHttpServletRequest());
	}

	public static String getUserPassword(RenderRequest req) {
		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getUserPassword(reqImpl.getHttpServletRequest());
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

	public static void renderPortlet(
			StringBuffer sb, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet)
		throws IOException, ServletException {

		renderPortlet(sb, ctx, req, res, portlet, null, null, null);
	}

	public static void renderPortlet(
			StringBuffer sb, ServletContext ctx, HttpServletRequest req,
			HttpServletResponse res, Portlet portlet, String columnId,
			Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		columnId = GetterUtil.getString(columnId);

		if (columnPos == null) {
			columnPos = new Integer(0);
		}

		if (columnCount == null) {
			columnCount = new Integer(0);
		}

		req.setAttribute(WebKeys.RENDER_PORTLET, portlet);

		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);

		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);

		req.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);

		RequestDispatcher rd = ctx.getRequestDispatcher(
			"/html/portal/render_portlet.jsp");

		if (sb != null) {
			StringServletResponse stringServletRes =
				new StringServletResponse(res);

			rd.include(req, stringServletRes);

			sb.append(stringServletRes.getString());
		}
		else {
			res.setContentType(Constants.TEXT_HTML);

			rd.include(req, res);
		}
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
			String portletId, User user, Layout layout, PortletMode portletMode)
		throws PortalException, SystemException {

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
						layout.getLayoutId(), layout.getOwnerId(),
						layout.getTypeSettings());
				}
			}

			return portletMode;
		}
	}

	public static WindowState updateWindowState(
			String portletId, User user, Layout layout, WindowState windowState,
			HttpServletRequest req)
		throws PortalException, SystemException {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((windowState == null) ||
			(Validator.isNull(windowState.toString())) ||
			(windowState.equals(LiferayWindowState.EXCLUSIVE))) {

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
				(windowState.equals(LiferayWindowState.POP_UP))) {

				if (layoutType.hasStateMax()) {
					String curMaxPortletId =
						StringUtil.split(layoutType.getStateMax())[0];

					// Clear cache and render parameters for the previous
					// portlet that had a maximum window state

					CachePortlet.clearResponse(
						req.getSession(), layout.getPrimaryKey(),
						curMaxPortletId);

					/*RenderParametersPool.clear(
						req, layout.getPlid(), curMaxPortletId);*/

					if (windowState.equals(LiferayWindowState.POP_UP)) {
						String stateMaxPrevious =
							layoutType.getStateMaxPrevious();

						if (stateMaxPrevious == null) {
							layoutType.setStateMaxPrevious(curMaxPortletId);

							updateLayout = true;
						}
					}
				}
				else {
					if (windowState.equals(LiferayWindowState.POP_UP)) {
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
						layout.getLayoutId(), layout.getOwnerId(),
						layout.getTypeSettings());
				}
			}

			return windowState;
		}
	}

	private PortalUtil() {

		// Groups

		String customSystemGroups[] =
			PropsUtil.getArray(PropsUtil.SYSTEM_GROUPS);

		if (customSystemGroups == null || customSystemGroups.length == 0) {
			_allSystemGroups = Group.SYSTEM_GROUPS;
		}
		else {
			_allSystemGroups = new String[
				Group.SYSTEM_GROUPS.length + customSystemGroups.length];

			System.arraycopy(
				Group.SYSTEM_GROUPS, 0, _allSystemGroups, 0,
				Group.SYSTEM_GROUPS.length);

			System.arraycopy(
				customSystemGroups, 0, _allSystemGroups,
				Group.SYSTEM_GROUPS.length, customSystemGroups.length);
		}

		_sortedSystemGroups = new String[_allSystemGroups.length];

		System.arraycopy(
			_allSystemGroups, 0, _sortedSystemGroups, 0,
			_allSystemGroups.length);

		Arrays.sort(_sortedSystemGroups, new StringComparator());

		// Roles

		String customSystemRoles[] = PropsUtil.getArray(PropsUtil.SYSTEM_ROLES);

		if (customSystemRoles == null || customSystemRoles.length == 0) {
			_allSystemRoles = Role.SYSTEM_ROLES;
		}
		else {
			_allSystemRoles = new String[
				Role.SYSTEM_ROLES.length + customSystemRoles.length];

			System.arraycopy(
				Role.SYSTEM_ROLES, 0, _allSystemRoles, 0,
				Role.SYSTEM_ROLES.length);

			System.arraycopy(
				customSystemRoles, 0, _allSystemRoles, Role.SYSTEM_ROLES.length,
				customSystemRoles.length);
		}

		_sortedSystemRoles = new String[_allSystemRoles.length];

		System.arraycopy(
			_allSystemRoles, 0, _sortedSystemRoles, 0, _allSystemRoles.length);

		Arrays.sort(_sortedSystemRoles, new StringComparator());

		// Reserved parameter names

		_reservedParams = CollectionFactory.getHashSet();

		_reservedParams.add("p_l_id");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_action");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
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
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(PortalUtil.class);

	private static PortalUtil _instance = new PortalUtil();

	private String[] _allSystemGroups;
	private String[] _allSystemRoles;
	private String[] _sortedSystemGroups;
	private String[] _sortedSystemRoles;
	private Set _reservedParams;

}