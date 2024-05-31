package com.gl.CollegeProject.configuration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gl.CollegeProject.entity.Role;
import com.gl.CollegeProject.entity.User;
import com.gl.CollegeProject.repo.Userrepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserDetails {

	private final Userrepo repo;

	private final PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void UserDetails(ApplicationReadyEvent event) {

		String encodedPassword = passwordEncoder.encode("welcome");

		User Kiran = User.builder().email("Kiran@Gmail.com").name("Kiran").password(encodedPassword).build();
		User Rehan = User.builder().email("Rehan@Gmail.com").name("Rehan").password(encodedPassword).build();

		Role user = Role.builder().RoleName("ROLE_USER").build();
		Role admin = Role.builder().RoleName("ROLE_ADMIN").build();

		Kiran.addrole(user);
		Rehan.addrole(admin);
		Rehan.addrole(user);

		repo.save(Rehan);
		repo.save(Kiran);

	}
}
