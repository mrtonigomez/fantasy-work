package com.example.scrapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
    private Team local_team;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "visitant_team_id", nullable = true)
    @JsonProperty("visitant_team_id")
    private Team visitant_team;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "winner_team_id", nullable = true)
    @JsonProperty("winner_team_id")
    private Team winner_team_id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "loser_team_id", nullable = true)
    @JsonProperty("loser_team_id")
    private Team loser_team_id;

    @Column(name = "point_team_local", nullable = false, length = 100)
    private String point_team_local;

    @Column(name = "point_team_visitant", nullable = false, length = 100)
    private String point_team_visitant;

}
