<%
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
%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.cal.DayAndPosition" %>
<%@ page import="com.liferay.portlet.calendar.model.CalEvent" %>

<%
CalEvent event = (CalEvent)request.getAttribute("liferay-ui:input-repeat:event");

Recurrence recurrence = null;

int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
String recurrenceTypeParam = ParamUtil.getString(request, "recurrenceType");
if (Validator.isNull(recurrenceTypeParam) && (event != null)) {
	if (event.getRepeating()) {
		recurrence = event.getRecurrenceObj();
		recurrenceType = recurrence.getFrequency();
	}
}

int dailyType = ParamUtil.getInteger(request, "dailyType");
String dailyTypeParam = ParamUtil.getString(request, "dailyType");
if (Validator.isNull(dailyTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByDay() != null) {
			dailyType = 1;
		}
	}
}

int dailyInterval = ParamUtil.getInteger(request, "dailyInterval", 1);
String dailyIntervalParam = ParamUtil.getString(request, "dailyInterval");
if (Validator.isNull(dailyIntervalParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		dailyInterval = recurrence.getInterval();
	}
}

int weeklyInterval = ParamUtil.getInteger(request, "weeklyInterval", 1);
String weeklyIntervalParam = ParamUtil.getString(request, "weeklyInterval");
if (Validator.isNull(weeklyIntervalParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		weeklyInterval = recurrence.getInterval();
	}
}

int monthlyType = ParamUtil.getInteger(request, "monthlyType");
String monthlyTypeParam = ParamUtil.getString(request, "monthlyType");
if (Validator.isNull(monthlyTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonthDay() == null) {
			monthlyType = 1;
		}
	}
}

int monthlyDay0 = ParamUtil.getInteger(request, "monthlyDay0", 15);
String monthlyDay0Param = ParamUtil.getString(request, "monthlyDay0");
if (Validator.isNull(monthlyDay0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonthDay() != null) {
			monthlyDay0 = recurrence.getByMonthDay()[0];
		}
	}
}

int monthlyInterval0 = ParamUtil.getInteger(request, "monthlyInterval0", 1);
String monthlyInterval0Param = ParamUtil.getString(request, "monthlyInterval0");
if (Validator.isNull(monthlyInterval0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		monthlyInterval0 = recurrence.getInterval();
	}
}

int monthlyPos = ParamUtil.getInteger(request, "monthlyPos", 1);
String monthlyPosParam = ParamUtil.getString(request, "monthlyPos");
if (Validator.isNull(monthlyPosParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			monthlyPos = recurrence.getByMonth()[0];
		}
		else if (recurrence.getByDay() != null) {
			monthlyPos = recurrence.getByDay()[0].getDayPosition();
		}
	}
}

int monthlyDay1 = ParamUtil.getInteger(request, "monthlyDay1", Calendar.SUNDAY);
String monthlyDay1Param = ParamUtil.getString(request, "monthlyDay1");
if (Validator.isNull(monthlyDay1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			monthlyDay1 = -1;
		}
		else if (recurrence.getByDay() != null) {
			monthlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
		}
	}
}

int monthlyInterval1 = ParamUtil.getInteger(request, "monthlyInterval1", 1);
String monthlyInterval1Param = ParamUtil.getString(request, "monthlyInterval1");
if (Validator.isNull(monthlyInterval1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		monthlyInterval1 = recurrence.getInterval();
	}
}

int yearlyType = ParamUtil.getInteger(request, "yearlyType");
String yearlyTypeParam = ParamUtil.getString(request, "yearlyType");
if (Validator.isNull(yearlyTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonthDay() == null) {
			yearlyType = 1;
		}
	}
}

int yearlyMonth0 = ParamUtil.getInteger(request, "yearlyMonth0", Calendar.JANUARY);
String yearlyMonth0Param = ParamUtil.getString(request, "yearlyMonth0");
if (Validator.isNull(yearlyMonth0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() == null) {
			yearlyMonth0 = recurrence.getDtStart().get(Calendar.MONTH);
		}
	}
}

int yearlyDay0 = ParamUtil.getInteger(request, "yearlyDay0", 15);
String yearlyDay0Param = ParamUtil.getString(request, "yearlyDay0");
if (Validator.isNull(yearlyDay0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() == null) {
			yearlyDay0 = recurrence.getDtStart().get(Calendar.DATE);
		}
	}
}

int yearlyInterval0 = ParamUtil.getInteger(request, "yearlyInterval0", 1);
String yearlyInterval0Param = ParamUtil.getString(request, "yearlyInterval0");
if (Validator.isNull(yearlyInterval0Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		yearlyInterval0 = recurrence.getInterval();
	}
}

int yearlyPos = ParamUtil.getInteger(request, "yearlyPos", 1);
String yearlyPosParam = ParamUtil.getString(request, "yearlyPos");
if (Validator.isNull(yearlyPosParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByDay() != null) {
			yearlyPos = recurrence.getByDay()[0].getDayPosition();
		}
	}
}

int yearlyDay1 = ParamUtil.getInteger(request, "yearlyDay1", Calendar.SUNDAY);
String yearlyDay1Param = ParamUtil.getString(request, "yearlyDay1");
if (Validator.isNull(yearlyDay1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByDay() != null) {
			yearlyDay1 = recurrence.getByDay()[0].getDayOfWeek();
		}
	}
}

int yearlyMonth1 = ParamUtil.getInteger(request, "yearlyMonth1", Calendar.JANUARY);
String yearlyMonth1Param = ParamUtil.getString(request, "yearlyMonth1");
if (Validator.isNull(yearlyMonth1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getByMonth() != null) {
			yearlyMonth1 = recurrence.getByMonth()[0];
		}
	}
}

int yearlyInterval1 = ParamUtil.getInteger(request, "yearlyInterval1", 1);
String yearlyInterval1Param = ParamUtil.getString(request, "yearlyInterval1");
if (Validator.isNull(yearlyInterval1Param) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		yearlyInterval1 = recurrence.getInterval();
	}
}

boolean weeklyPosSu = _getWeeklyDayPos(request, Calendar.SUNDAY, event, recurrence);
boolean weeklyPosMo = _getWeeklyDayPos(request, Calendar.MONDAY, event, recurrence);
boolean weeklyPosTu = _getWeeklyDayPos(request, Calendar.TUESDAY, event, recurrence);
boolean weeklyPosWe = _getWeeklyDayPos(request, Calendar.WEDNESDAY, event, recurrence);
boolean weeklyPosTh = _getWeeklyDayPos(request, Calendar.THURSDAY, event, recurrence);
boolean weeklyPosFr = _getWeeklyDayPos(request, Calendar.FRIDAY, event, recurrence);
boolean weeklyPosSa = _getWeeklyDayPos(request, Calendar.SATURDAY, event, recurrence);
%>

<script type="text/javascript">
	function <portlet:namespace />showTable(id) {
		document.getElementById("<portlet:namespace />neverTable").style.display = "none";
		document.getElementById("<portlet:namespace />dailyTable").style.display = "none";
		document.getElementById("<portlet:namespace />weeklyTable").style.display = "none";
		document.getElementById("<portlet:namespace />monthlyTable").style.display = "none";
		document.getElementById("<portlet:namespace />yearlyTable").style.display = "none";

		document.getElementById(id).style.display = "block";
	}
</script>

<aui:fieldset cssClass="taglib-input-repeat">
	<aui:column columnWidth="25">
		<aui:field-wrapper label="repeat" name="recurrenceType">

			<%
			String taglibOnClick = namespace + "showTable('" + namespace + "neverTable');";
			%>

			<aui:input checked="<%= recurrenceType == Recurrence.NO_RECURRENCE %>" label="never" name="recurrenceType" type="radio" value="<%= Recurrence.NO_RECURRENCE %>" onClick="<%= taglibOnClick %>" />

			<%
			taglibOnClick = namespace + "showTable('" + namespace + "dailyTable');";
			%>

			<aui:input checked="<%= recurrenceType == Recurrence.DAILY %>" label="daily" name="recurrenceType" type="radio" value="<%= Recurrence.DAILY %>" onClick="<%= taglibOnClick %>" />

			<%
			taglibOnClick = namespace + "showTable('" + namespace + "weeklyTable');";
			%>

			<aui:input checked="<%= recurrenceType == Recurrence.WEEKLY %>" label="weekly" name="recurrenceType" type="radio" value="<%= Recurrence.WEEKLY %>" onClick="<%= taglibOnClick %>" />

			<%
			taglibOnClick = namespace + "showTable('" + namespace + "monthlyTable');";
			%>

			<aui:input checked="<%= recurrenceType == Recurrence.MONTHLY %>" label="monthly" name="recurrenceType" type="radio" value="<%= Recurrence.MONTHLY %>" onClick="<%= taglibOnClick %>" />

			<%
			taglibOnClick = namespace + "showTable('" + namespace + "yearlyTable');";
			%>

			<aui:input checked="<%= recurrenceType == Recurrence.YEARLY %>" label="yearly" name="recurrenceType" type="radio" value="<%= Recurrence.YEARLY %>" onClick="<%= taglibOnClick %>" />
		</aui:field-wrapper>
	</aui:column>

	<aui:column columnWidth="75">
		<div id="<portlet:namespace />neverTable" style="display: none;">
			<liferay-ui:message key="do-not-repeat-this-event" />
		</div>

		<div id="<portlet:namespace />dailyTable" style="display: none;">
			<aui:input checked="<%= dailyType == 0 %>" cssClass="input-container" inlineField="<%= true %>" label="recur-every" name="dailyType" type="radio" value="0" />

			<aui:input inlineField="<%= true %>" inlineLabel="right" label="day-s" maxlength="3" name="dailyInterval" size="3" type="text" value="<%= dailyInterval %>" />

			<aui:input checked="<%= (dailyType == 1) %>" label="every-weekday" name="dailyType" type="radio" value="1" />
		</div>

		<div id="<portlet:namespace />weeklyTable" style="display: none;">
			<aui:input inlineField="<%= true %>" inlineLabel="left" label="recur-every" maxlength="2" name="weeklyInterval" size="2" suffix="weeks-on" type="text" value="<%= weeklyInterval %>" />

			<%
			String[] days = CalendarUtil.getDays(locale);
			%>

			<aui:layout cssClass="weekdays">
				<aui:column>
					<aui:input inlineLabel="right" label="<%= days[0] %>" name='<%= "weeklyDayPos" + Calendar.SUNDAY %>' type="checkbox" value="<%= weeklyPosSu %>" />

					<aui:input inlineLabel="right" label="<%= days[4] %>" name='<%= "weeklyDayPos" + Calendar.THURSDAY %>' type="checkbox" value="<%= weeklyPosTh %>" />
				</aui:column>

				<aui:column>
					<aui:input inlineLabel="right" label="<%= days[1] %>" name='<%= "weeklyDayPos" + Calendar.MONDAY %>' type="checkbox" value="<%= weeklyPosMo %>" />

					<aui:input inlineLabel="right" label="<%= days[5] %>" name='<%= "weeklyDayPos" + Calendar.FRIDAY %>' type="checkbox" value="<%= weeklyPosFr %>" />
				</aui:column>

				<aui:column>
					<aui:input inlineLabel="right" label="<%= days[2] %>" name='<%= "weeklyDayPos" + Calendar.TUESDAY %>' type="checkbox" value="<%= weeklyPosTu %>" />

					<aui:input inlineLabel="right" label="<%= days[6] %>" name='<%= "weeklyDayPos" + Calendar.SATURDAY %>' type="checkbox" value="<%= weeklyPosSa %>" />
				</aui:column>

				<aui:column>
					<aui:input inlineLabel="right" label="<%= days[3] %>" name='<%= "weeklyDayPos" + Calendar.WEDNESDAY %>' type="checkbox" value="<%= weeklyPosWe %>" />
				</aui:column>
			</aui:layout>
		</div>

		<div id="<portlet:namespace />monthlyTable" style="display: none;">
			<aui:input checked="<%= monthlyType == 0 %>" cssClass="input-container" inlineField="<%= true %>" label="day" name="monthlyType" type="radio" value="0" />

			<aui:input inlineField="<%= true %>" inlineLabel="right" label="of-every" maxlength="2" name="monthlyDay0" size="2" type="text" value="<%= monthlyDay0 %>" />

			<aui:input inlineField="<%= true %>" inlineLabel="right" label="month-s" maxlength="2" name="monthlyInterval0" size="2" type="text" value="<%= monthlyInterval0 %>" />

			<aui:input checked="<%= (monthlyType == 1) %>" cssClass="input-container" inlineField="<%= true %>" label="the" name="monthlyType" type="radio" value="1" />

			<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="left" label="" name="monthlyPos">
				<aui:option label="first" selected="<%= monthlyPos == 1 %>" value="1" />
				<aui:option label="second" selected="<%= monthlyPos == 2 %>" value="2" />
				<aui:option label="third" selected="<%= monthlyPos == 3 %>" value="3" />
				<aui:option label="fourth" selected="<%= monthlyPos == 4 %>" value="4" />
				<aui:option label="last" selected="<%= monthlyPos == -1 %>" value="-1" />
			</aui:select>

			<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="monthlyDay1">
				<aui:option label="<%= days[0] %>" selected="<%= monthlyDay1 == Calendar.SUNDAY %>" value="<%= Calendar.SUNDAY %>" />
				<aui:option label="<%= days[1] %>" selected="<%= monthlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
				<aui:option label="<%= days[2] %>" selected="<%= monthlyDay1 == Calendar.TUESDAY %>" value="<%= Calendar.TUESDAY %>" />
				<aui:option label="<%= days[3] %>" selected="<%= monthlyDay1 == Calendar.WEDNESDAY %>" value="<%= Calendar.WEDNESDAY %>" />
				<aui:option label="<%= days[4] %>" selected="<%= monthlyDay1 == Calendar.THURSDAY %>" value="<%= Calendar.THURSDAY %>" />
				<aui:option label="<%= days[5] %>" selected="<%= monthlyDay1 == Calendar.FRIDAY %>" value="<%= Calendar.FRIDAY %>" />
				<aui:option label="<%= days[6] %>" selected="<%= monthlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
			</aui:select>

			<aui:input inlineField="<%= true %>" inlineLabel="left" label="of-every" maxlength="2" name="monthlyInterval1" size="2" suffix="month-s" type="text" value="<%= monthlyInterval1 %>" />
		</div>

		<%
		int[] monthIds = CalendarUtil.getMonthIds();
		String[] months = CalendarUtil.getMonths(locale);
		%>

		<div id="<portlet:namespace />yearlyTable" style="display: none;">
			<aui:input checked="<%= yearlyType == 0 %>" cssClass="input-container" inlineField="<%= true %>" label="every" name="yearlyType" type="radio" value="0" />

			<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="left" label="" name="yearlyMonth0">

			<%
			for (int i = 0; i < 12; i++) {
			%>

				<aui:option label="<%= months[i] %>" selected="<%= monthIds[i] == yearlyMonth0 %>" value="<%= monthIds[i] %>" />

			<%
			}
			%>

			</aui:select>

			<aui:input inlineField="<%= true %>" inlineLabel="right" label="of-every" maxlength="2" name="yearlyDay0" size="2" type="text" value="<%= yearlyDay0 %>" />

			<aui:input inlineField="<%= true %>" inlineLabel="right" label="year-s" maxlength="2" name="yearlyInterval0" size="2" type="text" value="<%= yearlyInterval0 %>" />

			<aui:input checked="<%= yearlyType == 1 %>" cssClass="input-container" inlineField="<%= true %>" label="the" name="yearlyType" type="radio" value="1" />

			<aui:select cssClass="input-container" inlineField="<%= true %>" label="" name="yearlyPos">
				<aui:option label="first" selected="<%= yearlyPos == 1 %>" value="1" />
				<aui:option label="second" selected="<%= yearlyPos == 2 %>" value="2" />
				<aui:option label="third" selected="<%= yearlyPos == 3 %>" value="3" />
				<aui:option label="fourth" selected="<%= yearlyPos == 4 %>" value="4" />
				<aui:option label="last" selected="<%= yearlyPos == -1 %>" value="-1" />
			</aui:select>

			<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="right" label="of" name="yearlyDay1">
				<aui:option label="weekday" selected="<%= yearlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
				<aui:option label="weekend-day" selected="<%= yearlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
				<aui:option label="<%= days[0] %>" selected="<%= yearlyDay1 == Calendar.SUNDAY %>" value="<%= Calendar.SUNDAY %>" />
				<aui:option label="<%= days[1] %>" selected="<%= yearlyDay1 == Calendar.MONDAY %>" value="<%= Calendar.MONDAY %>" />
				<aui:option label="<%= days[2] %>" selected="<%= yearlyDay1 == Calendar.TUESDAY %>" value="<%= Calendar.TUESDAY %>" />
				<aui:option label="<%= days[3] %>" selected="<%= yearlyDay1 == Calendar.WEDNESDAY %>" value="<%= Calendar.WEDNESDAY %>" />
				<aui:option label="<%= days[4] %>" selected="<%= yearlyDay1 == Calendar.THURSDAY %>" value="<%= Calendar.THURSDAY %>" />
				<aui:option label="<%= days[5] %>" selected="<%= yearlyDay1 == Calendar.FRIDAY %>" value="<%= Calendar.FRIDAY %>" />
				<aui:option label="<%= days[6] %>" selected="<%= yearlyDay1 == Calendar.SATURDAY %>" value="<%= Calendar.SATURDAY %>" />
			</aui:select>

			<aui:select cssClass="input-container" inlineField="<%= true %>" inlineLabel="right" label="of-every" name="yearlyMonth1">

				<%
				for (int i = 0; i < 12; i++) {
				%>

					<aui:option label="<%= months[i] %>" selected="<%= (monthIds[i] == yearlyMonth1) %>" value="<%= monthIds[i] %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input inlineField="<%= true %>" inlineLabel="right" label="year-s" maxlength="2" name="yearlyInterval1" size="2" type="text" value="<%= yearlyInterval1 %>" />
		</div>
	</aui:column>
</aui:fieldset>


<%!
private boolean _getWeeklyDayPos(HttpServletRequest req, int day, CalEvent event, Recurrence recurrence) {
	boolean weeklyPos = ParamUtil.getBoolean(req, "weeklyDayPos" + day);

	String weeklyPosParam = ParamUtil.getString(req, "weeklyDayPos" + day);

	if (Validator.isNull(weeklyPosParam) && (event != null)) {
		if ((event.getRepeating()) && (recurrence != null)) {
			DayAndPosition[] dayPositions = recurrence.getByDay();

			if (dayPositions != null) {
				for (int i = 0; i < dayPositions.length; i++) {
					if (dayPositions[i].getDayOfWeek() == day) {
						return true;
					}
				}
			}
		}
	}

	return weeklyPos;
}
%>