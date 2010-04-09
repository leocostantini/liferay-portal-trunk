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

package com.liferay.counter.service.impl;

import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.persistence.CounterUtil;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * <a href="CounterServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CounterServiceImpl implements CounterService {

	public List<String> getNames() throws SystemException {
		return CounterUtil.getNames();
	}

	public long increment() throws SystemException {
		return CounterUtil.increment();
	}

	public long increment(String name) throws SystemException {
		return CounterUtil.increment(name);
	}

	public long increment(String name, int size) throws SystemException {
		return CounterUtil.increment(name, size);
	}

	public void rename(String oldName, String newName) throws SystemException {
		CounterUtil.rename(oldName, newName);
	}

	public void reset(String name) throws SystemException {
		CounterUtil.reset(name);
	}

	public void reset(String name, long size) throws SystemException {
		CounterUtil.reset(name, size);
	}

}