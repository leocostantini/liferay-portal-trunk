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

package com.liferay.portal.model;

/**
 * <a href="ResourcePermissionModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ResourcePermission table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    ResourcePermission
 * @see    com.liferay.portal.model.impl.ResourcePermissionImpl
 * @see    com.liferay.portal.model.impl.ResourcePermissionModelImpl
 */
public interface ResourcePermissionModel extends BaseModel<ResourcePermission> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getResourcePermissionId();

	public void setResourcePermissionId(long resourcePermissionId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public String getName();

	public void setName(String name);

	public int getScope();

	public void setScope(int scope);

	public String getPrimKey();

	public void setPrimKey(String primKey);

	public long getRoleId();

	public void setRoleId(long roleId);

	public long getActionIds();

	public void setActionIds(long actionIds);

	public ResourcePermission toEscapedModel();
}