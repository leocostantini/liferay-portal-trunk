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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="UserTrackerPathPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
@Transactional
public interface UserTrackerPathPersistence {
	public com.liferay.portal.model.UserTrackerPath create(
		long userTrackerPathId);

	public com.liferay.portal.model.UserTrackerPath remove(
		long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserTrackerPath remove(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(UserTrackerPath userTrackerPath, boolean merge)</code>.
	 */
	public com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        userTrackerPath the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when userTrackerPath is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.UserTrackerPath update(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserTrackerPath findByPrimaryKey(
		long userTrackerPathId)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserTrackerPath fetchByPrimaryKey(
		long userTrackerPathId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findByUserTrackerId(
		long userTrackerId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserTrackerPath findByUserTrackerId_First(
		long userTrackerId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserTrackerPath findByUserTrackerId_Last(
		long userTrackerId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserTrackerPathException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findAll()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.UserTrackerPath> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUserTrackerId(long userTrackerId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByUserTrackerId(long userTrackerId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}