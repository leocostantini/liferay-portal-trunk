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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.Set;

/**
 * <a href="PortletDataContext.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Holds context information that is used during exporting adn importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletDataContext implements Serializable {

	public PortletDataContext(String companyId, long groupId,
							  Set primaryKeys) {

		_companyId = companyId;
		_groupId = groupId;
		_primaryKeys = primaryKeys;
	}

	public String getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Set getPrimaryKeys() {
		return _primaryKeys;
	}

	public void addPrimaryKey(Class classObj, Object primaryKey) {
		_primaryKeys.add(
			classObj.getName() + StringPool.POUND + primaryKey);
	}

	public boolean checkPrimaryKey(Class classObj, Object primaryKey) {
		boolean value = hasPrimaryKey(classObj, primaryKey);

		if (!value) {
			addPrimaryKey(classObj, primaryKey);
		}

		return value;
	}

	public boolean hasPrimaryKey(Class classObj, Object primaryKey) {
		return _primaryKeys.contains(
			classObj.getName() + StringPool.POUND + primaryKey);
	}

	private String _companyId;
	private long _groupId;
	private Set _primaryKeys;

}