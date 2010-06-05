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

package com.liferay.portal.messaging.async;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="AsyncAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class AsyncAdvice extends AnnotationChainableMethodAdvice<Async> {

	public Object before(final MethodInvocation methodInvocation)
		throws Throwable {

		MethodTargetClassKey methodTargetClassKey = buildMethodTargetClassKey(
			methodInvocation);

		Async async = findAnnotation(methodTargetClassKey);

		if (async == _nullAsync) {
			return null;
		}

		Method method = methodTargetClassKey.getMethod();
		if (method.getReturnType() != void.class) {
			if (_log.isWarnEnabled()) {
				_log.warn("Detect a non-void with Async annotation, will " +
					"ignore Async and degenerate to normal sync invoking :" +
					method);
			}
			_annotations.put(methodTargetClassKey, _nullAsync);
			return null;
		}

		String destinationName = null;

		if ((_destinationNames != null) && !_destinationNames.isEmpty()) {
			destinationName = _destinationNames.get(
				methodTargetClassKey.getTargetClass());
		}

		if (destinationName == null) {
			destinationName = _defaultDestinationName;
		}

		MessageBusUtil.sendMessage(
			destinationName,
			new Runnable() {

				public void run() {
					try {
						nextMethodInterceptor.invoke(methodInvocation);
					}
					catch (Throwable t) {
						throw new RuntimeException(t);
					}
				}

				public String toString() {
					return methodInvocation.toString();
				}

			});

		return nullResult;
	}

	public Class<Async> getAnnotationClass() {
		return Async.class;
	}

	public String getDefaultDestinationName() {
		return _defaultDestinationName;
	}

	public Async getNullAnnotation() {
		return _nullAsync;
	}

	public void setDefaultDestinationName(String defaultDestinationName) {
		_defaultDestinationName = defaultDestinationName;
	}

	public void setDestinationNames(Map<Class<?>, String> destinationNames) {
		_destinationNames = destinationNames;
	}

	private static Async _nullAsync =
		new Async() {

			public Class<? extends Annotation> annotationType() {
				return Async.class;
			}

		};

	private static Log _log = LogFactoryUtil.getLog(AsyncAdvice.class);

	private String _defaultDestinationName;
	private Map<Class<?>, String> _destinationNames;

}