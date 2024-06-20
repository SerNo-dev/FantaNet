package it.fanta.fantanet.controller;

import it.fanta.fantanet.models.Match;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.repository.UserRepository;
import it.fanta.fantanet.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")

public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<Match> getMatchesByUser(@PathVariable Long userId) {
        Optional<Utente> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return matchService.getMatchesByUser(user.get());
        }
        return Collections.emptyList();
    }

}
