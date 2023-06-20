package com.example.Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Train.entites.BookTicket;

public interface BookTicketRepo extends JpaRepository<BookTicket, Long> {

	@Query("From BookTicket as b where b.user.id=:userId")
   public List<BookTicket> findBooktTicketByUser(@Param("userId") Long userId);


	@Query("SELECT b FROM BookTicket b WHERE b.user.id = :userId AND b.trainId = :trainId")
	public List<BookTicket> findBookTicketByUserAndTrain(@Param("userId") Long userId, @Param("trainId") Long trainId);



} 
