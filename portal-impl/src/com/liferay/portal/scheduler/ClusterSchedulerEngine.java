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

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineClusterManager;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Tina Tian
 */
public class ClusterSchedulerEngine
	implements SchedulerEngine, SchedulerEngineClusterManager {

	public void delete(String groupName) throws SchedulerException {
		_schedulerEngine.delete(groupName);
	}

	public void delete(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.delete(jobName, groupName);
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJob(jobName, groupName);
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName);
	}

	public void pause(String groupName) throws SchedulerException {
		_schedulerEngine.pause(groupName);
	}

	public void pause(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.pause(jobName, groupName);
	}

	public void resume(String groupName) throws SchedulerException {
		_schedulerEngine.resume(groupName);
	}

	public void resume(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.resume(jobName, groupName);
	}

	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	public void setSchedulerEngine (SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	public void shutdown() throws SchedulerException {
		try {
			if (PropsValues.CLUSTER_LINK_ENABLED) {
				ClusterExecutorUtil.removeClusterEventListener(
					_memorySchedulerClusterEventListener);

				LockLocalServiceUtil.unlock(
					SchedulerEngine.class.getName(),
					SchedulerEngine.class.getName(), _localClusterNodeId,
					_retrieveFromCache);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}

		_schedulerEngine.shutdown();
	}

	public void start() throws SchedulerException {
		try {
			if (PropsValues.CLUSTER_LINK_ENABLED) {
				_memorySchedulerClusterEventListener =
					new MemorySchedulerClusterEventListener();

				ClusterExecutorUtil.addClusterEventListener(
					_memorySchedulerClusterEventListener);

				lockMemorySchedulerCluster(false, null);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}

		_schedulerEngine.start();
	}

	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.suppressError(jobName, groupName);
	}

	public void unschedule(String groupName) throws SchedulerException {
		_schedulerEngine.unschedule(groupName);
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(Trigger trigger) throws SchedulerException {
		_schedulerEngine.unschedule(trigger);
	}

	public void update(Trigger trigger) throws SchedulerException {
		_schedulerEngine.update(trigger);
	}

	public void updateMemorySchedulerClusterMaster() throws SchedulerException {
		try {
			Lock lock = lockMemorySchedulerCluster(false, null);

			if (ClusterExecutorUtil.isClusterNodeAlive(lock.getOwner())) {
				return;
			}

			lockMemorySchedulerCluster(true, lock.getOwner());
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to update memory scheduler cluster master", e);
		}
	}

	protected boolean isMemorySchedulerClusterLockOwner(Lock lock)
		throws Exception {

		ClusterNode clusterNode = ClusterExecutorUtil.getLocalClusterNode();

		String localClusterNodeId = clusterNode.getClusterNodeId();

		if (localClusterNodeId.equals(lock.getOwner())) {
			return true;
		}

		return false;
	}

	protected Lock lockMemorySchedulerCluster(
			boolean replaceOldLock, String oldOwner)
		throws Exception {

		if (_localClusterNodeId == null) {
			ClusterNode clusterNode = ClusterExecutorUtil.getLocalClusterNode();

			_localClusterNodeId = clusterNode.getClusterNodeId();
		}

		Lock lock = LockLocalServiceUtil.lock(
			SchedulerEngine.class.getName(), SchedulerEngine.class.getName(),
			oldOwner, _localClusterNodeId, _retrieveFromCache, replaceOldLock);

		return lock;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterSchedulerEngine.class);

	private static String _localClusterNodeId;

	private ClusterEventListener _memorySchedulerClusterEventListener;
	private boolean _retrieveFromCache = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.MEMORY_CLUSTER_SCHEDULER_LOCK_CACHE_ENABLED));
	private SchedulerEngine _schedulerEngine;

	private class MemorySchedulerClusterEventListener
		implements ClusterEventListener {

		public void processClusterEvent(ClusterEvent clusterEvent) {
			ClusterEventType clusterEventType =
				clusterEvent.getClusterEventType();

			if (!clusterEventType.equals(ClusterEventType.DEPART)) {
				return;
			}

			try {
				updateMemorySchedulerClusterMaster();
			}
			catch (Exception e) {
				_log.error("Unable to update memory scheduler cluster lock", e);
			}
		}

	}

}