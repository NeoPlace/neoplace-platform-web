package com.neoplace.app.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class TransactionBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String amount;
	public String date;
	private String fromAdress;
	private String to;
	private String gasUsed;
	private String transactionHash;
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFrom() {
		return fromAdress;
	}
	public void setFrom(String from) {
		this.fromAdress = from;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	public String getTransactionHash() {
		return transactionHash;
	}
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	
	
	
}
