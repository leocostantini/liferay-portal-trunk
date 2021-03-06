/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util.comparator;

import com.liferay.documentlibrary.model.Content;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class ContentVersionComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "version ASC";

	public static String ORDER_BY_DESC = "version DESC";

	public static String[] ORDER_BY_FIELDS = {"version"};

	public ContentVersionComparator() {
		this(false);
	}

	public ContentVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		Content content1 = (Content)obj1;
		Content content2 = (Content)obj2;

		int value = content1.getVersion().compareTo(content2.getVersion());

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