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

package com.liferay.portlet.addressbook.action;

import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.Recipient;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.addressbook.util.ABUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAllAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ViewAllAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			List recipients = ABUtil.getRecipients();

			String letter = req.getParameter("letter");

			if (Validator.isChar(letter)) {
				List temp = new ArrayList();

				for (int i = 0; i < recipients.size(); i++) {
					Recipient recipient = (Recipient)recipients.get(i);

					if (StringUtil.startsWith(
							recipient.getRecipientName(), letter)) {

						temp.add(recipient);
					}
				}

				recipients = temp;
			}

			req.setAttribute(WebKeys.ADDRESS_BOOK_RECIPIENTS, recipients);

			if (req.getWindowState().equals(WindowState.NORMAL)) {
				return mapping.findForward("portlet.address_book.view");
			}
			else {
				return mapping.findForward("portlet.address_book.view_all");
			}
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(Constants.COMMON_ERROR);
		}
	}

}