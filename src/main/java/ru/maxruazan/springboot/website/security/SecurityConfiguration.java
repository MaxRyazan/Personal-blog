package ru.maxruazan.springboot.website.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@ComponentScan
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	private final AuthConfigImpl authConfig;

	@Autowired
	public SecurityConfiguration(AuthConfigImpl authConfig) {
		this.authConfig = authConfig;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authConfig);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {                //метод для разделения доступа
		// @formatter:off
		http
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/").permitAll()
				.antMatchers("/login", "/user/new").anonymous()
					.anyRequest().authenticated()
				.and()
					.formLogin()
//				.loginPage("/login.html")
//				.loginProcessingUrl("/login/process")
//				.usernameParameter("email")          //спринг по дефолту ищет username, а у нас email
				.and()
					.logout();
		// @formatter:on
	}


//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
