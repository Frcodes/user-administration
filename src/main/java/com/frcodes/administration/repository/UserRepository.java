package com.frcodes.administration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frcodes.administration.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
}