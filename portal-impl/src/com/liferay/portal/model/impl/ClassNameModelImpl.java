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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ClassNameModel;
import com.liferay.portal.model.ClassNameSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * The base model implementation for the ClassName service. Represents a row in the &quot;ClassName_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.ClassNameModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ClassNameImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassNameImpl
 * @see com.liferay.portal.model.ClassName
 * @see com.liferay.portal.model.ClassNameModel
 * @generated
 */
public class ClassNameModelImpl extends BaseModelImpl<ClassName>
	implements ClassNameModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a class name model instance should use the {@link com.liferay.portal.model.ClassName} interface instead.
	 */
	public static final String TABLE_NAME = "ClassName_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "classNameId", Types.BIGINT },
			{ "value", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table ClassName_ (classNameId LONG not null primary key,value VARCHAR(200) null)";
	public static final String TABLE_SQL_DROP = "drop table ClassName_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ClassName"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ClassName"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static ClassName toModel(ClassNameSoap soapModel) {
		ClassName model = new ClassNameImpl();

		model.setClassNameId(soapModel.getClassNameId());
		model.setValue(soapModel.getValue());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<ClassName> toModels(ClassNameSoap[] soapModels) {
		List<ClassName> models = new ArrayList<ClassName>(soapModels.length);

		for (ClassNameSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ClassName"));

	public ClassNameModelImpl() {
	}

	public long getPrimaryKey() {
		return _classNameId;
	}

	public void setPrimaryKey(long pk) {
		setClassNameId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_classNameId);
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
		_classNameId = classNameId;
	}

	public String getValue() {
		if (_value == null) {
			return StringPool.BLANK;
		}
		else {
			return _value;
		}
	}

	public void setValue(String value) {
		if (_originalValue == null) {
			_originalValue = _value;
		}

		_value = value;
	}

	public String getOriginalValue() {
		return GetterUtil.getString(_originalValue);
	}

	public ClassName toEscapedModel() {
		if (isEscapedModel()) {
			return (ClassName)this;
		}
		else {
			return (ClassName)Proxy.newProxyInstance(ClassName.class.getClassLoader(),
				new Class[] { ClassName.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					ClassName.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ClassNameModelImpl clone = new ClassNameImpl();
		ClassNameModelImpl cloneModel = (ClassNameModelImpl)clone;

		clone._classNameId = _classNameId;

		clone._originalValue = clone._value = _value;

		cloneModel._originalValue = cloneModel._value;

		return clone;
	}

	public int compareTo(ClassName className) {
		long pk = className.getPrimaryKey();

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

		ClassName className = null;

		try {
			className = (ClassName)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = className.getPrimaryKey();

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
		StringBundler sb = new StringBundler(5);

		sb.append("{classNameId=");
		sb.append(getClassNameId());
		sb.append(", value=");
		sb.append(getValue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ClassName");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append(getValue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _classNameId;
	private String _value;
	private String _originalValue;
	private transient ExpandoBridge _expandoBridge;
}