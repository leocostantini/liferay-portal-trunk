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

package com.liferay.portlet.mail.util.recipient;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DirectoryRecipientFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class DirectoryRecipientFinder implements RecipientFinder {

	public String getName() {
		return "directory";
	}

	public MultiValueMap getOptions(String userId) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			Organization organization = user.getOrganization();

			if (Validator.isNotNull(organization.getOrganizationId())) {
				return _options;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return new MultiValueMap();
	}

	public SortedSet getRecipients(String userId, String data, Map options) {
		SortedSet recipients = new TreeSet();

		data = data.toLowerCase();

		try {
			String optionOrganization =
				(String)options.get(_OPTION_ORGANIZATION);

			User user = UserLocalServiceUtil.getUserById(userId);

			Organization organization = user.getOrganization();

			LinkedHashMap params = null;

			if (Validator.isNotNull(organization.getOrganizationId()) &&
				(Validator.isNull(optionOrganization) ||
				 optionOrganization.equals("my-organization"))) {

				params = new LinkedHashMap();

				params.put("usersOrgs", organization.getOrganizationId());
			}

			List results = UserLocalServiceUtil.search(
				user.getCompanyId(), null, null, null, null, true, params, true,
				0, 50, null);

			for (int i = 0; i < results.size(); i++) {
				User recipient = (User)results.get(i);

				String str =
					recipient.getFullName() + StringPool.SPACE +
						StringPool.LESS_THAN + recipient.getEmailAddress() +
							StringPool.GREATER_THAN;

				if (str.toLowerCase().indexOf(data) != -1) {
					recipients.add(str);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return recipients;
	}

	public boolean isReadOnly(String userId) {
		return true;
	}

	public void save(String userId, InternetAddress ia)
		throws UnsupportedOperationException {

		throw new UnsupportedOperationException();
	}

	private static MultiValueMap _options = new MultiValueMap();

	private static final String _OPTION_ORGANIZATION = "organization";

	static {
		_options.put(_OPTION_ORGANIZATION, "my-organization");
		_options.put(_OPTION_ORGANIZATION, "all-available");
	}

	private static Log _log = LogFactory.getLog(DirectoryRecipientFinder.class);

}