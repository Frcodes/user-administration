package com.frcodes.administration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/admin")
@Api(value = "Administrator controller", tags = "Administration operations")
public class AdminController {

	@GetMapping(value = "/health")
	@ApiOperation(value = "Healty endpoint", notes = "Returns 'healty' as check the application run", response = String.class)
	public String health() {
		return "API REST healthy";
	}

}
