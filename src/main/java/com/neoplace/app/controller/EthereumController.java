package com.neoplace.app.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.neoplace.app.model.ReturnUserWalletToFront;

import com.neoplace.app.model.NewTransaction;
import com.neoplace.app.service.EthereumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

@RestController

public class EthereumController {

	@Autowired
	private EthereumService ethereumService;

	@RequestMapping(value="/getClientVersion", method = RequestMethod.GET)
	public String getClientVersion() throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InterruptedException, ExecutionException, CipherException {
		return ethereumService.getClientVersion();
	}
	
	/*
	 * return adress of portfolio with password in input
	 */
	public ReturnUserWalletToFront createNewAccountAndReturnPublicKey(@RequestBody final NewAccountInput newAccount) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException {
		return ethereumService.createNewAccountWithWeb3j(newAccount);
	}
	/*
	 * return all acount adresses on node Ethereum client used
	 */
	@RequestMapping("/getListAccount")
	public List<String> getListAccount() throws IOException{
		return ethereumService.getPersonalListAccounts();
	}

	/*
	 *send transaction from one wallet to another wallet /* 
	 */
	//@RequestMapping(method=RequestMethod.POST, value="/createNewTransaction")
	public ReturnUserWalletToFront sendTransaction(@RequestBody final NewTransaction newTransaction) throws Exception{
		return ethereumService.fullStepTransaction(newTransaction);
	}
	
	/*
	 * get balance ETH fromAdress input
	 */
	@RequestMapping(method=RequestMethod.GET, value="/getbalanceeth")
	public BigInteger getBalanceEther(final String fromAddress, final String password) throws Exception {
		return ethereumService.getBalanceAdress(fromAddress);
	}
	
	
	
}
