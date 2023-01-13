package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;

@Component
@Transactional
public class UserBuilder implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role role_admin = new Role("ROLE_ADMIN");
        Role role_user = new Role("ROLE_USER");

        User usr = new User("looser@mail.ru","Иван", "Иванов","looser",
                "$2a$12$5PZjnRHaJ/BIF6BGE3KA4ep0DGiFogdkhozNzCYJIlQDbB8HGhnLa", 23,
                Collections.singletonList(role_user));

        User admin = new User("admin@mail.ru","Петр", "Петров","admin",
                "$2a$12$5PZjnRHaJ/BIF6BGE3KA4ep0DGiFogdkhozNzCYJIlQDbB8HGhnLa", 33,
                Collections.singletonList(role_admin));

        entityManager.persist(usr);
        entityManager.persist(admin);
    }
}
