package com.studentregistration.studentregistration.repo;

import org.springframework.data.repository.CrudRepository;

import com.studentregistration.studentregistration.entity.User;
import com.studentregistration.studentregistration.entity.UserRegistration;

public interface RegistrationRepo extends CrudRepository<User, Integer> {

	User findByuserEmail(String userEmail);
	User findByuserId(Integer id);

}
