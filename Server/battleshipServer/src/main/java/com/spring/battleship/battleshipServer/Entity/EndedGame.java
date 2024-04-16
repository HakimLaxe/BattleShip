package com.spring.battleship.battleshipServer.Entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ended-game")
public class EndedGame {
    @EmbeddedId
    private EndedGameId id;

    public EndedGame(){}
    public EndedGame(EndedGameId id){
        this.id = id;
    }
    @Column(name="end-time",nullable = false)
    private LocalDateTime endTime;
   @Column(name="player1", nullable = false)
    private String player1;
   @Column(name="player2", nullable = false)
   private String player2;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Column(name="winner")
   private String winner;

    public EndedGameId getId() {
        return id;
    }

    public void setId(EndedGameId id) {
        this.id = id;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
}
