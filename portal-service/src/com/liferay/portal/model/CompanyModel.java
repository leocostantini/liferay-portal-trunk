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
 * <a href="CompanyModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Company table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    Company
 * @see    com.liferay.portal.model.impl.CompanyImpl
 * @see    com.liferay.portal.model.impl.CompanyModelImpl
 */
public interface CompanyModel extends BaseModel<Company> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getAccountId();

	public void setAccountId(long accountId);

	public String getWebId();

	public void setWebId(String webId);

	public String getKey();

	public void setKey(String key);

	public String getVirtualHost();

	public void setVirtualHost(String virtualHost);

	public String getMx();

	public void setMx(String mx);

	public String getHomeURL();

	public void setHomeURL(String homeURL);

	public long getLogoId();

	public void setLogoId(long logoId);

	public boolean getSystem();

	public boolean isSystem();

	public void setSystem(boolean system);

	public Company toEscapedModel();
}