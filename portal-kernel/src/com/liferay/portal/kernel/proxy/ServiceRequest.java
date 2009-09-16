/*
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

package com.liferay.portal.kernel.proxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <a href="ServiceRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ServiceRequest implements Serializable {

	public ServiceRequest(Method method, Object[] arguments)
		throws Exception {

		_method = method;
		_arguments = arguments;
	}

	public Object execute(Object object) throws Exception {
		try {
			return _method.invoke(object, _arguments);
		}
		catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException());
		}
	}

	public boolean hasReturnValue() {
		if (_method.getReturnType() != Void.TYPE) {
			return true;
		}
		else {
			return false;
		}
	}

	protected Object[] _arguments;
	protected Method _method;
}
