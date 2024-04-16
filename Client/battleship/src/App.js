import React, { useState, useEffect } from 'react';
import { PHASES } from './contants/constants'
import HomeMenu from './Components/HomeMenu/HomeMenu'
import SettingGrid from './Components/SettingPhase/SettingPhase'
import GameRules from './Components/GameRules/GameRules';
import PlayGame from './Components/PlayGame/PlayGame';
import { sendGrid } from './api/API'
import { showWarningAlert } from './Components/Alert/Alerts'
import './App.css'

function App() {

  const [phase, setPhase] = useState(PHASES.MENU_PHASE);
  const [playerId, setPlayerId] = useState('');
  const [gameId, setGameId] = useState('');
  const [playerGrid, setPlayerGrid] = useState([]);
  const [playerCells, setPlayerCells] = useState([]);

  const sendGridToServer = (grid) => {
    sendGrid(playerId, grid).then(res => {
      if (res.status === 200)
        setPhase(PHASES.PLAYING_PHASE)
      else
        showWarningAlert('Warning: ' + res.status + ' Status', res.data.message)
    }).catch(error => {
      console.error("Error catched:", error);
      showWarningAlert('Warning: ' + error.status + ' Status', error.data.message)
    });
  }

  const atEndSettingPhase = (playerGrid, cells) => {
    setPlayerGrid(playerGrid)
    setPlayerCells(cells)
    sendGridToServer(playerGrid)
  }

  const atEndMenuPhase = (playerId, gameId) => {
    console.log(`PlayerId: ${playerId} GameId: ${gameId}`)
    setPlayerId(playerId);
    setGameId(gameId)
    setPhase(PHASES.SETTING_PHASE);
  }

  return (
    <div>
      {(() => {
        switch (phase) {
          case PHASES.MENU_PHASE:
            return <HomeMenu atEndMenuPhase={atEndMenuPhase} />;
          case PHASES.SETTING_PHASE:
            return (
              <>
                <GameRules />
                <SettingGrid atEndSettingPhase={atEndSettingPhase} />
              </>
            );
          case PHASES.PLAYING_PHASE:
            return (
              <>
                <PlayGame cells={playerCells} grid={playerGrid} playerId={playerId} />
              </>
            );
          default:
            return <>Default</>;
        }
      })()}
    </div>
  );
}

export default App;
