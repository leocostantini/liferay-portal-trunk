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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the Website service. Represents a row in the &quot;Website&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.WebsiteModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.WebsiteImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this interface directly. All methods that expect a website model instance should use the {@link Website} interface instead.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Website
 * @see com.liferay.portal.model.impl.WebsiteImpl
 * @see com.liferay.portal.model.impl.WebsiteModelImpl
 * @generated
 */
public interface WebsiteModel extends BaseModel<Website> {
	/**
	 * Gets the primary key of this website.
	 *
	 * @return the primary key of this website
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this website
	 *
	 * @param pk the primary key of this website
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the website id of this website.
	 *
	 * @return the website id of this website
	 */
	public long getWebsiteId();

	/**
	 * Sets the website id of this website.
	 *
	 * @param websiteId the website id of this website
	 */
	public void setWebsiteId(long websiteId);

	/**
	 * Gets the company id of this website.
	 *
	 * @return the company id of this website
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this website.
	 *
	 * @param companyId the company id of this website
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the user id of this website.
	 *
	 * @return the user id of this website
	 */
	public long getUserId();

	/**
	 * Sets the user id of this website.
	 *
	 * @param userId the user id of this website
	 */
	public void setUserId(long userId);

	/**
	 * Gets the user uuid of this website.
	 *
	 * @return the user uuid of this website
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this website.
	 *
	 * @param userUuid the user uuid of this website
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Gets the user name of this website.
	 *
	 * @return the user name of this website
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this website.
	 *
	 * @param userName the user name of this website
	 */
	public void setUserName(String userName);

	/**
	 * Gets the create date of this website.
	 *
	 * @return the create date of this website
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this website.
	 *
	 * @param createDate the create date of this website
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Gets the modified date of this website.
	 *
	 * @return the modified date of this website
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this website.
	 *
	 * @param modifiedDate the modified date of this website
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Gets the class name of the model instance this website is polymorphically associated with.
	 *
	 * @return the class name of the model instance this website is polymorphically associated with
	 */
	public String getClassName();

	/**
	 * Gets the class name id of this website.
	 *
	 * @return the class name id of this website
	 */
	public long getClassNameId();

	/**
	 * Sets the class name id of this website.
	 *
	 * @param classNameId the class name id of this website
	 */
	public void setClassNameId(long classNameId);

	/**
	 * Gets the class p k of this website.
	 *
	 * @return the class p k of this website
	 */
	public long getClassPK();

	/**
	 * Sets the class p k of this website.
	 *
	 * @param classPK the class p k of this website
	 */
	public void setClassPK(long classPK);

	/**
	 * Gets the url of this website.
	 *
	 * @return the url of this website
	 */
	@AutoEscape
	public String getUrl();

	/**
	 * Sets the url of this website.
	 *
	 * @param url the url of this website
	 */
	public void setUrl(String url);

	/**
	 * Gets the type id of this website.
	 *
	 * @return the type id of this website
	 */
	public int getTypeId();

	/**
	 * Sets the type id of this website.
	 *
	 * @param typeId the type id of this website
	 */
	public void setTypeId(int typeId);

	/**
	 * Gets the primary of this website.
	 *
	 * @return the primary of this website
	 */
	public boolean getPrimary();

	/**
	 * Determines if this website is primary.
	 *
	 * @return <code>true</code> if this website is primary; <code>false</code> otherwise
	 */
	public boolean isPrimary();

	/**
	 * Sets whether this {$entity.humanName} is primary.
	 *
	 * @param primary the primary of this website
	 */
	public void setPrimary(boolean primary);

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

	public int compareTo(Website website);

	public int hashCode();

	public Website toEscapedModel();

	public String toString();

	public String toXmlString();
}