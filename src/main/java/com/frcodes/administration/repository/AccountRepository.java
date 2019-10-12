package com.frcodes.administration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frcodes.administration.model.Account;


@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
	
	Account findByIban(String iban);
}