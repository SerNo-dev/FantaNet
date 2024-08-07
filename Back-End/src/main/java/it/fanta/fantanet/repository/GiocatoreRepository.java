package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Giocatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiocatoreRepository extends JpaRepository<Giocatore,Integer> {
  List<Giocatore> findByApiId(Long apiId);

  @Query("SELECT g FROM Giocatore g WHERE LOWER(g.nome) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(g.cognome) LIKE LOWER(CONCAT('%', :keyword, '%'))")
  List<Giocatore> searchByNomeOrCognome(@Param("keyword") String keyword);
}
