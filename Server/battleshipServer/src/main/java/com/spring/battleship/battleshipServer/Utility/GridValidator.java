package com.spring.battleship.battleshipServer.Utility;

import com.spring.battleship.battleshipServer.Constant.Grid;
import com.spring.battleship.battleshipServer.Constant.ShipConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GridValidator {

    @Autowired
    private ShipConstants ships;

    public boolean isValidAttack(String cellValue){
        return (
                ships.aircraft().getSymbol().equals(cellValue)
                || ships.battleship().getSymbol().equals(cellValue)
                || ships.cruiser().getSymbol().equals(cellValue)
                || ships.destroyer().getSymbol().equals(cellValue)
                || ships.submariner().getSymbol().equals(cellValue)
                || ships.getEmptyCell().equals(cellValue)
                );
    }
    public boolean validateGrid(String[][] matrixGrid){
        if ( matrixGrid == null ){
            return false;
        }
        if ( !verifyShipsOccurences(matrixGrid) ){
            return false;
        }

        if ( !verifyShipsAligment(matrixGrid) ){
            return false;
        }

        return true;
    }

    private boolean verifyShipsOccurences(String[][] matrixGrid){
        return
                        checkShipOccurences(matrixGrid, ships.aircraft().getSymbol(), ships.aircraft().getShipLength() )
                        &&
                        checkShipOccurences(matrixGrid, ships.battleship().getSymbol(), ships.battleship().getShipLength() )
                        &&
                        checkShipOccurences(matrixGrid, ships.submariner().getSymbol(), ships.submariner().getShipLength() )
                        &&
                        checkShipOccurences(matrixGrid, ships.cruiser().getSymbol(), ships.cruiser().getShipLength() )
                        &&
                        checkShipOccurences(matrixGrid, ships.destroyer().getSymbol(), ships.destroyer().getShipLength() );
    }
    private boolean checkShipOccurences(String[][] matrixGrid, String shipSymbol, int shipLength){
        return Arrays.stream(matrixGrid)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.equals(shipSymbol))
                .count() == shipLength;
    }

    private boolean verifyShipsAligment(String[][] grid){
        return
                checkShipAlignment(grid, ships.aircraft().getSymbol())
                &&
                checkShipAlignment(grid, ships.battleship().getSymbol())
                &&
                checkShipAlignment(grid, ships.cruiser().getSymbol())
                &&
                checkShipAlignment(grid, ships.destroyer().getSymbol())
                &&
                checkShipAlignment(grid, ships.submariner().getSymbol());
    }
    private boolean checkShipAlignment(String[][] grid, String shipSymbol) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();

        for (int row = 0; row < Grid.MAX_ROW; row++) {
            for (int col = 0; col < Grid.MAX_COL; col++) {
                if (grid[row][col].equals(shipSymbol)) {
                    rows.add(row);
                    cols.add(col);
                }
            }
        }
        return rows.stream().distinct().count() == 1
                || cols.stream().distinct().count() == 1;
    }
}
