package com.bhaskar.repository;
	
import org.springframework.data.jpa.repository.JpaRepository;
	
import com.bhaskar.model.User;
	
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
	
}
