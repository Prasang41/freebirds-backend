package com.FreeBirds.FreeBirds.entities;

import com.FreeBirds.FreeBirds.enums.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proposals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double bidAmount;

    @Column(length = 2000)
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private User client;


    // ✅ Many proposals can belong to one freelancer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "freelancer_id", nullable = false)
    @JsonIgnore
    private User user;

    // ✅ Many proposals can belong to one job
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnore
    private Job job;
}
