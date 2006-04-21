/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.communities.action;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.GroupServiceUtil;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutServiceUtil;
import com.liferay.portal.service.spring.LayoutSetServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.communities.form.PageForm;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPagesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditPagesAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		PageForm pageForm = (PageForm)form;

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLayout(pageForm, req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLayout(req);
			}
			else if (cmd.equals("display_order")) {
				updateDisplayOrder(req);
			}
			else if (cmd.equals("friendly_url")) {
				updateFriendlyURL(req);
			}
			else if (cmd.equals("look_and_feel")) {
				updateLookAndFeel(req);
			}

			String redirect = ParamUtil.getString(req, "pagesRedirect");

			sendRedirect(req, res, redirect);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.communities.error");
			}
			else if (e != null &&
					 e instanceof LayoutFriendlyURLException ||
					 e instanceof LayoutHiddenException ||
					 e instanceof LayoutNameException ||
					 e instanceof LayoutParentLayoutIdException ||
					 e instanceof LayoutTypeException ||
					 e instanceof RequiredLayoutException) {

				if (e instanceof LayoutFriendlyURLException) {
					SessionErrors.add(
						req, LayoutFriendlyURLException.class.getName(), e);
				}
				else {
					SessionErrors.add(req, e.getClass().getName(), e);
				}
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			ActionUtil.getGroup(req);
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.communities.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.communities.edit_pages"));
	}

	protected void copyPreferences(
			ActionRequest req, Layout layout, Layout copyLayout)
		throws Exception {

		String companyId = layout.getCompanyId();

		LayoutTypePortlet copyLayoutTypePortlet =
			(LayoutTypePortlet)copyLayout.getLayoutType();

		List copyPortletIds = copyLayoutTypePortlet.getPortletIds();

		for (int i = 0; i < copyPortletIds.size(); i++) {
			String copyPortletId = (String)copyPortletIds.get(i);

			ActionRequestImpl reqImpl = (ActionRequestImpl)req;

			HttpServletRequest httpReq =
				(HttpServletRequest)reqImpl.getHttpServletRequest();

			// Copy preference

			PortletPreferencesPK prefsPK =
				PortletPreferencesFactory.getPortletPreferencesPK(
					httpReq, copyPortletId, layout.getPlid());

			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, prefsPK);

			PortletPreferencesPK copyPrefsPK =
				PortletPreferencesFactory.getPortletPreferencesPK(
					httpReq, copyPortletId, copyLayout.getPlid());

			PortletPreferencesImpl copyPrefsImpl =
				(PortletPreferencesImpl)
					PortletPreferencesLocalServiceUtil.getPreferences(
						companyId, copyPrefsPK);

			PortletPreferencesLocalServiceUtil.updatePreferences(
				prefsPK, copyPrefsImpl);

			// Copy portlet setup

			prefsPK = new PortletPreferencesPK(
				copyPortletId, layout.getLayoutId(), layout.getOwnerId());

			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, prefsPK);

			copyPrefsPK = new PortletPreferencesPK(
				copyPortletId, copyLayout.getLayoutId(),
				copyLayout.getOwnerId());

			copyPrefsImpl =
				(PortletPreferencesImpl)
					PortletPreferencesLocalServiceUtil.getPreferences(
						companyId, copyPrefsPK);

			PortletPreferencesLocalServiceUtil.updatePreferences(
				prefsPK, copyPrefsImpl);
		}
	}

	protected void deleteLayout(ActionRequest req) throws Exception {
		String layoutId = ParamUtil.getString(req, "layoutId");
		String ownerId = ParamUtil.getString(req, "ownerId");

		LayoutServiceUtil.deleteLayout(layoutId, ownerId);
	}

	protected void updateDisplayOrder(ActionRequest req) throws Exception {
		String ownerId = ParamUtil.getString(req, "ownerId");

		String parentLayoutId = ParamUtil.getString(req, "parentLayoutId");
		String[] layoutIds = StringUtil.split(
			ParamUtil.getString(req, "layoutIds"));

		LayoutServiceUtil.setLayouts(ownerId, parentLayoutId, layoutIds);
	}

	protected void updateFriendlyURL(ActionRequest req) throws Exception {
		String groupId = ParamUtil.getString(req, "groupId");

		String friendlyURL = ParamUtil.getString(req, "friendlyURL");

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		GroupServiceUtil.updateGroup(groupId, group.getName(), friendlyURL);
	}

	protected void updateLayout(PageForm pageForm, ActionRequest req)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		String layoutId = ParamUtil.getString(req, "layoutId");
		String ownerId = ParamUtil.getString(req, "ownerId");

		String groupId = ParamUtil.getString(req, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(req, "privateLayout");

		String parentLayoutId = ParamUtil.getString(req, "parentLayoutId");
		String name = ParamUtil.getString(req, "name");
		String languageId = ParamUtil.getString(req, "curLanguageId");
		String type = ParamUtil.getString(req, "type");
		boolean hidden = ParamUtil.getBoolean(req, "hidden");
		String friendlyURL = ParamUtil.getString(req, "friendlyURL");

		String copyLayoutId = ParamUtil.getString(req, "copyLayoutId");

		if (cmd.equals(Constants.ADD)) {

			// Add layout

			Layout layout = LayoutServiceUtil.addLayout(
				groupId, privateLayout, parentLayoutId, name, type, hidden,
				friendlyURL);

			if (type.equals(Layout.TYPE_PORTLET)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.setLayoutTemplateId(
					LayoutTypePortlet.DEFAULT_LAYOUT_TEMPLATE_ID);

				LayoutServiceUtil.updateLayout(
					layout.getLayoutId(), layout.getOwnerId(),
					layout.getTypeSettings());
			}
		}
		else {

			// Update layout

			Layout layout = LayoutServiceUtil.updateLayout(
				layoutId, ownerId, parentLayoutId, name, languageId, type,
				hidden, friendlyURL);

			if (!type.equals(Layout.TYPE_PORTLET)) {
				layout.setTypeSettingsProperties(
					pageForm.getTypeSettingsProperties());

				LayoutServiceUtil.updateLayout(
					layoutId, ownerId, layout.getTypeSettings());
			}
			else if ((Validator.isNotNull(copyLayoutId)) &&
					 (!copyLayoutId.equals(layout.getLayoutId()))) {

				try {
					Layout copyLayout = LayoutLocalServiceUtil.getLayout(
						copyLayoutId, ownerId);

					if (copyLayout.getType().equals(Layout.TYPE_PORTLET)) {
						LayoutServiceUtil.updateLayout(
							layoutId, ownerId, copyLayout.getTypeSettings());

						copyPreferences(req, layout, copyLayout);
					}
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}
	}

	protected void updateLookAndFeel(ActionRequest req) throws Exception {
		String companyId = PortalUtil.getCompanyId(req);

		String layoutId = ParamUtil.getString(req, "layoutId");
		String ownerId = ParamUtil.getString(req, "ownerId");

		String themeId = ParamUtil.getString(req, "themeId");
		String colorSchemeId = ParamUtil.getString(req, "colorSchemeId");

		if (Validator.isNotNull(themeId) && Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalUtil.getColorScheme(
				companyId, themeId, colorSchemeId);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		if (Validator.isNull(layoutId)) {

			// Update layout set

			LayoutSetServiceUtil.updateLookAndFeel(
				ownerId, themeId, colorSchemeId);
		}
		else {

			// Update layout

			LayoutServiceUtil.updateLookAndFeel(
				layoutId, ownerId, themeId, colorSchemeId);
		}
	}

}