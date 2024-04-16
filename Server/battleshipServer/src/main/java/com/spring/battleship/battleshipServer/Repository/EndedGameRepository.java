package com.spring.battleship.battleshipServer.Repository;

import com.spring.battleship.battleshipServer.Entity.EndedGame;
import com.spring.battleship.battleshipServer.Entity.EndedGameId;
import com.spring.battleship.battleshipServer.Entity.LiveGame;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface EndedGameRepository extends JpaRepository<EndedGame, EndedGameId> {

    @Query("SELECT eg FROM EndedGame eg WHERE eg.player1 = ?1 OR eg.player2 = ?1 ORDER BY eg.endTime")
    List<EndedGame> findByPlayerId(String playerId);
}
