package com.spring.battleship.battleshipServer.Utility;

import com.spring.battleship.battleshipServer.Constant.Grid;
import com.spring.battleship.battleshipServer.Constant.ShipConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GridUtils {
    @Autowired
    private ShipConstants ships;

    private int getIndex(int rowIndex, int colIndex){
        return rowIndex*10+colIndex;
    }

    public boolean changeCell(String[][] gridMatrix, int attackedCellRow, int attackedCellCol){
        String currentValue = gridMatrix[attackedCellCol][attackedCellRow];
        String newValue = null;
        boolean isShooted = true;

        if ( currentValue.equals(ships.aircraft().getSymbol()) )
            newValue = ships.aircraft().getAttackedCellSymbol();
        else if ( currentValue.equals(ships.battleship().getSymbol()) )
            newValue = ships.battleship().getAttackedCellSymbol();
        else if ( currentValue.equals(ships.cruiser().getSymbol()) )
            newValue = ships.cruiser().getAttackedCellSymbol();
        else if ( currentValue.equals(ships.destroyer().getSymbol()) )
            newValue = ships.destroyer().getAttackedCellSymbol();
        else if ( currentValue.equals(ships.submariner().getSymbol()) )
            newValue = ships.submariner().getAttackedCellSymbol();
        else if ( currentValue.equals(ships.getEmptyCell())) {
            newValue = ships.getEmptyAttackedCell();
            isShooted = false;
        }

        if ( newValue != null )
            gridMatrix[attackedCellCol][attackedCellRow] = newValue;

        else
            throw new RuntimeException("Invalid cell Value");

        return isShooted;
    }

    public int[] convertCoordinate(String cell){

        if(cell == null || cell.length() < 2) {
            return null;
        }

        int row = Character.toUpperCase(cell.charAt(0)) - 'A';
        int col;
        try {
            col = Integer.parseInt(cell.substring(1)) - 1;
        } catch(NumberFormatException e) {
            return null;
        }

        return new int[] {row, col};
    }

    public boolean isFirstMoveDone(String[][] gridMatrix){
        return
                        isShipAttacked(gridMatrix, ships.aircraft().getAttackedCellSymbol() )
                        || isShipAttacked(gridMatrix, ships.battleship().getAttackedCellSymbol() )
                        || isShipAttacked(gridMatrix, ships.cruiser().getAttackedCellSymbol() )
                        || isShipAttacked(gridMatrix, ships.destroyer().getAttackedCellSymbol() )
                        || isShipAttacked(gridMatrix, ships.submariner().getAttackedCellSymbol() )
                        || isShipAttacked(gridMatrix, ships.getEmptyAttackedCell());
    }

    public boolean areShipsDestroyed(String[][] grid){
        return
                isShipDestroyed(grid, ships.aircraft().getAttackedCellSymbol(), ships.aircraft().getShipLength())
                && isShipDestroyed(grid, ships.battleship().getAttackedCellSymbol(), ships.battleship().getShipLength())
                && isShipDestroyed(grid, ships.cruiser().getAttackedCellSymbol(), ships.cruiser().getShipLength())
                && isShipDestroyed(grid, ships.destroyer().getAttackedCellSymbol(), ships.destroyer().getShipLength())
                && isShipDestroyed(grid, ships.submariner().getAttackedCellSymbol(), ships.submariner().getShipLength());
    }

    private boolean isShipDestroyed(String[][] gridMatrix, String attackedCellSymbol, int shipLength){
        return Arrays.stream(gridMatrix)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.equals(attackedCellSymbol))
                .count() == shipLength;
    }
    private boolean isShipAttacked(String[][] gridMatrix, String attackedCellSymbol){
        return Arrays.stream(gridMatrix)
                .flatMap(Arrays::stream)
                .filter(cell -> cell.equals(attackedCellSymbol))
                .count() > 0;
    }

    public String[][] convertJsonToMatrix(String jsonGrid){
        String[][] gridMatrix;
        try{
            gridMatrix = performConversion(jsonGrid);
        }
        catch(Exception ex){
            System.out.println("[GridUtils][convertJsonToMatrix] Exception: " + ex.getMessage());
            return null;
        }
        return gridMatrix;
    }
    private String[][] performConversion(String jsonGrid){
        String[][] gridMatrix = new String[Grid.MAX_ROW][Grid.MAX_COL];
        String[] gridValues = jsonGrid
                .replace("[","")
                .replace("]","")
                .replace("\"","")
                .split(",");

        for (int i = 0; i < Grid.MAX_ROW ; i++){
            for (int j = 0; j < Grid.MAX_COL ; j++){
                gridMatrix[i][j] = gridValues[getIndex(i,j)];
            }
        }
        return gridMatrix;
    }
}
