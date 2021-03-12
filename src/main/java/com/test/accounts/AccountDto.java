package com.test.accounts;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AccountDto {
	private Integer id;
	
    private String account;

    private String password;

    private LocalDateTime lastAccessDt;

    private LocalDateTime regDt;

    public Account toEntity() {
        return new Account(id, account, password);
    }

}
