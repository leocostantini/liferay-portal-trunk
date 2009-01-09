/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="SortedProperties.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SortedProperties extends Properties {

	public SortedProperties() {
		super();

		_names = new TreeSet<String>();
	}

	public SortedProperties(Properties properties) {
		this();

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			setProperty(key, value);
		}
	}

	public void clear() {
		super.clear();

		_names.clear();
	}

	public void list(PrintStream out) {
		System.out.println("-- listing properties --");

		Enumeration<String> enu = propertyNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			out.println(name + StringPool.EQUAL + getProperty(name));
		}
	}

	public void list(PrintWriter out) {
		System.out.println("-- listing properties --");

		Enumeration<String> enu = propertyNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			out.println(name + StringPool.EQUAL + getProperty(name));
		}
	}

	public Enumeration<String> propertyNames() {
		return Collections.enumeration(_names);
	}

	public Object put(String key, String value) {
		if (_names.contains(key)) {
			_names.remove(key);
		}

		_names.add(key);

		return super.put(key, value);
	}

	public Object remove(Object key) {
		_names.remove(key);

		return super.remove(key);
	}

	public Object setProperty(String key, String value) {
		return put(key, value);
	}

	private Set<String> _names;

}