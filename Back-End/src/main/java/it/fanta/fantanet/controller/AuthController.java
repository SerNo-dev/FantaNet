package it.fanta.fantanet.controller;


import it.fanta.fantanet.Dto.UserDto;
import it.fanta.fantanet.Dto.UserLoginDto;
import it.fanta.fantanet.service.AuthService;
import it.fanta.fantanet.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;


    @PostMapping("/auth/register")
    public String register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).reduce("",((s, s2) -> s+s2)));
        }
        return userService.saveUser(userDto);
    }

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody @Validated UserLoginDto userLoginDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error->error.getDefaultMessage()).reduce("",(s, s2) -> s+s2));
        }
        return authService.authenticateUserAndCreateToken(userLoginDto);
    }

}
