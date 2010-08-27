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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the ShoppingItem service. Represents a row in the &quot;ShoppingItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.shopping.model.impl.ShoppingItemImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this interface directly. All methods that expect a shopping item model instance should use the {@link ShoppingItem} interface instead.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItem
 * @see com.liferay.portlet.shopping.model.impl.ShoppingItemImpl
 * @see com.liferay.portlet.shopping.model.impl.ShoppingItemModelImpl
 * @generated
 */
public interface ShoppingItemModel extends BaseModel<ShoppingItem> {
	/**
	 * Gets the primary key of this shopping item.
	 *
	 * @return the primary key of this shopping item
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this shopping item
	 *
	 * @param pk the primary key of this shopping item
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the item id of this shopping item.
	 *
	 * @return the item id of this shopping item
	 */
	public long getItemId();

	/**
	 * Sets the item id of this shopping item.
	 *
	 * @param itemId the item id of this shopping item
	 */
	public void setItemId(long itemId);

	/**
	 * Gets the group id of this shopping item.
	 *
	 * @return the group id of this shopping item
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this shopping item.
	 *
	 * @param groupId the group id of this shopping item
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the company id of this shopping item.
	 *
	 * @return the company id of this shopping item
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this shopping item.
	 *
	 * @param companyId the company id of this shopping item
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the user id of this shopping item.
	 *
	 * @return the user id of this shopping item
	 */
	public long getUserId();

	/**
	 * Sets the user id of this shopping item.
	 *
	 * @param userId the user id of this shopping item
	 */
	public void setUserId(long userId);

	/**
	 * Gets the user uuid of this shopping item.
	 *
	 * @return the user uuid of this shopping item
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this shopping item.
	 *
	 * @param userUuid the user uuid of this shopping item
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Gets the user name of this shopping item.
	 *
	 * @return the user name of this shopping item
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this shopping item.
	 *
	 * @param userName the user name of this shopping item
	 */
	public void setUserName(String userName);

	/**
	 * Gets the create date of this shopping item.
	 *
	 * @return the create date of this shopping item
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this shopping item.
	 *
	 * @param createDate the create date of this shopping item
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Gets the modified date of this shopping item.
	 *
	 * @return the modified date of this shopping item
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this shopping item.
	 *
	 * @param modifiedDate the modified date of this shopping item
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Gets the category id of this shopping item.
	 *
	 * @return the category id of this shopping item
	 */
	public long getCategoryId();

	/**
	 * Sets the category id of this shopping item.
	 *
	 * @param categoryId the category id of this shopping item
	 */
	public void setCategoryId(long categoryId);

	/**
	 * Gets the sku of this shopping item.
	 *
	 * @return the sku of this shopping item
	 */
	@AutoEscape
	public String getSku();

	/**
	 * Sets the sku of this shopping item.
	 *
	 * @param sku the sku of this shopping item
	 */
	public void setSku(String sku);

	/**
	 * Gets the name of this shopping item.
	 *
	 * @return the name of this shopping item
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this shopping item.
	 *
	 * @param name the name of this shopping item
	 */
	public void setName(String name);

	/**
	 * Gets the description of this shopping item.
	 *
	 * @return the description of this shopping item
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this shopping item.
	 *
	 * @param description the description of this shopping item
	 */
	public void setDescription(String description);

	/**
	 * Gets the properties of this shopping item.
	 *
	 * @return the properties of this shopping item
	 */
	@AutoEscape
	public String getProperties();

	/**
	 * Sets the properties of this shopping item.
	 *
	 * @param properties the properties of this shopping item
	 */
	public void setProperties(String properties);

	/**
	 * Gets the fields of this shopping item.
	 *
	 * @return the fields of this shopping item
	 */
	public boolean getFields();

	/**
	 * Determines whether this shopping item is fields.
	 *
	 * @return whether this shopping item is fields
	 */
	public boolean isFields();

	/**
	 * Sets whether this {$entity.humanName} is fields.
	 *
	 * @param fields the fields of this shopping item
	 */
	public void setFields(boolean fields);

	/**
	 * Gets the fields quantities of this shopping item.
	 *
	 * @return the fields quantities of this shopping item
	 */
	@AutoEscape
	public String getFieldsQuantities();

	/**
	 * Sets the fields quantities of this shopping item.
	 *
	 * @param fieldsQuantities the fields quantities of this shopping item
	 */
	public void setFieldsQuantities(String fieldsQuantities);

	/**
	 * Gets the min quantity of this shopping item.
	 *
	 * @return the min quantity of this shopping item
	 */
	public int getMinQuantity();

	/**
	 * Sets the min quantity of this shopping item.
	 *
	 * @param minQuantity the min quantity of this shopping item
	 */
	public void setMinQuantity(int minQuantity);

	/**
	 * Gets the max quantity of this shopping item.
	 *
	 * @return the max quantity of this shopping item
	 */
	public int getMaxQuantity();

	/**
	 * Sets the max quantity of this shopping item.
	 *
	 * @param maxQuantity the max quantity of this shopping item
	 */
	public void setMaxQuantity(int maxQuantity);

	/**
	 * Gets the price of this shopping item.
	 *
	 * @return the price of this shopping item
	 */
	public double getPrice();

	/**
	 * Sets the price of this shopping item.
	 *
	 * @param price the price of this shopping item
	 */
	public void setPrice(double price);

	/**
	 * Gets the discount of this shopping item.
	 *
	 * @return the discount of this shopping item
	 */
	public double getDiscount();

	/**
	 * Sets the discount of this shopping item.
	 *
	 * @param discount the discount of this shopping item
	 */
	public void setDiscount(double discount);

	/**
	 * Gets the taxable of this shopping item.
	 *
	 * @return the taxable of this shopping item
	 */
	public boolean getTaxable();

	/**
	 * Determines whether this shopping item is taxable.
	 *
	 * @return whether this shopping item is taxable
	 */
	public boolean isTaxable();

	/**
	 * Sets whether this {$entity.humanName} is taxable.
	 *
	 * @param taxable the taxable of this shopping item
	 */
	public void setTaxable(boolean taxable);

	/**
	 * Gets the shipping of this shopping item.
	 *
	 * @return the shipping of this shopping item
	 */
	public double getShipping();

	/**
	 * Sets the shipping of this shopping item.
	 *
	 * @param shipping the shipping of this shopping item
	 */
	public void setShipping(double shipping);

	/**
	 * Gets the use shipping formula of this shopping item.
	 *
	 * @return the use shipping formula of this shopping item
	 */
	public boolean getUseShippingFormula();

	/**
	 * Determines whether this shopping item is use shipping formula.
	 *
	 * @return whether this shopping item is use shipping formula
	 */
	public boolean isUseShippingFormula();

	/**
	 * Sets whether this {$entity.humanName} is use shipping formula.
	 *
	 * @param useShippingFormula the use shipping formula of this shopping item
	 */
	public void setUseShippingFormula(boolean useShippingFormula);

	/**
	 * Gets the requires shipping of this shopping item.
	 *
	 * @return the requires shipping of this shopping item
	 */
	public boolean getRequiresShipping();

	/**
	 * Determines whether this shopping item is requires shipping.
	 *
	 * @return whether this shopping item is requires shipping
	 */
	public boolean isRequiresShipping();

	/**
	 * Sets whether this {$entity.humanName} is requires shipping.
	 *
	 * @param requiresShipping the requires shipping of this shopping item
	 */
	public void setRequiresShipping(boolean requiresShipping);

	/**
	 * Gets the stock quantity of this shopping item.
	 *
	 * @return the stock quantity of this shopping item
	 */
	public int getStockQuantity();

	/**
	 * Sets the stock quantity of this shopping item.
	 *
	 * @param stockQuantity the stock quantity of this shopping item
	 */
	public void setStockQuantity(int stockQuantity);

	/**
	 * Gets the featured of this shopping item.
	 *
	 * @return the featured of this shopping item
	 */
	public boolean getFeatured();

	/**
	 * Determines whether this shopping item is featured.
	 *
	 * @return whether this shopping item is featured
	 */
	public boolean isFeatured();

	/**
	 * Sets whether this {$entity.humanName} is featured.
	 *
	 * @param featured the featured of this shopping item
	 */
	public void setFeatured(boolean featured);

	/**
	 * Gets the sale of this shopping item.
	 *
	 * @return the sale of this shopping item
	 */
	public boolean getSale();

	/**
	 * Determines whether this shopping item is sale.
	 *
	 * @return whether this shopping item is sale
	 */
	public boolean isSale();

	/**
	 * Sets whether this {$entity.humanName} is sale.
	 *
	 * @param sale the sale of this shopping item
	 */
	public void setSale(boolean sale);

	/**
	 * Gets the small image of this shopping item.
	 *
	 * @return the small image of this shopping item
	 */
	public boolean getSmallImage();

	/**
	 * Determines whether this shopping item is small image.
	 *
	 * @return whether this shopping item is small image
	 */
	public boolean isSmallImage();

	/**
	 * Sets whether this {$entity.humanName} is small image.
	 *
	 * @param smallImage the small image of this shopping item
	 */
	public void setSmallImage(boolean smallImage);

	/**
	 * Gets the small image id of this shopping item.
	 *
	 * @return the small image id of this shopping item
	 */
	public long getSmallImageId();

	/**
	 * Sets the small image id of this shopping item.
	 *
	 * @param smallImageId the small image id of this shopping item
	 */
	public void setSmallImageId(long smallImageId);

	/**
	 * Gets the small image u r l of this shopping item.
	 *
	 * @return the small image u r l of this shopping item
	 */
	@AutoEscape
	public String getSmallImageURL();

	/**
	 * Sets the small image u r l of this shopping item.
	 *
	 * @param smallImageURL the small image u r l of this shopping item
	 */
	public void setSmallImageURL(String smallImageURL);

	/**
	 * Gets the medium image of this shopping item.
	 *
	 * @return the medium image of this shopping item
	 */
	public boolean getMediumImage();

	/**
	 * Determines whether this shopping item is medium image.
	 *
	 * @return whether this shopping item is medium image
	 */
	public boolean isMediumImage();

	/**
	 * Sets whether this {$entity.humanName} is medium image.
	 *
	 * @param mediumImage the medium image of this shopping item
	 */
	public void setMediumImage(boolean mediumImage);

	/**
	 * Gets the medium image id of this shopping item.
	 *
	 * @return the medium image id of this shopping item
	 */
	public long getMediumImageId();

	/**
	 * Sets the medium image id of this shopping item.
	 *
	 * @param mediumImageId the medium image id of this shopping item
	 */
	public void setMediumImageId(long mediumImageId);

	/**
	 * Gets the medium image u r l of this shopping item.
	 *
	 * @return the medium image u r l of this shopping item
	 */
	@AutoEscape
	public String getMediumImageURL();

	/**
	 * Sets the medium image u r l of this shopping item.
	 *
	 * @param mediumImageURL the medium image u r l of this shopping item
	 */
	public void setMediumImageURL(String mediumImageURL);

	/**
	 * Gets the large image of this shopping item.
	 *
	 * @return the large image of this shopping item
	 */
	public boolean getLargeImage();

	/**
	 * Determines whether this shopping item is large image.
	 *
	 * @return whether this shopping item is large image
	 */
	public boolean isLargeImage();

	/**
	 * Sets whether this {$entity.humanName} is large image.
	 *
	 * @param largeImage the large image of this shopping item
	 */
	public void setLargeImage(boolean largeImage);

	/**
	 * Gets the large image id of this shopping item.
	 *
	 * @return the large image id of this shopping item
	 */
	public long getLargeImageId();

	/**
	 * Sets the large image id of this shopping item.
	 *
	 * @param largeImageId the large image id of this shopping item
	 */
	public void setLargeImageId(long largeImageId);

	/**
	 * Gets the large image u r l of this shopping item.
	 *
	 * @return the large image u r l of this shopping item
	 */
	@AutoEscape
	public String getLargeImageURL();

	/**
	 * Sets the large image u r l of this shopping item.
	 *
	 * @param largeImageURL the large image u r l of this shopping item
	 */
	public void setLargeImageURL(String largeImageURL);

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

	public int compareTo(ShoppingItem shoppingItem);

	public int hashCode();

	public ShoppingItem toEscapedModel();

	public String toString();

	public String toXmlString();
}