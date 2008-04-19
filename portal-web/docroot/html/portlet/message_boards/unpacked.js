if (!Liferay.Editor.bbCode) {
	Liferay.Editor.bbCode = new Class({
		initialize: function(options) {
			var instance = this;
			options = options || {};
			
			instance._textarea = jQuery(options.textarea);
			instance._location = jQuery(options.location || []);

			// Language keys
			instance._emailPrompt = options.emailPrompt || Liferay.Language.get('enter-an-email-address');
			instance._emailNamePrompt = options.emailNamePrompt || Liferay.Language.get('enter-a-name-associated-with-the-email');
			
			instance._fontTypeText = options.fontTypeText || Liferay.Language.get('font');
			instance._fontSizeText = options.fontSizeText || Liferay.Language.get('size');
			
			instance._imagePathPrompt = options.imagePathPrompt || Liferay.Language.get('enter-a-address-for-a-picture');
			
			instance._listPrompt = options.listPrompt || Liferay.Language.get('enter-a-list-item-click-cancel-or-leave-blank-to-end-the-list');
			
			instance._urlHrefPrompt = options.urlHrefPrompt || Liferay.Language.get('enter-a-address');
			instance._urlTitlePrompt = options.urlTitlePrompt || Liferay.Language.get('enter-a-title-for-the-link');

			//Create the buttons
			instance._createEmoticons();
			instance._createToolbar();

			if (options.onLoad) {
				options.onLoad();
			}
			
		},
		
		
		_createEmoticons: function() {
			var instance = this;

			var xHR = jQuery.ajax(
				{
					url: themeDisplay.getPathMain() + '/portal/emoticons',
					async: false
				}
			);

			var response = xHR.responseText;

			var emoticonsContainer = jQuery('<div class="lfr-emoticon-container"></div>').appendTo('body');
			instance._emoticons = emoticonsContainer.append(response);

			instance._emoticons.find('.emoticon').click(
				function(event) {
					var emoticonCode = this.getAttribute('emoticonCode');
					if (emoticonCode) {
						instance._insertEmoticon(emoticonCode);
					}
				}
			);
		},
		
		_createToolbar: function() {
			var instance = this;
			
			var html = '';

			instance._buttons = {
				fontType: {
					options: [instance._fontTypeText, 'Arial', 'Comic Sans', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana', 'Wingdings'],
					onChange: function(event) {
						var value = this[this.selectedIndex].value;
						if (value != instance._fontTypeText) {
							instance._insertTag('font', this[this.selectedIndex].value);
							this.selectedIndex = 0;							
						}
					}
				},

				fontSize: {
					options: [instance._fontSizeText, 1, 2, 3, 4, 5, 6, 7],
					onChange: function(event) {
						var value = this[this.selectedIndex].value;
						if (value != instance._fontSizeText) {
							instance._insertTag('size', this[this.selectedIndex].value);							
							this.selectedIndex = 0;							
						}
					},
					groupEnd: true
				},

				b: {
					text: 'bold',
					image: 'message_boards/bold.png',
					onClick: function(event) {
						instance._insertTag('b');
					}
				},

				i: {
					text: 'italic',
					image: 'message_boards/italic.png',
					onClick: function(event) {
						instance._insertTag('i');
					}
				},

				u: {
					text: 'underline',
					image: 'message_boards/underline.png',
					onClick: function(event) {
						instance._insertTag('u');
					}
				},

				s: {
					text: 'strikethrough',
					image: 'message_boards/strike.png',
					onClick: function(event) {
						instance._insertTag('s');
					}
				},

				fontColor: {
					className: 'use-color-picker',
					text: 'font-color',
					image: 'message_boards/color.png',
					groupEnd: true
				},

				url: {
					text: 'url',
					image: 'message_boards/hyperlink.png',
					onClick: function(event) {
						instance._insertURL();
					}
				},

				email: {
					text: 'email',
					image: 'message_boards/email.png',
					onClick: function(event) {
						instance._insertEmail();
					}
				},

				image: {
					text: 'image',
					image: 'message_boards/image.png',
					onClick: function(event) {
						instance._insertImage();
					}
				},

				ol: {
					text: 'ordered-list',
					image: 'message_boards/ordered_list.png',
					onClick: function(event) {
						instance._insertList(true);
					}
				},

				ul: {
					text: 'unordered-list',
					image: 'message_boards/unordered_list.png',
					onClick: function(event) {
						instance._insertList(true);
					}
				},

				left: {
					text: 'left',
					image: 'message_boards/justify_left.png',
					onClick: function(event) {
						instance._insertTag('left');
					}
				},

				center: {
					text: 'center',
					image: 'message_boards/justify_center.png',
					onClick: function(event) {
						instance._insertTag('center');
					}
				},

				right: {
					text: 'right',
					image: 'message_boards/justify_right.png',
					onClick: function(event) {
						instance._insertTag('right');
					}
				},

				indent: {
					text: 'indent',
					image: 'message_boards/indent.png',
					onClick: function(event) {
						instance._insertTag('indent');
					}
				},

				quote: {
					text: 'quote',
					image: 'message_boards/quote.png',
					onClick: function(event) {
						instance._insertTag('quote');
					}
				},

				code: {
					text: 'code',
					image: 'message_boards/code.png',
					onClick: function(event) {
						instance._insertTag('code');
					}
				},

				emoticons: {
					text: 'emoticons',
					image: 'emoticons/smile.gif'					
				}
			};

			jQuery.each(
				instance._buttons,
				function(i, n) {
					var buttonClass = ' ' + (this.className || '');
					var buttonText = this.text || '';

					if (i != 'insert' && !this.options) {
						var imagePath = themeDisplay.getPathThemeImages() + '/' + this.image;

						html += '<a buttonId="' + i + '" class="lfr-button ' + buttonClass + '" href="javascript: ;" title="' + buttonText + '">' +
								'<img alt="' + buttonText + '" buttonId="' + i + '" src="' + imagePath + '" >' +
								'</a>';
					}
					else if (this.options && this.options.length) {
						html += '<select class="' + buttonClass + '" selectId="' + i + '" title="' + buttonText + '">';
						jQuery.each(
							this.options,
							function(i, v) {
								html += '<option value="' + v + '">' + v + '</option>';
							}
						);
						html += '</select>';
					}

					if (this.groupEnd) {
						html += '<span class="lfr-separator"></span>';
					}
				}
			);

			if (!instance._location.length) {
				instance._location = jQuery('<div class="lfr-toolbar">' + html + '</div>');
				instance._textarea.before(instance._location);
			}
			else {
				instance._location.html(html);
			}

			var emoticonButton = instance._location.find('.lfr-button[@buttonId=emoticons]');
			var hoveringOver = false;
			var offsetHeight = 0;
			var offsetWidth = 0;
			var boxWidth = 0;

			emoticonButton.hoverIntent(
				{
					interval: 0,
					timeout: 250,
					over: function(event) {
						var offset = emoticonButton.offset({lite: true});

						if (offsetHeight == 0) {
							offsetHeight = this.offsetHeight;
						}
						
						if (offsetWidth == 0) {
							offsetWidth = this.offsetWidth;
						}

						instance._emoticons.show();

						if (boxWidth == 0) {
							boxWidth = instance._emoticons.width();
						}

						var left = offset.left - (boxWidth - offsetWidth);
						var top = offset.top + offsetHeight;

						instance._emoticons.css(
							{
								left: left,
								top: top
							}
						);
					},
					out: function(event) {console.log(hoveringOver);
						if (!hoveringOver) {
							instance._emoticons.hide();
						}

						// return false;
					}
				}
			);

			instance._emoticons.hoverIntent(
				{
					interval: 0,
					timeout: 250,	
					over: function(event) {
						hoveringOver = true;
						instance._emoticons.show();
					},
					out: function(event) {
						instance._emoticons.hide();
						hoveringOver = false;
					}			
				}
			);
			
			// Bugfix for Firefox attached mouse(over|out) events and textareas
			if (Liferay.Browser.is_firefox) {
				var emoticonDiv = instance._emoticons[0];
				var intent;

				emoticonDiv.onmouseover = function(event) {
					if (intent) {
						clearTimeout(intent);	
					}
				};

				emoticonDiv.onmouseout = function(event) {
					intent = setTimeout(
						function() {
							instance._emoticons.hide();
						},
						250
					);
				};
			}

			instance._location.click(
				function(event) {
					var target = event.target;
					var buttonId = event.target.getAttribute('buttonId');

					if (buttonId && instance._buttons[buttonId].onClick) {
						instance._buttons[buttonId].onClick.apply(target, [event]);
					}
				}
			);
			
			var selects = instance._location.find('select');
			selects.change(
				function(event) {
					var selectId = this.getAttribute('selectId');
					
					if (selectId && instance._buttons[selectId].onChange) {
						instance._buttons[selectId].onChange.apply(this, [event]);
					}
				}
			);
			
			instance._fontColorInput = jQuery('<input type="hidden" val="" />');
			
			instance._location.find('.use-color-picker').before(instance._fontColorInput);
			
			var colorPicker = new Liferay.ColorPicker(
				{
					hasImage: true,
					onClose: function() {
						instance._insertColor();
					}
				}
			);
		},
		
		_insertColor: function() {
			var instance = this;

			var color = instance._fontColorInput.val();
			instance._insertTag('color', color);
		},

		_insertEmail: function() {
			var instance = this;

			var addy = prompt(instance._emailPrompt, '');

			if (addy) {
				var name = prompt(instance._emailPrompt, '');

				instance._resetSelection();

				if (!name) {
					name = addy;
					addy = null;
				}

				instance._insertTag('email', addy, name);
			}
		},

		_insertEmoticon: function(emoticon) {
			var instance = this;

			var textarea = instance._textarea;
			var field = textarea[0];
			
			textarea.trigger('focus');

			if (Liferay.Browser.is_ie) {
				field.focus();

				var sel = document.selection.createRange();

				sel.text = emoticon;
			}
			else if (field.selectionStart || field.selectionStart == "0") {
				var startPos = field.selectionStart;
				var endPos = field.selectionEnd;

				var preSel = field.value.substring(0, startPos);
				var postSel = field.value.substring(endPos, field.value.length);

				field.value = preSel + emoticon + postSel;
			}
			else {
				field.value += emoticon;
			}
		},

		_insertImage: function() {
			var instance = this;

			var url = prompt( instance._imagePathPrompt + ':', 'http://');

			if (url) {
				instance._resetSelection();
				instance._insertTag('img', null, url);
			}
		},

		_insertList: function(ordered) {
			var instance = this;

			var list = "\n";
			var entry;

			while (entry = prompt(instance._listPrompt, '')) {
				if (!entry) {
					break;
				}

				list += '[*]' + entry + '\n';
			}

			if (list != '\n') {
				instance._resetSelection();
				instance._insertTag('list', ordered, list);
			}
		},
		
		
		_insertURL: function() {
			var instance = this;

			var url = prompt(instance._urlHrefPrompt + ':', 'http://');

			if (url != null) {
				var title = prompt(instance._urlTitlePrompt, ':');

				if (title) {
					instance._resetSelection();
					instance._insertTag('url', url, title);
				}
				else {
					instance._insertTag('url', url);
				}
			}
		},

		_insertTag: function(tag, param, content) {
			var instance = this;

			var begTag;

			if (param) {
				begTag = '[' + tag + '=' + param + ']';
			}
			else {
				begTag = '[' + tag + ']';
			}

			var endTag = '[/' + tag + ']';

			var textarea = instance._textarea;
			var field = textarea[0];
			var value = textarea.val();

			textarea.trigger('focus');

			if (Liferay.Browser.is_ie) {
				var sel = document.selection.createRange();

				if (content != null) {
					sel.text = begTag + content + endTag;
				}
				else {
					sel.text = begTag + sel.text + endTag;
				}
				sel.moveEnd('character', -endTag.length);
				sel.select();
			}
			else if (field.selectionStart || field.selectionStart == 0) {

				var startPos = field.selectionStart;
				var endPos = field.selectionEnd;

				var preSel = value.substring(0, startPos);
				var sel = value.substring(startPos, endPos);
				var postSel = value.substring(endPos, field.value.length);
				
				var caretPos = startPos + begTag.length;

				if (content != null) {
					field.value = preSel + begTag + content + endTag + postSel;
				}
				else {
					field.value = preSel + begTag + sel + endTag + postSel;
					field.setSelectionRange(caretPos, caretPos);
				}
			}
			else {
				field.value += begTag + content + endTag;
			}
		},

		_resetSelection: function() {
			var instance = this;

			var textarea = instance._textarea;
			var field = textarea[0];

			if (Liferay.Browser.is_ie) {
				field.focus();

				var sel = document.selection.createRange();

				sel.collapse(false);
				sel.select();
			}
			else if (field.selectionStart) {
				field.selectionEnd = field.selectionStart;
			}
		},
		
		_setHTML: function(content) {
			var instance = this;

			instance._textarea.val(content);
		}
		
	});
}