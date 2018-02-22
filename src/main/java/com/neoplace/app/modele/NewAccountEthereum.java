package com.neoplace.app.modele;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NewAccountEthereum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String privateKeyWallet;
    private String loginAccount;
    private String passwordAccount;
    private String publicKeyAdress;
}
