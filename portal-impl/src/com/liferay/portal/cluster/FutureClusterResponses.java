/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;

import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <a href="FutureClusterResponses.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class FutureClusterResponses implements Future<ClusterNodeResponses> {

	public FutureClusterResponses(int count) {
		_countDownLatch = new CountDownLatch(count);
		_clusterNodeResponses = new ClusterNodeResponses();
	}

	public void addClusterNodeResponse(
		ClusterNodeResponse clusterNodeResponse) {

		_clusterNodeResponses.addClusterResponse(clusterNodeResponse);

		_countDownLatch.countDown();
	}

	public void addExpectedReplyAddress(Address address) {
		_expectedReplyAddress.add(address);
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		if (_cancelled || isDone()) {
			return false;
		}

		_cancelled = true;

		return true;
	}

	public boolean expectsReply(Address address) {
		return _expectedReplyAddress.contains(address);
	}

	public ClusterNodeResponses get() throws InterruptedException {
		if (_cancelled) {
			throw new CancellationException();
		}

		_countDownLatch.await();

		return _clusterNodeResponses;
	}

	public ClusterNodeResponses get(long timeout, TimeUnit timeUnit)
		throws InterruptedException, TimeoutException {

		if (_cancelled) {
			throw new CancellationException();
		}

		if (_countDownLatch.await(timeout, timeUnit)) {
			return _clusterNodeResponses;
		}
		else {
			throw new TimeoutException();
		}
	}

	public ClusterNodeResponses getPartialResults() {
		return _clusterNodeResponses;
	}

	public boolean isCancelled() {
		return _cancelled;
	}

	public boolean isDone() {
		if ((_countDownLatch.getCount() == 0) || _cancelled) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _cancelled;
	private ClusterNodeResponses _clusterNodeResponses;
	private CountDownLatch _countDownLatch;
	private Set<Address> _expectedReplyAddress;

}