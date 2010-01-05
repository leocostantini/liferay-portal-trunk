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

.lfr-grid {
	width: 100%;
}

.lfr-component, .lfr-component ul, .lfr-component li, .lfr-component dl, .lfr-component dt, .lfr-component dd {
	margin: 0;
	padding: 0;
	list-style: none;
}

.lfr-component li img, img.icon {
	vertical-align: middle;
}

#layout-grid.dragging .lfr-portlet-column.empty {
	padding: 20px;
}

.lfr-js-required {
	position: absolute;
	left: -9999em;
	top: -9999em;
}

.js .lfr-js-required {
	left: 0;
	position: static;
	top: 0;
}

.popup-alert-notice, .popup-alert-warning {
	background: #ffc url() no-repeat 5px 50%;
	border-bottom: 1px solid;
	font-size: 1.1em;
	left: 0;
	padding: 10px;
	padding-left: 25px;
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 10000;
}

.popup-alert-notice {
	background-color: #ffc;
	background-image: url(<%= themeImagesPath %>/messages/alert.png);
	border-bottom-color: #fc0;
}

.popup-alert-warning {
	background-color: #fcc;
	background-image: url(<%= themeImagesPath %>/messages/error.png);
	border-bottom-color: #f00;
	font-weight: bold;
}

.ie6 .popup-alert-notice, .ie6 .popup-alert-warning {
	bottom: auto;
	left: expression( ( LFR_IGNORE_ME2 = document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ) + 'px' );
	position: absolute;
	right: auto;
	top: expression( ( LFR_IGNORE_ME = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop ) + 'px' );
}

.popup-alert-notice .countdown-timer {
	font-size: 1.1em;
	font-weight: bold;
}

.popup-alert-notice input, .popup-alert-warning input {
	vertical-align: middle;
}