Liferay.Portlet.TagsAdmin=new Class({initialize:function(){var A=this;var B=jQuery(A._entryScopeClass);A._container=jQuery(".vocabulary-container");jQuery(".vocabulary-close").click(function(){A._unselectAllEntries();A._closeEditSection()});jQuery(".vocabulary-save-properties").click(function(){A._saveProperties()});A._portletMessageContainer=jQuery("<div class=\"lfr-message-response\" id=\"vocabulary-messages\" />");A._entryMessageContainer=jQuery("<div class=\"lfr-message-response\" id=\"vocabulary-entry-messages\" />");A._portletMessageContainer.hide();A._entryMessageContainer.hide();A._container.before(A._portletMessageContainer);B.before(A._entryMessageContainer);var E=jQuery(".vocabulary-buttons");var D=jQuery(".vocabulary-toolbar");var F=function(H){H=Liferay.Language.get(H);D.find(".vocabulary-label").html(H)};var G=function(H){E.find(".button").removeClass("selected");jQuery(H).addClass("selected")};E.find(".tags-sets").click(function(){A._selectedVocabulary="tag";F("add-tag");G(this);A._loadData()});E.find(".categories").click(function(){A._selectedVocabulary="category";F("add-category");G(this);A._loadData()});jQuery("select.vocabulary-select-list").change(function(){var K=jQuery(".vocabulary-actions");var H=jQuery(this).val();var J=jQuery(this).find("option:selected").text();var I=K.find(".vocabulary-name");if(J=="(new)"){I.show().focus()}else{A._resetActionValues()}});jQuery("#vocabulary-search-bar").change(function(){jQuery("#vocabulary-search-input").focus();A._reloadSearch()});var C=function(){var M=jQuery(".vocabulary-actions");var L=M.find(".vocabulary-entry-name").val();var I=M.find(".vocabulary-select-list").val();var K=M.find(".vocabulary-select-list option:selected").text();var J=M.find(".vocabulary-name");A._hideAllMessages();var H=J.val();if(H){A._addVocabulary(H,function(){A._addEntry(L,H)});return }A._addEntry(L,K)};jQuery("input.vocabulary-save-entry").click(C);jQuery(".vocabulary-actions input").keyup(function(H){if(H.keyCode==13){C();return false}});jQuery("input.vocabulary-delete-entries-button").click(function(){if(confirm(Liferay.Language.get("are-you-sure-you-want-to-delete-this-entry"))){A._deleteEntry(A._selectedEntryId,function(){A._closeEditSection();A._displayVocabularyEntries(A._selectedVocabularyName)})}});jQuery("input.vocabulary-delete-list-button").click(function(){if(confirm(Liferay.Language.get("are-you-sure-you-want-to-delete-this-list"))){A._deleteVocabulary(A._selectedVocabularyId,function(){A._closeEditSection();A._loadData()})}});A._loadData()},_displayCategoriesVocabularyEntries:function(E,K){var J=this;var D=[];var I=jQuery(J._entryScopeClass);var H={sortOn:"li",dropOn:"span.folder",dropHoverClass:"hover-folder",drop:function(M,N){N.droppable=jQuery(this).parent();J._merge(M,N);var L=jQuery("#vocabulary-treeview");setTimeout(function(){L.find(":not(span)").removeClass();L.find("div").remove();L.removeData("toggler");L.treeview()},100)}};D.push("<div class=\"vocabulary-treeview-container lfr-component\"><ul id=\"vocabulary-treeview\" class=\"filetree\">");J._buildCategoryTreeview(E,D,0);D.push("</ul></div>");I.html(D.join(""));J._reloadSearch();var B=jQuery("#vocabulary-treeview");var A=jQuery(J._entryListClass);A.click(function(M){var L=J._getEntryId(this);var N=jQuery(".vocabulary-edit");J._selectEntry(L);J._showSection(N);M.stopPropagation()});B.treeview().tree(H);var G=jQuery(J._vocabularyScopeClass);var F=jQuery("li",G);var C=B.data("tree").identifier;F.droppable({accept:".vocabulary-category-item",tolerance:"pointer",hoverClass:"active-area",scope:C,cssNamespace:false,drop:function(L,M){M.droppable=jQuery(this);J._merge(L,M)}});if(K){K()}},_displayFolksonomiesVocabularyEntries:function(B,F){var A=this;var C=[];var E=jQuery(A._entryScopeClass);C.push("<ul>");jQuery.each(B,function(G){C.push("<li class=\"vocabulary-item results-row\" ");C.push("data-entry=\"");C.push(this.name);C.push("\" data-entryId=\"");C.push(this.entryId);C.push("\"><span><a href=\"javascript: ;\">");C.push(this.name);C.push("</a></span>");C.push("</li>")});C.push("</ul>");if(!B.length){C=[];A._sendMessage("info","no-entries-were-found","#vocabulary-entry-messages",true)}E.html(C.join(""));A._reloadSearch();var D=jQuery(A._entryListClass);D.mousedown(function(){var G=A._getEntryId(this);var H=jQuery(".vocabulary-edit");A._selectEntry(G);A._showSection(H)});D.draggable({appendTo:"body",cssNamespace:false,cursor:"move",distance:3,ghosting:false,helper:function(J,K){var H=jQuery(this);var G=H.width();var I=H.clone();I.css({width:G});I.addClass("portlet-tags-admin-helper");return I},opacity:0.7,scope:"vocabulary-item-scope",scroll:"auto",zIndex:1000});D.droppable({accept:".vocabulary-item",cssNamespace:false,drop:function(G,H){H.droppable=jQuery(this);A._merge(G,H)},hoverClass:"active-area",scope:"vocabulary-item-scope",tolerance:"pointer"});A._alternateRows();if(F){F()}},_displayList:function(C,E){var A=this;var B=[];var D=jQuery(A._vocabularyScopeClass);A._showLoading(".vocabulary-entries, .vocabulary-list");B.push("<ul>");A._getVocabularies(C,function(I){jQuery.each(I,function(K){B.push("<li");B.push(" class=\"vocabulary-category results-row");if(K==0){B.push(" selected ")}B.push("\" data-vocabulary=\"");B.push(this.name);B.push("\" data-vocabularyId=\"");B.push(this.vocabularyId);B.push("\"><span><a href=\"javascript: ;\">");B.push(this.name);B.push("</a></span>");B.push("</li>")});B.push("</ul>");D.html(B.join(""));var G=jQuery(A._vocabularyListClass+":first");var H=A._getVocabularyName(G);var F=A._getVocabularyId(G);A._selectedVocabularyName=H;A._selectedVocabularyId=F;A._feedVocabularySelect(I,F);var J=jQuery("li",D);J.mousedown(function(L){var K=A._getVocabularyId(this);A._selectVocabulary(K)});J.droppable({accept:".vocabulary-item",cssNamespace:false,drop:function(K,L){L.droppable=jQuery(this);A._merge(K,L)},hoverClass:"active-area",scroll:"auto",scope:"vocabulary-item-scope",tolerance:"pointer"});jQuery("li span a",D).editable(function(P,O){var N=P;var L=A._selectedVocabularyId;var M=(A._selectedVocabulary=="tag");var K=jQuery(this).parents("li:first");K.attr("data-vocabulary",P);A._updateVocabulary(L,N,M);return P},{cssclass:"vocabulary-edit-vocabulary",data:function(L,K){return L},height:"15px",width:"200px",onblur:"ignore",submit:Liferay.Language.get("save"),select:false,type:"text",event:"dblclick"});if(E){E()}})},_displayProperties:function(B){var A=this;A._getProperties(B,function(D){if(!D.length){D=[{key:"",value:""}]}var E=D.length;var C=jQuery("div.vocabulary-property-row").length;if(C>E){return }jQuery.each(D,function(){var F=jQuery("div.vocabulary-property-row:last");A._addProperty(F,this.key,this.value)})})},_displayVocabularyEntries:function(B,C){var A=this;jQuery("#vocabulary-entry-messages").hide();A._getVocabularyEntries(B,function(D){if(!A._selectedVocabulary||A._selectedVocabulary=="tag"){A._displayFolksonomiesVocabularyEntries(D,C)}if(A._selectedVocabulary=="category"){A._displayCategoriesVocabularyEntries(D,C)}})},_addEntry:function(C,B,D){var A=this;Liferay.Service.Tags.TagsEntry.addEntry({groupId:themeDisplay.getGroupId(),name:C,vocabulary:B,properties:[]},function(G){var F=G.exception;if(!F&&G.entryId){A._sendMessage("success","your-request-processed-successfully");A._selectVocabulary(G.vocabularyId);A._displayVocabularyEntries(A._selectedVocabularyName,function(){var H=A._selectEntry(G.entryId);if(H.length){jQuery(A._entryScopeClass).scrollTo(H)}A._showSection(".vocabulary-edit")});A._resetActionValues();if(D){D(C,B)}}else{var E="";if(F.indexOf("DuplicateEntryException")>-1){E="that-tag-already-exists"}else{if(F.indexOf("EntryNameException")>-1){E="one-of-your-fields-contain-invalid-characters"}else{if(F.indexOf("NoSuchVocabularyException")>-1){E="that-vocabulary-does-not-exists"}}}if(E){A._sendMessage("error",E)}}})},_addProperty:function(F,D,E){var B=this;var A=jQuery("div.vocabulary-property-row:last");var C=A.clone();C.find(".property-key").val(D);C.find(".property-value").val(E);C.insertAfter(F);C.show();if(!D&&!E){C.find("input:first").addClass("lfr-auto-focus")}B._attachPropertyIconEvents(C)},_addVocabulary:function(C,D){var A=this;var B=(A._selectedVocabulary=="tag");Liferay.Service.Tags.TagsVocabulary.addVocabulary({groupId:themeDisplay.getGroupId(),name:C,folksonomy:B},function(G){var F=G.exception;if(!G.exception){A._sendMessage("success","your-request-processed-successfully");A._displayList(B,function(){var H=A._selectVocabulary(G.vocabularyId);A._displayVocabularyEntries(A._selectedVocabularyName);if(H.length){jQuery(A._vocabularyScopeClass).scrollTo(H)}});jQuery(".vocabulary-actions .vocabulary-name").hide();if(D){D(C)}}else{var E="";if(F.indexOf("DuplicateVocabularyException")>-1){E="that-vocabulary-already-exists"}else{if(F.indexOf("VocabularyNameException")>-1){E="one-of-your-fields-contain-invalid-characters"}else{if(F.indexOf("NoSuchVocabularyException")>-1){E="that-parent-vocabulary-does-not-exist"}}}if(E){A._sendMessage("error",E)}}})},_alternateRows:function(){var A=this;var B=jQuery(A._entryScopeClass);jQuery("li",B).removeClass("alt");jQuery("li:odd",B).addClass("alt")},_attachPropertyIconEvents:function(B){var A=this;var C=jQuery(B);C.find(".add-property").click(function(){A._addProperty(B,"","")});C.find(".delete-property").click(function(){A._removeProperty(B)})},_buildCategoryTreeview:function(B,C,E){var A=this;var D=A._filterCategory(B,E);jQuery.each(D,function(G){var H=this.entryId;var F=this.name;var I=A._filterCategory(B,H).length;C.push("<li");C.push(" class=\"vocabulary-category-item\"");C.push(" data-entry=\"");C.push(this.name);C.push("\" data-entryId=\"");C.push(this.entryId);C.push("\"><span class=\"folder\">");C.push(F);C.push("</span>");if(I){C.push("<ul>");A._buildCategoryTreeview(B,C,H);C.push("</ul>")}C.push("</li>")});return D.length},_buildProperties:function(){var A=this;var B=[];jQuery(".vocabulary-property-row:visible").each(function(F,H){var C=jQuery(this);var E=C.find("input.property-key").val();var G=C.find("input.property-value").val();var D=["0",":",E,":",G,","].join("");B.push(D)});return B.join("")},_closeEditSection:function(){var A=this;A._hideSection(".vocabulary-edit")},_deleteEntry:function(B,C){var A=this;Liferay.Service.Tags.TagsEntry.deleteEntry({entryId:B},C)},_deleteVocabulary:function(B,C){var A=this;Liferay.Service.Tags.TagsVocabulary.deleteVocabulary({entryId:B},C)},_feedVocabularySelect:function(E,D){var B=this;var A=jQuery("select.vocabulary-select-list");var C=["<option value=\"0\"></option>","<option value=\"0\">(new)</option>"];jQuery.each(E,function(F){var G=(this.vocabularyId==D);C.push("<option");C.push(G?" selected ":"");C.push(" value=\"");C.push(this.vocabularyId);C.push("\">");C.push(this.name);C.push("</option>")});A.html(C.join(""))},_filterCategory:function(B,C){var A=this;return jQuery.grep(B,function(E,D){return(E.parentEntryId==C)})},_getEntry:function(B){var A=this;return jQuery("li[data-entryId="+B+"]")},_getEntryId:function(B){var A=this;return jQuery(B).attr("data-entryId")},_getEntryName:function(B){var A=this;return jQuery(B).attr("data-entry")},_getProperties:function(B,C){var A=this;Liferay.Service.Tags.TagsProperty.getProperties({entryId:B},C)},_getVocabularies:function(B,C){var A=this;Liferay.Service.Tags.TagsVocabulary.getGroupVocabularies({groupId:themeDisplay.getGroupId(),folksonomy:B},C)},_getVocabulary:function(B){var A=this;return jQuery("li[data-vocabularyId="+B+"]")},_getVocabularyEntries:function(B,C){var A=this;A._showLoading(A._entryScopeClass);Liferay.Service.Tags.TagsEntry.getGroupVocabularyEntries({groupId:themeDisplay.getGroupId(),name:B},C)},_getVocabularyId:function(B){var A=this;return jQuery(B).attr("data-vocabularyId")},_getVocabularyName:function(B){var A=this;return jQuery(B).attr("data-vocabulary")},_hideAllMessages:function(){var A=this;A._container.find(".lfr-message-response").hide()},_hideLoading:function(B){var A=this;A._container.find("div.loading-animation").remove()},_hideSection:function(B){var A=this;jQuery(B).parent().removeClass("vocabulary-editing-tag")},_loadData:function(){var A=this;var B=(A._selectedVocabulary=="tag");A._closeEditSection();A._displayList(B,function(){A._displayVocabularyEntries(A._selectedVocabularyName,function(){var C=A._getEntryId(A._entryListClass+":first")})})},_merge:function(B,L){var O=this;var P=L.draggable;var H=L.droppable;var G=O._getEntryId(P);var J=O._getEntryName(P);var D=O._getEntryId(H);var C=O._getEntryName(H);var E=O._getVocabularyId(H);var F=O._getVocabularyName(H);var M=!!F;var K=M?F:C;var A={SOURCE:O._getEntryName(P),DESTINATION:K};var N=Liferay.Language.get("are-you-sure-you-want-to-merge-x-into-x",["[$SOURCE$]","[$DESTINATION$]"]).replace(/\[\$(SOURCE|DESTINATION)\$\]/gm,function(T,R,Q,S){return A[R]});if(confirm(N)){if(O._selectedVocabulary=="tag"){if(M){var I=O._buildProperties();O._updateEntry(G,J,null,I,F);O._displayVocabularyEntries(O._selectedVocabularyName)}else{O._mergeEntries(G,D,function(){P.remove();O._selectEntry(D);O._alternateRows()})}}else{if(O._selectedVocabulary=="category"){var I=O._buildProperties();F=F||O._selectedVocabularyName;parentEntryName=M?null:C;O._updateEntry(G,J,parentEntryName,I,F)}}}},_mergeEntries:function(B,A,C){Liferay.Service.Tags.TagsEntry.mergeEntries({fromEntryId:B,toEntryId:A},C)},_reloadSearch:function(){var A=this;var C={};var E=jQuery("#vocabulary-select-search").val();var B=jQuery("#vocabulary-search-input");var G=jQuery(A._entryListClass);var F=jQuery(A._vocabularyListClass);B.unbind("keyup");if(/vocabularies/.test(E)){C={list:F,filter:jQuery("a",F)}}else{var D="span";if(A._selectedVocabulary=="tag"){D="span a"}C={list:G,filter:jQuery(D,G)}}B.liveSearch(C)},_removeProperty:function(B){var A=this;if(jQuery("div.vocabulary-property-row").length>2){B.remove()}},_resetActionValues:function(){var A=this;jQuery(".vocabulary-actions input:text").val("");jQuery(".vocabulary-actions .vocabulary-name").hide()},_saveProperties:function(){var A=this;var E=A._selectedEntryId;var F=jQuery("input.entry-name").val()||A._selectedEntryName;var B=null;var D=A._buildProperties();var C=A._selectedVocabularyName;A._updateEntry(E,F,B,D,C)},_selectCurrentVocabulary:function(C){var A=this;var B=jQuery("select.vocabulary-select-list option[value=\""+C+"\"]");B.attr("selected","selected")},_selectEntry:function(C){var B=this;var E=B._getEntry(C);var C=B._getEntryId(E);var D=B._getEntryName(E);B._selectedEntryId=C;B._selectedEntryName=D;if(E.is(".selected")||!C){return E}B._unselectAllEntries();E.addClass("selected");var F=jQuery(".vocabulary-edit");var A=F.find("input.entry-name");A.val(D);B._displayProperties(C);B._selectedEntry=E;return E},_selectVocabulary:function(B){var A=this;var D=A._getVocabulary(B);var C=A._getVocabularyName(D);var B=A._getVocabularyId(D);if(D.is(".selected")){return D}A._hideAllMessages();A._selectedVocabularyName=C;A._selectedVocabularyId=B;A._selectCurrentVocabulary(B);A._unselectAllVocabularies();A._closeEditSection();D.addClass("selected");A._displayVocabularyEntries(A._selectedVocabularyName);return D},_sendMessage:function(E,C,B,D){var A=this;var B=jQuery(B||"#vocabulary-messages");var F=Liferay.Language.get(C);var G="portlet-msg-"+E;clearTimeout(A._messageTimeout);B.removeClass("portlet-msg-error portlet-msg-success");B.addClass(G).html(F).fadeIn("fast");if(!D){A._messageTimeout=setTimeout(function(){B.fadeOut("slow")},7000)}},_showLoading:function(B){var A=this;jQuery(B).html("<div class=\"loading-animation\" />")},_showSection:function(C){var A=this;var B=jQuery(C);if(!B.is(":visible")){B.parent().addClass("vocabulary-editing-tag");B.find("input:first").focus()}},_unselectAllEntries:function(){var A=this;jQuery(A._entryListClass).removeClass("selected");jQuery("div.vocabulary-property-row:gt(0)").remove()},_unselectAllVocabularies:function(){var A=this;jQuery(A._vocabularyListClass).removeClass("selected")},_updateEntry:function(F,D,C,E,B){var A=this;Liferay.Service.Tags.TagsEntry.updateEntry({entryId:F,parentEntryName:C,name:D,vocabularyName:B,properties:E},function(H){var G=H.exception;if(!G){var I=A._selectedEntry.find("> span > a");if(!I.length){I.find("> span")}A._selectedEntry.attr("data-entry",D);I.text(D);A._closeEditSection()}else{if(G.indexOf("NoSuchVocabularyException")>-1){A._sendMessage("error","that-vocabulary-does-not-exist")}else{if(G.indexOf("NoSuchEntryException")>-1){A._sendMessage("error","that-parent-category-does-not-exist")}else{if(G.indexOf("Exception")>-1){A._sendMessage("error","one-of-your-fields-contain-invalid-characters")}}}}})},_updateVocabulary:function(A,C,B,D){Liferay.Service.Tags.TagsVocabulary.updateVocabulary({vocabularyId:A,name:C,folksonomy:B},D)},_entryListClass:".vocabulary-entries li",_entryScopeClass:".vocabulary-entries",_selectedVocabulary:"tag",_selectedVocabularyId:null,_selectedVocabularyName:null,_selectedEntryName:null,_vocabularyListClass:".vocabulary-list li",_vocabularyScopeClass:".vocabulary-list"})