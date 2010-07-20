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

import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the PasswordPolicyRel service. Represents a row in the &quot;PasswordPolicyRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. All helper methods and application logic should be put in {@link com.liferay.portal.model.impl.PasswordPolicyRelImpl}.
 * </p>
 *
 * <p>
 * Never reference this interface directly. All methods that expect a password policy rel model instance should use the {@link PasswordPolicyRel} interface instead.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRel
 * @see       com.liferay.portal.model.impl.PasswordPolicyRelImpl
 * @see       com.liferay.portal.model.impl.PasswordPolicyRelModelImpl
 * @generated
 */
public interface PasswordPolicyRelModel extends BaseModel<PasswordPolicyRel> {
	/**
	 * Gets the primary key of this password policy rel.
	 *
	 * @return the primary key of this password policy rel
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this password policy rel
	 *
	 * @param pk the primary key of this password policy rel
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the password policy rel id of this password policy rel.
	 *
	 * @return the password policy rel id of this password policy rel
	 */
	public long getPasswordPolicyRelId();

	/**
	 * Sets the password policy rel id of this password policy rel.
	 *
	 * @param passwordPolicyRelId the password policy rel id of this password policy rel
	 */
	public void setPasswordPolicyRelId(long passwordPolicyRelId);

	/**
	 * Gets the password policy id of this password policy rel.
	 *
	 * @return the password policy id of this password policy rel
	 */
	public long getPasswordPolicyId();

	/**
	 * Sets the password policy id of this password policy rel.
	 *
	 * @param passwordPolicyId the password policy id of this password policy rel
	 */
	public void setPasswordPolicyId(long passwordPolicyId);

	/**
	 * Gets the class name of the model instance this password policy rel is associated with.
	 *
	 * @return the class name of the model instance this password policy rel is associated with
	 */
	public String getClassName();

	/**
	 * Gets the class name id of this password policy rel.
	 *
	 * @return the class name id of this password policy rel
	 */
	public long getClassNameId();

	/**
	 * Sets the class name id of this password policy rel.
	 *
	 * @param classNameId the class name id of this password policy rel
	 */
	public void setClassNameId(long classNameId);

	/**
	 * Gets the class p k of this password policy rel.
	 *
	 * @return the class p k of this password policy rel
	 */
	public long getClassPK();

	/**
	 * Sets the class p k of this password policy rel.
	 *
	 * @param classPK the class p k of this password policy rel
	 */
	public void setClassPK(long classPK);

	/**
	 * Gets a copy of this password policy rel as an escaped model instance by wrapping it with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public PasswordPolicyRel toEscapedModel();

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

	public int compareTo(PasswordPolicyRel passwordPolicyRel);

	public int hashCode();

	public String toString();

	public String toXmlString();
}