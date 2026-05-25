package com.pena.banking_web_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String number;
    private String pin;


    public User(String username, String email, String number, String pin) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.pin = pin;
    }
}
