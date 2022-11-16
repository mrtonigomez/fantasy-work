package com.example.scrapping.repository;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    public Game findByDate(Timestamp date);

    /*@Query(nativeQuery = true, value = "SELECT * FROM game where ")
    Game findByVisitant_team(Long teamVisitantId);*/

}
