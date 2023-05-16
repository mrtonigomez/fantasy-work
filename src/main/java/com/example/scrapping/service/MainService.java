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

    private static final String urlGetTeams = "https://www.basketball-reference.com/teams";
    private final PlayerService playerService;
    private final TeamService teamService;
    private final InfoStatsService infoStatsService;

    public MainService(PlayerService playerService, TeamService teamService, InfoStatsService infoStatsService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.infoStatsService = infoStatsService;
    }

    public void getInfo() throws InterruptedException {
        // Check if the request return 200 code
        if (Helpers.getStatusConnectionCode(urlGetTeams) == 200) {

            List<Team> teams = teamService.getAllTeams();

            if (teams.isEmpty()) {
                teams = teamService.insertTeamData(urlGetTeams);
            }

            for (Team team : teams) {
                playerService.insertPlayerData(team, teamService, infoStatsService);
            }

        } else {
            log.info("El Status Code no es OK es: " + Helpers.getStatusConnectionCode(urlGetTeams));
        }
    }
}
