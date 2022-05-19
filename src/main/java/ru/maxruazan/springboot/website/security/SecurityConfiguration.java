package ru.maxruazan.springboot.website.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final DataSource dataSource;
	@Autowired
	public SecurityConfiguration(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/user/registration", "/user/new").permitAll()
				.antMatchers(HttpMethod.GET, "/blog").permitAll()
				.antMatchers("/blog/about").permitAll()
					.anyRequest().authenticated()
				.and()
					.formLogin()
				.loginPage("/user/registration")
				.loginProcessingUrl("/perform-login")
				.defaultSuccessUrl("/user/successes", true)
				.usernameParameter("email")
				.and()
				.logout()
				.logoutUrl("/")
				.logoutSuccessUrl("/blog")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select email, password, 'true' as enabled from users where email=?")
				.authoritiesByUsernameQuery("SELECT email, 'ROLE_SUPERUSER' FROM users WHERE email=?");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
