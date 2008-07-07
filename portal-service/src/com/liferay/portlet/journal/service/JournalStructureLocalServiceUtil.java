/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalStructureLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.journal.service.JournalStructureLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalStructureLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalStructureLocalService
 * @see com.liferay.portlet.journal.service.JournalStructureLocalServiceFactory
 *
 */
public class JournalStructureLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalStructure addJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.addJournalStructure(journalStructure);
	}

	public static void deleteJournalStructure(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.deleteJournalStructure(id);
	}

	public static void deleteJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.deleteJournalStructure(journalStructure);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.journal.model.JournalStructure getJournalStructure(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getJournalStructure(id);
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateJournalStructure(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.updateJournalStructure(journalStructure);
	}

	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		long userId, java.lang.String structureId, boolean autoStructureId,
		long plid, java.lang.String name, java.lang.String description,
		java.lang.String xsd, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.addStructure(userId, structureId,
			autoStructureId, plid, name, description, xsd,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String uuid, long userId, java.lang.String structureId,
		boolean autoStructureId, long plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.addStructure(uuid, userId,
			structureId, autoStructureId, plid, name, description, xsd,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		long userId, java.lang.String structureId, boolean autoStructureId,
		long plid, java.lang.String name, java.lang.String description,
		java.lang.String xsd, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.addStructure(userId, structureId,
			autoStructureId, plid, name, description, xsd,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalStructure addStructure(
		java.lang.String uuid, long userId, java.lang.String structureId,
		boolean autoStructureId, long plid, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.addStructure(uuid, userId,
			structureId, autoStructureId, plid, name, description, xsd,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalStructure addStructureToGroup(
		java.lang.String uuid, long userId, java.lang.String structureId,
		boolean autoStructureId, long groupId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.addStructureToGroup(uuid, userId,
			structureId, autoStructureId, groupId, name, description, xsd,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addStructureResources(long groupId,
		java.lang.String structureId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.addStructureResources(groupId,
			structureId, addCommunityPermissions, addGuestPermissions);
	}

	public static void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.addStructureResources(structure,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addStructureResources(long groupId,
		java.lang.String structureId, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.addStructureResources(groupId,
			structureId, communityPermissions, guestPermissions);
	}

	public static void addStructureResources(
		com.liferay.portlet.journal.model.JournalStructure structure,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.addStructureResources(structure,
			communityPermissions, guestPermissions);
	}

	public static void checkNewLine(long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.checkNewLine(groupId, structureId);
	}

	public static void deleteStructure(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.deleteStructure(groupId, structureId);
	}

	public static void deleteStructure(
		com.liferay.portlet.journal.model.JournalStructure structure)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.deleteStructure(structure);
	}

	public static void deleteStructures(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		journalStructureLocalService.deleteStructures(groupId);
	}

	public static com.liferay.portlet.journal.model.JournalStructure getStructure(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getStructure(id);
	}

	public static com.liferay.portlet.journal.model.JournalStructure getStructure(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getStructure(groupId, structureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures()
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getStructures();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId) throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getStructures(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> getStructures(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getStructures(groupId, start, end);
	}

	public static int getStructuresCount(long groupId)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.getStructuresCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.search(companyId, groupId,
			keywords, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> search(
		long companyId, long groupId, java.lang.String structureId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.search(companyId, groupId,
			structureId, name, description, andOperator, start, end, obc);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.searchCount(companyId, groupId,
			keywords);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String structureId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.searchCount(companyId, groupId,
			structureId, name, description, andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateStructure(
		long groupId, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalStructureLocalService journalStructureLocalService = JournalStructureLocalServiceFactory.getService();

		return journalStructureLocalService.updateStructure(groupId,
			structureId, name, description, xsd);
	}
}