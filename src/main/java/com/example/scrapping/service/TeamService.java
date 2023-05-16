package com.example.scrapping.service;

import com.example.scrapping.Helpers;
import com.example.scrapping.models.Team;
import com.example.scrapping.repository.TeamRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamService {
    private TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<Team> insertTeamData(String urlGetTeams) throws InterruptedException {
        //Get teams document html
        Document document = Helpers.getHtmlDocument(urlGetTeams);
        Elements documentTeams = document.select("#teams_active > tbody >tr> th > a");

        for (Element documentTeam : documentTeams) {
            System.out.println("Hola, añadiendo equipo...");

            //Create team
            this.createTeam(documentTeam.attr("href"));
            Thread.sleep(2000);

            System.out.println("Equipo añadido");
        }
        return this.getAllTeams();
    }

    public void createTeam(String url) {

        Team team = new Team();

        String urlTeam = "https://www.basketball-reference.com" + url;
        Document documentTeam = Helpers.getHtmlDocument(urlTeam);
        Map<String, String> dataInfo = this.getBasicData(documentTeam);

        team.setName(dataInfo.get("name"));
        team.setChampionships(dataInfo.get("championships"));
        team.setLocation(dataInfo.get("location"));

        if (url.split("/")[2].equals("NJN")) {
            team.setAbrv("BRK");
            this.addTeam(team);
            return;
        } else {
            team.setAbrv(url.split("/")[2]);
        }

        if (url.split("/")[2].equals("CHA")) {
            team.setAbrv("CHO");
            this.addTeam(team);
            return;
        } else {
            team.setAbrv(url.split("/")[2]);
        }

        if (url.split("/")[2].equals("NOH")) {
            team.setAbrv("NOP");
            this.addTeam(team);
            return;
        } else {
            team.setAbrv(url.split("/")[2]);
        }

        this.addTeam(team);
    }

    public Map<String, String> getBasicData(Document documentTeam) {

        Map<String, String> dataInfo = new HashMap<>();
        dataInfo.put("name", documentTeam.select("#meta > div:last-child > h1 > span:first-child").text());
        dataInfo.put("location", documentTeam.select("#meta > div:last-child > p:contains(location)").text().substring(10));
        dataInfo.put("championships", documentTeam.select("#meta > div:last-child > p:contains(championships)").text().substring(15).trim());

        return dataInfo;

    }

    public Team addTeam(Team team) {
        return repository.save(team);
    }

    public Team getTeamByCode(String abrv) {
        return repository.findByAbrv(abrv);
    }

    public List<Team> getAllTeams() {
        return repository.findAll();
    }


}
