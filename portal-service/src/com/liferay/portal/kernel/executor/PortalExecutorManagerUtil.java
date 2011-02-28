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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public class PortalExecutorManagerUtil {

	public static <T> Future<T> execute(
		String name, Callable<T> callable) {
		return _portalExecutorManager.execute(name, callable);
	}

	public static <T> T execute(
			String name, Callable<T> callable, long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {
		return _portalExecutorManager.execute(
			name, callable, timeout, timeUnit);
	}

	public static ThreadPoolExecutor getPortalExecutor(String name) {
		return _portalExecutorManager.getPortalExecutor(name);
	}

	public static ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent) {
		return _portalExecutorManager.getPortalExecutor(name, createIfAbsent);
	}

	public static void shutdown() {
		_portalExecutorManager.shutdown();
	}

	public static void shutdown(boolean interrupt) {
		_portalExecutorManager.shutdown(interrupt);
	}

	public static void shutdown(String name) {
		_portalExecutorManager.shutdown(name);
	}

	public static void shutdown(String name, boolean interrupt) {
		_portalExecutorManager.shutdown(name, interrupt);
	}

	public void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {
		_portalExecutorManager = portalExecutorManager;
	}

	private static PortalExecutorManager _portalExecutorManager;

}