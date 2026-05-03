package com.pena.banking_web_app.service;

import com.pena.banking_web_app.model.Transaction;
import com.pena.banking_web_app.model.User;
import com.pena.banking_web_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByAccount(User user){
        return transactionRepository.findByUser(user);
    }
    public  void updateAmount(User user, Double amount, String type, String toNumber,  String fromNumber ){
        transactionRepository.save(new Transaction(amount,type,toNumber,fromNumber,user));
    }

}
