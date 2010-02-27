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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.Permission;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="PermissionPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PermissionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PermissionPersistence)PortalBeanLocatorUtil.locate(PermissionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Permission permission = _persistence.create(pk);

		assertNotNull(permission);

		assertEquals(permission.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Permission newPermission = addPermission();

		_persistence.remove(newPermission);

		Permission existingPermission = _persistence.fetchByPrimaryKey(newPermission.getPrimaryKey());

		assertNull(existingPermission);
	}

	public void testUpdateNew() throws Exception {
		addPermission();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Permission newPermission = _persistence.create(pk);

		newPermission.setCompanyId(nextLong());
		newPermission.setActionId(randomString());
		newPermission.setResourceId(nextLong());

		_persistence.update(newPermission, false);

		Permission existingPermission = _persistence.findByPrimaryKey(newPermission.getPrimaryKey());

		assertEquals(existingPermission.getPermissionId(),
			newPermission.getPermissionId());
		assertEquals(existingPermission.getCompanyId(),
			newPermission.getCompanyId());
		assertEquals(existingPermission.getActionId(),
			newPermission.getActionId());
		assertEquals(existingPermission.getResourceId(),
			newPermission.getResourceId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Permission newPermission = addPermission();

		Permission existingPermission = _persistence.findByPrimaryKey(newPermission.getPrimaryKey());

		assertEquals(existingPermission, newPermission);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPermissionException");
		}
		catch (NoSuchPermissionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Permission newPermission = addPermission();

		Permission existingPermission = _persistence.fetchByPrimaryKey(newPermission.getPrimaryKey());

		assertEquals(existingPermission, newPermission);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Permission missingPermission = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPermission);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Permission newPermission = addPermission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Permission.class,
				Permission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("permissionId",
				newPermission.getPermissionId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Permission existingPermission = (Permission)result.get(0);

		assertEquals(existingPermission, newPermission);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Permission.class,
				Permission.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("permissionId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Permission addPermission() throws Exception {
		long pk = nextLong();

		Permission permission = _persistence.create(pk);

		permission.setCompanyId(nextLong());
		permission.setActionId(randomString());
		permission.setResourceId(nextLong());

		_persistence.update(permission, false);

		return permission;
	}

	private PermissionPersistence _persistence;
}