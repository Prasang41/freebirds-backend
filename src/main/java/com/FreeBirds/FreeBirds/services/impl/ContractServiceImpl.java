package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.ContractDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Contract;
import com.FreeBirds.FreeBirds.repository.ContractRepository;
import com.FreeBirds.FreeBirds.services.ContractService;
import com.FreeBirds.FreeBirds.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ModelMapper mapper;
    private final NotificationService notificationService; // Inject notification service

    @Override
    public Response createContract(ContractDTO contractDTO) {
        Contract contract = mapper.map(contractDTO, Contract.class);
        Contract saved = contractRepository.save(contract);

        // Send notification after creating contract
        notificationService.sendContractStatusNotification(
                saved.getUser().getId(),
                saved.getUser().getEmail(),
                saved.getJob().getTitle(),
                saved.getStatus().name()
        );

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Contract created successfully")
                .contract(mapper.map(saved, ContractDTO.class))
                .build();
    }

    @Override
    public Response getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Contract retrieved successfully")
                .contract(mapper.map(contract, ContractDTO.class))
                .build();
    }

    @Override
    public Response getAllContracts() {
        List<ContractDTO> contracts = contractRepository.findAll()
                .stream()
                .map(c -> mapper.map(c, ContractDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Contracts retrieved successfully")
                .contracts(contracts)
                .build();
    }

    @Override
    public Response updateContract(Long id, ContractDTO contractDTO) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        mapper.map(contractDTO, contract);
        Contract updated = contractRepository.save(contract);

        // Send notification after updating contract
        notificationService.sendContractStatusNotification(
                updated.getUser().getId(),
                updated.getUser().getEmail(),
                updated.getJob().getTitle(),
                updated.getStatus().name()
        );

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Contract updated successfully")
                .contract(mapper.map(updated, ContractDTO.class))
                .build();
    }

    @Override
    public Response deleteContract(Long id) {
        contractRepository.deleteById(id);

        return Response.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Contract deleted successfully")
                .build();
    }
}
