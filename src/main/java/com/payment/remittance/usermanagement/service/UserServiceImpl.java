package com.payment.remittance.usermanagement.service;
import com.payment.remittance.bitcoin.dto.Userbalance;
import com.payment.remittance.bitcoin.service.WalletService;
import com.payment.remittance.exceptions.UserNotFoundException;
import com.payment.remittance.usermanagement.dto.request.UserRequest;
import com.payment.remittance.usermanagement.dto.response.UserResponse;
import com.payment.remittance.usermanagement.model.AppUser;
import com.payment.remittance.usermanagement.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService {
  private final   ModelMapper modelMapper;
  private final AppUserRepository appUserRepository;
  private final WalletService walletService;
  private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(ModelMapper modelMapper, AppUserRepository appUserRepository, WalletService walletService, BCryptPasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.appUserRepository = appUserRepository;
        this.walletService = walletService;
        this.encoder = encoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if(appUserRepository.existsByEmail(userRequest.getEmail()))
            throw new IllegalArgumentException("the user with the email " +       userRequest.getEmail()  + " already exist");

        String password = encoder.encode(userRequest.getPassword());
        userRequest.setPassword(password);

        AppUser user = modelMapper.map(userRequest, AppUser.class);

        AppUser savedUser = appUserRepository.save(user);
        walletService.createWalletEntity(savedUser);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse fetchUserByEmail(String userName) {
        AppUser saveduser = appUserRepository.findByEmail(userName).orElseThrow(()-> new  UserNotFoundException("user with the email  "+ userName + " not found"));
        return modelMapper.map(saveduser, UserResponse.class);
    }

    @Override
    public Userbalance fetchUserBalance(long userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(()->new UserNotFoundException("the user with the id " + userId + " user not found"));

        return null;
    }
}
