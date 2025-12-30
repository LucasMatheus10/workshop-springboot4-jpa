package com.estudosjava.course.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudosjava.course.entities.User;
import com.estudosjava.course.repositories.UserRepository;

@Service
public class UserServices {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).get();
    }

    public User insert(User obj) {
        return repository.save(obj);
    }
}
