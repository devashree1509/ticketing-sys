package com.devashree.ticketing.controller;
import com.devashree.ticketing.dto.AuthRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthController(AuthenticationManager authenticationManager,JwtEncoder jwtEncoder){
        this.authenticationManager=authenticationManager;
        this.jwtEncoder=jwtEncoder;
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody AuthRequest request){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        Instant now = Instant.now();

        JwtClaimsSet claims=JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(86400))
                .subject(request.getEmail())
                .build();

        String token = jwtEncoder.encode
                (JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS256).build(),
                                claims)
                ).getTokenValue();

        return Map.of("token",token);
    }
}
