package com.pena.banking_web_app.repository;

import com.pena.banking_web_app.model.Transaction;
import com.pena.banking_web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUser(User user);
}
