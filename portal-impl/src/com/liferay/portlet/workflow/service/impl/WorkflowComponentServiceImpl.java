/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.workflow.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.jbi.WorkflowComponent;
import com.liferay.portal.kernel.jbi.WorkflowComponentException;
import com.liferay.portal.model.User;
import com.liferay.portlet.workflow.jbi.WorkflowXMLUtil;
import com.liferay.portlet.workflow.model.WorkflowTask;
import com.liferay.portlet.workflow.service.base.WorkflowComponentServiceBaseImpl;

import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowComponentServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Ganesh
 *
 */
public class WorkflowComponentServiceImpl
	extends WorkflowComponentServiceBaseImpl implements WorkflowComponent {

	public List getCurrentTasks(long instanceId, long tokenId)
		throws WorkflowComponentException {

		try {
			String xml = getCurrentTasksXml(instanceId, tokenId);

			return WorkflowXMLUtil.parseList(xml, "tasks");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getCurrentTasksXml(long instanceId, long tokenId)
		throws WorkflowComponentException {

		String userId = _getLoggedInUserId();

		return sawWorkflowLocalService.getCurrentTasksXml(
			instanceId, tokenId, userId);

	}

	public String deploy(String xml) throws WorkflowComponentException {

		return sawWorkflowLocalService.deploy(xml);

	}

	public Object getDefinition(long definitionId)
		throws WorkflowComponentException {

		try {
			String xml = getDefinitionXml(definitionId);

			return WorkflowXMLUtil.parseDefinition(xml);
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public List getDefinitions(
			long definitionId, String name, int start, int end)
		throws WorkflowComponentException {

		try {
			String xml = getDefinitionsXml(definitionId, name, start, end);

			return WorkflowXMLUtil.parseList(xml, "definitions");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getDefinitionsXml(
			long definitionId, String name, int start, int end)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.getDefinitionsXml(
			definitionId, name, start, end);

	}

	public int getDefinitionsCount(long definitionId, String name)
		throws WorkflowComponentException {

		try {
			String xml = getDefinitionsCountXml(definitionId, name);

			return WorkflowXMLUtil.parseInt(xml, "count");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getDefinitionsCountXml(long definitionId, String name)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.getDefinitionsCountXml(
			definitionId, name);

	}

	public String getDefinitionXml(long definitionId)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.getDefinitionXml(definitionId);

	}

	public List getInstances(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean retrieveUserInstances, boolean andOperator,
			int start,int end)
		throws WorkflowComponentException {

		try {
			String xml = getInstancesXml(
				definitionId, instanceId, definitionName, definitionVersion,
					startDateGT, startDateLT, endDateGT, endDateLT,
						hideEndedTasks,retrieveUserInstances,
							andOperator, start, end);

			return WorkflowXMLUtil.parseList(xml, "instances");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public int getInstancesCount(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean retrieveUserInstances, boolean andOperator)
		throws WorkflowComponentException {

		try {
			String xml = getInstancesCountXml(
				definitionId, instanceId, definitionName, definitionVersion,
					startDateGT, startDateLT, endDateGT, endDateLT,
						hideEndedTasks,retrieveUserInstances, andOperator);

			return WorkflowXMLUtil.parseInt(xml, "count");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getInstancesCountXml(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean retrieveUserInstances, boolean andOperator)
		throws WorkflowComponentException {

		String userId = _getLoggedInUserId();

		return sawWorkflowLocalService.getInstancesCountXml(definitionId,
				instanceId, definitionName, definitionVersion, startDateGT,
					startDateLT, endDateGT, endDateLT, userId, hideEndedTasks,
						retrieveUserInstances, andOperator);

}

	public String getInstancesXml(
			long definitionId, long instanceId, String definitionName,
			String definitionVersion, String startDateGT, String startDateLT,
			String endDateGT, String endDateLT, boolean hideEndedTasks,
			boolean retrieveUserInstances, boolean andOperator,
			int start,int end)
		throws WorkflowComponentException {

		String userId = _getLoggedInUserId();
		return sawWorkflowLocalService.getInstancesXml(
			definitionId, instanceId,
				definitionName, definitionVersion, startDateGT, startDateLT,
					endDateGT, endDateLT, userId, hideEndedTasks,
						retrieveUserInstances,andOperator, start, end);

	}

	public WorkflowTask getTask(long taskId) throws WorkflowComponentException {
		try {
			String xml = getTaskXml(taskId);

			return WorkflowXMLUtil.parseTask(xml);
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getTaskXml(long taskId) throws WorkflowComponentException {

		return sawWorkflowLocalService.getTaskXml(taskId);

	}

	public List getTaskFormElements(long taskId)
		throws WorkflowComponentException {

		try {
			String xml = getTaskFormElementsXml(taskId);

			return WorkflowXMLUtil.parseList(xml, "taskFormElements");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getTaskFormElementsXml(long taskId)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.getTaskFormElementsXml(taskId);

	}

	public List getTaskTransitions(long taskId)
		throws WorkflowComponentException {

		try {
			String xml = getTaskTransitionsXml(taskId);

			return WorkflowXMLUtil.parseList(xml, "taskTransitions");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getTaskTransitionsXml(long taskId)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.getTaskTransitionsXml(taskId);

	}

	public List getUserTasks(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks,
			boolean andOperator,int start, int end)
		throws WorkflowComponentException {

		try {
			String xml = getUserTasksXml(
				instanceId, taskName, definitionName, assignedTo, createDateGT,
					createDateLT, startDateGT, startDateLT, endDateGT, 
						endDateLT, hideEndedTasks, andOperator, start, end);

			return WorkflowXMLUtil.parseList(xml, "tasks");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public int getUserTasksCount(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks,
			boolean andOperator)
		throws WorkflowComponentException {

		try {
			String xml = getUserTasksCountXml(
				instanceId, taskName, definitionName, assignedTo, createDateGT,
					createDateLT, startDateGT, startDateLT, endDateGT, 
						endDateLT, hideEndedTasks, andOperator);

			return WorkflowXMLUtil.parseInt(xml, "count");
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String getUserTasksCountXml(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks,
			boolean andOperator)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.getUserTasksCountXml(instanceId,
			taskName, definitionName, assignedTo, createDateGT,
				createDateLT, startDateGT, startDateLT, endDateGT,
					endDateLT, hideEndedTasks, andOperator);

	}

	public String getUserTasksXml(
			long instanceId, String taskName, String definitionName,
			String assignedTo, String createDateGT, String createDateLT,
			String startDateGT, String startDateLT, String endDateGT,
			String endDateLT, boolean hideEndedTasks, boolean andOperator,
			int start, int end)
		throws WorkflowComponentException {

		String userId = _getLoggedInUserId();
		return sawWorkflowLocalService.getUserTasksXml(instanceId, taskName,
			definitionName, assignedTo, createDateGT, createDateLT,
				startDateGT, startDateLT, endDateGT, endDateLT, userId,
					hideEndedTasks, andOperator, start, end);

	}

	public void signalInstance(long instanceId)
		throws WorkflowComponentException {

		sawWorkflowLocalService.signalInstance(instanceId);

	}

	public void signalToken(long instanceId, long tokenId)
		throws WorkflowComponentException {

		sawWorkflowLocalService.signalToken(instanceId, tokenId);

	}

	public String startWorkflow(long definitionId)
		throws WorkflowComponentException {

		return sawWorkflowLocalService.startWorkflow(definitionId);

	}

	public Map updateTask(long taskId, String transition, Map parameterMap)
		throws WorkflowComponentException {

		try {
			String xml = updateTaskXml(taskId, transition, parameterMap);

			return WorkflowXMLUtil.parseErrors(xml);
		}
		catch (Exception e) {
			throw new WorkflowComponentException(e);
		}
	}

	public String updateTaskXml(
			long taskId, String transition, Map parameterMap)
		throws WorkflowComponentException {

		String userId = _getLoggedInUserId();

		return sawWorkflowLocalService.updateTaskXml(taskId, transition, userId,
			parameterMap);

	}

	private String _getLoggedInUserId() throws WorkflowComponentException{
		User user = null;
		try {
			user = getUser();
		} 
		catch (PortalException e1) {
			throw new WorkflowComponentException(e1);
		} 
		catch (SystemException e1) {
			throw new WorkflowComponentException(e1);
		}
		String userId = String.valueOf(user.getUserId());
		return userId;
	}

}