package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Match;
import it.fanta.fantanet.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match,Long> {
    List<Match> findByUser1OrUser2(Utente user1, Utente user2);
}
