package com.FreeBirds.FreeBirds.dtos;

import com.FreeBirds.FreeBirds.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private UserRole role;
    private  Boolean isActive;
    public LocalDateTime createdAt;
    private String experience;
    private String resumePath;

    private List<SkillDTO> skills;
    private List<ProjectDTO> projects;
    private List<EducationDTO> education;



}
