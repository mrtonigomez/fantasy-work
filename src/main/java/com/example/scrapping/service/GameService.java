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

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(statsGame.select("[data-stat$=date_game]").text());
        Timestamp dateT = new Timestamp(date.getTime());
        String visitantTeam;
        String localTeam;
        Team visitant_team_id;
        Team local_team_id;

        if (statsGame.select("[data-stat$=game_location]").text().equals("@")) {
            visitantTeam = statsGame.select("[data-stat$=team_id]").text();
            localTeam = statsGame.select("[data-stat$=opp_id]").text();

            visitant_team_id = teamService.getTeamByCode(visitantTeam);
            local_team_id = teamService.getTeamByCode(localTeam);
        }else {
            localTeam = statsGame.select("[data-stat$=team_id]").text();
            visitantTeam = statsGame.select("[data-stat$=opp_id]").text();

            local_team_id = teamService.getTeamByCode(localTeam);
            visitant_team_id = teamService.getTeamByCode(visitantTeam);
        }


      /*  if (statsGame.select("[data-stat$=team_id]").text().equals("CHO")) {
            team_id = teamService.getTeamByCode("CHA");
            opp_id = teamService.getTeamByCode(statsGame.select("[data-stat$=opp_id]").text());
        } else if (statsGame.select("[data-stat$=opp_id]").text().equals("CHO")) {
            team_id = teamService.getTeamByCode(statsGame.select("[data-stat$=team_id]").text());
            opp_id = teamService.getTeamByCode("CHA");
        } else {
            team_id = teamService.getTeamByCode(statsGame.select("[data-stat$=team_id]").text());
            opp_id = teamService.getTeamByCode(statsGame.select("[data-stat$=opp_id]").text());
        }*/

        if(findByVisitantTeamIdAndLocalTeamIdAndDate(visitant_team_id, local_team_id, dateT) != null){
            return findByVisitantTeamIdAndLocalTeamIdAndDate(visitant_team_id, local_team_id, dateT);
        }

        Game game = new Game();

        game.setLocalTeamId(local_team_id);
        game.setVisitantTeamId(visitant_team_id);
        game.setDate(dateT);
        return this.insertGame(game);
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
        return repository.findByVisitantTeamIdAndLocalTeamIdAndDate(team_visitant, team_local,date);
    }
}
