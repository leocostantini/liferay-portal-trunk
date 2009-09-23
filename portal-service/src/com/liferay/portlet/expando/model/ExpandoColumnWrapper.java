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

package com.liferay.portlet.expando.model;


/**
 * <a href="ExpandoColumnSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ExpandoColumn}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumn
 * @generated
 */
public class ExpandoColumnWrapper implements ExpandoColumn {
	public ExpandoColumnWrapper(ExpandoColumn expandoColumn) {
		_expandoColumn = expandoColumn;
	}

	public long getPrimaryKey() {
		return _expandoColumn.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_expandoColumn.setPrimaryKey(pk);
	}

	public long getColumnId() {
		return _expandoColumn.getColumnId();
	}

	public void setColumnId(long columnId) {
		_expandoColumn.setColumnId(columnId);
	}

	public long getCompanyId() {
		return _expandoColumn.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_expandoColumn.setCompanyId(companyId);
	}

	public long getTableId() {
		return _expandoColumn.getTableId();
	}

	public void setTableId(long tableId) {
		_expandoColumn.setTableId(tableId);
	}

	public java.lang.String getName() {
		return _expandoColumn.getName();
	}

	public void setName(java.lang.String name) {
		_expandoColumn.setName(name);
	}

	public int getType() {
		return _expandoColumn.getType();
	}

	public void setType(int type) {
		_expandoColumn.setType(type);
	}

	public java.lang.String getDefaultData() {
		return _expandoColumn.getDefaultData();
	}

	public void setDefaultData(java.lang.String defaultData) {
		_expandoColumn.setDefaultData(defaultData);
	}

	public java.lang.String getTypeSettings() {
		return _expandoColumn.getTypeSettings();
	}

	public void setTypeSettings(java.lang.String typeSettings) {
		_expandoColumn.setTypeSettings(typeSettings);
	}

	public com.liferay.portlet.expando.model.ExpandoColumn toEscapedModel() {
		return _expandoColumn.toEscapedModel();
	}

	public boolean isNew() {
		return _expandoColumn.isNew();
	}

	public boolean setNew(boolean n) {
		return _expandoColumn.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoColumn.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoColumn.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoColumn.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_expandoColumn.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoColumn.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoColumn.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoColumn.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _expandoColumn.clone();
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		return _expandoColumn.compareTo(expandoColumn);
	}

	public int hashCode() {
		return _expandoColumn.hashCode();
	}

	public java.lang.String toString() {
		return _expandoColumn.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoColumn.toXmlString();
	}

	public java.io.Serializable getDefaultValue() {
		return _expandoColumn.getDefaultValue();
	}

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _expandoColumn.getTypeSettingsProperties();
	}

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_expandoColumn.setTypeSettingsProperties(typeSettingsProperties);
	}

	private ExpandoColumn _expandoColumn;
}