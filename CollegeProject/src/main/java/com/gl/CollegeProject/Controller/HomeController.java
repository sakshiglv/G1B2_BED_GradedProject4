package com.gl.CollegeProject.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.CollegeProject.entity.User;
import com.gl.CollegeProject.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {

	private final UserServiceImpl userservice;

	@GetMapping
	public Set<User> fetchUser() {
		return this.userservice.listUser();
	}

	@PostMapping
	public User saveuser(@RequestBody User user) {
		return this.userservice.save(user);
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") int id) {
		return this.userservice.getUserById(id);
	}

	@DeleteMapping("/deleteById/{id}")
	public void delete(@PathVariable("id") int id) {
		userservice.delete(id);
	}

	@GetMapping("/update/{id}")
	public User Update(@PathVariable("id") int id, @RequestBody User user) {
		return userservice.Update(id, user);
	}

	@GetMapping("/SortByName")
	public List<User> getUserSortedByName() {
		return userservice.getUserSortedByName(Direction.ASC);
	}

	@GetMapping("/findByName/{Name}")
	public Optional<User> getUserByName(@PathVariable String Name) {
		return userservice.fetchByName(Name);
	}

}
