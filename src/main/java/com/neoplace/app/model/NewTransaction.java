package com.neoplace.app.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class NewTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String trigram;
	private String loginAccount;
    private String fromAddressPublicKey;
    private String fromFileNameJsonETH;
    private String to;
    private String fromPasswordAccount;
    private String valueToSend;
    private String labelMessage;

    public NewTransaction(String from, String trigram, String fromFileName, String to, String fromPassword, String valueToSend) {
    	this.fromAddressPublicKey = from;
    	this.trigram = trigram;
    	this.fromFileNameJsonETH = fromFileName;
    	this.to=to;
    	this.fromPasswordAccount = fromPassword;
    	this.valueToSend=valueToSend;
	}

	public NewTransaction() {
	}
	
	
	

	public String getFromAddressPublicKey() {
		return fromAddressPublicKey;
	}

	public void setFromAddressPublicKey(String fromAddressPublicKey) {
		this.fromAddressPublicKey = fromAddressPublicKey;
	}

	public String getFromFileNameJsonETH() {
		return fromFileNameJsonETH;
	}

	public void setFromFileNameJsonETH(String fromFileNameJsonETH) {
		this.fromFileNameJsonETH = fromFileNameJsonETH;
	}

	public String getFromPasswordAccount() {
		return fromPasswordAccount;
	}

	public void setFromPasswordAccount(String fromPasswordAccount) {
		this.fromPasswordAccount = fromPasswordAccount;
	}

	public String getLabelMessage() {
		return labelMessage;
	}

	public void setLabelMessage(String labelMessage) {
		this.labelMessage = labelMessage;
	}

	public String getTrigram() {
		return trigram;
	}

	public void setTrigram(String trigram) {
		this.trigram = trigram;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String login) {
		this.loginAccount = login;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrom() {
		return fromAddressPublicKey;
	}

	public void setFrom(String from) {
		this.fromAddressPublicKey = from;
	}

	public String getFromFileNameJson() {
		return fromFileNameJsonETH;
	}

	public void setFromFileNameJson(String fromFileNameJson) {
		this.fromFileNameJsonETH = fromFileNameJson;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFromPassword() {
		return fromPasswordAccount;
	}

	public void setFromPassword(String fromPassword) {
		this.fromPasswordAccount = fromPassword;
	}

	public String getValueToSend() {
		return valueToSend;
	}

	public void setValueToSend(String valueToSend) {
		this.valueToSend = valueToSend;
	}

}
