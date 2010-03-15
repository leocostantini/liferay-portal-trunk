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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.ServletVersionDetector;
import com.liferay.portal.kernel.util.ConcurrentHashSet;
import com.liferay.portal.util.PropsValues;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <a href="SharedSessionAttributeListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * Listener used to help manage shared session attributes into a cache. This
 * cache is more thread safe than the HttpSession and leads to fewer problems
 * with shared session attributes being modified out of sequence.
 * </p>
 *
 * @author Michael C. Han
 */
public class SharedSessionAttributeListener
	implements HttpSessionAttributeListener, HttpSessionListener {

	public SharedSessionAttributeListener() {
		if (ServletVersionDetector.is2_5()) {
			return;
		}

		_sessionIds = new ConcurrentHashSet<String>();
	}

	public void attributeAdded(HttpSessionBindingEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		if (!_sessionIds.contains(session.getId())) {
			return;
		}

		SharedSessionAttributeCache cache =
			SharedSessionAttributeCache.getInstance(session);

		String name = event.getName();

		for (String sharedName : PropsValues.SHARED_SESSION_ATTRIBUTES) {
			if (name.startsWith(sharedName)) {
				cache.setAttribute(name, event.getValue());

				return;
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		if (!_sessionIds.contains(session.getId())) {
			return;
		}

		SharedSessionAttributeCache cache =
			SharedSessionAttributeCache.getInstance(session);

		cache.removeAttribute(event.getName());
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		if (!_sessionIds.contains(session.getId())) {
			return;
		}

		SharedSessionAttributeCache cache =
			SharedSessionAttributeCache.getInstance(session);

		if (cache.contains(event.getName())) {
			cache.setAttribute(event.getName(), event.getValue());
		}
	}

	public void sessionCreated(HttpSessionEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		SharedSessionAttributeCache.getInstance(session);

		_sessionIds.add(session.getId());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		if (PropsValues.SESSION_DISABLED || ServletVersionDetector.is2_5()) {
			return;
		}

		HttpSession session = event.getSession();

		_sessionIds.remove(session.getId());
	}

	private Set<String> _sessionIds;

}