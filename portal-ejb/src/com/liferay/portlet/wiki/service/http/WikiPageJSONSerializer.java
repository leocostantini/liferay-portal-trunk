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

package com.liferay.portlet.wiki.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.wiki.model.WikiPage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="WikiPageJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.wiki.service.http.WikiPageServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.wiki.service.http.WikiPageServiceJSON
 *
 */
public class WikiPageJSONSerializer {
	public static JSONObject toJSONObject(WikiPage model) {
		JSONObject jsonObj = new JSONObject();
		String nodeId = model.getNodeId();

		if (nodeId == null) {
			jsonObj.put("nodeId", StringPool.BLANK);
		}
		else {
			jsonObj.put("nodeId", nodeId.toString());
		}

		String title = model.getTitle();

		if (title == null) {
			jsonObj.put("title", StringPool.BLANK);
		}
		else {
			jsonObj.put("title", title.toString());
		}

		jsonObj.put("version", model.getVersion());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		String content = model.getContent();

		if (content == null) {
			jsonObj.put("content", StringPool.BLANK);
		}
		else {
			jsonObj.put("content", content.toString());
		}

		String format = model.getFormat();

		if (format == null) {
			jsonObj.put("format", StringPool.BLANK);
		}
		else {
			jsonObj.put("format", format.toString());
		}

		jsonObj.put("head", model.getHead());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			WikiPage model = (WikiPage)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}