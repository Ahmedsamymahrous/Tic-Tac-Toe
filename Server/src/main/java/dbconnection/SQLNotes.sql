CREATE TABLE player (
    playerID   INTEGER       PRIMARY KEY AUTOINCREMENT
                             UNIQUE
                             NOT NULL,
    name       VARCHAR (100),
    email      VARCHAR (100) UNIQUE,
    password   VARCHAR (100),
    main_score INTEGER,
    status     INTEGER (3),
    avatar     VARCHAR (255) 
);

CREATE TABLE games (
    gameID       INTEGER PRIMARY KEY AUTOINCREMENT
                         UNIQUE
                         NOT NULL,
    play1ID      INTEGER REFERENCES player (playerID),
    player2ID    INTEGER REFERENCES player (playerID),
    playr1Score  INTEGER,
    player2Score INTEGER
);
Update player set avatar="/icons/1.png" WHERE playerID = 1;
Update player set avatar= "/icons/2.png" WHERE playerID = 2;
Update player set avatar= "/icons/3.png" WHERE playerID = 3;
Update player set avatar= "/icons/4.png" WHERE playerID = 4;
Update player set avatar= "/icons/5.png" WHERE playerID = 5;
Update player set avatar= "/icons/6.png" WHERE playerID = 6;
Update player set avatar= "/icons/7.png" WHERE playerID = 7;
Update player set avatar= "/icons/8.png" WHERE playerID = 8;
Update player set avatar= "/icons/9.png" WHERE playerID = 9;
Update player set avatar= "/icons/10.png" WHERE playerID = 10;
Update player set avatar= "/icons/11.png" WHERE playerID = 11;
Update player set avatar= "/icons/12.png" WHERE playerID = 12;
Update player set avatar= "/icons/13.png" WHERE playerID = 13;
Update player set avatar= "/icons/14.png" WHERE playerID = 14;
Update player set avatar= "/icons/15.png" WHERE playerID = 15;
Update player set avatar= "/icons/16.png" WHERE playerID = 16;
Update player set avatar= "/icons/17.png" WHERE playerID = 17;
Update player set avatar= "/icons/18.png" WHERE playerID = 18;
Update player set avatar= "/icons/19.png" WHERE playerID = 19;
Update player set avatar= "/icons/20.png" WHERE playerID = 20;
Update player set avatar= "/icons/21.png" WHERE playerID = 21;
Update player set avatar= "/icons/22.png" WHERE playerID = 22;


Update player set main_score = "0" ,status = "1" WHERE playerID > 3;

select * from player;

