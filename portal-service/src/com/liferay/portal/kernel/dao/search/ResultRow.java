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

package com.liferay.portal.kernel.dao.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ResultRow {

	public ResultRow(Object obj, long primaryKey, int pos) {
		this(obj, String.valueOf(primaryKey), pos);
	}

	public ResultRow(Object obj, long primaryKey, int pos, boolean bold) {
		this(obj, String.valueOf(primaryKey), pos, bold);
	}

	public ResultRow(Object obj, String primaryKey, int pos) {
		this(obj, primaryKey, pos, false);
	}

	public ResultRow(Object obj, String primaryKey, int pos, boolean bold) {
		_obj = obj;
		_primaryKey = primaryKey;
		_pos = pos;
		_bold = bold;
		_entries = new ArrayList<SearchEntry>();
	}

	public Object getObject() {
		return _obj;
	}

	public void setObject(Object obj) {
		_obj = obj;
	}

	public String getPrimaryKey() {
		return _primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		_primaryKey = primaryKey;
	}

	public int getPos() {
		return _pos;
	}

	public boolean isBold() {
		return _bold;
	}

	public void setBold(boolean bold) {
		_bold = bold;
	}

	public boolean isRestricted() {
		return _restricted;
	}

	public void setRestricted(boolean restricted) {
		_restricted = restricted;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getClassHoverName() {
		return _classHoverName;
	}

	public void setClassHoverName(String classHoverName) {
		_classHoverName = classHoverName;
	}

	public List<SearchEntry> getEntries() {
		return _entries;
	}

	public Object getParameter(String param) {
		if (_params == null) {
			_params = new HashMap<String, Object>();
		}

		return _params.get(param);
	}

	public void setParameter(String param, Object value) {
		if (_params == null) {
			_params = new HashMap<String, Object>();
		}

		_params.put(param, value);
	}

	// Text with name

	public void addText(String name) {
		addText(_entries.size(), name);
	}

	public void addText(int index, String name) {
		addText(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, SearchEntry.DEFAULT_CSS_CLASS, name);
	}

	public void addText(String align, String valign, String name) {
		addText(
			_entries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			SearchEntry.DEFAULT_CSS_CLASS, name);
	}

	public void addText(
		String align, String valign, int colspan, String cssClass,
		String name) {

		addText(_entries.size(), align, valign, colspan, cssClass, name);
	}

	public void addText(
		int index, String align, String valign, int colspan, String cssClass,
		String name) {

		_entries.add(
			index, new TextSearchEntry(align, valign, colspan, cssClass, name));
	}

	// Text with name and href

	public void addText(String name, String href) {
		addText(_entries.size(), name, href);
	}

	public void addText(int index, String name, String href) {
		addText(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, SearchEntry.DEFAULT_CSS_CLASS, name,
			href);
	}

	public void addText(String align, String valign, String name, String href) {
		addText(
			_entries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			SearchEntry.DEFAULT_CSS_CLASS, name, href);
	}

	public void addText(
		String align, String valign, int colspan, String cssClass, String name,
		String href) {

		addText(_entries.size(), align, valign, colspan, cssClass, name, href);
	}

	public void addText(
		int index, String align, String valign, int colspan, String cssClass,
		String name, String href) {

		if (_restricted) {
			href = null;
		}

		_entries.add(
			index, new TextSearchEntry(align, valign, colspan, cssClass, name,
				href));
	}

	// Text with name and portlet URL

	public void addText(String name, PortletURL portletURL) {
		if (portletURL == null) {
			addText(name);
		}
		else {
			addText(name, portletURL.toString());
		}
	}

	public void addText(int index, String name, PortletURL portletURL) {
		if (portletURL == null) {
			addText(index, name);
		}
		else {
			addText(index, name, portletURL.toString());
		}
	}

	public void addText(
		String align, String valign, String name, PortletURL portletURL) {

		addText(
			align, valign, SearchEntry.DEFAULT_COLSPAN,
			SearchEntry.DEFAULT_CSS_CLASS, name, portletURL);
	}

	public void addText(
		String align, String valign, int colspan, String cssClass, String name,
		PortletURL portletURL) {

		if (portletURL == null) {
			addText(align, valign, colspan, cssClass, name);
		}
		else {
			addText(align, valign, colspan, cssClass, name,
				portletURL.toString());
		}
	}

	public void addText(
		int index, String align, String valign, int colspan, String cssClass,
		String name, PortletURL portletURL) {

		if (portletURL == null) {
			addText(index, align, valign, colspan, cssClass, name);
		}
		else {
			addText(index, align, valign, colspan, cssClass, name,
				portletURL.toString());
		}
	}

	// Text with search entry

	public void addText(TextSearchEntry searchEntry) {
		if (_restricted) {
			searchEntry.setHref(null);
		}

		_entries.add(_entries.size(), searchEntry);
	}

	public void addText(int index, TextSearchEntry searchEntry) {
		if (_restricted) {
			searchEntry.setHref(null);
		}

		_entries.add(index, searchEntry);
	}

	// Button with name and href

	public void addButton(String name, String href) {
		addButton(_entries.size(), name, href);
	}

	public void addButton(int index, String name, String href) {
		addButton(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, SearchEntry.DEFAULT_CSS_CLASS, name,
			href);
	}

	public void addButton(
		String align, String valign, String name, String href) {

		addButton(
			_entries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			SearchEntry.DEFAULT_CSS_CLASS, name, href);
	}

	public void addButton(
		String align, String valign, int colspan, String cssClass, String name,
		String href) {

		addButton(_entries.size(), align, valign, colspan, cssClass, name,
			href);
	}

	public void addButton(
		int index, String align, String valign, int colspan, String cssClass,
		String name, String href) {

		if (_restricted) {
			href = null;
		}

		_entries.add(
			index, new ButtonSearchEntry(align, valign, colspan, cssClass, name,
			href));
	}

	// JSP

	public void addJSP(String path) {
		addJSP(_entries.size(), path);
	}

	public void addJSP(String align, String valign, String path) {
		addJSP(
			_entries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			SearchEntry.DEFAULT_CSS_CLASS, path);
	}

	public void addJSP(String align, String valign, int colspan,
   		String cssClass, String path) {

		addJSP(_entries.size(), align, valign, colspan, cssClass, path);
	}

	public void addJSP(int index, String path) {
		addJSP(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, SearchEntry.DEFAULT_CSS_CLASS, path);
	}

	public void addJSP(
		int index, String align, String valign, int colspan, String cssClass,
		String path) {

		_entries.add(index, new JSPSearchEntry(align, valign, colspan, cssClass,
			path));
	}

	// JSP with portlet context

	public void addJSP(
		String path, ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		addJSP(_entries.size(), path, servletContext, request, response);
	}

	public void addJSP(
		String align, String valign, String path, ServletContext servletContext,
		HttpServletRequest request, HttpServletResponse response) {

		addJSP(
			_entries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			SearchEntry.DEFAULT_CSS_CLASS, path, servletContext, request,
			response);
	}

	public void addJSP(
		String align, String valign, int colspan, String cssClass, String path,
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		addJSP(
			_entries.size(), align, valign, colspan, cssClass, path,
			servletContext, request, response);
	}

	public void addJSP(
		int index, String path, ServletContext servletContext,
		HttpServletRequest request, HttpServletResponse response) {

		addJSP(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, SearchEntry.DEFAULT_CSS_CLASS, path,
			servletContext, request, response);
	}

	public void addJSP(
		int index, String align, String valign, int colspan, String cssClass,
		String path, ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		_entries.add(
			index,
			new JSPSearchEntry(
				align, valign, colspan, cssClass, path, servletContext, request,
				response));
	}

	// Score

	public void addScore(float score) {
		addScore(_entries.size(), score);
	}

	public void addScore(int index, float score) {
		_entries.add(index, new ScoreSearchEntry(score));
	}

	private Object _obj;
	private String _primaryKey;
	private int _pos;
	private boolean _bold;
	private boolean _restricted;
	private String _className;
	private String _classHoverName;
	private List<SearchEntry> _entries;
	private Map<String, Object> _params;

}