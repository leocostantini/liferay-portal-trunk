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

package com.liferay.portlet.polls.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.CounterServiceFactory;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsChoiceLocalService;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceFactory;
import com.liferay.portlet.polls.service.PollsQuestionLocalService;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceFactory;
import com.liferay.portlet.polls.service.PollsQuestionService;
import com.liferay.portlet.polls.service.PollsQuestionServiceFactory;
import com.liferay.portlet.polls.service.PollsVoteLocalService;
import com.liferay.portlet.polls.service.persistence.PollsChoiceFinder;
import com.liferay.portlet.polls.service.persistence.PollsChoiceFinderUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoicePersistence;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.polls.service.persistence.PollsVotePersistence;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;

import java.util.List;

/**
 * <a href="PollsVoteLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class PollsVoteLocalServiceBaseImpl
	implements PollsVoteLocalService, InitializingBean {
	public PollsVote addPollsVote(PollsVote pollsVote)
		throws SystemException {
		pollsVote.setNew(true);

		return pollsVotePersistence.update(pollsVote, false);
	}

	public void deletePollsVote(long voteId)
		throws PortalException, SystemException {
		pollsVotePersistence.remove(voteId);
	}

	public void deletePollsVote(PollsVote pollsVote) throws SystemException {
		pollsVotePersistence.remove(pollsVote);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return pollsVotePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return pollsVotePersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public PollsVote getPollsVote(long voteId)
		throws PortalException, SystemException {
		return pollsVotePersistence.findByPrimaryKey(voteId);
	}

	public List<PollsVote> getPollsVotes(int start, int end)
		throws SystemException {
		return pollsVotePersistence.findAll(start, end);
	}

	public int getPollsVotesCount() throws SystemException {
		return pollsVotePersistence.countAll();
	}

	public PollsVote updatePollsVote(PollsVote pollsVote)
		throws SystemException {
		pollsVote.setNew(false);

		return pollsVotePersistence.update(pollsVote, true);
	}

	public PollsChoiceLocalService getPollsChoiceLocalService() {
		return pollsChoiceLocalService;
	}

	public void setPollsChoiceLocalService(
		PollsChoiceLocalService pollsChoiceLocalService) {
		this.pollsChoiceLocalService = pollsChoiceLocalService;
	}

	public PollsChoicePersistence getPollsChoicePersistence() {
		return pollsChoicePersistence;
	}

	public void setPollsChoicePersistence(
		PollsChoicePersistence pollsChoicePersistence) {
		this.pollsChoicePersistence = pollsChoicePersistence;
	}

	public PollsChoiceFinder getPollsChoiceFinder() {
		return pollsChoiceFinder;
	}

	public void setPollsChoiceFinder(PollsChoiceFinder pollsChoiceFinder) {
		this.pollsChoiceFinder = pollsChoiceFinder;
	}

	public PollsQuestionLocalService getPollsQuestionLocalService() {
		return pollsQuestionLocalService;
	}

	public void setPollsQuestionLocalService(
		PollsQuestionLocalService pollsQuestionLocalService) {
		this.pollsQuestionLocalService = pollsQuestionLocalService;
	}

	public PollsQuestionService getPollsQuestionService() {
		return pollsQuestionService;
	}

	public void setPollsQuestionService(
		PollsQuestionService pollsQuestionService) {
		this.pollsQuestionService = pollsQuestionService;
	}

	public PollsQuestionPersistence getPollsQuestionPersistence() {
		return pollsQuestionPersistence;
	}

	public void setPollsQuestionPersistence(
		PollsQuestionPersistence pollsQuestionPersistence) {
		this.pollsQuestionPersistence = pollsQuestionPersistence;
	}

	public PollsVotePersistence getPollsVotePersistence() {
		return pollsVotePersistence;
	}

	public void setPollsVotePersistence(
		PollsVotePersistence pollsVotePersistence) {
		this.pollsVotePersistence = pollsVotePersistence;
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

	public void afterPropertiesSet() {
		if (pollsChoiceLocalService == null) {
			pollsChoiceLocalService = PollsChoiceLocalServiceFactory.getImpl();
		}

		if (pollsChoicePersistence == null) {
			pollsChoicePersistence = PollsChoiceUtil.getPersistence();
		}

		if (pollsChoiceFinder == null) {
			pollsChoiceFinder = PollsChoiceFinderUtil.getFinder();
		}

		if (pollsQuestionLocalService == null) {
			pollsQuestionLocalService = PollsQuestionLocalServiceFactory.getImpl();
		}

		if (pollsQuestionService == null) {
			pollsQuestionService = PollsQuestionServiceFactory.getImpl();
		}

		if (pollsQuestionPersistence == null) {
			pollsQuestionPersistence = PollsQuestionUtil.getPersistence();
		}

		if (pollsVotePersistence == null) {
			pollsVotePersistence = PollsVoteUtil.getPersistence();
		}

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
		}
	}

	protected PollsChoiceLocalService pollsChoiceLocalService;
	protected PollsChoicePersistence pollsChoicePersistence;
	protected PollsChoiceFinder pollsChoiceFinder;
	protected PollsQuestionLocalService pollsQuestionLocalService;
	protected PollsQuestionService pollsQuestionService;
	protected PollsQuestionPersistence pollsQuestionPersistence;
	protected PollsVotePersistence pollsVotePersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}