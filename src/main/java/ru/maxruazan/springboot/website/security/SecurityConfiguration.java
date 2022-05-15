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

	private final DataSource dataSource;

	private final AuthConfigImpl authConfig;

	@Autowired
	public SecurityConfiguration(DataSource dataSource, AuthConfigImpl authConfig) {
		this.dataSource = dataSource;
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

//	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//				.and()
//				.withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
//				.and()
//				.withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
