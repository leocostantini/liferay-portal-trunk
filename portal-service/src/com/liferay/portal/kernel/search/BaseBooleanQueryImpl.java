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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <a href="BaseBooleanQueryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class BaseBooleanQueryImpl implements BooleanQuery{

	public void addTerms(String[] fields, String values) throws ParseException {
		if (Validator.isNull(values)) {
			return;
		}

		if (fields == null) {
			fields = new String[0];
		}

		Map<String, List<String>> termFieldsValuesMap = mapTermFieldsValues(
			fields, values);

		List<String> valuesList = termFieldsValuesMap.remove("no_field");

		if (!valuesList.isEmpty()) {
			String value = valuesList.get(0);

			for (String field : fields) {
				addTerm(field, value);
			}
		}

		addTerms(fields, termFieldsValuesMap);
	}

	protected void addTerms(
			String[] fields, Map<String, List<String>> termFieldsValuesMap)
		throws ParseException {

		for (String field : fields) {
			List<String> valuesList = termFieldsValuesMap.get(field);

			for (String value : valuesList) {
				addTerm(field, value);
			}
		}
	}

	protected Map<String, List<String>> mapTermFieldsValues(
			String[] fields, String values)
		throws ParseException {

		Map<String, List<String>> termFieldsValuesMap =
			new HashMap<String, List<String>>();

		for (String field : fields) {
			List<String> valuesList = new ArrayList<String>();

			values = mapTermFieldValues(
				field, values, valuesList,
				"(?i)^.*" + field + ":([\"\'])(.+?)(\\1).*$", "$1$2$3");

			values = mapTermFieldValues(
				field, values, valuesList,
				"(?i)^.*" + field + ":([^\\s\"']*).*$", "$1");

			termFieldsValuesMap.put(field, valuesList);
		}

		values = values.trim();

		List<String> valuesList = new ArrayList<String>();

		if (Validator.isNotNull(values)) {
			values = mapTermFieldValues(
				null, values, valuesList,
				"^[^\"\']*([\"\'])(.+?)(\\1)[^\"\']*$", "$1$2$3");

			valuesList.add(values);
		}

		termFieldsValuesMap.put("no_field", valuesList);

		return termFieldsValuesMap;
	}

	protected String mapTermFieldValues(
			String field, String values, List<String> valuesList,
			String pattern, String replacement) {

		if (Validator.isNull(values)) {
			return values;
		}

		if (Validator.isNull(pattern) || Validator.isNull(replacement)) {
			return values;
		}

		if (Validator.isNotNull(field)) {
			field += ":";
		}
		else {
			field = "";
		}

		while (values.matches(pattern)) {
			String value = values.replaceAll(pattern, replacement);

			valuesList.add(value);

			String duplicate =
				"(?i)\\s*" + Pattern.quote(field + value) + "\\s*";

			values = values.replaceAll(duplicate, StringPool.SPACE);
			values = values.trim();
		}

		return values;
	}

}