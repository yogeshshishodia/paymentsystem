package com.exam.entity;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String status;
    private String senderUsername;
    private String receiverUsername;
    private LocalDateTime date;
    
    private Double senderBalance;
    private Double receiverBalance;

    // Getters and setters for the balances
    public Double getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(Double senderBalance) {
        this.senderBalance = senderBalance;
    }

    public Double getReceiverBalance() {
        return receiverBalance;
    }

    public void setReceiverBalance(Double receiverBalance) {
        this.receiverBalance = receiverBalance;
    }

    @ManyToOne
    @JsonIgnoreProperties({"sentTransactions", "receivedTransactions"})
    private User sender;

    @ManyToOne
    @JsonIgnoreProperties({"sentTransactions", "receivedTransactions"})
    private User receiver;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

    // Getters and Setters
    
    
}
