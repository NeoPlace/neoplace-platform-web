package com.neoplace.app.controller;

import com.neoplace.app.modele.NewAccountInput;
import com.neoplace.app.modele.NewTransaction;
import com.neoplace.app.modele.ReturnUserWalletToFront;
import com.neoplace.app.service.BitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class BitcoinController {

    @Autowired
    BitcoinService bitcoinService;

    public ReturnUserWalletToFront generateWallet(@RequestBody final NewAccountInput newAccount) throws UnreadableWalletException, IOException, BlockStoreException{
        return bitcoinService.generateWallet(newAccount);
    }

    @RequestMapping(method=RequestMethod.POST, value="/loadWalletAndDoTransaction")
    public ReturnUserWalletToFront sendTransaction(final NewTransaction newTransaction) throws IOException {
		return bitcoinService.loadWalletAndSendTransaction(newTransaction);
    }
    
	@RequestMapping(method=RequestMethod.GET, value="/getbalancebtc")
	public String getBalanceBTC(final String fromAddress, final String password) throws Exception {
//        Balance balance = new Balance();
//        balance.setFromAdress(fromAddress);
//        balance.setPasswordToDecryptBTC(password);
//		final String value = String.valueOf(bitcoinService.getBalance(balance));
		//TODO change !!!!!
		//
        return "0";
	}

}
