package it.fanta.fantanet.service;

import it.fanta.fantanet.models.Team;
import it.fanta.fantanet.models.Venue;
import it.fanta.fantanet.repository.TeamRepository;

import it.fanta.fantanet.repository.VenueRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeamAndVenueService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Transactional
    public void saveTeamAndVenue(Team team, Venue venue) {
        teamRepository.save(team);
        venueRepository.save(venue);
    }

    @Transactional
    public Team findTeamByApiId(Long apiId) {
        System.out.println("API ID ricevuto: " + apiId);

        Team team = teamRepository.findByApiId(apiId);

        if (team != null) {
            System.out.println("Team trovato: " + team.getName());
        } else {
            System.out.println("Nessun team trovato per l'API ID: " + apiId);
        }
        return team;
    }
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }
}
