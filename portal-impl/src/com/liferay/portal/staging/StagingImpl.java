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

package com.liferay.portal.staging;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.RemoteOptionsException;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.staging.Staging;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.messaging.LayoutsLocalPublisherRequest;
import com.liferay.portlet.communities.messaging.LayoutsRemotePublisherRequest;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.portlet.PortletRequest;

/**
 * <a href="StagingImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Wesley Gong
 */
public class StagingImpl implements Staging {

	public void copyFromLive(PortletRequest portletRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			portletRequest);

		_publishLayouts(
			portletRequest, liveGroupId, stagingGroupId, parameterMap, false);
	}

	public void copyFromLive(
			PortletRequest portletRequest, Portlet portlet)
		throws Exception {

		long plid = ParamUtil.getLong(portletRequest, "plid");

		Layout targetLayout = LayoutLocalServiceUtil.getLayout(plid);

		Group stagingGroup = targetLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout sourceLayout = LayoutLocalServiceUtil.getLayout(
			liveGroup.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId());

		copyPortlet(
			portletRequest, liveGroup.getGroupId(), stagingGroup.getGroupId(),
			sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	public void copyPortlet(
			PortletRequest portletRequest, long sourceGroupId,
			long targetGroupId, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception {

		Map<String, String[]> parameterMap = getStagingParameters(
			portletRequest);

		File file = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			sourcePlid, sourceGroupId, portletId, parameterMap, null, null);

		try {
			LayoutServiceUtil.importPortletInfo(
				targetPlid, targetGroupId, portletId, parameterMap, file);
		}
		finally {
			file.delete();
		}
	}

	public void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> exportParameterMap, String remoteAddress,
			int remotePort, boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout,
			Map<String, String[]> importParameterMap, Date startDate,
			Date endDate)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = UserLocalServiceUtil.getUser(permissionChecker.getUserId());

		StringBundler sb = new StringBundler(4);

		if (secureConnection) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(remoteAddress);
		sb.append(StringPool.COLON);
		sb.append(remotePort);

		String url = sb.toString();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			url, user.getEmailAddress(), user.getPassword(),
			user.getPasswordEncrypted());

		// Ping remote host and verify that the group exists

		try {
			GroupServiceHttp.getGroup(httpPrincipal, remoteGroupId);
		}
		catch (NoSuchGroupException nsge) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (SystemException se) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION);

			ree.setURL(url);

			throw ree;
		}

		byte[] bytes = null;

		if (layoutIdMap == null) {
			bytes = LayoutServiceUtil.exportLayouts(
				sourceGroupId, privateLayout, exportParameterMap, startDate,
				endDate);
		}
		else {
			List<Layout> layouts = new ArrayList<Layout>();

			Iterator<Map.Entry<Long, Boolean>> itr1 =
				layoutIdMap.entrySet().iterator();

			while (itr1.hasNext()) {
				Entry<Long, Boolean> entry = itr1.next();

				long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
				boolean includeChildren = entry.getValue();

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				if (!layouts.contains(layout)) {
					layouts.add(layout);
				}

				Iterator<Layout> itr2 = getMissingParents(
					layout, sourceGroupId).iterator();

				while (itr2.hasNext()) {
					Layout parentLayout = itr2.next();

					if (!layouts.contains(parentLayout)) {
						layouts.add(parentLayout);
					}
				}

				if (includeChildren) {
					itr2 = layout.getAllChildren().iterator();

					while (itr2.hasNext()) {
						Layout childLayout = itr2.next();

						if (!layouts.contains(childLayout)) {
							layouts.add(childLayout);
						}
					}
				}
			}

			long[] layoutIds = new long[layouts.size()];

			for (int i = 0; i < layouts.size(); i++) {
				Layout curLayout = layouts.get(i);

				layoutIds[i] = curLayout.getLayoutId();
			}

			if (layoutIds.length <= 0) {
				throw new RemoteExportException(
					RemoteExportException.NO_LAYOUTS);
			}

			bytes = LayoutServiceUtil.exportLayouts(
				sourceGroupId, privateLayout, layoutIds, exportParameterMap,
				startDate, endDate);
		}

		LayoutServiceHttp.importLayouts(
			httpPrincipal, remoteGroupId, remotePrivateLayout,
			importParameterMap, bytes);
	}

	public List<Layout> getMissingParents(
			Layout layout, long liveGroupId)
		throws PortalException, SystemException {

		List<Layout> missingParents = new ArrayList<Layout>();

		long parentLayoutId = layout.getParentLayoutId();

		while (parentLayoutId > 0) {
			try {
				LayoutLocalServiceUtil.getLayout(
					liveGroupId, layout.isPrivateLayout(), parentLayoutId);

				// If one parent is found all others are assumed to exist

				break;
			}
			catch (NoSuchLayoutException nsle) {
				Layout parent = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					parentLayoutId);

				missingParents.add(parent);

				parentLayoutId = parent.getParentLayoutId();
			}
		}

		return missingParents;
	}

	public String getSchedulerGroupName(
		String destinationName, long groupId) {

		return destinationName.concat(StringPool.SLASH).concat(
			String.valueOf(groupId));
	}

	public Map<String, String[]> getStagingParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		return parameterMap;
	}

	public Map<String, String[]> getStagingParameters(
		PortletRequest portletRequest) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>(
				portletRequest.getParameterMap());

		if (!parameterMap.containsKey(PortletDataHandlerKeys.DATA_STRATEGY)) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS)) {

			parameterMap.put(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.DELETE_PORTLET_DATA)) {

			parameterMap.put(
				PortletDataHandlerKeys.DELETE_PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA_ALL)) {

			Boolean portletDataAll = Boolean.FALSE;

			if (MapUtil.getBoolean(
					parameterMap, PortletDataHandlerKeys.PORTLET_DATA)) {

				portletDataAll = Boolean.TRUE;
			}

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {portletDataAll.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.PORTLET_SETUP)) {
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_SETUP,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.THEME)) {
			parameterMap.put(
				PortletDataHandlerKeys.THEME,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.USER_ID_STRATEGY)) {

			parameterMap.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.CURRENT_USER_ID});
		}

		return parameterMap;
	}

	public void publishLayout(
			long plid, long liveGroupId, boolean includeChildren)
		throws Exception {

		Map<String, String[]> parameterMap = getStagingParameters();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		List<Layout> layouts = new ArrayList<Layout>();

		layouts.add(layout);

		layouts.addAll(getMissingParents(layout, liveGroupId));

		if (includeChildren) {
			layouts.addAll(layout.getAllChildren());
		}

		Iterator<Layout> itr = layouts.iterator();

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; itr.hasNext(); i++) {
			Layout curLayout = itr.next();

			layoutIds[i] = curLayout.getLayoutId();
		}

		publishLayouts(
			layout.getGroupId(), liveGroupId, layout.isPrivateLayout(),
			layoutIds, parameterMap, null, null);
	}

	public void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		File file = LayoutLocalServiceUtil.exportLayoutsAsFile(
			sourceGroupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			LayoutServiceUtil.importLayouts(
				targetGroupId, privateLayout, parameterMap, file);
		}
		finally {
			file.delete();
		}
	}

	public void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		List<Layout> layouts = new ArrayList<Layout>();

		Iterator<Map.Entry<Long, Boolean>> itr1 =
			layoutIdMap.entrySet().iterator();

		while (itr1.hasNext()) {
			Entry<Long, Boolean> entry = itr1.next();

			long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
			boolean includeChildren = entry.getValue();

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			Iterator<Layout> itr2 = getMissingParents(
				layout, targetGroupId).iterator();

			while (itr2.hasNext()) {
				Layout parentLayout = itr2.next();

				if (!layouts.contains(parentLayout)) {
					layouts.add(parentLayout);
				}
			}

			if (includeChildren) {
				itr2 = layout.getAllChildren().iterator();

				while (itr2.hasNext()) {
					Layout childLayout = itr2.next();

					if (!layouts.contains(childLayout)) {
						layouts.add(childLayout);
					}
				}
			}
		}

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; i < layouts.size(); i++) {
			Layout curLayout = layouts.get(i);

			layoutIds[i] = curLayout.getLayoutId();
		}

		publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, (long[])null,
			parameterMap, startDate, endDate);
	}

	public void publishToLive(PortletRequest portletRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		Group liveGroup = GroupLocalServiceUtil.getGroup(groupId);

		Map<String, String[]> parameterMap = getStagingParameters(
			portletRequest);

		if (liveGroup.isStaged()) {
			if (liveGroup.isStagedRemotely()) {
				publishToRemote(portletRequest);
			}
			else {
				Group stagingGroup = liveGroup.getStagingGroup();

				_publishLayouts(
					portletRequest, stagingGroup.getGroupId(), groupId,
					parameterMap, false);
			}
		}
	}

	public void publishToLive(
			PortletRequest portletRequest, Portlet portlet)
		throws Exception {

		long plid = ParamUtil.getLong(portletRequest, "plid");

		Layout sourceLayout = LayoutLocalServiceUtil.getLayout(plid);

		Group stagingGroup = sourceLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout targetLayout = LayoutLocalServiceUtil.getLayout(
			liveGroup.getGroupId(), sourceLayout.isPrivateLayout(),
			sourceLayout.getLayoutId());

		copyPortlet(
			portletRequest, stagingGroup.getGroupId(), liveGroup.getGroupId(),
			sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	public void publishToRemote(PortletRequest portletRequest)
		throws Exception {

		_publishToRemote(portletRequest, false);
	}

	public void scheduleCopyFromLive(PortletRequest portletRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			portletRequest);

		_publishLayouts(
			portletRequest, liveGroupId, stagingGroupId, parameterMap, true);
	}

	public void schedulePublishToLive(PortletRequest portletRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			portletRequest);

		_publishLayouts(
			portletRequest, stagingGroupId, liveGroupId, parameterMap, true);
	}

	public void schedulePublishToRemote(PortletRequest portletRequest)
		throws Exception {

		_publishToRemote(portletRequest, true);
	}

	public void unscheduleCopyFromLive(PortletRequest portletRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, stagingGroupId);

		LayoutServiceUtil.unschedulePublishToLive(
			stagingGroupId, jobName, groupName);
	}

	public void unschedulePublishToLive(PortletRequest portletRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			portletRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, liveGroupId);

		LayoutServiceUtil.unschedulePublishToLive(
			liveGroupId, jobName, groupName);
	}

	public void unschedulePublishToRemote(PortletRequest portletRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		String jobName = ParamUtil.getString(portletRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER, groupId);

		LayoutServiceUtil.unschedulePublishToRemote(
			groupId, jobName, groupName);
	}

	public void updateStaging(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long liveGroupId = ParamUtil.getLong(portletRequest, "liveGroupId");

		if (!GroupPermissionUtil.contains(
				permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {

			throw new PrincipalException();
		}

		int stagingType = ParamUtil.getInteger(portletRequest, "stagingType");

		if (stagingType == StagingConstants.TYPE_NOT_STAGED) {
			_disableStaging(portletRequest, liveGroupId);
		}
		else if (stagingType == StagingConstants.TYPE_LOCAL_STAGING) {
			_enableLocalStaging(portletRequest, liveGroupId);
		}
		else if (stagingType == StagingConstants.TYPE_REMOTE_STAGING) {
			_enableRemoteStaging(portletRequest, liveGroupId);
		}
	}

	private void _addWeeklyDayPos(
		PortletRequest portletRequest, List<DayAndPosition> list, int day) {

		if (ParamUtil.getBoolean(portletRequest, "weeklyDayPos" + day)) {
			list.add(new DayAndPosition(day, 0));
		}
	}

	protected void _disableStaging(
			PortletRequest portletRequest, long liveGroupId)
		throws Exception {

		Group liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

		UnicodeProperties properties =
			liveGroup.getTypeSettingsProperties();

		properties.remove("isStaged");
		properties.remove("isStagedRemotely");
		properties.remove("remoteAddress");
		properties.remove("remotePort");
		properties.remove("secureConnection");
		properties.remove("remoteGroupId");
		properties.remove("workflowEnabled");
		properties.remove("workflowStages");
		properties.remove("workflowRoleNames");

		Set<String> keys = new HashSet<String>();

		for (String key : properties.keySet()) {
			if (key.startsWith(StagingConstants.IS_STAGED_PORTLET)) {
				keys.add(key);
			}
		}

		for (String key : keys) {
			properties.remove(key);
		}

		if (liveGroup.hasStagingGroup()) {
			GroupLocalServiceUtil.deleteGroup(
				liveGroup.getStagingGroup().getGroupId());

			TasksProposalLocalServiceUtil.deleteProposals(
				liveGroup.getGroupId());
		}

		GroupLocalServiceUtil.updateGroup(
			liveGroup.getGroupId(), properties.toString());
	}

	protected void _enableLocalStaging(
			PortletRequest portletRequest, long liveGroupId)
		throws Exception {

		Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

		if (liveGroup.isStagedRemotely()) {
			_disableStaging(portletRequest, liveGroupId);
		}

		UnicodeProperties properties = liveGroup.getTypeSettingsProperties();

		properties.setProperty("isStaged", Boolean.TRUE.toString());
		properties.setProperty("isStagedRemotely", String.valueOf(false));

		_setCommonStagingOptions(portletRequest, properties);

		if (!liveGroup.hasStagingGroup()) {
			Group stagingGroup = GroupLocalServiceUtil.addGroup(
				liveGroup.getCreatorUserId(), liveGroup.getClassName(),
				liveGroup.getClassPK(), liveGroup.getGroupId(),
				liveGroup.getDescriptiveName() + " (Staging)",
				liveGroup.getDescription(), liveGroup.getType(),
				liveGroup.getFriendlyURL().concat("-staging"),
				liveGroup.isActive(), null);

			GroupServiceUtil.updateGroup(
				liveGroup.getGroupId(), properties.toString());

			if (liveGroup.hasPrivateLayouts()) {
				Map<String, String[]> parameterMap = getStagingParameters();

				publishLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), true,
					parameterMap, null, null);
			}

			if (liveGroup.hasPublicLayouts()) {
				Map<String, String[]> parameterMap = getStagingParameters();

				publishLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), false,
					parameterMap, null, null);
			}
		}
		else {
			GroupServiceUtil.updateGroup(
				liveGroup.getGroupId(), properties.toString());
		}
	}

	protected void _enableRemoteStaging(
			PortletRequest portletRequest, long liveGroupId)
		throws Exception {

		Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

		if (liveGroup.hasStagingGroup()) {
			_disableStaging(portletRequest, liveGroupId);
		}

		UnicodeProperties properties = liveGroup.getTypeSettingsProperties();

		String remoteAddress = ParamUtil.getString(
			portletRequest, "remoteAddress");
		int remotePort = ParamUtil.getInteger(portletRequest, "remotePort");
		boolean secureConnection = ParamUtil.getBoolean(
			portletRequest, "secureConnection");
		long remoteGroupId = ParamUtil.getLong(portletRequest, "remoteGroupId");

		_validate(remoteAddress, remotePort, remoteGroupId, secureConnection);

		properties.setProperty("isStaged", Boolean.TRUE.toString());
		properties.setProperty("isStagedRemotely", String.valueOf(true));

		properties.setProperty("remoteAddress", remoteAddress);
		properties.setProperty("remotePort", String.valueOf(remotePort));
		properties.setProperty(
			"secureConnection", String.valueOf(secureConnection));
		properties.setProperty(
			"remoteGroupId", String.valueOf(remoteGroupId));

		_setCommonStagingOptions(portletRequest, properties);

		GroupServiceUtil.updateGroup(
			liveGroup.getGroupId(), properties.toString());
	}

	private String _getCronText(
			PortletRequest portletRequest, Calendar startDate,
			boolean timeZoneSensitive, int recurrenceType)
		throws Exception {

		Calendar startCal = null;

		if (timeZoneSensitive) {
			startCal = CalendarFactoryUtil.getCalendar();

			startCal.setTime(startDate.getTime());
		}
		else {
			startCal = (Calendar)startDate.clone();
		}

		Recurrence recurrence = new Recurrence(
			startCal, new Duration(1, 0, 0, 0), recurrenceType);

		recurrence.setWeekStart(Calendar.SUNDAY);

		if (recurrenceType == Recurrence.DAILY) {
			int dailyType = ParamUtil.getInteger(portletRequest, "dailyType");

			if (dailyType == 0) {
				int dailyInterval = ParamUtil.getInteger(
					portletRequest, "dailyInterval", 1);

				recurrence.setInterval(dailyInterval);
			}
			else {
				DayAndPosition[] dayPos = {
					new DayAndPosition(Calendar.MONDAY, 0),
					new DayAndPosition(Calendar.TUESDAY, 0),
					new DayAndPosition(Calendar.WEDNESDAY, 0),
					new DayAndPosition(Calendar.THURSDAY, 0),
					new DayAndPosition(Calendar.FRIDAY, 0)};

				recurrence.setByDay(dayPos);
			}
		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			int weeklyInterval = ParamUtil.getInteger(
				portletRequest, "weeklyInterval", 1);

			recurrence.setInterval(weeklyInterval);

			List<DayAndPosition> dayPos = new ArrayList<DayAndPosition>();

			_addWeeklyDayPos(portletRequest, dayPos, Calendar.SUNDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.MONDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.TUESDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.WEDNESDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.THURSDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.FRIDAY);
			_addWeeklyDayPos(portletRequest, dayPos, Calendar.SATURDAY);

			if (dayPos.size() == 0) {
				dayPos.add(new DayAndPosition(Calendar.MONDAY, 0));
			}

			recurrence.setByDay(dayPos.toArray(new DayAndPosition[0]));
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			int monthlyType = ParamUtil.getInteger(
				portletRequest, "monthlyType");

			if (monthlyType == 0) {
				int monthlyDay = ParamUtil.getInteger(
					portletRequest, "monthlyDay0", 1);

				recurrence.setByMonthDay(new int[] {monthlyDay});

				int monthlyInterval = ParamUtil.getInteger(
					portletRequest, "monthlyInterval0", 1);

				recurrence.setInterval(monthlyInterval);
			}
			else {
				int monthlyPos = ParamUtil.getInteger(
					portletRequest, "monthlyPos");
				int monthlyDay = ParamUtil.getInteger(
					portletRequest, "monthlyDay1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(monthlyDay, monthlyPos)};

				recurrence.setByDay(dayPos);

				int monthlyInterval = ParamUtil.getInteger(
					portletRequest, "monthlyInterval1", 1);

				recurrence.setInterval(monthlyInterval);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			int yearlyType = ParamUtil.getInteger(portletRequest, "yearlyType");

			if (yearlyType == 0) {
				int yearlyMonth = ParamUtil.getInteger(
					portletRequest, "yearlyMonth0");
				int yearlyDay = ParamUtil.getInteger(
					portletRequest, "yearlyDay0", 1);

				recurrence.setByMonth(new int[] {yearlyMonth});
				recurrence.setByMonthDay(new int[] {yearlyDay});

				int yearlyInterval = ParamUtil.getInteger(
					portletRequest, "yearlyInterval0", 1);

				recurrence.setInterval(yearlyInterval);
			}
			else {
				int yearlyPos = ParamUtil.getInteger(
					portletRequest, "yearlyPos");
				int yearlyDay = ParamUtil.getInteger(
					portletRequest, "yearlyDay1");
				int yearlyMonth = ParamUtil.getInteger(
					portletRequest, "yearlyMonth1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(yearlyDay, yearlyPos)};

				recurrence.setByDay(dayPos);

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyInterval = ParamUtil.getInteger(
					portletRequest, "yearlyInterval1", 1);

				recurrence.setInterval(yearlyInterval);
			}
		}

		return RecurrenceSerializer.toCronText(recurrence);
	}

	private Calendar _getDate(
			PortletRequest portletRequest, String paramPrefix,
			boolean timeZoneSensitive)
		throws Exception {

		int dateMonth = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Month");
		int dateDay = ParamUtil.getInteger(portletRequest, paramPrefix + "Day");
		int dateYear = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Year");
		int dateHour = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Hour");
		int dateMinute = ParamUtil.getInteger(
			portletRequest, paramPrefix + "Minute");
		int dateAmPm = ParamUtil.getInteger(
			portletRequest, paramPrefix + "AmPm");

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

			locale = themeDisplay.getLocale();
			timeZone = themeDisplay.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getDefault();
		}

		Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

		cal.set(Calendar.MONTH, dateMonth);
		cal.set(Calendar.DATE, dateDay);
		cal.set(Calendar.YEAR, dateYear);
		cal.set(Calendar.HOUR_OF_DAY, dateHour);
		cal.set(Calendar.MINUTE, dateMinute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	protected String _getWorkflowRoleNames(PortletRequest portletRequest) {
		int workflowStages = ParamUtil.getInteger(
			portletRequest, "workflowStages");

		String workflowRoleNames;

		if (workflowStages == 0) {
			workflowRoleNames = StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler(workflowStages * 2 - 1);

			for (int i = 1; i <= (workflowStages - 1); i++) {
				if (i > 1) {
					sb.append(",");
				}

				String workflowRoleName = ParamUtil.getString(
					portletRequest, "workflowRoleName_" + i);

				sb.append(workflowRoleName);
			}

			String workflowRoleName = ParamUtil.getString(
				portletRequest, "workflowRoleName_Last");

			sb.append(",");
			sb.append(workflowRoleName);

			workflowRoleNames = sb.toString();
		}

		return workflowRoleNames;
	}

	private void _publishLayouts(
			PortletRequest portletRequest, long sourceGroupId, long targetGroupId,
			Map<String, String[]> parameterMap, boolean schedule)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(portletRequest, "scope");

		Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

		if (scope.equals("selected-pages")) {
			long[] rowIds = ParamUtil.getLongValues(portletRequest, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					portletRequest, "includeChildren_" + selPlid);

				layoutIdMap.put(selPlid, includeChildren);
			}
		}

		String range = ParamUtil.getString(portletRequest, "range");

		Date startDate = null;
		Date endDate = null;

		if (range.equals("dateRange")) {
			startDate = _getDate(portletRequest, "startDate", true).getTime();

			endDate = _getDate(portletRequest, "endDate", true).getTime();
		}
		else if (range.equals("fromLastPublishDate")) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				sourceGroupId, privateLayout);

			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			long lastPublishDate = GetterUtil.getLong(
				settingsProperties.getProperty("last-publish-date"));

			if (lastPublishDate > 0) {
				Calendar cal = Calendar.getInstance(
					themeDisplay.getTimeZone(), themeDisplay.getLocale());

				endDate = cal.getTime();

				cal.setTimeInMillis(lastPublishDate);

				startDate = cal.getTime();
			}
		}
		else if (range.equals("last")) {
			int rangeLast = ParamUtil.getInteger(portletRequest, "last");

			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		if (schedule) {
			String groupName = getSchedulerGroupName(
				DestinationNames.LAYOUTS_LOCAL_PUBLISHER, targetGroupId);

			int recurrenceType = ParamUtil.getInteger(
				portletRequest, "recurrenceType");

			Calendar startCal = _getDate(
				portletRequest, "schedulerStartDate", true);

			String cronText = _getCronText(
				portletRequest, startCal, true, recurrenceType);

			Date schedulerEndDate = null;

			int endDateType = ParamUtil.getInteger(
				portletRequest, "endDateType");

			if (endDateType == 1) {
				Calendar endCal = _getDate(
					portletRequest, "schedulerEndDate", true);

				schedulerEndDate = endCal.getTime();
			}

			String description = ParamUtil.getString(
				portletRequest, "description");

			LayoutServiceUtil.schedulePublishToLive(
				sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
				parameterMap, scope, startDate, endDate, groupName, cronText,
				startCal.getTime(), schedulerEndDate, description);
		}
		else {
			MessageStatus messageStatus = new MessageStatus();

			messageStatus.startTimer();

			String command =
				LayoutsLocalPublisherRequest.COMMAND_SELECTED_PAGES;

			try {
				if (scope.equals("all-pages")) {
					command = LayoutsLocalPublisherRequest.COMMAND_ALL_PAGES;

					publishLayouts(
						sourceGroupId, targetGroupId, privateLayout,
						parameterMap, startDate, endDate);
				}
				else {
					publishLayouts(
						sourceGroupId, targetGroupId, privateLayout,
						layoutIdMap, parameterMap, startDate, endDate);
				}
			}
			catch (Exception e) {
				messageStatus.setException(e);

				throw e;
			}
			finally {
				messageStatus.stopTimer();

				LayoutsLocalPublisherRequest publisherRequest =
					new LayoutsLocalPublisherRequest(
						command, themeDisplay.getUserId(), sourceGroupId,
						targetGroupId, privateLayout, layoutIdMap, parameterMap,
						startDate, endDate);

				messageStatus.setPayload(publisherRequest);

				MessageBusUtil.sendMessage(
					DestinationNames.MESSAGE_BUS_MESSAGE_STATUS, messageStatus);
			}
		}
	}

	private void _publishToRemote(
			PortletRequest portletRequest, boolean schedule)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String tabs1 = ParamUtil.getString(portletRequest, "tabs1");

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(portletRequest, "scope");

		if (Validator.isNull(scope)) {
			scope = "all-pages";
		}

		Map<Long, Boolean> layoutIdMap = null;
		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		if (scope.equals("selected-pages")) {
			layoutIdMap = new LinkedHashMap<Long, Boolean>();

			long[] rowIds = ParamUtil.getLongValues(portletRequest, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					portletRequest, "includeChildren_" + selPlid);

				layoutIdMap.put(selPlid, includeChildren);
			}
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UnicodeProperties groupTypeSettingsProperties =
			group.getTypeSettingsProperties();

		String remoteAddress = ParamUtil.getString(
			portletRequest, "remoteAddress",
			groupTypeSettingsProperties.getProperty("remoteAddress"));
		int remotePort = ParamUtil.getInteger(
			portletRequest, "remotePort",
			GetterUtil.getInteger(
				groupTypeSettingsProperties.getProperty("remotePort")));
		boolean secureConnection = ParamUtil.getBoolean(
			portletRequest, "secureConnection",
			GetterUtil.getBoolean(
				groupTypeSettingsProperties.getProperty("secureConnection")));

		long remoteGroupId = ParamUtil.getLong(
			portletRequest, "remoteGroupId",
			GetterUtil.getLong(
				groupTypeSettingsProperties.getProperty("remoteGroupId")));

		boolean remotePrivateLayout = ParamUtil.getBoolean(
			portletRequest, "remotePrivateLayout");

		_validate(remoteAddress, remotePort, remoteGroupId, secureConnection);

		String range = ParamUtil.getString(portletRequest, "range");

		Date startDate = null;
		Date endDate = null;

		if (range.equals("dateRange")) {
			startDate = _getDate(portletRequest, "startDate", true).getTime();

			endDate = _getDate(portletRequest, "endDate", true).getTime();
		}
		else if (range.equals("fromLastPublishDate")) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, privateLayout);

			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			long lastPublishDate = GetterUtil.getLong(
				settingsProperties.getProperty("last-publish-date"));

			if (lastPublishDate > 0) {
				Calendar cal = Calendar.getInstance(
					themeDisplay.getTimeZone(), themeDisplay.getLocale());

				endDate = cal.getTime();

				cal.setTimeInMillis(lastPublishDate);

				startDate = cal.getTime();
			}
		}
		else if (range.equals("last")) {
			int rangeLast = ParamUtil.getInteger(portletRequest, "last");

			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		if (schedule) {
			String groupName = getSchedulerGroupName(
				DestinationNames.LAYOUTS_REMOTE_PUBLISHER, groupId);

			int recurrenceType = ParamUtil.getInteger(
				portletRequest, "recurrenceType");

			Calendar startCal = _getDate(
				portletRequest, "schedulerStartDate", true);

			String cronText = _getCronText(
				portletRequest, startCal, true, recurrenceType);

			Date schedulerEndDate = null;

			int endDateType = ParamUtil.getInteger(
				portletRequest, "endDateType");

			if (endDateType == 1) {
				Calendar endCal = _getDate(
					portletRequest, "schedulerEndDate", true);

				schedulerEndDate = endCal.getTime();
			}

			String description = ParamUtil.getString(
				portletRequest, "description");

			LayoutServiceUtil.schedulePublishToRemote(
				groupId, privateLayout, layoutIdMap,
				getStagingParameters(portletRequest), remoteAddress, remotePort,
				secureConnection, remoteGroupId, remotePrivateLayout, startDate,
				endDate, groupName, cronText, startCal.getTime(),
				schedulerEndDate, description);
		}
		else {
			MessageStatus messageStatus = new MessageStatus();

			messageStatus.startTimer();

			try {
				copyRemoteLayouts(
					groupId, privateLayout, layoutIdMap, parameterMap,
					remoteAddress, remotePort, secureConnection, remoteGroupId,
					remotePrivateLayout, getStagingParameters(portletRequest),
					startDate, endDate);
			}
			catch (Exception e) {
				messageStatus.setException(e);

				throw e;
			}
			finally {
				messageStatus.stopTimer();

				LayoutsRemotePublisherRequest publisherRequest =
					new LayoutsRemotePublisherRequest(
						themeDisplay.getUserId(), groupId, privateLayout,
						layoutIdMap, parameterMap, remoteAddress, remotePort,
						secureConnection, remoteGroupId, remotePrivateLayout,
						startDate, endDate);

				messageStatus.setPayload(publisherRequest);

				MessageBusUtil.sendMessage(
					DestinationNames.MESSAGE_BUS_MESSAGE_STATUS, messageStatus);
			}
		}
	}

	protected void _setCommonStagingOptions(
		PortletRequest portletRequest, UnicodeProperties properties) {

		Enumeration<String> parameterNames = portletRequest.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			boolean isStaged = MapUtil.getBoolean(
				portletRequest.getParameterMap(), parameterName);

			if (parameterName.startsWith(StagingConstants.IS_STAGED_PORTLET) &&
				!parameterName.endsWith("Checkbox")) {

				properties.setProperty(parameterName, String.valueOf(isStaged));
			}
		}

		boolean workflowEnabled = false;

		int workflowStages = ParamUtil.getInteger(
			portletRequest, "workflowStages");

		if (workflowStages > 1) {
			workflowEnabled = true;
		}

		properties.setProperty(
			"workflowEnabled", String.valueOf(workflowEnabled));

		if (workflowEnabled) {
			String workflowRoleNames = _getWorkflowRoleNames(portletRequest);

			if (workflowStages < PropsValues.TASKS_DEFAULT_STAGES) {
				workflowStages = PropsValues.TASKS_DEFAULT_STAGES;
			}

			if (Validator.isNull(workflowRoleNames)) {
				workflowRoleNames = PropsValues.TASKS_DEFAULT_ROLE_NAMES;
			}

			properties.setProperty(
				"workflowStages", String.valueOf(workflowStages));
			properties.setProperty("workflowRoleNames", workflowRoleNames);
		}
	}

	protected void _validate(
			String remoteAddress, int remotePort, long remoteGroupId,
			boolean secureConnection)
		throws Exception {

		RemoteOptionsException roe;

		if (!Validator.isIPAddress(remoteAddress) &&
			!Validator.isDomain(remoteAddress)) {

			roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_ADDRESS);

			roe.setRemoteAddress(remoteAddress);

			throw roe;
		}

		if ((remotePort < 1) || (remotePort > 65535)) {
			roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_PORT);

			roe.setRemotePort(remotePort);

			throw roe;
		}

		if (remoteGroupId <= 0) {
			roe = new RemoteOptionsException(
				RemoteOptionsException.REMOTE_GROUP_ID);

			roe.setRemoteGroupId(remoteGroupId);

			throw roe;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = UserLocalServiceUtil.getUser(permissionChecker.getUserId());

		StringBundler sb = new StringBundler(4);

		if (secureConnection) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(remoteAddress);
		sb.append(StringPool.COLON);
		sb.append(remotePort);

		String url = sb.toString();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			url, user.getEmailAddress(), user.getPassword(),
			user.getPasswordEncrypted());

		// Ping remote host and verify that the group exists

		try {
			GroupServiceHttp.getGroup(httpPrincipal, remoteGroupId);
		}
		catch (NoSuchGroupException nsge) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (SystemException se) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION);

			ree.setURL(url);

			throw ree;
		}

	}

}