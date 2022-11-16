package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    public final String urlGetTeams = "https://www.basketball-reference.com/teams";
    private final PlayerStatService playerStatService;
    private final PlayerService playerService;
    private final TeamService teamService;
    private final GameService gameService;

    public MainService(PlayerStatService playerStatService, PlayerService playerService, TeamService teamService, GameService gameService) {
        this.playerStatService = playerStatService;
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameService = gameService;
    }

    public void getInfo() throws InterruptedException {

        Helpers helper = new Helpers();

        // Check if the request return 200 code
        if (helper.getStatusConnectionCode(this.urlGetTeams) == 200) {

            List<Team> teams = teamService.getAllTeams();

            if (teams.isEmpty()) {
                teams = this.insertTeamData(helper);
            }

            for (int i = 0; i < teams.size(); i++) {
                String urlGetTeam = "https://www.basketball-reference.com/teams/" + teams.get(i).getAbrv() + "/2023.html";
                Document documentTeam = helper.getHtmlDocument(urlGetTeam);

                Elements players = documentTeam.select("#roster > tbody >tr > td[data-stat$=player] > a");

                for (int j = 0; j < players.size(); j++) {
                    String uriPlayer = players.get(j).attr("href");
                    Player player = playerService.insertPlayerData(uriPlayer, teams.get(i));

                    String newUrlPlayers = "https://www.basketball-reference.com" + players.get(j).attr("href").replace(".html", "") + "/gamelog/2023";
                    Document documentPlayer = helper.getHtmlDocument(newUrlPlayers);

                    Elements stats = documentPlayer.select("#pgl_basic > tbody > tr");
                    playerStatService.insertPlayerStatsData(stats, player, gameService, teamService);

                    System.out.println("Hola, esperando cinco segundos ...");
                    Thread.sleep(5000);
                    System.out.println("Ya volví de esperar");
                }
            }

        } else {
            System.out.println("El Status Code no es OK es: " + helper.getStatusConnectionCode(urlGetTeams));
        }
    }

    public List<Team> insertTeamData(Helpers helper) throws InterruptedException {

        Document document = helper.getHtmlDocument(this.urlGetTeams);
        Elements documentTeams = document.select("#teams_active > tbody >tr> th > a");

        for (int i = 0; i < documentTeams.size(); i++) {
            teamService.createTeam(documentTeams.get(i).attr("href"));

            System.out.println("Hola, esperando cinco segundos ...");
            Thread.sleep(5000);
            System.out.println("Ya volví de esperar");
        }

        return teamService.getAllTeams();
    }


}
