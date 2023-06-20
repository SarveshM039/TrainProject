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
@Table(name = "bookTicket")
public class BookTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;
	private String trainName;
	private String trainFrom;
	private String trainTo;
	private String fromTiming;                                                 
	private String toTiming;
	private String Schedule;
	private String price;
	private String noofstop;
	private String firstName;
	private String lastName;
	private String email;
	private String aadhar;
	private String phone;
	private String gender;
	
    @ManyToOne 
	private User user;
    
   
    
}
