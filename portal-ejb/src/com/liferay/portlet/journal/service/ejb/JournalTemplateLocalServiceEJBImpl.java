/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.ejb;

import com.liferay.portlet.journal.service.JournalTemplateLocalService;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="JournalTemplateLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalTemplateLocalService
 * @see com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil
 * @see com.liferay.portlet.journal.service.ejb.JournalTemplateLocalServiceEJB
 * @see com.liferay.portlet.journal.service.ejb.JournalTemplateLocalServiceHome
 * @see com.liferay.portlet.journal.service.impl.JournalTemplateLocalServiceImpl
 *
 */
public class JournalTemplateLocalServiceEJBImpl
	implements JournalTemplateLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, java.lang.String templateId, boolean autoTemplateId,
		java.lang.String plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().addTemplate(userId,
			templateId, autoTemplateId, plid, structureId, name, description,
			xsl, formatXsl, langType, smallImage, smallImageURL, smallFile,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, java.lang.String templateId, boolean autoTemplateId,
		java.lang.String plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().addTemplate(userId,
			templateId, autoTemplateId, plid, structureId, name, description,
			xsl, formatXsl, langType, smallImage, smallImageURL, smallFile,
			communityPermissions, guestPermissions);
	}

	public com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		long userId, java.lang.String templateId, boolean autoTemplateId,
		java.lang.String plid, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().addTemplate(userId,
			templateId, autoTemplateId, plid, structureId, name, description,
			xsl, formatXsl, langType, smallImage, smallImageURL, smallFile,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public void addTemplateResources(java.lang.String companyId, long groupId,
		java.lang.String templateId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().addTemplateResources(companyId,
			groupId, templateId, addCommunityPermissions, addGuestPermissions);
	}

	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().addTemplateResources(template,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addTemplateResources(java.lang.String companyId, long groupId,
		java.lang.String templateId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().addTemplateResources(companyId,
			groupId, templateId, communityPermissions, guestPermissions);
	}

	public void addTemplateResources(
		com.liferay.portlet.journal.model.JournalTemplate template,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().addTemplateResources(template,
			communityPermissions, guestPermissions);
	}

	public void checkNewLine(java.lang.String companyId, long groupId,
		java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().checkNewLine(companyId,
			groupId, templateId);
	}

	public void deleteTemplate(java.lang.String companyId, long groupId,
		java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().deleteTemplate(companyId,
			groupId, templateId);
	}

	public void deleteTemplate(
		com.liferay.portlet.journal.model.JournalTemplate template)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalTemplateLocalServiceFactory.getTxImpl().deleteTemplate(template);
	}

	public java.util.List getStructureTemplates(java.lang.String companyId,
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl()
												 .getStructureTemplates(companyId,
			groupId, structureId);
	}

	public java.util.List getStructureTemplates(java.lang.String companyId,
		long groupId, java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl()
												 .getStructureTemplates(companyId,
			groupId, structureId, begin, end);
	}

	public int getStructureTemplatesCount(java.lang.String companyId,
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl()
												 .getStructureTemplatesCount(companyId,
			groupId, structureId);
	}

	public com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		java.lang.String companyId, long groupId, java.lang.String templateId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().getTemplate(companyId,
			groupId, templateId);
	}

	public java.util.List getTemplates()
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().getTemplates();
	}

	public java.util.List getTemplates(long groupId)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().getTemplates(groupId);
	}

	public java.util.List getTemplates(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().getTemplates(groupId,
			begin, end);
	}

	public int getTemplatesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().getTemplatesCount(groupId);
	}

	public java.util.List search(java.lang.String companyId, long groupId,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().search(companyId,
			groupId, templateId, structureId, structureIdComparator, name,
			description, andOperator, begin, end, obc);
	}

	public int searchCount(java.lang.String companyId, long groupId,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().searchCount(companyId,
			groupId, templateId, structureId, structureIdComparator, name,
			description, andOperator);
	}

	public com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		java.lang.String companyId, long groupId, java.lang.String templateId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsl, boolean formatXsl,
		java.lang.String langType, boolean smallImage,
		java.lang.String smallImageURL, java.io.File smallFile)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return JournalTemplateLocalServiceFactory.getTxImpl().updateTemplate(companyId,
			groupId, templateId, structureId, name, description, xsl,
			formatXsl, langType, smallImage, smallImageURL, smallFile);
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