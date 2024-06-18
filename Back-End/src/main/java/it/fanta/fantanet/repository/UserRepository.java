package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Utente, Long> {
    Utente findByUsername(String username);
    Optional<Utente> findByEmail(String email);
}
