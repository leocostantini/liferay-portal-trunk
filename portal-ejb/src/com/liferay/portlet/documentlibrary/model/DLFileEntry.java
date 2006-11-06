/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portlet.documentlibrary.service.spring.DLFolderLocalServiceUtil;
import com.liferay.util.NullSafeProperties;
import com.liferay.util.PropertiesUtil;

import java.io.IOException;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLFileEntry.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntry extends DLFileEntryModel {

	public static final double DEFAULT_VERSION = 1.0;

	public static final int DEFAULT_READ_COUNT = 0;

	public DLFileEntry() {
	}

	public DLFolder getFolder() {
		DLFolder folder = null;

		try {
			folder = DLFolderLocalServiceUtil.getFolder(getFolderId());
		}
		catch (Exception e) {
			folder = new DLFolder();

			_log.error(e);
		}

		return folder;
	}

	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return PropertiesUtil.toString(_extraSettingsProperties);
		}
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	public Properties getExtraSettingsProperties() {
		if (_extraSettingsProperties == null) {
			_extraSettingsProperties = new NullSafeProperties();

			try {
				PropertiesUtil.load(
					_extraSettingsProperties, super.getExtraSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		return _extraSettingsProperties;
	}

	public void setExtraSettingsProperties(Properties extraSettingsProperties) {
		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(
			PropertiesUtil.toString(_extraSettingsProperties));
	}

	private static Log _log = LogFactory.getLog(DLFileEntry.class);

	private Properties _extraSettingsProperties = null;

}