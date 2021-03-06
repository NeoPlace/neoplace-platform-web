import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {AppConfirmService} from "./app-confirm/app-confirm.service";
import {ApiService} from "./api.service";

declare var window: any;
declare var require: any;

const Web3 = require('web3');

export const environment = {
  production: false,
  //HttpProvider: "http://localhost:7545",
  HttpProvider: "https://rinkeby.infura.io"
};

@Injectable()
export class Web3Service {

	public static web3: any;
	public static account: any;

  constructor(private confirmService: AppConfirmService,
              private router: Router,
              private apiService: ApiService) {
    this.checkAndInstantiateWeb3();
  }

  checkAndInstantiateWeb3() {
    // Checking if Web3 has been injected by the browser (Mist/MetaMask)
    return new Promise((resolve, reject) => {
      if (typeof window.web3 !== 'undefined') {
        console.warn('Using web3 detected from external source. If you find that your accounts don\'t appear or you have 0 MetaCoin, ensure you\'ve configured that source properly. If using MetaMask, see the following link. Feel free to delete this warning. :) http://truffleframework.com/tutorials/truffle-and-metamask'
        );
        // Use Mist/MetaMask's provider
        Web3Service.web3 = new Web3(window.web3.currentProvider);

        Web3Service.account = window.web3.eth.accounts[0];
        var self = this;

        //check account unlocked
        if(!window.web3.eth.accounts[0]) {
          this.confirmService.confirm({title: "Please unlock your Metamask account.", message: " "})
            .subscribe(resp => {
              if(resp) {
                reject(false);
              }
            });
        }

        //check valid network
        if(window.web3.version.network != 4) {
          this.confirmService.confirm({title: "Please use Rinkeby test network on Metamask.", message: " "})
            .subscribe(resp => {
              if(resp) {
                reject(false);
              }
            });
        }
      } else {
        this.confirmService.confirm({title: "Please install MetaMask first.", message: " "})
          .subscribe(resp => {
            if(resp) {
              this.router.navigateByUrl("/login");
            }
          });
        reject(false);
      }
    });


  };


  login(publicAddress) {
    return new Promise((resolve, reject) => {
      let seq = this.apiService.get("subscribe/address", {publicAddress: publicAddress}).toPromise();

      return seq.then(value => {

        this.signMessage(value['publicAddress'], value['nonce'])
          .then(value1 => {
            this.authenticate(value1['publicAddress'], value1['signature'])
              .subscribe(value2 => {
                console.log("Successful authentication");
                resolve(true);
              }, error => {
                reject(error);
              })
          }).catch(err => {
          reject(err)
        });

      });
    })
  }

  signMessage(publicAddress, nonce) {
    return new Promise((resolve, reject) =>
      window.web3.personal.sign(
        Web3Service.web3.fromUtf8(`Log in NeoPlace (nonce: ${nonce})`),
        publicAddress,
        (err, signature) => {
          if (err) return reject(err);
          return resolve({ publicAddress: publicAddress, signature: signature });
        }
      )
    );
  }

  authenticate(publicAddress, signature) {
    return this.apiService.post("login", {pubKey: publicAddress, signature: signature});
  }
}
