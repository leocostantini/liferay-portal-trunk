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

package com.liferay.util.bridges.jsf.common;

import com.icesoft.faces.env.ServletEnvironmentRequest;
import com.icesoft.faces.webapp.http.portlet.PortletArtifactHack;

import javax.faces.context.FacesContext;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * <a href="JSFPortletUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neil Griffin
 *
 */
public class JSFPortletUtil {

	public static PortletPreferences getPortletPreferences(
		FacesContext facesContext) {

		return JSFPortletUtil.getPortletRequest(facesContext).getPreferences();
	}

	public static PortletRequest getPortletRequest(FacesContext facesContext) {
		PortletRequest returnValue = null;

		Object request = facesContext.getExternalContext().getRequest();

		if (request instanceof PortletRequest) {
			returnValue = (PortletRequest)request;
		}
		else if (request instanceof ServletEnvironmentRequest) {
			ServletEnvironmentRequest servletEnvironmentRequest =
				(ServletEnvironmentRequest)request;

			PortletArtifactHack portletArtifactHack =
				(PortletArtifactHack)servletEnvironmentRequest.getAttribute(
					PortletArtifactHack.PORTLET_HACK_KEY);

			if (portletArtifactHack != null) {
				returnValue = portletArtifactHack.getPortletRequest();
			}
		}

		return returnValue;
	}

	public static String getPreferenceValue(
		FacesContext facesContext, String preferenceName) {

		return getPreferenceValue(facesContext, preferenceName, null);
	}

	public static String getPreferenceValue(
		PortletPreferences portletPreferences, String preferenceName) {

		return getPreferenceValue(portletPreferences, preferenceName, null);
	}

	public static String getPreferenceValue(
		FacesContext facesContext, String preferenceName, String defaultValue) {

		return getPreferenceValue(
			getPortletPreferences(facesContext), preferenceName, defaultValue);
	}

	public static String getPreferenceValue(
		PortletPreferences portletPreferences, String preferenceName,
		String defaultValue) {

		String returnValue = defaultValue;

		if (portletPreferences != null) {
			portletPreferences.getValue(preferenceName, defaultValue);
		}

		return returnValue;
	}

}