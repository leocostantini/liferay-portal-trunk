/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.Country;

import java.util.List;

/**
 * <a href="CountryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryPersistence
 * @see       CountryPersistenceImpl
 * @generated
 */
public class CountryUtil {
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
	public static Country remove(Country country) throws SystemException {
		return getPersistence().remove(country);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Country update(Country country, boolean merge)
		throws SystemException {
		return getPersistence().update(country, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Country country) {
		getPersistence().cacheResult(country);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Country> countries) {
		getPersistence().cacheResult(countries);
	}

	public static com.liferay.portal.model.Country create(long countryId) {
		return getPersistence().create(countryId);
	}

	public static com.liferay.portal.model.Country remove(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(countryId);
	}

	public static com.liferay.portal.model.Country updateImpl(
		com.liferay.portal.model.Country country, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(country, merge);
	}

	public static com.liferay.portal.model.Country findByPrimaryKey(
		long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(countryId);
	}

	public static com.liferay.portal.model.Country fetchByPrimaryKey(
		long countryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(countryId);
	}

	public static com.liferay.portal.model.Country findByName(
		java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByName(name);
	}

	public static com.liferay.portal.model.Country fetchByName(
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByName(name);
	}

	public static com.liferay.portal.model.Country fetchByName(
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByName(name, retrieveFromCache);
	}

	public static com.liferay.portal.model.Country findByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByA2(a2);
	}

	public static com.liferay.portal.model.Country fetchByA2(
		java.lang.String a2) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByA2(a2);
	}

	public static com.liferay.portal.model.Country fetchByA2(
		java.lang.String a2, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByA2(a2, retrieveFromCache);
	}

	public static com.liferay.portal.model.Country findByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByA3(a3);
	}

	public static com.liferay.portal.model.Country fetchByA3(
		java.lang.String a3) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByA3(a3);
	}

	public static com.liferay.portal.model.Country fetchByA3(
		java.lang.String a3, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByA3(a3, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end, obc);
	}

	public static com.liferay.portal.model.Country findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portal.model.Country findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portal.model.Country[] findByActive_PrevAndNext(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		return getPersistence().findByActive_PrevAndNext(countryId, active, obc);
	}

	public static java.util.List<com.liferay.portal.model.Country> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Country> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Country> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		getPersistence().removeByA2(a2);
	}

	public static void removeByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException {
		getPersistence().removeByA3(a3);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByA2(java.lang.String a2)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByA2(a2);
	}

	public static int countByA3(java.lang.String a3)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByA3(a3);
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static CountryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CountryPersistence)PortalBeanLocatorUtil.locate(CountryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(CountryPersistence persistence) {
		_persistence = persistence;
	}

	private static CountryPersistence _persistence;
}