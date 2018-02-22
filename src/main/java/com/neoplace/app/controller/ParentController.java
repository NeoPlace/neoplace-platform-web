package com.neoplace.app.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;

import com.neoplace.app.modele.ReturnUserWalletToFront;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

import com.neoplace.app.modele.NewTransaction;

@RestController
public class ParentController {


	@Autowired
	EthereumController ethereumController;

	@Autowired
	BitcoinController bitcoinController;


	@RequestMapping(method=RequestMethod.POST, value="/createNewAccount")
	public ReturnUserWalletToFront createNewAccountAndReturnPublicKey(@RequestBody final NewAccountInput newAccount, Principal principal) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException {
		if(!principal.getName().equals(newAccount.getLoginAccount())) {
			return null;
		}
		if ("BTC".equals(newAccount.getTrigram())) {
			try {
				return bitcoinController.generateWallet(newAccount);
			} catch (UnreadableWalletException | BlockStoreException e) {
			}
		} 
		else if ("ETH".equals(newAccount.getTrigram())) {
			return ethereumController.createNewAccountAndReturnPublicKey(newAccount); 
		}
		return null;

	}

	@RequestMapping(method=RequestMethod.POST, value="/createNewTransaction")
	public ReturnUserWalletToFront sendTransaction(@RequestBody final NewTransaction newTransaction) throws Exception{
		if ("ETH".equals(newTransaction.getTrigram())) {
			return ethereumController.sendTransaction(newTransaction); 
		} 
		else if ("BTC".equals(newTransaction.getTrigram())) {
			return bitcoinController.sendTransaction(newTransaction);
		}
		return null;
	}
}
