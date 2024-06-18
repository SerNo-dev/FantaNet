package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Giocatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiocatoreRepository extends JpaRepository<Giocatore,Integer> {
    Giocatore findByApiId(long apiId);
    Giocatore findByNome(String nome);
}
