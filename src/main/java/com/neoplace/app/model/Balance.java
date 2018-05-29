package com.neoplace.app.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Balance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String trigram;
	private String fromAdress;
	private String login;
	private String amountWallet;
	private String passwordToDecryptBTC;
	
	
	
	public String getPasswordToDecryptBTC() {
		return passwordToDecryptBTC;
	}
	public void setPasswordToDecryptBTC(String passwordToDecryptBTC) {
		this.passwordToDecryptBTC = passwordToDecryptBTC;
	}
	public String getTrigram() {
		return trigram;
	}
	public void setTrigram(String trigram) {
		this.trigram = trigram;
	}
	public String getAmountWallet() {
		return amountWallet;
	}
	public void setAmountWallet(String amountWallet) {
		this.amountWallet = amountWallet;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAmount() {
		return amountWallet;
	}
	public void setAmount(String amount) {
		this.amountWallet = amount;
	}
	public String getFromAdress() {
		return fromAdress;
	}
	public void setFromAdress(String fromAdress) {
		this.fromAdress = fromAdress;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
