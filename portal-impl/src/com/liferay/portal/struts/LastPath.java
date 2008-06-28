/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.struts;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <a href="LastPath.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LastPath implements Serializable {

	public LastPath(String contextPath, String path) {
		this(contextPath, path, null);
	}

	public LastPath(
		String contextPath, String path, Map<String, String[]> parameterMap) {

		_contextPath = contextPath;
		_path = path;
		_parameterMap = new LinkedHashMap<String, String[]>(parameterMap);
	}

	public String getContextPath() {
		return _contextPath;
	}

	public void setContextPath(String contextPath) {
		_contextPath = contextPath;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{contextPath=");
		sb.append(_contextPath);
		sb.append(", path=");
		sb.append(_path);
		sb.append("}");

		return sb.toString();
	}

	private String _contextPath;
	private String _path;
	private Map<String, String[]> _parameterMap;

}