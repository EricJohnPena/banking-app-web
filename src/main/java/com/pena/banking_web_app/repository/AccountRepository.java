package com.pena.banking_web_app.repository;

import com.pena.banking_web_app.model.Account;
import com.pena.banking_web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUser(User user);
}
