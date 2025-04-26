package com.tracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tracker.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SpringSecurity  {
	
	@Autowired
	private UserDetailsServiceImpl userDetailService;
	
	

	
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http.authorizeRequests().requestMatchers("/api/quantum/**","/expense/**","/user/**").authenticated()
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.requestMatchers("/expense.html/**").permitAll()
			.anyRequest()
			.permitAll()
			.and()
			.httpBasic();
		 
			
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().csrf().disable();
	
			  return http.build();

}
//		protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//			auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
//		}
	 @Bean
	 public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailService)
	         throws Exception {
		 System.out.println("LLLLL");
	     return http.getSharedObject(AuthenticationManagerBuilder.class)
	             .userDetailsService(userDetailService)
	             .and()
	             .build();
	 }

		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		
	 
	 
}
}