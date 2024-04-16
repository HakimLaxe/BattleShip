package com.spring.battleship.battleshipServer.Manager;

import com.spring.battleship.battleshipServer.DTO.SendUpdateDTO;
import com.spring.battleship.battleshipServer.Entity.EndedGame;
import com.spring.battleship.battleshipServer.Entity.EndedGameId;
import com.spring.battleship.battleshipServer.Entity.LiveGame;
import com.spring.battleship.battleshipServer.Service.EndedGameService;
import com.spring.battleship.battleshipServer.Service.HostingGameService;
import com.spring.battleship.battleshipServer.Service.LiveGameService;
import com.spring.battleship.battleshipServer.Utility.GridUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EndedGameManager {
    @Autowired
    GridUtils gridUtils;

    @Autowired
    EndedGameService endedGameService;

    @Autowired
    HostingGameService hostingGameService;

    @Autowired
    LiveGameService liveGameService;

    public EndedGame retriveEndedGame(String playerId){
        List<EndedGame> endedGames = endedGameService.findEndedGameByPlayerId(playerId);
        if ( endedGames.isEmpty() ){
            return null;
        }
        return endedGames.get(0);
    }
    public boolean isGameEnded(LiveGame liveGame){
        String grid1 = liveGame.getPlayerGrid1();
        String grid2 = liveGame.getPlayerGrid2();
        String[][] matrixGrid1 = gridUtils.convertJsonToMatrix(grid1);
        String[][] matrixGrid2 = gridUtils.convertJsonToMatrix(grid2);
        return gridUtils.areShipsDestroyed(matrixGrid1) ||  gridUtils.areShipsDestroyed(matrixGrid2);
    }
    public String findWinner(String playerId, LiveGame liveGame){
        String grid1 = liveGame.getPlayerGrid1();
        String grid2 = liveGame.getPlayerGrid2();
        String[][] matrixGrid1 = gridUtils.convertJsonToMatrix(grid1);
        String[][] matrixGrid2 = gridUtils.convertJsonToMatrix(grid2);
        String winner = null;
        if ( gridUtils.areShipsDestroyed(matrixGrid1) ){
            winner = liveGame.getPlayer2();
        }
        else if ( gridUtils.areShipsDestroyed(matrixGrid2) ){
            winner = liveGame.getPlayer1();
        }
        return winner;
    }

    public SendUpdateDTO fillResponseFromEndedGame(String playerId, EndedGame endedGame){
        SendUpdateDTO sendUpdateDTO = new SendUpdateDTO();
        sendUpdateDTO.setGameEnded(true);
        sendUpdateDTO.setWinner(endedGame.getWinner());
        String message = playerId.equals(endedGame.getWinner()) ? "You Win!" : "You Lose!";
        sendUpdateDTO.setMessage(message);
        return sendUpdateDTO;
    }

    public SendUpdateDTO fillResponseFromLiveGame(String playerId, LiveGame liveGame){
        SendUpdateDTO sendUpdateDTO = new SendUpdateDTO();
        sendUpdateDTO.setGameEnded(true);
        sendUpdateDTO.setWinner(findWinner(playerId, liveGame));
        if ( playerId.equals(sendUpdateDTO.getWinner()) )
            sendUpdateDTO.setMessage("You Win!");
        else
            sendUpdateDTO.setMessage("You Lose!");

        return sendUpdateDTO;
    }

    public boolean performCrudOperations(String playerId, LiveGame liveGame){
        if ( insertEndedGame(playerId, liveGame) ){
            return deleteLiveAndHostingGame(liveGame.getGameId());
        }
        return false;
    }

    private boolean deleteLiveAndHostingGame(String gameId){
        boolean res = false;
        try {
            res =
                    hostingGameService.deleteHostingGameByGameId(gameId)
                            &&
                    liveGameService.deleteLiveGameByGameId(gameId);
        }
        catch (DataAccessException ex){
            System.out.println("[EndedGameManager][deleteLiveAndHostingGame] Exception: " + ex.getMessage());
            return false;
        }
        return res;
    }
    private boolean insertEndedGame(String playerId, LiveGame liveGame){
        EndedGameId endedGameId = new EndedGameId(liveGame.getGameId(), liveGame.getStartTime() );
        EndedGame endedGame = new EndedGame(endedGameId);
        endedGame.setPlayer1(liveGame.getPlayer1());
        endedGame.setPlayer2(liveGame.getPlayer2());
        endedGame.setEndTime(LocalDateTime.now());
        endedGame.setWinner(findWinner(playerId, liveGame));
        return performInsert(endedGame);
    }

    private boolean performInsert(EndedGame endedGame){
        try {
            this.endedGameService.saveEndedGame(endedGame);
        }
        catch (DataAccessException ex){
            System.out.println("[EndedGameManager][performInsert] Exception: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
