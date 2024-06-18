package it.fanta.fantanet.repository;

import it.fanta.fantanet.models.Giocatore;
import it.fanta.fantanet.models.GiocatoriVotiNellePartite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiocatoriVotiNellePartiteRepository extends JpaRepository<GiocatoriVotiNellePartite, Long> {
    List<GiocatoriVotiNellePartite> findByGiocatoreId(Long giocatoreId);
    List<GiocatoriVotiNellePartite> findByGiocatoreIn(List<Giocatore> giocatori);
    List<GiocatoriVotiNellePartite> findByGiocatoreIdAndGiocatoreIn(Integer giocatoreId, List<Giocatore> giocatori);

    @Query(value = """
    SELECT 
        g.id AS giocatore_id,
        g.api_id AS giocatore_api_id,
        g.nome AS giocatore_nome,
        g.cognome AS giocatore_cognome,
        g.eta AS giocatore_eta,
        g.nazionalita AS giocatore_nazionalita,
        g.posizione AS giocatore_posizione,
        g.prezzo AS giocatore_prezzo,
        g.photo_url AS giocatore_photoUrl,
        COALESCE(subquery.voto, 0.0) AS voto,
        t.id AS team_id,
        t.api_id AS team_api_id,
        t.name AS team_name,
        t.code AS team_code,
        t.country AS team_country,
        t.founded AS team_founded,
        t.national AS team_national,
        t.logo AS team_logo,
        f.id AS fixture_id,
        f.date AS fixture_date,
        f.home_team_id AS fixture_home_team_id,
        f.away_team_id AS fixture_away_team_id,
        f.home_team_name AS fixture_home_team_name,
        f.away_team_name AS fixture_away_team_name
    FROM 
        user_deck ud
    JOIN 
        giocatore g ON ud.giocatore_id = g.id
    LEFT JOIN 
        (SELECT DISTINCT ON (gvnp.giocatore_id) 
             gvnp.giocatore_id, 
             gvnp.rating AS voto,
             gvnp.team_id,
             gvnp.fixture_id
         FROM 
             giocatori_voti_nelle_partite gvnp
         WHERE gvnp.rating IS NOT NULL
         ORDER BY gvnp.giocatore_id, RANDOM()) subquery 
    ON g.id = subquery.giocatore_id
    LEFT JOIN team t ON subquery.team_id = t.id
    LEFT JOIN fixture f ON subquery.fixture_id = f.id
    WHERE 
        ud.user_id = :userId
    """, nativeQuery = true)
    List<Object[]> findVotiByUserDeckWithDefault(@Param("userId") Long userId);

}

