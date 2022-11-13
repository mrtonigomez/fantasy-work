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
    @JsonProperty("user")
    private Player player;

    //game_id

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

    @Column(name = "foults", nullable = false, length = 50)
    private int foults;

    @Column(name = "rating", nullable = false, length = 50)
    private int rating;

}
