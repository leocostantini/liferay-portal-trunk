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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLFileRankUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileRankUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileRank create(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK) {
		return getPersistence().create(dlFileRankPK);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(dlFileRankPK));
		}

		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank = getPersistence()
																			  .remove(dlFileRankPK);

		if (listener != null) {
			listener.onAfterRemove(dlFileRank);
		}

		return dlFileRank;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(dlFileRank);
		}

		dlFileRank = getPersistence().remove(dlFileRank);

		if (listener != null) {
			listener.onAfterRemove(dlFileRank);
		}

		return dlFileRank;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank update(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = dlFileRank.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileRank);
			}
			else {
				listener.onBeforeUpdate(dlFileRank);
			}
		}

		dlFileRank = getPersistence().update(dlFileRank);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileRank);
			}
			else {
				listener.onAfterUpdate(dlFileRank);
			}
		}

		return dlFileRank;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank update(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = dlFileRank.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileRank);
			}
			else {
				listener.onBeforeUpdate(dlFileRank);
			}
		}

		dlFileRank = getPersistence().update(dlFileRank, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileRank);
			}
			else {
				listener.onAfterUpdate(dlFileRank);
			}
		}

		return dlFileRank;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByPrimaryKey(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByPrimaryKey(dlFileRankPK);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByPrimaryKey(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(dlFileRankPK);
	}

	public static java.util.List findByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByUserId_PrevAndNext(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK,
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_PrevAndNext(dlFileRankPK, userId,
			obc);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name, begin, end);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_First(
		java.lang.String folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByF_N_First(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_Last(
		java.lang.String folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByF_N_Last(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByF_N_PrevAndNext(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK,
		java.lang.String folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByF_N_PrevAndNext(dlFileRankPK, folderId,
			name, obc);
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

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		getPersistence().removeByF_N(folderId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().countByF_N(folderId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static DLFileRankPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(DLFileRankPersistence persistence) {
		_persistence = persistence;
	}

	private static DLFileRankUtil _getUtil() {
		if (_util == null) {
			_util = (DLFileRankUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = DLFileRankUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileRank"));
	private static Log _log = LogFactory.getLog(DLFileRankUtil.class);
	private static DLFileRankUtil _util;
	private DLFileRankPersistence _persistence;
}