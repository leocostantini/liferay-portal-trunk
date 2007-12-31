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

package com.liferay.portal.upgrade.util;

import java.util.Iterator;

/**
 * <a href="ValueMapperWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ValueMapperWrapper implements ValueMapper {

	public ValueMapperWrapper(ValueMapper valueMapper) {
		_valueMapper = valueMapper;
	}

	public ValueMapper getValueMapper() {
		return _valueMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		return _valueMapper.getNewValue(oldValue);
	}

	public void mapValue(Object oldValue, Object newValue) throws Exception {
		_valueMapper.mapValue(oldValue, newValue);
	}

	public void appendException(Object exception) {
		_valueMapper.appendException(exception);
	}

	public Iterator iterator() throws Exception {
		return _valueMapper.iterator();
	}

	public int size() throws Exception {
		return _valueMapper.size();
	}

	private ValueMapper _valueMapper;

}