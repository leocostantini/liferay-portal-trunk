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

package com.liferay.portlet.workflow.service.base;

import com.liferay.documentlibrary.service.DLLocalService;
import com.liferay.documentlibrary.service.DLLocalServiceFactory;
import com.liferay.documentlibrary.service.DLService;
import com.liferay.documentlibrary.service.DLServiceFactory;

import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceLocalServiceFactory;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.ResourceServiceFactory;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourceFinderUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.ResourceUtil;

import com.liferay.portlet.workflow.service.SAWWorkflowLocalService;
import com.liferay.portlet.workflow.service.SAWWorkflowLocalServiceFactory;
import com.liferay.portlet.workflow.service.SAWWorkflowService;
import com.liferay.portlet.workflow.service.SAWWorkflowServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowComponentLocalService;
import com.liferay.portlet.workflow.service.WorkflowComponentLocalServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowComponentService;
import com.liferay.portlet.workflow.service.WorkflowComponentServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowDefinitionLocalService;
import com.liferay.portlet.workflow.service.WorkflowDefinitionLocalServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowDefinitionService;
import com.liferay.portlet.workflow.service.WorkflowInstanceLocalService;
import com.liferay.portlet.workflow.service.WorkflowInstanceLocalServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowInstanceService;
import com.liferay.portlet.workflow.service.WorkflowInstanceServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowTaskLocalService;
import com.liferay.portlet.workflow.service.WorkflowTaskLocalServiceFactory;
import com.liferay.portlet.workflow.service.WorkflowTaskService;
import com.liferay.portlet.workflow.service.WorkflowTaskServiceFactory;

/**
 * <a href="WorkflowDefinitionServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class WorkflowDefinitionServiceBaseImpl extends PrincipalBean
	implements WorkflowDefinitionService {
	public SAWWorkflowLocalService getSAWWorkflowLocalService() {
		return sawWorkflowLocalService;
	}

	public void setSAWWorkflowLocalService(
		SAWWorkflowLocalService sawWorkflowLocalService) {
		this.sawWorkflowLocalService = sawWorkflowLocalService;
	}

	public SAWWorkflowService getSAWWorkflowService() {
		return sawWorkflowService;
	}

	public void setSAWWorkflowService(SAWWorkflowService sawWorkflowService) {
		this.sawWorkflowService = sawWorkflowService;
	}

	public WorkflowComponentLocalService getWorkflowComponentLocalService() {
		return workflowComponentLocalService;
	}

	public void setWorkflowComponentLocalService(
		WorkflowComponentLocalService workflowComponentLocalService) {
		this.workflowComponentLocalService = workflowComponentLocalService;
	}

	public WorkflowComponentService getWorkflowComponentService() {
		return workflowComponentService;
	}

	public void setWorkflowComponentService(
		WorkflowComponentService workflowComponentService) {
		this.workflowComponentService = workflowComponentService;
	}

	public WorkflowDefinitionLocalService getWorkflowDefinitionLocalService() {
		return workflowDefinitionLocalService;
	}

	public void setWorkflowDefinitionLocalService(
		WorkflowDefinitionLocalService workflowDefinitionLocalService) {
		this.workflowDefinitionLocalService = workflowDefinitionLocalService;
	}

	public WorkflowInstanceLocalService getWorkflowInstanceLocalService() {
		return workflowInstanceLocalService;
	}

	public void setWorkflowInstanceLocalService(
		WorkflowInstanceLocalService workflowInstanceLocalService) {
		this.workflowInstanceLocalService = workflowInstanceLocalService;
	}

	public WorkflowInstanceService getWorkflowInstanceService() {
		return workflowInstanceService;
	}

	public void setWorkflowInstanceService(
		WorkflowInstanceService workflowInstanceService) {
		this.workflowInstanceService = workflowInstanceService;
	}

	public WorkflowTaskLocalService getWorkflowTaskLocalService() {
		return workflowTaskLocalService;
	}

	public void setWorkflowTaskLocalService(
		WorkflowTaskLocalService workflowTaskLocalService) {
		this.workflowTaskLocalService = workflowTaskLocalService;
	}

	public WorkflowTaskService getWorkflowTaskService() {
		return workflowTaskService;
	}

	public void setWorkflowTaskService(WorkflowTaskService workflowTaskService) {
		this.workflowTaskService = workflowTaskService;
	}

	public DLLocalService getDLLocalService() {
		return dlLocalService;
	}

	public void setDLLocalService(DLLocalService dlLocalService) {
		this.dlLocalService = dlLocalService;
	}

	public DLService getDLService() {
		return dlService;
	}

	public void setDLService(DLService dlService) {
		this.dlService = dlService;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	protected void init() {
		if (sawWorkflowLocalService == null) {
			sawWorkflowLocalService = SAWWorkflowLocalServiceFactory.getImpl();
		}

		if (sawWorkflowService == null) {
			sawWorkflowService = SAWWorkflowServiceFactory.getImpl();
		}

		if (workflowComponentLocalService == null) {
			workflowComponentLocalService = WorkflowComponentLocalServiceFactory.getImpl();
		}

		if (workflowComponentService == null) {
			workflowComponentService = WorkflowComponentServiceFactory.getImpl();
		}

		if (workflowDefinitionLocalService == null) {
			workflowDefinitionLocalService = WorkflowDefinitionLocalServiceFactory.getImpl();
		}

		if (workflowInstanceLocalService == null) {
			workflowInstanceLocalService = WorkflowInstanceLocalServiceFactory.getImpl();
		}

		if (workflowInstanceService == null) {
			workflowInstanceService = WorkflowInstanceServiceFactory.getImpl();
		}

		if (workflowTaskLocalService == null) {
			workflowTaskLocalService = WorkflowTaskLocalServiceFactory.getImpl();
		}

		if (workflowTaskService == null) {
			workflowTaskService = WorkflowTaskServiceFactory.getImpl();
		}

		if (dlLocalService == null) {
			dlLocalService = DLLocalServiceFactory.getImpl();
		}

		if (dlService == null) {
			dlService = DLServiceFactory.getImpl();
		}

		if (resourceLocalService == null) {
			resourceLocalService = ResourceLocalServiceFactory.getImpl();
		}

		if (resourceService == null) {
			resourceService = ResourceServiceFactory.getImpl();
		}

		if (resourcePersistence == null) {
			resourcePersistence = ResourceUtil.getPersistence();
		}

		if (resourceFinder == null) {
			resourceFinder = ResourceFinderUtil.getFinder();
		}
	}

	protected SAWWorkflowLocalService sawWorkflowLocalService;
	protected SAWWorkflowService sawWorkflowService;
	protected WorkflowComponentLocalService workflowComponentLocalService;
	protected WorkflowComponentService workflowComponentService;
	protected WorkflowDefinitionLocalService workflowDefinitionLocalService;
	protected WorkflowInstanceLocalService workflowInstanceLocalService;
	protected WorkflowInstanceService workflowInstanceService;
	protected WorkflowTaskLocalService workflowTaskLocalService;
	protected WorkflowTaskService workflowTaskService;
	protected DLLocalService dlLocalService;
	protected DLService dlService;
	protected ResourceLocalService resourceLocalService;
	protected ResourceService resourceService;
	protected ResourcePersistence resourcePersistence;
	protected ResourceFinder resourceFinder;
}