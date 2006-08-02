function MailSummaryObject(sender, subject, date, id, read) {
	this.next = null;
	this.prev = null;
	this.selected = false;
	this.id = id;
	this.index = 0;
	this.read = read;
	this.head = sender;
	this.pendingHighlight = false;
	sender.next = subject;
	sender.parent = this;
	subject.next = date;
	subject.parent = this;
	date.parent = this;
	
	sender.onmousedown = subject.onmousedown = date.onmousedown = Mail.onSummaryMouseDown;
	sender.ondblclick = subject.ondblclick = date.ondblclick = Mail.onSummaryDblclick;
}



var Mail = {
	colorHighlight : "#c3d4ee",
	currentFolder : null,
	currentFolderId : "",
	currentMessage : null,
	currentMessageId : null,
	dragging : false,
	dragIndicator : null,
	dragStart : null,
	foldersList : null,
	groupStart : null,
	lastSelected : null,
	messageTimer : null,
	sortBy : null,
	summaryList : { head : null, tail : null },
		
	checkFolderLocation : function(coord, update) {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		var foundInside = false;
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			
			if (update == true) {
				folderItem.nwOffset = Coordinates.northwestOffset(folderItem, true);
				folderItem.seOffset = Coordinates.southeastOffset(folderItem, true);
			}
			
			if (Mail.isMoveAllowed(folderItem.folder.id)) {
				if (coord.inside(folderItem.nwOffset, folderItem.seOffset)) {
						folderItem.style.backgroundColor = Mail.colorHighlight;
						foundInside = true;
				}
				else {
					folderItem.style.backgroundColor = "transparent";
				}
			}
		}
		
		if (Mail.dragIndicator != null) {
			var indicator = Mail.dragIndicator.getElementsByTagName("span")[0];
			if (foundInside) {
				indicator.innerHTML = "&laquo;";
				indicator.style.color = "#55FF55";
			}
			else {
				indicator.innerHTML = "X";
				indicator.style.color = "#FF5555";
			}
		}
	},
	
	clearPreview : function() {
		if (!Mail.summaryList) {
			return;
		}
		
		var msgsSender = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsDate = document.getElementById("portlet-mail-msgs-received");
		
		Mail.summaryList.head = null;
		Mail.summaryList.tail = null;
		msgsSender.innerHTML = "";
		msgsSubject.innerHTML = "";
		msgsDate.innerHTML = "";
	},

	decrementCount : function (reverse) {
		var spanList = Mail.currentFolder.li.getElementsByTagName("span");
		
		if (spanList.length > 0) {
			spanList[0].parentNode.removeChild(spanList[0]);
			
			if (reverse) {
				Mail.currentFolder.li.newCount++;
			}
			else {
				Mail.currentFolder.li.newCount--;
			}
			
			var countNum = Mail.currentFolder.li.newCount;
			
			if (countNum > 0) {
				var element = document.createElement("span");
				element.innerHTML = "&nbsp;(" + countNum + ")";
				element.className = "font-small";
				Mail.currentFolder.li.appendChild(element);
			}
		}
	},
	
	deleteSelectedMessages : function() {
		clearTimeout(Mail.messageTimer);
		
		var deleteList = Mail.getSelectedMessages();
		var confirmMsg = "Delete " + deleteList.length + " message" +
			(deleteList.length > 1 ? "s" : "") + " from " + Mail.currentFolder.name + "?";
		
		if (deleteList.length > 0 && confirm(confirmMsg)) {
			loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=deleteMessages&folderId=" + Mail.currentFolder.id + "&messages=" + deleteList);
			Mail.removeSelectedMessages();
		}
	},	
	
	dragToFolder : function(coord) {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			var foundFolder = coord.inside(
					Coordinates.northwestOffset(folderItem, true),
					Coordinates.southeastOffset(folderItem, true));
					
			if (foundFolder) {
				Mail.moveToFolder(folderItem.folder.id);
			}
		}
	},

	resetLastSelected : function() {
		Mail.lastSelected = null;
		Mail.groupStart = null;
	},
	
	removeSelectedMessages : function() {
		var detailsFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		detailsFrame.src = "";
		
		Mail.getSelectedMessages(Mail.removeSummary);
		Mail.resetLastSelected();
		Mail.getFolderDetails();
	},
	
	removeSummary : function(msObj) {
		var	field = msObj.head;
		var nextMs = msObj.next;
		var prevMs = msObj.prev;
			
		while (field) {
			var nextField = field.next;
			field.parentNode.removeChild(field);
			field.onmousedown = null;
			field.next = null;
			field = null;
			field = nextField;
		}
		
		if (nextMs != null) {
			nextMs.prev = prevMs;
		}
		if (prevMs != null) {
			prevMs.next = nextMs;
		}
		
		if (Mail.summaryList.tail == msObj) {
			Mail.summaryList.tail = prevMs;
		}
		if (Mail.summaryList.head == msObj) {
			Mail.summaryList.head = nextMs;
		}
		
		msObj = null;
	},
	
	submitCompose : function(action, form) {
		var selList = Mail.getSelectedMessages();
		
		if (selList > 0) {
			document.getElementById("portlet-mail-compose-action").value = action;
			document.getElementById("portlet-mail-message-id").value = Mail.currentMessageId;
			document.getElementById("portlet-mail-folder-id").value = Mail.currentFolder.id;

			submitForm(form);
		}
		else {
			alert("Please select a message");
		}
	},
	
	getFolderDetails : function() {
		var detailsFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var mailHeader = document.getElementById("portlet-mail-msg-header-div");
		var folderDiv = document.createElement("div");
		var totalDiv = document.createElement("div");
		var unreadDiv = document.createElement("div");
		
		folderDiv.innerHTML = Mail.currentFolder.name;
		folderDiv.style.fontWeight = "bold";
		folderDiv.className = "font-xx-large";
		if (Mail.currentFolder.newCount > 0) {
			unreadDiv.innerHTML = Mail.currentFolder.newCount + "&nbsp;Unread";
		}
		totalDiv.innerHTML = Mail.currentFolder.totalCount + "&nbsp;Total";

		detailsFrame.src = "";
		mailHeader.innerHTML = "";
		mailHeader.appendChild(folderDiv);
		mailHeader.appendChild(unreadDiv);
		mailHeader.appendChild(totalDiv);
	},
	
	getFolders : function() {
		loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=getFolders", Mail.getFoldersReturn);
	},
	
	getFoldersReturn : function(xmlHttpReq) {
		var foldersObject = eval("(" + xmlHttpReq.responseText + ")");
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = document.createElement("ul");
		var folders = foldersObject.folders;
		var selectedFolder = null;
		Mail.foldersList = folders;
		
		var animation = folderPane.getElementsByTagName("div")[0];
		animation.parentNode.removeChild(animation);
		
		if (folders.length > 0) {
			selectedFolder = folders[0];
			
			for (var i = 0; i < folders.length; i++) {
				var folder = folders[i];
				var folderItem = document.createElement("li");
				var newCount = document.createElement("span");
				
				if (folder.newCount > 0) {
					newCount.innerHTML = "&nbsp;(" + folder.newCount + ")";
				}
				newCount.className = "font-small"
				folderItem.innerHTML = folder.name;
				folderItem.folder = folder;
				folderItem.onclick = Mail.onFolderSelect;
				folderItem.newCount = folder.newCount;
				folderItem.appendChild(newCount);
				folderList.appendChild(folderItem);
				
				if (folder.id == Mail.currentFolderId) {
					/* Previous folder ID was set */
					selectedFolder = folder;
				}
			}
			
			folderPane.appendChild(folderList);
			
			Mail.setCurrentFolder(selectedFolder);
		}
		
		Mail.getPreview();
	},
	
	getMessageDetails : function(messageId) {
		
		if (!Mail.currentMessage || messageId != Mail.currentMessageId) {
			loadPage(themeDisplay.getPathMain() + "/mail/action",
				"cmd=getMessage&messageId=" + messageId + "&folderId=" + Mail.currentFolder.id,
				Mail.getMessageDetailsReturn, messageId);
				
		}
	},
	
	getMessageDetailsReturn : function(xmlHttpReq, messageId) {
		
		var messageObj = eval("(" + xmlHttpReq.responseText + ")");
		var mailHeader = document.getElementById("portlet-mail-msg-header-div");
		var tempBody = document.createElement("div");
		var msgHeader = document.createElement("div");
		
		Mail.currentMessage = messageObj;
		Mail.currentMessageId = messageId;
		
		msgHeader.innerHTML = messageObj.header;
		
		mailHeader.innerHTML = "";
		mailHeader.appendChild(msgHeader);
		
		if (!Mail.lastSelected.read) {
			Mail.decrementCount();
			
			var summaryField = Mail.lastSelected.head;

			while (summaryField) {
				summaryField.style.fontWeight = "normal";
				summaryField = summaryField.next;
			}
		
			Mail.lastSelected.read = true;
		}

		var iframe = document.getElementById("portlet-mail-msg-detailed-frame");

		iframe.src = "";
		iframe.src = themeDisplay.getPathMain() + "/mail/view_message?noCache=" + (new Date()).getTime();
		return;
	},
	
	getPreview : function () {
		loadPage(themeDisplay.getPathMain() + "/mail/action",
			"cmd=getPreview&folderId=" + Mail.currentFolder.id +
			"&sortBy=" + Mail.sortBy.value +
			"&asc=" + Mail.sortBy.asc,
			Mail.getPreviewReturn);
			
	},
	
	getPreviewReturn : function(xmlHttpReq) {
		var mailObject = eval("(" + xmlHttpReq.responseText + ")");
		var msgsSender = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsDate = document.getElementById("portlet-mail-msgs-received");
		
		for (var i = 0; i < mailObject.headers.length; i++) {
			var header = mailObject.headers[i];
			var sender = document.createElement("div");
			var subject = document.createElement("div");
			var date = document.createElement("div");
			
			sender.innerHTML = header.email;
			subject.innerHTML = header.subject;
			date.innerHTML = header.date;
			var msObj = new MailSummaryObject(sender, subject, date, header.id, header.read);
			var summaryList = Mail.summaryList;
			
			if (!header.read) {
				/* Bold unread messages */
				sender.style.fontWeight = "bold";
				subject.style.fontWeight = "bold";
				date.style.fontWeight = "bold";
			}
			
			msObj.index = i;
			
			/* Create doubly linked list */
			if (summaryList.head == null) {
				summaryList.head = msObj;
				summaryList.tail = summaryList.head;
			}
			else {
				summaryList.tail.next = msObj;
				msObj.prev = summaryList.tail;
				summaryList.tail = msObj;
			}
			
			msgsSender.appendChild(sender);
			msgsSubject.appendChild(subject);
			msgsDate.appendChild(date);
			
			if (Mail.currentMessageId == msObj.id) {
				/* Previous highlight state was set */
				Mail.summaryHighlight(msObj);
				Mail.lastSelected = msObj;
			}
		}
	},
	
	getSelectedMessages : function(processFunction) {
		var msObj = this.summaryList.head;
		var msgArray = new Array();
		var count = 0;
		var nextMs;
	
		while (msObj) {
			nextMs = msObj.next;
			if (msObj.selected) {
				
				if (processFunction) {
					processFunction(msObj);
				}
				msgArray[count] = msObj.id;
				count++;
			}
			msObj = nextMs;
		}	
		return(msgArray);
	},
	
	init : function() {
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderHandle = document.getElementById("portlet-mail-handle");
		var msgsPane = document.getElementById("portlet-mail-msgs-pane");
		
		var previewPane = document.getElementById("portlet-mail-msgs-preview-pane");
		var previewHandle = document.getElementById("portlet-mail-msgs-handle");
		var detailedPane = document.getElementById("portlet-mail-msg-detailed-pane");
		var detailedFrame = document.getElementById("portlet-mail-msg-detailed-frame");
		var msgHeader = document.getElementById("portlet-mail-msg-header");
		
		var msgsTitleFrom = document.getElementById("portlet-mail-msgs-title-from");
		var msgsTitleFromHandle = document.getElementById("portlet-mail-msgs-from-handle");
		var msgsTitleSubject = document.getElementById("portlet-mail-msgs-title-subject");
		var msgsTitleSubjectHandle = document.getElementById("portlet-mail-msgs-subject-handle");
		var msgsTitleReceived = document.getElementById("portlet-mail-msgs-title-received");
		
		var msgsFrom = document.getElementById("portlet-mail-msgs-from");
		var msgsSubject = document.getElementById("portlet-mail-msgs-subject");
		var msgsReceived = document.getElementById("portlet-mail-msgs-received");
		
		var mainMailGroup = Resize.createHandle(folderHandle);
		mainMailGroup.addRule(new ResizeRule(folderPane, Resize.HORIZONTAL, Resize.ADD));
		mainMailGroup.addRule(new ResizeRule(previewPane, Resize.HORIZONTAL, Resize.SUBTRACT));
		mainMailGroup.addRule(new ResizeRule(detailedPane, Resize.HORIZONTAL, Resize.SUBTRACT));
		mainMailGroup.addRule(new ResizeRule(msgHeader, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		var msgsGroup = Resize.createHandle(previewHandle);
		msgsGroup.addRule(new ResizeRule(previewPane, Resize.VERTICAL, Resize.ADD));
		msgsGroup.addRule(new ResizeRule(detailedFrame, Resize.VERTICAL, Resize.SUBTRACT));
		
		var fromGroup = Resize.createHandle(msgsTitleFromHandle);
		fromGroup.addRule(new ResizeRule(msgsTitleFrom, Resize.HORIZONTAL, Resize.ADD));
		fromGroup.addRule(new ResizeRule(msgsFrom, Resize.HORIZONTAL, Resize.ADD));
		fromGroup.addRule(new ResizeRule(msgsTitleSubject, Resize.HORIZONTAL, Resize.SUBTRACT));
		fromGroup.addRule(new ResizeRule(msgsSubject, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		var subjectGroup = Resize.createHandle(msgsTitleSubjectHandle);
		subjectGroup.addRule(new ResizeRule(msgsTitleSubject, Resize.HORIZONTAL, Resize.ADD));
		subjectGroup.addRule(new ResizeRule(msgsSubject, Resize.HORIZONTAL, Resize.ADD));
		subjectGroup.addRule(new ResizeRule(msgsTitleReceived, Resize.HORIZONTAL, Resize.SUBTRACT));
		subjectGroup.addRule(new ResizeRule(msgsReceived, Resize.HORIZONTAL, Resize.SUBTRACT));
		
		msgsTitleFrom.asc = true;
		msgsTitleFrom.value = "name";
		msgsTitleSubject.asc = true;
		msgsTitleSubject.value = "subject";
		msgsTitleReceived.asc = false;
		msgsTitleReceived.value = "date";
		msgsTitleFrom.onclick = msgsTitleSubject.onclick = msgsTitleReceived.onclick = Mail.onSortClick;
		Mail.sortBy = msgsTitleReceived;
		Mail.updateSortArrow();
		
		if (is_ie) {
			previewPane.onkeydown = Mail.onMailKeyPress;
		}
		else {
			document.onkeydown = Mail.onMailKeyPress;
		}
		previewPane.onselectstart = function() {return false;} // ie
		previewPane.onmousedown = function() {return false;} // mozilla

		window.unload = function() { Mail.clearPreview; }
		
		Mail.getFolders();
	},
	
	isMoveAllowed : function (toFolderId) {
		/* Mail cannot be moved to the same folder
		 * Mail cannot be moved to Drafts
		 * Drafts can only be move to Trash
		 * NOTE: checkFolderLocation need same exceptions.
		 */
		 
		if (Mail.currentFolderId == toFolderId ||
			toFolderId == "Drafts" ||
			(Mail.currentFolderId == "Drafts" && toFolderId != "Trash")) {
			
			return false;
		}
		else {
			return true;
		}
	},

	moveToFolder : function(folderId) {
		var moveList = Mail.getSelectedMessages();
		
		if (moveList.length <- 0) {
			alert("Please select messages to move");
		}
		else if (!Mail.isMoveAllowed(folderId)) {
			alert("You cannot move to " + folderId);
		}
		else {
			confirmMsg = "Move " + moveList.length + " message" +
				(moveList.length > 1 ? "s" : "") + " to " + folderId + "?";

			if (confirm(confirmMsg)) {
				Mail.removeSelectedMessages();
				loadPage(themeDisplay.getPathMain() + "/mail/action", "cmd=moveMessages&folderId=" + folderId + "&messages=" + moveList);
			}
			
			Mail.resetLastSelected();
		}
		
	},
	
	onFolderSelect : function() {
		if (Mail.currentFolder.id != this.folder.id) {
			Mail.currentMessage = null;
			Mail.currentMessageId = null;
			
			Mail.setCurrentFolder(this.folder);
			Mail.clearPreview();
			Mail.getPreview();
		}
	},
	
	onMailKeyPress : function(event) {
		var Key = {
			SHIFT	: 16,
			ESC		: 27,
			UP		: 38,
			DOWN	: 40,
			DELETE	: 46,
			A		: 65
		}
		
		if (!event) {
			event = window.event;
		}
		/*
		event.altKey
		event.ctrlKey
		event.shiftKey 
		*/

		var keycode = event.keyCode
		
		if ((keycode == Key.UP || keycode == Key.DOWN) &&
			 Mail.summaryList.head != null) {
			 
			
			var lastObj = Mail.lastSelected;
			var nextObj;
			
			if (Mail.lastSelected == null) {
				Mail.lastSelected = Mail.summaryList.head;
				nextObj = Mail.lastSelected;
			}
			else if (keycode == Key.DOWN) {
				nextObj = lastObj.next;
			}
			else if (keycode == Key.UP) {
				nextObj = lastObj.prev;
			}
			
			if (nextObj) {
				if (!event.shiftKey) {
					Mail.summaryUnhighlightAll();
					Mail.summaryHighlight(nextObj, event.shiftKey);
				}
				else {
					if (nextObj.index <= Mail.groupStart.index && keycode == Key.DOWN) {
						Mail.summaryUnhighlight(Mail.lastSelected, true);
					}
					if (nextObj.index >= Mail.groupStart.index && keycode == Key.UP) {
						Mail.summaryUnhighlight(Mail.lastSelected, true);
					}
					
					Mail.summaryHighlight(nextObj, event.shiftKey);
				}
				Mail.groupStart = Mail.lastSelected;
			}
		}
		else if (keycode == Key.DELETE) {
			Mail.deleteSelectedMessages();
		}
		else if (keycode == Key.ESC) {
			Mail.summaryUnhighlightAll();
			Mail.getFolderDetails();
		}
		else if (event.ctrlKey) {
			if (keycode == Key.A) {
				Mail.summaryHighlightAll();
				return false;
			}
		}
	},
	
	onMessageSelect : function() {
		alert(this.id);
	},
	
	onMoveFolderChange : function() {
		if (this.selectedIndex != 0) {
			Mail.moveToFolder(this.options[this.selectedIndex].innerHTML);
		}
		
		this.selectedIndex = 0;
	},
	
	onSortClick : function() {
		if (Mail.sortBy == this) {
			this.asc = this.asc ? false : true;
		}
	
		Mail.sortBy = this;
		Mail.clearPreview();
		Mail.getPreview();
		Mail.updateSortArrow();
	},
	
	onSummaryDblclick : function() {
		msObj = this.parent;
		if (Mail.currentMessage && (Mail.currentMessage.id == msObj.id )){
			Mail.previewPopup();
		}
	},
	
	onSummaryMouseDown : function(event) {
		event = mousePos.update(event);
		var obj = this;
		var msObj = obj.parent;
		
		msObj.pendingHighlight = true;
		Mail.lastSelected = msObj;
		
		document.onmousemove = Mail.onSummaryMouseMove;
		document.onmouseup = Mail.onSummaryMouseUp;
		
		Mail.dragStart = new Coordinate(mousePos.x, mousePos.y);
		Mail.checkFolderLocation(mousePos, true);
		return false;
	},
	
	onSummaryMouseMove : function(event) {
		mousePos.update(event);
		
		var numOfSelected = Mail.getSelectedMessages().length;
		
		if ((numOfSelected > 0 && mousePos.distance(Mail.dragStart) > 20) || Mail.dragging) {
			Mail.dragging = true;
			Mail.lastSelected.pendingHighlight = false;
			
			var dragIndicator = Mail.dragIndicator;
			
			if (dragIndicator == null) {
				dragIndicator = document.createElement("div");
				document.getElementsByTagName("body")[0].appendChild(dragIndicator);
				dragIndicator.id = "portlet-mail-drag-indicator";
				dragIndicator.onselectstart = function() {return false;};
				dragIndicator.onmousedown = function() {return false;};
				Mail.dragIndicator = dragIndicator;
			}
			
			dragIndicator.innerHTML = "<span>&nbsp;</span>&nbsp;" + numOfSelected + " message" +
				(numOfSelected != 1 ? "s" : "");
			dragIndicator.style.display = "block";
			dragIndicator.style.top = (mousePos.y - 15) + "px";
			dragIndicator.style.left = (mousePos.x - 5) + "px";
			
			Mail.checkFolderLocation(mousePos);
		}
	},
	
	onSummaryMouseUp : function(event) {
		event = mousePos.update(event);
		
		var dragIndicator = Mail.dragIndicator;
		if (dragIndicator != null) {
			dragIndicator.style.display = "none";
		}
		
		if (Mail.lastSelected.pendingHighlight) {
			if (!event.ctrlKey && !event.shiftKey) {
				Mail.summaryUnhighlightAll();
			}
			
			if (event.ctrlKey && Mail.lastSelected.selected) {
				Mail.summaryUnhighlight(Mail.lastSelected, event.ctrlKey);
				Mail.resetLastSelected();
			}
			else {
				Mail.summaryHighlight(Mail.lastSelected, event.ctrlKey);
				if (!event.shiftKey) {
					Mail.groupStart = Mail.lastSelected;
				}
			}
			
			if (event.shiftKey && Mail.groupStart != null) {
				var nextObj;
				var searchDown = true;
				var lastSelected = Mail.lastSelected;
				
				if (Mail.lastSelected.index > Mail.groupStart.index) {
					searchDown = false;
				}
				
				nextObj = searchDown ? Mail.lastSelected.next : Mail.lastSelected.prev;
				
				while (nextObj != Mail.groupStart) {
					Mail.summaryHighlight(nextObj, event.shiftKey);
					nextObj = searchDown ? nextObj.next : nextObj.prev;
				}
				
				Mail.lastSelected = lastSelected;
			}
		}
		
		document.onmousemove = null;
		document.onmouseup = null;
		
		Mail.checkFolderLocation(new Coordinate());
		
		if (Mail.dragging) {
			Mail.dragToFolder(mousePos);
		}
		
		Mail.dragging = false;
	},
	
	print : function() {
		var printWindow = Mail.previewPopup();
			
		if (printWindow != null && printWindow.print) {
			printWindow.print();
		}
		else {
			alert("Please select a message");
		}
	},
	
	previewPopup : function() {
		var popup = null;
		
		if (Mail.currentMessage) {
			var frameSrc = themeDisplay.getPathMain() + "/mail/view_message?header=true&messageId=" + Mail.currentMessageId;
			popup = window.open(frameSrc, "Print", "menubar=yes,width=640,height=480,toolbar=no,resizable=yes,scrollbars=yes");
		}
		
		return popup;
	},
	
	setCurrentFolder : function(folder) {
		Mail.currentFolder = folder;
		Mail.currentFolderId = folder.id;
		Mail.getFolderDetails();
		
		var folderPane = document.getElementById("portlet-mail-folder-pane");
		var folderList = folderPane.getElementsByTagName("li");
		
		for (var i = 0; i < folderList.length; i++) {
			var folderItem = folderList[i];
			
			if (Mail.currentFolder.id == folderItem.folder.id) {
				folderItem.style.backgroundColor = Mail.colorSelected;
				Mail.currentFolder.li = folderItem;
			}
			else {
				folderItem.style.backgroundColor = "transparent";
			}
		}

		var fromTitleText = document.getElementById("portlet-mail-msgs-title-from");
		var fromTitle = fromTitleText.getElementsByTagName("span");
		var mainToolbar = document.getElementById("portlet-mail-main-toolbar");
		var draftsToolbar = document.getElementById("portlet-mail-drafts-toolbar");
		
		if (Mail.currentFolder.id == "Sent" || Mail.currentFolder.id == "Drafts") {
			fromTitle[0].style.display = "none";
			fromTitle[1].style.display = "";
		}
		else {
			fromTitle[0].style.display = "";
			fromTitle[1].style.display = "none";
		}
		
		if (Mail.currentFolder.id == "Drafts") {
			mainToolbar.style.display = "none";
			draftsToolbar.style.display = "block";
		}
		else {
			mainToolbar.style.display = "block";
			draftsToolbar.style.display = "none";
		}
	},
	
	summaryHighlight : function(msObj, modifierOn, setOff) {
		var summaryField = msObj.head;
		var setColor = "transparent";
		
		msObj.pendingHighlight = false;
		
		if (Mail.messageTimer) {
			clearTimeout(Mail.messageTimer);
		}
		
		if (setOff == true) {
			msObj.selected = false;
		}
		else {
			msObj.selected = true;
			setColor = Mail.colorHighlight;
			Mail.lastSelected = msObj;
			
			/* Don't display details if modifier key was pressed */
			if (!modifierOn) {
				Mail.messageTimer = setTimeout("Mail.getMessageDetails("+msObj.id+")", 500);
			}
		}
		
		while (summaryField) {
			summaryField.style.backgroundColor = setColor;
			summaryField = summaryField.next;
		}
	},
	
	summaryHighlightAll : function(setOff) {
		var msObj = Mail.summaryList.head;
		
		while (msObj) {
			if (setOff) {
				Mail.summaryUnhighlight(msObj);
			}
			else {
				Mail.summaryHighlight(msObj, true);
			}
			msObj = msObj.next;
		}
	},
	
	summaryUnhighlight : function(msObj, modifierOn) {
		Mail.summaryHighlight(msObj, modifierOn, true);
	},
	
	summaryUnhighlightAll : function() {
		this.summaryHighlightAll(true);
	},
	
	updateSortArrow : function() {
		var sortTitles = new Array();
		sortTitles[0] = document.getElementById("portlet-mail-msgs-title-from");
		sortTitles[1] = document.getElementById("portlet-mail-msgs-title-subject");
		sortTitles[2] = document.getElementById("portlet-mail-msgs-title-received");
		
		for (var i = 0; i < sortTitles.length; i++) {
			var title = sortTitles[i];
			var titleDiv = title.getElementsByTagName("div")[0];
			var imageList = titleDiv.getElementsByTagName("img");
			var image;
			
			if (imageList.length > 0) {
				image = imageList[0];
				titleDiv.removeChild(image);
			}
			
			if (Mail.sortBy == title) {
				image = document.createElement("img");
				
				if (title.asc) {
					image.src = themeDisplay.getPathThemeImage() + "/arrows/01_up.gif";
				}
				else {
					image.src = themeDisplay.getPathThemeImage() + "/arrows/01_down.gif";
				}
				
				titleDiv.appendChild(image);
			}
		}
	}
}
