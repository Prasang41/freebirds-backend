package com.FreeBirds.FreeBirds.dtos;

import com.FreeBirds.FreeBirds.enums.ContractStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractDTO {
    private Long id;
    private Long clientId;
    private Long freelancerId;
    private Long jobId;
    private ContractStatus status;
}