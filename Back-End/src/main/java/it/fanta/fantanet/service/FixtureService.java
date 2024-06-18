package it.fanta.fantanet.service;
import it.fanta.fantanet.models.ApiResponse;
import it.fanta.fantanet.models.Fixture;
import it.fanta.fantanet.repository.FixtureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FixtureService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FixtureRepository fixtureRepository;

    @Value("${api.key}")
    private String apiKey;

    private final WebClient webClient;

    public FixtureService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void fetchAndSaveSerieAFixtures() {
        ApiResponse response = this.webClient.get()
                .uri("https://v3.football.api-sports.io/fixtures?league=135&season=2023")
                .header("X-RapidAPI-Key",apiKey)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        if (response != null) {
            for (ApiResponse.ResponseData data : response.getResponse()) {
                Fixture fixture = new Fixture();
                fixture.setId(data.getFixture().getId());
                fixture.setDate(data.getFixture().getDate());
                fixture.setHomeTeamId(data.getTeams().getHome().getId());
                fixture.setAwayTeamId(data.getTeams().getAway().getId());
                fixture.setHomeTeamName(data.getTeams().getHome().getName());
                fixture.setAwayTeamName(data.getTeams().getAway().getName());

                fixtureRepository.save(fixture);
            }
        }
    }

    public List<Fixture> findAll() {
        return fixtureRepository.findAll();
    }
    public Fixture findById(int id) {
        return fixtureRepository.findById(id).orElse(null);
    }
}

