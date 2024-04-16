import axios from 'axios';
import { SERVER_ENDPOINT } from '../contants/constants'

export async function checkNextMove(playerId) {
    try {
        const response = await axios.post(`${SERVER_ENDPOINT}/game-in-progress/get-update`, {
            playerId
        });
        if (response.status !== 200) {
            console.error('Error Status:', response.status);
        }

        return response;
    } catch (error) {
        if (error.response) {
            console.error('Request Error:', error.response.data);
            return error.response;
        } else {
            console.error('Error:', error.message);
            return { data: null, status: 'Network Error or Server Unreachable' };
        }
    }
}

export async function attackCell(playerId, cellId) {
    try {
        const response = await axios.post(`${SERVER_ENDPOINT}/game-in-progress/attack-cell`, {
            playerId, attackedCell: cellId
        });

        if (response.status !== 200) {
            console.error('Error Status:', response.status);
        }

        return response;
    } catch (error) {
        console.error('Request Error:', error.response.data);
        return error.response;
    }
}

export async function sendGrid(playerId, grid) {
    const jsonGrid = JSON.stringify(grid);
    try {
        const response = await axios.post(`${SERVER_ENDPOINT}/game-in-progress/confirm-grid`, {
            playerId, playerGrid: jsonGrid
        });

        if (response.status !== 200) {
            console.error('Error Status:', response.status);
        }

        return response;

    }
    catch (error) {
        if (error.response) {
            console.error('Request Error:', error.response.data);
            return error.response;
        } else if (error.request) {
            console.error('Request was made but no response was received');
        } else {
            console.error('Error', error.message);
        }
        return { data: null, error: error };
    }
}

export async function isGameReady(gameId, playerId) {
    try {
        const response = await axios.post(
            `${SERVER_ENDPOINT}/game-booting/is-game-ready`,
            { gameId, player1: playerId }
        );

        if (response.status === 200) {
            return response
        } else {
            console.error('Error Status:', response.status);
        }

        return response;
    } catch (error) {
        if (error.response) {
            console.error('Request Error:', error.response.data);
            return error.response;
        } else {
            console.error('Error:', error.message);
            return { data: null, status: 'Network Error or Server Unreachable' };
        }
    }
}


export async function joinGame(gameId) {
    try {
        const response = await axios.post(
            `${SERVER_ENDPOINT}/game-booting/join-game`,
            { gameId }
        );

        if (response.status === 200) {
            return response
        } else {
            console.error('Error Status:', response.status);
        }

        return response;
    } catch (error) {
        if (error.response) {
            console.error('Request Error:', error.response.data);
            return error.response;
        } else {
            console.error('Error:', error.message);
            return { data: null, status: 'Network Error or Server Unreachable' };
        }
    }
}


export async function createGame() {
    try {
        const response = await axios.post(`${SERVER_ENDPOINT}/game-booting/create-game`);

        if (response.status === 200) {
            return response
        } else {
            console.error('Error Status:', response.status);
        }

        return response;
    } catch (error) {
        console.error('Request Error:', error);
        return error.response;
    }
}