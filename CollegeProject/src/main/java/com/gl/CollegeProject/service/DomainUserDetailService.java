package com.gl.CollegeProject.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gl.CollegeProject.entity.DomainUserDetails;
import com.gl.CollegeProject.repo.Userrepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DomainUserDetailService implements UserDetailsService {

	private final Userrepo userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userrepo.findByName(username).map(DomainUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user name passed"));

	}

}
