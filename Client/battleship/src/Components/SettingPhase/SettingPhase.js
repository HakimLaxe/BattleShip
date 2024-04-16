import React, { useState, useEffect } from 'react';
import { SHIPS, ROWS, COLS } from '../../contants/constants'
import { changeClickedCell, updateGridToK0OrK1, getNumberOfK0, setShipOnCells, setShipOnPlayerGrid, isSettingPhaseEnded, getShipImage, getCellRotation } from '../../Utils/gridConverter'
import { isCellValid, isClickedCellValid } from '../../Utils/validation'
import { showWarningAlert } from '../../Components/Alert/Alerts'
import Cell from '../Cell/Cell'
import './SettingPhase.css';


const SettingGrid = ({ atEndSettingPhase }) => {

    const [cells, setCells] = useState([]);
    const [playerGrid, setPlayerGrid] = useState([]);
    const [shipIndex, setShipIndex] = useState(0);

    const checkEndShipPhase = () => {

        if (isSettingPhaseEnded(playerGrid)) {
            atEndSettingPhase(playerGrid, cells)
        }
    }

    const getCellImage = (type, cellId) => {
        let cellImg = getShipImage(playerGrid, cellId, type)
        return cellImg;
    }

    const getRotation = (type, cellId) => {
        return getCellRotation(playerGrid, cellId, type)
    }

    const checkShipCells = () => {

        if (isSettingPhaseEnded(playerGrid))
            return;

        let numOfK0 = getNumberOfK0(playerGrid)
        if (numOfK0 === SHIPS[shipIndex].length) {
            let updatedCells = setShipOnCells(cells, SHIPS[shipIndex])
            let updatedPlayerGrid = setShipOnPlayerGrid(playerGrid, SHIPS[shipIndex])
            setCells(updatedCells)
            setPlayerGrid(updatedPlayerGrid)
            let newShipIndex = shipIndex + 1
            setShipIndex(newShipIndex)
        }
    }

    const performUpdate = (cellId) => {
        let updatedCells = changeClickedCell(cells, cellId)
        setCells(updatedCells);
        let updatedGrid = updateGridToK0OrK1(playerGrid, cellId);
        setPlayerGrid(updatedGrid);
    }

    const manageCellClick = (cellId, type) => {

        if (type === 'K1') {
            if (isCellValid(playerGrid, cellId)) {
                performUpdate(cellId)
            }
            else {
                showWarningAlert('Warning', 'Invalid Cell Clicked')
            }
        }
        if (type === 'K0') {
            if (isClickedCellValid(playerGrid, cellId)) {
                performUpdate(cellId)
            }
            else {
                showWarningAlert('Warning', 'It is not possible to select a middle clicked cell')
            }
        }
    }

    useEffect(() => {
        checkShipCells()
        checkEndShipPhase()
    }, [cells]);

    useEffect(() => {
        const initialCells = [];

        ROWS.forEach((row, rowIndex) => {
            let rowCells = [];
            COLS.forEach((col, colIndex) => {
                let cellID = col + '' + row;
                if (rowIndex === 0) {
                    rowCells.push({ cellId: cellID, type: 'header', text: col, rotation: 0 });
                } else if (colIndex === 0) {
                    rowCells.push({ cellId: cellID, type: 'header', text: row, rotation: 0 });
                } else {
                    rowCells.push({ cellId: cellID, type: "K1", text: '', rotation: 0 });
                }
            });
            initialCells.push(rowCells);
        });

        setCells(initialCells);

        let matrix = []
        for (let i = 0; i < 10; i++) {
            let row = []
            for (let j = 0; j < 10; j++) {
                row.push('K1');
            }
            matrix.push([...row])
        }
        setPlayerGrid(matrix)

    }, []);

    return (
        <div className="setting-grid">
            {cells.map((rowCells, rowIndex) => (

                rowCells.map((cell, colIndex) => (

                    <Cell
                        cellId={cell.cellId}
                        type={cell.type}
                        text={cell.text}
                        imageUrl={getCellImage(cell.type, cell.cellId)}
                        rotation={getRotation(cell.type, cell.cellId)}
                        manageCellClick={manageCellClick}
                    />
                ))
            ))}
        </div>
    );
}
export default SettingGrid;