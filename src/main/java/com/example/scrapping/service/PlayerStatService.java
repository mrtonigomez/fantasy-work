package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.PlayerStatsRepository;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PlayerStatService {

    protected final PlayerStatsRepository repository;
    protected final GameService gameService;
    protected final TeamService teamService;

    public PlayerStatService(PlayerStatsRepository repository, GameService gameService, TeamService teamService) {
        this.repository = repository;
        this.gameService = gameService;
        this.teamService = teamService;
    }

    @SneakyThrows
    public void insertPlayerStatsData(String newUrlPlayers, Player player) {

        //Validacion de player stat y game

        Helpers helper = new Helpers();

        Document documentPlayer = helper.getHtmlDocument(newUrlPlayers);
        Elements stats = documentPlayer.select("#pgl_basic > tbody > tr");

        for (int i = 0; i < stats.size(); i++) {

            if (!stats.select("[data-stat$=pts]").isEmpty()) {

                Game game = this.createOrGetGame(stats.get(i));
                this.createOrGetPlayerStat(stats.get(i), player, game);

            }
        }
    }

    public void addPlayerStat(PlayerStats playerStats) {
        repository.save(playerStats);
    }

    public void getPlayerStatBy(PlayerStats playerStats) {
        repository.save(playerStats);
    }

    public Game setLocalVisitantTeams(Game game, Team local, Team visitant) {
        game.setVisitant_team(visitant);
        game.setLocal_team(local);

        return game;
    }

    public Game createOrGetGame(Element stats) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stats.select("[data-stat$=date_game]").text());
        Timestamp dateT = new Timestamp(date.getTime());

        Team team_id = teamService.getTeamByCode(stats.select("[data-stat$=team_id]").text());
        Team opp_id = teamService.getTeamByCode(stats.select("[data-stat$=opp_id]").text());

        Game game = new Game();

        if (stats.select("[data-stat$=game_location]").text().equals("@")) {
            game = this.setLocalVisitantTeams(game, opp_id, team_id);
        } else {
            game = this.setLocalVisitantTeams(game, team_id, opp_id);
        }

        game.setDate(dateT);
        return gameService.insertGame(game);
    }

    public void createOrGetPlayerStat(Element stats, Player player, Game game) {
        PlayerStats playerStats = new PlayerStats();

        playerStats.setPoints(Integer.parseInt(stats.select("[data-stat$=pts]").text()));
        playerStats.setMinutes(stats.select("[data-stat$=mp]").text());
        playerStats.setAssists(Integer.parseInt(stats.select("[data-stat$=ast]").text()));
        playerStats.setRebounds(Integer.parseInt(stats.select("[data-stat$=trb]").text()));
        playerStats.setFoults(Integer.parseInt(stats.select("[data-stat$=pf]").text()));
        playerStats.setBlocks(Integer.parseInt(stats.select("[data-stat$=blk]").text()));
        playerStats.setSteals(Integer.parseInt(stats.select("[data-stat$=stl]").text()));
        playerStats.setTurnovers(Integer.parseInt(stats.select("[data-stat$=tov]").text()));

        playerStats.setFgm(Integer.parseInt(stats.select("[data-stat$=fg]").text()));
        playerStats.setFga(Integer.parseInt(stats.select("[data-stat$=fga]").text()));

        playerStats.setFg3m(Integer.parseInt(stats.select("[data-stat$=fg3]").text()));
        playerStats.setFg3a(Integer.parseInt(stats.select("[data-stat$=fg3a]").text()));

        playerStats.setFtm(Integer.parseInt(stats.select("[data-stat$=ft]").text()));
        playerStats.setFta(Integer.parseInt(stats.select("[data-stat$=fta]").text()));

        playerStats.setPlayer(player);
        playerStats.setGame(game);

        this.addPlayerStat(playerStats);
    }

}
