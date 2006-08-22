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

package com.liferay.portlet.alfresco.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.alfresco.service.spring.AlfrescoContentLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="AlfrescoContentLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AlfrescoContentLocalServiceEJBImpl
	implements AlfrescoContentLocalService, SessionBean {
	public static final String CLASS_NAME = AlfrescoContentLocalService.class.getName() +
		".transaction";

	public static AlfrescoContentLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (AlfrescoContentLocalService)ctx.getBean(CLASS_NAME);
	}

	public org.alfresco.webservice.types.ResultSetRow[] getNodes(
		java.lang.String uuid, java.lang.String alfrescoWebClientURL,
		java.lang.String userId, java.lang.String password)
		throws com.liferay.portal.PortalException {
		return getService().getNodes(uuid, alfrescoWebClientURL, userId,
			password);
	}

	public java.lang.String getContent(java.lang.String uuid,
		java.lang.String path, java.lang.String alfrescoWebClientURL,
		java.lang.String userId, java.lang.String password)
		throws com.liferay.portal.PortalException {
		return getService().getContent(uuid, path, alfrescoWebClientURL,
			userId, password);
	}

	public java.lang.String _getContent(
		org.alfresco.webservice.content.Content content)
		throws java.lang.Exception {
		return getService()._getContent(content);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}