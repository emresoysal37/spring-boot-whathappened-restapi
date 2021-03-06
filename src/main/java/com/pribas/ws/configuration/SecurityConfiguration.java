package com.pribas.ws.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint());
		
		http.headers().frameOptions().disable();
		
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/timeline").authenticated()
				.antMatchers(HttpMethod.POST, "/api/moments").authenticated()
				.antMatchers(HttpMethod.POST, "/api/moment-attachment").authenticated()
				.antMatchers(HttpMethod.POST, "/api/logout").authenticated()
			.and()
			.authorizeRequests().anyRequest().permitAll();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	TokenFilter tokenFilter() {
		return new TokenFilter();
	}
}
