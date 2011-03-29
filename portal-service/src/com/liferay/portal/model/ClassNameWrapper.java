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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link ClassName}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassName
 * @generated
 */
public class ClassNameWrapper implements ClassName {
	public ClassNameWrapper(ClassName className) {
		_className = className;
	}

	public Class<?> getModelClass() {
		return ClassName.class;
	}

	public String getModelClassName() {
		return ClassName.class.getName();
	}

	/**
	* Gets the primary key of this class name.
	*
	* @return the primary key of this class name
	*/
	public long getPrimaryKey() {
		return _className.getPrimaryKey();
	}

	/**
	* Sets the primary key of this class name
	*
	* @param pk the primary key of this class name
	*/
	public void setPrimaryKey(long pk) {
		_className.setPrimaryKey(pk);
	}

	/**
	* Gets the class name of the model instance this class name is polymorphically associated with.
	*
	* @return the class name of the model instance this class name is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _className.getClassName();
	}

	/**
	* Gets the class name ID of this class name.
	*
	* @return the class name ID of this class name
	*/
	public long getClassNameId() {
		return _className.getClassNameId();
	}

	/**
	* Sets the class name ID of this class name.
	*
	* @param classNameId the class name ID of this class name
	*/
	public void setClassNameId(long classNameId) {
		_className.setClassNameId(classNameId);
	}

	/**
	* Gets the value of this class name.
	*
	* @return the value of this class name
	*/
	public java.lang.String getValue() {
		return _className.getValue();
	}

	/**
	* Sets the value of this class name.
	*
	* @param value the value of this class name
	*/
	public void setValue(java.lang.String value) {
		_className.setValue(value);
	}

	public boolean isNew() {
		return _className.isNew();
	}

	public void setNew(boolean n) {
		_className.setNew(n);
	}

	public boolean isCachedModel() {
		return _className.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_className.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _className.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_className.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _className.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _className.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_className.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new ClassNameWrapper((ClassName)_className.clone());
	}

	public int compareTo(com.liferay.portal.model.ClassName className) {
		return _className.compareTo(className);
	}

	public int hashCode() {
		return _className.hashCode();
	}

	public com.liferay.portal.model.ClassName toEscapedModel() {
		return new ClassNameWrapper(_className.toEscapedModel());
	}

	public java.lang.String toString() {
		return _className.toString();
	}

	public java.lang.String toXmlString() {
		return _className.toXmlString();
	}

	public ClassName getWrappedClassName() {
		return _className;
	}

	public void resetOriginalValues() {
		_className.resetOriginalValues();
	}

	private ClassName _className;
}