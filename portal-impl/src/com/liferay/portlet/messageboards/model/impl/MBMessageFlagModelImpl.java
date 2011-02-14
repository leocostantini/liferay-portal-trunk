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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBMessageFlagModel;
import com.liferay.portlet.messageboards.model.MBMessageFlagSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the MBMessageFlag service. Represents a row in the &quot;MBMessageFlag&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.messageboards.model.MBMessageFlagModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MBMessageFlagImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageFlagImpl
 * @see com.liferay.portlet.messageboards.model.MBMessageFlag
 * @see com.liferay.portlet.messageboards.model.MBMessageFlagModel
 * @generated
 */
public class MBMessageFlagModelImpl extends BaseModelImpl<MBMessageFlag>
	implements MBMessageFlagModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message boards message flag model instance should use the {@link com.liferay.portlet.messageboards.model.MBMessageFlag} interface instead.
	 */
	public static final String TABLE_NAME = "MBMessageFlag";
	public static final Object[][] TABLE_COLUMNS = {
			{ "messageFlagId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "threadId", Types.BIGINT },
			{ "messageId", Types.BIGINT },
			{ "flag", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table MBMessageFlag (messageFlagId LONG not null primary key,userId LONG,modifiedDate DATE null,threadId LONG,messageId LONG,flag INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table MBMessageFlag";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBMessageFlag"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBMessageFlag"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static MBMessageFlag toModel(MBMessageFlagSoap soapModel) {
		MBMessageFlag model = new MBMessageFlagImpl();

		model.setMessageFlagId(soapModel.getMessageFlagId());
		model.setUserId(soapModel.getUserId());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setThreadId(soapModel.getThreadId());
		model.setMessageId(soapModel.getMessageId());
		model.setFlag(soapModel.getFlag());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<MBMessageFlag> toModels(MBMessageFlagSoap[] soapModels) {
		List<MBMessageFlag> models = new ArrayList<MBMessageFlag>(soapModels.length);

		for (MBMessageFlagSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBMessageFlag"));

	public MBMessageFlagModelImpl() {
	}

	public long getPrimaryKey() {
		return _messageFlagId;
	}

	public void setPrimaryKey(long pk) {
		setMessageFlagId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_messageFlagId);
	}

	public long getMessageFlagId() {
		return _messageFlagId;
	}

	public void setMessageFlagId(long messageFlagId) {
		_messageFlagId = messageFlagId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	public long getMessageId() {
		return _messageId;
	}

	public void setMessageId(long messageId) {
		if (!_setOriginalMessageId) {
			_setOriginalMessageId = true;

			_originalMessageId = _messageId;
		}

		_messageId = messageId;
	}

	public long getOriginalMessageId() {
		return _originalMessageId;
	}

	public int getFlag() {
		return _flag;
	}

	public void setFlag(int flag) {
		if (!_setOriginalFlag) {
			_setOriginalFlag = true;

			_originalFlag = _flag;
		}

		_flag = flag;
	}

	public int getOriginalFlag() {
		return _originalFlag;
	}

	public MBMessageFlag toEscapedModel() {
		if (isEscapedModel()) {
			return (MBMessageFlag)this;
		}
		else {
			return (MBMessageFlag)Proxy.newProxyInstance(MBMessageFlag.class.getClassLoader(),
				new Class[] { MBMessageFlag.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					MBMessageFlag.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MBMessageFlagImpl clone = new MBMessageFlagImpl();

		clone.setMessageFlagId(getMessageFlagId());
		clone.setUserId(getUserId());
		clone.setModifiedDate(getModifiedDate());
		clone.setThreadId(getThreadId());
		clone.setMessageId(getMessageId());
		clone.setFlag(getFlag());

		return clone;
	}

	public int compareTo(MBMessageFlag mbMessageFlag) {
		long pk = mbMessageFlag.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBMessageFlag mbMessageFlag = null;

		try {
			mbMessageFlag = (MBMessageFlag)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = mbMessageFlag.getPrimaryKey();

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

	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{messageFlagId=");
		sb.append(getMessageFlagId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", threadId=");
		sb.append(getThreadId());
		sb.append(", messageId=");
		sb.append(getMessageId());
		sb.append(", flag=");
		sb.append(getFlag());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBMessageFlag");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>messageFlagId</column-name><column-value><![CDATA[");
		sb.append(getMessageFlagId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>threadId</column-name><column-value><![CDATA[");
		sb.append(getThreadId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageId</column-name><column-value><![CDATA[");
		sb.append(getMessageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>flag</column-name><column-value><![CDATA[");
		sb.append(getFlag());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _messageFlagId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _modifiedDate;
	private long _threadId;
	private long _messageId;
	private long _originalMessageId;
	private boolean _setOriginalMessageId;
	private int _flag;
	private int _originalFlag;
	private boolean _setOriginalFlag;
	private transient ExpandoBridge _expandoBridge;
}