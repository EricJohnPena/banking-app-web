package com.pena.banking_web_app.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private String type;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime date;
    private String to_number;
    private String from_number;
    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    public Transaction(Double amount, String type,String to_number, String from_number,  User user) {
        this.amount = amount;
        this.type = type;
        this.to_number = to_number;
        this.from_number = from_number;
        this.user = user;

    }

}
