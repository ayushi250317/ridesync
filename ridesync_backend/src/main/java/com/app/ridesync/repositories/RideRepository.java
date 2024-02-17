package com.app.ridesync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.Document;
import com.app.ridesync.entities.Ride;
import com.app.ridesync.entities.User;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
	
	List<User> findByUserId(String userId);
	
}
