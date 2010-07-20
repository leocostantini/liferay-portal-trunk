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
 * The base model interface for the Team service. Represents a row in the &quot;Team&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.TeamModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. All helper methods and application logic should be put in {@link com.liferay.portal.model.impl.TeamImpl}.
 * </p>
 *
 * <p>
 * Never reference this interface directly. All methods that expect a team model instance should use the {@link Team} interface instead.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Team
 * @see       com.liferay.portal.model.impl.TeamImpl
 * @see       com.liferay.portal.model.impl.TeamModelImpl
 * @generated
 */
public interface TeamModel extends BaseModel<Team> {
	/**
	 * Gets the primary key of this team.
	 *
	 * @return the primary key of this team
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this team
	 *
	 * @param pk the primary key of this team
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the team id of this team.
	 *
	 * @return the team id of this team
	 */
	public long getTeamId();

	/**
	 * Sets the team id of this team.
	 *
	 * @param teamId the team id of this team
	 */
	public void setTeamId(long teamId);

	/**
	 * Gets the company id of this team.
	 *
	 * @return the company id of this team
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this team.
	 *
	 * @param companyId the company id of this team
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the user id of this team.
	 *
	 * @return the user id of this team
	 */
	public long getUserId();

	/**
	 * Sets the user id of this team.
	 *
	 * @param userId the user id of this team
	 */
	public void setUserId(long userId);

	/**
	 * Gets the user uuid of this team.
	 *
	 * @return the user uuid of this team
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this team.
	 *
	 * @param userUuid the user uuid of this team
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Gets the user name of this team.
	 *
	 * @return the user name of this team
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this team.
	 *
	 * @param userName the user name of this team
	 */
	public void setUserName(String userName);

	/**
	 * Gets the create date of this team.
	 *
	 * @return the create date of this team
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this team.
	 *
	 * @param createDate the create date of this team
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Gets the modified date of this team.
	 *
	 * @return the modified date of this team
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this team.
	 *
	 * @param modifiedDate the modified date of this team
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Gets the group id of this team.
	 *
	 * @return the group id of this team
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this team.
	 *
	 * @param groupId the group id of this team
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the name of this team.
	 *
	 * @return the name of this team
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this team.
	 *
	 * @param name the name of this team
	 */
	public void setName(String name);

	/**
	 * Gets the description of this team.
	 *
	 * @return the description of this team
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this team.
	 *
	 * @param description the description of this team
	 */
	public void setDescription(String description);

	/**
	 * Gets a copy of this team as an escaped model instance by wrapping it with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public Team toEscapedModel();

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

	public int compareTo(Team team);

	public int hashCode();

	public String toString();

	public String toXmlString();
}