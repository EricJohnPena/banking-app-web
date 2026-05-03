package com.pena.banking_web_app.repository;

import com.pena.banking_web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
 User findByUsername(String name);
 User findByNumber(String number);
 boolean existsByNumber(String number);
}
