package com.FreeBirds.FreeBirds.services;

import com.FreeBirds.FreeBirds.dtos.LoginRequestDTO;
import com.FreeBirds.FreeBirds.dtos.RegistrationRequestDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.dtos.UserDTO;
import com.FreeBirds.FreeBirds.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Response registerUser(RegistrationRequestDTO registerRequestDTO);
    Response loginUser(LoginRequestDTO loginRequestDTO);
    Response updateOwnAccount(Long id , UserDTO userDTO, MultipartFile resume);
    Response getAccountDetails();
    User getCurrentLoggedInUser();
    Response deleteOwnAccount();
    Response getUserById(Long id);
    Response getAllUsers();
    Response getMyContract();
    Response getMyProposal();
}
