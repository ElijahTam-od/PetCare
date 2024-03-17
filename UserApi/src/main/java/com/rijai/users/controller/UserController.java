package com.rijai.users.controller;

import com.rijai.users.model.User;
import com.rijai.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> getAllUser()
    {
        return userService.getAllUsers();
    }

    @RequestMapping(value="/get-user-by-id/{id}")
    public User getUser(@PathVariable int id)
    {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/get-user-by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User foundUser = userService.getUserByEmail(email);

        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value="/get-user-by-username/{username}")
    public User getUserByUsername(@PathVariable String username)
    {
        return userService.getUserByUsername(username);
    }

    @RequestMapping(value="/get-user-by-email-and-password")
    public User getUserByEmailAndPassword(@RequestParam("email") String email, @RequestParam("password") String password)
    {
        // Implement the logic to retrieve the user by email and password
        return userService.getUserByEmailAndPassword(email, password);
    }

    @RequestMapping(value="/add-user", method= RequestMethod.POST)
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @RequestMapping(value="/update-user", method=RequestMethod.PUT)
    public User updateUser(@RequestBody User user)
    {
        return userService.updateUser(user);
    }
    @RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
    }


}
