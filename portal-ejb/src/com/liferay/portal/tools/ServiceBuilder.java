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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.ArrayUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.TextFormatter;
import com.liferay.util.Time;
import com.liferay.util.Validator;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import de.hunsicker.io.FileFormat;
import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="ServiceBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 * @author Harry Mark
 *
 */
public class ServiceBuilder {

	public static void main(String[] args) {
		if (args.length == 8) {
			new ServiceBuilder(
				args[0], args[1], args[2], args[3], args[4], args[5], args[6],
				args[7]);
		}
		else {
			System.out.println(
				"Please pass in the correct number of parameters. Sample " +
					"parameters are:\n" +
				"\tservice.xml\n" +
				"\tclasses/META-INF/portal-hbm.xml\n" +
				"\tclasses/META-INF/portal-model-hints.xml\n" +
				"\tclasses/META-INF/portal-spring-enterprise.xml\n" +
				"\tclasses/META-INF/portal-spring-professional.xml\n" +
				"\tcom.liferay.portal.kernel.bean.BeanLocatorUtil\n" +
				"\t../portal-service/src\n" +
				"\t../portal-web/docroot/html/js/liferay_services.js");
		}
	}

	public static Set getBadCmpFields() {
		Set badCmpFields = new HashSet();

		badCmpFields.add("access");
		badCmpFields.add("active");
		badCmpFields.add("alias");
		badCmpFields.add("date");
		badCmpFields.add("end");
		badCmpFields.add("idd");
		badCmpFields.add("featured");
		badCmpFields.add("fields");
		badCmpFields.add("from");
		badCmpFields.add("hidden");
		badCmpFields.add("index");
		badCmpFields.add("internal");
		badCmpFields.add("interval");
		badCmpFields.add("join");
		badCmpFields.add("key");
		badCmpFields.add("log");
		badCmpFields.add("number");
		badCmpFields.add("password");
		badCmpFields.add("primary");
		badCmpFields.add("sale");
		badCmpFields.add("settings");
		badCmpFields.add("size");
		badCmpFields.add("start");
		badCmpFields.add("text");
		badCmpFields.add("to");
		badCmpFields.add("type");
		badCmpFields.add("values");

		return badCmpFields;
	}

	public static Set getBadTableNames() {
		Set badTableNames = new HashSet();

		badTableNames = new HashSet();
		badTableNames.add("Account");
		badTableNames.add("Action");
		badTableNames.add("Cache");
		badTableNames.add("Contact");
		badTableNames.add("Group");
		badTableNames.add("Organization");
		badTableNames.add("Permission");
		badTableNames.add("Release");
		badTableNames.add("Resource");
		badTableNames.add("Role");
		badTableNames.add("User");

		return badTableNames;
	}

	public static void writeFile(File file, String content) throws IOException {
		writeFile(file, content, null);
	}

	public static void writeFile(File file, String content, Map jalopySettings)
		throws IOException {

		File tempFile = new File("ServiceBuilder.temp");

		FileUtil.write(tempFile, content);

		/*if (file.getName().equals("ShoppingItemPersistence.java")) {
			FileUtil.write(file, content);

			return;
		}*/

		// Strip unused imports

		String[] checkImports = new String[] {
			"com.liferay.portal.PortalException",
			"com.liferay.portal.kernel.log.Log",
			"com.liferay.portal.kernel.log.LogFactoryUtil",
			"com.liferay.portal.kernel.util.BooleanWrapper",
			"com.liferay.portal.kernel.util.DoubleWrapper",
			"com.liferay.portal.kernel.util.FloatWrapper",
			"com.liferay.portal.kernel.util.IntegerWrapper",
			"com.liferay.portal.kernel.util.LongWrapper",
			"com.liferay.portal.kernel.util.MethodWrapper",
			"com.liferay.portal.kernel.util.NullWrapper",
			"com.liferay.portal.kernel.util.OrderByComparator",
			"com.liferay.portal.kernel.util.ShortWrapper",
			"com.liferay.portal.kernel.util.StringMaker",
			"com.liferay.portal.kernel.util.StringPool",
			"com.liferay.portal.security.auth.HttpPrincipal",
			"com.liferay.portal.service.http.TunnelUtil",
			"com.liferay.portal.service.impl.PrincipalSessionBean",
			"com.liferay.portal.spring.hibernate.HibernateUtil",
			"com.liferay.portal.util.PropsUtil",
			"com.liferay.util.DateUtil",
			"com.liferay.util.GetterUtil",
			"com.liferay.util.InstancePool",
			"com.liferay.util.dao.hibernate.QueryPos",
			"com.liferay.util.dao.hibernate.QueryUtil",
			"java.rmi.RemoteException",
			"java.sql.ResultSet",
			"java.sql.SQLException",
			"java.sql.Types",
			"java.util.Collection",
			"java.util.Collections",
			"java.util.Date",
			"java.util.HashSet",
			"java.util.Iterator",
			"java.util.List",
			"java.util.Properties",
			"java.util.Set",
			"javax.sql.DataSource",
			"org.apache.commons.logging.Log",
			"org.apache.commons.logging.LogFactory",
			"org.hibernate.Hibernate",
			"org.hibernate.ObjectNotFoundException",
			"org.hibernate.Query",
			"org.hibernate.SQLQuery",
			"org.json.JSONArray",
			"org.json.JSONObject",
			"org.springframework.dao.DataAccessException",
			"org.springframework.jdbc.core.SqlParameter",
			"org.springframework.jdbc.object.MappingSqlQuery",
			"org.springframework.jdbc.object.SqlUpdate"
		};

		Set classes = ClassUtil.getClasses(tempFile);

		for (int i = 0; i < checkImports.length; i++) {
			String importClass = checkImports[i].substring(
				checkImports[i].lastIndexOf(".") + 1, checkImports[i].length());

			if (!classes.contains(importClass)) {
				content = StringUtil.replace(
					content, "import " + checkImports[i] + ";", "");
			}
		}

		FileUtil.write(tempFile, content);

		// Beautify

		StringBuffer sb = new StringBuffer();

		Jalopy jalopy = new Jalopy();

		jalopy.setFileFormat(FileFormat.UNIX);
		jalopy.setInput(tempFile);
		jalopy.setOutput(sb);

		try {
			Jalopy.setConvention("../tools/jalopy.xml");
		}
		catch (FileNotFoundException fnne) {
		}

		if (jalopySettings == null) {
			jalopySettings = new HashMap();
		}

		Environment env = Environment.getInstance();

		// Author

		String author = GetterUtil.getString(
			(String)jalopySettings.get("author"), "Brian Wing Shun Chan");

		env.set("author", author);

		// File name

		env.set("fileName", file.getName());

		Convention convention = Convention.getInstance();

		String classMask =
			"/**\n" +
			" * <a href=\"$fileName$.html\"><b><i>View Source</i></b></a>\n" +
			" *\n";

		String[] classCommentsArray = (String[])jalopySettings.get("classComments");

		if ((classCommentsArray != null) && (classCommentsArray.length > 0)) {
			for (int i = 0; i < classCommentsArray.length; i++) {
				String classComments = classCommentsArray[i];

				//classComments = "The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.";
				classComments = StringUtil.wrap(classComments, 76, "\n * ");

				if (classComments.startsWith("\n")) {
					classComments = classComments.substring(
						1, classComments.length());
				}

				classMask += " * <p>\n" + classComments + "\n * </p>\n *\n";
			}
		}

		classMask +=
			" * @author $author$\n" +
			" *\n";

		String[] seeArray = (String[])jalopySettings.get("see");

		if ((classCommentsArray != null) && (classCommentsArray.length > 0)) {
			for (int i = 0; i < seeArray.length; i++) {
				String see = seeArray[i];

				classMask += " * @see " + see + "\n";
			}

			classMask += " *\n";
		}

		classMask += " */";

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS,
			env.interpolate(classMask));

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE,
			env.interpolate(classMask));

		jalopy.format();

		String newContent = sb.toString();

		/*
		// Remove blank lines after try {

		newContent = StringUtil.replace(newContent, "try {\n\n", "try {\n");

		// Remove blank lines after ) {

		newContent = StringUtil.replace(newContent, ") {\n\n", ") {\n");

		// Remove blank lines empty braces { }

		newContent = StringUtil.replace(newContent, "\n\n\t}", "\n\t}");

		// Add space to last }

		newContent = newContent.substring(0, newContent.length() - 2) + "\n\n}";
		*/

		// Write file if and only if the file has changed

		String oldContent = null;

		if (file.exists()) {

			// Read file

			oldContent = FileUtil.read(file);

			// Keep old version number

			int x = oldContent.indexOf("@version $Revision:");

			if (x != -1) {
				int y = oldContent.indexOf("$", x);
				y = oldContent.indexOf("$", y + 1);

				String oldVersion = oldContent.substring(x, y + 1);

				newContent = StringUtil.replace(
					newContent, "@version $Rev: $", oldVersion);
			}
		}
		else {
			newContent = StringUtil.replace(
				newContent, "@version $Rev: $", "@version $Revision: 1.183 $");
		}

		if (oldContent == null || !oldContent.equals(newContent)) {
			FileUtil.write(file, newContent);

			System.out.println("Writing " + file);

			// Workaround for bug with XJavaDoc

			file.setLastModified(
				System.currentTimeMillis() - (Time.SECOND * 5));
		}

		tempFile.deleteOnExit();
	}

	public ServiceBuilder(String fileName, String hbmFileName,
						  String modelHintsFileName, String springEntFileName,
						  String springProFileName,
						  String beanLocatorUtilClassName, String serviceDir,
						  String jsonFileName) {

		new ServiceBuilder(
			fileName, hbmFileName, modelHintsFileName, springEntFileName,
			springProFileName, beanLocatorUtilClassName, serviceDir,
			jsonFileName, true);
	}

	public ServiceBuilder(String fileName, String hbmFileName,
						  String modelHintsFileName, String springEntFileName,
						  String springProFileName,
						  String beanLocatorUtilClassName, String serviceDir,
						  String jsonFileName, boolean build) {

		try {
			_badTableNames = ServiceBuilder.getBadTableNames();
			_badCmpFields = ServiceBuilder.getBadCmpFields();

			_hbmFileName = hbmFileName;
			_modelHintsFileName = modelHintsFileName;
			_springEntFileName = springEntFileName;
			_springProFileName = springProFileName;
			_beanLocatorUtilClassName = beanLocatorUtilClassName;
			_serviceDir = serviceDir;

			SAXReader reader = SAXReaderFactory.getInstance();

			Document doc = reader.read(new File(fileName));

			Element root = doc.getRootElement();

			_portalRoot = root.attributeValue("root-dir");

			String packagePath = root.attributeValue("package-path");

			_portletName = root.element("portlet").attributeValue("name");

			_portletShortName =
				root.element("portlet").attributeValue("short-name");

			_portletPackageName =
				TextFormatter.format(_portletName, TextFormatter.B);

			_outputPath =
				"src/" + StringUtil.replace(packagePath, ".", "/") + "/" +
					_portletPackageName;

			if (Validator.isNull(_serviceDir)) {
				_serviceOutputPath = _outputPath;
			}
			else {
				_serviceOutputPath =
					_serviceDir + "/" +
						StringUtil.replace(packagePath, ".", "/") + "/" +
							_portletPackageName;
			}

			_jsonFileName = jsonFileName;

			_packagePath = packagePath + "." + _portletPackageName;

			_ejbList = new ArrayList();

			List entities = root.elements("entity");

			Iterator itr1 = entities.iterator();

			while (itr1.hasNext()) {
				Element entityEl = (Element)itr1.next();

				String ejbName = entityEl.attributeValue("name");

				String table = entityEl.attributeValue("table");
				if (Validator.isNull(table)) {
					table = ejbName;

					if (_badTableNames.contains(ejbName)) {
						table += "_";
					}
				}

				boolean localService = GetterUtil.getBoolean(
					entityEl.attributeValue("local-service"), false);
				boolean remoteService = GetterUtil.getBoolean(
					entityEl.attributeValue("remote-service"), true);
				String persistenceClass = GetterUtil.getString(
					entityEl.attributeValue("persistence-class"),
					_packagePath + ".service.persistence."+ ejbName +
						"Persistence");
				String dataSource = entityEl.attributeValue("data-source");
				String sessionFactory = entityEl.attributeValue(
					"session-factory");
				String txManager = entityEl.attributeValue(
					"tx-manager");

				Iterator itr2 = null;

				List pkList = new ArrayList();
				List regularColList = new ArrayList();
				List collectionList = new ArrayList();
				List columnList = new ArrayList();

				List columns = entityEl.elements("column");

				itr2 = columns.iterator();

				while (itr2.hasNext()) {
					Element column = (Element)itr2.next();

					String columnName = column.attributeValue("name");

					String columnDBName = column.attributeValue("db-name");

					if (Validator.isNull(columnDBName)) {
						columnDBName = columnName;

						if (_badCmpFields.contains(columnName)) {
							columnDBName += "_";
						}
					}

					String columnType = column.attributeValue("type");
					boolean primary = GetterUtil.getBoolean(
						column.attributeValue("primary"), false);
					String collectionEntity = column.attributeValue("entity");
					String mappingKey = column.attributeValue("mapping-key");
					String mappingTable = column.attributeValue("mapping-table");
					String idType = column.attributeValue("id-type");
					String idParam = column.attributeValue("id-param");
					boolean convertNull = GetterUtil.getBoolean(
						column.attributeValue("convert-null"), true);

					EntityColumn col = new EntityColumn(
						columnName, columnDBName, columnType, primary,
						collectionEntity, mappingKey, mappingTable, idType,
						idParam, convertNull);

					if (primary) {
						pkList.add(col);
					}

					if (columnType.equals("Collection")) {
						collectionList.add(col);
					}
					else {
						regularColList.add(col);
					}

					columnList.add(col);
				}

				EntityOrder order = null;

				Element orderEl = entityEl.element("order");

				if (orderEl != null) {
					boolean asc = true;
					if ((orderEl.attribute("by") != null) &&
						(orderEl.attributeValue("by").equals("desc"))) {

						asc = false;
					}

					List orderColsList = new ArrayList();

					order = new EntityOrder(asc, orderColsList);

					List orderCols = orderEl.elements("order-column");

					Iterator itr3 = orderCols.iterator();

					while (itr3.hasNext()) {
						Element orderColEl = (Element)itr3.next();

						String orderColName =
							orderColEl.attributeValue("name");
						boolean orderColCaseSensitive = GetterUtil.getBoolean(
							orderColEl.attributeValue("case-sensitive"),
							true);

						boolean orderColByAscending = asc;
						String orderColBy = GetterUtil.getString(
							orderColEl.attributeValue("order-by"));
						if (orderColBy.equals("asc")) {
							orderColByAscending = true;
						}
						else if (orderColBy.equals("desc")) {
							orderColByAscending = false;
						}

						EntityColumn col = Entity.getColumn(
							orderColName, columnList);

						col = (EntityColumn)col.clone();

						col.setCaseSensitive(orderColCaseSensitive);
						col.setOrderByAscending(orderColByAscending);

						orderColsList.add(col);
					}
				}

				List finderList = new ArrayList();

				List finders = entityEl.elements("finder");

				itr2 = finders.iterator();

				while (itr2.hasNext()) {
					Element finderEl = (Element)itr2.next();

					String finderName = finderEl.attributeValue("name");
					String finderReturn =
						finderEl.attributeValue("return-type");
					String finderWhere =
						finderEl.attributeValue("where");
					boolean finderDBIndex = GetterUtil.getBoolean(
						finderEl.attributeValue("db-index"), true);

					List finderColsList = new ArrayList();

					List finderCols = finderEl.elements("finder-column");

					Iterator itr3 = finderCols.iterator();

					while (itr3.hasNext()) {
						Element finderColEl = (Element)itr3.next();

						String finderColName =
							finderColEl.attributeValue("name");

						String finderColDBName =
							finderColEl.attributeValue("db-name");

						if (Validator.isNull(finderColDBName)) {
							finderColDBName = finderColName;

							if (_badCmpFields.contains(finderColName)) {
								finderColDBName += "_";
							}
						}

						String finderColComparator = GetterUtil.getString(
							finderColEl.attributeValue("comparator"), "=");

						EntityColumn col = Entity.getColumn(
							finderColName, columnList);

						col = (EntityColumn)col.clone();

						col.setDBName(finderColDBName);
						col.setComparator(finderColComparator);

						finderColsList.add(col);
					}

					finderList.add(new EntityFinder(
						finderName, finderReturn, finderColsList, finderWhere,
						finderDBIndex));
				}

				List referenceList = new ArrayList();

				if (build) {
					List references = entityEl.elements("reference");

					itr2 = references.iterator();

					while (itr2.hasNext()) {
						Element reference = (Element)itr2.next();

						String refPackage =
							reference.attributeValue("package-path");
						String refEntity = reference.attributeValue("entity");

						if ((refPackage == null) || (refEntity == null)) {
							referenceList.add(reference.getText().trim());
						}
						else {
							referenceList.add(
								getEntity(refPackage + "." + refEntity));
						}
					}
				}

				_ejbList.add(
					new Entity(
						_packagePath, _portletName, _portletShortName, ejbName,
						table, localService, remoteService, persistenceClass,
						dataSource, sessionFactory, txManager, pkList,
						regularColList, collectionList, columnList, order,
						finderList, referenceList));
			}

			List exceptionList = new ArrayList();

			if (root.element("exceptions") != null) {
				List exceptions =
					root.element("exceptions").elements("exception");

				itr1 = exceptions.iterator();

				while (itr1.hasNext()) {
					Element exception = (Element)itr1.next();

					exceptionList.add(exception.getText());
				}
			}

			if (build) {
				for (int x = 0; x < _ejbList.size(); x++) {
					Entity entity = (Entity)_ejbList.get(x);

					System.out.println("Building " + entity.getName());

					if (true ||
						entity.getName().equals("EmailAddress") ||
						entity.getName().equals("User")) {

						if (entity.hasColumns()) {
							_createHBM(entity);
							_createHBMUtil(entity);

							_createPersistence(entity);
							_createPersistenceUtil(entity);

							_createModelImpl(entity);
							_createExtendedModelImpl(entity);

							_createModel(entity);
							_createExtendedModel(entity);

							_createModelSoap(entity);

							_createPool(entity);

							if (entity.getPKList().size() > 1) {
								_createEJBPK(entity);
							}
						}

						if (entity.hasLocalService()) {
							_createServiceBaseImpl(entity);
							_createServiceImpl(entity, _LOCAL);
							_createService(entity, _LOCAL);
							_createServiceEJB(entity, _LOCAL);
							_createServiceEJBImpl(entity, _LOCAL);
							_createServiceHome(entity, _LOCAL);
							_createServiceFactory(entity, _LOCAL);
							_createServiceUtil(entity, _LOCAL);
						}

						if (entity.hasRemoteService()) {
							_createServiceImpl(entity, _REMOTE);
							_createService(entity, _REMOTE);
							_createServiceEJB(entity, _REMOTE);
							_createServiceEJBImpl(entity, _REMOTE);
							_createServiceHome(entity, _REMOTE);
							_createServiceFactory(entity, _REMOTE);
							_createServiceUtil(entity, _REMOTE);

							_createServiceHttp(entity);
							_createServiceJSON(entity);

							if (entity.hasColumns()) {
								_createServiceJSONSerializer(entity);
							}

							_createServiceSoap(entity);
						}
					}
				}

				_createEJBXML();
				_createHBMXML();
				_createJSONJS();
				_createModelHintsXML();
				_createSpringXML(true);
				_createSpringXML(false);

				_createSQLIndexes();
				_createSQLTables();
				_createSQLSequences();

				_createExceptions(exceptionList);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entity getEntity(String name) throws IOException {
		int pos = name.lastIndexOf(".");

		if (pos == -1) {
			pos = _ejbList.indexOf(new Entity(name));

			return (Entity)_ejbList.get(pos);
		}
		else {
			String refPackage = name.substring(0, pos);
			String refPackageDir = StringUtil.replace(refPackage, ".", "/");
			String refEntity = name.substring(pos + 1, name.length());
			String refFileName = "src/" + refPackageDir + "/service.xml";

			File refFile = new File(refFileName);

			boolean useTempFile = false;

			if (!refFile.exists()) {
				refFileName = Time.getTimestamp();
				refFile = new File(refFileName);

				ClassLoader classLoader = getClass().getClassLoader();

				FileUtil.write(
					refFileName,
					StringUtil.read(
						classLoader, refPackageDir + "/service.xml"));

				useTempFile = true;
			}

			ServiceBuilder serviceBuilder = new ServiceBuilder(
				refFileName, _hbmFileName, _modelHintsFileName,
				_springEntFileName, _springProFileName,
				_beanLocatorUtilClassName, _serviceDir, _jsonFileName, false);

			Entity entity = serviceBuilder.getEntity(refEntity);

			if (useTempFile) {
				refFile.deleteOnExit();
			}

			return entity;
		}
	}

	private void _appendNullLogic(EntityColumn col, StringMaker sm) {
		sm.append("if (" + col.getName() + " == null) {");

		if (col.getComparator().equals("=")) {
			sm.append("query.append(\"" + col.getDBName() + " IS NULL\");");
		}
		else if (col.getComparator().equals("<>") || col.getComparator().equals("!=")) {
			sm.append("query.append(\"" + col.getDBName() + " IS NOT NULL\");");
		}
		else {
			sm.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " null\");");
		}

		sm.append("} else {");
	}

	private void _createEJBPK(Entity entity) throws IOException {
		List pkList = entity.getPKList();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sm.append("import com.liferay.portal.kernel.util.StringMaker;");
		sm.append("import com.liferay.portal.kernel.util.StringPool;");
		sm.append("import com.liferay.util.DateUtil;");
		sm.append("import java.io.Serializable;");
		sm.append("import java.util.Date;");

		// Class declaration

		sm.append("public class " + entity.getPKClassName() + " implements Comparable, Serializable {");

		// Fields

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sm.append("public " + col.getType() + " " + col.getName() + ";");
		}

		// Default constructor

		sm.append("public " + entity.getPKClassName() + "() {}");

		// Primary key constructor

		sm.append("public " + entity.getPKClassName() + "(");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sm.append(col.getType() + " " + col.getName());

			if ((i + 1) != pkList.size()) {
				sm.append(", ");
			}
		}

		sm.append(") {");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sm.append("this." + col.getName() + " = " + col.getName() + ";");
		}

		sm.append("}");

		// Getter and setter methods

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (!col.isCollection()) {
				sm.append("public " + col.getType() + " get" + col.getMethodName() + "() {");
				sm.append("return " + col.getName() + ";");
				sm.append("}");

				sm.append("public void set" + col.getMethodName() + "(" + col.getType() + " " + col.getName() + ") {");
				sm.append("this." + col.getName() + " = " + col.getName() + ";");
				sm.append("}");
			}
		}

		// Compare to method

		sm.append("public int compareTo(Object obj) {");
		sm.append("if (obj == null) {");
		sm.append("return -1;");
		sm.append("}");
		sm.append(entity.getPKClassName() + " pk = (" + entity.getPKClassName() + ")obj;");
		sm.append("int value = 0;");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			String colType = col.getType();

			if (!col.isPrimitiveType()) {
				if (colType.equals("Date")) {
					sm.append("value = DateUtil.compareTo(" + col.getName() + ", pk." + col.getName() + ");");
				}
				else {
					sm.append("value = " + col.getName() + ".compareTo(pk." + col.getName() + ");");
				}
			}
			else {
				if (colType.equals("boolean")) {
					sm.append("if (!" + col.getName() + " && pk." + col.getName() + ") {");
					sm.append("value = -1;");
					sm.append("}");
					sm.append("else if (" + col.getName() + " && !pk." + col.getName() + ") {");
					sm.append("value = 1;");
					sm.append("}");
					sm.append("else {");
					sm.append("value = 0;");
					sm.append("}");
				}
				else {
					sm.append("if (" + col.getName() + " < pk." + col.getName() + ") {");
					sm.append("value = -1;");
					sm.append("}");
					sm.append("else if (" + col.getName() + " > pk." + col.getName() + ") {");
					sm.append("value = 1;");
					sm.append("}");
					sm.append("else {");
					sm.append("value = 0;");
					sm.append("}");
				}
			}

			sm.append("if (value != 0) {");
			sm.append("return value;");
			sm.append("}");
		}

		sm.append("return 0;");
		sm.append("}");

		// Equals method

		sm.append("public boolean equals(Object obj) {");
		sm.append("if (obj == null) {");
		sm.append("return false;");
		sm.append("}");
		sm.append(entity.getPKClassName() + " pk = null;");
		sm.append("try {");
		sm.append("pk = (" + entity.getPKClassName() + ")obj;");
		sm.append("}");
		sm.append("catch (ClassCastException cce) {");
		sm.append("return false;");
		sm.append("}");
		sm.append("if (");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (!col.isPrimitiveType()) {
				sm.append("(" + col.getName() + ".equals(pk." + col.getName() + "))");
			}
			else {
				sm.append("(" + col.getName() + " == pk." + col.getName() + ")");
			}

			if ((i + 1) != pkList.size()) {
				sm.append(" && ");
			}
		}

		sm.append(") {");
		sm.append("return true;");
		sm.append("} else {");
		sm.append("return false;");
		sm.append("}");
		sm.append("}");

		// Hash code method

		sm.append("public int hashCode() {");
		sm.append("return (");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (i != 0) {
				sm.append(" + ");
			}

			if (!col.isPrimitiveType() && !col.getType().equals("String")) {
				sm.append(col.getName() + ".toString()");
			}
			else {
				sm.append(col.getName());
			}
		}

		sm.append(").hashCode();");
		sm.append("}");

		// To string method

		sm.append("public String toString() {");
		sm.append("StringMaker sm = new StringMaker();");
		sm.append("sm.append(StringPool.OPEN_CURLY_BRACE);");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sm.append("sm.append(\"" + col.getName() + "\");");
			sm.append("sm.append(StringPool.EQUAL);");
			sm.append("sm.append(" + col.getName() + ");");

			if ((i + 1) != pkList.size()) {
				sm.append("sm.append(StringPool.COMMA);");
				sm.append("sm.append(StringPool.SPACE);");
			}
		}

		sm.append("sm.append(StringPool.CLOSE_CURLY_BRACE);");
		sm.append("return sm.toString();");
		sm.append("}");

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_serviceOutputPath + "/service/persistence/" + entity.getPKClassName() + ".java");

		writeFile(ejbFile, sm.toString());

		if (Validator.isNotNull(_serviceDir)) {
			ejbFile = new File(_outputPath + "/service/persistence/" + entity.getPKClassName() + ".java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
	}

	private void _createEJBXML() throws IOException {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List referenceList = entity.getReferenceList();

			if (entity.hasLocalService()) {
				_createEJBXMLSession(entity, referenceList, sm, _LOCAL);
			}

			if (entity.hasRemoteService()) {
				_createEJBXMLSession(entity, referenceList, sm, _REMOTE);
			}
		}

		File xmlFile = new File("classes/META-INF/ejb-jar.xml");

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE ejb-jar PUBLIC \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN\" \"http://java.sun.com/dtd/ejb-jar_2_0.dtd\">\n" +
				"\n" +
				"<ejb-jar>\n" +
				"\t<enterprise-beans>\n" +
				"\t</enterprise-beans>\n" +
				"</ejb-jar>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int x = oldContent.indexOf("<session>");
		int y = oldContent.lastIndexOf("</session>");

		if (x == -1) {
			x = newContent.indexOf("<enterprise-beans/>");
			if (x != -1) {
				newContent = StringUtil.replace(
					newContent, "<enterprise-beans/>", "<enterprise-beans />");
			}

			x = newContent.indexOf("<enterprise-beans />");
			if (x != -1) {
				newContent = StringUtil.replace(
					newContent, "<enterprise-beans />",
					"<enterprise-beans>\n\t</enterprise-beans>");
			}

			x = newContent.indexOf("</enterprise-beans>") - 1;
			newContent =
				newContent.substring(0, x) + sm.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			int firstSession = newContent.indexOf(
				"<ejb-name>" + StringUtil.replace(
					_packagePath + ".service.ejb.", ".", "_"),
				x);
			int lastSession = newContent.lastIndexOf(
				"<ejb-name>" + StringUtil.replace(
					_packagePath + ".service.ejb.", ".", "_"),
				y);

			if (firstSession == -1 || firstSession > y) {
				x = newContent.indexOf("</enterprise-beans>") - 1;
				newContent =
					newContent.substring(0, x) + sm.toString() +
					newContent.substring(x, newContent.length());
			}
			else {
				firstSession = newContent.lastIndexOf(
					"<session>", firstSession) - 2;
				lastSession = newContent.indexOf(
					"</session>", lastSession) + 11;

				newContent =
					newContent.substring(0, firstSession) + sm.toString() +
					newContent.substring(lastSession, newContent.length());
			}
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createEJBXMLSession(Entity entity, List referenceList, StringMaker sm, int sessionType) throws IOException {
		sm.append("\t\t<session>\n");
		sm.append("\t\t\t<display-name>" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</display-name>\n");
		sm.append("\t\t\t<ejb-name>" + StringUtil.replace(_packagePath + ".service.ejb.", ".", "_") + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</ejb-name>\n");
		sm.append("\t\t\t<" + (sessionType == _LOCAL ? "local-" : "") + "home>" + _packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome</" + (sessionType == _LOCAL ? "local-" : "") + "home>\n");
		sm.append("\t\t\t<" + (sessionType == _LOCAL ? "local" : "remote") + ">" + _packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</" + (sessionType == _LOCAL ? "local" : "remote") + ">\n");
		sm.append("\t\t\t<ejb-class>" + _packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl</ejb-class>\n");
		sm.append("\t\t\t<session-type>Stateless</session-type>\n");
		sm.append("\t\t\t<transaction-type>Bean</transaction-type>\n");

		File manualFile = new File("../portal-ejb/classes/META-INF/ejb-jar-ref.xml");

		if (!manualFile.exists()) {
			manualFile = new File("../ext-ejb/classes/META-INF/ejb-jar-ref.xml");
		}

		if (manualFile.exists()) {
			String content = FileUtil.read(manualFile);

			int x = content.indexOf("<ejb-ref>");
			int y = content.lastIndexOf("</ejb-ref>") + 11;

			if (x != -1) {
				content = content.substring(x - 1, y);
				content = StringUtil.replace(content, "\t<", "\t\t\t<");

				sm.append(content);
			}
		}

		for (int j = 0; j < referenceList.size(); j++) {
			Object reference = referenceList.get(j);

			if (reference instanceof Entity) {
				Entity entityRef = (Entity)referenceList.get(j);

				if (entityRef.hasLocalService()) {
					sm.append("\t\t\t<ejb-local-ref>\n");
					sm.append("\t\t\t\t<ejb-ref-name>ejb/liferay/" + entityRef.getName() + "LocalServiceHome</ejb-ref-name>\n");
					sm.append("\t\t\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
					sm.append("\t\t\t\t<local-home>" + entityRef.getPackagePath() + ".service.ejb." + entityRef.getName() + "LocalServiceHome</local-home>\n");
					sm.append("\t\t\t\t<local>" + entityRef.getPackagePath() + ".service.ejb." + entityRef.getName() + "LocalServiceEJB</local>\n");
					sm.append("\t\t\t\t<ejb-link>" + StringUtil.replace(entityRef.getPackagePath() + ".service.ejb.", ".", "_") + entityRef.getName() + "LocalServiceEJB</ejb-link>\n");
					sm.append("\t\t\t</ejb-local-ref>\n");
				}
			}
			else if (reference instanceof String) {
				sm.append("\t\t\t" + reference + "\n");
			}
		}

		for (int j = 0; j < _ejbList.size(); j++) {
			Entity entityRef = (Entity)_ejbList.get(j);

			if (!((sessionType == _LOCAL) && entity.equals(entityRef)) && entityRef.hasLocalService()) {
				sm.append("\t\t\t<ejb-local-ref>\n");
				sm.append("\t\t\t\t<ejb-ref-name>ejb/liferay/" + entityRef.getName() + "LocalServiceHome</ejb-ref-name>\n");
				sm.append("\t\t\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
				sm.append("\t\t\t\t<local-home>" + _packagePath + ".service.ejb." + entityRef.getName() + "LocalServiceHome</local-home>\n");
				sm.append("\t\t\t\t<local>" + _packagePath + ".service.ejb." + entityRef.getName() + "LocalServiceEJB</local>\n");
				sm.append("\t\t\t\t<ejb-link>" + StringUtil.replace(_packagePath + ".service.ejb.", ".", "_") + entityRef.getName() + "LocalServiceEJB</ejb-link>\n");
				sm.append("\t\t\t</ejb-local-ref>\n");
			}
		}

		manualFile = new File("../portal-ejb/classes/META-INF/ejb-jar-local-ref.xml");

		if (!manualFile.exists()) {
			manualFile = new File("../ext-ejb/classes/META-INF/ejb-jar-local-ref.xml");
		}

		if (manualFile.exists()) {
			String content = FileUtil.read(manualFile);

			int x = content.indexOf("<ejb-local-ref>");
			int y = content.lastIndexOf("</ejb-local-ref>") + 17;

			if (x != -1) {
				content = content.substring(x - 1, y);
				content = StringUtil.replace(content, "\t<", "\t\t\t<");

				sm.append(content);
			}
		}

		sm.append("\t\t\t<resource-ref>\n");
		sm.append("\t\t\t\t<res-ref-name>jdbc/LiferayPool</res-ref-name>\n");
		sm.append("\t\t\t\t<res-type>javax.sql.DataSource</res-type>\n");
		sm.append("\t\t\t\t<res-auth>Container</res-auth>\n");
		sm.append("\t\t\t\t<res-sharing-scope>Shareable</res-sharing-scope>\n");
		sm.append("\t\t\t</resource-ref>\n");
		sm.append("\t\t\t<resource-ref>\n");
		sm.append("\t\t\t\t<res-ref-name>mail/MailSession</res-ref-name>\n");
		sm.append("\t\t\t\t<res-type>javax.mail.Session</res-type>\n");
		sm.append("\t\t\t\t<res-auth>Container</res-auth>\n");
		sm.append("\t\t\t</resource-ref>\n");
		sm.append("\t\t</session>\n");
	}

	private void _createExceptions(List exceptions) throws IOException {
		String copyright = null;
		try {
			copyright = FileUtil.read("../copyright.txt");
		}
		catch (FileNotFoundException fnfe) {
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			if (entity.hasColumns()) {
				exceptions.add(_getNoSuchEntityException(entity));
			}
		}

		for (int i = 0; i < exceptions.size(); i++) {
			String exception = (String)exceptions.get(i);

			StringMaker sm = new StringMaker();

			if (Validator.isNotNull(copyright)) {
				sm.append(copyright + "\n");
				sm.append("\n");
			}

			sm.append("package " + _packagePath + ";\n");
			sm.append("\n");
			sm.append("import com.liferay.portal.PortalException;\n");
			sm.append("\n");

			if (Validator.isNotNull(copyright)) {
				sm.append("/**\n");
				sm.append(" * <a href=\"" + exception + "Exception.java.html\"><b><i>View Source</i></b></a>\n");
				sm.append(" *\n");
				sm.append(" * @author Brian Wing Shun Chan\n");
				sm.append(" *\n");
				sm.append(" */\n");
			}

			sm.append("public class " + exception + "Exception extends PortalException {\n");
			sm.append("\n");
			sm.append("\tpublic " + exception + "Exception() {\n");
			sm.append("\t\tsuper();\n");
			sm.append("\t}\n");
			sm.append("\n");
			sm.append("\tpublic " + exception + "Exception(String msg) {\n");
			sm.append("\t\tsuper(msg);\n");
			sm.append("\t}\n");
			sm.append("\n");
			sm.append("\tpublic " + exception + "Exception(String msg, Throwable cause) {\n");
			sm.append("\t\tsuper(msg, cause);\n");
			sm.append("\t}\n");
			sm.append("\n");
			sm.append("\tpublic " + exception + "Exception(Throwable cause) {\n");
			sm.append("\t\tsuper(cause);\n");
			sm.append("\t}\n");
			sm.append("\n");
			sm.append("}");

			File exceptionFile = new File(_serviceOutputPath + "/" + exception + "Exception.java");

			if (!exceptionFile.exists()) {
				FileUtil.write(exceptionFile, sm.toString());
			}

			if (Validator.isNotNull(_serviceDir)) {
				exceptionFile = new File(_outputPath + "/" + exception + "Exception.java");

				if (exceptionFile.exists()) {
					System.out.println("Relocating " + exceptionFile);

					exceptionFile.delete();
				}
			}
		}
	}

	private void _createExtendedModel(Entity entity) throws IOException {
		JavaClass javaClass = _getJavaClass(_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".model;");

		// Interface declaration

		sm.append("public interface " + entity.getName() + " extends " + entity.getName() + "Model {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && !javaMethod.isStatic() && javaMethod.isPublic()) {
				sm.append("public " + javaMethod.getReturns().getValue() + _getDimensions(javaMethod.getReturns()) + " " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getType().getValue() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getValue());
				}

				if (newExceptions.size() > 0) {
					sm.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sm.append(itr.next());

						if (itr.hasNext()) {
							sm.append(", ");
						}
					}
				}

				sm.append(";");
			}
		}

		// Interface close brace

		sm.append("}");

		// Write file

		File modelFile = new File(_serviceOutputPath + "/model/" + entity.getName() + ".java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This interface is a model that represents the <code>" + entity.getTable() + "</code> table in the database.",
			"Customize <code>" + _packagePath + ".service.model.impl." + entity.getName() + "Impl</code> and rerun the ServiceBuilder to generate the new methods."
		};

		String[] see = {
			_packagePath + ".service.model." + entity.getName() + "Model",
			_packagePath + ".service.model.impl." + entity.getName() + "Impl",
			_packagePath + ".service.model.impl." + entity.getName() + "ModelImpl"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(modelFile, sm.toString(), jalopySettings);

		if (Validator.isNotNull(_serviceDir)) {
			modelFile = new File(_outputPath + "/model/" + entity.getName() + ".java");

			if (modelFile.exists()) {
				System.out.println("Relocating " + modelFile);

				modelFile.delete();
			}
		}
	}

	private void _createExtendedModelImpl(Entity entity) throws IOException {
		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".model.impl;");

		// Imports

		sm.append("import " + _packagePath + ".model." + entity.getName() + ";");

		// Class declaration

		sm.append("public class " + entity.getName() + "Impl extends " + entity.getName() + "ModelImpl implements " + entity.getName() + " {");

		// Empty constructor

		sm.append("public " + entity.getName() + "Impl() {");
		sm.append("}");

		// Class close brace

		sm.append("}");

		// Write file

		File modelFile = new File(_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		if (!modelFile.exists()) {
			writeFile(modelFile, sm.toString());
		}
	}

	private void _createHBM(Entity entity) throws IOException {
		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "HBM.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createHBMUtil(Entity entity) throws IOException {
		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "HBMUtil.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createHBMXML() throws IOException {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List pkList = entity.getPKList();
			List columnList = entity.getColumnList();

			if (entity.hasColumns()) {
				sm.append("\t<class name=\"" + _packagePath + ".model.impl." + entity.getName() + "Impl\" table=\"" + entity.getTable() + "\">\n");
				sm.append("\t\t<cache usage=\"read-write\" />\n");

				if (entity.hasCompoundPK()) {
					sm.append("\t\t<composite-id name=\"primaryKey\" class=\"" + _packagePath + ".service.persistence." + entity.getName() + "PK\">\n");

					for (int j = 0; j < pkList.size(); j++) {
						EntityColumn col = (EntityColumn)pkList.get(j);

						sm.append("\t\t\t<key-property name=\"" + col.getName() + "\" ");

						if (!col.getName().equals(col.getDBName())) {
							sm.append("column=\"" + col.getDBName() + "\" />\n");
						}
						else {
							sm.append("/>\n");
						}
					}

					sm.append("\t\t</composite-id>\n");
				}
				else {
					EntityColumn col = (EntityColumn)pkList.get(0);

					sm.append("\t\t<id name=\"" + col.getName() + "\" ");

					if (!col.getName().equals(col.getDBName())) {
						sm.append("column=\"" + col.getDBName() + "\" ");
					}

					sm.append("type=\"");

					if (!entity.hasPrimitivePK()) {
						sm.append("java.lang.");
					}

					sm.append(col.getType() + "\">\n");

					String colIdType = col.getIdType();

					if (Validator.isNull(colIdType)) {
						sm.append("\t\t\t<generator class=\"assigned\" />\n");
					}
					else if (colIdType.equals("class")) {
						sm.append("\t\t\t<generator class=\"" + col.getIdParam() + "\" />\n");
					}
					else if (colIdType.equals("sequence")) {
						sm.append("\t\t\t<generator class=\"sequence\">\n");
						sm.append("\t\t\t\t<param name=\"sequence\">" + col.getIdParam() + "</param>\n");
						sm.append("\t\t\t</generator>\n");
					}
					else {
						sm.append("\t\t\t<generator class=\"" + colIdType + "\" />\n");
					}

					sm.append("\t\t</id>\n");
				}

				for (int j = 0; j < columnList.size(); j++) {
					EntityColumn col = (EntityColumn)columnList.get(j);

					String colType = col.getType();

					if (!col.isPrimary() && !col.isCollection() && col.getEJBName() == null) {
						sm.append("\t\t<property name=\"" + col.getName() + "\" ");

						if (!col.getName().equals(col.getDBName())) {
							sm.append("column=\"" + col.getDBName() + "\" ");
						}

						if (col.isPrimitiveType() || colType.equals("String")) {
							sm.append("type=\"com.liferay.util.dao.hibernate.");
							sm.append(_getPrimitiveObj(colType));
							sm.append("Type\" ");
						}

						sm.append("/>\n");
					}
				}

				sm.append("\t</class>\n");
			}
		}

		File xmlFile = new File(_hbmFileName);

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n" +
				"\n" +
				"<hibernate-mapping default-lazy=\"false\" auto-import=\"false\">\n" +
				"</hibernate-mapping>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = _fixHBMXML(oldContent);

		int firstClass = newContent.indexOf(
			"<class name=\"" + _packagePath + ".model.impl.");
		int lastClass = newContent.lastIndexOf(
			"<class name=\"" + _packagePath + ".model.impl.");

		if (firstClass == -1) {
			int x = newContent.indexOf("</hibernate-mapping>");

			newContent =
				newContent.substring(0, x) + sm.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			firstClass = newContent.lastIndexOf(
				"<class", firstClass) - 1;
			lastClass = newContent.indexOf(
				"</class>", lastClass) + 9;

			newContent =
				newContent.substring(0, firstClass) + sm.toString() +
				newContent.substring(lastClass, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createJSONJS() throws IOException {
		StringMaker sm = new StringMaker();

		if (_ejbList.size() > 0) {
			sm.append("Liferay.Service." + _portletShortName + " = {\n");
			sm.append("\tservicePackage: \"" + _packagePath + ".service.http.\"\n");
			sm.append("};\n\n");
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			if (entity.hasRemoteService()) {
				JavaClass javaClass = _getJavaClass(_outputPath + "/service/http/" + entity.getName() + "ServiceJSON.java");

				JavaMethod[] methods = javaClass.getMethods();

				Set jsonMethods = new LinkedHashSet();

				for (int j = 0; j < methods.length; j++) {
					JavaMethod javaMethod = methods[j];

					String methodName = javaMethod.getName();

					if (javaMethod.isPublic()) {
						jsonMethods.add(methodName);
					}
				}

				if (jsonMethods.size() > 0) {
					sm.append("Liferay.Service." + _portletShortName + "." + entity.getName() + " = {\n");
					sm.append("\tserviceClassName: Liferay.Service." + _portletShortName + ".servicePackage + \"" + entity.getName() + "\" + Liferay.Service.classNameSuffix,\n\n");

					Iterator itr = jsonMethods.iterator();

					while (itr.hasNext()) {
						String methodName = (String)itr.next();

						sm.append("\t" + methodName + ": function(params, callback) {\n");
						sm.append("\t\tparams.serviceClassName = this.serviceClassName;\n");
						sm.append("\t\tparams.serviceMethodName = \"" + methodName + "\";\n\n");
						sm.append("\t\treturn Liferay.Service.ajax(params, callback);\n");
						sm.append("\t}");

						if (itr.hasNext()) {
							sm.append(",\n");
						}

						sm.append("\n");
					}

					sm.append("};\n\n");
				}
			}
		}

		File xmlFile = new File(_jsonFileName);

		if (!xmlFile.exists()) {
			String content = "";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int oldBegin = oldContent.indexOf(
			"Liferay.Service." + _portletShortName);

		int oldEnd = oldContent.lastIndexOf(
			"Liferay.Service." + _portletShortName);
		oldEnd = oldContent.indexOf("};", oldEnd);

		int newBegin = newContent.indexOf(
			"Liferay.Service." + _portletShortName);

		int newEnd = newContent.lastIndexOf(
			"Liferay.Service." + _portletShortName);
		newEnd = newContent.indexOf("};", newEnd);

		if (newBegin == -1) {
			newContent = oldContent + "\n\n" + sm.toString().trim();
		}
		else {
			newContent =
				newContent.substring(0, oldBegin) + sm.toString().trim() +
				newContent.substring(oldEnd + 2, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createModel(Entity entity) throws IOException {
		List regularColList = entity.getRegularColList();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".model;");

		// Imports

		if (entity.hasCompoundPK()) {
			sm.append("import " + _packagePath + ".service.persistence." + entity.getName() + "PK;");
		}

		sm.append("import com.liferay.portal.model.BaseModel;");
		sm.append("import java.util.Date;");

		// Interface declaration

		sm.append("public interface " + entity.getName() + "Model extends BaseModel {");

		// Primary key accessor

		sm.append("public " + entity.getPKClassName() + " getPrimaryKey();");

		sm.append("public void setPrimaryKey(" + entity.getPKClassName() + " pk);");

		// Getter and setter methods

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			String colType = col.getType();

			sm.append("public " + colType + " get" + col.getMethodName() + "();");

			if (colType.equals("boolean")) {
				sm.append("public " + colType + " is" + col.getMethodName() + "();");
			}

			sm.append("public void set" + col.getMethodName() + "(" + colType + " " + col.getName() + ");");
		}

		// Interface close brace

		sm.append("}");

		// Write file

		File modelFile = new File(_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This interface is a model that represents the <code>" + entity.getTable() + "</code> table in the database."
		};

		String[] see = {
			_packagePath + ".service.model." + entity.getName(),
			_packagePath + ".service.model.impl." + entity.getName() + "Impl",
			_packagePath + ".service.model.impl." + entity.getName() + "ModelImpl"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(modelFile, sm.toString(), jalopySettings);

		if (Validator.isNotNull(_serviceDir)) {
			modelFile = new File(_outputPath + "/model/" + entity.getName() + "Model.java");

			if (modelFile.exists()) {
				System.out.println("Relocating " + modelFile);

				modelFile.delete();
			}
		}
	}

	private void _createModelHintsXML() throws IOException {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List columnList = entity.getColumnList();

			if (entity.hasColumns()) {
				sm.append("\t<model name=\"" + _packagePath + ".model." + entity.getName() + "\">\n");

				Map defaultHints = ModelHintsUtil.getDefaultHints(_packagePath + ".model." + entity.getName());

				if ((defaultHints != null) && (defaultHints.size() > 0)) {
					sm.append("\t\t<default-hints>\n");

					Iterator itr = defaultHints.entrySet().iterator();

					while (itr.hasNext()) {
						Map.Entry entry = (Map.Entry)itr.next();

						String key = (String)entry.getKey();
						String value = (String)entry.getValue();

						sm.append("\t\t\t<hint name=\"" + key + "\">" + value + "</hint>\n");
					}

					sm.append("\t\t</default-hints>\n");
				}

				for (int j = 0; j < columnList.size(); j++) {
					EntityColumn col = (EntityColumn)columnList.get(j);

					if (!col.isCollection()) {
						sm.append("\t\t<field name=\"" + col.getName() + "\" type=\"" + col.getType() + "\"");

						Element field = ModelHintsUtil.getFieldsEl(_packagePath + ".model." + entity.getName(), col.getName());

						List hints = null;

						if (field != null) {
							hints = field.elements();
						}

						if ((hints == null) || (hints.size() == 0)) {
							sm.append(" />\n");
						}
						else {
							sm.append(">\n");

							Iterator itr = hints.iterator();

							while (itr.hasNext()) {
								Element hint = (Element)itr.next();

								if (hint.getName().equals("hint")) {
									sm.append("\t\t\t<hint name=\"" + hint.attributeValue("name") + "\">" + hint.getText() + "</hint>\n");
								}
								else {
									sm.append("\t\t\t<hint-collection name=\"" + hint.attributeValue("name") + "\" />\n");
								}
							}

							sm.append("\t\t</field>\n");
						}
					}
				}

				sm.append("\t</model>\n");
			}
		}

		File xmlFile = new File(_modelHintsFileName);

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"\n" +
				"<model-hints>\n" +
				"</model-hints>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int firstModel = newContent.indexOf(
			"<model name=\"" + _packagePath + ".model.");
		int lastModel = newContent.lastIndexOf(
			"<model name=\"" + _packagePath + ".model.");

		if (firstModel == -1) {
			int x = newContent.indexOf("</model-hints>");
			newContent =
				newContent.substring(0, x) + sm.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			firstModel = newContent.lastIndexOf(
				"<model", firstModel) - 1;
			lastModel = newContent.indexOf(
				"</model>", lastModel) + 9;

			newContent =
				newContent.substring(0, firstModel) + sm.toString() +
				newContent.substring(lastModel, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createModelImpl(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List regularColList = entity.getRegularColList();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".model.impl;");

		// Imports

		if (entity.hasCompoundPK()) {
			sm.append("import " + _packagePath + ".service.persistence." + entity.getName() + "PK;");
		}

		sm.append("import com.liferay.portal.model.impl.BaseModelImpl;");
		sm.append("import com.liferay.portal.util.PropsUtil;");
		sm.append("import com.liferay.util.DateUtil;");
		sm.append("import com.liferay.util.GetterUtil;");
		sm.append("import com.liferay.util.XSSUtil;");
		sm.append("import java.sql.Types;");
		sm.append("import java.util.Date;");

		// Class declaration

		sm.append("public class " + entity.getName() + "ModelImpl extends BaseModelImpl {");

		// Fields

		sm.append("public static String TABLE_NAME = \"" + entity.getTable() + "\";");

		sm.append("public static Object[][] TABLE_COLUMNS = {");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			String sqlType = _getSqlType(_packagePath + ".model." + entity.getName(), col.getName(), col.getType());

			sm.append("{\"" + col.getDBName() + "\", new Integer(Types." + sqlType + ")}");

			if ((i + 1) < regularColList.size()) {
				sm.append(",");
			}
		}

		sm.append("};");

		sm.append("public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(\"xss.allow." + _packagePath + ".model." + entity.getName() + "\"), XSS_ALLOW);");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			if (col.getType().equals("String")) {
				sm.append("public static boolean XSS_ALLOW_" + col.getName().toUpperCase() + " = GetterUtil.getBoolean(PropsUtil.get(\"xss.allow." + _packagePath + ".model." + entity.getName() + "." + col.getName() + "\"), XSS_ALLOW_BY_MODEL);");
			}
		}

		sm.append("public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(\"lock.expiration.time." + _packagePath + ".model." + entity.getName() + "Model\"));");

		// Empty constructor

		sm.append("public " + entity.getName() + "ModelImpl() {");
		sm.append("}");

		// Primary key accessor

		sm.append("public " + entity.getPKClassName() + " getPrimaryKey() {");

		if (entity.hasCompoundPK()) {
			sm.append("return new " + entity.getPKClassName() + "(");

			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = (EntityColumn)pkList.get(i);

				sm.append("_" + col.getName());

				if ((i + 1) != (pkList.size())) {
					sm.append(", ");
				}
			}

			sm.append(");");
		}
		else {
			EntityColumn col = (EntityColumn)pkList.get(0);

			sm.append("return _" + col.getName() + ";");
		}

		sm.append("}");

		sm.append("public void setPrimaryKey(" + entity.getPKClassName() + " pk) {");

		if (entity.hasCompoundPK()) {
			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = (EntityColumn)pkList.get(i);

				sm.append("set" + col.getMethodName() + "(pk." + col.getName() + ");");
			}
		}
		else {
			EntityColumn col = (EntityColumn)pkList.get(0);

			sm.append("set" + col.getMethodName() + "(pk);");
		}

		sm.append("}");

		// Getter and setter methods

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			String colType = col.getType();

			sm.append("public " + colType + " get" + col.getMethodName() + "() {");

			if (colType.equals("String") && col.isConvertNull()) {
				sm.append("return GetterUtil.getString(_" + col.getName() + ");");
			}
			else {
				sm.append("return _" + col.getName() + ";");
			}

			sm.append("}");

			if (colType.equals("boolean")) {
				sm.append("public " + colType + " is" + col.getMethodName() + "() {");
				sm.append("return _" + col.getName() + ";");
				sm.append("}");
			}

			sm.append("public void set" + col.getMethodName() + "(" + colType + " " + col.getName() + ") {");
			sm.append("if (");

			if (!col.isPrimitiveType()) {
				sm.append("(" + col.getName() + " == null && _" + col.getName() + " != null) ||");
				sm.append("(" + col.getName() + " != null && _" + col.getName() + " == null) ||");
				sm.append("(" + col.getName() + " != null && _" + col.getName() + " != null && !" + col.getName() + ".equals(_" + col.getName() + "))");
			}
			else {
				sm.append(col.getName() + " != _" + col.getName());
			}

			sm.append(") {");

			if (colType.equals("String")) {
				sm.append("if (!XSS_ALLOW_" + col.getName().toUpperCase() + ") {");
				sm.append(col.getName() + " = XSSUtil.strip(" + col.getName() + ");");
				sm.append("}");
			}

			sm.append("_" + col.getName() + " = " + col.getName() + ";");
			sm.append("}");
			sm.append("}");
		}

		// Clone method

		sm.append("public Object clone() {");
		sm.append(entity.getName() + "Impl clone = new " + entity.getName() + "Impl();");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			sm.append("clone.set" + col.getMethodName() + "(");

			if (col.getEJBName() == null) {
				sm.append("get" + col.getMethodName() + "()");
			}
			else {
				sm.append("(" + col.getEJBName() + ")get" + col.getMethodName() + "().clone()");
			}

			sm.append(");");
		}

		sm.append("return clone;");
		sm.append("}");

		// Compare to method

		sm.append("public int compareTo(Object obj) {");
		sm.append("if (obj == null) {");
		sm.append("return -1;");
		sm.append("}");
		sm.append(entity.getName() + "Impl " + entity.getVarName() + " = (" + entity.getName() + "Impl)obj;");

		if (entity.isOrdered()) {
			EntityOrder order = entity.getOrder();

			List orderList = order.getColumns();

			sm.append("int value = 0;");

			for (int i = 0; i < orderList.size(); i++) {
				EntityColumn col = (EntityColumn)orderList.get(i);

				String colType = col.getType();

				if (!col.isPrimitiveType()) {
					if (colType.equals("Date")) {
						sm.append("value = DateUtil.compareTo(get" + col.getMethodName() + "(), " + entity.getVarName() + ".get" + col.getMethodName() + "());");
					}
					else {
						if (col.isCaseSensitive()) {
							sm.append("value = get" + col.getMethodName() + "().compareTo(" + entity.getVarName() + ".get" + col.getMethodName() + "());");
						}
						else {
							sm.append("value = get" + col.getMethodName() + "().toLowerCase().compareTo(" + entity.getVarName() + ".get" + col.getMethodName() + "().toLowerCase());");
						}
					}
				}
				else {
					String ltComparator = "<";
					String gtComparator = ">";

					if (colType.equals("boolean")) {
						ltComparator = "==";
						gtComparator = "!=";
					}

					sm.append("if (get" + col.getMethodName() + "() " + ltComparator + " " + entity.getVarName() + ".get" + col.getMethodName() + "()) {");
					sm.append("value = -1;");
					sm.append("}");
					sm.append("else if (get" + col.getMethodName() + "() " + gtComparator + " " + entity.getVarName() + ".get" + col.getMethodName() + "()) {");
					sm.append("value = 1;");
					sm.append("}");
					sm.append("else {");
					sm.append("value = 0;");
					sm.append("}");
				}

				if (!col.isOrderByAscending()) {
					sm.append("value = value * -1;");
				}

				sm.append("if (value != 0) {");
				sm.append("return value;");
				sm.append("}");
			}

			sm.append("return 0;");
		}
		else {
			sm.append(entity.getPKClassName() + " pk = " + entity.getVarName() + ".getPrimaryKey();");

			if (entity.hasPrimitivePK()) {
				sm.append("if (getPrimaryKey() < pk) {");
				sm.append("return -1;");
				sm.append("}");
				sm.append("else if (getPrimaryKey() > pk) {");
				sm.append("return 1;");
				sm.append("}");
				sm.append("else {");
				sm.append("return 0;");
				sm.append("}");
			}
			else {
				sm.append("return getPrimaryKey().compareTo(pk);");
			}
		}

		sm.append("}");

		// Equals method

		sm.append("public boolean equals(Object obj) {");
		sm.append("if (obj == null) {");
		sm.append("return false;");
		sm.append("}");
		sm.append(entity.getName() + "Impl " + entity.getVarName() + " = null;");
		sm.append("try {");
		sm.append(entity.getVarName() + " = (" + entity.getName() + "Impl)obj;");
		sm.append("}");
		sm.append("catch (ClassCastException cce) {");
		sm.append("return false;");
		sm.append("}");
		sm.append(entity.getPKClassName() + " pk = " + entity.getVarName() + ".getPrimaryKey();");

		if (entity.hasPrimitivePK()) {
			sm.append("if (getPrimaryKey() == pk) {");
		}
		else {
			sm.append("if (getPrimaryKey().equals(pk)) {");
		}

		sm.append("return true;");
		sm.append("}");
		sm.append("else {");
		sm.append("return false;");
		sm.append("}");
		sm.append("}");

		// Hash code method

		sm.append("public int hashCode() {");

		if (entity.hasPrimitivePK()) {
			sm.append("return (int)getPrimaryKey();");
		}
		else {
			sm.append("return getPrimaryKey().hashCode();");
		}

		sm.append("}");

		// Fields

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			sm.append("private " + col.getType() + " _" + col.getName() + ";");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File modelFile = new File(_outputPath + "/model/impl/" + entity.getName() + "ModelImpl.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is a model that represents the <code>" + entity.getTable() + "</code> table in the database."
		};

		String[] see = {
			_packagePath + ".service.model." + entity.getName(),
			_packagePath + ".service.model." + entity.getName() + "Model",
			_packagePath + ".service.model.impl." + entity.getName() + "Impl"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(modelFile, sm.toString(), jalopySettings);
	}

	private void _createModelSoap(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List regularColList = entity.getRegularColList();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".model;");

		// Imports

		if (entity.hasCompoundPK()) {
			sm.append("import " + _packagePath + ".service.persistence." + entity.getName() + "PK;");
		}

		sm.append("import java.io.Serializable;");
		sm.append("import java.util.ArrayList;");
		sm.append("import java.util.Date;");
		sm.append("import java.util.List;");

		// Class declaration

		sm.append("public class " + entity.getName() + "Soap implements Serializable {");

		// Methods

		sm.append("public static " + entity.getName() + "Soap toSoapModel(" + entity.getName() + " model) {");
		sm.append(entity.getName() + "Soap soapModel = new " + entity.getName() + "Soap();");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			sm.append("soapModel.set" + col.getMethodName() + "(model.get" + col.getMethodName() + "());");
		}

		sm.append("return soapModel;");
		sm.append("}");

		sm.append("public static " + entity.getName() + "Soap[] toSoapModels(List models) {");
		sm.append("List soapModels = new ArrayList(models.size());");
		sm.append("for (int i = 0; i < models.size(); i++) {");
		sm.append(entity.getName() + " model = (" + entity.getName() + ")models.get(i);");
		sm.append("soapModels.add(toSoapModel(model));");
		sm.append("}");
		sm.append("return (" + entity.getName() + "Soap[])soapModels.toArray(new " + entity.getName() + "Soap[0]);");
		sm.append("}");

		// Empty constructor

		sm.append("public " + entity.getName() + "Soap() {");
		sm.append("}");

		// Primary key accessor

		sm.append("public " + entity.getPKClassName() + " getPrimaryKey() {");

		if (entity.hasCompoundPK()) {
			sm.append("return new " + entity.getPKClassName() + "(");

			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = (EntityColumn)pkList.get(i);

				sm.append("_" + col.getName());

				if ((i + 1) != (pkList.size())) {
					sm.append(", ");
				}
			}

			sm.append(");");
		}
		else {
			EntityColumn col = (EntityColumn)pkList.get(0);

			sm.append("return _" + col.getName() + ";");
		}

		sm.append("}");

		sm.append("public void setPrimaryKey(" + entity.getPKClassName() + " pk) {");

		if (entity.hasCompoundPK()) {
			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = (EntityColumn)pkList.get(i);

				sm.append("set" + col.getMethodName() + "(pk." + col.getName() + ");");
			}
		}
		else {
			EntityColumn col = (EntityColumn)pkList.get(0);

			sm.append("set" + col.getMethodName() + "(pk);");
		}

		sm.append("}");

		// Getter and setter methods

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			String colType = col.getType();

			sm.append("public " + colType + " get" + col.getMethodName() + "() {");
			sm.append("return _" + col.getName() + ";");
			sm.append("}");

			if (colType.equals("boolean")) {
				sm.append("public " + colType + " is" + col.getMethodName() + "() {");
				sm.append("return _" + col.getName() + ";");
				sm.append("}");
			}

			sm.append("public void set" + col.getMethodName() + "(" + colType + " " + col.getName() + ") {");
			sm.append("_" + col.getName() + " = " + col.getName() + ";");
			sm.append("}");
		}

		// Fields

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			sm.append("private " + col.getType() + " _" + col.getName() + ";");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File modelFile = new File(_serviceOutputPath + "/model/" + entity.getName() + "Soap.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is used by <code>" + _packagePath + ".service.http." + entity.getName() + "ServiceSoap</code>."
		};

		String[] see = {
			_packagePath + ".service.http." + entity.getName() + "ServiceSoap"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(modelFile, sm.toString(), jalopySettings);
	}

	private void _createPersistence(Entity entity) throws IOException {
		List columnList = entity.getColumnList();
		List finderList = entity.getFinderList();

		String pkClassName = entity.getPKClassName();
		String pkVarName = entity.getPKVarName();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sm.append("import " + _packagePath + "." + _getNoSuchEntityException(entity) + "Exception;");
		sm.append("import " + _packagePath + ".model." + entity.getName() + ";");
		sm.append("import " + _packagePath + ".model.impl." + entity.getName() + "Impl;");
		sm.append("import com.liferay.portal.PortalException;");
		sm.append("import com.liferay.portal.SystemException;");
		sm.append("import com.liferay.portal.kernel.dao.DynamicQuery;");
		sm.append("import com.liferay.portal.kernel.dao.DynamicQueryInitializer;");
		sm.append("import com.liferay.portal.kernel.util.OrderByComparator;");
		sm.append("import com.liferay.portal.kernel.util.StringMaker;");
		sm.append("import com.liferay.portal.kernel.util.StringPool;");
		sm.append("import com.liferay.portal.service.persistence.BasePersistence;");
		sm.append("import com.liferay.portal.spring.hibernate.HibernateUtil;");
		sm.append("import com.liferay.util.dao.hibernate.QueryPos;");
		sm.append("import com.liferay.util.dao.hibernate.QueryUtil;");
		sm.append("import java.sql.ResultSet;");
		sm.append("import java.sql.SQLException;");
		sm.append("import java.sql.Types;");
		sm.append("import java.util.Collection;");
		sm.append("import java.util.Collections;");
		sm.append("import java.util.Date;");
		sm.append("import java.util.HashSet;");
		sm.append("import java.util.Iterator;");
		sm.append("import java.util.List;");
		sm.append("import java.util.Set;");
		sm.append("import javax.sql.DataSource;");
		sm.append("import org.apache.commons.logging.Log;");
		sm.append("import org.apache.commons.logging.LogFactory;");
		sm.append("import org.hibernate.Hibernate;");
		sm.append("import org.hibernate.ObjectNotFoundException;");
		sm.append("import org.hibernate.Query;");
		sm.append("import org.hibernate.Session;");
		sm.append("import org.hibernate.SQLQuery;");
		sm.append("import org.springframework.dao.DataAccessException;");
		sm.append("import org.springframework.jdbc.core.SqlParameter;");
		sm.append("import org.springframework.jdbc.object.MappingSqlQuery;");
		sm.append("import org.springframework.jdbc.object.SqlUpdate;");

		// Class declaration

		sm.append("public class " + entity.getName() + "Persistence extends BasePersistence {");

		// Create method

		sm.append("public " + entity.getName() + " create(" + entity.getPKClassName() + " " + pkVarName + ") {");
		sm.append(entity.getName() + " " + entity.getVarName() + " = new " + entity.getName() + "Impl();");
		sm.append(entity.getVarName() + ".setNew(true);");
		sm.append(entity.getVarName() + ".setPrimaryKey(" + pkVarName + ");");
		sm.append("return " + entity.getVarName() + ";");
		sm.append("}");

		// Remove method

		sm.append("public " + entity.getName() + " remove(" + pkClassName + " " + pkVarName + ") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append(entity.getName() + " " + entity.getVarName() + " = (" + entity.getName() + ")session.get(" + entity.getName() + "Impl.class, ");

		if (entity.hasPrimitivePK()) {
			sm.append("new ");
			sm.append(_getPrimitiveObj(entity.getPKClassName()));
			sm.append("(");
		}

		sm.append(pkVarName);

		if (entity.hasPrimitivePK()) {
			sm.append(")");
		}

		sm.append(");");
		sm.append("if (" + entity.getVarName() + " == null) {");
		sm.append("if (_log.isWarnEnabled()) {");
		sm.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ");");
		sm.append("}");
		sm.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ");");
		sm.append("}");
		sm.append("return remove(" + entity.getVarName() + ");");
		sm.append("}");
		sm.append("catch (" + _getNoSuchEntityException(entity) + "Exception nsee) {");
		sm.append("throw nsee;");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		sm.append("public " + entity.getName() + " remove(" + entity.getName() + " " + entity.getVarName() + ") throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("session.delete(" + entity.getVarName() + ");");
		sm.append("session.flush();");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (col.isCollection() && col.isMappingManyToMany()) {
				Entity tempEntity = getEntity(col.getEJBName());

				// clearUsers(String pk)

				sm.append("clear" + tempEntity.getNames() + ".clear(" + entity.getVarName() + ".getPrimaryKey());");
			}
		}

		sm.append("return " + entity.getVarName() + ";");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		// Update method

		sm.append("public " + _packagePath + ".model." + entity.getName() + " update(" + _packagePath + ".model." + entity.getName() + " " + entity.getVarName() + ") throws SystemException {");
		sm.append("return update(" + entity.getVarName() + ", false);");
		sm.append("}");

		sm.append("public " + _packagePath + ".model." + entity.getName() + " update(" + _packagePath + ".model." + entity.getName() + " " + entity.getVarName() + ", boolean saveOrUpdate) throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("if (saveOrUpdate) {");
		sm.append("session.saveOrUpdate(" + entity.getVarName() + ");");
		sm.append("}");
		sm.append("else {");
		sm.append("if (" + entity.getVarName() + ".isNew()) {");
		sm.append("session.save(" + entity.getVarName() + ");");
		sm.append("}");
		sm.append("}");
		sm.append("session.flush();");
		sm.append(entity.getVarName() + ".setNew(false);");
		sm.append("return " + entity.getVarName() + ";");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		// Finder methods

		sm.append("public " + entity.getName() + " findByPrimaryKey(" + pkClassName + " " + pkVarName + ") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
		sm.append(entity.getName() + " " + entity.getVarName() + " = fetchByPrimaryKey(" + pkVarName + ");");
		sm.append("if (" + entity.getVarName() + " == null) {");
		sm.append("if (_log.isWarnEnabled()) {");
		sm.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ");");
		sm.append("}");
		sm.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ");");
		sm.append("}");
		sm.append("return " + entity.getVarName() + ";");
		sm.append("}");

		sm.append("public " + entity.getName() + " fetchByPrimaryKey(" + pkClassName + " " + pkVarName + ") throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("return (" + entity.getName() + ")session.get(" + entity.getName() + "Impl.class, ");

		if (entity.hasPrimitivePK()) {
			sm.append("new ");
			sm.append(_getPrimitiveObj(entity.getPKClassName()));
			sm.append("(");
		}

		sm.append(pkVarName);

		if (entity.hasPrimitivePK()) {
			sm.append(")");
		}

		sm.append(");");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		for (int i = 0; i < finderList.size(); i++) {
			EntityFinder finder = (EntityFinder)finderList.get(i);

			List finderColsList = finder.getColumns();

			if (!finder.isCollection()) {
				sm.append("public " + entity.getName() + " findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sm.append(entity.getName() + " " + entity.getVarName() + " = fetchBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(");");
				sm.append("if (" + entity.getVarName() + " == null) {");
				sm.append("StringMaker msg = new StringMaker();");
				sm.append("msg.append(\"No " + entity.getName() + " exists with the key \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sm.append("msg.append(StringPool.OPEN_CURLY_BRACE);");
					}

					sm.append("msg.append(\"" + col.getName() + "=\");");
					sm.append("msg.append(" + col.getName() + ");");

					if ((j + 1) != finderColsList.size()) {
						sm.append("msg.append(\", \");");
					}

					if ((j + 1) == finderColsList.size()) {
						sm.append("msg.append(StringPool.CLOSE_CURLY_BRACE);");
					}
				}

				sm.append("if (_log.isWarnEnabled()) {");
				sm.append("_log.warn(msg.toString());");
				sm.append("}");
				sm.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg.toString());");
				sm.append("}");
				sm.append("return " + entity.getVarName() + ";");
				sm.append("}");

				sm.append("public " + entity.getName() + " fetchBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(") throws SystemException {");
				sm.append("Session session = null;");
				sm.append("try {");
				sm.append("session = openSession();");
				sm.append("StringMaker query = new StringMaker();");
				sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + " WHERE \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						_appendNullLogic(col, sm);
					}

					sm.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}

					if ((j + 1) != finderColsList.size()) {
						sm.append("query.append(\" AND \");");
					}
					else if (Validator.isNull(finder.getWhere())) {
						sm.append("query.append(\" \");");
					}
					else {
						sm.append("query.append(\" AND " + finder.getWhere() + " \");");
					}
				}

				EntityOrder order = entity.getOrder();

				if (order != null) {
					List orderList = order.getColumns();

					sm.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < orderList.size(); j++) {
						EntityColumn col = (EntityColumn)orderList.get(j);

						sm.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != orderList.size()) {
							sm.append(".append(\", \");");
						}
						else {
							sm.append(";");
						}
					}
				}

				sm.append("Query q = session.createQuery(query.toString());");
				sm.append("q.setCacheable(true);");
				sm.append("int queryPos = 0;");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						sm.append("if (" + col.getName() + " != null) {");
					}

					String colType = col.getType();
					String colObjType = colType;

					if (col.isPrimitiveType()) {
						colObjType = _getPrimitiveObj(colType);
					}

					sm.append("q.set" + colObjType + "(queryPos++, " + col.getName());

					if (colType.equals("Boolean")) {
						sm.append(".booleanValue()");
					}
					else if (colType.equals("Double")) {
						sm.append(".doubleValue()");
					}
					else if (colType.equals("Float")) {
						sm.append(".floatValue()");
					}
					else if (colType.equals("Integer")) {
						sm.append(".intValue()");
					}
					else if (colType.equals("Long")) {
						sm.append(".longValue()");
					}
					else if (colType.equals("Short")) {
						sm.append(".shortValue()");
					}

					sm.append(");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}
				}

				sm.append("List list = q.list();");
				sm.append("if (list.size() == 0) {");
				sm.append("return null;");
				sm.append("}");
				sm.append(entity.getName() + " " + entity.getVarName() + " = (" + entity.getName() + ")list.get(0);");
				sm.append("return " + entity.getVarName() + ";");
				sm.append("}");
				sm.append("catch (Exception e) {");
				sm.append("throw HibernateUtil.processException(e);");
				sm.append("}");
				sm.append("finally {");
				sm.append("closeSession(session);");
				sm.append("}");
				sm.append("}");
			}
			else {
				sm.append("public List findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(") throws SystemException {");
				sm.append("Session session = null;");
				sm.append("try {");
				sm.append("session = openSession();");
				sm.append("StringMaker query = new StringMaker();");
				sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + " WHERE \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						_appendNullLogic(col, sm);
					}

					sm.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}

					if ((j + 1) != finderColsList.size()) {
						sm.append("query.append(\" AND \");");
					}
					else if (Validator.isNull(finder.getWhere())) {
						sm.append("query.append(\" \");");
					}
					else {
						sm.append("query.append(\" AND " + finder.getWhere() + " \");");
					}
				}

				EntityOrder order = entity.getOrder();

				if (order != null) {
					List orderList = order.getColumns();

					sm.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < orderList.size(); j++) {
						EntityColumn col = (EntityColumn)orderList.get(j);

						sm.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != orderList.size()) {
							sm.append(".append(\", \");");
						}
						else {
							sm.append(";");
						}
					}
				}

				sm.append("Query q = session.createQuery(query.toString());");
				sm.append("q.setCacheable(true);");
				sm.append("int queryPos = 0;");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						sm.append("if (" + col.getName() + " != null) {");
					}

					String colType = col.getType();
					String colObjType = colType;

					if (col.isPrimitiveType()) {
						colObjType = _getPrimitiveObj(colType);
					}

					sm.append("q.set" + colObjType + "(queryPos++, " + col.getName());

					if (colType.equals("Boolean")) {
						sm.append(".booleanValue()");
					}
					else if (colType.equals("Double")) {
						sm.append(".doubleValue()");
					}
					else if (colType.equals("Float")) {
						sm.append(".floatValue()");
					}
					else if (colType.equals("Integer")) {
						sm.append(".intValue()");
					}
					else if (colType.equals("Long")) {
						sm.append(".longValue()");
					}
					else if (colType.equals("Short")) {
						sm.append(".shortValue()");
					}

					sm.append(");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}
				}

				sm.append("return q.list();");
				sm.append("}");
				sm.append("catch (Exception e) {");
				sm.append("throw HibernateUtil.processException(e);");
				sm.append("}");
				sm.append("finally {");
				sm.append("closeSession(session);");
				sm.append("}");
				sm.append("}");

				sm.append("public List findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName() + ", ");
				}

				sm.append("int begin, int end) throws SystemException {");
				sm.append("return findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append("" + col.getName() + ", ");
				}

				sm.append("begin, end, null);");
				sm.append("}");

				sm.append("public List findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName() + ", ");
				}

				sm.append("int begin, int end, OrderByComparator obc) throws SystemException {");
				sm.append("Session session = null;");
				sm.append("try {");
				sm.append("session = openSession();");
				sm.append("StringMaker query = new StringMaker();");
				sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + " WHERE \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						_appendNullLogic(col, sm);
					}

					sm.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}

					if ((j + 1) != finderColsList.size()) {
						sm.append("query.append(\" AND \");");
					}
					else if (Validator.isNull(finder.getWhere())) {
						sm.append("query.append(\" \");");
					}
					else {
						sm.append("query.append(\" AND " + finder.getWhere() + " \");");
					}
				}

				sm.append("if (obc != null) {");
				sm.append("query.append(\"ORDER BY \");");
				sm.append("query.append(obc.getOrderBy());");
				sm.append("}");

				if (order != null) {
					List orderList = order.getColumns();

					sm.append("else {");
					sm.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < orderList.size(); j++) {
						EntityColumn col = (EntityColumn)orderList.get(j);

						sm.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != orderList.size()) {
							sm.append(".append(\", \");");
						}
						else {
							sm.append(";");
						}
					}

					sm.append("}");
				}

				sm.append("Query q = session.createQuery(query.toString());");
				sm.append("q.setCacheable(true);");
				sm.append("int queryPos = 0;");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						sm.append("if (" + col.getName() + " != null) {");
					}

					String colType = col.getType();
					String colObjType = colType;

					if (col.isPrimitiveType()) {
						colObjType = _getPrimitiveObj(colType);
					}

					sm.append("q.set" + colObjType + "(queryPos++, " + col.getName());

					if (colType.equals("Boolean")) {
						sm.append(".booleanValue()");
					}
					else if (colType.equals("Double")) {
						sm.append(".doubleValue()");
					}
					else if (colType.equals("Float")) {
						sm.append(".floatValue()");
					}
					else if (colType.equals("Integer")) {
						sm.append(".intValue()");
					}
					else if (colType.equals("Long")) {
						sm.append(".longValue()");
					}
					else if (colType.equals("Short")) {
						sm.append(".shortValue()");
					}

					sm.append(");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}
				}

				sm.append("return QueryUtil.list(q, getDialect(), begin, end);");
				sm.append("}");
				sm.append("catch (Exception e) {");
				sm.append("throw HibernateUtil.processException(e);");
				sm.append("}");
				sm.append("finally {");
				sm.append("closeSession(session);");
				sm.append("}");
				sm.append("}");

				sm.append("public " + entity.getName() + " findBy" + finder.getName() + "_First(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName() + ", ");
				}

				sm.append("OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");

				sm.append("List list = findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append("" + col.getName() + ", ");
				}

				sm.append("0, 1, obc);");

				sm.append("if (list.size() == 0) {");
				sm.append("StringMaker msg = new StringMaker();");
				sm.append("msg.append(\"No " + entity.getName() + " exists with the key \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sm.append("msg.append(StringPool.OPEN_CURLY_BRACE);");
					}

					sm.append("msg.append(\"" + col.getName() + "=\");");
					sm.append("msg.append(" + col.getName() + ");");

					if ((j + 1) != finderColsList.size()) {
						sm.append("msg.append(\", \");");
					}

					if ((j + 1) == finderColsList.size()) {
						sm.append("msg.append(StringPool.CLOSE_CURLY_BRACE);");
					}
				}

				sm.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg.toString());");				sm.append("}");
				sm.append("else {");
				sm.append("return (" + entity.getName() + ")list.get(0);");
				sm.append("}");
				sm.append("}");

				sm.append("public " + entity.getName() + " findBy" + finder.getName() + "_Last(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName() + ", ");
				}

				sm.append("OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");

				sm.append("int count = countBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append("" + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(");");

				sm.append("List list = findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append("" + col.getName() + ", ");
				}

				sm.append("count - 1, count, obc);");

				sm.append("if (list.size() == 0) {");
				sm.append("StringMaker msg = new StringMaker();");
				sm.append("msg.append(\"No " + entity.getName() + " exists with the key \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sm.append("msg.append(StringPool.OPEN_CURLY_BRACE);");
					}

					sm.append("msg.append(\"" + col.getName() + "=\");");
					sm.append("msg.append(" + col.getName() + ");");

					if ((j + 1) != finderColsList.size()) {
						sm.append("msg.append(\", \");");
					}

					if ((j + 1) == finderColsList.size()) {
						sm.append("msg.append(StringPool.CLOSE_CURLY_BRACE);");
					}
				}

				sm.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg.toString());");
				sm.append("}");
				sm.append("else {");
				sm.append("return (" + entity.getName() + ")list.get(0);");
				sm.append("}");
				sm.append("}");

				sm.append("public " + entity.getName() + "[] findBy" + finder.getName() + "_PrevAndNext(" + pkClassName + " " + pkVarName + ", ");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName() + ", ");
				}

				sm.append("OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sm.append(entity.getName() + " " + entity.getVarName() + " = findByPrimaryKey(" + pkVarName + ");");

				sm.append("int count = countBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append("" + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(");");

				sm.append("Session session = null;");
				sm.append("try {");
				sm.append("session = openSession();");
				sm.append("StringMaker query = new StringMaker();");
				sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + " WHERE \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						_appendNullLogic(col, sm);
					}

					sm.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}

					if ((j + 1) != finderColsList.size()) {
						sm.append("query.append(\" AND \");");
					}
					else if (Validator.isNull(finder.getWhere())) {
						sm.append("query.append(\" \");");
					}
					else {
						sm.append("query.append(\" AND " + finder.getWhere() + " \");");
					}
				}

				sm.append("if (obc != null) {");
				sm.append("query.append(\"ORDER BY \");");
				sm.append("query.append(obc.getOrderBy());");
				sm.append("}");

				if (order != null) {
					List orderList = order.getColumns();

					sm.append("else {");
					sm.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < orderList.size(); j++) {
						EntityColumn col = (EntityColumn)orderList.get(j);

						sm.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != orderList.size()) {
							sm.append(".append(\", \");");
						}
						else {
							sm.append(";");
						}
					}

					sm.append("}");
				}

				sm.append("Query q = session.createQuery(query.toString());");
				sm.append("q.setCacheable(true);");
				sm.append("int queryPos = 0;");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (_requiresNullCheck(col)) {
						sm.append("if (" + col.getName() + " != null) {");
					}

					String colType = col.getType();
					String colObjType = colType;

					if (col.isPrimitiveType()) {
						colObjType = _getPrimitiveObj(colType);
					}

					sm.append("q.set" + colObjType + "(queryPos++, " + col.getName());

					if (colType.equals("Boolean")) {
						sm.append(".booleanValue()");
					}
					else if (colType.equals("Double")) {
						sm.append(".doubleValue()");
					}
					else if (colType.equals("Float")) {
						sm.append(".floatValue()");
					}
					else if (colType.equals("Integer")) {
						sm.append(".intValue()");
					}
					else if (colType.equals("Long")) {
						sm.append(".longValue()");
					}
					else if (colType.equals("Short")) {
						sm.append(".shortValue()");
					}

					sm.append(");");

					if (_requiresNullCheck(col)) {
						sm.append("}");
					}
				}

				sm.append("Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, " + entity.getVarName() + ");");

				sm.append(entity.getName() + "[] array = new " + entity.getName() + "Impl[3];");

				sm.append("array[0] = (" + entity.getName() + ")objArray[0];");
				sm.append("array[1] = (" + entity.getName() + ")objArray[1];");
				sm.append("array[2] = (" + entity.getName() + ")objArray[2];");

				sm.append("return array;");
				sm.append("}");
				sm.append("catch (Exception e) {");
				sm.append("throw HibernateUtil.processException(e);");
				sm.append("}");
				sm.append("finally {");
				sm.append("closeSession(session);");
				sm.append("}");
				sm.append("}");
			}
		}

		sm.append("public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer) throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("DynamicQuery query = queryInitializer.initialize(session);");
		sm.append("return query.list();");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		sm.append("public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer, int begin, int end) throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("DynamicQuery query = queryInitializer.initialize(session);");
		sm.append("query.setLimit(begin, end);");
		sm.append("return query.list();");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		sm.append("public List findAll() throws SystemException {");
		sm.append("return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);");
		sm.append("}");

		sm.append("public List findAll(int begin, int end) throws SystemException {");
		sm.append("return findAll(begin, end, null);");
		sm.append("}");

		sm.append("public List findAll(int begin, int end, OrderByComparator obc) throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("StringMaker query = new StringMaker();");
		sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + " \");");

		sm.append("if (obc != null) {");
		sm.append("query.append(\"ORDER BY \");");
		sm.append("query.append(obc.getOrderBy());");
		sm.append("}");

		EntityOrder order = entity.getOrder();

		if (order != null) {
			List orderList = order.getColumns();

			sm.append("else {");
			sm.append("query.append(\"ORDER BY \");");

			for (int j = 0; j < orderList.size(); j++) {
				EntityColumn col = (EntityColumn)orderList.get(j);

				sm.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

				if ((j + 1) != orderList.size()) {
					sm.append(".append(\", \");");
				}
				else {
					sm.append(";");
				}
			}

			sm.append("}");
		}

		sm.append("Query q = session.createQuery(query.toString());");
		sm.append("q.setCacheable(true);");
		sm.append("return QueryUtil.list(q, getDialect(), begin, end);");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		// Remove by methods

		for (int i = 0; i < finderList.size(); i++) {
			EntityFinder finder = (EntityFinder)finderList.get(i);

			List finderColsList = finder.getColumns();

			if (!finder.isCollection()) {
				sm.append("public void removeBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}
				sm.append(") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sm.append(entity.getName() + " " + entity.getVarName() + " = findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(");");
				sm.append("remove(" + entity.getVarName() + ");");
				sm.append("}");
			}
			else {
				sm.append("public void removeBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getType() + " " + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(") throws SystemException {");
				sm.append("Iterator itr = findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sm.append(col.getName());

					if ((j + 1) != finderColsList.size()) {
						sm.append(", ");
					}
				}

				sm.append(").iterator();");
				sm.append("while (itr.hasNext()) {");
				sm.append(entity.getName() + " " + entity.getVarName() + " = (" + entity.getName() + ")itr.next();");
				sm.append("remove(" + entity.getVarName() + ");");
				sm.append("}");
				sm.append("}");
			}
		}

		sm.append("public void removeAll() throws SystemException {");
		sm.append("Iterator itr = findAll().iterator();");
		sm.append("while (itr.hasNext()) {");
		sm.append("remove((" + entity.getName() + ")itr.next());");
		sm.append("}");
		sm.append("}");

		// Count by methods

		for (int i = 0; i < finderList.size(); i++) {
			EntityFinder finder = (EntityFinder)finderList.get(i);

			List finderColsList = finder.getColumns();

			sm.append("public int countBy" + finder.getName() + "(");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sm.append(col.getType() + " " + col.getName());

				if ((j + 1) != finderColsList.size()) {
					sm.append(", ");
				}
			}

			sm.append(") throws SystemException {");
			sm.append("Session session = null;");
			sm.append("try {");
			sm.append("session = openSession();");
			sm.append("StringMaker query = new StringMaker();");
			sm.append("query.append(\"SELECT COUNT(*) \");");
			sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + " WHERE \");");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				if (_requiresNullCheck(col)) {
					_appendNullLogic(col, sm);
				}

				sm.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

				if (_requiresNullCheck(col)) {
					sm.append("}");
				}

				if ((j + 1) != finderColsList.size()) {
					sm.append("query.append(\" AND \");");
				}
				else if (Validator.isNull(finder.getWhere())) {
					sm.append("query.append(\" \");");
				}
				else {
					sm.append("query.append(\" AND " + finder.getWhere() + " \");");
				}
			}

			sm.append("Query q = session.createQuery(query.toString());");
			sm.append("q.setCacheable(true);");
			sm.append("int queryPos = 0;");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				if (_requiresNullCheck(col)) {
					sm.append("if (" + col.getName() + " != null) {");
				}

				String colType = col.getType();
				String colObjType = colType;

				if (col.isPrimitiveType()) {
					colObjType = _getPrimitiveObj(colType);
				}

				sm.append("q.set" + colObjType + "(queryPos++, " + col.getName());

				if (colType.equals("Boolean")) {
					sm.append(".booleanValue()");
				}
				else if (colType.equals("Double")) {
					sm.append(".doubleValue()");
				}
				else if (colType.equals("Float")) {
					sm.append(".floatValue()");
				}
				else if (colType.equals("Integer")) {
					sm.append(".intValue()");
				}
				else if (colType.equals("Long")) {
					sm.append(".longValue()");
				}
				else if (colType.equals("Short")) {
					sm.append(".shortValue()");
				}

				sm.append(");");

				if (_requiresNullCheck(col)) {
					sm.append("}");
				}
			}

			sm.append("Iterator itr = q.list().iterator();");
			sm.append("if (itr.hasNext()) {");
			sm.append("Long count = (Long)itr.next();");
			sm.append("if (count != null) {");
			sm.append("return count.intValue();");
			sm.append("}");
			sm.append("}");
			sm.append("return 0;");
			sm.append("}");
			sm.append("catch (Exception e) {");
			sm.append("throw HibernateUtil.processException(e);");
			sm.append("}");
			sm.append("finally {");
			sm.append("closeSession(session);");
			sm.append("}");
			sm.append("}");
		}

		sm.append("public int countAll() throws SystemException {");
		sm.append("Session session = null;");
		sm.append("try {");
		sm.append("session = openSession();");
		sm.append("StringMaker query = new StringMaker();");
		sm.append("query.append(\"SELECT COUNT(*) \");");
		sm.append("query.append(\"FROM " + _packagePath + ".model." + entity.getName() + "\");");
		sm.append("Query q = session.createQuery(query.toString());");
		sm.append("q.setCacheable(true);");
		sm.append("Iterator itr = q.list().iterator();");
		sm.append("if (itr.hasNext()) {");
		sm.append("Long count = (Long)itr.next();");
		sm.append("if (count != null) {");
		sm.append("return count.intValue();");
		sm.append("}");
		sm.append("}");
		sm.append("return 0;");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("throw HibernateUtil.processException(e);");
		sm.append("}");
		sm.append("finally {");
		sm.append("closeSession(session);");
		sm.append("}");
		sm.append("}");

		// Relationship methods

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if ((col.isCollection()) &&
				(col.isMappingManyToMany() || col.isMappingOneToMany())) {

				Entity tempEntity = getEntity(col.getEJBName());
				EntityOrder tempOrder = tempEntity.getOrder();

				// getUsers(String pk)

				sm.append("public List get" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sm.append("return get" + tempEntity.getNames() + "(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);");
				sm.append("}");

				// getUsers(String pk, int begin, int end)

				sm.append("public List get" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, int begin, int end) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sm.append("return get" + tempEntity.getNames() + "(pk, begin, end, null);");
				sm.append("}");

				// getUsers(String pk, int begin, int end, OrderByComparator obc)

				sm.append("public List get" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, int begin, int end, OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sm.append("Session session = null;");
				sm.append("try {");
				sm.append("session = HibernateUtil.openSession();");
				sm.append("StringMaker sm = new StringMaker();");
				sm.append("sm.append(_SQL_GET" + tempEntity.getName().toUpperCase() + "S);");

				sm.append("if (obc != null) {");
				sm.append("sm.append(\"ORDER BY \");");
				sm.append("sm.append(obc.getOrderBy());");
				sm.append("}");

				if (tempOrder != null) {
					List tempOrderList = tempOrder.getColumns();

					sm.append("else {");
					sm.append("sm.append(\"ORDER BY \");");

					for (int j = 0; j < tempOrderList.size(); j++) {
						EntityColumn tempOrderCol = (EntityColumn)tempOrderList.get(j);

						sm.append("sm.append(\"" + tempEntity.getTable() + "." + tempOrderCol.getDBName() + " " + (tempOrderCol.isOrderByAscending() ? "ASC" : "DESC") + "\");");

						if ((j + 1) != tempOrderList.size()) {
							sm.append("sm.append(\", \");");
						}
					}

					sm.append("}");
				}

				sm.append("String sql = sm.toString();");
				sm.append("SQLQuery q = session.createSQLQuery(sql);");
				sm.append("q.setCacheable(false);");
				sm.append("q.addEntity(\"" + tempEntity.getTable() + "\", " + tempEntity.getPackagePath() + ".model.impl." + tempEntity.getName() + "Impl.class);");
				sm.append("QueryPos qPos = QueryPos.getInstance(q);");
				sm.append("qPos.add(pk);");
				sm.append("return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);");
				sm.append("}");
				sm.append("catch (Exception e) {");
				sm.append("throw new SystemException(e);");
				sm.append("}");
				sm.append("finally {");
				sm.append("HibernateUtil.closeSession(session);");
				sm.append("}");
				sm.append("}");

				// getUsersSize(String pk)

				sm.append("public int get" + tempEntity.getNames() + "Size(" + entity.getPKClassName() + " pk) throws SystemException {");
				sm.append("Session session = null;");
				sm.append("try {");
				sm.append("session = openSession();");
				sm.append("SQLQuery q = session.createSQLQuery(_SQL_GET" + tempEntity.getName().toUpperCase() + "SSIZE);");
				sm.append("q.setCacheable(false);");
				sm.append("q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);");
				sm.append("QueryPos qPos = QueryPos.getInstance(q);");
				sm.append("qPos.add(pk);");
				sm.append("Iterator itr = q.list().iterator();");
				sm.append("if (itr.hasNext()) {");
				sm.append("Long count = (Long)itr.next();");
				sm.append("if (count != null) {");
				sm.append("return count.intValue();");
				sm.append("}");
				sm.append("}");
				sm.append("return 0;");
				sm.append("}");
				sm.append("catch (Exception e) {");
				sm.append("throw HibernateUtil.processException(e);");
				sm.append("}");
				sm.append("finally {");
				sm.append("closeSession(session);");
				sm.append("}");
				sm.append("}");

				// containsUser(String pk, String userPK)

				sm.append("public boolean contains" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws SystemException {");
				sm.append("try {");
				sm.append("return contains" + tempEntity.getName() + ".contains(pk, " + tempEntity.getVarName() + "PK);");
				sm.append("}");
				sm.append("catch (DataAccessException dae) {");
				sm.append("throw new SystemException(dae);");
				sm.append("}");
				sm.append("}");

				// containsUsers(String pk)

				sm.append("public boolean contains" + tempEntity.getName() + "s(" + entity.getPKClassName() + " pk) throws SystemException {");
				sm.append("if (get" + tempEntity.getNames() + "Size(pk) > 0) {");
				sm.append("return true;");
				sm.append("}");
				sm.append("else {");
				sm.append("return false;");
				sm.append("}");
				sm.append("}");

				if (col.isMappingManyToMany()) {

					// addUser(String pk, String userPK)

					sm.append("public void add" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("add" + tempEntity.getName() + ".add(pk, " + tempEntity.getVarName() + "PK);");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// addUser(String pk, User user)

					sm.append("public void add" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("add" + tempEntity.getName() + ".add(pk, " + tempEntity.getVarName() + ".getPrimaryKey());");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// addUsers(String pk, String[] userPKs)

					sm.append("public void add" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + "[] " + tempEntity.getVarName() + "PKs) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("for (int i = 0; i < " + tempEntity.getVarName() + "PKs.length; i++) {");
					sm.append("add" + tempEntity.getName() + ".add(pk, " + tempEntity.getVarName() + "PKs[i]);");
					sm.append("}");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// addUsers(String pk, List users)

					sm.append("public void add" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, List " + tempEntity.getVarNames() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("for (int i = 0; i < " + tempEntity.getVarNames() + ".size(); i++) {");
					sm.append(tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + " = (" + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + ")" + tempEntity.getVarNames() + ".get(i);");
					sm.append("add" + tempEntity.getName() + ".add(pk, " + tempEntity.getVarName() + ".getPrimaryKey());");
					sm.append("}");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// clearUsers(String pk)

					sm.append("public void clear" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("clear" + tempEntity.getNames() + ".clear(pk);");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// removeUser(String pk, String userPK)

					sm.append("public void remove" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("remove" + tempEntity.getName() + ".remove(pk, " + tempEntity.getVarName() + "PK);");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// removeUser(String pk, User user)

					sm.append("public void remove" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("remove" + tempEntity.getName() + ".remove(pk, " + tempEntity.getVarName() + ".getPrimaryKey());");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// removeUsers(String pk, String[] userPKs)

					sm.append("public void remove" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + "[] " + tempEntity.getVarName() + "PKs) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("for (int i = 0; i < " + tempEntity.getVarName() + "PKs.length; i++) {");
					sm.append("remove" + tempEntity.getName() + ".remove(pk, " + tempEntity.getVarName() + "PKs[i]);");
					sm.append("}");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// removeUsers(String pk, List users)

					sm.append("public void remove" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, List " + tempEntity.getVarNames() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("for (int i = 0; i < " + tempEntity.getVarNames() + ".size(); i++) {");
					sm.append(tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + " = (" + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + ")" + tempEntity.getVarNames() + ".get(i);");
					sm.append("remove" + tempEntity.getName() + ".remove(pk, " + tempEntity.getVarName() + ".getPrimaryKey());");
					sm.append("}");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// setUsers(String pk, String[] pks)

					sm.append("public void set" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + "[] " + tempEntity.getVarName() + "PKs) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("clear" + tempEntity.getNames() + ".clear(pk);");
					sm.append("for (int i = 0; i < " + tempEntity.getVarName() + "PKs.length; i++) {");
					sm.append("add" + tempEntity.getName() + ".add(pk, " + tempEntity.getVarName() + "PKs[i]);");
					sm.append("}");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");

					// setUsers(String pk, List pks)

					sm.append("public void set" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, List " + tempEntity.getVarNames() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
					sm.append("try {");
					sm.append("clear" + tempEntity.getNames() + ".clear(pk);");
					sm.append("for (int i = 0; i < " + tempEntity.getVarNames() + ".size(); i++) {");
					sm.append(tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + " = (" + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + ")" + tempEntity.getVarNames() + ".get(i);");
					sm.append("add" + tempEntity.getName() + ".add(pk, " + tempEntity.getVarName() + ".getPrimaryKey());");
					sm.append("}");
					sm.append("}");
					sm.append("catch (DataAccessException dae) {");
					sm.append("throw new SystemException(dae);");
					sm.append("}");
					sm.append("}");
				}
			}
		}

		sm.append("protected void initDao() {");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if ((col.isCollection()) &&
				(col.isMappingManyToMany() || col.isMappingOneToMany())) {

				Entity tempEntity = getEntity(col.getEJBName());

				// containsUser(String pk, String userPK)

				sm.append("contains" + tempEntity.getName() + " = new Contains" + tempEntity.getName() + "(this);");

				if (col.isMappingManyToMany()) {

					// addUser(String pk, String userPK)

					sm.append("add" + tempEntity.getName() + " = new Add" + tempEntity.getName() + "(this);");

					// clearUsers(String pk)

					sm.append("clear" + tempEntity.getNames() + " = new Clear" + tempEntity.getNames() + "(this);");

					// removeUser(String pk, String userPK)

					sm.append("remove" + tempEntity.getName() + " = new Remove" + tempEntity.getName() + "(this);");
				}
			}
		}

		sm.append("}");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if ((col.isCollection()) &&
				(col.isMappingManyToMany() || col.isMappingOneToMany())) {

				Entity tempEntity = getEntity(col.getEJBName());

				// containsUser(String pk, String userPK)

				sm.append("protected Contains" + tempEntity.getName() + " contains" + tempEntity.getName() + ";");

				if (col.isMappingManyToMany()) {

					// addUser(String pk, String userPK)

					sm.append("protected Add" + tempEntity.getName() + " add" + tempEntity.getName() + ";");

					// clearUsers(String pk)

					sm.append("protected Clear" + tempEntity.getNames() + " clear" + tempEntity.getNames() + ";");

					// removeUser(String pk, String userPK)

					sm.append("protected Remove" + tempEntity.getName() + " remove" + tempEntity.getName() + ";");
				}
			}
		}

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if ((col.isCollection()) &&
				(col.isMappingManyToMany() || col.isMappingOneToMany())) {

				Entity tempEntity = getEntity(col.getEJBName());

				String entitySqlType = _getSqlType(_packagePath + ".model." + entity.getName(), entity.getPKVarName(), entity.getPKClassName());

				String pkVarNameWrapper = pkVarName;

				if (entity.hasPrimitivePK()) {
					pkVarNameWrapper = "new " + _getPrimitiveObj(entity.getPKClassName()) + "(" + pkVarName + ")";
				}

				String tempEntitySqlType = _getSqlType(tempEntity.getPackagePath() + ".model." + entity.getName(), tempEntity.getPKVarName(), tempEntity.getPKClassName());

				String tempEntityPkVarNameWrapper = tempEntity.getPKVarName();

				if (tempEntity.hasPrimitivePK()) {
					tempEntityPkVarNameWrapper = "new " + _getPrimitiveObj(tempEntity.getPKClassName()) + "(" + tempEntityPkVarNameWrapper + ")";
				}

				// containsUser(String pk, String userPK)

				sm.append("protected class Contains" + tempEntity.getName() + " extends MappingSqlQuery {");
				sm.append("protected Contains" + tempEntity.getName() + "(" + entity.getName() + "Persistence persistence) {");
				sm.append("super(persistence.getDataSource(), _SQL_CONTAINS" + tempEntity.getName().toUpperCase() + ");");
				sm.append("declareParameter(new SqlParameter(Types." + entitySqlType + "));");
				sm.append("declareParameter(new SqlParameter(Types." + tempEntitySqlType + "));");
				sm.append("compile();");
				sm.append("}");
				sm.append("protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException {");
				sm.append("return new Integer(rs.getInt(\"COUNT_VALUE\"));");
				sm.append("}");
				sm.append("protected boolean contains(" + pkClassName + " " + pkVarName + ", " + tempEntity.getPKClassName() + " " + tempEntity.getPKVarName() + ") {");
				sm.append("List results = execute(new Object[] {" + pkVarNameWrapper + ", " + tempEntityPkVarNameWrapper + "});");
				sm.append("if (results.size() > 0) {");
				sm.append("Integer count = (Integer)results.get(0);");
				sm.append("if (count.intValue() > 0) {");
				sm.append("return true;");
				sm.append("}");
				sm.append("}");
				sm.append("return false;");
				sm.append("}");
				sm.append("}");

				if (col.isMappingManyToMany()) {

					// addUser(String pk, String userPK)

					sm.append("protected class Add" + tempEntity.getName() + " extends SqlUpdate {");
					sm.append("protected Add" + tempEntity.getName() + "(" + entity.getName() + "Persistence persistence) {");
					sm.append("super(persistence.getDataSource(), \"INSERT INTO " + col.getMappingTable() + " (" + pkVarName + ", " + tempEntity.getPKVarName() + ") VALUES (?, ?)\");");
					sm.append("_persistence = persistence;");
					sm.append("declareParameter(new SqlParameter(Types." + entitySqlType + "));");
					sm.append("declareParameter(new SqlParameter(Types." + tempEntitySqlType + "));");
					sm.append("compile();");
					sm.append("}");
					sm.append("protected void add(" + pkClassName + " " + pkVarName + ", " + tempEntity.getPKClassName() + " " + tempEntity.getPKVarName() + ") {");
					sm.append("if (!_persistence.contains" + tempEntity.getName() + ".contains(" + pkVarName + ", " + tempEntity.getPKVarName() + ")) {");
					sm.append("update(new Object[] {" + pkVarNameWrapper + ", " + tempEntityPkVarNameWrapper + "});");
					sm.append("}");
					sm.append("}");
					sm.append("private " + entity.getName() + "Persistence _persistence;");
					sm.append("}");

					// clearUsers(String pk)

					sm.append("protected class Clear" + tempEntity.getNames() + " extends SqlUpdate {");
					sm.append("protected Clear" + tempEntity.getNames() + "(" + entity.getName() + "Persistence persistence) {");
					sm.append("super(persistence.getDataSource(), \"DELETE FROM " + col.getMappingTable() + " WHERE " + pkVarName + " = ?\");");
					sm.append("declareParameter(new SqlParameter(Types." + entitySqlType + "));");
					sm.append("compile();");
					sm.append("}");
					sm.append("protected void clear(" + pkClassName + " " + pkVarName + ") {");
					sm.append("update(new Object[] {" + pkVarNameWrapper + "});");
					sm.append("}");
					sm.append("}");

					// removeUser(String pk, String userPK)

					sm.append("protected class Remove" + tempEntity.getName() + " extends SqlUpdate {");
					sm.append("protected Remove" + tempEntity.getName() + "(" + entity.getName() + "Persistence persistence) {");
					sm.append("super(persistence.getDataSource(), \"DELETE FROM " + col.getMappingTable() + " WHERE " + pkVarName + " = ? AND " + tempEntity.getPKVarName() + " = ?\");");
					sm.append("declareParameter(new SqlParameter(Types." + entitySqlType + "));");
					sm.append("declareParameter(new SqlParameter(Types." + tempEntitySqlType + "));");
					sm.append("compile();");
					sm.append("}");
					sm.append("protected void remove(" + pkClassName + " " + pkVarName + ", " + tempEntity.getPKClassName() + " " + tempEntity.getPKVarName() + ") {");
					sm.append("update(new Object[] {" + pkVarNameWrapper + ", " + tempEntityPkVarNameWrapper + "});");
					sm.append("}");
					sm.append("}");
				}
			}
		}

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (col.isCollection()) {
				Entity tempEntity = getEntity(col.getEJBName());

				if (col.isMappingManyToMany()) {

					// getUsers(String pk)

					sm.append("private static final String _SQL_GET" + tempEntity.getName().toUpperCase() + "S = \"SELECT {" + tempEntity.getTable() + ".*} FROM " + tempEntity.getTable() + " INNER JOIN " + col.getMappingTable() + " ON (" + col.getMappingTable() + "." + tempEntity.getPKVarName() + " = " + tempEntity.getTable() + "." + tempEntity.getPKVarName() + ") WHERE (" + col.getMappingTable() + "." + entity.getPKVarName() + " = ?)\";");

					// getUsersSize(String pk)

					sm.append("private static final String _SQL_GET" + tempEntity.getName().toUpperCase() + "SSIZE = \"SELECT COUNT(*) AS COUNT_VALUE FROM " + col.getMappingTable() + " WHERE " + entity.getPKVarName() + " = ?\";");

					// containsUser(String pk, String userPK)

					sm.append("private static final String _SQL_CONTAINS" + tempEntity.getName().toUpperCase() + " = \"SELECT COUNT(*) AS COUNT_VALUE FROM " + col.getMappingTable() + " WHERE " + entity.getPKVarName() + " = ? AND " + tempEntity.getPKVarName() + " = ?\";");
				}
				else if (col.isMappingOneToMany()) {

					// getUsers(String pk)

					sm.append("private static final String _SQL_GET" + tempEntity.getName().toUpperCase() + "S = \"SELECT {" + tempEntity.getTable() + ".*} FROM " + tempEntity.getTable() + " INNER JOIN " + entity.getTable() + " ON (" + entity.getTable() + "." + entity.getPKVarName() + " = " + tempEntity.getTable() + "." + entity.getPKVarName() + ") WHERE (" + entity.getTable() + "." + entity.getPKVarName() + " = ?)\";");

					// getUsersSize(String pk)

					sm.append("private static final String _SQL_GET" + tempEntity.getName().toUpperCase() + "SSIZE = \"SELECT COUNT(*) AS COUNT_VALUE FROM " + tempEntity.getTable() + " WHERE " + entity.getPKVarName() + " = ?\";");

					// containsUser(String pk, String userPK)

					sm.append("private static final String _SQL_CONTAINS" + tempEntity.getName().toUpperCase() + " = \"SELECT COUNT(*) AS COUNT_VALUE FROM " + tempEntity.getTable() + " WHERE " + entity.getPKVarName() + " = ? AND " + tempEntity.getPKVarName() + " = ?\";");
				}
			}
		}

		// Fields

		sm.append("private static Log _log = LogFactory.getLog(" + entity.getName() + "Persistence.class);");

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "Persistence.java");

		writeFile(ejbFile, sm.toString());
	}

	private void _createPersistenceUtil(Entity entity) throws IOException {
		JavaClass javaClass = _getJavaClass(_outputPath + "/service/persistence/" + entity.getName() + "Persistence.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sm.append("import com.liferay.portal.model.ModelListener;");
		sm.append("import com.liferay.portal.util.PropsUtil;");
		sm.append("import com.liferay.util.GetterUtil;");
		sm.append("import com.liferay.util.InstancePool;");
		sm.append("import com.liferay.util.Validator;");
		sm.append("import org.apache.commons.logging.Log;");
		sm.append("import org.apache.commons.logging.LogFactory;");

		// Class declaration

		sm.append("public class " + entity.getName() + "Util {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor()) {
				sm.append("public static " + javaMethod.getReturns().getValue() + _getDimensions(javaMethod.getReturns()) + " " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				String p0Name = "";

				if (parameters.length > 0) {
					p0Name = parameters[0].getName();
				}

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getType().getValue() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				if (thrownExceptions.length > 0) {
					sm.append(" throws ");

					for (int j = 0; j < thrownExceptions.length; j++) {
						Type thrownException = thrownExceptions[j];

						sm.append(thrownException.getValue());

						if ((j + 1) != thrownExceptions.length) {
							sm.append(", ");
						}
					}
				}

				sm.append(" {");

				if (methodName.equals("remove") || methodName.equals("update")) {
					sm.append("ModelListener listener = _getListener();");

					if (methodName.equals("update")) {
						sm.append("boolean isNew = " + p0Name + ".isNew();");
					}

					sm.append("if (listener != null) {");

					if (methodName.equals("remove")) {
						if (entity.getVarName().equals(p0Name)) {
							sm.append("listener.onBeforeRemove(" + p0Name + ");");
						}
						else {
							sm.append("listener.onBeforeRemove(findByPrimaryKey(" + p0Name + "));");
						}
					}
					else {
						sm.append("if (isNew) {");
						sm.append("listener.onBeforeCreate(" + p0Name + ");");
						sm.append("}");
						sm.append("else {");
						sm.append("listener.onBeforeUpdate(" + p0Name + ");");
						sm.append("}");
					}

					sm.append("}");

					if (methodName.equals("remove") && !entity.getVarName().equals(p0Name)) {
						sm.append(_packagePath + ".model." + entity.getName() + " " + entity.getVarName() + " = ");
					}
					else {
						sm.append(entity.getVarName() + " = ");
					}
				}
				else {
					if (!javaMethod.getReturns().getValue().equals("void")) {
						sm.append("return ");
					}
				}

				sm.append("getPersistence()." + methodName + "(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(");");

				if (methodName.equals("remove") || methodName.equals("update")) {
					sm.append("if (listener != null) {");

					if (methodName.equals("remove")) {
						sm.append("listener.onAfterRemove(" + entity.getVarName() + ");");
					}
					else {
						sm.append("if (isNew) {");
						sm.append("listener.onAfterCreate(" + entity.getVarName() + ");");
						sm.append("}");
						sm.append("else {");
						sm.append("listener.onAfterUpdate(" + entity.getVarName() + ");");
						sm.append("}");
					}

					sm.append("}");

					sm.append("return " + entity.getVarName() + ";");
				}

				sm.append("}");
			}
		}

		sm.append("public static " + entity.getName() + "Persistence getPersistence() {");
		sm.append("return _getUtil()._persistence;");
		sm.append("}");

		sm.append("public void setPersistence(" + entity.getName() + "Persistence persistence) {");
		sm.append("_persistence = persistence;");
		sm.append("}");

		sm.append("private static " + entity.getName() + "Util _getUtil() {");
		sm.append("if (_util == null) {");
		sm.append("_util = (" + entity.getName() + "Util)" + _beanLocatorUtilClassName + ".locate(_UTIL);");
		sm.append("}");
		sm.append("return _util;");
		sm.append("}");

		sm.append("private static ModelListener _getListener() {");
		sm.append("if (Validator.isNotNull(_LISTENER)) {");
		sm.append("try {");
		sm.append("return (ModelListener)Class.forName(_LISTENER).newInstance();");
		sm.append("}");
		sm.append("catch (Exception e) {");
		sm.append("_log.error(e);");
		sm.append("}");
		sm.append("}");
		sm.append("return null;");
		sm.append("}");

		// Fields

		sm.append("private static final String _UTIL = " + entity.getName() + "Util.class.getName();");
		sm.append("private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(\"value.object.listener." + _packagePath + ".model." + entity.getName() + "\"));");

		sm.append("private static Log _log = LogFactory.getLog(" + entity.getName() + "Util.class);");

		sm.append("private static " + entity.getName() + "Util _util;");

		sm.append("private " + entity.getName() + "Persistence _persistence;");

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "Util.java");

		writeFile(ejbFile, sm.toString());
	}

	private void _createPool(Entity entity) throws IOException {
		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "Pool.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createService(Entity entity, int sessionType) throws IOException {
		JavaClass javaClass = _getJavaClass(_outputPath + "/service/impl/" + entity.getName() + (sessionType != _REMOTE ? "Local" : "") + "ServiceImpl.java");

		JavaMethod[] methods = javaClass.getMethods();

		if (sessionType == _LOCAL) {
			if (javaClass.getSuperClass().getValue().endsWith(entity.getName() + "LocalServiceBaseImpl")) {
				JavaClass parentJavaClass = _getJavaClass(_outputPath + "/service/base/" + entity.getName() + "LocalServiceBaseImpl.java");

				JavaMethod[] parentMethods = parentJavaClass.getMethods();

				JavaMethod[] allMethods = new JavaMethod[parentMethods.length + methods.length];

				ArrayUtil.combine(parentMethods, methods, allMethods);

				methods = allMethods;
			}
		}

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service;");

		// Interface declaration

		sm.append("public interface " + entity.getName() + _getSessionTypeName(sessionType) + "Service {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && javaMethod.isPublic() && _isCustomMethod(javaMethod)) {
				sm.append("public " + javaMethod.getReturns().getValue() + _getDimensions(javaMethod.getReturns()) + " " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getType().getValue() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getValue());
				}

				if (sessionType != _LOCAL) {
					newExceptions.add("java.rmi.RemoteException");
				}

				if (newExceptions.size() > 0) {
					sm.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sm.append(itr.next());

						if (itr.hasNext()) {
							sm.append(", ");
						}
					}
				}

				sm.append(";");
			}
		}

		// Interface close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_serviceOutputPath + "/service/" + entity.getName() + _getSessionTypeName(sessionType) + "Service.java");

		Map jalopySettings = new HashMap();

		String serviceComments = null;

		if (sessionType == _REMOTE) {
			serviceComments = "This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.";
		}
		else {
			serviceComments = "This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.";
		}

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This interface defines the service. The default implementation is <code>" + _packagePath + ".service.impl." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl</code>. Modify methods in that class and rerun ServiceBuilder to populate this class and all other generated classes.",
			serviceComments
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + "ServiceFactory",
			_packagePath + ".service." + entity.getName() + "ServiceUtil"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);

		ejbFile = new File(_outputPath + "/service/spring/" + entity.getName() + _getSessionTypeName(sessionType) + "Service.java");

		if (ejbFile.exists()) {
			System.out.println("Relocating " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createServiceBaseImpl(Entity entity) throws IOException {
		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.base;");

		// Imports

		sm.append("import " + _packagePath + ".service." + entity.getName() + "LocalService;");
		sm.append("import " + _packagePath + ".service.persistence." + entity.getName() + "Util;");
		sm.append("import com.liferay.portal.SystemException;");
		sm.append("import com.liferay.portal.kernel.dao.DynamicQueryInitializer;");
		sm.append("import java.util.List;");

		// Class declaration

		sm.append("public abstract class " + entity.getName() + "LocalServiceBaseImpl implements " + entity.getName() + "LocalService {");

		// Methods

		if (entity.hasColumns()) {
			sm.append("public List dynamicQuery(DynamicQueryInitializer queryInitializer) throws SystemException {");
			sm.append("return " + entity.getName() + "Util.findWithDynamicQuery(queryInitializer);");
			sm.append("}");

			sm.append("public List dynamicQuery(DynamicQueryInitializer queryInitializer, int begin, int end) throws SystemException {");
			sm.append("return " + entity.getName() + "Util.findWithDynamicQuery(queryInitializer, begin, end);");
			sm.append("}");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/base/" + entity.getName() + "LocalServiceBaseImpl.java");

		writeFile(ejbFile, sm.toString());
	}

	private void _createServiceEJB(Entity entity, int sessionType) throws IOException {
		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sm.append("import " + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service;");

		if (sessionType == _LOCAL) {
			sm.append("import javax.ejb.EJBLocalObject;");
		}
		else {
			sm.append("import javax.ejb.EJBObject;");
		}

		// Interface declaration

		sm.append("public interface " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB extends EJB" + (sessionType == _LOCAL ? "Local" : "") + "Object, " + entity.getName() + _getSessionTypeName(sessionType) + "Service {");

		// Interface close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is the EJB interface of the service that is used when Liferay is run inside a full J2EE container."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service",
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil",
			_packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl",
			_packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome",
			_packagePath + ".service.impl." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceEJBImpl(Entity entity, int sessionType) throws IOException {
		JavaClass javaClass = _getJavaClass(_serviceOutputPath + "/service/" + entity.getName() + (sessionType != _REMOTE ? "Local" : "") + "Service.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sm.append("import " + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service;");

		if (methods.length > 0) {
			sm.append("import " + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory;");
		}

		sm.append("import javax.ejb.CreateException;");
		sm.append("import javax.ejb.SessionContext;");
		sm.append("import javax.ejb.SessionBean;");

		if (sessionType == _REMOTE) {
			sm.append("import com.liferay.portal.service.impl.PrincipalSessionBean;");
		}

		// Class declaration

		sm.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl implements " + entity.getName() + _getSessionTypeName(sessionType) + "Service, SessionBean {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && javaMethod.isPublic() && _isCustomMethod(javaMethod)) {
				sm.append("public " + javaMethod.getReturns().getValue() + _getDimensions(javaMethod.getReturns()) + " " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getType().getValue() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getValue());
				}

				if (newExceptions.size() > 0) {
					sm.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sm.append(itr.next());

						if (itr.hasNext()) {
							sm.append(", ");
						}
					}
				}

				sm.append("{");

				if (sessionType == _REMOTE) {
					sm.append("PrincipalSessionBean.setThreadValues(_sc);");
				}

				if (!javaMethod.getReturns().getValue().equals("void")) {
					sm.append("return ");
				}

				sm.append(entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.getTxImpl()." + methodName + "(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(");");
				sm.append("}");
			}
		}

		sm.append("public void ejbCreate() throws CreateException {");
		sm.append("}");

		sm.append("public void ejbRemove() {");
		sm.append("}");

		sm.append("public void ejbActivate() {");
		sm.append("}");

		sm.append("public void ejbPassivate() {");
		sm.append("}");

		sm.append("public SessionContext getSessionContext() {");
		sm.append("return _sc;");
		sm.append("}");

		sm.append("public void setSessionContext(SessionContext sc) {");
		sm.append("_sc = sc;");
		sm.append("}");

		// Fields

		sm.append("private SessionContext _sc;");

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is the EJB implementation of the service that is used when Liferay is run inside a full J2EE container."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service",
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil",
			_packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB",
			_packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome",
			_packagePath + ".service.impl." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceFactory(Entity entity, int sessionType) throws IOException {
		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service;");

		// Class declaration

		sm.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory {");

		// Methods

		sm.append("public static " + entity.getName() + _getSessionTypeName(sessionType) + "Service getService() {");
		sm.append("return _getFactory()._service;");
		sm.append("}");

		sm.append("public static " + entity.getName() + _getSessionTypeName(sessionType) + "Service getTxImpl() {");
		sm.append("if (_txImpl == null) {");
		sm.append("_txImpl = (" + entity.getName() + _getSessionTypeName(sessionType) + "Service)" + _beanLocatorUtilClassName + ".locate(_TX_IMPL);");
		sm.append("}");
		sm.append("return _txImpl;");
		sm.append("}");

		sm.append("public void setService(" + entity.getName() + _getSessionTypeName(sessionType) + "Service service) {");
		sm.append("_service = service;");
		sm.append("}");

		sm.append("private static " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory _getFactory() {");
		sm.append("if (_factory == null) {");
		sm.append("_factory = (" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory)" + _beanLocatorUtilClassName + ".locate(_FACTORY);");
		sm.append("}");
		sm.append("return _factory;");
		sm.append("}");

		// Fields

		sm.append("private static final String _FACTORY = " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.class.getName();");
		sm.append("private static final String _TX_IMPL = " + entity.getName() + _getSessionTypeName(sessionType) + "Service.class.getName() + \".transaction\";");

		sm.append("private static " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory _factory;");
		sm.append("private static " + entity.getName() + _getSessionTypeName(sessionType) + "Service _txImpl;");

		sm.append("private " + entity.getName() + _getSessionTypeName(sessionType) + "Service _service;");

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_serviceOutputPath + "/service/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is responsible for the lookup of the implementation for <code>" + _packagePath + ".service." + entity.getName() + "Service</code>. Spring manages the lookup and lifecycle of the beans. This means you can modify the Spring configuration files to return a different implementation or to inject additional behavior.",
			"See the <code>spring.configs</code> property in portal.properties for additional information on how to customize the Spring XML files."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + "Service",
			_packagePath + ".service." + entity.getName() + "ServiceUtil"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);

		ejbFile = new File(_outputPath + "/service/spring/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.java");

		if (ejbFile.exists()) {
			System.out.println("Relocating " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createServiceHome(Entity entity, int sessionType) throws IOException {
		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sm.append("import javax.ejb.CreateException;");

		if (sessionType == _LOCAL) {
			sm.append("import javax.ejb.EJBLocalHome;");
		}
		else {
			sm.append("import java.rmi.RemoteException;");
			sm.append("import javax.ejb.EJBHome;");
		}

		// Interface declaration

		sm.append("public interface " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome extends EJB" + (sessionType == _LOCAL ? "Local" : "") + "Home {");

		// Create method

		sm.append("public " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB create() throws CreateException");

		if (sessionType != _LOCAL) {
			sm.append(", RemoteException");
		}

		sm.append(";");

		// Interface close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is the EJB home of the service that is used when Liferay is run inside a full J2EE container."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service",
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil",
			_packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB",
			_packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl",
			_packagePath + ".service.impl." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceHttp(Entity entity) throws IOException {
		JavaClass javaClass = _getJavaClass(_outputPath + "/service/impl/" + entity.getName() + "ServiceImpl.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.http;");

		// Imports

		if (_hasHttpMethods(javaClass)) {
			sm.append("import " + _packagePath + ".service." + entity.getName() + "ServiceUtil;");
		}

		sm.append("import com.liferay.portal.kernel.log.Log;");
		sm.append("import com.liferay.portal.kernel.log.LogFactoryUtil;");
		sm.append("import com.liferay.portal.kernel.util.BooleanWrapper;");
		sm.append("import com.liferay.portal.kernel.util.DoubleWrapper;");
		sm.append("import com.liferay.portal.kernel.util.FloatWrapper;");
		sm.append("import com.liferay.portal.kernel.util.IntegerWrapper;");
		sm.append("import com.liferay.portal.kernel.util.LongWrapper;");
		sm.append("import com.liferay.portal.kernel.util.MethodWrapper;");
		sm.append("import com.liferay.portal.kernel.util.NullWrapper;");
		sm.append("import com.liferay.portal.kernel.util.ShortWrapper;");
		sm.append("import com.liferay.portal.security.auth.HttpPrincipal;");
		sm.append("import com.liferay.portal.service.http.TunnelUtil;");

		// Class declaration

		sm.append("public class " + entity.getName() + "ServiceHttp {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && javaMethod.isPublic() && _isCustomMethod(javaMethod)) {
				Type returnType = javaMethod.getReturns();
				String returnTypeName = returnType.getValue() + _getDimensions(returnType);

				sm.append("public static " + returnTypeName + " " + methodName + "(HttpPrincipal httpPrincipal");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					if (j == 0) {
						sm.append(", ");
					}

					sm.append(javaParameter.getType().getValue() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getValue());
				}

				newExceptions.add("com.liferay.portal.SystemException");

				if (newExceptions.size() > 0) {
					sm.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sm.append(itr.next());

						if (itr.hasNext()) {
							sm.append(", ");
						}
					}
				}

				sm.append("{");
				sm.append("try {");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					String parameterTypeName =
						javaParameter.getType().getValue() +
							_getDimensions(javaParameter.getType());

					sm.append("Object paramObj" + j + " = ");

					if (parameterTypeName.equals("boolean")) {
						sm.append("new BooleanWrapper(" + javaParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("double")) {
						sm.append("new DoubleWrapper(" + javaParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("float")) {
						sm.append("new FloatWrapper(" + javaParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("int")) {
						sm.append("new IntegerWrapper(" + javaParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("long")) {
						sm.append("new LongWrapper(" + javaParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("short")) {
						sm.append("new ShortWrapper(" + javaParameter.getName() + ");");
					}
					else {
						sm.append(javaParameter.getName() + ";");

						sm.append("if (" + javaParameter.getName() + " == null) {");
						sm.append("paramObj" + j + " = new NullWrapper(\"" + _getClassName(javaParameter.getType()) + "\");");
						sm.append("}");
					}
				}

				sm.append("MethodWrapper methodWrapper = new MethodWrapper(");
				sm.append(entity.getName() + "ServiceUtil.class.getName(),");
				sm.append("\"" + methodName + "\",");

				if (parameters.length == 0) {
					sm.append("new Object[0]);");
				}
				else {
					sm.append("new Object[] {");

					for (int j = 0; j < parameters.length; j++) {
						sm.append("paramObj" + j);

						if ((j + 1) != parameters.length) {
							sm.append(", ");
						}
					}

					sm.append("});");
				}

				if (!returnTypeName.equals("void")) {
					sm.append("Object returnObj = null;");
				}

				sm.append("try {");

				if (!returnTypeName.equals("void")) {
					sm.append("returnObj =");
				}

				sm.append("TunnelUtil.invoke(httpPrincipal, methodWrapper);");
				sm.append("}");
				sm.append("catch (Exception e) {");

				Iterator itr = newExceptions.iterator();

				while (itr.hasNext()) {
					String exceptionType = (String)itr.next();

					sm.append("if (e instanceof " + exceptionType + ") {");
					sm.append("throw (" + exceptionType + ")e;");
					sm.append("}");
				}

				sm.append("throw new com.liferay.portal.SystemException(e);");
				sm.append("}");

				if (!returnTypeName.equals("void")) {
					if (returnTypeName.equals("boolean")) {
						sm.append("return ((Boolean)returnObj).booleanValue();");
					}
					else if (returnTypeName.equals("double")) {
						sm.append("return ((Double)returnObj).doubleValue();");
					}
					else if (returnTypeName.equals("float")) {
						sm.append("return ((Float)returnObj).floatValue();");
					}
					else if (returnTypeName.equals("int")) {
						sm.append("return ((Integer)returnObj).intValue();");
					}
					else if (returnTypeName.equals("long")) {
						sm.append("return ((Long)returnObj).longValue();");
					}
					else if (returnTypeName.equals("short")) {
						sm.append("return ((Short)returnObj).shortValue();");
					}
					else {
						sm.append("return (" + returnTypeName + ")returnObj;");
					}
				}

				sm.append("}");
				sm.append("catch (com.liferay.portal.SystemException se) {");
				sm.append("_log.error(se, se);");
				sm.append("throw se;");
				sm.append("}");
				sm.append("}");
			}
		}

		// Fields

		if (sm.indexOf("_log.") != -1) {
			sm.append("private static Log _log = LogFactoryUtil.getLog(" + entity.getName() + "ServiceHttp.class);");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/http/" + entity.getName() + "ServiceHttp.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class provides a HTTP utility for the <code>" + _packagePath + ".service." + entity.getName() + "ServiceUtil</code> service utility. The static methods of this class calls the same methods of the service utility. However, the signatures are different because it requires an additional <code>com.liferay.portal.security.auth.HttpPrincipal</code> parameter.",
			"The benefits of using the HTTP utility is that it is fast and allows for tunneling without the cost of serializing to text. The drawback is that it only works with Java.",
			"Set the property <code>tunnel.servlet.hosts.allowed</code> in portal.properties to configure security.",
			"The HTTP utility is only generated for remote services."
		};

		String[] see = {
			"com.liferay.portal.security.auth.HttpPrincipal",
			_packagePath + ".service." + entity.getName() + "ServiceUtil",
			_packagePath + ".service.http." + entity.getName() + "ServiceSoap"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceImpl(Entity entity, int sessionType) throws IOException {
		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.impl;");

		// Imports

		sm.append("import " + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service;");

		if (sessionType == _REMOTE) {
			sm.append("import com.liferay.portal.service.impl.PrincipalBean;");
		}
		else {
			sm.append("import " + _packagePath + ".service.base." + entity.getName() + "LocalServiceBaseImpl;");
		}

		// Class declaration

		if (sessionType == _REMOTE) {
			sm.append("public class " + entity.getName() + "ServiceImpl extends PrincipalBean implements " + entity.getName() + "Service {");
		}
		else {
			sm.append("public class " + entity.getName() + "LocalServiceImpl extends " + entity.getName() + "LocalServiceBaseImpl {");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/impl/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl.java");

		if (!ejbFile.exists()) {
			writeFile(ejbFile, sm.toString());
		}
	}

	private void _createServiceJSON(Entity entity) throws IOException {
		JavaClass javaClass = _getJavaClass(_outputPath + "/service/impl/" + entity.getName() + "ServiceImpl.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.http;");

		// Imports

		if (_hasSoapMethods(javaClass)) {
			sm.append("import " + _packagePath + ".service." + entity.getName() + "ServiceUtil;");
		}

		sm.append("import org.json.JSONArray;");
		sm.append("import org.json.JSONObject;");

		// Class declaration

		sm.append("public class " + entity.getName() + "ServiceJSON {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && javaMethod.isPublic() && _isCustomMethod(javaMethod) && _isSoapMethod(javaMethod)) {
				String returnValueName = javaMethod.getReturns().getValue();
				String returnValueDimension = _getDimensions(javaMethod.getReturns());

				String extendedModelName = _packagePath + ".model." + entity.getName();
				String soapModelName = "JSONObject";

				sm.append("public static ");

				if (returnValueName.equals(extendedModelName)) {
					sm.append(soapModelName + returnValueDimension);
				}
				else if (returnValueName.equals("java.util.List")) {
					if (entity.hasColumns()) {
						sm.append("JSONArray");
					}
					else {
						sm.append("java.util.List");
					}
				}
				else if (returnValueName.equals("com.liferay.portal.kernel.json.JSONArrayWrapper")) {
					sm.append("JSONArray");
				}
				else if (returnValueName.equals("com.liferay.portal.kernel.json.JSONObjectWrapper")) {
					sm.append("JSONObject");
				}
				else {
					sm.append(returnValueName + returnValueDimension);
				}

				sm.append(" " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					String parameterTypeName = javaParameter.getType().getValue() + _getDimensions(javaParameter.getType());

					if (parameterTypeName.equals("java.util.Locale")) {
						parameterTypeName = "String";
					}

					sm.append(parameterTypeName + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getValue());
				}

				newExceptions.add("java.rmi.RemoteException");

				if (newExceptions.size() > 0) {
					sm.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sm.append(itr.next());

						if (itr.hasNext()) {
							sm.append(", ");
						}
					}
				}

				sm.append("{");

				if (!returnValueName.equals("void")) {
					sm.append(returnValueName + returnValueDimension + " returnValue = ");
				}

				sm.append(entity.getName() + "ServiceUtil." + methodName + "(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					String parameterTypeName =
						javaParameter.getType().getValue() +
							_getDimensions(javaParameter.getType());

					if (parameterTypeName.equals("java.util.Locale")) {
						sm.append("new java.util.Locale(");
					}

					sm.append(javaParameter.getName());

					if (parameterTypeName.equals("java.util.Locale")) {
						sm.append(")");
					}

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(");");

				if (!returnValueName.equals("void")) {
					if (returnValueName.equals(extendedModelName)) {
						sm.append("return " + entity.getName() + "JSONSerializer.toJSONObject(returnValue);");
					}
					else if (entity.hasColumns() && returnValueName.equals("java.util.List")) {
						sm.append("return " + entity.getName() + "JSONSerializer.toJSONArray(returnValue);");
					}
					else if (returnValueName.equals("com.liferay.portal.kernel.json.JSONArrayWrapper")) {
						sm.append("return (JSONArray)returnValue.getValue();");
					}
					else if (returnValueName.equals("com.liferay.portal.kernel.json.JSONObjectWrapper")) {
						sm.append("return (JSONObject)returnValue.getValue();");
					}
					else {
						sm.append("return returnValue;");
					}
				}

				sm.append("}");
			}
		}

		// Fields

		if (sm.indexOf("_log.") != -1) {
			sm.append("private static Log _log = LogFactoryUtil.getLog(" + entity.getName() + "ServiceJSON.class);");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/http/" + entity.getName() + "ServiceJSON.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class provides a JSON utility for the <code>" + _packagePath + ".service." + entity.getName() + "ServiceUtil</code> service utility. The static methods of this class calls the same methods of the service utility. However, the signatures are different because it is difficult for JSON to support certain types.",
			"ServiceBuilder follows certain rules in translating the methods. For example, if the method in the service utility returns a <code>java.util.List</code>, that is translated to a <code>org.json.JSONArray</code>. If the method in the service utility returns a <code>" + _packagePath + ".model." + entity.getName() + "</code>, that is translated to a <code>org.json.JSONObject</code>. Methods that JSON cannot safely use are skipped. The logic for the translation is encapsulated in <code>" + _packagePath + ".service.http." + entity.getName() + "JSONSerializer</code>.",
			"This allows you to call the the backend services directly from JavaScript. See <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a reference of how that portlet uses the generated JavaScript in <code>portal-web/docroot/html/js/service.js</code> to call the backend services directly from JavaScript.",
			"The JSON utility is only generated for remote services."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + "ServiceUtil",
			_packagePath + ".service.http." + entity.getName() + "JSONSerializer"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceJSONSerializer(Entity entity) throws IOException {
		List regularColList = entity.getRegularColList();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.http;");

		// Imports

		sm.append("import " + _packagePath + ".model." + entity.getName() + ";");
		sm.append("import com.liferay.portal.kernel.util.StringPool;");
		sm.append("import java.util.Date;");
		sm.append("import java.util.List;");
		sm.append("import org.json.JSONArray;");
		sm.append("import org.json.JSONObject;");

		// Class declaration

		sm.append("public class " + entity.getName() + "JSONSerializer {");

		// Methods

		sm.append("public static JSONObject toJSONObject(" + entity.getName() + " model) {");
		sm.append("JSONObject jsonObj = new JSONObject();");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = (EntityColumn)regularColList.get(i);

			if (col.isPrimitiveType()) {
				sm.append("jsonObj.put(\"" + col.getName() + "\", model.get" + col.getMethodName() + "());");
			}
			else {
				sm.append(col.getType() + " " + col.getName() + " = model.get" + col.getMethodName() + "();");
				sm.append("if (" + col.getName() + " == null) {");
				sm.append("jsonObj.put(\"" + col.getName() + "\", StringPool.BLANK);");
				sm.append("}");
				sm.append("else {");
				sm.append("jsonObj.put(\"" + col.getName() + "\", " + col.getName() + ".toString());");
				sm.append("}");
			}
		}

		sm.append("return jsonObj;");
		sm.append("}");

		sm.append("public static JSONArray toJSONArray(List models) {");
		sm.append("JSONArray jsonArray = new JSONArray();");
		sm.append("for (int i = 0; i < models.size(); i++) {");
		sm.append(entity.getName() + " model = (" + entity.getName() + ")models.get(i);");
		sm.append("jsonArray.put(toJSONObject(model));");
		sm.append("}");
		sm.append("return jsonArray;");
		sm.append("}");

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/http/" + entity.getName() + "JSONSerializer.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class is used by <code>" + _packagePath + ".service.http." + entity.getName() + "ServiceJSON</code> to translate objects."
		};

		String[] see = {
			_packagePath + ".service.http." + entity.getName() + "ServiceJSON"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceSoap(Entity entity) throws IOException {
		JavaClass javaClass = _getJavaClass(_outputPath + "/service/impl/" + entity.getName() + "ServiceImpl.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service.http;");

		// Imports

		if (_hasSoapMethods(javaClass)) {
			sm.append("import " + _packagePath + ".service." + entity.getName() + "ServiceUtil;");
		}

		sm.append("import com.liferay.portal.kernel.log.Log;");
		sm.append("import com.liferay.portal.kernel.log.LogFactoryUtil;");
		sm.append("import java.rmi.RemoteException;");

		// Class declaration

		sm.append("public class " + entity.getName() + "ServiceSoap {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && javaMethod.isPublic() && _isCustomMethod(javaMethod) && _isSoapMethod(javaMethod)) {
				String returnValueName = javaMethod.getReturns().getValue();
				String returnValueDimension = _getDimensions(javaMethod.getReturns());

				String extendedModelName = _packagePath + ".model." + entity.getName();
				String soapModelName = _packagePath + ".model." + entity.getName() + "Soap";

				sm.append("public static ");

				if (returnValueName.equals(extendedModelName)) {
					sm.append(soapModelName + returnValueDimension);
				}
				else if (returnValueName.equals("java.util.List")) {
					if (entity.hasColumns()) {
						sm.append(soapModelName + "[]");
					}
					else {
						sm.append("java.util.List");
					}
				}
				else {
					sm.append(returnValueName + returnValueDimension);
				}

				sm.append(" " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					String parameterTypeName = javaParameter.getType().getValue() + _getDimensions(javaParameter.getType());

					if (parameterTypeName.equals("java.util.Locale")) {
						parameterTypeName = "String";
					}

					sm.append(parameterTypeName + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(") throws RemoteException {");
				sm.append("try {");

				if (!returnValueName.equals("void")) {
					sm.append(returnValueName + returnValueDimension + " returnValue = ");
				}

				sm.append(entity.getName() + "ServiceUtil." + methodName + "(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					String parameterTypeName =
						javaParameter.getType().getValue() +
							_getDimensions(javaParameter.getType());

					if (parameterTypeName.equals("java.util.Locale")) {
						sm.append("new java.util.Locale(");
					}

					sm.append(javaParameter.getName());

					if (parameterTypeName.equals("java.util.Locale")) {
						sm.append(")");
					}

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(");");

				if (!returnValueName.equals("void")) {
					if (returnValueName.equals(extendedModelName)) {
						sm.append("return " + soapModelName + ".toSoapModel(returnValue);");
					}
					else if (entity.hasColumns() && returnValueName.equals("java.util.List")) {
						sm.append("return " + soapModelName + ".toSoapModels(returnValue);");
					}
					else {
						sm.append("return returnValue;");
					}
				}

				sm.append("}");

				sm.append("catch (Exception e) {");
				sm.append("_log.error(e, e);");
				sm.append("throw new RemoteException(e.getMessage());");
				sm.append("}");
				sm.append("}");
			}
		}

		// Fields

		if (sm.indexOf("_log.") != -1) {
			sm.append("private static Log _log = LogFactoryUtil.getLog(" + entity.getName() + "ServiceSoap.class);");
		}

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/http/" + entity.getName() + "ServiceSoap.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class provides a SOAP utility for the <code>" + _packagePath + ".service." + entity.getName() + "ServiceUtil</code> service utility. The static methods of this class calls the same methods of the service utility. However, the signatures are different because it is difficult for SOAP to support certain types.",
			"ServiceBuilder follows certain rules in translating the methods. For example, if the method in the service utility returns a <code>java.util.List</code>, that is translated to an array of <code>" + _packagePath + ".model." + entity.getName() + "Soap</code>. If the method in the service utility returns a <code>" + _packagePath + ".model." + entity.getName() + "</code>, that is translated to a <code>" + _packagePath + ".model." + entity.getName() + "Soap</code>. Methods that SOAP cannot safely wire are skipped.",
			"The benefits of using the SOAP utility is that it is cross platform compatible. SOAP allows different languages like Java, .NET, C++, PHP, and even Perl, to call the generated services. One drawback of SOAP is that it is slow because it needs to serialize all calls into a text format (XML).",
			"You can see a list of services at http://localhost:8080/tunnel-web/secure/axis. Set the property <code>tunnel.servlet.hosts.allowed</code> in portal.properties to configure security.",
			"The SOAP utility is only generated for remote services."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + "ServiceUtil",
			_packagePath + ".service.http." + entity.getName() + "ServiceHttp",
			_packagePath + ".service.model." + entity.getName() + "Soap"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);
	}

	private void _createServiceUtil(Entity entity, int sessionType) throws IOException {
		JavaClass javaClass = _getJavaClass(_serviceOutputPath + "/service/" + entity.getName() + (sessionType != _REMOTE ? "Local" : "") + "Service.java");

		JavaMethod[] methods = javaClass.getMethods();

		StringMaker sm = new StringMaker();

		// Package

		sm.append("package " + _packagePath + ".service;");

		// Class declaration

		sm.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil {");

		// Methods

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isConstructor() && javaMethod.isPublic() && _isCustomMethod(javaMethod)) {
				sm.append("public static " + javaMethod.getReturns().getValue() + _getDimensions(javaMethod.getReturns()) + " " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getType().getValue() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getValue());
				}

				if (newExceptions.size() > 0) {
					sm.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sm.append(itr.next());

						if (itr.hasNext()) {
							sm.append(", ");
						}
					}
				}

				sm.append("{");
				sm.append(entity.getName() + _getSessionTypeName(sessionType) + "Service " + entity.getVarName() + _getSessionTypeName(sessionType) + "Service = " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.getService();");

				if (!javaMethod.getReturns().getValue().equals("void")) {
					sm.append("return ");
				}

				sm.append(entity.getVarName() + _getSessionTypeName(sessionType) + "Service." + methodName + "(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sm.append(javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sm.append(", ");
					}
				}

				sm.append(");");
				sm.append("}");
			}
		}

		// Class close brace

		sm.append("}");

		// Write file

		File ejbFile = new File(_serviceOutputPath + "/service/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil.java");

		Map jalopySettings = new HashMap();

		String[] classComments = {
			_DEFAULT_CLASS_COMMENTS,
			"This class provides static methods for the <code>" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service</code> bean. The static methods of this class calls the same methods of the bean instance. It's convenient to be able to just write one line to call a method on a bean instead of writing a lookup call and a method call.",
			"<code>" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory</code> is responsible for the lookup of the bean."
		};

		String[] see = {
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service",
			_packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory"
		};

		jalopySettings.put("classComments", classComments);
		jalopySettings.put("see", see);

		writeFile(ejbFile, sm.toString(), jalopySettings);

		ejbFile = new File(_outputPath + "/service/spring/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil.java");

		if (ejbFile.exists()) {
			System.out.println("Relocating " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createSpringXML(boolean enterprise) throws IOException {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			if (entity.hasLocalService()) {
				_createSpringXMLSession(entity, sm, _LOCAL, enterprise);
			}

			if (entity.hasRemoteService()) {
				_createSpringXMLSession(entity, sm, _REMOTE, enterprise);
			}

			_createSpringXMLSession(entity, sm);
		}

		File xmlFile = null;

		if (enterprise) {
			xmlFile = new File(_springEntFileName);
		}
		else {
			xmlFile = new File(_springProFileName);
		}

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n" +
				"\n" +
				"<beans>\n" +
				"</beans>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = _fixSpringXML(oldContent);

		int x = oldContent.indexOf("<beans>");
		int y = oldContent.lastIndexOf("</beans>");

		int firstSession = newContent.indexOf(
			"<bean id=\"" +  _packagePath + ".service.", x);

		int lastSession = newContent.lastIndexOf(
			"<bean id=\"" +  _packagePath + ".service.", y);

		if (firstSession == -1 || firstSession > y) {
			x = newContent.indexOf("</beans>");
			newContent =
				newContent.substring(0, x) + sm.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			firstSession = newContent.lastIndexOf("<bean", firstSession) - 1;
			lastSession = newContent.indexOf("</bean>", lastSession) + 8;

			newContent =
				newContent.substring(0, firstSession) + sm.toString() +
				newContent.substring(lastSession, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createSpringXMLSession(Entity entity, StringMaker sm, int sessionType, boolean enterprise) {
		if (enterprise) {
			sm.append("\t<bean id=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service.enterprise\" class=\"" + (sessionType == _LOCAL ? "com.liferay.portal.spring.ejb.LocalSessionFactoryBean" : "com.liferay.portal.spring.ejb.RemoteSessionFactoryBean") + "\" lazy-init=\"true\">\n");

			sm.append("\t\t<property name=\"businessInterface\">\n");
			sm.append("\t\t\t<value>" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service</value>\n");
			sm.append("\t\t</property>\n");

			sm.append("\t\t<property name=\"jndiName\">\n");

			if (sessionType == _LOCAL) {
				sm.append("\t\t\t<value>ejb/liferay/" + entity.getName() + "LocalServiceHome</value>\n");
			}
			else {
				sm.append("\t\t\t<value>" + StringUtil.replace(_packagePath + ".service.ejb.", ".", "_") + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</value>\n");
			}

			sm.append("\t\t</property>\n");

			sm.append("\t</bean>\n");
		}

		sm.append("\t<bean id=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service.professional\" class=\"" + _packagePath + ".service.impl." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl\" lazy-init=\"true\" />\n");

		sm.append("\t<bean id=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service.transaction\" class=\"org.springframework.transaction.interceptor.TransactionProxyFactoryBean\" lazy-init=\"true\">\n");
		sm.append("\t\t<property name=\"transactionManager\">\n");
		sm.append("\t\t\t<ref bean=\"" + entity.getTXManager() + "\" />\n");
		sm.append("\t\t</property>\n");
		sm.append("\t\t<property name=\"target\">\n");
		sm.append("\t\t\t<ref bean=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service.professional\" />\n");
		sm.append("\t\t</property>\n");
		sm.append("\t\t<property name=\"transactionAttributes\">\n");
		sm.append("\t\t\t<props>\n");
		//sm.append("\t\t\t\t<prop key=\"*\">PROPAGATION_REQUIRED,-PortalException,-SystemException</prop>\n");
		sm.append("\t\t\t\t<prop key=\"*\">PROPAGATION_REQUIRED</prop>\n");
		sm.append("\t\t\t</props>\n");
		sm.append("\t\t</property>\n");
		sm.append("\t</bean>\n");

		sm.append("\t<bean id=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory\" class=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory\" lazy-init=\"true\">\n");
		sm.append("\t\t<property name=\"service\">\n");
		sm.append("\t\t\t<ref bean=\"" + _packagePath + ".service." + entity.getName() + _getSessionTypeName(sessionType) + "Service." + (enterprise ? "enterprise" : "transaction") + "\" />\n");
		sm.append("\t\t</property>\n");
		sm.append("\t</bean>\n");
	}

	private void _createSpringXMLSession(Entity entity, StringMaker sm) {
		if (entity.hasColumns()) {
			sm.append("\t<bean id=\"" + _packagePath + ".service.persistence." + entity.getName() + "Persistence\" class=\"" + entity.getPersistenceClass() + "\" lazy-init=\"true\">\n");
			sm.append("\t\t<property name=\"dataSource\">\n");
			sm.append("\t\t\t<ref bean=\"" + entity.getDataSource() + "\" />\n");
			sm.append("\t\t</property>\n");
			sm.append("\t\t<property name=\"sessionFactory\">\n");
			sm.append("\t\t\t<ref bean=\"" + entity.getSessionFactory() + "\" />\n");
			sm.append("\t\t</property>\n");
			sm.append("\t</bean>\n");

			sm.append("\t<bean id=\"" + _packagePath + ".service.persistence." + entity.getName() + "Util\" class=\"" + _packagePath + ".service.persistence." + entity.getName() + "Util\" lazy-init=\"true\">\n");
			sm.append("\t\t<property name=\"persistence\">\n");
			sm.append("\t\t\t<ref bean=\"" + _packagePath + ".service.persistence." + entity.getName() + "Persistence\" />\n");
			sm.append("\t\t</property>\n");
			sm.append("\t</bean>\n");
		}
	}

	private void _createSQLIndexes() throws IOException {
		String sqlPath = _portalRoot + "/sql";

		// indexes.sql

		File sqlFile = new File(sqlPath + "/indexes.sql");

		Map indexSQLs = new TreeMap();

		BufferedReader br = new BufferedReader(new FileReader(sqlFile));

		while (true) {
			String indexSQL = br.readLine();

			if (indexSQL == null) {
				break;
			}

			if (Validator.isNotNull(indexSQL.trim())) {
				int pos = indexSQL.indexOf(" on ");

				String indexSpec = indexSQL.substring(pos + 4);

				indexSQLs.put(indexSpec, indexSQL);
			}
		}

		br.close();

		// indexes.properties

		File propsFile = new File(sqlPath + "/indexes.properties");

		Map indexProps = new TreeMap();

		br = new BufferedReader(new FileReader(propsFile));

		while (true) {
			String indexMapping = br.readLine();

			if (indexMapping == null) {
				break;
			}

			if (Validator.isNotNull(indexMapping.trim())) {
				String[] splitIndexMapping = indexMapping.split("\\=");

				indexProps.put(splitIndexMapping[1], splitIndexMapping[0]);
			}
		}

		br.close();

		// indexes.sql

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List finderList = entity.getFinderList();

			for (int j = 0; j < finderList.size(); j++) {
				EntityFinder finder = (EntityFinder)finderList.get(j);

				if (finder.isDBIndex()) {
					StringMaker sm = new StringMaker();

					sm.append(entity.getTable() + " (");

					List finderColsList = finder.getColumns();

					for (int k = 0; k < finderColsList.size(); k++) {
						EntityColumn col = (EntityColumn)finderColsList.get(k);

						sm.append(col.getDBName());

						if ((k + 1) != finderColsList.size()) {
							sm.append(", ");
						}
					}

					sm.append(");");

					String indexSpec = sm.toString();

					String indexHash =
						Integer.toHexString(indexSpec.hashCode()).toUpperCase();

					String indexName = "IX_" + indexHash;

					sm = new StringMaker();

					sm.append("create index " + indexName + " on ");
					sm.append(indexSpec);

					indexSQLs.put(indexSpec, sm.toString());

					String finderName =
						entity.getTable() + StringPool.PERIOD +
							finder.getName();

					indexProps.put(finderName, indexName);
				}
			}
		}

		StringMaker sm = new StringMaker();

		Iterator itr = indexSQLs.values().iterator();

		String prevEntityName = null;

		while (itr.hasNext()) {
			String indexSQL = (String)itr.next();

			String entityName = indexSQL.split(" ")[4];

			if ((prevEntityName != null) &&
				(!prevEntityName.equals(entityName))) {

				sm.append("\n");
			}

			sm.append(indexSQL);

			if (itr.hasNext()) {
				sm.append("\n");
			}

			prevEntityName = entityName;
		}

		FileUtil.write(sqlFile, sm.toString(), true);

		// indexes.properties

		sm = new StringMaker();

		itr = indexProps.keySet().iterator();

		prevEntityName = null;

		while (itr.hasNext()) {
			String finderName = (String)itr.next();

			String indexName = (String)indexProps.get(finderName);

			String entityName = finderName.split("\\.")[0];

			if ((prevEntityName != null) &&
				(!prevEntityName.equals(entityName))) {

				sm.append("\n");
			}

			sm.append(indexName + StringPool.EQUAL + finderName);

			if (itr.hasNext()) {
				sm.append("\n");
			}

			prevEntityName = entityName;
		}

		FileUtil.write(propsFile, sm.toString(), true);
	}

	private void _createSQLSequences() throws IOException {
		String sqlPath = _portalRoot + "/sql";

		File sqlFile = new File(sqlPath + "/sequences.sql");

		Set sequenceSQLs = new TreeSet();

		BufferedReader br = new BufferedReader(new FileReader(sqlFile));

		while (true) {
			String sequenceSQL = br.readLine();

			if (sequenceSQL == null) {
				break;
			}

			if (Validator.isNotNull(sequenceSQL)) {
				sequenceSQLs.add(sequenceSQL);
			}
		}

		br.close();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List columnList = entity.getColumnList();

			for (int j = 0; j < columnList.size(); j++) {
				EntityColumn column = (EntityColumn)columnList.get(j);

				if ("sequence".equals(column.getIdType())) {
					StringMaker sm = new StringMaker();

					String sequenceName = column.getIdParam();

					if (sequenceName.length() > 30) {
						sequenceName = sequenceName.substring(0, 30);
					}

					sm.append("create sequence " + sequenceName + ";");

					String sequenceSQL = sm.toString();

					if (!sequenceSQLs.contains(sequenceSQL)) {
						sequenceSQLs.add(sequenceSQL);
					}
				}
			}
		}

		StringMaker sm = new StringMaker();

		Iterator itr = sequenceSQLs.iterator();

		while (itr.hasNext()) {
			String sequenceSQL = (String)itr.next();

			sm.append(sequenceSQL);

			if (itr.hasNext()) {
				sm.append("\n");
			}
		}

		FileUtil.write(sqlFile, sm.toString(), true);
	}

	private void _createSQLTables() throws IOException {
		String sqlPath = _portalRoot + "/sql";

		File sqlFile = new File(sqlPath + "/portal-tables.sql");

		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, StringPool.BLANK);
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List pkList = entity.getPKList();
			List regularColList = entity.getRegularColList();

			if (regularColList.size() > 0) {
				StringMaker sm = new StringMaker();

				sm.append(_CREATE_TABLE + entity.getTable() + " (\n");

				for (int j = 0; j < regularColList.size(); j++) {
					EntityColumn col = (EntityColumn)regularColList.get(j);

					String colName = col.getName();
					String colType = col.getType();
					String colIdType = col.getIdType();

					sm.append("\t" + col.getDBName());
					sm.append(" ");

					if (colType.equalsIgnoreCase("boolean")) {
						sm.append("BOOLEAN");
					}
					else if (colType.equalsIgnoreCase("double") ||
							 colType.equalsIgnoreCase("float")) {

						sm.append("DOUBLE");
					}
					else if (colType.equals("int") ||
							 colType.equals("Integer") ||
							 colType.equalsIgnoreCase("short")) {

						sm.append("INTEGER");
					}
					else if (colType.equalsIgnoreCase("long")) {
						sm.append("LONG");
					}
					else if (colType.equals("String")) {
						Map hints = ModelHintsUtil.getHints(_packagePath + ".model." + entity.getName(), colName);

						int maxLength = 75;

						if (hints != null) {
							maxLength = GetterUtil.getInteger(
								(String)hints.get("max-length"), maxLength);
						}

						if (maxLength < 4000) {
							sm.append("VARCHAR(" + maxLength + ")");
						}
						else if (maxLength == 4000) {
							sm.append("STRING");
						}
						else if (maxLength > 4000) {
							sm.append("TEXT");
						}
					}
					else if (colType.equals("Date")) {
						sm.append("DATE null");
					}
					else {
						sm.append("invalid");
					}

					if (col.isPrimary() || colName.equals("groupId") ||
						colName.equals("companyId") ||
						colName.equals("userId")) {

						sm.append(" not null");

						if (col.isPrimary() && !entity.hasCompoundPK()) {
							sm.append(" primary key");
						}
					}
					else if (colType.equals("String")) {
						sm.append(" null");
					}

					if (Validator.isNotNull(colIdType) &&
						colIdType.equals("identity")) {

						sm.append(" IDENTITY");
					}

					if (((j + 1) != regularColList.size()) ||
						(entity.hasCompoundPK())) {

						sm.append(",");
					}

					sm.append("\n");
				}

				if (entity.hasCompoundPK()) {
					sm.append("\tprimary key (");

					for (int k = 0; k < pkList.size(); k++) {
						EntityColumn pk = (EntityColumn)pkList.get(k);

						sm.append(pk.getDBName());

						if ((k + 1) != pkList.size()) {
							sm.append(", ");
						}
					}

					sm.append(")\n");
				}

				sm.append(");");

				String newCreateTableString = sm.toString();

				_createSQLTables(sqlFile, newCreateTableString, entity, true);
				_createSQLTables(new File(sqlPath + "/update-4.2.0-4.3.0.sql"), newCreateTableString, entity, false);
			}
		}
	}

	private void _createSQLTables(File sqlFile, String newCreateTableString, Entity entity, boolean addMissingTables) throws IOException {
		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, StringPool.BLANK);
		}

		String content = FileUtil.read(sqlFile);

		int x = content.indexOf(_CREATE_TABLE + entity.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content =
					content.substring(0, x) + newCreateTableString +
						content.substring(y + 2, content.length());

				FileUtil.write(sqlFile, content);
			}
		}
		else if (addMissingTables) {
			StringMaker sm = new StringMaker();

			BufferedReader br = new BufferedReader(new StringReader(content));

			String line = null;
			boolean appendNewTable = true;

			while ((line = br.readLine()) != null) {
				if (appendNewTable && line.startsWith(_CREATE_TABLE)) {
					x = _CREATE_TABLE.length();
					y = line.indexOf(" ", x);

					String tableName = line.substring(x, y);

					if (tableName.compareTo(entity.getTable()) > 0) {
						sm.append(newCreateTableString + "\n\n");

						appendNewTable = false;
					}
				}

				sm.append(line).append('\n');
			}

			if (appendNewTable) {
				sm.append("\n" + newCreateTableString);
			}

			br.close();

			FileUtil.write(sqlFile, sm.toString(), true);
		}
	}

	private String _fixHBMXML(String content) throws IOException {
		StringMaker sm = new StringMaker();

		BufferedReader br = new BufferedReader(new StringReader(content));

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith("\t<class name=\"")) {
				line = StringUtil.replace(
					line,
					new String[] {
						".service.persistence.", "HBM\" table=\""
					},
					new String[] {
						".model.", "\" table=\""
					});

				if (line.indexOf(".model.impl.") == -1) {
					line = StringUtil.replace(
						line,
						new String[] {
							".model.", "\" table=\""
						},
						new String[] {
							".model.impl.", "Impl\" table=\""
						});
				}
			}

			sm.append(line);
			sm.append('\n');
		}

		br.close();

		return sm.toString().trim();
	}

	private String _fixSpringXML(String content) throws IOException {
		return StringUtil.replace(content, ".service.spring.", ".service.");
	}

	private String _getClassName(Type type) {
		int dimensions = type.getDimensions();
		String name = type.getValue();

		if (dimensions > 0) {
			StringMaker sm = new StringMaker();

			for (int i = 0; i < dimensions; i++) {
				sm.append("[");
			}

			if (name.equals("boolean")) {
				return sm.toString() + "Z";
			}
			else if (name.equals("byte")) {
				return sm.toString() + "B";
			}
			else if (name.equals("char")) {
				return sm.toString() + "C";
			}
			else if (name.equals("double")) {
				return sm.toString() + "D";
			}
			else if (name.equals("float")) {
				return sm.toString() + "F";
			}
			else if (name.equals("int")) {
				return sm.toString() + "I";
			}
			else if (name.equals("long")) {
				return sm.toString() + "J";
			}
			else if (name.equals("short")) {
				return sm.toString() + "S";
			}
			else {
				return sm.toString() + "L" + name + ";";
			}
		}

		return name;
	}

	private String _getDimensions(Type type) {
		String dimensions = "";

		for (int i = 0; i < type.getDimensions(); i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	private JavaClass _getJavaClass(String fileName) throws IOException {
		int pos = fileName.indexOf("src/") + 3;

		String srcFile = fileName.substring(pos + 1, fileName.length());
		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSource(new File(fileName));

		return builder.getClassByName(className);
	}

	private String _getNoSuchEntityException(Entity entity) {
		String noSuchEntityException = entity.getName();

		if (Validator.isNull(entity.getPortletShortName()) || noSuchEntityException.startsWith(entity.getPortletShortName())) {
			noSuchEntityException = noSuchEntityException.substring(entity.getPortletShortName().length(), noSuchEntityException.length());
		}

		noSuchEntityException = "NoSuch" + noSuchEntityException;

		return noSuchEntityException;
	}

	private String _getPrimitiveObj(String type) {
		if (type.equals("boolean")) {
			return "Boolean";
		}
		else if (type.equals("double")) {
			return "Double";
		}
		else if (type.equals("float")) {
			return "Float";
		}
		else if (type.equals("int")) {
			return "Integer";
		}
		else if (type.equals("long")) {
			return "Long";
		}
		else if (type.equals("short")) {
			return "Short";
		}
		else {
			return type;
		}
	}

	private String _getSessionTypeName(int sessionType) {
		if (sessionType == _LOCAL) {
			return "Local";
		}
		else {
			return "";
		}
	}

	private String _getSqlType(String model, String field, String type) {
		if (type.equals("boolean")) {
			return "BOOLEAN";
		}
		else if (type.equals("double")) {
			return "DOUBLE";
		}
		else if (type.equals("float")) {
			return "FLOAT";
		}
		else if (type.equals("int")) {
			return "INTEGER";
		}
		else if (type.equals("long")) {
			return "BIGINT";
		}
		else if (type.equals("short")) {
			return "INTEGER";
		}
		else if (type.equals("Date")) {
			return "TIMESTAMP";
		}
		else if (type.equals("String")) {
			Map hints = ModelHintsUtil.getHints(model, field);

			if (hints != null) {
				int maxLength = GetterUtil.getInteger(
					(String)hints.get("max-length"));

				if (maxLength == 2000000) {
					return "CLOB";
				}
			}

			return "VARCHAR";
		}
		else {
			return null;
		}
	}

	private boolean _hasHttpMethods(JavaClass javaClass) {
		JavaMethod[] methods = javaClass.getMethods();

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			if (!javaMethod.isConstructor() && javaMethod.isPublic() &&
				_isCustomMethod(javaMethod)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasSoapMethods(JavaClass javaClass) {
		JavaMethod[] methods = javaClass.getMethods();

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			if (!javaMethod.isConstructor() && javaMethod.isPublic() &&
				_isCustomMethod(javaMethod) && _isSoapMethod(javaMethod)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isCustomMethod(JavaMethod method) {
		String methodName = method.getName();

		if (methodName.equals("hasAdministrator") ||
			methodName.equals("ejbCreate") ||
			methodName.equals("ejbRemove") ||
			methodName.equals("ejbActivate") ||
			methodName.equals("ejbPassivate") ||
			methodName.equals("getSessionContext") ||
			methodName.equals("setSessionContext") ||
			methodName.equals("hashCode") ||
			methodName.equals("getClass") ||
			methodName.equals("wait") ||
			methodName.equals("equals") ||
			methodName.equals("toString") ||
			methodName.equals("notify") ||
			methodName.equals("notifyAll")) {

			return false;
		}
		else if (methodName.equals("getPermissionChecker")) {
			return false;
		}
		else if (methodName.equals("getUser") &&
				 method.getParameters().length == 0) {

			return false;
		}
		else if (methodName.equals("getUserId") &&
				 method.getParameters().length == 0) {

			return false;
		}
		else {
			return true;
		}
	}

	private boolean _isSoapMethod(JavaMethod method) {
		String returnValueName = method.getReturns().getValue();

		if (returnValueName.startsWith("java.io") ||
			returnValueName.equals("java.util.Map") ||
			returnValueName.equals("java.util.Properties") ||
			returnValueName.startsWith("javax")) {

			return false;
		}

		JavaParameter[] parameters = method.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			JavaParameter javaParameter = parameters[i];

			String parameterTypeName =
				javaParameter.getType().getValue() +
					_getDimensions(javaParameter.getType());

			if ((parameterTypeName.indexOf(
					"com.liferay.portal.model.") != -1) ||
				(parameterTypeName.equals(
					"com.liferay.portal.theme.ThemeDisplay")) ||
				(parameterTypeName.equals(
					"com.liferay.portlet.PortletPreferencesImpl")) ||
				 parameterTypeName.startsWith("java.io") ||
				 //parameterTypeName.startsWith("java.util.List") ||
				 //parameterTypeName.startsWith("java.util.Locale") ||
				 parameterTypeName.startsWith("java.util.Map") ||
				 parameterTypeName.startsWith("java.util.Properties") ||
				 parameterTypeName.startsWith("javax")) {

				return false;
			}
		}

		return true;
	}

	private boolean _requiresNullCheck(EntityColumn col) {
		return !col.isPrimitiveType();
	}

	private static final int _REMOTE = 0;

	private static final int _LOCAL = 1;

	private static final String _CREATE_TABLE = "create table ";

	private static final String _DEFAULT_CLASS_COMMENTS = "ServiceBuilder generated this class. Modifications in this class will be overwritten the next time is generated.";

	private Set _badTableNames;
	private Set _badCmpFields;
	private String _hbmFileName;
	private String _modelHintsFileName;
	private String _springEntFileName;
	private String _springProFileName;
	private String _beanLocatorUtilClassName;
	private String _serviceDir;
	private String _jsonFileName;
	private String _portalRoot;
	private String _portletName;
	private String _portletShortName;
	private String _portletPackageName;
	private String _outputPath;
	private String _serviceOutputPath;
	private String _packagePath;
	private List _ejbList;

}