import { Injectable } from '@angular/core';

declare var require: any;

@Injectable()
export class EthereumWalletService {


  constructor() {
  }

  create() {
    var ethereumw = require('ethereumjs-wallet');
    var wallet = ethereumw.generate();

    return ({
      trigram: "ETH",
      name: "Ethereum",
      publicAddress: wallet.getAddressString(),
      privateAddress: wallet.getPrivateKeyString()
    });
  }
}
