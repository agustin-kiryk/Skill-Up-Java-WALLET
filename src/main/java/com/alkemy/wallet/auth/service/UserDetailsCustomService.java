package com.alkemy.wallet.auth.service;


import com.alkemy.wallet.auth.dto.UserAuthDto;
import com.alkemy.wallet.entity.UserEntity;
import com.alkemy.wallet.mapper.exception.RepeatedUsername;
import com.alkemy.wallet.repository.IRoleRepository;
import com.alkemy.wallet.repository.IUserRepository;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

  @Autowired
  private IUserRepository IUserRepository;
  @Autowired
  private IRoleRepository iRoleRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = IUserRepository.findByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("username or password not found");
    }
    return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
  }

  public void save(@Valid UserAuthDto userDto) throws RepeatedUsername {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    if (IUserRepository.findByEmail(userDto.getEmail()) != null) {
      throw new RepeatedUsername("Username repetido");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
   // userEntity.setRole(iRoleRepository.findByName("USER"));
    userEntity = this.IUserRepository.save(userEntity);

  }

  public void saveAdmin(@Valid UserAuthDto userDto) throws RepeatedUsername {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    if (IUserRepository.findByEmail(userDto.getEmail()) != null) {
      throw new RepeatedUsername("Username repetido");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    userEntity.setRole(iRoleRepository.findByName("ADMIN"));
    userEntity = this.IUserRepository.save(userEntity);
  }


}