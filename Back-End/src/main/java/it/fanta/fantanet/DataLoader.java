package it.fanta.fantanet;

import it.fanta.fantanet.service.ApiService;
import it.fanta.fantanet.service.FixtureService;
import it.fanta.fantanet.service.PlayerRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ApiService apiService;
    @Autowired
    private FixtureService fixtureService;

    @Autowired
    PlayerRatingService playerRatingService;

    @Override
    public void run(String... args) throws Exception {
//    apiService.fetchAndSaveTeams();
//     fixtureService.fetchAndSaveSerieAFixtures();
//     apiService.fetchAndSaveGiocatori();
//     playerRatingService.fetchAndSavePlayerRatingsForSerieA();
    }

}
