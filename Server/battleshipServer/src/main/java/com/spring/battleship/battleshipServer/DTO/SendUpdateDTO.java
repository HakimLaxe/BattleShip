package com.spring.battleship.battleshipServer.DTO;

public class SendUpdateDTO {
    private String message;
    private boolean isYourTurn;
    private String lastAttack;

    private boolean isGameEnded;
    private String winner;

    public String getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(String lastAttack) {
        this.lastAttack = lastAttack;
    }


    public SendUpdateDTO(){}
    public SendUpdateDTO(String message, boolean isYourTurn, String lastAttack) {
        this.message = message;
        this.isYourTurn = isYourTurn;
        this.lastAttack = lastAttack;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        isGameEnded = gameEnded;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
