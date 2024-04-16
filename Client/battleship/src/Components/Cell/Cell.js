import React, { useEffect, useState } from 'react';
import './Cell.css'

const Cell = ({ cellId, type, text, rotation, imageUrl, manageCellClick }) => {

    const getClassName = (type) => {
        switch (type) {
            case 'A1': return 'cell cell-aircraft';
            case 'A0': return 'cell cell-aircraft cell-attacked';
            case 'B1': return 'cell cell-battleship';
            case 'B0': return 'cell cell-battleship cell-attacked';
            case 'C1': return 'cell cell-cruiser';
            case 'C0': return 'cell cell-cruiser cell-attacked';
            case 'S1': return 'cell cell-submariner';
            case 'S0': return 'cell cell-submariner cell-attacked';
            case 'D1': return 'cell cell-destroyer';
            case 'D0': return 'cell cell-destroyer cell-attacked';
            case 'O1': return 'cell';
            case 'X0': return 'cell cell-attacked';
            case 'Y0': return 'cell ship-cell-attacked cell-attacked';
            case 'K1': return 'cell clickable-cell';
            case 'K0': return 'cell clickable-cell cell-clicked';
            case 'header': return 'cell cell-header';
            default: return 'cell';
        }
    };

    const getRotation = () => {
        if (type === 'Y0')
            return '0'
        return rotation
    }

    const getText = (text, type) => {
        if (['AO', 'B0', 'C0', 'D0', 'S0', 'X0', 'Y0'].includes(type)) {
            return "❌"
        }
        return text
    }

    const getImage = () => {
        return type === 'Y0' ? `url("/images/sunk-ship.png")` : `url(${imageUrl})`
    }

    const onCellClick = () => {
        manageCellClick(cellId, type)
    }
    return (
        <div
            className={getClassName(type)}
            key={cellId}
            onClick={() => onCellClick()}
            style={{
                backgroundImage: getImage(),
                backgroundSize: "cover",
                backgroundRepeat: "no-repeat",
                backgroundPosition: "center",
                transform: `rotate(${getRotation()}deg)`
            }}
        >
            {getText(text, type)}
        </div>
    );
}
export default Cell;