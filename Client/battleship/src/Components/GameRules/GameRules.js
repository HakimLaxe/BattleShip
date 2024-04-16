import React from 'react';
import './GameRules.css';

const GameRules = () => {
    return (
        <div className="gameRules">
            <p>Place ships:</p>
            <p>Aircraft (5 cells)</p>
            <p>Battleship (4 cells)</p>
            <p>Cruiser (3 cells)</p>
            <p>Submariner (3 cells)</p>
            <p>Destroyer (2 cells)</p>
        </div>
    );
}

export default GameRules;
