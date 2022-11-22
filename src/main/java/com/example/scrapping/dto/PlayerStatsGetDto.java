package com.example.scrapping.dto;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerStatsGetDto {

    private Long id;

    private GameGetDto game;

    private String minutes;

    private int points;

    private int rebounds;

    private int assists;

    private int steals;

    private int blocks;

    private int turnovers;

    private int fgm;

    private int fga;

    private int fg3m;

    private int fg3a;

    private int ftm;

    private int fta;

    private int foults;

    private int rating;

}
