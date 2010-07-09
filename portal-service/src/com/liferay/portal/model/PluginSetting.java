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


/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PluginSetting table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.PluginSettingImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSettingModel
 * @see       com.liferay.portal.model.impl.PluginSettingImpl
 * @see       com.liferay.portal.model.impl.PluginSettingModelImpl
 * @generated
 */
public interface PluginSetting extends PluginSettingModel {
	/**
	* Adds a role to the list of roles.
	*/
	public void addRole(java.lang.String role);

	/**
	* Sets a string of ordered comma delimited plugin ids.
	*/
	public void setRoles(java.lang.String roles);

	/**
	* Gets an array of required roles of the plugin.
	*
	* @return an array of required roles of the plugin
	*/
	public java.lang.String[] getRolesArray();

	/**
	* Sets an array of required roles of the plugin.
	*/
	public void setRolesArray(java.lang.String[] rolesArray);

	/**
	* Returns true if the plugin has a role with the specified name.
	*
	* @return true if the plugin has a role with the specified name
	*/
	public boolean hasRoleWithName(java.lang.String roleName);

	/**
	* Returns true if the user has permission to use this plugin
	*
	* @return true if the user has permission to use this plugin
	*/
	public boolean hasPermission(long userId);
}