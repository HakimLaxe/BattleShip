import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import { SERVER_ENDPOINT, FIRST_TURN_MESSAGE } from '../contants/constants'
import { convertCoordinate } from '../Utils/gridConverter'

export function setupMocks() {
    let firstMove = true
    let grids = []
    let lastMove = {}
    const mock = new MockAdapter(axios);


    mock.onPost(`${SERVER_ENDPOINT}/confirm-grid`).reply(config => {
        const data = JSON.parse(config.data);
        grids.push({ playerId: data.playerId, grid: data.grid })
        console.log(data.grid)
        return [200, { message: 'grid confirmed' }];
    });

    mock.onPost(`${SERVER_ENDPOINT}/get-update`).reply(config => {
        const data = JSON.parse(config.data);
        let playerId = data.playerId
        let returnMessage;
        if (firstMove) {
            firstMove = false;
            return [200, { message: FIRST_TURN_MESSAGE }];
        }
        if (lastMove !== {} && playerId !== lastMove.playerId) {
            returnMessage = lastMove.attackedCell
            return [200, { message: returnMessage }];
        }
        return [200, { message: 'No update' }];
    });


    mock.onPost(`${SERVER_ENDPOINT}/attack-cell`).reply(config => {

        const data = JSON.parse(config.data);
        let attackedPlayerId =
            data.playerId === grids[0].playerId ? grids[1].playerId : grids[0].playerId;
        let [row, col] = convertCoordinate(data.cellId)
        console.log('attacked cell ' + data.cellId)
        if (grids[row][col] === 'K1')
            grids[row][col] = 'X0'
        else if (grids[row][col] !== 'X0') {
            grids[row][col] = grids[row][col][0] + '0'
        }
        lastMove = { playerId: data.playerId, attackedCell: data.cellId }
        return [200, { playerId: 'Attack Performed' }];
    });

    mock.onPost(`${SERVER_ENDPOINT}/join-game`).reply(config => {

        const data = JSON.parse(config.data);
        if (data.gameId === 'A1B2C')
            return [200, { playerId: 'D9EF812X' }];
        else
            return [404, { message: 'Invalid GameId' }];
    });

    mock.onPost(`${SERVER_ENDPOINT}/create-game`).reply(config => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                const minutes = new Date().getMinutes();
                const isEven = minutes % 2 === 0;

                if (isEven)
                    resolve([200, { playerId: 'H5JX98W1' }]);
                else
                    resolve([500, { message: 'Server Error during Game Creation' }]);
            }, 2000);
        });
    });

}