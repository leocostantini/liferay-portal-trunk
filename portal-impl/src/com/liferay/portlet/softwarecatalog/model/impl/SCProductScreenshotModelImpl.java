/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SCProductScreenshot table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshotImpl
 * @see       com.liferay.portlet.softwarecatalog.model.SCProductScreenshot
 * @see       com.liferay.portlet.softwarecatalog.model.SCProductScreenshotModel
 * @generated
 */
public class SCProductScreenshotModelImpl extends BaseModelImpl<SCProductScreenshot> {
	public static final String TABLE_NAME = "SCProductScreenshot";
	public static final Object[][] TABLE_COLUMNS = {
			{ "productScreenshotId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "productEntryId", new Integer(Types.BIGINT) },
			{ "thumbnailId", new Integer(Types.BIGINT) },
			{ "fullImageId", new Integer(Types.BIGINT) },
			{ "priority", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table SCProductScreenshot (productScreenshotId LONG not null primary key,companyId LONG,groupId LONG,productEntryId LONG,thumbnailId LONG,fullImageId LONG,priority INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SCProductScreenshot";
	public static final String ORDER_BY_JPQL = " ORDER BY scProductScreenshot.productEntryId ASC, scProductScreenshot.priority ASC";
	public static final String ORDER_BY_SQL = " ORDER BY SCProductScreenshot.productEntryId ASC, SCProductScreenshot.priority ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot"));

	public SCProductScreenshotModelImpl() {
	}

	public long getPrimaryKey() {
		return _productScreenshotId;
	}

	public void setPrimaryKey(long pk) {
		setProductScreenshotId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_productScreenshotId);
	}

	public long getProductScreenshotId() {
		return _productScreenshotId;
	}

	public void setProductScreenshotId(long productScreenshotId) {
		_productScreenshotId = productScreenshotId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getProductEntryId() {
		return _productEntryId;
	}

	public void setProductEntryId(long productEntryId) {
		_productEntryId = productEntryId;

		if (!_setOriginalProductEntryId) {
			_setOriginalProductEntryId = true;

			_originalProductEntryId = productEntryId;
		}
	}

	public long getOriginalProductEntryId() {
		return _originalProductEntryId;
	}

	public long getThumbnailId() {
		return _thumbnailId;
	}

	public void setThumbnailId(long thumbnailId) {
		_thumbnailId = thumbnailId;

		if (!_setOriginalThumbnailId) {
			_setOriginalThumbnailId = true;

			_originalThumbnailId = thumbnailId;
		}
	}

	public long getOriginalThumbnailId() {
		return _originalThumbnailId;
	}

	public long getFullImageId() {
		return _fullImageId;
	}

	public void setFullImageId(long fullImageId) {
		_fullImageId = fullImageId;

		if (!_setOriginalFullImageId) {
			_setOriginalFullImageId = true;

			_originalFullImageId = fullImageId;
		}
	}

	public long getOriginalFullImageId() {
		return _originalFullImageId;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;

		if (!_setOriginalPriority) {
			_setOriginalPriority = true;

			_originalPriority = priority;
		}
	}

	public int getOriginalPriority() {
		return _originalPriority;
	}

	public SCProductScreenshot toEscapedModel() {
		if (isEscapedModel()) {
			return (SCProductScreenshot)this;
		}
		else {
			return (SCProductScreenshot)Proxy.newProxyInstance(SCProductScreenshot.class.getClassLoader(),
				new Class[] { SCProductScreenshot.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SCProductScreenshot.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		SCProductScreenshotImpl clone = new SCProductScreenshotImpl();

		clone.setProductScreenshotId(getProductScreenshotId());
		clone.setCompanyId(getCompanyId());
		clone.setGroupId(getGroupId());
		clone.setProductEntryId(getProductEntryId());
		clone.setThumbnailId(getThumbnailId());
		clone.setFullImageId(getFullImageId());
		clone.setPriority(getPriority());

		return clone;
	}

	public int compareTo(SCProductScreenshot scProductScreenshot) {
		int value = 0;

		if (getProductEntryId() < scProductScreenshot.getProductEntryId()) {
			value = -1;
		}
		else if (getProductEntryId() > scProductScreenshot.getProductEntryId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getPriority() < scProductScreenshot.getPriority()) {
			value = -1;
		}
		else if (getPriority() > scProductScreenshot.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SCProductScreenshot scProductScreenshot = null;

		try {
			scProductScreenshot = (SCProductScreenshot)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = scProductScreenshot.getPrimaryKey();

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
		StringBundler sb = new StringBundler(15);

		sb.append("{productScreenshotId=");
		sb.append(getProductScreenshotId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", productEntryId=");
		sb.append(getProductEntryId());
		sb.append(", thumbnailId=");
		sb.append(getThumbnailId());
		sb.append(", fullImageId=");
		sb.append(getFullImageId());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.portlet.softwarecatalog.model.SCProductScreenshot");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>productScreenshotId</column-name><column-value><![CDATA[");
		sb.append(getProductScreenshotId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>productEntryId</column-name><column-value><![CDATA[");
		sb.append(getProductEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>thumbnailId</column-name><column-value><![CDATA[");
		sb.append(getThumbnailId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fullImageId</column-name><column-value><![CDATA[");
		sb.append(getFullImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _productScreenshotId;
	private long _companyId;
	private long _groupId;
	private long _productEntryId;
	private long _originalProductEntryId;
	private boolean _setOriginalProductEntryId;
	private long _thumbnailId;
	private long _originalThumbnailId;
	private boolean _setOriginalThumbnailId;
	private long _fullImageId;
	private long _originalFullImageId;
	private boolean _setOriginalFullImageId;
	private int _priority;
	private int _originalPriority;
	private boolean _setOriginalPriority;
	private transient ExpandoBridge _expandoBridge;
}