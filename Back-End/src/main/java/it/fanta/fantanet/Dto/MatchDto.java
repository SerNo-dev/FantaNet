package it.fanta.fantanet.Dto;

import it.fanta.fantanet.models.Utente;
import lombok.Data;

@Data
public class MatchDto {
    private Long user1Id;
    private Long user2Id;
    private double user1Score;
    private double user2Score;
    private Long winnerId;
}
