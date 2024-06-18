package it.fanta.fantanet.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiocatoriVotiDto {
    private Long id;
    private int giocatoreId;
    private int teamId;
    private int fixtureId;
    private String rating;
}
