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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowTaskManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowTaskManagerUtil {

	public static WorkflowTask assignWorkflowTaskToRole(
			long companyId, long userId, long workflowTaskId, long roleId,
			String comment, Date dueDate, Map<String, Serializable> context)
		throws WorkflowException {

		return _workflowTaskManager.assignWorkflowTaskToRole(
			companyId, userId, workflowTaskId, roleId, comment, dueDate,
			context);
	}

	public static WorkflowTask assignWorkflowTaskToUser(
			long companyId, long userId, long workflowTaskId,
			long assigneeUserId, String comment, Date dueDate,
			Map<String, Serializable> context)
		throws WorkflowException {

		return _workflowTaskManager.assignWorkflowTaskToUser(
			companyId, userId, workflowTaskId, assigneeUserId, comment, dueDate,
			context);
	}

	public static WorkflowTask completeWorkflowTask(
			long companyId, long userId, long workflowTaskId,
			String transitionName, String comment,
			Map<String, Serializable> context)
		throws WorkflowException {

		return _workflowTaskManager.completeWorkflowTask(
			companyId, userId, workflowTaskId, transitionName, comment,
			context);
	}

	public static List<String> getNextTransitionNames(
			long companyId, long userId, long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getNextTransitionNames(
			companyId, userId, workflowTaskId);
	}

	public static long[] getPooledActorsIds(long companyId, long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getPooledActorsIds(
			companyId, workflowTaskId);
	}

	public static WorkflowTask getWorkflowTask(
			long companyId, long workflowTaskId)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTask(companyId, workflowTaskId);
	}

	public static int getWorkflowTaskCount(long companyId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCount(companyId, completed);
	}

	public static int getWorkflowTaskCountByRole(
			long companyId, long roleId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByRole(
			companyId, roleId, completed);
	}

	public static int getWorkflowTaskCountByUser(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByUser(
			companyId, userId, completed);
	}

	public static int getWorkflowTaskCountByUserRoles(
			long companyId, long userId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByUserRoles(
			companyId, userId, completed);
	}

	public static int getWorkflowTaskCountByWorkflowInstance(
			long companyId, long workflowInstanceId, Boolean completed)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTaskCountByWorkflowInstance(
			companyId, workflowInstanceId, completed);
	}

	public static WorkflowTaskManager getWorkflowTaskManager() {
		return _workflowTaskManager;
	}

	public static List<WorkflowTask> getWorkflowTasks(
			long companyId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasks(
			companyId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByRole(
			long companyId, long roleId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByRole(
			companyId, roleId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByUser(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByUser(
			companyId, userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByUserRoles(
			long companyId, long userId, Boolean completed, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByUserRoles(
			companyId, userId, completed, start, end, orderByComparator);
	}

	public static List<WorkflowTask> getWorkflowTasksByWorkflowInstance(
			long companyId, long workflowInstanceId, Boolean completed,
			int start, int end, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowTaskManager.getWorkflowTasksByWorkflowInstance(
			companyId, workflowInstanceId, completed, start, end,
			orderByComparator);
	}

	public static WorkflowTask updateDueDate(
			long companyId, long userId, long workflowTaskId, String comment,
			Date dueDate)
		throws WorkflowException {

		return _workflowTaskManager.updateDueDate(
			companyId, userId, workflowTaskId, comment, dueDate);
	}

	public void setWorkflowTaskManager(
		WorkflowTaskManager workflowTaskManager) {

		_workflowTaskManager = workflowTaskManager;
	}

	private static WorkflowTaskManager _workflowTaskManager;

}