CREATE TABLE user (
	userId VARCHAR (20) NOT NULL,
	userName VARCHAR (20) NOT NULL,
	useTypeId INT (1) NOT NULL,
	sexId INT (1) NOT NULL,
	rigisterDate TIMESTAMP NOT NULL,
	password VARCHAR (20) NOT NULL,
	lastAccessDate TIMESTAMP NULL,
	emailAddress VARCHAR (20) NULL,
	vCard VARCHAR (20) NULL,
	PRIMARY KEY (userId)
)