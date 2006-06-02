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

package com.liferay.portal.service.impl;

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyHomeURLException;
import com.liferay.portal.CompanyPortalURLException;
import com.liferay.portal.NoSuchAccountException;
import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.AccountUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.ContactUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.CompanyLocalService;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.PropertiesLocalServiceUtil;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ImageUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;
import com.liferay.util.lucene.Hits;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.imageio.ImageIO;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

/**
 * <a href="CompanyLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CompanyLocalServiceImpl implements CompanyLocalService {

	public void checkCompany(String companyId)
		throws PortalException, SystemException {

		// Company

		String companyMx = companyId;
		Date now = new Date();

		Company company = null;

		try {
			company = CompanyUtil.findByPrimaryKey(companyId);
		}
		catch (NoSuchCompanyException nsce) {
			String portalURL = "localhost";
			String homeURL = "localhost";
			String name = companyId;
			String legalName = null;
			String legalId = null;
			String legalType = null;
			String sicCode = null;
			String tickerSymbol = null;
			String industry = null;
			String type = null;
			String size = null;

			company = CompanyUtil.create(companyId);

			try {
				company.setKeyObj(Encryptor.generateKey());
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}

			company.setPortalURL(portalURL);
			company.setHomeURL(homeURL);
			company.setMx(companyMx);

			CompanyUtil.update(company);

			updateCompany(
				companyId, portalURL, homeURL, companyMx, name, legalName,
				legalId, legalType, sicCode, tickerSymbol, industry, type,
				size);

			// Demo settings

			if (companyId.equals("liferay.net")) {
				company = CompanyUtil.findByPrimaryKey(companyId);

				company.setPortalURL("demo.liferay.net");
				company.setHomeURL("demo.liferay.net");

				CompanyUtil.update(company);

				updateSecurity(companyId, Company.AUTH_TYPE_EA, true, true);

				PortletPreferences prefs = AdminUtil.getPreferences(companyId);

				try {
					prefs.setValue("email-from-name", "Liferay Demo");
					prefs.setValue("email-from-address", "test@liferay.net");

					prefs.store();
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}
			}
		}

		// Key

		checkCompanyKey(companyId);

		// Default user

		User defaultUser = null;

		try {
			defaultUser = UserLocalServiceUtil.getDefaultUser(companyId);
		}
		catch (NoSuchUserException nsue) {
			defaultUser = UserUtil.create(User.getDefaultUserId(companyId));

			defaultUser.setCompanyId(User.DEFAULT);
			defaultUser.setCreateDate(now);
			defaultUser.setModifiedDate(now);
			defaultUser.setPassword("password");
			defaultUser.setEmailAddress(User.DEFAULT + "@" + companyMx);
			defaultUser.setLanguageId(null);
			defaultUser.setTimeZoneId(null);
			defaultUser.setGreeting("Welcome!");
			defaultUser.setResolution(
				PropsUtil.get(PropsUtil.DEFAULT_GUEST_LAYOUT_RESOLUTION));
			defaultUser.setLoginDate(now);
			defaultUser.setFailedLoginAttempts(0);
			defaultUser.setAgreedToTermsOfUse(false);
			defaultUser.setActive(true);

			UserUtil.update(defaultUser);

			// Contact

			Contact defaultContact = ContactUtil.create(
				defaultUser.getUserId());

			defaultContact.setCompanyId(defaultUser.getCompanyId());
			defaultContact.setUserId(defaultUser.getUserId());
			defaultContact.setUserName(StringPool.BLANK);
			defaultContact.setCreateDate(now);
			defaultContact.setModifiedDate(now);
			defaultContact.setAccountId(defaultUser.getCompanyId());
			defaultContact.setParentContactId(
				Contact.DEFAULT_PARENT_CONTACT_ID);
			defaultContact.setFirstName(StringPool.BLANK);
			defaultContact.setMiddleName(StringPool.BLANK);
			defaultContact.setLastName(StringPool.BLANK);
			defaultContact.setMale(true);
			defaultContact.setBirthday(now);

			ContactUtil.update(defaultContact);
		}

		// System groups

		GroupLocalServiceUtil.checkSystemGroups(companyId);

		// System roles

		RoleLocalServiceUtil.checkSystemRoles(companyId);

		// Default admin

		if (UserUtil.countByCompanyId(companyId) == 0) {
			String creatorUserId = null;
			boolean autoUserId = true;
			String userId = StringPool.BLANK;
			boolean autoPassword = false;
			String password1 = "test";
			String password2 = "test";
			boolean passwordReset = false;
			String emailAddress = "test@" + companyMx;
			Locale locale = defaultUser.getLocale();
			String firstName = "Test";
			String middleName = StringPool.BLANK;
			String lastName = "Test";
			String nickName = StringPool.BLANK;
			String prefixId = StringPool.BLANK;
			String suffixId = StringPool.BLANK;
			boolean male = true;
			int birthdayMonth = Calendar.JANUARY;
			int birthdayDay = 1;
			int birthdayYear = 1970;
			String jobTitle = StringPool.BLANK;
			String organizationId = null;
			String locationId = null;

			if (companyId.equals("liferay.net")) {
				firstName = "Liferay";
				lastName = "Demo";
			}

			User user = UserLocalServiceUtil.addUser(
				creatorUserId, companyId, autoUserId, userId, autoPassword,
				password1, password2, passwordReset, emailAddress, locale,
				firstName, middleName, lastName, nickName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				organizationId, locationId, false);

			Role adminRole = RoleLocalServiceUtil.getRole(
				companyId, Role.ADMINISTRATOR);

			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, Role.POWER_USER);

			String[] roleIds = new String[] {
				adminRole.getRoleId(), powerUserRole.getRoleId()
			};

			RoleLocalServiceUtil.setUserRoles(user.getUserId(), roleIds);
		}
	}

	public void checkCompanyKey(String companyId)
		throws PortalException, SystemException {

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		if (company.getKeyObj() == null) {
			try {
				company.setKeyObj(Encryptor.generateKey());
			}
			catch (EncryptorException ee) {
				throw new SystemException(ee);
			}
		}

		CompanyUtil.update(company);
	}

	public List getCompanies() throws SystemException {
		return CompanyUtil.findAll();
	}

	public Company getCompany(String companyId)
		throws PortalException, SystemException {

		return CompanyUtil.findByPrimaryKey(companyId);
	}

	public Hits search(String companyId, String keywords)
		throws SystemException {

		return search(companyId, null, keywords);
	}

	public Hits search(String companyId, String groupId, String keywords)
		throws SystemException {

		try {
			Hits hits = new Hits();

			if (Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery booleanQuery = new BooleanQuery();

			if (Validator.isNotNull(groupId)) {
				LuceneUtil.addRequiredTerm(
					booleanQuery, LuceneFields.GROUP_ID, groupId);
			}

			LuceneUtil.addTerm(booleanQuery, LuceneFields.TITLE, keywords);
			LuceneUtil.addTerm(booleanQuery, LuceneFields.CONTENT, keywords);

			Searcher searcher = LuceneUtil.getSearcher(companyId);

			Query query = QueryParser.parse(
				booleanQuery.toString(), LuceneFields.CONTENT,
				LuceneUtil.getAnalyzer());

			hits.recordHits(searcher.search(query));

			return hits;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ParseException pe) {
			throw new SystemException(pe);
		}
	}

	public Company updateCompany(
			String companyId, String portalURL, String homeURL, String mx,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws PortalException, SystemException {

		// Company

		Date now = new Date();

		validate(portalURL, homeURL, name);

		Company company = CompanyUtil.findByPrimaryKey(companyId);

		company.setPortalURL(portalURL);
		company.setHomeURL(homeURL);

		if (GetterUtil.getBoolean(PropsUtil.get(PropsUtil.MAIL_MX_UPDATE))) {
			company.setMx(mx);
		}

		CompanyUtil.update(company);

		// Account

		String accountId = companyId;

		Account account = null;

		try {
			account = AccountUtil.findByPrimaryKey(accountId);
		}
		catch (NoSuchAccountException nsae) {
			account = AccountUtil.create(accountId);

			account.setCreateDate(now);
			account.setCompanyId(companyId);
			account.setUserId(User.getDefaultUserId(companyId));
			account.setUserName(StringPool.BLANK);
		}

		account.setModifiedDate(now);
		account.setName(name);
		account.setLegalName(legalName);
		account.setLegalId(legalId);
		account.setLegalType(legalType);
		account.setSicCode(sicCode);
		account.setTickerSymbol(tickerSymbol);
		account.setIndustry(industry);
		account.setType(type);
		account.setSize(size);

		AccountUtil.update(account);

		return company;
	}

	public void updateDisplay(
			String companyId, String languageId, String timeZoneId,
			String resolution)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getDefaultUser(companyId);

		user.setLanguageId(languageId);
		user.setTimeZoneId(timeZoneId);
		user.setResolution(resolution);

		UserUtil.update(user);
	}

	public void updateLogo(String companyId, File file)
		throws PortalException, SystemException {

		try {
			ImageLocalUtil.put(companyId, FileUtil.getBytes(file));

			BufferedImage thumbnail = ImageUtil.scale(ImageIO.read(file), .6);

			// PNG

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ImageIO.write(thumbnail, "png", baos);

			ImageLocalUtil.put(companyId + ".png", baos.toByteArray());

			// WBMP

			baos = new ByteArrayOutputStream();

			ImageUtil.encodeWBMP(thumbnail, baos);

			ImageLocalUtil.put(companyId + ".wbmp", baos.toByteArray());
		}
		catch (InterruptedException ie) {
			throw new SystemException(ie);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void updateSecurity(
			String companyId, String authType, boolean autoLogin,
			boolean strangers)
		throws PortalException, SystemException {

		Properties propertiesObj =
			PropertiesLocalServiceUtil.getPortalProperties(companyId);

		propertiesObj.setProperty(
			PropsUtil.COMPANY_SECURITY_AUTH_TYPE, authType);
		propertiesObj.setProperty(
			PropsUtil.COMPANY_SECURITY_AUTO_LOGIN, Boolean.toString(autoLogin));
		propertiesObj.setProperty(
			PropsUtil.COMPANY_SECURITY_STRANGERS, Boolean.toString(strangers));

		PropertiesLocalServiceUtil.updatePortalProperties(
			companyId, propertiesObj);
	}

	protected void validate(String portalURL, String homeURL, String name)
		throws PortalException {

		if (Validator.isNull(portalURL)) {
			throw new CompanyPortalURLException();
		}
		else if (Validator.isNull(homeURL)) {
			throw new CompanyHomeURLException();
		}
		else if (Validator.isNull(name)) {
			throw new AccountNameException();
		}
	}

}