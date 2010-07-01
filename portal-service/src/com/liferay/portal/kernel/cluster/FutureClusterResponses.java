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

package com.liferay.portal.kernel.cluster;

import java.util.HashSet;
import java.util.List;
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

	public FutureClusterResponses(
		String requestUuid, List<Address> addresses,
		ClusterExecutor clusterExecutor) {

		_clusterExecutor = clusterExecutor;
		_clusterNodeResponses = new ClusterNodeResponses();
		_countDownLatch = new CountDownLatch(addresses.size());
		_expectedReplyAddress = new HashSet<Address>(addresses);
		_requestUuid = requestUuid;
	}

	public void addClusterNodeResponse(
		ClusterNodeResponse clusterNodeResponse) {

		_clusterNodeResponses.addClusterResponse(clusterNodeResponse);

		_countDownLatch.countDown();

		if (isDone()) {
			completeRequest();
		}
	}

	public void addExpectedReplyAddress(Address address) {
		_expectedReplyAddress.add(address);
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		if (_cancelled || isDone()) {
			return false;
		}

		_cancelled = true;

		completeRequest();

		return true;
	}

	public void completeRequest() {
		_clusterExecutor.requestComplete(_requestUuid);
	}

	public boolean expectsReply(Address address) {
		return _expectedReplyAddress.contains(address);
	}

	public ClusterNodeResponses get() throws InterruptedException {
		try {
			if (_cancelled) {
				throw new CancellationException();
			}

			_countDownLatch.await();

			return _clusterNodeResponses;
		}
		finally {
			completeRequest();
		}
	}

	public ClusterNodeResponses get(long timeout, TimeUnit timeUnit)
		throws InterruptedException, TimeoutException {

		try {
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
		finally {
			completeRequest();
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
	private ClusterExecutor _clusterExecutor;
	private ClusterNodeResponses _clusterNodeResponses;
	private CountDownLatch _countDownLatch;
	private Set<Address> _expectedReplyAddress;
	private String _requestUuid;

}