package com.ridesync.app.loginPageController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginPage {
	
	@Autowired
	private loginService loginService;
	
	@RequestMapping("login/getUsers")
	public List<User> getAllUsers(){ // the(id) specifies the part where we must look.
		return loginService.getAllUsers();
	}
	
	@RequestMapping("login/getUsers/{email}")
	public User getUser(@PathVariable("email") String email){ // the(id) specifies the part where we must look.
		return loginService.getUser(email);
	}
	
	@PostMapping("login/addUser")
	public String addUser(@RequestBody User user){
		return loginService.addUser(user);
	}
}
