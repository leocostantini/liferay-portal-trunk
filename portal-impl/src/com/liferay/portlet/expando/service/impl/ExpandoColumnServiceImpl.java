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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.base.ExpandoColumnServiceBaseImpl;
import com.liferay.portlet.expando.service.permission.ExpandoColumnPermission;

/**
 * <a href="ExpandoColumnServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnServiceImpl extends ExpandoColumnServiceBaseImpl {

	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);

		return expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);

		return expandoColumnLocalService.addColumn(
			tableId, name, type, defaultData);
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.DELETE);

		expandoColumnLocalService.deleteColumn(columnId);
	}

	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	public ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateColumn(
			columnId, name, type, defaultData);
	}

	public ExpandoColumn updateTypeSettings(long columnId, String typeSettings)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateTypeSettings(
			columnId, typeSettings);
	}

}