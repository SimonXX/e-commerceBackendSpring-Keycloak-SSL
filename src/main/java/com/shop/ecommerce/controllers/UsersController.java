package com.shop.ecommerce.controllers;


import com.shop.ecommerce.entities.User;
import com.shop.ecommerce.services.UserService;
import com.shop.ecommerce.support.ResponseMessage;
import com.shop.ecommerce.support.exceptions.MailUserAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService accountingService;



    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid User user) {//il controller chiama il metodo del service
        try {
            User added = accountingService.registerUser(user);
            return new ResponseEntity(added, HttpStatus.OK);
        } catch (MailUserAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage("ERROR_MAIL_USER_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('client_admin')")
    @GetMapping
    public List<User> getAll() {
        return accountingService.getAllUsers();
    }


    @PreAuthorize("hasRole('client_user') or hasRole('client_admin')")
    @GetMapping("/search")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        User user = accountingService.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
