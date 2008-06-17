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

package com.liferay.portlet.wiki.engines.jspwiki.plugin;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.parser.Heading;
import com.ecyrd.jspwiki.plugin.PluginException;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.PwdGenerator;

import java.util.Map;

/**
 * <a href="TableOfContents.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This is a modification of JSPWiki's core TableOfContents plugin for use
 * within Liferay. This plugin modifies the original behavior by producing
 * ordered lists and making contents collapsable.
 * </p>
 *
 * @author Alexander Chow
 * @author Jorge Ferrer
 *
 */
public class TableOfContents extends com.ecyrd.jspwiki.plugin.TableOfContents {

	public String execute(WikiContext context, Map params)
		throws PluginException {

		if (!params.containsKey(PARAM_NUMBERED)) {
			params.put(PARAM_NUMBERED, Boolean.TRUE.toString());
		}

		String result = super.execute(context, params);

		if (_count < 2) {
			return StringPool.BLANK;
		}

		int x = result.indexOf("<div class=\"collapsebox\">\n");

		x = result.indexOf("</h4>", x);

		int y = x + "</h4>".length();

		if ((x != -1) && (y != -1)) {
			String id = "toc_" + PwdGenerator.getPassword();

			StringMaker sm = new StringMaker();

			sm.append(result.substring(0, x));
			sm.append(StringPool.NBSP);
			sm.append("<span style=\"cursor: pointer;\" ");
			sm.append("onClick=\"Liferay.Util.toggleByIdSpan(this, '");
			sm.append(id);
			sm.append("'); self.focus();\">[");
			sm.append("<span>-</span>");
			sm.append("<span style=\"display: none;\">+</span>");
			sm.append("]</span>\n");
			sm.append("</h4>");

			sm.append("<div id=\"");
			sm.append(id);
			sm.append("\">\n");
			sm.append(result.substring(y));
			sm.append("</div>\n");

			result = sm.toString();
		}

		return result;
	}

	public void headingAdded(WikiContext context, Heading heading) {
		super.headingAdded(context, heading);

		_count++;
	}

	private int _count;

}