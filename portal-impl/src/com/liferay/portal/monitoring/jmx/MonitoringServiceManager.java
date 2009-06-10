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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.monitoring.MonitoringLevel;
import com.liferay.portal.monitoring.MonitoringService;

import java.util.Set;

/**
 * <a href="MonitoringServiceManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class MonitoringServiceManager implements MonitoringServiceManagerMBean {

	public MonitoringServiceManager(MonitoringService monitorService) {
		_monitoringService = monitorService;
	}

	public String[] getMonitoredNamespaces() {
		Set<String> namespaces = _monitoringService.getMonitoredNamespaces();

		return namespaces.toArray(new String[namespaces.size()]);
	}

	public String getMonitoringLevel(String namespace) {
		MonitoringLevel level = _monitoringService.getMonitoringLevel(
			namespace);

		if (level == null) {
			level = MonitoringLevel.OFF;
		}

		return level.toString();
	}

	public void setMonitoringLevel(String namespace, String level) {
		MonitoringLevel monitoringLevel = MonitoringLevel.valueOf(level);

		_monitoringService.setMonitoringLevel(namespace, monitoringLevel);
	}

	private MonitoringService _monitoringService;

}