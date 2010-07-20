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
 * This interface is a model that represents the OrgGroupPermission table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.OrgGroupPermissionImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupPermissionModel
 * @see       com.liferay.portal.model.impl.OrgGroupPermissionImpl
 * @see       com.liferay.portal.model.impl.OrgGroupPermissionModelImpl
 * @generated
 */
public interface OrgGroupPermission extends OrgGroupPermissionModel {
	public boolean containsGroup(
		java.util.List<com.liferay.portal.model.Group> groups);

	public boolean containsOrganization(
		java.util.List<com.liferay.portal.model.Organization> organizations);
}