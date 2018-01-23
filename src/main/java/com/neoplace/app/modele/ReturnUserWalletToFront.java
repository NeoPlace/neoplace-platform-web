package com.neoplace.app.modele;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Data
public class ReturnUserWalletToFront {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    private String trigramCrypto;
    private String publicKey;
    private String nameWalletJsonEthOrBtcName;
    private BigInteger balanceWallet;
    private String loginAccount;
    private String labelName;
    @ElementCollection
    private List<TransactionBean> listTransaction;
    
    
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public List<TransactionBean> getListTransaction() {
		return listTransaction;
	}
	public void setListTransaction(List<TransactionBean> listTransaction) {
		this.listTransaction = listTransaction;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTrigramCrypto() {
		return trigramCrypto;
	}
	public void setTrigramCrypto(String trigramCrypto) {
		this.trigramCrypto = trigramCrypto;
	}
	public String getNameWalletJsonEthOrBtcName() {
		return nameWalletJsonEthOrBtcName;
	}
	public void setNameWalletJsonEthOrBtcName(String nameWalletJsonEthOrBtcName) {
		this.nameWalletJsonEthOrBtcName = nameWalletJsonEthOrBtcName;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public BigInteger getBalanceWallet() {
		return balanceWallet;
	}
	public void setBalanceWallet(BigInteger balanceWallet) {
		this.balanceWallet = balanceWallet;
	}

}
