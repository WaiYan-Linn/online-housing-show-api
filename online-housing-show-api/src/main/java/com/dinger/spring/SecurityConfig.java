package com.dinger.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dinger.spring.security.CustomUserDetailsService;
import com.dinger.spring.security.JwtTokenSecurityFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtTokenSecurityFilter jwtTokenSecurityFilter;
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();	
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder encoder) {
		var dao = new DaoAuthenticationProvider(encoder);
		dao.setUserDetailsService(userDetailsService);
		return dao;
	}
	
	@Bean
	SecurityFilterChain http(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(config ->{
			config.requestMatchers("/public/**","/auth/**").permitAll();
			config.anyRequest().authenticated();
			
		});
		
		http.cors(config -> {});
		http.csrf(config -> config.disable());
		http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(jwtTokenSecurityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
}
