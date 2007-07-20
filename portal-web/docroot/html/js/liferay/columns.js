(function(lib){
	lib.Columns = function(options) {
		lib.Columns.init(options);
	};

	lib.Columns.extendNativeFunctionObject({

		init: function(options) {
			var instance = this;

			// Set private variables
			instance._columns = options.columnSelector;
			instance._dropPosition = options.positionClass;
			instance._dropArea = options.areaClass;
			instance._handle = options.handleSelector;
			instance._onDrop = options.onComplete;
			instance._portlets = options.boxSelector;
			instance._cache = {};

			jQuery(instance._columns).find(instance._portlets).each(function() {
				instance.add(this);
			});
		},

		add: function(portlet) {
			var instance = this;
			var jPortlet = jQuery(portlet);
			var jHandle = jPortlet.find(instance._handle);

			jHandle.css({cursor: "move"});
			jPortlet.css({position: "relative"});

			jPortlet.lDrag({
				clone: true,
				dragClass: "drag-indicator",
				handle: jPortlet.find(instance._handle)[0],
				onMove: function(s){instance._onMove(s)},
				onComplete: function(s){instance._onComplete(s);},
				threshold: 2,
				scroll: true
			});
		},

		_clearCache: function() {
			if (this._cache.dropArea) {
				jQuery(this._cache.dropArea).remove();
			}
			if (this._cache.dropPosition) {
				jQuery(this._cache.dropPosition).remove();
			}
			this._cache = {};
		},

		_findContainer: function(coord) {
			var instance = this;
			var jColumns = instance._cache.columns;

			if (!jColumns) {
				jQuery(".lfr-column").addClass("dragging");

				jColumns = instance._cache.columns = jQuery(instance._columns);
				instance._cache.columnData = [];

				jColumns.each(function(i){
					var element = this.parentNode.nodeName.toLowerCase() == "td" ? this.parentNode : this.parentNode;
					instance._cache.columnData[i] = jQuery(element).coordinatesData(true);
				});
			}

			var data, quadrant, rt = null;

			jColumns.each(function(i) {

				quadrant = coord.insideObjectData(instance._cache.columnData[i]);

				if (quadrant) {
					data = instance._cache.columnData[i];
					data.quadrant = quadrant;
					rt = this;
					return false;
				}
			});

			return rt ? {area: rt, data: data} : null;
		},

		_findPortlet: function(coord, scope, current) {
			var instance = this;

			if (!instance._cache.portlets) {
				instance._cache.portlets = [];
				instance._cache.portletData = [];
				instance._cache.portletList = [];
			}

			var jPortlets = instance._cache.portlets[scope.id];

			if (!jPortlets) {
				jPortlets = jQuery(instance._portlets, scope);
				instance._cache.portlets[scope.id] = jPortlets;
				instance._cache.portletData[scope.id] = [];
				instance._cache.portletList[scope.id] = new LinkedList();

				jPortlets.each(function(i){
					instance._cache.portletData[scope.id][i] = jQuery(this).coordinatesData(true);
					instance._cache.portletList[scope.id].add(this);
				});
			}

			var data, quadrant, rt = null;

			jPortlets.each(function(i) {
				data = instance._cache.portletData[scope.id][i];
				quadrant = coord.insideObjectData(data);

				if (quadrant) {
					data.quadrant = quadrant;
					rt = this;
					return false;
				}
				else if (coord.y < data.midPoint.y) {
					data.quadrant = 1;
					rt = this;
					return false;
				}
				else if (i == jPortlets.length - 1) {
					data.quadrant = 4;
					rt = this;
					return false;
				}
			});

			return rt ? {position: rt, data: data} : null;
		},

		_showArea: function(container, nwOffset) {
			instance = this;
			var dropArea = instance._cache.dropArea;

			if (!dropArea) {
				jQuery("body:first").append("<div class='" + instance._dropArea + "'></div>");
				instance._cache.dropArea = dropArea = jQuery("." + instance._dropArea + ":first");
				dropArea.css({
					position: "absolute",
					zIndex: ZINDEX.DROP_AREA
				});
			}

			var element = jQuery(container.parentNode.nodeName.toLowerCase() == "td" ? container.parentNode : container.parentNode);
			dropArea.css({
				display: "",
				height: element.height(),
				left: nwOffset.x,
				top: nwOffset.y,
				width: element.width()
			});
		},

		_showPosition: function(portlet, nwOffset, quadrant) {
			instance = this;
			var dropPos = instance._cache.dropPosition;

			if (!dropPos) {
				jQuery("body:first").append("<div class='" + instance._dropPosition + "'></div>");
				dropPos = jQuery("." + instance._dropPosition + ":first");
				dropPos.css({
					position: "absolute",
					zIndex: ZINDEX.DROP_POSITION
				});
				instance._cache.dropPosition = dropPos;
			}
			var jDropPos = jQuery(instance._cache.dropPosition);
			var jPortlet = jQuery(portlet);

			dropPos.css({
				display: "",
				height: jPortlet.height()/2,
				left: nwOffset.x + "px",
				width: jPortlet.width() + "px"
			});

			if (quadrant == 1 || quadrant == 2) {
				dropPos.css({ top: nwOffset.y + "px" });
				if (dropPos[0].className != "drop-position-top") {
					dropPos[0].className = "drop-position-top";
				}
			}
			else {
				dropPos.css({ top: (nwOffset.y + jPortlet.height()/2) + "px" });
				if (dropPos[0].className != "drop-position-bottom") {
					dropPos[0].className = "drop-position-bottom";
				}
			}
		},

		_hideArea: function() {
			if (this._cache.dropArea) {
				this._cache.dropArea.css({display: "none"});
			}
		},

		_hidePosition: function() {
			if (this._cache.dropPosition) {
				this._cache.dropPosition.css({display: "none"});
			}
		},

		_hasMoved: function(current, position, quadrant) {
			var instance = this;
			var rt = true;

			if (current == position) {
				rt = false;
			}
			else {
				var portlets = instance._cache.portletList[current.parentNode.id];
				portlets.each(function(item) {
					if (item == position) {
						if ((quadrant == 1 || quadrant == 2) && item.listInfo.prev == current) {
							rt = false;
						}
						else if ((quadrant == 3 || quadrant == 4) && item.listInfo.next == current) {
							rt = false;
						}
					}
				});
			}

			return rt;
		},

		_onMove: function(settings) {
			jQuery(settings.container).css({top: 0, left: 0});
			var instance = this;
			var foundContainer = instance._findContainer(mousePos);
			var foundPortlet = null;

			if (foundContainer) {
				foundPortlet = instance._findPortlet(mousePos, foundContainer.area, settings.container);
				instance._showArea(foundContainer.area, foundContainer.data.nwOffset);
			}
			else {
				instance._hideArea();
			}

			if (foundPortlet && settings.container != foundPortlet.position) {
				var cPortlet = foundPortlet.position;
				var data = foundPortlet.data;
				instance._showPosition(cPortlet, data.nwOffset, data.quadrant);
			}
			else {
				instance._hidePosition();
			}
		},

		_onComplete: function(settings) {
			jQuery(settings.container).css({top: 0, left: 0});
			var instance = this;
			var foundContainer = instance._findContainer(mousePos);

			instance._hidePosition();
			instance._hideArea();

			if (instance._onDrop && foundContainer) {
				var foundPortlet = instance._findPortlet(mousePos, foundContainer.area, settings.container);

				if (foundPortlet) {
					if (instance._hasMoved(settings.container, foundPortlet.position, foundPortlet.data.quadrant)) {
						// Move if not in the same position
						instance._onDrop({
							portlet: settings.container,
							neighbor: foundPortlet.position,
							quadrant: foundPortlet.data.quadrant
						});
					}
				}
				else {
					// Add portlet to empty column
					instance._onDrop({
						portlet: settings.container,
						container: foundContainer.area
					});
				}
			}

			jQuery(".lfr-column").removeClass("dragging");
			instance._clearCache();
		}
	});
})(Liferay);
