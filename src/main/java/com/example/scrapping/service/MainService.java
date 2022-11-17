package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    public final String urlGetTeams = "https://www.basketball-reference.com/teams";
    private final PlayerStatService playerStatService;
    private final PlayerService playerService;
    private final TeamService teamService;
    private final GameService gameService;
    private final Helpers helper;

    public MainService(PlayerStatService playerStatService, PlayerService playerService, TeamService teamService, GameService gameService, Helpers helper) {
        this.playerStatService = playerStatService;
        this.playerService = playerService;
        this.teamService = teamService;
        this.gameService = gameService;
        this.helper = helper;
    }

    public void getInfo() throws InterruptedException {
        // Check if the request return 200 code
        if (helper.getStatusConnectionCode(this.urlGetTeams) == 200) {

            List<Team> teams = teamService.getAllTeams();

            if (teams.isEmpty()) {
                teams = this.insertTeamData();
            }

            for (Team team : teams) {
                this.insertPlayerData(team);
            }

        } else {
            System.out.println("El Status Code no es OK es: " + helper.getStatusConnectionCode(urlGetTeams));
        }
    }

    public List<Team> insertTeamData() throws InterruptedException {

        Document document = helper.getHtmlDocument(this.urlGetTeams);
        Elements documentTeams = document.select("#teams_active > tbody >tr> th > a");

        for (Element documentTeam : documentTeams) {
            teamService.createTeam(documentTeam.attr("href"));

            System.out.println("Hola, esperando cinco segundos ...");
            Thread.sleep(5000);
            System.out.println("Ya volví de esperar");
        }

        return teamService.getAllTeams();
    }

    public void insertPlayerData(Team team) {
        String urlGetTeam = "https://www.basketball-reference.com/teams/" + team.getAbrv() + "/2023.html";
        Document documentTeam = helper.getHtmlDocument(urlGetTeam);

        Elements playersDocument = documentTeam.select("#roster > tbody >tr > td[data-stat$=player] > a");

        playersDocument.forEach(playerDocument -> {
            String urlPlayer = "https://www.basketball-reference.com" + playerDocument.attr("href");
            Player player = playerService.insertOrGetPlayerData(urlPlayer, team);

            String urlPlayerLatestStats = "https://www.basketball-reference.com" + playerDocument.attr("href").replace(".html", "") + "/gamelog/2023";
            playerStatService.insertPlayerStatsData(urlPlayerLatestStats, player);

            System.out.println("Hola, esperando cinco segundos ...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ya volví de esperar");
        });

    }


}
