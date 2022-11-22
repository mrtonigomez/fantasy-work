package com.example.scrapping.controller;

import com.example.scrapping.service.MainService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    MainService service;

    @GetMapping("/get-info")
    public void getInfo() throws InterruptedException {
        service.getInfo();

    }

}
