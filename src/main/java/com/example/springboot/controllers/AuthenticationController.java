package com.example.springboot.controllers;


import com.example.springboot.dtos.AutheticationDto;
import com.example.springboot.dtos.LoginResponseDto;
import com.example.springboot.dtos.RegisterDto;
import com.example.springboot.infra.security.TokenService;
import com.example.springboot.models.user.UserModel;
import com.example.springboot.repositories.UserRepository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutheticationDto autheticationDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                autheticationDto.login(),
                autheticationDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());


        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto registerDto){
        if(userRepository.findByLogin(registerDto.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        UserModel newUser = new UserModel(registerDto.login(),encryptedPassword, registerDto.role());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}

