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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.SCLicenseSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SCLicenseModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>SCLicense</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.model.SCLicense
 * @see com.liferay.portlet.softwarecatalog.model.SCLicenseModel
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCLicenseImpl
 *
 */
public class SCLicenseModelImpl extends BaseModelImpl<SCLicense> {
	public static final String TABLE_NAME = "SCLicense";
	public static final Object[][] TABLE_COLUMNS = {
			{ "licenseId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "url", new Integer(Types.VARCHAR) },
			

			{ "openSource", new Integer(Types.BOOLEAN) },
			

			{ "active_", new Integer(Types.BOOLEAN) },
			

			{ "recommended", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table SCLicense (licenseId LONG not null primary key,name VARCHAR(75) null,url STRING null,openSource BOOLEAN,active_ BOOLEAN,recommended BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table SCLicense";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCLicense"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.softwarecatalog.model.SCLicense"),
			true);

	public static SCLicense toModel(SCLicenseSoap soapModel) {
		SCLicense model = new SCLicenseImpl();

		model.setLicenseId(soapModel.getLicenseId());
		model.setName(soapModel.getName());
		model.setUrl(soapModel.getUrl());
		model.setOpenSource(soapModel.getOpenSource());
		model.setActive(soapModel.getActive());
		model.setRecommended(soapModel.getRecommended());

		return model;
	}

	public static List<SCLicense> toModels(SCLicenseSoap[] soapModels) {
		List<SCLicense> models = new ArrayList<SCLicense>(soapModels.length);

		for (SCLicenseSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean FINDER_CACHE_ENABLED_SCLICENSES_SCPRODUCTENTRIES =
		GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.SCLicenses_SCProductEntries"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.softwarecatalog.model.SCLicense"));

	public SCLicenseModelImpl() {
	}

	public long getPrimaryKey() {
		return _licenseId;
	}

	public void setPrimaryKey(long pk) {
		setLicenseId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_licenseId);
	}

	public long getLicenseId() {
		return _licenseId;
	}

	public void setLicenseId(long licenseId) {
		if (licenseId != _licenseId) {
			_licenseId = licenseId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if ((name != _name) || ((name != null) && !name.equals(_name))) {
			_name = name;
		}
	}

	public String getUrl() {
		return GetterUtil.getString(_url);
	}

	public void setUrl(String url) {
		if ((url != _url) || ((url != null) && !url.equals(_url))) {
			_url = url;
		}
	}

	public boolean getOpenSource() {
		return _openSource;
	}

	public boolean isOpenSource() {
		return _openSource;
	}

	public void setOpenSource(boolean openSource) {
		if (openSource != _openSource) {
			_openSource = openSource;
		}
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		if (active != _active) {
			_active = active;
		}
	}

	public boolean getRecommended() {
		return _recommended;
	}

	public boolean isRecommended() {
		return _recommended;
	}

	public void setRecommended(boolean recommended) {
		if (recommended != _recommended) {
			_recommended = recommended;
		}
	}

	public SCLicense toEscapedModel() {
		if (isEscapedModel()) {
			return (SCLicense)this;
		}
		else {
			SCLicense model = new SCLicenseImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setLicenseId(getLicenseId());
			model.setName(HtmlUtil.escape(getName()));
			model.setUrl(HtmlUtil.escape(getUrl()));
			model.setOpenSource(getOpenSource());
			model.setActive(getActive());
			model.setRecommended(getRecommended());

			model = (SCLicense)Proxy.newProxyInstance(SCLicense.class.getClassLoader(),
					new Class[] { SCLicense.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(SCLicense.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		SCLicenseImpl clone = new SCLicenseImpl();

		clone.setLicenseId(getLicenseId());
		clone.setName(getName());
		clone.setUrl(getUrl());
		clone.setOpenSource(getOpenSource());
		clone.setActive(getActive());
		clone.setRecommended(getRecommended());

		return clone;
	}

	public int compareTo(SCLicense scLicense) {
		int value = 0;

		value = getName().compareTo(scLicense.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SCLicense scLicense = null;

		try {
			scLicense = (SCLicense)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = scLicense.getPrimaryKey();

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

	private long _licenseId;
	private String _name;
	private String _url;
	private boolean _openSource;
	private boolean _active;
	private boolean _recommended;
	private transient ExpandoBridge _expandoBridge;
}