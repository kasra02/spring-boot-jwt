package com.youtube.jwt.controller;

import com.youtube.jwt.entity.JwtRequest;
import com.youtube.jwt.entity.JwtResponse;
import com.youtube.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Value("${own.app.jwtCookieName}")
    private String jwtCookie;

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate2"})
    public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);

        ResponseCookie accessToken = ResponseCookie.from(jwtCookie, jwtResponse.getJwtToken().toString()).path("/authenticate2").build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken.toString())
                .body(jwtResponse)
                ;
    }
}
