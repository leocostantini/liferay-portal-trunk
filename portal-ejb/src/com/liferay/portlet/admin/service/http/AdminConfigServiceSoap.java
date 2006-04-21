/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.admin.service.http;

import com.liferay.portal.shared.util.StackTraceUtil;

import com.liferay.portlet.admin.service.spring.AdminConfigServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="AdminConfigServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AdminConfigServiceSoap {
	public static com.liferay.portlet.admin.model.AdminConfigModel[] getAdminConfig(
		java.lang.String companyId, java.lang.String type)
		throws RemoteException {
		try {
			java.util.List returnValue = AdminConfigServiceUtil.getAdminConfig(companyId,
					type);

			return (com.liferay.portlet.admin.model.AdminConfig[])returnValue.toArray(new com.liferay.portlet.admin.model.AdminConfig[0]);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.admin.model.JournalConfig getJournalConfig(
		java.lang.String companyId, java.lang.String portletId)
		throws RemoteException {
		try {
			com.liferay.portlet.admin.model.JournalConfig returnValue = AdminConfigServiceUtil.getJournalConfig(companyId,
					portletId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.admin.model.ShoppingConfig getShoppingConfig(
		java.lang.String companyId) throws RemoteException {
		try {
			com.liferay.portlet.admin.model.ShoppingConfig returnValue = AdminConfigServiceUtil.getShoppingConfig(companyId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.admin.model.UserConfig getUserConfig(
		java.lang.String companyId) throws RemoteException {
		try {
			com.liferay.portlet.admin.model.UserConfig returnValue = AdminConfigServiceUtil.getUserConfig(companyId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void updateJournalConfig(
		com.liferay.portlet.admin.model.JournalConfig journalConfig,
		java.lang.String portletId) throws RemoteException {
		try {
			AdminConfigServiceUtil.updateJournalConfig(journalConfig, portletId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void updateShoppingConfig(
		com.liferay.portlet.admin.model.ShoppingConfig shoppingConfig)
		throws RemoteException {
		try {
			AdminConfigServiceUtil.updateShoppingConfig(shoppingConfig);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void updateUserConfig(
		com.liferay.portlet.admin.model.UserConfig userConfig)
		throws RemoteException {
		try {
			AdminConfigServiceUtil.updateUserConfig(userConfig);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(AdminConfigServiceSoap.class);
}