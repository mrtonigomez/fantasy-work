package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Game;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.GameRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GameService {

    protected final GameRepository repository;
    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game createGame(Element statsGame, TeamService teamService) throws ParseException {
        if (statsGame.className().equals("thead")) {
            return new Game();
        }

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(statsGame.select("[data-stat$=date_game]").text());
        Timestamp dateT = new Timestamp(date.getTime());
        String teamId = statsGame.select("[data-stat$=team_id]").text();
        String oppId = statsGame.select("[data-stat$=opp_id]").text();
        String gameLocation = statsGame.select("[data-stat$=game_location]").text();

        Team visitantTeam = teamService.getTeamByCode(gameLocation.equals("@") ? teamId : oppId);
        Team localTeam = teamService.getTeamByCode(gameLocation.equals("@") ? oppId : teamId);

        Game currentGame = findByVisitantTeamIdAndLocalTeamIdAndDate(visitantTeam, localTeam, dateT);
        if (currentGame != null) {
            return currentGame;
        }

        Game game = new Game();
        game.setLocalTeamId(visitantTeam);
        game.setVisitantTeamId(localTeam);
        game.setDate(dateT);

        return insertGame(game);
    }

    public Game insertGame(Game game) {
        return repository.save(game);
    }
    public Game getGameByDate(Timestamp date) {
        return repository.findByDate(date);
    }
    public Game getGameByVisitant(Timestamp date) {
        return repository.findByDate(date);
    }
    public Game findByVisitantTeamIdAndLocalTeamIdAndDate(Team teamVisitant, Team teamLocal, Timestamp date) {
        return repository.findByVisitantTeamIdAndLocalTeamIdAndDate(teamVisitant, teamLocal, date);
    }
}
