package com.spring.battleship.battleshipServer.Service;

import com.spring.battleship.battleshipServer.Entity.LiveGame;
import com.spring.battleship.battleshipServer.Repository.LiveGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LiveGameService {
    private final LiveGameRepository liveGameRepository;

    @Autowired
    public LiveGameService(LiveGameRepository liveGameRepository){
        this.liveGameRepository = liveGameRepository;
    }

    public LiveGame saveLiveGame(LiveGame liveGame){
        return liveGameRepository.save(liveGame);
    }

    public boolean checkIfLiveGameExistByGameIdAndPlayerId(String gameId, String playerId){
        return this.liveGameRepository.findOccurencesByGameIdAndPlayerId(gameId,playerId) == 1;
    }

    public Optional<LiveGame> findLiveGameByPlayerId(String playerId){
        return this.liveGameRepository.findByPlayerId(playerId);
    }
    public boolean deleteLiveGameByGameId(String gameId){
        return this.liveGameRepository.deleteByGameId(gameId) == 1;
    }
}
