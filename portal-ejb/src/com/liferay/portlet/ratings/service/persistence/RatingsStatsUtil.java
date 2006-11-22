/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="RatingsStatsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RatingsStatsUtil {
	public static com.liferay.portlet.ratings.model.RatingsStats create(
		long statsId) {
		return getPersistence().create(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats remove(
		long statsId)
		throws com.liferay.portlet.ratings.NoSuchStatsException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(statsId));
		}

		com.liferay.portlet.ratings.model.RatingsStats ratingsStats = getPersistence()
																		  .remove(statsId);

		if (listener != null) {
			listener.onAfterRemove(ratingsStats);
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats remove(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(ratingsStats);
		}

		ratingsStats = getPersistence().remove(ratingsStats);

		if (listener != null) {
			listener.onAfterRemove(ratingsStats);
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats update(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = ratingsStats.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(ratingsStats);
			}
			else {
				listener.onBeforeUpdate(ratingsStats);
			}
		}

		ratingsStats = getPersistence().update(ratingsStats);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(ratingsStats);
			}
			else {
				listener.onAfterUpdate(ratingsStats);
			}
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats update(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(_LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = ratingsStats.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(ratingsStats);
			}
			else {
				listener.onBeforeUpdate(ratingsStats);
			}
		}

		ratingsStats = getPersistence().update(ratingsStats, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(ratingsStats);
			}
			else {
				listener.onAfterUpdate(ratingsStats);
			}
		}

		return ratingsStats;
	}

	public static com.liferay.portlet.ratings.model.RatingsStats findByPrimaryKey(
		long statsId)
		throws com.liferay.portlet.ratings.NoSuchStatsException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats fetchByPrimaryKey(
		long statsId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(statsId);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats findByC_C(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portlet.ratings.NoSuchStatsException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C(className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsStats fetchByC_C(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(className, classPK);
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByC_C(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portlet.ratings.NoSuchStatsException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_C(className, classPK);
	}

	public static int countByC_C(java.lang.String className,
		java.lang.String classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(className, classPK);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static RatingsStatsPersistence getPersistence() {
		if (_util == null) {
			ApplicationContext ctx = SpringUtil.getContext();
			_util = (RatingsStatsUtil)ctx.getBean(_UTIL);
		}

		return _util._persistence;
	}

	public void setPersistence(RatingsStatsPersistence persistence) {
		_persistence = persistence;
	}

	private static final String _UTIL = RatingsStatsUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.ratings.model.RatingsStats"));
	private static Log _log = LogFactory.getLog(RatingsStatsUtil.class);
	private static RatingsStatsUtil _util;
	private RatingsStatsPersistence _persistence;
}