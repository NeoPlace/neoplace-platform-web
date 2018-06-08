package com.neoplace.app.utils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.xml.bind.DatatypeConverter;

public class Web3Utils {

    private static String ethereumRinkeby = "https://rinkeby.infura.io/...";

    private static Web3j web3j =  Web3j.build(new HttpService(ethereumRinkeby));


    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }
}
