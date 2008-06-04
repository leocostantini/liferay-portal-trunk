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

package com.liferay.portal.service;


/**
 * <a href="ThemeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ThemeLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ThemeLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ThemeLocalService
 * @see com.liferay.portal.service.ThemeLocalServiceFactory
 *
 */
public class ThemeLocalServiceUtil {
	public static com.liferay.portal.model.ColorScheme getColorScheme(
		long companyId, java.lang.String themeId,
		java.lang.String colorSchemeId, boolean wapTheme) {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		return themeLocalService.getColorScheme(companyId, themeId,
			colorSchemeId, wapTheme);
	}

	public static com.liferay.portal.model.Theme getTheme(long companyId,
		java.lang.String themeId, boolean wapTheme) {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		return themeLocalService.getTheme(companyId, themeId, wapTheme);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		return themeLocalService.getThemes(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId, long groupId, long userId, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		return themeLocalService.getThemes(companyId, groupId, userId, wapTheme);
	}

	public static java.util.List<String> init(
		javax.servlet.ServletContext ctx, java.lang.String themesPath,
		boolean loadFromServletContext, java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		return themeLocalService.init(ctx, themesPath, loadFromServletContext,
			xmls, pluginPackage);
	}

	public static java.util.List<String> init(
		java.lang.String servletContextName, javax.servlet.ServletContext ctx,
		java.lang.String themesPath, boolean loadFromServletContext,
		java.lang.String[] xmls,
		com.liferay.portal.kernel.plugin.PluginPackage pluginPackage) {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		return themeLocalService.init(servletContextName, ctx, themesPath,
			loadFromServletContext, xmls, pluginPackage);
	}

	public static void uninstallThemes(java.util.List<String> themeIds) {
		ThemeLocalService themeLocalService = ThemeLocalServiceFactory.getService();

		themeLocalService.uninstallThemes(themeIds);
	}
}