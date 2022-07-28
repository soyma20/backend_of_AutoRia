package com.example.backend_of_autoria.security.filters;

import com.example.backend_of_autoria.dao.CustomerDAO;
import com.example.backend_of_autoria.models.Customer;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainFilter extends OncePerRequestFilter {

    private CustomerDAO customerDAO;

    public MainFilter(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.replace("Bearer ", "");

            String subject = Jwts.parser()
                    .setSigningKey("soima".getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            Customer customer = customerDAO.findByUsername(subject);
            if (customer != null) {
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(
                                customer.getUsername(),
                                customer.getPassword(),
                                customer.getAuthorities()
                        ));

            }

        }
        filterChain.doFilter(request, response);

    }
}
