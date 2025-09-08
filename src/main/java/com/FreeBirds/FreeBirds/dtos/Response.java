package com.FreeBirds.FreeBirds.dtos;

import com.FreeBirds.FreeBirds.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    // Generic fields
    private int status; // HTTP status code
    private String message; // success/error message
    private final LocalDateTime timestamp = LocalDateTime.now();

    // Auth fields
    private String token;
    private UserRole role;
    private Boolean active;
    private String expirationTime;
    private Object data;

    // User data
    private UserDTO user;
    private List<UserDTO> users;

    // Job data
    private JobDTO job;
    private List<JobDTO> jobs;

    // Proposal data
    private ProposalDTO proposal;
    private List<ProposalDTO> proposals;

    // Contract data
    private ContractDTO contract;
    private List<ContractDTO> contracts;

    // Payment data
    private PaymentDTO payment;
    private List<PaymentDTO> payments;

    // Review data
    private ReviewDTO review;
    private List<ReviewDTO> reviews;

    // Notification data
    private NotificationDTO notification;
    private List<NotificationDTO> notifications;

    private SkillDTO skill;
    private List<SkillDTO> skills;

    private ProjectDTO project;
    private List<ProjectDTO> projects;

    private EducationDTO education;
    private List<EducationDTO> educationList;
}
