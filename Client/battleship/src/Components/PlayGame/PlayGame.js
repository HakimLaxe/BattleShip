import React, { useState, useEffect } from 'react';
import { COLS, ROWS, TURN } from '../../contants/constants'
import { revertCoordinate } from '../../Utils/gridConverter'
import PlayerGrid from '../PlayerGrid/PlayerGrid';
import OpponentGrid from '../OpponentGrid/OpponentGrid';
import MessageBox from '../MessageBox/MessageBox';
import { checkNextMove } from '../../api/API'
import './PlayGame.css'

const PlayGame = ({ cells, grid, playerId }) => {
    const [opponentGrid, setOpponentGrid] = useState([]);
    const [playerGrid, setPlayerGrid] = useState([])
    const [message, setMessage] = useState('Waiting for your opponent');
    const [turn, setTurn] = useState(null);
    const [attackedCell, setAttackedCell] = useState(null);
    const [cellToBeAttacked, setCellToBeAttacked] = useState(null);
    const [firstRender, setFirstRender] = useState(true)
    const [isPollingStarted, setIsPollingStarted] = useState(true);
    const [isGameEnded, setIsGameEnded] = useState(false);
    const [winner, setWinner] = useState(null);

    const manageResposeData = (responseData) => {
        if (responseData.gameEnded) {
            setIsGameEnded(responseData.gameEnded)
            setWinner(responseData.winner)
        }

        if (turn === null || turn !== responseData.yourTurn) {
            setTurn(responseData.yourTurn)
        }

        if (message === null || message !== responseData.message)
            setMessage(responseData.message)

        if (attackedCell === null ||
            (attackedCell !== responseData.lastAttack && attackedCell !== "")
        )
            setAttackedCell(responseData.lastAttack)
    }

    useEffect(() => {
        if (winner !== playerId) {
            let copyGrid = [...playerGrid]
            copyGrid.map(rowCells => {
                rowCells.map(cell => {
                    if (['A1', 'B1', 'C1', 'D1', 'S1'].includes(cell.type))
                        cell.type = 'Y0'
                }
                )
            })
            setPlayerGrid(copyGrid)
        }
    }, [isGameEnded])

    useEffect(() => {
        if (attackedCell !== "") {
            let copyGrid = [...playerGrid]
            copyGrid.map(rowCells => {
                rowCells.map(cell => {
                    if (cell.cellId === attackedCell) {
                        cell.type = cell.type === 'K1' ? 'X0' : 'Y0'
                    }
                }
                )
            })
            setPlayerGrid(copyGrid)
        }
    }, [attackedCell])

    useEffect(() => {
        let intervalId
        if (isPollingStarted) {
            const fetchData = async () => {
                checkNextMove(playerId).then(res => {
                    if (res.status === 200) {
                        manageResposeData(res.data)
                    }
                })
                    .catch(error => {
                        console.log(error)
                    })
            }
            intervalId = setInterval(fetchData, 3000);
        }
        return () => {
            clearInterval(intervalId);
        };
    }, [isPollingStarted])

    useEffect(() => {

        if (firstRender) {

            setPlayerGrid(cells)
            let startingOpponentGrid = []
            for (let i = 0; i < ROWS.length; i++) {
                let row = []
                for (let j = 0; j < COLS.length; j++) {
                    let cellId = COLS[j] + String(ROWS[i])
                    if (i === 0) {
                        row.push({ text: COLS[j], type: 'header', cellId: cellId })
                    }
                    else if (j === 0) {
                        row.push({ text: ROWS[i], type: 'header', cellId: cellId })
                    }
                    else {
                        row.push({ text: '?', type: 'K1', cellId: cellId })
                    }
                }
                startingOpponentGrid.push([...row])
            }
            setOpponentGrid(startingOpponentGrid)
            setFirstRender(false)
        }
    }, []);

    return (
        <div className="game-container">
            <MessageBox message={message} />
            <div className="grids-container">
                <PlayerGrid cells={playerGrid} grid={grid} />
                <OpponentGrid
                    grid={opponentGrid}
                    turn={turn}
                    playerId={playerId}
                    stopPlaying={isGameEnded}
                />
            </div>
        </div>
    );
}

export default PlayGame;