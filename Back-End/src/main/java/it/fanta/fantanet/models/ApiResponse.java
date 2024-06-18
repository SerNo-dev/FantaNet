package it.fanta.fantanet.models;


import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {

    private List<ResponseData> response;
    private Paging paging;
    private List<Player> players;


    @Override
    public String toString() {
        return "ApiResponse{" +
                "response=" + response +
                ", paging=" + paging +
                ", players=" + players +
                '}';
    }


    @Data
    public static class ResponseData {
        private ApiTeam team;
        private Venue venue;
        private Player player;
        private List<Statistic> statistics;
        private Fixture fixture;
        private Teams teams;

        private List<Partita> partita;

    }

    public static class Partita {
        private long rating;

    }

    @Data
    public static class ApiTeam {
        private int id;
        private String name;
        private String logo;
        private String code;
        private int founded;
        private String country;


        public Team toModelTeam() {
            Team modelTeam = new Team();
            modelTeam.setApiId((long) this.id);
            modelTeam.setName(this.name);
            modelTeam.setLogo(this.logo);
            modelTeam.setCode(this.code);
            modelTeam.setFounded(this.founded);
            modelTeam.setCountry(this.country);

            return modelTeam;
        }
    }

    @Data
    public static class Fixture {
        private int id;
        private String date;
    }

    @Data
    public static class Player {
        private int id;
        private String firstname;
        private String lastname;
        private int age;
        private String nationality;
        private String photo;
        private List<Statistic> statistics;
    }


    @Data
    public static class Statistic {
        private Game games;
        private ApiTeam team;

    }

    @Data
    public static class Game {
        private String position;

        private int minutes;
        private int number;
        private String rating;
        private boolean captain;
        private boolean substitute;


    }


    @Data
        public static class Paging {
        private int current;
        private int total;

    }

    @Data
    public static class Teams {
        private ApiTeam home;
        private ApiTeam away;

    }

}