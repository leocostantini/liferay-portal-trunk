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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.journal.service.JournalFeedServiceUtil;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.journal.service.JournalFeedServiceUtil} service utility. The
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
 * @see       JournalFeedServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.journal.service.JournalFeedServiceUtil
 * @generated
 */
public class JournalFeedServiceHttp {
	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = feedId;

			if (feedId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new BooleanWrapper(autoFeedId);

			Object paramObj3 = name;

			if (name == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = description;

			if (description == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = type;

			if (type == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = structureId;

			if (structureId == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = templateId;

			if (templateId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = rendererTemplateId;

			if (rendererTemplateId == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = new IntegerWrapper(delta);

			Object paramObj10 = orderByCol;

			if (orderByCol == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = orderByType;

			if (orderByType == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = targetLayoutFriendlyUrl;

			if (targetLayoutFriendlyUrl == null) {
				paramObj12 = new NullWrapper("java.lang.String");
			}

			Object paramObj13 = targetPortletId;

			if (targetPortletId == null) {
				paramObj13 = new NullWrapper("java.lang.String");
			}

			Object paramObj14 = contentField;

			if (contentField == null) {
				paramObj14 = new NullWrapper("java.lang.String");
			}

			Object paramObj15 = feedType;

			if (feedType == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = new DoubleWrapper(feedVersion);

			Object paramObj17 = serviceContext;

			if (serviceContext == null) {
				paramObj17 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalFeedServiceUtil.class.getName(),
					"addFeed",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17
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

			return (com.liferay.portlet.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFeed(HttpPrincipal httpPrincipal, long groupId,
		long feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(feedId);

			MethodWrapper methodWrapper = new MethodWrapper(JournalFeedServiceUtil.class.getName(),
					"deleteFeed", new Object[] { paramObj0, paramObj1 });

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

	public static void deleteFeed(HttpPrincipal httpPrincipal, long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = feedId;

			if (feedId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalFeedServiceUtil.class.getName(),
					"deleteFeed", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portlet.journal.model.JournalFeed getFeed(
		HttpPrincipal httpPrincipal, long groupId, long feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(feedId);

			MethodWrapper methodWrapper = new MethodWrapper(JournalFeedServiceUtil.class.getName(),
					"getFeed", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portlet.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeed getFeed(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = feedId;

			if (feedId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalFeedServiceUtil.class.getName(),
					"getFeed", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portlet.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeed updateFeed(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = feedId;

			if (feedId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = type;

			if (type == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = structureId;

			if (structureId == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = templateId;

			if (templateId == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = rendererTemplateId;

			if (rendererTemplateId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new IntegerWrapper(delta);

			Object paramObj9 = orderByCol;

			if (orderByCol == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = orderByType;

			if (orderByType == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = targetLayoutFriendlyUrl;

			if (targetLayoutFriendlyUrl == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = targetPortletId;

			if (targetPortletId == null) {
				paramObj12 = new NullWrapper("java.lang.String");
			}

			Object paramObj13 = contentField;

			if (contentField == null) {
				paramObj13 = new NullWrapper("java.lang.String");
			}

			Object paramObj14 = feedType;

			if (feedType == null) {
				paramObj14 = new NullWrapper("java.lang.String");
			}

			Object paramObj15 = new DoubleWrapper(feedVersion);

			Object paramObj16 = serviceContext;

			if (serviceContext == null) {
				paramObj16 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(JournalFeedServiceUtil.class.getName(),
					"updateFeed",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16
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

			return (com.liferay.portlet.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFeedServiceHttp.class);
}