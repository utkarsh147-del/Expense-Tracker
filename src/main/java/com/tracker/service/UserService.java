package com.tracker.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tracker.entity.User;
import com.tracker.repository.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	private static final BCryptPasswordEncoder passwordencoder=new BCryptPasswordEncoder();
	public boolean saveNewEntry(User user)
	{
		try
		{
			user.setUnencryptpassword(user.getPassword());
		user.setPassword(passwordencoder.encode(user.getPassword()));
		
		user.setRoles(Arrays.asList("USER"));
		userRepository.save(user);
		return true;
	}
	catch(Exception e)
	{
//		logger.info("hahhhh");
//		throw Exceptions;
		return false;
	}
	}
	public void saveUser(User user)
	{
//		Lastrepository.save(user);
		userRepository.save(user);
	}
	public void saveAdmin(User user)
	{
	
//		user.setPassword(passwordencoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
	
//		Lastrepository.save(user);
		userRepository.save(user);
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
		
	}
	public Optional<User> findById(ObjectId id)
	{
		return userRepository.findById(id);
	}
	
	public void deleteById(ObjectId id)
	{
		userRepository.deleteById(id);
	}
	
	public User findByUserName(String userName)
	{
		
		return userRepository.findByuserName(userName);
	}
	public boolean authenticate(String username, String password) {
        User user = userRepository.findByuserName(username);
        System.out.println(user.getUserName());
        System.out.println(user.getUnencryptpassword() );
        System.out.println((user.getPassword()));
        return user != null && user.getUnencryptpassword().equals(password);
    }
	
	
	
	
	
	
	
}
