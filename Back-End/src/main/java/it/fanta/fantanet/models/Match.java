package it.fanta.fantanet.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utente user1;

    @ManyToOne
    private Utente user2;

    private double user1Score;
    private double user2Score;

    @Temporal(TemporalType.TIMESTAMP)
    private Date matchDate;

    @ManyToOne
    private Utente winner;
}
