package com.spring.battleship.battleshipServer.DTO;

public class AttackRequestDTO {
    private String playerId;
    private String attackedCell;

    public AttackRequestDTO(String playerId, String attackedCell) {
        this.playerId = playerId;
        this.attackedCell = attackedCell;
    }

    public AttackRequestDTO() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getAttackedCell() {
        return attackedCell;
    }

    public void setAttackedCell(String attackedCell) {
        this.attackedCell = attackedCell;
    }
}
