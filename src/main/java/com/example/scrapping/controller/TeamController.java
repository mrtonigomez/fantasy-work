package com.example.scrapping.controller;

import com.example.scrapping.dto.TeamGetDto;
import com.example.scrapping.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/team")
    public List<TeamGetDto> getPlayersList() {
        return teamService.getAllTeamsDto();
    }

}
