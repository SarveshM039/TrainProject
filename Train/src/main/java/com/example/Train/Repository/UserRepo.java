package com.example.Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Train.entites.User;



public interface UserRepo extends JpaRepository<User, Long> {
	
	
	
	@Query("SELECT u FROM  User u WHERE u.email= :email")
	public User findByEmail(@Param("email") String email);

	
	

	

	
	
	
}
