package com.spring.battleship.battleshipServer.Model;

public class Ship {
    private String name;
    private int shipLength;
    private String symbol;

    private String attackedCellSymbol;

    public Ship(String name, int shipLength, String symbol, String attackedCellSymbol) {
        this.name = name;
        this.shipLength = shipLength;
        this.symbol = symbol;
        this.attackedCellSymbol = attackedCellSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShipLength() {
        return shipLength;
    }

    public void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAttackedCellSymbol() {
        return attackedCellSymbol;
    }

    public void setAttackedCellSymbol(String attackedCellSymbol) {
        this.attackedCellSymbol = attackedCellSymbol;
    }
}
