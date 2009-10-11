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

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-asset-category-admin .vocabulary-container {
	width: 100%;
}

.portlet-asset-category-admin .vocabulary-container .results-header {
	background: #F0F5F7;
	font-weight: bold;
	margin: 2px 0;
	padding: 5px 10px;
}

.portlet-asset-category-admin .vocabulary-categories-container {
	min-width: 250px;
}

.ie6 .portlet-asset-category-admin .vocabulary-categories-container {
	width: 300px;
}

.portlet-asset-category-admin .vocabulary-list-container .results-header {
	background: #d3dadd;
}

.portlet-asset-category-admin .vocabulary-categories-container .results-header {
	background: #AEB9BE;
}

.portlet-asset-category-admin .vocabulary-edit-category .results-header {
	background: #6F7D83;
	color: #fff;
	display: none;
}

.portlet-asset-category-admin .vocabulary-content td {
	vertical-align: top;
}

.portlet-asset-category-admin .vocabulary-content li.vocabulary-category {
	padding: 1px 0;
}

.portlet-asset-category-admin .vocabulary-content li.vocabulary-category, .portlet-asset-category-admin li.vocabulary-item, .vocabulary-item.portlet-asset-category-admin-helper {
	border-bottom: 1px solid #D3D7DB;
	font-weight: bold;
	list-style: none;
}

.portlet-asset-category-admin .vocabulary-item.alt {
	background: #F0F2F4;
}

.portlet-asset-category-admin .vocabulary-container .results-row a, .vocabulary-item.results-row a {
	padding: 8px 0 8px 10px;
}

.portlet-asset-category-admin .vocabulary-list a {
	display: block;
	text-decoration: none;
}

.ie .portlet-asset-category-admin .vocabulary-list a {
	zoom: 1;
}

.portlet-asset-category-admin .vocabulary-item a, .vocabulary-item.portlet-asset-category-admin-helper a {
	display: block;
	padding-left: 20px;
}

.portlet-asset-category-admin .vocabulary-item.selected, .vocabulary-item.portlet-asset-category-admin-helper.selected {
	background: #aeb9be;
}

.ie6 .portlet-asset-category-admin .vocabulary-treeview-container .vocabulary-item.selected {
	background: none;
}

.portlet-asset-category-admin .vocabulary-item.selected a {
	color: #000;
	text-decoration: none;
}

.portlet-asset-category-admin .vocabulary-category-item.selected > span {
	font-weight: bold;
}

.portlet-asset-category-admin .vocabulary-list .selected a {
	background: #6F7D83;
	color: #fff;
}

.portlet-asset-category-admin .vocabulary-list .selected a:hover {
	background: #878F93;
}

.portlet-asset-category-admin .vocabulary-categories .active-area {
	background: #ffc;
}

.portlet-asset-category-admin .vocabulary-categories {
	border-right: 1px solid #D3D7DB;
	height: 300px;
	overflow: auto;
}

.portlet-asset-category-admin .vocabulary-list {
	border-left: 1px #D3D7DB solid;
	border-right: 1px #D3D7DB solid;
	height: 300px;
	overflow: auto;
	overflow-x: hidden;
}

.portlet-asset-category-admin .vocabulary-list a:hover, .portlet-asset-category-admin .vocabulary-categories a:hover {
	background: #D3DADD;
}

.portlet-asset-category-admin .vocabulary-properties {
	width: 380px;
}

.portlet-asset-category-admin .ui-asset-categories .nowrap {
	empty-cells: show;
	overflow: hidden;
	white-space: nowrap;
}

.portlet-asset-category-admin .vocabulary-search-bar {
	background: #F0F2F4;
	border: 1px solid #D3D7DB;
	border-left: 0;
	border-right: 0;
	padding: 10px;
}

.portlet-asset-category-admin .vocabulary-toolbar {
	background: #F6F8FB;
	border-bottom: 1px solid #dedede;
}

.portlet-asset-category-admin .vocabulary-buttons {
	float: left;
	min-width: 220px;
	padding: 5px 0px 5px;
}

.portlet-asset-category-admin .vocabulary-actions {
	padding: 5px;
	text-align: right;
}

.portlet-asset-category-admin .vocabulary-buttons .button {
	background: url(<%= themeImagesPath %>/common/page.png) no-repeat scroll 10px 50%;
	color: #9EA8AD;
	cursor: pointer;
	display: block;
	float: left;
	font-weight: bold;
	margin-right: 5px;
	min-width: 70px;
	padding: 5px 5px 5px 30px;
}

.portlet-asset-category-admin .vocabulary-buttons .selected {
	background-color: #CFD5D7;
	color: #0F0F0F;
}

.portlet-asset-category-admin .vocabulary-edit-category .vocabulary-edit {
	display: none;
	padding: 5px 5px 10px 10px;
}

.portlet-asset-category-admin .vocabulary-editing-tag .vocabulary-edit, .portlet-asset-category-admin .vocabulary-editing-tag .results-header {
	display: block;
}

.portlet-asset-category-admin div.vocabulary-close {
	text-align: right;
}

.portlet-asset-category-admin div.vocabulary-close span {
	cursor: pointer;
}

.portlet-asset-category-admin .vocabulary-property-row {
	display: none;
	white-space: nowrap;
}

.portlet-asset-category-admin .vocabulary-footer {
	border-top: 1px solid #dedede;
	margin-top: 5px;
	padding: 10px 0 0;
}

.asset-category-layer-wrapper {
	display: none;
}

.aui-widget-bd .asset-category-layer {
	padding: 10px;
	text-align: left;
}

.aui-widget-bd .asset-category-layer .aui-ctrl-holder {
	margin-bottom: 10px;
}

.aui-widget-bd .asset-category-layer label {
	display: block;
	font-weight: bold;
}

.aui-widget-bd .asset-category-layer .aui-ctrl-holder input, .aui-widget-bd .asset-category-layer .aui-ctrl-holder select {
	width: 200px;
}

.ie6 .aui-widget-bd .asset-category-layer .aui-ctrl-holder, .ie6 .aui-widget-bd .asset-category-layer .aui-ctrl-holder {
	width: 200px;
}

.aui-widget-bd .asset-category-layer .button-holder {
	margin-top: 10px;
}

.asset-category-layer .aui-overlay {
	overflow: visible;
	width: 230px;
}

.portlet-asset-category-admin #vocabulary-category-messages {
	margin: 10px;
}

.portlet-asset-category-admin .vocabulary-treeview-container {
	padding: 5px;
}

.portlet-asset-category-admin .vocabulary-container .category-name {
	width: 300px;
}

.portlet-asset-category-admin #vocabulary-search-input {
	width: 250px;
}