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
@Table(name="cancel")
public class Cancel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;
	private String trainName;
	private String trainFrom;
	private String trainTo;
	private String price;
	private String firstName;
	private String email;
	private String phone;
    
	 @ManyToOne 
	private User user;
    
}
