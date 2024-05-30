package com.blogapp.contollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blogapp.dto.UserDTO;
import com.blogapp.services.UserService;
import jakarta.validation.Valid;

/**
 * This class acts as a controller for handling HTTP requests related to users.
 * It maps HTTP requests to the appropriate service methods for CRUD operations on users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Autowiring the UserService to handle business logic related to users.
    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     * @param dto The data transfer object containing user details.
     * @return ResponseEntity containing the created UserDTO and HTTP status.
     */
    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO dto) {
        return new ResponseEntity<UserDTO>(userService.createUser(dto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     * @param user The data transfer object containing updated user details.
     * @param userId The ID of the user to be updated.
     * @return ResponseEntity containing the updated UserDTO and HTTP status.
     */
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO user, @PathVariable("id") Integer userId) {
        return new ResponseEntity<UserDTO>(userService.updateUser(user, userId), HttpStatus.OK);
    }

    /**
     * Retrieves a user by their ID.
     * @param userId The ID of the user to be retrieved.
     * @return ResponseEntity containing the UserDTO and HTTP status.
     */
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDTO> getUserById(@Valid @PathVariable("id") Integer userId) {
        return new ResponseEntity<UserDTO>(userService.getUserById(userId), HttpStatus.OK);
    }

    /**
     * Retrieves all users.
     * @return ResponseEntity containing a list of all UserDTO and HTTP status.
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<List<UserDTO>>(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Deletes a user.
     * @param userId The ID of the user to be deleted.
     * @return ResponseEntity containing a success message and HTTP status.
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<String>("User with id " + userId + " deleted successfully!!", HttpStatus.OK);
    }

}
