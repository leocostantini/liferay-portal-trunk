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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the DLFileShortcut service. Represents a row in the &quot;DLFileShortcut&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcut
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl
 * @generated
 */
public interface DLFileShortcutModel extends BaseModel<DLFileShortcut> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a d l file shortcut model instance should use the {@link DLFileShortcut} interface instead.
	 */

	/**
	 * Gets the primary key of this d l file shortcut.
	 *
	 * @return the primary key of this d l file shortcut
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this d l file shortcut
	 *
	 * @param pk the primary key of this d l file shortcut
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the uuid of this d l file shortcut.
	 *
	 * @return the uuid of this d l file shortcut
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this d l file shortcut.
	 *
	 * @param uuid the uuid of this d l file shortcut
	 */
	public void setUuid(String uuid);

	/**
	 * Gets the file shortcut id of this d l file shortcut.
	 *
	 * @return the file shortcut id of this d l file shortcut
	 */
	public long getFileShortcutId();

	/**
	 * Sets the file shortcut id of this d l file shortcut.
	 *
	 * @param fileShortcutId the file shortcut id of this d l file shortcut
	 */
	public void setFileShortcutId(long fileShortcutId);

	/**
	 * Gets the group id of this d l file shortcut.
	 *
	 * @return the group id of this d l file shortcut
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this d l file shortcut.
	 *
	 * @param groupId the group id of this d l file shortcut
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the to group id of this d l file shortcut.
	 *
	 * @return the to group id of this d l file shortcut
	 */
	public long getToGroupId();

	/**
	 * Sets the to group id of this d l file shortcut.
	 *
	 * @param toGroupId the to group id of this d l file shortcut
	 */
	public void setToGroupId(long toGroupId);

	/**
	 * Gets the company id of this d l file shortcut.
	 *
	 * @return the company id of this d l file shortcut
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this d l file shortcut.
	 *
	 * @param companyId the company id of this d l file shortcut
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the user id of this d l file shortcut.
	 *
	 * @return the user id of this d l file shortcut
	 */
	public long getUserId();

	/**
	 * Sets the user id of this d l file shortcut.
	 *
	 * @param userId the user id of this d l file shortcut
	 */
	public void setUserId(long userId);

	/**
	 * Gets the user uuid of this d l file shortcut.
	 *
	 * @return the user uuid of this d l file shortcut
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this d l file shortcut.
	 *
	 * @param userUuid the user uuid of this d l file shortcut
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Gets the user name of this d l file shortcut.
	 *
	 * @return the user name of this d l file shortcut
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this d l file shortcut.
	 *
	 * @param userName the user name of this d l file shortcut
	 */
	public void setUserName(String userName);

	/**
	 * Gets the create date of this d l file shortcut.
	 *
	 * @return the create date of this d l file shortcut
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this d l file shortcut.
	 *
	 * @param createDate the create date of this d l file shortcut
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Gets the modified date of this d l file shortcut.
	 *
	 * @return the modified date of this d l file shortcut
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this d l file shortcut.
	 *
	 * @param modifiedDate the modified date of this d l file shortcut
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Gets the folder id of this d l file shortcut.
	 *
	 * @return the folder id of this d l file shortcut
	 */
	public long getFolderId();

	/**
	 * Sets the folder id of this d l file shortcut.
	 *
	 * @param folderId the folder id of this d l file shortcut
	 */
	public void setFolderId(long folderId);

	/**
	 * Gets the to folder id of this d l file shortcut.
	 *
	 * @return the to folder id of this d l file shortcut
	 */
	public long getToFolderId();

	/**
	 * Sets the to folder id of this d l file shortcut.
	 *
	 * @param toFolderId the to folder id of this d l file shortcut
	 */
	public void setToFolderId(long toFolderId);

	/**
	 * Gets the to name of this d l file shortcut.
	 *
	 * @return the to name of this d l file shortcut
	 */
	@AutoEscape
	public String getToName();

	/**
	 * Sets the to name of this d l file shortcut.
	 *
	 * @param toName the to name of this d l file shortcut
	 */
	public void setToName(String toName);

	/**
	 * Gets the status of this d l file shortcut.
	 *
	 * @return the status of this d l file shortcut
	 */
	public int getStatus();

	/**
	 * Sets the status of this d l file shortcut.
	 *
	 * @param status the status of this d l file shortcut
	 */
	public void setStatus(int status);

	/**
	 * Gets the status by user id of this d l file shortcut.
	 *
	 * @return the status by user id of this d l file shortcut
	 */
	public long getStatusByUserId();

	/**
	 * Sets the status by user id of this d l file shortcut.
	 *
	 * @param statusByUserId the status by user id of this d l file shortcut
	 */
	public void setStatusByUserId(long statusByUserId);

	/**
	 * Gets the status by user uuid of this d l file shortcut.
	 *
	 * @return the status by user uuid of this d l file shortcut
	 * @throws SystemException if a system exception occurred
	 */
	public String getStatusByUserUuid() throws SystemException;

	/**
	 * Sets the status by user uuid of this d l file shortcut.
	 *
	 * @param statusByUserUuid the status by user uuid of this d l file shortcut
	 */
	public void setStatusByUserUuid(String statusByUserUuid);

	/**
	 * Gets the status by user name of this d l file shortcut.
	 *
	 * @return the status by user name of this d l file shortcut
	 */
	@AutoEscape
	public String getStatusByUserName();

	/**
	 * Sets the status by user name of this d l file shortcut.
	 *
	 * @param statusByUserName the status by user name of this d l file shortcut
	 */
	public void setStatusByUserName(String statusByUserName);

	/**
	 * Gets the status date of this d l file shortcut.
	 *
	 * @return the status date of this d l file shortcut
	 */
	public Date getStatusDate();

	/**
	 * Sets the status date of this d l file shortcut.
	 *
	 * @param statusDate the status date of this d l file shortcut
	 */
	public void setStatusDate(Date statusDate);

	/**
	 * @deprecated {@link #isApproved}
	 */
	public boolean getApproved();

	/**
	 * Determines if this d l file shortcut is approved.
	 *
	 * @return <code>true</code> if this d l file shortcut is approved; <code>false</code> otherwise
	 */
	public boolean isApproved();

	/**
	 * Determines if this d l file shortcut is a draft.
	 *
	 * @return <code>true</code> if this d l file shortcut is a draft; <code>false</code> otherwise
	 */
	public boolean isDraft();

	/**
	 * Determines if this d l file shortcut is expired.
	 *
	 * @return <code>true</code> if this d l file shortcut is expired; <code>false</code> otherwise
	 */
	public boolean isExpired();

	/**
	 * Determines if this d l file shortcut is pending.
	 *
	 * @return <code>true</code> if this d l file shortcut is pending; <code>false</code> otherwise
	 */
	public boolean isPending();

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

	public int compareTo(DLFileShortcut dlFileShortcut);

	public int hashCode();

	public DLFileShortcut toEscapedModel();

	public String toString();

	public String toXmlString();
}