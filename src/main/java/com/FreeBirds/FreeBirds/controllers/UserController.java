package com.FreeBirds.FreeBirds.controllers;

import com.FreeBirds.FreeBirds.dtos.LoginRequestDTO;
import com.FreeBirds.FreeBirds.dtos.RegistrationRequestDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.dtos.UserDTO;
import com.FreeBirds.FreeBirds.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public Response register(@RequestBody RegistrationRequestDTO dto) {
        return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequestDTO dto) {
        return userService.loginUser(dto);
    }

    @PutMapping(value = "/{id}/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> updateAccount(
            @PathVariable Long id,
            @RequestPart("user") String userJson,   // raw JSON string
            @RequestPart(value = "resume", required = false) MultipartFile resume
    ) {
        // Convert String -> DTO manually
        UserDTO userDTO;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userDTO = objectMapper.readValue(userJson, UserDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON in 'user' part", e);
        }

        Response response = userService.updateOwnAccount(id, userDTO, resume);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/me")
    public Response getAccountDetails() {
        return userService.getAccountDetails();
    }

    @DeleteMapping("/delete")
    public Response deleteAccount() {
        return userService.deleteOwnAccount();
    }

    @GetMapping("/{id}")
    public Response getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Response getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/my-contracts")
    public Response getMyContracts() {
        return userService.getMyContract();
    }

    @GetMapping("/my-proposals")
    public Response getMyProposals() {
        return userService.getMyProposal();
    }
}
