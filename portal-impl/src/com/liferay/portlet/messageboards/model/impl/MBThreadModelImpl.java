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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadModel;
import com.liferay.portlet.messageboards.model.MBThreadSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the MBThread service. Represents a row in the &quot;MBThread&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.messageboards.model.MBThreadModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MBThreadImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadImpl
 * @see com.liferay.portlet.messageboards.model.MBThread
 * @see com.liferay.portlet.messageboards.model.MBThreadModel
 * @generated
 */
public class MBThreadModelImpl extends BaseModelImpl<MBThread>
	implements MBThreadModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message boards thread model instance should use the {@link com.liferay.portlet.messageboards.model.MBThread} interface instead.
	 */
	public static final String TABLE_NAME = "MBThread";
	public static final Object[][] TABLE_COLUMNS = {
			{ "threadId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "categoryId", Types.BIGINT },
			{ "rootMessageId", Types.BIGINT },
			{ "rootMessageUserId", Types.BIGINT },
			{ "messageCount", Types.INTEGER },
			{ "viewCount", Types.INTEGER },
			{ "lastPostByUserId", Types.BIGINT },
			{ "lastPostDate", Types.TIMESTAMP },
			{ "priority", Types.DOUBLE },
			{ "status", Types.INTEGER },
			{ "statusByUserId", Types.BIGINT },
			{ "statusByUserName", Types.VARCHAR },
			{ "statusDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table MBThread (threadId LONG not null primary key,groupId LONG,companyId LONG,categoryId LONG,rootMessageId LONG,rootMessageUserId LONG,messageCount INTEGER,viewCount INTEGER,lastPostByUserId LONG,lastPostDate DATE null,priority DOUBLE,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table MBThread";
	public static final String ORDER_BY_JPQL = " ORDER BY mbThread.priority DESC, mbThread.lastPostDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MBThread.priority DESC, MBThread.lastPostDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBThread"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBThread"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static MBThread toModel(MBThreadSoap soapModel) {
		MBThread model = new MBThreadImpl();

		model.setThreadId(soapModel.getThreadId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setCategoryId(soapModel.getCategoryId());
		model.setRootMessageId(soapModel.getRootMessageId());
		model.setRootMessageUserId(soapModel.getRootMessageUserId());
		model.setMessageCount(soapModel.getMessageCount());
		model.setViewCount(soapModel.getViewCount());
		model.setLastPostByUserId(soapModel.getLastPostByUserId());
		model.setLastPostDate(soapModel.getLastPostDate());
		model.setPriority(soapModel.getPriority());
		model.setStatus(soapModel.getStatus());
		model.setStatusByUserId(soapModel.getStatusByUserId());
		model.setStatusByUserName(soapModel.getStatusByUserName());
		model.setStatusDate(soapModel.getStatusDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<MBThread> toModels(MBThreadSoap[] soapModels) {
		List<MBThread> models = new ArrayList<MBThread>(soapModels.length);

		for (MBThreadSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return MBThread.class;
	}

	public String getModelClassName() {
		return MBThread.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBThread"));

	public MBThreadModelImpl() {
	}

	public long getPrimaryKey() {
		return _threadId;
	}

	public void setPrimaryKey(long pk) {
		setThreadId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_threadId);
	}

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public long getRootMessageId() {
		return _rootMessageId;
	}

	public void setRootMessageId(long rootMessageId) {
		_rootMessageId = rootMessageId;
	}

	public long getRootMessageUserId() {
		return _rootMessageUserId;
	}

	public void setRootMessageUserId(long rootMessageUserId) {
		_rootMessageUserId = rootMessageUserId;
	}

	public String getRootMessageUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getRootMessageUserId(), "uuid",
			_rootMessageUserUuid);
	}

	public void setRootMessageUserUuid(String rootMessageUserUuid) {
		_rootMessageUserUuid = rootMessageUserUuid;
	}

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		_messageCount = messageCount;
	}

	public int getViewCount() {
		return _viewCount;
	}

	public void setViewCount(int viewCount) {
		_viewCount = viewCount;
	}

	public long getLastPostByUserId() {
		return _lastPostByUserId;
	}

	public void setLastPostByUserId(long lastPostByUserId) {
		_lastPostByUserId = lastPostByUserId;
	}

	public String getLastPostByUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getLastPostByUserId(), "uuid",
			_lastPostByUserUuid);
	}

	public void setLastPostByUserUuid(String lastPostByUserUuid) {
		_lastPostByUserUuid = lastPostByUserUuid;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatusByUserId(), "uuid",
			_statusByUserUuid);
	}

	public void setStatusByUserUuid(String statusByUserUuid) {
		_statusByUserUuid = statusByUserUuid;
	}

	public String getStatusByUserName() {
		if (_statusByUserName == null) {
			return StringPool.BLANK;
		}
		else {
			return _statusByUserName;
		}
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	/**
	 * @deprecated {@link #isApproved}
	 */
	public boolean getApproved() {
		return isApproved();
	}

	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDraft() {
		if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPending() {
		if (getStatus() == WorkflowConstants.STATUS_PENDING) {
			return true;
		}
		else {
			return false;
		}
	}

	public MBThread toEscapedModel() {
		if (isEscapedModel()) {
			return (MBThread)this;
		}
		else {
			return (MBThread)Proxy.newProxyInstance(MBThread.class.getClassLoader(),
				new Class[] { MBThread.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					MBThread.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MBThreadImpl mbThreadImpl = new MBThreadImpl();

		mbThreadImpl.setThreadId(getThreadId());
		mbThreadImpl.setGroupId(getGroupId());
		mbThreadImpl.setCompanyId(getCompanyId());
		mbThreadImpl.setCategoryId(getCategoryId());
		mbThreadImpl.setRootMessageId(getRootMessageId());
		mbThreadImpl.setRootMessageUserId(getRootMessageUserId());
		mbThreadImpl.setMessageCount(getMessageCount());
		mbThreadImpl.setViewCount(getViewCount());
		mbThreadImpl.setLastPostByUserId(getLastPostByUserId());
		mbThreadImpl.setLastPostDate(getLastPostDate());
		mbThreadImpl.setPriority(getPriority());
		mbThreadImpl.setStatus(getStatus());
		mbThreadImpl.setStatusByUserId(getStatusByUserId());
		mbThreadImpl.setStatusByUserName(getStatusByUserName());
		mbThreadImpl.setStatusDate(getStatusDate());

		mbThreadImpl.resetOriginalValues();

		return mbThreadImpl;
	}

	public int compareTo(MBThread mbThread) {
		int value = 0;

		if (getPriority() < mbThread.getPriority()) {
			value = -1;
		}
		else if (getPriority() > mbThread.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

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

		long pk = mbThread.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public void resetOriginalValues() {
	}

	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{threadId=");
		sb.append(getThreadId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", categoryId=");
		sb.append(getCategoryId());
		sb.append(", rootMessageId=");
		sb.append(getRootMessageId());
		sb.append(", rootMessageUserId=");
		sb.append(getRootMessageUserId());
		sb.append(", messageCount=");
		sb.append(getMessageCount());
		sb.append(", viewCount=");
		sb.append(getViewCount());
		sb.append(", lastPostByUserId=");
		sb.append(getLastPostByUserId());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusByUserId=");
		sb.append(getStatusByUserId());
		sb.append(", statusByUserName=");
		sb.append(getStatusByUserName());
		sb.append(", statusDate=");
		sb.append(getStatusDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(49);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBThread");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>threadId</column-name><column-value><![CDATA[");
		sb.append(getThreadId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>categoryId</column-name><column-value><![CDATA[");
		sb.append(getCategoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rootMessageId</column-name><column-value><![CDATA[");
		sb.append(getRootMessageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rootMessageUserId</column-name><column-value><![CDATA[");
		sb.append(getRootMessageUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageCount</column-name><column-value><![CDATA[");
		sb.append(getMessageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>viewCount</column-name><column-value><![CDATA[");
		sb.append(getViewCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostByUserId</column-name><column-value><![CDATA[");
		sb.append(getLastPostByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserId</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusByUserName</column-name><column-value><![CDATA[");
		sb.append(getStatusByUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusDate</column-name><column-value><![CDATA[");
		sb.append(getStatusDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _threadId;
	private long _groupId;
	private long _companyId;
	private long _categoryId;
	private long _rootMessageId;
	private long _rootMessageUserId;
	private String _rootMessageUserUuid;
	private int _messageCount;
	private int _viewCount;
	private long _lastPostByUserId;
	private String _lastPostByUserUuid;
	private Date _lastPostDate;
	private double _priority;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserUuid;
	private String _statusByUserName;
	private Date _statusDate;
	private transient ExpandoBridge _expandoBridge;
}