package com.spring.battleship.battleshipServer.DTO;

public class AttackResponseDTO {
    private boolean isValid;
    private boolean isShotted;
    private String attackCell;

    public AttackResponseDTO(boolean isValid, boolean isShotted, String attackCell) {
        this.isValid = isValid;
        this.isShotted = isShotted;
        this.attackCell = attackCell;
    }

    public AttackResponseDTO() {
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isShotted() {
        return isShotted;
    }

    public void setShotted(boolean shotted) {
        isShotted = shotted;
    }

    public String getAttackCell() {
        return attackCell;
    }

    public void setAttackCell(String attackCell) {
        this.attackCell = attackCell;
    }
}
