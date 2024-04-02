package com.hateo.service;

import com.hateo.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    //Add Account
    public Account addAccount(Account account);
    //GetAll Account
    public List<Account> getAllAccounts();
    //get Single Account by id
    public ResponseEntity<?> getSingleAccount(int id);
    //Deposit into Account Balance
    public ResponseEntity<?>deposit(int id,double balance);
    //Withdrawal from an Account
    public ResponseEntity<?> withdrawal(int id,double bal);
    //Delete a Specific Account
    public ResponseEntity<?> deleteAccount(int id);
}
