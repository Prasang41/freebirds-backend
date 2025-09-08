package com.FreeBirds.FreeBirds.entities;

import com.FreeBirds.FreeBirds.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private String title;
    private String message;
    private  boolean read = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;
}
