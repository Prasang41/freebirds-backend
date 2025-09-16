package com.FreeBirds.FreeBirds.dtos;


import com.FreeBirds.FreeBirds.enums.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProposalDTO {
    private Long id;
    private Double bidAmount;
    private String coverLetter;
    private ProposalStatus status;
private Long clientId;
    private Long freelancerId;
    private Long jobId;
}
