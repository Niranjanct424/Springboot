package com.niranjan.executor.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.niranjan.executor.api.entity.User;
import com.niranjan.executor.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	Object target;
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Async
	public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception
	{
		Long start = System.currentTimeMillis();
		List<User> users = parseCSVFile(file);
		logger.info("saving list of users size {} ",users.size(),""+Thread.currentThread().getName());
		users = userRepository.saveAll(users);
		Long end = System.currentTimeMillis();
		logger.info("Totol time "+(end-start));
		return CompletableFuture.completedFuture(users);
		
	}
	
	@Async
	public CompletableFuture<List<User>> findAllUsers()
	{
		logger.info("Get list of user by "+Thread.currentThread().getName());
		List<User> users =  userRepository.findAll();
		return CompletableFuture.completedFuture(users); 
	}
	
	private List<User> parseCSVFile(final MultipartFile file) throws Exception
	{
		final List<User> users = new ArrayList<>(); 
		try {
			
			try(final BufferedReader br  = new BufferedReader(new InputStreamReader(file.getInputStream())))
			{
				String line;
				while ((line = br.readLine()) != null) {
					final String[] data = line.split(",");
					final User user = new User();
					user.setName(data[0]);
					user.setEmail(data[1]);
					user.setGender(data[2]);
					users.add(user);
				}
				return users;
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			logger.error("Faild to Parse CSv file {} ");
			throw new Exception(" Faild to Parse CSv file {} ");
		}
		
	}
}
