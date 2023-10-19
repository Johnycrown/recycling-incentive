package com.payment.remittance.bitcoin.service;
import com.payment.remittance.LatestPrice.LatestExchangeService;
import com.payment.remittance.bitcoin.dto.*;
import com.payment.remittance.bitcoin.model.WalletEntity;
import com.payment.remittance.bitcoin.repository.WalletRepository;
import com.payment.remittance.exceptions.UserNotFoundException;
import com.payment.remittance.transaction.repository.TransactionRepository;
import com.payment.remittance.usermanagement.model.AppUser;
import com.payment.remittance.usermanagement.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;


import org.bitcoinj.wallet.Wallet;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public  class WalletServiceImpl implements WalletService {
    private final AppUserRepository appUserRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    @Value("${crypto.url}")
    private  String cyrptoApibaseUrl;
    @Value("{crypto.walletId}")
    private String walletId;
    @Value("${crypto.apikey}")
    private String apikey;
    private final static RestTemplate restTemplate = new RestTemplate();
    @Override
    public void createNewWallet() {
        NetworkParameters networkParameters = TestNet3Params.get();

        log.info("network info \n\n\n{}", networkParameters.getAddrSeeds() );
        WalletAppKit kit = new WalletAppKit(networkParameters, new File("."), "your_wallet_file_name2") {
            @Override
            protected void onSetupCompleted() {

                if (wallet().getKeyChainGroupSize() == 0) {
                    wallet().importKey(new ECKey());
                }
            }
        };

        kit.startAsync();
        kit.awaitRunning();

        Wallet wallet = kit.wallet();

        log.info("wallet address generated \n{}", wallet.currentReceiveAddress().toString());

        byte[] walletData = serializeWallet(wallet);
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setWalletData(walletData);
        walletRepository.save(walletEntity);
    }

    public byte[] serializeWallet(Wallet wallet) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            wallet.saveToFileStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
    @Override
    public Wallet retrieveWallet() throws UnreadableWalletException {
        Optional<WalletEntity> optionalWalletEntity = walletRepository.findById(1L); // Assuming you store the wallet in a specific record (e.g., with ID 1)

        if (optionalWalletEntity.isPresent()) {
            WalletEntity walletEntity = optionalWalletEntity.get();
            byte[] serializedWalletData = walletEntity.getWalletData();

            Wallet wallet = Wallet.loadFromFileStream(new ByteArrayInputStream(serializedWalletData));
            return wallet;
        } else {
            throw new RuntimeException("Wallet not found in the database.");
        }
    }

    @Override
    public WalletEntity createWalletEntity(AppUser appUser) {

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(appUser.getId());
        walletEntity.setBalance(BigDecimal.ZERO);

        return   walletRepository.save(walletEntity);
    }

    @Override
    public Userbalance fetchUserBalance(long userId) {
        WalletEntity userWallet = walletRepository.findByUserId(userId).orElseThrow();

        return Userbalance.builder().satBalance(userWallet.getBalance())
                .dollarEstimate(LatestExchangeService.getDollarEquivalent(userWallet.getBalance()))
                .build();
    }

    @Override
    public Userbalance rewardUser(RewardRequest rewardRequest) {

      WalletEntity walletEntity=   walletRepository.findByUserId(rewardRequest.getUserid()).orElseThrow(()-> new UserNotFoundException("user not found "));

      Double amount = rewardRequest.getBottleQuantity() * 0.00000035;
      BigDecimal amountToreward = walletEntity.getBalance().add(BigDecimal.valueOf(amount));
        System.out.println("\n\n" + amountToreward+"\n\n");
      walletEntity.setBalance(amountToreward);

      WalletEntity updatedWallet =  walletRepository.save(walletEntity);

        System.out.println("\n\n" +updatedWallet.getBalance()+"\n\n");




        return fetchUserBalance(updatedWallet.getUserId());
    }

    @Override
    public String withdrawEarning(WithdrawalRequest withdrawalRequest) {
        WalletEntity walletEntity = walletRepository.findByUserId(withdrawalRequest.getUserId()).orElseThrow();
        double userBalance = Double.parseDouble(String.valueOf(walletEntity.getBalance()));
        if( userBalance < withdrawalRequest.getAmount()){
            throw new IllegalArgumentException("Insufficient funds");
        }
        BigDecimal amountToWithDraw= walletEntity.getBalance().subtract(BigDecimal.valueOf(withdrawalRequest.getAmount()));
        walletEntity.setBalance(amountToWithDraw);

        Recipient recipient = new Recipient();
        recipient.setAddress(withdrawalRequest.getDepositAddress());
        recipient.setAmount(String.valueOf(withdrawalRequest.getAmount()));
        Item item = new Item();
        ArrayList<Recipient> recipients = new ArrayList<>();
        recipients.add(recipient);
        item.setRecipients(recipients);
        item.setFeePriority("low");
        Datawalet datawalet = new Datawalet();
        datawalet.setItem(item);
        String fullUrl = cyrptoApibaseUrl + walletId+"/bitcoin/testnet/transaction-requests?context=withdrawTransaction";
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-Key", apikey);
        HttpEntity entity = new HttpEntity<>(datawalet,headers);
        restTemplate.postForEntity(fullUrl,entity,Object.class);


        return "withdraw successfullly";
    }


}
