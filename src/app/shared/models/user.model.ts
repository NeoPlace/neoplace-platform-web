import {Wallet} from "./wallet.model";
import {Profil} from "./profil.model";

export interface User {
  username: string;
  wallet: Wallet[];
  profil: Profil;
  id: string;
  token: string;

  publicAddress?: string;
  nonce?: string;
}
