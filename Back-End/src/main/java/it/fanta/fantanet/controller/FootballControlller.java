package it.fanta.fantanet.controller;

import it.fanta.fantanet.models.DataRequest;
import it.fanta.fantanet.service.ApiService;
import it.fanta.fantanet.service.TeamAndVenueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class FootballControlller {
    @Autowired
    private TeamAndVenueService teamAndVenueService;

    @Autowired
    private ApiService apiService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER)")
    public void saveData(@RequestBody DataRequest dataRequest) {
        teamAndVenueService.saveTeamAndVenue(dataRequest.getTeam(), dataRequest.getVenue());
    }

    @GetMapping("/fetch-teams")
    public void fetchTeams() {
        apiService.fetchAndSaveTeams();
    }

    @GetMapping("/fetch-giocatori")
    public void fetchGiocatori() {
        apiService.fetchAndSaveGiocatori();
    }
}




