package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureRepository  extends JpaRepository<Fixture, Integer> {
}
