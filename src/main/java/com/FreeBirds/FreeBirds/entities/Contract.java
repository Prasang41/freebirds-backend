package com.FreeBirds.FreeBirds.entities;

import com.FreeBirds.FreeBirds.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contracts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

    @ManyToOne
    private Job job;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;


}
