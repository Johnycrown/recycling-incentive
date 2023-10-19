package com.payment.remittance.bitcoin.controller;
import com.payment.remittance.bitcoin.dto.RewardRequest;
import com.payment.remittance.bitcoin.dto.Userbalance;
import com.payment.remittance.bitcoin.dto.WithdrawalRequest;
import com.payment.remittance.bitcoin.service.WalletServiceImpl;
import com.payment.remittance.usermanagement.controller.AunthenticateController;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController()
@RequestMapping("/wallet")
public class WalletController extends AunthenticateController {
    @Autowired
    private WalletServiceImpl walletService;
    private Wallet wallet;


    @GetMapping("/generate-address")
    public ResponseEntity<?> generateNewReceivingAddress() throws IOException, ClassNotFoundException, UnreadableWalletException {
        Wallet wallet = walletService.retrieveWallet();
        if (wallet == null) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( wallet.currentReceiveAddress());
    }

    @PostMapping("/save-wallet")
    public ResponseEntity<?> saveWallet() throws IOException {
       walletService.createNewWallet();
       return ResponseEntity.ok("Save succesffuly");
    }

    @PostMapping("/reward")
    public ResponseEntity<?> rewardUser(@RequestBody RewardRequest rewardRequest){
      Userbalance response = walletService.rewardUser(rewardRequest);
      return ResponseEntity.ok().body(response);
    }

    @PostMapping("/balance")
    public ResponseEntity<?> fetchBalance(@RequestParam long id){
        Userbalance response = walletService.fetchUserBalance(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawBalance(@RequestParam WithdrawalRequest withdrawalRequest){
        String response = walletService.withdrawEarning(withdrawalRequest);
        return ResponseEntity.ok().body(response);
    }

}
