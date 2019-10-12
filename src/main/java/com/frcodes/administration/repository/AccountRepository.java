package com.frcodes.administration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frcodes.administration.model.Account;


@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	
	Account findByIban(String iban);
	
	List<Account> findByUserId(Long userId);
}