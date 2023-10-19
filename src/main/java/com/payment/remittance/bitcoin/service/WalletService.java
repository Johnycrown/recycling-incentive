package com.payment.remittance.bitcoin.service;

import com.payment.remittance.bitcoin.dto.RewardRequest;
import com.payment.remittance.bitcoin.dto.Userbalance;
import com.payment.remittance.bitcoin.dto.WithdrawalRequest;
import com.payment.remittance.bitcoin.model.WalletEntity;
import com.payment.remittance.usermanagement.model.AppUser;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.IOException;

public interface WalletService {
    public void createNewWallet();

    public Wallet retrieveWallet() throws UnreadableWalletException;

    WalletEntity createWalletEntity(AppUser appUser);

    Userbalance  fetchUserBalance(long userId);

    Userbalance  rewardUser(RewardRequest rewardRequest);
    String withdrawEarning(WithdrawalRequest withdrawalRequest);

}
