/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model;

import com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingOrderItemSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderItemSoap implements Serializable {
	public static ShoppingOrderItemSoap toSoapModel(ShoppingOrderItem model) {
		ShoppingOrderItemSoap soapModel = new ShoppingOrderItemSoap();
		soapModel.setOrderId(model.getOrderId());
		soapModel.setItemId(model.getItemId());
		soapModel.setSku(model.getSku());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setProperties(model.getProperties());
		soapModel.setPrice(model.getPrice());
		soapModel.setQuantity(model.getQuantity());
		soapModel.setShippedDate(model.getShippedDate());

		return soapModel;
	}

	public static ShoppingOrderItemSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			ShoppingOrderItem model = (ShoppingOrderItem)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (ShoppingOrderItemSoap[])soapModels.toArray(new ShoppingOrderItemSoap[0]);
	}

	public ShoppingOrderItemSoap() {
	}

	public ShoppingOrderItemPK getPrimaryKey() {
		return new ShoppingOrderItemPK(_orderId, _itemId);
	}

	public void setPrimaryKey(ShoppingOrderItemPK pk) {
		setOrderId(pk.orderId);
		setItemId(pk.itemId);
	}

	public String getOrderId() {
		return _orderId;
	}

	public void setOrderId(String orderId) {
		_orderId = orderId;
	}

	public String getItemId() {
		return _itemId;
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getProperties() {
		return _properties;
	}

	public void setProperties(String properties) {
		_properties = properties;
	}

	public double getPrice() {
		return _price;
	}

	public void setPrice(double price) {
		_price = price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public Date getShippedDate() {
		return _shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		_shippedDate = shippedDate;
	}

	private String _orderId;
	private String _itemId;
	private String _sku;
	private String _name;
	private String _description;
	private String _properties;
	private double _price;
	private int _quantity;
	private Date _shippedDate;
}