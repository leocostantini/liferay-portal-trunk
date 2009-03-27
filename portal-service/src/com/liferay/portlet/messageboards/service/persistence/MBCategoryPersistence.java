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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="MBCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface MBCategoryPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory);

	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBCategory> mbCategories);

	public com.liferay.portlet.messageboards.model.MBCategory create(
		long categoryId);

	public com.liferay.portlet.messageboards.model.MBCategory remove(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory remove(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(MBCategory mbCategory, boolean merge)</code>.
	 */
	public com.liferay.portlet.messageboards.model.MBCategory update(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbCategory the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbCategory is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.messageboards.model.MBCategory update(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBCategory updateImpl(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory fetchByPrimaryKey(
		long categoryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBCategory findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory[] findByCompanyId_PrevAndNext(
		long categoryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory findByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}