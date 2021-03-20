package com.myexample.demofullstack.controller;

import com.myexample.demofullstack.dto.EditUserRequestDto;
import com.myexample.demofullstack.model.AppUser;
import com.myexample.demofullstack.service.FormValidationService;
import com.myexample.demofullstack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FormValidationService formValidationService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody AppUser user, BindingResult result){
        if (result.hasErrors()) return formValidationService.formValidation(result);
        AppUser newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> fetchUser(@PathVariable String username, Principal principal){
        AppUser user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser( @RequestBody EditUserRequestDto editUserRequestDto, BindingResult result, Principal principal){
        if (result.hasErrors()) return formValidationService.formValidation(result);
        AppUser updatedUser = userService.updateUser(editUserRequestDto, principal.getName());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public String deactivateUser(@PathVariable String username, Principal principal){
        return userService.deactivateAccount(principal.getName());
    }

    @PatchMapping(value = "/{username}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProfilePicture(@RequestParam("image") MultipartFile file1, @PathVariable String username, Principal principal){
        AppUser user = userService.addProfilePicture(principal.getName(), file1);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }



}
