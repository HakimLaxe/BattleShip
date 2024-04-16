import React, { useState, useEffect } from 'react';
import { getShipImage, getCellRotation, revertCoordinate } from '../../Utils/gridConverter';
import Cell from '../Cell/Cell';
import './PlayerGrid.css'

const PlayerGrid = ({ cells, grid }) => {

    const getCellImage = (type, cellId) => {
        let cellImg = getShipImage(grid, cellId, type);
        return cellImg;
    }

    const getRotation = (type, cellId) => {
        return getCellRotation(grid, cellId, type);
    }

    return (
        <div className="setting-playing-grid">

            {cells.map((rowCells, rowIndex) => (
                rowCells.map((cell, cellIndex) => (
                    <Cell
                        key={cell.cellId}
                        cellId={cell.cellId}
                        type={cell.type}
                        text={cell.text}
                        imageUrl={getCellImage(cell.type, cell.cellId)}
                        rotation={getRotation(cell.type, cell.cellId)}
                        manageCellClick={() => console.log(cell.cellId + ' clicked')}
                    />
                ))
            ))
            }
        </div>
    );
}

export default PlayerGrid;
