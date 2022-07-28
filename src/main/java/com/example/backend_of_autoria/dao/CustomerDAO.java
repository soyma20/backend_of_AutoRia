package com.example.backend_of_autoria.dao;

import com.example.backend_of_autoria.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);
}
