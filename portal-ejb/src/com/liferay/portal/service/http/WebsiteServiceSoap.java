/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.WebsiteServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="WebsiteServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsiteServiceSoap {
	public static com.liferay.portal.model.WebsiteSoap addWebsite(
		java.lang.String className, java.lang.String classPK,
		java.lang.String url, int typeId, boolean primary)
		throws RemoteException {
		try {
			com.liferay.portal.model.Website returnValue = WebsiteServiceUtil.addWebsite(className,
					classPK, url, typeId, primary);

			return com.liferay.portal.model.WebsiteSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteWebsite(long websiteId) throws RemoteException {
		try {
			WebsiteServiceUtil.deleteWebsite(websiteId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.WebsiteSoap getWebsite(
		long websiteId) throws RemoteException {
		try {
			com.liferay.portal.model.Website returnValue = WebsiteServiceUtil.getWebsite(websiteId);

			return com.liferay.portal.model.WebsiteSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.WebsiteSoap[] getWebsites(
		java.lang.String className, java.lang.String classPK)
		throws RemoteException {
		try {
			java.util.List returnValue = WebsiteServiceUtil.getWebsites(className,
					classPK);

			return com.liferay.portal.model.WebsiteSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.WebsiteSoap updateWebsite(
		long websiteId, java.lang.String url, int typeId, boolean primary)
		throws RemoteException {
		try {
			com.liferay.portal.model.Website returnValue = WebsiteServiceUtil.updateWebsite(websiteId,
					url, typeId, primary);

			return com.liferay.portal.model.WebsiteSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WebsiteServiceSoap.class);
}