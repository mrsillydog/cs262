--Retrieve a list of all the games, ordered by date with most recent coming first.
SELECT *
FROM Game
ORDER BY Game.time DESC;


--Retrieve all the games that occurred in the past week.
SELECT *
FROM Game
WHERE Game.time BETWEEN (DATE(NOW()) - 7) and DATE(NOW());

--Retrieve a list of players who have (non-NULL) names.
SELECT *
FROM Player
WHERE Player.name IS NOT NULL;

--Retrieve a list of IDs for players who have some game score larger than 2000.
SELECT PlayerGame.playerID
FROM PlayerGame
WHERE PlayerGame.score > 2000;

--Retrieve a list of players who have GMail accounts.
SELECT *
FROM Player
WHERE Player.emailAddress LIKE '%@gmail.edu';

--Retrieve all “The King”’s game scores in decreasing order.
SELECT PlayerGame.score
FROM Player, PlayerGame
WHERE Player.ID = PlayerGame.playerID
  AND Player.name = 'The King'
ORDER BY score DESC;


--Retrieve the name of the winner of the game played on 2006-06-28 13:20:00.
SELECT Player.name
FROM Player, PlayerGame, Game
WHERE Player.ID = PlayerGame.playerID
  AND Game.ID = PlayerGame.gameID
  AND PlayerGame.score = (SELECT MAX(PlayerGame.score) FROM PlayerGame, Game
    WHERE Game.time = '2006-06-28 13:20:00'
      AND PlayerGame.gameID = Game.ID);

/*
c. That clause ensures that P1 and P2 aren't the same Player. Since they have
the same name, greater than serves as an inequality operator.

d. Any situation where you need to compare tables of the same category,
essentially creating a matrix. So, if you wanted to check if two people
were friends (or enemies).
*/