package com.exam.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.entity.Transaction;
import com.exam.entity.User;
import com.exam.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    public String transferMoney(String senderUsername, String receiverUsername, Double amount) {
        Optional<User> senderOpt = userService.findByUsername(senderUsername);
        Optional<User> receiverOpt = userService.findByUsername(receiverUsername);

        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            User sender = senderOpt.get();
            User receiver = receiverOpt.get();

            if (sender.getBalance() >= amount) {
                // Deduct money from sender
                sender.setBalance(sender.getBalance() - amount);
                // Add money to receiver
                receiver.setBalance(receiver.getBalance() + amount);

                // Update user balances
                userService.updateBalance(sender, sender.getBalance());
                userService.updateBalance(receiver, receiver.getBalance());

                // Save transaction
                Transaction transaction = new Transaction();
                transaction.setSender(sender);
                transaction.setReceiver(receiver);
                transaction.setAmount(amount);
                transaction.setStatus("SUCCESS");
                transaction.setReceiverUsername(receiverUsername);
                transaction.setSenderUsername(senderUsername);
                transaction.setDate(LocalDateTime.now()); // Set current date and time
                transactionRepository.save(transaction);

                return "Transaction successful!";
            } else {
                return "Insufficient funds.";
            }
        } else {
            return "Sender or Receiver not found.";
        }
    }

    public List<Transaction> getTransactions(User user) {
        List<Transaction> transactions = transactionRepository.findBySenderOrReceiver(user, user);
        List<Transaction> transactionDTOs = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Transaction dto = new Transaction();
            dto.setId(transaction.getId());
            dto.setAmount(transaction.getAmount());

            // Safely get sender username and balance, check for null
            if (transaction.getSender() != null) {
                dto.setSenderUsername(transaction.getSender().getUsername());
                dto.setSenderBalance(transaction.getSender().getBalance()); // Include sender balance
            } else {
                dto.setSenderUsername("N/A");
                dto.setSenderBalance(0.0);  // Default value if sender is null
            }

            // Safely get receiver username and balance, check for null
            if (transaction.getReceiver() != null) {
                dto.setReceiverUsername(transaction.getReceiver().getUsername());
                dto.setReceiverBalance(transaction.getReceiver().getBalance()); // Include receiver balance
            } else {
                dto.setReceiverUsername("N/A");
                dto.setReceiverBalance(0.0);  // Default value if receiver is null
            }
            
            // Set the date in the DTO
            dto.setDate(transaction.getDate());  // Assign the date from the original transaction

            transactionDTOs.add(dto);
        }

        return transactionDTOs;
    }


}
