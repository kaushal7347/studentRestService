package com.studentregistration.studentregistration.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.studentregistration.studentregistration.entity.User;
import com.studentregistration.studentregistration.entity.UserRegistration;
import com.studentregistration.studentregistration.service.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	RegistrationService service;

	@PostMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> addUser(@RequestBody UserRegistration userDetails, UriComponentsBuilder builder) {
		User user = new User();
		BeanUtils.copyProperties(userDetails, user);
		boolean flag = service.addUser(user);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("userbyid/{id}").buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "userbyemail/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRegistration> getUserByEmail(@PathVariable("email") String email) {
		UserRegistration ob = new UserRegistration();
		BeanUtils.copyProperties(service.getUserByEmail(email), ob);
		return new ResponseEntity<UserRegistration>(ob, HttpStatus.OK);
	}
	
	@GetMapping(value = "userbyid/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRegistration> getUserById(@PathVariable("id") Integer id) {
		UserRegistration ob = new UserRegistration();
		BeanUtils.copyProperties(service.getUserById(id), ob);
		return new ResponseEntity<UserRegistration>(ob, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
		service.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping(value = "user", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRegistration> updateArticle(@RequestBody UserRegistration userRegistration) {
		User user = new User();
		BeanUtils.copyProperties(userRegistration, user);
		service.updateUser(user);

		UserRegistration ob = new UserRegistration();
		BeanUtils.copyProperties(user, ob);
		return new ResponseEntity<UserRegistration>(ob, HttpStatus.OK);
	}
}
