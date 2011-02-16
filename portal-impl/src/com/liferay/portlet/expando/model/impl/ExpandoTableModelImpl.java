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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableModel;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * The base model implementation for the ExpandoTable service. Represents a row in the &quot;ExpandoTable&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.expando.model.ExpandoTableModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ExpandoTableImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableImpl
 * @see com.liferay.portlet.expando.model.ExpandoTable
 * @see com.liferay.portlet.expando.model.ExpandoTableModel
 * @generated
 */
public class ExpandoTableModelImpl extends BaseModelImpl<ExpandoTable>
	implements ExpandoTableModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a expando table model instance should use the {@link com.liferay.portlet.expando.model.ExpandoTable} interface instead.
	 */
	public static final String TABLE_NAME = "ExpandoTable";
	public static final Object[][] TABLE_COLUMNS = {
			{ "tableId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "name", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoTable (tableId LONG not null primary key,companyId LONG,classNameId LONG,name VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoTable";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.expando.model.ExpandoTable"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.expando.model.ExpandoTable"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.expando.model.ExpandoTable"));

	public ExpandoTableModelImpl() {
	}

	public long getPrimaryKey() {
		return _tableId;
	}

	public void setPrimaryKey(long pk) {
		setTableId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_tableId);
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;
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

	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public ExpandoTable toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoTable)this;
		}
		else {
			return (ExpandoTable)Proxy.newProxyInstance(ExpandoTable.class.getClassLoader(),
				new Class[] { ExpandoTable.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public Object clone() {
		ExpandoTableImpl expandoTableImpl = new ExpandoTableImpl();

		ExpandoTableModelImpl expandoTableModelImpl = (ExpandoTableModelImpl)expandoTableImpl;

		expandoTableImpl.setTableId(getTableId());

		expandoTableImpl.setCompanyId(getCompanyId());

		expandoTableModelImpl._originalCompanyId = expandoTableModelImpl._companyId;

		expandoTableModelImpl._setOriginalCompanyId = false;
		expandoTableImpl.setClassNameId(getClassNameId());

		expandoTableModelImpl._originalClassNameId = expandoTableModelImpl._classNameId;

		expandoTableModelImpl._setOriginalClassNameId = false;
		expandoTableImpl.setName(getName());

		expandoTableModelImpl._originalName = expandoTableModelImpl._name;

		return expandoTableImpl;
	}

	public int compareTo(ExpandoTable expandoTable) {
		long pk = expandoTable.getPrimaryKey();

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

		ExpandoTable expandoTable = null;

		try {
			expandoTable = (ExpandoTable)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoTable.getPrimaryKey();

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
		StringBundler sb = new StringBundler(9);

		sb.append("{tableId=");
		sb.append(getTableId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", name=");
		sb.append(getName());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.expando.model.ExpandoTable");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>tableId</column-name><column-value><![CDATA[");
		sb.append(getTableId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _tableId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private String _name;
	private String _originalName;
}