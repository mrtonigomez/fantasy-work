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

    public static final String urlGetTeams = "https://www.basketball-reference.com/teams";
    private final PlayerStatService playerStatService;
    private final PlayerService playerService;
    private final TeamService teamService;

    public MainService(PlayerStatService playerStatService, PlayerService playerService, TeamService teamService) {
        this.playerStatService = playerStatService;
        this.playerService = playerService;
        this.teamService = teamService;
    }

    public void getInfo() throws InterruptedException {

        Helpers helper = new Helpers();

        // Check if the request return 200 code
        if (helper.getStatusConnectionCode(urlGetTeams) == 200) {

            Document document = helper.getHtmlDocument(urlGetTeams);
            Elements documentTeams = document.select("#teams_active > tbody >tr> th > a");

            for (int i = 0; i < documentTeams.size(); i++) {
                Team team = teamService.createTeam(documentTeams.get(i).attr("href"));

                String urlGetTeam = "https://www.basketball-reference.com" + documentTeams.get(i).attr("href") + "2023.html";
                Document documentTeam = helper.getHtmlDocument(urlGetTeam);

                Elements players = documentTeam.select("#roster > tbody >tr > td[data-stat$=player] > a");

                for (int j = 0; j < players.size(); j++) {

                    Player player = playerService.createPlayer(players.get(j).attr("href"), team);

                    String newUrlPlayers = "https://www.basketball-reference.com" + players.get(j).attr("href").replace(".html", "") + "/gamelog/2023";
                    Document documentPlayer = helper.getHtmlDocument(newUrlPlayers);

                    Elements stats = documentPlayer.select("#pgl_basic > tbody > tr");
                    playerStatService.recolectInfoWeb(stats, player);

                    System.out.println("Hola, esperando dos segundos ...");
                    Thread.sleep(1000);
                    System.out.println("Ya volví de esperar");
                }

            }

        } else {
            System.out.println("El Status Code no es OK es: " +  helper.getStatusConnectionCode(urlGetTeams));
        }
    }


}
