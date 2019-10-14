package com.frcodes.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frcodes.administration.model.User;
import com.frcodes.administration.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/account")
@Api(value = "User account management", tags = "User account management")
public class UserAccountController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping(value = "/users")
	@ApiOperation(value = "Users of applications", 
	notes = "Returns list of users", response = List.class)
	public List<User> listUser() {
		return userService.listUser();
	}

}
