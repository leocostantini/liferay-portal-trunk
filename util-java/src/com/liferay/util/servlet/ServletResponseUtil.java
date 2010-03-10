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

package com.liferay.util.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.SocketException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.CharUtils;

/**
 * <a href="ServletResponseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Zsolt Balogh
 */
public class ServletResponseUtil {

	public static void sendFile(
			HttpServletResponse response, String fileName, byte[] bytes)
		throws IOException {

		sendFile(null, response, fileName, bytes, null);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, byte[] bytes)
		throws IOException {

		sendFile(request, response, fileName, bytes, null);
	}

	public static void sendFile(
			HttpServletResponse response, String fileName, byte[] bytes,
			String contentType)
		throws IOException {

		sendFile(null, response, fileName, bytes, contentType);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, byte[] bytes, String contentType)
		throws IOException {

		setHeaders(request, response, fileName, contentType);

		write(response, bytes);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream is)
		throws IOException {

		sendFile(request, response, fileName, is, null);
	}

	public static void sendFile(
			HttpServletResponse response, String fileName, InputStream is,
			String contentType)
		throws IOException {

		sendFile(null, response, fileName, is, contentType);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream is, String contentType)
		throws IOException {

		sendFile(request, response, fileName, is, 0, contentType);
	}

	public static void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream is, int contentLength,
			String contentType)
		throws IOException {

		setHeaders(request, response, fileName, contentType);

		write(response, is, contentLength);
	}

	public static void write(HttpServletResponse response, String s)
		throws IOException {

		write(response, s.getBytes(StringPool.UTF8));
	}

	public static void write(HttpServletResponse response, byte[] bytes)
		throws IOException {

		write(response, bytes, 0);
	}

	public static void write(
			HttpServletResponse response, byte[] bytes, int contentLength)
		throws IOException {

		try {

			// LEP-3122

			if (!response.isCommitted()) {

				// LEP-536

				if (contentLength == 0) {
					contentLength = bytes.length;
				}

				response.setContentLength(contentLength);

				ServletOutputStream servletOutputStream =
					response.getOutputStream();

				servletOutputStream.write(bytes, 0, contentLength);
			}
		}
		catch (IOException ioe) {
			if (ioe instanceof SocketException ||
				ioe.getClass().getName().equals(_CLIENT_ABORT_EXCEPTION)) {

				if (_log.isWarnEnabled()) {
					_log.warn(ioe);
				}
			}
			else {
				throw ioe;
			}
		}
	}

	public static void write(HttpServletResponse response, InputStream is)
		throws IOException {

		write(response, is, 0);
	}

	public static void write(
			HttpServletResponse response, InputStream is, int contentLength)
		throws IOException {

		if (response.isCommitted()) {
			return;
		}

		if (contentLength > 0) {
			response.setContentLength(contentLength);
		}

		StreamUtil.transfer(is, response.getOutputStream());
	}

	protected static void setHeaders(
		HttpServletRequest request, HttpServletResponse response,
		String fileName, String contentType) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		// LEP-2201

		if (Validator.isNotNull(contentType)) {
			response.setContentType(contentType);
		}

		response.setHeader(
			HttpHeaders.CACHE_CONTROL, HttpHeaders.CACHE_CONTROL_PUBLIC_VALUE);
		response.setHeader(HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_PUBLIC_VALUE);

		if (Validator.isNotNull(fileName)) {
			String contentDisposition =
				"attachment; filename=\"" + fileName + "\"";

			// If necessary for non-ASCII characters, encode based on RFC 2184.
			// However, not all browsers support RFC 2184. See LEP-3127.

			boolean ascii = true;

			for (int i = 0; i < fileName.length(); i++) {
				if (!CharUtils.isAscii(fileName.charAt(i))) {
					ascii = false;

					break;
				}
			}

			try {
				if (!ascii) {
					URLCodec codec = new URLCodec(StringPool.UTF8);

					String encodedFileName =
						StringUtil.replace(codec.encode(fileName), "+", "%20");

					if (BrowserSnifferUtil.isIe(request)) {
						contentDisposition =
							"attachment; filename=\"" + encodedFileName + "\"";
					}
					else {
						contentDisposition =
							"attachment; filename*=UTF-8''" + encodedFileName;
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			String extension = GetterUtil.getString(
				FileUtil.getExtension(fileName)).toLowerCase();

			String[] mimeTypesContentDispositionInline = null;

			try {
				mimeTypesContentDispositionInline = PropsUtil.getArray(
					"mime.types.content.disposition.inline");
			}
			catch (Exception e) {
				mimeTypesContentDispositionInline = new String[0];
			}

			if (ArrayUtil.contains(
					mimeTypesContentDispositionInline, extension)) {

				contentDisposition = StringUtil.replace(
					contentDisposition, "attachment; ", "inline; ");
			}

			response.setHeader(
				HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
		}
	}

	private static final String _CLIENT_ABORT_EXCEPTION =
		"org.apache.catalina.connector.ClientAbortException";

	private static Log _log = LogFactoryUtil.getLog(ServletResponseUtil.class);

}