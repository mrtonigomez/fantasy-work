package com.example.scrapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "championships", nullable = false, length = 50)
    private float championships;

    @OneToMany(mappedBy = "team")
    @JsonProperty("player")
    private List<Player> players;

    @OneToMany(mappedBy = "local_team")
    @JsonProperty("local_team")
    private List<Game> local_team;

    @OneToMany(mappedBy = "visitant_team")
    @JsonProperty("visitant_team")
    private List<Game> visitant_team;

    @OneToMany(mappedBy = "loser_team_id")
    @JsonProperty("loser_team_id")
    private List<Game> loser_team_id;


    @OneToMany(mappedBy = "winner_team_id")
    @JsonProperty("winner_team_id")
    private List<Game> winner_team_id;

}
