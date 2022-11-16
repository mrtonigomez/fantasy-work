package com.example.scrapping.service;

import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.PlayerStatsRepository;
import lombok.SneakyThrows;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PlayerStatService {

    protected final PlayerStatsRepository repository;

    public PlayerStatService(PlayerStatsRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    public void insertPlayerStatsData(Elements stats, Player player, GameService gameService, TeamService teamService) {

        for (int i = 0; i < stats.size(); i++) {

            if (!stats.get(i).select("[data-stat$=pts]").isEmpty()) {

                PlayerStats playerStats = new PlayerStats();

                playerStats.setPoints(Integer.parseInt(stats.get(i).select("[data-stat$=pts]").text()));
                playerStats.setMinutes(stats.get(i).select("[data-stat$=mp]").text());
                playerStats.setAssists(Integer.parseInt(stats.get(i).select("[data-stat$=ast]").text()));
                playerStats.setRebounds(Integer.parseInt(stats.get(i).select("[data-stat$=trb]").text()));
                playerStats.setFoults(Integer.parseInt(stats.get(i).select("[data-stat$=pf]").text()));
                playerStats.setBlocks(Integer.parseInt(stats.get(i).select("[data-stat$=blk]").text()));
                playerStats.setSteals(Integer.parseInt(stats.get(i).select("[data-stat$=stl]").text()));
                playerStats.setTurnovers(Integer.parseInt(stats.get(i).select("[data-stat$=tov]").text()));

                playerStats.setFgm(Integer.parseInt(stats.get(i).select("[data-stat$=fg]").text()));
                playerStats.setFga(Integer.parseInt(stats.get(i).select("[data-stat$=fga]").text()));

                playerStats.setFg3m(Integer.parseInt(stats.get(i).select("[data-stat$=fg3]").text()));
                playerStats.setFg3a(Integer.parseInt(stats.get(i).select("[data-stat$=fg3a]").text()));

                playerStats.setFtm(Integer.parseInt(stats.get(i).select("[data-stat$=ft]").text()));
                playerStats.setFta(Integer.parseInt(stats.get(i).select("[data-stat$=fta]").text()));

                playerStats.setPlayer(player);

                Game game = new Game();

                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stats.get(i).select("[data-stat$=date_game]").text());
                game.setDate(date);

                Team team_id = teamService.getTeamByCode(stats.get(i).select("[data-stat$=team_id]").text());
                Team opp_id = teamService.getTeamByCode(stats.get(i).select("[data-stat$=opp_id]").text());

                if (stats.get(i).select("[data-stat$=game_location]").text().equals("@")){
                    game = this.setLocalVisitantTeams(game, opp_id, team_id);
                }else {
                    game = this.setLocalVisitantTeams(game, team_id, opp_id);
                }
                playerStats.setGame(gameService.insertGame(game));

                this.addPlayerStat(playerStats);
            }

        }
    }

    public void addPlayerStat(PlayerStats playerStats) {
        repository.save(playerStats);
    }

    public Game setLocalVisitantTeams(Game game, Team local, Team visitant) {
        game.setVisitant_team(visitant);
        game.setLocal_team(local);

        return game;
    }

}
