package com.example.Train.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Train.entites.AddTrain;

public interface AddTrainRepo extends JpaRepository<AddTrain, Long> {

	@Query("from AddTrain where trainFrom=:trainFrom and trainTo=:trainTo ")
	List<AddTrain> findtrains( @Param("trainFrom") String from, @Param("trainTo")String to );

	 

}
