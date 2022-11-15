package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.PlayerStats;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.PlayerRepository;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlayerService {

    private PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player createPlayer(String url, Team team) {

        Helpers helper = new Helpers();
        Player player = new Player();

        String urlPlayer = "https://www.basketball-reference.com" + url;
        Document documentPlayer = helper.getHtmlDocument(urlPlayer);
        Map<String, String> dataInfo = this.getBasicData(documentPlayer);

        int rowToSearch = documentPlayer.select("#per_game > tbody > tr").size() - 1;
        Elements elements = documentPlayer.select("#per_game > tbody > tr:nth-child(" + rowToSearch + ")");
        float price = this.calculatePrice(elements);

        player.setName(dataInfo.get("name"));
        player.setPostion(dataInfo.get("postion"));
        player.setPrice(price);
        player.setTeam(team);

        return this.addPlayer(player);

    }

    public Map<String, String> getBasicData(Document documentPlayer) {

        Map<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", documentPlayer.select("#meta > div:last-child > h1 > span").text());
        dataInfo.put("postion", documentPlayer.select("#per_game > tbody > tr:last-child > td[data-stat$=pos]").text());

        return dataInfo;

    }

    public float calculatePrice(Elements elements) {

        float price = 0F;

        //Comprobar si hay registros de la temporada pasada. Sino, es un rookie y el price es el minimo
        if (!elements.select("td[data-stat$=pts_per_g]").isEmpty()){
            boolean allStar = elements.select("th:first-child > span.sr_star").size() == 1;
            float ppg = Float.parseFloat(elements.select("td[data-stat$=pts_per_g]").text());

            if (ppg < 5f) {
                price = 5000000f;
            } else if (ppg > 5f && ppg < 10f) {
                price = 10000000f;
            } else if (ppg > 10f && ppg < 15f) {
                price = 15000000f;
            } else if (ppg > 15f && ppg < 20f) {
                price = 20000000f;
            } else if (ppg > 20f && ppg < 25f) {
                price = 30000000f;
            } else if (ppg > 25f && ppg < 30f) {
                price = 40000000f;
            } else if (ppg > 30f) {
                price = 50000000f;
            }

            if (allStar) {
                price += 5000000;
            }
        }else {
            price = 5000000f;
        }

        return price;
    }

    public Player addPlayer(Player player) {
        return repository.save(player);
    }

}
