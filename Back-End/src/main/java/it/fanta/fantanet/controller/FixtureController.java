package it.fanta.fantanet.controller;


import it.fanta.fantanet.models.Fixture;
import it.fanta.fantanet.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fixtures")
public class FixtureController {


    @Autowired
    private FixtureService fixtureService;

    @GetMapping
    public List<Fixture> getAllFixtures() {
        return fixtureService.findAll();
    }

    @GetMapping("/{id}")
    public Fixture getFixtureById(@PathVariable int id) {
        return fixtureService.findById(id);
    }
}
