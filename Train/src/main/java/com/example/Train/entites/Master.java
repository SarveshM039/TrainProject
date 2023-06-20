package com.example.Train.entites;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="master")
@Data
public class Master {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;
	
	private String trainName;
	private String fromPlace;
    private Integer ticketPrice;
    private String timing;
    private String schedule;
   
}