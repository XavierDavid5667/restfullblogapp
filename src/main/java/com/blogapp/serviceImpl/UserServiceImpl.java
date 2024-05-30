package com.blogapp.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blogapp.dto.UserDTO;
import com.blogapp.entities.User;
import com.blogapp.exception.ResouceNotFoundException;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * This class implements the UserService interface to provide the business
 * logic for CRUD operations on users.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // Autowiring the UserRepository to interact with the database.
    @Autowired
    private UserRepository userRepository;

    // Autowiring the ModelMapper to convert between User and UserDTO.
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Creates a new user.
     * 
     * @param userDTO The data transfer object containing user details.
     * @return The created UserDTO.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user with email: {}", userDTO.getEmail());
        User savedUser = userRepository.save(dtoToUser(userDTO));
        log.info("User created successfully with ID: {}", savedUser.getId());
        return userToDto(savedUser);
    }

    /**
     * Updates an existing user.
     * 
     * @param userDTO The data transfer object containing updated user details.
     * @param userId The ID of the user to be updated.
     * @return The updated UserDTO.
     * @throws ResouceNotFoundException if the user is not found.
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        log.info("Updating user with ID: {}", userId);
        User userFromRepo = userRepository.findById(userId)
                .orElseThrow(() -> new ResouceNotFoundException("User with id " + userId + " not found!!"));
        userFromRepo.setName(userDTO.getName());
        userFromRepo.setEmail(userDTO.getEmail());
        userFromRepo.setPassword(userDTO.getPassword());
        userFromRepo.setAbout(userDTO.getAbout());
        User updatedUser = userRepository.save(userFromRepo);
        log.info("User with ID: {} updated successfully", userId);
        return userToDto(updatedUser);
    }

    /**
     * Retrieves a user by its ID.
     * 
     * @param userId The ID of the user to be retrieved.
     * @return The UserDTO.
     * @throws ResouceNotFoundException if the user is not found.
     */
    @Override
    public UserDTO getUserById(Integer userId) {
        log.info("Retrieving user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResouceNotFoundException("User with id " + userId + " not found!!"));
        log.info("User with ID: {} retrieved successfully", userId);
        return userToDto(user);
    }

    /**
     * Retrieves all users.
     * 
     * @return A list of all UserDTO.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Retrieving all users");
        List<User> users = userRepository.findAll();
        log.info("All users retrieved successfully");
        return users.stream().map(this::userToDto).toList();
    }

    /**
     * Deletes a user by its ID.
     * 
     * @param userId The ID of the user to be deleted.
     * @throws ResouceNotFoundException if the user is not found.
     */
    @Override
    public void deleteUser(Integer userId) {
        log.info("Deleting user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResouceNotFoundException("User with id " + userId + " not found!!"));
        userRepository.delete(user);
        log.info("User with ID: {} deleted successfully", userId);
    }

    /**
     * Converts a UserDTO to a User entity.
     * 
     * @param userDTO The UserDTO to be converted.
     * @return The User entity.
     */
    private User dtoToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Converts a User entity to a UserDTO.
     * 
     * @param user The User entity to be converted.
     * @return The UserDTO.
     */
    private UserDTO userToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
