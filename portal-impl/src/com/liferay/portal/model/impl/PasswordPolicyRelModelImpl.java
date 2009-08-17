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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.PasswordPolicyRelSoap;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PasswordPolicyRelModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PasswordPolicyRel table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRelImpl
 * @see       com.liferay.portal.model.PasswordPolicyRel
 * @see       com.liferay.portal.model.PasswordPolicyRelModel
 * @generated
 */
public class PasswordPolicyRelModelImpl extends BaseModelImpl<PasswordPolicyRel> {
	public static final String TABLE_NAME = "PasswordPolicyRel";
	public static final Object[][] TABLE_COLUMNS = {
			{ "passwordPolicyRelId", new Integer(Types.BIGINT) },
			

			{ "passwordPolicyId", new Integer(Types.BIGINT) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table PasswordPolicyRel (passwordPolicyRelId LONG not null primary key,passwordPolicyId LONG,classNameId LONG,classPK LONG)";
	public static final String TABLE_SQL_DROP = "drop table PasswordPolicyRel";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PasswordPolicyRel"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PasswordPolicyRel"),
			true);

	public static PasswordPolicyRel toModel(PasswordPolicyRelSoap soapModel) {
		PasswordPolicyRel model = new PasswordPolicyRelImpl();

		model.setPasswordPolicyRelId(soapModel.getPasswordPolicyRelId());
		model.setPasswordPolicyId(soapModel.getPasswordPolicyId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());

		return model;
	}

	public static List<PasswordPolicyRel> toModels(
		PasswordPolicyRelSoap[] soapModels) {
		List<PasswordPolicyRel> models = new ArrayList<PasswordPolicyRel>(soapModels.length);

		for (PasswordPolicyRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PasswordPolicyRel"));

	public PasswordPolicyRelModelImpl() {
	}

	public long getPrimaryKey() {
		return _passwordPolicyRelId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordPolicyRelId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_passwordPolicyRelId);
	}

	public long getPasswordPolicyRelId() {
		return _passwordPolicyRelId;
	}

	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		_passwordPolicyRelId = passwordPolicyRelId;
	}

	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyId = passwordPolicyId;

		if (!_setOriginalPasswordPolicyId) {
			_setOriginalPasswordPolicyId = true;

			_originalPasswordPolicyId = passwordPolicyId;
		}
	}

	public long getOriginalPasswordPolicyId() {
		return _originalPasswordPolicyId;
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

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = classNameId;
		}
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = classPK;
		}
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public PasswordPolicyRel toEscapedModel() {
		if (isEscapedModel()) {
			return (PasswordPolicyRel)this;
		}
		else {
			PasswordPolicyRel model = new PasswordPolicyRelImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setPasswordPolicyRelId(getPasswordPolicyRelId());
			model.setPasswordPolicyId(getPasswordPolicyId());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());

			model = (PasswordPolicyRel)Proxy.newProxyInstance(PasswordPolicyRel.class.getClassLoader(),
					new Class[] { PasswordPolicyRel.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(PasswordPolicyRel.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		PasswordPolicyRelImpl clone = new PasswordPolicyRelImpl();

		clone.setPasswordPolicyRelId(getPasswordPolicyRelId());
		clone.setPasswordPolicyId(getPasswordPolicyId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());

		return clone;
	}

	public int compareTo(PasswordPolicyRel passwordPolicyRel) {
		long pk = passwordPolicyRel.getPrimaryKey();

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

		PasswordPolicyRel passwordPolicyRel = null;

		try {
			passwordPolicyRel = (PasswordPolicyRel)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = passwordPolicyRel.getPrimaryKey();

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
		StringBuilder sb = new StringBuilder();

		sb.append("{passwordPolicyRelId=");
		sb.append(getPasswordPolicyRelId());
		sb.append(", passwordPolicyId=");
		sb.append(getPasswordPolicyId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PasswordPolicyRel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>passwordPolicyRelId</column-name><column-value><![CDATA[");
		sb.append(getPasswordPolicyRelId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passwordPolicyId</column-name><column-value><![CDATA[");
		sb.append(getPasswordPolicyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _passwordPolicyRelId;
	private long _passwordPolicyId;
	private long _originalPasswordPolicyId;
	private boolean _setOriginalPasswordPolicyId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private transient ExpandoBridge _expandoBridge;
}