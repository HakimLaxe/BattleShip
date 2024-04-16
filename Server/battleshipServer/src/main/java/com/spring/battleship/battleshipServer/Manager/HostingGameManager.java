package com.spring.battleship.battleshipServer.Manager;

import com.spring.battleship.battleshipServer.DTO.HostingGameDTO;
import com.spring.battleship.battleshipServer.Entity.HostingGame;
import com.spring.battleship.battleshipServer.Service.HostingGameService;
import com.spring.battleship.battleshipServer.Utility.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HostingGameManager {
    private RandomStringGenerator randomStringGenerator;
    private HostingGameService hostingGameService;

    @Autowired
    public HostingGameManager(RandomStringGenerator randomStringGenerator, HostingGameService hostingGameService) {
        this.randomStringGenerator = randomStringGenerator;
        this.hostingGameService = hostingGameService;
    }

    public String generatePlayerIdentifier(){
        String playerId;
        do{
            playerId = randomStringGenerator.generateRandomString(8);
        }
        while( hostingGameService.isPlayerIdAlreadyUsed(playerId) );
        return playerId;
    }

    public String generateGameIdentifier(){
        String gameId;
        do{
            gameId = randomStringGenerator.generateRandomString(5);
        }
        while( hostingGameService.isGameIdAlreadyUsed(gameId) );
        return gameId;
    }

    public void fillJoinedHostingGameDTO(HostingGameDTO hostingGameDTO, HostingGame updatedHostingGame){
        hostingGameDTO.setGameId(updatedHostingGame.getGameId());
        hostingGameDTO.setPlayer2(updatedHostingGame.getPlayer2());
        hostingGameDTO.setStartTime(updatedHostingGame.getStartTime());
    }

    public void saveCreatedHostingGame(HostingGameDTO hostingGameDTO){
        HostingGame hostingGame = new HostingGame(
                hostingGameDTO.getGameId(),
                hostingGameDTO.getPlayer1(),
                hostingGameDTO.getStartTime()
        );
        this.hostingGameService.saveHostingGame(hostingGame);
    }
    public HostingGameDTO createNewHostingGame(){
        HostingGameDTO hostingGameDTO = new HostingGameDTO();
        try{
            hostingGameDTO.setGameId( generateGameIdentifier() );
            hostingGameDTO.setPlayer1( generatePlayerIdentifier() );
            hostingGameDTO.setStartTime( LocalDateTime.now() );
            saveCreatedHostingGame(hostingGameDTO);
        }
        catch (Exception ex) {
            System.out.println("[HostingGameManager][createNewHostingGame] Exception: " + ex.getMessage());
        }
        return hostingGameDTO;
    }

    public HostingGameDTO joinNewHostingGame(String gameId){
        HostingGameDTO hostingGameDTO = new HostingGameDTO();
        try {
            if (hostingGameService.isGameIdAlreadyUsed(gameId)) {
                HostingGame updatedHostingGame = hostingGameService.updatePlayer2(gameId, generatePlayerIdentifier());
                if (updatedHostingGame.getGameId() != null) {
                    fillJoinedHostingGameDTO(hostingGameDTO, updatedHostingGame);
                }
            }
        }
        catch (Exception ex){
            System.out.println("[HostingGameManager][joinNewHostingGame] Exception: " + ex.getMessage());
        }
        return hostingGameDTO;
    }
}
