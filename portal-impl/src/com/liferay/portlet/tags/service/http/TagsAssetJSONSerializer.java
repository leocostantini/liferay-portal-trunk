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

package com.liferay.portlet.tags.service.http;

import com.liferay.portlet.tags.model.TagsAsset;

import com.liferay.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="TagsAssetJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.tags.service.http.TagsAssetServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.http.TagsAssetServiceJSON
 *
 */
public class TagsAssetJSONSerializer {
	public static JSONObject toJSONObject(TagsAsset model) {
		JSONObject jsonObj = new JSONObject();
		JSONUtil.put(jsonObj, "assetId", model.getAssetId());
		JSONUtil.put(jsonObj, "groupId", model.getGroupId());
		JSONUtil.put(jsonObj, "companyId", model.getCompanyId());
		JSONUtil.put(jsonObj, "userId", model.getUserId());
		JSONUtil.put(jsonObj, "userName", model.getUserName());
		JSONUtil.put(jsonObj, "createDate", model.getCreateDate());
		JSONUtil.put(jsonObj, "modifiedDate", model.getModifiedDate());
		JSONUtil.put(jsonObj, "classNameId", model.getClassNameId());
		JSONUtil.put(jsonObj, "classPK", model.getClassPK());
		JSONUtil.put(jsonObj, "startDate", model.getStartDate());
		JSONUtil.put(jsonObj, "endDate", model.getEndDate());
		JSONUtil.put(jsonObj, "publishDate", model.getPublishDate());
		JSONUtil.put(jsonObj, "expirationDate", model.getExpirationDate());
		JSONUtil.put(jsonObj, "mimeType", model.getMimeType());
		JSONUtil.put(jsonObj, "title", model.getTitle());
		JSONUtil.put(jsonObj, "description", model.getDescription());
		JSONUtil.put(jsonObj, "summary", model.getSummary());
		JSONUtil.put(jsonObj, "url", model.getUrl());
		JSONUtil.put(jsonObj, "height", model.getHeight());
		JSONUtil.put(jsonObj, "width", model.getWidth());
		JSONUtil.put(jsonObj, "priority", model.getPriority());
		JSONUtil.put(jsonObj, "viewCount", model.getViewCount());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			TagsAsset model = (TagsAsset)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}