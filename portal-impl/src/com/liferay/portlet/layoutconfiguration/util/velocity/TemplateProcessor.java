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

package com.liferay.portlet.layoutconfiguration.util.velocity;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.comparator.PortletRenderWeightComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="TemplateProcessor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 *
 */
public class TemplateProcessor {

	public TemplateProcessor(
		ServletContext ctx, HttpServletRequest req, HttpServletResponse res,
		String portletId) {

		_ctx = ctx;
		_req = req;
		_res = res;
		_portletId = portletId;
		_columnsMap = new HashMap<String, String>();
		_portletsMap = new TreeMap<Portlet, Object[]>(
			new PortletRenderWeightComparator());
	}

	public String processColumn(String columnId) throws Exception {
		Map<String, String> attributes = new HashMap<String, String>();

		attributes.put("id", columnId);

		PortletColumnLogic logic = new PortletColumnLogic(_ctx, _req, _res);

		StringBuilder sb = new StringBuilder();

		logic.processContent(sb, attributes);

		_portletsMap.putAll(logic.getPortletsMap());

		String columnIdPlaceHolder = "[$TEMPLATE_COLUMN_" + columnId + "$]";

		_columnsMap.put(columnIdPlaceHolder, sb.toString());

		return columnIdPlaceHolder;
	}

	public String processMax() throws Exception {
		RuntimeLogic logic = new PortletLogic(_ctx, _req, _res, _portletId);

		StringBuilder sb = new StringBuilder();

		logic.processContent(sb, new HashMap<String, String>());

		return sb.toString();
	}

	public String processPortlet(String portletId) throws Exception {
		RuntimeLogic logic = new PortletLogic(_ctx, _req, _res, portletId);

		StringBuilder sb = new StringBuilder();

		logic.processContent(sb, new HashMap<String, String>());

		return sb.toString();
	}

	public Map<String, String> getColumnsMap() {
		return _columnsMap;
	}

	public Map<Portlet, Object[]> getPortletsMap() {
		return _portletsMap;
	}

	private ServletContext _ctx;
	private HttpServletRequest _req;
	private HttpServletResponse _res;
	private String _portletId;
	private Map<String, String> _columnsMap;
	private Map<Portlet, Object[]> _portletsMap;

}