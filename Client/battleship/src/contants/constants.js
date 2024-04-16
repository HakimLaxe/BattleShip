export const SERVER_ENDPOINT = 'http://localhost:8080';
export const ENV = 'SVIL';
export const SHIPS = [
    { "name": "Aircraft", "length": 5 },
    { "name": "Battleship", "length": 4 },
    { "name": "Cruiser", "length": 3 },
    { "name": "Submariner", "length": 3 },
    { "name": "Destroyer", "length": 2 }
];
export const GRID_SIZE = 100;
export const ROWS = ['', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
export const COLS = ['', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'];
export const PHASES = { MENU_PHASE: 0, SETTING_PHASE: 1, PLAYING_PHASE: 2 };
export const TURN = { YOUR_TURN: 0, ENEMY_TURN: 1 }
export const NO_UPDATE_MESSAGE = 'No update'
export const FIRST_TURN_MESSAGE = 'You have to make first turn'