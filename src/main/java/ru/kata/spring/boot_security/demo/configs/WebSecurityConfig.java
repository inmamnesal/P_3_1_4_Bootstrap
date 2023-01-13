package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.persistence.EntityManager;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userService;
    private final EntityManager entityManager;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService, EntityManager entityManager) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.entityManager = entityManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/index").permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .failureUrl("/index")
//                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

/*    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/


     /* @Bean
    public String createUsers(){
          Role role_admin = new Role("ROLE_ADMIN");
          Role role_user = new Role("ROLE_USER");

          User usr = new User("looser",
                  "{bcrypt}$2a$12$5PZjnRHaJ/BIF6BGE3KA4ep0DGiFogdkhozNzCYJIlQDbB8HGhnLa",
                            Collections.singletonList(role_user));

          User admin = new User("admin",
                  "{bcrypt}$2a$12$5PZjnRHaJ/BIF6BGE3KA4ep0DGiFogdkhozNzCYJIlQDbB8HGhnLa",
                  Collections.singletonList(role_admin));

//        UserDetails user = User.builder()
//                .username("looser")
//                .password("{bcrypt}$2a$12$5PZjnRHaJ/BIF6BGE3KA4ep0DGiFogdkhozNzCYJIlQDbB8HGhnLa")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$5PZjnRHaJ/BIF6BGE3KA4ep0DGiFogdkhozNzCYJIlQDbB8HGhnLa")
//                .roles("ADMIN","USER")
//                .build();

//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

          entityManager.persist(usr);
          entityManager.persist(admin);
          return "created";
    }*/


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}