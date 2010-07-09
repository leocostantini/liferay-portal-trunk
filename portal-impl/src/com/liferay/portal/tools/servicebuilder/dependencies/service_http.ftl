package ${packagePath}.service.http;

<#if hasHttpMethods>
	import ${packagePath}.service.${entity.name}ServiceUtil;
</#if>

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.FloatWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.ShortWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link ${packagePath}.service.${entity.name}ServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}ServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       ${packagePath}.service.${entity.name}ServiceUtil
 * @generated
 */
public class ${entity.name}ServiceHttp {

	<#assign hasMethods = false>

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign hasMethods = true>

			<#assign returnTypeName = serviceBuilder.getTypeGenericsName(method.returns)>
			<#assign parameters = method.parameters>

			public static ${returnTypeName} ${method.name} (HttpPrincipal httpPrincipal

			<#list parameters as parameter>
				, ${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}
			</#list>

			)
			throws

			<#if !method.exceptions?seq_contains("com.liferay.portal.kernel.exception.SystemException")>
				com.liferay.portal.kernel.exception.SystemException

				<#if method.exceptions?size gt 0>
					,
				</#if>
			</#if>

			<#list method.exceptions as exception>
				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				try {
					<#list parameters as parameter>
						<#assign parameterTypeName = serviceBuilder.getTypeGenericsName(parameter.type)>

						Object paramObj${parameter_index} =

						<#if parameterTypeName == "boolean">
							new BooleanWrapper(${parameter.name});
						<#elseif parameterTypeName == "double">
							new DoubleWrapper(${parameter.name});
						<#elseif parameterTypeName == "float">
							new FloatWrapper(${parameter.name});
						<#elseif parameterTypeName == "int">
							new IntegerWrapper(${parameter.name});
						<#elseif parameterTypeName == "long">
							new LongWrapper(${parameter.name});
						<#elseif parameterTypeName == "short">
							new ShortWrapper(${parameter.name});
						<#else>
							${parameter.name};

							if (${parameter.name} == null) {
								paramObj${parameter_index} = new NullWrapper("${serviceBuilder.getClassName(parameter.type)}");
							}
						</#if>
					</#list>

					MethodWrapper methodWrapper = new MethodWrapper(${entity.name}ServiceUtil.class.getName(), "${method.name}",

					<#if parameters?size == 0>
						new Object[0]
					<#else>
						new Object[] {
							<#list parameters as parameter>
								paramObj${parameter_index}

								<#if parameter_has_next>
									,
								</#if>
							</#list>
						}
					</#if>

					);

					<#if returnTypeName != "void">
						Object returnObj = null;
					</#if>

					try {
						<#if returnTypeName != "void">
							returnObj =
						</#if>

						TunnelUtil.invoke(httpPrincipal, methodWrapper);
					}
					catch (Exception e) {
						<#list method.exceptions as exception>
							if (e instanceof ${exception.value}) {
								throw (${exception.value})e;
							}
						</#list>

						throw new com.liferay.portal.kernel.exception.SystemException(e);
					}

					<#if returnTypeName != "void">
						<#if returnTypeName == "boolean">
							return ((Boolean)returnObj).booleanValue();
						<#elseif returnTypeName == "double">
							return ((Double)returnObj).doubleValue();
						<#elseif returnTypeName == "float">
							return ((Float)returnObj).floatValue();
						<#elseif returnTypeName == "int">
							return ((Integer)returnObj).intValue();
						<#elseif returnTypeName == "long">
							return ((Long)returnObj).longValue();
						<#elseif returnTypeName == "short">
							return ((Short)returnObj).shortValue();
						<#elseif returnTypeName == "java.lang.Object">
							return returnObj;
						<#else>
							return (${returnTypeName})returnObj;
						</#if>
					</#if>
				}
				catch (com.liferay.portal.kernel.exception.SystemException se) {
					_log.error(se, se);

					throw se;
				}
			}
		</#if>
	</#list>

	<#if hasMethods>
		private static Log _log = LogFactoryUtil.getLog(${entity.name}ServiceHttp.class);
	</#if>

}