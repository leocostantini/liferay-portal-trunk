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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil
 * @generated
 */
public class SCProductEntryServiceHttp {
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		HttpPrincipal httpPrincipal, java.lang.String name,
		java.lang.String type, java.lang.String tags,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String author,
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		long[] licenseIds, java.util.List<byte[]> thumbnails,
		java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = name;

			if (name == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = type;

			if (type == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = tags;

			if (tags == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = shortDescription;

			if (shortDescription == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = longDescription;

			if (longDescription == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = pageURL;

			if (pageURL == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = author;

			if (author == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = repoGroupId;

			if (repoGroupId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = repoArtifactId;

			if (repoArtifactId == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = licenseIds;

			if (licenseIds == null) {
				paramObj9 = new NullWrapper("[J");
			}

			Object paramObj10 = thumbnails;

			if (thumbnails == null) {
				paramObj10 = new NullWrapper("java.util.List");
			}

			Object paramObj11 = fullImages;

			if (fullImages == null) {
				paramObj11 = new NullWrapper("java.util.List");
			}

			Object paramObj12 = serviceContext;

			if (serviceContext == null) {
				paramObj12 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(SCProductEntryServiceUtil.class.getName(),
					"addProductEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.softwarecatalog.model.SCProductEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteProductEntry(HttpPrincipal httpPrincipal,
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(productEntryId);

			MethodWrapper methodWrapper = new MethodWrapper(SCProductEntryServiceUtil.class.getName(),
					"deleteProductEntry", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		HttpPrincipal httpPrincipal, long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(productEntryId);

			MethodWrapper methodWrapper = new MethodWrapper(SCProductEntryServiceUtil.class.getName(),
					"getProductEntry", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.softwarecatalog.model.SCProductEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		HttpPrincipal httpPrincipal, long productEntryId,
		java.lang.String name, java.lang.String type, java.lang.String tags,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String author,
		java.lang.String repoGroupId, java.lang.String repoArtifactId,
		long[] licenseIds, java.util.List<byte[]> thumbnails,
		java.util.List<byte[]> fullImages)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(productEntryId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = type;

			if (type == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = tags;

			if (tags == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = shortDescription;

			if (shortDescription == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = longDescription;

			if (longDescription == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = pageURL;

			if (pageURL == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = author;

			if (author == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = repoGroupId;

			if (repoGroupId == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = repoArtifactId;

			if (repoArtifactId == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = licenseIds;

			if (licenseIds == null) {
				paramObj10 = new NullWrapper("[J");
			}

			Object paramObj11 = thumbnails;

			if (thumbnails == null) {
				paramObj11 = new NullWrapper("java.util.List");
			}

			Object paramObj12 = fullImages;

			if (fullImages == null) {
				paramObj12 = new NullWrapper("java.util.List");
			}

			MethodWrapper methodWrapper = new MethodWrapper(SCProductEntryServiceUtil.class.getName(),
					"updateProductEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.softwarecatalog.model.SCProductEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductEntryServiceHttp.class);
}