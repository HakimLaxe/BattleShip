package com.spring.battleship.battleshipServer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class HostingGameDTO {
    private String gameId;
    private String player1;
    private String player2;
    private LocalDateTime startTime;

    @JsonIgnore
    public boolean isInvalidJoinableGame(){
        return (this.gameId == null) || (this.player2 == null) || (this.startTime == null);
    }
    @JsonIgnore
    public boolean isInvalidCreatedGame(){
        return (this.gameId == null) || (this.player1 == null) || (this.startTime == null);
    }

    public HostingGameDTO(String gameId, String player1, LocalDateTime startTime) {
        this.gameId = gameId;
        this.player1 = player1;
        this.startTime = startTime;
    }

    public HostingGameDTO() {
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
