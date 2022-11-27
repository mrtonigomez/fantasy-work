package com.example.scrapping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerForTeamDto {

    private Long id;

    private String name;

    private String postion;

    private float price;

    private String url;

}
