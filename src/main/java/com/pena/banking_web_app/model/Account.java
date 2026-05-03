package com.pena.banking_web_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double balance;
    @OneToOne
    @JoinColumn(name = "fk_user_id")
    private User user;
}
