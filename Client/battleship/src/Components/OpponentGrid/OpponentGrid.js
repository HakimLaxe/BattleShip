import React, { useState, useEffect } from 'react';
import Cell from '../Cell/Cell';
import { TURN } from '../../contants/constants'
import { attackCell } from '../../api/API'
import { showWarningAlert } from '../Alert/Alerts';
import './OpponentGrid.css'

const OpponentGrid = ({ grid, turn, playerId, stopPlaying }) => {

    const [lastClickedCell, setLastClickedCell] = useState('');
    const [opponentGrid, setOpponentGrid] = useState([]);

    const changeGrid = (isShotted) => {
        let copyGrid = [...opponentGrid]
        let newCellType = isShotted ? 'Y0' : 'X0'
        copyGrid.map(row => {
            row.map(copyCell => {
                if (copyCell.cellId === lastClickedCell) {
                    copyCell.type = newCellType
                }
            })
        })
        setOpponentGrid(copyGrid)
    }

    const performAttack = () => {

        attackCell(playerId, lastClickedCell).then(res => {
            if (res.status === 200) {
                if (res.data.valid) {
                    changeGrid(res.data.shotted)
                    setLastClickedCell('')
                }
            }
            else
                showWarningAlert('Warning: ' + res.status + ' Status', res.data.message)
        }).catch(error => {
            console.error("Error catched:", error);
            showWarningAlert('Warning: ' + error.status + ' Status', error.data.message)
        });
    }

    useEffect(() => {

        if (stopPlaying)
            return

        if (turn && lastClickedCell !== '') {
            performAttack()
        }
    }, [lastClickedCell])

    useEffect(() => {
        setOpponentGrid(grid);
    });

    const onClickButton = (cell) => {

        if (stopPlaying)
            return

        const { cellId, type } = cell;
        let cellType = type
        if (cellType === 'X0' || cellType === 'Y0')
            return;
        let newCellType = cellType === 'K1' ? 'K0' : 'K1'
        let copyGrid = [...opponentGrid]
        copyGrid.map(row => {
            row.map(copyCell => {
                if (copyCell.cellId === cellId) {
                    copyCell.type = newCellType
                    if (newCellType === 'K0') {
                        setLastClickedCell(cellId)
                    }
                }
                if (copyCell.type === 'K0' && copyCell.cellId !== cellId) {
                    copyCell.type = 'K1'
                }
            })
        })
        setOpponentGrid(copyGrid)
    }
    return (
        <div className='setting-opponent-grid'>
            {opponentGrid.map(row => row.map(cell =>
                <Cell
                    key={cell.cellId}
                    cellId={cell.cellId}
                    type={cell.type}
                    text={cell.text}
                    imageUrl={cell.type === 'Y0' ? '/images/sunk-ship.png' : undefined}
                    manageCellClick={() => onClickButton(cell)}
                />))}
        </div>
    );
}
export default OpponentGrid;