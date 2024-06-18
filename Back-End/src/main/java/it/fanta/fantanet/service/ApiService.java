package it.fanta.fantanet.service;


import it.fanta.fantanet.models.ApiResponse;
import it.fanta.fantanet.models.Giocatore;
import it.fanta.fantanet.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Value("${api.base-url}")
    private String apiBaseUrl;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.teams-endpoint}")
    private String teamsEndpoint;

    @Value("${api.players-endpoint}")
    private String playersEndpoint;

    @Autowired
    private TeamAndVenueService teamAndVenueService;

    @Autowired
    private GiocatoreService giocatoreService;

    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void fetchAndSaveTeams() {
        ApiResponse response = this.webClient.get()
                .uri(apiBaseUrl + teamsEndpoint)
                .header("X-RapidAPI-Key", apiKey)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        if (response != null) {
            for (ApiResponse.ResponseData data : response.getResponse()) {
                ApiResponse.ApiTeam apiTeam = data.getTeam();
                Team team = apiTeam.toModelTeam();
                teamAndVenueService.saveTeamAndVenue(team, data.getVenue());
            }
        }
    }



    private static final int PLAYERS_PER_PAGE = 20;
    private int currentPage = 1;

    public void fetchAndSaveGiocatori() {
        fetchAndSaveGiocatoriForPage(currentPage);
    }

    private void fetchAndSaveGiocatoriForPage(int page) {
        String apiUrl = apiBaseUrl + playersEndpoint + "?league=135&season=2023&page=" + page;
        System.out.println("Fetching data for page: " + page);
        System.out.println("API URL: " + apiUrl); // Stampa l'URL dell'API

        ApiResponse response = this.webClient.get()
                .uri(apiUrl)
                .header("X-RapidAPI-Key", apiKey)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        if (response != null) {
            System.out.println("API Response: " + response); // Stampa la risposta completa dell'API

            List<ApiResponse.ResponseData> players = response.getResponse();
            if (players != null && !players.isEmpty()) {
                System.out.println("Received players for page " + page + ": " + players.size());
                List<Giocatore> giocatori = players.stream()
                        .map(data -> {
                            Giocatore giocatore = new Giocatore();
                            giocatore.setNome(data.getPlayer().getFirstname());
                            giocatore.setCognome(data.getPlayer().getLastname());
                            giocatore.setApiId(data.getPlayer().getId());
                            giocatore.setPhotoUrl(data.getPlayer().getPhoto());
                            giocatore.setNazionalita(data.getPlayer().getNationality());
                            giocatore.setEta(data.getPlayer().getAge());
                            System.out.println("Player ID: " + data.getPlayer().getId());

                            if (data.getStatistics() != null && !data.getStatistics().isEmpty()) {
                                ApiResponse.Game gameStats = data.getStatistics().get(0).getGames();
                                giocatore.setPosizione(gameStats.getPosition());
                                Long apiTeamId = (long) data.getStatistics().get(0).getTeam().getId();
                                Team databaseTeam = teamAndVenueService.findTeamByApiId(apiTeamId);
                                giocatore.setTeam(databaseTeam);
                            }
                            giocatore.setPrezzo(calcolaPrezzo(giocatore));
                            return giocatore;
                        })
                        .collect(Collectors.toList());

                giocatoreService.saveGiocatori(giocatori);
                int currentPage = response.getPaging().getCurrent();
                int totalPages = response.getPaging().getTotal();
                System.out.println("Current page: " + currentPage + " of " + totalPages);

                if (currentPage < totalPages) {
                    fetchAndSaveGiocatoriForPage(currentPage + 1);
                }
            } else {
                System.out.println("No players found in the response.");
            }
        } else {
            System.out.println("No response from API.");
        }
    }


    private int calcolaPrezzo(Giocatore giocatore) {

        return 100;
    }


    }



