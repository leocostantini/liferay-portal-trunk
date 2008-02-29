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

package com.liferay.portlet.tasks.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.CounterServiceFactory;

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceFactory;

import com.liferay.portal.service.ActivityTrackerLocalService;
import com.liferay.portal.service.ActivityTrackerLocalServiceFactory;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceFactory;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.CompanyServiceFactory;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesLocalServiceFactory;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceLocalServiceFactory;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.ResourceServiceFactory;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceFactory;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.UserServiceFactory;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.persistence.ActivityTrackerFinder;
import com.liferay.portal.service.persistence.ActivityTrackerFinderUtil;
import com.liferay.portal.service.persistence.ActivityTrackerPersistence;
import com.liferay.portal.service.persistence.ActivityTrackerUtil;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesFinderUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourceFinderUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.ResourceUtil;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserFinderUtil;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserUtil;

import com.liferay.portlet.tasks.service.TasksProposalLocalService;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceFactory;
import com.liferay.portlet.tasks.service.TasksProposalService;
import com.liferay.portlet.tasks.service.TasksProposalServiceFactory;
import com.liferay.portlet.tasks.service.TasksReviewLocalService;
import com.liferay.portlet.tasks.service.TasksReviewLocalServiceFactory;
import com.liferay.portlet.tasks.service.TasksReviewService;
import com.liferay.portlet.tasks.service.persistence.TasksProposalFinder;
import com.liferay.portlet.tasks.service.persistence.TasksProposalFinderUtil;
import com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence;
import com.liferay.portlet.tasks.service.persistence.TasksProposalUtil;
import com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence;
import com.liferay.portlet.tasks.service.persistence.TasksReviewUtil;

import org.springframework.beans.factory.InitializingBean;

/**
 * <a href="TasksReviewServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class TasksReviewServiceBaseImpl extends PrincipalBean
	implements TasksReviewService, InitializingBean {
	public TasksProposalLocalService getTasksProposalLocalService() {
		return tasksProposalLocalService;
	}

	public void setTasksProposalLocalService(
		TasksProposalLocalService tasksProposalLocalService) {
		this.tasksProposalLocalService = tasksProposalLocalService;
	}

	public TasksProposalService getTasksProposalService() {
		return tasksProposalService;
	}

	public void setTasksProposalService(
		TasksProposalService tasksProposalService) {
		this.tasksProposalService = tasksProposalService;
	}

	public TasksProposalPersistence getTasksProposalPersistence() {
		return tasksProposalPersistence;
	}

	public void setTasksProposalPersistence(
		TasksProposalPersistence tasksProposalPersistence) {
		this.tasksProposalPersistence = tasksProposalPersistence;
	}

	public TasksProposalFinder getTasksProposalFinder() {
		return tasksProposalFinder;
	}

	public void setTasksProposalFinder(TasksProposalFinder tasksProposalFinder) {
		this.tasksProposalFinder = tasksProposalFinder;
	}

	public TasksReviewLocalService getTasksReviewLocalService() {
		return tasksReviewLocalService;
	}

	public void setTasksReviewLocalService(
		TasksReviewLocalService tasksReviewLocalService) {
		this.tasksReviewLocalService = tasksReviewLocalService;
	}

	public TasksReviewPersistence getTasksReviewPersistence() {
		return tasksReviewPersistence;
	}

	public void setTasksReviewPersistence(
		TasksReviewPersistence tasksReviewPersistence) {
		this.tasksReviewPersistence = tasksReviewPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public ActivityTrackerLocalService getActivityTrackerLocalService() {
		return activityTrackerLocalService;
	}

	public void setActivityTrackerLocalService(
		ActivityTrackerLocalService activityTrackerLocalService) {
		this.activityTrackerLocalService = activityTrackerLocalService;
	}

	public ActivityTrackerPersistence getActivityTrackerPersistence() {
		return activityTrackerPersistence;
	}

	public void setActivityTrackerPersistence(
		ActivityTrackerPersistence activityTrackerPersistence) {
		this.activityTrackerPersistence = activityTrackerPersistence;
	}

	public ActivityTrackerFinder getActivityTrackerFinder() {
		return activityTrackerFinder;
	}

	public void setActivityTrackerFinder(
		ActivityTrackerFinder activityTrackerFinder) {
		this.activityTrackerFinder = activityTrackerFinder;
	}

	public CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	public PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	public void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	public PortletPreferencesPersistence getPortletPreferencesPersistence() {
		return portletPreferencesPersistence;
	}

	public void setPortletPreferencesPersistence(
		PortletPreferencesPersistence portletPreferencesPersistence) {
		this.portletPreferencesPersistence = portletPreferencesPersistence;
	}

	public PortletPreferencesFinder getPortletPreferencesFinder() {
		return portletPreferencesFinder;
	}

	public void setPortletPreferencesFinder(
		PortletPreferencesFinder portletPreferencesFinder) {
		this.portletPreferencesFinder = portletPreferencesFinder;
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

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void afterPropertiesSet() {
		if (tasksProposalLocalService == null) {
			tasksProposalLocalService = TasksProposalLocalServiceFactory.getImpl();
		}

		if (tasksProposalService == null) {
			tasksProposalService = TasksProposalServiceFactory.getImpl();
		}

		if (tasksProposalPersistence == null) {
			tasksProposalPersistence = TasksProposalUtil.getPersistence();
		}

		if (tasksProposalFinder == null) {
			tasksProposalFinder = TasksProposalFinderUtil.getFinder();
		}

		if (tasksReviewLocalService == null) {
			tasksReviewLocalService = TasksReviewLocalServiceFactory.getImpl();
		}

		if (tasksReviewPersistence == null) {
			tasksReviewPersistence = TasksReviewUtil.getPersistence();
		}

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
		}

		if (mailService == null) {
			mailService = MailServiceFactory.getImpl();
		}

		if (activityTrackerLocalService == null) {
			activityTrackerLocalService = ActivityTrackerLocalServiceFactory.getImpl();
		}

		if (activityTrackerPersistence == null) {
			activityTrackerPersistence = ActivityTrackerUtil.getPersistence();
		}

		if (activityTrackerFinder == null) {
			activityTrackerFinder = ActivityTrackerFinderUtil.getFinder();
		}

		if (companyLocalService == null) {
			companyLocalService = CompanyLocalServiceFactory.getImpl();
		}

		if (companyService == null) {
			companyService = CompanyServiceFactory.getImpl();
		}

		if (companyPersistence == null) {
			companyPersistence = CompanyUtil.getPersistence();
		}

		if (portletPreferencesLocalService == null) {
			portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getImpl();
		}

		if (portletPreferencesPersistence == null) {
			portletPreferencesPersistence = PortletPreferencesUtil.getPersistence();
		}

		if (portletPreferencesFinder == null) {
			portletPreferencesFinder = PortletPreferencesFinderUtil.getFinder();
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

		if (userLocalService == null) {
			userLocalService = UserLocalServiceFactory.getImpl();
		}

		if (userService == null) {
			userService = UserServiceFactory.getImpl();
		}

		if (userPersistence == null) {
			userPersistence = UserUtil.getPersistence();
		}

		if (userFinder == null) {
			userFinder = UserFinderUtil.getFinder();
		}
	}

	protected TasksProposalLocalService tasksProposalLocalService;
	protected TasksProposalService tasksProposalService;
	protected TasksProposalPersistence tasksProposalPersistence;
	protected TasksProposalFinder tasksProposalFinder;
	protected TasksReviewLocalService tasksReviewLocalService;
	protected TasksReviewPersistence tasksReviewPersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected MailService mailService;
	protected ActivityTrackerLocalService activityTrackerLocalService;
	protected ActivityTrackerPersistence activityTrackerPersistence;
	protected ActivityTrackerFinder activityTrackerFinder;
	protected CompanyLocalService companyLocalService;
	protected CompanyService companyService;
	protected CompanyPersistence companyPersistence;
	protected PortletPreferencesLocalService portletPreferencesLocalService;
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	protected PortletPreferencesFinder portletPreferencesFinder;
	protected ResourceLocalService resourceLocalService;
	protected ResourceService resourceService;
	protected ResourcePersistence resourcePersistence;
	protected ResourceFinder resourceFinder;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
}