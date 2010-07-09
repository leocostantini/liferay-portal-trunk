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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link OrgLaborService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborService
 * @generated
 */
public class OrgLaborServiceUtil {
	public static com.liferay.portal.model.OrgLabor addOrgLabor(
		long organizationId, int typeId, int sunOpen, int sunClose,
		int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
		int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
		int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addOrgLabor(organizationId, typeId, sunOpen, sunClose,
			monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
			thuClose, friOpen, friClose, satOpen, satClose);
	}

	public static void deleteOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteOrgLabor(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor getOrgLabor(long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrgLabor(orgLaborId);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> getOrgLabors(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrgLabors(organizationId);
	}

	public static com.liferay.portal.model.OrgLabor updateOrgLabor(
		long orgLaborId, int typeId, int sunOpen, int sunClose, int monOpen,
		int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose,
		int thuOpen, int thuClose, int friOpen, int friClose, int satOpen,
		int satClose)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateOrgLabor(orgLaborId, typeId, sunOpen, sunClose,
			monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
			thuClose, friOpen, friClose, satOpen, satClose);
	}

	public static OrgLaborService getService() {
		if (_service == null) {
			_service = (OrgLaborService)PortalBeanLocatorUtil.locate(OrgLaborService.class.getName());
		}

		return _service;
	}

	public void setService(OrgLaborService service) {
		_service = service;
	}

	private static OrgLaborService _service;
}