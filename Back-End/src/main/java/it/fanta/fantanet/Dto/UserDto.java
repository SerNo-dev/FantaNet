package it.fanta.fantanet.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message="L'username non può essere null ovuoto o solo spazi")
    private String username;
    @NotBlank(message = "La password non può essere vuota o mancante o composta da soli spazi'")
    private String password;
    @Email
    @NotBlank(message = "l'email non può essere null o vuota o solo spazi")
    private String email;

    private String avatarUrl;
}
