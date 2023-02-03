package com.example.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FlightBook extends JpaRepository<FlightEntity, String>{

}
