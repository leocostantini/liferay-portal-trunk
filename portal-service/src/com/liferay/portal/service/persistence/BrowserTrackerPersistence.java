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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.BrowserTracker;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerPersistenceImpl
 * @see       BrowserTrackerUtil
 * @generated
 */
public interface BrowserTrackerPersistence extends BasePersistence<BrowserTracker> {
	public void cacheResult(
		com.liferay.portal.model.BrowserTracker browserTracker);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.BrowserTracker> browserTrackers);

	public com.liferay.portal.model.BrowserTracker create(long browserTrackerId);

	public com.liferay.portal.model.BrowserTracker remove(long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.BrowserTracker updateImpl(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.BrowserTracker findByPrimaryKey(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.BrowserTracker fetchByPrimaryKey(
		long browserTrackerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.BrowserTracker findByUserId(long userId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.BrowserTracker fetchByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.BrowserTracker fetchByUserId(long userId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.BrowserTracker> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}