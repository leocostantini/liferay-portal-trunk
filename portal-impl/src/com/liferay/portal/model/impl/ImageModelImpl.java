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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ImageModel;
import com.liferay.portal.model.ImageSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the Image service. Represents a row in the &quot;Image&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.ImageModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ImageImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ImageImpl
 * @see com.liferay.portal.model.Image
 * @see com.liferay.portal.model.ImageModel
 * @generated
 */
public class ImageModelImpl extends BaseModelImpl<Image> implements ImageModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a image model instance should use the {@link com.liferay.portal.model.Image} interface instead.
	 */
	public static final String TABLE_NAME = "Image";
	public static final Object[][] TABLE_COLUMNS = {
			{ "imageId", Types.BIGINT },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "text_", Types.CLOB },
			{ "type_", Types.VARCHAR },
			{ "height", Types.INTEGER },
			{ "width", Types.INTEGER },
			{ "size_", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table Image (imageId LONG not null primary key,modifiedDate DATE null,text_ TEXT null,type_ VARCHAR(75) null,height INTEGER,width INTEGER,size_ INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table Image";
	public static final String ORDER_BY_JPQL = " ORDER BY image.imageId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Image.imageId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Image"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Image"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Image toModel(ImageSoap soapModel) {
		Image model = new ImageImpl();

		model.setImageId(soapModel.getImageId());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setText(soapModel.getText());
		model.setType(soapModel.getType());
		model.setHeight(soapModel.getHeight());
		model.setWidth(soapModel.getWidth());
		model.setSize(soapModel.getSize());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Image> toModels(ImageSoap[] soapModels) {
		List<Image> models = new ArrayList<Image>(soapModels.length);

		for (ImageSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Image"));

	public ImageModelImpl() {
	}

	public long getPrimaryKey() {
		return _imageId;
	}

	public void setPrimaryKey(long pk) {
		setImageId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_imageId);
	}

	public long getImageId() {
		return _imageId;
	}

	public void setImageId(long imageId) {
		_imageId = imageId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getText() {
		if (_text == null) {
			return StringPool.BLANK;
		}
		else {
			return _text;
		}
	}

	public void setText(String text) {
		_text = text;
	}

	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	public Image toEscapedModel() {
		if (isEscapedModel()) {
			return (Image)this;
		}
		else {
			return (Image)Proxy.newProxyInstance(Image.class.getClassLoader(),
				new Class[] { Image.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					Image.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ImageModelImpl clone = new ImageImpl();

		clone._imageId = _imageId;
		clone._modifiedDate = _modifiedDate;
		clone._text = _text;
		clone._type = _type;
		clone._height = _height;
		clone._width = _width;
		clone._size = _size;

		return clone;
	}

	public int compareTo(Image image) {
		int value = 0;

		if (getImageId() < image.getImageId()) {
			value = -1;
		}
		else if (getImageId() > image.getImageId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Image image = null;

		try {
			image = (Image)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = image.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{imageId=");
		sb.append(getImageId());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", text=");
		sb.append(getText());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", height=");
		sb.append(getHeight());
		sb.append(", width=");
		sb.append(getWidth());
		sb.append(", size=");
		sb.append(getSize());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Image");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>imageId</column-name><column-value><![CDATA[");
		sb.append(getImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>text</column-name><column-value><![CDATA[");
		sb.append(getText());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>height</column-name><column-value><![CDATA[");
		sb.append(getHeight());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>width</column-name><column-value><![CDATA[");
		sb.append(getWidth());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>size</column-name><column-value><![CDATA[");
		sb.append(getSize());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _imageId;
	private Date _modifiedDate;
	private String _text;
	private String _type;
	private int _height;
	private int _width;
	private int _size;
	private transient ExpandoBridge _expandoBridge;
}