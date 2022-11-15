package com.example.scrapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "player_stats")
@Getter
@Setter
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "player_id", nullable = true)
    @JsonProperty("player")
    private Player player;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "game_id", nullable = true)
    @JsonProperty("game_id")
    private Game game;

    @Column(name = "minutes", nullable = false, length = 50)
    private String minutes;

    @Column(name = "points", nullable = false, length = 1)
    private int points;

    @Column(name = "rebounds", nullable = false, length = 50)
    private int rebounds;

    @Column(name = "assists", nullable = false, length = 50)
    private int assists;

    @Column(name = "steals", nullable = false, length = 50)
    private int steals;

    @Column(name = "blocks", nullable = false, length = 50)
    private int blocks;

    @Column(name = "turnovers", nullable = false, length = 50)
    private int turnovers;

    @Column(name = "fgm", nullable = false, length = 50)
    private int fgm;

    @Column(name = "fga", nullable = false, length = 50)
    private int fga;

    @Column(name = "fg3m", nullable = false, length = 50)
    private int fg3m;

    @Column(name = "fg3a", nullable = false, length = 50)
    private int fg3a;

    @Column(name = "ftm", nullable = false, length = 50)
    private int ftm;

    @Column(name = "fta", nullable = false, length = 50)
    private int fta;

    @Column(name = "foults", nullable = false, length = 50)
    private int foults;

    @Column(name = "rating", nullable = false, length = 50)
    private int rating;

}
