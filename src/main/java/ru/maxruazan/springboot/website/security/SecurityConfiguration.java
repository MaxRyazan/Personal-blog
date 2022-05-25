package ru.maxruazan.springboot.website.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.maxruazan.springboot.website.service.UserService;



@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	private final UserService userService;


	@Autowired
	public SecurityConfiguration(UserService userService) {
        this.userService = userService;

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/user/new").permitAll()
				.antMatchers("/blog/about").permitAll()
				.antMatchers(HttpMethod.GET, "/blog").permitAll()
				.antMatchers(HttpMethod.GET, "/blog/add", "/blog/{id}/edit").hasRole("USER_ROLE")
				.antMatchers(HttpMethod.POST, "/blog/add", "/blog/{id}/edit").hasRole("USER_ROLE")
				.antMatchers("/blog/{id}/remove").hasRole("ADMIN_ROLE")
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

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//				.dataSource(dataSource)
//				.passwordEncoder(passwordEncoder())
//				.usersByUsernameQuery("select email, password, 'true' as enabled from t_user where email=?")
//				.authoritiesByUsernameQuery("SELECT email, role_name FROM t_user_roles e \n" +
//						"JOIN t_role r ON e.user_id=r.user_id \n" +
//						"JOIN t_role d ON r.role_id=d.role_id;");
//	}


	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
}
