/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.cache.SingleVMPool;

import net.sf.ehcache.Cache;

import org.apache.velocity.runtime.resource.Resource;

/**
 * <a href="LiferayResourceCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LiferayResourceCacheUtil {

	public static final String CACHE_NAME =
		LiferayResourceCacheUtil.class.getName();

	public static void clear() {
		_cache.removeAll();
	}

	public static Resource get(String key) {
		return (Resource)SingleVMPool.get(_cache, key);
	}

	public static void put(String key, Resource resource) {
		SingleVMPool.put(_cache, key, resource);
	}

	public static void remove(String key) {
		SingleVMPool.remove(_cache, key);
	}

	private static Cache _cache = SingleVMPool.getCache(CACHE_NAME);

}