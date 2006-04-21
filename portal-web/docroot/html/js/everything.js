var agent = navigator.userAgent.toLowerCase();

var is_ie = (agent.indexOf("msie") != -1);
var is_ie_4 = (is_ie && (agent.indexOf("msie 4") != -1));
var is_ie_5 = (is_ie && (agent.indexOf("msie 5.0") != -1));
var is_ie_5_up = (is_ie && !is_ie_4);
var is_ie_5_5 = (is_ie && (agent.indexOf("msie 5.5") != -1));
var is_ie_5_5_up = (is_ie && !is_ie_4 && !is_ie_5);

var is_mozilla = ((agent.indexOf("mozilla") != -1) && (agent.indexOf("spoofer") == -1) && (agent.indexOf("compatible") == -1) && (agent.indexOf("opera") == -1) && (agent.indexOf("webtv") == -1) && (agent.indexOf("hotjava") == -1));
var is_mozilla_1_3_up = (is_mozilla && (navigator.productSub > 20030210));

var is_ns_4 = (!is_ie && (agent.indexOf("mozilla/4.") != -1));

var is_rtf = (is_ie_5_5_up || is_mozilla_1_3_up);

var is_safari = (agent.indexOf("safari") != -1);
var previousMenuId = null;

function fixPopUp(id) {

	// Move the pop up table to the top of the HTML to fix zIndex and relative
	// position issues

	var popUpEl = document.getElementById(id);
	var bodyEl = document.getElementsByTagName("BODY")[0];

	popUpEl.parentNode.removeChild(popUpEl);
	bodyEl.insertBefore(popUpEl, bodyEl.childNodes[0]);
}

function hidePopUp(id) {
	var el = document.getElementById(id);
	el.style.display= "none";
}

function showPopUp(id) {
	var previousEl = document.getElementById(previousMenuId);

	if (previousEl != null) {
		hidePopUp(previousMenuId);
	}

	previousMenuId = id;

	var el = document.getElementById(id);

	if (is_mozilla) {
		el.style.left = mousePos.x;
		el.style.top = mousePos.y;
	}
	else {
		el.style.pixelLeft = mousePos.x;
		el.style.pixelTop = mousePos.y;
	}

	el.style.display = "block";
}
var imgRollOvers = new Array();
var imgRollOversCount = 0;

function findImage(name) {
	for (var i = 0; i < imgRollOversCount; i++) {
		if (name == imgRollOvers[i][2]) {
			return imgRollOvers[i];
		}
	}
}

function loadImage(name, on, off) {
	imgRollOvers[imgRollOversCount] = new Array(3);
	imgRollOvers[imgRollOversCount][0] = new Image();
	imgRollOvers[imgRollOversCount][0].src = on;
	imgRollOvers[imgRollOversCount][1] = new Image();
	imgRollOvers[imgRollOversCount][1].src = off;
	imgRollOvers[imgRollOversCount][2] = name;
	imgRollOversCount++;
}

function offRollOver() {
	for (var i = 0; i < imgRollOversCount; i++) {
		if (document.images[imgRollOvers[i][2]] != null) {
			document.images[imgRollOvers[i][2]].src = imgRollOvers[i][1].src;
		}
	}
}

function onRollOver(names) {
	if (names[0]) {
		var x = findImage(names);
		var y = findImage(names);

		document.images[x[2]].src = y[0].src;
	}
	else {
		if (findImage(names)) {
			document.images[names].src = findImage(names)[0].src;
		}
	}
}
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

var submitFormCount = 0;

function addItem(box, text, value, sort) {
	box[box.length] = new Option(text, value);

	if (sort == true) {
		sortBox(box);
	}
}

if (!Array.prototype.push) {
	function array_push() {
		for(var i = 0; i < arguments.length; i++) {
			this[this.length] = arguments[i];
		}

		return this.length;
	}

	Array.prototype.push = array_push;
}

if (!Array.prototype.pop) {
	function array_pop(){
		lastElement = this[this.length - 1];
		this.length = Math.max(this.length - 1, 0);

		return lastElement;
	}

	Array.prototype.pop = array_pop;
}

function addLoadEvent(func) {
    var oldonload = window.onload;
    if (typeof window.onload != 'function') {
        window.onload = func;
    } else {
        window.onload = function() {
            oldonload();
            func();
        }
    }
}

function autoComplete(box, text) {
	var prevStartPos;
	var prevMidPos;
	var prevEndPos;

	if ((box.length > 0) && (text != "")) {

		// The character '*' is ordered differently between Java and JavaScript

		text = text.toLowerCase().replace(/\*/g, "");

		// Use binary search to find the first instance of the selection

		var startPos = 0;
		var midPos = Math.floor(box.length / 2);
		var endPos = box.length;

		for (;;) {
			var sText = trimString(box.options[midPos].text.toLowerCase().replace(/\*/g, ""));

			if (midPos > 0) {
				var prevSText = trimString(box.options[midPos - 1].text.toLowerCase().replace(/\*/g, ""));

				if ((sText.indexOf(text) == 0) &&
					(prevSText.indexOf(text) != 0)) {

					box.selectedIndex = midPos;

					break;
				}
			}
			else {
				if (sText.indexOf(text) == 0) {
					box.selectedIndex = midPos;

					break;
				}
			}

			box.selectedIndex = -1;

			if (text < sText) {
				endPos = midPos;
				midPos = (Math.floor((endPos - startPos) / 2)) + startPos;
			}
			else if (text > sText) {
				startPos = midPos;
				midPos = (Math.floor((endPos - midPos) / 2)) + midPos;
			}

			if ((prevStartPos != null) && (prevMidPos != null) && (prevEndPos != null)) {

				// Break out of the loop when all positions repeat

				if ((prevStartPos == startPos) && (prevMidPos == midPos) && (prevEndPos == endPos)) {
					break;
				}
			}

			prevStartPos = startPos;
			prevMidPos = midPos;
			prevEndPos = endPos;
		}
	}
	else {
		box.selectedIndex = -1;
	}
}

function autoFill(fromBox, toBox) {
	i = fromBox.selectedIndex;

	if (i != -1) {
		s = fromBox.options[i].value;

		if (s != "") {
			to = toBox.value;

			if (to == "") {
				toBox.value = s;
			}
			else {
				toBox.value = to + ", " + s;
			}
		}
	}
}

function blink() {
	if (document.all) {
		var blinkArray = document.all.tags("blink");

		for (var i = 0; i < blinkArray.length; i++) {
			blinkArray[i].style.visibility = blinkArray[i].style.visibility == "" ? "hidden" : "";
		}
	}
}

/*
if (document.all) {
	setInterval("blink()", 750);
}
*/

function changeOpacity (object, opacity) {
	opacity = (opacity >= 100) ? 99.999 : opacity;
	opacity = (opacity < 0) ? 0 : opacity;
    
	object.style.opacity = (opacity / 100);
	object.style.MozOpacity = (opacity / 100);
	object.style.KhtmlOpacity = (opacity / 100);
	object.style.filter = "alpha(opacity=" + opacity + ")";
}

function check(form, name, checked) {
	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];

		if ((e.name == name) && (e.type == "checkbox")) {
			e.checked = checked;
		}
	}
}

function checkAll(form, name, allBox) {
	if (isArray(name)) {
		for (var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];

			if (e.type == "checkbox") {
				for (var j = 0; j < name.length; j++) {
					if (e.name == name[j]) {
						e.checked = allBox.checked;
					}
				}
			}
		}
	}
	else {
		for (var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];

			if ((e.name == name) && (e.type == "checkbox")) {
				e.checked = allBox.checked;
			}
		}
	}
}

function checkAllBox(form, name, allBox) {
	var totalBoxes = 0;
	var totalOn = 0;

	if (isArray(name)) {
		for (var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];

			if ((e.name != allBox.name) && (e.type == "checkbox")) {
				for (var j = 0; j < name.length; j++) {
					if (e.name == name[j]) {
						totalBoxes++;

						if (e.checked) {
							totalOn++;
						}
					}
				}
			}
		}
	}
	else {
		for (var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];

			if ((e.name != allBox.name) && (e.name == name) && (e.type == "checkbox")) {
				totalBoxes++;

				if (e.checked) {
					totalOn++;
				}
			}
		}
	}

	if (totalBoxes == totalOn) {
		allBox.checked = true;
	}
	else {
		allBox.checked = false;
	}
}

function checkMaxLength(box, maxLength) {
	if ((box.value.length) >= maxLength) {
		box.value = box.value.substring(0, maxLength - 1);
	}
}

function checkTab(box) {
	if ((document.all) && (event.keyCode == 9)) {
		box.selection = document.selection.createRange();
		setTimeout("processTab(\"" + box.id + "\")", 0);
	}
}

function count(s, text) {
	if ((s == null) || (text == null)) {
		return 0;
	}

	var count = 0;

	var pos = s.indexOf(text);

	while (pos != -1) {
		pos = s.indexOf(text, pos + text.length);
		count++;
	}

	return count;
}

function disableEsc() {
	if ((document.all) && (event.keyCode == 27)) {
		event.returnValue = false;
	}
}

function disableFields(fields) {
	for (var i = 0; i < fields.length; i++) {
		fields[i].disabled = true;
	}
}

function enableFields(fields) {
	for (var i = 0; i < fields.length; i++) {
		fields[i].disabled = false;
	}
}

function getElementByClassName (obj, className, nodeName) {
	if (nodeName == null) {
		nodeName = "div";
	}
	
	var list = obj.getElementsByTagName(nodeName);
	for (var i = 0; i <  list.length; i++) {
		if (list[i].className && list[i].className.match(className)) {
			return list[i];
		}
	}
	return null;
}

function getIndex(col, value) {
	for (var i = 0; i < col.length; i++) {
		if (col[i].value == value) {
			return i;
		}
	}

	return -1;
}

function getSelectedIndex(col) {
	for (var i = 0; i < col.length; i++) {
		if (col[i].checked == true) {
			return i;
		}
	}

	return -1;
}

function getSelectedRadioName(col) {
	var i = getSelectedIndex(col);

	if (i == -1) {
		var radioName = col.name;

		if (radioName == null) {
			radioName = "";
		}

		return radioName;
	}
	else {
		return col[i].name;
	}
}

function getSelectedRadioValue(col) {
	var i = getSelectedIndex(col);

	if (i == -1) {
		var radioValue = col.value;

		if (radioValue == null) {
			radioValue = "";
		}

		return radioValue;
	}
	else {
		return col[i].value;
	}
}

function hasItemWithText(box, text) {
	for (var i = 0; i < box.length; i ++) {
		if (box.options[i].text.toLowerCase() == text.toLowerCase()) {
			return true;
		}
	}

	return false;
}

function hasItemWithValue(box, value) {
	for (var i = 0; i < box.length; i ++) {
		if (box.options[i].value.toLowerCase() == value.toLowerCase()) {
			return true;
		}
	}

	return false;
}

function isArray(object) {
	if (!window.Array) {
		return false;
	}
	else {
		return object.constructor == window.Array;
	}
}

function isEven(x) {
	if ((x % 2) == 0) {
		return true;
	}

	return false;
}

function isOdd(x) {
	return !isEven(x);
}

function isRadioChecked(col) {
	var i = getSelectedIndex(col);

	return col[i].checked;
}

function isSelected(box) {
	if (box.selectedIndex >= 0) {
		return true;
	}
	else {
		return false;
	}
}

function listChecked(form) {
	var s = "";

	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];

		if ((e.type == "checkbox") && (e.checked == true) && (e.value > "")) {
			s += e.value + ",";
		}
	}

	return s;
}

function listCheckedExcept(form, except) {
	var s = "";

	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];

		if ((e.type == "checkbox") && (e.checked == true) && (e.value > "") && (e.name.indexOf(except) != 0)) {
			s += e.value + ",";
		}
	}

	return s;
}

function listSelect(box, delimeter) {
	var s = "";

	if (delimeter == null) {
		delimeter = ",";
	}

	if (box == null) {
		return "";
	}

	for (var i = 0; i < box.length; i++) {
    	if (box.options[i].value > "") {
			s += box.options[i].value + delimeter;
		}
	}

	if (s == ".none,") {
		return "";
	}
	else {
		return s;
	}
}

function listSelected(box, delimeter) {
	var s = "";

	if (delimeter == null) {
		delimeter = ",";
	}

	if (box == null) {
		return "";
	}

	for (var i = 0; i < box.length; i++) {
    	if ((box.options[i].value > "") && (box.options[i].selected)) {
			s += box.options[i].value + delimeter;
		}
	}

	if (s == ".none,") {
		return "";
	}
	else {
		return s;
	}
}

function listUnchecked(form) {
	var s = "";

	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];

		if ((e.type == "checkbox") && (e.checked == false) && (e.value > "")) {
			s += e.value + ",";
		}
	}

	return s;
}

function listUncheckedExcept(form, except) {
	var s = "";

	for (var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];

		if ((e.type == "checkbox") && (e.checked == false) && (e.value > "") && (e.name.indexOf(except) != 0)) {
			s += e.value + ",";
		}
	}

	return s;
}

function mathRound(n, places) {
	return (Math.round(n * Math.pow(10, places))) / Math.pow(10, places);
}

function moveItem(fromBox, toBox, sort) {
	var newText = null;
	var newValue = null;
	var newOption = null;

	if (fromBox.selectedIndex >= 0) {
		for (var i = 0; i < fromBox.length; i++) {
			if (fromBox.options[i].selected) {
				newText = fromBox.options[i].text;
				newValue = fromBox.options[i].value;

				newOption = new Option(newText, newValue);

				toBox[toBox.length] = newOption;
			}
		}

		for (var i = 0; i < toBox.length; i++) {
			for (var j = 0; j < fromBox.length; j++) {
				if (fromBox[j].value == toBox[i].value) {
					fromBox[j] = null;

					break;
				}
			}
		}
	}

	if (newText != null) {
		if (sort == true) {
			sortBox(toBox);
		}
	}
}

function printCheck() {
	if (window.print) {
		return 1;
	}

	return 0;
}

function printWindow() {
	if (window.print) {
		window.print();
	}
}

function processTab(id) {
	document.all[id].selection.text = String.fromCharCode(9);
	document.all[id].focus();
}

function random() {
	return randomMinMax(0, 4294967296);
};

function randomMinMax(min, max) {
	return (Math.round(Math.random() * (max - min))) + min;
}

function redirect(form) {
	var url = form.options[form.options.selectedIndex].value;

	if (url != "null") {
		self.location = url;
	}
}

function reelHome(id, startPosX, startPosY, duration, count, c) {
    if (isNaN(startPosX) || isNaN(startPosY))
        return;
        
	var obj = document.getElementById(id);
	if (obj == null) return;
	
	var top = parseInt(obj.style.top);
	var left = parseInt(obj.style.left);
	
	if (count == null) {
	    count = 1;
	}
	
	if (duration == null) {
	    duration == 20;
	}
	
	if (c == null) {
	    // calculate this constant once to speed up next iteration
	    c = Math.PI / (2 * duration);
		obj.style.zIndex = 10;
    }
	
	if (count < duration) {
	    var ratio = 1 - Math.sin(count * c);
	    
	    // shift cos by -PI/2 and up 1.
		obj.style.left = (startPosX * ratio) + "px";
		obj.style.top = (startPosY * ratio) + "px";
		
		setTimeout("reelHome(\""+id+"\","+startPosX+","+startPosY+","+duration+","+(++count)+","+c+")", 16)
	}
	else {
		obj.style.top = "0px";
		obj.style.left = "0px";
		obj.style.zIndex = 0;
	}
}

function removeItem(box, value) {
	if (value == null) {
		for (var i = box.length - 1; i >= 0; i--) {
			if (box.options[i].selected) {
				box[i] = null;
			}
		}
	}
	else {
		for (var i = box.length - 1; i >= 0; i--) {
			if (box.options[i].value == value) {
				box[i] = null;
			}
		}
	}
}

function reorder(box, down) {
	var si = box.selectedIndex;

	if (si == -1) {
		box.selectedIndex = 0;
	}
	else {
		sText = box.options[si].text;
		sValue = box.options[si].value;

		if ((box.options[si].value > "") && (si > 0) && (down == 0)) {
			box.options[si].text = box.options[si - 1].text;
			box.options[si].value = box.options[si - 1].value;
			box.options[si - 1].text = sText;
			box.options[si - 1].value = sValue;
			box.selectedIndex--;
		}
		else if ((si < box.length - 1) && (box.options[si + 1].value > "") && (down == 1)) {
			box.options[si].text = box.options[si + 1].text;
			box.options[si].value = box.options[si + 1].value;
			box.options[si + 1].text = sText;
			box.options[si + 1].value = sValue;
			box.selectedIndex++;
		}
		else if (si == 0) {
			for (var i = 0; i < (box.length - 1); i++) {
				box.options[i].text = box.options[i + 1].text;
				box.options[i].value = box.options[i + 1].value;
			}

			box.options[box.length - 1].text = sText;
			box.options[box.length - 1].value = sValue;

			box.selectedIndex = box.length - 1;
		}
		else if (si == (box.length - 1)) {
			for (var j = (box.length - 1); j > 0; j--) {
				box.options[j].text = box.options[j - 1].text;
				box.options[j].value = box.options[j - 1].value;
			}

			box.options[0].text = sText;
			box.options[0].value = sValue;

			box.selectedIndex = 0;
		}
	}
}

function selectAndCopy(el) {
	el.focus();
	el.select();

	if (document.all) {
		var textRange = el.createTextRange();

		textRange.execCommand("copy");
	}
}

function setBox(oldBox, newBox) {
	for (var i = oldBox.length - 1; i > -1; i--) {
		oldBox.options[i] = null;
	}

	for (var i = 0; i < newBox.length; i++) {
		oldBox.options[i] = new Option(newBox[i].value, i);
	}

	oldBox.options[0].selected = true;
}

function setSelectedValue(col, value) {
	for (var i = 0; i < col.length; i++) {
		if ((col[i].value != "") && (col[i].value == value)) {
			col.selectedIndex = i;

			break;
		}
	}
}

function slideMaximize(id, height, speed) {
	var obj = document.getElementById(id);
	var reference = obj.getElementsByTagName("DIV")[0];
   	height += speed;

	if (height < (reference.offsetHeight)) {
		obj.style.height = height + "px";
		setTimeout("slideMaximize(\""+id+"\","+height+","+speed+")", 10)
	}
	else {
		obj.style.overflow = "";
		obj.style.height = "";
	}
}

function slideMinimize(id, height, speed) {
	var obj = document.getElementById(id);
	height -= speed;

	if (height > 0) {
		obj.style.height = height + "px";
		setTimeout("slideMinimize(\""+id+"\","+height+","+speed+")", 10)
	}
	else {
		obj.style.display = "none";
	}
}

function sortBox(box) {
	var newBox = new Array();

	for (var i = 0; i < box.length; i++) {
		newBox[i] = new Array(box[i].value, box[i].text);
	}

	newBox.sort(sortByAscending);

	for (var i = box.length - 1; i > -1; i--) {
		box.options[i] = null;
	}

	for (var i = 0; i < newBox.length; i++) {
		box.options[box.length] = new Option(newBox[i][1], newBox[i][0]);
	}
}

function sortByAscending(a, b) {
	if (a[1].toLowerCase() > b[1].toLowerCase()) {
		return 1;
	}
	else if(a[1].toLowerCase() < b[1].toLowerCase()) {
		return -1;
	}
	else {
		return 0;
	}
}

function sortByDescending(a, b) {
	if (a[1].toLowerCase() > b[1].toLowerCase()) {
		return -1;
	}
	else if(a[1].toLowerCase() < b[1].toLowerCase()) {
		return 1;
	}
	else {
		return 0;
	}
}

function stripCarriageReturn(s) {
	return s.replace(/\r|\n|\r\n/g, " ");
}

function submitForm(form, action, singleSubmit) {
	if (submitFormCount == 0) {
		if (singleSubmit == null || singleSubmit) {
			submitFormCount++;

			for (var i = 0; i < form.length; i++){
				var e = form.elements[i];

				if (e.type && (e.type.toLowerCase() == "button" || e.type.toLowerCase() == "reset" || e.type.toLowerCase() == "submit")) {
					e.disabled=true;
				}
			}
		}

		if (action != null) {
			form.action = action;
		}

		if (!is_ns_4) {
			document.body.style.cursor = "wait";
		}

		form.submit();
	}
	else {
		if (this.submitFormAlert != null) {
  			submitFormAlert();
		}
	}
}

// Netscape 4 functions

if (is_ns_4) {
	function encodeURIComponent(uri) {
		return escape(uri);
	}

	function decodeURIComponent(uri) {
		return unescape(uri);
	}
}

// String functions

function startsWith(str, x) {
	if (str.indexOf(x) == 0) {
		return true;
	}
	else {
		return false;
	}
}

function endsWith(str, x) {
	if (str.lastIndexOf(x) == str.length - x.length) {
		return true;
	}
	else {
		return false;
	}
}

function toggleById(id, returnState, displayType) {
	var obj = document.getElementById(id);

	if (returnState) {
		return toggleByObject(obj, returnState, displayType);
	}
	else {
		toggleByObject(obj, null, displayType);
	}
}

function toggleByObject(obj, returnState, displayType) {
	var hidden = false;
	var display = "block";

	if (displayType != null) {
		display = displayType;
	}

	if (obj != null) {
		if (!obj.style.display || !obj.style.display.toLowerCase().match("none")) {
			obj.style.display = "none";
		}
		else {
			obj.style.display = display;
			hidden = true;
		}
	}
	
	if (returnState) {
		return hidden;
	}
}

function trimString(str) {
	str = str.replace(/^\s+/g, "").replace(/\s+$/g, "");

	var charCode = str.charCodeAt(0);

	while (charCode == 160) {
		str = str.substring(1, str.length);
		charCode = str.charCodeAt(0);
	}

	charCode = str.charCodeAt(str.length - 1);

	while (charCode == 160) {
		str = str.substring(0, str.length - 1);
		charCode = str.charCodeAt(str.length - 1);
	}

	return str;
}

String.prototype.trim = trimString;
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

function validateImageFileName(fileName) {
	if (fileName == null || fileName == "") {
		return false;
	}

	if (fileName.toLowerCase().indexOf(".gif") == fileName.length - 4 ||
		fileName.toLowerCase().indexOf(".jpg") == fileName.length - 4 ||
		fileName.toLowerCase().indexOf(".png") == fileName.length - 4) {

		return true;
	}

	return false;
}
function changeBackground(path, extension) { 
	var bodyWidth; 
	if (is_safari) { 
		bodyWidth = self.innerWidth; 
	} 
	else { 
		bodyWidth = document.body.clientWidth; 
	} 
	       
	if (extension != null) { 
		if (bodyWidth <= 1024) { 
			document.body.style.backgroundImage = "url(" + path + "." + extension + ")";
		} 
		else if (bodyWidth > 1024 && bodyWidth <= 1280) { 
			document.body.style.backgroundImage = "url(" + path + "-1280." + extension + ")";
		} 
		else if (bodyWidth > 1280) { 
			document.body.style.backgroundImage = "url(" + path + "-1600." + extension + ")";
		} 
	} 
}
 
var PortletHeaderBar = {

	fadeIn : function (id) {
		var bar = document.getElementById(id);
		
		// portlet has been removed.  exit.
		if (bar == null)
			return;
			
		if (bar.startOut) {
			// stop fadeOut prematurely
			clearTimeout(bar.timerOut);
			//debug_div.innerHTML += bar.timerOut + " stop OUT prematurely<br/>";
			bar.timerOut = 0;
		}
		bar.startOut = false;		
		bar.startIn = true;		

		bar.opac += 20;
		//debug_div.innerHTML += "IN "+bar.opac+"<br/>";
		for (var i = 0; i < bar.iconList.length; i++) {
			changeOpacity(bar.iconList[i], bar.opac);
		}
		bar.iconBar.style.display = "block";
		
		if (bar.opac < 100) {
			bar.timerIn = setTimeout("PortletHeaderBar.fadeIn(\"" + id + "\")", 50);
		}
		else {
			bar.timerIn = 0;
			bar.startIn = false;
		}
	},
	
	fadeOut : function (id) {
		var bar = document.getElementById(id);
		
		// portlet has been removed.  exit.
		if (bar == null)
			return;
		
		if (bar.startIn) {
			// stop fadeIn prematurely
			clearTimeout(bar.timerIn);
			//debug_div.innerHTML += + bar.timerIn + " stop IN prematurely<br/>";
			bar.timerIn = 0;
		}
		bar.startIn = false;
		bar.startOut = true;		
		
		bar.opac -= 20;
		//debug_div.innerHTML += "OUT "+bar.opac+"<br/>";
		for (var i = 0; i < bar.iconList.length; i++) {
			changeOpacity(bar.iconList[i], bar.opac);
		}
		bar.iconBar.style.display = "block";
		if (bar.opac > 0) {
			bar.timerOut = setTimeout("PortletHeaderBar.fadeOut(\"" + id + "\")", 50);
		}
		else {
			bar.iconBar.style.display = "none";
			bar.timerOut = 0;
			bar.startOut = false;
		}
	},
	
	init : function (bar) {
		if (!bar.iconBar) {
			bar.iconBar = getElementByClassName(bar, "portlet-small-icon-bar");
		}
			
		if (!bar.iconList) {
			bar.iconList = bar.iconBar.getElementsByTagName("img");
		}
	},
	
	hide : function (id) {
		var bar = document.getElementById(id);
		//debug_div.innerHTML += "<br/>hide " + bar.timerIn + " " + bar.startIn + " <br/>";
		
		// If fadeIn timer has been set, but hasn't started, cancel it
		if (bar.timerIn && !bar.startIn) {
			// cancel unstarted fadeIn
			//debug_div.innerHTML +=  "cancel unstarted IN<br/>";
			clearTimeout(bar.timerIn);
			bar.timerIn = 0;
		}	
		
		if (!bar.startOut && bar.opac > 0) {
			if (bar.timerOut) {
				// reset unstarted fadeOut timer
				clearTimeout(bar.timerOut);
				//debug_div.innerHTML += "Out restarted<br/>";
				bar.timerOut = 0;
			}

			this.init(bar);
			bar.timerOut = setTimeout("PortletHeaderBar.fadeOut(\"" + id + "\")", 150);
			//debug_div.innerHTML += bar.timerOut + " hide OUT<br/>";
		}
	},
	
	show : function (id) {
		//debug_div.innerHTML += "<br/>show<br/>";
		var bar = document.getElementById(id);
		
		// If fadeOut timer has been set, but hasn't started, cancel it
		if (bar.timerOut && !bar.startOut) {
			// cancel unstarted fadeOut
			//debug_div.innerHTML +=  "cancel unstarted OUT<br/>";
			clearTimeout(bar.timerOut);
			bar.timerOut = 0;
		}
		
		if (!bar.startIn && (!bar.opac || bar.opac < 100)){
			if (!bar.opac) {
				bar.opac = 0;
			}

			if (bar.timerIn) {
				// reset unstarted fadeIn timer
				clearTimeout(bar.timerIn);
				//debug_div.innerHTML += "In restarted<br/>";
				bar.timerIn = 0;
			}

			this.init(bar);
			bar.timerIn = setTimeout("PortletHeaderBar.fadeIn(\"" + id + "\")", 150);
			//debug_div.innerHTML += bar.timerIn + " show IN<br/>";
		}
	}
}
var Tabs = {

	show : function (namespace, names, id) {
		var el = document.all[namespace + id + "TabsId"];

		if (el) {
			el.className = "current";
		}

		el = document.all[namespace + id + "TabsSection"];
		
		if (el) {
			el.style.display = "block";
		}

		for (var i = 0; (names.length > 1) && (i < names.length); i++) {
			if (id != names[i]) {
				el = document.all[namespace + names[i] + "TabsId"];
				
				if (el) {
					el.className = "none";
				}

				el = document.all[namespace + names[i] + "TabsSection"];

				if (el) {
					el.style.display = "none";
				}
			}
		}
	}

};
/*  Prototype JavaScript framework, version 1.3.1
 *  (c) 2005 Sam Stephenson <sam@conio.net>
 *
 *  THIS FILE IS AUTOMATICALLY GENERATED. When sending patches, please diff
 *  against the source tree, available from the Prototype darcs repository. 
 *
 *  Prototype is freely distributable under the terms of an MIT-style license.
 *
 *  For details, see the Prototype web site: http://prototype.conio.net/
 *
/*--------------------------------------------------------------------------*/

var Prototype = {
  Version: '1.3.1',
  emptyFunction: function() {}
}

var Class = {
  create: function() {
    return function() { 
      this.initialize.apply(this, arguments);
    }
  }
}

var Abstract = new Object();

Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}

Object.prototype.extend = function(object) {
  return Object.extend.apply(this, [this, object]);
}

Function.prototype.bind = function(object) {
  var __method = this;
  return function() {
    __method.apply(object, arguments);
  }
}

Function.prototype.bindAsEventListener = function(object) {
  var __method = this;
  return function(event) {
    __method.call(object, event || window.event);
  }
}

Number.prototype.toColorPart = function() {
  var digits = this.toString(16);
  if (this < 16) return '0' + digits;
  return digits;
}

var Try = {
  these: function() {
    var returnValue;

    for (var i = 0; i < arguments.length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) {}
    }

    return returnValue;
  }
}

/*--------------------------------------------------------------------------*/

var PeriodicalExecuter = Class.create();
PeriodicalExecuter.prototype = {
  initialize: function(callback, frequency) {
    this.callback = callback;
    this.frequency = frequency;
    this.currentlyExecuting = false;

    this.registerCallback();
  },

  registerCallback: function() {
    setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
  },

  onTimerEvent: function() {
    if (!this.currentlyExecuting) {
      try { 
        this.currentlyExecuting = true;
        this.callback(); 
      } finally { 
        this.currentlyExecuting = false;
      }
    }
  }
}

/*--------------------------------------------------------------------------*/

function $() {
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

    if (arguments.length == 1) 
      return element;

    elements.push(element);
  }

  return elements;
}

if (!Array.prototype.push) {
  Array.prototype.push = function() {
		var startLength = this.length;
		for (var i = 0; i < arguments.length; i++)
      this[startLength + i] = arguments[i];
	  return this.length;
  }
}

if (!Function.prototype.apply) {
  // Based on code from http://www.youngpup.net/
  Function.prototype.apply = function(object, parameters) {
    var parameterStrings = new Array();
    if (!object)     object = window;
    if (!parameters) parameters = new Array();
    
    for (var i = 0; i < parameters.length; i++)
      parameterStrings[i] = 'parameters[' + i + ']';
    
    object.__apply__ = this;
    var result = eval('object.__apply__(' + 
      parameterStrings.join(', ') + ')');
    object.__apply__ = null;
    
    return result;
  }
}

String.prototype.extend({
  stripTags: function() {
    return this.replace(/<\/?[^>]+>/gi, '');
  },

  escapeHTML: function() {
    var div = document.createElement('div');
    var text = document.createTextNode(this);
    div.appendChild(text);
    return div.innerHTML;
  },

  unescapeHTML: function() {
    var div = document.createElement('div');
    div.innerHTML = this.stripTags();
    return div.childNodes[0].nodeValue;
  }
});

var Ajax = {
  getTransport: function() {
    return Try.these(
      function() {return new ActiveXObject('Msxml2.XMLHTTP')},
      function() {return new ActiveXObject('Microsoft.XMLHTTP')},
      function() {return new XMLHttpRequest()}
    ) || false;
  }
}

Ajax.Base = function() {};
Ajax.Base.prototype = {
  setOptions: function(options) {
    this.options = {
      method:       'post',
      asynchronous: true,
      parameters:   ''
    }.extend(options || {});
  },

  responseIsSuccess: function() {
    return this.transport.status == undefined
        || this.transport.status == 0 
        || (this.transport.status >= 200 && this.transport.status < 300);
  },

  responseIsFailure: function() {
    return !this.responseIsSuccess();
  }
}

Ajax.Request = Class.create();
Ajax.Request.Events = 
  ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];

Ajax.Request.prototype = (new Ajax.Base()).extend({
  initialize: function(url, options) {
    this.transport = Ajax.getTransport();
    this.setOptions(options);
    this.request(url);
  },

  request: function(url) {
    var parameters = this.options.parameters || '';
    if (parameters.length > 0) parameters += '&_=';

    try {
      if (this.options.method == 'get')
        url += '?' + parameters;

      this.transport.open(this.options.method, url,
        this.options.asynchronous);

      if (this.options.asynchronous) {
        this.transport.onreadystatechange = this.onStateChange.bind(this);
        setTimeout((function() {this.respondToReadyState(1)}).bind(this), 10);
      }

      this.setRequestHeaders();

      var body = this.options.postBody ? this.options.postBody : parameters;
      this.transport.send(this.options.method == 'post' ? body : null);

    } catch (e) {
    }
  },

  setRequestHeaders: function() {
    var requestHeaders = 
      ['X-Requested-With', 'XMLHttpRequest',
       'X-Prototype-Version', Prototype.Version];

    if (this.options.method == 'post') {
      requestHeaders.push('Content-type', 
        'application/x-www-form-urlencoded');

      /* Force "Connection: close" for Mozilla browsers to work around
       * a bug where XMLHttpReqeuest sends an incorrect Content-length
       * header. See Mozilla Bugzilla #246651. 
       */
      if (this.transport.overrideMimeType)
        requestHeaders.push('Connection', 'close');
    }

    if (this.options.requestHeaders)
      requestHeaders.push.apply(requestHeaders, this.options.requestHeaders);

    for (var i = 0; i < requestHeaders.length; i += 2)
      this.transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
  },

  onStateChange: function() {
    var readyState = this.transport.readyState;
    if (readyState != 1)
      this.respondToReadyState(this.transport.readyState);
  },

  respondToReadyState: function(readyState) {
    var event = Ajax.Request.Events[readyState];

    if (event == 'Complete')
      (this.options['on' + this.transport.status]
       || this.options['on' + (this.responseIsSuccess() ? 'Success' : 'Failure')]
       || Prototype.emptyFunction)(this.transport);

    (this.options['on' + event] || Prototype.emptyFunction)(this.transport);

    /* Avoid memory leak in MSIE: clean up the oncomplete event handler */
    if (event == 'Complete')
      this.transport.onreadystatechange = Prototype.emptyFunction;
  }
});

Ajax.Updater = Class.create();
Ajax.Updater.ScriptFragment = '(?:<script.*?>)((\n|.)*?)(?:<\/script>)';

Ajax.Updater.prototype.extend(Ajax.Request.prototype).extend({
  initialize: function(container, url, options) {
    this.containers = {
      success: container.success ? $(container.success) : $(container),
      failure: container.failure ? $(container.failure) :
        (container.success ? null : $(container))
    }

    this.transport = Ajax.getTransport();
    this.setOptions(options);

    var onComplete = this.options.onComplete || Prototype.emptyFunction;
    this.options.onComplete = (function() {
      this.updateContent();
      onComplete(this.transport);
    }).bind(this);

    this.request(url);
  },

  updateContent: function() {
    var receiver = this.responseIsSuccess() ?
      this.containers.success : this.containers.failure;

    var match    = new RegExp(Ajax.Updater.ScriptFragment, 'img');
    var response = this.transport.responseText.replace(match, '');
    var scripts  = this.transport.responseText.match(match);

    if (receiver) {
      if (this.options.insertion) {
        new this.options.insertion(receiver, response);
      } else {
        receiver.innerHTML = response;
      }
    }

    if (this.responseIsSuccess()) {
      if (this.onComplete)
        setTimeout((function() {this.onComplete(
          this.transport)}).bind(this), 10);
    }

    if (this.options.evalScripts && scripts) {
      match = new RegExp(Ajax.Updater.ScriptFragment, 'im');
      setTimeout((function() {
        for (var i = 0; i < scripts.length; i++)
          eval(scripts[i].match(match)[1]);
      }).bind(this), 10);
    }
  }
});

Ajax.PeriodicalUpdater = Class.create();
Ajax.PeriodicalUpdater.prototype = (new Ajax.Base()).extend({
  initialize: function(container, url, options) {
    this.setOptions(options);
    this.onComplete = this.options.onComplete;

    this.frequency = (this.options.frequency || 2);
    this.decay = 1;

    this.updater = {};
    this.container = container;
    this.url = url;

    this.start();
  },

  start: function() {
    this.options.onComplete = this.updateComplete.bind(this);
    this.onTimerEvent();
  },

  stop: function() {
    this.updater.onComplete = undefined;
    clearTimeout(this.timer);
    (this.onComplete || Ajax.emptyFunction).apply(this, arguments);
  },

  updateComplete: function(request) {
    if (this.options.decay) {
      this.decay = (request.responseText == this.lastText ? 
        this.decay * this.options.decay : 1);

      this.lastText = request.responseText;
    }
    this.timer = setTimeout(this.onTimerEvent.bind(this), 
      this.decay * this.frequency * 1000);
  },

  onTimerEvent: function() {
    this.updater = new Ajax.Updater(this.container, this.url, this.options);
  }
});

document.getElementsByClassName = function(className) {
  var children = document.getElementsByTagName('*') || document.all;
  var elements = new Array();
  
  for (var i = 0; i < children.length; i++) {
    var child = children[i];
    var classNames = child.className.split(' ');
    for (var j = 0; j < classNames.length; j++) {
      if (classNames[j] == className) {
        elements.push(child);
        break;
      }
    }
  }
  
  return elements;
}

/*--------------------------------------------------------------------------*/

if (!window.Element) {
  var Element = new Object();
}

Object.extend(Element, {
  toggle: function() {
    for (var i = 0; i < arguments.length; i++) {
      var element = $(arguments[i]);
      element.style.display = 
        (element.style.display == 'none' ? '' : 'none');
    }
  },

  hide: function() {
    for (var i = 0; i < arguments.length; i++) {
      var element = $(arguments[i]);
      element.style.display = 'none';
    }
  },

  show: function() {
    for (var i = 0; i < arguments.length; i++) {
      var element = $(arguments[i]);
      element.style.display = '';
    }
  },

  remove: function(element) {
    element = $(element);
    element.parentNode.removeChild(element);
  },
   
  getHeight: function(element) {
    element = $(element);
    return element.offsetHeight; 
  },

  hasClassName: function(element, className) {
    element = $(element);
    if (!element)
      return;
    var a = element.className.split(' ');
    for (var i = 0; i < a.length; i++) {
      if (a[i] == className)
        return true;
    }
    return false;
  },

  addClassName: function(element, className) {
    element = $(element);
    Element.removeClassName(element, className);
    element.className += ' ' + className;
  },

  removeClassName: function(element, className) {
    element = $(element);
    if (!element)
      return;
    var newClassName = '';
    var a = element.className.split(' ');
    for (var i = 0; i < a.length; i++) {
      if (a[i] != className) {
        if (i > 0)
          newClassName += ' ';
        newClassName += a[i];
      }
    }
    element.className = newClassName;
  },
  
  // removes whitespace-only text node children
  cleanWhitespace: function(element) {
    var element = $(element);
    for (var i = 0; i < element.childNodes.length; i++) {
      var node = element.childNodes[i];
      if (node.nodeType == 3 && !/\S/.test(node.nodeValue)) 
        Element.remove(node);
    }
  }
});

var Toggle = new Object();
Toggle.display = Element.toggle;

/*--------------------------------------------------------------------------*/

Abstract.Insertion = function(adjacency) {
  this.adjacency = adjacency;
}

Abstract.Insertion.prototype = {
  initialize: function(element, content) {
    this.element = $(element);
    this.content = content;
    
    if (this.adjacency && this.element.insertAdjacentHTML) {
      this.element.insertAdjacentHTML(this.adjacency, this.content);
    } else {
      this.range = this.element.ownerDocument.createRange();
      if (this.initializeRange) this.initializeRange();
      this.fragment = this.range.createContextualFragment(this.content);
      this.insertContent();
    }
  }
}

var Insertion = new Object();

Insertion.Before = Class.create();
Insertion.Before.prototype = (new Abstract.Insertion('beforeBegin')).extend({
  initializeRange: function() {
    this.range.setStartBefore(this.element);
  },
  
  insertContent: function() {
    this.element.parentNode.insertBefore(this.fragment, this.element);
  }
});

Insertion.Top = Class.create();
Insertion.Top.prototype = (new Abstract.Insertion('afterBegin')).extend({
  initializeRange: function() {
    this.range.selectNodeContents(this.element);
    this.range.collapse(true);
  },
  
  insertContent: function() {  
    this.element.insertBefore(this.fragment, this.element.firstChild);
  }
});

Insertion.Bottom = Class.create();
Insertion.Bottom.prototype = (new Abstract.Insertion('beforeEnd')).extend({
  initializeRange: function() {
    this.range.selectNodeContents(this.element);
    this.range.collapse(this.element);
  },
  
  insertContent: function() {
    this.element.appendChild(this.fragment);
  }
});

Insertion.After = Class.create();
Insertion.After.prototype = (new Abstract.Insertion('afterEnd')).extend({
  initializeRange: function() {
    this.range.setStartAfter(this.element);
  },
  
  insertContent: function() {
    this.element.parentNode.insertBefore(this.fragment, 
      this.element.nextSibling);
  }
});

var Field = {
  clear: function() {
    for (var i = 0; i < arguments.length; i++)
      $(arguments[i]).value = '';
  },

  focus: function(element) {
    $(element).focus();
  },
  
  present: function() {
    for (var i = 0; i < arguments.length; i++)
      if ($(arguments[i]).value == '') return false;
    return true;
  },
  
  select: function(element) {
    $(element).select();
  },
   
  activate: function(element) {
    $(element).focus();
    $(element).select();
  }
}

/*--------------------------------------------------------------------------*/

var Form = {
  serialize: function(form) {
    var elements = Form.getElements($(form));
    var queryComponents = new Array();
    
    for (var i = 0; i < elements.length; i++) {
      var queryComponent = Form.Element.serialize(elements[i]);
      if (queryComponent)
        queryComponents.push(queryComponent);
    }
    
    return queryComponents.join('&');
  },
  
  getElements: function(form) {
    var form = $(form);
    var elements = new Array();

    for (tagName in Form.Element.Serializers) {
      var tagElements = form.getElementsByTagName(tagName);
      for (var j = 0; j < tagElements.length; j++)
        elements.push(tagElements[j]);
    }
    return elements;
  },
  
  getInputs: function(form, typeName, name) {
    var form = $(form);
    var inputs = form.getElementsByTagName('input');
    
    if (!typeName && !name)
      return inputs;
      
    var matchingInputs = new Array();
    for (var i = 0; i < inputs.length; i++) {
      var input = inputs[i];
      if ((typeName && input.type != typeName) ||
          (name && input.name != name)) 
        continue;
      matchingInputs.push(input);
    }

    return matchingInputs;
  },

  disable: function(form) {
    var elements = Form.getElements(form);
    for (var i = 0; i < elements.length; i++) {
      var element = elements[i];
      element.blur();
      element.disabled = 'true';
    }
  },

  enable: function(form) {
    var elements = Form.getElements(form);
    for (var i = 0; i < elements.length; i++) {
      var element = elements[i];
      element.disabled = '';
    }
  },

  focusFirstElement: function(form) {
    var form = $(form);
    var elements = Form.getElements(form);
    for (var i = 0; i < elements.length; i++) {
      var element = elements[i];
      if (element.type != 'hidden' && !element.disabled) {
        Field.activate(element);
        break;
      }
    }
  },

  reset: function(form) {
    $(form).reset();
  }
}

Form.Element = {
  serialize: function(element) {
    var element = $(element);
    var method = element.tagName.toLowerCase();
    var parameter = Form.Element.Serializers[method](element);
    
    if (parameter)
      return encodeURIComponent(parameter[0]) + '=' + 
        encodeURIComponent(parameter[1]);                   
  },
  
  getValue: function(element) {
    var element = $(element);
    var method = element.tagName.toLowerCase();
    var parameter = Form.Element.Serializers[method](element);
    
    if (parameter) 
      return parameter[1];
  }
}

Form.Element.Serializers = {
  input: function(element) {
    switch (element.type.toLowerCase()) {
      case 'submit':
      case 'hidden':
      case 'password':
      case 'text':
        return Form.Element.Serializers.textarea(element);
      case 'checkbox':  
      case 'radio':
        return Form.Element.Serializers.inputSelector(element);
    }
    return false;
  },

  inputSelector: function(element) {
    if (element.checked)
      return [element.name, element.value];
  },

  textarea: function(element) {
    return [element.name, element.value];
  },

  select: function(element) {
    var value = '';
    if (element.type == 'select-one') {
      var index = element.selectedIndex;
      if (index >= 0)
        value = element.options[index].value || element.options[index].text;
    } else {
      value = new Array();
      for (var i = 0; i < element.length; i++) {
        var opt = element.options[i];
        if (opt.selected)
          value.push(opt.value || opt.text);
      }
    }
    return [element.name, value];
  }
}

/*--------------------------------------------------------------------------*/

var $F = Form.Element.getValue;

/*--------------------------------------------------------------------------*/

Abstract.TimedObserver = function() {}
Abstract.TimedObserver.prototype = {
  initialize: function(element, frequency, callback) {
    this.frequency = frequency;
    this.element   = $(element);
    this.callback  = callback;
    
    this.lastValue = this.getValue();
    this.registerCallback();
  },
  
  registerCallback: function() {
    setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
  },
  
  onTimerEvent: function() {
    var value = this.getValue();
    if (this.lastValue != value) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  }
}

Form.Element.Observer = Class.create();
Form.Element.Observer.prototype = (new Abstract.TimedObserver()).extend({
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.Observer = Class.create();
Form.Observer.prototype = (new Abstract.TimedObserver()).extend({
  getValue: function() {
    return Form.serialize(this.element);
  }
});

/*--------------------------------------------------------------------------*/

Abstract.EventObserver = function() {}
Abstract.EventObserver.prototype = {
  initialize: function(element, callback) {
    this.element  = $(element);
    this.callback = callback;
    
    this.lastValue = this.getValue();
    if (this.element.tagName.toLowerCase() == 'form')
      this.registerFormCallbacks();
    else
      this.registerCallback(this.element);
  },
  
  onElementEvent: function() {
    var value = this.getValue();
    if (this.lastValue != value) {
      this.callback(this.element, value);
      this.lastValue = value;
    }
  },
  
  registerFormCallbacks: function() {
    var elements = Form.getElements(this.element);
    for (var i = 0; i < elements.length; i++)
      this.registerCallback(elements[i]);
  },
  
  registerCallback: function(element) {
    if (element.type) {
      switch (element.type.toLowerCase()) {
        case 'checkbox':  
        case 'radio':
          element.target = this;
          element.prev_onclick = element.onclick || Prototype.emptyFunction;
          element.onclick = function() {
            this.prev_onclick(); 
            this.target.onElementEvent();
          }
          break;
        case 'password':
        case 'text':
        case 'textarea':
        case 'select-one':
        case 'select-multiple':
          element.target = this;
          element.prev_onchange = element.onchange || Prototype.emptyFunction;
          element.onchange = function() {
            this.prev_onchange(); 
            this.target.onElementEvent();
          }
          break;
      }
    }    
  }
}

Form.Element.EventObserver = Class.create();
Form.Element.EventObserver.prototype = (new Abstract.EventObserver()).extend({
  getValue: function() {
    return Form.Element.getValue(this.element);
  }
});

Form.EventObserver = Class.create();
Form.EventObserver.prototype = (new Abstract.EventObserver()).extend({
  getValue: function() {
    return Form.serialize(this.element);
  }
});


if (!window.Event) {
  var Event = new Object();
}

Object.extend(Event, {
  KEY_BACKSPACE: 8,
  KEY_TAB:       9,
  KEY_RETURN:   13,
  KEY_ESC:      27,
  KEY_LEFT:     37,
  KEY_UP:       38,
  KEY_RIGHT:    39,
  KEY_DOWN:     40,
  KEY_DELETE:   46,

  element: function(event) {
    return event.target || event.srcElement;
  },

  isLeftClick: function(event) {
    return (((event.which) && (event.which == 1)) ||
            ((event.button) && (event.button == 1)));
  },

  pointerX: function(event) {
    return event.pageX || (event.clientX + 
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY + 
      (document.documentElement.scrollTop || document.body.scrollTop));
  },

  stop: function(event) {
    if (event.preventDefault) { 
      event.preventDefault(); 
      event.stopPropagation(); 
    } else {
      event.returnValue = false;
    }
  },

  // find the first node with the given tagName, starting from the
  // node the event was triggered on; traverses the DOM upwards
  findElement: function(event, tagName) {
    var element = Event.element(event);
    while (element.parentNode && (!element.tagName ||
        (element.tagName.toUpperCase() != tagName.toUpperCase())))
      element = element.parentNode;
    return element;
  },

  observers: false,
  
  _observeAndCache: function(element, name, observer, useCapture) {
    if (!this.observers) this.observers = [];
    if (element.addEventListener) {
      this.observers.push([element, name, observer, useCapture]);
      element.addEventListener(name, observer, useCapture);
    } else if (element.attachEvent) {
      this.observers.push([element, name, observer, useCapture]);
      element.attachEvent('on' + name, observer);
    }
  },
  
  unloadCache: function() {
    if (!Event.observers) return;
    for (var i = 0; i < Event.observers.length; i++) {
      Event.stopObserving.apply(this, Event.observers[i]);
      Event.observers[i][0] = null;
    }
    Event.observers = false;
  },

  observe: function(element, name, observer, useCapture) {
    var element = $(element);
    useCapture = useCapture || false;
    
    if (name == 'keypress' &&
        ((navigator.appVersion.indexOf('AppleWebKit') > 0) 
        || element.attachEvent))
      name = 'keydown';
    
    this._observeAndCache(element, name, observer, useCapture);
  },

  stopObserving: function(element, name, observer, useCapture) {
    var element = $(element);
    useCapture = useCapture || false;
    
    if (name == 'keypress' &&
        ((navigator.appVersion.indexOf('AppleWebKit') > 0) 
        || element.detachEvent))
      name = 'keydown';
    
    if (element.removeEventListener) {
      element.removeEventListener(name, observer, useCapture);
    } else if (element.detachEvent) {
      element.detachEvent('on' + name, observer);
    }
  }
});

/* prevent memory leaks in IE */
Event.observe(window, 'unload', Event.unloadCache, false);

var Position = {

  // set to true if needed, warning: firefox performance problems
  // NOT neeeded for page scrolling, only if draggable contained in
  // scrollable elements
  includeScrollOffsets: false, 

  // must be called before calling withinIncludingScrolloffset, every time the
  // page is scrolled
  prepare: function() {
    this.deltaX =  window.pageXOffset 
                || document.documentElement.scrollLeft 
                || document.body.scrollLeft 
                || 0;
    this.deltaY =  window.pageYOffset 
                || document.documentElement.scrollTop 
                || document.body.scrollTop 
                || 0;
  },

  realOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0; 
      element = element.parentNode;
    } while (element);
    return [valueL, valueT];
  },

  cumulativeOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
    } while (element);
    return [valueL, valueT];
  },

  // caches x/y coordinate pair to use with overlap
  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = this.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] && 
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = this.realOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = this.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] && 
            this.xcomp <  this.offset[0] + element.offsetWidth);
  },

  // within must be called directly before
  overlap: function(mode, element) {  
    if (!mode) return 0;  
    if (mode == 'vertical') 
      return ((this.offset[1] + element.offsetHeight) - this.ycomp) / 
        element.offsetHeight;
    if (mode == 'horizontal')
      return ((this.offset[0] + element.offsetWidth) - this.xcomp) / 
        element.offsetWidth;
  },

  clone: function(source, target) {
    source = $(source);
    target = $(target);
    target.style.position = 'absolute';
    var offsets = this.cumulativeOffset(source);
    target.style.top    = offsets[1] + 'px';
    target.style.left   = offsets[0] + 'px';
    target.style.width  = source.offsetWidth + 'px';
    target.style.height = source.offsetHeight + 'px';
  }
}
/**
 * Copyright 2005 Darren L. Spurgeon
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var AjaxJspTag = {
  Version: '1.1-beta1'
}

var isSafari = false;
var isMoz = false;
var isIE = false;

if (navigator.userAgent.indexOf("Safari") > 0) {
  isSafari = true;
  isMoz = false;
  isIE = false;
}
else if (navigator.product == "Gecko") {
  isSafari = false;
  isMoz = true;
  isIE = false;
} else {
  isSafari = false;
  isMoz = false;
  isIE = true;
}

AJAX_METHOD_UPDATER = "updater";
AJAX_METHOD_REQUEST = "request";

AJAX_DEFAULT_PARAMETER = "ajaxParameter";

AJAX_PORTLET_MAX = 1;
AJAX_PORTLET_MIN = 2;
AJAX_PORTLET_CLOSE = 3;


/* ---------------------------------------------------------------------- */
/* Example File From "_JavaScript and DHTML Cookbook"
   Published by O'Reilly & Associates
   Copyright 2003 Danny Goodman
*/

// utility function to retrieve a future expiration date in proper format;
// pass three integer parameters for the number of days, hours,
// and minutes from now you want the cookie to expire; all three
// parameters required, so use zeros where appropriate
function getExpDate(days, hours, minutes) {
  var expDate = new Date();
  if (typeof days == "number" && typeof hours == "number" && typeof hours == "number") {
    expDate.setDate(expDate.getDate() + parseInt(days));
    expDate.setHours(expDate.getHours() + parseInt(hours));
    expDate.setMinutes(expDate.getMinutes() + parseInt(minutes));
    return expDate.toGMTString();
  }
}

// utility function called by getCookie()
function getCookieVal(offset) {
  var endstr = document.cookie.indexOf (";", offset);
  if (endstr == -1) {
    endstr = document.cookie.length;
  }
  return unescape(document.cookie.substring(offset, endstr));
}

// primary function to retrieve cookie by name
function getCookie(name) {
  var arg = name + "=";
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0;
  while (i < clen) {
    var j = i + alen;
    if (document.cookie.substring(i, j) == arg) {
      return getCookieVal(j);
    }
    i = document.cookie.indexOf(" ", i) + 1;
    if (i == 0) break;
  }
  return null;
}

// store cookie value with optional details as needed
function setCookie(name, value, expires, path, domain, secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");
}

// remove the cookie by setting ancient expiration date
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}
/* ---------------------------------------------------------------------- */
/* End Copyright 2003 Danny Goodman */


/* ---------------------------------------------------------------------- */
/* UTILITY FUNCTIONS
 */

/**
 * Type Detection
 */
function isAlien(a) {
  return isObject(a) && typeof a.constructor != 'function';
}

function isArray(a) {
  return isObject(a) && a.constructor == Array;
}

function isBoolean(a) {
  return typeof a == 'boolean';
}

function isEmpty(o) {
  var i, v;
  if (isObject(o)) {
    for (i in o) {
      v = o[i];
      if (isUndefined(v) && isFunction(v)) {
        return false;
      }
    }
  }
  return true;
}

function isFunction(a) {
  return typeof a == 'function';
}

function isNull(a) {
  return typeof a == 'object' && !a;
}

function isNumber(a) {
  return typeof a == 'number' && isFinite(a);
}

function isObject(a) {
  return (a && typeof a == 'object') || isFunction(a);
}

function isString(a) {
  return typeof a == 'string';
}

function isUndefined(a) {
  return typeof a == 'undefined';
}

function getPropertyByName(node, nodeName) {
  var arr = new Array();
  var items = node.getElementsByTagName("name");
  for (var i=0; i<items.length; i++) {
    if (nodeName == items[i].firstChild.nodeValue) {
      arr[0] = items[i].firstChild.nodeValue;
      arr[1] = items[i].parentNode.getElementsByTagName("value")[0].firstChild.nodeValue;
      return arr;
    }
  }
  return null;
}

function getValueForNode(node, nodeName) {
  var arr = getPropertyByName(node, nodeName);
  return arr != null ? arr[1] : null;
}

function evalBoolean(value, defaultValue) {
  if (!isNull(value) && isString(value)) {
    return ("true" == value.toLowerCase() || "yes" == value.toLowerCase()) ? "true" : "false";
  } else {
    return defaultValue == true ? "true" : "false";
  }
}

/*
 * Extract querystring from a URL
 */
function extractQueryString(url) {
  var ret = (url.indexOf('?') >= 0 && url.indexOf('?') < (url.length-1))
    ? url.substr(url.indexOf('?')+1)
    : '';
  return ret;
}

/*
 * Trim the querystring from a URL
 */
function trimQueryString(url) {
  var ret = url.indexOf('?') >= 0
    ? url.substring(0, url.indexOf('?'))
    : url;
  return ret;
}

/*
 *
 */
function delimitQueryString(qs) {
  var ret = '';
  if (qs.length > 0) {
    var params = qs.split('&');
    for (var i=0; i<params.length; i++) {
      if (i > 0) ret += ',';
      ret += params[i];
    }
  }
  return ret;
}

function getElementsByClassName(node, className) {
  var children = node.getElementsByTagName('*');
  var elements = new Array();

  for (var i = 0; i < children.length; i++) {
    var child = children[i];
    var classNames = child.className.split(' ');
    for (var j = 0; j < classNames.length; j++) {
      if (classNames[j] == className) {
        elements.push(child);
        break;
      }
    }
  }
  return elements;
}

/*
 * Add multiple onLoad Events
 */
function addOnLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      oldonload();
      func();
    }
  }
}

function getElementY(element){
  var targetTop = 0;
  if (element.offsetParent) {
    while (element.offsetParent) {
      targetTop += element.offsetTop;
      element = element.offsetParent;
    }
  } else if (element.y) {
    targetTop += element.y;
  }
  return targetTop;
}

function getElementX(element){
  var targetLeft = 0;
  if (element.offsetParent) {
    while (element.offsetParent) {
      targetLeft += element.offsetLeft;
      element = element.offsetParent;
    }
  } else if (element.x) {
    targetLeft += element.yx;
  }
  return targetLeft;
}

function findNextElementByTagName(element, tagName) {
  var children = element.getElementsByTagName('*');
  for (var i = 0; i < children.length; i++) {
    var child = children[i];
    if (child.tagName.toLowerCase() == tagName.toLowerCase())
      return child;
  }
  return null;
}


/**
 * Returns true if an element has a specified class name
 */
function hasClass(node, className) {
  if (node.className == className) {
    return true;
  }
  var reg = new RegExp('(^| )'+ className +'($| )')
  if (reg.test(node.className)) {
    return true;
  }
  return false;
}

/**
 * Emulate PHP's ereg_replace function in javascript
 */
function eregReplace(search, replace, subject) {
  return subject.replace(new RegExp(search,'g'), replace);
}

function arrayToParameterString(array) {
  var str = '';
  for (var i=0; i<array.length; i++) {
    str += (i >= 0 ? '&' : '') + array[i];
  }
  return str;
}

function replaceWithValue(sourceString, regExp, element) {
  var retString = "";
  switch (element.type) {
    case 'checkbox':
    case 'radio':
    case 'text':
    case 'textarea':
    case 'password':
    case 'select-one':
    case 'select-multiple':
      retString = sourceString.replace(regExp, $F(element));
      break;
    default:
      retString = sourceString.replace(regExp, element.innerHTML);
      break;
  }
  return retString;
}

function decodeHtml(sourceString) {
  var retString = sourceString.replace(/&amp;/, "&");
  retString = retString.replace(/&lt;/, "<");
  retString = retString.replace(/&gt;/, ">");
  return retString;
}


/* ---------------------------------------------------------------------- */
/* AJAXJSPTAG.BASE
 */

AjaxJspTag.Base = function() {};
AjaxJspTag.Base.prototype = {

  resolveParameters: function() {
    // Strip URL of querystring and append it to parameters
    var qs = delimitQueryString(extractQueryString(this.baseUrl));
    if (this.options.parameters) {
      this.options.parameters += ',' + qs;
    } else {
      this.options.parameters = qs;
    }
    this.baseUrl = trimQueryString(this.baseUrl);
  },

  setAjaxOptions: function(ajaxOptions) {
    this.ajaxOptions = {
      asynchronous: true,
      method: 'get',
      evalScripts: true,
      onComplete: this.onRequestComplete.bind(this)
    }.extend(ajaxOptions || {});
  },

  sendRequest: function() {
    new Ajax.Request(this.baseUrl, this.ajaxOptions);
  },

  sendUpdateRequest: function(target) {
    this.ajaxUpdater = new Ajax.Updater(target, this.baseUrl, this.ajaxOptions);
  },

  sendPeriodicalUpdateRequest: function(target) {
    this.ajaxPeriodicalUpdater =
      new Ajax.PeriodicalUpdater(target, this.baseUrl, this.ajaxOptions);
  },

  onRequestComplete: function(request) {
    if (request != null && request.status == 200) {
      var xmlDoc = request.responseXML;
      if (this.options.ajaxMethod != AJAX_METHOD_UPDATER
          && this.isEmptyResponse(request.responseXML)) {
        if (this.options.emptyFunction) {
          this.options.emptyFunction(request);
        }
      } else if (this.options.ajaxMethod != AJAX_METHOD_UPDATER
                 && this.isErrorResponse(request.responseXML)) {
        this.options.errorFunction(request.responseXML);
      } else {
        this.handlerFunction(request.responseXML);
      }
    } else {
      if (this.options.errorFunction) {
        this.options.errorFunction(request);
      }
    }
  },

  isEmptyResponse: function(xml) {
    var root = xml.documentElement;
    if (root.getElementsByTagName("response").length == 0
        || root.getElementsByTagName("response")[0].getElementsByTagName("item").length == 0) {
      return true;
    }
    return false;
  },

  isErrorResponse: function(xml) {
    var root = xml.documentElement;
    if (root.nodeName == "parsererror"
        || root.getElementsByTagName("error").length == 1
        || root.getElementsByTagName("response").length == 0) {
      return true;
    }
    return false;
  },

  buildParameterString: function(parameterList) {
    var ajaxParameters = parameterList || '';
    var re = new RegExp("(\\{[^,]*\\})", 'g'); // should retrieve each {} group
    var results = ajaxParameters.match(re);
    if (results != null) {
      for (var r=0; r<results.length; r++) {
        var nre = new RegExp(results[r], 'g');
        var field = $(results[r].substring(1, results[r].length-1));
        ajaxParameters = replaceWithValue(ajaxParameters, nre, field);
      }
    }
    return ajaxParameters;
  },

  attachBehaviors: function(element, event, listener, obj) {
    if (isArray(element)) {
      for (var i=0; i<element.length; i++) {
        eval("element[i].on"+event+" = listener.bindAsEventListener(obj)");
      }
    } else {
      eval("element.on"+event+" = listener.bindAsEventListener(obj)");
    }
  }

}


/* ---------------------------------------------------------------------- */
/* SELECT TAG
 */

AjaxJspTag.Select = Class.create();
AjaxJspTag.Select.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.sourceElem, "change", this.sourceElemChanged, this);
  },

  setOptions: function(options) {
    this.options = {
      sourceElem: $(options.source),
      targetElem: $(options.target),
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});
  },

  populateSelect: function(xml) {
    var root = xml.documentElement;

    // reset field
    this.options.targetElem.options.length = 0;
    this.options.targetElem.disabled = false;

    // grab list of options
    var respNode = root.getElementsByTagName("response")[0];
    var items = respNode.getElementsByTagName("item");
    for (var i=0; i<items.length; i++) {
      var name = items[i].getElementsByTagName("name")[0].firstChild.nodeValue;
      var value = items[i].getElementsByTagName("value")[0].firstChild.nodeValue;
      this.options.targetElem.options[i] = new Option(name, value);
    }
  },

  sourceElemChanged: function(e) {
    this.resolveParameters();
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.populateSelect(xml);
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* TOGGLE TAG
 */

AjaxJspTag.Toggle = Class.create();
AjaxJspTag.Toggle.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.imageElem, "click", this.imageElemClicked, this);
  },

  setOptions: function(options) {
    this.options = {
      imageElem: $(options.image),
      stateElem: $(options.state),
      stateXmlName: options.stateXmlName ? options.stateXmlName : "toggleState",
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});
  },

  toggleImage: function(xml) {
    var root = xml.documentElement;

    // set state
    var respNode = root.getElementsByTagName("response")[0];
    var toggleState = getValueForNode(respNode, this.options.stateXmlName);
    if (this.options.stateElem) {
      this.options.stateElem.value = toggleState;
    }

    // set image
    var patternRegExp = /\{0\}/;
    this.options.imageElem.src = this.options.imagePattern.replace(patternRegExp, toggleState);
  },

  imageElemClicked: function(e) {
    this.resolveParameters();
    this.setAjaxOptions({
      parameters: this.options.parameters ?
        arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
        : ''
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.toggleImage(xml);
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* UPDATE FIELD TAG
 */

AjaxJspTag.UpdateField = Class.create();
AjaxJspTag.UpdateField.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.actionElem, "click", this.actionElemClicked, this);
  },

  setOptions: function(options) {
    this.options = {
      sourceElem: $(options.source),
      actionElem: $(options.action),
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});
  },

  updateField: function(xml) {
    var root = xml.documentElement;
    var respNode = root.getElementsByTagName("response")[0];

    var items = respNode.getElementsByTagName("item");
    var targetArray = this.options.target.split(",");
    if (items.length > 0) {
      for (var i=0; i<targetArray.length; i++) {
        var value = getValueForNode(respNode, targetArray[i]);
        var field = $(targetArray[i]);
        if (value != null && field != null && field.type == "text") {
          field.value = value;
         }
      }
    }
  },

  actionElemClicked: function(e) {
    this.resolveParameters();
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.updateField(xml);
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* AUTOCOMPLETE TAG
 */

AjaxJspTag.Autocomplete = Class.create();
AjaxJspTag.Autocomplete.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.suggestList = new Array();
    this.currentIndex = 0;
    this.createPopup();
    this.createIframe();
    this.options.sourceElem.setAttribute("autocomplete", "off");
    this.attachBehaviors(this.options.sourceElem, "keyup", this.sourceElemChanged, this);
  },

  setOptions: function(options) {
    this.options = {
      sourceElem: $(options.source),
      targetElem: $(options.target),
      eventType: options.eventType ? options.eventType : "click",
      appendValue: evalBoolean(options.appendValue),
      appendSeparator: options.appendSeparator || " ",
      forceSelection: evalBoolean(options.forceSelection)
    }.extend(options || {});

    // Autocomplete is unique in that we may have a progress icon
    // So, we need to hook in the resetProgressStyle function on an empty result
    var self = this;
    this.options.emptyFunction = function() {
      self.handleEmptyResult();
      if (options.emptyFunction) options.emptyFunction();
    }

    this.popupElem = "ajaxAutocompletePopup";
  },

  autocomplete: function() {
    var root = this.xml.documentElement;

    // clear contents of container (i.e., DIV tag)
    $(this.popupElem).innerHTML = "";

    var ul = document.createElement("ul");
    items = root.getElementsByTagName("item");
    for (var i=0; i<items.length; i++) {
      var name = items[i].getElementsByTagName("name")[0].firstChild.nodeValue;
      var value = items[i].getElementsByTagName("value")[0].firstChild.nodeValue;
      var li = document.createElement("li");
      var liIdAttr = document.createAttribute("id");
      li.setAttribute("id", value);
      var liText = document.createTextNode(name);
      li.appendChild(liText);
      ul.appendChild(li);
    }
    $(this.popupElem).appendChild(ul);
    $(this.popupElem).style.visibility = 'visible';

    this.setSelected();
  },

  sourceElemChanged: function(e) {
    var key = 0;
    if (e.keyCode) { key = e.keyCode; }
    else if (typeof(e.which)!= 'undefined') { key = e.which; }
    var fieldLength = $F(this.options.sourceElem).length;

    //up arrow
    if (key == 38) {
      if (this.currentIndex > 0) {
        this.suggestList[this.currentIndex].className = '';
        this.currentIndex--;
        this.suggestList[this.currentIndex].className = 'selected';
        this.suggestList[this.currentIndex].scrollIntoView(false);
      }

    //down arrow
    } else if (key == 40) {
      if(this.currentIndex < this.suggestList.length - 1) {
        this.suggestList[this.currentIndex].className = '';
        this.currentIndex++;
        this.suggestList[this.currentIndex].className = 'selected';
        this.suggestList[this.currentIndex].scrollIntoView(false);
      }

    //enter
    } else if (key == 13 && $(this.popupElem).style.visibility == 'visible') {
      this.fillField(this.suggestList[this.currentIndex]);
      Event.stop(e);
      this.executePostFunction();

    //escape
    } else if (key == 27) {
      this.hidePopup();
      Event.stop(e);

    } else {
      if (fieldLength < this.options.minimumCharacters) {
        this.hidePopup();
      } else if (key != 37 && key != 39 && key != 17 && key != 18) {
        this.resolveParameters();
        this.setAjaxOptions({
          parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
        });
        this.setProgressStyle();
        this.sendRequest();
      }
    }
  },

  fillField: function(selection) {
    this.options.sourceElem.value = decodeHtml(selection.innerHTML);
    if (this.options.appendValue == "false") {
      this.options.targetElem.value = selection.getAttribute("id");
    } else {
      if (this.options.targetElem.value.length > 0)
        this.options.targetElem.value += this.options.appendSeparator;
      this.options.targetElem.value += selection.getAttribute("id");
    }
    $(this.popupElem).style.visibility = 'hidden';
    this.hidePopup();
  },

  handleEmptyResult: function(e) {
    this.resetProgressStyle();
    if (this.options.forceSelection == "true") {
      // remove last character typed
      this.options.sourceElem.value = this.options.sourceElem.value.substr(0, this.options.sourceElem.value.length-1);
    } else {
      // simply hide the popup
      this.hidePopup();
    }
  },

  handlerFunction: function(xml) {
    this.xml = xml;
    this.resetProgressStyle();
    this.autocomplete();
  },

  executePostFunction: function() {
    if (this.options.postFunction) {
      this.options.postFunction(this.xml);
    }
  },

  createPopup: function() {
    new Insertion.Top(
      document.getElementsByTagName("body")[0],
        "<div id=\""+this.popupElem+"\" class=\""+this.options.className+"\"></div>");
  },

  hidePopup: function() {
    $(this.popupElem).style.visibility = 'hidden';
    $('layerCover').style.display = "none";
  },

  setProgressStyle: function() {
    if (this.options.progressStyle != null) {
      Element.addClassName(this.options.sourceElem, this.options.progressStyle);
    }
  },

  resetProgressStyle: function() {
    if (this.options.progressStyle != null) {
      Element.removeClassName(this.options.sourceElem, this.options.progressStyle);
    }
  },

  setSelected: function() {
    this.currentIndex = 0;
    this.suggestList = $(this.popupElem).getElementsByTagName("li");
    if ((this.suggestList.length > 1)
       || (this.suggestList.length == 1
           && this.suggestList[0].innerHTML != $F(this.options.sourceElem))) {
      this.setPopupStyles();
      for (var i = 0; i < this.suggestList.length; i++) {
        this.suggestList[i].index = i;
        this.addOptionHandlers(this.suggestList[i]);
      }
      this.suggestList[0].className = 'selected';
    } else {
      $(this.popupElem).style.visibility = 'hidden';
    }
    return null;
  },

  setPopupStyles: function() {
    var maxHeight;
    if (isIE) {
      maxHeight = 200;
    } else {
      maxHeight = window.outerHeight/3;
    }

    if ($(this.popupElem).offsetHeight < maxHeight) {
      $(this.popupElem).style.overflow = 'hidden';
    } else if (isMoz) {
      $(this.popupElem).style.maxHeight = maxHeight + 'px';
      $(this.popupElem).style.overflow = '-moz-scrollbars-vertical';
    } else {
      $(this.popupElem).style.height = maxHeight + 'px';
      $(this.popupElem).style.overflowY = 'auto';
    }
    $(this.popupElem).scrollTop = 0;
    $(this.popupElem).style.visibility = 'visible';

    $(this.popupElem).style.top = (getElementY(this.options.sourceElem)+this.options.sourceElem.offsetHeight+2) + "px";
    $(this.popupElem).style.left = getElementX(this.options.sourceElem) + "px";
    if (isIE) {
      $(this.popupElem).style.width = this.options.sourceElem.offsetWidth + "px";
    } else {
      $(this.popupElem).style.minWidth = this.options.sourceElem.offsetWidth + "px";
    }
    $(this.popupElem).style.overflow = "visible";

    // do iframe
    $('layerCover').style.top = (getElementY(this.options.sourceElem)+this.options.sourceElem.offsetHeight+2) + "px";
    $('layerCover').style.left = getElementX(this.options.sourceElem) + "px";
    $('layerCover').style.width = $(this.popupElem).offsetWidth;
    $('layerCover').style.height = $(this.popupElem).offsetHeight;
    $('layerCover').style.display = "block";
    $('layerCover').style.zIndex = 10;
    $(this.popupElem).style.zIndex = 20;
  },

  createIframe: function() {
    new Insertion.Before($(this.popupElem), "<iframe id='layerCover' style='display: none; position: absolute; top: 0; left: 0;' src='about:blank' frameborder='0' scrolling='no'></iframe>");
  },

  handleClick: function(e) {
    this.fillField(Event.element(e));
    this.executePostFunction();
  },

  handleOver: function(e) {
    this.suggestList[this.currentIndex].className = '';
    this.currentIndex = Event.element(e).index;
    this.suggestList[this.currentIndex].className = 'selected';
  },

  addOptionHandlers: function(option) {
    option.onclick = this.handleClick.bindAsEventListener(this);
    option.onmouseover = this.handleOver.bindAsEventListener(this);
  }

});


/* ---------------------------------------------------------------------- */
/* CALLOUT TAG
 */

AjaxJspTag.Callout = Class.create();
AjaxJspTag.Callout.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.sourceElementList,
                         this.options.eventType,
                         this.sourceElemClicked,
                         this);
    this.createContainer();
    this.targetElem = this.constructBox();
    this.activeElem = null;
  },

  setOptions: function(options) {
    var list;
    if (options.sourceClass) {
      list = document.getElementsByClassName(options.sourceClass);
    } else {
      list = new Array();
      list.push($(options.source));
    }
    this.options = {
      sourceElementList: list,
      classNamePrefix: options.classNamePrefix ? options.classNamePrefix : "callout",
      eventType: options.eventType ? options.eventType : "click"
    }.extend(options || {});

    if (options.timeout) {
      if (Number(options.timeout) > 250)
        this.options.timeout = Number(options.timeout)
      else
        this.options.timeout = 250;
    }

    if (options.title) {
      this.options.useTitleBar = "true";
    } else if (options.useTitleBar) {
      this.options.useTitleBar = evalBoolean(options.useTitleBar);
    } else {
      this.options.useTitleBar = "false";
    }

    if (!options.boxPosition) this.options.boxPosition = "top right";
    this.calloutContainer = "calloutContainer";
    this.calloutParameter = AJAX_DEFAULT_PARAMETER;
  },

  callout: function(xml) {
    var root = xml.documentElement;

    var items = root.getElementsByTagName("item");
    if (items.length > 0) {
      var name = items[0].getElementsByTagName("name")[0].firstChild.nodeValue;
      var value = items[0].getElementsByTagName("value")[0].firstChild.nodeValue;
      // fill text (if present)
      if (this.options.useTitleBar == "true") {
        if (!this.options.title) {
          this.targetElem.childNodes[1].innerHTML = name;
        } else {
          this.targetElem.childNodes[1].innerHTML = this.options.title;
        }
        this.targetElem.childNodes[2].innerHTML = value;
      } else {
        this.targetElem.childNodes[1].innerHTML = value;
      }

      // size box
      if (isIE) {
        this.targetElem.style.width = "250px";
      } else {
        this.targetElem.style.minWidth = "250px";
      }
      this.targetElem.style.overflow = "visible";

      // bring box to front
      this.targetElem.style.display = "block";
      this.targetElem.style.visibility = "visible";

      // move box to new location
      this.moveBox(this.activeElem, this.targetElem);

      // hook events
      this.targetElem.childNodes[0].onclick = this.handleCloseClick.bindAsEventListener(this);
      window.onclick = this.checkBoxPosition.bindAsEventListener(this); // when clicked outside callout
      if (this.options.timeout) {
        if (this.timer)
          clearTimeout(this.timer);
        this.timer = setTimeout(this.handleHover.bind(this), this.options.timeout);
      }
    }
  },

  sourceElemClicked: function(e) {
    // replace unique parameter 'ajaxCallout' with inner content of containing element
    // ONLY IF we are using the class as the identifier; otherwise, treat it like a field
    this.activeElem = Event.element(e);
    this.resolveParameters();
    var ajaxParameters = this.options.parameters || '';
    var re = new RegExp("(\\{"+this.calloutParameter+"\\})", 'g');
    ajaxParameters = replaceWithValue(ajaxParameters, re, this.activeElem);
    // set the rest of the parameters generically
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(ajaxParameters).split(','))
    });
    this.sendRequest();
  },

  handlerFunction: function(xml) {
    this.callout(xml);
    if (this.options.postFunction) {
      this.options.postFunction(this.activeElem);
    }
  },

  createContainer: function() {
    new Insertion.Top(
      document.getElementsByTagName("body")[0],
        "<div id=\""+this.calloutContainer+"\" style=\"position: absolute; top: 0; left: 0\"></div>");
  },

  constructBox: function() {
    // create base
    var eBox = document.createElement("div");
    eBox.className = this.options.classNamePrefix+"Box";
    eBox.setAttribute("style", "position: absolute; top: 0; left: 0");
    document.documentElement.appendChild(eBox);

    // add elements
    var eClose = document.createElement("div");
    eClose.className = this.options.classNamePrefix+"Close";
    eClose.appendChild(document.createTextNode("X"));
    eBox.appendChild(eClose);

    if (this.options.useTitleBar == "true") {
      var eTitle = document.createElement("div");
      eTitle.className = this.options.classNamePrefix+"Title";
      eBox.appendChild(eTitle);
    }

    var eContent = document.createElement("div");
    eContent.className = this.options.classNamePrefix+"Content";
    eBox.appendChild(eContent);

    eBox.style.display = "none";
    $(this.calloutContainer).appendChild(eBox);
    return eBox;
  },

  moveBox: function(anchor, box) {
    box.style.position = "absolute";
    var posXY = Position.cumulativeOffset(anchor);

    if (this.options.boxPosition.indexOf("top") >= 0) {
      box.style.top = (posXY[1] - (box.offsetHeight) - 10) + "px";
    } else {
      box.style.top = (posXY[1] + (anchor.offsetHeight) + 10) + "px";
    }
    if (this.options.boxPosition.indexOf("right") >= 0) {
      box.style.left = (posXY[0] + 10) + "px";
    } else {
      box.style.left = (posXY[0] - (box.offsetWidth) - 10) + "px";
    }

    // Check for off-screen position
    if (box.offsetLeft < 0) {
      box.style.left = 0;
    }
    if (box.offsetTop < 0) {
      box.style.top = 0;
    }
  },

  handleCloseClick: function(e) {
    clearTimeout(this.timer);
    if (window.captureEvents) {
      window.releaseEvents(Event.MOUSEMOVE);
    }
    window.onmousemove = null;
    this.targetElem.style.display = "none";
  },

  checkBoxPosition: function(e) {
    var outsideContainer = false;
    var outsideSource = false;

    // evaluate if cursor is over box
    var clickX = e.clientX; // cursor X position
    var clickY = e.clientY; // cursor Y position
    var boundX1 = this.targetElem.offsetLeft;
    var boundX2 = boundX1 + this.targetElem.offsetWidth;
    var boundY1 = this.targetElem.offsetTop;
    var boundY2 = boundY1 + this.targetElem.offsetHeight;
    if (clickX < boundX1 || clickX > boundX2 || clickY < boundY1 || clickY > boundY2) {
      outsideContainer = true;
    }

    // evaluate if cursor is over source
    boundX1 = this.activeElem.offsetLeft;
    boundX2 = boundX1 + this.activeElem.offsetWidth;
    boundY1 = this.activeElem.offsetTop;
    boundY2 = boundY1 + this.activeElem.offsetHeight;

    if (clickX < boundX1 || clickX > boundX2 || clickY < boundY1 || clickY > boundY2) {
      outsideSource = true;
    }
    if (outsideContainer && outsideSource) {
      this.handleCloseClick();
    }
  },

  handleHover: function(e) {
    if (window.captureEvents) {
      window.captureEvents(Event.MOUSEMOVE);
    }
    window.onmousemove = this.checkBoxPosition.bindAsEventListener(this);
  }

});


/* ---------------------------------------------------------------------- */
/* HTML CONTENT TAG
 */

AjaxJspTag.HtmlContent = Class.create();
AjaxJspTag.HtmlContent.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.attachBehaviors(this.options.sourceElementList,
                         this.options.eventType,
                         this.sourceElemClicked,
                         this);
  },

  setOptions: function(options) {
    var list;
    if (options.sourceClass) {
      list = document.getElementsByClassName(options.sourceClass);
    } else {
      list = new Array();
      list.push($(options.source));
    }
    this.options = {
      sourceElementList: list,
      targetElem: $(options.target),
      contentXmlName: options.contentXmlName ? options.contentXmlName : "ajaxContent",
      eventType: options.eventType ? options.eventType : "click",
      ajaxMethod: AJAX_METHOD_UPDATER
    }.extend(options || {});

    this.contentParameter = AJAX_DEFAULT_PARAMETER;
  },

  sourceElemClicked: function(e) {
    this.resolveParameters();

    var ajaxParameters = this.options.parameters || '';
    if (this.options.sourceClass) {
      this.activeElem = Event.element(e);
      var re = new RegExp("(\\{"+this.contentParameter+"\\})", 'g');
      ajaxParameters = replaceWithValue(ajaxParameters, re, this.activeElem);
    }
    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(ajaxParameters).split(','))
    });

    this.sendUpdateRequest(this.options.target);
  },

  handlerFunction: function(xml) {
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* TAB PANEL TAG
 */

AjaxJspTag.TabPanel = Class.create();
AjaxJspTag.TabPanel.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    this.execute();
  },

  setOptions: function(options) {
    this.options = {
      ajaxMethod: AJAX_METHOD_UPDATER
    }.extend(options || {});
  },

  execute: function() {
    this.resolveParameters();

    this.setAjaxOptions({
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });

    this.sendUpdateRequest(this.options.target);

    $(this.options.currentStyleId).id = '';
    this.options.source.id = this.options.currentStyleId;
  },

  handlerFunction: function(xml) {
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});


/* ---------------------------------------------------------------------- */
/* PORTLET TAG
 */

AjaxJspTag.Portlet = Class.create();
AjaxJspTag.Portlet.prototype = (new AjaxJspTag.Base()).extend({

  initialize: function(url, options) {
    this.baseUrl = url;
    this.setOptions(options);
    if (this.options.executeOnLoad == "true") {
      this.execute();
    }
    if (this.preserveState) this.checkCookie();

    // Attach events to icons if defined
    if (this.options.imageClose) {
      this.attachBehaviors(this.options.closeElement, "click", this.closePortlet, this);
    }
    if (this.options.imageRefresh) {
      this.attachBehaviors(this.options.refreshElement, "click", this.refreshPortlet, this);
    }
    if (this.options.imageMaximize && this.options.imageMinimize) {
      this.attachBehaviors(this.options.toggleElement, "click", this.togglePortlet, this);
    }
  },

  checkCookie: function() {
    // Check cookie for save state
    var cVal = getCookie("AjaxJspTag.Portlet."+this.options.source);
    if (cVal != null) {
      if (cVal == AJAX_PORTLET_MIN) {
        this.togglePortlet();
      } else if (cVal == AJAX_PORTLET_CLOSE) {
        this.closePortlet();
      }
    }
  },

  setOptions: function(options) {
    this.options = {
      ajaxMethod: AJAX_METHOD_UPDATER,
      targetElement: options.classNamePrefix+"Content",
      closeElement: getElementsByClassName($(options.source), (options.classNamePrefix+"Close"))[0],
      refreshElement: getElementsByClassName($(options.source), (options.classNamePrefix+"Refresh"))[0],
      toggleElement: getElementsByClassName($(options.source), (options.classNamePrefix+"Size"))[0],
      executeOnLoad: evalBoolean(options.executeOnLoad, true),
      preserveState: evalBoolean(options.preserveState),
      expireDays: options.expireDays || "0",
      expireHours: options.expireHours || "0",
      expireMinutes: options.expireMinutes || "0",
      isMaximized: true
    }.extend(options || {});

    if (parseInt(this.options.expireDays) > 0
        || parseInt(this.options.expireHours) > 0
        || parseInt(this.options.expireMinutes) > 0) {
      this.preserveState = true;
      this.options.expireDate = getExpDate(
        parseInt(this.options.expireDays),
        parseInt(this.options.expireHours),
        parseInt(this.options.expireMinutes));
    }

    this.autoRefreshSet = false;
  },

  execute: function() {
    this.resolveParameters();

    this.setAjaxOptions({
      frequency: this.options.refreshPeriod ? (this.options.refreshPeriod) : null,
      parameters: arrayToParameterString(this.buildParameterString(this.options.parameters).split(','))
    });

    if (this.options.refreshPeriod && this.autoRefreshSet == false) {
      this.sendPeriodicalUpdateRequest(
          getElementsByClassName($(this.options.source), this.options.targetElement)[0]);
      this.autoRefreshSet = true;
    } else {
      this.sendUpdateRequest(
          getElementsByClassName($(this.options.source), this.options.targetElement)[0]);
    }
  },

  stopAutoRefresh: function() {
    // stop auto-update if present
    if (this.ajaxPeriodicalUpdater != null
        && this.options.refreshPeriod
        && this.autoRefreshSet == true) {
      this.ajaxPeriodicalUpdater.stop();
    }
  },

  startAutoRefresh: function() {
    // stop auto-update if present
    if (this.ajaxPeriodicalUpdater != null && this.options.refreshPeriod) {
      this.ajaxPeriodicalUpdater.start();
    }
  },

  refreshPortlet: function(e) {
    // clear existing updater
    this.stopAutoRefresh();
    if (this.ajaxPeriodicalUpdater != null) {
      this.startAutoRefresh();
    } else {
      this.execute();
    }
  },

  closePortlet: function(e) {
    this.stopAutoRefresh();
    Element.remove(this.options.source);
    // Save state in cookie
    if (this.preserveState) {
      setCookie("AjaxJspTag.Portlet."+this.options.source,
        AJAX_PORTLET_CLOSE,
        this.options.expireDate);
    }
  },

  togglePortlet: function(e) {
    Element.toggle(getElementsByClassName($(this.options.source), this.options.targetElement)[0]);
    var image = this.options.toggleElement;
    if (this.options.isMaximized) {
      image.src = this.options.imageMaximize;
      this.stopAutoRefresh();
    } else {
      image.src = this.options.imageMinimize;
      this.startAutoRefresh();
    }
    this.options.isMaximized = !this.options.isMaximized;
    // Save state in cookie
    if (this.preserveState) {
      setCookie("AjaxJspTag.Portlet."+this.options.source,
        (this.options.isMaximized == true ? AJAX_PORTLET_MAX : AJAX_PORTLET_MIN),
        this.options.expireDate);
    }
  },

  handlerFunction: function(xml) {
    if (this.options.postFunction) {
      this.options.postFunction(xml);
    }
  }

});
function loadForm(form, action, elId) {
	if (is_ie_5_up) {
		var pos = action.indexOf("?");

		var path = action;
		var queryString = "";

		if (pos != -1) {
			path = action.substring(0, pos);
			queryString = action.substring(pos + 1, action.length);
		}

		if (!endsWith(queryString, "&")) {
			queryString += "&";
		}

		for (var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];

			if ((e.name != null) && (e.value != null)) {
				queryString += e.name + "=" + encodeURIComponent(e.value) + "&";
			}
		}

		document.body.style.cursor = "wait";

		pos = path.indexOf("/portal/layout");

		path = path.substring(0, pos) + "/portal/render_portlet";

		var returnFunction =
			function (xmlHttpReq) {
				if (is_ie) {
					document.getElementById(elId).innerHTML = xmlHttpReq.responseText;
				}
				else {
					//var html = document.createTextNode(xmlHttpReq.responseText);

					//document.getElementById(elId).innerHTML = "";
					//document.getElementById(elId).appendChild(html);
				}

				document.body.style.cursor = "default";
			};

		loadPage(path, queryString, returnFunction);
	}
	else {
		submitForm(form, action);
	}
}

function loadPage(path, queryString, returnFunction, returnArgs) {
	if (queryString == null) {
		queryString = "";
	}

	if (!endsWith(queryString, "&")) {
		queryString += "&";
	}

	queryString += "r=" + random();

	if (returnFunction == null) {
		returnFunction = function () {};
	}

	var xmlHttpReq;

	try {
		xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch (e) {
		try {
			xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch (e) {
			try {
				xmlHttpReq = new XMLHttpRequest();
			}
			catch (e) {
			}
		}
	}

	try {
		if (false) {
			xmlHttpReq.open("GET", path + "?" + queryString, true);
		}
		else {
			xmlHttpReq.open("POST", path, true);
			xmlHttpReq.setRequestHeader("Method", "POST " + path + " HTTP/1.1");
			xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttpReq.send(queryString);
		}

		xmlHttpReq.onreadystatechange =
			function() {
				if (xmlHttpReq.readyState == 4) {
					if (xmlHttpReq.status == 200) {
						returnFunction(xmlHttpReq, returnArgs);
					}
				}
			};
	}
	catch (e) {
	}
}
/*  Copyright Mihai Bazon, 2002-2005  |  www.bazon.net/mishoo
 * -----------------------------------------------------------
 *
 * The DHTML Calendar, version 1.0 "It is happening again"
 *
 * Details and latest version at:
 * www.dynarch.com/projects/calendar
 *
 * This script is developed by Dynarch.com.  Visit us at www.dynarch.com.
 *
 * This script is distributed under the GNU Lesser General Public License.
 * Read the entire license text here: http://www.gnu.org/licenses/lgpl.html
 */
 Calendar=function(firstDayOfWeek,dateStr,onSelected,onClose){this.activeDiv=null;this.currentDateEl=null;this.getDateStatus=null;this.getDateToolTip=null;this.getDateText=null;this.timeout=null;this.onSelected=onSelected||null;this.onClose=onClose||null;this.dragging=false;this.hidden=false;this.minYear=1970;this.maxYear=2050;this.dateFormat=Calendar._TT["DEF_DATE_FORMAT"];this.ttDateFormat=Calendar._TT["TT_DATE_FORMAT"];this.isPopup=true;this.weekNumbers=true;this.firstDayOfWeek=typeof firstDayOfWeek=="number"?firstDayOfWeek:Calendar._FD;this.showsOtherMonths=false;this.dateStr=dateStr;this.ar_days=null;this.showsTime=false;this.time24=true;this.yearStep=2;this.hiliteToday=true;this.multiple=null;this.table=null;this.element=null;this.tbody=null;this.firstdayname=null;this.monthsCombo=null;this.yearsCombo=null;this.hilitedMonth=null;this.activeMonth=null;this.hilitedYear=null;this.activeYear=null;this.dateClicked=false;if(typeof Calendar._SDN=="undefined"){if(typeof Calendar._SDN_len=="undefined")Calendar._SDN_len=3;var ar=new Array();for(var i=8;i>0;){ar[--i]=Calendar._DN[i].substr(0,Calendar._SDN_len);}Calendar._SDN=ar;if(typeof Calendar._SMN_len=="undefined")Calendar._SMN_len=3;ar=new Array();for(var i=12;i>0;){ar[--i]=Calendar._MN[i].substr(0,Calendar._SMN_len);}Calendar._SMN=ar;}};Calendar._C=null;Calendar.is_ie=(/msie/i.test(navigator.userAgent)&&!/opera/i.test(navigator.userAgent));Calendar.is_ie5=(Calendar.is_ie&&/msie 5\.0/i.test(navigator.userAgent));Calendar.is_opera=/opera/i.test(navigator.userAgent);Calendar.is_khtml=/Konqueror|Safari|KHTML/i.test(navigator.userAgent);Calendar.getAbsolutePos=function(el){var SL=0,ST=0;var is_div=/^div$/i.test(el.tagName);if(is_div&&el.scrollLeft)SL=el.scrollLeft;if(is_div&&el.scrollTop)ST=el.scrollTop;var r={x:el.offsetLeft-SL,y:el.offsetTop-ST};if(el.offsetParent){var tmp=this.getAbsolutePos(el.offsetParent);r.x+=tmp.x;r.y+=tmp.y;}return r;};Calendar.isRelated=function(el,evt){var related=evt.relatedTarget;if(!related){var type=evt.type;if(type=="mouseover"){related=evt.fromElement;}else if(type=="mouseout"){related=evt.toElement;}}while(related){if(related==el){return true;}related=related.parentNode;}return false;};Calendar.removeClass=function(el,className){if(!(el&&el.className)){return;}var cls=el.className.split(" ");var ar=new Array();for(var i=cls.length;i>0;){if(cls[--i]!=className){ar[ar.length]=cls[i];}}el.className=ar.join(" ");};Calendar.addClass=function(el,className){Calendar.removeClass(el,className);el.className+=" "+className;};Calendar.getElement=function(ev){var f=Calendar.is_ie?window.event.srcElement:ev.currentTarget;while(f.nodeType!=1||/^div$/i.test(f.tagName))f=f.parentNode;return f;};Calendar.getTargetElement=function(ev){var f=Calendar.is_ie?window.event.srcElement:ev.target;while(f.nodeType!=1)f=f.parentNode;return f;};Calendar.stopEvent=function(ev){ev||(ev=window.event);if(Calendar.is_ie){ev.cancelBubble=true;ev.returnValue=false;}else{ev.preventDefault();ev.stopPropagation();}return false;};Calendar.addEvent=function(el,evname,func){if(el.attachEvent){el.attachEvent("on"+evname,func);}else if(el.addEventListener){el.addEventListener(evname,func,true);}else{el["on"+evname]=func;}};Calendar.removeEvent=function(el,evname,func){if(el.detachEvent){el.detachEvent("on"+evname,func);}else if(el.removeEventListener){el.removeEventListener(evname,func,true);}else{el["on"+evname]=null;}};Calendar.createElement=function(type,parent){var el=null;if(document.createElementNS){el=document.createElementNS("http://www.w3.org/1999/xhtml",type);}else{el=document.createElement(type);}if(typeof parent!="undefined"){parent.appendChild(el);}return el;};Calendar._add_evs=function(el){with(Calendar){addEvent(el,"mouseover",dayMouseOver);addEvent(el,"mousedown",dayMouseDown);addEvent(el,"mouseout",dayMouseOut);if(is_ie){addEvent(el,"dblclick",dayMouseDblClick);el.setAttribute("unselectable",true);}}};Calendar.findMonth=function(el){if(typeof el.month!="undefined"){return el;}else if(typeof el.parentNode.month!="undefined"){return el.parentNode;}return null;};Calendar.findYear=function(el){if(typeof el.year!="undefined"){return el;}else if(typeof el.parentNode.year!="undefined"){return el.parentNode;}return null;};Calendar.showMonthsCombo=function(){var cal=Calendar._C;if(!cal){return false;}var cal=cal;var cd=cal.activeDiv;var mc=cal.monthsCombo;if(cal.hilitedMonth){Calendar.removeClass(cal.hilitedMonth,"hilite");}if(cal.activeMonth){Calendar.removeClass(cal.activeMonth,"active");}var mon=cal.monthsCombo.getElementsByTagName("div")[cal.date.getMonth()];Calendar.addClass(mon,"active");cal.activeMonth=mon;var s=mc.style;s.display="block";if(cd.navtype<0)s.left=cd.offsetLeft+"px";else{var mcw=mc.offsetWidth;if(typeof mcw=="undefined")mcw=50;s.left=(cd.offsetLeft+cd.offsetWidth-mcw)+"px";}s.top=(cd.offsetTop+cd.offsetHeight)+"px";};Calendar.showYearsCombo=function(fwd){var cal=Calendar._C;if(!cal){return false;}var cal=cal;var cd=cal.activeDiv;var yc=cal.yearsCombo;if(cal.hilitedYear){Calendar.removeClass(cal.hilitedYear,"hilite");}if(cal.activeYear){Calendar.removeClass(cal.activeYear,"active");}cal.activeYear=null;var Y=cal.date.getFullYear()+(fwd?1:-1);var yr=yc.firstChild;var show=false;for(var i=12;i>0;--i){if(Y>=cal.minYear&&Y<=cal.maxYear){yr.innerHTML=Y;yr.year=Y;yr.style.display="block";show=true;}else{yr.style.display="none";}yr=yr.nextSibling;Y+=fwd?cal.yearStep:-cal.yearStep;}if(show){var s=yc.style;s.display="block";if(cd.navtype<0)s.left=cd.offsetLeft+"px";else{var ycw=yc.offsetWidth;if(typeof ycw=="undefined")ycw=50;s.left=(cd.offsetLeft+cd.offsetWidth-ycw)+"px";}s.top=(cd.offsetTop+cd.offsetHeight)+"px";}};Calendar.tableMouseUp=function(ev){var cal=Calendar._C;if(!cal){return false;}if(cal.timeout){clearTimeout(cal.timeout);}var el=cal.activeDiv;if(!el){return false;}var target=Calendar.getTargetElement(ev);ev||(ev=window.event);Calendar.removeClass(el,"active");if(target==el||target.parentNode==el){Calendar.cellClick(el,ev);}var mon=Calendar.findMonth(target);var date=null;if(mon){date=new Date(cal.date);if(mon.month!=date.getMonth()){date.setMonth(mon.month);cal.setDate(date);cal.dateClicked=false;cal.callHandler();}}else{var year=Calendar.findYear(target);if(year){date=new Date(cal.date);if(year.year!=date.getFullYear()){date.setFullYear(year.year);cal.setDate(date);cal.dateClicked=false;cal.callHandler();}}}with(Calendar){removeEvent(document,"mouseup",tableMouseUp);removeEvent(document,"mouseover",tableMouseOver);removeEvent(document,"mousemove",tableMouseOver);cal._hideCombos();_C=null;return stopEvent(ev);}};Calendar.tableMouseOver=function(ev){var cal=Calendar._C;if(!cal){return;}var el=cal.activeDiv;var target=Calendar.getTargetElement(ev);if(target==el||target.parentNode==el){Calendar.addClass(el,"hilite active");Calendar.addClass(el.parentNode,"rowhilite");}else{if(typeof el.navtype=="undefined"||(el.navtype!=50&&(el.navtype==0||Math.abs(el.navtype)>2)))Calendar.removeClass(el,"active");Calendar.removeClass(el,"hilite");Calendar.removeClass(el.parentNode,"rowhilite");}ev||(ev=window.event);if(el.navtype==50&&target!=el){var pos=Calendar.getAbsolutePos(el);var w=el.offsetWidth;var x=ev.clientX;var dx;var decrease=true;if(x>pos.x+w){dx=x-pos.x-w;decrease=false;}else dx=pos.x-x;if(dx<0)dx=0;var range=el._range;var current=el._current;var count=Math.floor(dx/10)%range.length;for(var i=range.length;--i>=0;)if(range[i]==current)break;while(count-->0)if(decrease){if(--i<0)i=range.length-1;}else if(++i>=range.length)i=0;var newval=range[i];el.innerHTML=newval;cal.onUpdateTime();}var mon=Calendar.findMonth(target);if(mon){if(mon.month!=cal.date.getMonth()){if(cal.hilitedMonth){Calendar.removeClass(cal.hilitedMonth,"hilite");}Calendar.addClass(mon,"hilite");cal.hilitedMonth=mon;}else if(cal.hilitedMonth){Calendar.removeClass(cal.hilitedMonth,"hilite");}}else{if(cal.hilitedMonth){Calendar.removeClass(cal.hilitedMonth,"hilite");}var year=Calendar.findYear(target);if(year){if(year.year!=cal.date.getFullYear()){if(cal.hilitedYear){Calendar.removeClass(cal.hilitedYear,"hilite");}Calendar.addClass(year,"hilite");cal.hilitedYear=year;}else if(cal.hilitedYear){Calendar.removeClass(cal.hilitedYear,"hilite");}}else if(cal.hilitedYear){Calendar.removeClass(cal.hilitedYear,"hilite");}}return Calendar.stopEvent(ev);};Calendar.tableMouseDown=function(ev){if(Calendar.getTargetElement(ev)==Calendar.getElement(ev)){return Calendar.stopEvent(ev);}};Calendar.calDragIt=function(ev){var cal=Calendar._C;if(!(cal&&cal.dragging)){return false;}var posX;var posY;if(Calendar.is_ie){posY=window.event.clientY+document.body.scrollTop;posX=window.event.clientX+document.body.scrollLeft;}else{posX=ev.pageX;posY=ev.pageY;}cal.hideShowCovered();var st=cal.element.style;st.left=(posX-cal.xOffs)+"px";st.top=(posY-cal.yOffs)+"px";return Calendar.stopEvent(ev);};Calendar.calDragEnd=function(ev){var cal=Calendar._C;if(!cal){return false;}cal.dragging=false;with(Calendar){removeEvent(document,"mousemove",calDragIt);removeEvent(document,"mouseup",calDragEnd);tableMouseUp(ev);}cal.hideShowCovered();};Calendar.dayMouseDown=function(ev){var el=Calendar.getElement(ev);if(el.disabled){return false;}var cal=el.calendar;cal.activeDiv=el;Calendar._C=cal;if(el.navtype!=300)with(Calendar){if(el.navtype==50){el._current=el.innerHTML;addEvent(document,"mousemove",tableMouseOver);}else addEvent(document,Calendar.is_ie5?"mousemove":"mouseover",tableMouseOver);addClass(el,"hilite active");addEvent(document,"mouseup",tableMouseUp);}else if(cal.isPopup){cal._dragStart(ev);}if(el.navtype==-1||el.navtype==1){if(cal.timeout)clearTimeout(cal.timeout);cal.timeout=setTimeout("Calendar.showMonthsCombo()",250);}else if(el.navtype==-2||el.navtype==2){if(cal.timeout)clearTimeout(cal.timeout);cal.timeout=setTimeout((el.navtype>0)?"Calendar.showYearsCombo(true)":"Calendar.showYearsCombo(false)",250);}else{cal.timeout=null;}return Calendar.stopEvent(ev);};Calendar.dayMouseDblClick=function(ev){Calendar.cellClick(Calendar.getElement(ev),ev||window.event);if(Calendar.is_ie){document.selection.empty();}};Calendar.dayMouseOver=function(ev){var el=Calendar.getElement(ev);if(Calendar.isRelated(el,ev)||Calendar._C||el.disabled){return false;}if(el.ttip){if(el.ttip.substr(0,1)=="_"){el.ttip=el.caldate.print(el.calendar.ttDateFormat)+el.ttip.substr(1);}el.calendar.tooltips.innerHTML=el.ttip;}if(el.navtype!=300){Calendar.addClass(el,"hilite");if(el.caldate){Calendar.addClass(el.parentNode,"rowhilite");}}return Calendar.stopEvent(ev);};Calendar.dayMouseOut=function(ev){with(Calendar){var el=getElement(ev);if(isRelated(el,ev)||_C||el.disabled)return false;removeClass(el,"hilite");if(el.caldate)removeClass(el.parentNode,"rowhilite");if(el.calendar)el.calendar.tooltips.innerHTML=_TT["SEL_DATE"];return stopEvent(ev);}};Calendar.cellClick=function(el,ev){var cal=el.calendar;var closing=false;var newdate=false;var date=null;if(typeof el.navtype=="undefined"){if(cal.currentDateEl){Calendar.removeClass(cal.currentDateEl,"selected");Calendar.addClass(el,"selected");closing=(cal.currentDateEl==el);if(!closing){cal.currentDateEl=el;}}cal.date.setDateOnly(el.caldate);date=cal.date;var other_month=!(cal.dateClicked=!el.otherMonth);if(!other_month&&!cal.currentDateEl)cal._toggleMultipleDate(new Date(date));else newdate=!el.disabled;if(other_month)cal._init(cal.firstDayOfWeek,date);}else{if(el.navtype==200){Calendar.removeClass(el,"hilite");cal.callCloseHandler();return;}date=new Date(cal.date);if(el.navtype==0)date.setDateOnly(new Date());cal.dateClicked=false;var year=date.getFullYear();var mon=date.getMonth();function setMonth(m){var day=date.getDate();var max=date.getMonthDays(m);if(day>max){date.setDate(max);}date.setMonth(m);};switch(el.navtype){case 400:Calendar.removeClass(el,"hilite");var text=Calendar._TT["ABOUT"];if(typeof text!="undefined"){text+=cal.showsTime?Calendar._TT["ABOUT_TIME"]:"";}else{text="Help and about box text is not translated into this language.\n"+"If you know this language and you feel generous please update\n"+"the corresponding file in \"lang\" subdir to match calendar-en.js\n"+"and send it back to <mihai_bazon@yahoo.com> to get it into the distribution  ;-)\n\n"+"Thank you!\n"+"http://dynarch.com/mishoo/calendar.epl\n";}alert(text);return;case-2:if(year>cal.minYear){date.setFullYear(year-1);}break;case-1:if(mon>0){setMonth(mon-1);}else if(year-->cal.minYear){date.setFullYear(year);setMonth(11);}break;case 1:if(mon<11){setMonth(mon+1);}else if(year<cal.maxYear){date.setFullYear(year+1);setMonth(0);}break;case 2:if(year<cal.maxYear){date.setFullYear(year+1);}break;case 100:cal.setFirstDayOfWeek(el.fdow);return;case 50:var range=el._range;var current=el.innerHTML;for(var i=range.length;--i>=0;)if(range[i]==current)break;if(ev&&ev.shiftKey){if(--i<0)i=range.length-1;}else if(++i>=range.length)i=0;var newval=range[i];el.innerHTML=newval;cal.onUpdateTime();return;case 0:if((typeof cal.getDateStatus=="function")&&cal.getDateStatus(date,date.getFullYear(),date.getMonth(),date.getDate())){return false;}break;}if(!date.equalsTo(cal.date)){cal.setDate(date);newdate=true;}else if(el.navtype==0)newdate=closing=true;}if(newdate){ev&&cal.callHandler();}if(closing){Calendar.removeClass(el,"hilite");ev&&cal.callCloseHandler();}};Calendar.prototype.create=function(_par){var parent=null;if(!_par){parent=document.getElementsByTagName("body")[0];this.isPopup=true;}else{parent=_par;this.isPopup=false;}this.date=this.dateStr?new Date(this.dateStr):new Date();var table=Calendar.createElement("table");this.table=table;table.cellSpacing=0;table.cellPadding=0;table.calendar=this;Calendar.addEvent(table,"mousedown",Calendar.tableMouseDown);var div=Calendar.createElement("div");this.element=div;div.className="calendar";if(this.isPopup){div.style.position="absolute";div.style.display="none";}div.appendChild(table);var thead=Calendar.createElement("thead",table);var cell=null;var row=null;var cal=this;var hh=function(text,cs,navtype){cell=Calendar.createElement("td",row);cell.colSpan=cs;cell.className="button";if(navtype!=0&&Math.abs(navtype)<=2)cell.className+=" nav";Calendar._add_evs(cell);cell.calendar=cal;cell.navtype=navtype;cell.innerHTML="<div unselectable='on'>"+text+"</div>";return cell;};row=Calendar.createElement("tr",thead);var title_length=6;(this.isPopup)&&--title_length;(this.weekNumbers)&&++title_length;hh("?",1,400).ttip=Calendar._TT["INFO"];this.title=hh("",title_length,300);this.title.className="title";if(this.isPopup){this.title.ttip=Calendar._TT["DRAG_TO_MOVE"];this.title.style.cursor="move";hh("&#x00d7;",1,200).ttip=Calendar._TT["CLOSE"];}row=Calendar.createElement("tr",thead);row.className="headrow";this._nav_py=hh("&#x00ab;",1,-2);this._nav_py.ttip=Calendar._TT["PREV_YEAR"];this._nav_pm=hh("&#x2039;",1,-1);this._nav_pm.ttip=Calendar._TT["PREV_MONTH"];this._nav_now=hh(Calendar._TT["TODAY"],this.weekNumbers?4:3,0);this._nav_now.ttip=Calendar._TT["GO_TODAY"];this._nav_nm=hh("&#x203a;",1,1);this._nav_nm.ttip=Calendar._TT["NEXT_MONTH"];this._nav_ny=hh("&#x00bb;",1,2);this._nav_ny.ttip=Calendar._TT["NEXT_YEAR"];row=Calendar.createElement("tr",thead);row.className="daynames";if(this.weekNumbers){cell=Calendar.createElement("td",row);cell.className="name wn";cell.innerHTML=Calendar._TT["WK"];}for(var i=7;i>0;--i){cell=Calendar.createElement("td",row);if(!i){cell.navtype=100;cell.calendar=this;Calendar._add_evs(cell);}}this.firstdayname=(this.weekNumbers)?row.firstChild.nextSibling:row.firstChild;this._displayWeekdays();var tbody=Calendar.createElement("tbody",table);this.tbody=tbody;for(i=6;i>0;--i){row=Calendar.createElement("tr",tbody);if(this.weekNumbers){cell=Calendar.createElement("td",row);}for(var j=7;j>0;--j){cell=Calendar.createElement("td",row);cell.calendar=this;Calendar._add_evs(cell);}}if(this.showsTime){row=Calendar.createElement("tr",tbody);row.className="time";cell=Calendar.createElement("td",row);cell.className="time";cell.colSpan=2;cell.innerHTML=Calendar._TT["TIME"]||"&nbsp;";cell=Calendar.createElement("td",row);cell.className="time";cell.colSpan=this.weekNumbers?4:3;(function(){function makeTimePart(className,init,range_start,range_end){var part=Calendar.createElement("span",cell);part.className=className;part.innerHTML=init;part.calendar=cal;part.ttip=Calendar._TT["TIME_PART"];part.navtype=50;part._range=[];if(typeof range_start!="number")part._range=range_start;else{for(var i=range_start;i<=range_end;++i){var txt;if(i<10&&range_end>=10)txt='0'+i;else txt=''+i;part._range[part._range.length]=txt;}}Calendar._add_evs(part);return part;};var hrs=cal.date.getHours();var mins=cal.date.getMinutes();var t12=!cal.time24;var pm=(hrs>12);if(t12&&pm)hrs-=12;var H=makeTimePart("hour",hrs,t12?1:0,t12?12:23);var span=Calendar.createElement("span",cell);span.innerHTML=":";span.className="colon";var M=makeTimePart("minute",mins,0,59);var AP=null;cell=Calendar.createElement("td",row);cell.className="time";cell.colSpan=2;if(t12)AP=makeTimePart("ampm",pm?"pm":"am",["am","pm"]);else cell.innerHTML="&nbsp;";cal.onSetTime=function(){var pm,hrs=this.date.getHours(),mins=this.date.getMinutes();if(t12){pm=(hrs>=12);if(pm)hrs-=12;if(hrs==0)hrs=12;AP.innerHTML=pm?"pm":"am";}H.innerHTML=(hrs<10)?("0"+hrs):hrs;M.innerHTML=(mins<10)?("0"+mins):mins;};cal.onUpdateTime=function(){var date=this.date;var h=parseInt(H.innerHTML,10);if(t12){if(/pm/i.test(AP.innerHTML)&&h<12)h+=12;else if(/am/i.test(AP.innerHTML)&&h==12)h=0;}var d=date.getDate();var m=date.getMonth();var y=date.getFullYear();date.setHours(h);date.setMinutes(parseInt(M.innerHTML,10));date.setFullYear(y);date.setMonth(m);date.setDate(d);this.dateClicked=false;this.callHandler();};})();}else{this.onSetTime=this.onUpdateTime=function(){};}var tfoot=Calendar.createElement("tfoot",table);row=Calendar.createElement("tr",tfoot);row.className="footrow";cell=hh(Calendar._TT["SEL_DATE"],this.weekNumbers?8:7,300);cell.className="ttip";if(this.isPopup){cell.ttip=Calendar._TT["DRAG_TO_MOVE"];cell.style.cursor="move";}this.tooltips=cell;div=Calendar.createElement("div",this.element);this.monthsCombo=div;div.className="combo";for(i=0;i<Calendar._MN.length;++i){var mn=Calendar.createElement("div");mn.className=Calendar.is_ie?"label-IEfix":"label";mn.month=i;mn.innerHTML=Calendar._SMN[i];div.appendChild(mn);}div=Calendar.createElement("div",this.element);this.yearsCombo=div;div.className="combo";for(i=12;i>0;--i){var yr=Calendar.createElement("div");yr.className=Calendar.is_ie?"label-IEfix":"label";div.appendChild(yr);}this._init(this.firstDayOfWeek,this.date);parent.appendChild(this.element);};Calendar._keyEvent=function(ev){var cal=window._dynarch_popupCalendar;if(!cal||cal.multiple)return false;(Calendar.is_ie)&&(ev=window.event);var act=(Calendar.is_ie||ev.type=="keypress"),K=ev.keyCode;if(ev.ctrlKey){switch(K){case 37:act&&Calendar.cellClick(cal._nav_pm);break;case 38:act&&Calendar.cellClick(cal._nav_py);break;case 39:act&&Calendar.cellClick(cal._nav_nm);break;case 40:act&&Calendar.cellClick(cal._nav_ny);break;default:return false;}}else switch(K){case 32:Calendar.cellClick(cal._nav_now);break;case 27:act&&cal.callCloseHandler();break;case 37:case 38:case 39:case 40:if(act){var prev,x,y,ne,el,step;prev=K==37||K==38;step=(K==37||K==39)?1:7;function setVars(){el=cal.currentDateEl;var p=el.pos;x=p&15;y=p>>4;ne=cal.ar_days[y][x];};setVars();function prevMonth(){var date=new Date(cal.date);date.setDate(date.getDate()-step);cal.setDate(date);};function nextMonth(){var date=new Date(cal.date);date.setDate(date.getDate()+step);cal.setDate(date);};while(1){switch(K){case 37:if(--x>=0)ne=cal.ar_days[y][x];else{x=6;K=38;continue;}break;case 38:if(--y>=0)ne=cal.ar_days[y][x];else{prevMonth();setVars();}break;case 39:if(++x<7)ne=cal.ar_days[y][x];else{x=0;K=40;continue;}break;case 40:if(++y<cal.ar_days.length)ne=cal.ar_days[y][x];else{nextMonth();setVars();}break;}break;}if(ne){if(!ne.disabled)Calendar.cellClick(ne);else if(prev)prevMonth();else nextMonth();}}break;case 13:if(act)Calendar.cellClick(cal.currentDateEl,ev);break;default:return false;}return Calendar.stopEvent(ev);};Calendar.prototype._init=function(firstDayOfWeek,date){var today=new Date(),TY=today.getFullYear(),TM=today.getMonth(),TD=today.getDate();this.table.style.visibility="hidden";var year=date.getFullYear();if(year<this.minYear){year=this.minYear;date.setFullYear(year);}else if(year>this.maxYear){year=this.maxYear;date.setFullYear(year);}this.firstDayOfWeek=firstDayOfWeek;this.date=new Date(date);var month=date.getMonth();var mday=date.getDate();var no_days=date.getMonthDays();date.setDate(1);var day1=(date.getDay()-this.firstDayOfWeek)%7;if(day1<0)day1+=7;date.setDate(-day1);date.setDate(date.getDate()+1);var row=this.tbody.firstChild;var MN=Calendar._SMN[month];var ar_days=this.ar_days=new Array();var weekend=Calendar._TT["WEEKEND"];var dates=this.multiple?(this.datesCells={}):null;for(var i=0;i<6;++i,row=row.nextSibling){var cell=row.firstChild;if(this.weekNumbers){cell.className="day wn";cell.innerHTML=date.getWeekNumber();cell=cell.nextSibling;}row.className="daysrow";var hasdays=false,iday,dpos=ar_days[i]=[];for(var j=0;j<7;++j,cell=cell.nextSibling,date.setDate(iday+1)){iday=date.getDate();var wday=date.getDay();cell.className="day";cell.pos=i<<4|j;dpos[j]=cell;var current_month=(date.getMonth()==month);if(!current_month){if(this.showsOtherMonths){cell.className+=" othermonth";cell.otherMonth=true;}else{cell.className="emptycell";cell.innerHTML="&nbsp;";cell.disabled=true;continue;}}else{cell.otherMonth=false;hasdays=true;}cell.disabled=false;cell.innerHTML=this.getDateText?this.getDateText(date,iday):iday;if(dates)dates[date.print("%Y%m%d")]=cell;if(this.getDateStatus){var status=this.getDateStatus(date,year,month,iday);if(this.getDateToolTip){var toolTip=this.getDateToolTip(date,year,month,iday);if(toolTip)cell.title=toolTip;}if(status===true){cell.className+=" disabled";cell.disabled=true;}else{if(/disabled/i.test(status))cell.disabled=true;cell.className+=" "+status;}}if(!cell.disabled){cell.caldate=new Date(date);cell.ttip="_";if(!this.multiple&&current_month&&iday==mday&&this.hiliteToday){cell.className+=" selected";this.currentDateEl=cell;}if(date.getFullYear()==TY&&date.getMonth()==TM&&iday==TD){cell.className+=" today";cell.ttip+=Calendar._TT["PART_TODAY"];}if(weekend.indexOf(wday.toString())!=-1)cell.className+=cell.otherMonth?" oweekend":" weekend";}}if(!(hasdays||this.showsOtherMonths))row.className="emptyrow";}this.title.innerHTML=Calendar._MN[month]+", "+year;this.onSetTime();this.table.style.visibility="visible";this._initMultipleDates();};Calendar.prototype._initMultipleDates=function(){if(this.multiple){for(var i in this.multiple){var cell=this.datesCells[i];var d=this.multiple[i];if(!d)continue;if(cell)cell.className+=" selected";}}};Calendar.prototype._toggleMultipleDate=function(date){if(this.multiple){var ds=date.print("%Y%m%d");var cell=this.datesCells[ds];if(cell){var d=this.multiple[ds];if(!d){Calendar.addClass(cell,"selected");this.multiple[ds]=date;}else{Calendar.removeClass(cell,"selected");delete this.multiple[ds];}}}};Calendar.prototype.setDateToolTipHandler=function(unaryFunction){this.getDateToolTip=unaryFunction;};Calendar.prototype.setDate=function(date){if(!date.equalsTo(this.date)){this._init(this.firstDayOfWeek,date);}};Calendar.prototype.refresh=function(){this._init(this.firstDayOfWeek,this.date);};Calendar.prototype.setFirstDayOfWeek=function(firstDayOfWeek){this._init(firstDayOfWeek,this.date);this._displayWeekdays();};Calendar.prototype.setDateStatusHandler=Calendar.prototype.setDisabledHandler=function(unaryFunction){this.getDateStatus=unaryFunction;};Calendar.prototype.setRange=function(a,z){this.minYear=a;this.maxYear=z;};Calendar.prototype.callHandler=function(){if(this.onSelected){this.onSelected(this,this.date.print(this.dateFormat));}};Calendar.prototype.callCloseHandler=function(){if(this.onClose){this.onClose(this);}this.hideShowCovered();};Calendar.prototype.destroy=function(){var el=this.element.parentNode;el.removeChild(this.element);Calendar._C=null;window._dynarch_popupCalendar=null;};Calendar.prototype.reparent=function(new_parent){var el=this.element;el.parentNode.removeChild(el);new_parent.appendChild(el);};Calendar._checkCalendar=function(ev){var calendar=window._dynarch_popupCalendar;if(!calendar){return false;}var el=Calendar.is_ie?Calendar.getElement(ev):Calendar.getTargetElement(ev);for(;el!=null&&el!=calendar.element;el=el.parentNode);if(el==null){window._dynarch_popupCalendar.callCloseHandler();return Calendar.stopEvent(ev);}};Calendar.prototype.show=function(){var rows=this.table.getElementsByTagName("tr");for(var i=rows.length;i>0;){var row=rows[--i];Calendar.removeClass(row,"rowhilite");var cells=row.getElementsByTagName("td");for(var j=cells.length;j>0;){var cell=cells[--j];Calendar.removeClass(cell,"hilite");Calendar.removeClass(cell,"active");}}this.element.style.display="block";this.hidden=false;if(this.isPopup){window._dynarch_popupCalendar=this;Calendar.addEvent(document,"keydown",Calendar._keyEvent);Calendar.addEvent(document,"keypress",Calendar._keyEvent);Calendar.addEvent(document,"mousedown",Calendar._checkCalendar);}this.hideShowCovered();};Calendar.prototype.hide=function(){if(this.isPopup){Calendar.removeEvent(document,"keydown",Calendar._keyEvent);Calendar.removeEvent(document,"keypress",Calendar._keyEvent);Calendar.removeEvent(document,"mousedown",Calendar._checkCalendar);}this.element.style.display="none";this.hidden=true;this.hideShowCovered();};Calendar.prototype.showAt=function(x,y){var s=this.element.style;s.left=x+"px";s.top=y+"px";this.show();};Calendar.prototype.showAtElement=function(el,opts){var self=this;var p=Calendar.getAbsolutePos(el);if(!opts||typeof opts!="string"){this.showAt(p.x,p.y+el.offsetHeight);return true;}function fixPosition(box){if(box.x<0)box.x=0;if(box.y<0)box.y=0;var cp=document.createElement("div");var s=cp.style;s.position="absolute";s.right=s.bottom=s.width=s.height="0px";document.body.appendChild(cp);var br=Calendar.getAbsolutePos(cp);document.body.removeChild(cp);if(Calendar.is_ie){br.y+=document.body.scrollTop;br.x+=document.body.scrollLeft;}else{br.y+=window.scrollY;br.x+=window.scrollX;}var tmp=box.x+box.width-br.x;if(tmp>0)box.x-=tmp;tmp=box.y+box.height-br.y;if(tmp>0)box.y-=tmp;};this.element.style.display="block";Calendar.continuation_for_the_fucking_khtml_browser=function(){var w=self.element.offsetWidth;var h=self.element.offsetHeight;self.element.style.display="none";var valign=opts.substr(0,1);var halign="l";if(opts.length>1){halign=opts.substr(1,1);}switch(valign){case "T":p.y-=h;break;case "B":p.y+=el.offsetHeight;break;case "C":p.y+=(el.offsetHeight-h)/2;break;case "t":p.y+=el.offsetHeight-h;break;case "b":break;}switch(halign){case "L":p.x-=w;break;case "R":p.x+=el.offsetWidth;break;case "C":p.x+=(el.offsetWidth-w)/2;break;case "l":p.x+=el.offsetWidth-w;break;case "r":break;}p.width=w;p.height=h+40;self.monthsCombo.style.display="none";fixPosition(p);self.showAt(p.x,p.y);};if(Calendar.is_khtml)setTimeout("Calendar.continuation_for_the_fucking_khtml_browser()",10);else Calendar.continuation_for_the_fucking_khtml_browser();};Calendar.prototype.setDateFormat=function(str){this.dateFormat=str;};Calendar.prototype.setTtDateFormat=function(str){this.ttDateFormat=str;};Calendar.prototype.parseDate=function(str,fmt){if(!fmt)fmt=this.dateFormat;this.setDate(Date.parseDate(str,fmt));};Calendar.prototype.hideShowCovered=function(){if(!Calendar.is_ie&&!Calendar.is_opera)return;function getVisib(obj){var value=obj.style.visibility;if(!value){if(document.defaultView&&typeof(document.defaultView.getComputedStyle)=="function"){if(!Calendar.is_khtml)value=document.defaultView. getComputedStyle(obj,"").getPropertyValue("visibility");else value='';}else if(obj.currentStyle){value=obj.currentStyle.visibility;}else value='';}return value;};var tags=new Array("applet","iframe","select");var el=this.element;var p=Calendar.getAbsolutePos(el);var EX1=p.x;var EX2=el.offsetWidth+EX1;var EY1=p.y;var EY2=el.offsetHeight+EY1;for(var k=tags.length;k>0;){var ar=document.getElementsByTagName(tags[--k]);var cc=null;for(var i=ar.length;i>0;){cc=ar[--i];p=Calendar.getAbsolutePos(cc);var CX1=p.x;var CX2=cc.offsetWidth+CX1;var CY1=p.y;var CY2=cc.offsetHeight+CY1;if(this.hidden||(CX1>EX2)||(CX2<EX1)||(CY1>EY2)||(CY2<EY1)){if(!cc.__msh_save_visibility){cc.__msh_save_visibility=getVisib(cc);}cc.style.visibility=cc.__msh_save_visibility;}else{if(!cc.__msh_save_visibility){cc.__msh_save_visibility=getVisib(cc);}cc.style.visibility="hidden";}}}};Calendar.prototype._displayWeekdays=function(){var fdow=this.firstDayOfWeek;var cell=this.firstdayname;var weekend=Calendar._TT["WEEKEND"];for(var i=0;i<7;++i){cell.className="day name";var realday=(i+fdow)%7;if(i){cell.ttip=Calendar._TT["DAY_FIRST"].replace("%s",Calendar._DN[realday]);cell.navtype=100;cell.calendar=this;cell.fdow=realday;Calendar._add_evs(cell);}if(weekend.indexOf(realday.toString())!=-1){Calendar.addClass(cell,"weekend");}cell.innerHTML=Calendar._SDN[(i+fdow)%7];cell=cell.nextSibling;}};Calendar.prototype._hideCombos=function(){this.monthsCombo.style.display="none";this.yearsCombo.style.display="none";};Calendar.prototype._dragStart=function(ev){if(this.dragging){return;}this.dragging=true;var posX;var posY;if(Calendar.is_ie){posY=window.event.clientY+document.body.scrollTop;posX=window.event.clientX+document.body.scrollLeft;}else{posY=ev.clientY+window.scrollY;posX=ev.clientX+window.scrollX;}var st=this.element.style;this.xOffs=posX-parseInt(st.left);this.yOffs=posY-parseInt(st.top);with(Calendar){addEvent(document,"mousemove",calDragIt);addEvent(document,"mouseup",calDragEnd);}};Date._MD=new Array(31,28,31,30,31,30,31,31,30,31,30,31);Date.SECOND=1000;Date.MINUTE=60*Date.SECOND;Date.HOUR=60*Date.MINUTE;Date.DAY=24*Date.HOUR;Date.WEEK=7*Date.DAY;Date.parseDate=function(str,fmt){var today=new Date();var y=0;var m=-1;var d=0;var a=str.split(/\W+/);var b=fmt.match(/%./g);var i=0,j=0;var hr=0;var min=0;for(i=0;i<a.length;++i){if(!a[i])continue;switch(b[i]){case "%d":case "%e":d=parseInt(a[i],10);break;case "%m":m=parseInt(a[i],10)-1;break;case "%Y":case "%y":y=parseInt(a[i],10);(y<100)&&(y+=(y>29)?1900:2000);break;case "%b":case "%B":for(j=0;j<12;++j){if(Calendar._MN[j].substr(0,a[i].length).toLowerCase()==a[i].toLowerCase()){m=j;break;}}break;case "%H":case "%I":case "%k":case "%l":hr=parseInt(a[i],10);break;case "%P":case "%p":if(/pm/i.test(a[i])&&hr<12)hr+=12;else if(/am/i.test(a[i])&&hr>=12)hr-=12;break;case "%M":min=parseInt(a[i],10);break;}}if(isNaN(y))y=today.getFullYear();if(isNaN(m))m=today.getMonth();if(isNaN(d))d=today.getDate();if(isNaN(hr))hr=today.getHours();if(isNaN(min))min=today.getMinutes();if(y!=0&&m!=-1&&d!=0)return new Date(y,m,d,hr,min,0);y=0;m=-1;d=0;for(i=0;i<a.length;++i){if(a[i].search(/[a-zA-Z]+/)!=-1){var t=-1;for(j=0;j<12;++j){if(Calendar._MN[j].substr(0,a[i].length).toLowerCase()==a[i].toLowerCase()){t=j;break;}}if(t!=-1){if(m!=-1){d=m+1;}m=t;}}else if(parseInt(a[i],10)<=12&&m==-1){m=a[i]-1;}else if(parseInt(a[i],10)>31&&y==0){y=parseInt(a[i],10);(y<100)&&(y+=(y>29)?1900:2000);}else if(d==0){d=a[i];}}if(y==0)y=today.getFullYear();if(m!=-1&&d!=0)return new Date(y,m,d,hr,min,0);return today;};Date.prototype.getMonthDays=function(month){var year=this.getFullYear();if(typeof month=="undefined"){month=this.getMonth();}if(((0==(year%4))&&((0!=(year%100))||(0==(year%400))))&&month==1){return 29;}else{return Date._MD[month];}};Date.prototype.getDayOfYear=function(){var now=new Date(this.getFullYear(),this.getMonth(),this.getDate(),0,0,0);var then=new Date(this.getFullYear(),0,0,0,0,0);var time=now-then;return Math.floor(time/Date.DAY);};Date.prototype.getWeekNumber=function(){var d=new Date(this.getFullYear(),this.getMonth(),this.getDate(),0,0,0);var DoW=d.getDay();d.setDate(d.getDate()-(DoW+6)%7+3);var ms=d.valueOf();d.setMonth(0);d.setDate(4);return Math.round((ms-d.valueOf())/(7*864e5))+1;};Date.prototype.equalsTo=function(date){return((this.getFullYear()==date.getFullYear())&&(this.getMonth()==date.getMonth())&&(this.getDate()==date.getDate())&&(this.getHours()==date.getHours())&&(this.getMinutes()==date.getMinutes()));};Date.prototype.setDateOnly=function(date){var tmp=new Date(date);this.setDate(1);this.setFullYear(tmp.getFullYear());this.setMonth(tmp.getMonth());this.setDate(tmp.getDate());};Date.prototype.print=function(str){var m=this.getMonth();var d=this.getDate();var y=this.getFullYear();var wn=this.getWeekNumber();var w=this.getDay();var s={};var hr=this.getHours();var pm=(hr>=12);var ir=(pm)?(hr-12):hr;var dy=this.getDayOfYear();if(ir==0)ir=12;var min=this.getMinutes();var sec=this.getSeconds();s["%a"]=Calendar._SDN[w];s["%A"]=Calendar._DN[w];s["%b"]=Calendar._SMN[m];s["%B"]=Calendar._MN[m];s["%C"]=1+Math.floor(y/100);s["%d"]=(d<10)?("0"+d):d;s["%e"]=d;s["%H"]=(hr<10)?("0"+hr):hr;s["%I"]=(ir<10)?("0"+ir):ir;s["%j"]=(dy<100)?((dy<10)?("00"+dy):("0"+dy)):dy;s["%k"]=hr;s["%l"]=ir;s["%m"]=(m<9)?("0"+(1+m)):(1+m);s["%M"]=(min<10)?("0"+min):min;s["%n"]="\n";s["%p"]=pm?"PM":"AM";s["%P"]=pm?"pm":"am";s["%s"]=Math.floor(this.getTime()/1000);s["%S"]=(sec<10)?("0"+sec):sec;s["%t"]="\t";s["%U"]=s["%W"]=s["%V"]=(wn<10)?("0"+wn):wn;s["%u"]=w+1;s["%w"]=w;s["%y"]=(''+y).substr(2,2);s["%Y"]=y;s["%%"]="%";var re=/%./g;if(!Calendar.is_ie5&&!Calendar.is_khtml)return str.replace(re,function(par){return s[par]||par;});var a=str.match(re);for(var i=0;i<a.length;i++){var tmp=s[a[i]];if(tmp){re=new RegExp(a[i],'g');str=str.replace(re,tmp);}}return str;};Date.prototype.__msh_oldSetFullYear=Date.prototype.setFullYear;Date.prototype.setFullYear=function(y){var d=new Date(this);d.__msh_oldSetFullYear(y);if(d.getMonth()!=this.getMonth())this.setDate(28);this.__msh_oldSetFullYear(y);};window._dynarch_popupCalendar=null;
/*  Copyright Mihai Bazon, 2002, 2003  |  http://dynarch.com/mishoo/
 * ---------------------------------------------------------------------------
 *
 * The DHTML Calendar
 *
 * Details and latest version at:
 * http://dynarch.com/mishoo/calendar.epl
 *
 * This script is distributed under the GNU Lesser General Public License.
 * Read the entire license text here: http://www.gnu.org/licenses/lgpl.html
 *
 * This file defines helper functions for setting up the calendar.  They are
 * intended to help non-programmers get a working calendar on their site
 * quickly.  This script should not be seen as part of the calendar.  It just
 * shows you what one can do with the calendar, while in the same time
 * providing a quick and simple method for setting it up.  If you need
 * exhaustive customization of the calendar creation process feel free to
 * modify this code to suit your needs (this is recommended and much better
 * than modifying calendar.js itself).
 */
 Calendar.setup=function(params){function param_default(pname,def){if(typeof params[pname]=="undefined"){params[pname]=def;}};param_default("inputField",null);param_default("displayArea",null);param_default("button",null);param_default("eventName","click");param_default("ifFormat","%Y/%m/%d");param_default("daFormat","%Y/%m/%d");param_default("singleClick",true);param_default("disableFunc",null);param_default("dateStatusFunc",params["disableFunc"]);param_default("dateText",null);param_default("firstDay",null);param_default("align","Br");param_default("range",[1900,2999]);param_default("weekNumbers",true);param_default("flat",null);param_default("flatCallback",null);param_default("onSelect",null);param_default("onClose",null);param_default("onUpdate",null);param_default("date",null);param_default("showsTime",false);param_default("timeFormat","24");param_default("electric",true);param_default("step",2);param_default("position",null);param_default("cache",false);param_default("showOthers",false);param_default("multiple",null);var tmp=["inputField","displayArea","button"];for(var i in tmp){if(typeof params[tmp[i]]=="string"){params[tmp[i]]=document.getElementById(params[tmp[i]]);}}if(!(params.flat||params.multiple||params.inputField||params.displayArea||params.button)){alert("Calendar.setup:\n  Nothing to setup (no fields found).  Please check your code");return false;}function onSelect(cal){var p=cal.params;var update=(cal.dateClicked||p.electric);if(update&&p.inputField){p.inputField.value=cal.date.print(p.ifFormat);if(typeof p.inputField.onchange=="function")p.inputField.onchange();}if(update&&p.displayArea)p.displayArea.innerHTML=cal.date.print(p.daFormat);if(update&&typeof p.onUpdate=="function")p.onUpdate(cal);if(update&&p.flat){if(typeof p.flatCallback=="function")p.flatCallback(cal);}if(update&&p.singleClick&&cal.dateClicked)cal.callCloseHandler();};if(params.flat!=null){if(typeof params.flat=="string")params.flat=document.getElementById(params.flat);if(!params.flat){alert("Calendar.setup:\n  Flat specified but can't find parent.");return false;}var cal=new Calendar(params.firstDay,params.date,params.onSelect||onSelect);cal.showsOtherMonths=params.showOthers;cal.showsTime=params.showsTime;cal.time24=(params.timeFormat=="24");cal.params=params;cal.weekNumbers=params.weekNumbers;cal.setRange(params.range[0],params.range[1]);cal.setDateStatusHandler(params.dateStatusFunc);cal.getDateText=params.dateText;if(params.ifFormat){cal.setDateFormat(params.ifFormat);}if(params.inputField&&typeof params.inputField.value=="string"){cal.parseDate(params.inputField.value);}cal.create(params.flat);cal.show();return false;}var triggerEl=params.button||params.displayArea||params.inputField;triggerEl["on"+params.eventName]=function(){var dateEl=params.inputField||params.displayArea;var dateFmt=params.inputField?params.ifFormat:params.daFormat;var mustCreate=false;var cal=window.calendar;if(dateEl)params.date=Date.parseDate(dateEl.value||dateEl.innerHTML,dateFmt);if(!(cal&&params.cache)){window.calendar=cal=new Calendar(params.firstDay,params.date,params.onSelect||onSelect,params.onClose||function(cal){cal.hide();});cal.showsTime=params.showsTime;cal.time24=(params.timeFormat=="24");cal.weekNumbers=params.weekNumbers;mustCreate=true;}else{if(params.date)cal.setDate(params.date);cal.hide();}if(params.multiple){cal.multiple={};for(var i=params.multiple.length;--i>=0;){var d=params.multiple[i];var ds=d.print("%Y%m%d");cal.multiple[ds]=d;}}cal.showsOtherMonths=params.showOthers;cal.yearStep=params.step;cal.setRange(params.range[0],params.range[1]);cal.params=params;cal.setDateStatusHandler(params.dateStatusFunc);cal.getDateText=params.dateText;cal.setDateFormat(dateFmt);if(mustCreate)cal.create();cal.refresh();if(!params.position)cal.showAtElement(params.button||params.displayArea||params.inputField,params.align);else cal.showAt(params.position[0],params.position[1]);return false;};return cal;};
/*
 * Origian code from http://typetester.maratz.com/
 * Modified by Liferay
 */
 
function ColorPicker (src) {
	var cp = document.createElement("div");
	var image = document.createElement("img");
	var body = document.getElementsByTagName("body")[0];
	var textInput = null;
	var originalValue = "";
	var self = this;
	
	cp.id = "color-picker-div";
	cp.style.height = "192px";
	cp.style.width = "100px";
	cp.style.position = "absolute";
	cp.style.display = "none";
	cp.style.cursor= "crosshair";
	cp.style.zIndex = 1;
	
	image.style.height = "192px";
	image.style.width = "100px";
	image.src = src;
	
	cp.appendChild(image);

	body.insertBefore(cp, body.childNodes[0]);
	
	/*
	 * Public methods
	 */
	 
	this.hide = function () {
		cp.style.display = "none";
	}
	
	this.toggle = function (obj) {
		if (cp.style.display == "none") {
			var nwOffset = Coordinates.northwestOffset(obj, true);
			cp.style.left = nwOffset.x + 25 + "px";
			cp.style.top = nwOffset.y + "px";
			cp.style.display = "block";
			// Grab the first input field in the parent node
			textInput = obj.parentNode.getElementsByTagName("INPUT")[0];
			originalValue = textInput.value;
		}
		else {
			self.hide();
		}
	};
	
	
	/*
	 * Private methods
	 */
	 
	var getColor = function (event, obj) {
		var nwOffset = Coordinates.northwestOffset(obj, true);
	
		mousePos.update(event);
	
		var x = mousePos.x - nwOffset.x;
		var y = mousePos.y - nwOffset.y;
	
		var rmax = 0;
		var gmax = 0;
		var bmax = 0;
	
		if (y <= 32) {
			rmax = 255;
			gmax = (y / 32.0) * 255;
			bmax = 0;
		} else if (y <= 64) {
			y = y - 32;
			rmax = 255 - (y / 32.0) * 255;
			gmax = 255;
			bmax = 0;
		} else if (y <= 96) {
			y = y - 64;
			rmax = 0;
			gmax = 255;
			bmax = (y / 32.0) * 255;
		} else if (y <= 128) {
			y = y - 96;
			rmax = 0;
			gmax = 255 - (y / 32.0) * 255;
			bmax = 255;
		} else if (y <= 160) {
			y = y - 128;
			rmax = (y / 32.0) * 255;
			gmax = 0;
			bmax = 255;
		} else {
			y = y - 160;
			rmax = 255;
			gmax = 0;
			bmax = 255 - (y / 32.0) * 255;
		}

		if (x <= 50) {
			var r = Math.abs(Math.floor(rmax * x / 50.0));
			var g = Math.abs(Math.floor(gmax * x / 50.0));
			var b = Math.abs(Math.floor(bmax * x / 50.0));
		} else {
			x -= 50;
			var r = Math.abs(Math.floor(rmax + (x / 50.0) * (255 - rmax)));
			var g = Math.abs(Math.floor(gmax + (x / 50.0) * (255 - gmax)));
			var b = Math.abs(Math.floor(bmax + (x / 50.0) * (255 - bmax)));
		}
	
		return rgb2hex(r, g, b);
	};

	var rgb2hex = function (r, g, b) {
		color = '#';
		color += hex(Math.floor(r / 16));
		color += hex(r % 16);
		color += hex(Math.floor(g / 16));
		color += hex(g % 16);
		color += hex(Math.floor(b / 16));
		color += hex(b % 16);
		return color;
	};

	var hex = function (dec){
		return (dec).toString(16);
	};

	var onEnd = function (event) {
		var color = getColor(event, image);
		originalValue = color;
		textInput.value = color;
		self.hide();
	};

	var onMove = function (event) {
		var color = getColor(event, image);
		textInput.value = color;
		textInput.onchange();
	};

	var reset = function () {
		textInput.value = originalValue;
		textInput.onchange();
	};


	/*
	 * Events
	 */
	 
	image.onmousemove = onMove;
	image.onclick = onEnd;
	image.onmouseout = reset;
}
/**********************************************************
 Very minorly modified from the example by Tim Taylor
 http://tool-man.org/examples/sorting.html
 
 Added Coordinate.prototype.inside( northwest, southeast );
 
 **********************************************************/

var Coordinates = {
	ORIGIN : new Coordinate(0, 0),

	northwestPosition : function(element) {
		var x = parseInt(element.style.left);
		var y = parseInt(element.style.top);

		return new Coordinate(isNaN(x) ? 0 : x, isNaN(y) ? 0 : y);
	},

	southeastPosition : function(element) {
		return Coordinates.northwestPosition(element).plus(
				new Coordinate(element.offsetWidth, element.offsetHeight));
	},

	northwestOffset : function(element, isRecursive) {
		var offset = new Coordinate(element.offsetLeft, element.offsetTop);

		if (!isRecursive) return offset;

		var parent = element.offsetParent;
		while (parent) {
			offset = offset.plus(
					new Coordinate(parent.offsetLeft, parent.offsetTop));
			parent = parent.offsetParent;
		}
		return offset;
	},

	southeastOffset : function(element, isRecursive) {
		return Coordinates.northwestOffset(element, isRecursive).plus(
				new Coordinate(element.offsetWidth, element.offsetHeight));
	},

	fixEvent : function(event) {
		if (typeof event == 'undefined')
    		event = window.event;
    		
		event.windowCoordinate = new Coordinate(event.clientX, event.clientY);
		
		return event;
	}
};

function Coordinate(x, y) {
	this.x = x;
	this.y = y;
}

Coordinate.prototype.toString = function() {
	return "(" + this.x + "," + this.y + ")";
}

Coordinate.prototype.plus = function(that) {
	return new Coordinate(this.x + that.x, this.y + that.y);
}

Coordinate.prototype.minus = function(that) {
	return new Coordinate(this.x - that.x, this.y - that.y);
}

Coordinate.prototype.distance = function(that) {
	var deltaX = this.x - that.x;
	var deltaY = this.y - that.y;

	return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
}

Coordinate.prototype.max = function(that) {
	var x = Math.max(this.x, that.x);
	var y = Math.max(this.y, that.y);
	return new Coordinate(x, y);
}

Coordinate.prototype.constrain = function(min, max) {
	if (min.x > max.x || min.y > max.y) return this;

	var x = this.x;
	var y = this.y;

	if (min.x != null) x = Math.max(x, min.x);
	if (max.x != null) x = Math.min(x, max.x);
	if (min.y != null) y = Math.max(y, min.y);
	if (max.y != null) y = Math.min(y, max.y);

	return new Coordinate(x, y);
}

Coordinate.prototype.reposition = function(element) {
	element.style["top"] = this.y + "px";
	element.style["left"] = this.x + "px";
}

Coordinate.prototype.equals = function(that) {
	if (this == that) return true;
	if (!that || that == null) return false;

	return this.x == that.x && this.y == that.y;
}

// returns true of this point is inside specified box
Coordinate.prototype.inside = function(northwest, southeast) {
	if ((this.x >= northwest.x) && (this.x <= southeast.x) &&
		(this.y >= northwest.y) && (this.y <= southeast.y)) {
		
		return true;
	}
	else {
		return false;
	}
}

// getMousePos(event) has been depricated.  Use mousePos.update(event) instead.
function getMousePos(event) {
    mousePos.update(event);
}

function MousePos () { };

// Extend the "Coordinate" class
MousePos.prototype = new Coordinate(0, 0);

MousePos.prototype.update = function(event) {
	event = Coordinates.fixEvent(event);
	var position = event.windowCoordinate;
	
	this.x = position.x;
	this.y = position.y;

	if (is_safari) {
		// do nothing
	}
	else  {
		this.x += document.body.scrollLeft;
		this.y += document.body.scrollTop;
	}

	if (this.x < 0)
		this.x = 0;

	if (this.y < 0)
		this.y = 0;

	return event;
}

// Track mouse's absolute position (counting scrollbars)
var mousePos = new MousePos(0,0);
/*
 * drag.js - click & drag DOM elements
 *
 * originally based on Youngpup's dom-drag.js, www.youngpup.net
 */

/**********************************************************
 Further modified from the example by Tim Taylor
 http://tool-man.org/examples/sorting.html
 
 Changed onMouseMove where it calls group.onDrag and then
 adjusts the offset for changes to the DOM.  If the item
 being moved changed parents it would be off so changed to
 get the absolute offset (recursive northwestOffset).
 
 Modified Liferay: http://www.liferay.com
 **********************************************************/

var Drag = {
	BIG_Z_INDEX : 100,
	group : null,
	isDragging : false,

	makeDraggable : function(group, handle) {
	    if (handle == null)
    		group.handle = group;
    	else
    		group.handle = handle;
    		
		group.handle.group = group;

		group.minX = null;
		group.minY = null;
		group.maxX = null;
		group.maxY = null;
		group.threshold = 0;
		group.thresholdY = 0;
		group.thresholdX = 0;

		group.onDragStart = new Function();
		group.onDragEnd = new Function();
		group.onDrag = new Function();
		
		// TODO: use element.prototype.myFunc
		group.setDragHandle = Drag.setDragHandle;
		group.setDragThreshold = Drag.setDragThreshold;
		group.setDragThresholdX = Drag.setDragThresholdX;
		group.setDragThresholdY = Drag.setDragThresholdY;
		group.constrain = Drag.constrain;
		group.constrainVertical = Drag.constrainVertical;
		group.constrainHorizontal = Drag.constrainHorizontal;

		group.handle.onmousedown = Drag.onMouseDown;
	},

	constrainVertical : function() {
		var nwOffset = Coordinates.northwestOffset(this, true);
		this.minX = nwOffset.x;
		this.maxX = nwOffset.x;
	},

	constrainHorizontal : function() {
		var nwOffset = Coordinates.northwestOffset(this, true);
		this.minY = nwOffset.y;
		this.maxY = nwOffset.y;
	},

	constrain : function(nwPosition, sePosition) {
	    // Constrain by recursive positions
		this.minX = nwPosition.x;
		this.minY = nwPosition.y;
		this.maxX = sePosition.x;
		this.maxY = sePosition.y;
	},

	setDragHandle : function(handle) {
		if (handle && handle != null) 
			this.handle = handle;
		else
			this.handle = this;

		this.handle.group = this;
		this.onmousedown = null;
		this.handle.onmousedown = Drag.onMouseDown;
	},

	setDragThreshold : function(threshold) {
		if (isNaN(parseInt(threshold))) return;

		this.threshold = threshold;
	},

	setDragThresholdX : function(threshold) {
		if (isNaN(parseInt(threshold))) return;

		this.thresholdX = threshold;
	},

	setDragThresholdY : function(threshold) {
		if (isNaN(parseInt(threshold))) return;

		this.thresholdY = threshold;
	},

	onMouseDown : function(event) {
		event = mousePos.update(event);
		Drag.group = this.group;

		var group = this.group;
		var mouse = mousePos;
		var nwPosition = Coordinates.northwestPosition(group);
		var nwOffset = Coordinates.northwestOffset(group, true);
		var sePosition = Coordinates.southeastPosition(group);
		var seOffset = Coordinates.southeastOffset(group, true);

		group.originalZIndex = group.style.zIndex;
		
		//group.initialWindowCoordinate = mouse;
		
		// TODO: need a better name, but don't yet understand how it
		// participates in the magic while dragging 
		
		//group.dragCoordinate = mouse;
		
		// Offset of the mouse relative to the dragging group
		// This should remain constant.
		group.mouseNwOffset = mouse.minus(nwOffset);
		group.mouseSeOffset = mouse.minus(seOffset);
		group.mouseStart = new Coordinate(mousePos.x, mousePos.y);
		
		//Drag.showStatus(mouse, nwPosition, sePosition, nwOffset, seOffset);

		group.onDragStart(nwPosition, sePosition, nwOffset, seOffset);

		// Constraint coordinates are translated to mouse constraint coordinates.
		// The algorithm below will looks at the bounds of the dragging group and
		// makes sure that no part of it extends outside the constraint bounds.
		var minMouseX;
		var minMouseY;
		var maxMouseX;
		var maxMouseY;
		
		if (group.minX != null)
			minMouseX = group.minX + group.mouseNwOffset.x;
		if (group.minY != null)
			minMouseY = group.minY + group.mouseNwOffset.y;
		if (group.maxX != null) 
			maxMouseX = group.maxX + group.mouseSeOffset.x;
		if (group.maxY != null) 
			maxMouseY = group.maxY + group.mouseSeOffset.y;
			
		if (minMouseX && maxMouseX && minMouseX > maxMouseX)
			maxMouseX = minMouseX;
		if (minMouseY && maxMouseY && minMouseY > maxMouseY)
			maxMouseY = minMouseY;

		group.mouseMin = new Coordinate(minMouseX, minMouseY);
		group.mouseMax = new Coordinate(maxMouseX, maxMouseY);

		document.onmousemove = Drag.onMouseMove;
		document.onmouseup = Drag.onMouseUp;

		return false;
	},

	showStatus : function(mouse, nwPosition, sePosition, nwOffset, seOffset) {
		window.status = 
				"mouse: " + mouse.toString() + "	" + 
				"NW pos: " + nwPosition.toString() + "	" + 
				"SE pos: " + sePosition.toString() + "	" + 
				"NW offset: " + nwOffset.toString() + "	" +
				"SE offset: " + seOffset.toString();
	},

	onMouseMove : function(event) {
		event = mousePos.update(event);
		// Assigning "group" because event is associated with the document
		// and not the dragging obj.  This is for robustness during a drag
		var group = Drag.group;
		var mouse = mousePos;
		var nwOffset = Coordinates.northwestOffset(group, true);
		var nwPosition = Coordinates.northwestPosition(group);
		var sePosition = Coordinates.southeastPosition(group);
		var seOffset = Coordinates.southeastOffset(group, true);

		//Drag.showStatus(mouse, nwPosition, sePosition, nwOffset, seOffset);

		// Automatically scroll the page it drags near the top or bottom
		var scrollZone = 20;
		var scrollSpeed = 5;

		if (!is_safari) {
			if ((document.body.scrollTop + document.body.clientHeight + 2 * scrollZone) < document.body.scrollHeight && mousePos.y > (document.body.scrollTop + document.body.clientHeight - scrollZone)) {
				window.scroll(0, document.body.scrollTop + scrollSpeed);
				nwPosition.y += scrollSpeed;
			}
			if (document.body.scrollTop > 0 && mousePos.y < (document.body.scrollTop + scrollZone)) {
				window.scroll(0, document.body.scrollTop - scrollSpeed);
				nwPosition.y -= scrollSpeed;
			}
		}
		
		var adjusted = mouse.constrain(group.mouseMin, group.mouseMax);
		
		// new-pos = cur-pos + (adj-mouse-pos - mouse-offset - screen-offset)
		//
		//	 new-pos: where we want to position the element using styles
		//	 cur-pos: current styled position of group
		//	 adj-mouse-pos: mouse position adjusted for constraints
		//	 mouse-offset: mouse position relative to the dragging group
		//	 screen-offset: screen position of the current element
		//
		nwPosition = nwPosition.plus(adjusted.minus(nwOffset).minus(group.mouseNwOffset));

		if (!Drag.isDragging) {
			if (group.threshold > 0) {
				var distance = group.mouseStart.distance(mouse);
				if (distance < group.threshold) return true;
			} else if (group.thresholdY > 0) {
				var deltaY = Math.abs(group.mouseStart.y - mouse.y);
				if (deltaY < group.thresholdY) return true;
			} else if (group.thresholdX > 0) {
				var deltaX = Math.abs(group.mouseStart.x - mouse.x);
				if (deltaX < group.thresholdX) return true;
			}

			Drag.isDragging = true;
			group.style["zIndex"] = Drag.BIG_Z_INDEX;
		}

		nwPosition.reposition(group);
		//group.dragCoordinate = adjusted;

		// once dragging has started, the position of the group
		// relative to the mouse should stay fixed.  They can get out
		// of sync if the DOM is manipulated while dragging, so we
		// correct the error here
		//
		// changed to be recursive/use absolute offset for corrections
		var offsetBefore = Coordinates.northwestOffset(group, true);
		group.onDrag(nwPosition, sePosition, nwOffset, seOffset);
		var offsetAfter = Coordinates.northwestOffset(group, true);

		if (!offsetBefore.equals(offsetAfter)) {
			// Position of the group has changed after the onDrag call.
			// Move element to the current mouse position
			var errorDelta = offsetBefore.minus(offsetAfter);
			nwPosition = Coordinates.northwestPosition(group).plus(errorDelta);
			nwPosition.reposition(group);
		}

		return false;
	},

	onMouseUp : function(event) {
		event = mousePos.update(event);
		var group = Drag.group;

		var mouse = event.windowCoordinate;
		var nwOffset = Coordinates.northwestOffset(group, true);
		var nwPosition = Coordinates.northwestPosition(group);
		var sePosition = Coordinates.southeastPosition(group);
		var seOffset = Coordinates.southeastOffset(group, true);

		document.onmousemove = null;
		document.onmouseup   = null;

		group.onDragEnd(nwPosition, sePosition, nwOffset, seOffset);
		Drag.group = null;
		Drag.isDragging = false;

		return false;
	}

};
/**********************************************************
 Adapted from the sortable lists example by Tim Taylor
 http://tool-man.org/examples/sorting.html
 Modified by Tom Westcott : http://www.cyberdummy.co.uk 
 Modified by Liferay: http://www.liferay.com
 **********************************************************/

var DragDrop = {
	firstContainer : null,
	lastContainer : null,
	layoutMaximized : true,
	parent_id : null,
	parent_group : null,
	plid : "",
	portletSpacerName : "portlet-boundary",
	start_next : null,

	makeListContainer : function(list, groupId, enableDragDrop) {
		// each container becomes a linked list node
		if (this.firstContainer == null) {
			this.firstContainer = this.lastContainer = list;
			list.previousContainer = null;
			list.nextContainer = null;
		} else {
			list.previousContainer = this.lastContainer;
			list.nextContainer = null;
			this.lastContainer.nextContainer = list;
			this.lastContainer = list;
		}

		// these functions are called when an item is draged over
		// a container or out of a container bounds.  onDragOut
		// is also called when the drag ends with an item having
		// been added to the container
		list.group = groupId;

		var items = list.childNodes;
		var items2;
		var curItem = null;
		var foundHandle = null;
		var j = 0;
		
		for (var i = 0; i < items.length; i++) {
			curItem = items[i];

			if (curItem.className == DragDrop.portletSpacerName) {
				if (!curItem.isStatic && enableDragDrop) {
					// if it's not static, make it moveable
					items2 = curItem.getElementsByTagName("div");
					items3 = curItem.getElementsByTagName("span");
					foundHandle = null;

					for (j = 0; j < items2.length; j++) {
						if (items2[j].className && items2[j].className.match("portlet-title")) {
							items2[j].style.cursor= "move";
							foundHandle = items2[j];
							break;
						}
					}
					if (!foundHandle) {
						for (j = 0; j < items3.length; j++) {
							if (items3[j].className && items3[j].className.match("portlet-title")) {
								items3[j].style.cursor= "move";
								foundHandle = items3[j];
								break;
							}
						}
					}

					if (foundHandle) {
						DragDrop.makeItemDragable(curItem, foundHandle);
					}
				}
				else if (curItem.isStatic) {

					// otherwise, keep track of the static ones
					if (curItem.isStaticStart) {
						list.startPlaceholder = curItem;
					}

					else if (!list.endPlaceholder && curItem.isStaticEnd) {
						list.endPlaceholder = curItem;
					}
				}
			}
			else if (curItem.className && curItem.className.match("layout-blank")) {
				curItem.isStatic = true;
				curItem.isStaticEnd = true;
				curItem.isStaticStart = false;

				// count the bottom blank portlet as the end placeholder
				if (!list.endPlaceholder) {
					list.endPlaceholder = curItem;
				}
			}
		}
	},

	makeItemDragable : function(item, handle) {
		var portletCont = item.getElementsByTagName("div")[0];
		Drag.makeDraggable(portletCont, handle);
		portletCont.setDragThreshold(5);
		
		// tracks if the item is currently outside all containers
		portletCont.isOutside = false;
		
		portletCont.onDragStart = DragDrop.onDragStart;
		portletCont.onDrag = DragDrop.onDrag;
		portletCont.onDragEnd = DragDrop.onDragEnd;
	},

	onDragStart : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		// update all container bounds, since they may have changed
		// on a previous drag
		// could be more smart about when to do this
		var container = DragDrop.firstContainer;
		while (container != null) {
			container.className = "layout-column-highlight";
			container.northwest = Coordinates.northwestOffset( container, true );
			container.southeast = Coordinates.southeastOffset( container, true );
			container = container.nextContainer;
		}
		
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		// item starts out over current parent
		DragDrop.parent_id = curContainer.id;
		DragDrop.parent_group = curContainer.group;
		// Keep track of original neighbor, so it can "snap" back later
		DragDrop.start_next = DragUtils.nextItem(portletBound);
		
		portletCont.id = "portlet-dragging";
	},

	onDrag : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		// This is point of interest when checking for "inside" and "outside"
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		var hotPoint = nwOffset.plus(portletCont.mouseNwOffset);
		
		if (portletBound.className.search("portlet-dragging-placeholder") < 0) {
			portletBound.className += " portlet-dragging-placeholder";
		}
		
		// check if we were nowhere
		
		if (portletCont.isOutside) {
			// check each container to see if in its bounds
			var container = DragDrop.firstContainer;
			while (container != null) {

				if (hotPoint.inside( container.northwest, container.southeast )) {
					// we're inside this one
					portletCont.isOutside = false;

					// since isOutside was true, the current parent is this
					// original starting container.  Remove it and add it to
					// it's new parent (if different).
					if (container != curContainer) {
						portletBound.parentNode.removeChild(portletBound);
						container.insertBefore(portletBound, container.endPlaceholder);
						// Update boundaries
						container.southeast = Coordinates.southeastOffset( container, true );
					}
					break;
				}
				container = container.nextContainer;
			}
			// we're still not inside the bounds of any container
		}
		else if (!hotPoint.inside( curContainer.northwest, curContainer.southeast )) {
			// We're outside our parent's bounds
			portletCont.isOutside = true;
			
			// check if we're inside a new container's bounds
			var container = DragDrop.firstContainer;
			while (container != null) {
				if (hotPoint.inside( container.northwest, container.southeast )) {
					// we're inside this one
					portletCont.isOutside = false;
					curContainer.removeChild( portletBound );
					container.insertBefore(portletBound, container.endPlaceholder);
					// Update boundaries
					container.southeast = Coordinates.southeastOffset( container, true );
					break;
				}
				container = container.nextContainer;
			}
			// if we're not in any container now,
			// add it back to it's original position.
			if (portletCont.isOutside) {
				portletBound.parentNode.removeChild(portletBound);
				container = document.getElementById(DragDrop.parent_id);
				container.insertBefore( portletBound, DragDrop.start_next );
			}
		}
		
		if (!portletCont.isOutside) {
			// We're inside some container bounds,
			// so swap contianer into the correct position
			
			var count = 0;
			
			var curOffsetTop = portletCont.offsetTop + portletCont.mouseNwOffset.y;
			var swapped = false;
			var downSearch;
			var neighbor = null;
			
			if (Coordinates.northwestPosition(portletCont).y > 0) {
				neighbor = DragUtils.nextItem(portletBound);
				downSearch = true;
			}
			else {
				neighbor = DragUtils.previousItem(portletBound);
				downSearch = false;
			}
			
			while (neighbor != null) {
				swapped = false;
				var divList = neighbor.getElementsByTagName("div");
				if (divList.length > 0 &&
					neighbor.className &&
					neighbor.className.match(DragDrop.portletSpacerName)) {

					if (neighbor.isStatic) {
						break;
					}

					var neighborOffsetMid = divList[0].offsetTop + neighbor.offsetHeight/2;

					if (downSearch && curOffsetTop > neighborOffsetMid) {
						DragUtils.swap(neighbor, portletBound);
						swapped = true;
					}
					else if (!downSearch && curOffsetTop < neighborOffsetMid) {
						DragUtils.swap(portletBound, neighbor);
						swapped = true;
					}
				}
				if (downSearch)
					neighbor = DragUtils.nextItem(swapped ? portletBound : neighbor);
				else
					neighbor = DragUtils.previousItem(swapped ? portletBound : neighbor);
			}
		}
	},

	onDragEnd : function(nwPosition, sePosition, nwOffset, seOffset) {
	
		var portletCont = this;
		var portletBound = portletCont.parentNode;
		var curContainer = portletBound.parentNode;
		var offsetBefore = Coordinates.northwestOffset(portletCont, true);
		
		// Remove dragging style
		portletCont.id = "dragging_" + portletBound.portletId;
		portletBound.className = DragDrop.portletSpacerName;
			
		// if the drag ends and we're still outside all containers
		// it's time to remove ourselves from the document or add 
		// to the trash bin
		if (this.isOutside) {
			reelHome(portletCont.id, parseInt(portletCont.style.left), parseInt(portletCont.style.top), 15);
			return;
		}

		var position = 0;
		var positionFound = false;
		var container = DragDrop.firstContainer;
		var curContainer = null;
		var items = null;

		// Find the new position of the portlet
		while (container != null) {
			if (!positionFound) {
				position = 0;
				items = container.childNodes;
				for (var i=0; i<items.length; i++) {
					if (items[i].className == DragDrop.portletSpacerName) {
						if (items[i].portletId == portletBound.portletId) {
							positionFound = true;
							curContainer = container;
							break;
						}
						if (!items[i].isStatic)
							position++;
					}
				}
			}
			container.className = "";
			container = container.nextContainer;
		}
		
		reelHome(portletCont.id, parseInt(portletCont.style.left), parseInt(portletCont.style.top), 15);

		movePortlet(DragDrop.plid, portletBound.portletId, curContainer.columnId, position);
	},
	
	snapBack : function(obj, offsetBefore, offsetAfter) {
		if (!offsetBefore.equals(offsetAfter)) {
			var errorDelta = offsetBefore.minus(offsetAfter);
			var nwPosition = Coordinates.northwestPosition(obj).plus(errorDelta);
			nwPosition.reposition(obj);
		}

		// snapping animation
		reelHome(obj.id, parseInt(obj.style.left), parseInt(obj.style.top), 25);
	}
	
};

var DragUtils = {
	swap : function(item1, item2) {
	//swap item1 up before item 2
		var parent = item1.parentNode;
		parent.removeChild(item1);
		parent.insertBefore(item1, item2);

		//item1.style["top"] = "0px";
		//item1.style["left"] = "0px";
	},

	nextItem : function(item) {
		var sibling = item.nextSibling;
		while (sibling != null) {
			if (sibling.nodeName == item.nodeName) return sibling;
			sibling = sibling.nextSibling;
		}
		return null;
	},

	previousItem : function(item) {
		var sibling = item.previousSibling;
		while (sibling != null) {
			if (sibling.nodeName == item.nodeName) return sibling;
			sibling = sibling.previousSibling;
		}
		return null;
	}		
};
var LayoutConfiguration = {
	categories : new Array(),
	imagePath : "",
	initialized : false,
	loadingImage : null,
	menu : null,
	menuDiv : null,
	menuIframe : null,
	portlets : new Array(),
	showTimer : 0,
	
	init : function (xmlHttpReq) {
		var addDiv = document.createElement("div");
		var arrow1 = new Image();
		var arrow2 = new Image();
		arrow1.src = this.imagePath + "/arrows/01_down.gif";
		arrow2.src = this.imagePath + "/arrows/01_right.gif";
		
		var body = document.getElementsByTagName("body")[0];
		
		if (this.loadingImage) {
			body.removeChild(this.loadingImage);
			this.loadingImage = null;
		}
		
		addDiv.style.textAlign = "left";
		addDiv.style.zIndex = "9";
		addDiv.style.position = "relative";
		addDiv.innerHTML = xmlHttpReq.responseText;
		body.insertBefore(addDiv, body.childNodes[0]);
		this.menu = document.getElementById("portal_add_content");
		
		if (this.menu != null) {
			var list = this.menu.childNodes;
			
			for (var i = 0; i < list.length; i++) {
				if (list[i].className != null && list[i].className.match("portal-add-content")) {
					this.menuDiv = list[i];
				}
				if (list[i].nodeName != null && list[i].nodeName.toLowerCase().match("iframe")) {
					this.menuIframe = list[i];
				}
			}

			var elems = this.menu.getElementsByTagName("div");

			for (var i = 0; i < elems.length; i++) {
				if (elems[i].className == "layout_configuration_portlet") {
					this.portlets.push(elems[i]);
				}
				else if (elems[i].className == "layout_configuration_category") {
					this.categories.push(elems[i]);
				}
			}

			Drag.makeDraggable(this.menu);
			this.initialized = true;
			this.toggle();
			
			// Double foucus for IE bug
			if (is_ie) {
				document.getElementById("layout_configuration_content").focus();
			}
		}
	},
	
	toggle : function (ppid, plid,  mainPath, imagePath) {
		var menu = document.getElementById("portal_add_content");
		
		if (!this.initialized) {
			this.imagePath = imagePath
			this.loadingImage = document.createElement("div");
			var image = document.createElement("img");
			
			this.loadingImage.className = "portal-add-content";
			this.loadingImage.style.position = "absolute";
			this.loadingImage.style.top = document.getElementsByTagName("body")[0].scrollTop + "px";
			this.loadingImage.style.left = "0";
			this.loadingImage.style.zIndex = "9";
			
			image.src = this.imagePath + "/progress_bar/loading_animation.gif";
			this.loadingImage.appendChild(image);
			document.getElementsByTagName("body")[0].appendChild(this.loadingImage);
			
			loadPage(mainPath + "/portal/render_portlet", "p_p_id=" + ppid + "&p_l_id=" + plid, LayoutConfiguration.returnPortlet);
		}
		else {
			if (this.menu.style.display == "none") {
				yPos = document.getElementsByTagName("body")[0].scrollTop;
				this.menu.style["top"] = yPos + "px";
				this.menu.style.display = "block";
				this.menu.style.zIndex = "9";
				this.resize();

				document.getElementById("layout_configuration_content").focus();
			}
			else {
				this.menu.style.display = "none";
			}
		}
	},
	
	resize : function () {
		if (this.menuIframe != null) {
			this.menuIframe.height = this.menuDiv.offsetHeight;
			this.menuIframe.width = this.menuDiv.offsetWidth;
		}
		if (!is_ie) {
			document.getElementById("layout_configuration_content").focus();
		}
	},
	
	returnPortlet : function (xmlHttpReq) {
		LayoutConfiguration.init(xmlHttpReq);
	},
	
	startShowTimer : function (word) {
		if (this.showTimer) {
			clearTimeout(this.showTimer);
			this.showTimer = 0;
		}

		this.showTimer = setTimeout("LayoutConfiguration.showMatching(\"" + word + "\")", 250);
	},
	
	showMatching : function (word) {
		var portlets = this.portlets;
		var categories = this.categories;

		if (word == "*") {
			for (var i = 0; i < portlets.length; i++) {
				portlets[i].style.display = "block";
			}

			for (var i = 0; i < categories.length; i++) {
				categories[i].style.display = "block";
				this.toggleCategory(categories[i].getElementsByTagName("table")[0], "block");
			}
		}
		else if (word == "") {
			for (var i = 0; i < categories.length; i++) {
				categories[i].style.display = "block";
				this.toggleCategory(categories[i].getElementsByTagName("table")[0], "none");
			}
			for (var i = 0; i < portlets.length; i++) {
				portlets[i].style.display = "block";
			}
		}
		else {
			word = word.toLowerCase();

			for (var i = 0; i < categories.length; i++) {
				categories[i].style.display = "none";
			}

			for (var i = 0; i < portlets.length; i++) {
				if (portlets[i].id.toLowerCase().match(word)) {
					portlets[i].style.display = "block";

					this.showCategories(categories, portlets[i].id);
				}
				else {
					portlets[i].style.display = "none";
				}
			}
		}
		
		this.resize();
	},

	showCategories : function (categories, name) {
		var colon = name.lastIndexOf(":");

		while (colon != -1) {
			name = name.substr(0, colon);

			for (var i = 0; i < categories.length; i++) {
				if (categories[i].id.match(name)) {
					categories[i].style.display = "block";
					this.toggleCategory(categories[i].getElementsByTagName("table")[0], "block");
				}
			}

			colon = name.lastIndexOf(":");
		}
	},

	toggleCategory : function (obj, display) {
		var parent = obj;
		
		while(parent.nodeName.toLowerCase() != "table") {
			parent = parent.parentNode;
		}
		
		var data = parent.rows[1].cells[0];
		var pane = getElementByClassName(data, "layout_configuration_category_pane");
		var image = obj.getElementsByTagName("img")[0];
		
		if (display) {
			pane.style.display = display;
			if (display.toLowerCase().match("block")) {
				image.src = this.imagePath + "/arrows/01_down.gif";
			}
			else {
				image.src = this.imagePath + "/arrows/01_right.gif";
			}
		}
		else {
			if (toggleByObject(pane, true)) {
				image.src = this.imagePath + "/arrows/01_down.gif";
			}
			else {
				image.src = this.imagePath + "/arrows/01_right.gif";
			}
		}
	}

};
