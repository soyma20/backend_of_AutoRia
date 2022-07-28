package com.example.backend_of_autoria.controllers;

import com.example.backend_of_autoria.dao.CustomerDAO;
import com.example.backend_of_autoria.models.Customer;
import com.example.backend_of_autoria.models.dto.CustomerDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@AllArgsConstructor
public class CustomerController {

    private PasswordEncoder passwordEncoder;
    private CustomerDAO customerDAO;
    private AuthenticationManager authenticationManager;

    @GetMapping("/open")
    public String open() {
        return "open";
    }

    @GetMapping("/secure")
    public String secure() {
        return "secure url";
    }

    @PostMapping("/save")
    public void save(@RequestBody CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setUsername(customerDTO.getLogin());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setEmail(customerDTO.getEmail());
        customerDAO.save(customer);

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomerDTO customerDTO) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customerDTO.getLogin(), customerDTO.getPassword()));
        if (authenticate != null) {

            Date date = new Date();
            date.setTime(date.getTime() + 1000000);

            String jwToken = Jwts.builder()
                    .setSubject(authenticate.getName())
                    .signWith(SignatureAlgorithm.HS512, "soima".getBytes())
                    .setExpiration(date)
                    .compact();

            System.out.println(jwToken);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwToken);
            return new ResponseEntity<>("you are loged in", httpHeaders, HttpStatus.OK);

        }

        return new ResponseEntity<>("eroro in login", HttpStatus.UNAUTHORIZED);


    }
}