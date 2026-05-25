package com.pena.banking_web_app.service;

import com.pena.banking_web_app.model.Account;
import com.pena.banking_web_app.model.User;
import com.pena.banking_web_app.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account findByUser(User user){
        return accountRepository.findByUser(user);
    }

    public boolean transact(Double amount, Account account){
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
        return true;
    }
    public void createAccount(User user){
        Account account = new Account(0.0, user);
        accountRepository.save(account);

    }



}
