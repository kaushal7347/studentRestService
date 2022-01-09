package com.studentregistration.studentregistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentregistration.studentregistration.entity.User;
import com.studentregistration.studentregistration.repo.RegistrationRepo;

@Service
public class RegistrationService {

	@Autowired
	RegistrationRepo repo;

	public synchronized boolean addUser(User user) {
		User list = repo.findByuserEmail(user.getUserEmail());
		if (list != null) {
			return false;
		} else {
			repo.save(user);
			return true;
		}
	}

	public User getUserByEmail(String email) {
		User obj = repo.findByuserEmail(email);
		return obj;
	}

	public User getUserById(Integer id) {
		User obj = repo.findByuserId(id);
		return obj;
	}

	public void deleteUser(Integer id) {
		repo.delete(getUserById(id));
	}

	public void updateUser(User user) {
		repo.save(user);
	}
}
