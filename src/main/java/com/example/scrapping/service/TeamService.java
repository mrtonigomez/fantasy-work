package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.TeamRepository;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeamService {

    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team createTeam(String url) {

        Helpers helper = new Helpers();
        Team team = new Team();

        String urlTeam = "https://www.basketball-reference.com" + url;
        Document documentTeam = helper.getHtmlDocument(urlTeam);
        Map<String, String> dataInfo = this.getBasicData(documentTeam);

        team.setName(dataInfo.get("name"));
        team.setChampionships(Integer.parseInt(dataInfo.get("championships")));
        team.setLocation(dataInfo.get("location"));

        return this.addTeam(team);

    }

    public Map<String, String> getBasicData(Document documentTeam) {

        Map<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", documentTeam.select("#meta > div:last-child > h1 > span:first-child").text());
        dataInfo.put("location", documentTeam.select("#meta > div:last-child > p:contains(location)").text().substring(10));
        dataInfo.put("championships", documentTeam.select("#meta > div:last-child > p:contains(championships)").text().substring(15));

        return dataInfo;

    }

    public Team addTeam(Team team) {
        return repository.save(team);
    }

}
