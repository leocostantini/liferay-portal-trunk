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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.dynamicdatamapping.NoSuchContentException;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMContentPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMContentPersistence)PortalBeanLocatorUtil.locate(DDMContentPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		assertNotNull(ddmContent);

		assertEquals(ddmContent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		_persistence.remove(newDDMContent);

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		assertNull(existingDDMContent);
	}

	public void testUpdateNew() throws Exception {
		addDDMContent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMContent newDDMContent = _persistence.create(pk);

		newDDMContent.setUuid(randomString());

		_persistence.update(newDDMContent, false);

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		assertEquals(existingDDMContent.getUuid(), newDDMContent.getUuid());
		assertEquals(existingDDMContent.getContentId(),
			newDDMContent.getContentId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		assertEquals(existingDDMContent, newDDMContent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		assertEquals(existingDDMContent, newDDMContent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMContent missingDDMContent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMContent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDDMContent.getContentId()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMContent existingDDMContent = result.get(0);

		assertEquals(existingDDMContent, newDDMContent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId", nextLong()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DDMContent addDDMContent() throws Exception {
		long pk = nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		ddmContent.setUuid(randomString());

		_persistence.update(ddmContent, false);

		return ddmContent;
	}

	private DDMContentPersistence _persistence;
}