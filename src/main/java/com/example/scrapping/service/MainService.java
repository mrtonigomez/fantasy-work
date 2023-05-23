package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@Slf4j
public class MainService {

    private String urlGetTeams = "https://www.basketball-reference.com/teams";
    private final PlayerService playerService;
    private final TeamService teamService;

    public MainService(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    public void getInfo() throws InterruptedException {
        // Check if the request return 200 code
        if (Helpers.getStatusConnectionCode(urlGetTeams) == 200) {

            //Obtener los equipos
            List<Team> teams = teamService.getAllTeams();

            if (teams.size() != 30) {
                teams = teamService.insertTeamData(urlGetTeams);
            }

            //Por cada equipo insertar sus jugadores
            for (Team team : teams) {
                playerService.insertPlayerData(team, teamService);
            }

        } else {
            log.info("El Status Code no es OK es: " + Helpers.getStatusConnectionCode(urlGetTeams));
        }
    }
}
