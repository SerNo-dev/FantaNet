package it.fanta.fantanet.service;

import it.fanta.fantanet.models.Venue;
import it.fanta.fantanet.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(Integer id) {
        return venueRepository.findById(id).orElse(null);
    }

    public Venue saveVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public void deleteVenue(Integer id) {
        venueRepository.deleteById(id);
    }
}
