package it.fanta.fantanet.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private Long apiId;
    private String name;
    private String code;
    private String country;
    private int founded;
    private boolean national;
    private String logo;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<Giocatore> giocatori;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<GiocatoriVotiNellePartite> giocatoriVoti;

}
