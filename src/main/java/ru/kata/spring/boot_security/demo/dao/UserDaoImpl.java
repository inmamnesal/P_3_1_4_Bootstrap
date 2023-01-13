package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;
    public UserDaoImpl() {
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUserByUsername(String email) {
        Query query = entityManager.createQuery("SELECT u FROM User u where u.email =:email", User.class);
        query.setParameter("email", email);
        Optional <List<User>> userList = Optional.ofNullable(query.getResultList());

//        Optional<User> user = Optional.ofNullable(entityManager.createQuery("SELECT u FROM User u where u.username =:username", User.class).getResultList().get(0));


        if (userList.get().isEmpty()) {
            throw new UsernameNotFoundException("email not found!");
        }

        return userList.get().get(0);
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUserById(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
