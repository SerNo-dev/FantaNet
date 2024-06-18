package it.fanta.fantanet.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Giocatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long apiId;

    private String nome;
    private String cognome;
    private int eta;

    private String nazionalita;
    private String posizione;
    private int prezzo;
    private String photoUrl;

    @ManyToOne
    @JsonBackReference
    private Team team;

    @ManyToMany(mappedBy = "giocatoriAcquistati")
    @JsonBackReference
    private List<Utente> utentiAcquirenti;


    @ManyToMany(mappedBy = "carrello")
    @JsonBackReference
    private List<Utente> utentiCarrello;

    @ManyToMany(mappedBy = "deck")
    @JsonBackReference
    private List<Utente> utentiDeck;


    @Override
    public String toString() {
        return "Giocatore{" +
                "id=" + id +
                ", apiId=" + apiId +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", eta=" + eta +
                ", nazionalita='" + nazionalita + '\'' +
                ", posizione='" + posizione + '\'' +
                ", prezzo=" + prezzo +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
