/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogsadmin.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Juan Fernández
 */
public class EntrySearchTerms extends EntryDisplayTerms {

	public EntrySearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		author = DAOParamUtil.getLike(portletRequest, AUTHOR);
		status = DAOParamUtil.getLike(portletRequest, STATUS);
		title = DAOParamUtil.getLike(portletRequest, TITLE);
	}

}