package it.fanta.fantanet.controller;

import it.fanta.fantanet.models.Team;
import it.fanta.fantanet.service.TeamAndVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamAndVenueService teamAndVenueService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamAndVenueService.findAllTeams();
    }

    @GetMapping("/{apiId}")
    public Team getTeamByApiId(@PathVariable Long apiId) {
        return teamAndVenueService.findTeamByApiId(apiId);
    }
}
