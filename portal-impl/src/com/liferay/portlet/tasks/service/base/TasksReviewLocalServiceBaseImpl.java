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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.search.DynamicQueryInitializer;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceFactory;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.UserServiceFactory;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserFinderUtil;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserUtil;

import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.SocialActivityLocalServiceFactory;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityFinderUtil;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityUtil;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.TasksProposalLocalService;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceFactory;
import com.liferay.portlet.tasks.service.TasksProposalService;
import com.liferay.portlet.tasks.service.TasksProposalServiceFactory;
import com.liferay.portlet.tasks.service.TasksReviewLocalService;
import com.liferay.portlet.tasks.service.persistence.TasksProposalFinder;
import com.liferay.portlet.tasks.service.persistence.TasksProposalFinderUtil;
import com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence;
import com.liferay.portlet.tasks.service.persistence.TasksProposalUtil;
import com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence;
import com.liferay.portlet.tasks.service.persistence.TasksReviewUtil;

import java.util.List;

/**
 * <a href="TasksReviewLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class TasksReviewLocalServiceBaseImpl
	implements TasksReviewLocalService {
	public TasksReview addTasksReview(TasksReview tasksReview)
		throws SystemException {
		tasksReview.setNew(true);

		return tasksReviewPersistence.update(tasksReview, false);
	}

	public void deleteTasksReview(long reviewId)
		throws PortalException, SystemException {
		tasksReviewPersistence.remove(reviewId);
	}

	public void deleteTasksReview(TasksReview tasksReview)
		throws SystemException {
		tasksReviewPersistence.remove(tasksReview);
	}

	public List<TasksReview> dynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		return tasksReviewPersistence.findWithDynamicQuery(queryInitializer);
	}

	public List<TasksReview> dynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		return tasksReviewPersistence.findWithDynamicQuery(queryInitializer,
			start, end);
	}

	public TasksReview getTasksReview(long reviewId)
		throws PortalException, SystemException {
		return tasksReviewPersistence.findByPrimaryKey(reviewId);
	}

	public TasksReview updateTasksReview(TasksReview tasksReview)
		throws SystemException {
		tasksReview.setNew(false);

		return tasksReviewPersistence.update(tasksReview, true);
	}

	public TasksReviewPersistence getTasksReviewPersistence() {
		return tasksReviewPersistence;
	}

	public void setTasksReviewPersistence(
		TasksReviewPersistence tasksReviewPersistence) {
		this.tasksReviewPersistence = tasksReviewPersistence;
	}

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

	public SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	public void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	protected void init() {
		if (tasksReviewPersistence == null) {
			tasksReviewPersistence = TasksReviewUtil.getPersistence();
		}

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

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
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

		if (socialActivityLocalService == null) {
			socialActivityLocalService = SocialActivityLocalServiceFactory.getImpl();
		}

		if (socialActivityPersistence == null) {
			socialActivityPersistence = SocialActivityUtil.getPersistence();
		}

		if (socialActivityFinder == null) {
			socialActivityFinder = SocialActivityFinderUtil.getFinder();
		}
	}

	protected TasksReviewPersistence tasksReviewPersistence;
	protected TasksProposalLocalService tasksProposalLocalService;
	protected TasksProposalService tasksProposalService;
	protected TasksProposalPersistence tasksProposalPersistence;
	protected TasksProposalFinder tasksProposalFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
	protected SocialActivityLocalService socialActivityLocalService;
	protected SocialActivityPersistence socialActivityPersistence;
	protected SocialActivityFinder socialActivityFinder;
}