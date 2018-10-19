--
-- This SQL script builds a monopoly database, deleting any pre-existing version.
--
-- @author kvlinden
-- @version Summer, 2015
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS PlayerGame CASCADE;
DROP TABLE IF EXISTS Game CASCADE;
DROP TABLE IF EXISTS Player CASCADE;
DROP TABLE IF EXISTS PlayerState CASCADE;
DROP TABLE IF EXISTS LocationState CASCADE;


-- Create the schema.
CREATE TABLE Game (
	ID integer PRIMARY KEY,
	time timestamp
	);

CREATE TABLE Player (
	ID integer PRIMARY KEY,
	emailAddress varchar(50) NOT NULL,
	name varchar(50)
	);

CREATE TABLE PlayerGame (
	gameID integer REFERENCES Game(ID),
	playerID integer REFERENCES Player(ID),
	score integer
	);

CREATE TABLE PlayerState (
	gameID integer REFERENCES Game(ID),
	playerID integer REFERENCES Player(ID),
	pieceLocation integer,
	cash integer,
	CHECK (pieceLocation BETWEEN 1 and 40),
	CHECK (cash>=0)
	);

CREATE TABLE LocationState (
	gameID integer REFERENCES Game(ID),
	playerID integer REFERENCES Player(ID),
	location integer,
	houses integer,
	CHECK (location BETWEEN 1 and 40),
	CHECK (houses BETWEEN 0 and 5)
	);



-- Allow users to select data from the tables.
GRANT SELECT ON Game TO PUBLIC;
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON PlayerGame TO PUBLIC;
GRANT SELECT ON PlayerState TO PUBLIC;
GRANT SELECT ON LocationState TO PUBLIC;

-- Add sample records.
INSERT INTO Game VALUES (1, '2006-06-27 08:00:00');
INSERT INTO Game VALUES (2, '2006-06-28 13:20:00');
INSERT INTO Game VALUES (3, '2006-06-29 18:41:00');

INSERT INTO Player(ID, emailAddress) VALUES (1, 'me@calvin.edu');
INSERT INTO Player VALUES (2, 'king@gmail.edu', 'The King');
INSERT INTO Player VALUES (3, 'dog@gmail.edu', 'Dogbreath');

INSERT INTO PlayerGame VALUES (1, 1, 0.00);
INSERT INTO PlayerGame VALUES (1, 2, 0.00);
INSERT INTO PlayerGame VALUES (1, 3, 2350.00);
INSERT INTO PlayerGame VALUES (2, 1, 1000.00);
INSERT INTO PlayerGame VALUES (2, 2, 0.00);
INSERT INTO PlayerGame VALUES (2, 3, 500.00);
INSERT INTO PlayerGame VALUES (3, 2, 0.00);
INSERT INTO PlayerGame VALUES (3, 3, 5500.00);

INSERT INTO PlayerState VALUES(1, 1, 25, 2500);
INSERT INTO PlayerState VALUES(1, 2, 2, 2);
INSERT INTO PlayerState VALUES(1, 3, 39, 5000);
INSERT INTO PlayerState VALUES(2, 1, 14, 1);
INSERT INTO PlayerState VALUES(2, 2, 1, 1000);
INSERT INTO PlayerState VALUES(2, 3, 40, 0);
INSERT INTO PlayerState VALUES(3, 2, 20, 500);
INSERT INTO PlayerState VALUES(3, 3, 20, 3750);

INSERT INTO LocationState VALUES(1, 1, 1, 5);
INSERT INTO LocationState VALUES(1, 1, 2, 5);
INSERT INTO LocationState VALUES(1, 1, 3, 5);
INSERT INTO LocationState VALUES(1, 1, 4, 5);
INSERT INTO LocationState VALUES(1, 1, 5, 5);

INSERT INTO LocationState VALUES(1, 2, 6, 0);
INSERT INTO LocationState VALUES(1, 2, 7, 0);
INSERT INTO LocationState VALUES(1, 2, 8, 0);
INSERT INTO LocationState VALUES(1, 2, 9, 0);
INSERT INTO LocationState VALUES(1, 2, 10, 0);
