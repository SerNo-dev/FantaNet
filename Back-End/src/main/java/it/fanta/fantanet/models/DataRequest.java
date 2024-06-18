package it.fanta.fantanet.models;

import lombok.Data;

@Data
public class DataRequest {
    private Team team;
    private Venue venue;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
