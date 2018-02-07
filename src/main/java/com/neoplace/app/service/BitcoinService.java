package com.neoplace.app.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Joiner;
import com.neoplace.app.modele.Balance;
import com.neoplace.app.modele.NewAccountInput;
import com.neoplace.app.modele.ReturnUserWalletToFront;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.Wallet.SendResult;
import org.springframework.stereotype.Service;

import com.neoplace.app.modele.NewTransaction;

/*
 * Regarder balance : https://www.blocktrail.com/tBTC/address/mtaBxJjWXBziuwqUZ7bHpEiqGEZ5L8u6Cx/transactions
 * Générer bitcoin test : https://testnet.manu.backend.hamburg/faucet
 */

@Service
@Slf4j
public class BitcoinService {

	final String passwordToDecrypt = "Toto2018@";
	static NetworkParameters params = new TestNet3Params();

	String filePrefix = "peer2-testnet";
	WalletAppKit kit = new WalletAppKit(params, new File("."), filePrefix);

	public static final String D_BITCOIN = "D:/bitcoin/";
	private final static String emplacementSauvegarde = D_BITCOIN;

	/**
	 *
	 * @param passwordToEncrypt
	 * @return
	 * @throws UnreadableWalletException
	 * @throws IOException
	 * @throws BlockStoreException
	 */
	public ReturnUserWalletToFront generateWallet(final NewAccountInput newAccount) throws UnreadableWalletException, IOException, BlockStoreException {
		return generateNewWallet(newAccount);
	}

	/**
	 *
	 * @throws IOException
	 */
	public ReturnUserWalletToFront loadWalletAndSendTransaction(final NewTransaction newTransaction) throws IOException {
		final String publicKey = newTransaction.getFrom();
		Wallet walletLaod = loadExistingWallet(publicKey, passwordToDecrypt);
		System.out.println("Get balance " + walletLaod.getBalance());
		walletLaod.reset();

		final BlockStore blockStore = new MemoryBlockStore(walletLaod.getParams());
		BlockChain chain = null;
		try {
			chain = new BlockChain(walletLaod.getParams(), walletLaod, new MemoryBlockStore(walletLaod.getParams()));
		} catch (BlockStoreException e) {
			System.out.println("Blockstore exception");
		}
		PeerGroup peerGroup = new PeerGroup(walletLaod.getParams(), chain);
		peerGroup.setFastCatchupTimeSecs(walletLaod.getEarliestKeyCreationTime());
		peerGroup.addPeerDiscovery(new DnsDiscovery(walletLaod.getParams()));
		peerGroup.addWallet(walletLaod);
		peerGroup.startAsync();
		peerGroup.downloadBlockChain();

		Coin balance = walletLaod.getBalance();
		System.out.println("Wallet balance: " + balance);

		sendTransactionBTC(walletLaod, walletLaod.getParams(), peerGroup, Coin.SATOSHI, "mtaBxJjWXBziuwqUZ7bHpEiqGEZ5L8u6Cx");
		
		return null;
	}

	public long getBalance(final Balance balance){
		final String publicKey = balance.getFromAdress();
		final String passwordToDecryptBTC = balance.getPasswordToDecryptBTC();
		Wallet walletLaod = loadExistingWallet(publicKey, passwordToDecryptBTC);
		long value = walletLaod.getBalance().value;
		System.out.println("Get balance " + walletLaod.getBalance());
		return value;
	}

	/**
	 *
	 * @param publicKeyAdress
	 * @param passwordToDecrypt
	 * @return
	 */
	private static Wallet loadExistingWallet(final String publicKeyAdress, final String passwordToDecrypt){
		Wallet wallet;
		try {
			wallet = Wallet.loadFromFile(new File(emplacementSauvegarde+publicKeyAdress));
			if (wallet.isEncrypted()) {
				wallet.decrypt(passwordToDecrypt);
			}
			wallet.getNetworkParameters();
			return wallet;
		} catch (UnreadableWalletException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	/**
	 * return public key - currentReceiverAdress of wallet
	 */
	public static ReturnUserWalletToFront generateNewWallet(final NewAccountInput accountInput) throws IOException, UnreadableWalletException, BlockStoreException {

		final String passwordToEncryptWallet = accountInput.getPasswordAccount();
		
		//creation wallet
		//For optimal operation the wallet needs to be connected to a BlockChain and a Peer or PeerGroup.
		
		Wallet wallet = new Wallet(params);
		wallet.encrypt(passwordToEncryptWallet);

		BlockChain chain = new BlockChain(params, wallet, new MemoryBlockStore(params));
		PeerGroup peerGroup = new PeerGroup(params, chain);
		peerGroup.addWallet(wallet);
		peerGroup.startAsync();

		wallet.saveToFile(new File(emplacementSauvegarde+wallet.currentReceiveAddress()));


		final String walletMainAddress = wallet.currentReceiveAddress().toString();
		log.info("Wallet BTC address creation " + walletMainAddress + " "  );

		//return user wallet BTC to front
		ReturnUserWalletToFront returnUserWalletToFront = new ReturnUserWalletToFront();
		returnUserWalletToFront.setPublicKey(wallet.currentReceiveAddress().toString());
		returnUserWalletToFront.setTrigramCrypto("BTC");
		returnUserWalletToFront.setBalanceWallet(new BigInteger("0"));
		returnUserWalletToFront.setNameWalletJsonEthOrBtcName(wallet.currentReceiveAddress().toString());
		returnUserWalletToFront.setLoginAccount(accountInput.getLoginAccount());

		//optionnel pour la sécurité - 12 mots pour se rappeler du wallet
		DeterministicSeed seed = wallet.getKeyChainSeed();
		System.out.println(("Seed words are: " + Joiner.on(" ").join(seed.getMnemonicCode())));
		System.out.println(("Seed birthday is: " + seed.getCreationTimeSeconds()));

		String seedCode = "yard impulse luxury drive today throw farm pepper survey wreck glass federal";
		long creationtime = 1409478661L;
		DeterministicSeed seed1 = null;
		try {
			seed1 = new DeterministicSeed(seedCode, null, "", creationtime);
		} catch (UnreadableWalletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Wallet restoredWallet = Wallet.fromSeed(params, seed1);

		return  returnUserWalletToFront;
	}

	/**
	 * Faire transaction entre deux parties
	 * Spending money consists of four steps:
			Create a send request
			Complete it
			Commit the transaction and then save the wallet
			Broadcast the generated transaction
	 */
	private static void sendTransactionBTC(final Wallet wallet, final NetworkParameters params, final PeerGroup peerGroup, final Coin valueToSend, String adressDestinataire) throws IOException{

		//address to always display in GUI (for developers who needs it)
		final Address currentReceiveAdress = wallet.currentReceiveAddress();   System.out.println("currentReceiveAdress " + currentReceiveAdress);

		final Address freshReceiveAddressWallet = wallet.freshReceiveAddress();  System.out.println("freshReceiveAddress " + freshReceiveAddressWallet);

		final Address targetAddress = new Address (params, adressDestinataire);
		// Do the send of 1 satoshi in the background. This could throw InsufficientMoneyException.
		SendResult result = null;
		try {
			result = wallet.sendCoins(peerGroup, targetAddress, Coin.SATOSHI);
		} catch (InsufficientMoneyException e) {
			System.out.println("toto insufficientMoneyException");
		}
		// Save the wallet to disk, optional if using auto saving (see below).
		wallet.encrypt("Toto2018@");
		// Wait for the transaction to propagate across the P2P network, indicating acceptance.
		try {
			result.broadcastComplete.get();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("interruptedException ExecutionException");
		}

		//forme plus complexe et plus détaillée qu'en haut pour envoyer une transaction
				/*// Make sure this code is run in a single thread at once.
				SendRequest request = SendRequest.to(address, value);
				// The SendRequest object can be customized at this point to modify how the transaction will be created.
				wallet.completeTx(request);
				// Ensure these funds won't be spent again.
				wallet.commitTx(request.tx);
				wallet.saveToFile(...);
				// A proposed transaction is now sitting in request.tx - send it in the background.
				ListenableFuture<Transaction> future = peerGroup.broadcastTransaction(request.tx);

				// The future will complete when we've seen the transaction ripple across the network to a sufficient degree.
				// Here, we just wait for it to finish, but we can also attach a listener that'll get run on a background
				// thread when finished. Or we could just assume the network accepts the transaction and carry on.
				future.get(); */
	}


}

