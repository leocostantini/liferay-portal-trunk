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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * <a href="InformixDB.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neil Griffin
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class InformixDB extends BaseDB {

	public static DB getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = removeNull(template);

		return template;
	}

	protected InformixDB() {
		super(TYPE_INFORMIX);
	}

	protected String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBundler sb = new StringBundler(22);

		sb.append("database sysmaster;\n");
		sb.append("drop database ");
		sb.append(databaseName);
		sb.append(";\n");
		sb.append("create database ");
		sb.append(databaseName);
		sb.append(" WITH LOG;\n");
		sb.append("\n");
		sb.append("create procedure 'lportal'.isnull(test_string varchar)\n");
		sb.append("returning boolean;\n");
		sb.append("IF test_string IS NULL THEN\n");
		sb.append("\tRETURN 't';\n");
		sb.append("ELSE\n");
		sb.append("\tRETURN 'f';\n");
		sb.append("END IF\n");
		sb.append("end procedure;\n");
		sb.append("\n\n");
		sb.append(
			FileUtil.read(
				sqlDir + "/portal" + suffix + "/portal" + suffix +
					"-informix.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read(sqlDir + "/indexes/indexes-informix.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read(sqlDir + "/sequences/sequences-informix.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "informix";
	}

	protected String[] getTemplate() {
		return _INFORMIX_TEMPLATE;
	}

	protected String reword(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBundler sb = new StringBundler();

		String line = null;

		boolean createTable = false;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"rename column @table@.@old-column@ TO @new-column@;",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify (@old-column@ @type@);",
					REWORD_TEMPLATE, template);
			}
			else if (line.indexOf(DROP_INDEX) != -1) {
				String[] tokens = StringUtil.split(line, " ");

				line = StringUtil.replace(
					"drop index @index@;", "@index@", tokens[2]);
			}
			else if (line.indexOf("typeSettings text") > 0) {
				line = StringUtil.replace(
					line, "typeSettings text", "typeSettings lvarchar(4096)");
			}
			else if (line.indexOf("varchar(300)") > 0) {
				line = StringUtil.replace(
					line, "varchar(300)", "lvarchar(300)");
			}
			else if (line.indexOf("varchar(500)") > 0) {
				line = StringUtil.replace(
					line, "varchar(500)", "lvarchar(500)");
			}
			else if (line.indexOf("varchar(1000)") > 0) {
				line = StringUtil.replace(
					line, "varchar(1000)", "lvarchar(1000)");
			}
			else if (line.indexOf("varchar(1024)") > 0) {
				line = StringUtil.replace(
					line, "varchar(1024)", "lvarchar(1024)");
			}
			else if (line.indexOf("1970-01-01") > 0) {
				line = StringUtil.replace(
					line, "1970-01-01", "1970-01-01 00:00:00.0");
			}
			else if (line.indexOf("create table") >= 0) {
				createTable = true;
			}
			else if ((line.indexOf(");") >= 0) && createTable) {
				line = StringUtil.replace(
					line, ");",
					")\nextent size 16 next size 16\nlock mode row;");

				createTable = false;
			}
			else if (line.indexOf("commit;") >= 0) {
				line = StringPool.BLANK;
			}

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	private static String[] _INFORMIX_TEMPLATE = {
		"--", "'T'", "'F'",
		"'1970-01-01'", "CURRENT YEAR TO FRACTION",
		" byte in table", " boolean", " datetime YEAR TO FRACTION",
		" float", " int", " int8",
		" lvarchar", " text", " varchar",
		"", "commit"
	};

	private static InformixDB _instance = new InformixDB();

}