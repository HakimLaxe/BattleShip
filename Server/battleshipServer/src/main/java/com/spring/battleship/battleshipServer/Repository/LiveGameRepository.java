package com.spring.battleship.battleshipServer.Repository;

import com.spring.battleship.battleshipServer.Entity.LiveGame;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface LiveGameRepository extends JpaRepository<LiveGame, String> {
    @Query("SELECT lg FROM LiveGame lg WHERE lg.player1 = ?1 OR lg.player2 = ?1")
    Optional<LiveGame> findByPlayerId(String playerId);
    @Query("SELECT COUNT(lg) FROM LiveGame lg WHERE lg.gameId = ?1 AND lg.player1 = ?2")
    long findOccurencesByGameIdAndPlayerId(String gameId, String playerId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM LiveGame lg WHERE lg.gameId = ?1")
    int deleteByGameId(String gameId);
}
