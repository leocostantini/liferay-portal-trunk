Liferay.Portlet.TagsAdmin = new Class({
	initialize: function() {
		var instance = this;

		var childrenContainer = jQuery(instance._entryScopeClass);
		instance._container = jQuery('.vocabulary-container');

		jQuery('.vocabulary-close').click(
			function() {
				instance._unselectAllEntries();
				instance._closeEditSection();
			}
		);

		jQuery('.vocabulary-save-properties').click(
			function() {
				instance._saveProperties();
			}
		);

		instance._portletMessageContainer = jQuery('<div class="lfr-message-response" id="vocabulary-messages" />');
		instance._entryMessageContainer = jQuery('<div class="lfr-message-response" id="vocabulary-entry-messages" />');

		instance._portletMessageContainer.hide();
		instance._entryMessageContainer.hide();

		instance._container.before(instance._portletMessageContainer);
		childrenContainer.before(instance._entryMessageContainer);

		var buttons = jQuery('.vocabulary-buttons')
		var toolbar = jQuery('.vocabulary-toolbar');

		var changeAddLabel = function(label) {
			label = Liferay.Language.get(label);
			toolbar.find('.vocabulary-label').html(label);
		}

		var selectButton = function(button) {
			buttons.find('.button').removeClass('selected');
			jQuery(button).addClass('selected');
		};

		buttons.find('.tags-sets').click(
			function() {
				instance._selectedVocabulary = 'tag';
				changeAddLabel('add-tag');
				selectButton(this);
				instance._loadData();
			}
		);

		buttons.find('.categories').click(
			function() {
				instance._selectedVocabulary = 'category';
				changeAddLabel('add-category');
				selectButton(this);
				instance._loadData();
			}
		);

		jQuery('select.vocabulary-select-list').change(
			function() {
				var actionScope = jQuery('.vocabulary-actions');
				var vocabularyId = jQuery(this).val();
				var vocabularyName = jQuery(this).find('option:selected').text();
				var inputVocabularyName = actionScope.find('.vocabulary-name');

				if (vocabularyName == "(new)") {
					inputVocabularyName.show().focus();
				}
				else {
					instance._resetActionValues();
				}
			}
		);

		jQuery('#vocabulary-search-bar').change(
			function() {
				jQuery('#vocabulary-search-input').focus();
				instance._reloadSearch();
			}
		);

		var addEntry = function() {
			var actionScope = jQuery('.vocabulary-actions');
			var entryName = actionScope.find('.vocabulary-entry-name').val();
			var vocabularyId = actionScope.find('.vocabulary-select-list').val();
			var vocabularyName = actionScope.find('.vocabulary-select-list option:selected').text();
			var inputVocabularyName = actionScope.find('.vocabulary-name');

			instance._hideAllMessages();

			var newVocabulary = inputVocabularyName.val();

			if (newVocabulary) {
				instance._addVocabulary(
					newVocabulary,
					function() {
						instance._addEntry(entryName, newVocabulary);
					}
				);

				return;
			}

			instance._addEntry(entryName, vocabularyName);
		};

		jQuery('input.vocabulary-save-entry').click(addEntry);

		jQuery('.vocabulary-actions input').keyup(
			function(event) {
				if (event.keyCode == 13) {
					addEntry();

					return false;
				}
			}
		);

		jQuery('input.vocabulary-delete-entries-button').click(
			function () {
				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-entry'))) {
					instance._deleteEntry(
						instance._selectedEntryId,
						function() {
							instance._closeEditSection();
							instance._displayVocabularyEntries(instance._selectedVocabularyName);
						}
					);
				}
			}
		);

		jQuery('input.vocabulary-delete-list-button').click(
			function () {
				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-list'))) {
					instance._deleteVocabulary(
						instance._selectedVocabularyId,
						function() {
							instance._closeEditSection();
							instance._loadData();
						}
					);
				}
			}
		);

		instance._loadData();
	},

	_displayCategoriesVocabularyEntries: function(entries, callback) {
		var instance = this;

		var buffer = [];
		var childrenList = jQuery(instance._entryScopeClass);

		var treeOptions = {
			sortOn: 'li',
			dropOn: 'span.folder',
			dropHoverClass: 'hover-folder',
			drop: function(event, ui) {
				ui.droppable = jQuery(this).parent();
				instance._merge(event, ui);

				var categoryTree = jQuery('#vocabulary-treeview');

				setTimeout(
					function() {
						categoryTree.find(':not(span)').removeClass();
						categoryTree.find('div').remove();
						categoryTree.removeData('toggler');
						categoryTree.treeview();
				}, 100);
			}
		};

		buffer.push('<div class="vocabulary-treeview-container lfr-component"><ul id="vocabulary-treeview" class="filetree">');
		instance._buildCategoryTreeview(entries, buffer, 0);
		buffer.push('</ul></div>');

		childrenList.html(buffer.join(''));

		instance._reloadSearch();

		var categoryTree = jQuery('#vocabulary-treeview');
		var	entryList = jQuery(instance._entryListClass);

		entryList.click(
			function(event) {
				var entryId = instance._getEntryId(this);
				var editContainer = jQuery('.vocabulary-edit');

				instance._selectEntry(entryId);
				instance._showSection(editContainer);

				event.stopPropagation();
			}
		);

		categoryTree.treeview().tree(treeOptions);

		var list = jQuery(instance._vocabularyScopeClass);
		var listLinks = jQuery('li', list);
		var treeScope = categoryTree.data('tree').identifier;

		listLinks.droppable(
			{
				accept: '.vocabulary-category-item',
				tolerance: 'pointer',
				hoverClass:	'active-area',
				scope: treeScope,
				cssNamespace: false,
				drop: function(event, ui) {
					ui.droppable = jQuery(this);
					instance._merge(event, ui);
				}
			}
		);

		if (callback) {
			callback();
		}
	},

	_displayFolksonomiesVocabularyEntries: function(entries, callback) {
		var instance = this;

		var buffer = [];
		var childrenList = jQuery(instance._entryScopeClass);

		buffer.push('<ul>');

		jQuery.each(
			entries,
			function(i) {
				buffer.push('<li class="vocabulary-item results-row" ');
				buffer.push('data-entry="');
				buffer.push(this.name);
				buffer.push('" data-entryId="');
				buffer.push(this.entryId);
				buffer.push('"><span><a href="javascript: ;">');
				buffer.push(this.name);
				buffer.push('</a></span>');
				buffer.push('</li>');
			}
		);

		buffer.push('</ul>');

		if (!entries.length) {
			buffer = [];
			instance._sendMessage('info', 'no-entries-were-found', '#vocabulary-entry-messages', true);
		}

		childrenList.html(buffer.join(''));

		instance._reloadSearch();

		var	entryList = jQuery(instance._entryListClass);

		entryList.mousedown(
			function() {
				var entryId = instance._getEntryId(this);
				var editContainer = jQuery('.vocabulary-edit');

				instance._selectEntry(entryId);
				instance._showSection(editContainer);
			}
		);

		entryList.draggable(
			{
				appendTo: 'body',
				cssNamespace: false,
				cursor: 'move',
				distance: 3,
				ghosting: false,
				helper: function(event, ui) {
					var drag = jQuery(this);
					var width = drag.width();

					return drag.clone().css(
						{
							width: width
						}
					);
				},
				opacity: 0.7,
				scope: 'vocabulary-item-scope',
				scroll: 'auto',
				zIndex: 1000
			}
		);

		entryList.droppable(
			{
				accept: '.vocabulary-item',
				cssNamespace: false,
				drop: function(event, ui) {
					ui.droppable = jQuery(this);
					instance._merge(event, ui);
				},
				hoverClass:	'active-area',
				scope: 'vocabulary-item-scope',
				tolerance: 'pointer'
			}
		);

		instance._alternateRows();

		if (callback) {
			callback();
		}
	},

	_displayList: function(folksonomy, callback) {
		var instance = this;

		var buffer = [];
		var list = jQuery(instance._vocabularyScopeClass);

		instance._showLoading('.vocabulary-entries, .vocabulary-list');

		buffer.push('<ul>');

		instance._getVocabularies(
			folksonomy,
			function(vocabularies) {
				jQuery.each(
					vocabularies,
					function(i) {
						buffer.push('<li');
						buffer.push(' class="vocabulary-category results-row');

						if (i == 0){
							buffer.push(' selected ');
						}

						buffer.push('" data-vocabulary="');
						buffer.push(this.name);
						buffer.push('" data-vocabularyId="');
						buffer.push(this.vocabularyId);
						buffer.push('"><span><a href="javascript: ;">');
						buffer.push(this.name);
						buffer.push('</a></span>');
						buffer.push('</li>');
					}
				);

				buffer.push('</ul>');

				list.html(buffer.join(''));

				var firstVocabulary = jQuery(instance._vocabularyListClass + ':first');
				var vocabularyName = instance._getVocabularyName(firstVocabulary);
				var vocabularyId = instance._getVocabularyId(firstVocabulary);

				instance._selectedVocabularyName = vocabularyName;
				instance._selectedVocabularyId = vocabularyId;
				instance._feedVocabularySelect(vocabularies, vocabularyId);

				var listLinks = jQuery('li', list);

				listLinks.mousedown(
					function(event) {
						var vocabularyId = instance._getVocabularyId(this);
						instance._selectVocabulary(vocabularyId);
					}
				);

				listLinks.droppable(
					{
						accept: '.vocabulary-item',
						cssNamespace: false,
						drop: function(event, ui) {
							ui.droppable = jQuery(this);
							instance._merge(event, ui);
						},
						hoverClass:	'active-area',
						scroll: 'auto',
						scope: 'vocabulary-item-scope',
						tolerance: 'pointer'
					}
				);

				jQuery('li span a', list).editable(
					function(value, settings) {
						var vocabularyName = value;
						var vocabularyId = instance._selectedVocabularyId;
						var folksonomy = (instance._selectedVocabulary == 'tag');
						var li = jQuery(this).parents('li:first');

						li.attr('data-vocabulary', value);
						instance._updateVocabulary(vocabularyId, vocabularyName, folksonomy);

						return value;
					},
					{
						cssclass: 'vocabulary-edit-vocabulary',
						data: function(value, settings) {
							// var instance = this;

							return value;
						},

						height: '15px',
						width: '200px',
						onblur: 'ignore',
						submit: Liferay.Language.get('save'),
						select: false,
						type: 'text',
						event: 'dblclick'
					}
				);

				if (callback) {
					callback();
				}
			}
		);
	},

	_displayProperties: function(entryId) {
		var instance = this;

		instance._getProperties(
			entryId,
			function(properties) {
				if (!properties.length){
					properties = [{ key: '', value: '' }];
				}

				var total = properties.length;
				var totalRendered = jQuery('div.vocabulary-property-row').length;

				if (totalRendered > total) {
					return;
				}

				jQuery.each(
					properties,
					function() {
						var baseProperty = jQuery('div.vocabulary-property-row:last');

						instance._addProperty(baseProperty, this.key, this.value);
					}
				);
			}
		);
	},

	_displayVocabularyEntries: function(vocabulary, callback) {
		var instance = this;

		jQuery('#vocabulary-entry-messages').hide();

		instance._getVocabularyEntries(
			vocabulary,
			function(entries) {
				if (!instance._selectedVocabulary || instance._selectedVocabulary == 'tag') {
					instance._displayFolksonomiesVocabularyEntries(entries, callback);
				}

				if (instance._selectedVocabulary == 'category') {
					instance._displayCategoriesVocabularyEntries(entries, callback);
				}
			}
		);
	},

	_addEntry: function(entryName, vocabulary, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsEntry.addEntry(
			{
				groupId: themeDisplay.getGroupId(),
				name: entryName,
				properties: [],
				vocabulary: vocabulary
			},
			function(message) {
				var exception = message.exception;

				if (!exception && message.entryId) {
					instance._sendMessage('success', 'your-request-processed-successfully');

					instance._selectVocabulary(message.vocabularyId);

					instance._displayVocabularyEntries(
						instance._selectedVocabularyName,
						function() {
							var entry = instance._selectEntry(message.entryId);

							if (entry.length){
								jQuery(instance._entryScopeClass).scrollTo(entry);
							}

							instance._showSection('.vocabulary-edit');
						}
					);

					instance._resetActionValues();

					if (callback) {
						callback(entryName, vocabulary);
					}
				}
				else {
					var errorKey = '';

					if (exception.indexOf('DuplicateEntryException') > -1) {
						errorKey = 'that-tag-already-exists';
					}
					else if (exception.indexOf('EntryNameException') > -1) {
						errorKey = 'one-of-your-fields-contain-invalid-characters';
					}
					else if (exception.indexOf('NoSuchVocabularyException') > -1) {
						errorKey = 'that-vocabulary-does-not-exists';
					}

					if (errorKey) {
						instance._sendMessage('error', errorKey);
					}
				}
			}
		);
	},

	_addProperty: function(baseNode, key, value) {
		var instance = this;

		var baseProperty = jQuery('div.vocabulary-property-row:last');
		var newProperty = baseProperty.clone();

		newProperty.find('.property-key').val(key);
		newProperty.find('.property-value').val(value);
		newProperty.insertAfter(baseNode);
		newProperty.show();

		if (!key && !value) {
			newProperty.find('input:first').addClass('lfr-auto-focus');
		}

		instance._attachPropertyIconEvents(newProperty);
	},

	_addVocabulary: function(vocabulary, callback) {
		var instance = this;

		var folksonomy = (instance._selectedVocabulary == 'tag');

		Liferay.Service.Tags.TagsVocabulary.addVocabulary(
			{
				groupId: themeDisplay.getGroupId(),
				name: vocabulary,
				folksonomy: folksonomy
			},
			function(message) {
				var exception = message.exception;

				if (!message.exception) {
					instance._sendMessage('success', 'your-request-processed-successfully');

					instance._displayList(
						folksonomy,
						function() {
							var vocabulary = instance._selectVocabulary(message.vocabularyId);
							instance._displayVocabularyEntries(instance._selectedVocabularyName);

							if (vocabulary.length) {
								jQuery(instance._vocabularyScopeClass).scrollTo(vocabulary);
							}
						}
					);

					jQuery('.vocabulary-actions .vocabulary-name').hide();

					if (callback) {
						callback(vocabulary);
					}
				}
				else {
					var errorKey = '';

					if (exception.indexOf('DuplicateVocabularyException') > -1) {
						errorKey = 'that-vocabulary-already-exists';
					}
					else if (exception.indexOf('VocabularyNameException') > -1) {
						errorKey = 'one-of-your-fields-contain-invalid-characters';
					}
					else if (exception.indexOf('NoSuchVocabularyException') > -1) {
						errorKey = 'that-parent-vocabulary-does-not-exist';
					}

					if (errorKey) {
						instance._sendMessage('error', errorKey);
					}
				}
			}
		);
	},

	_alternateRows: function() {
		var instance = this;

		var entriesScope = jQuery(instance._entryScopeClass);

		jQuery('li', entriesScope).removeClass('alt');
		jQuery('li:odd', entriesScope).addClass('alt');
	},

	_attachPropertyIconEvents: function(property) {
		var instance = this;

		var row = jQuery(property);

		row.find('.add-property').click(
			function() {
				instance._addProperty(property, '', '');
			}
		);

		row.find('.delete-property').click(
			function() {
				instance._removeProperty(property);
			}
		);
	},

	_buildCategoryTreeview: function(entries, buffer, parentId) {
		var instance = this;

		var children = instance._filterCategory(entries, parentId);

		jQuery.each(
			children,
			function(i) {
				var entryId = this.entryId;
				var name = this.name;
				var hasChild = instance._filterCategory(entries, entryId).length;

				buffer.push('<li');
				buffer.push(' class="vocabulary-category-item"');
				buffer.push(' data-entry="');
				buffer.push(this.name);
				buffer.push('" data-entryId="');
				buffer.push(this.entryId);

				buffer.push('"><span class="folder">');
				buffer.push(name);
				buffer.push('</span>');

				if (hasChild) {
					buffer.push('<ul>');

					instance._buildCategoryTreeview(entries, buffer, entryId);

					buffer.push('</ul>');
				}

				buffer.push('</li>');
			}
		);

		return children.length;
	},

	_buildProperties: function() {
		var instance = this;

		var buffer = [];

		jQuery('.vocabulary-property-row:visible').each(
			function(i, o) {
				var propertyRow = jQuery(this);
				var key = propertyRow.find('input.property-key').val();
				var value = propertyRow.find('input.property-value').val();
				var rowValue = ['0', ':', key, ':', value, ','].join('');

				buffer.push(rowValue);
			}
		);

		return buffer.join('');
	},

	_closeEditSection: function() {
		var instance = this;

		instance._hideSection('.vocabulary-edit');
	},

	_deleteEntry: function(entryId, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsEntry.deleteEntry(
			{
				entryId: entryId
			},
			callback
		);
	},

	_deleteVocabulary: function(vocabularyId, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsVocabulary.deleteVocabulary(
			{
				entryId: vocabularyId
			},
			callback
		);
	},

	_feedVocabularySelect: function(vocabularies, defaultValue) {
		var instance = this;

		var select = jQuery('select.vocabulary-select-list');
		var buffer = ['<option value="0"></option>', '<option value="0">(new)</option>'];

		jQuery.each(
			vocabularies,
			function(i) {
				var selected = (this.vocabularyId == defaultValue);

				buffer.push('<option');
				buffer.push(selected ? ' selected ' : '');
				buffer.push(' value="');
				buffer.push(this.vocabularyId);
				buffer.push('">');
				buffer.push(this.name);
				buffer.push('</option>');
			}
		);

		select.html(buffer.join(''));
	},

	_filterCategory: function(entries, parentId) {
		var instance = this;

		return jQuery.grep(
			entries,
			function(item, i) {
				return (item.parentEntryId == parentId);
			}
		);
	},

	_getEntry: function(entryId) {
		var instance = this;

		return jQuery('li[data-entryId=' + entryId + ']')
	},

	_getEntryId: function(exp) {
		var instance = this;

		return jQuery(exp).attr('data-entryId');
	},

	_getEntryName: function(exp) {
		var instance = this;

		return jQuery(exp).attr('data-entry');
	},

	_getProperties: function(entryId, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsProperty.getProperties(
			{
				entryId: entryId
			},
			callback
		);
	},

	_getVocabularies: function(folksonomy, callback) {
		var instance = this;

		Liferay.Service.Tags.TagsVocabulary.getVocabularies(
			{
				companyId: themeDisplay.getCompanyId(),
				folksonomy: folksonomy
			},
			callback
		);
	},

	_getVocabulary: function(vocabularyId) {
		var instance = this;

		return jQuery('li[data-vocabularyId=' + vocabularyId + ']')
	},

	_getVocabularyEntries: function(vocabulary, callback) {
		var instance = this;

		instance._showLoading(instance._entryScopeClass);

		Liferay.Service.Tags.TagsEntry.getVocabularyEntries(
			{
				companyId: themeDisplay.getCompanyId(),
				name: vocabulary
			},
			callback
		);
	},

	_getVocabularyId: function(exp) {
		var instance = this;

		return jQuery(exp).attr('data-vocabularyId');
	},

	_getVocabularyName: function(exp) {
		var instance = this;

		return jQuery(exp).attr('data-vocabulary');
	},

	_hideAllMessages: function() {
		var instance = this;

		instance._container.find('.lfr-message-response').hide();
	},

	_hideLoading: function(exp) {
		var instance = this;

		instance._container.find('div.loading-animation').remove();
	},

	_hideSection: function(exp) {
		var instance = this;

		jQuery(exp).parent().removeClass('vocabulary-editing-tag');
	},

	_loadData: function() {
		var instance = this;

		var folksonomy = (instance._selectedVocabulary == 'tag');

		instance._closeEditSection();

		instance._displayList(
			folksonomy,
			function() {
				instance._displayVocabularyEntries(
					instance._selectedVocabularyName,
					function() {
						var entryId = instance._getEntryId(instance._entryListClass + ':first');
					}
				);
			}
		);
	},

	_merge: function(event, ui) {
		var instance = this;

		var draggable = ui.draggable;
		var droppable = ui.droppable;
		var fromEntryId = instance._getEntryId(draggable);
		var fromEntryName = instance._getEntryName(draggable);
		var toEntryId = instance._getEntryId(droppable);
		var toEntryName = instance._getEntryName(droppable);
		var vocabularyId = instance._getVocabularyId(droppable);
		var vocabularyName = instance._getVocabularyName(droppable);

		var isChangingVocabulary = !!vocabularyName;
		var destination = isChangingVocabulary ? vocabularyName : toEntryName;

		var tagText = {
			SOURCE: instance._getEntryName(draggable),
			DESTINATION: destination
		};

		var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x', ['[$SOURCE$]', '[$DESTINATION$]']).replace(
			/\[\$(SOURCE|DESTINATION)\$\]/gm,
			function(completeMatch, match, index, str) {
				return tagText[match];
			}
		);

		if (confirm(mergeText)) {
			if (instance._selectedVocabulary == 'tag') {
				if (isChangingVocabulary) {
					var properties = instance._buildProperties();
					instance._updateEntry(fromEntryId, fromEntryName, null, properties, vocabularyName);
					instance._displayVocabularyEntries(instance._selectedVocabularyName);
				}
				else {
					instance._mergeEntries(
						fromEntryId,
						toEntryId,
						function() {
							draggable.remove();
							instance._selectEntry(toEntryId);
							instance._alternateRows();
						}
					);
				}
			}
			else if (instance._selectedVocabulary == 'category') {
				var properties = instance._buildProperties();

				vocabularyName = vocabularyName || instance._selectedVocabularyName;
				parentEntryName = isChangingVocabulary ? null : toEntryName;

				instance._updateEntry(fromEntryId, fromEntryName, parentEntryName, properties, vocabularyName);
			}
		}
	},

	_mergeEntries: function(fromId, toId, callback) {
		Liferay.Service.Tags.TagsEntry.mergeEntries(
			{
				fromEntryId: fromId,
				toEntryId: toId
			},
			callback
		);
	},

	_reloadSearch: function() {
		var	instance = this;
		var options = {};
		var selected = jQuery('#vocabulary-select-search').val();
		var input = jQuery('#vocabulary-search-input');
		var entryList = jQuery(instance._entryListClass);
		var vocabularyList = jQuery(instance._vocabularyListClass);

		input.unbind('keyup');

		if (/vocabularies/.test(selected)) {
			options = {
				list: vocabularyList,
				filter: jQuery('a', vocabularyList)
			};
		}
		else {
			var filter = 'span';

			if (instance._selectedVocabulary == 'tag') {
				filter = 'span a';
			}

			options = {
				list: entryList,
				filter: jQuery(filter, entryList)
			};
		}

		input.liveSearch(options);
	},

	_removeProperty: function(property) {
		var instance = this;

		if (jQuery('div.vocabulary-property-row').length > 2) {
			property.remove();
		}
	},

	_resetActionValues: function() {
		var instance = this;

		jQuery('.vocabulary-actions input:text').val('');
		jQuery('.vocabulary-actions .vocabulary-name').hide();
	},

	_saveProperties: function() {
		var instance = this;

		var entryId = instance._selectedEntryId;
		var entryName = jQuery('input.entry-name').val() || instance._selectedEntryName;
		var parentCategoryName = null;
		var properties = instance._buildProperties();
		var vocabularyName = instance._selectedVocabularyName;

		instance._updateEntry(entryId, entryName, parentCategoryName, properties, vocabularyName);
	},

	_selectCurrentVocabulary: function(value) {
		var instance = this;

		var option = jQuery('select.vocabulary-select-list option[value="' + value + '"]');
		option.attr('selected', 'selected');
	},

	_selectEntry: function(entryId) {
		var instance = this;

		var entry = instance._getEntry(entryId);
		var entryId = instance._getEntryId(entry);
		var entryName = instance._getEntryName(entry);

		instance._selectedEntryId = entryId;
		instance._selectedEntryName = entryName;

		if (entry.is('.selected') || !entryId) {
			return entry;
		}

		instance._unselectAllEntries();
		entry.addClass('selected');

		var editContainer = jQuery('.vocabulary-edit');
		var entryNameField = editContainer.find('input.entry-name');

		entryNameField.val(entryName);
		instance._displayProperties(entryId);

		instance._selectedEntry = entry;

		return entry;
	},

	_selectVocabulary: function(vocabularyId) {
		var instance = this;

		var vocabulary = instance._getVocabulary(vocabularyId);
		var vocabularyName = instance._getVocabularyName(vocabulary);
		var vocabularyId = instance._getVocabularyId(vocabulary);

		if (vocabulary.is('.selected')) {
			return vocabulary;
		}

		instance._hideAllMessages();
		instance._selectedVocabularyName = vocabularyName;
		instance._selectedVocabularyId = vocabularyId;
		instance._selectCurrentVocabulary(vocabularyId);

		instance._unselectAllVocabularies();
		instance._closeEditSection();

		vocabulary.addClass('selected');
		instance._displayVocabularyEntries(instance._selectedVocabularyName);

		return vocabulary;
	},

	_sendMessage: function(type, key, output, noAutoHide) {
		var instance = this;

		var output = jQuery(output || '#vocabulary-messages');
		var message = Liferay.Language.get(key);
		var typeClass = 'portlet-msg-' + type;

		clearTimeout(instance._messageTimeout);

		output.removeClass('portlet-msg-error portlet-msg-success');
		output.addClass(typeClass).html(message).fadeIn('fast');

		if (!noAutoHide) {
			instance._messageTimeout = setTimeout(
				function() {
					output.fadeOut('slow');
				}, 7000);
		}
	},

	_showLoading: function(container) {
		var instance = this;

		jQuery(container).html('<div class="loading-animation" />');
	},

	_showSection: function(exp) {
		var instance = this;

		var element = jQuery(exp);

		if (!element.is(':visible')) {
			element.parent().addClass('vocabulary-editing-tag');
			element.find('input:first').focus();
		}
	},

	_unselectAllEntries: function() {
		var instance = this;

		jQuery(instance._entryListClass).removeClass('selected');
		jQuery('div.vocabulary-property-row:gt(0)').remove();
	},

	_unselectAllVocabularies: function() {
		var instance = this;

		jQuery(instance._vocabularyListClass).removeClass('selected');
	},

	_updateEntry: function(entryId, name, parentEntryName, properties, vocabularyName) {
		var instance = this;

		Liferay.Service.Tags.TagsEntry.updateEntry(
			{
				groupId: themeDisplay.getGroupId(),
				entryId: entryId,
				parentEntryName: parentEntryName,
				name: name,
				vocabularyName: vocabularyName,
				properties: properties
			},
			function(message) {
				var exception = message.exception;

				if (!exception) {
					var selectedText = instance._selectedEntry.find('> span > a');

					if (!selectedText.length) {
						selectedText.find('> span');
					}

					instance._selectedEntry.attr('data-entry', name);
					selectedText.text(name);

					instance._closeEditSection();
				}
				else {
					if (exception.indexOf('NoSuchVocabularyException') > -1) {
						instance._sendMessage('error', 'that-vocabulary-does-not-exist');
					}
					else if (exception.indexOf('NoSuchEntryException') > -1) {
						instance._sendMessage('error', 'that-parent-category-does-not-exist');
					}
					else if (exception.indexOf('Exception') > -1) {
						instance._sendMessage('error', 'one-of-your-fields-contain-invalid-characters');
					}
				}
			}
		);
	},

	_updateVocabulary: function(vocabularyId, vocabularyName, folksonomy, callback) {
		Liferay.Service.Tags.TagsVocabulary.updateVocabulary(
			{
				groupId: themeDisplay.getGroupId(),
				folksonomy: folksonomy,
				name: vocabularyName,
				vocabularyId: vocabularyId
			},
			callback
		);
	},

	_entryListClass: '.vocabulary-entries li',
	_entryScopeClass: '.vocabulary-entries',
	_selectedVocabulary: 'tag',
	_selectedVocabularyId: null,
	_selectedVocabularyName: null,
	_selectedEntryName: null,
	_vocabularyListClass: '.vocabulary-list li',
	_vocabularyScopeClass: '.vocabulary-list'
});