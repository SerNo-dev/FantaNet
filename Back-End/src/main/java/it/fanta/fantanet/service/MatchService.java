package it.fanta.fantanet.service;

import it.fanta.fantanet.models.Match;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    public void saveMatch(Match match) {
        System.out.println("Saving match to database: " + match);
        matchRepository.save(match);
        System.out.println("Match saved to database: " + match);
    }

    public List<Match> getMatchesByUser(Utente user) {
        return matchRepository.findByUser1OrUser2(user, user);
    }
}
