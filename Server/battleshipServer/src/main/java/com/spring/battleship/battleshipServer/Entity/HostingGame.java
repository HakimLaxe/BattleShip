package com.spring.battleship.battleshipServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="hosting-game")
public class HostingGame {
    @Id
    @Column(name="game-id", nullable = false)
    private String gameId;

    @Column(name="player1", nullable = false)
    private String player1;
    @Column(name="player2")
    private String player2;
    @Column(name="start-time", nullable = false)
    private LocalDateTime startTime;

    public HostingGame(){}
    public HostingGame(String gameId, String player1, LocalDateTime startTime) {
        this.gameId = gameId;
        this.player1 = player1;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
