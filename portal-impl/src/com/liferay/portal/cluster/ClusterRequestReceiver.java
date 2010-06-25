/*
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
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jgroups.ChannelException;
import org.jgroups.Message;
import org.jgroups.View;

/**
 * <a href="ClusterRequestReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Tina Tian
 */
public class ClusterRequestReceiver extends BaseReceiver {

	ClusterRequestReceiver(ClusterExecutorImpl clusterExecutorImpl) {
		_clusterExecutorImpl = clusterExecutorImpl;
	}

	public void receive(Message message) {
		org.jgroups.Address sourceAddress = message.getSrc();

		org.jgroups.Address localAddress =
			_clusterExecutorImpl.getControlChannel().getLocalAddress();

		Object obj = message.getObject();

		if (obj == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		if (localAddress.equals(sourceAddress)) {
			boolean isProcessed = processLocalMessage(obj, sourceAddress);

			if (isProcessed) {
				return;
			}
		}

		if (obj instanceof ClusterRequest) {
			ClusterRequest clusterRequest = (ClusterRequest) obj;

			processClusterRequest(clusterRequest, sourceAddress, localAddress);
		}
		else if (obj instanceof ClusterNodeResponse) {
			ClusterNodeResponse clusterNodeResponse = (ClusterNodeResponse) obj;

			processClusterResponse(
				clusterNodeResponse, sourceAddress, localAddress);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process message content of type " +
					obj.getClass().getName());
			}
		}
	}

	public void viewAccepted(View view) {
		if (_log.isDebugEnabled()) {
			_log.debug("Accepted view " + view);
		}

		if (_lastView == null) {
			_lastView = view;

			return;
		}

		List<Address> departAddresses = getDepartAddresses(view);

		_lastView = view;

		if (departAddresses.isEmpty()) {
			return;
		}

		_clusterExecutorImpl.memberRemoved(departAddresses);
	}

	protected boolean processLocalMessage(
		Object message, org.jgroups.Address sourceAddress) {

		if (message instanceof ClusterRequest) {
			ClusterRequest clusterRequest = (ClusterRequest) message;

			ClusterMessageType requestType =
				clusterRequest.getMessageType();

			if (requestType.equals(ClusterMessageType.NOTIFY) ||
				requestType.equals(ClusterMessageType.UPDATE)) {

				ClusterNode clusterNode =
					clusterRequest.getOriginatingClusterNode();

				if (clusterNode != null) {
					Address joinAddress = new AddressImpl(sourceAddress);

					_clusterExecutorImpl.memberJoined(joinAddress, clusterNode);
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Content of Notify Message does not " +
							"contain ClusterNode information");
					}
				}

				return true;
			}
		}

		if (_clusterExecutorImpl.isShortcutLocalMethod()) {
			return true;
		}

		return false;
	}

	protected void processClusterRequest(
		ClusterRequest clusterRequest, org.jgroups.Address sourceAddress,
		org.jgroups.Address localAddress) {

		ClusterMessageType requestType = clusterRequest.getMessageType();

		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		if (requestType.equals(ClusterMessageType.NOTIFY) ||
			requestType.equals(ClusterMessageType.UPDATE)) {

			ClusterNode clusterNode = clusterRequest.getOriginatingClusterNode();

			if (clusterNode != null) {
				_clusterExecutorImpl.memberJoined(
					new AddressImpl(sourceAddress), clusterNode);

				clusterNodeResponse.setClusterNode(clusterNode);
				clusterNodeResponse.setMessageType(requestType);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Content of Notify Message does not " +
						"contain ClusterNode information");
				}

				return;
			}
		}
		else {
			clusterNodeResponse.setMulticast(clusterRequest.isMulticast());

			clusterNodeResponse.setUuid(clusterRequest.getUuid());

			clusterNodeResponse.setMessageType(ClusterMessageType.EXECUTE);

			MethodWrapper methodWrapper = clusterRequest.getMethodWrapper();

			if (methodWrapper != null) {
				try {
					Object returnValue = MethodInvoker.invoke(
						methodWrapper);

					if (returnValue instanceof Serializable) {
						clusterNodeResponse.setResult(returnValue);
					}
					else if (returnValue != null) {
						clusterNodeResponse.setException(
							new ClusterException(
								"Return value is not serializable"));
					}
				}
				catch (Exception e) {
					clusterNodeResponse.setException(e);
				}
			}
			else {
				clusterNodeResponse.setException(
					new ClusterException(
						"Payload is not of type " +
						MethodWrapper.class.getName()));
			}
		}

		try {
			_clusterExecutorImpl.getControlChannel().send(
				sourceAddress, localAddress, clusterNodeResponse);
		}
		catch (ChannelException ce) {
			_log.error(
				"Unable to send response message " +
				clusterNodeResponse, ce);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	protected void processClusterResponse(
		ClusterNodeResponse clusterNodeResponse, org.jgroups.Address sourceAddress,
		org.jgroups.Address localAddress) {

		ClusterMessageType responseType = clusterNodeResponse.getMessageType();

		if (responseType.equals(ClusterMessageType.NOTIFY) ||
			responseType.equals(ClusterMessageType.UPDATE)) {

			ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

			if (clusterNode != null) {
				Address joinAddress = new AddressImpl(sourceAddress);

				_clusterExecutorImpl.memberJoined(joinAddress, clusterNode);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Response of Notify Message does not contain " +
						"ClusterNode information");
				}
			}
			return;
		}

		String uuid = clusterNodeResponse.getUuid();

		FutureClusterResponses futureClusterResponses =
			_clusterExecutorImpl.getExecutionResults(uuid);

		Address address = new AddressImpl(sourceAddress);

		if (futureClusterResponses.expectsReply(address)) {
			futureClusterResponses.addClusterResponse(clusterNodeResponse);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Unknow UUID " + uuid + " from " + sourceAddress);
			}
		}
	}

	protected List<Address> getDepartAddresses(View view) {
		List<Address> departAddresses = new ArrayList<Address>();

		Vector<org.jgroups.Address> jGroupsAddresses = view.getMembers();
		Vector<org.jgroups.Address> lastJGroupsAddresses =
			_lastView.getMembers();

		List<org.jgroups.Address> tempAddresses =
			new ArrayList<org.jgroups.Address>(jGroupsAddresses.size());
		tempAddresses.addAll(jGroupsAddresses);

		List<org.jgroups.Address> lastAddresses =
			new ArrayList<org.jgroups.Address>(lastJGroupsAddresses.size());
		lastAddresses.addAll(lastJGroupsAddresses);

		tempAddresses.retainAll(lastJGroupsAddresses);
		lastAddresses.removeAll(tempAddresses);

		if (!lastAddresses.isEmpty()) {
			Iterator<org.jgroups.Address> itr = lastAddresses.iterator();

			while (itr.hasNext()) {
				departAddresses.add(new AddressImpl(itr.next()));
			}
		}

		return departAddresses;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterRequestReceiver.class);

	private ClusterExecutorImpl _clusterExecutorImpl;
	private View _lastView;
}