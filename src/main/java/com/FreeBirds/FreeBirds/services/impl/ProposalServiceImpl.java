package com.FreeBirds.FreeBirds.services.impl;

import com.FreeBirds.FreeBirds.dtos.ProposalDTO;
import com.FreeBirds.FreeBirds.dtos.Response;
import com.FreeBirds.FreeBirds.entities.Proposal;
import com.FreeBirds.FreeBirds.repository.ProposalRepository;
import com.FreeBirds.FreeBirds.services.ProposalService;
import com.FreeBirds.FreeBirds.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final ModelMapper mapper;
    private final NotificationService notificationService; // Inject notification service

    @Override
    public Response createProposal(ProposalDTO proposalDTO) {
        Proposal proposal = mapper.map(proposalDTO, Proposal.class);
        Proposal saved = proposalRepository.save(proposal);

        // Send notification after creating proposal
        notificationService.sendProposalNotification(
                saved.getUser().getId(),
                saved.getUser().getEmail(),
                saved.getJob().getTitle(),
                saved.getStatus().name()
        );

        return Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Proposal created successfully")
                .proposal(mapper.map(saved, ProposalDTO.class))
                .build();
    }

    @Override
    public Response getProposalById(Long id) {
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposal retrieved successfully")
                .proposal(mapper.map(proposal, ProposalDTO.class))
                .build();
    }

    @Override
    public Response getAllProposals() {
        List<ProposalDTO> proposals = proposalRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, ProposalDTO.class))
                .collect(Collectors.toList());

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposals retrieved successfully")
                .proposals(proposals)
                .build();
    }

    @Override
    public Response updateProposal(Long id, ProposalDTO proposalDTO) {
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        mapper.map(proposalDTO, proposal);
        Proposal updated = proposalRepository.save(proposal);

        // Send notification after updating proposal
        notificationService.sendProposalNotification(
                updated.getUser().getId(),
                updated.getUser().getEmail(),
                updated.getJob().getTitle(),
                updated.getStatus().name()
        );

        return Response.builder()
                .status(HttpStatus.OK.value())
                .message("Proposal updated successfully")
                .proposal(mapper.map(updated, ProposalDTO.class))
                .build();
    }

    @Override
    public Response deleteProposal(Long id) {
        proposalRepository.deleteById(id);

        return Response.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Proposal deleted successfully")
                .build();
    }
}
