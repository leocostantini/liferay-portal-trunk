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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.AddressServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="AddressServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the <code>com.liferay.portal.service.AddressServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is difficult
 * for SOAP to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>, that
 * is translated to an array of <code>com.liferay.portal.model.AddressSoap</code>.
 * If the method in the service utility returns a <code>com.liferay.portal.model.Address</code>,
 * that is translated to a <code>com.liferay.portal.model.AddressSoap</code>. Methods
 * that SOAP cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform compatible.
 * SOAP allows different languages like Java, .NET, C++, PHP, and even Perl, to
 * call the generated services. One drawback of SOAP is that it is slow because
 * it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/tunnel-web/secure/axis.
 * Set the property <code>tunnel.servlet.hosts.allowed</code> in portal.properties
 * to configure security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.AddressServiceUtil
 * @see com.liferay.portal.service.http.AddressServiceHttp
 * @see com.liferay.portal.service.model.AddressSoap
 *
 */
public class AddressServiceSoap {
	public static com.liferay.portal.model.AddressSoap addAddress(
		java.lang.String className, java.lang.String classPK,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId, int typeId,
		boolean mailing, boolean primary) throws RemoteException {
		try {
			com.liferay.portal.model.Address returnValue = AddressServiceUtil.addAddress(className,
					classPK, street1, street2, street3, city, zip, regionId,
					countryId, typeId, mailing, primary);

			return com.liferay.portal.model.AddressSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAddress(long addressId) throws RemoteException {
		try {
			AddressServiceUtil.deleteAddress(addressId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.AddressSoap getAddress(
		long addressId) throws RemoteException {
		try {
			com.liferay.portal.model.Address returnValue = AddressServiceUtil.getAddress(addressId);

			return com.liferay.portal.model.AddressSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.AddressSoap[] getAddresses(
		java.lang.String className, java.lang.String classPK)
		throws RemoteException {
		try {
			java.util.List returnValue = AddressServiceUtil.getAddresses(className,
					classPK);

			return com.liferay.portal.model.AddressSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.AddressSoap updateAddress(
		long addressId, java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		java.lang.String regionId, java.lang.String countryId, int typeId,
		boolean mailing, boolean primary) throws RemoteException {
		try {
			com.liferay.portal.model.Address returnValue = AddressServiceUtil.updateAddress(addressId,
					street1, street2, street3, city, zip, regionId, countryId,
					typeId, mailing, primary);

			return com.liferay.portal.model.AddressSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AddressServiceSoap.class);
}