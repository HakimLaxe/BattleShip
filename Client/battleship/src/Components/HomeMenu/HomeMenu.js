import React, { useState, useEffect } from 'react';
import { joinGame, createGame, isGameReady } from '../../api/API'
import { isGameIdValid } from '../../Utils/validation'
import { showWarningAlert } from '../Alert/Alerts'
import './HomeMenu.css'


const HomeMenu = ({ atEndMenuPhase }) => {

    const [showModal, setShowModal] = useState(false);
    const [showLoader, setShowLoader] = useState(false);
    const [showInputField, setShowInputField] = useState(false);
    const [gameId, setGameId] = useState('');
    const [playerId, setPlayerId] = useState('');
    const [message, setMessage] = useState("");
    const [inputGameId, setInputGameId] = useState("");
    const [isPollingStarted, setIsPollingStarted] = useState(false);

    useEffect(() => {
        let intervalId
        if (isPollingStarted) {
            const fetchData = async () => {
                isGameReady(gameId, playerId).then(res => {
                    if (res.status === 200 && res.data) {
                        setIsPollingStarted(false)
                        atEndMenuPhase(playerId, gameId)
                    }
                })
            }
            intervalId = setInterval(fetchData, 3000);
        }

        return () => {
            clearInterval(intervalId);
        };
    }, [isPollingStarted]);

    const onCreateGame = () => {
        createGame().then(res => {
            if (res.status === 200) {
                setPlayerId(res.data.player1)
                setGameId(res.data.gameId)
                setIsPollingStarted(true)
                //atEndMenuPhase(res.data.playerId)
            }
            else
                showWarningAlert('Warning: ' + res.status + ' Status', res.data.message)
        }).catch(error => {
            console.error("Error catched:", error);
            showWarningAlert('Warning: ' + error.status + ' Status', error.data.message)
        });
    }


    const onJoinSubmit = (event) => {
        event.preventDefault();
        if (isGameIdValid(inputGameId)) {
            joinGame(inputGameId).then(res => {
                if (res.status === 200) {
                    setPlayerId(res.data.player2)
                    setGameId(res.data.gameId)
                    atEndMenuPhase(res.data.player2, inputGameId)
                }
                else {
                    showWarningAlert('Warning: ' + res.status + ' Status', res.data.message)
                }
            })

        }
        else {
            showWarningAlert('Warning:', 'Invalid GameId Format')
        }
    }

    const handleInputGameIdChange = (event) => {
        setInputGameId(event.target.value);
    }

    useEffect(() => {
        setMessage(`Game Created! Share this gameId ${gameId} with your enemy!`)
        setShowLoader(false)

    }, [gameId])

    const openModalCreate = () => {
        setShowModal(true);
        setShowInputField(false);
        setShowLoader(true);
        setMessage("Creating a new Game...");
        onCreateGame();
    };

    const openModalJoin = () => {
        setShowModal(true);
        setShowInputField(true);
        setShowLoader(false);
        setMessage("Insert GameCode");
    }

    const openCredits = () => {
        setShowModal(true);
        setShowInputField(false);
        setShowLoader(false);
        setMessage("Simple Portfolio FullStack Application done by: Luca Fumarola");
    }

    const closeModal = () => {
        setShowModal(false);
    };

    return (
        <div className="game-menu-container">
            {showModal && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={() => closeModal()}>&times;</span>
                        {showLoader && <div className="loader"></div>}
                        <p>{message}</p>
                        {showInputField &&
                            <form>
                                <input
                                    type="text"
                                    onChange={handleInputGameIdChange}
                                    placeholder="GameId"
                                />
                                <button type="submit" onClick={(event) => onJoinSubmit(event)}>
                                    Submit
                                </button>
                            </form>
                        }
                    </div>
                </div>
            )}
            <div className="game-menu">
                <button onClick={() => openModalCreate()}>Create a Game</button>
                <button onClick={() => openModalJoin()}>Join a New Game</button>
                <button onClick={() => openCredits()}>Credits</button>
            </div>
        </div>
    );
}

export default HomeMenu;