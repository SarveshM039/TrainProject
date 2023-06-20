package com.example.Train.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private long  aadhar;
	private String password;
	private long phone;
	private String address;
    private String role;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy ="user")
    private List<BookTicket> bookTickets= new ArrayList<>();
	 
}
