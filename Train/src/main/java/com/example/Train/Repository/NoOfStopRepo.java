package com.example.Train.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Train.entites.No_of_stops;

public interface NoOfStopRepo extends JpaRepository<No_of_stops, Integer> {

}
