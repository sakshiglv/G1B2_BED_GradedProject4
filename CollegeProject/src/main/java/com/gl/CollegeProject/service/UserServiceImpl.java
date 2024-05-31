package com.gl.CollegeProject.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gl.CollegeProject.entity.User;
import com.gl.CollegeProject.repo.Userrepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

	private final Userrepo repo;

	public User save(User user) {
		return repo.save(user);
	}

	public Set<User> listUser() {
		return new HashSet<User>(repo.findAll());
	}

	public User getUserById(int id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid id"));
	}

	public User Update(int id, User user) {
		User existing_user = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
		existing_user.setName(user.getName());
		existing_user.setEmail(user.getEmail());
		existing_user.setRoles(user.getRoles());
		existing_user.setPassword(user.getPassword());
		return repo.save(existing_user);

	}

	public void delete(int id) {
		repo.deleteById(id);
	}

	public Optional<User> fetchByName(String Name) {

		return repo.findByName(Name);
	}

	public List<User> getUserSortedByName(Direction direction) {

		return repo.findAll(Sort.by(direction, "name"));
	}
}
