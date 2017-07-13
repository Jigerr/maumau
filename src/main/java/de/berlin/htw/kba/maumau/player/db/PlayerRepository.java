package de.berlin.htw.kba.maumau.player.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    
    @Query(value = "select * from player where game_table_id = ?1", nativeQuery = true)
    List<Player> findByGameTableId(Integer gameTableId);

}
