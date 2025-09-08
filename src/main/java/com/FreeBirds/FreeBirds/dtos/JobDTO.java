package com.FreeBirds.FreeBirds.dtos;

import com.FreeBirds.FreeBirds.enums.JobStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobDTO {
        private Long id;
        private String title;
        private String description;
        private Double budget;
        private LocalDateTime deadline;
        private JobStatus status;
        private Long clientId;
    }

