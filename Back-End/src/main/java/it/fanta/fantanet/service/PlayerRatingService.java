package it.fanta.fantanet.service;

import it.fanta.fantanet.ApiFixturesPlayer.ApiResponseFixturePlayers;
import it.fanta.fantanet.models.Fixture;
import it.fanta.fantanet.models.Giocatore;
import it.fanta.fantanet.models.GiocatoriVotiNellePartite;
import it.fanta.fantanet.models.Team;
import it.fanta.fantanet.repository.GiocatoreRepository;
import it.fanta.fantanet.repository.GiocatoriVotiNellePartiteRepository;
import it.fanta.fantanet.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PlayerRatingService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FixtureService fixtureService;

    @Autowired
    private TeamAndVenueService teamAndVenueService;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private GiocatoriVotiNellePartiteRepository giocatoriVotiNellePartiteRepository;

    @Autowired
    private GiocatoreRepository giocatoreRepository;
    @Autowired
    TeamRepository teamRepository;

    @Value("${api.key}")
    private String apiKey;

    private WebClient webClient;

    @Autowired
    public void initializeWebClient() {
        this.webClient = webClientBuilder.build();
    }

    private static final String apiBaseUrl = "https://v3.football.api-sports.io";

    public void fetchAndSavePlayerRatingsForSerieA() {
        List<Fixture> fixtures = fixtureService.findAll();
        List<Team> teams = teamRepository.findAll();
        for (Fixture fixture : fixtures) {
            for (Team team : teams) {
                int teamApiId = team.getApiId().intValue();
                fetchAndSavePlayerRatingsForFixture(fixture, teamApiId);
            }
        }
    }

    private void fetchAndSavePlayerRatingsForFixture(Fixture fixture, int teamApiId) {
        System.out.println("Recupero dei dati dei giocatori per la partita: " + fixture.getId());
        String apiUrl = "https://v3.football.api-sports.io/fixtures/players?fixture=" + fixture.getId() + "&team=" + teamApiId;

        System.out.println("URL chiamata API: " + apiUrl);

        ApiResponseFixturePlayers response = this.webClient.get()
                .uri(apiUrl)
                .header("X-RapidAPI-Key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    System.out.println("Errore HTTP: " + clientResponse.statusCode());
                    return clientResponse.createException().flatMap(Mono::error);
                })
                .bodyToMono(ApiResponseFixturePlayers.class)
                .doOnNext(apiResponseFixturePlayers -> {
                    System.out.println("Corpo della risposta raw: " + apiResponseFixturePlayers);
                })
                .block();

        if (response == null || response.getResponse().isEmpty()) {
            System.out.println("La risposta dall'API è vuota o nulla.");
        } else {
            System.out.println("Risposta dall'API: " + response);
            savePlayerData(response, fixture, teamApiId);
        }
    }
    private void savePlayerData(ApiResponseFixturePlayers response, Fixture fixture, int teamId) {
        if (response != null && response.getResponse() != null) {
            for (ApiResponseFixturePlayers.ResponseData responseData : response.getResponse()) {
                List<ApiResponseFixturePlayers.PlayerData> playerDataList = responseData.getPlayers();
                if (playerDataList != null) {
                    for (ApiResponseFixturePlayers.PlayerData playerData : playerDataList) {
                        ApiResponseFixturePlayers.Player player = playerData.getPlayer();
                        if (player != null) {
                            String fullName = player.getFirstname() + " " + player.getLastname(); // Nome completo

                            System.out.println("Nome del giocatore: " + fullName);
                            System.out.println("URL della foto: " + player.getPhoto());

                            // Iterare sulle statistiche per ottenere il rating
                            List<ApiResponseFixturePlayers.Statistic> statistics = playerData.getStatistics();
                            if (statistics != null && !statistics.isEmpty()) {
                                for (ApiResponseFixturePlayers.Statistic statistic : statistics) {
                                    ApiResponseFixturePlayers.Games games = statistic.getGames();
                                    if (games != null) {
                                        System.out.println("Rating del giocatore: " + games.getRating());

                                        List<Giocatore> giocatori = giocatoreRepository.findByApiId((long) player.getId());
                                        Giocatore giocatore;
                                        if (giocatori.isEmpty()) {
                                            giocatore = new Giocatore();
                                            giocatore.setApiId(player.getId());
                                            giocatore.setNome(fullName);
                                            giocatore.setCognome(player.getLastname());
                                            giocatore.setEta(player.getAge());
                                            giocatore.setNazionalita(player.getNationality());
                                            giocatore.setPhotoUrl(player.getPhoto());
                                            giocatoreRepository.save(giocatore);
                                        } else {
                                            // Gestisci il caso in cui ci sono più giocatori con lo stesso apiId
                                            giocatore = giocatori.get(0); // Usa il primo giocatore trovato
                                        }

                                        Team team = teamRepository.findByApiId((long) teamId);
                                        if (team != null) {
                                            GiocatoriVotiNellePartite giocatoriVoti = new GiocatoriVotiNellePartite();
                                            giocatoriVoti.setGiocatore(giocatore);
                                            giocatoriVoti.setTeam(team);
                                            giocatoriVoti.setFixture(fixture);

                                            // Convertire la stringa in Double con il controllo di null
                                            String ratingStr = games.getRating();
                                            if (ratingStr != null && !ratingStr.trim().isEmpty()) {
                                                try {
                                                    giocatoriVoti.setRating(Double.parseDouble(ratingStr.trim()));
                                                } catch (NumberFormatException e) {
                                                    System.err.println("Rating non valido per il giocatore " + fullName + ": " + ratingStr);
                                                    giocatoriVoti.setRating(0.0); // Imposta un valore di default se il rating non è valido
                                                }
                                            } else {
                                                giocatoriVoti.setRating(0.0); // Imposta un valore di default se il rating è null o vuoto
                                            }

                                            giocatoriVotiNellePartiteRepository.save(giocatoriVoti);
                                        } else {
                                            System.out.println("Team non trovato per ID: " + teamId);
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Statistiche non disponibili per il giocatore: " + fullName);
                            }
                        } else {
                            System.out.println("Dati del giocatore non disponibili.");
                        }
                    }
                } else {
                    System.out.println("Nessun dato dei giocatori disponibile.");
                }
            }
        } else {
            System.out.println("Nessuna risposta o nessun dato dei giocatori disponibile.");
        }
    }

}











