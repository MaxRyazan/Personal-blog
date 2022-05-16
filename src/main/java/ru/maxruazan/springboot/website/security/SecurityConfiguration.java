package ru.maxruazan.springboot.website.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
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
				.antMatchers("/user/test", "/user/new").anonymous()
					.anyRequest().authenticated()
				.and()
					.formLogin()
				.loginPage("/user/test")
				.defaultSuccessUrl("/user/successes")
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID");
		// @formatter:on
	}


//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
