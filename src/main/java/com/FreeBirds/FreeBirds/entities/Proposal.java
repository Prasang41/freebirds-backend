package com.FreeBirds.FreeBirds.entities;

import com.FreeBirds.FreeBirds.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proposal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double bidAmount;
    private  String coverletter;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

    @ManyToOne
    private Job job;
}
