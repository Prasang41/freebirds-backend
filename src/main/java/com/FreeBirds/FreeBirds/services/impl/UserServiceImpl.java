package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.*;
import com.FreeBirds.FreeBirds.entities.*;
import com.FreeBirds.FreeBirds.enums.NotificationType;
import com.FreeBirds.FreeBirds.enums.UserRole;
import com.FreeBirds.FreeBirds.exceptions.InvalidCredentialException;
import com.FreeBirds.FreeBirds.exceptions.NotFoundException;
import com.FreeBirds.FreeBirds.repository.ContractRepository;
import com.FreeBirds.FreeBirds.repository.ProposalRepository;
import com.FreeBirds.FreeBirds.repository.UserRepository;
import com.FreeBirds.FreeBirds.security.JwtUtils;
import com.FreeBirds.FreeBirds.services.NotificationService;
import com.FreeBirds.FreeBirds.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final ContractRepository contractRepository;
    private final ProposalRepository proposalRepository;
    private final NotificationService notificationService;

    @Override
    public Response registerUser(RegistrationRequestDTO registerRequestDTO) {
        UserRole role= UserRole.FREELANCER;
        if(registerRequestDTO.getRole()!=null)
        {
            role = registerRequestDTO.getRole();
        }
        User userToSave = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .role(role)
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .phoneNumber(registerRequestDTO.getPhoneNumber())
                .isActive(true)
                .build();

        userRepository.save(userToSave);

        Response notificationResponse = notificationService.sendUserRegistrationNotification(
                userToSave.getId(),
                userToSave.getEmail(),
                userToSave.getFirstName()
        );
        return Response.builder()
                .status(200)
                .message("User Registration Successfull")
                .user(modelMapper.map(userToSave, UserDTO.class))
                .notification(notificationResponse.getNotification())
                .build();
    }

    @Override
    public Response loginUser(LoginRequestDTO loginRequestDTO) {
        User user=userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new NotFoundException("Email Not Found"));
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new InvalidCredentialException("Wrong Password");
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return Response.builder().status(200)
                .message("Login Successfull")
                .role(user.getRole())
                .user(UserDTO.builder().id(user.getId()).email(user.getEmail()).build())
                .token(token)
                .active(user.getIsActive())
                .expirationTime("6 months")
                .build();
    }


    @Override
    public Response updateOwnAccount(Long id, UserDTO userDTO, MultipartFile resume) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update simple fields
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setExperience(userDTO.getExperience());

        // Clear and add skills
        user.getSkills().clear();
        if (userDTO.getSkills() != null) {
            userDTO.getSkills().forEach(skillDTO -> {
                Skill skill = new Skill();
                skill.setSkillName(skillDTO.getSkillName());
                skill.setUser(user);
                user.getSkills().add(skill);
            });
        }

        // Clear and add projects
        user.getProjects().clear();
        if (userDTO.getProjects() != null) {
            userDTO.getProjects().forEach(projectDTO -> {
                Project project = new Project();
                project.setProjectName(projectDTO.getProjectName());
                project.setDescription(projectDTO.getDescription());
                project.setUser(user);
                user.getProjects().add(project);
            });
        }

        // Clear and add education
        user.getEducation().clear();
        if (userDTO.getEducation() != null) {
            userDTO.getEducation().forEach(eduDTO -> {
                Education edu = new Education();
                edu.setCourseName(eduDTO.getCourseName());
                edu.setSpecialization(eduDTO.getSpecialization());
                edu.setCollegeName(eduDTO.getCollegeName());
                edu.setEndingyear(eduDTO.getEndingyear());
                edu.setUser(user);
                user.getEducation().add(edu);
            });
        }

        // Handle Resume file
        if (resume != null && !resume.isEmpty()) {
            try {
                File uploadDir = new File("C:/Users/VIKAS KUMAR/Downloads/FreeBirds/");
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String fileName = id + "_" + resume.getOriginalFilename();
                String filePath = "C:/Users/VIKAS KUMAR/Downloads/FreeBirds/" + fileName;
                resume.transferTo(new File(filePath));

                user.setResumePath(filePath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload resume: " + e.getMessage());
            }
        }

        user.setCreatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        return Response.builder()
                .status(200)
                .message("Account updated successfully")
                .user(modelMapper.map(user, UserDTO.class))
                .build();
    }

    @Override
    public Response getAccountDetails() {
        User user = getCurrentLoggedInUser();
        return Response.builder()
                .status(200)
                .message("User Details Fetched")
                .data(modelMapper.map(user, UserDTO.class))
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
    }

    @Override
    public Response deleteOwnAccount() {
        User currentUser = getCurrentLoggedInUser();
        userRepository.delete(currentUser);
        return Response.builder()
                .status(200)
                .message("Account Deleted Successfully")
                .build();
    }

    @Override
    public Response getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return Response.builder()
                .status(200)
                .message("User Fetched")
                .data(modelMapper.map(user, UserDTO.class))
                .build();
    }

    @Override
    public Response getAllUsers() {
        return Response.builder()
                .status(200)
                .message("All Users Fetched")
                .data(userRepository.findAll()
                        .stream()
                        .map(user -> modelMapper.map(user, UserDTO.class))
                        .toList())
                .build();
    }

    @Override
    public Response getMyContract() {
        User currentUser = getCurrentLoggedInUser();

        List<Contract> contracts;
        if (currentUser.getRole() == UserRole.CLIENT) {
            contracts = contractRepository.findByClient(currentUser);
        } else {
            contracts = contractRepository.findByFreelancer(currentUser);
        }

        return Response.builder()
                .status(200)
                .message("Contracts Fetched")
                .contracts(
                        contracts.stream()
                                .map(c -> modelMapper.map(c, ContractDTO.class))
                                .toList()
                )
                .build();
    }

    @Override
    public Response getMyProposal() {
        User currentUser = getCurrentLoggedInUser();
        return Response.builder()
                .status(200)
                .message("Proposals Fetched")
                .data(proposalRepository.findByUser_Id(currentUser.getId()))
                .build();
    }
}
