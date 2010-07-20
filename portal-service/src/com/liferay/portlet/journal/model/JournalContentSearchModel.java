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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the JournalContentSearch service. Represents a row in the &quot;JournalContentSearch&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. All helper methods and application logic should be put in {@link com.liferay.portlet.journal.model.impl.JournalContentSearchImpl}.
 * </p>
 *
 * <p>
 * Never reference this interface directly. All methods that expect a journal content search model instance should use the {@link JournalContentSearch} interface instead.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearch
 * @see       com.liferay.portlet.journal.model.impl.JournalContentSearchImpl
 * @see       com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl
 * @generated
 */
public interface JournalContentSearchModel extends BaseModel<JournalContentSearch> {
	/**
	 * Gets the primary key of this journal content search.
	 *
	 * @return the primary key of this journal content search
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this journal content search
	 *
	 * @param pk the primary key of this journal content search
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the content search id of this journal content search.
	 *
	 * @return the content search id of this journal content search
	 */
	public long getContentSearchId();

	/**
	 * Sets the content search id of this journal content search.
	 *
	 * @param contentSearchId the content search id of this journal content search
	 */
	public void setContentSearchId(long contentSearchId);

	/**
	 * Gets the group id of this journal content search.
	 *
	 * @return the group id of this journal content search
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this journal content search.
	 *
	 * @param groupId the group id of this journal content search
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the company id of this journal content search.
	 *
	 * @return the company id of this journal content search
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this journal content search.
	 *
	 * @param companyId the company id of this journal content search
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the private layout of this journal content search.
	 *
	 * @return the private layout of this journal content search
	 */
	public boolean getPrivateLayout();

	/**
	 * Determines whether this journal content search is private layout.
	 *
	 * @return whether this journal content search is private layout
	 */
	public boolean isPrivateLayout();

	/**
	 * Sets the private layout of this journal content search.
	 *
	 * @param privateLayout the private layout of this journal content search
	 */
	public void setPrivateLayout(boolean privateLayout);

	/**
	 * Gets the layout id of this journal content search.
	 *
	 * @return the layout id of this journal content search
	 */
	public long getLayoutId();

	/**
	 * Sets the layout id of this journal content search.
	 *
	 * @param layoutId the layout id of this journal content search
	 */
	public void setLayoutId(long layoutId);

	/**
	 * Gets the portlet id of this journal content search.
	 *
	 * @return the portlet id of this journal content search
	 */
	@AutoEscape
	public String getPortletId();

	/**
	 * Sets the portlet id of this journal content search.
	 *
	 * @param portletId the portlet id of this journal content search
	 */
	public void setPortletId(String portletId);

	/**
	 * Gets the article id of this journal content search.
	 *
	 * @return the article id of this journal content search
	 */
	@AutoEscape
	public String getArticleId();

	/**
	 * Sets the article id of this journal content search.
	 *
	 * @param articleId the article id of this journal content search
	 */
	public void setArticleId(String articleId);

	/**
	 * Gets a copy of this journal content search as an escaped model instance by wrapping it with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public JournalContentSearch toEscapedModel();

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

	public int compareTo(JournalContentSearch journalContentSearch);

	public int hashCode();

	public String toString();

	public String toXmlString();
}