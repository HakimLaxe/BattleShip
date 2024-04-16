package com.spring.battleship.battleshipServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.checkerframework.common.reflection.qual.ClassBound;

import java.time.LocalDateTime;

@Entity
@Table(name = "live-game")
public class LiveGame {
    @Id
    @Column(name="game-id", nullable = false)
    private String gameId;
    @Column(name="player1", nullable = false)
    private String player1;
    @Column(name="player2", nullable = false)
    private String player2;
    @Column(name="player-grid1")
    private String playerGrid1;
    @Column(name="player-grid2")
    private String playerGrid2;
    @Column(name="start-time", nullable = false)
    private LocalDateTime startTime;
    @Column(name="first-turn")
    private String firstTurn;
    @Column(name = "number-of-turns")
    private int numberOfTurns;
    @Column(name = "last-attack")
    private String lastAttack;
    public String getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(String lastAttack) {
        this.lastAttack = lastAttack;
    }

    public LiveGame(){}
    public LiveGame(String gameId, String player1, String player2, String playerGrid1, String playerGrid2, LocalDateTime startTime) {
        this.gameId = gameId;
        this.player1 = player1;
        this.player2 = player2;
        this.playerGrid1 = playerGrid1;
        this.playerGrid2 = playerGrid2;
        this.startTime = startTime;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayerGrid1() {
        return playerGrid1;
    }

    public void setPlayerGrid1(String playerGrid1) {
        this.playerGrid1 = playerGrid1;
    }

    public String getPlayerGrid2() {
        return playerGrid2;
    }

    public void setPlayerGrid2(String playerGrid2) {
        this.playerGrid2 = playerGrid2;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(String firstTurn) {
        this.firstTurn = firstTurn;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }
}
