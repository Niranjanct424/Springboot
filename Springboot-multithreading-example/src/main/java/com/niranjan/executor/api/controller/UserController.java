package com.niranjan.executor.api.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niranjan.executor.api.entity.User;
import com.niranjan.executor.api.service.UserService;
/*
 * Note: Used completable feature in project to make to make our application asynchronously and unblocking. 
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces ="application/json" )
	public ResponseEntity<?> saveUser(@RequestParam (value = "files") MultipartFile[] files) throws Exception
	{
		for (MultipartFile file : files) {
			userService.saveUsers(file);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/users" ,produces = "application/json")
	public CompletableFuture<ResponseEntity> findAllUsers()
	{
		return userService.findAllUsers().thenApply(ResponseEntity::ok);
	}
	
	@GetMapping(value = "/getUsersByThreads" ,produces = "application/json")
	public ResponseEntity<?> getUsers()
	{
		CompletableFuture<List<User>> user1 = userService.findAllUsers();
		CompletableFuture<List<User>> user2 = userService.findAllUsers();
		CompletableFuture<List<User>> user3 = userService.findAllUsers();
		CompletableFuture<List<User>> user4 = userService.findAllUsers();
		CompletableFuture.allOf(user1,user2,user3,user4);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
