package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue,Integer> {
}
