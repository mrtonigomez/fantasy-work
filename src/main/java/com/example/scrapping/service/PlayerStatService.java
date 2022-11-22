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
    protected final Helpers helper;

    public PlayerStatService(PlayerStatsRepository repository, Helpers helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @SneakyThrows
    public void insertPlayerStatsData(Element statGame, Player player, Game game) {
        if (!statGame.select("[data-stat$=pts]").isEmpty()) {

            if (this.findByPlayerAndGame(player, game) != null) {
                return;
            }
            this.createPlayerStat(statGame, player, game);

        }
    }

    public void createPlayerStat(Element stats, Player player, Game game) {
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
        playerStats.setRating(this.calculateRating(playerStats));

        this.addPlayerStat(playerStats);
    }

    public Integer calculateRating(PlayerStats playerStat) {
        int rating = 0;

        int pointsWin = (playerStat.getFgm() * 2) + (playerStat.getAssists() * 2) + (playerStat.getRebounds() * 2) +
                (playerStat.getBlocks()) + (playerStat.getSteals()) + (playerStat.getFtm());

        int pointsLose = ((playerStat.getFga() - playerStat.getFgm()) * 2) + (playerStat.getFoults()) +
                (playerStat.getTurnovers()) + (playerStat.getFta() - playerStat.getFtm());

        rating = pointsWin - pointsLose;

        if (rating <= 5) {
            return 5;
        }

        return rating;
    }

    public void addPlayerStat(PlayerStats playerStats) {
        repository.save(playerStats);
    }

    public void getPlayerStatBy(PlayerStats playerStats) {
        repository.save(playerStats);
    }

    public PlayerStats findByPlayerAndGame(Player player, Game game){
        return repository.findByPlayerAndGame(player, game);
    }

}
