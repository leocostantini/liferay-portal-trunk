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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.PluginPackageNameAndContextComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.Version;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * <a href="LocalPluginPackageRepository.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class LocalPluginPackageRepository {

	public LocalPluginPackageRepository() {
	}

	public List getPluginPackages() {
		return new ArrayList(_pluginPackages.values());
	}

	public List getSortedPluginPackages() {
		Collection sortedSet =
			new TreeSet(new PluginPackageNameAndContextComparator());
		sortedSet.addAll(_pluginPackages.values());
		return new ArrayList(sortedSet);
	}

	public void addPluginPackage(PluginPackage pluginPackage) {

		if (pluginPackage.getContext() == null) {
			System.out.println(
				"Plugin package cannot be registered because it does not have" +
					"an installation context");
			return;
		}

		_pendingPackages.remove(pluginPackage.getContext());
		_pendingPackages.remove(pluginPackage.getModuleId());

		_pluginPackages.remove(pluginPackage.getContext());
		_pluginPackages.put(pluginPackage.getContext(), pluginPackage);
	}

	public PluginPackage getLatestPluginPackage(
		String groupId, String artifactId) {

		PluginPackage result = null;

		Iterator iterator = _pluginPackages.values().iterator();
		while (iterator.hasNext()) {
			PluginPackage pluginPackage = (PluginPackage) iterator.next();

			if (pluginPackage.getGroupId().equals(groupId) &&
				pluginPackage.getArtifactId().equals(artifactId) &&
				(result == null ||
					pluginPackage.isLaterVersionThan(result))) {
				result = pluginPackage;
			}
		}

		return result;

	}

	public List getPluginPackages(String groupId, String artifactId) {

		List result = new ArrayList();

		Iterator iterator = _pluginPackages.values().iterator();

		while (iterator.hasNext()) {
			PluginPackage pluginPackage = (PluginPackage) iterator.next();

			if (pluginPackage.getGroupId().equals(groupId) &&
				pluginPackage.getArtifactId().equals(artifactId)) {
				result.add(pluginPackage);
			}
		}

		return result;
	}

	public PluginPackage getPluginPackage(String context) {

		return (PluginPackage) _pluginPackages.get(context);
	}

	public void removePluginPackage(PluginPackage pluginPackage) {
		_pluginPackages.remove(pluginPackage.getContext());
	}

	public void removePluginPackage(String context) {
		_pluginPackages.remove(context);
	}

	public PluginPackage getInstallingPluginPackage(String context) {
		return (PluginPackage) _pendingPackages.get(context);
	}

	public void registerPluginPackageInstallation(PluginPackage pluginPackage) {
		if (pluginPackage.getContext() != null) {
			PluginPackage previousPluginPackage =
				(PluginPackage) _pluginPackages.get(pluginPackage.getContext());
			if (previousPluginPackage == null) {
				addPluginPackage(pluginPackage);
			}
		}

		String key =
			pluginPackage.getContext();
		if (key == null) {
			key = pluginPackage.getModuleId();
		}

		_pendingPackages.put(key, pluginPackage);
	}

	public void registerPluginPackageInstallation(String deploymentContext) {

		PluginPackage pluginPackage = getPluginPackage(deploymentContext);

		if (pluginPackage == null) {
			String moduleId =
				deploymentContext + StringPool.SLASH + deploymentContext +
					StringPool.SLASH + Version.UNKNOWN + StringPool.SLASH +
						"war";

			pluginPackage = new PluginPackageImpl(moduleId);

			pluginPackage.setName(deploymentContext);
			pluginPackage.setContext(deploymentContext);
		}

		registerPluginPackageInstallation(pluginPackage);
	}

	public void unregisterPluginPackageInstallation(String context) {
		_pendingPackages.remove(context);
	}

	private Map _pluginPackages = new HashMap();
	private Map _pendingPackages = new HashMap();

}