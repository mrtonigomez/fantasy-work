package com.example.scrapping;

import com.example.scrapping.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {

    @Autowired
    MainService service;

    @GetMapping("/get-info")
    public void getInfo() throws InterruptedException {
        service.getInfo();

    }

}
