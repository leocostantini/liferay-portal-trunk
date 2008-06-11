alter table BlogsEntry add draft BOOLEAN;
alter table BlogsEntry add allowTrackbacks BOOLEAN;
alter table BlogsEntry add trackbacks TEXT null;

COMMIT_TRANSACTION;

update BlogsEntry set draft = FALSE;
update BlogsEntry set allowTrackbacks = TRUE;

alter table Contact_ add facebookSn VARCHAR(75) null;
alter table Contact_ add mySpaceSn VARCHAR(75) null;
alter table Contact_ add twitterSn VARCHAR(75) null;

drop table ExpandoRow;
create table ExpandoRow (
	rowId_ LONG not null primary key,
	tableId LONG,
	classPK LONG
);

drop table ExpandoValue;
create table ExpandoValue (
	valueId LONG not null primary key,
	tableId LONG,
	columnId LONG,
	rowId_ LONG,
	classNameId LONG,
	classPK LONG,
	data_ STRING null
);

create table quartz_job_details (
	job_name VARCHAR(80) not null,
	job_group VARCHAR(80) not null,
	description VARCHAR(120) null,
	job_class_name VARCHAR(128) not null,
	is_durable VARCHAR(1) not null,
	is_volatile VARCHAR(1) not null,
	is_stateful VARCHAR(1) not null,
	requests_recovery VARCHAR(1) not null,
	job_data BLOB null,
	primary key (job_name, job_group)
);

create table quartz_job_listeners (
	job_name  VARCHAR(80) not null,
	job_group VARCHAR(80) not null,
	job_listener VARCHAR(80) not null,
	primary key (job_name, job_group, job_listener)
);

create table quartz_triggers (
	trigger_name VARCHAR(80) not null,
	trigger_group VARCHAR(80) not null,
	job_name VARCHAR(80) not null,
	job_group VARCHAR(80) not null,
	is_volatile VARCHAR(1) not null,
	description VARCHAR(120) null,
	next_fire_time LONG null,
	prev_fire_time LONG null,
	priority INTEGER null,
	trigger_state VARCHAR(16) not null,
	trigger_type VARCHAR(8) not null,
	start_time LONG not null,
	end_time LONG null,
	calendar_name VARCHAR(80) null,
	misfire_instr INTEGER null,
	job_data BLOB null,
	primary key (trigger_name, trigger_group)
);

create table quartz_simple_triggers (
	trigger_name VARCHAR(80) not null,
	trigger_group VARCHAR(80) not null,
	repeat_count LONG not null,
	repeat_interval LONG not null,
	times_triggered LONG not null,
	primary key (trigger_name, trigger_group)
);

create table quartz_cron_triggers (
	trigger_name VARCHAR(80) not null,
	trigger_group VARCHAR(80) not null,
	cron_expression VARCHAR(80) not null,
	time_zone_id VARCHAR(80),
	primary key (trigger_name, trigger_group)
);

create table quartz_blob_triggers (
	trigger_name VARCHAR(80) not null,
	trigger_group VARCHAR(80) not null,
	blob_data BLOB null,
	primary key (trigger_name, trigger_group)
);

create table quartz_trigger_listeners (
    trigger_name  VARCHAR(80) not null,
    trigger_group VARCHAR(80) not null,
    trigger_listener VARCHAR(80) not null,
	primary key (trigger_name, trigger_group, trigger_listener)
);

create table quartz_calendars (
	calendar_name  VARCHAR(80) not null primary key,
	calendar BLOB not null
);

create table quartz_paused_trigger_grps (
	trigger_group  VARCHAR(80) not null primary key
);

create table quartz_fired_triggers (
	entry_id VARCHAR(95) not null primary key,
	trigger_name VARCHAR(80) not null,
	trigger_group VARCHAR(80) not null,
	is_volatile VARCHAR(1) not null,
	instance_name VARCHAR(80) not null,
	fired_time LONG not null,
	priority INTEGER not null,
	state VARCHAR(16) not null,
	job_name VARCHAR(80) null,
	job_group VARCHAR(80) null,
	is_stateful VARCHAR(1) null,
	requests_recovery VARCHAR(1) null
);

create table quartz_scheduler_state (
	instance_name VARCHAR(80) not null primary key,
	last_checkin_time LONG not null,
	checkin_interval LONG not null
);

create table quartz_locks (
	lock_name  VARCHAR(40) not null primary key
);

insert into quartz_locks values('TRIGGER_ACCESS');
insert into quartz_locks values('JOB_ACCESS');
insert into quartz_locks values('CALENDAR_ACCESS');
insert into quartz_locks values('STATE_ACCESS');
insert into quartz_locks values('MISFIRE_ACCESS');

create index IX_quartz_triggers_state on quartz_triggers(trigger_state);
create index IX_quartz_triggers_next_fire_time on quartz_triggers(next_fire_time);
create index IX_quartz_triggers_state_next_fire_time on quartz_triggers(trigger_state, next_fire_time);
create index IX_quartz_fired_triggers_trigger_name on quartz_fired_triggers(trigger_name);
create index IX_quartz_fired_triggers_trigger_group on quartz_fired_triggers(trigger_group);
create index IX_quartz_fired_triggers_trigger_name_group on quartz_fired_triggers(trigger_name, trigger_group);
create index IX_quartz_fired_triggers_instance_name on quartz_fired_triggers(instance_name);
create index IX_quartz_fired_triggers_job_name on quartz_fired_triggers(job_name);
create index IX_quartz_fired_triggers_job_group on quartz_fired_triggers(job_group);

drop table SocialActivity;
create table SocialActivity (
	activityId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	mirrorActivityId LONG,
	classNameId LONG,
	classPK LONG,
	type_ INTEGER,
	extraData STRING null,
	receiverUserId LONG
);

create table SocialRequest (
	uuid_ VARCHAR(75) null,
	requestId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	type_ INTEGER,
	extraData STRING null,
	receiverUserId LONG,
	status INTEGER
);

update User_ set timeZoneId = 'America/Anchorage' where timeZoneId = 'AST';
update User_ set timeZoneId = 'America/Los_Angeles' where timeZoneId = 'PST';
update User_ set timeZoneId = 'America/Denver' where timeZoneId = 'MST';
update User_ set timeZoneId = 'America/Chicago' where timeZoneId = 'CST';
update User_ set timeZoneId = 'America/New_York' where timeZoneId = 'EST';
update User_ set timeZoneId = 'America/Puerto_Rico' where timeZoneId = 'PRT';
update User_ set timeZoneId = 'America/St_Johns' where timeZoneId = 'CNT';
update User_ set timeZoneId = 'America/Sao_Paulo' where timeZoneId = 'BET';
update User_ set timeZoneId = 'UTC' where timeZoneId = 'GMT';
update User_ set timeZoneId = 'Europe/Lisbon' where timeZoneId = 'WET';
update User_ set timeZoneId = 'Europe/Paris' where timeZoneId = 'CET';
update User_ set timeZoneId = 'Europe/Istanbul' where timeZoneId = 'EET';
update User_ set timeZoneId = 'Asia/Tehran' where timeZoneId = 'Iran';
update User_ set timeZoneId = 'Asia/Calcutta' where timeZoneId = 'IST';
update User_ set timeZoneId = 'Asia/Saigon' where timeZoneId = 'VST';
update User_ set timeZoneId = 'Asia/Shanghai' where timeZoneId = 'CTT';
update User_ set timeZoneId = 'Asia/Tokyo' where timeZoneId = 'JST';
update User_ set timeZoneId = 'Asia/Seoul' where timeZoneId = 'ROK';
update User_ set timeZoneId = 'Australia/Darwin' where timeZoneId = 'ACT';
update User_ set timeZoneId = 'Australia/Sydney' where timeZoneId = 'AET';
update User_ set timeZoneId = 'Pacific/Guadalcanal' where timeZoneId = 'SST';
update User_ set timeZoneId = 'Pacific/Auckland' where timeZoneId = 'NST';

alter table WikiPage add modifiedDate DATE null;

COMMIT_TRANSACTION;

update WikiPage set modifiedDate = createDate;