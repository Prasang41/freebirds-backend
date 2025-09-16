package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.ContractDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Contract;
import com.FreeBirds.FreeBirds.entities.Job;
import com.FreeBirds.FreeBirds.entities.User;
import com.FreeBirds.FreeBirds.enums.ContractStatus;
import com.FreeBirds.FreeBirds.repository.ContractRepository;
import com.FreeBirds.FreeBirds.repository.JobRepository;
import com.FreeBirds.FreeBirds.repository.UserRepository;
import com.FreeBirds.FreeBirds.services.ContractService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createContract(ContractDTO contractDTO) {
        Optional<User> client = userRepository.findById(contractDTO.getClientId());
        Optional<User> freelancer = userRepository.findById(contractDTO.getFreelancerId());
        Optional<Job> job = jobRepository.findById(contractDTO.getJobId());

        if (client.isEmpty() || freelancer.isEmpty() || job.isEmpty()) {
            return Response.builder()
                    .status(400)
                    .message("Invalid client, freelancer, or job ID")
                    .build();
        }

        Contract contract = Contract.builder()
                .client(client.get())
                .freelancer(freelancer.get())
                .job(job.get())
                .status(contractDTO.getStatus() != null ? contractDTO.getStatus() : ContractStatus.PENDING)
                .build();

        Contract saved = contractRepository.save(contract);

        return Response.builder()
                .status(201)
                .message("Contract created successfully")
                .contract(modelMapper.map(saved, ContractDTO.class))
                .build();
    }

    @Override
    public Response getContractById(Long id) {
        return contractRepository.findById(id)
                .map(contract -> Response.builder()
                        .status(200)
                        .message("Contract found")
                        .contract(modelMapper.map(contract, ContractDTO.class))
                        .build())
                .orElse(Response.builder()
                        .status(404)
                        .message("Contract not found")
                        .build());
    }

    @Override
    public Response getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();

        return Response.builder()
                .status(200)
                .message("All contracts fetched")
                .contracts(
                        contracts.stream()
                                .map(c -> modelMapper.map(c, ContractDTO.class))
                                .toList()
                )
                .build();
    }

    @Override
    public Response updateContract(Long id, ContractDTO contractDTO) {
        Optional<Contract> existing = contractRepository.findById(id);

        if (existing.isEmpty()) {
            return Response.builder()
                    .status(404)
                    .message("Contract not found")
                    .build();
        }

        Contract contract = existing.get();

        if (contractDTO.getStatus() != null) {
            contract.setStatus(contractDTO.getStatus());
        }
        if (contractDTO.getClientId() != null) {
            userRepository.findById(contractDTO.getClientId())
                    .ifPresent(contract::setClient);
        }
        if (contractDTO.getFreelancerId() != null) {
            userRepository.findById(contractDTO.getFreelancerId())
                    .ifPresent(contract::setFreelancer);
        }
        if (contractDTO.getJobId() != null) {
            jobRepository.findById(contractDTO.getJobId())
                    .ifPresent(contract::setJob);
        }

        Contract updated = contractRepository.save(contract);

        return Response.builder()
                .status(200)
                .message("Contract updated successfully")
                .contract(modelMapper.map(updated, ContractDTO.class))
                .build();
    }

    @Override
    public Response deleteContract(Long id) {
        Optional<Contract> existing = contractRepository.findById(id);

        if (existing.isEmpty()) {
            return Response.builder()
                    .status(404)
                    .message("Contract not found")
                    .build();
        }

        contractRepository.delete(existing.get());

        return Response.builder()
                .status(200)
                .message("Contract deleted successfully")
                .build();
    }
}
