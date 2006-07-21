/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="MBThreadModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBThreadModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.messageboards.model.MBThread"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBThread"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_THREADID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBThread.threadId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CATEGORYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBThread.categoryId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TOPICID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBThread.topicId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ROOTMESSAGEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.messageboards.model.MBThread.rootMessageId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBThreadModel"));

	public MBThreadModel() {
	}

	public String getPrimaryKey() {
		return _threadId;
	}

	public void setPrimaryKey(String pk) {
		setThreadId(pk);
	}

	public String getThreadId() {
		return GetterUtil.getString(_threadId);
	}

	public void setThreadId(String threadId) {
		if (((threadId == null) && (_threadId != null)) ||
				((threadId != null) && (_threadId == null)) ||
				((threadId != null) && (_threadId != null) &&
				!threadId.equals(_threadId))) {
			if (!XSS_ALLOW_THREADID) {
				threadId = XSSUtil.strip(threadId);
			}

			_threadId = threadId;
		}
	}

	public String getCategoryId() {
		return GetterUtil.getString(_categoryId);
	}

	public void setCategoryId(String categoryId) {
		if (((categoryId == null) && (_categoryId != null)) ||
				((categoryId != null) && (_categoryId == null)) ||
				((categoryId != null) && (_categoryId != null) &&
				!categoryId.equals(_categoryId))) {
			if (!XSS_ALLOW_CATEGORYID) {
				categoryId = XSSUtil.strip(categoryId);
			}

			_categoryId = categoryId;
		}
	}

	public String getTopicId() {
		return GetterUtil.getString(_topicId);
	}

	public void setTopicId(String topicId) {
		if (((topicId == null) && (_topicId != null)) ||
				((topicId != null) && (_topicId == null)) ||
				((topicId != null) && (_topicId != null) &&
				!topicId.equals(_topicId))) {
			if (!XSS_ALLOW_TOPICID) {
				topicId = XSSUtil.strip(topicId);
			}

			_topicId = topicId;
		}
	}

	public String getRootMessageId() {
		return GetterUtil.getString(_rootMessageId);
	}

	public void setRootMessageId(String rootMessageId) {
		if (((rootMessageId == null) && (_rootMessageId != null)) ||
				((rootMessageId != null) && (_rootMessageId == null)) ||
				((rootMessageId != null) && (_rootMessageId != null) &&
				!rootMessageId.equals(_rootMessageId))) {
			if (!XSS_ALLOW_ROOTMESSAGEID) {
				rootMessageId = XSSUtil.strip(rootMessageId);
			}

			_rootMessageId = rootMessageId;
		}
	}

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		if (messageCount != _messageCount) {
			_messageCount = messageCount;
		}
	}

	public int getViewCount() {
		return _viewCount;
	}

	public void setViewCount(int viewCount) {
		if (viewCount != _viewCount) {
			_viewCount = viewCount;
		}
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		if (((lastPostDate == null) && (_lastPostDate != null)) ||
				((lastPostDate != null) && (_lastPostDate == null)) ||
				((lastPostDate != null) && (_lastPostDate != null) &&
				!lastPostDate.equals(_lastPostDate))) {
			_lastPostDate = lastPostDate;
		}
	}

	public Object clone() {
		MBThread clone = new MBThread();
		clone.setThreadId(getThreadId());
		clone.setCategoryId(getCategoryId());
		clone.setTopicId(getTopicId());
		clone.setRootMessageId(getRootMessageId());
		clone.setMessageCount(getMessageCount());
		clone.setViewCount(getViewCount());
		clone.setLastPostDate(getLastPostDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBThread mbThread = (MBThread)obj;
		int value = 0;
		value = DateUtil.compareTo(getLastPostDate(), mbThread.getLastPostDate());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBThread mbThread = null;

		try {
			mbThread = (MBThread)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = mbThread.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _threadId;
	private String _categoryId;
	private String _topicId;
	private String _rootMessageId;
	private int _messageCount;
	private int _viewCount;
	private Date _lastPostDate;
}