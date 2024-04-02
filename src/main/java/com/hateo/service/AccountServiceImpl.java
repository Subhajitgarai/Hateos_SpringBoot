package com.hateo.service;

import com.hateo.customException.CustomException;
import com.hateo.entity.Account;
import com.hateo.repository.AccountRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class AccountServiceImpl implements AccountService{

    private final AccountRepo accountRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    //Add an Account
    public Account addAccount(Account account){
         return accountRepo.save(account);
    }
    //Get All Account details
    public List<Account> getAllAccounts(){
        System.out.println("Getting all Account Details");
        return accountRepo.findAll();

    }
    //get Single Account by Id
    public ResponseEntity<?> getSingleAccount(int id){
        Optional<Account> byId = accountRepo.findById(id);
        if (byId.isEmpty()){
            return new ResponseEntity<>("Invalid Id", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(byId.get(),HttpStatus.OK);
        }
    }
    //Deposit into Account Balance
    public ResponseEntity<?>deposit(int id,double balance){
        Optional<Account> byId = accountRepo.findById(id);
        if (byId.isEmpty()){
            return new ResponseEntity<>("Invalid Id",HttpStatus.NOT_FOUND);
        }
        else {
            Account account = byId.get();
            double currentBal=account.getBalance();
            double updatedBalance=currentBal+balance;
            account.setBalance(updatedBalance);
            System.out.println("Depositing");
            return new ResponseEntity<>(accountRepo.save(account),HttpStatus.OK);
        }
    }
    //Withdrawal from an Account
    public ResponseEntity<?> withdrawal(int id,double bal){
        Optional<Account> byId = accountRepo.findById(id);
        if (byId.isEmpty()){
            return new ResponseEntity<>("Invalid Id",HttpStatus.NOT_FOUND);
        }
        else {
            Account account = byId.get();
            if (bal>account.getBalance()){
              throw new CustomException("Low Balance");
            }
            double currentBal=account.getBalance();
            double updatedBalance=currentBal-bal;
            account.setBalance(updatedBalance);
            System.out.println("withdrawal");
            return new ResponseEntity<>(accountRepo.save(account),HttpStatus.OK);
        }
    }
    //Delete a Specific Account
    public ResponseEntity<?> deleteAccount(int id){
        Optional<Account> byId = accountRepo.findById(id);
        if (byId.isEmpty()){
            return new ResponseEntity<>("Account Does not Exists !!",HttpStatus.NOT_FOUND);
        }
        else {
            accountRepo.deleteById(id);
            return new ResponseEntity<>("Account information's deleted!!",HttpStatus.OK);

        }
    }
}
