package it.fanta.fantanet.ApiFixturesPlayer;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponseFixturePlayers {
    private List<ResponseData> response;


    @Data
    public static class ResponseData {
        private Team team;
        private List<PlayerData> players;
    }

    @Override
    public String toString() {
        return "ApiResponse22{" +
                "response=" + response +
                '}';
    }

    @Data
    public static class Team {
        private int id;
        private String name;
        private String logo;
        private String update;

    }

    @Data
    public static class PlayerData {
        private Player player;
        private List<Statistic> statistics;

    }

    @Data
    public static class Player {
        private int id;
        private String name;
        private String photo;
        private String firstname;
        private String lastname;
        private int age;
        private String nationality;

    }

    @Data
    public static class Statistic {
        private Games games;
        private Integer offsides;
        private Shots shots;
        private Goals goals;
        private Passes passes;
        private Tackles tackles;
        private Duels duels;
        private Dribbles dribbles;
        private Fouls fouls;
        private Cards cards;
        private Penalty penalty;

    }

    @Data
    public static class Games {
        private int minutes;
        private int number;
        private String position;
        private String rating;
        private boolean captain;
        private boolean substitute;

    }

    public static class Shots {
        private Integer total;
        private Integer on;

    }

    @Data
    public static class Goals {
        private Integer total;
        private Integer conceded;
        private Integer assists;
        private Integer saves;

    }

    @Data
    public static class Passes {
        private Integer total;
        private Integer key;
        private String accuracy;

    }

    @Data
    public static class Tackles {
        private Integer total;
        private Integer blocks;
        private Integer interceptions;


    }

    @Data
    public static class Duels {
        private Integer total;
        private Integer won;

    }

    @Data
    public static class Dribbles {
        private Integer attempts;
        private Integer success;
        private Integer past;


    }

    @Data
    public static class Fouls {
        private Integer drawn;
        private Integer committed;

    }

    @Data
    public static class Cards {
        private Integer yellow;
        private Integer red;


    }

    @Data
    public static class Penalty {
        private Integer won;
        private Integer commited;
        private Integer scored;
        private Integer missed;
        private Integer saved;

    }
}
