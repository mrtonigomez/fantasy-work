package com.example.scrapping.controller;

import com.example.scrapping.dto.PlayerGetDto;
import com.example.scrapping.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/players")
    public List<PlayerGetDto> getPlayersList() {
        return playerService.getPlayers();
    }

}
