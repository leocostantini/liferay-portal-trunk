/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.comparator.JavaMethodComparator;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.TextFormatter;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="CopyInterfaceBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CopyInterfaceBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new CopyInterfaceBuilder(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public CopyInterfaceBuilder(String parentDir, String srcFile) {
		try {
			_copyInterface(parentDir, srcFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _copyInterface(String parentDir, String srcFile)
		throws IOException {

		JavaClass javaClass = _getJavaClass(parentDir, srcFile);

		JavaMethod[] methods = javaClass.getMethods();

		Arrays.sort(methods, new JavaMethodComparator());

		StringBuilder sb = new StringBuilder();

		// Package

		sb.append("package " + javaClass.getPackage() + ";");

		// Imports

		sb.append("[$IMPORTS$]");

		// Class declaration

		sb.append("public class Copy" + javaClass.getName() + " implements " + javaClass.getName() + " {");

		String varName = "_" + TextFormatter.format(javaClass.getName(), TextFormatter.I);

		// Methods

		Set<String> imports = new TreeSet<String>();

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (javaMethod.isPublic()) {
				String returnValueName = javaMethod.getReturns().getValue();

				imports.add(returnValueName);

				sb.append("public " + javaMethod.getReturns().getJavaClass().getName() + _getDimensions(javaMethod.getReturns()) + " " + methodName + "(");

				JavaParameter[] parameters = javaMethod.getParameters();

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sb.append(javaParameter.getType().getJavaClass().getName() + _getDimensions(javaParameter.getType()) + " " + javaParameter.getName());

					imports.add(javaParameter.getType().getValue());

					if ((j + 1) != parameters.length) {
						sb.append(", ");
					}
				}

				sb.append(")");

				Type[] thrownExceptions = javaMethod.getExceptions();

				Set<String> newExceptions = new LinkedHashSet<String>();

				for (int j = 0; j < thrownExceptions.length; j++) {
					Type thrownException = thrownExceptions[j];

					newExceptions.add(thrownException.getJavaClass().getName());

					imports.add(thrownException.getValue());
				}

				if (newExceptions.size() > 0) {
					sb.append(" throws ");

					Iterator<String> itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sb.append(itr.next());

						if (itr.hasNext()) {
							sb.append(", ");
						}
					}
				}

				sb.append("{");

				if (!returnValueName.equals("void")) {
					sb.append("return ");
				}

				sb.append(varName + "." + methodName + "(");

				for (int j = 0; j < parameters.length; j++) {
					JavaParameter javaParameter = parameters[j];

					sb.append(javaParameter.getName());

					if ((j + 1) != parameters.length) {
						sb.append(", ");
					}
				}

				sb.append(");");
				sb.append("}");
			}
		}

		// Fields

		sb.append("private " + javaClass.getName() + " " + varName + ";");

		// Class close brace

		sb.append("}");

		// Imports

		String content = sb.toString();

		sb = new StringBuilder();

		Iterator<String> itr = imports.iterator();

		while (itr.hasNext()) {
			String importClass = itr.next();

			if (!importClass.equals("boolean") && !importClass.equals("double") && !importClass.equals("int") && !importClass.equals("long") && !importClass.equals("short") && !importClass.equals("void")) {
				sb.append("import " + importClass + ";");
			}
		}

		content = StringUtil.replace(content, "[$IMPORTS$]", sb.toString());

		// Write file

		File file = new File(parentDir + "/" + StringUtil.replace(javaClass.getPackage(), ".", "/") + "/Copy" + javaClass.getName() + ".java");

		ServiceBuilder.writeFile(file, content);
	}

	private String _getDimensions(Type type) {
		String dimensions = "";

		for (int i = 0; i < type.getDimensions(); i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	private JavaClass _getJavaClass(String parentDir, String srcFile)
		throws IOException {

		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSource(new File(parentDir + "/" + srcFile));

		return builder.getClassByName(className);
	}

}