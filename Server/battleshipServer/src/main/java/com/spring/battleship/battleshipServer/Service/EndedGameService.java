package com.spring.battleship.battleshipServer.Service;

import com.spring.battleship.battleshipServer.Entity.EndedGame;
import com.spring.battleship.battleshipServer.Entity.EndedGameId;
import com.spring.battleship.battleshipServer.Entity.LiveGame;
import com.spring.battleship.battleshipServer.Repository.EndedGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndedGameService {
    private final EndedGameRepository endedGameRepository;

    public EndedGameService(EndedGameRepository endedGameRepository) {
        this.endedGameRepository = endedGameRepository;
    }
    public EndedGame saveEndedGame(EndedGame endedGame){
        return endedGameRepository.save(endedGame);
    }

    public List<EndedGame> findEndedGameByPlayerId(String playerId){
        return this.endedGameRepository.findByPlayerId(playerId);
    }
}
