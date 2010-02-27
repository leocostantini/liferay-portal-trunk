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

package com.liferay.portlet.layoutconfiguration.util.xml;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.RenderResponseImpl;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ActionURLLogic.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionURLLogic extends RuntimeLogic {

	public static final String OPEN_TAG = "<runtime-action-url";

	public static final String CLOSE_1_TAG = "</runtime-action-url>";

	public static final String CLOSE_2_TAG = "/>";

	public ActionURLLogic(RenderResponse renderResponse) {
		_renderResponseImpl = (RenderResponseImpl)renderResponse;
	}

	public String getOpenTag() {
		return OPEN_TAG;
	}

	public String getClose1Tag() {
		return CLOSE_1_TAG;
	}

	public void processXML(StringBuilder sb, String xml) throws Exception {
		Document doc = SAXReaderUtil.read(xml);

		Element root = doc.getRootElement();

		LiferayPortletURL portletURL =
			_renderResponseImpl.createLiferayPortletURL(getLifecycle());

		String portletId = root.attributeValue("portlet-name");

		if (portletId != null) {
			portletId = PortalUtil.getJsSafePortletId(portletId);

			portletURL.setPortletId(portletId);
		}

		for (int i = 1;; i++) {
			String paramName = root.attributeValue("param-name-" + i);
			String paramValue = root.attributeValue("param-value-" + i);

			if ((paramName == null) || (paramValue == null)) {
				break;
			}

			portletURL.setParameter(paramName, paramValue);
		}

		sb.append(portletURL.toString());
	}

	public String getLifecycle() {
		return _lifecycle;
	}

	private RenderResponseImpl _renderResponseImpl;
	private String _lifecycle = PortletRequest.ACTION_PHASE;

}