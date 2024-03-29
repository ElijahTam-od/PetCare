package com.rijai.users.services;

import com.rijai.users.model.User;
import com.rijai.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers()
    {
        List<User>userRecords = new ArrayList<>();
        userRepository.findAll().forEach(userRecords::add);
        return userRecords;
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        }
        else
            return null;
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            return user.get();
        }
        else
            return null;
    }

    public User getUserByEmailAndPassword(String email, String password){
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if(user.isPresent()) {
            return user.get();
        }
        else
            return null;
    }
    public User addUser(User user)
    {
        return userRepository.save(user);
    }
    public User updateUser(User user)
    {
        return userRepository.save(user);
    }
    public User getUser(int id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        else
            return null;
    }
    public void deleteUser(int id)
    {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.delete(user.get());
        }
    }
}
