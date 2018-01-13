package com.neoplace.app.modele;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NewAccountInput {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String trigram;
    private String loginAccount;
    private String passwordAccount;
    private String publicKeyAdress;
    
    
    
	public String getTrigram() {
		return trigram;
	}
	public void setTrigram(String trigram) {
		this.trigram = trigram;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getPasswordAccount() {
		return passwordAccount;
	}
	public void setPasswordAccount(String passwordAccount) {
		this.passwordAccount = passwordAccount;
	}
	public String getPublicKeyAdress() {
		return publicKeyAdress;
	}
	public void setPublicKeyAdress(String publicKeyAdress) {
		this.publicKeyAdress = publicKeyAdress;
	}
    
    
}
