package com.spring.battleship.battleshipServer.DTO;

import java.time.LocalDateTime;

public class LiveGameDTO {
    private String playerId;
    private String playerGrid;
    private LocalDateTime startTime;

    public LiveGameDTO(){}
    public LiveGameDTO(String playerId, String playerGrid, LocalDateTime startTime) {
        this.playerId = playerId;
        this.playerGrid = playerGrid;
        this.startTime = startTime;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerGrid() {
        return playerGrid;
    }

    public void setPlayerGrid(String playerGrid) {
        this.playerGrid = playerGrid;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
