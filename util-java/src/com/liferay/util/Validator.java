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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.cal.CalendarUtil;

/**
 * <a href="Validator.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Alysa Carver
 *
 */
public class Validator {

	public static boolean equals(String s1, String s2) {
		if ((s1 == null) && (s2 == null)) {
			return true;
		}
		else if ((s1 == null) || (s2 == null)) {
			return false;
		}
		else {
			return s1.equals(s2);
		}
	}

	public static boolean isAddress(String address) {
		if (isNull(address)) {
			return false;
		}

		String[] tokens = address.split(StringPool.AT);

		if (tokens.length != 2) {
			return false;
		}

		for (int i = 0; i < tokens.length; i++) {
			char[] c = tokens[i].toCharArray();

			for (int j = 0; j < c.length; j++) {
				if (Character.isWhitespace(c[j])) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isChar(char c) {
		return Character.isLetter(c);
	}

	public static boolean isChar(String s) {
		if (isNull(s)) {
			return false;
		}

		char[] c = s.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (!isChar(c[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean isDigit(char c) {
		int x = (int)c;

		if ((x >= 48) && (x <= 57)) {
			return true;
		}

		return false;
	}

	public static boolean isDigit(String s) {
		if (isNull(s)) {
			return false;
		}

		char[] c = s.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (!isDigit(c[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean isHex(String s) {
		if (isNull(s)) {
			return false;
		}

		return true;
	}

	public static boolean isHTML(String s) {
		if (isNull(s)) {
			return false;
		}

		if (((s.indexOf("<html>") != -1) || (s.indexOf("<HTML>") != -1)) &&
			((s.indexOf("</html>") != -1) || (s.indexOf("</HTML>") != -1))) {

			return true;
		}

		return false;
	}

	public static boolean isLUHN(String number) {
		if (number == null) {
			return false;
		}

		number = StringUtil.reverse(number);

		int total = 0;

		for (int i = 0; i < number.length(); i++) {
			int x = 0;

			if (((i + 1) % 2) == 0) {
				x = Integer.parseInt(number.substring(i, i + 1)) * 2;

				if (x >= 10) {
					String s = Integer.toString(x);

					x = Integer.parseInt(s.substring(0, 1)) +
						Integer.parseInt(s.substring(1, 2));
				}
			}
			else {
				x = Integer.parseInt(number.substring(i, i + 1));
			}

			total = total + x;
		}

		if ((total % 10) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isDate(int month, int day, int year) {
		return CalendarUtil.isDate(month, day, year);
	}

	public static boolean isGregorianDate(int month, int day, int year) {
		return CalendarUtil.isGregorianDate(month, day, year);
	}

	public static boolean isJulianDate(int month, int day, int year) {
		return CalendarUtil.isJulianDate(month, day, year);
	}

	public static boolean isEmailAddress(String ea) {
		if (isNull(ea)) {
			return false;
		}

		int eaLength = ea.length();

		if (eaLength < 6) {

			// j@j.c

			return false;
		}

		ea = ea.toLowerCase();

		int at = ea.indexOf('@');

		// Unix based email addresses cannot be longer than 24 characters.
		// However, many Windows based email addresses can be longer than 24,
		// so we will set the maximum at 96.

		//int maxEmailLength = 24;
		int maxEmailLength = 96;

		if ((at > maxEmailLength) || (at == -1) || (at == 0) ||
			((at <= eaLength) && (at > eaLength - 5))) {

			// 123456789012345678901234@joe.com
			// joe.com
			// @joe.com
			// joe@joe
			// joe@jo
			// joe@j

			return false;
		}

		int dot = ea.lastIndexOf('.');

		if ((dot == -1) || (dot < at) || (dot > eaLength - 3)) {

			// joe@joecom
			// joe.@joecom
			// joe@joe.c

			return false;
		}

		if (ea.indexOf("..") != -1) {

			// joe@joe..com

			return false;
		}

		char[] name = ea.substring(0, at).toCharArray();

		for (int i = 0; i < name.length; i++) {
			if ((!isChar(name[i])) &&
				(!isDigit(name[i])) &&
				(!isEmailAddressSpecialChar(name[i]))) {

				return false;
			}
		}

		if (isEmailAddressSpecialChar(name[0]) ||
			isEmailAddressSpecialChar(name[name.length - 1])) {

			// .joe.@joe.com
			// -joe-@joe.com
			// _joe_@joe.com

			return false;
		}

		char[] host = ea.substring(at + 1, ea.length()).toCharArray();

		for (int i = 0; i < host.length; i++) {
			if ((!isChar(host[i])) &&
				(!isDigit(host[i])) &&
				(!isEmailAddressSpecialChar(host[i]))) {

				return false;
			}
		}

		if (isEmailAddressSpecialChar(host[0]) ||
			isEmailAddressSpecialChar(host[host.length - 1])) {

			// joe@.joe.com.
			// joe@-joe.com-

			return false;
		}

		// postmaster@joe.com

		if (ea.startsWith("postmaster@")) {
			return false;
		}

		// root@.com

		if (ea.startsWith("root@")) {
			return false;
		}

		return true;
	}

	public static boolean isEmailAddressSpecialChar(char c) {

		// LEP-1445

		for (int i = 0; i < _EMAIL_ADDRESS_SPECIAL_CHAR.length; i++) {
			if (c == _EMAIL_ADDRESS_SPECIAL_CHAR[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated Use <code>isEmailAddress</code>.
	 */
	public static boolean isValidEmailAddress(String ea) {
		return isEmailAddress(ea);
	}

	public static boolean isName(String name) {
		if (isNull(name)) {
			return false;
		}

		char[] c = name.trim().toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (((!isChar(c[i])) &&
				(!Character.isWhitespace(c[i]))) ||
					(c[i] == ',')) {

				return false;
			}
		}

		return true;
	}

	public static boolean isNumber(String number) {
		if (isNull(number)) {
			return false;
		}

		char[] c = number.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (!isDigit(c[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean isNull(Long l) {
		if ((l == null) || l.longValue() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNull(String s) {
		if (s == null) {
			return true;
		}

		s = s.trim();

		if ((s.equals(StringPool.NULL)) || (s.equals(StringPool.BLANK))) {
			return true;
		}

		return false;
	}

	public static boolean isNull(Object[] array) {
		if ((array == null) || (array.length == 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNotNull(String s) {
		return !isNull(s);
	}

	public static boolean isNotNull(Long l) {
		return !isNull(l);
	}

	public static boolean isNotNull(Object[] array) {
		return !isNull(array);
	}

	public static boolean isPassword(String password) {
		if (isNull(password)) {
			return false;
		}

		if (password.length() < 4) {
			return false;
		}

		char[] c = password.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if ((!isChar(c[i])) &&
				(!isDigit(c[i]))) {

				return false;
			}
		}

		return true;
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		return isNumber(StringUtil.extractDigits(phoneNumber));
	}

	private static char[] _EMAIL_ADDRESS_SPECIAL_CHAR = new char[] {
		'.', '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^',
		'_', '`', '{', '|', '}', '~'
	};

}