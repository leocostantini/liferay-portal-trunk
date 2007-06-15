/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.util.PropsUtil;
import com.liferay.util.Validator;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.classic.Session;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.hibernate.SessionFactory;

/**
 * <a href="HibernateConfiguration.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class HibernateConfiguration extends LocalSessionFactoryBean {

	protected SessionFactory getTransactionAwareSessionFactoryProxy(
		SessionFactory target) {

		// LEP-2996

		Class sessionFactoryInterface = SessionFactory.class;

		if (target instanceof SessionFactoryImplementor) {
			sessionFactoryInterface = SessionFactoryImplementor.class;
		}

		return (SessionFactory)Proxy.newProxyInstance(
			sessionFactoryInterface.getClassLoader(),
			new Class[] {sessionFactoryInterface},
			new SessionFactoryInvocationHandler(target));
	}

	protected Configuration newConfiguration() {
		Configuration cfg = new Configuration();

		try {
			ClassLoader classLoader = getClass().getClassLoader();

			String[] configs = PropsUtil.getArray(PropsUtil.HIBERNATE_CONFIGS);

			for (int i = 0; i < configs.length; i++) {
				try {
					InputStream is =
						classLoader.getResourceAsStream(configs[i]);

					if (is != null) {
						cfg = cfg.addInputStream(is);

						is.close();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			cfg.setProperties(PropsUtil.getProperties());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return cfg;
	}

	protected void postProcessConfiguration(Configuration cfg) {

		// Make sure that settings in portal.properties are set. See
		// the buildSessionFactory implementation in the
		// LocalSessionFactoryBean class to understand how Spring automates a
		// lot of configuration for Hibernate.

		String connectionReleaseMode = PropsUtil.get(
			Environment.RELEASE_CONNECTIONS);

		if (Validator.isNotNull(connectionReleaseMode)) {
			cfg.setProperty(
				Environment.RELEASE_CONNECTIONS, connectionReleaseMode);
		}
	}

	private static Log _log = LogFactory.getLog(HibernateConfiguration.class);

}