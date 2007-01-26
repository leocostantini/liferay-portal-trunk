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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.Plugin;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.xml.XMLSafeReader;

import java.io.IOException;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="PluginUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PluginUtil {

	public static List getAllPlugins() {

		SortedSet plugins = new TreeSet();
		String[] pluginRepositories = getRepositoryURLs();

		for (int i = 0; i < pluginRepositories.length; i++) {
			String pluginRepository = pluginRepositories[i];
			PluginRepository pluginsCache = getRepository(
					pluginRepository);
			plugins.addAll(pluginsCache.getPlugins());
		}

		return new ArrayList(plugins);
	}

	public static Plugin getPluginById(String moduleId, String repositoryURL)
			throws DocumentException, IOException {

		return getRepository(repositoryURL).findPlugin(moduleId);
	}

	public static String[] getRepositoryURLs() {
		return PropsUtil.getArray(PropsUtil.PLUGIN_REPOSITORIES);
	}

	public static PluginRepository getRepository(String repositoryURL) {

		PluginRepository repository =
				(PluginRepository)_repositoryCache.get(repositoryURL);

		if (repository != null) {
			return repository;
		}

		String pluginsXmlURL = repositoryURL + StringPool.SLASH +
				_PLUGINS_XML_FILENAME;

		try {
			HttpClient client = new HttpClient();

			GetMethod getFile = new GetMethod(pluginsXmlURL);

			int responseCode = client.executeMethod(getFile);
			if (responseCode != 200) {
				throw new RuntimeException("Cannot download file: " +
						pluginsXmlURL +" Response" + " code: " + responseCode);
			}

			byte[] bytes = getFile.getResponseBody();

			getFile.releaseConnection();

			if ((bytes != null) && (bytes.length > 0)) {

				repository =
						_parsePluginsXml(new String(bytes), repositoryURL);

				return repository;
			}
			else {

				throw new RuntimeException("Download error");
			}
		}
		catch (MalformedURLException mue) {

			throw new RuntimeException("Invalid URL:" + pluginsXmlURL);
		}
		catch (IOException ioe) {

			throw new RuntimeException("Communication error: " + ioe);
		}
		catch (DocumentException de) {

			throw new RuntimeException("Parse Error: " + de);
		}
	}

	private static PluginRepository _parsePluginsXml(
			String xml, String repositoryURL)
			throws DocumentException, IOException {

		_log.debug("Plugins: " + xml);

		PluginRepository plugins = new PluginRepository();

		if (xml == null) {
			return plugins;
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Iterator itr1 = root.elements().iterator();

		while (itr1.hasNext()) {
			Element pluginElm = (Element)itr1.next();

			String pluginName = pluginElm.elementText("name");

			if (_log.isDebugEnabled()) {
				_log.debug("Reading plugin definition " + pluginName);
			}

			Plugin plugin = new PluginImpl();

			List liferayVersions = _readElementList(
					pluginElm.element("liferay-versions"), "liferay-version");
			if (!_isCurrentVersionSupported(liferayVersions)) {
				break;
			}

			plugin.setRepositoryURL(repositoryURL);
			plugin.setTags(liferayVersions);

			plugin.setName(GetterUtil.getString(
					pluginElm.elementText("name"), plugin.getName()));
			plugin.setModuleId(GetterUtil.getString(
					pluginElm.elementText("module-id"), plugin.getModuleId()));
			plugin.setType(GetterUtil.getString(
					pluginElm.elementText("type"), plugin.getType()));
			plugin.setShortDescription(GetterUtil.getString(
					pluginElm.elementText("short-description"),
					plugin.getShortDescription()));
			plugin.setLongDescription(GetterUtil.getString(
					pluginElm.elementText("long-description"),
					plugin.getLongDescription()));
			plugin.setPageURL(GetterUtil.getString(
					pluginElm.elementText("page-url"), plugin.getPageURL()));
			plugin.setAuthor(GetterUtil.getString(
					pluginElm.elementText("author"), plugin.getAuthor()));

			plugin.setTags(_readElementList(pluginElm.element("tags"), "tag"));

			plugin.setLicenses(_readElementList(
					pluginElm.element("licenses"), "license"));

			plugins.addPlugin(plugin);
		}

		return plugins;
	}

	private static boolean _isCurrentVersionSupported(List versions) {

		for (int i = 0; i < versions.size(); i++) {

			String version = (String) versions.get(i);

			String currentVersion = ReleaseInfo.getVersion();

			if (currentVersion.equals(version)) {

				return true;
			}

			String minorVersion = version.substring(0,
					version.lastIndexOf('.'));

			String currentMinorVersion = currentVersion.substring(
					0, currentVersion.lastIndexOf('.'));

			if (version.endsWith(".*") &&
				(currentMinorVersion.equals(minorVersion))) {

				return true;
			}
		}

		return false;
	}

	private static List _readElementList(Element parent, String childTagName) {

		List result = new ArrayList();

		Iterator itr2 = parent.elements(childTagName).iterator();

		while (itr2.hasNext()) {
			Element tagEl = (Element)itr2.next();

			result.add(tagEl.getText());
		}

		return result;
	}

	private static Plugin getSamplePlugin(String pluginRepository) {
		Plugin p = new PluginImpl();
		p.setRepositoryURL(pluginRepository);
		p.setModuleId("liferay-samples/sample-struts-portlet/4.2.1/war");
		p.setName("Sample Struts portlet");
		p.setType("portlet");
		p.setAuthor("Jorge");
		p.setShortDescription("bla bla bla");
		p.setPageURL("http://www.liferay.com");

		List licenses = new ArrayList();
		licenses.add("MIT");
		p.setLicenses(licenses);
		return p;
	}

	private static final String _PLUGINS_XML_FILENAME = "liferay-plugins.xml";

	private static Log _log = LogFactory.getLog(PluginUtil.class);
	private static Map _repositoryCache = new HashMap();
}