package com.FreeBirds.FreeBirds.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviewe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;
    private  String feedback;

    @ManyToOne
    private  User reviewer;

    @ManyToOne
    private User reviewee;

    @ManyToOne
    private  Contract contract;

}
