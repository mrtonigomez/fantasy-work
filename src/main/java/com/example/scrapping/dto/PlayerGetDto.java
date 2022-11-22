package com.example.scrapping.dto;

import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.models.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerGetDto {

    private Long id;

    private String name;

    private String postion;

    private float price;

    private String url;

    private List<PlayerStatsGetDto> playerStatsList;

}
