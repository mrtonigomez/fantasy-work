package com.example.scrapping.repository;

import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String name);

    Player findByUrl(String url);

    List<Player> findByTeam(Team team);

}
