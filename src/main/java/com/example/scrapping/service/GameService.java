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
    protected final Helpers helper;

    public GameService(GameRepository repository, Helpers helper) {
        this.repository = repository;
        this.helper = helper;
    }

    public Game createGame(Element statsGame, TeamService teamService) throws ParseException {

        if (!statsGame.className().equals("thead")) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(statsGame.select("[data-stat$=date_game]").text());
            Timestamp dateT = new Timestamp(date.getTime());
            String visitantTeam;
            String localTeam;
            String team_id;
            String opp_id;
            Team visitant_team_id;
            Team local_team_id;

            team_id = statsGame.select("[data-stat$=team_id]").text();
            opp_id = statsGame.select("[data-stat$=opp_id]").text();

            if (statsGame.select("[data-stat$=game_location]").text().equals("@")) {
                visitant_team_id = teamService.getTeamByCode(team_id);
                local_team_id = teamService.getTeamByCode(opp_id);
            } else {
                local_team_id = teamService.getTeamByCode(team_id);
                visitant_team_id = teamService.getTeamByCode(opp_id);
            }


            if (findByVisitantTeamIdAndLocalTeamIdAndDate(visitant_team_id, local_team_id, dateT) != null) {
                return findByVisitantTeamIdAndLocalTeamIdAndDate(visitant_team_id, local_team_id, dateT);
            }

            Game game = new Game();

            game.setLocalTeamId(local_team_id);
            game.setVisitantTeamId(visitant_team_id);
            game.setDate(dateT);
            return this.insertGame(game);
        }else {
            return new Game();
        }

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

    public Game findByVisitantTeamIdAndLocalTeamIdAndDate(Team team_visitant, Team team_local, Timestamp date) {
        return repository.findByVisitantTeamIdAndLocalTeamIdAndDate(team_visitant, team_local, date);
    }
}
