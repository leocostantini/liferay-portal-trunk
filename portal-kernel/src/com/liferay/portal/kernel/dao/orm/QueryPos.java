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

package com.liferay.portal.kernel.dao.orm;

import java.sql.Timestamp;

/**
 * <a href="QueryPos.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class QueryPos {

	public static QueryPos getInstance(Query query) {
		return new QueryPos(query);
	}

	public void add(boolean value) {
		_query.setBoolean(_pos++, value);
	}

	public void add(Boolean value) {
		if (value != null) {
			_query.setBoolean(_pos++, value.booleanValue());
		}
		else {
			addNull();
		}
	}

	public void add(boolean[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(boolean[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Boolean[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Boolean[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(double value) {
		_query.setDouble(_pos++, value);
	}

	public void add(Double value) {
		if (value != null) {
			_query.setDouble(_pos++, value.doubleValue());
		}
		else {
			addNull();
		}
	}

	public void add(double[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(double[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Double[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Double[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(float value) {
		_query.setFloat(_pos++, value);
	}

	public void add(Float value) {
		if (value != null) {
			_query.setFloat(_pos++, value.intValue());
		}
		else {
			addNull();
		}
	}

	public void add(float[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(float[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Float[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Float[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(int value) {
		_query.setInteger(_pos++, value);
	}

	public void add(int[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(int[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Integer value) {
		if (value != null) {
			_query.setInteger(_pos++, value.intValue());
		}
		else {
			addNull();
		}
	}

	public void add(Integer[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Integer[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(long value) {
		_query.setLong(_pos++, value);
	}

	public void add(Long value) {
		if (value != null) {
			_query.setLong(_pos++, value.longValue());
		}
		else {
			addNull();
		}
	}

	public void add(long[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(long[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Long[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Long[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(short value) {
		_query.setShort(_pos++, value);
	}

	public void add(Short value) {
		if (value != null) {
			_query.setShort(_pos++, value.shortValue());
		}
		else {
			addNull();
		}
	}

	public void add(short[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(short[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Short[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Short[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(String value) {
		_query.setString(_pos++, value);
	}

	public void add(String[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(String[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public void add(Timestamp value) {
		_query.setTimestamp(_pos++, value);
	}

	public void add(Timestamp[] values) {
		add(values, _DEFAULT_ARRAY_COUNT);
	}

	public void add(Timestamp[] values, int count) {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < count; j++) {
				add(values[i]);
			}
		}
	}

	public int getPos() {
		return _pos;
	}

	private void addNull() {
		_query.setSerializable(_pos++, null);
	}

	private QueryPos(Query query) {
		_query = query;
	}

	private static final int _DEFAULT_ARRAY_COUNT = 1;

	private int _pos;
	private Query _query;

}