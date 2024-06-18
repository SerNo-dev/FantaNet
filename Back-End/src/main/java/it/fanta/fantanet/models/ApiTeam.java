package it.fanta.fantanet.models;


import lombok.Data;

@Data
public class ApiTeam {   private int id;
    private String name;
    private String logo;
    private String code;
    private String country;


    public Team toTeam() {
        Team team = new Team();
        team.setApiId((long) this.id);
        team.setName(this.name);
        team.setLogo(this.logo);
        team.setCode(this.code);
        team.setCountry(this.country);
        return team;
    }

}
