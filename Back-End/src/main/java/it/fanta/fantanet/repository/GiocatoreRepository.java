package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Giocatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiocatoreRepository extends JpaRepository<Giocatore,Integer> {
  List<Giocatore> findByApiId(Long apiId);
    Giocatore findByNome(String nome);
}
