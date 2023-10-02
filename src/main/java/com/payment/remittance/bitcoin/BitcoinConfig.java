package com.payment.remittance.bitcoin;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitcoinConfig {
    private Wallet wallet;
    //@Bean
    public void configure(){
        NetworkParameters testnetParams = TestNet3Params.get();
        wallet = Wallet.createDeterministic(testnetParams, Script.ScriptType.P2PKH);
        }
    public Address generateNewReceivingAddress() {
        if (wallet == null) {
            throw new IllegalStateException("Bitcoin configuration not initialized. Call configure() first.");
        }

        return wallet.currentReceiveAddress();
    }    
}
