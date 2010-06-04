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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="HeaderTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Sergio González
 */
public class HeaderTag extends IncludeTag {

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:header:title", _title);
		request.setAttribute("liferay-ui:header:backLabel", _backLabel);
		request.setAttribute("liferay-ui:header:backURL", _backURL);
		request.setAttribute("liferay-ui:header:cssClass", _cssClass);
	}

	protected void cleanUp() {
		_title = null;
		_backLabel = null;
		_backURL = null;
		_cssClass = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setBackLabel(String backLabel) {
		_backLabel = backLabel;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/header/page.jsp";

	private String _title;
	private String _backLabel;
	private String _backURL;
	private String _cssClass;

}