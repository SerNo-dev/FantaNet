package it.fanta.fantanet.controller;

import it.fanta.fantanet.models.Giocatore;
import it.fanta.fantanet.repository.GiocatoreRepository;
import it.fanta.fantanet.service.GiocatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giocatori")
public class GiocatoreController {
    @Autowired
    private GiocatoreRepository giocatoreRepository;
    @Autowired
    private GiocatoreService giocatoreService;

    @GetMapping("/all")
    public List<Giocatore> getAllGiocatori() {
        return giocatoreService.getAllGiocatori();
    }

    @GetMapping("/{id}")
    public Giocatore getGiocatoreById(@PathVariable int id) {
        return giocatoreService.getGiocatoreById(id);
    }

    @GetMapping
    public Page<Giocatore> getGiocatori(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return giocatoreRepository.findAll(pageable);
    }

}
