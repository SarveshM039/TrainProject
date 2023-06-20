package com.example.Train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Train.entites.Cancel;

public interface CancelRepo extends JpaRepository<Cancel, Long> {

	@Query("From Cancel as c where c.user.id=:userId") public
	 List<Cancel> findCancelByUser(@Param("userId") Long userId);
}
