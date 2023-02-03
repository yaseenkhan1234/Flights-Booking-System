package com.example.otp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositaryInterface extends JpaRepository<RegistrationClass,String>{
	
	public Optional<RegistrationClass> findById(String email);
	
	

}
