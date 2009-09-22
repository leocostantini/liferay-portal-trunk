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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

/**
 * <a href="WorkflowDefinitionComparator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class WorkflowDefinitionComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "name ASC, version ASC";

	public static String ORDER_BY_DESC = "name DESC, version DESC";

	public static String[] ORDER_BY_FIELDS = {"name", "version"};

	public WorkflowDefinitionComparator() {
		this(false);
	}

	public WorkflowDefinitionComparator(boolean asc) {
		_asc = asc;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		WorkflowDefinition workflowDefinition1 = (WorkflowDefinition)obj1;
		WorkflowDefinition workflowDefinition2 = (WorkflowDefinition)obj2;

		String name1 = workflowDefinition1.getWorkflowDefinitionName();
		String name2 = workflowDefinition2.getWorkflowDefinitionName();

		int value = name1.compareTo(name2);

		if (value != 0) {
			if (_asc) {
				return value;
			}
			else {
				return -value;
			}
		}

		Integer version1 = workflowDefinition1.getWorkflowDefinitionVersion();
		Integer version2 = workflowDefinition2.getWorkflowDefinitionVersion();

		value = version1.compareTo(version2);

		if (_asc) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_asc) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _asc;
	}

	private boolean _asc;

}