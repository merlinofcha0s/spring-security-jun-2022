package fr.plb.springsecuritydemo.web.rest;

import fr.plb.springsecuritydemo.service.UserService;
import fr.plb.springsecuritydemo.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final UserService userService;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public AccountResource(UserService userService,
                           AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerAccount(@Valid @RequestBody UserDTO userDTO) {
       return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public UserDTO login(@Valid @RequestBody UserDTO userDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDTO.getLogin(),
                userDTO.getPassword()
        );

        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return userService.registerUser(userDTO);
    }

}
