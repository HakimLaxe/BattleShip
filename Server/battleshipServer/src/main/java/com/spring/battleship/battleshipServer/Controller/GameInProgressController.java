package com.spring.battleship.battleshipServer.Controller;

import com.spring.battleship.battleshipServer.DTO.*;
import com.spring.battleship.battleshipServer.Entity.EndedGame;
import com.spring.battleship.battleshipServer.Entity.LiveGame;
import com.spring.battleship.battleshipServer.Manager.EndedGameManager;
import com.spring.battleship.battleshipServer.Manager.LiveGameManager;
import com.spring.battleship.battleshipServer.Utility.GridUtils;
import com.spring.battleship.battleshipServer.Utility.GridValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/game-in-progress")
public class GameInProgressController {
    @Autowired
    private GridValidator gridValidator;
    @Autowired
    private GridUtils gridUtils;
    @Autowired
    private LiveGameManager liveGameManager;

    @Autowired
    private EndedGameManager endedGameManager;
    @RequestMapping(
            path = "/confirm-grid",
            method = {RequestMethod.POST}
    )
    public ResponseEntity<Boolean> confirmGrid(@RequestBody LiveGameDTO requestLiveGameDTO){
        boolean result = false;
        String playerId = requestLiveGameDTO.getPlayerId();
        String playerGrid = requestLiveGameDTO.getPlayerGrid();

        String[][] gridMatrix = gridUtils.convertJsonToMatrix(playerGrid);
        if ( gridValidator.validateGrid(gridMatrix) ){
            result = liveGameManager.updateLiveGame(playerId, playerGrid);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(
            path = "/get-update",
            method = {RequestMethod.POST}
    )
    public ResponseEntity<SendUpdateDTO> sendUpdate(@RequestBody LiveGameDTO liveGameDTO){

        String playerId = liveGameDTO.getPlayerId();
        EndedGame endedGame = endedGameManager.retriveEndedGame(playerId);

        if ( endedGame != null ){
            SendUpdateDTO sendUpdateDTO = endedGameManager.fillResponseFromEndedGame(playerId, endedGame);
            return ResponseEntity.ok(sendUpdateDTO);
        }

        LiveGame liveGame = liveGameManager.getLiveGameByPlayerId(playerId);

        if ( liveGame == null ){
            return ResponseEntity.badRequest().body(new SendUpdateDTO("Invalid PlayerId",false,""));
        }

        if ( endedGameManager.isGameEnded(liveGame) ){
            if ( !endedGameManager.performCrudOperations(playerId, liveGame) )
                return ResponseEntity.internalServerError().body(new SendUpdateDTO());

            SendUpdateDTO sendUpdateDTO = endedGameManager.fillResponseFromLiveGame(playerId,liveGame);
            return ResponseEntity.ok(sendUpdateDTO);
        }

        SendUpdateDTO sendUpdateDTO = liveGameManager.fillResponse(playerId,liveGame);
        if ( sendUpdateDTO == null ){
            return ResponseEntity.internalServerError().body(new SendUpdateDTO());
        }
        return ResponseEntity.ok(sendUpdateDTO);
    }

    @RequestMapping(
            path = "/attack-cell",
            method = {RequestMethod.POST}
    )
    public ResponseEntity<AttackResponseDTO> attackCell(@RequestBody AttackRequestDTO attackRequestDTO){
        String playerId = attackRequestDTO.getPlayerId();
        String attackedCell = attackRequestDTO.getAttackedCell();
        LiveGame liveGame = liveGameManager.getLiveGameByPlayerId(playerId);
        if ( liveGame == null ){
            return ResponseEntity.badRequest().body(new AttackResponseDTO());
        }

        AttackResponseDTO attackResponseDTO = liveGameManager.fillAttackResponse(playerId, attackedCell, liveGame);
        if ( attackResponseDTO == null ){
            return ResponseEntity.internalServerError().body(attackResponseDTO);
        }
        return ResponseEntity.ok(attackResponseDTO);
    }

}
