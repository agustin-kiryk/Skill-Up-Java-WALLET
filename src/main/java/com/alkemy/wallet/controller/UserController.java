package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.UserDto;
import com.alkemy.wallet.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {

  @Autowired
  private IUserService userService;


  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> getAll()
  {
    List<UserDto> users= userService.listAllUsers();
    return ResponseEntity.ok().body(users);
  }

}
