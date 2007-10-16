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

package com.liferay.portlet.ratings.service.base;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;

import com.liferay.portlet.ratings.service.RatingsEntryLocalService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceFactory;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryUtil;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsUtil;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <a href="RatingsEntryLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class RatingsEntryLocalServiceBaseImpl
	implements RatingsEntryLocalService, InitializingBean {
	public List dynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		return RatingsEntryUtil.findWithDynamicQuery(queryInitializer);
	}

	public List dynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		return RatingsEntryUtil.findWithDynamicQuery(queryInitializer, begin,
			end);
	}

	public RatingsEntryPersistence getRatingsEntryPersistence() {
		return ratingsEntryPersistence;
	}

	public void setRatingsEntryPersistence(
		RatingsEntryPersistence ratingsEntryPersistence) {
		this.ratingsEntryPersistence = ratingsEntryPersistence;
	}

	public RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	public void setRatingsStatsLocalService(
		RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
	}

	public void afterPropertiesSet() {
		if (ratingsEntryPersistence == null) {
			ratingsEntryPersistence = RatingsEntryUtil.getPersistence();
		}

		if (ratingsStatsLocalService == null) {
			ratingsStatsLocalService = RatingsStatsLocalServiceFactory.getImpl();
		}

		if (ratingsStatsPersistence == null) {
			ratingsStatsPersistence = RatingsStatsUtil.getPersistence();
		}
	}

	protected RatingsEntryPersistence ratingsEntryPersistence;
	protected RatingsStatsLocalService ratingsStatsLocalService;
	protected RatingsStatsPersistence ratingsStatsPersistence;
}