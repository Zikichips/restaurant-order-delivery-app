package com.foodOrderApp.FoodOrderDelivery.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subscriber")
public class SubscriberUserController {

    private UserService userService;

    public SubscriberUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myprofile")
    public ResponseEntity<UserResponseDTO> getUserById(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        if(user != null) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(user.getId());
            userResponseDTO.setUsername(user.getUsername());
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setRole(user.getRole().toString());

            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update-my-profile")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user) {
        User userToUpdate = userService.findByUsername(userDetails.getUsername());
        boolean updated = userService.updateUser(userToUpdate.getId(), user);
        if(updated) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User could not be updated", HttpStatus.BAD_REQUEST);
    }
}
