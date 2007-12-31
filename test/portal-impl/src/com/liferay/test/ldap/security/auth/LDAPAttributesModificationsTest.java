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

package com.liferay.test.ldap.security.auth;

import com.liferay.test.TestProps;
import com.liferay.test.ldap.BaseLDAPTest;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * <a href="LDAPAttributesModificationsTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jerry Niu
 *
 */
public class LDAPAttributesModificationsTest extends BaseLDAPTest {

	protected void useContext(DirContext ctx) {
		modifyAttribute(ctx);
	}

	protected void modifyAttribute(DirContext ctx) {
		try {
			String name = TestProps.get("ldap.attribute.mod.name");

			ModificationItem[] mods = new ModificationItem[1];

			mods[0] = new ModificationItem(
				DirContext.REPLACE_ATTRIBUTE,
				new BasicAttribute(
					TestProps.get("ldap.attribute.mod.name.id"),
					TestProps.get("ldap.attribute.mod.name.value")));

			ctx.modifyAttributes(name, mods);
		}
		catch (Exception e) {
			fail(e);
		}
	}

}