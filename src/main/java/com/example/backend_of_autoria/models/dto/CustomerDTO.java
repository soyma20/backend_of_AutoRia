package com.example.backend_of_autoria.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {
    private String login;
    private String password;
    private String email;

}
