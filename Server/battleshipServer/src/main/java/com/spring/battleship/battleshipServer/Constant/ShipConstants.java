package com.spring.battleship.battleshipServer.Constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.spring.battleship.battleshipServer.Model.Ship;

@Configuration
public class ShipConstants {

    @Value("K0")
    private String emptyAttackedCell;
    @Value("K1")
    private String emptyCell;
    @Bean
    public Ship destroyer() {
        return new Ship("Destroyer", 2, "D1", "D0");
    }

    @Bean
    public Ship battleship() {
        return new Ship("Battleship", 4, "B1", "B0");
    }

    @Bean
    public Ship aircraft() {
        return new Ship("Aircraft", 5, "A1", "A0");
    }

    @Bean
    public Ship cruiser() {
        return new Ship("Cruiser", 3, "C1", "C0");
    }

    @Bean
    public Ship submariner() {
        return new Ship("Submariner", 3, "S1", "S0");
    }

    public String getEmptyAttackedCell() {
        return emptyAttackedCell;
    }

    public void setEmptyAttackedCell(String emptyAttackedCell) {
        this.emptyAttackedCell = emptyAttackedCell;
    }

    public String getEmptyCell() {
        return emptyCell;
    }

    public void setEmptyCell(String emptyCell) {
        this.emptyCell = emptyCell;
    }
}
