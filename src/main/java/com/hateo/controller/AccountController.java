package com.hateo.controller;

import com.hateo.entity.Account;
import com.hateo.repository.AccountRepo;
import com.hateo.service.AccountService;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;
    private final AccountRepo accountRepo;

    public AccountController(AccountService accountService, AccountRepo accountRepo) {
        this.accountService = accountService;
        this.accountRepo = accountRepo;
    }

    //Get All Account Information
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccount() {
        List<Account> allAcc = accountRepo.findAll();
        allAcc.forEach(account -> {
            //Self Link
            Link selfLink = WebMvcLinkBuilder.linkTo(AccountController.class).slash("accounts").slash(account.getId()).withSelfRel().withType("GET");
            account.add(selfLink);
            //Adding Deposit Link
            Link depositLink = linkTo(methodOn(AccountController.class).depositAmount(account.getId(), 0)).withRel("Deposit").withType("PATCH");
            depositLink = depositLink.withHref(depositLink.getHref() + "?balance={balance}");
            account.add(depositLink);
            //Adding Withdrawal Link
            Link withdrawLink = linkTo(methodOn(AccountController.class).WithdrawalAmount(account.getId(), 0)).withRel("Withdraw").withType("PATCH");
            withdrawLink = withdrawLink.withHref(withdrawLink.getHref() + "?balance={balance}");
            account.add(withdrawLink);
            //Delete Link Add
            Link deleteLink = linkTo(methodOn(AccountController.class).deleteAccount(account.getId())).withRel("Delete").withType("DELETE");
            account.add(deleteLink);

        });

        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    //Get Specific Account
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getSpecificAccount(@PathVariable int id) {
        Optional<Account> byId = accountRepo.findById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>("Invalid Id!", HttpStatus.NOT_FOUND);
        } else {
            Account account = byId.get();
            Link selfLink = WebMvcLinkBuilder.linkTo(AccountController.class).slash("accounts").slash(account.getId()).withSelfRel().withType("GET");
            account.add(selfLink);
            Link depositLink = linkTo(methodOn(AccountController.class).depositAmount(account.getId(), 0)).withRel("Deposit").withType("PATCH");
            depositLink = depositLink.withHref(depositLink.getHref() + "?balance={balance}");
            account.add(depositLink);

            //Withdrawal Link
            Link withdrawLink = linkTo(methodOn(AccountController.class).WithdrawalAmount(account.getId(), 0)).withRel("Withdraw").withType("PATCH");
            withdrawLink = withdrawLink.withHref(withdrawLink.getHref() + "?balance={balance}");
            account.add(withdrawLink);
            //Delete Link Add
            Link deleteLink = linkTo(methodOn(AccountController.class).deleteAccount(account.getId())).withRel("Delete").withType("DELETE");
            account.add(deleteLink);
            return accountService.getSingleAccount(id);
        }

    }

    //Add an Account
    @PostMapping("/accounts")
    public Account addAccount(@RequestBody Account account) {
        Account addedAcc = accountService.addAccount(account);
        Link selfLink = WebMvcLinkBuilder.linkTo(AccountController.class).slash("accounts").slash(addedAcc.getId()).withSelfRel().withType("GET");
        account.add(selfLink);
        //Adding Deposit Link
        Link depositLink = linkTo(methodOn(AccountController.class).depositAmount(addedAcc.getId(), 0)).withRel("Deposit").withType("PATCH");
        depositLink = depositLink.withHref(depositLink.getHref() + "?balance={balance}");
        account.add(depositLink);
        //Adding Withdrawal Link
        Link withdrawLink = linkTo(methodOn(AccountController.class).WithdrawalAmount(addedAcc.getId(), 0)).withRel("Withdraw").withType("PATCH");
        withdrawLink = withdrawLink.withHref(withdrawLink.getHref() + "?balance={balance}");
        account.add(withdrawLink);
        //Delete Link Add
        Link deleteLink = linkTo(methodOn(AccountController.class).deleteAccount(account.getId())).withRel("Delete").withType("DELETE");
        account.add(deleteLink);
        return addedAcc;
    }


    //Deposit
    @PatchMapping("accounts/{id}/deposit")
    public ResponseEntity<?> depositAmount(@PathVariable int id, @Param("balance") double balance) {
        return accountService.deposit(id, balance);
    }

    //Withdrawal amount
    @PatchMapping("accounts/{id}/withdraw")
    public ResponseEntity<?> WithdrawalAmount(@PathVariable int id, @Param("balance") int balance) {
        return accountService.withdrawal(id, balance);
    }

    //Delete an account
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) {
        return accountService.deleteAccount(id);
    }

    @GetMapping("/test1")
    public String test() {
        return "Hello Test !";
    }

    @GetMapping("/jenkins")
    public String jenkinsTest() {
        return "Congratulation from jenkins 3,2,1!!";
    }


}
