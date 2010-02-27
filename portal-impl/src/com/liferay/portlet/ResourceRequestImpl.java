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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Portlet;

import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ResourceRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ResourceRequestImpl
	extends ClientDataRequestImpl implements ResourceRequest {

	public String getCacheability() {
		return _cacheablity;
	}

	public String getETag() {
		return null;
	}

	public String getLifecycle() {
		return PortletRequest.RESOURCE_PHASE;
	}

	public Map<String, String[]> getPrivateRenderParameterMap() {
		return null;
	}

	public String getResourceID() {
		return _resourceID;
	}

	protected void init(
		HttpServletRequest request, Portlet portlet,
		InvokerPortlet invokerPortlet, PortletContext portletContext,
		WindowState windowState, PortletMode portletMode,
		PortletPreferences preferences, long plid) {

		super.init(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, preferences, plid);

		_cacheablity = ParamUtil.getString(
			request, "p_p_cacheability", ResourceURL.PAGE);
		_resourceID = request.getParameter("p_p_resource_id");
	}

	private String _cacheablity;
	private String _resourceID;

}