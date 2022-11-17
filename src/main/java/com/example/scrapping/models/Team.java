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

    @Column(name = "abrv", nullable = false, length = 100)
    private String abrv;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "championships", nullable = false, length = 50)
    private String championships;

    @OneToMany(mappedBy = "team")
    @JsonProperty("player")
    private List<Player> players;

    @OneToMany(mappedBy = "localTeamId")
    @JsonProperty("local_team_games")
    private List<Game> local_team_games;

    @OneToMany(mappedBy = "visitantTeamId")
    @JsonProperty("visitant_team_games")
    private List<Game> visitant_team_games;

    @OneToMany(mappedBy = "loser_team")
    @JsonProperty("loser_team_games")
    private List<Game> loser_team_games;

    @OneToMany(mappedBy = "winner_team")
    @JsonProperty("winner_team_games")
    private List<Game> winner_team_games;

}
