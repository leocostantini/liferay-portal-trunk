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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the DDMContent service. Represents a row in the &quot;DDMContent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMContent
 * @see com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentImpl
 * @see com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl
 * @generated
 */
public interface DDMContentModel extends BaseModel<DDMContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a d d m content model instance should use the {@link DDMContent} interface instead.
	 */

	/**
	 * Gets the primary key of this d d m content.
	 *
	 * @return the primary key of this d d m content
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this d d m content
	 *
	 * @param pk the primary key of this d d m content
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the uuid of this d d m content.
	 *
	 * @return the uuid of this d d m content
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this d d m content.
	 *
	 * @param uuid the uuid of this d d m content
	 */
	public void setUuid(String uuid);

	/**
	 * Gets the content ID of this d d m content.
	 *
	 * @return the content ID of this d d m content
	 */
	public long getContentId();

	/**
	 * Sets the content ID of this d d m content.
	 *
	 * @param contentId the content ID of this d d m content
	 */
	public void setContentId(long contentId);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(DDMContent ddmContent);

	public int hashCode();

	public DDMContent toEscapedModel();

	public String toString();

	public String toXmlString();
}