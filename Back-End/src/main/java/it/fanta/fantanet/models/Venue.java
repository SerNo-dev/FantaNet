package it.fanta.fantanet.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Venue {

    @Id
    private Long id;
    private String name;
    private String address;
    private String city;
    private int capacity;
    private String surface;
    private String image;
}
