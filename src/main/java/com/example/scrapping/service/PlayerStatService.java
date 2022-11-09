package com.example.scrapping.service;

import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.repository.PlayerStatsRepository;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatService {

    protected final PlayerStatsRepository repository;

    public PlayerStatService(PlayerStatsRepository repository) {
        this.repository = repository;
    }

    public void recolectInfoWeb(Elements stats) {

        for (int i = 0; i < stats.size(); i++) {

            PlayerStats playerStats = new PlayerStats();
            playerStats.setPoints(Integer.parseInt(stats.get(i).select("[data-stat$=pts]").text()));
            playerStats.setMinutes(stats.get(i).select("[data-stat$=mp]").text());
            playerStats.setAssists(Integer.parseInt(stats.get(i).select("[data-stat$=ast]").text()));
            playerStats.setRebounds(Integer.parseInt(stats.get(i).select("[data-stat$=trb]").text()));
            playerStats.setFoults(Integer.parseInt(stats.get(i).select("[data-stat$=pf]").text()));
            playerStats.setBlocks(Integer.parseInt(stats.get(i).select("[data-stat$=blk]").text()));
            playerStats.setSteals(Integer.parseInt(stats.get(i).select("[data-stat$=stl]").text()));
            playerStats.setTurnovers(Integer.parseInt(stats.get(i).select("[data-stat$=tov]").text()));

            this.addPlayerStat(playerStats);

        }


    }

    public void addPlayerStat(PlayerStats playerStats) {
        repository.save(playerStats);
    }

}
