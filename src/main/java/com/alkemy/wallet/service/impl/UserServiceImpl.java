package com.alkemy.wallet.service.impl;


import com.alkemy.wallet.dto.AccountBasicDto;
import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.mapper.UserMap;
import com.alkemy.wallet.mapper.exception.ParamNotFound;
import com.alkemy.wallet.repository.IUserRepository;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private IUserRepository IUserRepository;

  @Autowired
  AccountServiceImpl accountService;

  @Autowired
  private UserMap userMap;

  @Override
  public UserDto findById(Long id){

    return userMap.userEntity2Dto(IUserRepository.findByUserId(id));
  }

  @Override
  public List<AccountBasicDto> getAccountsBalance(Long id) {

    UserDto user = findById(id);
    List<AccountBasicDto> accounts = user.getAccounts();

    for (int i = 0; i < accounts.size(); i++) {

      AccountBasicDto account;
      account = accounts.get(i);
      account.setBalance(accountService.calculateBalance(account.getAccountId()));

    }
    return accounts;
  }
  @Override
  public List<UserDto> listAllUsers() {

    return userMap.userEntityList2DtoList(IUserRepository.findAll());
  }

  @Override
  public void update(UserDto user, Long id) {

  }

  @Override
  public boolean deleteById(Long id) throws Exception {
    try {
      if (IUserRepository.existsById(id)) {
        IUserRepository.deleteById(id);
        return true;
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
  @Override
  public void delete(Long id) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Long userId = this.IUserRepository.findByEmail(email).getUserId();
    UserEntity entity = this.IUserRepository.findById(userId).orElseThrow(
        ()->new ParamNotFound("invalid Id"));
    this.IUserRepository.deleteById(id);

  }



}


