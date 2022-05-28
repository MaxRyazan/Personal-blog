package ru.maxruazan.springboot.website.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/user/new").permitAll()
				.antMatchers("/blog/about").permitAll()
				.antMatchers(HttpMethod.GET, "/blog", "/blog/{id}").permitAll()
				.antMatchers(HttpMethod.GET, "/blog/add", "/blog/{id}/edit").hasAnyRole("USER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/blog/add", "/blog/{id}/edit").hasAnyRole("USER", "ADMIN")
				.antMatchers("/blog/{id}/remove").hasRole("ADMIN")
				.anyRequest().authenticated()
					.and()
				.formLogin()
				.loginPage("/user/registration").permitAll()
				.loginProcessingUrl("/perform-login")
				.defaultSuccessUrl("/user/successes", true)
				.usernameParameter("email")
					.and()
				.logout()
				.logoutUrl("/user/logout")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
