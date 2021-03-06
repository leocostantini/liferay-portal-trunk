/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.StructureStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureLocalServiceImpl
	extends DDMStructureLocalServiceBaseImpl {

	public DDMStructure addStructure(
			long userId, long groupId, String structureKey,
			boolean autoStructureKey, long classNameId, String name,
			String description, String xsd, String storageType,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Dynamic data mapping structure

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());

		structureKey = structureKey.trim().toUpperCase();

		if (autoStructureKey) {
			structureKey = String.valueOf(counterLocalService.increment());
		}

		try {
			xsd = DDMXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		Date now = new Date();

		validate(groupId, structureKey, autoStructureKey, name, xsd);

		long structureId = counterLocalService.increment();

		DDMStructure structure = ddmStructurePersistence.create(structureId);

		structure.setUuid(serviceContext.getUuid());
		structure.setGroupId(serviceContext.getScopeGroupId());
		structure.setClassNameId(classNameId);
		structure.setCompanyId(user.getCompanyId());
		structure.setUserId(user.getUserId());
		structure.setUserName(user.getFullName());
		structure.setCreateDate(serviceContext.getCreateDate(now));
		structure.setModifiedDate(serviceContext.getModifiedDate(now));
		structure.setStructureKey(structureKey);
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);
		structure.setStorageType(storageType);

		ddmStructurePersistence.update(structure, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addStructureResources(
				structure, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addStructureResources(
				structure, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return structure;
	}

	public void addStructureResources(
			DDMStructure structure, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), DDMStructure.class.getName(),
			structure.getStructureKey(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addStructureResources(
			DDMStructure structure, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), DDMStructure.class.getName(),
			structure.getStructureKey(), communityPermissions,
			guestPermissions);
	}

	public void deleteStructure(DDMStructure structure)
		throws PortalException, SystemException {

		// Dynamic data mapping structure

		ddmStructurePersistence.remove(structure);

		// Resources

		resourceLocalService.deleteResource(
			structure.getCompanyId(), DDMStructure.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			structure.getStructureKey());
	}

	public void deleteStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructure structure =
			ddmStructurePersistence.findByG_S(groupId, structureKey);

		deleteStructure(structure);
	}

	public void deleteStructureEntries(long groupId)
		throws PortalException, SystemException {

		for (DDMStructure structure :
				ddmStructurePersistence.findByGroupId(groupId)) {

			deleteStructure(structure);
		}
	}

	public List<DDMStructure> getClassStructureEntries(
			long classNameId, int start, int end)
		throws SystemException {

		return ddmStructurePersistence.findByClassNameId(
			classNameId, start, end);
	}

	public DDMStructure getStructure(long structureId)
		throws PortalException, SystemException {

		return ddmStructurePersistence.findByPrimaryKey(structureId);
	}

	public DDMStructure getStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		return ddmStructurePersistence.findByG_S(groupId, structureKey);
	}

	public List<DDMStructure> getStructureEntries() throws SystemException {
		return ddmStructurePersistence.findAll();
	}

	public List<DDMStructure> getStructureEntries(long groupId)
		throws SystemException {

		return ddmStructurePersistence.findByGroupId(groupId);
	}

	public List<DDMStructure> getStructureEntries(
			long groupId, int start, int end)
		throws SystemException {

		return ddmStructurePersistence.findByGroupId(groupId, start, end);
	}

	public int getStructureEntriesCount(long groupId) throws SystemException {
		return ddmStructurePersistence.countByGroupId(groupId);
	}

	public List<DDMStructure> search(
			long companyId, long groupId, String[] classNameIds,
			String[] structureKeys, String[] storageTypes, String keywords,
			int start, int end, OrderByComparator orderByComparator)
		throws PortalException, SystemException {

		return ddmStructureFinder.findByKeywords(
			companyId, groupId, classNameIds, structureKeys, storageTypes,
			keywords, start, end, orderByComparator);
	}

	public List<DDMStructure> search(
			long companyId, long groupId, String[] classNameIds,
			String[] structureKeys, String[] storageTypes, String[] names,
			String[] descriptions, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException, SystemException {

		return ddmStructureFinder.findByC_G_CN_S_ST_D(
			companyId, groupId, classNameIds, structureKeys, storageTypes,
			names, descriptions, andOperator, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, long groupId, String[] classNameIds,
			String[] structureKeys, String[] storageTypes, String keywords)
		throws PortalException, SystemException {

		return ddmStructureFinder.countByKeywords(
			companyId, groupId, classNameIds, structureKeys, storageTypes,
			keywords);
	}

	public int searchCount(
			long companyId, long groupId, String[] classNameIds,
			String[] structureKeys, String[] storageTypes, String[] names,
			String[] descriptions, boolean andOperator)
		throws PortalException, SystemException {

		return ddmStructureFinder.countByC_G_CN_S_ST_D(
			companyId, groupId, classNameIds, structureKeys, storageTypes,
			names, descriptions, andOperator);
	}

	public DDMStructure updateStructure(
			long groupId, String structureKey, String name, String description,
			String xsd, String storageType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			xsd = DDMXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		DDMStructure structure =
			ddmStructurePersistence.findByG_S(groupId, structureKey);

		validate(groupId, name, xsd);

		structure.setModifiedDate(serviceContext.getModifiedDate(null));
		structure.setName(name);
		structure.setDescription(description);
		structure.setXsd(xsd);
		structure.setStorageType(storageType);

		ddmStructurePersistence.update(structure, false);

		return structure;
	}

	protected void validate(List<Element> elements, Set<String> names)
		throws PortalException {

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("meta-data")) {
				continue;
			}

			String name = element.attributeValue("name", StringPool.BLANK);
			String type = element.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(name) ||
				name.startsWith(DDMStructureConstants.RESERVED)) {

				throw new StructureXsdException();
			}

			char[] charArray = name.toCharArray();

			for (int i = 0; i < charArray.length; i++) {
				if (!Validator.isChar(charArray[i]) &&
					!Validator.isDigit(charArray[i]) &&
					(charArray[i] != CharPool.DASH) &&
					(charArray[i] != CharPool.UNDERLINE)) {

					throw new StructureXsdException();
				}
			}

			String path = name;

			Element parentElement = element.getParent();

			while (!parentElement.isRootElement()) {
				path =
					parentElement.attributeValue("name", StringPool.BLANK) +
						StringPool.SLASH + path;

				parentElement = parentElement.getParent();
			}

			path = path.toLowerCase();

			if (names.contains(path)) {
				throw new StructureDuplicateElementException();
			}
			else {
				names.add(path);
			}

			if (Validator.isNull(type)) {
				throw new StructureXsdException();
			}

			validate(element.elements(), names);
		}
	}

	protected void validate(
			long groupId, String structureKey, boolean autoStructureKey,
			String name, String xsd)
		throws PortalException, SystemException {

		if (!autoStructureKey) {
			validateStructureKey(structureKey);

			DDMStructure structure = ddmStructurePersistence.fetchByG_S(
				groupId, structureKey);

			if (structure != null) {
				throw new StructureDuplicateStructureKeyException();
			}
		}

		validate(groupId, name, xsd);
	}

	protected void validate(long groupId, String name, String xsd)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new StructureNameException();
		}

		if (Validator.isNull(xsd)) {
			throw new StructureXsdException();
		}
		else {
			try {
				List<Element> elements = new ArrayList<Element>();

				Document document = SAXReaderUtil.read(xsd);

				Element rootElement = document.getRootElement();

				if (rootElement.elements().isEmpty()) {
					throw new StructureXsdException();
				}

				elements.addAll(rootElement.elements());

				Set<String> elNames = new HashSet<String>();

				validate(elements, elNames);
			}
			catch (StructureDuplicateElementException fdsee) {
				throw fdsee;
			}
			catch (StructureXsdException sxe) {
				throw sxe;
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}
		}
	}

	protected void validateStructureKey(String structureKey)
		throws PortalException {

		if (Validator.isNull(structureKey) ||
			Validator.isNumber(structureKey) ||
			structureKey.contains(StringPool.SPACE)) {

			throw new StructureStructureKeyException();
		}
	}

}