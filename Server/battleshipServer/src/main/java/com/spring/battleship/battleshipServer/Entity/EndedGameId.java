package com.spring.battleship.battleshipServer.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class EndedGameId implements Serializable {
    @Column(name="game-id", nullable = false)
    private String gameId;
    @Column(name="start-time", nullable = false)
    private LocalDateTime startTime;
    public EndedGameId() {}

    public EndedGameId(String gameId, LocalDateTime startTime) {
        this.gameId = gameId;
        this.startTime = startTime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndedGameId)) return false;
        EndedGameId that = (EndedGameId) o;
        return Objects.equals(getGameId(), that.getGameId()) &&
                Objects.equals(getStartTime(), that.getStartTime());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getStartTime());
    }

    public String getGameId() {
        return gameId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}

