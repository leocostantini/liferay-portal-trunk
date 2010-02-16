/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicyRel;

import java.util.List;

/**
 * <a href="PasswordPolicyRelUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyRelPersistence
 * @see       PasswordPolicyRelPersistenceImpl
 * @generated
 */
public class PasswordPolicyRelUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static PasswordPolicyRel remove(PasswordPolicyRel passwordPolicyRel)
		throws SystemException {
		return getPersistence().remove(passwordPolicyRel);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PasswordPolicyRel update(
		PasswordPolicyRel passwordPolicyRel, boolean merge)
		throws SystemException {
		return getPersistence().update(passwordPolicyRel, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel) {
		getPersistence().cacheResult(passwordPolicyRel);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PasswordPolicyRel> passwordPolicyRels) {
		getPersistence().cacheResult(passwordPolicyRels);
	}

	public static com.liferay.portal.model.PasswordPolicyRel create(
		long passwordPolicyRelId) {
		return getPersistence().create(passwordPolicyRelId);
	}

	public static com.liferay.portal.model.PasswordPolicyRel remove(
		long passwordPolicyRelId)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(passwordPolicyRelId);
	}

	public static com.liferay.portal.model.PasswordPolicyRel updateImpl(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(passwordPolicyRel, merge);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByPrimaryKey(
		long passwordPolicyRelId)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(passwordPolicyRelId);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByPrimaryKey(
		long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(passwordPolicyRelId);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPasswordPolicyId(passwordPolicyId);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPasswordPolicyId(passwordPolicyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> findByPasswordPolicyId(
		long passwordPolicyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPasswordPolicyId(passwordPolicyId, start, end, obc);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByPasswordPolicyId_First(
		long passwordPolicyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPasswordPolicyId_First(passwordPolicyId, obc);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByPasswordPolicyId_Last(
		long passwordPolicyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPasswordPolicyId_Last(passwordPolicyId, obc);
	}

	public static com.liferay.portal.model.PasswordPolicyRel[] findByPasswordPolicyId_PrevAndNext(
		long passwordPolicyRelId, long passwordPolicyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPasswordPolicyId_PrevAndNext(passwordPolicyRelId,
			passwordPolicyId, obc);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	public static com.liferay.portal.model.PasswordPolicyRel findByP_C_C(
		long passwordPolicyId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_C_C(passwordPolicyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByP_C_C(
		long passwordPolicyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP_C_C(passwordPolicyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.PasswordPolicyRel fetchByP_C_C(
		long passwordPolicyId, long classNameId, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP_C_C(passwordPolicyId, classNameId, classPK,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.PasswordPolicyRel> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByPasswordPolicyId(long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPasswordPolicyId(passwordPolicyId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByP_C_C(long passwordPolicyId, long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchPasswordPolicyRelException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_C_C(passwordPolicyId, classNameId, classPK);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByPasswordPolicyId(long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPasswordPolicyId(passwordPolicyId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByP_C_C(long passwordPolicyId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByP_C_C(passwordPolicyId, classNameId, classPK);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PasswordPolicyRelPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PasswordPolicyRelPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyRelPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PasswordPolicyRelPersistence persistence) {
		_persistence = persistence;
	}

	private static PasswordPolicyRelPersistence _persistence;
}