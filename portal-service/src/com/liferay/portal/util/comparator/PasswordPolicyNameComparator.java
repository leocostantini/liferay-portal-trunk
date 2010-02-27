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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PasswordPolicy;

/**
 * <a href="PasswordPolicyNameComparator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class PasswordPolicyNameComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "PasswordPolicy.name ASC";

	public static String ORDER_BY_DESC = "PasswordPolicy.name DESC";

	public static String[] ORDER_BY_FIELDS = {"name"};

	public PasswordPolicyNameComparator() {
		this(false);
	}

	public PasswordPolicyNameComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		PasswordPolicy passwordPolicy1 = (PasswordPolicy)obj1;
		PasswordPolicy passwordPolicy2 = (PasswordPolicy)obj2;

		int value = passwordPolicy1.getName().compareTo(
			passwordPolicy2.getName());

		if (_asc) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_asc) {
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
		return _asc;
	}

	private boolean _asc;

}