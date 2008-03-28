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

package com.liferay.portlet.news.util;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portlet.news.model.Feed;
import com.liferay.util.Time;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="CategoryWebCacheItem.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoryWebCacheItem implements WebCacheItem {

	public Object convert(String url) throws WebCacheException {
		try {
			Map<String, Set<Feed>> categoryMap =
				new HashMap<String, Set<Feed>>();
			Set<String> categorySet = new TreeSet<String>();
			Map<String, Feed> feedMap = new HashMap<String, Feed>();
			Set<Feed> feedSet = new TreeSet<Feed>();

			String xml = null;

			try {
				xml = HttpUtil.URLtoString(url);
			}
			catch (IOException ioe) {
				xml = ContentUtil.get(
					"com/liferay/portlet/news/dependencies/categories.tsv");
			}

			BufferedReader br = new BufferedReader(new StringReader(xml));

			String s = null;

			while ((s = br.readLine()) != null) {
				String feedURL;
				String fullName;
				String shortName;
				String categoryName;

				int pos;

				pos = s.indexOf("\t");
				feedURL = s.substring(0, pos);

				s = s.substring(pos + 1, s.length());
				pos = s.indexOf("\t");
				fullName = s.substring(0, pos);

				s = s.substring(pos + 1, s.length());
				pos = s.indexOf("\t");
				shortName = s.substring(0, pos);

				categoryName = s.substring(pos + 1, s.length());

				Feed feed =
					new Feed(feedURL, fullName, shortName, categoryName);

				categorySet.add(feed.getCategoryName());
				feedMap.put(feedURL, feed);
				feedSet.add(feed);
			}

			categoryMap = new HashMap<String, Set<Feed>>();

			String temp = "";
			Set<Feed> tempSet = null;

			for (Feed feed : feedSet) {
				if (Validator.isNull(feed.getCategoryName())) {
					continue;
				}

				if (temp.equals(feed.getCategoryName())) {
					tempSet.add(feed);
				}
				else {
					tempSet = new TreeSet<Feed>();

					categoryMap.put(feed.getCategoryName(), tempSet);
					tempSet.add(feed);
				}

				temp = feed.getCategoryName();
			}

			categoryMap = Collections.unmodifiableMap(categoryMap);
			categorySet = Collections.unmodifiableSet(categorySet);

			feedMap = Collections.unmodifiableMap(feedMap);
			feedSet = Collections.unmodifiableSet(feedSet);

			Object[] objArray = new Object[4];

			objArray[0] = categoryMap;
			objArray[1] = categorySet;
			objArray[2] = feedMap;
			objArray[3] = feedSet;

			return objArray;
		}
		catch (Exception e) {
			throw new WebCacheException(e);
		}
	}

	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.DAY * 3;

}