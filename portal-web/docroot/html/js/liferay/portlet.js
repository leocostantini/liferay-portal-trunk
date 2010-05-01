Liferay.Portlet = {
	list: [],

	add: function(options) {
		var instance = this;

		var plid = options.plid || themeDisplay.getPlid();
		var portletId = options.portletId;
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

		var placeHolder = options.placeHolder;

		if (!placeHolder) {
			placeHolder = AUI().Node.create('<div class="loading-animation" />');
		}
		else {
			placeHolder = AUI().one(placeHolder);
		}

		var positionOptions = options.positionOptions;
		var beforePortletLoaded = options.beforePortletLoaded;
		var onComplete = options.onComplete;

		var container = AUI().one(Liferay.Layout.options.dropContainer);

		if (!container) {
			return;
		}

		var portletPosition = 0;
		var currentColumnId = 'column-1';

		if (options.placeHolder) {
			var column = placeHolder.get('parentNode');

			placeHolder.addClass('portlet-boundary');

			portletPosition = column.all('.portlet-boundary').indexOf(placeHolder);

			currentColumnId = Liferay.Util.getColumnId(column.attr('id'));
		}

		var url = themeDisplay.getPathMain() + '/portal/update_layout';

		var data = {
			cmd: 'add',
			dataType: 'json',
			doAsUserId: doAsUserId,
			p_l_id: plid,
			p_p_col_id: currentColumnId,
			p_p_col_pos: portletPosition,
			p_p_id: portletId,
			p_p_isolated: true
		};

		var firstPortlet = container.one('.portlet-boundary');
		var hasStaticPortlet = (firstPortlet && firstPortlet.isStatic);

		if (!options.placeHolder && !options.plid) {
			if (!hasStaticPortlet) {
				container.prepend(placeHolder);
			}
			else {
				firstPortlet.placeAfter(placeHolder);
			}
		}

		if (themeDisplay.isFreeformLayout()) {
			container.prepend(placeHolder);
		}

		data.currentURL = Liferay.currentURL;

		return instance.addHTML(
			{
				beforePortletLoaded: beforePortletLoaded,
				data: data,
				onComplete: onComplete,
				placeHolder: placeHolder,
				url: url
			}
		);
	},

	addHTML: function(options) {
		var instance = this;

		var A = AUI();

		var portletBoundary = null;

		var beforePortletLoaded = options.beforePortletLoaded;
		var data = options.data;
		var dataType = 'html';
		var onComplete = options.onComplete;
		var placeHolder = options.placeHolder;
		var url = options.url;

		if (data && data.dataType) {
			dataType = data.dataType;
		}

		var addPortletReturn = function(html) {
			var container = placeHolder.get('parentNode');

			var portletBound = A.Node.create('<div></div>');

			portletBound.plug(A.Plugin.ParseContent);

			portletBound.setContent(html);
			portletBound = portletBound.get('firstChild');

			var id = portletBound.attr('id');

			var portletId = Liferay.Util.getPortletId(id);

			portletBound.portletId = portletId;

			placeHolder.hide();
			placeHolder.placeAfter(portletBound);
			placeHolder.remove();

			instance.refreshLayout(portletBound);

			Liferay.Util.addInputType(portletBound);

			if (window.location.hash) {
				window.location.hash = 'p_' + portletId;
			}

			portletBoundary = portletBound;

			if (Liferay.Layout) {
				Liferay.Layout.updateCurrentPortletInfo(portletBoundary);

				if (container) {
					Liferay.Layout.syncEmptyColumnClassUI(container);
				}
			}

			if (onComplete) {
				onComplete(portletBoundary, portletId);
			}

			return portletId;
		};

		if (beforePortletLoaded) {
			beforePortletLoaded(placeHolder);
		}

		A.use(
			'io-request',
			'parse-content',
			function(A) {
				A.io.request(
					url,
					{
						data: data,
						dataType: dataType,
						method: 'POST',
						on: {
							success: function(event, id, obj) {
								var instance = this;

								var response = this.get('responseData');

								if (dataType == 'html') {
									addPortletReturn(response);
								}
								else {
									if (response.refresh) {
										location.reload();
									}
									else {
										addPortletReturn(response.portletHTML);
									}
								}
							}
						}
					}
				);
			}
		);
	},

	close: function(portlet, skipConfirm, options) {
		var instance = this;

		if (skipConfirm || confirm(Liferay.Language.get('are-you-sure-you-want-to-remove-this-component'))) {
			options = options || {};

			var plid = options.plid || themeDisplay.getPlid();
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
			var currentPortlet = AUI().one(portlet);

			if (currentPortlet) {
				var portletId = currentPortlet.portletId;
				var column = currentPortlet.ancestor(Liferay.Layout.options.dropContainer);

				Liferay.Layout.updateCurrentPortletInfo(currentPortlet);

				currentPortlet.remove();

				if (column) {
					Liferay.Layout.syncEmptyColumnClassUI(column);
				}

				var url = themeDisplay.getPathMain() + '/portal/update_layout';

				AUI().use(
					'aui-io-request',
					function(A) {
						A.io.request(
							url,
							{
								data: {
									cmd: 'delete',
									doAsUserId: doAsUserId,
									p_l_id: plid,
									p_p_id: portletId
								},
								method: 'POST'
							}
						);
					}
				);

				Liferay.fire(
					'closePortlet',
					 {
						plid: plid,
						portletId: portletId
					}
				);
			}
		}
		else {
			self.focus();
		}
	},

	isStatic: function(portletId) {
		var instance = this;

		var id = Liferay.Util.getPortletId(portletId.id || portletId);

		return (id in instance._staticPortlets);
	},

	onLoad: function(options) {
		var instance = this;

		var A = AUI();

		var canEditTitle = options.canEditTitle;
		var columnPos = options.columnPos;
		var isStatic = (options.isStatic == 'no') ? null : options.isStatic;
		var namespacedId = options.namespacedId;
		var portletId = options.portletId;
		var refreshURL = options.refreshURL;

		if (isStatic) {
			instance.registerStatic(portletId);
		}

		var portlet = A.one('#' + namespacedId);

		if (portlet && !portlet.portletProcessed) {
			portlet.portletProcessed = true;
			portlet.portletId = portletId;
			portlet.columnPos = columnPos;
			portlet.isStatic = isStatic;
			portlet.refreshURL = refreshURL;

			// Functions to run on portlet load

			if (canEditTitle) {
				Liferay.Util.portletTitleEdit(
					{
						doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
						obj: portlet,
						plid: themeDisplay.getPlid(),
						portletId: portletId
					}
				);
			}

			if (!themeDisplay.layoutMaximized) {
				var configurationLink = portlet.all('.portlet-configuration a');

				configurationLink.on(
					'click',
					function(event) {
						var configurationURL = event.currentTarget.attr('href');

						instance.openConfiguration(portlet, portletId, configurationURL, namespacedId);

						event.preventDefault();
					}
				);

				var minimizeLink = portlet.one('.portlet-minimize a');

				if (minimizeLink) {
					minimizeLink.on(
						'click',
						function(event) {
							instance.minimize(portlet, minimizeLink);

							event.halt();
						}
					);
				}

				var maximizeLink = portlet.one('.portlet-maximize a');

				if (maximizeLink) {
					maximizeLink.on(
						'click',
						function(event) {
							submitForm(document.hrefFm, event.currentTarget.attr('href'));

							event.halt();
						}
					);
				}

				var closeLink = portlet.one('.portlet-close a');

				if (closeLink) {
					closeLink.on(
						'click',
						function(event) {
							instance.close(portlet);

							event.halt();
						}
					);
				}

				var refreshLink = portlet.one('.portlet-refresh a');

				if (refreshLink) {
					refreshLink.on(
						'click',
						A.bind(instance.refresh, instance, portlet)
					);
				}

				var printLink = portlet.one('.portlet-print a');

				if (printLink) {
					printLink.on(
						'click',
						function(event) {
							location.href = event.currentTarget.attr('href');

							event.halt();
						}
					);
				}

				var portletCSSLink = portlet.one('.portlet-css a');

				if (portletCSSLink) {
					portletCSSLink.on(
						'click',
						function(event) {
							instance._loadCSSEditor(portletId);
						}
					);
				}
			}

			Liferay.fire(
				'portletReady',
				{
					portlet: portlet,
					portletId: portletId
				}
			);

			var list = instance.list;

			var index = A.Array.indexOf(list, portletId);

			if (index > -1) {
				list.splice(index, 1);
			}

			if (!list.length) {
				Liferay.fire(
					'allPortletsReady',
					{
						portletId: portletId
					}
				);
			}
		}
	},

	openConfiguration: function(portlet, portletId, configurationURL, namespacedId) {
		var instance = this;

		var A = AUI();

		portlet = A.one(portlet);

		if (portlet && configurationURL) {
			var title = portlet.one('.portlet-title') || portlet.one('.portlet-title-default');

			var iframeId = namespacedId + 'configurationIframe';

			var iframeTPL = '<iframe class="configuration-frame" frameborder="0" id="' + iframeId + '" name="' + iframeId + '" src="' + configurationURL + '"></iframe>';
			var iframe = A.Node.create(iframeTPL);

			var bodyContent = A.Node.create('<div></div>');

			bodyContent.append(iframe);

			var fixSize = function(number) {
				return ((parseInt(number, 10) || 0) - 5) + 'px';
			};

			var updateIframeSize = function(event) {
				setTimeout(
					function() {
						var bodyHeight = bodyNode.getStyle('height');

						iframe.setStyle('height', fixSize(bodyHeight));

						bodyNode.loadingmask.refreshMask();
					},
					50
				);
			};

			var dialog = new A.Dialog(
				{
					after: {
						heightChange: updateIframeSize,
						widthChange: updateIframeSize
					},
					align: {
						node: null,
						points: ['tc', 'tc']
					},
					bodyContent: bodyContent,
					destroyOnClose: true,
					draggable: true,
					title: title.html() + ' - ' + Liferay.Language.get('configuration'),
					width: 820
				}
			).render();

			dialog.move(dialog.get('x'), dialog.get('y') + 100);

			var bodyNode = dialog.bodyNode;

			bodyNode.plug(A.LoadingMask).loadingmask.show();

			iframe.on(
				'load',
				function(){
					iframe.get('contentDocument.documentElement').setStyle('overflow', 'visible');

					if ((iframe.get('contentWindow.location.search').indexOf('p_p_id=86') == -1) &&
						(iframe.get('contentWindow.location.search').indexOf('maintainDialog=true') == -1)) {

						dialog.close();
					}

					var iframeBody = iframe.get('contentWindow.document.body');

					iframeBody.addClass('configuration-popup');

					iframe.set('height', iframeBody.get('scrollHeight'));

					A.on(
						'key',
						function(event) {
							dialog.close();
						},
						[iframeBody],
						'down:27'
					);

					var closeButton = iframeBody.one('.aui-button-input-cancel');

					if (closeButton) {
						closeButton.on('click', dialog.close, dialog);
					}

					bodyNode.loadingmask.hide();
				}
			);
		}
	},

	refresh: function(portlet) {
		var instance = this;

		portlet = AUI().one(portlet);

		if (portlet && portlet.refreshURL) {
			var url = portlet.refreshURL;
			var id = portlet.attr('portlet');

			var placeHolder = AUI().Node.create('<div class="loading-animation" id="p_load' + id + '" />');

			portlet.placeBefore(placeHolder);
			portlet.remove();

			instance.addHTML(
				{
					data: {
						p_p_state: 'normal'
					},
					onComplete: function(portlet, portletId) {
						portlet.refreshURL = url;
					},
					placeHolder: placeHolder,
					url: url
				}
			);
		}
	},

	refreshLayout: function(portletBound) {
	},

	registerStatic: function(portletId) {
		var instance = this;

		var Node = AUI().Node;

		if (Node && portletId instanceof Node) {
			portletId = portletId.attr('id');
		}
		else if (portletId.id) {
			portletId = portletId.id;
		}

		var id = Liferay.Util.getPortletId(portletId);

		instance._staticPortlets[id] = true;
	},

	_staticPortlets: {}
};

Liferay.provide(
	Liferay.Portlet,
	'minimize',
	function(portlet, el, options) {
		var instance = this;

		var A = AUI();

		options = options || {};

		var plid = options.plid || themeDisplay.getPlid();
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

		portlet = A.one(portlet);

		if (portlet) {
			var content = portlet.one('.portlet-content-container');

			if (content) {
				var restore = content.hasClass('aui-helper-hidden');

				content.toggle();
				portlet.toggleClass('portlet-minimized');

				var link = A.one(el);

				if (link) {
					var img = link.one('img');

					if (img) {
						var title = (restore) ? Liferay.Language.get('minimize') : Liferay.Language.get('restore');

						var imgSrc = img.attr('src');

						if (restore) {
							imgSrc = imgSrc.replace(/restore.png$/, 'minimize.png');
						}
						else {
							imgSrc = imgSrc.replace(/minimize.png$/, 'restore.png');
						}

						img.attr('alt', title);
						img.attr('title', title);

						link.attr('title', title);
						img.attr('src', imgSrc);
					}
				}

				var html = '';
				var portletBody = content.one('.portlet-body');

				if (portletBody) {
					html = portletBody.html();
				}

				var hasBodyContent = !!(A.Lang.trim(html));

				if (hasBodyContent) {
					content.unplug(A.Plugin.IO);
				}
				else {
					content.plug(
						A.Plugin.IO,
						{
							autoLoad: false,
							data: {
								doAsUserId: doAsUserId,
								p_l_id: plid,
								p_p_id: portlet.portletId,
								p_p_state: 'exclusive'
							},
							showLoading: false,
							uri: themeDisplay.getPathMain() + '/portal/render_portlet'
						}
					);
				}

				A.io.request(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						after: {
							success: function() {
								if (restore && content.io) {
									content.io.start();
								}
							}
						},
						data: {
							cmd: 'minimize',
							doAsUserId: doAsUserId,
							p_l_id: plid,
							p_p_id: portlet.portletId,
							p_p_restore: restore
						},
						method: 'POST'
					}
				);
			}
		}
	},
	['aui-io']
);

Liferay.provide(
	Liferay.Portlet,
	'_loadCSSEditor',
	function(portletId) {
		Liferay.PortletCSS.init(portletId);
	},
	['liferay-look-and-feel']
);

// Backwards compatability

Liferay.Portlet.ready = function(fn) {
	Liferay.on(
		'portletReady',
		function(event) {
			fn(event.portletId, event.portlet);
		}
	);
};