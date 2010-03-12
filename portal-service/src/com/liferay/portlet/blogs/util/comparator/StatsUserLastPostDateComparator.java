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

package com.liferay.portlet.blogs.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.blogs.model.BlogsStatsUser;

/**
 * <a href="StatsUserLastPostDateComparator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class StatsUserLastPostDateComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "lastPostDate ASC";

	public static String ORDER_BY_DESC = "lastPostDate DESC";

	public static String[] ORDER_BY_FIELDS = {"lastPostDate"};

	public StatsUserLastPostDateComparator() {
		this(false);
	}

	public StatsUserLastPostDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		BlogsStatsUser statsUser1 = (BlogsStatsUser)obj1;
		BlogsStatsUser statsUser2 = (BlogsStatsUser)obj2;

		int value = DateUtil.compareTo(
			statsUser1.getLastPostDate(), statsUser2.getLastPostDate());

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}