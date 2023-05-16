package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class MainService {

    private final static String urlGetTeams = "https://www.basketball-reference.com/teams";
    private final PlayerService playerService;
    private final TeamService teamService;
    private final InfoStatsService infoStatsService;
    private final Helpers helper;

    public MainService(PlayerService playerService, TeamService teamService, Helpers helper,
                       InfoStatsService infoStatsService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.infoStatsService = infoStatsService;
        this.helper = helper;
    }

    public void getInfo() throws InterruptedException {
        // Check if the request return 200 code
        if (helper.getStatusConnectionCode(urlGetTeams) == 200) {

            List<Team> teams = teamService.getAllTeams();

            if (teams.isEmpty()) {
                teams = teamService.insertTeamData(urlGetTeams);
            }

            for (Team team : teams) {
                playerService.insertPlayerData(team, teamService, infoStatsService);
            }

        } else {
            System.out.println("El Status Code no es OK es: " + helper.getStatusConnectionCode(urlGetTeams));
        }
    }
}
