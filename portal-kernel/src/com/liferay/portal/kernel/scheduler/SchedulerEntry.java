/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="SchedulerEntry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public interface SchedulerEntry {

	public String getDescription();

	public MessageListener getEventListener();

	public String getEventListenerClass();

	public String getTimeUnit();

	public Trigger getTrigger() throws SystemException;

	public TriggerType getTriggerType();

	public String getTriggerValue();

	public boolean isReadProperty();

	public void setDescription(String description);

	public void setEventListener(MessageListener eventListener);

	public void setEventListenerClass(String eventListenerClass);

	public void setReadProperty(boolean readProperty);

	public void setTimeUnit(String timeUnit);

	public void setTriggerType(TriggerType triggerType);

	public void setTriggerValue(String triggerValue);

}