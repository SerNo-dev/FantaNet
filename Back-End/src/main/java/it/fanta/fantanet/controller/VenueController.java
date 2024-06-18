package it.fanta.fantanet.controller;

import it.fanta.fantanet.models.Venue;
import it.fanta.fantanet.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")

public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Integer id) {
        return venueService.getVenueById(id);
    }

    @PostMapping
    public Venue saveVenue(@RequestBody Venue venue) {
        return venueService.saveVenue(venue);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Integer id) {
        venueService.deleteVenue(id);
    }
}

