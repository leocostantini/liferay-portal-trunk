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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="PluginPackageException.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PluginPackageException extends SystemException {

	public PluginPackageException() {
		super();
	}

	public PluginPackageException(String msg) {
		super(msg);
	}

	public PluginPackageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PluginPackageException(Throwable cause) {
		super(cause);
	}

}