package com.exam.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.Transaction;
import com.exam.entity.User;
import com.exam.service.TransactionService;
import com.exam.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	
	@Autowired
	private UserService userService;
	
	
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam String senderUsername,
                                               @RequestParam String receiverUsername,
                                               @RequestParam Double amount) {
        String response = transactionService.transferMoney(senderUsername, receiverUsername, amount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/history/{username}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            List<Transaction> transactions = transactionService.getTransactions(user.get());
            
            // Log for debugging purposes
            transactions.forEach(transaction -> {
                System.out.println("Transaction ID: " + transaction.getId());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Sender Username: " + transaction.getSenderUsername());
                System.out.println("Receiver Username: " + transaction.getReceiverUsername());
//                System.out.println("Sender Balance: " + transaction.getSenderBalance());
                System.out.println("Transaction Sender: " + transaction.getSender());
                System.out.println("Date: " + transaction.getDate());
                
                System.out.println("Sender Balance: " + (transaction.getSender() != null ? transaction.getSender().getBalance() : "N/A"));
            });

            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    }
