create table Team (
	teamId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table Users_Teams (
	userId LONG not null,
	teamId LONG not null,
	primary key (userId, teamId)
);