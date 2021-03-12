package com.test.accounts;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccount(String account);
}
