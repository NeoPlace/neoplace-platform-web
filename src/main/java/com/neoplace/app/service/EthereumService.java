package com.neoplace.app.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.neoplace.app.modele.ReturnUserWalletToFront;

import com.neoplace.app.modele.NewAccountInput;
import com.neoplace.app.repo.EthTransactionRepositoryInterface;
import com.neoplace.app.repo.EthereumNewAccountRepositoryInterface;
import com.neoplace.app.repo.EthereumNewAccountUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.infura.InfuraHttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.neoplace.app.modele.NewTransaction;

import org.web3j.utils.Numeric;

@Service
/*
 * Voir documentation web3j : https://docs.web3j.io/transactions.html
 * Pour le mécanisme des transactions ETH : https://docs.web3j.io/transactions.html#transaction-mechanisms
 * http://faucet.ropsten.be:3001/
 */
@Slf4j
public class EthereumService {
	
	private static org.apache.log4j.Logger logger1 = org.apache.log4j.Logger.getLogger(EthereumService.class);

	private static final String PATH_D = "D:/";
	private static final String PATH_E = "D:/";

	@Autowired
	private EthereumNewAccountRepositoryInterface ethereumNewAccountRepositoryInterface;
	@Autowired
	private EthereumNewAccountUserRepo ethereumNewAccountUserRepo;
	@Autowired
	private EthTransactionRepositoryInterface ethTransactionRepositoryInterface;

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EthereumService.class);

	private static final String infuranet  = "https://infuranet.infura.io/gOk7y1kphORye2ZR9sHz";
	private static final String mainNetInfura = "https://mainnet.infura.io/gOk7y1kphORye2ZR9sHz";
	private static final String ropsten = "https://ropsten.infura.io/gOk7y1kphORye2ZR9sHz";
	private static final String ethereumRinkeby = "https://rinkeby.infura.io/gOk7y1kphORye2ZR9sHz";

	static final BigInteger GAS_PRICE = BigInteger.valueOf(50_000_000_000L);
	static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
	private static final BigInteger ACCOUNT_UNLOCK_DURATION = BigInteger.valueOf(30);
	private static final int SLEEP_DURATION = 15000;
	private static final int ATTEMPTS = 40;

	/**
	 * Instance of new Infura cloud Ethereum client
	 * Use HttpService if Parity is launched in local mode (default to 8545 port)
	 */
	Web3j parity =  Web3j.build(new InfuraHttpService(ropsten));
	//Web3j parity =  Web3j.build(new HttpService());

	public String getClientVersion() throws IOException, InterruptedException, ExecutionException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException {
		String web3ParityVersion = parity.web3ClientVersion().sendAsync().get().getWeb3ClientVersion();
		return web3ParityVersion;
	}

	public List<String> getPersonalListAccounts() throws IOException{
		return parity.ethAccounts().send().getAccounts();
	}

	
	public void saveUser(NewAccountInput newAccount){
		UserAccount userAccount = new UserAccount();
		userAccount.setLogin(newAccount.getLoginAccount());
		userAccount.setPassword(newAccount.getPasswordAccount());
		ethereumNewAccountUserRepo.save(userAccount);
		logger1.info("Save user  " + userAccount.getId() + " " + userAccount.getLogin() + " " + userAccount.getPassword());
	}
	/*
	 * Use parity or web3j, not both at same time
	 * create new account with password defined
	 */
	/*public String createNewAccountWithParity(final String privateKey) throws IOException {
		NewAccountIdentifier newAccount = parity.personalNewAccount(privateKey).send();
		return newAccount.getAccountId();
	}*/

	/**
	 * Use parity or web3j, not both at same time
	 * create new account with password defined
	 * @param newAccount
	 * @return
	 * @throws IOException
	 * @throws CipherException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public ReturnUserWalletToFront createNewAccountWithWeb3j(final NewAccountInput newAccount) throws IOException, CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
				
		final String nameWallet = WalletUtils.generateFullNewWalletFile(newAccount.getPasswordAccount(), new File(PATH_D));
		final String publicAdress = WalletUtils.loadCredentials(newAccount.getPasswordAccount(), new File(PATH_D +nameWallet)).getAddress();
		
		saveNewAccountWithPublicKey(newAccount, publicAdress);
		saveUser(newAccount);

		ReturnUserWalletToFront wallet = new ReturnUserWalletToFront();
		wallet.setNameWalletJsonEthOrBtcName(nameWallet);
		wallet.setPublicKey(publicAdress);
		wallet.setLoginAccount(newAccount.getLoginAccount());
		wallet.setTrigramCrypto("ETH");
		try {
			wallet.setBalanceWallet(getBalanceAdress(publicAdress));
		} catch (Exception e) {
		}
		return wallet;
	}

	/**
	 *
	 * @param newAccount
	 * @param publicKey
	 */
	private void saveNewAccountWithPublicKey(final NewAccountInput newAccount, final String publicKey) {
		newAccount.setPublicKeyAdress(publicKey);
		ethereumNewAccountRepositoryInterface.save(newAccount);
		logger1.info("Save new account " + newAccount.getLoginAccount() + newAccount.getTrigram() + newAccount.getPasswordAccount() + publicKey);

	}

	/**Not used - Parity 
	 *Transaction with Parity client un compte doit être débloqué avant de procéder à une transaction
	 */
	/*public boolean unlockAccountWeb3jParity(String adressWallet, String privateKeyWallet) throws Exception {
		PersonalUnlockAccount personalUnlockAccount =
				parity.personalUnlockAccount(
						adressWallet, privateKeyWallet, ACCOUNT_UNLOCK_DURATION)
				.sendAsync().get();
		return personalUnlockAccount.accountUnlocked();
	}*/

	/**
	 * send a transaction, or use parity.personalSignAndSendTransaction() to do it all in one
	 */
	public TransactionReceipt doTransaction(NewTransaction transactionFront) throws Exception {
		final String fromFileName = transactionFront.getFromFileNameJson();
		final String fromPassword = transactionFront.getFromPassword();
		final String toAdressAccount = transactionFront.getTo();
		final String valueToSend = transactionFront.getValueToSend();
		BigDecimal valueSend = null;

		if (valueToSend != null && !valueToSend.isEmpty()) {
			valueSend = new BigDecimal(valueToSend);
		}

		final Credentials credentials = WalletUtils.loadCredentials(fromPassword, new File(PATH_D+fromFileName));

		final TransactionReceipt transactionReceipt = Transfer.sendFunds(
				parity, credentials, toAdressAccount, valueSend, Convert.Unit.WEI);

		if (transactionReceipt != null ) {
			//environ 1 min d'attente 
			return waitForTransactionReceipt(transactionReceipt.getTransactionHash());
		} else {
			throw new Exception("Error transaction");
		}
	}

	/**
	 * Full steps transaction (même chose qu'en haut mais plus détaillée)
	 */
	public ReturnUserWalletToFront fullStepTransaction(NewTransaction transactionInputFromFront) throws Exception {
		final String fromFileName = transactionInputFromFront.getFromFileNameJson();
		final String fromPassword = transactionInputFromFront.getFromPassword();
		final String toAdressAccount = transactionInputFromFront.getTo();
		final String valueToSend = transactionInputFromFront.getValueToSend();

		final Credentials credentials = WalletUtils.loadCredentials(fromPassword, new File(PATH_D+fromFileName));


		//Identify the next available nonce for the sender account
		final BigInteger nonce = getNonce(transactionInputFromFront.getFrom());

		Transaction transaction = Transaction.createEtherTransaction(
				transactionInputFromFront.getFrom(),nonce,GAS_PRICE,GAS_LIMIT,toAdressAccount,new BigInteger(valueToSend));

		// create our transaction
		RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
				nonce, GAS_PRICE, GAS_LIMIT, toAdressAccount, new BigInteger(valueToSend));

		// sign & send our transaction
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		EthSendTransaction ethSendTransaction = parity.ethSendRawTransaction(hexValue).sendAsync().get();

		if (ethSendTransaction.getTransactionHash() != null) {
			TransactionReceipt receiptTransaction = waitForTransactionReceipt(ethSendTransaction.getTransactionHash());
			final String transactionHash = receiptTransaction.getTransactionHash();
			final String from = receiptTransaction.getFrom();
			final String to = receiptTransaction.getTo();
			final String gasUsed = new String(receiptTransaction.getGasUsed().toByteArray());
					
			//create transaction
			TransactionBean transactionObject = new TransactionBean();
			transactionObject.setFrom(from);
			transactionObject.setTo(to);
			transactionObject.setGasUsed(gasUsed);
			transactionObject.setTransactionHash(transactionHash);
			transactionObject.setDate(LocalDateTime.now().toString());
			
			//save each Transaction in DB
			ethTransactionRepositoryInterface.save(transactionObject);
			
			//create ReturnUserWalletToFront and set transaction
			ReturnUserWalletToFront walletReturnAfterTransaction = new ReturnUserWalletToFront();
			walletReturnAfterTransaction.setPublicKey(transactionInputFromFront.getFrom());
			walletReturnAfterTransaction.setTrigramCrypto("ETH");
			walletReturnAfterTransaction.setBalanceWallet(new BigInteger(from));
			walletReturnAfterTransaction.setLoginAccount(transactionInputFromFront.getLoginAccount());
			walletReturnAfterTransaction.setLabelName(transactionInputFromFront.getLabelMessage());
			
			//retrieve all transactions from DB from publicKey and set it in ReturnUserWalletToFront object
			List<TransactionBean> listeTransaction = ethTransactionRepositoryInterface.findByFromAdress(transactionInputFromFront.getFrom());
			listeTransaction.add(transactionObject);
			walletReturnAfterTransaction.setListTransaction(listeTransaction);
			
			return walletReturnAfterTransaction;
			
		}
		return null;
	}
	
	public TransactionReceipt waitForTransactionReceipt(String transactionHash) throws Exception {

		Optional<TransactionReceipt> transactionReceiptOptional =
				getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS);

		if (!transactionReceiptOptional.isPresent()) {
			throw new Exception ("Transaction receipt not generated after " + ATTEMPTS + " attempts");
		}

		return transactionReceiptOptional.get();
	}

	private Optional<TransactionReceipt> getTransactionReceipt(String transactionHash, int sleepDuration, int attempts) throws Exception {

		Optional<TransactionReceipt> receiptOptional =
				sendTransactionReceiptRequest(transactionHash);
		for (int i = 0; i < attempts; i++) {
			if (!receiptOptional.isPresent()) {
				Thread.sleep(sleepDuration);
				receiptOptional = sendTransactionReceiptRequest(transactionHash);
			} else {
				break;
			}
		}

		return receiptOptional;
	}

	private Optional<TransactionReceipt> sendTransactionReceiptRequest(String transactionHash) throws Exception {
		EthGetTransactionReceipt transactionReceipt = parity.ethGetTransactionReceipt(transactionHash).sendAsync().get();
		return transactionReceipt.getTransactionReceipt();
	}

	public BigInteger getBalanceAdress(final String fromAdress) throws Exception {
		EthGetBalance balance = parity.ethGetBalance(fromAdress, DefaultBlockParameterName.LATEST).sendAsync().get();
		if (balance.getError() != null && balance.getError().getMessage() != null) {
			throw new Exception(balance.getError().getMessage());
		}
		else {
			return balance.getBalance();
		}
	}

	/*
	 * Pour récupérer l'historique des transactions depuis le début
	 */
	public void getTransactionsHistory(final String fromAdress) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		parity.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.EARLIEST)
				.filter(tx -> tx.getFrom().equals(fromAdress))
				.subscribe(
						tx -> System.out.println(tx.getValue()),
						Throwable::printStackTrace,
						countDownLatch::countDown);
		//parity.transactionObservable();
		Thread.sleep(TimeUnit.MINUTES.toMillis(1));
	}

	/**
	 * 
	 * The nonce is an increasing numeric value which is used to uniquely identify transactions. 
	 * A nonce can only be used once and until a transaction is mined, it is possible to send multiple versions of a transaction with the same nonce, 
	 * however, once mined, any subsequent submissions will be rejected.
	 */
	private BigInteger getNonce(final String address) throws Exception {
		EthGetTransactionCount ethGetTransactionCount = parity.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();
		return ethGetTransactionCount.getTransactionCount();
	}	
	
}
