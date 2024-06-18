package it.fanta.fantanet.controller;


import it.fanta.fantanet.models.GiocatoriVotiNellePartite;
import it.fanta.fantanet.service.GiocatoriVotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voti")
public class GiocatoriVotiController {
    @Autowired
    private GiocatoriVotiService votiService;



    @GetMapping
    public List<GiocatoriVotiNellePartite> getAllVoti() {
        return votiService.getAllVoti();
    }

    @GetMapping("/{id}")
    public GiocatoriVotiNellePartite getVotoById(@PathVariable Long id) {
        return votiService.getVotoById(id);
    }

    @PostMapping
    public GiocatoriVotiNellePartite saveVoto(@RequestBody GiocatoriVotiNellePartite voto) {
        return votiService.saveVoto(voto);
    }

    @DeleteMapping("/{id}")
    public void deleteVoto(@PathVariable Long id) {
        votiService.deleteVoto(id);
    }


    @GetMapping("giocatore/{giocatoreId}")
    public List<GiocatoriVotiNellePartite> getVotiByGiocatoreId(@PathVariable Long giocatoreId) {
        return votiService.getVotiByGiocatoreId(giocatoreId);
    }




    @GetMapping("/user/{userId}/deck")
    public List<GiocatoriVotiNellePartite> getVotiByUserDeck(@PathVariable Long userId) {
        System.out.println("Received request to get votes for user deck: userId = " + userId);
        List<GiocatoriVotiNellePartite> voti = votiService.findVotiByUserDeck(userId);
        System.out.println("Found " + voti.size() + " votes for user deck");
        return voti;
    }
}
