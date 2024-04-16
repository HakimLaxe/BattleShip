-- BattleShip.`ended-game` definition

CREATE TABLE `ended-game` (
  `game-id` varchar(100) NOT NULL,
  `player1` varchar(100) NOT NULL,
  `player2` varchar(100) NOT NULL,
  `start-time` datetime NOT NULL,
  `end-time` datetime NOT NULL,
  `winner` varchar(100) DEFAULT NULL,
  UNIQUE KEY `ended_game_unique` (`game-id`,`start-time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- BattleShip.`hosting-game` definition

CREATE TABLE `hosting-game` (
  `player1` varchar(100) NOT NULL,
  `player2` varchar(100) DEFAULT NULL,
  `start-time` datetime NOT NULL,
  `game-id` varchar(100) NOT NULL,
  PRIMARY KEY (`game-id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- BattleShip.`live-game` definition

CREATE TABLE `live-game` (
  `game-Id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `player1` varchar(100) NOT NULL,
  `player2` varchar(100) NOT NULL,
  `player-grid1` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `player-grid2` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `start-time` datetime(6) NOT NULL,
  `first-turn` varchar(100) DEFAULT NULL,
  `number-of-turns` int DEFAULT NULL,
  `last-attack` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`game-Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;