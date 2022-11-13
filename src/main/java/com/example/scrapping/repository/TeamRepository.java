package com.example.scrapping.repository;

import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
