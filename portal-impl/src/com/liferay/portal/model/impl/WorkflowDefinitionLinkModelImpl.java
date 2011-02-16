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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowDefinitionLinkModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the WorkflowDefinitionLink service. Represents a row in the &quot;WorkflowDefinitionLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.WorkflowDefinitionLinkModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WorkflowDefinitionLinkImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLinkImpl
 * @see com.liferay.portal.model.WorkflowDefinitionLink
 * @see com.liferay.portal.model.WorkflowDefinitionLinkModel
 * @generated
 */
public class WorkflowDefinitionLinkModelImpl extends BaseModelImpl<WorkflowDefinitionLink>
	implements WorkflowDefinitionLinkModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a workflow definition link model instance should use the {@link com.liferay.portal.model.WorkflowDefinitionLink} interface instead.
	 */
	public static final String TABLE_NAME = "WorkflowDefinitionLink";
	public static final Object[][] TABLE_COLUMNS = {
			{ "workflowDefinitionLinkId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "workflowDefinitionName", Types.VARCHAR },
			{ "workflowDefinitionVersion", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table WorkflowDefinitionLink (workflowDefinitionLinkId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,workflowDefinitionName VARCHAR(75) null,workflowDefinitionVersion INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table WorkflowDefinitionLink";
	public static final String ORDER_BY_JPQL = " ORDER BY workflowDefinitionLink.workflowDefinitionName ASC";
	public static final String ORDER_BY_SQL = " ORDER BY WorkflowDefinitionLink.workflowDefinitionName ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.WorkflowDefinitionLink"));

	public WorkflowDefinitionLinkModelImpl() {
	}

	public long getPrimaryKey() {
		return _workflowDefinitionLinkId;
	}

	public void setPrimaryKey(long pk) {
		setWorkflowDefinitionLinkId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_workflowDefinitionLinkId);
	}

	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLinkId;
	}

	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		_workflowDefinitionLinkId = workflowDefinitionLinkId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public String getWorkflowDefinitionName() {
		if (_workflowDefinitionName == null) {
			return StringPool.BLANK;
		}
		else {
			return _workflowDefinitionName;
		}
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_workflowDefinitionName = workflowDefinitionName;
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public WorkflowDefinitionLink toEscapedModel() {
		if (isEscapedModel()) {
			return (WorkflowDefinitionLink)this;
		}
		else {
			return (WorkflowDefinitionLink)Proxy.newProxyInstance(WorkflowDefinitionLink.class.getClassLoader(),
				new Class[] { WorkflowDefinitionLink.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					WorkflowDefinitionLink.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		WorkflowDefinitionLinkModelImpl clone = new WorkflowDefinitionLinkImpl();
		WorkflowDefinitionLinkModelImpl cloneModel = (WorkflowDefinitionLinkModelImpl)clone;

		clone._workflowDefinitionLinkId = _workflowDefinitionLinkId;

		clone._originalGroupId = clone._groupId = _groupId;

		cloneModel._originalGroupId = cloneModel._groupId;
		cloneModel._setOriginalGroupId = false;
		clone._originalCompanyId = clone._companyId = _companyId;

		cloneModel._originalCompanyId = cloneModel._companyId;
		cloneModel._setOriginalCompanyId = false;

		clone._userId = _userId;

		clone._userName = _userName;

		clone._createDate = _createDate;

		clone._modifiedDate = _modifiedDate;

		clone._originalClassNameId = clone._classNameId = _classNameId;

		cloneModel._originalClassNameId = cloneModel._classNameId;
		cloneModel._setOriginalClassNameId = false;

		clone._workflowDefinitionName = _workflowDefinitionName;

		clone._workflowDefinitionVersion = _workflowDefinitionVersion;

		return clone;
	}

	public int compareTo(WorkflowDefinitionLink workflowDefinitionLink) {
		int value = 0;

		value = getWorkflowDefinitionName()
					.compareTo(workflowDefinitionLink.getWorkflowDefinitionName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		WorkflowDefinitionLink workflowDefinitionLink = null;

		try {
			workflowDefinitionLink = (WorkflowDefinitionLink)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = workflowDefinitionLink.getPrimaryKey();

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
		StringBundler sb = new StringBundler(21);

		sb.append("{workflowDefinitionLinkId=");
		sb.append(getWorkflowDefinitionLinkId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", workflowDefinitionName=");
		sb.append(getWorkflowDefinitionName());
		sb.append(", workflowDefinitionVersion=");
		sb.append(getWorkflowDefinitionVersion());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.WorkflowDefinitionLink");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>workflowDefinitionLinkId</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionLinkId());
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
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>workflowDefinitionName</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>workflowDefinitionVersion</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionVersion());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _workflowDefinitionLinkId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
	private transient ExpandoBridge _expandoBridge;
}