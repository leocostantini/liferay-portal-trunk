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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * <a href="PrincipalThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PrincipalThreadLocal {

	public static String getName() {
		String name = _name.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getName " + name);
		}

		return name;
	}

	public static void setName(long name) {
		setName(String.valueOf(name));
	}

	public static void setName(String name) {
		if (_log.isDebugEnabled()) {
			_log.debug("setName " + name);
		}

		_name.set(name);
	}

	private static Log _log = LogFactoryUtil.getLog(PrincipalThreadLocal.class);

	private static ThreadLocal<String> _name =
		new AutoResetThreadLocal<String>();

}