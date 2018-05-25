package com.neoplace.app.utils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3Utils {

    private static Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
    private static Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
}
