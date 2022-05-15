package ru.maxruazan.springboot.website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.sql.DataSource;

@EnableWebSecurity
@ComponentScan
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
                http    .csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/").permitAll()
                        .and()
                        .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login/process")
                        .usernameParameter("email")          //спринг по дефолту ищет username, а у нас email
                        .and()
                        .logout();

        }
}
