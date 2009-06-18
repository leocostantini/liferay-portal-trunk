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

package com.liferay.portlet.asset.service.persistence;

/**
 * <a href="AssetTagStatsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetTagStatsUtil {
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		getPersistence().cacheResult(assetTagStats);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagStats> assetTagStatses) {
		getPersistence().cacheResult(assetTagStatses);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.asset.model.AssetTagStats create(
		long tagStatsId) {
		return getPersistence().create(tagStatsId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats remove(
		long tagStatsId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().remove(tagStatsId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats remove(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(assetTagStats);
	}

	/**
	 * @deprecated Use <code>update(AssetTagStats assetTagStats, boolean merge)</code>.
	 */
	public static com.liferay.portlet.asset.model.AssetTagStats update(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(assetTagStats);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetTagStats the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetTagStats is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.asset.model.AssetTagStats update(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(assetTagStats, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateImpl(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetTagStats, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByPrimaryKey(
		long tagStatsId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByPrimaryKey(tagStatsId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats fetchByPrimaryKey(
		long tagStatsId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(tagStatsId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByClassNameId_First(classNameId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByClassNameId_Last(classNameId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats[] findByClassNameId_PrevAndNext(
		long tagStatsId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(tagStatsId, classNameId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId) throws com.liferay.portal.SystemException {
		return getPersistence().findByTagId(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTagId(tagId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTagId(tagId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByTagId_First(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_First(tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByTagId_Last(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_Last(tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats[] findByTagId_PrevAndNext(
		long tagStatsId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_PrevAndNext(tagStatsId, tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByC_T(
		long classNameId, long tagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByC_T(classNameId, tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats fetchByC_T(
		long classNameId, long tagId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_T(classNameId, tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats fetchByC_T(
		long classNameId, long tagId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_T(classNameId, tagId, retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByClassNameId(classNameId);
	}

	public static void removeByTagId(long tagId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTagId(tagId);
	}

	public static void removeByC_T(long classNameId, long tagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		getPersistence().removeByC_T(classNameId, tagId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByClassNameId(classNameId);
	}

	public static int countByTagId(long tagId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTagId(tagId);
	}

	public static int countByC_T(long classNameId, long tagId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_T(classNameId, tagId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AssetTagStatsPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AssetTagStatsPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetTagStatsPersistence _persistence;
}