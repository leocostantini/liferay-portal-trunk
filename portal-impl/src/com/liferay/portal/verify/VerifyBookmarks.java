/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;

import java.util.List;

public class VerifyBookmarks extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Verifying");

		try {
			verifyBookmarks();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	protected void verifyBookmarks() throws Exception {
		List<BookmarksEntry> entries =
			BookmarksEntryLocalServiceUtil.getNoAssetEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + entries.size() +
					" entries with no tags assets");
		}

		for (BookmarksEntry entry : entries) {
			try {
				BookmarksEntryLocalServiceUtil.updateAsset(
					entry.getUserId(), entry, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update tags asset for entry " +
							entry.getEntryId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Tags assets verified for entries");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyBookmarks.class);

}