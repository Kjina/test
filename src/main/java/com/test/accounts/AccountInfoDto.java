package com.test.accounts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInfoDto {
  private String email;
  private String password;

  private String auth;
}
