package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Game;
import com.example.scrapping.models.Player;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@Slf4j
public class PlayerService {

    private final PlayerRepository repository;
    private final GameService gameService;
    private final PlayerStatService playerStatService;

    public PlayerService(PlayerRepository repository, GameService gameService, PlayerStatService playerStatService) {
        this.repository = repository;
        this.gameService = gameService;
        this.playerStatService = playerStatService;
    }

    public void insertPlayerData(Team team, TeamService teamService) throws InterruptedException {

        //Get all DOM elements from players by team
        String urlGetTeam = "https://www.basketball-reference.com/teams/" + team.getAbrv() + "/2023.html";
        Document documentTeam = Helpers.getHtmlDocument(urlGetTeam);
        Elements playersDocument = documentTeam.select("#roster > tbody >tr > td[data-stat$=player] > a");

        if (getPlayersByTeam(team).size() != playersDocument.size()) {
            //Loop over each player
            playersDocument.forEach(playerDocument -> {
                try {
                    String urlPlayer = "https://www.basketball-reference.com" + playerDocument.attr("href");
                    //Create or get player object
                    Player player = insertOrGetPlayerData(urlPlayer, team);

                    String urlPlayerLatestStats = "https://www.basketball-reference.com" +
                            playerDocument.attr("href").replace(".html", "") + "/gamelog/2023";
                    Document documentPlayer = Helpers.getHtmlDocument(urlPlayerLatestStats);
                    Elements stats = documentPlayer.select("#pgl_basic > tbody > tr");

                    //Create a Game object and PlayerStats
                    recolectInfo(stats, player, teamService);

                    log.info("Hola, esperando tres segundos ...");
                    Thread.sleep(3000);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        log.info("Hola, esperando dos segundos ...");
        Thread.sleep(2000);
    }

    public Player insertOrGetPlayerData(String urlPlayer, Team team) throws InterruptedException {

        Player playerFind = getPlayerByUrl(urlPlayer);
        if (playerFind != null) {
            return playerFind;
        }

        //Obtener el documento HTML del jugador
        Document documentPlayer = Helpers.getHtmlDocument(urlPlayer);
        Map<String, String> dataInfo = getBasicData(documentPlayer);

        //Obtener la fila del año anterior para calcular su precio
        int rowToSearch = documentPlayer.select("#per_game > tbody > tr").size() - 1;
        Elements elements = documentPlayer.select("#per_game > tbody > tr:nth-child(" + rowToSearch + ")");
        float price = calculatePrice(elements);

        //Crear el objeto Player
        Player player = new Player();
        player.setName(dataInfo.get("name"));
        player.setPostion(dataInfo.get("postion"));
        player.setPrice(price);
        player.setUrl(urlPlayer);
        player.setTeam(team);

        log.info("Jugador " + player.getName() + " insertado");
        Thread.sleep(1000);
        return addPlayer(player);
    }

    public Map<String, String> getBasicData(Document documentPlayer) {
        Map<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", documentPlayer.select("#meta > div:last-child > h1 > span").text());
        dataInfo.put("postion", documentPlayer.select("#per_game > tbody > tr:last-child > td[data-stat$=pos]").text());

        return dataInfo;
    }

    public Player getPlayerByName(String name) {
        return repository.findByName(name);
    }

    public Player getPlayerByUrl(String url) {
        return repository.findByUrl(url);
    }

    public void recolectInfo(Elements statsGame, Player player, TeamService teamService) throws ParseException {
        for (Element statGame : statsGame) {
            Game game = gameService.createGame(statGame, teamService);

            if (game.getId() != null) {
                playerStatService.insertPlayerStatsData(statGame, player, game);
            }
        }
    }

    public float calculatePrice(Elements elements) {
        float price;

        if (!elements.select("td[data-stat$=pts_per_g]").isEmpty()) {
            boolean allStar = elements.select("th:first-child > span.sr_star").size() == 1;
            float ppg = Float.parseFloat(elements.select("td[data-stat$=pts_per_g]").text());

            price = calculatePriceByPpg(ppg);

            if (allStar) {
                price += 5000000;
            }
        } else {
            price = 5000000f;
        }

        return price;
    }

    private float calculatePriceByPpg(float ppg) {
        if (ppg < 5f) {
            return 5000000f;
        } else if (ppg > 5f && ppg <= 10f) {
            return 10000000f;
        } else if (ppg > 10f && ppg <= 15f) {
            return 15000000f;
        } else if (ppg > 15f && ppg <= 20f) {
            return 20000000f;
        } else if (ppg > 20f && ppg <= 25f) {
            return 30000000f;
        } else if (ppg > 25f && ppg < 30f) {
            return 40000000f;
        } else {
            return 50000000f;
        }
    }

    public Player addPlayer(Player player) {
        return repository.save(player);
    }

    public List<Player> getPlayersByTeam(Team team) {
        return repository.findByTeam(team);
    }

}
