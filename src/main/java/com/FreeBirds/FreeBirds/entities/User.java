package com.FreeBirds.FreeBirds.entities;

import com.FreeBirds.FreeBirds.enums.UserRole;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = false ,nullable = false)
    private String firstName;
    @Column(unique = false,nullable = false)
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // ✅ User posted jobs
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> postedJobs;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Proposal> freelancerProposals; // proposals made by freelancer

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Proposal> clientProposals; // proposals received by client


    // ✅ Skills
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    // ✅ Projects
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    // ✅ Education
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education = new ArrayList<>();

    // ✅ Experience
    private String experience;



    // ✅ Contracts
    // ✅ Contracts where this user is the client
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> clientContracts = new ArrayList<>();

    // ✅ Contracts where this user is the freelancer
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contract> freelancerContracts = new ArrayList<>();


    // ✅ Reviews
    // Reviews given by this user
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewsGiven ;

    // Reviews received by this user
    @OneToMany(mappedBy = "reviewee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewsReceived ;


    // ✅ Resume field
    private String resumePath;


    private Boolean isActive;
}

