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

package com.liferay.portlet.iframe.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.iframe.util.IFrameUtil;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		String src = transformSrc(renderRequest, renderResponse);

		if (Validator.isNull(src)) {
			return mapping.findForward("/portal/portlet_not_setup");
		}

		renderRequest.setAttribute(WebKeys.IFRAME_SRC, src);

		return mapping.findForward("portlet.iframe.view");
	}

	protected String getSrc(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletPreferences preferences = renderRequest.getPreferences();

		String src = preferences.getValue("src", StringPool.BLANK);

		src = ParamUtil.getString(renderRequest, "src", src);

		return src;
	}

	protected String getUserName(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException, SystemException {

		PortletPreferences preferences = renderRequest.getPreferences();
		String userName = preferences.getValue("user-name", StringPool.BLANK);

		return IFrameUtil.getUserName(renderRequest, userName);
	}

	protected String getPassword(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletPreferences preferences = renderRequest.getPreferences();
		String password = preferences.getValue("password", StringPool.BLANK);

		return IFrameUtil.getPassword(renderRequest, password);
	}

	protected String transformSrc(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException, SystemException {

		PortletPreferences preferences = renderRequest.getPreferences();

		String src = getSrc(renderRequest, renderResponse);

		boolean auth = GetterUtil.getBoolean(
			preferences.getValue("auth", StringPool.BLANK));
		String authType = preferences.getValue("auth-type", StringPool.BLANK);
		String userName = getUserName(renderRequest, renderResponse);
		String password = getPassword(renderRequest, renderResponse);

		if (auth) {
			if (authType.equals("basic")) {
				int pos = src.indexOf("://");

				String protocol = src.substring(0, pos + 3);
				String url = src.substring(pos + 3, src.length());

				src =
					protocol + userName + ":" + password +
					"@" + url;
			}
			else {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)renderRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String portletId = PortalUtil.getPortletId(renderRequest);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					themeDisplay.getCompanyId(), portletId);

				src =
					themeDisplay.getPathMain() + "/" + portlet.getStrutsPath() +
						"/proxy?p_l_id=" + themeDisplay.getPlid() + "&p_p_id=" +
							portletId;
			}
		}

		return src;
	}

}