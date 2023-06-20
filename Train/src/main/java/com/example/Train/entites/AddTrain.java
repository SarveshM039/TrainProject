package com.example.Train.entites;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "addTrain")
public class AddTrain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;
	private String trainName;
	private String Schedule;
	private String trainFrom;
	private String trainTo;
	private String fromTiming;
	private String toTiming;
	private String price;
	private Long noofstop;
	

}
