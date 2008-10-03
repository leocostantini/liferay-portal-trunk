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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.shopping.BillingCityException;
import com.liferay.portlet.shopping.BillingCountryException;
import com.liferay.portlet.shopping.BillingEmailAddressException;
import com.liferay.portlet.shopping.BillingFirstNameException;
import com.liferay.portlet.shopping.BillingLastNameException;
import com.liferay.portlet.shopping.BillingPhoneException;
import com.liferay.portlet.shopping.BillingStateException;
import com.liferay.portlet.shopping.BillingStreetException;
import com.liferay.portlet.shopping.BillingZipException;
import com.liferay.portlet.shopping.CCExpirationException;
import com.liferay.portlet.shopping.CCNameException;
import com.liferay.portlet.shopping.CCNumberException;
import com.liferay.portlet.shopping.CCTypeException;
import com.liferay.portlet.shopping.ShippingCityException;
import com.liferay.portlet.shopping.ShippingCountryException;
import com.liferay.portlet.shopping.ShippingEmailAddressException;
import com.liferay.portlet.shopping.ShippingFirstNameException;
import com.liferay.portlet.shopping.ShippingLastNameException;
import com.liferay.portlet.shopping.ShippingPhoneException;
import com.liferay.portlet.shopping.ShippingStateException;
import com.liferay.portlet.shopping.ShippingStreetException;
import com.liferay.portlet.shopping.ShippingZipException;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil;
import com.liferay.portlet.shopping.util.ShoppingPreferences;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="CheckoutAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CheckoutAction extends CartAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (redirectToLogin(actionRequest, actionResponse)) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		getLatestOrder(actionRequest);

		if (cmd.equals(Constants.SAVE)) {
			updateCart(actionRequest);
			updateLatestOrder(actionRequest);
			saveLatestOrder(actionRequest);
			forwardCheckout(actionRequest, actionResponse);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			try {
				updateLatestOrder(actionRequest);

				setForward(actionRequest, "portlet.shopping.checkout_second");
			}
			catch (Exception e) {
				if (e instanceof BillingCityException ||
					e instanceof BillingCountryException ||
					e instanceof BillingEmailAddressException ||
					e instanceof BillingFirstNameException ||
					e instanceof BillingLastNameException ||
					e instanceof BillingPhoneException ||
					e instanceof BillingStateException ||
					e instanceof BillingStreetException ||
					e instanceof BillingZipException ||
					e instanceof CCExpirationException ||
					e instanceof CCNameException ||
					e instanceof CCNumberException ||
					e instanceof CCTypeException ||
					e instanceof ShippingCityException ||
					e instanceof ShippingCountryException ||
					e instanceof ShippingEmailAddressException ||
					e instanceof ShippingFirstNameException ||
					e instanceof ShippingLastNameException ||
					e instanceof ShippingPhoneException ||
					e instanceof ShippingStateException ||
					e instanceof ShippingStreetException ||
					e instanceof ShippingZipException) {

					SessionErrors.add(actionRequest, e.getClass().getName());

					setForward(
						actionRequest, "portlet.shopping.checkout_first");
				}
				else if (e instanceof PrincipalException) {
					setForward(actionRequest, "portlet.shopping.error");
				}
				else {
					throw e;
				}
			}
		}
		else if (cmd.equals(Constants.VIEW)) {
			setForward(actionRequest, "portlet.shopping.checkout_third");
		}
		else {
			setForward(actionRequest, "portlet.shopping.checkout_first");
		}
	}

	protected void forwardCheckout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ShoppingCart cart = ShoppingUtil.getCart(actionRequest);

		ShoppingOrder order = (ShoppingOrder)actionRequest.getAttribute(
			WebKeys.SHOPPING_ORDER);

		ShoppingPreferences preferences = ShoppingPreferences.getInstance(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId());

		String returnURL = ShoppingUtil.getPayPalReturnURL(
			((ActionResponseImpl)actionResponse).createActionURL(), order);
		String notifyURL = ShoppingUtil.getPayPalNotifyURL(themeDisplay);

		if (preferences.usePayPal()) {
			double total = ShoppingUtil.calculateTotal(
				cart.getItems(), order.getBillingState(), cart.getCoupon(),
				cart.getAltShipping(), cart.isInsure());

			String redirectURL = ShoppingUtil.getPayPalRedirectURL(
				preferences, order, total, returnURL, notifyURL);

			actionResponse.sendRedirect(redirectURL);
		}
		else {
			ShoppingOrderLocalServiceUtil.sendEmail(order, "confirmation");

			actionResponse.sendRedirect(returnURL);
		}
	}

	protected void getLatestOrder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ShoppingOrder order = ShoppingOrderLocalServiceUtil.getLatestOrder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId());

		actionRequest.setAttribute(WebKeys.SHOPPING_ORDER, order);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void saveLatestOrder(ActionRequest actionRequest)
		throws Exception {

		ShoppingCart cart = ShoppingUtil.getCart(actionRequest);

		ShoppingOrder order =
			ShoppingOrderLocalServiceUtil.saveLatestOrder(cart);

		actionRequest.setAttribute(WebKeys.SHOPPING_ORDER, order);
	}

	protected void updateLatestOrder(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String billingFirstName = ParamUtil.getString(
			actionRequest, "billingFirstName");
		String billingLastName = ParamUtil.getString(
			actionRequest, "billingLastName");
		String billingEmailAddress = ParamUtil.getString(
			actionRequest, "billingEmailAddress");
		String billingCompany = ParamUtil.getString(
			actionRequest, "billingCompany");
		String billingStreet = ParamUtil.getString(
			actionRequest, "billingStreet");
		String billingCity = ParamUtil.getString(actionRequest, "billingCity");

		String billingStateSel = ParamUtil.getString(
			actionRequest, "billingStateSel");
		String billingState = billingStateSel;
		if (Validator.isNull(billingStateSel)) {
			billingState = ParamUtil.getString(actionRequest, "billingState");
		}

		String billingZip = ParamUtil.getString(actionRequest, "billingZip");
		String billingCountry = ParamUtil.getString(
			actionRequest, "billingCountry");
		String billingPhone = ParamUtil.getString(
			actionRequest, "billingPhone");

		boolean shipToBilling = ParamUtil.getBoolean(
			actionRequest, "shipToBilling");
		String shippingFirstName = ParamUtil.getString(
			actionRequest, "shippingFirstName");
		String shippingLastName = ParamUtil.getString(
			actionRequest, "shippingLastName");
		String shippingEmailAddress = ParamUtil.getString(
			actionRequest, "shippingEmailAddress");
		String shippingCompany = ParamUtil.getString(
			actionRequest, "shippingCompany");
		String shippingStreet = ParamUtil.getString(
			actionRequest, "shippingStreet");
		String shippingCity = ParamUtil.getString(
			actionRequest, "shippingCity");

		String shippingStateSel = ParamUtil.getString(
			actionRequest, "shippingStateSel");
		String shippingState = shippingStateSel;
		if (Validator.isNull(shippingStateSel)) {
			shippingState = ParamUtil.getString(actionRequest, "shippingState");
		}

		String shippingZip = ParamUtil.getString(actionRequest, "shippingZip");
		String shippingCountry = ParamUtil.getString(
			actionRequest, "shippingCountry");
		String shippingPhone = ParamUtil.getString(
			actionRequest, "shippingPhone");

		String ccName = ParamUtil.getString(actionRequest, "ccName");
		String ccType = ParamUtil.getString(actionRequest, "ccType");
		String ccNumber = ParamUtil.getString(actionRequest, "ccNumber");
		int ccExpMonth = ParamUtil.getInteger(actionRequest, "ccExpMonth");
		int ccExpYear = ParamUtil.getInteger(actionRequest, "ccExpYear");
		String ccVerNumber = ParamUtil.getString(actionRequest, "ccVerNumber");

		String comments = ParamUtil.getString(actionRequest, "comments");

		ShoppingOrder order = ShoppingOrderLocalServiceUtil.updateLatestOrder(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);

		actionRequest.setAttribute(WebKeys.SHOPPING_ORDER, order);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}