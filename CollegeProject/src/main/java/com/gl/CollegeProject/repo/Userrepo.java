package com.gl.CollegeProject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.CollegeProject.entity.User;

@Repository
public interface Userrepo extends JpaRepository<User, Integer> {

	public Optional<User> findByName(String Name);

}
