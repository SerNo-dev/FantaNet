package it.fanta.fantanet.controller;

import it.fanta.fantanet.Dto.MatchDto;
import it.fanta.fantanet.models.Match;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.repository.UserRepository;
import it.fanta.fantanet.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public void saveMatch(@RequestBody MatchDto matchDTO) {
        System.out.println("Received request to save match: " + matchDTO);

        Optional<Utente> user1 = userRepository.findById(matchDTO.getUser1Id());
        Optional<Utente> user2 = userRepository.findById(matchDTO.getUser2Id());
        Optional<Utente> winner = matchDTO.getWinnerId() != null ? userRepository.findById(matchDTO.getWinnerId()) : Optional.empty();

        if (user1.isPresent() && user2.isPresent()) {
            Match match = new Match();
            match.setUser1(user1.get());
            match.setUser2(user2.get());
            match.setUser1Score(matchDTO.getUser1Score());
            match.setUser2Score(matchDTO.getUser2Score());
            winner.ifPresent(match::setWinner);
            match.setMatchDate(new Date());

            System.out.println("Saving match: " + match);
            matchService.saveMatch(match);
            System.out.println("Match saved successfully: " + match);
        } else {
            System.out.println("One or more users not found: user1=" + user1.isPresent() + ", user2=" + user2.isPresent() + ", winner=" + winner.isPresent());
        }
    }
}
