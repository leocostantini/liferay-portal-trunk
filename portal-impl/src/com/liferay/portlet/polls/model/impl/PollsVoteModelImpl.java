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

package com.liferay.portlet.polls.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.model.PollsVoteSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="PollsVoteModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PollsVote table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVoteImpl
 * @see       com.liferay.portlet.polls.model.PollsVote
 * @see       com.liferay.portlet.polls.model.PollsVoteModel
 * @generated
 */
public class PollsVoteModelImpl extends BaseModelImpl<PollsVote> {
	public static final String TABLE_NAME = "PollsVote";
	public static final Object[][] TABLE_COLUMNS = {
			{ "voteId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "questionId", new Integer(Types.BIGINT) },
			{ "choiceId", new Integer(Types.BIGINT) },
			{ "voteDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table PollsVote (voteId LONG not null primary key,userId LONG,questionId LONG,choiceId LONG,voteDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table PollsVote";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.polls.model.PollsVote"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.polls.model.PollsVote"),
			true);

	public static PollsVote toModel(PollsVoteSoap soapModel) {
		PollsVote model = new PollsVoteImpl();

		model.setVoteId(soapModel.getVoteId());
		model.setUserId(soapModel.getUserId());
		model.setQuestionId(soapModel.getQuestionId());
		model.setChoiceId(soapModel.getChoiceId());
		model.setVoteDate(soapModel.getVoteDate());

		return model;
	}

	public static List<PollsVote> toModels(PollsVoteSoap[] soapModels) {
		List<PollsVote> models = new ArrayList<PollsVote>(soapModels.length);

		for (PollsVoteSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.polls.model.PollsVote"));

	public PollsVoteModelImpl() {
	}

	public long getPrimaryKey() {
		return _voteId;
	}

	public void setPrimaryKey(long pk) {
		setVoteId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_voteId);
	}

	public long getVoteId() {
		return _voteId;
	}

	public void setVoteId(long voteId) {
		_voteId = voteId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;

		if (!_setOriginalQuestionId) {
			_setOriginalQuestionId = true;

			_originalQuestionId = questionId;
		}
	}

	public long getOriginalQuestionId() {
		return _originalQuestionId;
	}

	public long getChoiceId() {
		return _choiceId;
	}

	public void setChoiceId(long choiceId) {
		_choiceId = choiceId;
	}

	public Date getVoteDate() {
		return _voteDate;
	}

	public void setVoteDate(Date voteDate) {
		_voteDate = voteDate;
	}

	public PollsVote toEscapedModel() {
		if (isEscapedModel()) {
			return (PollsVote)this;
		}
		else {
			PollsVote model = new PollsVoteImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setVoteId(getVoteId());
			model.setUserId(getUserId());
			model.setQuestionId(getQuestionId());
			model.setChoiceId(getChoiceId());
			model.setVoteDate(getVoteDate());

			model = (PollsVote)Proxy.newProxyInstance(PollsVote.class.getClassLoader(),
					new Class[] { PollsVote.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					PollsVote.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		PollsVoteImpl clone = new PollsVoteImpl();

		clone.setVoteId(getVoteId());
		clone.setUserId(getUserId());
		clone.setQuestionId(getQuestionId());
		clone.setChoiceId(getChoiceId());
		clone.setVoteDate(getVoteDate());

		return clone;
	}

	public int compareTo(PollsVote pollsVote) {
		long pk = pollsVote.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PollsVote pollsVote = null;

		try {
			pollsVote = (PollsVote)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = pollsVote.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{voteId=");
		sb.append(getVoteId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", questionId=");
		sb.append(getQuestionId());
		sb.append(", choiceId=");
		sb.append(getChoiceId());
		sb.append(", voteDate=");
		sb.append(getVoteDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.polls.model.PollsVote");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>voteId</column-name><column-value><![CDATA[");
		sb.append(getVoteId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>questionId</column-name><column-value><![CDATA[");
		sb.append(getQuestionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>choiceId</column-name><column-value><![CDATA[");
		sb.append(getChoiceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>voteDate</column-name><column-value><![CDATA[");
		sb.append(getVoteDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _voteId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _questionId;
	private long _originalQuestionId;
	private boolean _setOriginalQuestionId;
	private long _choiceId;
	private Date _voteDate;
	private transient ExpandoBridge _expandoBridge;
}