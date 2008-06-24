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

package com.liferay.portal.servlet.filters.sso.opensso;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="OpenSSOFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Prashant Dighe
 *
 */
public class OpenSSOFilter extends BasePortalFilter {

	protected void processFilter(
		ServletRequest req, ServletResponse res, FilterChain chain) {

		try {
			HttpServletRequest httpReq = (HttpServletRequest)req;
			HttpServletResponse httpRes = (HttpServletResponse)res;

			long companyId = PortalUtil.getCompanyId(httpReq);

			boolean enabled = PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
				PropsValues.OPEN_SSO_AUTH_ENABLED);
			String loginUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_LOGIN_URL,
				PropsValues.OPEN_SSO_LOGIN_URL);
			String logoutUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_LOGOUT_URL,
				PropsValues.OPEN_SSO_LOGOUT_URL);
			String serviceUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_SERVICE_URL,
				PropsValues.OPEN_SSO_SERVICE_URL);

			if (!enabled || Validator.isNull(loginUrl) ||
				Validator.isNull(logoutUrl) || Validator.isNull(serviceUrl)) {

				processFilter(OpenSSOFilter.class, req, res, chain);

				return;
			}

			String requestURI = GetterUtil.getString(httpReq.getRequestURI());

			if (requestURI.endsWith("/portal/logout")) {
				HttpSession httpSes = httpReq.getSession();

				httpSes.invalidate();

				httpRes.sendRedirect(logoutUrl);
			}
			else {
				boolean authenticated = false;

				try {

					// LEP-5943

					authenticated = OpenSSOUtil.isAuthenticated(
						httpReq, serviceUrl);
				}
				catch (Exception e) {
					_log.error(e, e);

					processFilter(OpenSSOFilter.class, req, res, chain);

					return;
				}

				if (authenticated) {

					// LEP-5943

					String newSubjectId = OpenSSOUtil.getSubjectId(
						httpReq, serviceUrl);

					HttpSession httpSes = httpReq.getSession();

					String oldSubjectId = (String)httpSes.getAttribute(
						_SUBJECT_ID_KEY);

					if (oldSubjectId == null) {
						httpSes.setAttribute(_SUBJECT_ID_KEY, newSubjectId);
					}
					else if (!newSubjectId.equals(oldSubjectId)) {
						httpSes.invalidate();

						httpSes = httpReq.getSession();

						httpSes.setAttribute(_SUBJECT_ID_KEY, newSubjectId);
					}

					processFilter(OpenSSOFilter.class, req, res, chain);
				}
				else {
					httpRes.sendRedirect(loginUrl);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final String _SUBJECT_ID_KEY = "open.sso.subject.id";

	private static Log _log = LogFactoryUtil.getLog(OpenSSOFilter.class);

}