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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.List;

public class GroupImpl extends GroupModelImpl implements Group {

	public GroupImpl() {
	}

	public boolean isCommunity() {
		return hasClassName(Group.class);
	}

	public boolean isCompany() {
		return hasClassName(Company.class);
	}

	public boolean isLayout() {
		return hasClassName(Layout.class);
	}

	public boolean isLayoutPrototype() {
		return hasClassName(LayoutPrototype.class);
	}

	public boolean isOrganization() {
		return hasClassName(Organization.class);
	}

	public boolean isUser() {
		return hasClassName(User.class);
	}

	public boolean isUserGroup() {
		return hasClassName(UserGroup.class);
	}

	public Group getLiveGroup() {
		if (!isStagingGroup()) {
			return null;
		}

		try {
			if (_liveGroup == null) {
				_liveGroup = GroupLocalServiceUtil.getGroup(
					getLiveGroupId());
			}

			return _liveGroup;
		}
		catch (Exception e) {
			_log.error("Error getting live group for " + getLiveGroupId(), e);

			return null;
		}
	}

	public Group getStagingGroup() {
		if (isStagingGroup()) {
			return null;
		}

		try {
			if (_stagingGroup == null) {
				_stagingGroup =
					GroupLocalServiceUtil.getStagingGroup(getGroupId());
			}

			return _stagingGroup;
		}
		catch (Exception e) {
			_log.error("Error getting staging group for " + getGroupId(), e);

			return null;
		}
	}

	public boolean hasStagingGroup() {
		if (isStagingGroup()) {
			return false;
		}

		if (_stagingGroup != null) {
			return true;
		}

		try {
			return GroupLocalServiceUtil.hasStagingGroup(getGroupId());
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isStagingGroup() {
		if (getLiveGroupId() == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
			return false;
		}
		else {
			return true;
		}
	}

	public String getDescriptiveName() {
		String name = getName();

		try {
			if (isCompany()) {
				name = "global";
			}
			else if (isLayout()) {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					getClassPK());

				name = layout.getName(LocaleUtil.getDefault());
			}
			else if (isLayoutPrototype()) {
				LayoutPrototype layoutPrototype =
					LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
						getClassPK());

				name = layoutPrototype.getName(LocaleUtil.getDefault());
			}
			else if (isOrganization()) {
				long organizationId = getClassPK();

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				name = organization.getName();
			}
			else if (isUser()) {
				long userId = getClassPK();

				User user = UserLocalServiceUtil.getUserById(userId);

				name = user.getFullName();
			}
			else if (isUserGroup()) {
				long userGroupId = getClassPK();

				UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
					userGroupId);

				name = userGroup.getName();
			}
		}
		catch (Exception e) {
			_log.error("Error getting descriptive name for " + getGroupId(), e);
		}

		return name;
	}

	public String getTypeLabel() {
		return GroupConstants.getTypeLabel(getType());
	}

	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}
		else {
			return _typeSettingsProperties.toString();
		}
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			try {
				_typeSettingsProperties.load(super.getTypeSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _typeSettingsProperties;
	}

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	public String getPathFriendlyURL(
		boolean privateLayout, ThemeDisplay themeDisplay) {

		if (privateLayout) {
			if (isUser()) {
				return themeDisplay.getPathFriendlyURLPrivateUser();
			}
			else {
				return themeDisplay.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			return themeDisplay.getPathFriendlyURLPublic();
		}
	}

	public long getDefaultPrivatePlid() {
		return getDefaultPlid(true);
	}

	public int getPrivateLayoutsPageCount() {
		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), true);

			return layoutSet.getPageCount();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getDefaultPublicPlid() {
		return getDefaultPlid(false);
	}

	public int getPublicLayoutsPageCount() {
		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), false);

			return layoutSet.getPageCount();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isWorkflowEnabled() {
		return GetterUtil.getBoolean(
			getTypeSettingsProperty("workflowEnabled"));
	}

	public int getWorkflowStages() {
		return GetterUtil.getInteger(
			getTypeSettingsProperty("workflowStages"),
			PropsValues.TASKS_DEFAULT_STAGES);
	}

	public String getWorkflowRoleNames() {
		return GetterUtil.getString(
			getTypeSettingsProperty("workflowRoleNames"),
			PropsValues.TASKS_DEFAULT_ROLE_NAMES);
	}

	protected long getDefaultPlid(boolean privateLayout) {
		try {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				getGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	protected boolean hasClassName(Class<?> classObj) {
		long classNameId = getClassNameId();

		if (classNameId == PortalUtil.getClassNameId(classObj)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GroupImpl.class);

	private Group _stagingGroup;
	private Group _liveGroup;
	private UnicodeProperties _typeSettingsProperties = null;

}