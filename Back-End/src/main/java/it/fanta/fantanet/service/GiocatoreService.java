package it.fanta.fantanet.service;

import it.fanta.fantanet.models.Giocatore;
import it.fanta.fantanet.repository.GiocatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiocatoreService {
    @Autowired
    private GiocatoreRepository giocatoreRepository;

    public void saveGiocatori(List<Giocatore> giocatori) {
        giocatoreRepository.saveAll(giocatori);
    }
    public Giocatore findByApiId(Long apiId) {
        return giocatoreRepository.findByApiId(apiId);
    }

    public List<Giocatore> getAllGiocatori() {
        return giocatoreRepository.findAll();
    }

    public Giocatore getGiocatoreById(int id) {
        return giocatoreRepository.findById(id).orElse(null);
    }

    public Giocatore saveGiocatore(Giocatore giocatore) {
        return giocatoreRepository.save(giocatore);
    }

    public void deleteGiocatore(int id) {
        giocatoreRepository.deleteById(id);
    }
}
