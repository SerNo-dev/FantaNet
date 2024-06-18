package it.fanta.fantanet.service;

import it.fanta.fantanet.models.*;
import it.fanta.fantanet.repository.GiocatoriVotiNellePartiteRepository;
import it.fanta.fantanet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiocatoriVotiService {

    @Autowired
    private GiocatoriVotiNellePartiteRepository giocatoriVotiNellePartiteRepository;

    @Autowired
    private UserRepository userRepository;
    public List<GiocatoriVotiNellePartite> getAllVoti() {
        return giocatoriVotiNellePartiteRepository.findAll();
    }

    public GiocatoriVotiNellePartite getVotoById(Long id) {
        return giocatoriVotiNellePartiteRepository.findById(id).orElse(null);
    }

    public GiocatoriVotiNellePartite saveVoto(GiocatoriVotiNellePartite voto) {
        return giocatoriVotiNellePartiteRepository.save(voto);
    }

    public void deleteVoto(Long id) {
        giocatoriVotiNellePartiteRepository.deleteById(id);
    }

    public List<GiocatoriVotiNellePartite> getVotiByGiocatoreId(Long giocatoreId) {
        List<GiocatoriVotiNellePartite> voti = giocatoriVotiNellePartiteRepository.findByGiocatoreId(giocatoreId);
        System.out.println("Trovati " + voti.size() + " voti per il giocatore con ID " + giocatoreId);
        return voti;
    }

    public List<GiocatoriVotiNellePartite> findVotiByUserDeck(Long userId) {
        List<Object[]> results = giocatoriVotiNellePartiteRepository.findVotiByUserDeckWithDefault(userId);
        List<GiocatoriVotiNellePartite> votiList = new ArrayList<>();

        for (Object[] result : results) {
            GiocatoriVotiNellePartite voto = new GiocatoriVotiNellePartite();
            Giocatore giocatore = new Giocatore();
            Team team = new Team();
            Fixture fixture = new Fixture();

            giocatore.setId((Integer) result[0]);
            giocatore.setApiId((Long) result[1]);
            giocatore.setNome((String) result[2]);
            giocatore.setCognome((String) result[3]);
            giocatore.setEta((Integer) result[4]);
            giocatore.setNazionalita((String) result[5]);
            giocatore.setPosizione((String) result[6]);
            giocatore.setPrezzo((Integer) result[7]);
            giocatore.setPhotoUrl((String) result[8]);

            voto.setGiocatore(giocatore);
            voto.setRating((Double) result[9]);

            if (result[10] != null) {
                team.setId((Integer) result[10]);
                team.setApiId((Long) result[11]);
                team.setName((String) result[12]);
                team.setCode((String) result[13]);
                team.setCountry((String) result[14]);
                team.setFounded((Integer) result[15]);
                team.setNational((Boolean) result[16]);
                team.setLogo((String) result[17]);
                voto.setTeam(team);
            }

            if (result[18] != null) {
                fixture.setId((Integer) result[18]);
                fixture.setDate((String) result[19]);
                fixture.setHomeTeamId((Integer) result[20]);
                fixture.setAwayTeamId((Integer) result[21]);
                fixture.setHomeTeamName((String) result[22]);
                fixture.setAwayTeamName((String) result[23]);
                voto.setFixture(fixture);
            }

            votiList.add(voto);
        }

        return votiList;
    }

}
