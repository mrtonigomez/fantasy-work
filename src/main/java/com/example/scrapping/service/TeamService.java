package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TeamService {
    private TeamRepository repository;

    public TeamService(TeamRepository repository)
    {
        this.repository = repository;
    }

    public List<Team> insertTeamData(String urlGetTeams) throws InterruptedException
    {
        //Get teams document html
        Document document = Helpers.getHtmlDocument(urlGetTeams);
        Elements documentTeams = document.select("#teams_active > tbody >tr> th > a");

        for (Element documentTeam : documentTeams)
        {
            log.info("Hola, añadiendo equipo...");

            //Create or get team
            createTeam(documentTeam.attr("href"));
            Thread.sleep(2000);
        }
        return getAllTeams();
    }

    public Team createTeam(String url) {
        String urlTeam = "https://www.basketball-reference.com" + url;
        Document documentTeam = Helpers.getHtmlDocument(urlTeam);

        Team teamFind = getTeamByName(documentTeam.select("#meta > div:last-child > h1 > span:first-child").text());
        if (teamFind != null) {
            return teamFind;
        }

        Team team = new Team();
        Map<String, String> dataInfo = getBasicData(documentTeam);

        team.setName(dataInfo.get("name"));
        team.setChampionships(dataInfo.get("championships"));
        team.setLocation(dataInfo.get("location"));

        String abbreviation = url.split("/")[2];

        switch (abbreviation)
        {
            case "NJN":
                team.setAbrv("BRK");
                break;
            case "CHA":
                team.setAbrv("CHO");
                break;
            case "NOH":
                team.setAbrv("NOP");
                break;
            default:
                team.setAbrv(abbreviation);
                break;
        }

        addTeam(team);
        log.info("Equipo " + team.getAbrv() + " añadido");
        return team;
    }

    public Map<String, String> getBasicData(Document documentTeam)
    {

        Map<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", documentTeam.select("#meta > div:last-child > h1 > span:first-child").text());
        dataInfo.put("location", documentTeam.select("#meta > div:last-child > p:contains(location)").text().substring(10));
        dataInfo.put("championships", documentTeam.select("#meta > div:last-child > p:contains(championships)").text().substring(15).trim());

        return dataInfo;
    }

    public Team addTeam(Team team)
    {
        return repository.save(team);
    }

    public Team getTeamByCode(String abrv)
    {
        return repository.findByAbrv(abrv);
    }

    public Team getTeamByName(String name)
    {
        return repository.findByName(name);
    }

    public List<Team> getAllTeams()
    {
        return repository.findAll();
    }
}
