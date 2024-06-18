package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findByApiId(Long apiId);
}
