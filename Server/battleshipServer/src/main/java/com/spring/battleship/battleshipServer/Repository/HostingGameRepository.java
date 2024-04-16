package com.spring.battleship.battleshipServer.Repository;

import com.spring.battleship.battleshipServer.Entity.HostingGame;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface HostingGameRepository extends JpaRepository<HostingGame, String>  {

    @Query("SELECT hg FROM HostingGame hg WHERE hg.gameId = ?1 AND player2 IS NULL")
    Optional<HostingGame> findByGameIdWherePlayer2IsNull(String gameId);

    @Query("SELECT hg FROM HostingGame hg WHERE hg.gameId = ?1")
    Optional<HostingGame> findByGameId(String gameId);
    @Query("SELECT COUNT(hg) FROM HostingGame hg WHERE hg.gameId = ?1 AND hg.player1 = ?2")
    long findOccurencesByGameIdAndPlayerId(String gameId, String playerId);
    @Query("SELECT COUNT(hg) FROM HostingGame hg WHERE hg.gameId = ?1")
    long findOccurencesByGameId(String gameId);

    @Query("SELECT COUNT(hg) FROM HostingGame hg WHERE hg.player1 = ?1 OR hg.player2 = ?1")
    long findOccurencesByPlayerId(String playerId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM HostingGame hg WHERE hg.gameId = ?1")
    int deleteByGameId(String gameId);
}
