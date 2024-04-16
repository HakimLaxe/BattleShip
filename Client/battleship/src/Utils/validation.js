import { getNumberOfK0, getClickedCells, convertCoordinate } from './gridConverter'

export const isGameIdValid = (gameId) => {
    const pattern = /^[A-Z0-9]{5}$/;
    return pattern.test(gameId);
}

export const isClickedCellValid = (playerGrid, cellId) => {
    let values = []
    let val;
    let [row, col] = convertCoordinate(cellId)
    let clickedCells = getClickedCells(playerGrid);
    if (isVertical(col, clickedCells)) {
        values = clickedCells.map(cell => cell.row)
        val = row
    }
    if (isHorizontal(row, clickedCells)) {
        values = clickedCells.map(cell => cell.col)
        val = col
    }

    if (Math.max(...values) !== val && Math.min(...values) !== val)
        return false;

    return true;
}

export const isCellValid = (playerGrid, cellId) => {

    if (getNumberOfK0(playerGrid) === 0) {
        return true
    }

    let [row, col] = convertCoordinate(cellId)
    let clickedCells = getClickedCells(playerGrid);
    if (!isVertical(col, clickedCells) && !isHorizontal(row, clickedCells))
        return false
    if (!checkAdjacentCell(row, col, clickedCells))
        return false
    return true
}

export const isHorizontal = (row, clickedCells) => {
    return clickedCells.filter(cell => cell.row === row).length === clickedCells.length
}

export const isVertical = (col, clickedCells) => {
    return clickedCells.filter(cell => cell.col === col).length === clickedCells.length
}

export const checkAdjacentCell = (row, col, clickedCells) => {
    for (let cell of clickedCells) {
        if (Math.abs((cell.row - row) + (cell.col - col)) === 1)
            return true
    }
    return false
}