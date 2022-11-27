package com.example.scrapping.dto;

import com.example.scrapping.models.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamGetDto {

    private Long id;

    private String name;

    private String abrv;

    private String location;

    private String championships;

    private List<PlayerForTeamDto> players;

}
