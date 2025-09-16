package com.FreeBirds.FreeBirds.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String specialization;
    private String collegeName;
    private Integer endingyear;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
