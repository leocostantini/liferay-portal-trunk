/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.service.base.MBThreadLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.util.Indexer;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="MBThreadLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBThreadLocalServiceImpl extends MBThreadLocalServiceBaseImpl {

	public void deleteThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		deleteThread(thread);
	}

	public void deleteThread(MBThread thread)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			thread.getCategoryId());
		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			thread.getRootMessageId());

		// Indexer

		try {
			Indexer.deleteMessages(
				rootMessage.getCompanyId(), thread.getThreadId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + thread.getThreadId(), se);
		}

		// Attachments

		long companyId = rootMessage.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = thread.getAttachmentsDir();

		try {
			dlService.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		// Messages

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			thread.getThreadId());

		for (MBMessage message : messages) {

			// Social

			socialActivityLocalService.deleteActivities(
				MBMessage.class.getName(), message.getMessageId());

			// Ratings

			ratingsStatsLocalService.deleteStats(
				MBMessage.class.getName(), message.getMessageId());

			// Asset

			assetEntryLocalService.deleteEntry(
				MBMessage.class.getName(), message.getMessageId());

			// Statistics

			if (!category.isDiscussion()) {
				mbStatsUserLocalService.updateStatsUser(
					message.getGroupId(), message.getUserId());
			}

			// Message flags

			mbMessageFlagPersistence.removeByMessageId(message.getMessageId());

			// Resources

			if (!message.isDiscussion()) {
				resourceLocalService.deleteResource(
					message.getCompanyId(), MBMessage.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, message.getMessageId());
			}

			// Message

			mbMessagePersistence.remove(message);
		}

		// Category

		category.setThreadCount(category.getThreadCount() - 1);
		category.setMessageCount(category.getMessageCount() - messages.size());

		mbCategoryPersistence.update(category, false);

		// Thread

		mbThreadPersistence.remove(thread);
	}

	public void deleteThreads(long groupId, long categoryId)
		throws PortalException, SystemException {

		List<MBThread> threads = mbThreadPersistence.findByG_C(
			groupId, categoryId);

		for (MBThread thread : threads) {
			deleteThread(thread);
		}
	}

	public int getCategoryThreadsCount(long groupId, long categoryId, int status)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return mbThreadPersistence.countByG_C(groupId, categoryId);
		}
		else {
			return mbThreadPersistence.countByG_C_S(groupId, categoryId, status);
		}
	}

	public List<MBThread> getGroupThreads(long groupId, int status, int start, int end)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return mbThreadPersistence.findByGroupId(groupId, start, end);
		}
		else {
			return mbThreadPersistence.findByG_S(groupId, status, start, end);
		}
	}

	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous, int start, int end)
		throws PortalException, SystemException {

		if (userId <= 0) {
			if (status == StatusConstants.ANY) {
				return mbThreadPersistence.findByGroupId(groupId, start, end);
			}
			else {
				return mbThreadPersistence.findByG_S(groupId, status, start, end);
			}
		}
		else {
			if (subscribed) {
				return mbThreadFinder.findByS_G_U_S(groupId, userId, status, start, end);
			}
			else {
				List<Long> threadIds = null;

				if (includeAnonymous) {
					threadIds = mbMessageFinder.findByG_U_S(
						groupId, userId, status, start, end);
				}
				else {
					threadIds = mbMessageFinder.findByG_U_S_A(
						groupId, userId, status, false, start, end);
				}

				List<MBThread> threads = new ArrayList<MBThread>(
					threadIds.size());

				for (long threadId : threadIds) {
					MBThread thread = mbThreadPersistence.findByPrimaryKey(
						threadId);

					threads.add(thread);
				}

				return threads;
			}
		}
	}

	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, 
			boolean subscribed, int start, int end)
		throws PortalException, SystemException {

		return getGroupThreads(
				groupId, userId, status, subscribed, true, start, end);
	}

	public List<MBThread> getGroupThreads(
			long groupId, long userId, int status, int start, int end)
		throws PortalException, SystemException {

		return getGroupThreads(groupId, userId, status, false, start, end);
	}

	public int getGroupThreadsCount(long groupId, int status) 
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return mbThreadPersistence.countByGroupId(groupId);
		}
		else {
			return mbThreadPersistence.countByG_S(groupId, status);
		}
	}

	public int getGroupThreadsCount(long groupId, long userId, int status)
		throws SystemException {

		return getGroupThreadsCount(groupId, userId, status, false);
	}

	public int getGroupThreadsCount(
			long groupId, long userId, int status, boolean subscribed)
		throws SystemException {

		return getGroupThreadsCount(groupId, userId, status, subscribed, true);
	}

	public int getGroupThreadsCount(
			long groupId, long userId, int status, boolean subscribed,
			boolean includeAnonymous)
		throws SystemException {

		if (userId <= 0) {
			return mbThreadPersistence.countByGroupId(groupId);
		}
		else {
			if (subscribed) {
				return mbThreadFinder.countByS_G_U_S(groupId, userId, status);
			}
			else {
				if (includeAnonymous) {
					return mbMessageFinder.countByG_U_S(
							groupId, userId, status);
				}
				else {
					return mbMessageFinder.countByG_U_S_A(
							groupId, userId, status, false);
				}
			}
		}
	}

	public MBThread getThread(long threadId)
		throws PortalException, SystemException {

		return mbThreadPersistence.findByPrimaryKey(threadId);
	}

	public List<MBThread> getThreads(
			long groupId, long categoryId, int status, int start, int end)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return mbThreadPersistence.findByG_C(
					groupId, categoryId, start, end);
		}
		else {
			return mbThreadPersistence.findByG_C_S(
					groupId, categoryId, status, start, end);
		}

	}

	public int getThreadsCount(long groupId, long categoryId, int status)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return mbThreadPersistence.countByG_C(groupId, categoryId);
		}
		else {
			return mbThreadPersistence.countByG_C_S(groupId, categoryId, status);
		}
	}

	public MBThread moveThread(long groupId, long categoryId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			threadId);

		long oldCategoryId = thread.getCategoryId();

		MBCategory oldCategory = null;

		if (oldCategoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			oldCategory = mbCategoryPersistence.findByPrimaryKey(oldCategoryId);
		}

		MBCategory category = null;

		if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			category = mbCategoryPersistence.findByPrimaryKey(categoryId);
		}

		// Messages

		List<MBMessage> messages = mbMessagePersistence.findByG_C_T(
			groupId, oldCategoryId, thread.getThreadId());

		for (MBMessage message : messages) {
			message.setCategoryId(categoryId);

			mbMessagePersistence.update(message, false);

			// Indexer

			try {
				if ((category != null) && !category.isDiscussion()) {
					Indexer.updateMessage(
						message.getCompanyId(), message.getGroupId(),
						message.getUserId(), message.getUserName(),
						categoryId, message.getThreadId(),
						message.getMessageId(), message.getSubject(),
						message.getBody(), message.isAnonymous(),
						message.getModifiedDate(), message.getAssetTagNames(),
						message.getExpandoBridge());
				}
			}
			catch (SearchException se) {
				_log.error("Indexing " + message.getMessageId(), se);
			}
		}

		// Thread

		thread.setCategoryId(categoryId);

		mbThreadPersistence.update(thread, false);

		// Category

		if (oldCategory != null) {
			oldCategory.setThreadCount(oldCategory.getThreadCount() - 1);
			oldCategory.setMessageCount(
				oldCategory.getMessageCount() - messages.size());

			mbCategoryPersistence.update(oldCategory, false);
		}

		if (category != null) {
			category.setThreadCount(category.getThreadCount() + 1);
			category.setMessageCount(
				category.getMessageCount() + messages.size());

			mbCategoryPersistence.update(category, false);
		}

		return thread;
	}

	public MBThread splitThread(long messageId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		MBCategory category = null;
		long oldThreadId = message.getThreadId();
		String oldAttachmentsDir = message.getAttachmentsDir();

		if (message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			category = message.getCategory();
		}

		// Message flags

		mbMessageFlagLocalService.deleteThreadFlags(oldThreadId);

		// Create new thread

		MBThread thread = addThread(message.getCategoryId(), message);

		// Update message

		message.setThreadId(thread.getThreadId());
		message.setParentMessageId(0);
		message.setAttachmentsDir(null);

		mbMessagePersistence.update(message, false);

		// Attachments

		moveAttachmentsFromOldThread(message, oldAttachmentsDir);

		// Indexer

		try {
			if ((category != null) && !category.isDiscussion()) {
				Indexer.updateMessage(
					message.getCompanyId(), message.getGroupId(),
					message.getUserId(), message.getUserName(),
					category.getCategoryId(), message.getThreadId(),
					message.getMessageId(), message.getSubject(),
					message.getBody(), message.isAnonymous(),
					message.getModifiedDate(), message.getAssetTagNames(),
					message.getExpandoBridge());
			}
		}
		catch (SearchException se) {
			_log.error("Indexing " + message.getMessageId(), se);
		}

		// Update children

		int messagesMoved = 1;

		messagesMoved += moveChildrenMessages(
			message, category, oldThreadId);

		// Update new thread

		thread.setMessageCount(messagesMoved);

		mbThreadPersistence.update(thread, false);

		// Update old thread

		MBThread oldThread = mbThreadPersistence.findByPrimaryKey(oldThreadId);

		oldThread.setMessageCount(oldThread.getMessageCount() - messagesMoved);

		mbThreadPersistence.update(oldThread, false);

		// Category

		if (category != null) {
			category.setThreadCount(category.getThreadCount() + 1);

			mbCategoryPersistence.update(category, false);
		}

		return thread;
	}

	public MBThread updateThread(long threadId, int viewCount)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		thread.setViewCount(viewCount);

		mbThreadPersistence.update(thread, false);

		return thread;
	}

	protected MBThread addThread(long categoryId, MBMessage message)
		throws SystemException {

		long threadId = counterLocalService.increment();

		MBThread thread = mbThreadPersistence.create(threadId);

		thread.setGroupId(message.getGroupId());
		thread.setCategoryId(categoryId);
		thread.setRootMessageId(message.getMessageId());

		thread.setMessageCount(thread.getMessageCount() + 1);

		if (message.isAnonymous()) {
			thread.setLastPostByUserId(0);
		}
		else {
			thread.setLastPostByUserId(message.getUserId());
		}

		thread.setLastPostDate(message.getCreateDate());

		if (message.getPriority() != MBThreadImpl.PRIORITY_NOT_GIVEN) {
			thread.setPriority(message.getPriority());
		}

		mbThreadPersistence.update(thread, false);

		return thread;
	}

	protected void moveAttachmentsFromOldThread(
			MBMessage message, String oldAttachmentsDir)
		throws PortalException, SystemException {

		if (!message.getAttachments()) {
			return;
		}

		long companyId = message.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long groupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
		long repositoryId = CompanyConstants.SYSTEM;
		String newAttachmentsDir = message.getAttachmentsDir();

		try {
			dlService.addDirectory(companyId, repositoryId, newAttachmentsDir);
		}
		catch (DuplicateDirectoryException dde) {
		}

		String[] fileNames = dlService.getFileNames(
			companyId, repositoryId, oldAttachmentsDir);

		for (String fileName : fileNames) {
			String name = StringUtil.extractLast(fileName, StringPool.SLASH);
			byte[] fileBytes = dlService.getFile(
				companyId, repositoryId, fileName);

			dlService.addFile(
				companyId, portletId, groupId, repositoryId,
				newAttachmentsDir + "/" + name, 0, StringPool.BLANK,
				message.getModifiedDate(), new ServiceContext(), fileBytes);

			dlService.deleteFile(companyId, portletId, repositoryId, fileName);
		}

		try {
			dlService.deleteDirectory(
				companyId, portletId, repositoryId, oldAttachmentsDir);
		}
		catch (NoSuchDirectoryException nsde) {
		}
	}

	protected int moveChildrenMessages(
			MBMessage parentMessage, MBCategory category, long oldThreadId)
		throws SystemException, PortalException {

		int messagesMoved = 0;

		List<MBMessage> messages = mbMessagePersistence.findByT_P(
			oldThreadId, parentMessage.getMessageId());

		for (MBMessage message : messages) {
			String oldAttachmentsDir = message.getAttachmentsDir();

			message.setCategoryId(parentMessage.getCategoryId());
			message.setThreadId(parentMessage.getThreadId());
			message.setAttachmentsDir(null);

			mbMessagePersistence.update(message, false);

			moveAttachmentsFromOldThread(message, oldAttachmentsDir);

			try {
				if ((category != null) && !category.isDiscussion()) {
					Indexer.updateMessage(
						message.getCompanyId(), message.getGroupId(),
						message.getUserId(), message.getUserName(),
						category.getCategoryId(), message.getThreadId(),
						message.getMessageId(), message.getSubject(),
						message.getBody(), message.isAnonymous(),
						message.getModifiedDate(), message.getAssetTagNames(),
						message.getExpandoBridge());
				}
			}
			catch (SearchException se) {
				_log.error("Indexing " + message.getMessageId(), se);
			}

			messagesMoved++;

			messagesMoved += moveChildrenMessages(
				message, category, oldThreadId);
		}

		return messagesMoved;
	}

	private static Log _log =
		LogFactoryUtil.getLog(MBThreadLocalServiceImpl.class);

}