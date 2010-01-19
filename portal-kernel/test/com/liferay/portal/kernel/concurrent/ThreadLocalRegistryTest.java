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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

import java.util.concurrent.CyclicBarrier;

/**
 * <a href="ThreadLocalRegistryTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ThreadLocalRegistryTest extends TestCase {

	public void testThreadIsolation() throws Exception {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
		final Thread parentThread = Thread.currentThread();

		Thread childThread = new Thread() {

			public void run() {
				try {
					ThreadLocal<Boolean> threadLocal =
						ThreadLocalRegistry.createAndRegisterThreadLocal(
							Boolean.FALSE);
					ThreadLocal<?>[] threadLocals =
						ThreadLocalRegistry.captureSnapshot();
					assertEquals(1, threadLocals.length);
					assertFalse((Boolean)threadLocals[0].get());
					// check programming change
					threadLocal.set(Boolean.TRUE);
					threadLocals =
						ThreadLocalRegistry.captureSnapshot();
					assertEquals(1, threadLocals.length);
					assertTrue((Boolean)threadLocals[0].get());
					// Sync point 1, parenet thread should not see this
					// threadlocal
					cyclicBarrier.await();

					cyclicBarrier.await();
					// Sync point 2, should not able to see parent thread's
					// threadlocal
					threadLocals =
						ThreadLocalRegistry.captureSnapshot();
					assertEquals(1, threadLocals.length);
					assertTrue((Boolean)threadLocals[0].get());

					cyclicBarrier.await();

					cyclicBarrier.await();
					// Sync point 3, parent thread's reset should not affect
					// child thread
					threadLocals =
						ThreadLocalRegistry.captureSnapshot();
					assertEquals(1, threadLocals.length);
					assertTrue((Boolean)threadLocals[0].get());
				}
				catch (Exception ex) {
					parentThread.interrupt();
				}
			}
		};

		childThread.start();
		cyclicBarrier.await();
		// Sync point 1, should see nothing
		ThreadLocal<?>[] threadLocals = ThreadLocalRegistry.captureSnapshot();
		assertEquals(0, threadLocals.length);

		ThreadLocal<Integer> threadLocal =
						ThreadLocalRegistry.createAndRegisterThreadLocal(0);
		threadLocals = ThreadLocalRegistry.captureSnapshot();
		assertEquals(1, threadLocals.length);
		assertEquals(0, threadLocals[0].get());
		// check programming change
		threadLocal.set(1);
		threadLocals = ThreadLocalRegistry.captureSnapshot();
		assertEquals(1, threadLocals.length);
		assertEquals(1, threadLocals[0].get());
		cyclicBarrier.await();

		// Sync point 2, should not able to see child thread's threadlocal
		threadLocals = ThreadLocalRegistry.captureSnapshot();
		assertEquals(1, threadLocals.length);
		assertEquals(1, threadLocals[0].get());
		cyclicBarrier.await();

		// check reset
		ThreadLocalRegistry.resetThreadLocals();
		threadLocals = ThreadLocalRegistry.captureSnapshot();
		assertEquals(1, threadLocals.length);
		assertEquals(0, threadLocals[0].get());
		cyclicBarrier.await();

		childThread.join();
	}

}