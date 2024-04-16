package com.spring.battleship.battleshipServer.Controller;

import com.spring.battleship.battleshipServer.DTO.HostingGameDTO;
import com.spring.battleship.battleshipServer.Manager.HostingGameManager;
import com.spring.battleship.battleshipServer.Manager.LiveGameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/game-booting")

public class GameBootingController {

    @Autowired
    private HostingGameManager hostingGameManager;
    @Autowired
    private LiveGameManager liveGameManager;
    @RequestMapping(
            path = "/create-game",
            method = {RequestMethod.POST}
    )
    public ResponseEntity<HostingGameDTO> manageGameCreation(){

        HostingGameDTO hostingGameDTO = this.hostingGameManager.createNewHostingGame();
        if ( hostingGameDTO.isInvalidCreatedGame() ){
            return ResponseEntity.internalServerError().body(new HostingGameDTO());
        }
        return ResponseEntity.ok(hostingGameDTO);
    }

    @RequestMapping(
            path = "/join-game",
            method = {RequestMethod.POST}
    )
    public ResponseEntity<HostingGameDTO> manageJoinGame(@RequestBody HostingGameDTO requestHostingGameDTO){
        String gameId = requestHostingGameDTO.getGameId();
        HostingGameDTO hostingGameDTO = this.hostingGameManager.joinNewHostingGame(gameId);
        if ( hostingGameDTO.isInvalidJoinableGame() ){
            return ResponseEntity.internalServerError().body(new HostingGameDTO());
        }
        this.liveGameManager.insertLiveGame(hostingGameDTO.getGameId());
        return ResponseEntity.ok(hostingGameDTO);
    }

    @RequestMapping(
            path = "/is-game-ready",
            method = {RequestMethod.POST}
    )
    public ResponseEntity<Boolean> manageIsGameReady(@RequestBody HostingGameDTO requestHostingGameDTO){
        String gameId = requestHostingGameDTO.getGameId();
        String playerId = requestHostingGameDTO.getPlayer1();
        boolean res = this.liveGameManager.checkIfGameIsReady(gameId,playerId);

        return ResponseEntity.ok(res);
    }
}
