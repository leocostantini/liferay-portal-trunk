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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OrganizationUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationUtil {
	public static com.liferay.portal.model.Organization create(
		java.lang.String organizationId) {
		return getPersistence().create(organizationId);
	}

	public static com.liferay.portal.model.Organization remove(
		java.lang.String organizationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(organizationId));
		}

		com.liferay.portal.model.Organization organization = getPersistence()
																 .remove(organizationId);

		if (listener != null) {
			listener.onAfterRemove(organization);
		}

		return organization;
	}

	public static com.liferay.portal.model.Organization remove(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(organization);
		}

		organization = getPersistence().remove(organization);

		if (listener != null) {
			listener.onAfterRemove(organization);
		}

		return organization;
	}

	public static com.liferay.portal.model.Organization update(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = organization.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(organization);
			}
			else {
				listener.onBeforeUpdate(organization);
			}
		}

		organization = getPersistence().update(organization);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(organization);
			}
			else {
				listener.onAfterUpdate(organization);
			}
		}

		return organization;
	}

	public static com.liferay.portal.model.Organization update(
		com.liferay.portal.model.Organization organization, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = organization.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(organization);
			}
			else {
				listener.onBeforeUpdate(organization);
			}
		}

		organization = getPersistence().update(organization, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(organization);
			}
			else {
				listener.onAfterUpdate(organization);
			}
		}

		return organization;
	}

	public static com.liferay.portal.model.Organization findByPrimaryKey(
		java.lang.String organizationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByPrimaryKey(organizationId);
	}

	public static com.liferay.portal.model.Organization fetchByPrimaryKey(
		java.lang.String organizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(organizationId);
	}

	public static java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.Organization findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Organization findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Organization[] findByCompanyId_PrevAndNext(
		java.lang.String organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByCompanyId_PrevAndNext(organizationId,
			companyId, obc);
	}

	public static java.util.List findByLocations(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByLocations(companyId);
	}

	public static java.util.List findByLocations(long companyId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByLocations(companyId, begin, end);
	}

	public static java.util.List findByLocations(long companyId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByLocations(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.Organization findByLocations_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByLocations_First(companyId, obc);
	}

	public static com.liferay.portal.model.Organization findByLocations_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByLocations_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Organization[] findByLocations_PrevAndNext(
		java.lang.String organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByLocations_PrevAndNext(organizationId,
			companyId, obc);
	}

	public static java.util.List findByC_P(long companyId,
		java.lang.String parentOrganizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_P(companyId, parentOrganizationId);
	}

	public static java.util.List findByC_P(long companyId,
		java.lang.String parentOrganizationId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_P(companyId, parentOrganizationId,
			begin, end);
	}

	public static java.util.List findByC_P(long companyId,
		java.lang.String parentOrganizationId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_P(companyId, parentOrganizationId,
			begin, end, obc);
	}

	public static com.liferay.portal.model.Organization findByC_P_First(
		long companyId, java.lang.String parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByC_P_First(companyId,
			parentOrganizationId, obc);
	}

	public static com.liferay.portal.model.Organization findByC_P_Last(
		long companyId, java.lang.String parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByC_P_Last(companyId, parentOrganizationId,
			obc);
	}

	public static com.liferay.portal.model.Organization[] findByC_P_PrevAndNext(
		java.lang.String organizationId, long companyId,
		java.lang.String parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByC_P_PrevAndNext(organizationId,
			companyId, parentOrganizationId, obc);
	}

	public static com.liferay.portal.model.Organization findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Organization fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByLocations(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByLocations(companyId);
	}

	public static void removeByC_P(long companyId,
		java.lang.String parentOrganizationId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_P(companyId, parentOrganizationId);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByLocations(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLocations(companyId);
	}

	public static int countByC_P(long companyId,
		java.lang.String parentOrganizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_P(companyId, parentOrganizationId);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List getGroups(java.lang.String pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List getGroups(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().getGroups(pk, begin, end);
	}

	public static java.util.List getGroups(java.lang.String pk, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().getGroups(pk, begin, end, obc);
	}

	public static int getGroupsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static boolean containsGroup(java.lang.String pk, long groupPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	public static boolean containsGroups(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsGroups(pk);
	}

	public static void addGroup(java.lang.String pk, long groupPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().addGroup(pk, groupPK);
	}

	public static void addGroup(java.lang.String pk,
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().addGroup(pk, group);
	}

	public static void addGroups(java.lang.String pk, long[] groupPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().addGroups(pk, groupPKs);
	}

	public static void addGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(java.lang.String pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().clearGroups(pk);
	}

	public static void removeGroup(java.lang.String pk, long groupPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().removeGroup(pk, groupPK);
	}

	public static void removeGroup(java.lang.String pk,
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().removeGroup(pk, group);
	}

	public static void removeGroups(java.lang.String pk, long[] groupPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	public static void removeGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().removeGroups(pk, groups);
	}

	public static void setGroups(java.lang.String pk, long[] groupPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().setGroups(pk, groupPKs);
	}

	public static void setGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().setGroups(pk, groups);
	}

	public static java.util.List getUsers(java.lang.String pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().getUsers(pk, begin, end);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		return getPersistence().getUsers(pk, begin, end, obc);
	}

	public static int getUsersSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static boolean containsUser(java.lang.String pk, long userPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static void addUser(java.lang.String pk, long userPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().addUser(pk, userPK);
	}

	public static void addUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().addUser(pk, user);
	}

	public static void addUsers(java.lang.String pk, long[] userPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().addUsers(pk, userPKs);
	}

	public static void addUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(java.lang.String pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException {
		getPersistence().clearUsers(pk);
	}

	public static void removeUser(java.lang.String pk, long userPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().removeUser(pk, userPK);
	}

	public static void removeUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().removeUser(pk, user);
	}

	public static void removeUsers(java.lang.String pk, long[] userPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().removeUsers(pk, userPKs);
	}

	public static void removeUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().removeUsers(pk, users);
	}

	public static void setUsers(java.lang.String pk, long[] userPKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().setUsers(pk, userPKs);
	}

	public static void setUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException {
		getPersistence().setUsers(pk, users);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static OrganizationPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(OrganizationPersistence persistence) {
		_persistence = persistence;
	}

	private static OrganizationUtil _getUtil() {
		if (_util == null) {
			_util = (OrganizationUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = OrganizationUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Organization"));
	private static Log _log = LogFactory.getLog(OrganizationUtil.class);
	private static OrganizationUtil _util;
	private OrganizationPersistence _persistence;
}