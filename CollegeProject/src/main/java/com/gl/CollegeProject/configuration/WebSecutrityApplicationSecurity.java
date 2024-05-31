package com.gl.CollegeProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecutrityApplicationSecurity {

	// authentication
	private final UserDetailsService userDetailsService;

	/*
	 * Below is the 2 method through which we can tie the spring application to
	 * spring security and encode it at the same time
	 */

	@Bean
	PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

	@Bean
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
		authprovider.setPasswordEncoder(passwordEncoder());
		authprovider.setUserDetailsService(this.userDetailsService);

		return authprovider;
	}

	// authorization
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authz -> authz

				.requestMatchers(HttpMethod.GET, "/user**").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST, "/user**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/user**").hasRole("ADMIN")
				.requestMatchers("/h2-console**", "/login**", "/logout**", "/actutator**").permitAll().anyRequest()
				.fullyAuthenticated());
		http.cors(cors -> cors.disable());
		http.csrf(csrf -> csrf.disable());
		http.httpBasic(Customizer.withDefaults());
		http.headers(customizer -> customizer.frameOptions(o -> o.disable()));
		return http.build();

	}

}
