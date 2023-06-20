package com.example.Train.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Train.entites.PaymentBook;

public interface PaymentBookRepo extends JpaRepository<PaymentBook, Long> {

	public PaymentBook findByOrderId(String orderId);

	
	  @Query("From PaymentBook as p where p.user.id=:userId") public
	 List<PaymentBook> findPaymentByUser(@Param("userId") Long userId);


	
	  @Query("SELECT p From PaymentBook as p where p.user.id=:userId AND p.payBookId = :payBookId")
		public List<PaymentBook>  findPaymentByUserAndTrain(@Param("userId") Long userId, @Param("payBookId") Long payBookId);


	
}
