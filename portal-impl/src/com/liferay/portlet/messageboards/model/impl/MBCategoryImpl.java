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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.PortalException;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

import java.util.List;
import java.util.ArrayList;

/**
 * <a href="MBCategoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBCategoryImpl extends MBCategoryModelImpl implements MBCategory {

	public static long DEFAULT_PARENT_CATEGORY_ID = 0;

	public MBCategoryImpl() {
	}

	public List getAncestorCategoryIds() throws PortalException, SystemException {
		List ancestorCategoryIds = new ArrayList();

		MBCategory category = this;

		while (true) {
			if (!category.isRoot()) {
				category = MBCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());

				ancestorCategoryIds.add(new Long(category.getCategoryId()));
			}
			else {
				break;
			}
		}

		return ancestorCategoryIds;
	}

	public List getAncestors() throws PortalException, SystemException {
		List ancestors = new ArrayList();

		MBCategory category = this;

		while (true) {
			if (!category.isRoot()) {
				category = MBCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());

				ancestors.add(category);
			}
			else {
				break;
			}
		}

		return ancestors;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public boolean isRoot() {
		if (getParentCategoryId() == DEFAULT_PARENT_CATEGORY_ID) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDiscussion() {
		if (getCategoryId() == CompanyImpl.SYSTEM) {
			return true;
		}
		else {
			return false;
		}
	}

	private String _userUuid;

}