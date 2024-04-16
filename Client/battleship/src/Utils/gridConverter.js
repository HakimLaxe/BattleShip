import { isHorizontal, isVertical } from './validation'

export function convertCoordinate(coordinate) {
    const letters = 'ABCDEFGHIJ';
    const col = letters.indexOf(coordinate[0].toUpperCase());
    const row = parseInt(coordinate.substring(1), 10) - 1;
    return [row, col];
}

export function revertCoordinate(row, col) {
    const letters = 'ABCDEFGHIJ';
    const letter = letters[col];
    const number = row + 1;
    return `${letter}${number}`;
}

export function getCellRotation(playerGrid, cellId, type) {
    if (type === 'O1' || type === 'X0' || type === 'K1'
        || type === 'K0' || type === 'header') {
        return 0
    }

    let [row, col] = convertCoordinate(cellId)
    let shipCells = getShipCells(playerGrid, type)
    let val;
    let values = []

    if (isVertical(col, shipCells)) {
        return 90
    }

    return 0
}

export function getShipImage(playerGrid, cellId, type) {
    if (type === 'O1' || type === 'X0' || type === 'K1'
        || type === 'K0' || type === 'header') {
        return ''
    }

    let [row, col] = convertCoordinate(cellId)
    let shipCells = getShipCells(playerGrid, type)
    let val;
    let values = []

    if (isVertical(col, shipCells)) {
        values = shipCells.map(cell => cell.row)
        val = row
    }
    if (isHorizontal(row, shipCells)) {
        values = shipCells.map(cell => cell.col)
        val = col
    }

    if (Math.max(...values) === val)
        return '/images/' + type + '-bow.png'

    if (Math.min(...values) === val)
        return '/images/' + type + '-stern.png'

    return '/images/' + type + '-midship.png'

}

export function setShipOnPlayerGrid(playerGrid, ship) {
    let shipType = ship.name[0] + '1'
    let updatedGrid = playerGrid.map(row => [...row]);
    for (let row = 0; row < 10; row++) {
        for (let col = 0; col < 10; col++) {
            if (updatedGrid[row][col] === 'K0')
                updatedGrid[row][col] = shipType
        }
    }
    return updatedGrid;
}

export function setShipOnCells(cells, ship) {
    let shipType = ship.name[0] + '1'
    let updatedCells = cells.map(row => [...row]);
    for (let row of updatedCells) {
        for (let cell of row) {
            if (cell.type === 'K0') {
                cell.type = shipType
            }
        }
    }
    return updatedCells;
}

export function changeClickedCell(cells, cellId) {
    let updatedCells = cells.map(row => [...row]);

    for (let row of updatedCells) {
        for (let cell of row) {
            if (cell.cellId === cellId) {
                cell.type = cell.type === 'K1' ? 'K0' : 'K1';
                break;
            }
        }
    }
    return updatedCells;
}

export function updateGridToK0OrK1(playerGrid, cellId) {
    let [row, col] = convertCoordinate(cellId)
    let updatedGrid = playerGrid.map(row => [...row]);
    updatedGrid[row][col] = (updatedGrid[row][col] === 'K1' ? 'K0' : 'K1');
    return updatedGrid;
}

export function getNumberOfK0(matrix) {
    let numOfK0 = 0;
    matrix.map(rows => {
        rows.map(value => {
            if (value === 'K0')
                numOfK0++
        })
    })
    return numOfK0;
}

export function isSettingPhaseEnded(matrix) {
    let numOfD1 = 0;
    matrix.map(rows => {
        rows.map(value => {
            if (value === 'D1')
                numOfD1++
        })
    })
    return numOfD1 === 2;
}

export function getShipCells(matrix, shipType) {
    let shipCoords = []
    matrix.map((rows, rowsIndex) => {
        rows.map((value, valueIndex) => {
            if (value === shipType) {
                shipCoords.push(
                    { "row": rowsIndex, "col": valueIndex }
                )
            }
        })
    })
    return shipCoords
}

export function getClickedCells(matrix) {
    let clickedCellCoords = []
    matrix.map((rows, rowsIndex) => {
        rows.map((value, valueIndex) => {
            if (value === 'K0') {
                clickedCellCoords.push(
                    { "row": rowsIndex, "col": valueIndex }
                )
            }
        })
    })
    return clickedCellCoords
}
