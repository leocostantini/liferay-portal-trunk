<?xml version="1.0" encoding="UTF-8"?>

<beans
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd"
>
	<bean id="liferayHibernateSessionFactory" class="com.liferay.portal.spring.hibernate.PortletHibernateConfiguration">
		<property name="dataSource">
			<ref bean="liferayDataSource" />
		</property>
	</bean>
	<bean id="liferaySessionFactory" class="com.liferay.portal.dao.orm.hibernate.SessionFactoryImpl">
		<property name="sessionFactoryImplementor">
			<ref bean="liferayHibernateSessionFactory" />
		</property>
	</bean>
	<bean id="liferayTransactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
		<property name="dataSource">
			<ref bean="liferayDataSource" />
		</property>
		<property name="globalRollbackOnParticipationFailure" value="false" />
		<property name="sessionFactory">
			<ref bean="liferayHibernateSessionFactory" />
		</property>
	</bean>
</beans>