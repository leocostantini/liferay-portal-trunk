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

package com.liferay.portlet.calendar.service;

/**
 * <a href="CalEventLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portlet.calendar.service.impl.CalEventLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be accessed
 * from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.calendar.service.CalEventServiceFactory
 * @see com.liferay.portlet.calendar.service.CalEventServiceUtil
 *
 */
public interface CalEventLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.Recurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.Recurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.calendar.model.CalEvent addEvent(
		java.lang.String userId, java.lang.String plid, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.Recurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEventResources(long eventId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEventResources(long eventId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addEventResources(
		com.liferay.portlet.calendar.model.CalEvent event,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkEvents()
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteEvent(long eventId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteEvent(com.liferay.portlet.calendar.model.CalEvent event)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteEvents(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.io.File export(long eventId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portlet.calendar.model.CalEvent getEvent(long eventId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getEvents(long groupId, java.lang.String type,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List getEvents(long groupId, java.util.Calendar cal)
		throws com.liferay.portal.SystemException;

	public java.util.List getEvents(long groupId, java.util.Calendar cal,
		java.lang.String type) throws com.liferay.portal.SystemException;

	public int getEventsCount(long groupId, java.lang.String type)
		throws com.liferay.portal.SystemException;

	public java.util.List getRepeatingEvents(long groupId)
		throws com.liferay.portal.SystemException;

	public boolean hasEvents(long groupId, java.util.Calendar cal)
		throws com.liferay.portal.SystemException;

	public boolean hasEvents(long groupId, java.util.Calendar cal,
		java.lang.String type) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent updateEvent(
		java.lang.String userId, long eventId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.Recurrence recurrence,
		java.lang.String remindBy, int firstReminder, int secondReminder)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}