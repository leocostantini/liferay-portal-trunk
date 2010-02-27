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

package com.liferay.portlet.journal.util;

import java.util.Map;

/**
 * <a href="TransformerListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class TransformerListener {

	public TransformerListener() {
	}

	public boolean isTemplateDriven() {
		return _templateDriven;
	}

	public void setTemplateDriven(boolean templateDriven) {
		_templateDriven = templateDriven;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public Map<String, String> getTokens() {
		return _tokens;
	}

	public void setTokens(Map<String, String> tokens) {
		_tokens = tokens;
	}

	public abstract String onXml(String s);

	public abstract String onScript(String s);

	public abstract String onOutput(String s);

	private boolean _templateDriven;
	private String _languageId;
	private Map<String, String> _tokens;

}