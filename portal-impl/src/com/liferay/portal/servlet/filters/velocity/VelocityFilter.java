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

package com.liferay.portal.servlet.filters.velocity;

import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="VelocityFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author	   Brian Wing Shun Chan
 * @author	   Raymond Aug�
 * @deprecated
 */
public class VelocityFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		processFilter(VelocityFilter.class, request, response, filterChain);
	}

}