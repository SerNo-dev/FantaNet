package it.fanta.fantanet.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Fixture {
    @Id
    private int id;
    private String date;
    private int homeTeamId;
    private int awayTeamId;
    private String homeTeamName;
    private String awayTeamName;

    @OneToMany(mappedBy = "fixture")
    @JsonIgnore
    private List<GiocatoriVotiNellePartite> giocatoriVoti;
}
