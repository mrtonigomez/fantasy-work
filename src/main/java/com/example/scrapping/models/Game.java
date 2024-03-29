package com.example.scrapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "games")
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false, length = 100)
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "local_team_id", nullable = true)
    @JsonProperty("local_team_id")
    private Team localTeamId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "visitant_team_id", nullable = true)
    @JsonProperty("visitant_team_id")
    private Team visitantTeamId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "winner_team_id", nullable = true)
    @JsonProperty("winner_team_id")
    private Team winner_team;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "loser_team_id", nullable = true)
    @JsonProperty("loser_team_id")
    private Team loser_team;

    @Column(name = "points_team_local", nullable = true, length = 100)
    private String points_team_local;

    @Column(name = "points_team_visitant", nullable = true, length = 100)
    private String points_team_visitant;

}
