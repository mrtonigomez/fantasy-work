package com.example.scrapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "postion", nullable = false, length = 10)
    private String postion;

    @Column(name = "price", nullable = false, length = 50)
    private float price;

    @OneToMany(mappedBy = "player")
    @JsonProperty("player_stats")
    private List<PlayerStats> playerStatsList;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id", nullable = true)
    @JsonProperty("team")
    private Team team;

}
