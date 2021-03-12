package com.test.accounts;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Integer save(AccountDto accountVo);
}