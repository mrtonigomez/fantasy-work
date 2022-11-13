package com.example.scrapping.repository;

import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
