package com.example.scrapping.service;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class InfoStatsService {

    GameService gameService;
    PlayerStatService playerStatService;

    public InfoStatsService(GameService gameService, PlayerStatService playerStatService) {
        this.gameService = gameService;
        this.playerStatService = playerStatService;
    }

    public void recolectInfo(Elements statsGame, Player player, TeamService teamService) throws ParseException {

        for (Element statGame : statsGame) {
            Game game = gameService.createGame(statGame, teamService);
            playerStatService.insertPlayerStatsData(statGame, player, game);
        }

    }
}
