<%
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
%>

.breadcrumbs {
	margin-bottom: 10px;
	margin-left: 0px;
}

.lfr-button {
	background: url() no-repeat 0 50%;
	padding: 2px 0 2px 20px;
}

.js .controls-hidden .lfr-meta-actions, .js .controls-hidden .portlet-borderless-bar, .js .controls-hidden .portlet-icons, .js .controls-hidden .lfr-configurator-visibility {
	display: none;
}

.js .page-maximized .portlet-icons {
	display: block;
}

.configuration-frame {
	border-width: 0;
	overflow: auto;
	width: 800px;
}

.float-container:after {
	clear: both;
	content: ".";
	display: block;
	height: 0;
	visibility: hidden;
}

.ie .float-container {
	height: 1%;
}

.lfr-meta-actions {
	padding-top: 1em;
}

.lfr-table {
	border-collapse: collapse;
	clear: both;
}

.lfr-table tr td, .lfr-table tr th {
	padding: 0 5px;
}

.lfr-table tr th {
	font-weight: bold;
}

.lfr-table tr td:first-child, .lfr-table tr td.first-child, .lfr-table tr th:first-child, .lfr-table tr th.first-child {
	padding-left: 0;
}

.lfr-table tr td:last-child, .lfr-table tr td.last-child, .lfr-table tr th:last-child, .lfr-table tr th.last-child {
	padding-right: 0;
}

.lfr-control-links a {
	margin-right: 1em;
}

.portlet-toolbar {
	margin-bottom: 1.5em;
}

.lfr-checkbox-preselected {
	opacity: 0.5;
}

.ie .lfr-checkbox-preselected {
	filter: alpha(opacity=50);
	zoom: 1;
}

.lfr-configurator-visibility {
	opacity: 0.5;
}

.ie .lfr-configurator-visibility {
	filter: alpha(opacity=50);
	zoom: 1;
}

.gecko .lfr-input-time, .ie .lfr-input-time {
	padding-left: 2px;
}