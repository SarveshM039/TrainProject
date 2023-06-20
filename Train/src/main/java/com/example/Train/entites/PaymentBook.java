package com.example.Train.entites;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="payment")
public class PaymentBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payBookId;
	
	private String orderId; 
	 
	private String amount;
	 
	private String receipt;
	 
	private String status;
	
	@ManyToOne 
	private User user;
	
	
	private String paymentId;

	
}
