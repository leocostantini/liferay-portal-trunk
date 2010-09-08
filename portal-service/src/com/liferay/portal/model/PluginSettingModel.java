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
 * The base model interface for the PluginSetting service. Represents a row in the &quot;PluginSetting&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.PluginSettingModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.PluginSettingImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this interface directly. All methods that expect a plugin setting model instance should use the {@link PluginSetting} interface instead.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PluginSetting
 * @see com.liferay.portal.model.impl.PluginSettingImpl
 * @see com.liferay.portal.model.impl.PluginSettingModelImpl
 * @generated
 */
public interface PluginSettingModel extends BaseModel<PluginSetting> {
	/**
	 * Gets the primary key of this plugin setting.
	 *
	 * @return the primary key of this plugin setting
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this plugin setting
	 *
	 * @param pk the primary key of this plugin setting
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the plugin setting id of this plugin setting.
	 *
	 * @return the plugin setting id of this plugin setting
	 */
	public long getPluginSettingId();

	/**
	 * Sets the plugin setting id of this plugin setting.
	 *
	 * @param pluginSettingId the plugin setting id of this plugin setting
	 */
	public void setPluginSettingId(long pluginSettingId);

	/**
	 * Gets the company id of this plugin setting.
	 *
	 * @return the company id of this plugin setting
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this plugin setting.
	 *
	 * @param companyId the company id of this plugin setting
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the plugin id of this plugin setting.
	 *
	 * @return the plugin id of this plugin setting
	 */
	@AutoEscape
	public String getPluginId();

	/**
	 * Sets the plugin id of this plugin setting.
	 *
	 * @param pluginId the plugin id of this plugin setting
	 */
	public void setPluginId(String pluginId);

	/**
	 * Gets the plugin type of this plugin setting.
	 *
	 * @return the plugin type of this plugin setting
	 */
	@AutoEscape
	public String getPluginType();

	/**
	 * Sets the plugin type of this plugin setting.
	 *
	 * @param pluginType the plugin type of this plugin setting
	 */
	public void setPluginType(String pluginType);

	/**
	 * Gets the roles of this plugin setting.
	 *
	 * @return the roles of this plugin setting
	 */
	@AutoEscape
	public String getRoles();

	/**
	 * Sets the roles of this plugin setting.
	 *
	 * @param roles the roles of this plugin setting
	 */
	public void setRoles(String roles);

	/**
	 * Gets the active of this plugin setting.
	 *
	 * @return the active of this plugin setting
	 */
	public boolean getActive();

	/**
	 * Determines if this plugin setting is active.
	 *
	 * @return <code>true</code> if this plugin setting is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this {$entity.humanName} is active.
	 *
	 * @param active the active of this plugin setting
	 */
	public void setActive(boolean active);

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

	public int compareTo(PluginSetting pluginSetting);

	public int hashCode();

	public PluginSetting toEscapedModel();

	public String toString();

	public String toXmlString();
}