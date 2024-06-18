package it.fanta.fantanet.service;

import it.fanta.fantanet.Dto.UserLoginDto;
import it.fanta.fantanet.exception.UnauthorizedException;
import it.fanta.fantanet.models.Utente;
import it.fanta.fantanet.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Map<String, Object> authenticateUserAndCreateToken(UserLoginDto userLoginDto){
        Utente user = userService.getUserByEmail(userLoginDto.getEmail());

        // Controlla la password codificata con quella fornita dall'utente
        if(passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            String token = jwtTool.createToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", token);
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("avatar",user.getAvatarUrl());
            response.put("crediti", user.getCrediti());
            response.put("id", user.getId());
            response.put("carrello", user.getCarrello());
            response.put("deck", user.getDeck());


            return response;
        } else {
            throw new UnauthorizedException("Error in authorization, relogin!");
        }
    }
}

