package com.spring.battleship.battleshipServer.Service;

import com.spring.battleship.battleshipServer.Entity.HostingGame;
import com.spring.battleship.battleshipServer.Repository.HostingGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HostingGameService {
    private final HostingGameRepository hostingGameRepository;

    @Autowired
    public HostingGameService(HostingGameRepository hostingGameRepository) {
        this.hostingGameRepository = hostingGameRepository;
    }

    public boolean isGameIdAlreadyUsed(String gameId){
        return this.hostingGameRepository.findOccurencesByGameId(gameId) == 1;
    }

    public boolean isPlayerIdAlreadyUsed(String playerId){
        return this.hostingGameRepository.findOccurencesByPlayerId(playerId) == 1;
    }
    public  Optional<HostingGame> retriveHostingGameByGameId(String gameId){
        return this.hostingGameRepository.findByGameId(gameId);
    }
    public HostingGame updatePlayer2(String gameId, String player2){
        Optional<HostingGame> hostingGameOpt = this.hostingGameRepository.findByGameIdWherePlayer2IsNull(gameId);
        if (hostingGameOpt.isPresent()) {
            HostingGame hostingGame = hostingGameOpt.get();
            hostingGame.setPlayer2(player2);
            try {
                HostingGame insertedHostingGame = hostingGameRepository.save(hostingGame);
                return insertedHostingGame;
            } catch (DataAccessException ex) {
                System.out.println("[HostingGameService][updatePlayer2] Exception: " +ex.getMessage());
            }
        }
        return new HostingGame();
    }

    public boolean deleteHostingGameByGameId(String gameId){
        return this.hostingGameRepository.deleteByGameId(gameId) == 1;
    }
    public HostingGame saveHostingGame(HostingGame hostingGame){
        return this.hostingGameRepository.save(hostingGame);
    }
}
