package com.spring.battleship.battleshipServer.Manager;

import com.spring.battleship.battleshipServer.Constant.ShipConstants;
import com.spring.battleship.battleshipServer.DTO.AttackResponseDTO;
import com.spring.battleship.battleshipServer.DTO.SendUpdateDTO;
import com.spring.battleship.battleshipServer.Entity.HostingGame;
import com.spring.battleship.battleshipServer.Entity.LiveGame;
import com.spring.battleship.battleshipServer.Service.HostingGameService;
import com.spring.battleship.battleshipServer.Service.LiveGameService;
import com.spring.battleship.battleshipServer.Utility.GridUtils;
import com.spring.battleship.battleshipServer.Utility.GridValidator;
import com.spring.battleship.battleshipServer.Utility.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import java.util.Optional;

@Component
public class LiveGameManager {
    private LiveGameService liveGameService;
    private HostingGameService hostingGameService;

    private RandomStringGenerator randomStringGenerator;

    private GridUtils gridUtils;

    @Autowired
    private GridValidator gridValidator;

    @Autowired
    private ShipConstants ships;
    @Autowired
    public LiveGameManager(LiveGameService liveGameService, HostingGameService hostingGameService, RandomStringGenerator randomStringGenerator, GridUtils gridUtils){
        this.liveGameService = liveGameService;
        this.hostingGameService = hostingGameService;
        this.randomStringGenerator = randomStringGenerator;
        this.gridUtils = gridUtils;
    }

    public AttackResponseDTO fillAttackResponse(String playerId, String attackedCell, LiveGame liveGame){
        AttackResponseDTO attackResponseDTO = new AttackResponseDTO();
        if ( !isPlayerTurn(playerId, liveGame) || attackedCell == null || attackedCell.isBlank() ){
            attackResponseDTO.setValid(false);
        }
        else {
            String grid = playerId.equals(liveGame.getPlayer1()) ? liveGame.getPlayerGrid2() : liveGame.getPlayerGrid1();
            String[][] matrixGrid = gridUtils.convertJsonToMatrix(grid);
            int[] coordinate = gridUtils.convertCoordinate(attackedCell);
            int row = coordinate[0];
            int col = coordinate[1];
            if ( !gridValidator.isValidAttack(matrixGrid[row][col]) )
                attackResponseDTO.setValid(false);

            else {
                boolean isShotted = gridUtils.changeCell(matrixGrid, row, col);
                if ( updateLiveGameAfterAttack(liveGame, playerId, matrixGrid, attackedCell) )
                    fillAttackResponse(attackResponseDTO, isShotted, attackedCell);
            }
        }
        return attackResponseDTO;
    }

    private void fillAttackResponse(AttackResponseDTO attackResponseDTO, boolean isShotted, String attackedCell){
        attackResponseDTO.setValid(true);
        attackResponseDTO.setShotted(isShotted);
        attackResponseDTO.setAttackCell(attackedCell);
    }

    private boolean updateLiveGameAfterAttack(LiveGame liveGame, String playerId, String[][] grid, String attackedCell){
        Gson gson = new Gson();
        String jsonGrid = gson.toJson(grid);
        liveGame.setNumberOfTurns( liveGame.getNumberOfTurns() + 1 );
        liveGame.setLastAttack( playerId.concat("-").concat(attackedCell));
        if ( playerId.equals(liveGame.getPlayer1()) ){
            liveGame.setPlayerGrid2(jsonGrid);
        }
        else{
            liveGame.setPlayerGrid1(jsonGrid);
        }
        return performInsert(liveGame);
    }

    public SendUpdateDTO fillResponse(String playerId, LiveGame liveGame){
        try{
            SendUpdateDTO sendUpdateDTO = new SendUpdateDTO();
            setTurnPlayer(playerId, liveGame, sendUpdateDTO);
            setMessage(playerId, liveGame, sendUpdateDTO);
            setLastAttack(playerId, liveGame, sendUpdateDTO);
            return sendUpdateDTO;
        }
        catch (Exception ex){
            System.out.println("[LiveGameManager][fillResponse] Exception: " + ex.getMessage());
        }
        return null;
    }
    public LiveGame getLiveGameByPlayerId(String playerId){
        return this.liveGameService.findLiveGameByPlayerId(playerId).get();
    }
    private boolean isPlayerTurn(String playerId, LiveGame liveGame){
        if (
                ( liveGame.getFirstTurn().equals(playerId) && liveGame.getNumberOfTurns() % 2 == 0 ) ||
                ( !liveGame.getFirstTurn().equals(playerId) && liveGame.getNumberOfTurns() % 2 != 0 )
        )
            return true;

        return false;
    }
    private void setTurnPlayer(String playerId, LiveGame liveGame, SendUpdateDTO sendUpdateDTO){
        if ( isPlayerTurn(playerId, liveGame) )
            sendUpdateDTO.setYourTurn(true);
        else
            sendUpdateDTO.setYourTurn(false);
    }
    private void setMessage(String playerId, LiveGame liveGame, SendUpdateDTO sendUpdateDTO){
        if ( liveGame.getNumberOfTurns() == 0 && isPlayerTurn(playerId,liveGame) ){
            sendUpdateDTO.setMessage("You won the coin toss! First turn is yours");
        }
        else if ( liveGame.getNumberOfTurns() == 0 && !isPlayerTurn(playerId,liveGame) ){
            sendUpdateDTO.setMessage("You lost the coin toss! Waiting for your opponent turn");
        }
        else if ( isPlayerTurn(playerId,liveGame)
                && liveGame.getLastAttack() != null
                && !liveGame.getLastAttack().isBlank() ){
            String attackedCell = liveGame.getLastAttack().split("-")[1];
            sendUpdateDTO.setMessage("Your Opponent Attacked ".concat(attackedCell).concat(". Now it's your turn!"));
        }
        else if ( !isPlayerTurn(playerId,liveGame) ){
            sendUpdateDTO.setMessage("Wait for your opponent attack");
        }

    }
    private void setLastAttack(String playerId, LiveGame liveGame, SendUpdateDTO sendUpdateDTO){
        if ( isPlayerTurn(playerId,liveGame) && liveGame.getNumberOfTurns() > 0 ){
            if ( liveGame.getLastAttack() != null && liveGame.getLastAttack().contains("-")) {
                String[] fields = liveGame.getLastAttack().split("-");
                String attackerPlayerId = fields[0];
                String attackedCell = fields[1];
                sendUpdateDTO.setLastAttack(attackerPlayerId.equals(playerId) ? "" : attackedCell);
            }
        }
    }
    private String decideStartingPlayer(String player1, String player2){
        return randomStringGenerator.tossACoin() == 0 ? player1 : player2;
    }

    public boolean updateLiveGame(String playerId, String playerGrid){
        Optional<LiveGame> optionalLiveGame = this.liveGameService.findLiveGameByPlayerId(playerId);
        if ( optionalLiveGame.isEmpty() ){
            System.out.println("[LiveGameManager][updateLiveGame] Invalid PlayerId");
            return false;
        }
        LiveGame liveGame = optionalLiveGame.get();

        if ( playerId.equals(liveGame.getPlayer1()) )
            liveGame.setPlayerGrid1(playerGrid);
        else
            liveGame.setPlayerGrid2(playerGrid);

        if ( liveGame.getPlayerGrid1() != null && liveGame.getPlayerGrid2() != null ){
            liveGame.setFirstTurn( decideStartingPlayer(liveGame.getPlayer1(),liveGame.getPlayer2() ) );
            liveGame.setNumberOfTurns(0);
        }

        return performInsert(liveGame);
    }

    public boolean insertLiveGame(String gameId){
        Optional<HostingGame> optionalHostingGame = this.hostingGameService.retriveHostingGameByGameId(gameId);
        if ( optionalHostingGame.isEmpty() ){
            System.out.println("[LiveGameManager][insertLiveGame] Invalid GameId");
            return false;
        }
        HostingGame hostingGame = optionalHostingGame.get();
        LiveGame liveGame = new LiveGame(
                hostingGame.getGameId(),
                hostingGame.getPlayer1(),
                hostingGame.getPlayer2(),
                null,
                null,
                hostingGame.getStartTime()
        );

        return performInsert(liveGame);
    }

    public boolean checkIfGameIsReady(String gameId, String playerId){
        if ( this.liveGameService.checkIfLiveGameExistByGameIdAndPlayerId(gameId,playerId) ) {
            return true;
        }
        return false;
    }

    private boolean performInsert(LiveGame liveGame){
        try {
            this.liveGameService.saveLiveGame(liveGame);
        }
        catch (DataAccessException ex){
            System.out.println("[LiveGameManager][insertLiveGame] Exception: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
