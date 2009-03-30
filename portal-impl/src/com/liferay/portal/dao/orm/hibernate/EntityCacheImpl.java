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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.LRUMap;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="EntityCacheImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EntityCacheImpl implements CacheRegistryItem, EntityCache {

	public static final String CACHE_NAME = EntityCache.class.getName();

	public void afterPropertiesSet() {
		CacheRegistry.register(this);
	}

	public void clearCache() {
		Map<String, Object> localCache = _localCache.get();

		localCache.clear();

		PortalCache[] portalCaches = _portalCaches.values().toArray(
			new PortalCache[_portalCaches.size()]);

		for (PortalCache portalCache : portalCaches) {
			portalCache.removeAll();
		}
	}

	public void clearCache(String className) {
		Map<String, Object> localCache = _localCache.get();

		localCache.clear();

		PortalCache portalCache = _getPortalCache(className);

		portalCache.removeAll();
	}

	public void clearLocalCache() {
		Map<String, Object> localCache = _localCache.get();

		localCache.clear();
	}

	public String getRegistryName() {
		return CACHE_NAME;
	}

	public Object getResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			return null;
		}

		Map<String, Object> localCache = _localCache.get();

		String localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

		Object result = localCache.get(localCacheKey);

		if (result == null) {
			PortalCache portalCache = _getPortalCache(classObj.getName());

			String cacheKey = _encodeCacheKey(primaryKeyObj);

			result = _multiVMPool.get(portalCache, cacheKey);

			if (result == null) {
				result = StringPool.BLANK;

				_multiVMPool.put(portalCache, cacheKey, result);
			}

			localCache.put(localCacheKey, result);
		}

		if (result != null) {
			result = _objectToResult(result);
		}

		return result;
	}

	public void invalidate() {
		clearCache();
	}

	public Object loadResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			Session session = null;

			try {
				session = sessionFactory.openSession();

				return session.load(classObj, primaryKeyObj);
			}
			finally {
				sessionFactory.closeSession(session);
			}
		}

		Map<String, Object> localCache = _localCache.get();

		String localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

		Object result = localCache.get(localCacheKey);

		if (result == null) {
			PortalCache portalCache = _getPortalCache(classObj.getName());

			String cacheKey = _encodeCacheKey(primaryKeyObj);

			result = _multiVMPool.get(portalCache, cacheKey);

			if (result == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Load " + classObj + " " + primaryKeyObj +
							" from session");
				}

				Session session = null;

				try {
					session = sessionFactory.openSession();

					result = session.load(classObj, primaryKeyObj);
				}
				finally {
					if (result == null) {
						result = StringPool.BLANK;
					}

					result = _objectToResult(result);

					_multiVMPool.put(portalCache, cacheKey, result);

					sessionFactory.closeSession(session);
				}
			}

			localCache.put(localCacheKey, result);
		}

		result = _objectToResult(result);

		return result;
	}

	public void putResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, Object result) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive() ||
			(result == null)) {

			return;
		}

		Map<String, Object> localCache = _localCache.get();

		String localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

		result = _objectToResult(result);

		localCache.put(localCacheKey, result);

		PortalCache portalCache = _getPortalCache(classObj.getName());

		String cacheKey = _encodeCacheKey(primaryKeyObj);

		_multiVMPool.put(portalCache, cacheKey, result);
	}

	public void removeResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			return;
		}

		Map<String, Object> localCache = _localCache.get();

		String localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

		localCache.remove(localCacheKey);

		PortalCache portalCache = _getPortalCache(classObj.getName());

		String cacheKey = _encodeCacheKey(primaryKeyObj);

		_multiVMPool.remove(portalCache, cacheKey);
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private String _encodeCacheKey(Serializable primaryKeyObj) {
		return String.valueOf(primaryKeyObj);
	}

	private String _encodeGroupKey(String className) {
		StringBuilder sb = new StringBuilder();

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(className);

		return sb.toString();
	}

	private String _encodeLocalCacheKey(
		Class<?> classObj, Serializable primaryKeyObj) {

		StringBuilder sb = new StringBuilder();

		sb.append(classObj.getName());
		sb.append(StringPool.POUND);
		sb.append(primaryKeyObj);

		return sb.toString();
	}

	private PortalCache _getPortalCache(String className) {
		String groupKey = _encodeGroupKey(className);

		PortalCache portalCache = _portalCaches.get(groupKey);

		if (portalCache == null) {
			portalCache = _multiVMPool.getCache(groupKey, true);

			_portalCaches.put(groupKey, portalCache);
		}

		return portalCache;
	}

	private Object _objectToResult(Object result) {
		if (result instanceof String) {
			return null;
		}
		else {
			result = ((BaseModel<?>)result).clone();

			BaseModel<?> model = (BaseModel<?>)result;

			model.setCachedModel(true);

			return model;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntityCacheImpl.class);

	private static ThreadLocal<Map<String, Object>> _localCache =
		new InitialThreadLocal<Map<String, Object>>(new LRUMap<String, Object>(
			PropsValues.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE));

	private MultiVMPool _multiVMPool;
	private Map<String, PortalCache> _portalCaches =
		new ConcurrentHashMap<String, PortalCache>();

}