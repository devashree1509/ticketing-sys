package com.devashree.ticketing.controller;
import com.devashree.ticketing.dto.AuthRequest;
import com.devashree.ticketing.entity.User;

import com.devashree.ticketing.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,JwtEncoder jwtEncoder,UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.authenticationManager=authenticationManager;
        this.jwtEncoder=jwtEncoder;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody AuthRequest request){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        User user= userRepository.findByEmail(request.getEmail()).orElseThrow();
        Instant now = Instant.now();

        JwtClaimsSet claims=JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(86400))
                .subject(request.getEmail())
                .claim("role",user.getRole().name())
                .build();

        String token = jwtEncoder.encode
                (JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS256).build(),
                                claims)
                ).getTokenValue();

        return Map.of("token",token);
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
