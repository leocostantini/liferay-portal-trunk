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

package com.liferay.portal.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the Company service. Represents a row in the &quot;Company&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.CompanyModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.CompanyImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this interface directly. All methods that expect a company model instance should use the {@link Company} interface instead.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Company
 * @see com.liferay.portal.model.impl.CompanyImpl
 * @see com.liferay.portal.model.impl.CompanyModelImpl
 * @generated
 */
public interface CompanyModel extends BaseModel<Company> {
	/**
	 * Gets the primary key of this company.
	 *
	 * @return the primary key of this company
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this company
	 *
	 * @param pk the primary key of this company
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the company id of this company.
	 *
	 * @return the company id of this company
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this company.
	 *
	 * @param companyId the company id of this company
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the account id of this company.
	 *
	 * @return the account id of this company
	 */
	public long getAccountId();

	/**
	 * Sets the account id of this company.
	 *
	 * @param accountId the account id of this company
	 */
	public void setAccountId(long accountId);

	/**
	 * Gets the web id of this company.
	 *
	 * @return the web id of this company
	 */
	@AutoEscape
	public String getWebId();

	/**
	 * Sets the web id of this company.
	 *
	 * @param webId the web id of this company
	 */
	public void setWebId(String webId);

	/**
	 * Gets the key of this company.
	 *
	 * @return the key of this company
	 */
	@AutoEscape
	public String getKey();

	/**
	 * Sets the key of this company.
	 *
	 * @param key the key of this company
	 */
	public void setKey(String key);

	/**
	 * Gets the virtual host of this company.
	 *
	 * @return the virtual host of this company
	 */
	@AutoEscape
	public String getVirtualHost();

	/**
	 * Sets the virtual host of this company.
	 *
	 * @param virtualHost the virtual host of this company
	 */
	public void setVirtualHost(String virtualHost);

	/**
	 * Gets the mx of this company.
	 *
	 * @return the mx of this company
	 */
	@AutoEscape
	public String getMx();

	/**
	 * Sets the mx of this company.
	 *
	 * @param mx the mx of this company
	 */
	public void setMx(String mx);

	/**
	 * Gets the home u r l of this company.
	 *
	 * @return the home u r l of this company
	 */
	@AutoEscape
	public String getHomeURL();

	/**
	 * Sets the home u r l of this company.
	 *
	 * @param homeURL the home u r l of this company
	 */
	public void setHomeURL(String homeURL);

	/**
	 * Gets the logo id of this company.
	 *
	 * @return the logo id of this company
	 */
	public long getLogoId();

	/**
	 * Sets the logo id of this company.
	 *
	 * @param logoId the logo id of this company
	 */
	public void setLogoId(long logoId);

	/**
	 * Gets the system of this company.
	 *
	 * @return the system of this company
	 */
	public boolean getSystem();

	/**
	 * Determines if this company is system.
	 *
	 * @return <code>true</code> if this company is system; <code>false</code> otherwise
	 */
	public boolean isSystem();

	/**
	 * Sets whether this company is system.
	 *
	 * @param system the system of this company
	 */
	public void setSystem(boolean system);

	/**
	 * Gets the max users of this company.
	 *
	 * @return the max users of this company
	 */
	public int getMaxUsers();

	/**
	 * Sets the max users of this company.
	 *
	 * @param maxUsers the max users of this company
	 */
	public void setMaxUsers(int maxUsers);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(Company company);

	public int hashCode();

	public Company toEscapedModel();

	public String toString();

	public String toXmlString();
}