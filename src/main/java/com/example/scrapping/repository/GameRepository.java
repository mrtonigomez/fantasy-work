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

    Game findByVisitantTeamIdAndLocalTeamIdAndDate(Team visitant_team, Team local_team, Timestamp date);

}
