package com.example.scrapping.repository;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
