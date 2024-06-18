package it.fanta.fantanet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class GiocatoriVotiNellePartite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "giocatore_id")
    private Giocatore giocatore;



    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "fixture_id")
    private Fixture fixture;

    private double rating; // qui convertito dopo in double nel db, preso in formato string dall'Api

}
