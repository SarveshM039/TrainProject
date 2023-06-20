package com.example.Train.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.Train.Repository.UserRepo;
import com.example.Train.entites.User;



public class CustomUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
			User user= repo.findByEmail(username);
			if(user == null) {
				throw new UsernameNotFoundException("User not found");
			}
			return new CustomUserDetail(user);
		}


}
