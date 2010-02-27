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

package com.liferay.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PublicRenderParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletContext;

import javax.xml.namespace.QName;

/**
 * <a href="PortletConfigImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class PortletConfigImpl implements LiferayPortletConfig {

	public PortletConfigImpl(Portlet portlet, PortletContext portletContext) {
		_portletApp = portlet.getPortletApp();
		_portlet = portlet;
		_portletName = portlet.getRootPortletId();

		int pos = _portletName.indexOf(PortletConstants.WAR_SEPARATOR);

		if (pos != -1) {
			_portletName = _portletName.substring(0, pos);
		}

		_portletContext = portletContext;
		_bundlePool = new HashMap<String, ResourceBundle>();
	}

	public Map<String, String[]> getContainerRuntimeOptions() {
		return _portletApp.getContainerRuntimeOptions();
	}

	public String getDefaultNamespace() {
		return _portletApp.getDefaultNamespace();
	}

	public String getInitParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _portlet.getInitParams().get(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_portlet.getInitParams().keySet());
	}

	public PortletContext getPortletContext() {
		return _portletContext;
	}

	public String getPortletId() {
		return _portlet.getPortletId();
	}

	public String getPortletName() {
		return _portletName;
	}

	public Enumeration<QName> getProcessingEventQNames() {
		return Collections.enumeration(
			toJavaxQNames(_portlet.getProcessingEvents()));
	}

	public Enumeration<String> getPublicRenderParameterNames() {
		List<String> publicRenderParameterNames = new ArrayList<String>();

		for (PublicRenderParameter publicRenderParameter :
				_portlet.getPublicRenderParameters()) {

			publicRenderParameterNames.add(
				publicRenderParameter.getIdentifier());
		}

		return Collections.enumeration(publicRenderParameterNames);
	}

	public Enumeration<QName> getPublishingEventQNames() {
		return Collections.enumeration(
			toJavaxQNames(_portlet.getPublishingEvents()));
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		String resourceBundleClassName = _portlet.getResourceBundle();

		if (Validator.isNull(resourceBundleClassName)) {
			String poolId = _portlet.getPortletId();

			ResourceBundle bundle = _bundlePool.get(poolId);

			if (bundle == null) {
				StringBundler sb = new StringBundler(16);

				try {
					PortletInfo portletInfo = _portlet.getPortletInfo();

					sb.append(JavaConstants.JAVAX_PORTLET_TITLE);
					sb.append("=");
					sb.append(portletInfo.getTitle());
					sb.append("\n");

					sb.append(JavaConstants.JAVAX_PORTLET_SHORT_TITLE);
					sb.append("=");
					sb.append(portletInfo.getShortTitle());
					sb.append("\n");

					sb.append(JavaConstants.JAVAX_PORTLET_KEYWORDS);
					sb.append("=");
					sb.append(portletInfo.getKeywords());
					sb.append("\n");

					sb.append(JavaConstants.JAVAX_PORTLET_DESCRIPTION);
					sb.append("=");
					sb.append(portletInfo.getDescription());
					sb.append("\n");

					bundle = new PropertyResourceBundle(
						new UnsyncByteArrayInputStream(
							sb.toString().getBytes()));
				}
				catch (Exception e) {
					_log.error(e, e);
				}

				_bundlePool.put(poolId, bundle);
			}

			return bundle;
		}
		else {
			String poolId = _portlet.getPortletId() + "." + locale.toString();

			ResourceBundle bundle = _bundlePool.get(poolId);

			if (bundle == null) {
				if (!_portletApp.isWARFile() &&
					resourceBundleClassName.equals(
						StrutsResourceBundle.class.getName())) {

					bundle = new StrutsResourceBundle(_portletName, locale);
				}
				else {
					PortletBag portletBag = PortletBagPool.get(
						_portlet.getRootPortletId());

					bundle = portletBag.getResourceBundle(locale);
				}

				bundle = new PortletResourceBundle(
					bundle, _portlet.getPortletInfo());

				_bundlePool.put(poolId, bundle);
			}

			return bundle;
		}
	}

	public Enumeration<Locale> getSupportedLocales() {
		List<Locale> supportedLocales = new ArrayList<Locale>();

		for (String languageId : _portlet.getSupportedLocales()) {
			supportedLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return Collections.enumeration(supportedLocales);
	}

	public boolean isWARFile() {
		return _portletApp.isWARFile();
	}

	protected Set<javax.xml.namespace.QName> toJavaxQNames(
		Set<com.liferay.portal.kernel.xml.QName> liferayQNames) {

		Set<QName> javaxQNames = new HashSet<QName>(liferayQNames.size());

		for (com.liferay.portal.kernel.xml.QName liferayQName :
				liferayQNames) {

			QName javaxQName = new QName(
				liferayQName.getNamespaceURI(), liferayQName.getLocalPart(),
				liferayQName.getNamespacePrefix());

			javaxQNames.add(javaxQName);
		}

		return javaxQNames;
	}

	private static Log _log = LogFactoryUtil.getLog(PortletConfigImpl.class);

	private PortletApp _portletApp;
	private Portlet _portlet;
	private String _portletName;
	private PortletContext _portletContext;
	private Map<String, ResourceBundle> _bundlePool;

}